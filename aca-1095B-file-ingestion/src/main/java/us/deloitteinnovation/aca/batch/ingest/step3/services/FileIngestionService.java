package us.deloitteinnovation.aca.batch.ingest.step3.services;

import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.BusinessValidationRuleDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;

import java.util.List;

/**
 * Created by rgopalani on 10/12/2015.
 */

public interface FileIngestionService {

    int[][] bulkInsertFD(final List<Step3FilerDataDto> filerDemographics);

    int[] bulkUpdateFD(final List<Step3FilerDataDto> filerDemographics);

    void bulkInsertFCS(final FilerCoverageDto filerDemographics);

    void bulkInsertBusinessLogs(final List<BusinessValidationRuleDto> businessLogList);

    void bulkInsertExceptionReport(List<BusinessValidationRuleDto> exceptionReportListList);

    int[] bulkUpdateFormStatus(List<Step3FilerDataDto> filerDemographics);

    void bulkDeleteFromCoverage(FilerCoverageDto filerDemographics);

    int[] bulkUpdateVersionNo(List<Step3FilerDataDto> otherRecordsToUpdate);

    Integer getDistinctSourceUniqueIdsForBatch(Integer batchId);
}
