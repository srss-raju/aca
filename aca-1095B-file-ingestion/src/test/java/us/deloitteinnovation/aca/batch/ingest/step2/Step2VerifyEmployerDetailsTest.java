package us.deloitteinnovation.aca.batch.ingest.step2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Ignore;
import org.junit.Test;

import us.deloitteinnovation.aca.batch.utils.BatchUtils;

/**
 * Created by bhchaganti on 10/10/2016.
 */
public class Step2VerifyEmployerDetailsTest extends Step2VerifyFilerProcessorTest {

	@Test
    public void testEmployerDetailsWithCityNull() throws Exception {
	 	filerDemographicDto.setEmployerCityOrTown(null);
	 	boolean isValid = BatchUtils.isEmployerCityExists(filerDemographicDto.getEmployerCityOrTown());
	 	assertThat("Employer city null check passed", isValid, is(true));
    }
	@Test
    public void testEmployerDetailsWithCityNotNull() throws Exception {
		filerDemographicDto.setEmployerCityOrTown("XYZ");
	 	boolean isValid = BatchUtils.isEmployerCityExists(filerDemographicDto.getEmployerCityOrTown());
	 	assertThat("Employer city not null check failed", isValid, is(false));
    }
 	@Test
    public void testEmployerDetailsWithCitySpace() throws Exception {
	 	filerDemographicDto.setEmployerCityOrTown(" ");
	 	boolean isValid = BatchUtils.isEmployerCityExists(filerDemographicDto.getEmployerCityOrTown());
	 	assertThat("Employer city with space check failed", isValid, is(false));
    }
 	@Test
    public void testEmployerDetailsWithCitySpaces() throws Exception {
	 	filerDemographicDto.setEmployerCityOrTown("   ");
	 	boolean isValid = BatchUtils.isEmployerCityExists(filerDemographicDto.getEmployerCityOrTown());
	 	assertThat("Employer city with spaces check failed", isValid, is(false));
    }
    
}
