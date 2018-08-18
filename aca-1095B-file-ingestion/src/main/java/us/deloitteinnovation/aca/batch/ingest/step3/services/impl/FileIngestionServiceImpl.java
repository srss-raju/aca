package us.deloitteinnovation.aca.batch.ingest.step3.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.constants.BatchQueryConstants;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.BusinessValidationRuleDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.services.FileIngestionService;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static us.deloitteinnovation.aca.batch.constants.BatchQueryConstants.*;


/**
 * Created by tthakore on 10/12/2015.
 */
@Component
@Transactional
public class FileIngestionServiceImpl extends NamedParameterJdbcDaoSupport implements FileIngestionService {

    private final static Logger logger = LoggerFactory.getLogger(FileIngestionServiceImpl.class);


    JdbcTemplate jdbcTemplate;


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public FileIngestionServiceImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        setJdbcTemplate(jdbcTemplate);
    }

    private int batchSize = 100;



    @Override
    public int[][] bulkInsertFD(final List<Step3FilerDataDto> filerDemographics) {
        return this.jdbcTemplate.batchUpdate(
                INSERT_FILER_DEMOGRAPHICS_FILE_INGESTION, filerDemographics, this.batchSize,
                new ParameterizedPreparedStatementSetter<Step3FilerDataDto>() {
                    @Override
                    public void setValues(PreparedStatement pStatement, Step3FilerDataDto filerDemographic) {
                        try {

                            BatchUtils.setString(1, pStatement, filerDemographic.getBatchInfo().getBatchId());
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
                            BatchUtils.setString(18, pStatement, filerDemographic.getProviderAddressLine1());
                            BatchUtils.setString(19, pStatement, filerDemographic.getProviderAddressLine2());
                            BatchUtils.setString(20, pStatement, filerDemographic.getProviderCityOrTown());
                            BatchUtils.setString(21, pStatement, filerDemographic.getProviderContactNo());
                            BatchUtils.setString(22, pStatement, filerDemographic.getProviderCountry());
                            BatchUtils.setString(23, pStatement, filerDemographic.getProviderIdentificationNumber());
                            BatchUtils.setString(24, pStatement, filerDemographic.getProviderName());
                            BatchUtils.setString(25, pStatement, filerDemographic.getProviderStateOrProvince());
                            BatchUtils.setString(26, pStatement, filerDemographic.getProviderZipOrPostalCode());
                            BatchUtils.setString(27, pStatement, filerDemographic.getRecipientAddressLine1());
                            BatchUtils.setString(28, pStatement, filerDemographic.getRecipientAddressLine2());
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
                            BatchUtils.setString(58, pStatement, filerDemographic.getStatus());
                            BatchUtils.setString(59, pStatement, filerDemographic.getMailedForm());
                            BatchUtils.setString(60, pStatement, filerDemographic.getSourceCd());
                            BatchUtils.setString(61, pStatement, filerDemographic.getSourceUniqueId());
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            java.util.Date currentDate = new java.util.Date();
                            String currentTime = simpleDateFormat.format(currentDate);
                            BatchUtils.setString(62, pStatement, currentTime);
                            //Version number will  0 initially for all records  getting inserted.
                            BatchUtils.setString(63, pStatement, (filerDemographic.getVersionNumber() != null)?filerDemographic.getVersionNumber():0);
                            BatchUtils.setString(64, pStatement, 0);

                        } catch (SQLException e) {
                            logger.error("Error file ingestion service impl ", e);
                        }
                    }

                    public int getBatchSize() {
                        return filerDemographics.size();
                    }
                });
    }

    /**
     * @param filerDemographics
     * @return
     */
    @Override
    public int[] bulkUpdateFD(List<Step3FilerDataDto> filerDemographics) {
        final SqlParameterSource[] paramsRA = new SqlParameterSource[filerDemographics.size()];
        for (int i = 0; i < paramsRA.length; i++) {
            final Step3FilerDataDto fd = filerDemographics.get(i);
            final Map<String, Object> params = createUpdateParams(fd);
            paramsRA[i] = new MapSqlParameterSource(params);
        }
        return namedParameterJdbcTemplate.batchUpdate(UPDATE_FILER_DEMOGRAPHICS_FILE_INGESTION, paramsRA);
    }

    /**
     * Insert  record in filer coverage table
     **/
    @Override
    public void bulkInsertFCS(FilerCoverageDto filerCoverages) {
        this.jdbcTemplate.update(INSERT_FILER_COVERAGE, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pStatement) {
                prepareInsertStatementForCoverage(pStatement, filerCoverages);
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
            BatchUtils.setString(22, pStatement, filerCoverage.getTaxYear());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date currentDate = new java.util.Date();
            String currentTime = simpleDateFormat.format(currentDate);
            BatchUtils.setString(23, pStatement, currentTime);

        } catch (SQLException e) {
            logger.error("Error file ingestion service impl prepareInsertStatementForCoverage :", e);
        }
    }

    @Override
    public void bulkInsertBusinessLogs(List<BusinessValidationRuleDto> businessLogList) {
        this.jdbcTemplate.batchUpdate(INSERT_INTO_BUSINESS_RULE_LOG, businessLogList, batchSize,
                new ParameterizedPreparedStatementSetter<BusinessValidationRuleDto>() {
                    public void setValues(PreparedStatement pStatement, BusinessValidationRuleDto businessRuleLogDTO) {
                        prepareInsertStatementBRLog(pStatement, businessRuleLogDTO);
                    }
                });
    }

    /**
     * Insert  record in filer coverage table
     **/
    private void prepareInsertStatementBRLog(final PreparedStatement pStatement, final BusinessValidationRuleDto brLogs) {
        try {
            BatchUtils.setString(1, pStatement, brLogs.getTaxYear());
            BatchUtils.setString(2, pStatement, brLogs.getSourceCd());
            BatchUtils.setString(3, pStatement, brLogs.getSourceUniqueId());
            BatchUtils.setString(4, pStatement, brLogs.getBusinessDecision());
            BatchUtils.setString(5, pStatement, brLogs.getBusinessRule());
            BatchUtils.setDate(6, pStatement, brLogs.getDob());
            BatchUtils.setString(7, pStatement, brLogs.getCorrectionCode());
            BatchUtils.setString(8, pStatement, brLogs.getBatchId());
            BatchUtils.setString(9, pStatement, brLogs.getRowNumber());
            BatchUtils.setString(10, pStatement, brLogs.getUpdatedBy());
            BatchUtils.setString(11, pStatement, brLogs.getUpdatedDate());
        } catch (SQLException e) {
            logger.error("Error file ingestion service impl prepareInsertStatementBRLog :", e);
        }
    }

    @Override
    public void bulkInsertExceptionReport(List<BusinessValidationRuleDto> exceptionReportListList) {

        this.jdbcTemplate.batchUpdate(INSERT_EXCEPTION_REPORT, exceptionReportListList, batchSize,
                new ParameterizedPreparedStatementSetter<BusinessValidationRuleDto>() {
                    public void setValues(PreparedStatement pStatement, BusinessValidationRuleDto exceptionReport) {
                        preparedExceptionReportStatement(pStatement, exceptionReport);
                    }
                });

    }

    private void preparedExceptionReportStatement(final PreparedStatement pStatement, final BusinessValidationRuleDto exceptionReport) {
        try {
            BatchUtils.setString(1, pStatement, exceptionReport.getBusinessRule());
            BatchUtils.setString(2, pStatement, exceptionReport.getBatchId());
            BatchUtils.setString(3, pStatement, exceptionReport.getSourceUniqueId());
            BatchUtils.setString(4, pStatement, exceptionReport.getRowNumber());

        } catch (SQLException e) {
            logger.error("Error in file ingestion serviceimple :", e);
        }
    }

    @Override
    public int[] bulkUpdateFormStatus(List<Step3FilerDataDto> filerDemographics) {
        final SqlParameterSource[] paramsRA = new SqlParameterSource[filerDemographics.size()];
        for (int i = 0; i < paramsRA.length; i++) {
            final Step3FilerDataDto fd = filerDemographics.get(i);
            final Map<String, Object> params = createRegenerateParam(fd);
            paramsRA[i] = new MapSqlParameterSource(params);
        }
        return namedParameterJdbcTemplate.batchUpdate(MARK_RECORD_AS_REGENERATE, paramsRA);
    }

    @Override
    public void bulkDeleteFromCoverage(FilerCoverageDto filerDemographicsCoverage) {
        final Map<String, Object> params = createDeleteParam(filerDemographicsCoverage);
        namedParameterJdbcTemplate.update(DELETE_FILER_COVERAGES_FOR_FD, params);
    }

    private Map<String, Object> createDeleteParam(FilerCoverageDto filerDemographic) {
        Map<String, Object> params = new HashMap<>();
        BatchUtils.setStringWithNull(BatchConstants.PARAM_TAX_YEAR, params, filerDemographic.getId().getTaxYear());
        BatchUtils.setStringWithNull(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, params, filerDemographic.getId().getSourceUniqueId());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_SOURCE_CD, params, filerDemographic.getId().getSourceCd());
        return params;
    }

    /**
     *updates the version number of records in the database.
     *
     * @param otherRecordsToUpdate
     */
    @Override
    public int[] bulkUpdateVersionNo(List<Step3FilerDataDto> otherRecordsToUpdate) {
        final SqlParameterSource[] paramsRA = new SqlParameterSource[otherRecordsToUpdate.size()];
        for (int i = 0; i < paramsRA.length; i++) {
            final Step3FilerDataDto fd = otherRecordsToUpdate.get(i);
            final Map<String, Object> params = createVersionUpdateParam(fd);
            paramsRA[i] = new MapSqlParameterSource(params);
        }
        return namedParameterJdbcTemplate.batchUpdate(UPDATE_FILER_DEMOGRAPHICS_VERSION, paramsRA);

    }

    /**
     * Gets the list of source unique id's from business decisions log
     * table belonging to a given batch id which passed the cross record
     * validation rules.
     *
     * @param batchId
     * @return list of source unique id's as integers
     */
    @Override
    public Integer getDistinctSourceUniqueIdsForBatch(Integer batchId) {
        Integer sidCount=null;

        SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(BatchQueryConstants.GET_PASSED_COUNT_FROM_BUSINESS_LOG + batchId);

        while (sqlRowSet.next()) {
            sidCount = sqlRowSet.getInt("SIDCOUNT");
        }
        return sidCount;
    }

    private Map<String, Object> createVersionUpdateParam(Step3FilerDataDto filerDemographic) {
        Map<String, Object> params = new HashMap<>();
        BatchUtils.setStringWithNull(BatchConstants.PARAM_TAX_YEAR, params, filerDemographic.getId().getTaxYear());
        BatchUtils.setStringWithNull(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, params, filerDemographic.getId().getSourceUniqueId());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_SOURCE_CD, params, filerDemographic.getId().getSourceCd());
        BatchUtils.setStringWithNull(BatchConstants.RECORD_VERSION, params, filerDemographic.getVersionNumber());
        return params;
    }

    private Map<String, Object> createRegenerateParam(Step3FilerDataDto filerDemographic) {
        Map<String, Object> params = new HashMap<>();
        BatchUtils.setStringWithNull(BatchConstants.PARAM_TAX_YEAR, params, filerDemographic.getId().getTaxYear());
        BatchUtils.setStringWithNull(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, params, filerDemographic.getId().getSourceUniqueId());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_SOURCE_CD, params, filerDemographic.getId().getSourceCd());
        BatchUtils.setStringWithNull(BatchConstants.FORM_STATUS, params, (filerDemographic.getFormStatus() == null) ? null : CommonDataConstants.FormStatus.FORM_STATUS_REGENERATE);
        BatchUtils.setStringWithNull(BatchConstants.CORRECTION_CODE, params, (filerDemographic.getCorrectionIndicator() != null) ? Integer.parseInt(filerDemographic.getCorrectionIndicator()) : null);
        return params;
    }

    private Map<String, Object> createUpdateParams(Step3FilerDataDto filerDemographic) {
        Map<String, Object> params = new HashMap<>();
        BatchUtils.setStringWithNull(BatchConstants.PARAM_TAX_YEAR, params, filerDemographic.getId().getTaxYear());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_FIRST_NAME, params, filerDemographic.getRecipientFirstName());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_MIDDLE_NAME, params, filerDemographic.getRecipientMiddleName());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_LAST_NAME, params, filerDemographic.getRecipientLastName());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_SUFFIX_NAME, params, filerDemographic.getRecepientSuffixName());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_SSN, params, filerDemographic.getRecipientSsn());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_TIN, params, filerDemographic.getRecipientTin());
        BatchUtils.setDate(BatchConstants.PARAM_RECIPIENT_DOB, params, filerDemographic.getRecipientDob());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_ADDRESS_LINE_1, params, filerDemographic.getRecipientAddressLine1());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_ADDRESS_LINE_2, params, filerDemographic.getRecipientAddressLine2());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_CITY, params, filerDemographic.getRecipientCity());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_STATE, params, filerDemographic.getRecipientState());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_ZIP_5, params, filerDemographic.getRecepientZip5());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_ZIP_4, params, filerDemographic.getRecepientZip4());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_E_MAIL, params, filerDemographic.getEMail());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RECIPIENT_LANGUAGE_PREFERENCE, params, filerDemographic.getLanguagePreference());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_POLICY_ORIGIN, params, filerDemographic.getPolicyOrigin());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_SHOP_IDENTIFIER, params, filerDemographic.getShopIdentifier());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_IRS_TRANSMISSION_CD, params, filerDemographic.getIrsTransmissionStatusCD());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_EMPLOYER_NAME, params, filerDemographic.getEmployerName());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_EMPLOYER_IDENTIFICATION_NUMBER, params, filerDemographic.getEmployerIdentificationNumber());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_EMPLOYER_CONTACT_NO, params, filerDemographic.getEmployerContactNo());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_EMPLOYER_ADDRESS_LINE_1, params, filerDemographic.getEmployerAddressLine1());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_EMPLOYER_ADDRESS_LINE_2, params, filerDemographic.getEmployerAddressLine2());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_EMPLOYER_CITY_OR_TOWN, params, filerDemographic.getEmployerCityOrTown());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_EMPLOYER_STATE_OR_PROVINCE, params, filerDemographic.getEmployerStateOrProvince());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_EMPLOYER_COUNTRY, params, filerDemographic.getEmployerCountry());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_EMPLOYER_ZIP_OR_POSTAL_CODE, params, filerDemographic.getZipOrPostalCode());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_PROVIDER_NAME, params, filerDemographic.getProviderName());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_PROVIDER_IDENTIFICATION_NUMBER, params, filerDemographic.getProviderIdentificationNumber());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_PROVIDER_CONTACT_NO, params, filerDemographic.getProviderContactNo());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_PROVIDER_ADDRESS_LINE_1, params, filerDemographic.getProviderAddressLine1());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_PROVIDER_ADDRESS_LINE_2, params, filerDemographic.getProviderAddressLine2());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_PROVIDER_CITY_OR_TOWN, params, filerDemographic.getProviderCityOrTown());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_PROVIDER_STATE_OR_PROVINCE, params, filerDemographic.getProviderStateOrProvince());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_PROVIDER_COUNTRY, params, filerDemographic.getProviderCountry());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_PROVIDER_ZIP_OR_POSTAL_CODE, params, filerDemographic.getProviderZipOrPostalCode());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_FILER_STATUS, params, filerDemographic.getFilerStatus());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_RESPONSIBLE_PERSON_UNIQUE_ID, params, filerDemographic.getResponsiblePersonUniqueId());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_COMMUNICATION_PREFERENCE, params, filerDemographic.getCommunicationPreference());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_CORRECTION_CODE, params, filerDemographic.getCorrection());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_JAN, params, filerDemographic.getJan());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_FEB, params, filerDemographic.getFeb());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_MAR, params, filerDemographic.getMar());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_APR, params, filerDemographic.getApr());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_MAY, params, filerDemographic.getMay());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_JUN, params, filerDemographic.getJun());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_JUL, params, filerDemographic.getJul());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_AUG, params, filerDemographic.getAug());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_SEP, params, filerDemographic.getSep());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_OCT, params, filerDemographic.getOct());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_NOV, params, filerDemographic.getNov());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_DEC, params, filerDemographic.getDec());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_UPDATED_BY, params, filerDemographic.getUpdatedBy());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_UPDATED_DATE, params, filerDemographic.getUpdatedDt());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_MAILED_FORM, params, filerDemographic.getMailedForm());
        BatchUtils.setStringWithNull(BatchConstants.BATCH_ID, params, filerDemographic.getBatchInfo().getBatchId());
        BatchUtils.setStringWithNull(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER, params, filerDemographic.getId().getSourceUniqueId());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_SOURCE_CD, params, filerDemographic.getId().getSourceCd());
        BatchUtils.setStringWithNull(BatchConstants.FORM_STATUS, params, filerDemographic.getFormStatus());
        if (!CommonDataConstants.RECORD_STATUS_INACTIVE.equals(filerDemographic.getStatus())) {
            if (!filerDemographic.getFilerStatus().equals(String.valueOf(CommonDataConstants.FilerStatus.FILER_STATUS_C)))
                BatchUtils.setStringWithNull(BatchConstants.FORM_STATUS, params, (filerDemographic.getFormStatus() == null) ? null : CommonDataConstants.FormStatus.FORM_STATUS_REGENERATE);
            else
                BatchUtils.setStringWithNull(BatchConstants.FORM_STATUS, params, null);
        }
        BatchUtils.setStringWithNull(BatchConstants.PARAM_STATUS, params, filerDemographic.getStatus());
        BatchUtils.setStringWithNull(BatchConstants.PARAM_CORRECTION_INDICATOR, params, filerDemographic.getCorrectionIndicator());
        BatchUtils.setStringWithNull(BatchConstants.RECORD_VERSION, params, filerDemographic.getVersionNumber());
        return params;
    }
}
