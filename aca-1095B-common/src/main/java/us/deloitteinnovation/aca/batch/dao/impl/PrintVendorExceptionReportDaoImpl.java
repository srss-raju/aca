package us.deloitteinnovation.aca.batch.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.constants.BatchQueryConstants;
import us.deloitteinnovation.aca.batch.dao.PrintVendorExceptionReportDao;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.mapper.PrintVendorExceptionReportMapper;
import us.deloitteinnovation.aca.entity.PrintDetail;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rgopalani on 10/12/2015.
 */
public class PrintVendorExceptionReportDaoImpl extends NamedParameterJdbcDaoSupport implements PrintVendorExceptionReportDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * @param exceptionReport
     * @return
     */
    @Override
    public int save(PrintVendorExceptionReportDto exceptionReport) {
        String sql = BatchQueryConstants.INSERT_EXCEPTION_REPORT;
        final Map<String, Object> params = new HashMap<>();
        params.put(BatchConstants.BATCH_ID, exceptionReport.getBatchInfo().getBatchId());
        params.put(BatchConstants.EX_DETAILS, exceptionReport.getExDetails());
        if (exceptionReport.getRowNumber() > 0 && exceptionReport.getSourceUniqueId() > 0) {
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
        final String sql = BatchQueryConstants.EXCEPTION_REPORT_COUNT;
        final Map<String, Object> params = new HashMap<>();
        params.put(BatchConstants.BATCH_ID, batchInfoDto.getBatchId());
        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    @Override
    public List<PrintVendorExceptionReportDto> getExceptionReport(BatchInfoDto batchInfoDto) {
        final String sql = BatchQueryConstants.EXCEPTION_REPORT;
        final Map<String, Object> params = new HashMap<>();
        params.put(BatchConstants.BATCH_ID, batchInfoDto.getBatchId());
        return this.namedParameterJdbcTemplate.query(sql, params, new PrintVendorExceptionReportMapper());

    }

    @Override
    public void clearAll() {
        final String sql = BatchQueryConstants.CLEAR_EXCEPTION_REPORT;
        this.jdbcTemplate.update(sql);

    }

    @Override
    public int saveReport(PrintVendorExceptionReportDto exceptionReport) {

        final String sql = BatchQueryConstants.INSERT_EXCEPTION_REPORT;
        final Map<String, Object> params = new HashMap<>();
        // EX_DETAILS,BATCH_ID,source_unique_id, row_number
        params.put(BatchConstants.BATCH_ID, exceptionReport.getBatchInfo().getBatchId());
        params.put(BatchConstants.EX_DETAILS, exceptionReport.getExDetails());
        params.put(BatchConstants.EX_ROW_NUMBER, exceptionReport.getRowNumber());
        params.put(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, exceptionReport.getSourceUniqueId());
        return jdbcTemplate.update(sql,
                new Object[] { exceptionReport.getExDetails(), exceptionReport.getBatchInfo().getBatchId(), exceptionReport.getSourceUniqueId(),
                exceptionReport.getRowNumber() });
    }
    
    @Override
    public void saveReport(final List<PrintVendorExceptionReportDto> exceptionReportList) {

    	//EX_DETAILS,BATCH_ID,source_unique_id, row_number
        final String sql = BatchQueryConstants.INSERT_EXCEPTION_REPORT;
        super.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                final PrintVendorExceptionReportDto exceptionReport = exceptionReportList.get(i);
                ps.setString(1, exceptionReport.getExDetails());
                ps.setInt(2, exceptionReport.getBatchInfo().getBatchId());
                ps.setLong(3, exceptionReport.getSourceUniqueId());
                ps.setInt(4, exceptionReport.getRowNumber());
            }

            @Override
            public int getBatchSize() {
                return exceptionReportList.size();
            }
        });
    }

}
