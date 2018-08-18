package us.deloitteinnovation.aca.batch.receipt.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import us.deloitteinnovation.aca.batch.receipt.dto.ProcessReceiptDto;

/**
 * 
 * @author rbongurala
 *
 */
public class ProcessReceiptProcessor implements ItemProcessor<ProcessReceiptDto, ProcessReceiptDto> {
	private static final Logger logger = LoggerFactory.getLogger(ProcessReceiptProcessor.class);

	@Override
	public ProcessReceiptDto process(ProcessReceiptDto processReceiptDto) throws Exception {
		logger.info("In ProcessReceiptProcessor----------->>>");
		return processReceiptDto;
	}

}
