package us.deloitteinnovation.aca.batch.ingest.step2;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import us.deloitteinnovation.aca.batch.constants.FileIngestionConstants;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.ExceptionReportDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.service.ExceptionReportService;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;
import us.deloitteinnovation.aca.entity.FilerDemographicStagingEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static us.deloitteinnovation.aca.batch.utils.BatchUtils.*;
import static us.deloitteinnovation.aca.constants.CommonEntityConstants.ALL_NINES_DATE_FLAG;
import static us.deloitteinnovation.aca.constants.CommonEntityConstants.ALL_SIXES_DATE_FLAG;

/**
 * <p>
 * Processor class to process individual filer demographic records.
 * </p>
 */
public class Step2VerifyFilerProcessor implements ItemProcessor<FilerDemographicDto, FilerDemographicStagingEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Step2VerifyFilerProcessor.class);

    @Autowired
    BatchInfoDto batchInfoDto;
    @Autowired
    Validator validator;
    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService;
    @Autowired
    ExceptionReportService exceptionReportService;
    @Autowired
    Environment env;
    @Value("#{jobParameters['YEAR']}")
    String taxYearAsBatchArg;
    // TODO: Can we read the job parameter as @Value("#{jobParameters[FileIngestionConstants.BATCH_ARG_TAX_YEAR]}")? Do not want to hard code it here.
    /**
     * <p>
     * Process a filer demographic record returned by ItemWriter
     * If there are any business exceptions in a record then they are
     * added to a set of {@link ExceptionReportDto}'s .
     * If there are no exceptions, then the filer demographic record is returned,
     * null is returned otherwise
     * </p>
     *
     * @param filerDemographicDto Individual filer demographic record to process
     * @return {@link FilerDemographicStagingEntity}
     * @throws Exception
     */
    @Override
    public FilerDemographicStagingEntity process(FilerDemographicDto filerDemographicDto) throws Exception {
        try {
            boolean hasExceptions = false;
            boolean hasWarnings = false;
            Set<ExceptionReportDto> exceptionReportDtoSet = new HashSet();
            filerDemographicDto.getId().setSourceCd(this.batchInfoDto.getSourceCode());
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Processing RID {}",
                        filerDemographicDto.getId().getSourceUniqueId());

            }
            // Verify RID data
            BindingResult ridDataViolations = bindAndValidate(filerDemographicDto.getId(), validator);
            if (ridDataViolations.hasErrors()) {
                hasExceptions = true;
                for (ObjectError error : ridDataViolations.getAllErrors()) {
                    ExceptionReportDto exceptionReport = constructExceptionReport(error, filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }
            // Verify for all zero's in RID
            String recipientUniqueId = filerDemographicDto.getId().getSourceUniqueId();
            if (StringUtils.isNotBlank(recipientUniqueId)) {
                if (recipientUniqueId.matches("^[0]+$")) {
                    hasExceptions = true;
                    ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("rid.content.all.zeros.error"), filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }
            // Verify coverage details like case/application id etc.
            BindingResult coverageDtoValidations = bindAndValidate((filerDemographicDto.getFilerCoverage()), validator);
            if (coverageDtoValidations.hasErrors()) {
                hasExceptions = true;
                for (ObjectError error : coverageDtoValidations.getAllErrors()) {
                    ExceptionReportDto exceptionReport = constructExceptionReport(error, filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }
            // Verify filer demographics details.
            BindingResult validationResult = bindAndValidate(filerDemographicDto, validator);
            if (validationResult.hasErrors()) {
                hasExceptions = true;
                for (ObjectError error : validationResult.getAllErrors()) {
                    ExceptionReportDto exceptionReport = constructExceptionReport(error, filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }
            // Check if SSN has sequential numbers in it.
            if (isSequentialNumbers(filerDemographicDto.getRecipientSsn())) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.ssn.sequential.numbers.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // Check if SSN has repeating numbers in it
            if (isConsecutiveNumbers(filerDemographicDto.getRecipientSsn())) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.ssn.consecutive.numbers.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // Check if TIN has sequential numbers in it.
            if (isSequentialNumbers(filerDemographicDto.getRecipientTin())) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.tin.sequential.numbers.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // Check if TIN has repeating numbers in it
            if (isConsecutiveNumbers(filerDemographicDto.getRecipientTin())) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.tin.consecutive.numbers.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // Check if the tax year matches with the tax year mentioned in the job parameter
            String taxYearFromDto = filerDemographicDto.getId().getTaxYear();
            if (!taxYearAsBatchArg.equals(taxYearFromDto)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("tax.year.mismatch.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // DOB data check. See sec 3.1.1  row 11 of ICD.
            Date recipientDob = filerDemographicDto.getRecipientDob();
            Calendar dobCalendar = Calendar.getInstance();
            if (recipientDob != null) {
                dobCalendar.setTime(recipientDob);
                Calendar taxYearCalendar = Calendar.getInstance();
                taxYearCalendar.set(Calendar.YEAR, Integer.parseInt(taxYearAsBatchArg));
                taxYearCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
                taxYearCalendar.set(Calendar.DAY_OF_MONTH, 31); // Last day of December


                if (!(dobCalendar.before(taxYearCalendar) || dobCalendar.equals(taxYearCalendar))) {
                    hasExceptions = true;
                    ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.dob.date.comparison.error"), filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
                // DOB should not be greater than 120 years prior to tax year in batch argument.
                taxYearCalendar.clear();
                taxYearCalendar.setTime(recipientDob);
                taxYearCalendar.set(Calendar.YEAR, Integer.parseInt(taxYearAsBatchArg) - 120);
                taxYearCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
                taxYearCalendar.set(Calendar.DAY_OF_MONTH, 31); // Last day of December
                if ((dobCalendar.before(taxYearCalendar))) {
                    hasExceptions = true;
                    ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.dob.date.comparison.error"), filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }
            // Check for all zero's in Zip5
            String recipientZip5 = filerDemographicDto.getRecepientZip5();
            if (StringUtils.isNotBlank(recipientZip5)) {
                if (recipientZip5.matches("^[0]+$")) {
                    hasExceptions = true;
                    ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.zip5.allzeros.error"), filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }
            // Check for repeated allowed special characters in Recipient Address line 1
            String recipientAddressLine1 = filerDemographicDto.getRecipientAddressLine1();
            if (StringUtils.isNotBlank(recipientAddressLine1)) {
                if (containsRepeatedAllowedSpecialChars(recipientAddressLine1)) {
                    hasExceptions = true;
                    ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.address.line1.allowed.spchar.error"), filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }
            // Check to see if has allowed special characters
            if (StringUtils.isNotBlank(recipientAddressLine1)) {
                if (!containsAllowedSpecialCharacters(recipientAddressLine1)) {
                    hasExceptions = true;
                    ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.address.line1.allowed.spchar.error"), filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }
            // Check for repeated allowed special characters in recipient Address line 2
            String recipientAddressLine2 = filerDemographicDto.getRecipientAddressLine2();
            if (containsRepeatedAllowedSpecialChars(recipientAddressLine2)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.address.line2.allowed.spchar.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // Check to see if has allowed special characters in recipient address line 2
            if (!containsAllowedSpecialCharacters(recipientAddressLine2)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.address.line2.allowed.spchar.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // Issue warning for recipient address line 2
            if ((StringUtils.isBlank(recipientAddressLine1) || recipientAddressLine1.trim().length() <= 3) && StringUtils.isBlank(recipientAddressLine2)) {
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("recipient.address.line2.warning"), filerDemographicDto);
                exceptionReportService.save(exceptionReport);
            }
            // Check for repeated allowed special characters for provider address line 1
            String providerAddressLine1 = filerDemographicDto.getProviderAddressLine1();
            if (containsRepeatedAllowedSpecialChars(providerAddressLine1)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("provider.address.line1.allowed.spchar.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // Check to see if has allowed special characters in provider address line 1
            if (!containsAllowedSpecialCharacters(providerAddressLine1)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("provider.address.line1.allowed.spchar.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // Check for repeated allowed special characters for provider address line 2
            String providerAddressLine2 = filerDemographicDto.getProviderAddressLine2();
            if (containsRepeatedAllowedSpecialChars(providerAddressLine2)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("provider.address.line2.allowed.spchar.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // Check to see if has allowed special characters in provider address line 2
            if (!containsAllowedSpecialCharacters(providerAddressLine2)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("provider.address.line2.allowed.spchar.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }

            // Issue warning for provider address line 2
            if ((StringUtils.isBlank(providerAddressLine1) || providerAddressLine1.trim().length() <= 3) && StringUtils.isBlank(providerAddressLine2)) {
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("provider.address.line2.warning"), filerDemographicDto);
                exceptionReportService.save(exceptionReport);
            }
            // Check allowed special characters for provider city or town
            if (!containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown())) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("provider.city.or.town.allowed.spchar.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }

            // ICD ER22.2. Checks for all 6's or all 9's in coverage begin date
            String correctionCode = filerDemographicDto.getCorrection();
            String originalCoverageBeginDt = filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDtStr();
            if (!isCoverageDateValid(correctionCode, originalCoverageBeginDt)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("original.coverage.begin.date.value.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // ICD ER22.3. Checks for the coverage begin date year
            String taxYear = filerDemographicDto.getId().getTaxYear();
            Date coverageBeginDate = filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt();
            boolean allSixAllNineFlagForCoverageBeginDt = ALL_SIXES_DATE_FLAG.equals(originalCoverageBeginDt) || ALL_NINES_DATE_FLAG.equals(originalCoverageBeginDt);
            if (!allSixAllNineFlagForCoverageBeginDt
                    && !isCoverageTaxYearValid(taxYear, coverageBeginDate)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("original.coverage.begin.date.tax.year.value.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // ICD ER22.4. Checks for coverage month and DOB month
            if (!allSixAllNineFlagForCoverageBeginDt
                    && !isCoverageMonthValid(coverageBeginDate, recipientDob)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("original.coverage.begin.date.month.value.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // ICD ER23.2. Checks for all 6's or all 9's in coverage begin date
            String originalCoverageEndDt = filerDemographicDto.getFilerCoverage().getOrigCoverageEndDtStr();
            if (!isCoverageDateValid(correctionCode, originalCoverageEndDt)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("original.coverage.end.date.value.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }

            // ICD ER23.4. Checks for the coverage begin date year
            Date coverageEndDate = filerDemographicDto.getFilerCoverage().getOrigCoverageEndDt();
            boolean allSixAllNineFlagForCoverageEndDt = ALL_SIXES_DATE_FLAG.equals(originalCoverageEndDt) || ALL_NINES_DATE_FLAG.equals(originalCoverageEndDt);
            if (!allSixAllNineFlagForCoverageEndDt
                    && !isCoverageTaxYearValid(taxYear, coverageEndDate)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("original.coverage.end.date.tax.year.value.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // ICD ER23.3. Checks if begin date is before end date
            if (!allSixAllNineFlagForCoverageBeginDt &&
                    !allSixAllNineFlagForCoverageEndDt &&
                    !isBeginDateBeforeEndDate(coverageBeginDate, coverageEndDate)) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("original.coverage.end.date.before.begin.date.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // ICD ER23.5 Check if coverage begin an end date are equal if they are all 6's or all 9's
            if (ALL_NINES_DATE_FLAG.equals(originalCoverageBeginDt) ||
                    ALL_NINES_DATE_FLAG.equals(originalCoverageEndDt)) {
                if (originalCoverageBeginDt != null && !originalCoverageBeginDt.equals(originalCoverageEndDt)) {
                    hasExceptions = true;
                    ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("original.coverage.date.nines.mismatch"), filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }
            // ICD ER23.6 Check if coverage begin an end date are equal if they are all 6's or all 9's
            if (ALL_SIXES_DATE_FLAG.equals(originalCoverageBeginDt) ||
                    ALL_SIXES_DATE_FLAG.equals(originalCoverageEndDt)) {
                if (originalCoverageBeginDt != null && !originalCoverageBeginDt.equals(originalCoverageEndDt)) {
                    hasExceptions = true;
                    ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("original.coverage.date.sixes.mismatch"), filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }

            Pattern pattern = Pattern.compile("[0-9]{0,15}");
            if (filerDemographicDto.getFilerStatus() != null && filerDemographicDto.getFilerStatus().equalsIgnoreCase("C")) {
                if (filerDemographicDto.getResponsiblePersonUniqueId() == null || (filerDemographicDto.getResponsiblePersonUniqueId() != null && filerDemographicDto.getResponsiblePersonUniqueId().equals("0"))) {
                    hasExceptions = true;
                    ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("responsible.person.unique.id.value.required.when.filer.status.is.C.error"), filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }
            if (filerDemographicDto.getFilerStatus() != null && filerDemographicDto.getFilerStatus().equalsIgnoreCase("R")) {
                if (filerDemographicDto.getResponsiblePersonUniqueId() != null && filerDemographicDto.getId().getSourceUniqueId() != null && pattern.matcher(filerDemographicDto.getResponsiblePersonUniqueId()).matches() &&
                        filerDemographicDto.getResponsiblePersonUniqueId().compareTo(Long.toString(NumberUtils.toLong(StringUtils.trimToNull(filerDemographicDto.getId().getSourceUniqueId())))) != 0) {
                    hasExceptions = true;
                    ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("responsible.person.uniqueid.recipient.uniqueid.mismatch.error"), filerDemographicDto);
                    exceptionReportDtoSet.add(exceptionReport);
                }
            }

        /*
        * ICD ER44.4 Reject record if the filer status is 'C' and
        * Responsible_person_unique_id and source_unique_id are same.
        */
            String filerStatus = filerDemographicDto.getFilerStatus();
            if (FileIngestionConstants.FILER_STATUS_COVERED_PERSON.equalsIgnoreCase(filerStatus)) { //Ignoring case because as per ACAB-1638 filer status can be in lower/upper case.
                String sourceUniqueId = filerDemographicDto.getId().getSourceUniqueId();
                String responsibleUniqueId = filerDemographicDto.getResponsiblePersonUniqueId();
                if (StringUtils.isNotBlank(sourceUniqueId) && StringUtils.isNotBlank(responsibleUniqueId)) {
                    if (sourceUniqueId.equals(responsibleUniqueId)) {
                        hasExceptions = true;
                        ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("filer.status.c.responsible.person.error"), filerDemographicDto);
                        exceptionReportDtoSet.add(exceptionReport);
                    } else {
                        Long lSourceUniqueId = NumberUtils.toLong(sourceUniqueId);
                        Long lResponsibleUniqueId = NumberUtils.toLong(responsibleUniqueId);
                        if (lSourceUniqueId == lResponsibleUniqueId && lResponsibleUniqueId != 0l & lSourceUniqueId != 0l) {
                            hasExceptions = true;
                            ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("filer.status.c.responsible.person.error"), filerDemographicDto);
                            exceptionReportDtoSet.add(exceptionReport);
                        }
                    }
                }
            }

            if (filerDemographicDto.getEmployerCityOrTown() != null || (filerDemographicDto.getEmployerCityOrTown() != null && filerDemographicDto.getEmployerCityOrTown().equalsIgnoreCase(" "))) {
                hasExceptions = true;
                ExceptionReportDto exceptionReport = constructExceptionReport(env.getProperty("employer.city.value.not.required.error"), filerDemographicDto);
                exceptionReportDtoSet.add(exceptionReport);
            }
            // If there are no exceptions, process the filer demographics record and insert into staging
            if (!hasExceptions) {
                // Assign to the entity.
                FilerDemographicStagingEntity filerDemographicStagingEntity = getStagingEntityFromFilerDemographics(filerDemographicDto);
                filerDemographicStagingEntity.setSourceUniqueId(Long.parseLong(filerDemographicDto.getId().getSourceUniqueId()));
                filerDemographicStagingEntity.setSourceCd(filerDemographicDto.getId().getSourceCd());
                filerDemographicStagingEntity.setRecordSource(FileIngestionConstants.RECORD_SOURCE_FILE);
                filerDemographicStagingEntity.setRowNumber(filerDemographicDto.getLineNumber() - FileIngestionConstants.LINES_TO_SKIP);
                filerDemographicStagingEntity.setFilerStatus(filerDemographicDto.getFilerStatus().toUpperCase());
                filerDemographicStagingEntity.setCommunicationPreference(filerDemographicDto.getCommunicationPreference().toUpperCase().charAt(0));
                filerDemographicStagingEntity.setMailedForm(filerDemographicDto.getMailedForm().toUpperCase());
                filerDemographicStagingEntity.setTaxYear(Integer.parseInt(filerDemographicDto.getId().getTaxYear()));
                filerDemographicStagingEntity.setOriginalCoverageBeginDt(BatchUtils.getDate(filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDtStr()));
                filerDemographicStagingEntity.setOriginalCoverageEndDt(BatchUtils.getDate(filerDemographicDto.getFilerCoverage().getOrigCoverageEndDtStr()));
                filerDemographicStagingEntity.setBatchId(this.batchInfoDto.getBatchId());
                filerDemographicStagingEntity.setUpdatedDt(new Date());
                filerDemographicStagingEntity.setStatus(filerDemographicStagingEntity.isActive() ? FileIngestionConstants.STATUS_ACTIVE : FileIngestionConstants.STATUS_INACTIVE);
                filerDemographicStagingEntity.setCaseApplicationId(filerDemographicDto.getFilerCoverage().getRecipientCaseApplicationId());
                if (filerDemographicDto.getResponsiblePersonUniqueId() != null) {
                    filerDemographicStagingEntity.setResponsiblePersonUniqueId(Long.valueOf(filerDemographicDto.getResponsiblePersonUniqueId()));
                }
                filerDemographicStagingEntity.setProviderContactNo(NumberUtils.toLong(filerDemographicDto.getProviderContactNo()));
                filerDemographicStagingEntity.setProgramName(filerDemographicDto.getFilerCoverage().getProgramName());

                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Processed RID {} at line#: {}",
                            filerDemographicDto.getId().getSourceUniqueId(),
                            filerDemographicDto.getLineNumber() - FileIngestionConstants.LINES_TO_SKIP);
                }
                return filerDemographicStagingEntity;
            } else { // If there are exceptions, then filter out the item and log the exceptions
                for (ExceptionReportDto exceptionReportDto : exceptionReportDtoSet) {
                    exceptionReportService.save(exceptionReportDto);
                }
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn("Filtering out RID {} at line#: {}",
                            filerDemographicDto.getId().getSourceUniqueId(),
                            filerDemographicDto.getLineNumber() - FileIngestionConstants.LINES_TO_SKIP);
                }
                return null;
            }
        } catch (Exception e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Filtering out RID {} at line#: {}",
                        filerDemographicDto.getId().getSourceUniqueId(),
                        filerDemographicDto.getLineNumber() - FileIngestionConstants.LINES_TO_SKIP);
                LOGGER.warn("Reason: " + e.getLocalizedMessage());
            }
            String message = env.getProperty("faulty.line.error");
            ExceptionReportDto exceptionReportDto = new ExceptionReportDto();
            exceptionReportDto.setExDetails(message);
            exceptionReportDto.setRowNumber(filerDemographicDto.getLineNumber() - FileIngestionConstants.LINES_TO_SKIP);
            exceptionReportDto.setSourceUniqueId(Long.valueOf(filerDemographicDto.getId().getSourceUniqueId()));
            exceptionReportDto.setBatchInfo(this.batchInfoDto);
            exceptionReportService.save(exceptionReportDto);
            return null;
        }
    }

    /* Method to create and return an ExceptionReportDto.
    *  This dto is populated with details of batch info and
    *  exception message
    * */
    private ExceptionReportDto constructExceptionReport(Object object, FilerDemographicDto filerDemographicDto) {
        String ridString = filerDemographicDto.getId().getSourceUniqueId();
        Long sourceUniqueId = StringUtils.isBlank(ridString) ? 0L : NumberUtils.toLong(ridString);
        ExceptionReportDto exceptionReport = new ExceptionReportDto();
        exceptionReport.setSourceUniqueId(sourceUniqueId);
        exceptionReport.setBatchInfo(this.batchInfoDto);
        if (object instanceof ObjectError) {
            ObjectError error = (ObjectError) object;
            exceptionReport.setExDetails(error.getDefaultMessage());
        } else if (object instanceof String) {
            String envKeyValue = (String) object;
            exceptionReport.setExDetails(envKeyValue);
        }
        exceptionReport.setRowNumber(filerDemographicDto.getLineNumber() - FileIngestionConstants.LINES_TO_SKIP);
        return exceptionReport;
    }

    /* Method to return a FilerDemographicStagingEntity with
    *  with values of matching properties copied from FilerDemographic dto,
    *  which this processor is processing
    * */
    private FilerDemographicStagingEntity getStagingEntityFromFilerDemographics(FilerDemographicDto fdDto) {
        FilerDemographicStagingEntity filerDemographicStagingEntity = new FilerDemographicStagingEntity();
        BeanUtils.copyProperties(fdDto, filerDemographicStagingEntity);
        return filerDemographicStagingEntity;
    }
}

