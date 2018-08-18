package us.deloitteinnovation.aca.batch.ingest.step3.services;

import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.BusinessValidationRuleDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;

import java.util.List;

/**
 * Created by tthakore on 9/1/2016.
 */

@Component
public interface Step3RecordValidationService {

    List<Step3FilerDataDto> getRecordsByUID(String sourceCd, long sourceUID, String taxYear);

    List<Step3FilerDataDto> getRecordsBySSN(String uid, String taxYear, String sourceCd);

    List<Step3FilerDataDto> getRecordsByTIN(String uid, String taxYear, String sourceCd);

    List<Step3FilerDataDto> getRecordsFromFileByStateandTaxYear(String state, String taxYear);



    void bulkInsertFD(List<Step3FilerDataDto> filerDemographics);

    void bulkInsertFCS(FilerCoverageDto filerDemographics);

    void bulkInsertBusinessLogs(List<BusinessValidationRuleDto> businessLogList);

    void bulkInsertExceptionReport(List<BusinessValidationRuleDto> exceptionReportListList);

    void bulkUpdateFD(List<Step3FilerDataDto> filerDemographics);

    void bulkUpdateFormStatus(List<Step3FilerDataDto> filerDemographics);

    void bulkDeleteFromCoverage(FilerCoverageDto filerDemographics);

    void bulkUpdateVersionNo(List<Step3FilerDataDto> otherRecordsToUpdate);

    List<Step3FilerDataDto> getCoveredPersonListbyUID(String sourceCd, long sourceUID, String taxYear);



}
