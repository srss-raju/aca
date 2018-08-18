package us.deloitteinnovation.aca.batch.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.constants.BatchQueryConstants;
import us.deloitteinnovation.aca.batch.dao.BatchInfoDao;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;

import java.util.HashMap;
import java.util.Map;

/**
 */
@Transactional
public class BatchInfoDaoImpl extends NamedParameterJdbcDaoSupport implements BatchInfoDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param batchInfo
     * @return
     */
    @Override
    @Transactional
    public int save(BatchInfoDto batchInfo) {
        Map<String, Object> params = new HashMap<>();
        params.put(CommonEntityConstants.RECEIVE_DATE, batchInfo.getReceiveDt());
        this.namedParameterJdbcTemplate.update(BatchQueryConstants.INSERT_BATCH_INFO, params);
        return this.namedParameterJdbcTemplate.queryForObject("select MAX(BATCH_ID) from BATCH_INFO", params, Integer.class);
    }

    /**
     * @param batchInfo
     * @return
     */
    @Override
    public int update(BatchInfoDto batchInfo) {
        Map<String, Object> params = new HashMap<>();
        params.put(BatchConstants.AGENCY_CODE, batchInfo.getAgencyCd());
        params.put(BatchConstants.STATE_CODE, batchInfo.getStateCd());
        params.put(BatchConstants.SYSTEM_CODE, batchInfo.getSystemCd());
        params.put(BatchConstants.FILE_VERSION, batchInfo.getFileVersion());
        params.put(BatchConstants.BATCH_ID, batchInfo.getBatchId());
        params.put(BatchConstants.FILE_NAME, batchInfo.getFileName());
        params.put(BatchConstants.BATCH_TYPE, batchInfo.getBatchType());
        return this.namedParameterJdbcTemplate.update(BatchQueryConstants.UPDATE_BATCH_INFO_CODES, params);
    }

    /**
     * @param batchInfo
     * @return
     */
    @Override
    public int updateCounts(BatchInfoDto batchInfo) {
        Map<String, Object> params = new HashMap<>();
        params.put(BatchConstants.TOTAL_COUNT, batchInfo.getTotalCount());
        params.put(BatchConstants.TOTAL_FAIL, batchInfo.getTotalFail());
        params.put(BatchConstants.TOTAL_PASS, batchInfo.getTotalPass());
        params.put(BatchConstants.BATCH_ID, batchInfo.getBatchId());
        return this.namedParameterJdbcTemplate.update(BatchQueryConstants.UPDATE_BATCH_INFO_COUNTS, params);

    }

    @Override
    public void clearAll() {
        this.jdbcTemplate.update(BatchQueryConstants.CLEAR_BATCH_INFO);
    }

    @Override
    public int getTopBatchId() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(BatchQueryConstants.GET_TOP_BATCH_ID);
        Integer batchId = 0;
        while (sqlRowSet.next()) {
            batchId = sqlRowSet.getInt("BATCH_ID");
        }
        return batchId;
    }

    @Override
    public String getFileNameForBatchId(int batchId) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(BatchQueryConstants.GET_FILE_NAME_FOR_BATCH_ID + batchId);
        String fileName = null;
        while (sqlRowSet.next()) {
            fileName = sqlRowSet.getString("FILENAME");
        }
        return fileName;
    }
    
    
    @Override
    public int updatePrintAndProcess(BatchInfoDto batchInfo) {
        Map<String, Object> params = new HashMap<>();
        params.put(BatchConstants.AGENCY_CODE, batchInfo.getAgencyCd());
        params.put(BatchConstants.STATE_CODE, batchInfo.getStateCd());
        params.put(BatchConstants.SYSTEM_CODE, batchInfo.getSystemCd());
        params.put(BatchConstants.FILE_VERSION, batchInfo.getFileVersion());
        params.put(BatchConstants.FILE_NAME, batchInfo.getFileName());
     
        params.put(BatchConstants.TOTAL_COUNT, batchInfo.getTotalCount());
        params.put(BatchConstants.TOTAL_FAIL, batchInfo.getTotalFail());
        params.put(BatchConstants.TOTAL_PASS, batchInfo.getTotalPass());
        params.put(BatchConstants.REQUISITION_ID, batchInfo.getRequisitionId());
        params.put(BatchConstants.BATCH_TYPE, batchInfo.getBatchType());
        params.put(BatchConstants.BATCH_INFO_ID, batchInfo.getBatchId());
        return this.namedParameterJdbcTemplate.update(BatchQueryConstants.UPDATE_BATCH_INFO_PRINTANDPROCESS_XML, params);

    }
}

