package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.BatchInfo;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
public interface BatchInfoRepository extends CrudRepository<BatchInfo, Integer> {
    @Async
    @Query("select bi.batchId from BatchInfo bi where bi.batchType='File Ingestion' order by bi.batchId desc")
    List<Integer> retrieveBatchIdsForFileIngestion();

}
