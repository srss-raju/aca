package us.deloitteinnovation.aca.batch.ingest.step3;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.CommonConfiguration;
import us.deloitteinnovation.aca.batch.AbstractFileIngestionTestCase;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.profile.ProfileProperties;

import javax.sql.DataSource;
import java.util.Map;

import static org.junit.Assert.assertEquals;


/**
 * Created by tthakore on 9/1/2016.
 */

public class Step3FileValidationIT extends AbstractFileIngestionTestCase {


    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JobBuilderFactory jobs;
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    ProfileProperties profileProperties;

    @Autowired
    @Qualifier("step3ValidateFilerData")
    Step step3VerifyFilerData;

    String state;
    String year;


    @Before
    public void before() throws Exception {

        state = "AR";
        year = "2016";
    }

    @Test
    public void step3TestForSpringBatch() throws Exception {
        JobExecution jobExecution = executeJob(state, year);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    private JobExecution executeJob(String state, String year) throws Exception {

        Map<String, JobParameter> params = Maps.newHashMap();
        params.put("uniqueTimeStamp", new JobParameter(System.currentTimeMillis(), true));
        params.put(BatchExportConstants.JobPropertiesKeys.STATE, new JobParameter(state, false));
        params.put(BatchExportConstants.JobPropertiesKeys.YEAR, new JobParameter(year, false));
        JobParameters jobParameters = new JobParameters(params);

        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(startCRV());
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        return jobExecution;
    }

    protected Job startCRV() {
        Flow verifyAndValidateJobFlow = new FlowBuilder<Flow>("verifyAndValidateJobFlow")
                .start(step3VerifyFilerData)
                .on("COMPLETED")
                .end()
                .build();

        return jobs.get("importFilers").preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(verifyAndValidateJobFlow)
                .end()
                .build();
    }


}
