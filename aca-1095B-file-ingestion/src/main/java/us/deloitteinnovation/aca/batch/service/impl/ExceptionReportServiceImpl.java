package us.deloitteinnovation.aca.batch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.dao.ExceptionReportDao;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.ExceptionReportDto;
import us.deloitteinnovation.aca.batch.service.ExceptionReportService;

import java.util.List;

/**
 * Created by rgopalani on 10/12/2015.
 */
@Component
public class ExceptionReportServiceImpl implements ExceptionReportService {
    @Autowired
    private ExceptionReportDao exceptionReportDao;

    /**
     * @param exceptionReport
     * @return
     */
    @Override
    public int save(ExceptionReportDto exceptionReport) {
        return exceptionReportDao.save(exceptionReport);
    }

    @Override
    public int getExceptionReportCount(BatchInfoDto batchInfoDto) {
        return exceptionReportDao.getExceptionReportCount(batchInfoDto);
    }

    @Override
    public List<ExceptionReportDto> getExceptionReport(BatchInfoDto batchInfoDto) {
        return exceptionReportDao.getExceptionReport(batchInfoDto);
    }

    @Override
    public void clearAll() {
        exceptionReportDao.clearAll();
    }

    @Override
    public void clearExceptionReport(int batchId) {

    }

    @Override
    public List<Integer> getDistinctSourceUniqueIdForBatchId(int batchId) {
        return exceptionReportDao.getDistinctSourceUniqueIdForBatchId(batchId);
    }

}
