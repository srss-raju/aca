package us.deloitteinnovation.aca.batch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.dao.BatchInfoDao;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;

/**
 */
public class BatchInfoServiceImpl implements BatchInfoService {
    @Autowired
    private BatchInfoDao batchInfoDao;

    /**
     * @param batchInfo
     * @return
     */
    @Override
    public int save(BatchInfoDto batchInfo) {
        return batchInfoDao.save(batchInfo);
    }

    /**
     * @param batchInfo
     * @return
     */
    @Override
    public int update(BatchInfoDto batchInfo) {
        return this.batchInfoDao.update(batchInfo);
    }

    /**
     * @param batchInfo
     * @return
     */
    @Override
    public int updateCounts(BatchInfoDto batchInfo) {
        return this.batchInfoDao.updateCounts(batchInfo);
    }

    /* Methods for integration tests */
    public void clearAll() {
        batchInfoDao.clearAll();
    }

    @Override
    public int getTopBatchId() {
        return batchInfoDao.getTopBatchId();
    }

    @Override
    public String getFileNameForBatchId(int batchId) {
        return batchInfoDao.getFileNameForBatchId(batchId);
    }
    
    @Override
    public int updatePrintAndProcess(BatchInfoDto batchInfo){
    	return batchInfoDao.updatePrintAndProcess(batchInfo);
    }
}
