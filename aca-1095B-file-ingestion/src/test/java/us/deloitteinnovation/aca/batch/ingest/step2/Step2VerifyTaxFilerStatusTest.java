/**
 * 
 */
package us.deloitteinnovation.aca.batch.ingest.step2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Date;

import org.junit.Test;

import us.deloitteinnovation.aca.batch.utils.BatchUtils;

/**
 * @author gacche
 *
 */
public class Step2VerifyTaxFilerStatusTest extends Step2VerifyFilerProcessorTest {
	@Test
    public void testFilerStatusWithValidValue() throws Exception {
	 	filerDemographicDto.setFilerStatus("R");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
 	@Test
    public void testFilerStatusWithValidLowerCaseCharValue() throws Exception {
	 	filerDemographicDto.setFilerStatus("r");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
 	@Test
    public void testFilerStatusWithInvalidCharValue() throws Exception {
	 	filerDemographicDto.setFilerStatus("G");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWithNull() throws Exception {
	 	filerDemographicDto.setFilerStatus(null);
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWithSpace() throws Exception {
	 	filerDemographicDto.setFilerStatus(" ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWithSpaces() throws Exception {
	 	filerDemographicDto.setFilerStatus("   ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWithNumber() throws Exception {
	 	filerDemographicDto.setFilerStatus("5");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWith2Number() throws Exception {
	 	filerDemographicDto.setFilerStatus("55");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWith2Char() throws Exception {
	 	filerDemographicDto.setFilerStatus("EE");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWith3Char() throws Exception {
	 	filerDemographicDto.setFilerStatus("EEE");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWith1SplChar() throws Exception {
	 	filerDemographicDto.setFilerStatus("#");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWith2SplChar() throws Exception {
	 	filerDemographicDto.setFilerStatus("#@");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWithAlphaNumeric() throws Exception {
	 	filerDemographicDto.setFilerStatus("a2");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWithNemericSplChar() throws Exception {
	 	filerDemographicDto.setFilerStatus("#2");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testFilerStatusWithCharAlpha() throws Exception {
	 	filerDemographicDto.setFilerStatus("S$");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	
 	@Test
    public void testResponsiblePersonUniqueIdWithValidLengthValue() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("220024261234568");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWith1Number() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("5");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWith2Number() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("55");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWith3Number() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("555");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWithInValidLengthValue() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("2200242612343456");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWithNullValue() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId(null);
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWithSpace() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId(" ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWithSpaces() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("  ");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWith1Char() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("S");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWith2Char() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("SS");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWith1SplChar() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("#");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWith2SplChar() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("#@");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWithAlphaNumeric() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("a2445bb");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWithNemericSplChar() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("#%^&2567");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWithCharSplChar() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("SRTY$#$&*");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdWithMorethan15SplChar() throws Exception {
	 	filerDemographicDto.setResponsiblePersonUniqueId("S$!%^&*!@#$%^&*$%");
        constraintViolations = validator.validate(filerDemographicDto);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
    }
 	@Test
    public void testResponsiblePersonUniqueIdRecipientUniqueIdEqualWhenFilerStatusREqual() throws Exception {
 		filerDemographicDto.setResponsiblePersonUniqueId("220024261234568");
 		filerDemographicDto.getId().setSourceUniqueId("220024261234568");
 		boolean isValid = BatchUtils.isRespPersonUniqueIdRecipientUniqueIdEqual(filerDemographicDto.getResponsiblePersonUniqueId(), filerDemographicDto.getId().getSourceUniqueId());
 		assertThat("ResponsiblePersonId ReciUniqueId Equal Check Failed When FilerStatus is R", isValid, is(true));
    }
 	@Test
    public void testResponsiblePersonUniqueIdRecipientUniqueIdEqualWhenFilerStatusRUnEqual() throws Exception {
 		filerDemographicDto.setResponsiblePersonUniqueId("220024261234568");
 		filerDemographicDto.getId().setSourceUniqueId("220024261234567");
 		boolean isValid = BatchUtils.isRespPersonUniqueIdRecipientUniqueIdEqual(filerDemographicDto.getResponsiblePersonUniqueId(), filerDemographicDto.getId().getSourceUniqueId());
 		assertThat("ResponsiblePersonId ReciUniqueId Equal Check Failed When FilerStatus is R", isValid, is(false));
    }
 	@Test
    public void testResponsiblePersonUniqueIdMustExistsWhenFilerStatusCWithValue() throws Exception {
 		filerDemographicDto.setResponsiblePersonUniqueId("220024261234568");
 		boolean isValid = BatchUtils.isRespPersonUniqueIdExists(filerDemographicDto.getResponsiblePersonUniqueId());
 		assertThat("ResponsiblePersonId ReciUniqueId Equal Check Failed When FilerStatus is R", isValid, is(true));
    }
 	@Test
    public void testResponsiblePersonUniqueIdMustExistsWhenFilerStatusCWithNull() throws Exception {
 		filerDemographicDto.setResponsiblePersonUniqueId(null);
 		boolean isValid = BatchUtils.isRespPersonUniqueIdExists(filerDemographicDto.getResponsiblePersonUniqueId());
 		assertThat("ResponsiblePersonId ReciUniqueId Equal Check Failed When FilerStatus is R", isValid, is(false));
    }
}

