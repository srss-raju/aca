package us.deloitteinnovation.aca;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by yaojia on 11/11/2016.
 */

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "us.deloitteinnovation.aca.entity")
@EnableJpaRepositories(basePackages = "us.deloitteinnovation.aca")
@Profile({"test"})
public class LocalJpaConfiguration extends JpaConfiguration {

    @Bean
    @Override
    public String password() {
        return String.valueOf(profileProperties.getDbPassword());
    }
}
