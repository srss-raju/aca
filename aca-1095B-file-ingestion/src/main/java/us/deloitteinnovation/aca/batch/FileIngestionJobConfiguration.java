package us.deloitteinnovation.aca.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.deloitteinnovation.aca.batch.constants.FileIngestionConstants;
import us.deloitteinnovation.aca.batch.ingest.step2.FileNameVerificationDecider;
import us.deloitteinnovation.aca.batch.listener.ImportFilersJobExecutionListener;


/**
 * Configuration for jobs and flows.
 */

@Configuration
@Import(FileIngestionConfiguration.class)
public class FileIngestionJobConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileIngestionJobConfiguration.class);
    @Autowired
    public ImportFilersJobExecutionListener importFilersJobExecutionListener;
    @Autowired
    JobBuilderFactory jobs;
    @Autowired
    Step step1RefreshStaging;
    @Autowired
    Step step2VerifyFilerData;
    @Autowired
    Step step3ValidateFilerData;
    @Autowired
    Step step4UpdateBatchCounts;
    @Autowired
    FileNameVerificationDecider fileNameVerificationDecider;


    @Bean
    public Job importFilers() {
        Flow refreshStagingJobFlow = new FlowBuilder<Flow>(FileIngestionConstants.FlowConstants.REFRESH_STAGING_JOB_FLOW)
                .start(step1RefreshStaging)
                .end();

        Flow verifyAndValidateJobFlow = new FlowBuilder<Flow>(FileIngestionConstants.FlowConstants.VERIFY_AND_VALIDATE_JOB_FLOW)
                .start(fileNameVerificationDecider)
                .on(FileIngestionConstants.FlowConstants.FLOW_STATUS_PASSED)
                .to(step2VerifyFilerData)
                .next(step3ValidateFilerData)
                .next(step4UpdateBatchCounts)
                .from(fileNameVerificationDecider)
                .on(FileIngestionConstants.FlowConstants.FLOW_STATUS_FAILED)
                .end()
                .build();

        return jobs.get(FileIngestionConstants.IMPORT_FILERS_JOB_NAME).preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(refreshStagingJobFlow)
                .on(FileIngestionConstants.JobConstants.JOB_STATUS_COMPLETED)
                .to(verifyAndValidateJobFlow)
                .end()
                .listener(importFilersJobExecutionListener)
                .build();
    }


}
