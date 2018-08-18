package us.deloitteinnovation.aca.batch.ingest.step2;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by bhchaganti on 10/4/2016.
 */
public class Step2VerifyAddressInformationChangesTest extends Step2VerifyFilerProcessorTest {


    @Test
    public void testZip4With4Numeric() throws Exception {
        filerDemographicDto.setRecipientZip4("1234");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testZip4WithNull() throws Exception {
        filerDemographicDto.setRecipientZip4("1234");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testZip4WithSpace() throws Exception {
        filerDemographicDto.setRecipientZip4(" ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER19.1: RECIPIENT ZIP 4");
    }

    @Test
    public void testZip4WithSpaces() throws Exception {
        filerDemographicDto.setRecipientZip4("  ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER19.1: RECIPIENT ZIP 4");
    }

    @Test
    public void testZip4With5NonNumeric() throws Exception {
        filerDemographicDto.setRecipientZip4("abcde");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER19.1: RECIPIENT ZIP 4");
    }

    @Test
    public void testZip4With4NonNumeric() throws Exception {
        filerDemographicDto.setRecipientZip4("abcd");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER19.1: RECIPIENT ZIP 4");
    }

    @Test
    public void testZip4With5Spchar() throws Exception {
        filerDemographicDto.setRecipientZip4("*&^%$");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER19.1: RECIPIENT ZIP 4");
    }

    @Test
    public void testZip4With4Spchar() throws Exception {
        filerDemographicDto.setRecipientZip4("^%$#");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER19.1: RECIPIENT ZIP 4");
    }

    @Test
    public void testZip5WithNullValue() {
        filerDemographicDto.setRecipientZip5(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER18.1: RECIPIENT ZIP 5");
    }

    @Test
    public void testZip5WithSpace() {
        filerDemographicDto.setRecipientZip5(" ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER18.1: RECIPIENT ZIP 5");
    }

    @Test
    public void testZip5WithSpaces() {
        filerDemographicDto.setRecipientZip5("   ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER18.1: RECIPIENT ZIP 5");
    }

    @Test
    public void testZip5With5NonNumeric() {
        filerDemographicDto.setRecipientZip5("abcde");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER18.1: RECIPIENT ZIP 5");
    }

    @Test
    public void testZip5With5Numeric() {
        filerDemographicDto.setRecipientZip5("94089");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testZip5With6NonNumeric() {
        filerDemographicDto.setRecipientZip5("abcdef");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER18.1: RECIPIENT ZIP 5");
    }

    @Test
    public void testZip5With6Zeros() {
        filerDemographicDto.setRecipientZip5("000000");
        assertThat("All Zero's data check failed", filerDemographicDto.getRecepientZip5().matches("^[0]+$"), is(true));
        assertFilerDemographicDto(filerDemographicDto, 1, "ER18.1: RECIPIENT ZIP 5");
    }

    @Test
    public void testZip5With4Zeros() {
        filerDemographicDto.setRecipientZip5("0000");
        assertThat("All Zero's data check failed", filerDemographicDto.getRecepientZip5().matches("^[0]+$"), is(true));
        assertFilerDemographicDto(filerDemographicDto, 1, "ER18.1: RECIPIENT ZIP 5");
    }

    @Test
    public void testZip5With5Zeros() {
        filerDemographicDto.setRecipientZip5("00000");
        assertThat("All Zero's data check failed", filerDemographicDto.getRecepientZip5().matches("^[0]+$"), is(true));
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testZip5With6Numeric() {
        filerDemographicDto.setRecipientZip5("940897");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER18.1: RECIPIENT ZIP 5");
    }

    @Test
    public void testNullStateCode() {
        filerDemographicDto.setRecipientState(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER17.1: RECIPIENT_STATE is required and must be exactly 2 letters");
    }

    @Test
    public void testStateCodeWithSpace() {
        filerDemographicDto.setRecipientState(" ");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.1: RECIPIENT_STATE is required and must be exactly 2 letters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.2: RECIPIENT_STATE must be any value from the allowable state codes defined in the ICD");

    }

    @Test
    public void testStateCodeWithSpaces() {
        filerDemographicDto.setRecipientState("  ");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.1: RECIPIENT_STATE is required and must be exactly 2 letters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.2: RECIPIENT_STATE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testStateCodeWith2letters() {
        filerDemographicDto.setRecipientState("ab");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER17.2: RECIPIENT_STATE must be any value from the allowable state codes defined in the ICD");
    }


    @Test
    public void testStateCodeWith2Numbers() {
        filerDemographicDto.setRecipientState("23");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.1: RECIPIENT_STATE is required and must be exactly 2 letters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.2: RECIPIENT_STATE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testStateCodeWith2Alphanum() {
        filerDemographicDto.setRecipientState("2a");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.1: RECIPIENT_STATE is required and must be exactly 2 letters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.2: RECIPIENT_STATE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testStateCodeWith2SpChar() {
        filerDemographicDto.setRecipientState("*%");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.1: RECIPIENT_STATE is required and must be exactly 2 letters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.2: RECIPIENT_STATE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testStateCodeWith3letters() {
        filerDemographicDto.setRecipientState("abc");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.1: RECIPIENT_STATE is required and must be exactly 2 letters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.2: RECIPIENT_STATE must be any value from the allowable state codes defined in the ICD");
    }


    @Test
    public void testStateCodeWith3Numbers() {
        filerDemographicDto.setRecipientState("234");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.1: RECIPIENT_STATE is required and must be exactly 2 letters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER17.2: RECIPIENT_STATE must be any value from the allowable state codes defined in the ICD");

    }

    @Test
    public void testStateCodeWithValidUSStateUpperCase() {
        filerDemographicDto.setRecipientState("CA");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));

    }

    @Test
    public void testStateCodeWithValidUSStateLowerCase() {
        filerDemographicDto.setRecipientState("ca");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));

    }

    @Test
    public void testStateCodeWithInvalidUSStateUpperCase() {
        filerDemographicDto.setRecipientState("ZZ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER17.2: RECIPIENT_STATE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testStateCodeWithInvalidUSStateLowerCase() {
        filerDemographicDto.setRecipientState("zz");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER17.2: RECIPIENT_STATE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testNullCity() {
        filerDemographicDto.setRecipientCity(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER16.1: RECIPIENT_CITY is required and must not exceed 22 characters.");
    }

    @Test
    public void testCityWithSpace() {
        filerDemographicDto.setRecipientCity(" ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER16.2: RECIPIENT_CITY cannot be only numbers or special characters");
    }

    @Test
    public void testCityWithSpaces() {
        filerDemographicDto.setRecipientCity("  ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER16.2: RECIPIENT_CITY cannot be only numbers or special characters");
    }

    @Test
    public void testCityWithOnlyNumbers() {
        filerDemographicDto.setRecipientCity("12345");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER16.2: RECIPIENT_CITY cannot be only numbers or special characters");
    }

    @Test
    public void testCityWithOnlySpecialChars() {
        filerDemographicDto.setRecipientCity("*&^%$");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER16.2: RECIPIENT_CITY cannot be only numbers or special characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER16.3 RECIPIENT_CITY allows only alphabets A-Z and number 0-9 and special characters");
    }

    @Test
    public void testCityWithOnlySpecialCharsAndNum() {
        filerDemographicDto.setRecipientCity("*&^%$1234");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER16.2: RECIPIENT_CITY cannot be only numbers or special characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER16.3 RECIPIENT_CITY allows only alphabets A-Z and number 0-9 and special characters");
    }

    @Test
    public void testCityWithSpecialCharsNumAndAlphaAppend() {
        filerDemographicDto.setRecipientCity("*&^%$1234abc");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER16.3 RECIPIENT_CITY allows only alphabets A-Z and number 0-9 and special characters");
    }

    @Test
    public void testCityWithSpecialCharsNumAndAlphaPrepend() {
        filerDemographicDto.setRecipientCity("abc*&^%$1234");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER16.3 RECIPIENT_CITY allows only alphabets A-Z and number 0-9 and special characters");
    }

    @Test
    public void testCityWithSpecialCharsNumAndAlphaInBetween() {
        filerDemographicDto.setRecipientCity("*&^%$abc1234");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER16.3 RECIPIENT_CITY allows only alphabets A-Z and number 0-9 and special characters");
    }

    @Test
    public void testCityWithOnlySpecialCharsAndAlpha() {
        filerDemographicDto.setRecipientCity("*&^%$abc");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER16.3 RECIPIENT_CITY allows only alphabets A-Z and number 0-9 and special characters");
    }

    @Test
    public void testCityWithOnlyNumAndAlpha() {
        filerDemographicDto.setRecipientCity("12abc34");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testCityWithOnlyNumAndAlphaMoreThan25Chars() {
        filerDemographicDto.setRecipientCity("abc1234abc1234abc1234abc1234");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER16.1: RECIPIENT_CITY is required and must not exceed 22 characters.");
    }

    @Test
    public void testCityWithOnlySpCharAndAlphaMoreThan25Chars() {
        filerDemographicDto.setRecipientCity("*&^%$abc*&^%$abc*&^%$abc*&^%$abc*&^%$abc*&^%$abc*&^%$abc");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER16.1: RECIPIENT_CITY is required and must not exceed 22 characters.");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER16.3 RECIPIENT_CITY allows only alphabets A-Z and number 0-9 and special characters");
    }

    @Test
    public void testCityWithOnlySpCharAndNumaMoreThan25Chars() {
        filerDemographicDto.setRecipientCity("*&^%$123456*&^%$123456*&^%$123456*&^%$123456*&^%$123456*&^%$123456");
        assertFilerDemographicDto(filerDemographicDto, 3, "ER16.2: RECIPIENT_CITY cannot be only numbers or special characters");
        assertFilerDemographicDto(filerDemographicDto, 3, "ER16.1: RECIPIENT_CITY is required and must not exceed 22 characters.");
        assertFilerDemographicDto(filerDemographicDto, 3, "ER16.3 RECIPIENT_CITY allows only alphabets A-Z and number 0-9 and special characters");
    }

    @Test
    public void testCityWithOnlyAllowedSpecialCharsAndAlphaNum() {
        filerDemographicDto.setRecipientCity("abc123.-");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testCityWithNotAllowedSpecialCharsAndAlphaNum() {
        filerDemographicDto.setRecipientCity("abc123&&%");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER16.3 RECIPIENT_CITY allows only alphabets A-Z and number 0-9 and special characters");
    }

    @Test
    public void testCityWithNotAllowedSpecialCharsAndNum() {
        filerDemographicDto.setRecipientCity("123&&%");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER16.3 RECIPIENT_CITY allows only alphabets A-Z and number 0-9 and special characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER16.2: RECIPIENT_CITY cannot be only numbers or special characters");
    }

    /**
     * RecipientAddressLine1 tests
     */

    @Test
    public void testNullRecipientAddressLine1() {
        filerDemographicDto.setRecipientAddressLine1(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1");
        //assertFilerDemographicDto(filerDemographicDto, 2, "ER14.3: RECIPIENT_ADDRESS_LINE_1");
    }

    @Test
    public void testRecipientAddressLine1WithSpace() {
        filerDemographicDto.setRecipientAddressLine1(" ");
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER14.2: RECIPIENT_ADDRESS_LINE_1");

    }

    @Test
    public void testNullRecipientAddressLine1WithSpaces() {
        filerDemographicDto.setRecipientAddressLine1("  ");
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1 is required and must not exceed 35 characters");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER14.2: RECIPIENT_ADDRESS_LINE_1 cannot be only numbers or special characters");
    }

    @Test
    public void testRecipientAddressLine1WithSpecialChar() {
        filerDemographicDto.setRecipientAddressLine1("&*%");
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1 is required and must not exceed 35 characters");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER14.2: RECIPIENT_ADDRESS_LINE_1 cannot be only numbers or special characters");
    }

    @Test
    public void testRecipientAddressLine1WithNum() {
        filerDemographicDto.setRecipientAddressLine1("123");
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1 is required and must not exceed 35 characters");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER14.2: RECIPIENT_ADDRESS_LINE_1 cannot be only numbers or special characters");
    }

    @Test
    public void testRecipientAddressLine1WithNumAndSpChar() {
        filerDemographicDto.setRecipientAddressLine1("123**");
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1 is required and must not exceed 35 characters");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER14.2: RECIPIENT_ADDRESS_LINE_1 cannot be only numbers or special characters");
    }

    @Test
    public void testRecipientAddressLine1WithAlphaNum() {
        filerDemographicDto.setRecipientAddressLine1("abc123**");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testRecipientAddressLine1With35Chars() {
        filerDemographicDto.setRecipientAddressLine1("abcdefghabcdefghabcdefghabcdefgh23$");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testRecipientAddressLine1WithMoreThan35Chars() {
        filerDemographicDto.setRecipientAddressLine1("abcdefghabcdefghabcdefghabcdefghabcdefgh");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1 is required and must not exceed 35 characters");
    }

    /**
     * RecepientAddressLine2 related tests
     */

    @Test
    public void testNullRecipientAddressLine2() {
        filerDemographicDto.setRecipientAddressLine2(null);
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testRecipientAddressLine2WithSpace() {
        filerDemographicDto.setRecipientAddressLine2(" ");
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER15.2: RECIPIENT_ADDRESS_LINE_2");

    }

    @Test
    public void testNullRecipientAddressLine2WithSpaces() {
        filerDemographicDto.setRecipientAddressLine2("  ");
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1 is required and must not exceed 35 characters");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER15.2: RECIPIENT_ADDRESS_LINE_2 cannot be only numbers or special characters");
    }

    @Test
    public void testRecipientAddressLine2WithSpecialChar() {
        filerDemographicDto.setRecipientAddressLine2("&*%");
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1 is required and must not exceed 35 characters");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER15.2: RECIPIENT_ADDRESS_LINE_2 cannot be only numbers or special characters");
    }

    @Test
    public void testRecipientAddressLine2WithNum() {
        filerDemographicDto.setRecipientAddressLine2("123");
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1 is required and must not exceed 35 characters");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER15.2: RECIPIENT_ADDRESS_LINE_2 cannot be only numbers or special characters");
    }

    @Test
    public void testRecipientAddressLine2WithNumAndSpChar() {
        filerDemographicDto.setRecipientAddressLine2("123**");
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER14.1: RECIPIENT_ADDRESS_LINE_1 is required and must not exceed 35 characters");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER15.2: RECIPIENT_ADDRESS_LINE_2 cannot be only numbers or special characters");
    }

    @Test
    public void testRecipientAddressLine2WithAlphaNum() {
        filerDemographicDto.setRecipientAddressLine2("abc123**");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testRecipientAddressLine2With35Chars() {
        filerDemographicDto.setRecipientAddressLine2("abcdefghabcdefghabcdefghabcdefgh23$");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testRecipientAddressLine2WithMoreThan35Chars() {
        filerDemographicDto.setRecipientAddressLine2("abcdefghabcdefghabcdefghabcdefghabcdefgh");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER15.1: RECIPIENT_ADDRESS_LINE_2 is optional and must not exceed 35 characters");
    }

}
