package us.deloitteinnovation.aca.batch.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.repository.PrintVendorJdbcRepository;

import java.util.Map;

/**
 * Transfers JobParameters into the Job ExecutionContext.
 * @author : Govind
 */
public class ExportBatchPrintVendorListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(ExportBatchPrintVendorListener.class);

	@Autowired
	PrintVendorJdbcRepository printVendorJdbcRepository;

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

		String frequency = (String) jobExecutionContext.get(BatchExportConstants.StepExecutionContextKeys.PRINT_VENDOR_XML_FREQUENCY);
		String mailStatus = (String) jobExecutionContext.get(BatchExportConstants.StepExecutionContextKeys.PRINT_VENDOR_XML_MAILSTATUS);
		Long year = Long.valueOf((String) jobExecutionContext.get("YEAR"));
		Long totalRecordCountForFilerDemographics = printVendorJdbcRepository.getFilerDemographicrecordCount((String) jobExecutionContext.get("STATE"),year.toString(),mailStatus,frequency);
		LOG.info("RECORD COUNT -- "+totalRecordCountForFilerDemographics);
		LOG.info("YEAR -- "+year.toString());
		LOG.info("Frequency -- "+frequency);
		LOG.info("Mail Status -- "+mailStatus);
		
		jobExecutionContext.put(BatchExportConstants.StepExecutionContextKeys.FORM_1095S_RECORD_COUNT,String.valueOf(totalRecordCountForFilerDemographics));
		jobExecutionContext.put(BatchExportConstants.StepExecutionContextKeys.PRINT_VENDOR_XML_FREQUENCY,frequency);
		jobExecutionContext.put(BatchExportConstants.StepExecutionContextKeys.PRINT_VENDOR_XML_MAILSTATUS,mailStatus);
	}
}
