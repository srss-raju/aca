package us.deloitteinnovation.aca.batch.ingest.step1;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import us.deloitteinnovation.aca.CommonConfiguration;
import us.deloitteinnovation.aca.batch.AbstractFileIngestionTestCase;
import us.deloitteinnovation.aca.batch.FileIngestionConfiguration;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Step1RefreshStagingTaskletIT extends AbstractFileIngestionTestCase {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    JobBuilderFactory jobs;
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    Step step1RefreshStaging;

    String state;
    String year;

    @Before
    public void before() throws Exception {
        state = "IN"; // Since we are inserting test records for IN state
        year = "2016"; // Since we are inserting test records or 2017 tax year
        // Setup the resource for the sql scripts.
        Resource deleteTestRecordScript = new ClassPathResource("DeleteTestRecord.sql");
        Resource insertTestRecordScript = new ClassPathResource("InsertTestRecord.sql");
        // Delete the test record
        JdbcTestUtils.executeSqlScript(jdbcTemplate, deleteTestRecordScript, false);
        // Load the test record
        JdbcTestUtils.executeSqlScript(jdbcTemplate, insertTestRecordScript, false);
    }

    @Test
    @Ignore
    /* Ignore since this is a integration test case */
    public void testStep1TaskletExecution() throws Exception {
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
        jobLauncherTestUtils.setJob(testStoredProc());

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        return jobExecution;
    }

    protected Job testStoredProc() {
        Flow refreshStagingJobFlow = new FlowBuilder<Flow>("refreshStagingJobFlow")
                .start(step1RefreshStaging)
                .end();
        return jobs.get("importFilers").preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(refreshStagingJobFlow)
                .end()
                .build();
    }
}