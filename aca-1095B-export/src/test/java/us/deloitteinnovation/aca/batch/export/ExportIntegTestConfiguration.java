package us.deloitteinnovation.aca.batch.export;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import us.deloitteinnovation.aca.CommonConfiguration;
import us.deloitteinnovation.aca.FlatFileImportTestConfiguration;
import us.deloitteinnovation.aca.batch.dao.FilerDemographicsDao;
import us.deloitteinnovation.aca.batch.dao.impl.FilerDemographicsDaoImpl;

/**
 * Test-Specific Configurations
 */
@Configuration
@ActiveProfiles("dev")
@Import({CommonConfiguration.class, ExportBatchConfiguration.class, FlatFileImportTestConfiguration.class})
@EnableJpaRepositories("us.deloitteinnovation.aca.repository")
@EnableTransactionManagement
public class ExportIntegTestConfiguration {

    @Autowired
    JobBuilderFactory jobs;
    @Autowired
    Step              step1OriginalConvertFilers;
    @Autowired
    JdbcTemplate      jdbcTemplate;

    @Bean
    public FilerDemographicsDao filerDemographicsDao() {
        FilerDemographicsDaoImpl dao = new FilerDemographicsDaoImpl();
        dao.setJdbcTemplate(jdbcTemplate);
        return dao;
    }

}
