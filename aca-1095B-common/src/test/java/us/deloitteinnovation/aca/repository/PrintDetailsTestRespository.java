package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;

/**
 * Persistence helper methods for Print Details testing.
 */
@Transactional
public interface PrintDetailsTestRespository extends CrudRepository<FilerDemographic, FilerDemographicPK> {

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @Query(value = "delete from print_details where SOURCE_CD = :sourceCd",nativeQuery = true)
    void deleteBySourceCode(@Param("sourceCd") String sourceCd) ;
}
