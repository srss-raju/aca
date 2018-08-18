package us.deloitteinnovation.aca.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.deloitteinnovation.aca.entity.FilerCoverage;
import us.deloitteinnovation.aca.entity.FilerCoveragePK;

import java.util.List;

/**
 * Created by yaojia on 11/30/2016.
 */

@Repository
public interface FilerCoverageSourceRepository extends CrudRepository<FilerCoverage, FilerCoveragePK> {

    List<FilerCoverage> findById_SourceUniqueIdAndId_SourceCdAndTaxYear(long sourceUniqueId, String sourceCd, int taxYear);
}
