package us.deloitteinnovation.aca.batch.service;

import org.springframework.stereotype.Service;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;

import java.util.List;

/**
 * Created by rgopalani on 10/12/2015.
 */
@Service
public interface PrintVendorExceptionReportService {
    /**
     * @param exceptionReport
     * @return
     */
    int save(PrintVendorExceptionReportDto exceptionReport);

    int getExceptionReportCount(BatchInfoDto batchInfoDto);

    List<PrintVendorExceptionReportDto> getExceptionReport(BatchInfoDto batchInfoDto);

    /* Used for test cases */
    void clearAll();

    void clearExceptionReport(int batchId);

    int saveReport(PrintVendorExceptionReportDto exceptionReport);

	void saveReport(List<PrintVendorExceptionReportDto> exceptionReportList);
}
