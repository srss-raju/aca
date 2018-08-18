package us.deloitteinnovation.aca.batch.service;

import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.exception.PrintVendorBusinessValidationRuleDto;

import java.util.List;

/**
 * Created by rgopalani on 10/12/2015.
 */

public interface PrintVendorFileIngestionService {


    void bulkInsertFCS(final List<FilerCoverageDto> filerDemographics);

    void bulkInsertBusinessLogs(final List<PrintVendorBusinessValidationRuleDto> businessLogList);

}
