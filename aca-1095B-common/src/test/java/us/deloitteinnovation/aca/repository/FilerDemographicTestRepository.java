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
 */
@Transactional
public interface FilerDemographicTestRepository extends CrudRepository<FilerDemographic, FilerDemographicPK> {

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @Query(value = "delete from filer_demographics where RECIPIENT_STATE = :recipientState ",nativeQuery = true)
    void deleteByRecipientState(@Param("recipientState") String recipientState) ;

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @Query(value = "delete from filer_demographics where SOURCE_CD = :sourceCd",nativeQuery = true)
    void deleteBySourceCode(@Param("sourceCd") String sourceCd) ;

    @Transactional
    @Modifying
    @Query("UPDATE FilerDemographic fd SET fd.irsTransmissionStatusCd = :status WHERE fd.id.sourceCd like 'FM%'")
    void updateIrsTransmissionStatus(@Param("status") String status);
}
