package us.deloitteinnovation.aca.batch.ingest.step3.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.ingest.step3.Step3FileIngestionCRVProcessor;
import us.deloitteinnovation.aca.batch.ingest.step3.Step3FileIngestionCRVWriter;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3InitialFilerList;
import us.deloitteinnovation.aca.batch.ingest.step3.services.Step3RecordValidationService;
import us.deloitteinnovation.profile.ProfileProperties;

import javax.sql.DataSource;
import java.util.ArrayList;
/**
 * <p>
 *    Step3FIExecutionListener class createdto gether all the unique list of uid's available in file.
 *    target  is to create list of all sourcecd-sourceUID and pass it to reader to process further
 *
 *    @see Step3RecordValidationService
 *    @see us.deloitteinnovation.aca.batch.FileIngestionJobConfiguration
 *    @see us.deloitteinnovation.aca.batch.ingest.step3.Step3FileIngestionCRVReader
 * </p>
 */
@Component
public class Step3FIExecutionListener extends StepExecutionListenerSupport {


    @Autowired
    DataSource dataSource;
    @Autowired
    ProfileProperties profileProperties;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    Step3RecordValidationService step3RecordValidationService;

    @Autowired
    Step3InitialFilerList step3InitialFilerList;

    private static Logger logger = LoggerFactory.getLogger(Step3RecordSkipListener.class);

    private StepExecution stepExecution;
    private String state;
    private String taxYear;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("start of reading initial records from DB Step3ExecutionListener class and beforeStep method ");
        this.stepExecution = stepExecution;
        try
        {
            step3InitialFilerList.getStep3InitialFilerList().addAll(step3RecordValidationService.getRecordsFromFileByStateandTaxYear(state, taxYear));
        }
        catch (Exception e)
        {
            logger.error("<-------------------------------------------------------------------------------------->");
            logger.error("Step3ExecutionListener :beforeStep :- Error in reading records for validation. ", e);
            logger.error("<-------------------------------------------------------------------------------------->");

            throw  new IllegalArgumentException(e);
        }
        logger.info("Number of initial records found in file is "+step3InitialFilerList.getStep3InitialFilerList().size());
        logger.info("start of reading initial records from DB Step3ExecutionListener class and beforeStep method ");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        return ExitStatus.COMPLETED;
    }

    @Value("#{jobParameters['STATE']}")
    public void setState(final String name) {
        state = name;
    }

    @Value("#{jobParameters['YEAR']}")
    public void setTaxYear(final String year) {
        taxYear = year;
    }
}
