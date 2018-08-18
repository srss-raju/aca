package us.deloitteinnovation.aca.batch.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.profile.ProfileProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import static us.deloitteinnovation.aca.constants.CommonEntityConstants.CORRECTION;

/**
 * Maps a field set to a FilerDemographicDto object.
 */
@Component
public class ApplicationMapper implements FieldSetMapper<FilerDemographicDto> {

    //private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMapper.class);

    @Autowired
    private ProfileProperties profileProperties;

    /**
     * @param fieldSet the {@link FieldSet} to map
     * @param line
     * @return
     * @throws BindException
     */
    public FilerDemographicDto mapFieldSet(FieldSet fieldSet, int line) throws BindException {
        int taxYear = NumberUtils.toInt(readString(fieldSet, BatchConstants.TAX_YEAR));
        FilerDemographicDto filerDemographic = new FilerDemographicDto();
        if (!StringUtils.isEmpty(readString(fieldSet, BatchConstants.RECIPIENT_LANGUAGE_PREFERENCE))) {
            filerDemographic.setLanguagePreference(readString(fieldSet, BatchConstants.RECIPIENT_LANGUAGE_PREFERENCE));
        } else {
            filerDemographic.setLanguagePreference("");
        }

        FilerDemographicPKDto demographicPK = new FilerDemographicPKDto();
        demographicPK.setSourceUniqueId(readString(fieldSet, BatchConstants.RECIPIENT_UNIQUE_ID));
        demographicPK.setTaxYear(readString(fieldSet, BatchConstants.TAX_YEAR));
        filerDemographic.setId(demographicPK);
        filerDemographic.setCorrection(readString(fieldSet, CORRECTION));
        filerDemographic.setRecipientFirstName(readString(fieldSet, BatchConstants.RECIPIENT_FIRST_NAME));
        filerDemographic.setRecipientMiddleName(readString(fieldSet, BatchConstants.RECIPIENT_MIDDLE_NAME));
        filerDemographic.setRecipientLastName(readString(fieldSet, BatchConstants.RECIPIENT_LAST_NAME));
        filerDemographic.setRecepientSuffixName(readString(fieldSet, BatchConstants.RECIPIENT_NAME_SUFFIX));
        filerDemographic.setRecipientSsn(readString(fieldSet, CommonDataConstants.RECIPIENT_SSN));
        filerDemographic.setRecipientTin(readString(fieldSet, CommonDataConstants.RECIPIENT_TIN));
        filerDemographic.setRecipientDobStr(readString(fieldSet, CommonDataConstants.RECIPIENT_DOB));
        if (BatchUtils.validateDate(fieldSet, CommonDataConstants.RECIPIENT_DOB, BatchConstants.RECIPIENT_DOB_REGEX)) {
            filerDemographic.setRecipientDob(BatchUtils.getDate(
                    readString(fieldSet, CommonDataConstants.RECIPIENT_DOB)));
        }

        filerDemographic.setRecipientAddressLine1(readString(fieldSet, BatchConstants.RECIPIENT_ADDRESS_LINE_1));
        filerDemographic.setRecipientAddressLine2(readString(fieldSet, BatchConstants.RECIPIENT_ADDRESS_LINE_2));
        filerDemographic.setRecipientCity(readString(fieldSet, BatchConstants.RECIPIENT_CITY));
        filerDemographic.setRecipientState(readString(fieldSet, BatchConstants.RECIPIENT_STATE_CODE));
        filerDemographic.setRecipientZip4(readString(fieldSet, BatchConstants.RECIPIENT_ZIP_4));
        filerDemographic.setRecipientZip5(readString(fieldSet, BatchConstants.RECIPIENT_ZIP_5));
        filerDemographic.setPolicyOrigin(readString(fieldSet, BatchConstants.POLICY_ORIGIN));

        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String currentTime = simpleDateFormat.format(currentDate);

        final String filerStatus = readString(fieldSet, BatchConstants.FILER_STATUS);
        filerDemographic.setFilerStatus(filerStatus);

        /** Add filer coverage if filer has it.  Filer status 'N' means 'not covered' */
        FilerCoverageDto filerCoverage = new FilerCoverageDto();
        filerCoverage.setId(demographicPK);
        filerCoverage.setRecipientCaseApplicationId(readString(fieldSet, BatchConstants.RECIPIENT_CASE_APPLICATION_ID));
        filerCoverage.setProgramName(readString(fieldSet, BatchConstants.POLICY_PROGRAM_NAME));

        final String coverageBeginDateStr = readString(fieldSet, BatchConstants.POLICY_COVERAGE_BEGIN_DT);
        filerCoverage.setOrigCoverageBeginDtStr(coverageBeginDateStr);
        Date coverageBeginDate = BatchUtils.getDate(coverageBeginDateStr);
        filerCoverage.setOrigCoverageBeginDt(coverageBeginDate);

        final String coverageEndDateStr = readString(fieldSet, BatchConstants.POLICY_COVERAGE_END_DT);
        filerCoverage.setOrigCoverageEndDtStr(coverageEndDateStr);
        Date coverageEndDate = BatchUtils.getDate(coverageEndDateStr);
        filerCoverage.setOrigCoverageEndDt(coverageEndDate);

        if (filerCoverage.getOrigCoverageBeginDt() != null && filerCoverage.getOrigCoverageEndDt() != null) {
            this.getCoverageMonths(taxYear, filerCoverage);
        }
        filerCoverage.setUpdatedBy("System");
        filerCoverage.setUpdatedDt(currentTime);
        filerDemographic.updateCoverageMonths(filerCoverage);
        filerDemographic.addFilerCoverage(filerCoverage);   // add this regardless of filer status because coverage dates
        // of 99999999 or 00000000 can be otherwise meaningful
        filerDemographic.setActive(!"N".equals(filerDemographic.getFilerStatus()));  // record with no coverage is set as inactive

        filerDemographic.setShopIdentifier(readString(fieldSet, BatchConstants.POLICY_SHOP_IDENTIFIER));
        filerDemographic.setEmployerName(readString(fieldSet, BatchConstants.EMPLOYER_NAME));
        filerDemographic.setEmployerIdentificationNumber(readString(fieldSet, BatchConstants.EMPLOYER_IDENTIFICATION_NUMBER));
        filerDemographic.setEmployerAddressLine1(readString(fieldSet, BatchConstants.EMPLOYER_ADDRESS_LINE_1));
        filerDemographic.setEmployerAddressLine2(readString(fieldSet, BatchConstants.EMPLOYER_ADDRESS_LINE_2));
        filerDemographic.setEmployerCityOrTown(readString(fieldSet, BatchConstants.EMPLOYER_CITY_OR_TOWN));
        filerDemographic.setEmployerStateOrProvince(readString(fieldSet, BatchConstants.EMPLOYER_STATE_OR_PROVINCE));
        filerDemographic.setEmployerCountry(readString(fieldSet, BatchConstants.EMPLOYER_COUNTRY));
        filerDemographic.setZipOrPostalCode(readString(fieldSet, BatchConstants.EMPLOYER_ZIP_OR_FOREIGN_POSTAL_CODE));
        filerDemographic.setEmployerContactNo(readString(fieldSet, BatchConstants.EMPLOYER_CONTACT_NO));
        filerDemographic.setProviderName(readString(fieldSet, BatchConstants.PROVIDER_NAME));
        final String providerIdentificationNumber = StringUtils.trimToEmpty(
                readString(fieldSet, BatchConstants.PROVIDER_IDENTIFICATION_NUMBER)).replace("-", "");
        filerDemographic.setProviderIdentificationNumber(providerIdentificationNumber);
        filerDemographic.setProviderContactNo(readString(fieldSet, BatchConstants.PROVIDER_CONTACT_NUMBER));
        filerDemographic.setProviderAddressLine1(readString(fieldSet, BatchConstants.PROVIDER_ADDRESS_LINE_1));
        filerDemographic.setProviderAddressLine2(readString(fieldSet, BatchConstants.PROVIDER_ADDRESS_LINE_2));
        filerDemographic.setProviderCityOrTown(readString(fieldSet, BatchConstants.PROVIDER_CITY_OR_TOWN));
        filerDemographic.setProviderStateOrProvince(readString(fieldSet, BatchConstants.PROVIDER_STATE_OR_PROVINCE));
        filerDemographic.setProviderCountry(readString(fieldSet, BatchConstants.PROVIDER_COUNTRY));
        filerDemographic.setProviderZipOrPostalCode(readString(fieldSet, BatchConstants.PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE));
        String respPersonUniqueId = readString(fieldSet, BatchConstants.RESPONSIBLE_PERSON_UNIQUE_ID);
        Pattern pattern = Pattern.compile("[0-9]+");
        if (respPersonUniqueId == null || (respPersonUniqueId != null && pattern.matcher(respPersonUniqueId).matches())) {
            final long responsiblePersonUniqueId = NumberUtils.toLong(respPersonUniqueId);
            if ("C".equalsIgnoreCase(filerStatus)) {
                filerDemographic.setResponsiblePersonUniqueId(Long.toString(responsiblePersonUniqueId));
            } else if ("R".equalsIgnoreCase(filerStatus)) {
                if (Long.valueOf(responsiblePersonUniqueId) != 0L) {
                    filerDemographic.setResponsiblePersonUniqueId(Long.toString(responsiblePersonUniqueId));
                } else {
                    filerDemographic.setResponsiblePersonUniqueId(Long.toString(NumberUtils.toLong(readString(fieldSet, BatchConstants.RECIPIENT_UNIQUE_ID))));
                }
            }
        } else {
            filerDemographic.setResponsiblePersonUniqueId(respPersonUniqueId);
        }
        filerDemographic.setCommunicationPreference(readString(fieldSet, BatchConstants.COMM_PREFERENCE));
        filerDemographic.seteMail(readString(fieldSet, BatchConstants.EMAIL));
        filerDemographic.setMailedForm(readString(fieldSet, BatchConstants.MAILED_FORM));
        filerDemographic.setLineNumber(line);
        filerDemographic.setUpdatedBy("File Ingestion");
        filerDemographic.setUpdatedDt(currentTime);
        // filerDemographic.setFilerCoverages(filerCoverage);
        return filerDemographic;
    }

