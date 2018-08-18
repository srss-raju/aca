package us.deloitteinnovation.aca.batch.ingest.step4;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.dto.BatchCountDto;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.ingest.step3.services.FileIngestionService;
import us.deloitteinnovation.aca.repository.ExceptionReportRepository;

/**
 * Created by bhchaganti on 11/11/2016.
 */
public class Step4UpdateBatchCountsReader implements ItemReader<BatchCountDto> {
    @Autowired
    BatchInfoDto batchInfoDto;
    @Autowired
    ExceptionReportRepository exceptionReportRepository;
    @Autowired
    FileIngestionService fileIngestionService;
    boolean readComplete = false;

    @Override
    public BatchCountDto read() throws Exception {
        //Return null explicityly;otherwise reader cannot know when to stop.
        if(readComplete){
            return null;
        }
        BatchCountDto batchCountDto = new BatchCountDto();
        Integer currentBatchId = batchInfoDto.getBatchId();
        Integer countInExceptionReport = exceptionReportRepository.getDistinctRowNumberCount(currentBatchId);
        Integer countInBusinessDecisionsLog = fileIngestionService.getDistinctSourceUniqueIdsForBatch(currentBatchId);
        batchCountDto.setCountInExceptionReport(countInExceptionReport);
        batchCountDto.setCountInBusinessDecisionLog(countInBusinessDecisionsLog);
        readComplete=true;
        return batchCountDto;
    }


}
