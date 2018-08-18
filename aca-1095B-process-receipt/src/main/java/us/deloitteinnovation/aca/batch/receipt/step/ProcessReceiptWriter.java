package us.deloitteinnovation.aca.batch.receipt.step;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import us.deloitteinnovation.aca.batch.receipt.dto.ProcessReceiptDto;
import us.deloitteinnovation.aca.batch.receipt.repository.PrintDetailsMailRepository;

/**
 * 
 * @author rbongurala
 *
 */
public class ProcessReceiptWriter implements ItemWriter<ProcessReceiptDto> {
	private static final Logger logger = LoggerFactory.getLogger(ProcessReceiptWriter.class);
	
	@Autowired
	PrintDetailsMailRepository printDetailsMailRepository;

	@Override
	public void write(List<? extends ProcessReceiptDto> processReceiptDtoList) throws Exception {
		logger.info("In ProcessReceiptWriter----------->>>");
		for(ProcessReceiptDto dto : processReceiptDtoList){
			printDetailsMailRepository.updatePrintDetailsStatus(dto.getPrintDetails(),dto.isCOR());
		}
		
		for(ProcessReceiptDto dto : processReceiptDtoList){
			printDetailsMailRepository.updatePrintDetailsForEmptyFiles(dto.getEmptyFiles());
		}
	}

}
