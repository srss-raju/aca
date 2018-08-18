package us.deloitteinnovation.aca.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.deloitteinnovation.aca.CommonConfiguration;
import us.deloitteinnovation.aca.JpaConfiguration;
import us.deloitteinnovation.aca.batch.export.ExportBatchJobConfiguration;

/**
 * Unit test config for Export module
 *
 * @author yaojia
 * @since 2.1.0
 */

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@Import({ExportBatchJobConfiguration.class, JpaConfiguration.class, CommonConfiguration.class})
public class ExportTestConfiguration {
}
