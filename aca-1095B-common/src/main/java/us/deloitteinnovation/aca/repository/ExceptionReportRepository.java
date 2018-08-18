package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.ExceptionReport;

import java.util.List;

/**
 * Created by bhchaganti on 9/14/2016.
 */
@Transactional(propagation = Propagation.REQUIRED)
public interface ExceptionReportRepository extends CrudRepository<ExceptionReport, Integer> {

    @Async
    @Query(value = "select count(DISTINCT er.rowNumber) from ExceptionReport er where er.batchInfo.batchId=:batchId and er.exDetails NOT LIKE 'WR%'")
    Integer getDistinctRowNumberCount(@Param("batchId")int batchId);

    List<ExceptionReport> findBySourceUniqueId(long sourceUniqueId);
    List<ExceptionReport> findBySourceUniqueIdAndRowNumber(long sourceUniqueId, int rowNumber);
}
