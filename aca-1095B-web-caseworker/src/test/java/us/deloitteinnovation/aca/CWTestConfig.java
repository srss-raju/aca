package us.deloitteinnovation.aca;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by yaojia on 11/10/2016.
 */

@Configuration
@Profile("test")
@ComponentScan(basePackages = {"us.deloitteinnovation.aca"})
public class CWTestConfig {

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
