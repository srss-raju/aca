package us.deloitteinnovation.aca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.deloitteinnovation.aca.batch.export.ExportBatchConfiguration;
import us.deloitteinnovation.aca.batch.export.ExportBatchJobConfiguration;

/**
 * ACA Export Application main entry point.
 */
@SpringBootApplication
@ComponentScan(basePackages = "us.deloitteinnovation.aca.batch", excludeFilters = @ComponentScan.Filter(Configuration.class))
@Import({CommonConfiguration.class, ExportBatchConfiguration.class, ExportBatchJobConfiguration.class})
public class Aca109495XmlExportApplication {
    public static void main(String[] args) {
        // System.exit is common for Batch applications since the exit code can be used to
        // drive a workflow
        System.exit(SpringApplication.exit(SpringApplication.run(Aca109495XmlExportApplication.class, args)));
    }
}
