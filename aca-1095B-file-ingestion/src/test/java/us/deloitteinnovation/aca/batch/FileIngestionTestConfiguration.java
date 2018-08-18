package us.deloitteinnovation.aca.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import us.deloitteinnovation.aca.CommonConfiguration;
import us.deloitteinnovation.aca.JpaConfiguration;


/**
 * Created by bhchaganti on 8/17/2016.
 */

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@Import({FileIngestionConfiguration.class, JpaConfiguration.class, CommonConfiguration.class})

public class FileIngestionTestConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileIngestionTestConfiguration.class);

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

}