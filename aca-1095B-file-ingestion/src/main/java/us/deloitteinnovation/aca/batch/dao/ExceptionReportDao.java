package us.deloitteinnovation.aca.batch.dao;


import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.ExceptionReportDto;

import java.util.List;


public interface ExceptionReportDao {
    /**
     * @param exceptionReport
     * @return
     */
    int save(ExceptionReportDto exceptionReport);

    /**
     * @param batchInfoDto
     * @return
     */
    int getExceptionReportCount(BatchInfoDto batchInfoDto);

    List<ExceptionReportDto> getExceptionReport(BatchInfoDto batchInfoDto);

    void clearAll();

    List<Integer> getDistinctSourceUniqueIdForBatchId(int batchId);
}
