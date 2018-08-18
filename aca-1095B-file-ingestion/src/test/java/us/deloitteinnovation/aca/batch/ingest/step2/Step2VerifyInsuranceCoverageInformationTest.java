package us.deloitteinnovation.aca.batch.ingest.step2;

import org.junit.Test;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by bhchaganti on 10/14/2016.
 */
public class Step2VerifyInsuranceCoverageInformationTest extends Step2VerifyFilerProcessorTest {

    /* Policy origin related tests */
    @Test
    public void testNullPolicyOrigin() {
        filerDemographicDto.setPolicyOrigin(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER20.1: POLICY_ORIGIN is a required field and must not exceed 1 character and must be C");
    }

    @Test
    public void testPolicyOriginWithSpace() {
        filerDemographicDto.setPolicyOrigin(" ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER20.1: POLICY_ORIGIN is a required field and must not exceed 1 character and must be C");
    }

    @Test
    public void testPolicyOriginWithSpaces() {
        filerDemographicDto.setPolicyOrigin("   ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER20.1: POLICY_ORIGIN is a required field and must not exceed 1 character and must be C");
    }

    @Test
    public void testPolicyOriginWith1Character() {
        filerDemographicDto.setPolicyOrigin("D");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER20.1: POLICY_ORIGIN is a required field and must not exceed 1 character and must be C");
    }

    @Test
    public void testPolicyOriginWith2Characters() {
        filerDemographicDto.setPolicyOrigin("DE");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER20.1: POLICY_ORIGIN is a required field and must not exceed 1 character and must be C");
    }

    @Test
    public void testPolicyOriginWith3Characters() {
        filerDemographicDto.setPolicyOrigin("DE#");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER20.1: POLICY_ORIGIN is a required field and must not exceed 1 character and must be C");
    }

    @Test
    public void testPolicyOriginWith1AllowedCharacter() {
        filerDemographicDto.setPolicyOrigin("C");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testPolicyOriginIncluding1AllowedCharacter() {
        filerDemographicDto.setPolicyOrigin("CD");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER20.1: POLICY_ORIGIN is a required field and must not exceed 1 character and must be C");
    }

    @Test
    public void testPolicyOriginWith1AllowedCharacterLowerCase() {
        filerDemographicDto.setPolicyOrigin("c");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER20.1: POLICY_ORIGIN is a required field and must not exceed 1 character and must be C");
    }

    /* Policy Program name related tests */
    @Test
    public void testNullProgramName() {
        filerDemographicDto.getFilerCoverage().setProgramName(null);
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER21.1: POLICY_PROGRAM_NAME is required and must not exceed 50 characters.");
    }

    @Test
    public void testProgramNameWithSpace() {
        filerDemographicDto.getFilerCoverage().setProgramName(" ");
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER21.2: POLICY_PROGRAM_NAME cannot contain only numbers or special characters.");
    }

    @Test
    public void testProgramNameWithSpaces() {
        filerDemographicDto.getFilerCoverage().setProgramName("   ");
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER21.2: POLICY_PROGRAM_NAME cannot contain only numbers or special characters.");
    }

    @Test
    public void testProgramnameWithLessThan50Chars() {
        String ProgramName = "abcdefghijklmnopqrstuvwxyza";
        filerDemographicDto.getFilerCoverage().setProgramName(ProgramName);
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
    }

    @Test
    public void testProgramnameWithMoreThan50Chars() {
        String ProgramName = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
        filerDemographicDto.getFilerCoverage().setProgramName(ProgramName);
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER21.1: POLICY_PROGRAM_NAME is required and must not exceed 50 characters.");
    }

    @Test
    public void testProgramnameWith50Chars() {
        String Programname = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwx";
        filerDemographicDto.getFilerCoverage().setProgramName(Programname);
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
    }

    @Test
    public void testProgramNameWithNumbers() {
        filerDemographicDto.getFilerCoverage().setProgramName("12345");
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER21.2: POLICY_PROGRAM_NAME cannot contain only numbers or special characters.");
    }

    @Test
    public void testProgramNameWithSpecialCharacters() {
        filerDemographicDto.getFilerCoverage().setProgramName("&&^%$");
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER21.2: POLICY_PROGRAM_NAME cannot contain only numbers or special characters.");
    }

    @Test
    public void testProgramNameWithSpecialCharactersAndMoreThan50Chars() {
        filerDemographicDto.getFilerCoverage().setProgramName("&&^%$&&^%$&&^%$&&^%$&&^%$&&^%$&&^%$&&^%$&&^%$&&^%$&&^%$");
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 2, "ER21.2: POLICY_PROGRAM_NAME cannot contain only numbers or special characters.");
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 2, "ER21.1: POLICY_PROGRAM_NAME is required and must not exceed 50 characters.");
    }

