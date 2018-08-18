package us.deloitteinnovation.aca.batch.ingest.step1;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.util.Assert;
import us.deloitteinnovation.aca.batch.constants.FileIngestionConstants;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.ExceptionReportDto;
import us.deloitteinnovation.aca.batch.service.ExceptionReportService;
import us.deloitteinnovation.aca.batch.service.FileImportMailerService;
import us.deloitteinnovation.aca.repository.FilerDemographicStagingRepository;

/**
 * <p>
 * Tasklet to call a stored procedure which truncates FILER_DEMOGRAPHICS_STAGING
 * and copies data from FILER_DEMOGRAPHICS for a given state and tax year.
 * </p>
 */

public class Step1RefreshStagingTasklet implements Tasklet, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(Step1RefreshStagingTasklet.class);

    String state;
    String sourceCd;
    String year; // From batch arguments
    Integer taxYear;
    JobExecution jobExecution;
    @Autowired
    FilerDemographicStagingRepository filerDemographicStagingRepository;
    @Autowired
    ExceptionReportService exceptionReportService;
    @Autowired
    BatchInfoDto batchInfoDto;
    @Autowired
    FileImportMailerService fileImportMailerService;
    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService;
    SingleConnectionDataSource singleConnectionDataSource = null;
    JdbcTemplate singleConnJdbcTemplate;
    SimpleJdbcCall simpleJdbcCall;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        try {
            jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();

            state = (String) chunkContext.getStepContext().getJobParameters().get(FileIngestionConstants.BATCH_ARG_STATE);
            Assert.notNull(state, "Null value provided for parameter '" + FileIngestionConstants.BATCH_ARG_STATE + "'");
            Assert.hasLength(state, "Blank value provided for parameter '" + FileIngestionConstants.BATCH_ARG_STATE + "'");
            year = (String) chunkContext.getStepContext().getJobParameters().get(FileIngestionConstants.BATCH_ARG_TAX_YEAR);
            Assert.notNull(year, "Null value provided for parameter '" + FileIngestionConstants.BATCH_ARG_TAX_YEAR + "'");
            Assert.hasLength(year, "Blank value provided for parameter '" + FileIngestionConstants.BATCH_ARG_TAX_YEAR + "'");
            taxYear = Integer.valueOf(year);
            sourceCd = sourceSystemConfigDataService.getByState(state, taxYear).getSourceCd();

            /**
             * Set up a separate data source only for executing the stored procedure.
             * We are doing this because when the application starts, the connection retrieved from the
             * data source has the default isolation level, whereas the stored procedure is executed at the SNAPSHOT
             * isolation level. So if we try to call the stored procedure with data source configured
             * for this application, we get an exception because of mismatch in isolation levels.
             * In order to overcome the exception, we configure a single connection data source exclusively to execute
             * the stored procedure and set the isolation level for this single connection data source to SNAPSHOT.
             */
            singleConnectionDataSource = new SingleConnectionDataSource(this.jdbcTemplate.getDataSource().getConnection(), true);
            singleConnectionDataSource.getConnection().setTransactionIsolation(4096);// 4096 is the SNAPSHOT isolation level indicator for ms sql server.
            singleConnJdbcTemplate = new JdbcTemplate(singleConnectionDataSource);

            simpleJdbcCall = new SimpleJdbcCall(singleConnJdbcTemplate)
                    .withSchemaName("dbo")
                    .withProcedureName("ResetFilerDemoStaging");
            // Parameters to be passed to the stored proc.
            MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
            sqlParameterSource.addValue("source_cd", sourceCd);
            sqlParameterSource.addValue("tax_year", taxYear);
            // Execute the stored proc.
            simpleJdbcCall.execute(sqlParameterSource);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Staging table refreshed for {} state and tax year {}", state, taxYear);
            }
        } catch (Exception ex) {
            // Log errors into error table
            ExceptionReportDto exceptionReport = new ExceptionReportDto();
            exceptionReport.setSourceUniqueId(0l);
            exceptionReport.setBatchInfo(this.batchInfoDto);
            exceptionReport.setExDetails(ExceptionUtils.getRootCauseMessage(ex));
            exceptionReport.setRowNumber(0);
            exceptionReportService.save(exceptionReport);
            // Send email
            fileImportMailerService.sendMail(batchInfoDto, jobExecution);
            // Stop the job right here!!
            jobExecution.stop();
        }
        return RepeatStatus.FINISHED;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(jdbcTemplate);
    }

}