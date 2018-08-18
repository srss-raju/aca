package us.deloitteinnovation.aca.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.TransmissionIdStack;

import static us.deloitteinnovation.aca.batch.export.ExportUtil.removeForm109495FilenameForStep4;

/**
 * Created by bhchaganti on 5/16/2016.
 */
//TODO: After Job, this class might not be of any use. Please delete it after testing. Bhaskar Chaganti 1/23/2017
public class Step4ExecutionListener implements StepExecutionListener {
    private static Logger logger = LoggerFactory.getLogger(Step4ExecutionListener.class);
    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService;
    @Autowired
    ExportJob1095Repository exportJobRepository;
    @Autowired
    TransmissionIdStack transmissionIdStack;

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        Integer transmissionId = null;

        if ( ! transmissionIdStack.isStackEmpty() ) {

            transmissionId = transmissionIdStack.popTransmissionId();
            removeForm109495FilenameForStep4(stepExecution.getJobExecution());
            if (logger.isInfoEnabled()) {
                logger.info("Done with transmission id: " + transmissionId);
            }
        }

        if( ! transmissionIdStack.isStackEmpty()){
            return new ExitStatus("CONTINUE");
        } else {
            return ExitStatus.COMPLETED;
        }
    }
}
