package us.deloitteinnovation.aca.batch;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yaojia on 11/10/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"dev"})
@ContextConfiguration(classes = {FileIngestionTestConfiguration.class})
@Component
public abstract class AbstractFileIngestionTestCase {

}
