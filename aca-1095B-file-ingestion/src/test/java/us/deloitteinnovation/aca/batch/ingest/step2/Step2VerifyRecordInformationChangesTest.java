package us.deloitteinnovation.aca.batch.ingest.step2;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by bhchaganti on 10/18/2016.
 */
public class Step2VerifyRecordInformationChangesTest extends Step2VerifyFilerProcessorTest {

    @Test
    public void testNullCorrectionCode() {
        filerDemographicDto.setCorrection(null);
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
    }

    @Test
    public void testCorrectionCodeWithSpace() {
        filerDemographicDto.setCorrection(" ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
    }

    @Test
    public void testCorrectionCodeWithSpaces() {
        filerDemographicDto.setCorrection("   ");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
    }

    @Test
    public void testCorrectionCodeWithLowerCaseOUC() {
        filerDemographicDto.setCorrection("o");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
        filerDemographicDto.setCorrection("u");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
        filerDemographicDto.setCorrection("c");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
    }

    @Test
    public void testCorrectionCodeWithUpperCaseOUC() {
        filerDemographicDto.setCorrection("O");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));

        filerDemographicDto.setCorrection("U");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));

        filerDemographicDto.setCorrection("C");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }

    @Test
    public void testCorrectionCodeWithUpperCaseDFE() {
        filerDemographicDto.setCorrection("D");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
        filerDemographicDto.setCorrection("F");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
        filerDemographicDto.setCorrection("E");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
    }

    @Test
    public void testCorrectionCodeWithLowerCaseDFE() {
        filerDemographicDto.setCorrection("d");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
        filerDemographicDto.setCorrection("f");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
        filerDemographicDto.setCorrection("e");
        assertFilerDemographicDto(filerDemographicDto, 1, "ER4.1: CORRECTION is a required field and must be capital O, U or C");
    }
}
