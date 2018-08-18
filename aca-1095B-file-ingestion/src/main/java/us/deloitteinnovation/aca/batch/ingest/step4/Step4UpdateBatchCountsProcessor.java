package us.deloitteinnovation.aca.batch.ingest.step4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.dto.BatchCountDto;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;

/**
 * Created by bhchaganti on 11/13/2016.
 */
public class Step4UpdateBatchCountsProcessor implements ItemProcessor<BatchCountDto, BatchCountDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Step4UpdateBatchCountsProcessor.class);
    @Autowired
    BatchInfoDto batchInfoDto;

    @Override
    public BatchCountDto process(BatchCountDto batchCountDto) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Updating counts for batch id: " + batchInfoDto.getBatchId());
        }
        return batchCountDto;
    }
}
