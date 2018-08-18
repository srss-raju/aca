package us.deloitteinnovation.aca.batch.invalidaddress;

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
public class InvalidAddressListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(InvalidAddressListener.class);


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
		String state = (String) jobExecutionContext.get(BatchExportConstants.JobPropertiesKeys.STATE);
		
		jobExecutionContext.put(BatchExportConstants.JobPropertiesKeys.STATE,state);
		
		LOG.info("IN InvalidAddressListener  State----->>"+state);
		
		
	}
}
