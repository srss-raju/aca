package us.deloitteinnovation.aca.batch.service;

import org.springframework.stereotype.Service;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.Filer;

import java.util.List;

/**
 * Created by RajeshKumar B on 23/01/2017.
 */
@Service("printVendorFilerCoveredPersonService")
public interface PrintVendorFilerCoveredPersonService {
    public List<CoveredPerson> getCoveredPersonList(long responsiblePersonUniqueId, String taxYear, String sourceCd);

	public List<CoveredPerson> getCoveredPersonList(Filer filer);
}
