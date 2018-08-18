package us.deloitteinnovation.aca.web.caseworkerportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import us.deloitteinnovation.aca.entity.AuditFilerDemographic;

import java.util.List;

/**
 * Created by yaojia on 11/11/2016.
 */

@Repository
public interface AuditFilerDemographicRepository extends CrudRepository<AuditFilerDemographic, Integer> {

    @Override
    List<AuditFilerDemographic> findAll();

    List<AuditFilerDemographic> findBySourceUniqueIdAndSourceCdAndTaxYear(long sourceUniqueId, String sourceCd, int taxYear);
    List<AuditFilerDemographic> findByResponsiblePersonUniqueIdAndSourceCdAndTaxYearAndRecordVersionOrderByFilerStatusDesc(long responsiblePersonUniqueId, String sourceCd, int taxYear, int recordVersion);

}
