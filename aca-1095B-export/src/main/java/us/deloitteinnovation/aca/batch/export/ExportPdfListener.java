package us.deloitteinnovation.aca.batch.export;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;


/**
 * Transfers JobParameters into the Job ExecutionContext.
 * @author : RajeshKumar B
 */
public class ExportPdfListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(ExportPdfListener.class);
	

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

		String startDate = (String) jobExecutionContext.get("STARTDATE");
		String endDate = (String) jobExecutionContext.get("ENDDATE");
		String state = (String) jobExecutionContext.get("STATE");
		Object yearParam = jobExecutionContext.get("YEAR");
		Long year = yearParam.getClass().isAssignableFrom(String.class) ?
				Long.parseLong((String) yearParam) : (Long) yearParam;	// Cast param class from String or numeric
		LOG.info("STATE -- "+state);
		LOG.info("YEAR -- "+year.toString());
		LOG.info("STARTDATE -- "+startDate);
		LOG.info("ENDDATE -- "+endDate);
		
		int offset = 0;
        jobExecutionContext.put("OFFSET",offset);
		jobExecutionContext.put("STATE",state);
		jobExecutionContext.put("YEAR",year);
		jobExecutionContext.put("STARTDATE",startDate);
		jobExecutionContext.put("ENDDATE",endDate);
	}
	
}
