package us.deloitteinnovation.aca.batch.ingest.step4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.dto.BatchCountDto;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;

import java.util.List;

/**
 * Created by bhchaganti on 11/13/2016.
 */
public class Step4UpdateBatchCountsWriter implements ItemWriter<BatchCountDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Step4UpdateBatchCountsWriter.class);
    @Autowired
    BatchInfoService batchInfoService;
    @Autowired
    BatchInfoDto batchInfoDto;

    @Override
    public void write(List<? extends BatchCountDto> list) throws Exception {

        for (BatchCountDto batchCountDto : list) {
            this.batchInfoDto.setTotalPass(batchCountDto.getCountInBusinessDecisionLog());
            this.batchInfoDto.setTotalFail(batchCountDto.getCountInExceptionReport());
            batchInfoService.updateCounts(batchInfoDto);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Counts updated for batch id: " + batchInfoDto.getBatchId());
            }
        }
    }
}
