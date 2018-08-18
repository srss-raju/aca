package us.deloitteinnovation.aca.batch.ingest.step3.dto;

/**
 * Created by tthakore on 9/1/2016.
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static us.deloitteinnovation.aca.constants.CommonDataConstants.*;

/**
 */
@Component
public class Step3FilerDataDtoMapper implements RowMapper<Step3FilerDataDto> {

    private static Logger logger = LoggerFactory.getLogger(Step3FilerDataDtoMapper.class);

    @Override
    public Step3FilerDataDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        /*setup current date and time*/
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String currentTime = simpleDateFormat.format(currentDate);


        Step3FilerDataDto filer = new Step3FilerDataDto();
        filer.setBatchInfo(new BatchInfoDto());
        filer.setId(new FilerDemographicPKDto());
        filer.setId(new FilerDemographicPKDto());
        filer.getId().setSourceCd(rs.getString("SOURCE_CD"));
        filer.getId().setSourceUniqueId(String.valueOf(rs.getLong("SOURCE_UNIQUE_ID")));
        filer.getId().setTaxYear(rs.getString("TAX_YEAR"));

        if (logger.isDebugEnabled()) {
            logger.debug("FilerMapper on source cd {} and source unique id {}", filer.getSourceCd(), filer.getSourceUniqueId());
        }

        String name;
        if (rs.getString("RECIPIENT_MIDDLE_NAME") == null)
            name = rs.getString("RECIPIENT_FIRST_NAME") + " " + rs.getString("RECIPIENT_LAST_NAME");
        else
            name = rs.getString("RECIPIENT_FIRST_NAME") + " " + rs.getString("RECIPIENT_MIDDLE_NAME") + " " + rs.getString("RECIPIENT_LAST_NAME");

