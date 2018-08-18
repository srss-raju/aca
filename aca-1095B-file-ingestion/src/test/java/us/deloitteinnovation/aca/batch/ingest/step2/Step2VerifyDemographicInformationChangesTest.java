package us.deloitteinnovation.aca.batch.ingest.step2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by bhchaganti on 10/10/2016.
 * Modified by gacche on 27/10/16
 */
public class Step2VerifyDemographicInformationChangesTest extends Step2VerifyFilerProcessorTest {
	
	/* Test only with Charachers*/
	@Test
    public void testRecipientMiddleNameForOnlyChars() throws Exception {
	 	filerDemographicDto.setRecipientMiddleName("ABC");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientFirstNameForOnlyChars() throws Exception {
	 	filerDemographicDto.setRecipientFirstName("ABC");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientLastNameForOnlyChars() throws Exception {
	 	filerDemographicDto.setRecipientLastName("ABC");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	/* Test only with Charachers with spaces*/
	@Test
    public void testRecipientMiddleNameForCharsAndSpace() throws Exception {
	 	filerDemographicDto.setRecipientMiddleName("ABC ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientFirstNameForCharsAndSpace() throws Exception {
	 	filerDemographicDto.setRecipientFirstName("ABC ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientLastNameForCharsAndSpace() throws Exception {
	 	filerDemographicDto.setRecipientLastName("ABC ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	/* Test only with Charachers with spaces in middle*/
	@Test
    public void testRecipientMiddleNameForCharsAndSpaceInMiddle() throws Exception {
	 	filerDemographicDto.setRecipientMiddleName("ABC XYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientFirstNameForCharsAndSpaceInMiddle() throws Exception {
	 	filerDemographicDto.setRecipientFirstName("ABC XYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientLastNameForCharsAndSpaceInMiddle() throws Exception {
	 	filerDemographicDto.setRecipientLastName("ABC XYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	/* Test only with Charachers and special characters*/
	@Test
    public void testRecipientMiddleNameForCharsAndSpecialChars() throws Exception {
	 	filerDemographicDto.setRecipientMiddleName("ABC@#$");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
	@Test
    public void testRecipientFirstNameForCharsAndSpecialChars() throws Exception {
	 	filerDemographicDto.setRecipientFirstName("ABC@#$");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
	@Test
    public void testRecipientLastNameForCharsAndSpecialChars() throws Exception {
	 	filerDemographicDto.setRecipientLastName("ABC@#$");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
	/* Test with single dash*/
	@Test
    public void testRecipientMiddleNameForSingleDash() throws Exception {
	 	filerDemographicDto.setRecipientMiddleName("ABC-");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientFirstNameForSingleDash() throws Exception {
	 	filerDemographicDto.setRecipientFirstName("ABC-");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientLastNameForSingleDash() throws Exception {
	 	filerDemographicDto.setRecipientLastName("ABC-");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientMiddleNameForSingleDashInMiddle() throws Exception {
	 	filerDemographicDto.setRecipientMiddleName("ABC-XYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientFirstNameForSingleDashInMiddle() throws Exception {
	 	filerDemographicDto.setRecipientFirstName("ABC-XYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientLastNameForSingleDashInMiddle() throws Exception {
	 	filerDemographicDto.setRecipientLastName("ABC-XYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientMiddleNameForSingleDashInFirst() throws Exception {
	 	filerDemographicDto.setRecipientMiddleName("-ABCXYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientFirstNameForSingleDashInFirst() throws Exception {
	 	filerDemographicDto.setRecipientFirstName("-ABCXYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientLastNameForSingleDashInFirst() throws Exception {
	 	filerDemographicDto.setRecipientLastName("-ABCXYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	/* Test only with numbers*/
	@Test
    public void testRecipientMiddleNameWithOnlyNumbers() {

        filerDemographicDto.setRecipientMiddleName("1234");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER6.2: RECIPIENT_MIDDLE_NAME cannot only contain numbers or special characters. Value must contain letter A-Z");
    }
	@Test
    public void testRecipientFirstNameWithOnlyNumbers() {

        filerDemographicDto.setRecipientFirstName("1234");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER5.2: RECIPIENT_FIRST_NAME cannot only contain numbers or special characters. Value must contain letters A-Z");
    }
	@Test
    public void testRecipientLastNameWithOnlyNumbers() {

        filerDemographicDto.setRecipientLastName("1234");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER7.2: RECIPIENT_LAST_NAME cannot only contain numbers or special characters. Value must contain letters A-Z");
    }
	/* Test only with numbers and special characters*/
	@Test
    public void testRecipientMiddleNameForNumbersAndSpecialChars() throws Exception {
	 	filerDemographicDto.setRecipientMiddleName("123@#$");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(2));
    }
	@Test
    public void testRecipientFirstNameForNumbersAndSpecialChars() throws Exception {
	 	filerDemographicDto.setRecipientFirstName("123@#$");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(2));
    }
	@Test
    public void testRecipientLastNameForNumbersAndSpecialChars() throws Exception {
	 	filerDemographicDto.setRecipientLastName("123@#$");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(2));
    }
	/* Test only with Alpha numeric*/
	@Test
    public void testRecipientMiddleNameForAlphaNumeric() throws Exception {
	 	filerDemographicDto.setRecipientMiddleName("123XYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientFirstNameForAlphaNumeric() throws Exception {
	 	filerDemographicDto.setRecipientFirstName("123XYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientLastNameForAlphaNumeric() throws Exception {
	 	filerDemographicDto.setRecipientLastName("123XYZ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	/* Test for valid length*/
	@Test
    public void testRecipientMiddleNameForValidLength() throws Exception {
	 	filerDemographicDto.setRecipientMiddleName("RecipientMiddleName");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientFirstNameForValidLength() throws Exception {
	 	filerDemographicDto.setRecipientFirstName("RecipientFirstName");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	@Test
    public void testRecipientLastNameForValidLength() throws Exception {
	 	filerDemographicDto.setRecipientLastName("RecipientLastName");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
	/* Test only with numbers*/
	@Test
    public void testRecipientFirstNameWithBlank() {

        filerDemographicDto.setRecipientFirstName("    ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
	@Test
    public void testRecipientLastNameWithBlank() {

        filerDemographicDto.setRecipientLastName("    ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
	@Test
    public void testRecipientMiddleNameWithBlank() {

        filerDemographicDto.setRecipientMiddleName("    ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
        //assertFilerDemographicDto(filerDemographicDto, 1, "ER6.2: RECIPIENT_MIDDLE_NAME cannot only contain numbers or special characters. Value must contain letter A-Z");
    }
	/* Test for Invalid length */
    @Test
    public void testRecipientMiddleNameForInValidLength() {

        filerDemographicDto.setRecipientMiddleName("Recipient middle name");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER6.1: RECIPIENT_MIDDLE_NAME must not exceed 20 characters.");
    }

    @Test
    public void testRecipientFirstNameForInValidLength() {

        filerDemographicDto.setRecipientFirstName("Recipient First name  ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER5.1: RECIPIENT_FIRST_NAME is a required field and must not exceed 20 characters.");
    }

    @Test
    public void testRecipientLastNameForInValidLength() {

        filerDemographicDto.setRecipientLastName("Recipient last name  ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER7.1: RECIPIENT_LAST_NAME is required and must not exceed 20 characters.");
    }
    /* Test for special chars */
    @Test
    public void testRecipientMiddleNameForSpecialChars() {

        filerDemographicDto.setRecipientMiddleName("Ab\\\\c");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER6.3: RECIPIENT_MIDDLE_NAME cannot contain any of the following special characters");
    }

    @Test
    public void testRecipientFirstNameForSpecialChars() {

        filerDemographicDto.setRecipientFirstName("Ab\\\\c");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER5.3: RECIPIENT_FIRST_NAME cannot contain any of the following special characters");
    }

    @Test
    public void testRecipientLastNameForSpecialChars() {

        filerDemographicDto.setRecipientLastName("Ab\\\\c");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER7.3: RECIPIENT_LAST_NAME cannot contain any of the following special characters");
    }

    @Test
    public void testRecipientSuffixForSpecialChars() {

        filerDemographicDto.setRecepientSuffixName("Ab\\\\c");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER8.3: RECIPIENT_NAME_SUFFIX cannot contain any of the following special characters");
    }

    /* Test for double dash (--) */
    @Test
    public void testRecipientMiddleNameForDoubleDash() {

        filerDemographicDto.setRecipientMiddleName("N--");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER6.3: RECIPIENT_MIDDLE_NAME cannot contain any of the following special characters");
    }

    @Test
    public void testRecipientFirstNameForDoubleDash() {

        filerDemographicDto.setRecipientFirstName("N--");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER5.3: RECIPIENT_FIRST_NAME cannot contain any of the following special characters");
    }

    @Test
    public void testRecipientLastNameForDoubleDash() {

        filerDemographicDto.setRecipientLastName("N--");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER7.3: RECIPIENT_LAST_NAME cannot contain any of the following special characters");
    }

    @Test
    public void testRecipientSuffixForDoubleDash() {

        filerDemographicDto.setRecepientSuffixName("N--");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER8.3: RECIPIENT_NAME_SUFFIX cannot contain any of the following special characters");
    }

    @Test
    @Ignore
    public void testRecipientDobWithAllZeros() {
        filerDemographicDto.setRecipientDobStr("00000000");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER11.1: RECIPIENT_DOB is a required field and must be in the MMDDYYYY format with no separators.");
    }

    @Test
    @Ignore
    public void testRecipientDobWithInvalidDate() {
        filerDemographicDto.setRecipientDobStr("02301980");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER11.1: RECIPIENT_DOB is a required field and must be in the MMDDYYYY format with no separators.");
    }

}
