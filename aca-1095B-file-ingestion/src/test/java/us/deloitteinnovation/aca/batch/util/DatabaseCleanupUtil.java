package us.deloitteinnovation.aca.batch.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.entity.FilerCoverage;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;
import us.deloitteinnovation.aca.repository.CWFilerDemographicRepository;
import us.deloitteinnovation.aca.repository.FilerCoverageSourceRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by yaojia on 12/3/2016.
 */

@Component
public class DatabaseCleanupUtil {

    @Autowired
    CWFilerDemographicRepository filerDemographicRepository;

    @Autowired
    FilerCoverageSourceRepository filerCoverageSourceRepository;


    public void cleanUpDatabase(Collection<FilerDemographicPK> pks) {
        for (FilerDemographicPK pk : pks) {

            /* Delete FilerCoverageSource table */
            List<FilerCoverage> coverages = filerCoverageSourceRepository.
                    findById_SourceUniqueIdAndId_SourceCdAndTaxYear(pk.getSourceUniqueId(), pk.getSourceCd(), pk.getTaxYear());
            if (coverages != null && !coverages.isEmpty()) {
                filerCoverageSourceRepository.delete(coverages);
            }

            /* Delete FilerDemographic table */
            if (filerDemographicRepository.exists(pk)) {
                filerDemographicRepository.delete(pk);
            }
        }
    }
}
