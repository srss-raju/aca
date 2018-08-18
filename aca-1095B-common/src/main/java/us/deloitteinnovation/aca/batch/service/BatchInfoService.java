package us.deloitteinnovation.aca.batch.service;

import org.springframework.stereotype.Service;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;

/**
 */
@Service("batchInfoService")
public interface BatchInfoService {
    /**
     * @param batchInfo
     * @return
     */
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
