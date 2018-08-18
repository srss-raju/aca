package us.deloitteinnovation.aca.batch.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dao.FilerCoverageDao;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static us.deloitteinnovation.aca.batch.constants.BatchQueryConstants.*;

/**
 * Created by rgopalani on 10/12/2015.
 */
public class FilerCoverageDaoImpl extends NamedParameterJdbcDaoSupport implements FilerCoverageDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(FilerCoverageDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private int batchSize = 500;

    @Override
    public void bulkInsert(final List<? extends FilerDemographicDto> filerDemographics) {
        final Collection<FilerCoverageDto> filerCoverages = new ArrayList<>();
        for (final FilerDemographicDto fd : filerDemographics) {
            if (!"N".equals(fd.getFilerStatus())) {
                filerCoverages.addAll(fd.getFilerCoverages());
            }
        }
        this.jdbcTemplate.batchUpdate(INSERT_FILER_COVERAGE, filerCoverages, batchSize,
                new ParameterizedPreparedStatementSetter<FilerCoverageDto>() {
                    public void setValues(PreparedStatement pStatement, FilerCoverageDto filerCoverage) {
                        prepareInsertStatement(pStatement, filerCoverage);
                    }
                });
    }

    private void prepareInsertStatement(final PreparedStatement pStatement, final FilerCoverageDto filerCoverage) {
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
    public int update(FilerDemographicDto filerDemographic) {
        Map<String, Object> params = new HashMap<>();
        int numRows = 0;
        for (FilerCoverageDto filerCoverage : filerDemographic.getFilerCoverages()) {
            BatchUtils.setMonth(BatchConstants.PARAM_JAN, params, filerCoverage.getJan());
            BatchUtils.setMonth(BatchConstants.PARAM_FEB, params, filerCoverage.getFeb());
            BatchUtils.setMonth(BatchConstants.PARAM_MAR, params, filerCoverage.getMar());
            BatchUtils.setMonth(BatchConstants.PARAM_APR, params, filerCoverage.getApr());
            BatchUtils.setMonth(BatchConstants.PARAM_MAY, params, filerCoverage.getMay());
            BatchUtils.setMonth(BatchConstants.PARAM_JUN, params, filerCoverage.getJun());
            BatchUtils.setMonth(BatchConstants.PARAM_JUL, params, filerCoverage.getJul());
            BatchUtils.setMonth(BatchConstants.PARAM_AUG, params, filerCoverage.getAug());
            BatchUtils.setMonth(BatchConstants.PARAM_SEP, params, filerCoverage.getSep());
            BatchUtils.setMonth(BatchConstants.PARAM_OCT, params, filerCoverage.getOct());
            BatchUtils.setMonth(BatchConstants.PARAM_NOV, params, filerCoverage.getNov());
            BatchUtils.setMonth(BatchConstants.PARAM_DEC, params, filerCoverage.getDec());
            BatchUtils.setDate(BatchConstants.PARAM_ORIG_COVERAGE_BEGIN_DATE, params, filerCoverage.getOrigCoverageBeginDt());
            BatchUtils.setDate(BatchConstants.PARAM_ORIG_COVERAGE_END_DATE, params, filerCoverage.getOrigCoverageEndDt());
            BatchUtils.setString(BatchConstants.PARAM_PROGRAM_NAME, params, filerCoverage.getProgramName());
            BatchUtils.setString(BatchConstants.PARAM_UPDATED_BY, params, filerCoverage.getUpdatedBy());
            BatchUtils.setString(BatchConstants.PARAM_UPDATED_DATE, params, filerCoverage.getUpdatedDt());
            BatchUtils.setString(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, params, filerCoverage.getId().getSourceUniqueId());
            BatchUtils.setString(BatchConstants.PARAM_SOURCE_CD, params, filerCoverage.getId().getSourceCd());
            BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_CASE_APPLICATION_ID, params, filerCoverage.getRecipientCaseApplicationId());
            numRows += this.namedParameterJdbcTemplate.update(UPDATE_FILER_COVERAGE, params);
        }
        return numRows;
    }

    @Override
    public int[] daleteAll(Set<FilerDemographicPKDto> ids) {
        final SqlParameterSource[] paramSources = new SqlParameterSource[ids.size()];
        final Iterator<FilerDemographicPKDto> idIter = ids.iterator();
        for (int i = 0; i < paramSources.length; i++) {
            final Map<String, String> params = new HashMap<>(2);
            final FilerDemographicPKDto id = idIter.next();
            params.put("sourceUniqueId", id.getSourceUniqueId());
            params.put("sourceCd", id.getSourceCd());
            paramSources[i] = new MapSqlParameterSource(params);
        }
        return namedParameterJdbcTemplate.batchUpdate(DELETE_FILER_COVERAGES_FOR_FD, paramSources);
    }

    public int daleteAll(FilerDemographicDto filerDemographic) {
        final Map<String, Object> params = new HashMap<>();
        BatchUtils.setString(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, params, filerDemographic.getId().getSourceUniqueId());
        BatchUtils.setString(BatchConstants.PARAM_SOURCE_CD, params, filerDemographic.getId().getSourceCd());
        return namedParameterJdbcTemplate.update(DELETE_FILER_COVERAGES_FOR_FD, params);
    }
}
