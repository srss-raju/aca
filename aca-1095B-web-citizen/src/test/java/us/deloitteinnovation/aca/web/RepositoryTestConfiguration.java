package us.deloitteinnovation.aca.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import us.deloitteinnovation.aca.CommonConfiguration;

import us.deloitteinnovation.aca.JpaConfiguration;
import us.deloitteinnovation.aca.batch.dao.FilerDemographicsDao;
import us.deloitteinnovation.aca.batch.dao.impl.FilerDemographicsDaoImpl;

/**
 */
@ActiveProfiles("dev")
@Import({CommonConfiguration.class})
@EnableJpaRepositories("us.deloitteinnovation.aca")
@EnableTransactionManagement
public class RepositoryTestConfiguration {
    @Autowired
    JdbcTemplate      jdbcTemplate;

    @Bean
    public FilerDemographicsDao filerDemographicsDao() {
        FilerDemographicsDaoImpl dao = new FilerDemographicsDaoImpl();
        dao.setJdbcTemplate(jdbcTemplate);
        return dao;
    }
}

