package us.deloitteinnovation.aca.batch.service;

import org.springframework.stereotype.Service;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.Filer;

import java.util.List;

/**
 * Created by rgopalani on 12/28/2015.
 */
@Service("filerCoveredPersonService")
public interface FilerCoveredPersonService {
    public List<CoveredPerson> getCoveredPersonList(long responsiblePersonUniqueId, String taxYear, String sourceCd);

	public List<CoveredPerson> getCoveredPersonList(Filer filer);
}
