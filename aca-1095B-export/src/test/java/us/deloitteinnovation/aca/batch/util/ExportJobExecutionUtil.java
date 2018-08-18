package us.deloitteinnovation.aca.batch.util;

import com.google.common.collect.Maps;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.BatchTestUtil;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.batch.export.step4.Step4109495Data;
import us.deloitteinnovation.aca.validator.StateCode;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Util object for test cases to launch Spring Batch Job.
 *
 * @author yaojia
 * @since 2.1.0
 */

@Component
public class ExportJobExecutionUtil {

    @Autowired
    JobBuilderFactory jobs;
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    JobRepository jobRepository;


    public void executeJob(Job job, StateCode state, int taxYear) throws Exception {
        // Setup launch parameters
        Map<String, JobParameter> params = Maps.newHashMap();
        params.put("uniqueTimeStamp", new JobParameter(System.currentTimeMillis(), true));
        params.put(BatchExportConstants.JobPropertiesKeys.STATE, new JobParameter(state.toString().toUpperCase(), false));
        params.put(BatchExportConstants.JobPropertiesKeys.YEAR, new JobParameter(new Long(taxYear), false));
        JobParameters jobParameters = new JobParameters(params);
        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);

        // Ready to launch job
        jobLauncherTestUtils.setJob(job);
        launch(jobLauncherTestUtils, jobParameters);
    }

    public void executeJob(Job job, StateCode state, int taxYear, Map<String, String> args) throws Exception {
        // Setup launch parameters
        Map<String, JobParameter> params = Maps.newHashMap();
        params.put("uniqueTimeStamp", new JobParameter(System.currentTimeMillis(), true));
        params.put(BatchExportConstants.JobPropertiesKeys.STATE, new JobParameter(state.toString().toUpperCase(), false));
        params.put(BatchExportConstants.JobPropertiesKeys.YEAR, new JobParameter(new Long(taxYear), false));
        for (Map.Entry<String, String> entry : args.entrySet()) {
            params.put(entry.getKey(), new JobParameter(entry.getValue(), false));
        }
        JobParameters jobParameters = new JobParameters(params);
        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);

        // Ready to launch job
        jobLauncherTestUtils.setJob(job);
        launch(jobLauncherTestUtils, jobParameters);
    }

    private void launch(JobLauncherTestUtils jobLauncher, JobParameters parameters) throws Exception {
        JobExecution jobExecution = jobLauncher.launchJob(parameters);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}
