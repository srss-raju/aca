package us.deloitteinnovation.aca.util;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 */
@ActiveProfiles({"test"})
public class ConvertTest {

    @Test
    public void zipFull() throws Exception {
        String zip = "123456789" ;
        String [] zipFull = Convert.toZipCode(zip) ;
        assertEquals("12345", zipFull[0]) ;
        assertEquals("6789", zipFull[1]) ;
    }

    @Test
    public void zipFullWithDash() throws Exception {
        String zip = "12345-6789" ;
        String [] zipFull = Convert.toZipCode(zip) ;
        assertEquals("12345", zipFull[0]) ;
        assertEquals("6789", zipFull[1]) ;
    }

    @Test
    public void zipFullWithDashIncorrect() throws Exception {
        String zip = "1234567-89" ;
        String [] zipFull = Convert.toZipCode(zip) ;
        assertEquals("12345", zipFull[0]) ;
        assertEquals("6789", zipFull[1]) ;
    }

    @Test
    public void zipPartial() throws Exception {
        String zip = "123456" ;
        String [] zipFull = Convert.toZipCode(zip) ;
        assertEquals("12345", zipFull[0]) ;
        assertEquals("6", zipFull[1]) ;
    }

    @Test
    public void zip5Digit() throws Exception {
        String zip = "12345" ;
        String [] zipFull = Convert.toZipCode(zip) ;
        assertEquals("12345", zipFull[0]) ;
        assertNull(zipFull[1]) ;
    }

    @Test
    public void zipNull() throws Exception {
        String zip = null ;
        String [] zipFull = Convert.toZipCode(zip) ;
        assertNull(zipFull[0]) ;
        assertNull(zipFull[1]) ;
    }

    @Test
    public void zip1Digit() throws Exception {
        String zip = "1" ;
        String [] zipFull = Convert.toZipCode(zip) ;
        assertEquals("1", zipFull[0]);
        assertNull(zipFull[1]) ;
    }

}
