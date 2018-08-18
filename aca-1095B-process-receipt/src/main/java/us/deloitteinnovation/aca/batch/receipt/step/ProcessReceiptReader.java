package us.deloitteinnovation.aca.batch.receipt.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.receipt.dto.ProcessReceiptDto;
import us.deloitteinnovation.aca.batch.receipt.util.ProcessReceiptUtil;
import us.deloitteinnovation.profile.ProfileProperties;

/**
 * 
 * @author rbongurala
 *
 */
public class ProcessReceiptReader implements ItemReader<ProcessReceiptDto> {
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessReceiptReader.class);
	
	@Autowired
    ProfileProperties profileProperties;
	
	
	StepExecution stepExecution;
	
	boolean readStepComplete = false;

	@Override
	public ProcessReceiptDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		logger.info("In Reader----------->>>");
		ProcessReceiptDto processReceiptDto  = null;
		String fileType = (String) stepExecution.getJobExecution().getExecutionContext().get(BatchExportConstants.StepExecutionContextKeys.PROCESS_RECEIPT_FILE_TYPE);
		
		if (readStepComplete)
         return null;
		ProcessReceiptUtil processReceiptUtil = new ProcessReceiptUtil();
		if("COR".equalsIgnoreCase(fileType)){
			processReceiptDto  = processReceiptUtil.readReceiptFile(profileProperties);
		}else if("COM".equalsIgnoreCase(fileType)){
			processReceiptDto  = processReceiptUtil.readMailFile(profileProperties);
		}
		readStepComplete = true;
		return processReceiptDto;
	}
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

}
