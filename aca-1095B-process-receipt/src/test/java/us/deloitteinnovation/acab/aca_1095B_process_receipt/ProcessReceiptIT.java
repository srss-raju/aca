package us.deloitteinnovation.acab.aca_1095B_process_receipt;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import us.deloitteinnovation.aca.CommonConfiguration;
import us.deloitteinnovation.aca.batch.receipt.ProcessReceiptConfiguration;
import us.deloitteinnovation.aca.batch.receipt.ProcessReceiptListener;

import com.google.common.collect.Maps;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@SpringApplicationConfiguration(classes = { CommonConfiguration.class, ProcessReceiptConfiguration.class })
public class ProcessReceiptIT {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobBuilderFactory jobs;

    @Autowired
    Step confirmationFileProcessStep;

    @Autowired
    Step cleanupBatchMetaData;

    @Autowired
    ProcessReceiptListener processReceiptListener;

    @Test
    public void firstProcessCORFileTest() throws Exception {
        final Map<String, JobParameter> params = Maps.newHashMap();
        params.put("FILETYPE", new JobParameter("COR"));
        params.put("DATE", new JobParameter(new Date()));
        final JobParameters jobParameters = new JobParameters(params);
        final JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(processConfirmationFiles());
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        System.err.println(jobExecution.getExitStatus());
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    @Test
    public void secondProcessCOMFileTest() throws Exception {
        final Map<String, JobParameter> params = Maps.newHashMap();
        params.put("FILETYPE", new JobParameter("COM"));
        final JobParameters jobParameters = new JobParameters(params);

        final JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(processConfirmationFiles());
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        System.err.println(jobExecution.getExitStatus());
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    public Job processConfirmationFiles() {
        return jobs.get("processConfirmationFiles").preventRestart().incrementer(new RunIdIncrementer()).listener(processReceiptListener)
                .flow(confirmationFileProcessStep).next(cleanupBatchMetaData).end().build();
    }
}
