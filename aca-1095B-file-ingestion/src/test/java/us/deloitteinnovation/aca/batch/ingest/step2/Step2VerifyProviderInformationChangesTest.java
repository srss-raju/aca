package us.deloitteinnovation.aca.batch.ingest.step2;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static us.deloitteinnovation.aca.batch.utils.BatchUtils.containsAllowedSpecialCharactersForProviderCity;

/**
 * Created by bhchaganti on 10/10/2016.
 */
public class Step2VerifyProviderInformationChangesTest extends Step2VerifyFilerProcessorTest {

    /* Provider name length tests */
    @Test
    public void testNullProviderName() {
        filerDemographicDto.setProviderName(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER34.1: PROVIDER_NAME is a required field and must not exceed 75 characters");
    }

    @Test
    public void testProviderNameWithSpace() {
        filerDemographicDto.setProviderName(" ");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.1: PROVIDER_NAME is a required field and must not exceed 75 characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.2: PROVIDER_NAME cannot only contain numbers or special characters");
    }

    @Test
    public void testProviderNameWithSpaces() {
        filerDemographicDto.setProviderName("   ");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.1: PROVIDER_NAME is a required field and must not exceed 75 characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.2: PROVIDER_NAME cannot only contain numbers or special characters");
    }

    @Test
    public void testProvidernameWithLessThan75Chars() {
        String providerName = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        filerDemographicDto.setProviderName(providerName);
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testProvidernameWithMoreThan75Chars() {
        String providerName = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        filerDemographicDto.setProviderName(providerName);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER34.1: PROVIDER_NAME is a required field and must not exceed 75 characters");
    }

    @Test
    public void testProvidernameWith75Chars() {
        String providername = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvw";
        filerDemographicDto.setProviderName(providername);
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    /*Provider name content tests*/
    @Test
    public void testProviderNameWithNumbers() {
        filerDemographicDto.setProviderName("132456");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER34.2: PROVIDER_NAME cannot only contain numbers or special characters");
    }

    @Test
    public void testProviderNameWithSpecialChars() {
        filerDemographicDto.setProviderName("''~`");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.2: PROVIDER_NAME cannot only contain numbers or special characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters & ' \" -- # < > * ^ % $ @ ! { } [ ] ? / \\ | + = --");
    }

    @Test
    public void testProviderNameWithNumAndSpecialChars() {
        filerDemographicDto.setProviderName("23''5~`");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.2: PROVIDER_NAME cannot only contain numbers or special characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters & ' \" -- # < > * ^ % $ @ ! { } [ ] ? / \\ | + = --");
    }

    @Test
    public void testProviderNameWithNumAndSpecialCharsMoreThan75Chars() {
        filerDemographicDto.setProviderName("23''5~23''5~23''5~23''5~23''5~23''5~23''5~23''5~23''5~23''5~23''5~23''5~23''5~23''5~");
        assertFilerDemographicDto(filerDemographicDto, 3, "ER34.2: PROVIDER_NAME cannot only contain numbers or special characters");
        assertFilerDemographicDto(filerDemographicDto, 3, "ER34.1: PROVIDER_NAME is a required field and must not exceed 75 characters");
        assertFilerDemographicDto(filerDemographicDto, 3, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters & ' \" -- # < > * ^ % $ @ ! { } [ ] ? / \\ | + = --");
    }

    @Test
    public void testProviderNameWithNumAndSpecialCharsWith75Chars() {
        filerDemographicDto.setProviderName("23''5~'34423''5~'34423''5~'34423''5~'34423''5~'34423''5~'34423''5~'344~'344");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.2: PROVIDER_NAME cannot only contain numbers or special characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters & ' \" -- # < > * ^ % $ @ ! { } [ ] ? / \\ | + = --");
    }

    @Test
    public void testProviderNameWithNumbersAndAlpha() {
        filerDemographicDto.setProviderName("132456abc");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testProviderNameWithSpecialCharsAndAlpha() {
        filerDemographicDto.setProviderName("d'a'b~");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters & ' \" -- # < > * ^ % $ @ ! { } [ ] ? / \\ | + = --");
    }

    @Test
    public void testProviderNameWithNumAndSpecialCharsAndAlpha() {
        filerDemographicDto.setProviderName("2abc3''5~`");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters & ' \" -- # < > * ^ % $ @ ! { } [ ] ? / \\ | + = --");
    }

    @Test
    public void testProviderNameWithNumAndSpecialCharsMoreThan75CharsAndAlpha() {
        filerDemographicDto.setProviderName("23''5~23''5~23''5~23''5~23''5~23'abc'5~23''5~def23''xyw5~23''5~23''5~23''5~23''5~23''5~23''5~");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.1: PROVIDER_NAME is a required field and must not exceed 75 characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters & ' \" -- # < > * ^ % $ @ ! { } [ ] ? / \\ | + = --");
    }

    @Test
    public void testProviderNameWithNumAndSpecialCharsWith75CharsAndAlpha() {
        filerDemographicDto.setProviderName("23''5~'3def3''5~'34423''5~'3abc3''5~'34423''5~'34423''5~'34423''5~'344~'344");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters & ' \" -- # < > * ^ % $ @ ! { } [ ] ? / \\ | + = --");
    }

    @Test
    public void testProviderNameWithRestrictedSpChars() {
        filerDemographicDto.setProviderName("##<>*-");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.2: PROVIDER_NAME cannot only contain numbers or special characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters");

    }

    @Test
    public void testProviderNameWithDoubleDash() {
        filerDemographicDto.setProviderName("--");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.2: PROVIDER_NAME cannot only contain numbers or special characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters");
    }

    @Test
    public void testProviderNameWithRestrictedSpCharsAlphaNum() {
        filerDemographicDto.setProviderName("#b#<>a*-d");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters");

    }

    @Test
    public void testProviderNameWithDoubleDashAlphaNum() {
        filerDemographicDto.setProviderName("--abc");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER34.3: PROVIDER_NAME cannot contain any of the following special characters");
    }

    @Test
    public void testProviderNameWithAllowedSpCharsAlphaNum() {
        filerDemographicDto.setProviderName("23abc5~");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    /* EIN tests*/

    @Test
    public void testNullEin() {
        filerDemographicDto.setProviderIdentificationNumber(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER35.1: PROVIDER_EIN is required and must not exceed 10 characters");
    }

    @Test
    public void testEinWithSpaces() {
        filerDemographicDto.setProviderIdentificationNumber("  ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER35.2: PROVIDER_EIN value must be in the format of XXXXXXXXX or XX-XXXXXXX");
    }

    @Test
    public void testEinWithSpace() {
        filerDemographicDto.setProviderIdentificationNumber(" ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER35.2: PROVIDER_EIN value must be in the format of XXXXXXXXX or XX-XXXXXXX");
    }

    @Test
    public void testEinWith10Chars() {
        filerDemographicDto.setProviderIdentificationNumber("abc&^%234j");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER35.2: PROVIDER_EIN value must be in the format of XXXXXXXXX or XX-XXXXXXX");
    }

    @Test
    public void testEinWithLessThan10Chars() {
        filerDemographicDto.setProviderIdentificationNumber("ac&%234j");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER35.2: PROVIDER_EIN value must be in the format of XXXXXXXXX or XX-XXXXXXX");
    }

    @Test
    public void testEinWithMoreThan10Chars() {
        filerDemographicDto.setProviderIdentificationNumber("ac&%234jac&%234jac&%234jac&%234jac&%234j");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER35.2: PROVIDER_EIN value must be in the format of XXXXXXXXX or XX-XXXXXXX");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER35.1: PROVIDER_EIN is required and must not exceed 10 characters");
    }

    @Test
    public void testEinWithCorrectFormat() {
        filerDemographicDto.setProviderIdentificationNumber("123456789");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testEinWithCorrectFormatHavingHyphenAtRightPlace() {
        filerDemographicDto.setProviderIdentificationNumber("12-3456789");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testEinWithCorrectFormatHavingHyphenAtWrongPlace() {
        filerDemographicDto.setProviderIdentificationNumber("12345-6789");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER35.2: PROVIDER_EIN value must be in the format of XXXXXXXXX or XX-XXXXXXX");
    }

    @Test
    public void testEinWithCorrectFormatMoreThan10Chars() {
        filerDemographicDto.setProviderIdentificationNumber("12345678901234");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER35.2: PROVIDER_EIN value must be in the format of XXXXXXXXX or XX-XXXXXXX");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER35.1: PROVIDER_EIN is required and must not exceed 10 characters");
    }

    @Test
    public void testEinWithCorrectFormatHavingHyphenAtRightPlaceMoreThan10Chars() {
        filerDemographicDto.setProviderIdentificationNumber("12-3456789123");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER35.2: PROVIDER_EIN value must be in the format of XXXXXXXXX or XX-XXXXXXX");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER35.1: PROVIDER_EIN is required and must not exceed 10 characters");
    }

    /* Provider contact number format tests*/
    @Test
    public void testNullProviderContactNumber() {
        filerDemographicDto.setProviderContactNo(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER36.1: CONTACT_PHONE_NUMBER is required and must not exceed 10 characters");
    }

    @Test
    public void testNullProviderContactNumberWithSpace() {
        filerDemographicDto.setProviderContactNo(" ");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER36.1: CONTACT_PHONE_NUMBER is required and must not exceed 10 characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER36.2: CONTACT_PHONE_NUMBER must contain numeric digits from 0-9");
    }

    @Test
    public void testNullProviderContactNumberWithSpaces() {
        filerDemographicDto.setProviderContactNo("   ");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER36.1: CONTACT_PHONE_NUMBER is required and must not exceed 10 characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER36.2: CONTACT_PHONE_NUMBER must contain numeric digits from 0-9");
    }

    @Test
    public void testProviderContactNumberWithMoreThan10Chars() {
        filerDemographicDto.setProviderContactNo("abcdefghijklmnopqrstuvwxyz");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER36.1: CONTACT_PHONE_NUMBER is required and must not exceed 10 characters");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER36.2: CONTACT_PHONE_NUMBER must contain numeric digits from 0-9");
    }

    @Test
    public void testProviderContactNumberWithLessThan10Chars() {
        filerDemographicDto.setProviderContactNo("abcabcabc");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER36.2: CONTACT_PHONE_NUMBER must contain numeric digits from 0-9");
    }

    @Test
    public void testProviderContactNumberWith10Chars() {
        filerDemographicDto.setProviderContactNo("abcabcabca");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER36.2: CONTACT_PHONE_NUMBER must contain numeric digits from 0-9");
    }

    @Test
    public void testProviderContactNumberWithSpChar() {
        filerDemographicDto.setProviderContactNo("&*^%$");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER36.2: CONTACT_PHONE_NUMBER must contain numeric digits from 0-9");
    }

    @Test
    public void testProviderContactNumberWithNumbers() {
        filerDemographicDto.setProviderContactNo("123456789");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testProviderContactNumberWithNumbersHaving10Digits() {
        filerDemographicDto.setProviderContactNo("1234567890");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testProviderContactNumberWithNumbersMoreThan10Digits() {
        filerDemographicDto.setProviderContactNo("1234567893456");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER36.1: CONTACT_PHONE_NUMBER is required and must not exceed 10 characters");
    }

    /* Provider city or town tests */

    @Test
    public void testNullCity() {
        filerDemographicDto.setProviderCityOrTown(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER39.1: PROVIDER_CITY_OR_TOWN is required and must not exceed 22 characters");
        assertThat("Special characters check failed", containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown()), is(equalTo(true)));
    }

    @Test
    public void testCityWithSpace() {
        filerDemographicDto.setProviderCityOrTown(" ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER39.2: PROVIDER_CITY_OR_TOWN cannot be only numbers or special characters");
        assertThat("Special characters check failed", containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown()), is(equalTo(true)));
    }

    @Test
    public void testCityWithLessThan22Chars() {
        filerDemographicDto.setProviderCityOrTown("Dunwoody");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
        assertThat("Special characters check failed", containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown()), is(equalTo(true)));
    }

    @Test
    public void testCityWith22Chars() {
        filerDemographicDto.setProviderCityOrTown("DunwoodyDunwoody Dunwo");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
        assertThat("Special characters check failed", containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown()), is(equalTo(true)));
    }

    @Test
    public void testCityWithMoreThan22Chars() {
        filerDemographicDto.setProviderCityOrTown("theQuickBrownFoxJumped OverTheLazyDog");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER39.1: PROVIDER_CITY_OR_TOWN is required and must not exceed 22 characters");
        assertThat("Special characters check failed", containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown()), is(equalTo(true)));
    }

    @Test
    public void testCityWithSpecialChars() {
        filerDemographicDto.setProviderCityOrTown("&^%&&&");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER39.2: PROVIDER_CITY_OR_TOWN cannot be only numbers or special characters");
        assertThat("Special characters check failed", containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown()), is(equalTo(false)));
    }

    @Test
    public void testCityWithNumbers() {
        filerDemographicDto.setProviderCityOrTown("12345");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER39.2: PROVIDER_CITY_OR_TOWN cannot be only numbers or special characters");
        assertThat("Special characters check failed", containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown()), is(equalTo(true)));
    }

    @Test
    public void testCityWithAllowedSpecialCharsAndAlpha() {
        filerDemographicDto.setProviderCityOrTown("ca'li,f.orn-ia");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
        assertThat("Special characters check failed", containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown()), is(equalTo(true)));
    }

    @Test
    public void testCityWithNotAllowedSpecialCharsAndAlpha() {
        filerDemographicDto.setProviderCityOrTown("ca'li,f.or*n-ia");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
        assertThat("Special characters check failed", containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown()), is(equalTo(false)));
    }