    @Test
    public void testProgramNameWithSpecialCharactersAndNumbers() {
        filerDemographicDto.getFilerCoverage().setProgramName("1&&4^%$");
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER21.2: POLICY_PROGRAM_NAME cannot contain only numbers or special characters.");
    }

    @Test
    public void testProgramNameWithAlphaNum() {
        filerDemographicDto.getFilerCoverage().setProgramName("a12v345");
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
    }

    @Test
    public void testProgramNameWithSpecialCharAndAlpha() {
        filerDemographicDto.getFilerCoverage().setProgramName("&a&^c%b$");
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
    }

    /* Original coverage begin date tests */
    @Test
    public void testNullOriginalCoverageBeginDate() {
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(null);
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        boolean isValid = BatchUtils.isCoverageTaxYearValid(filerDemographicDto.getId().getTaxYear(), filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt());
        assertThat("Coverage tax year check failed", isValid, is(true));
    }

    @Test
    public void testOriginalCoverageBeginDateWithSpaces() {
        String originalCoverageBeginDateString = " ";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
    }

    @Test
    public void testOriginalCoverageBeginDateWithLessThan8Chars() {
        String originalCoverageBeginDateString = "942016";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
    }

    @Test
    public void testOriginalCoverageBeginDateWith8Chars() {
        String originalCoverageBeginDateString = "09042016";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
    }

    @Test
    public void testOriginalCoverageBeginDateWithMoreThan8Chars() {
        String originalCoverageBeginDateString = "010012016";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
    }

    @Test
    public void testOriginalCoverageBeginDateWith8CharsNotInMMDDYYFormat() {
        String originalCoverageBeginDateString = "010012016";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
    }

    /*
    Original coverage end date tests
    */
    @Test
    public void testOriginalCoverageEndDateWithMoreThan8Chars() {
        String originalCoverageEndDateString = "120302016";
        filerDemographicDto.getFilerCoverage().setOrigCoverageEndDt(BatchUtils.getDate(originalCoverageEndDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER23.1: ORIGINAL_COVERAGE_END_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
    }

    @Test
    public void testOriginalCoverageEndDateWith8CharsNotInMMDDYYFormat() {
        String originalCoverageEndDateString = "120302016";
        filerDemographicDto.getFilerCoverage().setOrigCoverageEndDt(BatchUtils.getDate(originalCoverageEndDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER23.1: ORIGINAL_COVERAGE_END_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
    }

    // Check for validity of the coverage begin date
    @Test
    public void testOriginalCoverageBeginDateWithoutAll9s() {
        String originalCoverageBeginDateString = "99999979";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(true));
    }

    @Test
    public void testOriginalCoverageBeginDateWithoutAll6s() {
        String originalCoverageBeginDateString = "66666676";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(true));
    }

    @Test
    public void testOriginalCoverageBeginDateWithAll9s() {
        String originalCoverageBeginDateString = "99999999";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
//        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(false));
    }

    @Test
    public void testOriginalCoverageBeginDateWithAll6s() {
        String originalCoverageBeginDateString = "66666666";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
    }

    @Test
    public void testOriginalCoverageBeginDateWithAll9sWithoutCorrectionCode() {
        String originalCoverageBeginDateString = "99999999";
        filerDemographicDto.setCorrection(null);
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        /*assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");*/
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(true));
    }

    @Test
    public void testOriginalCoverageBeginDateWithAll6sWithoutCorrectionCode() {
        String originalCoverageBeginDateString = "66666666";
        filerDemographicDto.setCorrection(null);
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
//        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(true));
    }

    @Test
    public void testOriginalCoverageBeginDateWithAll9sWithCorrectionCodeU() {
        String originalCoverageBeginDateString = "99999999";
        filerDemographicDto.setCorrection("U");
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
//        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(false));
    }

    @Test
    public void testOriginalCoverageBeginDateWithAll6sWithCorrectionCodeU() {
        String originalCoverageBeginDateString = "66666666";
        filerDemographicDto.setCorrection("U");
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
//        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(false));
    }

    @Test
    public void testOriginalCoverageBeginDateWithoutAll9sWithCorrectionCodeU() {
        String originalCoverageBeginDateString = "99999979";
        filerDemographicDto.setCorrection("U");
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(true));
    }

