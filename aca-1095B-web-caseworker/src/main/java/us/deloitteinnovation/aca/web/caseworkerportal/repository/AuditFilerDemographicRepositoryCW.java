package us.deloitteinnovation.aca.web.caseworkerportal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import us.deloitteinnovation.aca.entity.FilerDemographicAudit;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;

import java.util.List;

/**
 * Created by ritmukherjee on 11/19/2015.
 */
@Repository
@Deprecated
public interface AuditFilerDemographicRepositoryCW extends CrudRepository<FilerDemographicAudit,FilerDemographicPK> {

    @Async
    @Query("select t from FilerDemographicAudit t" +
            "  where   (t.id.sourceUniqueId) = (:sourceUniqueId)  "+
            " AND  lower(t.id.sourceCd) = lower(:sourceCd) "+
            " order by t.updatedDt desc")
    List<FilerDemographicAudit> getCustomerAuditDetails(
            @Param("sourceUniqueId") long sourceUniqueId,
            @Param("sourceCd") String sourceCd);

}
