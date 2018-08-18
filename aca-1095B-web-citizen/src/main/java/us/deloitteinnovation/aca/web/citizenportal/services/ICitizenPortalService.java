package us.deloitteinnovation.aca.web.citizenportal.services;

import us.deloitteinnovation.aca.entity.FilerDemographicCP;
import us.deloitteinnovation.aca.entity.SourceSystemConfig;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.web.citizenportal.vo.UserInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by tthakore on 9/28/2015.
 */

public interface ICitizenPortalService {

        FilerDemographicCP verifyUserInformation(UserInfo userinfo);

        List<SourceSystemConfig> getSelectedStateList();
        List<CoveredPerson> getCoveredPersonListWithTaxYR(long responsiblePersonUniqueId, String sourceCd, String taxYear);
        String getCorrectionIndicator(long sourceuniqueID, String sourceCd, String taxYear);

}
