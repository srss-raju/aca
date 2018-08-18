package us.deloitteinnovation.aca.batch.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.constants.FileIngestionConstants;
import us.deloitteinnovation.aca.batch.constants.Months;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.profile.ProfileProperties;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static us.deloitteinnovation.aca.batch.dto.FilerDemographicDto.FilerDemographicCacheDto;
import static us.deloitteinnovation.aca.constants.CommonEntityConstants.*;

/**
 * Created by rgopalani on 9/27/2015.
 */
public class BatchUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchUtils.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
    private static final SimpleDateFormat MMDDYYYY_DATE_FORMAT = new SimpleDateFormat("MMddyyyy");
    private static Map<String, String> sixesNinesDateMap = new HashMap<>();

    static {
        sixesNinesDateMap.put(ALL_SIXES_DATE_FLAG, CONVERTED_ALL_SIXES_DATE_FLAG);
        sixesNinesDateMap.put(ALL_NINES_DATE_FLAG, CONVERTED_ALL_NINES_DATE_FLAG);
    }

    /**
     * @param dateInString
     * @return Date
     */
    public static Date getDate(final String dateInString) {
        try {
            MMDDYYYY_DATE_FORMAT.setLenient(false);
            // If we get all 6's and all 9's convert them into valid date strings
            if (ALL_SIXES_DATE_FLAG.equals(dateInString) || ALL_NINES_DATE_FLAG.equals(dateInString)) {
                return MMDDYYYY_DATE_FORMAT.parse(sixesNinesDateMap.get(dateInString));
            }
            if (dateInString.trim().length() != 8) {
                return null;
            } else {
                return MMDDYYYY_DATE_FORMAT.parse(dateInString);
            }
        } catch (final ParseException ex) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(ex.getLocalizedMessage());
            }
            return null;
        } catch (Exception ex) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(ex.getLocalizedMessage());
            }
            return null;
        }
    }

    /**
     * @param fieldSet
     * @param field
     * @param dateFormatRegex
     * @return
     */
    public static boolean validateDate(FieldSet fieldSet, String field, String dateFormatRegex) {
        final String dateStr = StringUtils.trimToNull(fieldSet.readString(field));
        return dateStr != null
                && !ALL_NINES_DATE_FLAG.equals(dateStr)
                && dateStr.matches(dateFormatRegex);
    }

    /**
     * @param fieldSet
     * @param field
     * @return
     */
    public static boolean isEmpty(FieldSet fieldSet, String field) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Start of BatchUtils.isEmpty");
        }
        if (fieldSet.readString(field) != null
                && !StringUtils.isEmpty(fieldSet.readString(field))) {
            return true;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("End of BatchUtils.isEmpty");
        }
        return false;
    }

    /**
     * @param fieldSet
     * @param field
     * @return
     */
    public static boolean isNumber(FieldSet fieldSet, String field) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Start of BatchUtils.isNumber");
        }
        if (fieldSet.readString(field) != null
                && !fieldSet.readString(field).equals("")
                && NumberUtils.isNumber(fieldSet.readString(field))) {
            return true;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("End of BatchUtils.isNumber");
        }
        return false;
    }

    /**
     * @param fieldSet
     * @param field
     * @return
     */
    public static boolean isSingleCharacter(FieldSet fieldSet, String field) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Start of BatchUtils.isSingleCharacter");
        }
        if (fieldSet.readString(field) != null
                && !StringUtils.isEmpty(fieldSet.readString(field))
                && fieldSet.readString(field).length() == 1) {
            return true;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("End of BatchUtils.isSingleCharacter");
        }
        return false;
    }

    /**
     * @param item
     * @param validator
     * @return
     */
    public static BindingResult bindAndValidate(Object item, Validator validator) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Start of BatchUtils.bindAndValidate");
        }
        DataBinder binder = new DataBinder(item);
        binder.setValidator(validator);
        binder.validate();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("End of BatchUtils.bindAndValidate");
        }
        return binder.getBindingResult();
    }

    public static String buildExceptionMessage(final String message) {
        return String.format("%s %s %s",
                BatchConstants.NEW_LINE, message, BatchConstants.NEW_LINE);
    }

    /**
     * @param results
     * @return
     */
    public static String buildValidationException(BindingResult results) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Start of BatchUtils.buildValidationException");
        }
        StringBuffer msg = new StringBuffer();
        Set<String> uniqueErrors = new LinkedHashSet<>();
        for (ObjectError error : results.getAllErrors()) {
            uniqueErrors.add(error.getDefaultMessage());
        }

        for (String error : uniqueErrors) {
            addErrorLine(error, msg);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("End of BatchUtils.buildValidationException");
        }
        return msg.toString();
    }

    public static void addErrorLine(final String errorMessage, final StringBuffer sb) {
        sb.append(String.format("-*-*-*- %s -*-*-*-\n", errorMessage));
    }

    /**
     * @param index
     * @param preparedStatement
     * @param item
     * @throws SQLException
     */
    public static void setString(int index, PreparedStatement preparedStatement, Object item) throws SQLException {
        if (item != null) {
            preparedStatement.setString(index, item.toString());
        } else {
            preparedStatement.setNull(index, Types.VARCHAR);
        }
    }

    /**
     * @param index
     * @param preparedStatement
     * @param item
     * @throws SQLException
     */
    public static void setMonth(int index, PreparedStatement preparedStatement, String item) throws SQLException {
        if (item != null && !StringUtils.isEmpty(item)) {
            preparedStatement.setString(index, item);
        } else {
            preparedStatement.setString(index, BatchConstants.NO);
        }
    }

    /**
     * @param index
     * @param preparedStatement
     * @param item
     * @throws SQLException
     */
    public static void setDate(int index, PreparedStatement preparedStatement, Date item) throws SQLException {
        if (item != null) {
            preparedStatement.setDate(index, new java.sql.Date(item.getTime()));
        } else {
            preparedStatement.setNull(index, Types.DATE);
        }
    }

    public static void setCurrentDate(int index, PreparedStatement ps) throws SQLException {
        ps.setDate(index, new java.sql.Date(System.currentTimeMillis()));
    }

    public static final int getCoverageMonth(Date date) {
        Format formatter = new SimpleDateFormat("MM");
        String s = formatter.format(date);
        return Integer.parseInt(s);
    }

    public static final int getCoverageYear(Date date) {
        Format formatter = new SimpleDateFormat("YYYY");
        String s = formatter.format(date);
        return Integer.parseInt(s);
    }

    public static void setMonth(FilerCoverageDto filerCoverageDto, int startCoverageMonth, int endCoverageMonth) {
        for (int i = startCoverageMonth; i <= endCoverageMonth; i++) {
            String month = Months.byOrdinal(i).toString();
            switch (month) {
                case "JAN":
                    filerCoverageDto.setJan(BatchConstants.YES);
                    break;

                case "FEB":
                    filerCoverageDto.setFeb(BatchConstants.YES);
                    break;

                case "MAR":
                    filerCoverageDto.setMar(BatchConstants.YES);
                    break;
                case "APR":
                    filerCoverageDto.setApr(BatchConstants.YES);
                    break;

                case "MAY":
                    filerCoverageDto.setMay(BatchConstants.YES);
                    break;

                case "JUN":
                    filerCoverageDto.setJun(BatchConstants.YES);
                    break;
                case "JUL":
                    filerCoverageDto.setJul(BatchConstants.YES);
                    break;

                case "AUG":
                    filerCoverageDto.setAug(BatchConstants.YES);
                    break;

                case "SEP":
                    filerCoverageDto.setSep(BatchConstants.YES);
                    break;
                case "OCT":
                    filerCoverageDto.setOct(BatchConstants.YES);
                    break;

                case "NOV":
                    filerCoverageDto.setNov(BatchConstants.YES);
                    break;

                case "DEC":
                    filerCoverageDto.setDec(BatchConstants.YES);
                    break;
                default:
                    LOGGER.error("No Month Found");

            }
        }
    }

    /**
     * @param index
     * @param map
     * @param item
     * @throws SQLException
     */
    public static void setString(String index, Map<String, Object> map, Object item) {
        if (item != null) {
            map.put(index, item.toString());
        } else {
            map.put(index, "");
        }


    }

    /**
     * @param index
     * @param map
     * @param item
     * @throws SQLException
     */
    public static void setStringWithNull(String index, Map<String, Object> map, Object item) {
        if (item != null) {
            map.put(index, item.toString());
        } else {
            map.put(index, null);
        }


    }

    /**
     * @param index
     * @param map
     * @param item
     * @throws SQLException
     */
    public static void setMonth(String index, Map map, String item) {
        if (item != null && !StringUtils.isEmpty(item)) {
            map.put(index, item);
        } else {
            map.put(index, BatchConstants.NO);
        }
    }

    /**
     * @param index
     * @param map
     * @param item
     * @throws SQLException
     */
    public static void setStringWithoutEmpty(String index, Map map, String item) {
        if (StringUtils.isNotBlank(item)) {
            map.put(index, item);
        } else {
            map.put(index, Types.VARCHAR);
        }
    }

    /**
     * @param index
     * @param map
     * @param item
     * @throws SQLException
     */
    public static void setDate(String index, Map map, Date item) {
        if (item != null) {
            map.put(index, new java.sql.Date(item.getTime()));
        } else {
            map.put(index, Types.DATE);
        }
    }

    /**
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isValidCoverageDateRange(Date startDate, Date endDate) {
        // false if either value is null
        boolean isEqualOrGreater = true;
        if (startDate == null || endDate == null) {
            isEqualOrGreater = false;
        } else if (startDate.equals(endDate)) {
            isEqualOrGreater = true;
        } else if (endDate.after(startDate)) {
            isEqualOrGreater = true;
        }
        return isEqualOrGreater;
    }

    /**
     * @param item
     * @param stringBuffer
     */
    public static void validateShopIdentifier(FilerDemographicDto item, StringBuffer stringBuffer, ProfileProperties profileProperties) {
        if (item.getId().getTaxYear() != null && item.getId().getTaxYear().equals(profileProperties.getProperty(BatchConstants.PROP_TAX_YEAR)) && item.getShopIdentifier() != null && !org.springframework.util.StringUtils.isEmpty(item.getShopIdentifier())) {
            addErrorLine(ErrorMessageConstants.TAX_YEAR_SHOP_IDENTIFIER_EMPTY, stringBuffer);
        }
    }

    /**
     * @param item
     * @param stringBuffer
     */
    public static void validateEmployerDetails(FilerDemographicDto item, StringBuffer stringBuffer) {
        if (item.getPolicyOrigin() != null && item.getPolicyOrigin().equals("C")) {
            if (StringUtils.isNotEmpty(item.getEmployerContactNo())
                    || StringUtils.isNotEmpty(item.getEmployerAddressLine1())
                    || StringUtils.isNotEmpty(item.getEmployerAddressLine2())
                    || StringUtils.isNotEmpty(item.getEmployerCityOrTown())
                    || StringUtils.isNotEmpty(item.getEmployerCountry())
                    || StringUtils.isNotEmpty(item.getEmployerIdentificationNumber())
                    || StringUtils.isNotEmpty(item.getEmployerName())
                    || StringUtils.isNotEmpty(item.getZipOrPostalCode())
                    || StringUtils.isNotEmpty(item.getEmployerStateOrProvince())) {
                addErrorLine(ErrorMessageConstants.POLICY_ORIGIN_EMPLOYER_DETAIL_EMPTY, stringBuffer);
            }
        }
    }

    /**
     * @param item
     * @param stringBuffer
     */
    public static void validateEmail(FilerDemographicDto item, StringBuffer stringBuffer) {
        if ((item.geteMail() == null || org.springframework.util.StringUtils.isEmpty(item.geteMail())) && item.getCommunicationPreference() != null && (item.getCommunicationPreference().equals(BatchConstants.COMM_PREFERENCE_EMAIL) || item.getCommunicationPreference().equals(BatchConstants.COMM_PREFERENCE_BOTH))) {
            addErrorLine(ErrorMessageConstants.EMAIL_COMM_PREFERENCE, stringBuffer);
        }
    }

    /**
     * @param item
     * @param stringBuffer
     */
    public static void validateRecipientSSNOrTIN(FilerDemographicDto item, StringBuffer stringBuffer) {
        if ((item.getRecipientSsn() == null || org.springframework.util.StringUtils.isEmpty(item.getRecipientSsn())) && (item.getRecipientTin() == null || org.springframework.util.StringUtils.isEmpty(item.getRecipientTin()))) {
            addErrorLine(ErrorMessageConstants.RECIPIENT_SSN_TIN_REQ, stringBuffer);
        }
    }

    /**
     * @param item
     * @param stringBuffer
     * @param date
     */
    public static void validateRecipientDOB(FilerDemographicDto item, StringBuffer stringBuffer, Date date) {
        if (item.getRecipientDob() != null && !BatchUtils.isValidDateRange(item.getRecipientDob(), date)) {
            addErrorLine(ErrorMessageConstants.RECIPIENT_DATE_OF_BIRTH_AFTER, stringBuffer);
        }
    }

    /**
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isValidDateRange(Date startDate, Date endDate) {
        // false if either value is null
        boolean isEqualOrGreater = true;
        if (startDate == null || endDate == null) {
            isEqualOrGreater = false;
        } else if (startDate.equals(endDate)) {
            isEqualOrGreater = true;
        } else if (endDate.after(startDate)) {
            isEqualOrGreater = true;
        } else if (startDate.after(endDate)) {
            isEqualOrGreater = false;
        }
        return isEqualOrGreater;
    }

    /**
     * @param item
     * @param stringBuffer
     */
    public static void validateTaxYear(FilerDemographicDto item, StringBuffer stringBuffer, ProfileProperties profileProperties) {
        if (item.getId().getTaxYear() != null && !profileProperties.getProperty(BatchConstants.PROP_TAX_YEAR).equals(item.getId().getTaxYear())) {
            addErrorLine(ErrorMessageConstants.TAX_YEAR_CURRENT, stringBuffer);
        }
    }

    public static void validateCoverageStartAndEndDate(final FilerDemographicDto item, final StringBuffer sb, ProfileProperties profileProperties) {
        final FilerCoverageDto filerCoverage = item.getFilerCoverages().get(0);
        if ("O".equals(item.getCorrection()) &&
                (ALL_NINES_DATE_FLAG.equals(filerCoverage.getOrigCoverageBeginDtStr())
                        || ALL_NINES_DATE_FLAG.equals(filerCoverage.getOrigCoverageEndDtStr()))) {
            addErrorLine(ErrorMessageConstants.FLAG_DATE_ONLY_FOR_UPDATE_RECORDS, sb);
        }
        if ("N".equals(item.getFilerStatus()) &&
                (!ALL_ZEROS_DATE_FLAG.equals(filerCoverage.getOrigCoverageBeginDtStr())
                        || !ALL_ZEROS_DATE_FLAG.equals(filerCoverage.getOrigCoverageEndDtStr()))) {
            addErrorLine(String.format("Record with filer status 'N' must have a "
                    + "coverage start and end date of \"%s\".", ALL_ZEROS_DATE_FLAG), sb);
        }
        /** Flag date values are not valid dates, so can't have date validation logic applied to them */
        if (!isFlagDateValue(filerCoverage.getOrigCoverageBeginDtStr())) {
            try {
                final String startDateStr = "01/01/" + profileProperties.getProperty(BatchConstants.PROP_TAX_YEAR);
                final String endDateStr = "12/31/" + profileProperties.getProperty(BatchConstants.PROP_TAX_YEAR);
                Date startDate = DATE_FORMAT.parse(startDateStr);
                Date endDate = DATE_FORMAT.parse(endDateStr);
                if (filerCoverage.getOrigCoverageBeginDt().before(startDate)
                        || filerCoverage.getOrigCoverageBeginDt().after(endDate)
                        || filerCoverage.getOrigCoverageEndDt().after(endDate)
                        || filerCoverage.getOrigCoverageEndDt().before(startDate)) {
                    addErrorLine(String.format(
                            "The coverage begin and end dates must be for the current tax year: \"%s\".",
                            item.getId().getTaxYear()), sb);
                }
            } catch (final ParseException ex) {
                LOGGER.error("Couldn't parse coverage begin/end date.", ex);
                throw new RuntimeException(ex);
            }
        }
    }

    public static boolean isFlagDateValue(final String dateStr) {
        return ALL_NINES_DATE_FLAG.equals(dateStr)
                || ALL_ZEROS_DATE_FLAG.equals(dateStr);
    }

    /**
     * @param item
     * @param stringBuffer
     */
    public static void validateFilerStatus(FilerDemographicDto item, StringBuffer stringBuffer) {
        if ((item.getFilerStatus() != null && item.getFilerStatus().equals("C") && item.getResponsiblePersonUniqueId() != null && item.getResponsiblePersonUniqueId().toString().equals(""))) {
            addErrorLine(ErrorMessageConstants.FILER_STATUS_AS_C_AND_RESPONSIBLE_PERSON_UNIQUE_ID, stringBuffer);
        }
        // RECIPIENT UNIQUE ID=RESPONSIBLE_PERSON_UNIQUE_ID and FILER STATUS = R validation is not required now after discussion with States
       /* if ((item.getFilerStatus() != null && item.getFilerStatus().equals("R") && item.getResponsiblePersonUniqueId() != null && item.getId() != null && item.getId().getSourceUniqueId() != null && !(item.getResponsiblePersonUniqueId().toString().equals(item.getId().getSourceUniqueId().toString())))) {
            stringBuffer.append(BatchConstants.NEW_LINE).append(ErrorMessageConstants.FILER_STATUS_AS_R_AND_RESPONSIBLE_PERSON_UNIQUE_ID).append(BatchConstants.NEW_LINE);
        }*/
    }

    /**
     * @param item
     * @param stringBuffer
     * @param year
     */
    public static void validateCoverageDate(FilerDemographicDto item, StringBuffer stringBuffer, int year) {
        for (FilerCoverageDto filerCoverage : item.getFilerCoverages()) {
            if (filerCoverage != null
                    && filerCoverage.getOrigCoverageBeginDt() != null
                    && filerCoverage.getOrigCoverageEndDt() != null
                    && !ALL_NINES_DATE_FLAG.equals(filerCoverage.getOrigCoverageEndDtStr())) {
                if (!BatchUtils.isValidDateRange(
                        filerCoverage.getOrigCoverageBeginDt(),
                        filerCoverage.getOrigCoverageEndDt())) {
                    addErrorLine(ErrorMessageConstants.COVERAGE_START_DATE_END_DATE_GREATER, stringBuffer);
                }
            }
        }
    }

    public static void rejectResponsiblePersonChange(
            final FilerDemographicDto item,
            final FilerDemographicCacheDto mostRecentlyCached,
            final StringBuffer sb) {
        if ("C".equals(item.getFilerStatus())
                && mostRecentlyCached != null
                && !Objects.equals(
                item.getResponsiblePersonUniqueId(),
                mostRecentlyCached.getResponsiblePersonUniqueId())) {
            addErrorLine(ErrorMessageConstants.RESP_PERSON_CHANGE_NOT_ALLOWED, sb);
        }
    }

    public static Filer convert(FilerDemographicDto dto) {
        Filer filer = new Filer();
        filer.setCorrection(dto.getCorrection());
        if (dto.getCorrectionDt() != null) {
            filer.setUpdatedDate(new java.sql.Date(dto.getCorrectionDt().getTime()));
        }
        filer.setEmployerAddressLine1(dto.getEmployerAddressLine1());
        filer.setEmployerAddressLine2(dto.getEmployerAddressLine2());
        filer.setEmployerCity(dto.getEmployerCityOrTown());
        filer.setEmployerEIN(dto.getEmployerIdentificationNumber());
        filer.setEmployerName(dto.getEmployerName());
        filer.setEmployerState(dto.getEmployerStateOrProvince());
        filer.setFilerStatus(dto.getFilerStatus());
        filer.setPolicyOrigin(dto.getPolicyOrigin());
        filer.setProviderAddLine1(dto.getProviderAddressLine1());
        filer.setProviderAddLine2(dto.getProviderAddressLine2());
        filer.setProviderCity(dto.getProviderCityOrTown());
        filer.setProviderContactNo(Long.parseLong(dto.getProviderContactNo()));
        filer.setProviderEIN(dto.getProviderIdentificationNumber());
        filer.setProviderName(dto.getProviderName());
        filer.setProviderState(dto.getProviderStateOrProvince());
        filer.setProviderZip(dto.getProviderZipOrPostalCode());
        filer.setRecipientAddLine1(dto.getRecipientAddressLine1());
        filer.setRecipientAddLine2(dto.getRecipientAddressLine2());
        filer.setRecipientCity(dto.getRecipientCity());
        filer.setRecipientDOB(dto.getRecipientDob().toString());
        filer.setRecipientFirstName(dto.getRecipientFirstName());
        filer.setRecipientLastName(dto.getRecipientLastName());
        filer.setRecipientMiddleName(dto.getRecipientMiddleName());
        filer.setRecipientSSN(dto.getRecipientSsn());
        filer.setRecipientState(dto.getRecipientState());
        filer.setRecipientSuffix(dto.getRecepientSuffixName());
        filer.setRecipientTIN(dto.getRecipientTin());
        filer.setRecipientZip4(dto.getRecepientZip4());
        filer.setRecipientZip(dto.getRecepientZip5());
        // filer.set(dto.getResponsiblePersonUniqueId());
        filer.setShopIdentifier(dto.getShopIdentifier());
        filer.setTaxYear(dto.getId().getTaxYear());
        filer.setUpdatedBy(dto.getUpdatedBy());
        //filer.setUpdatedDate(dto.getUpdatedDt());
        // TODO So..... who does this zip belong to?
        //filer.setZ(dto.getZipOrPostalCode());

        filer.setJan(dto.getJan());
        filer.setFeb(dto.getFeb());
        filer.setMar(dto.getMar());
        filer.setApr(dto.getApr());
        filer.setMay(dto.getMay());
        filer.setJun(dto.getJun());
        filer.setJul(dto.getJul());
        filer.setAug(dto.getAug());
        filer.setSep(dto.getSep());
        filer.setOct(dto.getOct());
        filer.setNov(dto.getNov());
        filer.setDec(dto.getDec());
        filer.setFilerStatus(dto.isActive() ? STATUS_ACTIVE : STATUS_INACTIVE);
        //BatchUtils.setString(59, pStatement, dto.getMailedForm());
        filer.setSourceCd(dto.getId().getSourceCd());
        filer.setSourceUniqueId(Long.parseLong(dto.getId().getSourceUniqueId()));
        return filer;
    }

    /**
     * Method to determine if a string has consecutive/repetitive numbers in it
     *
     * @param str the input string
     * @return true if the string has consecutive numbers, false otherwise.
     */
    public static boolean isConsecutiveNumbers(String str) {

        if (StringUtils.isNotBlank(str)) {
            return str.matches("^(\\d)\\1+$");
        } else {
            return false;
        }
    }

    /**
     * Method to determine if a string has sequential numbers in it.
     *
     * @param str the input string
     * @return true if the string has sequential numbers, false otherwise.
     */
    public static boolean isSequentialNumbers(String str) {
        boolean isSequential;

        if (StringUtils.isBlank(str)) {
            return false;
        }
        // Since SSN is always 9 digits.
        isSequential = "012345678".equals(str);
        if (!isSequential) isSequential = "123456789".equals(str);
        if (!isSequential) isSequential = "234567890".equals(str);
        if (!isSequential) isSequential = "345678901".equals(str);
        if (!isSequential) isSequential = "456789012".equals(str);
        if (!isSequential) isSequential = "567890123".equals(str);
        if (!isSequential) isSequential = "678901234".equals(str);
        if (!isSequential) isSequential = "789012345".equals(str);
        if (!isSequential) isSequential = "890123456".equals(str);
        if (!isSequential) isSequential = "901234567".equals(str);

        return isSequential;
    }

    public static boolean containsRepeatedAllowedSpecialChars(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else {
            return (str.contains("##")
                    || str.contains("&&")
                    || str.contains("[[")
                    || str.contains(",,")
                    || str.contains("..")
                    || str.contains("((")
                    || str.contains("))")
                    || str.contains("\\\\")
                    || str.contains("''")
                    || str.contains("--")
                    || str.contains("]]")
                    || str.contains("//"));
        }

    }

    public static boolean containsAllowedSpecialCharacters(String str) {
        boolean isContains = true;
        if (StringUtils.isBlank(str))
            return true;
        char[] strToCharArray = str.toCharArray();
        for (char c : strToCharArray) {
            if (Character.isLetterOrDigit(c) || c == ' ' || c == '#' || c == '&' || c == '[' || c == '\'' || c == ',' || c == '.' || c == '(' || c == ')' || c == '\\' || c == '-' || c == ']' || c == '/') {
                isContains = true;
            } else {
                isContains = false;
                break;
            }
        }
        return isContains;
    }

    public static boolean containsAllowedSpecialCharactersForProviderCity(String str) {
        boolean isContains = true;
        if (StringUtils.isBlank(str))
            return true;
        char[] strToCharArray = str.toCharArray();
        for (char c : strToCharArray) {
            if (Character.isLetterOrDigit(c) || c == ' ' || c == '.' || c == '-' || c == '\'' || c == ',') {
                isContains = true;
            } else {
                isContains = false;
                break;
            }
        }
        return isContains;
    }

    public static boolean isCoverageDateValid(String correctionCode, String originalCoverageDt) {
        boolean isValid = true;
        if (StringUtils.isNotBlank(correctionCode)) {
            if (FileIngestionConstants.CORRECTION_CODE_ORIGINAL.equals(correctionCode)
                    || FileIngestionConstants.CORRECTION_CODE_UPDATE.equals(correctionCode)) {
                if (FileIngestionConstants.COVERAGE_DATE_ALL_NINES.equals(originalCoverageDt)
                        || FileIngestionConstants.COVERAGE_DATE_ALL_SIXES.equals(originalCoverageDt)) {
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    public static boolean isCoverageTaxYearValid(String taxYear, Date coverageDate) {
        boolean isValid = true;
        Calendar coverageBeginDtCalendar = new GregorianCalendar();
        if (StringUtils.isNotBlank(taxYear) && coverageDate != null) {
            coverageBeginDtCalendar.setTime(coverageDate);
            isValid = (Integer.parseInt(taxYear) == (coverageBeginDtCalendar.get(Calendar.YEAR)));
        }
        return isValid;
    }

    public static boolean isCoverageMonthValid(Date coverageBeginDate, Date recipientDob) {
        boolean isValid = true;
        Calendar dobCalendar, coverageBeginDateCalendar;
        if (coverageBeginDate != null && recipientDob != null) {
            dobCalendar = new GregorianCalendar();
            dobCalendar.setTime(recipientDob);
            coverageBeginDateCalendar = new GregorianCalendar();
            coverageBeginDateCalendar.setTime(coverageBeginDate);
            isValid = dobCalendar.before(coverageBeginDateCalendar) || dobCalendar.equals(coverageBeginDateCalendar);
            if (!isValid) {
                if (dobCalendar.get(Calendar.YEAR) == coverageBeginDateCalendar.get(Calendar.YEAR)) {
                    isValid = dobCalendar.get(Calendar.MONTH) == coverageBeginDateCalendar.get(Calendar.MONTH);
                }
            }
        }
        return isValid;
    }

    public static boolean isBeginDateBeforeEndDate(Date beginDate, Date endDate) {
        boolean isValid = true;
        if (beginDate != null && endDate != null) {
            Calendar beginDateCalendar = new GregorianCalendar();
            beginDateCalendar.setTime(beginDate);
            Calendar endDateCalendar = new GregorianCalendar();
            endDateCalendar.setTime(endDate);
            isValid = beginDateCalendar.before(endDateCalendar) || beginDateCalendar.equals(endDateCalendar);
        }
        return isValid;
    }

    public static boolean isRespPersonUniqueIdRecipientUniqueIdEqual(String respPerUniqueId, String reciUniqueId) {
        boolean isEqual = (respPerUniqueId.compareTo(reciUniqueId) == 0) ? true : false;
        return isEqual;
    }

    public static boolean isRespPersonUniqueIdExists(String respPerUniqueId) {
        boolean isValid = (respPerUniqueId != null) ? true : false;
        return isValid;
    }

    public static boolean isEmployerCityExists(String empCity) {
        boolean isValid = (empCity == null) ? true : false;
        return isValid;
    }
/*    public static void main(String[] args) {

    }*/

}
