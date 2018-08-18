package us.deloitteinnovation.aca.batch.ingest.step2;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dao.ExceptionReportDao;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.service.ExceptionReportService;
import us.deloitteinnovation.aca.batch.service.impl.ExceptionReportServiceImpl;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;
import us.deloitteinnovation.aca.entity.FilerDemographicStagingEntity;

import javax.validation.ConstraintViolation;
import java.util.ResourceBundle;
import java.util.Set;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Super class for all filer verification checks
 */
public class Step2VerifyFilerProcessorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Step2VerifyFilerProcessorTest.class);

    protected LocalValidatorFactoryBean validator;
    String sourceCd;
    ResourceBundle resourceBundle;
    FilerDemographicDto filerDemographicDto;
    Set<ConstraintViolation<FilerDemographicDto>> constraintViolations = null;
    Set<ConstraintViolation<FilerCoverageDto>> coverageConstraintViolations = null;
    Step2VerifyFilerProcessor step2VerifyFilerProcessor;
    @Autowired
    ExceptionReportDao exceptionReportDao;
    FilerDemographicStagingEntity filerDemographicStagingEntity;
    BatchInfoDto batchInfoDto;
    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService;
    //@Autowired
    ExceptionReportService exceptionReportService;
    Environment env;


    @Before
    public void setUp() throws Exception {
        sourceCd = "INFSSICE";
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        filerDemographicDto = new FilerDemographicDto();
        batchInfoDto = new BatchInfoDto();
        batchInfoDto.setSourceCode(sourceCd);
        batchInfoDto.setBatchId(1234);
        resourceBundle = ResourceBundle.getBundle("Step2VerifyFilerProcessorTest");
        exceptionReportService = new ExceptionReportServiceImpl();
        env = mock(Environment.class);
        when(env.getProperty("tax.year.mismatch.error")).thenReturn("ER3.1.1.1: Tax year for the record does not match the tax year in the file name");
        batchInfoDto.setSourceCode(sourceCd);
        ReflectionTestUtils.setField(exceptionReportService, "exceptionReportDao", exceptionReportDao);
        step2VerifyFilerProcessor = new Step2VerifyFilerProcessor();
        step2VerifyFilerProcessor.batchInfoDto = new BatchInfoDto();
        step2VerifyFilerProcessor.sourceSystemConfigDataService = new SourceSystemConfigDataService();
        step2VerifyFilerProcessor.exceptionReportService = this.exceptionReportService;
        step2VerifyFilerProcessor.validator = this.validator;
        step2VerifyFilerProcessor.env = this.env;
        step2VerifyFilerProcessor.taxYearAsBatchArg = resourceBundle.getString("SOURCE_UNIQUE_ID");
        initializeFilerDemographicDto();
    }

    /**
     * Helper method to initialize the filer demographic DTO.
     */
    private void initializeFilerDemographicDto() throws Exception {
        FilerDemographicPKDto demographicPK = new FilerDemographicPKDto();
        demographicPK.setSourceCd(sourceCd);
        demographicPK.setSourceUniqueId(resourceBundle.getString("SOURCE_UNIQUE_ID"));
        demographicPK.setTaxYear(resourceBundle.getString("TAX_YEAR"));
        filerDemographicDto.setId(demographicPK);
        filerDemographicDto.setLanguagePreference(resourceBundle.getString("RECIPIENT_LANGUAGE_PREFERENCE"));
        filerDemographicDto.setCorrection(resourceBundle.getString("CORRECTION_CODE"));
        filerDemographicDto.setRecipientFirstName(resourceBundle.getString("RECIPIENT_FIRST_NAME"));
        filerDemographicDto.setRecipientMiddleName(resourceBundle.getString("RECIPIENT_MIDDLE_NAME"));
        filerDemographicDto.setRecipientLastName(resourceBundle.getString("RECIPIENT_LAST_NAME"));
        filerDemographicDto.setRecepientSuffixName(resourceBundle.getString("RECIPIENT_SUFFIX_NAME"));
        filerDemographicDto.setRecipientSsn(resourceBundle.getString("RECIPIENT_SSN"));
        filerDemographicDto.setRecipientTin(resourceBundle.getString("RECIPIENT_TIN"));
        filerDemographicDto.setRecipientDobStr(resourceBundle.getString("RECIPIENT_DOB"));
        filerDemographicDto.setRecipientDob(BatchUtils.getDate(resourceBundle.getString("RECIPIENT_DOB")));
        filerDemographicDto.setRecipientAddressLine1(resourceBundle.getString("RECIPIENT_ADDRESS_LINE_1"));
        filerDemographicDto.setRecipientAddressLine2(resourceBundle.getString("RECIPIENT_ADDRESS_LINE_2"));
        filerDemographicDto.setRecipientCity(resourceBundle.getString("RECIPIENT_CITY"));
        filerDemographicDto.setRecipientState(resourceBundle.getString("RECIPIENT_STATE"));
        filerDemographicDto.setRecipientZip4(resourceBundle.getString("RECIPIENT_ZIP_4"));
        filerDemographicDto.setRecipientZip5(resourceBundle.getString("RECIPIENT_ZIP_5"));
        filerDemographicDto.setPolicyOrigin(resourceBundle.getString("POLICY_ORIGIN"));
        filerDemographicDto.setFilerStatus(resourceBundle.getString("FILER_STATUS"));
        /** Add filer coverage if filer has it.  Filer status 'N' means 'not covered' */
        FilerCoverageDto filerCoverage = new FilerCoverageDto();
        filerCoverage.setId(demographicPK);
        filerCoverage.setRecipientCaseApplicationId(resourceBundle.getString("RECIPIENT_CASE_APPLICATION_ID"));
        filerCoverage.setProgramName(resourceBundle.getString("POLICY_PROGRAM_NAME"));
        final String coverageBeginDateStr = resourceBundle.getString("POLICY_COVERAGE_BEGIN_DT");
        filerCoverage.setOrigCoverageBeginDtStr(coverageBeginDateStr);
        java.util.Date coverageBeginDate = BatchUtils.getDate(coverageBeginDateStr);
        filerCoverage.setOrigCoverageBeginDt(coverageBeginDate);
        final String coverageEndDateStr = resourceBundle.getString("POLICY_COVERAGE_END_DT");
        filerCoverage.setOrigCoverageEndDtStr(coverageEndDateStr);
        java.util.Date coverageEndDate = BatchUtils.getDate(coverageEndDateStr);
        filerCoverage.setOrigCoverageEndDt(coverageEndDate);
        if (filerCoverage.getOrigCoverageBeginDt() != null && filerCoverage.getOrigCoverageEndDt() != null) {
            this.getCoverageMonths(Integer.valueOf(resourceBundle.getString("TAX_YEAR")), filerCoverage);
        }
        filerCoverage.setUpdatedBy("System");
        filerCoverage.setUpdatedDt(new java.util.Date().toString());
        filerDemographicDto.updateCoverageMonths(filerCoverage);
        filerDemographicDto.addFilerCoverage(filerCoverage);
        filerDemographicDto.setActive(!"N".equals(filerDemographicDto.getFilerStatus()));  // record with no coverage is set as inactive
        filerDemographicDto.setShopIdentifier(resourceBundle.getString("SHOP_IDENTIFIER"));
        filerDemographicDto.setEmployerName(resourceBundle.getString("EMPLOYER_NAME"));
        filerDemographicDto.setEmployerIdentificationNumber(resourceBundle.getString("EMPLOYER_IDENTIFICATION_NUMBER"));
        filerDemographicDto.setEmployerAddressLine1(resourceBundle.getString("EMPLOYER_ADDRESS_LINE_1"));
        filerDemographicDto.setEmployerAddressLine2(resourceBundle.getString("EMPLOYER_ADDRESS_LINE_2"));
        filerDemographicDto.setEmployerCityOrTown(resourceBundle.getString("EMPLOYER_CITY_OR_TOWN"));
        filerDemographicDto.setEmployerStateOrProvince(resourceBundle.getString("EMPLOYER_STATE_OR_PROVINCE"));
        filerDemographicDto.setEmployerCountry(resourceBundle.getString("EMPLOYER_COUNTRY"));
        filerDemographicDto.setZipOrPostalCode(resourceBundle.getString("EMPLOYER_ZIP_OR_POSTAL_CODE"));
        filerDemographicDto.setEmployerContactNo(resourceBundle.getString("EMPLOYER_CONTACT_NO"));
        filerDemographicDto.setProviderName(resourceBundle.getString("PROVIDER_NAME"));
        final String providerIdentificationNumber = StringUtils.trimToEmpty(resourceBundle.getString("PROVIDER_IDENTIFICATION_NUMBER")).replace("-", "");
        filerDemographicDto.setProviderIdentificationNumber(providerIdentificationNumber);
        filerDemographicDto.setProviderContactNo(resourceBundle.getString("PROVIDER_CONTACT_NO"));
        filerDemographicDto.setProviderAddressLine1(resourceBundle.getString("PROVIDER_ADDRESS_LINE_1"));
        filerDemographicDto.setProviderAddressLine2(resourceBundle.getString("PROVIDER_ADDRESS_LINE_2"));
        filerDemographicDto.setProviderCityOrTown(resourceBundle.getString("PROVIDER_CITY_OR_TOWN"));
        filerDemographicDto.setProviderStateOrProvince(resourceBundle.getString("PROVIDER_STATE_OR_PROVINCE"));
        filerDemographicDto.setProviderCountry(resourceBundle.getString("PROVIDER_COUNTRY"));
        filerDemographicDto.setProviderZipOrPostalCode(resourceBundle.getString("PROVIDER_ZIP_OR_POSTAL_CODE"));
        filerDemographicDto.setResponsiblePersonUniqueId(resourceBundle.getString("SOURCE_UNIQUE_ID"));
        filerDemographicDto.setCommunicationPreference(resourceBundle.getString("COMMUNICATION_PREFERENCE"));
        filerDemographicDto.seteMail(resourceBundle.getString("RECIPIENT_E_MAIL"));
        filerDemographicDto.setMailedForm(resourceBundle.getString("MAILED_FORM"));
        filerDemographicDto.setUpdatedBy("System");
        filerDemographicDto.setUpdatedDt(new java.util.Date().toString());
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

    @Test
    public void testMinimal() {
        assertThat("FilerDemographicDto is null", filerDemographicDto, notNullValue());
        assertThat("Filer Demographic Dto not initialized", filerDemographicDto.getId().getSourceUniqueId(), notNullValue());
    }

    @Test @Ignore("Needs some ground work. Ignoring it for now")
    public void testProcess() {
        try {
            filerDemographicStagingEntity = step2VerifyFilerProcessor.process(filerDemographicDto);
            assertThat("Staging entity is null", filerDemographicStagingEntity, notNullValue());
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Error while testing Step2 processor..");
                e.printStackTrace();
            }
        }
    }

    protected void assertFilerDemographicDto(FilerDemographicDto filerDemographicDto, int numErrors, String message) {
        constraintViolations = validator.validate(filerDemographicDto);
        MatcherAssert.assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(numErrors));
        MatcherAssert.assertThat("Invalid or wrong error message", getConstraintViolations(constraintViolations), containsString(message));
    }

    protected String getConstraintViolations(Set<ConstraintViolation<FilerDemographicDto>> constraintViolations) {

        StringBuffer constraintViolationsStringBuffer = new StringBuffer();
        for (ConstraintViolation<FilerDemographicDto> violation : constraintViolations) {
            constraintViolationsStringBuffer.append(violation.getMessageTemplate() + ":" + violation.getMessage() + "\n");
        }

        return constraintViolationsStringBuffer.toString();
    }

    protected void assertFilerCoverageDto(FilerCoverageDto filerCoverageDto, int numErrors, String message) {
        coverageConstraintViolations = validator.validate(filerCoverageDto);
        MatcherAssert.assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(numErrors));
        MatcherAssert.assertThat("Invalid or wrong error message", getCoverageConstraintViolations(coverageConstraintViolations), containsString(message));
    }

    protected String getCoverageConstraintViolations(Set<ConstraintViolation<FilerCoverageDto>> coverageConstraintViolations) {

        StringBuffer constraintViolationsStringBuffer = new StringBuffer();
        for (ConstraintViolation<FilerCoverageDto> violation : coverageConstraintViolations) {
            constraintViolationsStringBuffer.append(violation.getMessageTemplate() + ":" + violation.getMessage() + "\n");
        }

        return constraintViolationsStringBuffer.toString();
    }

    @After
    public void tearDown() throws Exception {
    }
}