    @Test
    public void testCityWithAllowedSpecialCharsAndAlphaHavingMoreThan22Chars() {
        filerDemographicDto.setProviderCityOrTown("ca'li,f.orn-iaca'li,f.orn-iaca'li,f.orn-iaca'li,f.orn-iaca'li,f.orn-iaca'li,f.orn-iaca'li,f.orn-iaca'li,f.orn-ia");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER39.1: PROVIDER_CITY_OR_TOWN is required and must not exceed 22 characters");
        assertThat("Special characters check failed", containsAllowedSpecialCharactersForProviderCity(filerDemographicDto.getProviderCityOrTown()), is(equalTo(true)));
    }

    /* State or province related tests */

    @Test
    public void testNullState() {
        filerDemographicDto.setProviderStateOrProvince(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER40.1: PROVIDER_STATE_OR_PROVINCE is required and must not exceed 2 letters.");
    }

    @Test
    public void testStateWithSpaces() {
        filerDemographicDto.setProviderStateOrProvince("  ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER40.2: PROVIDER_STATE_OR_PROVINCE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testStateWithLessThan25Chars() {
        filerDemographicDto.setProviderStateOrProvince("abcdefghijklmnopqrstuvwx");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER40.1: PROVIDER_STATE_OR_PROVINCE is required and must not exceed 2 letters.");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER40.2: PROVIDER_STATE_OR_PROVINCE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testStateWith25Chars() {
        filerDemographicDto.setProviderStateOrProvince("abcdefghijklmnopqrstuvwxy");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER40.1: PROVIDER_STATE_OR_PROVINCE is required and must not exceed 2 letters.");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER40.2: PROVIDER_STATE_OR_PROVINCE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testStateWithMoreThan25Chars() {
        filerDemographicDto.setProviderStateOrProvince("abcdefghijklmnopqrstuvwxyz");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER40.1: PROVIDER_STATE_OR_PROVINCE is required and must not exceed 2 letters.");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER40.2: PROVIDER_STATE_OR_PROVINCE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testStateWithValidStateCode() {
        filerDemographicDto.setProviderStateOrProvince("CA");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testStateWithValidStateCodeLowerCase() {
        filerDemographicDto.setProviderStateOrProvince("ca");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testStateWithInValidStateCodeLowercase() {
        filerDemographicDto.setProviderStateOrProvince("zz");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER40.2: PROVIDER_STATE_OR_PROVINCE must be any value from the allowable state codes defined in the ICD");
    }

    @Test
    public void testStateWithInValidStateCode() {
        filerDemographicDto.setProviderStateOrProvince("ZZ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER40.2: PROVIDER_STATE_OR_PROVINCE must be any value from the allowable state codes defined in the ICD");
    }

    /* Provider country related tests */

    @Test
    public void testNullCountry() {
        filerDemographicDto.setProviderCountry(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER41.1: PROVIDER_COUNTRY is required and must not exceed 25 letters");
    }

    @Test
    public void testCountryWithLessThan25Chars() {
        filerDemographicDto.setProviderCountry("abcdefghi#$%890pqrstuvwx");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER41.1: PROVIDER_COUNTRY is required and must not exceed 25 letters");
    }

    @Test
    public void testCountryWith25Chars() {
        filerDemographicDto.setProviderCountry("united states of americaa");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER41.1: PROVIDER_COUNTRY is required and must not exceed 25 letters");
    }

    @Test
    public void testCountryWithMoreThan25Chars() {
        filerDemographicDto.setProviderCountry("abcdefghi#$%890pqrstuvwxyz");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER41.1: PROVIDER_COUNTRY is required and must not exceed 25 letters");
    }

    @Test
    public void testCountryWithAllNumbers() {
        filerDemographicDto.setProviderCountry("123456");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER41.1: PROVIDER_COUNTRY is required and must not exceed 25 letters");
    }

    @Test
    public void testCountryWithAllSpecialCharacters() {
        filerDemographicDto.setProviderCountry("&&^%$#");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER41.1: PROVIDER_COUNTRY is required and must not exceed 25 letters");
    }

    @Test
    public void testCountryWithAllLetters25Characters() {
        filerDemographicDto.setProviderCountry("abcdefghiJKLabcDEFghIJKlm");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testCountryWithAllLettersLessThan25Characters() {
        filerDemographicDto.setProviderCountry("abcdefghiJKLabcDEFghI");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testCountryWithAllLettersMoreThan25Characters() {
        filerDemographicDto.setProviderCountry("abcdefghijklabcdefghijklmnopqrstuvwxyzABCD");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER41.1: PROVIDER_COUNTRY is required and must not exceed 25 letters");
    }

    /* Provider zip or foreign postal code tests */
    @Test
    public void testProviderZipWithNullValue() {
        filerDemographicDto.setProviderZipOrPostalCode(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER42.1: PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE is required");
    }

    @Test
    public void testProviderZipWithSpaces() {
        filerDemographicDto.setProviderZipOrPostalCode("  ");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER42.2: PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE values must be digits 0-9");
        assertFilerDemographicDto(filerDemographicDto, 2, "ER42.3: PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE must have a minimum length of 5 digits and a maximum length of 9 digits");
    }

    @Test
    public void testProviderZipWithNumbers() {
        filerDemographicDto.setProviderZipOrPostalCode("125");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER42.3: PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE must have a minimum length of 5 digits and a maximum length of 9 digits");
    }

    @Test
    public void testProviderZipWithNonNumbers() {
        filerDemographicDto.setProviderZipOrPostalCode("abc*&");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER42.2: PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE values must be digits 0-9");
    }

    @Test
    public void testProviderZipWith5Numbers() {
        filerDemographicDto.setProviderZipOrPostalCode("12245");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testProviderZipWith5AlphaNum() {
        filerDemographicDto.setProviderZipOrPostalCode("12ab5");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER42.2: PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE values must be digits 0-9");
    }

    @Test
    public void testProviderZipWith6Numbers() {
        filerDemographicDto.setProviderZipOrPostalCode("238459");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testProviderZipWith9Numbers() {
        filerDemographicDto.setProviderZipOrPostalCode("122455678");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testProviderZipWith9AlphaNum() {
        filerDemographicDto.setProviderZipOrPostalCode("1a24c56d8");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER42.2: PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE values must be digits 0-9");
    }

    @Test
    public void testProviderZipWith10Numbers() {
        filerDemographicDto.setProviderZipOrPostalCode("1224556789");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER42.3: PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE must have a minimum length of 5 digits and a maximum length of 9 digits");
    }

    @Test
    public void testProviderZipWith2Zeros() {
        filerDemographicDto.setProviderZipOrPostalCode("002547895");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testProviderZipWith3Zeros() {
        filerDemographicDto.setProviderZipOrPostalCode("000547895");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testProviderZipWith5Zeros() {
        filerDemographicDto.setProviderZipOrPostalCode("000007895");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER42.4: First 5 digits of the  PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE cannot be all zeroes");
    }

    @Test
    public void testProviderZipWith6Zeros() {
        filerDemographicDto.setProviderZipOrPostalCode("000000895");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER42.4: First 5 digits of the  PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE cannot be all zeroes");
    }
}
