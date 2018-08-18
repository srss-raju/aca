package us.deloitteinnovation.aca.batch.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;

/**
 */
@Component
@Transactional
public interface BatchInfoDao {
    /**
     * @param batchInfo
     * @return
     */
    @Transactional
    int save(BatchInfoDto batchInfo);

    /**
     * @param batchInfo
     * @return
     */
    int update(BatchInfoDto batchInfo);

    /**
     * @param batchInfo
     * @return
     */
    int updateCounts(BatchInfoDto batchInfo);

    void clearAll();

    int getTopBatchId();

    String getFileNameForBatchId(int batchId);

	int updatePrintAndProcess(BatchInfoDto batchInfo);
}