    private String readString(final FieldSet fieldSet, final String fieldName) {
        String fieldValue = StringUtils.trimToNull(fieldSet.readString(fieldName));
        if (StringUtils.isNotBlank(fieldValue) && fieldValue.contains("\"")) {
            fieldValue = fieldValue.replaceAll("\"", "");
        }
        return fieldValue;
    }

    private void getCoverageMonths(int taxYear, FilerCoverageDto filerCoverage) {
        String coverageStartDate = filerCoverage.getOrigCoverageBeginDtStr();
        String coverageEndDate = filerCoverage.getOrigCoverageEndDtStr();
        int coverageStarDateYear = Integer.parseInt(coverageStartDate.substring(4, 8));
        int coverageEndDateYear = Integer.parseInt(coverageEndDate.substring(4, 8));
        if ((coverageStarDateYear < taxYear) && (coverageEndDateYear > taxYear)) {
            int startMonth = BatchUtils.getCoverageMonth(BatchUtils.getDate(BatchConstants.COVERAGE_START_DATE_DEFAULT));
            int endMonth = BatchUtils.getCoverageMonth(BatchUtils.getDate(BatchConstants.COVERAGE_END_DATE_DEFAULT));
            BatchUtils.setMonth(filerCoverage, startMonth, endMonth);
        } else if ((coverageStarDateYear < taxYear) && (coverageEndDateYear == taxYear)) {
            int startMonth = BatchUtils.getCoverageMonth(BatchUtils.getDate(BatchConstants.COVERAGE_START_DATE_DEFAULT));
            int endMonth = Integer.parseInt(coverageEndDate.substring(0, 2));
            BatchUtils.setMonth(filerCoverage, startMonth, endMonth);
        } else if ((coverageStarDateYear == taxYear) && (coverageEndDateYear == taxYear)) {
            int startMonth = Integer.parseInt(coverageStartDate.substring(0, 2));
            int endMonth = Integer.parseInt(coverageEndDate.substring(0, 2));
            BatchUtils.setMonth(filerCoverage, startMonth, endMonth);
        } else if ((coverageStarDateYear == taxYear) && (coverageEndDateYear > taxYear)) {
            int startMonth = Integer.parseInt(coverageStartDate.substring(0, 2));
            int endMonth = BatchUtils.getCoverageMonth(BatchUtils.getDate(BatchConstants.COVERAGE_END_DATE_DEFAULT));
            BatchUtils.setMonth(filerCoverage, startMonth, endMonth);
        }
    }

    public ProfileProperties getProfileProperties() {
        return profileProperties;
    }

    public void setProfileProperties(ProfileProperties profileProperties) {
        this.profileProperties = profileProperties;
    }
}