        filer.getBatchInfo().setBatchId(rs.getInt("BATCH_ID"));
        filer.setRecipientFirstName(rs.getString("RECIPIENT_FIRST_NAME"));
        filer.setRecipientLastName(rs.getString("RECIPIENT_LAST_NAME"));
        filer.setRecipientMiddleName(rs.getString("RECIPIENT_MIDDLE_NAME"));
        filer.setRecepientSuffixName(rs.getString("RECIPIENT_SUFFIX_NAME"));
        filer.setRecipientTin(rs.getString("RECIPIENT_TIN"));
        filer.getId().setTaxYear(rs.getString("TAX_YEAR"));
        filer.setShopIdentifier(rs.getString("SHOP_IDENTIFIER"));
        //  filer.setFilerDemoSeq(rs.getLong("FILER_DEMO_SEQ"));
        filer.setRecipientSsn(rs.getString("RECIPIENT_SSN"));
        filer.setJan(determineMonthStatus(rs.getString(JAN)));
        filer.setFeb(determineMonthStatus(rs.getString(FEB)));
        filer.setMar(determineMonthStatus(rs.getString(MAR)));
        filer.setApr(determineMonthStatus(rs.getString(APR)));
        filer.setMay(determineMonthStatus(rs.getString(MAY)));
        filer.setJun(determineMonthStatus(rs.getString(JUN)));
        filer.setJul(determineMonthStatus(rs.getString(JUL)));
        filer.setAug(determineMonthStatus(rs.getString(AUG)));
        filer.setSep(determineMonthStatus(rs.getString(SEP)));
        filer.setOct(determineMonthStatus(rs.getString(OCT)));
        filer.setNov(determineMonthStatus(rs.getString(NOV)));
        filer.setDec(determineMonthStatus(rs.getString(DEC)));
        //   filer.setId(rs.getInt("FILER_DEMO_SEQ"));
        filer.setFilerStatus(rs.getString("FILER_STATUS"));
        filer.setStatus(rs.getString("STATUS"));
        filer.setRecipientAddressLine1(rs.getString("RECIPIENT_ADDRESS_LINE_1"));
        filer.setRecipientAddressLine2(rs.getString("RECIPIENT_ADDRESS_LINE_2"));
        filer.setRecipientCity(rs.getString("RECIPIENT_CITY"));
        filer.setRecipientState(rs.getString("RECIPIENT_STATE"));
        filer.setRecipientZip5(rs.getString("RECIPIENT_ZIP_5"));
        filer.setRecipientZip4(rs.getString("RECIPIENT_ZIP_4"));
        filer.setProviderName(rs.getString("PROVIDER_NAME"));
        filer.setRecipientDob(rs.getDate("RECIPIENT_DOB"));
        filer.setProviderIdentificationNumber(rs.getString("PROVIDER_IDENTIFICATION_NUMBER"));
        filer.setProviderAddressLine1(rs.getString("PROVIDER_ADDRESS_LINE_1"));
        filer.setProviderAddressLine2(rs.getString("PROVIDER_ADDRESS_LINE_2"));
        filer.setProviderCityOrTown(rs.getString("PROVIDER_CITY_OR_TOWN"));
        filer.setProviderStateOrProvince(rs.getString("PROVIDER_STATE_OR_PROVINCE"));
        filer.setProviderZipOrPostalCode(rs.getString("PROVIDER_ZIP_OR_POSTAL_CODE"));
        filer.setProviderContactNo(String.valueOf(rs.getLong("PROVIDER_CONTACT_NO")));
        filer.setPolicyOrigin(rs.getString("POLICY_ORIGIN"));
        filer.setEmployerName(rs.getString("EMPLOYER_NAME"));
        filer.setEmployerIdentificationNumber(rs.getString("EMPLOYER_IDENTIFICATION_NUMBER"));
        filer.setEmployerAddressLine1(rs.getString("EMPLOYER_ADDRESS_LINE_1"));
        filer.setEmployerAddressLine2(rs.getString("EMPLOYER_ADDRESS_LINE_2"));
        filer.setEmployerCityOrTown(rs.getString("EMPLOYER_CITY_OR_TOWN"));
        filer.setEmployerStateOrProvince(rs.getString("EMPLOYER_STATE_OR_PROVINCE"));
        filer.setZipOrPostalCode(rs.getString("EMPLOYER_ZIP_OR_POSTAL_CODE"));
        filer.setProviderAddressLine2(rs.getString("PROVIDER_ADDRESS_LINE_2"));
        filer.setFormStatus(rs.getString("FORM_STATUS"));
        filer.setRecordSource(rs.getString("RECORD_SOURCE"));
        filer.setRowNumber(String.valueOf(rs.getInt("ROW_NUMBER")));
        String correction = rs.getString("CORRECTION_CODE");
        filer.setMailedForm(rs.getString("MAILED_FORM"));
        filer.setUpdatedDt(currentTime);
        filer.setUpdatedBy(rs.getString("UPDATED_BY"));
        filer.setResponsiblePersonUniqueId(rs.getString("RESPONSIBLE_PERSON_UNIQUE_ID"));
        filer.setCorrection(correction);
        filer.setOriginalCorrectionCode(correction);
        /** Add filer coverage if filer has it.  Filer status 'N' means 'not covered' */
        FilerCoverageDto filerCoverage = new FilerCoverageDto();
        filerCoverage.setId(filer.getId());
        filerCoverage.setRecipientCaseApplicationId(rs.getString("CASE_APPLICATION_ID"));
        filerCoverage.setProgramName(rs.getString(BatchConstants.POLICY_PROGRAM_NAME));
        filerCoverage.setOrigCoverageBeginDt(rs.getDate(BatchConstants.POLICY_COVERAGE_BEGIN_DT));
        filerCoverage.setOrigCoverageEndDt(rs.getDate(BatchConstants.POLICY_COVERAGE_END_DT));
        filerCoverage.setTaxYear(filer.getId().getTaxYear());
        if (filerCoverage.getOrigCoverageBeginDt() != null) {
            filerCoverage.setJan(determineMonthStatus(rs.getString(JAN)));
            filerCoverage.setFeb(determineMonthStatus(rs.getString(FEB)));
            filerCoverage.setMar(determineMonthStatus(rs.getString(MAR)));
            filerCoverage.setApr(determineMonthStatus(rs.getString(APR)));
            filerCoverage.setMay(determineMonthStatus(rs.getString(MAY)));
            filerCoverage.setJun(determineMonthStatus(rs.getString(JUN)));
            filerCoverage.setJul(determineMonthStatus(rs.getString(JUL)));
            filerCoverage.setAug(determineMonthStatus(rs.getString(AUG)));
            filerCoverage.setSep(determineMonthStatus(rs.getString(SEP)));
            filerCoverage.setOct(determineMonthStatus(rs.getString(OCT)));
            filerCoverage.setNov(determineMonthStatus(rs.getString(NOV)));
            filerCoverage.setDec(determineMonthStatus(rs.getString(DEC)));
        }
        filerCoverage.setComments(rs.getString("COMMENTS"));
        filerCoverage.setUpdatedBy(rs.getString("UPDATED_BY"));
        filerCoverage.setUpdatedDt(currentTime);
        filer.addFilerCoverage(filerCoverage);
        filer.setComments(rs.getString("COMMENTS"));
        filer.setCommunicationPreference(rs.getString("COMMUNICATION_PREFERENCE"));
        filer.setProviderCountry(rs.getString("PROVIDER_COUNTRY"));
        filer.seteMail(rs.getString("RECIPIENT_E_MAIL"));
        filer.setCorrectionDt(rs.getDate("CORRECTION_DATE"));
        filer.setEmployerCountry(rs.getString("EMPLOYER_COUNTRY"));
        filer.setLanguagePreference(rs.getString("RECIPIENT_LANGUAGE_PREFERENCE"));
        filer.setEmployerContactNo(rs.getString("EMPLOYER_CONTACT_NO"));
        filer.setCorrectionIndicator(rs.getString("CORRECTION_INDICATOR"));
        filer.setVersionNumber(rs.getInt("RECORD_VERSION"));
       filer.setIrsTransmissionStatusCD(rs.getString("IRS_TRANSMISSION_STATUS_CD"));


        return filer;
    }

    /**
     * @param monthValue
     * @return Returns CommonEntityConstants.COVERED if the monthValue equals COVERED.  Otherwise returns UNCOVERED.
     */
    protected String determineMonthStatus(String monthValue) {
        if (CommonEntityConstants.COVERED.equals(monthValue))
            return CommonEntityConstants.COVERED;
        return CommonEntityConstants.UNCOVERED;
    }

    private String readString(final FieldSet fieldSet, final String fieldName) {
        return StringUtils.trimToNull(fieldSet.readString(fieldName));
    }

}