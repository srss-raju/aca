package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.FilerDemographicStagingEntity;

import java.util.List;

/**
 * CRUD repository for the filer_demographics_staging table.
 */
@Transactional
public interface FilerDemographicStagingRepository extends CrudRepository<FilerDemographicStagingEntity, Integer> {

    @Query(value = "SELECT DISTINCT ROW_NUMBER FROM filer_demographics_staging where record_source='FILE' AND BATCH_ID=:batchId", nativeQuery = true)
    List<Integer> getRowNumbersFromStagingForBatchId(@Param("batchId")int batchId);
}
