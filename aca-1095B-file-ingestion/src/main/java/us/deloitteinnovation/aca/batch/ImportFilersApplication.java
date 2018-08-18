package us.deloitteinnovation.aca.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.deloitteinnovation.aca.CommonConfiguration;

/**
 * Created by bhchaganti on 8/31/2016.
 */
@SpringBootApplication
@ComponentScan(
        basePackages = "us.deloitteinnovation.aca.batch",
        excludeFilters = @ComponentScan.Filter(Configuration.class))
@Import({CommonConfiguration.class, FileIngestionConfiguration.class, FileIngestionJobConfiguration.class})
public class ImportFilersApplication {


    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(ImportFilersApplication.class, args)));
    }
}
