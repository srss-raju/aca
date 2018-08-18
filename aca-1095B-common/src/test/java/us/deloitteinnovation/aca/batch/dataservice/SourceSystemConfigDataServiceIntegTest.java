package us.deloitteinnovation.aca.batch.dataservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;
import us.deloitteinnovation.aca.CommonConfiguration;
import us.deloitteinnovation.aca.JpaConfiguration;
import us.deloitteinnovation.aca.model.SourceSystemConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 */
public class SourceSystemConfigDataServiceIntegTest extends AbstractCommonTestConfig {

    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService ;

    @Test
    public void testGetSourceSystemConfig() throws Exception {
        SourceSystemConfig config = sourceSystemConfigDataService.getSourceSystemConfig("INFSSICE") ;
        assertNotNull(config) ;
        assertEquals("INFSSICE", config.getSourceCd()) ;
    }
}