package us.deloitteinnovation.aca.batch.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;
import us.deloitteinnovation.aca.model.PdfForm;

/**
 * Created by tthakore on 11/24/2015.
 */
public class RecordSkipListener implements SkipListener {

    private static Logger logger = LoggerFactory.getLogger(RecordSkipListener.class);
    ErrorReportingObjectStore errorReportingObjectStore = ErrorReportingObjectStore.getInstance();
    @Override
    public void onSkipInRead(Throwable throwable) {
        logger.error("Error in reading file so this file will be skipped.----->", throwable) ;
        if( errorReportingObjectStore.getSkippedFileNameList().indexOf(errorReportingObjectStore.getCurrentFileName())  == -1)errorReportingObjectStore.getSkippedFileNameList().add(errorReportingObjectStore.getCurrentFileName());
    }

    @Override
    public void onSkipInWrite(Object o, Throwable throwable) {
        logger.error("Error in writing  file so this file will be skipped.----->", throwable) ;
        if( errorReportingObjectStore.getSkippedFileNameList().indexOf(errorReportingObjectStore.getCurrentFileName())  == -1)errorReportingObjectStore.getSkippedFileNameList().add(errorReportingObjectStore.getCurrentFileName());
    }

    @Override
    public void onSkipInProcess(Object o, Throwable throwable) {
        logger.error("Error in processing  file so this file will be skipped.----->", throwable) ;
        if( errorReportingObjectStore.getSkippedFileNameList().indexOf(errorReportingObjectStore.getCurrentFileName())  == -1)errorReportingObjectStore.getSkippedFileNameList().add(errorReportingObjectStore.getCurrentFileName());
    }
}
