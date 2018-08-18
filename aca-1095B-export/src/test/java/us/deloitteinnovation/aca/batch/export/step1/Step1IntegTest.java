package us.deloitteinnovation.aca.batch.export.step1;

import com.google.common.collect.Maps;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dao.FilerDemographicsDao;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.export.ExportBatchJobExecutionListener;
import us.deloitteinnovation.aca.batch.export.ExportIntegTestConfiguration;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.entity.SourceSystemConfig;
import us.deloitteinnovation.aca.entity.SourceSystemConfigPK;
import us.deloitteinnovation.aca.repository.FilerDemographicTestRepository;
import us.deloitteinnovation.aca.repository.SourceSystemConfigRepository;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {ExportIntegTestConfiguration.class})
public class Step1IntegTest {

    @Autowired
    JobBuilderFactory jobs;

    @Autowired
    JobLauncher jobLauncher ;

    @Autowired
    JobRepository jobRepository ;

    @Autowired
    ExportJob1095Repository exportJobRepository ;

    @Autowired
    FlatFileItemReader<FilerDemographicDto> applicationReader ;

    @Autowired
    FilerDemographicsDao filerDemographicsDao ;

    @Autowired
    FilerDemographicTestRepository filerDemographicTestRepository ;

    @Autowired
    SourceSystemConfigRepository sourceSystemConfigRepository ;

    @Autowired
    Step step1OriginalConvertFilers ;

    @Autowired
    Step step1CorrectionConvertFilers;

    @Autowired
    Step step1ReplacementsConvertFilers;

    @Autowired
    ExportBatchJobExecutionListener exportBatchJobExecutionListener ;

    SourceSystemConfig config;

    List<FilerDemographicDto> filers;

    Integer taxYear;

    @Before
    public void before() throws Exception{
        taxYear = new Integer(2016);
        config = new SourceSystemConfig() ;
        config.setId(new SourceSystemConfigPK());
        config.getId().setSourceCd("FMTESTSTATE");
        config.getId().setTaxYear(taxYear.toString());
        config.setPrintPreferences("N");
        config.setStateAbbreviation("FM");
        config.setSourceCdId(67891);
        sourceSystemConfigRepository.save(config) ;

        BatchInfoDto batchInfo = new BatchInfoDto() ;
        // Insert some fake State data
        filers = Step1Form1095WriterTest.importFilers("classpath:FMTESTSTATE02262016_01.dat", applicationReader) ;
        for (FilerDemographicDto filer : filers) {
            filer.getId().setSourceCd(config.getId().getSourceCd());
            filer.setBatchInfo(batchInfo);
        }
        filerDemographicsDao.bulkInsert(filers);
    }

    @Test
    @Ignore
    public void step1Originals() throws Exception {
        Job job = jobStep1Originals();
        executeJob(job);
    }

    @Test
    @Ignore
    public void step1Corrections() throws Exception {
    /* Test Corrections - Mark the records as corrected */
        filerDemographicTestRepository.updateIrsTransmissionStatus("CO");
        Job job = jobStep1Corrections();
        executeJob(job);
    }

    @Ignore
    @Test
    public void step1Replacements() throws Exception {
    /* Test Replacements - Mark the records as reject corrected */
        filerDemographicTestRepository.updateIrsTransmissionStatus("RC");
        Job job = jobStep1Replacements();
        executeJob(job);
    }


    /* Job Definitions */
    private Job jobStep1Originals() {

        return jobs.get(BatchExportConstants.JobNames.ORIGINAL1095S).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step1OriginalConvertFilers)
                .end()
                .build();
    }

    private Job jobStep1Corrections() {

        return jobs.get(BatchExportConstants.JobNames.ORIGINAL1095S).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step1CorrectionConvertFilers)
                .end()
                .build();
    }

    private Job jobStep1Replacements() {

        return jobs.get(BatchExportConstants.JobNames.ORIGINAL1095S).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step1ReplacementsConvertFilers)
                .end()
                .build();
    }

    /* Helper method to execute jobs */
    private void executeJob(Job job) throws Exception {
        Map<String, JobParameter> params = Maps.newHashMap();
        params.put("uniqueTimeStamp", new JobParameter(System.currentTimeMillis(), true)) ;
        params.put(BatchExportConstants.JobPropertiesKeys.STATE, new JobParameter("FM", false)) ;
        params.put(BatchExportConstants.JobPropertiesKeys.YEAR, new JobParameter(2016L, false)) ;

        JobParameters jobParameters = new JobParameters(params);

        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils() ;
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(job);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus()) ;

        Step1Form1095Dto form1095b ;
        for (FilerDemographicDto filer : filers) {
            // The job will only process head-of-household
            if (filer.getFilerStatus().equalsIgnoreCase(CommonDataConstants.FilerStatus.FILER_STATUS_R + "") ||
                    filer.getFilerStatus().equalsIgnoreCase(CommonDataConstants.FilerStatus.FILER_STATUS_N + "")) {
                form1095b = exportJobRepository.getForm1095bById(config.getId().getSourceCd(), Integer.parseInt(filer.getId().getSourceUniqueId()), taxYear);
                Step1Form1095WriterTest.assertForm1095bNotNull(form1095b);
            }
        }
    }

    @After
    public void after(){
         /* Delete Filers by test state */
        filerDemographicTestRepository.deleteByRecipientState("FM") ;
        /* Delete the test state */
        sourceSystemConfigRepository.delete(config);
    }
}
