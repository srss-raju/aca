package us.deloitteinnovation.aca.batch.listener;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.constants.FileIngestionConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.service.FileImportMailerService;
import us.deloitteinnovation.aca.batch.service.ReportGenerationService;
import us.deloitteinnovation.profile.ProfileProperties;

/**
 * Created by bhchaganti on 9/1/2016.
 */
public class ImportFilersJobExecutionListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportFilersJobExecutionListener.class);
    @Autowired
    private BatchInfoDto batchInfo;

    @Autowired
    private FileImportMailerService fileImportMailerService;

    @Autowired
    private ReportGenerationService reportGenerationService;

    @Autowired
    private ProfileProperties profileProperties;
    
    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.debug("beforeJob method executing");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        generateReport(jobExecution);
        // Get the job execution status
        String jobExitCode = jobExecution.getExitStatus().getExitCode();

        if (jobExitCode.equals(BatchConstants.IMPORT_APPLICATION_FLOW_FAILED)) {

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Job Failed..Sending email..");
            }
            fileImportMailerService.sendMail(this.batchInfo, jobExecution);
        }


    }
    
 
    
    void generateReport(JobExecution jobExecution) {
        Object errorReportGenerated = jobExecution.getExecutionContext().get("errorReportGenerated");

        if (errorReportGenerated == null) {
            errorReportGenerated = "false";
        }

        if (!"true".equalsIgnoreCase(errorReportGenerated.toString())) {
            final String state = jobExecution.getJobParameters().getString(FileIngestionConstants.BATCH_ARG_STATE);
            final String outputFolderKey = state + BatchConstants.OUTPUT_FILE_FOLDER;
            final String outputFolder = profileProperties.getProperty(outputFolderKey);
            
            if(outputFolder==null ||  !new File(outputFolder).exists()){
                LOGGER.warn("State specific Reports folder does not exists for the given state code {} ",state);
                return;
            }

            try {
                reportGenerationService.generateRecordLevelReport(outputFolder, batchInfo.getBatchId());
            } catch (final Exception e) {
                LOGGER.error("Error while generating report", e);
            }
        }
    }
}
