package us.deloitteinnovation.aca.batch.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;

/**
 * Created by tthakore on 3/25/2016.
 */
public class IrsErrorHandlingProtocolListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(IrsErrorHandlingProtocolListener.class);
    private ErrorReportingObjectStore errorReportingObjectStore = ErrorReportingObjectStore.getInstance();
    private long jobStartTime=0;
    private long jobEndTime=0;

    @Override
    public void beforeJob(JobExecution var1) {
        jobStartTime = System.currentTimeMillis();
    }


    @Override
    public void afterJob(JobExecution jobExecution) {
        ErrorReportingObjectStore errorReportingObjectStore = ErrorReportingObjectStore.getInstance();
        jobEndTime = System.currentTimeMillis();
        long totalTimeTakenInSec = (jobEndTime - jobStartTime)/1000;
        StringBuilder info = new StringBuilder();
        info.append("\n-------------------------------------------------------------------------------\n");
        info.append("\n*******************************************************************************\n");
        info.append("Total time taken in job execution is :-"+totalTimeTakenInSec+" sec \n");
        info.append("Job Status :-"+jobExecution.getStatus()+"\n");
        info.append("Processed Files are :- \n");
        if(errorReportingObjectStore.getProcessedFileNameList().size() == 0)
        {
            info.append("N/A \n");
        }
        for(String filename : errorReportingObjectStore.getProcessedFileNameList())
        {
            info.append("\t "+filename+"\n");
        }

        info.append("\n\n\nSkipped  Files are :- \n");
        if(errorReportingObjectStore.getSkippedFileNameList().size() == 0)
        {
            info.append("N/A \n");
        }
        for(String filename : errorReportingObjectStore.getSkippedFileNameList())
        {
            info.append("\t "+filename+"\n");
        }
        info.append("\n*******************************************************************************\n");
        info.append("\n-------------------------------------------------------------------------------\n");

        LOGGER.error(info.toString());

    }


    ;
}
