package us.deloitteinnovation.aca.batch.service;

import org.springframework.stereotype.Service;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.ExceptionReportDto;

import java.util.List;

/**
 * Created by rgopalani on 10/12/2015.
 */
@Service
public interface ExceptionReportService {
    /**
     * @param exceptionReport
     * @return
     */
    int save(ExceptionReportDto exceptionReport);

    int getExceptionReportCount(BatchInfoDto batchInfoDto);

    List<ExceptionReportDto> getExceptionReport(BatchInfoDto batchInfoDto);

    /* Used for test cases */
    void clearAll();

    void clearExceptionReport(int batchId);

    List<Integer> getDistinctSourceUniqueIdForBatchId(int batchId);
}
