package us.deloitteinnovation.aca.batch.receipt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;

import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;

/**
 * Transfers JobParameters into the Job ExecutionContext.
 * @author : RajeshKumar B
 */
public class ProcessReceiptListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessReceiptListener.class);


	/**
	 * Stores Job Parameters within the Job ExecutionContext.
	 *
	 * @param jobExecution
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {
		// Convert Job Parameters into Job Context variables.
		ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
		for (Map.Entry<String, JobParameter> entry : jobExecution.getJobParameters().getParameters().entrySet()) {
			jobExecutionContext.put(entry.getKey(), entry.getValue().getValue());
		}
		String fileType = (String) jobExecutionContext.get(BatchExportConstants.StepExecutionContextKeys.PROCESS_RECEIPT_FILE_TYPE);
		
		jobExecutionContext.put(BatchExportConstants.StepExecutionContextKeys.PROCESS_RECEIPT_FILE_TYPE,fileType);
		
		LOG.info("IN ProcessReceiptBatchJobExecutionListener  fileType----->>"+fileType);
		
		
	}
}