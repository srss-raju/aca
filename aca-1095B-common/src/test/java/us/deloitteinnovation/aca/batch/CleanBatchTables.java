package us.deloitteinnovation.aca.batch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;
import us.deloitteinnovation.aca.CommonConfiguration;
import us.deloitteinnovation.aca.JpaConfiguration;
import us.deloitteinnovation.profile.ProfileConfiguration;

import javax.sql.DataSource;

/**
 * Created by ergreen on 11/9/2015.
 */
public class CleanBatchTables extends AbstractCommonTestConfig {

    @Autowired
    private DataSource dataSource;

    /** Drops the batch job database */
    @Value("classpath:/org/springframework/batch/core/schema-drop-sqlserver.sql")
    private Resource mysqlDropSqlResource;

    /** Initializes the batch job database */
    @Value("classpath:/org/springframework/batch/core/schema-sqlserver.sql")
    private Resource mysqlSqlResource;

    @Before
    public void setUp() {
    }

    @Test
    public void cleanBatchTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(mysqlDropSqlResource);  // drop existing
        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}
