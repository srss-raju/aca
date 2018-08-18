package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.FilerDemographicIrsStatusUpdate;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;

/**
 * Created by tthakore on 9/28/2015.
 */

@Transactional(propagation = Propagation.REQUIRED)
public interface FilerDemographicIrsErrorRepository extends CrudRepository<FilerDemographicIrsStatusUpdate, FilerDemographicPK> {


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE us.deloitteinnovation.aca.entity.FilerDemographicIrsStatusUpdate SET IRS_TRANSMISSION_STATUS_CD = :status, UPDATED_BY= :updatedBy, UPDATED_DATE =:updatedDate WHERE SOURCE_CD = :sourceCd AND SOURCE_UNIQUE_ID = :sourceUniqueId AND IRS_TRANSMISSION_STATUS_CD != :ignoredStatus")
    void updateIrsStatusCD(@Param("sourceCd") String sourceCode, @Param("sourceUniqueId") Long sourceUniqueId, @Param("status") String status,@Param("updatedDate") java.util.Date updatedDate, @Param("updatedBy") String updatedBy, @Param("ignoredStatus") String ignoredStatus);

}
