package us.deloitteinnovation.aca.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.service.PrintVendorFileImportMailerService;

/**
 * Created by bhchaganti on 9/1/2016.
 */
public class PrintVendorImportFilersJobExecutionListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintVendorImportFilersJobExecutionListener.class);
    @Autowired
    private BatchInfoDto batchInfo;

    @Autowired
    private PrintVendorFileImportMailerService fileImportMailerService;


    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        // Get the job execution status
        String jobExitCode = jobExecution.getExitStatus().getExitCode();

        if (jobExitCode.equals(BatchConstants.IMPORT_APPLICATION_FLOW_FAILED)) {

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Job Failed..Sending email..");
            }
            fileImportMailerService.sendMail(this.batchInfo, jobExecution);
        }


    }
}