    @Test
    public void testOriginalCoverageBeginDateWithoutAll6sWithCorrectionCodeU() {
        String originalCoverageBeginDateString = "66666676";
        filerDemographicDto.setCorrection("U");
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(true));
    }

    @Test
    public void testOriginalCoverageBeginDateWithoutAll9sWithCorrectionCodeC() {
        String originalCoverageBeginDateString = "99999979";
        filerDemographicDto.setCorrection("C");
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(true));
    }

    @Test
    public void testOriginalCoverageBeginDateWithoutAll6sWithCorrectionCodeC() {
        String originalCoverageBeginDateString = "66666676";
        filerDemographicDto.setCorrection("C");
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(true));
    }

    @Test
    public void testOriginalCoverageBeginDateWithAll9sWithCorrectionCodeC() {
        String originalCoverageBeginDateString = "99999999";
        filerDemographicDto.setCorrection("C");
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
//        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(true));
    }

    @Test
    public void testOriginalCoverageBeginDateWithAll6sWithCorrectionCodeC() {
        String originalCoverageBeginDateString = "66666666";
        filerDemographicDto.setCorrection("C");
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
//        assertFilerCoverageDto(filerDemographicDto.getFilerCoverage(), 1, "ER22.1: ORIGINAL_COVERAGE_BEGIN_DT is a required field and must not exceed 8 characters and must be a valid date in MMDDYYYY format with no separators");
        coverageConstraintViolations = validator.validate(filerDemographicDto.getFilerCoverage());
        assertThat(getCoverageConstraintViolations(coverageConstraintViolations), coverageConstraintViolations.size(), is(0));
        assertThat("Original coverage begin date check failed", BatchUtils.isCoverageDateValid(filerDemographicDto.getCorrection(), originalCoverageBeginDateString), is(true));
    }

    /* Tax year validation tests */
    @Test
    public void testCoverageTaxYearBeforeCurrentTaxYear() {
        String originalCoverageBeginDateString = "02212015";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        boolean isValid = BatchUtils.isCoverageTaxYearValid(filerDemographicDto.getId().getTaxYear(), filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt());
        assertThat("Coverage tax year check failed", isValid, is(false));
    }

    @Test
    public void testCoverageTaxYearAfterCurrentTaxYear() {
        String originalCoverageBeginDateString = "02212017";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        boolean isValid = BatchUtils.isCoverageTaxYearValid(filerDemographicDto.getId().getTaxYear(), filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt());
        assertThat("Coverage tax year check failed", isValid, is(false));
    }

    @Test
    public void testCoverageTaxYearEqualsTaxYear() {
        String originalCoverageBeginDateString = "02212016";
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        boolean isValid = BatchUtils.isCoverageTaxYearValid(filerDemographicDto.getId().getTaxYear(), filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt());
        assertThat("Coverage tax year check failed", isValid, is(true));
    }

    @Test
    public void testCoverageTaxYearWithNullTaxYear() {
        String originalCoverageBeginDateString = "02212017";
        filerDemographicDto.getId().setTaxYear(null);
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        boolean isValid = BatchUtils.isCoverageTaxYearValid(filerDemographicDto.getId().getTaxYear(), filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt());
        assertThat("Coverage tax year check failed", isValid, is(true));
    }

    @Test
    public void testCoverageTaxYearWithNullTaxYearAndNullCoverageBeginDt() {
        String originalCoverageBeginDateString = null;
        filerDemographicDto.getId().setTaxYear(null);
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(originalCoverageBeginDateString));
        boolean isValid = BatchUtils.isCoverageTaxYearValid(filerDemographicDto.getId().getTaxYear(), filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt());
        assertThat("Coverage tax year check failed", isValid, is(true));
    }

    @Test
    public void testCoverageBeforeDOB() {
        String dobString = "11052016";
        filerDemographicDto.setRecipientDob(BatchUtils.getDate(dobString));
        Date originalCoverageBeginDate = filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt();
        boolean isValid = BatchUtils.isCoverageMonthValid(originalCoverageBeginDate, filerDemographicDto.getRecipientDob());
        assertThat("Coverage month check failed", isValid, is(false));
    }

    @Test
    public void testCoverageAfterDOB() {
        String dobString = "10022016";
        filerDemographicDto.setRecipientDob(BatchUtils.getDate(dobString));
        Date originalCoverageBeginDate = filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt();
        boolean isValid = BatchUtils.isCoverageMonthValid(originalCoverageBeginDate, filerDemographicDto.getRecipientDob());
        assertThat("Coverage month check failed", isValid, is(true));
    }

    @Test
    public void testCoverageStartingFromDOB() {
        String dobString = "10042016";
        filerDemographicDto.setRecipientDob(BatchUtils.getDate(dobString));
        Date originalCoverageBeginDate = filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt();
        boolean isValid = BatchUtils.isCoverageMonthValid(originalCoverageBeginDate, filerDemographicDto.getRecipientDob());
        assertThat("Coverage month check failed", isValid, is(true));
    }

    @Test
    public void testCoverageForSameMonthAsDOB() {
        String dobString = "02152016";
        String coverageBegin = "02012016";
        filerDemographicDto.setRecipientDob(BatchUtils.getDate(dobString));
        filerDemographicDto.getFilerCoverage().setOrigCoverageBeginDt(BatchUtils.getDate(coverageBegin));
        Date originalCoverageBeginDate = filerDemographicDto.getFilerCoverage().getOrigCoverageBeginDt();
        boolean isValid = BatchUtils.isCoverageMonthValid(originalCoverageBeginDate, filerDemographicDto.getRecipientDob());
        assertThat("Coverage month check failed", isValid, is(true));
    }

    /* Tests to check begin date falls before end date */
    @Test
    public void testCoverageBeginBeforeCoverageEnd() {
        String originalCoverageBeginDateString = "02212016";
        String originalCoverageEndDateString = "04212017";
        boolean isValid = BatchUtils.isBeginDateBeforeEndDate(BatchUtils.getDate(originalCoverageBeginDateString), BatchUtils.getDate(originalCoverageEndDateString));
        assertThat("Coverage begin and end date test failure", isValid, is(true));
    }

    @Test
    public void testCoverageBeginAfterCoverageEnd() {
        String originalCoverageBeginDateString = "04212017";
        String originalCoverageEndDateString = "02212016";
        boolean isValid = BatchUtils.isBeginDateBeforeEndDate(BatchUtils.getDate(originalCoverageBeginDateString), BatchUtils.getDate(originalCoverageEndDateString));
        assertThat("Coverage begin and end date test failure", isValid, is(false));
    }

    @Test
    public void testCoverageBeginEqualsCoverageEnd() {
        String originalCoverageBeginDateString = "02212016";
        String originalCoverageEndDateString = "02212016";
        boolean isValid = BatchUtils.isBeginDateBeforeEndDate(BatchUtils.getDate(originalCoverageBeginDateString), BatchUtils.getDate(originalCoverageEndDateString));
        assertThat("Coverage begin and end date test failure", isValid, is(true));
    }

    /* Shop Identifier relates tests */
    @Test
    public void testNullShopIdentifier() {
        filerDemographicDto.setShopIdentifier(null);
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testShopIdentifierWithSpace() {
        filerDemographicDto.setShopIdentifier(" ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER24.1: SHOP_IDENTIFIER cannot have a value");
    }

    @Test
    public void testShopIdentifierWithSpaces() {
        filerDemographicDto.setShopIdentifier("   ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER24.1: SHOP_IDENTIFIER cannot have a value");
    }

    @Test
    public void testShopIdentifierWithNonSpaces() {
        filerDemographicDto.setShopIdentifier("abc123*^");
        constraintViolations = validator.validate(filerDemographicDto);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER24.1: SHOP_IDENTIFIER cannot have a value");
    }
}
