package us.deloitteinnovation.aca.batch.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.constants.BatchQueryConstants;
import us.deloitteinnovation.aca.batch.dao.ExceptionReportDao;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.ExceptionReportDto;
import us.deloitteinnovation.aca.batch.mapper.ExceptionReportMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rgopalani on 10/12/2015.
 */
@Component
public class ExceptionReportDaoImpl extends NamedParameterJdbcDaoSupport implements ExceptionReportDao {

    JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ExceptionReportDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        setJdbcTemplate(jdbcTemplate);
    }

    /**
     * @param exceptionReport
     * @return
     */
    @Override
    public int save(ExceptionReportDto exceptionReport) {
        String sql = BatchQueryConstants.INSERT_EXCEPTION_REPORT;
        Map<String, Object> params = new HashMap<>();
        params.put(BatchConstants.BATCH_ID, exceptionReport.getBatchInfo().getBatchId());
        params.put(BatchConstants.EX_DETAILS, exceptionReport.getExDetails());
        if (exceptionReport.getRowNumber() >= 0 && exceptionReport.getSourceUniqueId() >= 0) {
            params.put(BatchConstants.EX_ROW_NUMBER, exceptionReport.getRowNumber());
            params.put(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, exceptionReport.getSourceUniqueId());
            sql = BatchQueryConstants.INSERT_EXCEPTION_REPORT_ROW_NUM_SOURCE_UNIQUE_ID;
        } else if (exceptionReport.getRowNumber() > 0) {
            params.put(BatchConstants.EX_ROW_NUMBER, exceptionReport.getRowNumber());
            sql = BatchQueryConstants.INSERT_EXCEPTION_REPORT_ROW_NUM;
        }
        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public int getExceptionReportCount(BatchInfoDto batchInfoDto) {
        String sql = BatchQueryConstants.EXCEPTION_REPORT_COUNT;
        Map<String, Object> params = new HashMap<>();
        params.put(BatchConstants.BATCH_ID, batchInfoDto.getBatchId());
        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    @Override
    public List<ExceptionReportDto> getExceptionReport(BatchInfoDto batchInfoDto) {
        String sql = BatchQueryConstants.EXCEPTION_REPORT;
        Map<String, Object> params = new HashMap<>();
        params.put(BatchConstants.BATCH_ID, batchInfoDto.getBatchId());
        return this.namedParameterJdbcTemplate.query(sql, params, new ExceptionReportMapper());

    }

    @Override
    public void clearAll() {
        String sql = BatchQueryConstants.CLEAR_EXCEPTION_REPORT;
        this.jdbcTemplate.update(sql);

    }

    @Override
    public List<Integer> getDistinctSourceUniqueIdForBatchId(int batchId) {
        String sql = BatchQueryConstants.GET_DISTINCT_EXCEPTION_ROW_NUM_FOR_BATCH + batchId + " and EX_DETAILS NOT LIKE 'WR%'";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        List<Integer> listSourceUniqueIds = new ArrayList<>();
        while(sqlRowSet.next()){
            listSourceUniqueIds.add(sqlRowSet.getInt("SID"));
        }
        return listSourceUniqueIds;
    }
}
