package us.deloitteinnovation.aca.batch.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.service.PrintVendorFileIngestionService;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;
import us.deloitteinnovation.aca.exception.PrintVendorBusinessValidationRuleDto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static us.deloitteinnovation.aca.batch.constants.BatchQueryConstants.INSERT_FILER_COVERAGE;
import static us.deloitteinnovation.aca.batch.constants.BatchQueryConstants.INSERT_INTO_BUSINESS_RULE_LOG;


/**
 * Created by tthakore on 10/12/2015.
 */


public class PrintVendorFileIngestionServiceImpl extends NamedParameterJdbcDaoSupport implements PrintVendorFileIngestionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PrintVendorFileIngestionServiceImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private int batchSize = 500;


    /**
     * Insert  record in filer coverage table
     **/
    @Override
    public void bulkInsertFCS(final List<FilerCoverageDto> filerCoverages) {

        this.jdbcTemplate.batchUpdate(INSERT_FILER_COVERAGE, filerCoverages, batchSize,
                new ParameterizedPreparedStatementSetter<FilerCoverageDto>() {
                    public void setValues(PreparedStatement pStatement, FilerCoverageDto filerCoverage) {
                        prepareInsertStatementForCoverage(pStatement, filerCoverage);
                    }
                });
    }

    /**
     * Insert  record in filer coverage table
     **/
    private void prepareInsertStatementForCoverage(final PreparedStatement pStatement, final FilerCoverageDto filerCoverage) {
        try {
            BatchUtils.setMonth(1, pStatement, filerCoverage.getApr());
            BatchUtils.setMonth(2, pStatement, filerCoverage.getAug());
            BatchUtils.setString(3, pStatement, filerCoverage.getComments());
            BatchUtils.setMonth(4, pStatement, filerCoverage.getDec());
            BatchUtils.setMonth(5, pStatement, filerCoverage.getFeb());
            BatchUtils.setMonth(6, pStatement, filerCoverage.getJan());
            BatchUtils.setMonth(7, pStatement, filerCoverage.getJul());
            BatchUtils.setMonth(8, pStatement, filerCoverage.getJun());
            BatchUtils.setMonth(9, pStatement, filerCoverage.getMar());
            BatchUtils.setMonth(10, pStatement, filerCoverage.getMay());
            BatchUtils.setMonth(11, pStatement, filerCoverage.getNov());
            BatchUtils.setMonth(12, pStatement, filerCoverage.getOct());
            BatchUtils.setDate(13, pStatement, filerCoverage.getOrigCoverageBeginDt());
            BatchUtils.setDate(14, pStatement, filerCoverage.getOrigCoverageEndDt());
            BatchUtils.setMonth(15, pStatement, filerCoverage.getSep());
            BatchUtils.setString(16, pStatement, filerCoverage.getUpdatedBy());
            BatchUtils.setString(17, pStatement, filerCoverage.getUpdatedDt());
            BatchUtils.setString(18, pStatement, filerCoverage.getProgramName());
            BatchUtils.setString(19, pStatement, filerCoverage.getId().getSourceCd());
            BatchUtils.setString(20, pStatement, filerCoverage.getId().getSourceUniqueId());
            BatchUtils.setString(21, pStatement, filerCoverage.getRecipientCaseApplicationId());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void bulkInsertBusinessLogs(List<PrintVendorBusinessValidationRuleDto> businessLogList) {
        this.jdbcTemplate.batchUpdate(INSERT_INTO_BUSINESS_RULE_LOG, businessLogList, batchSize,
                new ParameterizedPreparedStatementSetter<PrintVendorBusinessValidationRuleDto>() {
                    public void setValues(PreparedStatement pStatement, PrintVendorBusinessValidationRuleDto businessRuleLogDTO) {
                        prepareInsertStatementBRLog(pStatement, businessRuleLogDTO);
                    }
                });
    }

    /**
     * Insert  record in filer coverage table
     **/
    private void prepareInsertStatementBRLog(final PreparedStatement pStatement, final PrintVendorBusinessValidationRuleDto brLogs) {
        try {
            BatchUtils.setString(1, pStatement, brLogs.getTaxYear());
            BatchUtils.setString(2, pStatement, brLogs.getSourceCd());
            BatchUtils.setString(3, pStatement, brLogs.getSourceUniqueId());
            BatchUtils.setString(4, pStatement, brLogs.getBusinessDecision());
            BatchUtils.setString(5, pStatement, brLogs.getBusinessRule());
            BatchUtils.setString(6, pStatement, brLogs.getUid());
            BatchUtils.setDate(7, pStatement, brLogs.getDob());
            BatchUtils.setString(8, pStatement, brLogs.getCorrectionCode());
            BatchUtils.setString(9, pStatement, brLogs.getBatchId());
            BatchUtils.setString(10, pStatement, brLogs.getRowNumber());
            BatchUtils.setString(11, pStatement, brLogs.getUpdatedBy());
            BatchUtils.setString(12, pStatement, brLogs.getUpdatedDate());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

}
