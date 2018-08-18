package us.deloitteinnovation.aca.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;

import static org.junit.Assert.* ;


/**
 * @see FilerMapper
 */
public class FilerMapperTest extends AbstractCommonTestConfig {

    FilerMapper filerMapper ;

    @Before
    public void before() {
        filerMapper = new FilerMapper() ;
    }

    @Test
    public void determineMonthStatus() {
        assertEquals(CommonEntityConstants.COVERED, filerMapper.determineMonthStatus("1")) ;
        assertEquals(CommonEntityConstants.UNCOVERED, filerMapper.determineMonthStatus("0")) ;
        assertEquals(CommonEntityConstants.UNCOVERED, filerMapper.determineMonthStatus(null)) ;
        assertEquals(CommonEntityConstants.UNCOVERED, filerMapper.determineMonthStatus("" + System.currentTimeMillis())) ;
    }

}
