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
import us.deloitteinnovation.aca.batch.dao.FilerDemographicsDao;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.mapper.FilerDemographicsMapper;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static us.deloitteinnovation.aca.batch.constants.BatchQueryConstants.*;
import static us.deloitteinnovation.aca.constants.CommonEntityConstants.STATUS_ACTIVE;
import static us.deloitteinnovation.aca.constants.CommonEntityConstants.STATUS_INACTIVE;

/**
 * Created by rgopalani on 10/12/2015.
 */
public class FilerDemographicsDaoImpl extends NamedParameterJdbcDaoSupport implements FilerDemographicsDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(FilerDemographicsDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private int batchSize = 500;

    @Override
    public int[][] bulkInsert(final List<FilerDemographicDto> filerDemographics) {
        return this.jdbcTemplate.batchUpdate(
                INSERT_FILER_DEMOGRAPHICS, filerDemographics, this.batchSize,
                new ParameterizedPreparedStatementSetter<FilerDemographicDto>() {
                    public void setValues(PreparedStatement pStatement, FilerDemographicDto filerDemographic) {
                        try {
                            pStatement.setInt(1, filerDemographic.getBatchInfo().getBatchId());
                            BatchUtils.setString(2, pStatement, filerDemographic.getComments());
                            BatchUtils.setString(3, pStatement, filerDemographic.getCommunicationPreference());
                            BatchUtils.setString(4, pStatement, filerDemographic.getCorrection());
                            BatchUtils.setDate(5, pStatement, filerDemographic.getCorrectionDt());
                            BatchUtils.setString(6, pStatement, filerDemographic.getEMail());
                            BatchUtils.setString(7, pStatement, filerDemographic.getEmployerAddressLine1());
                            BatchUtils.setString(8, pStatement, filerDemographic.getEmployerAddressLine2());
                            BatchUtils.setString(9, pStatement, filerDemographic.getEmployerCityOrTown());
                            BatchUtils.setString(10, pStatement, filerDemographic.getEmployerContactNo());
                            BatchUtils.setString(11, pStatement, filerDemographic.getEmployerCountry());
                            BatchUtils.setString(12, pStatement, filerDemographic.getEmployerIdentificationNumber());
                            BatchUtils.setString(13, pStatement, filerDemographic.getEmployerName());
                            BatchUtils.setString(14, pStatement, filerDemographic.getEmployerStateOrProvince());
                            BatchUtils.setString(15, pStatement, filerDemographic.getFilerStatus());
                            BatchUtils.setString(16, pStatement, filerDemographic.getLanguagePreference());
                            BatchUtils.setString(17, pStatement, filerDemographic.getPolicyOrigin());
                            //BatchUtils.setString(18, pStatement, filerDemographic.getProgramName());
                            BatchUtils.setString(18, pStatement, filerDemographic.getProviderAddressLine1());
                            BatchUtils.setString(19, pStatement, filerDemographic.getProviderAddressLine2());
                            BatchUtils.setString(20, pStatement, filerDemographic.getProviderCityOrTown());
                            BatchUtils.setString(21, pStatement, filerDemographic.getProviderContactNo().toString());
                            BatchUtils.setString(22, pStatement, filerDemographic.getProviderCountry());
                            BatchUtils.setString(23, pStatement, filerDemographic.getProviderIdentificationNumber());
                            BatchUtils.setString(24, pStatement, filerDemographic.getProviderName());
                            BatchUtils.setString(25, pStatement, filerDemographic.getProviderStateOrProvince());
                            BatchUtils.setString(26, pStatement, filerDemographic.getProviderZipOrPostalCode());
                            BatchUtils.setString(27, pStatement, filerDemographic.getRecipientAddressLine1());
                            BatchUtils.setString(28, pStatement, filerDemographic.getRecipientAddressLine2());
                            /*BatchUtils.setString(30, pStatement, filerDemographic.getRecepientCaseApplicationId());*/
                            BatchUtils.setString(29, pStatement, filerDemographic.getRecipientCity());
                            BatchUtils.setDate(30, pStatement, filerDemographic.getRecipientDob());
                            BatchUtils.setString(31, pStatement, filerDemographic.getRecipientFirstName());
                            BatchUtils.setString(32, pStatement, filerDemographic.getRecipientLastName());
                            BatchUtils.setString(33, pStatement, filerDemographic.getRecipientMiddleName());
                            BatchUtils.setString(34, pStatement, filerDemographic.getRecipientSsn());
                            BatchUtils.setString(35, pStatement, filerDemographic.getRecipientState());
                            BatchUtils.setString(36, pStatement, filerDemographic.getRecepientSuffixName());
                            BatchUtils.setString(37, pStatement, filerDemographic.getRecipientTin());
                            BatchUtils.setString(38, pStatement, filerDemographic.getRecepientZip4());
                            BatchUtils.setString(39, pStatement, filerDemographic.getRecepientZip5());
                            BatchUtils.setString(40, pStatement, filerDemographic.getResponsiblePersonUniqueId());
                            BatchUtils.setString(41, pStatement, filerDemographic.getShopIdentifier());
                            pStatement.setString(42, filerDemographic.getId().getTaxYear());
                            BatchUtils.setString(43, pStatement, filerDemographic.getUpdatedBy());
                            BatchUtils.setString(44, pStatement, filerDemographic.getUpdatedDt());
                            BatchUtils.setString(45, pStatement, filerDemographic.getZipOrPostalCode());
                            BatchUtils.setString(46, pStatement, filerDemographic.getJan());
                            BatchUtils.setString(47, pStatement, filerDemographic.getFeb());
                            BatchUtils.setString(48, pStatement, filerDemographic.getMar());
                            BatchUtils.setString(49, pStatement, filerDemographic.getApr());
                            BatchUtils.setString(50, pStatement, filerDemographic.getMay());
                            BatchUtils.setString(51, pStatement, filerDemographic.getJun());
                            BatchUtils.setString(52, pStatement, filerDemographic.getJul());
                            BatchUtils.setString(53, pStatement, filerDemographic.getAug());
                            BatchUtils.setString(54, pStatement, filerDemographic.getSep());
                            BatchUtils.setString(55, pStatement, filerDemographic.getOct());
                            BatchUtils.setString(56, pStatement, filerDemographic.getNov());
                            BatchUtils.setString(57, pStatement, filerDemographic.getDec());
                            BatchUtils.setString(58, pStatement,
                                    filerDemographic.isActive() ? STATUS_ACTIVE : STATUS_INACTIVE);
                            BatchUtils.setString(59, pStatement, filerDemographic.getMailedForm());
                            BatchUtils.setString(60, pStatement, filerDemographic.getId().getSourceCd());
                            BatchUtils.setString(61, pStatement, filerDemographic.getId().getSourceUniqueId());
                        } catch (SQLException e) {
                            LOGGER.error(e.getMessage());
                        }
                    }

                    public int getBatchSize() {
                        return filerDemographics.size();
                    }
                });
    }

    @Override
    public int[] bulkUpdate(List<FilerDemographicDto> filerDemographics) {
        final SqlParameterSource[] paramsRA = new SqlParameterSource[filerDemographics.size()];
        for (int i = 0; i < paramsRA.length; i++) {
            final FilerDemographicDto fd = filerDemographics.get(i);
            final Map<String, Object> params = createUpdateParams(fd);
            paramsRA[i] = new MapSqlParameterSource(params);
        }
        return namedParameterJdbcTemplate.batchUpdate(UPDATE_FILER_DEMOGRAPHICS, paramsRA);
    }

    /**
     * @param filerDemographic
     * @return
     */
    public int update(final FilerDemographicDto filerDemographic) {
        return this.namedParameterJdbcTemplate.update(UPDATE_FILER_DEMOGRAPHICS,
                createUpdateParams(filerDemographic));
    }

    private Map<String, Object> createUpdateParams(final FilerDemographicDto filerDemographic) {
        Map<String, Object> params = new HashMap<>();
        params.put(BatchConstants.PARAM_TAX_YEAR, filerDemographic.getId().getTaxYear());
     /*   BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_CASE_APPLICATION_ID, params, filerDemographic.getRecepientCaseApplicationId());*/
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_FIRST_NAME, params, filerDemographic.getRecipientFirstName());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_MIDDLE_NAME, params, filerDemographic.getRecipientMiddleName());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_LAST_NAME, params, filerDemographic.getRecipientLastName());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_SUFFIX_NAME, params, filerDemographic.getRecepientSuffixName());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_SSN, params, filerDemographic.getRecipientSsn());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_TIN, params, filerDemographic.getRecipientTin());
        BatchUtils.setDate(BatchConstants.PARAM_RECIPIENT_DOB, params, filerDemographic.getRecipientDob());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_ADDRESS_LINE_1, params, filerDemographic.getRecipientAddressLine1());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_ADDRESS_LINE_2, params, filerDemographic.getRecipientAddressLine2());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_CITY, params, filerDemographic.getRecipientCity());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_STATE, params, filerDemographic.getRecipientState());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_ZIP_5, params, filerDemographic.getRecepientZip5());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_ZIP_4, params, filerDemographic.getRecepientZip4());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_E_MAIL, params, filerDemographic.getEMail());
        BatchUtils.setString(BatchConstants.PARAM_RECIPIENT_LANGUAGE_PREFERENCE, params, filerDemographic.getLanguagePreference());
        BatchUtils.setString(BatchConstants.PARAM_POLICY_ORIGIN, params, filerDemographic.getPolicyOrigin());
        BatchUtils.setString(BatchConstants.PARAM_SHOP_IDENTIFIER, params, filerDemographic.getShopIdentifier());
        BatchUtils.setString(BatchConstants.PARAM_EMPLOYER_NAME, params, filerDemographic.getEmployerName());
        BatchUtils.setString(BatchConstants.PARAM_EMPLOYER_IDENTIFICATION_NUMBER, params, filerDemographic.getEmployerIdentificationNumber());
        BatchUtils.setString(BatchConstants.PARAM_EMPLOYER_CONTACT_NO, params, filerDemographic.getEmployerContactNo());
        BatchUtils.setString(BatchConstants.PARAM_EMPLOYER_ADDRESS_LINE_1, params, filerDemographic.getEmployerAddressLine1());
        BatchUtils.setString(BatchConstants.PARAM_EMPLOYER_ADDRESS_LINE_2, params, filerDemographic.getEmployerAddressLine2());
        BatchUtils.setString(BatchConstants.PARAM_EMPLOYER_CITY_OR_TOWN, params, filerDemographic.getEmployerCityOrTown());
        BatchUtils.setString(BatchConstants.PARAM_EMPLOYER_STATE_OR_PROVINCE, params, filerDemographic.getEmployerStateOrProvince());
        BatchUtils.setString(BatchConstants.PARAM_EMPLOYER_COUNTRY, params, filerDemographic.getEmployerCountry());
        BatchUtils.setString(BatchConstants.PARAM_EMPLOYER_ZIP_OR_POSTAL_CODE, params, filerDemographic.getZipOrPostalCode());
        BatchUtils.setString(BatchConstants.PARAM_PROVIDER_NAME, params, filerDemographic.getProviderName());
        BatchUtils.setString(BatchConstants.PARAM_PROVIDER_IDENTIFICATION_NUMBER, params, filerDemographic.getProviderIdentificationNumber());
        BatchUtils.setString(BatchConstants.PARAM_PROVIDER_CONTACT_NO, params, filerDemographic.getProviderContactNo());
        BatchUtils.setString(BatchConstants.PARAM_PROVIDER_ADDRESS_LINE_1, params, filerDemographic.getProviderAddressLine1());
        BatchUtils.setString(BatchConstants.PARAM_PROVIDER_ADDRESS_LINE_2, params, filerDemographic.getProviderAddressLine2());
        BatchUtils.setString(BatchConstants.PARAM_PROVIDER_CITY_OR_TOWN, params, filerDemographic.getProviderCityOrTown());
        BatchUtils.setString(BatchConstants.PARAM_PROVIDER_STATE_OR_PROVINCE, params, filerDemographic.getProviderStateOrProvince());
        BatchUtils.setString(BatchConstants.PARAM_PROVIDER_COUNTRY, params, filerDemographic.getProviderCountry());
        BatchUtils.setString(BatchConstants.PARAM_PROVIDER_ZIP_OR_POSTAL_CODE, params, filerDemographic.getProviderZipOrPostalCode());
        BatchUtils.setString(BatchConstants.PARAM_FILER_STATUS, params, filerDemographic.getFilerStatus());
        BatchUtils.setString(BatchConstants.PARAM_RESPONSIBLE_PERSON_UNIQUE_ID, params, filerDemographic.getResponsiblePersonUniqueId());
        BatchUtils.setString(BatchConstants.PARAM_COMMUNICATION_PREFERENCE, params, filerDemographic.getCommunicationPreference());
        BatchUtils.setString(BatchConstants.PARAM_CORRECTION_CODE, params, filerDemographic.getCorrection());
        BatchUtils.setString(BatchConstants.PARAM_JAN, params, filerDemographic.getJan());
        BatchUtils.setString(BatchConstants.PARAM_FEB, params, filerDemographic.getFeb());
        BatchUtils.setString(BatchConstants.PARAM_MAR, params, filerDemographic.getMar());
        BatchUtils.setString(BatchConstants.PARAM_APR, params, filerDemographic.getApr());
        BatchUtils.setString(BatchConstants.PARAM_MAY, params, filerDemographic.getMay());
        BatchUtils.setString(BatchConstants.PARAM_JUN, params, filerDemographic.getJun());
        BatchUtils.setString(BatchConstants.PARAM_JUL, params, filerDemographic.getJul());
        BatchUtils.setString(BatchConstants.PARAM_AUG, params, filerDemographic.getAug());
        BatchUtils.setString(BatchConstants.PARAM_SEP, params, filerDemographic.getSep());
        BatchUtils.setString(BatchConstants.PARAM_OCT, params, filerDemographic.getOct());
        BatchUtils.setString(BatchConstants.PARAM_NOV, params, filerDemographic.getNov());
        BatchUtils.setString(BatchConstants.PARAM_DEC, params, filerDemographic.getDec());
        BatchUtils.setString(BatchConstants.PARAM_UPDATED_BY, params, filerDemographic.getUpdatedBy());
        BatchUtils.setString(BatchConstants.PARAM_UPDATED_DATE, params, filerDemographic.getUpdatedDt());
        BatchUtils.setString(BatchConstants.PARAM_MAILED_FORM, params, filerDemographic.getMailedForm());
        //BatchUtils.setString(BatchConstants.PARAM_PROGRAM_NAME, params, filerDemographic.getProgramName());
        BatchUtils.setString(BatchConstants.BATCH_ID, params, filerDemographic.getBatchInfo().getBatchId());
        BatchUtils.setString(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, params, filerDemographic.getId().getSourceUniqueId());
        BatchUtils.setString(BatchConstants.PARAM_SOURCE_CD, params, filerDemographic.getId().getSourceCd());

        return params;
    }

    @Override
    public int clearCoverage(FilerDemographicDto filerDemographic) {
        Map<String, Object> params = new HashMap<>();
        BatchUtils.setString(BatchConstants.PARAM_JAN, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_FEB, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_MAR, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_APR, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_MAY, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_JUN, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_JUL, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_AUG, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_SEP, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_OCT, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_NOV, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_DEC, params, "0");
        BatchUtils.setString(BatchConstants.PARAM_UPDATED_BY, params, filerDemographic.getUpdatedBy());
        BatchUtils.setString(BatchConstants.PARAM_UPDATED_DATE, params, filerDemographic.getUpdatedDt());
        BatchUtils.setString(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, params, filerDemographic.getId().getSourceUniqueId());
        BatchUtils.setString(BatchConstants.PARAM_SOURCE_CD, params, filerDemographic.getId().getSourceCd());
        return namedParameterJdbcTemplate.update(
                UPDATE_FILER_DEMOGRAPHICS_COVERAGE, params);
    }

    @Override
    public int updateCoverage(FilerDemographicDto filerDemographic) {
        Map<String, Object> params = new HashMap<>();
        BatchUtils.setString(BatchConstants.PARAM_JAN, params, filerDemographic.getJan());
        BatchUtils.setString(BatchConstants.PARAM_FEB, params, filerDemographic.getFeb());
        BatchUtils.setString(BatchConstants.PARAM_MAR, params, filerDemographic.getMar());
        BatchUtils.setString(BatchConstants.PARAM_APR, params, filerDemographic.getApr());
        BatchUtils.setString(BatchConstants.PARAM_MAY, params, filerDemographic.getMay());
        BatchUtils.setString(BatchConstants.PARAM_JUN, params, filerDemographic.getJun());
        BatchUtils.setString(BatchConstants.PARAM_JUL, params, filerDemographic.getJul());
        BatchUtils.setString(BatchConstants.PARAM_AUG, params, filerDemographic.getAug());
        BatchUtils.setString(BatchConstants.PARAM_SEP, params, filerDemographic.getSep());
        BatchUtils.setString(BatchConstants.PARAM_OCT, params, filerDemographic.getOct());
        BatchUtils.setString(BatchConstants.PARAM_NOV, params, filerDemographic.getNov());
        BatchUtils.setString(BatchConstants.PARAM_DEC, params, filerDemographic.getDec());
        BatchUtils.setString(BatchConstants.PARAM_UPDATED_BY, params, filerDemographic.getUpdatedBy());
        BatchUtils.setString(BatchConstants.PARAM_UPDATED_DATE, params, filerDemographic.getUpdatedDt());
        BatchUtils.setString(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, params, filerDemographic.getId().getSourceUniqueId());
        BatchUtils.setString(BatchConstants.PARAM_SOURCE_CD, params, filerDemographic.getId().getSourceCd());
        return namedParameterJdbcTemplate.update(
                UPDATE_FILER_DEMOGRAPHICS_COVERAGE, params);
    }

    /**
     * @param filerDemographic
     */
    public List<FilerDemographicDto> getFilerDemographic(final FilerDemographicDto filerDemographic) {
        Map<String, Object> params = new HashMap<>();
        BatchUtils.setStringWithoutEmpty(BatchConstants.PARAM_RECIPIENT_SSN, params, filerDemographic.getRecipientSsn());
        BatchUtils.setStringWithoutEmpty(BatchConstants.PARAM_RECIPIENT_TIN, params, filerDemographic.getRecipientTin());
        return this.namedParameterJdbcTemplate.query(SELECT_FILER_DEMOGRAPHICS, params, new FilerDemographicsMapper());
    }

    @Override
    public List<FilerDemographicDto> findAll(Set<FilerDemographicPKDto> ids) {
        return null;
    }

    @Override
    public void bulkToggleActive(Collection<FilerDemographicPKDto> idsToDeactivate, final boolean active) {
        jdbcTemplate.batchUpdate(TOGGLE_FILER_DEMOGRAPHICS_ACTIVE,
                idsToDeactivate, batchSize,
                new ParameterizedPreparedStatementSetter<FilerDemographicPKDto>() {
                    @Override
                    public void setValues(
                            PreparedStatement pStatement,
                            FilerDemographicPKDto id) throws SQLException {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        Date currentDate = new Date();
                        String currentTime = simpleDateFormat.format(currentDate);
                        BatchUtils.setString(1, pStatement, active ? STATUS_ACTIVE : STATUS_INACTIVE);
                        BatchUtils.setString(2, pStatement, BatchConstants.UPDATED_BY_NAME);
                        BatchUtils.setString(3, pStatement, currentTime);
                        BatchUtils.setString(4, pStatement, id.getSourceUniqueId());
                        BatchUtils.setString(5, pStatement, id.getSourceCd());
                    }
                });
    }

    @Override
    public void bulkMarkRegenerate(final Collection<FilerDemographicPKDto> idsToMarkRegenerate) {
        final String sql = "update FILER_DEMOGRAPHICS "
                + " set FORM_STATUS = ?, UPDATED_BY = ?, UPDATED_DATE = ?"
                + " where SOURCE_UNIQUE_ID = ? and SOURCE_CD = ? and FORM_STATUS = ?";
        jdbcTemplate.batchUpdate(sql, idsToMarkRegenerate, batchSize,
                new ParameterizedPreparedStatementSetter<FilerDemographicPKDto>() {
                    @Override
                    public void setValues(final PreparedStatement ps, final FilerDemographicPKDto id) throws SQLException {
                        ps.setString(1, CommonDataConstants.FormStatus.FORM_STATUS_REGENERATE);
                        BatchUtils.setString(2, ps, BatchConstants.UPDATED_BY_NAME);
                        BatchUtils.setCurrentDate(3, ps);
                        ps.setString(4, id.getSourceUniqueId());
                        ps.setString(5, id.getSourceCd());
                        ps.setString(6, CommonDataConstants.FormStatus.FORM_STATUS_GENERATED);
                    }
                });
    }

    @Override
    public void updateTransmissionStatusCodes(
            final Set<FilerDemographicPKDto> ids, final String transmissionStatusCode) {
        final String sql = "update FILER_DEMOGRAPHICS"
                + " set IRS_TRANSMISSION_STATUS_CD = ?"
                + " where SOURCE_UNIQUE_ID = ? and SOURCE_CD = ?";
        jdbcTemplate.batchUpdate(sql, ids, batchSize,
                new ParameterizedPreparedStatementSetter<FilerDemographicPKDto>() {
                    @Override
                    public void setValues(PreparedStatement ps, FilerDemographicPKDto id) throws SQLException {
                        ps.setString(1, transmissionStatusCode);
                        ps.setString(2, id.getSourceUniqueId());
                        ps.setString(3, id.getSourceCd());
                    }
                });
    }

    @Override
    public long filerDemoSeqCurrentValue() {
        return jdbcTemplate.queryForObject(SELECT_FILER_DEMO_SEQ_CURRENT_VAL, Long.class);
    }
}
