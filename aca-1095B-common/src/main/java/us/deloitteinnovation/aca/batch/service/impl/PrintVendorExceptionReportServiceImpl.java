package us.deloitteinnovation.aca.batch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.dao.PrintVendorExceptionReportDao;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.service.PrintVendorExceptionReportService;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;

import java.util.List;

/**
 * Created by rgopalani on 10/12/2015.
 */
public class PrintVendorExceptionReportServiceImpl implements PrintVendorExceptionReportService {
    @Autowired
    private PrintVendorExceptionReportDao exceptionReportDao;

    /**
     * @param exceptionReport
     * @return
     */
    @Override
    public int save(PrintVendorExceptionReportDto exceptionReport) {
        return exceptionReportDao.save(exceptionReport);
    }

    @Override
    public int getExceptionReportCount(BatchInfoDto batchInfoDto) {
        return exceptionReportDao.getExceptionReportCount(batchInfoDto);
    }

    @Override
    public List<PrintVendorExceptionReportDto> getExceptionReport(BatchInfoDto batchInfoDto) {
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
    public int saveReport(PrintVendorExceptionReportDto exceptionReport) {
        return exceptionReportDao.saveReport(exceptionReport);
    }
    
    @Override
    public void saveReport(List<PrintVendorExceptionReportDto> exceptionReportList){
    	 exceptionReportDao.saveReport(exceptionReportList);
    }

}
