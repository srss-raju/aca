/**
 * 
 */
package us.deloitteinnovation.aca.batch.ingest.step2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

/**
 * @author gacche
 *
 */
public class Step2VerifyCommunicationPreferencesTest extends Step2VerifyFilerProcessorTest {

	 	@Test
	    public void testCommunicationPreferenceWithValidValue() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("E");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
	    }
	 	@Test
	    public void testCommunicationPreferenceWithValidLowerCaseCharValue() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("e");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
	    }
	 	@Test
	    public void testCommunicationPreferenceWithInvalidCharValue() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("G");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWithNull() throws Exception {
		 	filerDemographicDto.setCommunicationPreference(null);
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWithSpace() throws Exception {
		 	filerDemographicDto.setCommunicationPreference(" ");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWithSpaces() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("   ");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWithNumber() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("5");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWith2Number() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("55");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWith2Char() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("EE");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWith3Char() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("EEE");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWith1SplChar() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("#");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWith2SplChar() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("#@");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWithAlphaNumeric() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("a256#@&");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWithNemericSplChar() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("#267*&^");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testCommunicationPreferenceWithCharSplChar() throws Exception {
		 	filerDemographicDto.setCommunicationPreference("S$RTY%$#");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWithValidValue() throws Exception {
		 	filerDemographicDto.setMailedForm("Y");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
	    }
	 	@Test
	    public void testMailedFormWithValidLoweCaseValue() throws Exception {
		 	filerDemographicDto.setMailedForm("y");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));
	    }
	 	@Test
	    public void testMailedFormWithInValidValue() throws Exception {
		 	filerDemographicDto.setMailedForm("S");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWithNullValue() throws Exception {
		 	filerDemographicDto.setMailedForm(null);
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWithSpace() throws Exception {
		 	filerDemographicDto.setMailedForm(" ");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWithSpaces() throws Exception {
		 	filerDemographicDto.setMailedForm("  ");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWith2Char() throws Exception {
		 	filerDemographicDto.setMailedForm("YY");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWith3Char() throws Exception {
		 	filerDemographicDto.setMailedForm("YYY");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWith1Number() throws Exception {
		 	filerDemographicDto.setMailedForm("4");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWith2Number() throws Exception {
		 	filerDemographicDto.setMailedForm("44");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWith1SplChar() throws Exception {
		 	filerDemographicDto.setMailedForm("#");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWith2SplChar() throws Exception {
		 	filerDemographicDto.setMailedForm("#@");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWithAlphaNumeric() throws Exception {
		 	filerDemographicDto.setMailedForm("a2dfg%^&*");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWithNemericSplChar() throws Exception {
		 	filerDemographicDto.setMailedForm("#234@#%");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
	 	@Test
	    public void testMailedFormWithCharSplChar() throws Exception {
		 	filerDemographicDto.setMailedForm("S$TYU&%^%");
	        constraintViolations = validator.validate(filerDemographicDto);
	        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(1));
	    }
}
