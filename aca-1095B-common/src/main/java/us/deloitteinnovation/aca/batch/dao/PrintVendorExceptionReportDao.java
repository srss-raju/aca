package us.deloitteinnovation.aca.batch.dao;

import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;

import java.util.List;

@Component
public interface PrintVendorExceptionReportDao {
    /**
     * @param exceptionReport
     * @return
     */
    int save(PrintVendorExceptionReportDto exceptionReport);

    /**
     * @param batchInfoDto
     * @return
     */
    int getExceptionReportCount(BatchInfoDto batchInfoDto);

    List<PrintVendorExceptionReportDto> getExceptionReport(BatchInfoDto batchInfoDto);

    void clearAll();

    int saveReport(PrintVendorExceptionReportDto exceptionReport);

	void saveReport(List<PrintVendorExceptionReportDto> exceptionReportList);
}
