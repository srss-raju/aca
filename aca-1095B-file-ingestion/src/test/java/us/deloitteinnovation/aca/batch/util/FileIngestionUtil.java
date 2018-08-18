package us.deloitteinnovation.aca.batch.util;

import com.google.common.collect.Maps;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.ingest.step2.FileNameVerificationDecider;
import us.deloitteinnovation.aca.batch.listener.ImportFilersJobExecutionListener;
import us.deloitteinnovation.profile.ProfileProperties;

import java.util.Map;

/**
 * Created by yaojia on 11/29/2016.
 */

@Component
public class FileIngestionUtil {
    @Autowired
    MockDatUtil mockDatUtil;
    @Autowired
    ImportFilersJobExecutionListener importFilersJobExecutionListener;
    @Autowired
    JobBuilderFactory jobs;
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    FileNameVerificationDecider fileNameVerificationDecider;
    @Autowired
    Step step1RefreshStaging;
    @Autowired
    Step step2VerifyFilerData;
    @Autowired
    Step step3ValidateFilerData;
    @Autowired
    Step step4UpdateBatchCounts;
    @Autowired
    ProfileProperties profileProperties;

    private static JobExecution executeJob(String state, String year, JobLauncher jobLauncher, JobRepository jobRepository, Job job) throws Exception {
        Map<String, JobParameter> params = Maps.newHashMap();
        params.put("uniqueTimeStamp", new JobParameter(System.currentTimeMillis(), true));
        params.put(BatchExportConstants.JobPropertiesKeys.STATE, new JobParameter(state, false));
        params.put(BatchExportConstants.JobPropertiesKeys.YEAR, new JobParameter(year, false));
        JobParameters jobParameters = new JobParameters(params);

        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(job);

        return jobLauncherTestUtils.launchJob(jobParameters);
    }

    private static Job ingestFile(Step step1, JobExecutionDecider verificationDecider, Step step2, Step step3, Step step4,
                                 JobBuilderFactory jobBuilderFactory, JobExecutionListener listener) {
        Flow refreshStagingJobFlow = new FlowBuilder<Flow>("refreshStagingJobFlow")
                .start(step1)
                .end();

        Flow verifyAndValidateJobFlow = new FlowBuilder<Flow>("verifyAndValidateJobFlow")
                .start(verificationDecider)
                .on("PASSED")
                .to(step2)
                .next(step3)
                .next(step4)
                .from(verificationDecider)
                .on("FAILED")
                .end()
                .build();

        return jobBuilderFactory.get("importFilers").preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(refreshStagingJobFlow)
                .on("COMPLETED")
                .to(verifyAndValidateJobFlow)
                .end()
                .listener(listener)
                .build();
    }

    public boolean executeFileIngestion(String state, int year) {
        Job job = ingestFile(step1RefreshStaging, fileNameVerificationDecider, step2VerifyFilerData,
                step3ValidateFilerData, step4UpdateBatchCounts, jobs, importFilersJobExecutionListener);
        try {
            JobExecution jobExecution = executeJob(state, Integer.toString(year), jobLauncher, jobRepository, job);
            return ExitStatus.COMPLETED.equals(jobExecution.getExitStatus());
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
