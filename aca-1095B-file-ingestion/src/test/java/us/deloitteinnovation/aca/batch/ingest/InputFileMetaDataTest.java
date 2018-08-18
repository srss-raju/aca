package us.deloitteinnovation.aca.batch.ingest;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import us.deloitteinnovation.aca.batch.FileIngestionTestConfiguration;
import us.deloitteinnovation.aca.batch.file.InputFileMetaData;

import javax.validation.ConstraintViolation;
import java.util.ResourceBundle;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by bhchaganti on 8/17/2016.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FileIngestionTestConfiguration.class})
public class InputFileMetaDataTest {

    protected LocalValidatorFactoryBean validator;
    InputFileMetaData inputFileMetaData = null;

    @Before
    public void setUp() throws Exception {

        /* Setup the validator*/
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        ResourceBundle resourceBundle = ResourceBundle.getBundle("InputFileMetaDataTest");
        inputFileMetaData = new InputFileMetaData();
        inputFileMetaData.setAgencyCode(resourceBundle.getString("agencyCode"));
        inputFileMetaData.setStateCode(resourceBundle.getString("stateCode"));
        inputFileMetaData.setDate(resourceBundle.getString("date"));
        inputFileMetaData.setExtension(resourceBundle.getString("extension"));
        inputFileMetaData.setSystemCode(resourceBundle.getString("systemCode"));
        inputFileMetaData.setRecordCountInHeader(Integer.parseInt(resourceBundle.getString("recordCountInHeader")));
        inputFileMetaData.setTaxYear(resourceBundle.getString("taxYear"));
        inputFileMetaData.setVersion(resourceBundle.getString("version"));
        inputFileMetaData.setFileName(resourceBundle.getString("fileName"));

    }


    @Test
    @Ignore
    public void testFileNameMetaData() {
        Set<ConstraintViolation<InputFileMetaData>> constraintViolations = null;
        constraintViolations = validator.validate(inputFileMetaData);
        assertThat(getConstraintViolations(constraintViolations), constraintViolations.size(), is(0));

    }

    private String getConstraintViolations(Set<ConstraintViolation<InputFileMetaData>> constraintViolations) {

        StringBuffer constraintViolationsStringBuffer = new StringBuffer();
        for (ConstraintViolation<InputFileMetaData> violation : constraintViolations) {
            constraintViolationsStringBuffer.append(violation.getMessageTemplate() + ":" + violation.getMessage() + "\n");
        }

        return constraintViolationsStringBuffer.toString();
    }


    @After
    public void tearDown() throws Exception {
    }
}
