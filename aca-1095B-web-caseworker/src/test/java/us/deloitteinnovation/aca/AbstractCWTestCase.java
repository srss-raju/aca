package us.deloitteinnovation.aca;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yaojia on 11/10/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"test", "TEST"})
@SpringApplicationConfiguration(CWTestConfig.class)
@Component
public abstract class AbstractCWTestCase {

}
