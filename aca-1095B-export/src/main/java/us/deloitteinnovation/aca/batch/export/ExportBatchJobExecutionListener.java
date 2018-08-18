package us.deloitteinnovation.aca.batch.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.entity.IrsTransmissionDetails;
import us.deloitteinnovation.aca.repository.IrsTransmissionDetailsRepository;

import java.util.List;
import java.util.Map;

/**
 * Transfers JobParameters into the Job ExecutionContext.
 */
public class ExportBatchJobExecutionListener extends JobExecutionListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(ExportBatchJobExecutionListener.class);

    @Autowired
    IrsTransmissionDetailsRepository irsTransmissionDetailsRepository;

    /**
     * Stores Job Parameters within the Job ExecutionContext.  If only a RECEIPT_ID is provided, gathers the STATE and
     * YEAR for that previous transmission and stores them within the Job ExecutionContext.
     * @param jobExecution
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Convert Job Parameters into Job Context variables.
        ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
        JobParameter jobParameter ;
        for (Map.Entry<String, JobParameter> entry : jobExecution.getJobParameters().getParameters().entrySet()) {
            jobExecutionContext.put(entry.getKey(), entry.getValue().getValue());

            // If a RECEIPT_ID was provided, load the state and year
            if (BatchExportConstants.JobPropertiesKeys.RECEIPT_ID.equals(entry.getKey()))
                storeStateAndYear(jobExecutionContext, entry.getValue().getValue().toString());
        }
    }

    /**
     * Given a receipt id, gets a corresponding IrsTransmissionDetails and stores its US State and Year in Job Execution Context.
     * It is possible that several details exist with the same receiptId.  However, they should all be for the same state and year.      *
     * @param context
     * @param receiptId
     */
    private void storeStateAndYear(ExecutionContext context, String receiptId) {
        List<IrsTransmissionDetails> detailsList = irsTransmissionDetailsRepository.findByTransmissionReceiptId(receiptId);
        if (detailsList == null || detailsList.size() == 0)
            throw new IllegalArgumentException("Transmission Details not found using receipt id '" + receiptId + "'.  Unable to continue.");
        // It is possible that several details exist with the same receiptId.  However, they should all be for the same state and year.  So just use the first
        IrsTransmissionDetails details = detailsList.get(0) ;
        // TODO Change this to Source code if we go in that direction as a job parameter
        context.put(BatchExportConstants.JobPropertiesKeys.STATE, details.getSourceCd().substring(0, 2));
        context.put(BatchExportConstants.JobPropertiesKeys.YEAR, details.getTaxYear());
    }
}
