package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.FilerCoverage;
import us.deloitteinnovation.aca.entity.FilerCoveragePK;

/**
 * Persistence helper methods for FilerCoverageSource testing.
 * @see us.deloitteinnovation.aca.entity.FilerCoverage
 */
@Transactional
public interface FilerCoverageSourceTestRepository extends CrudRepository<FilerCoverage, FilerCoveragePK> {

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @Query(value = "delete from FILER_COVERAGE_SOURCE where SOURCE_CD = :sourceCd",nativeQuery = true)
    void deleteBySourceCode(@Param("sourceCd") String sourceCd) ;
}
