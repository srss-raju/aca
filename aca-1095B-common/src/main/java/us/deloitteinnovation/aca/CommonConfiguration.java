package us.deloitteinnovation.aca;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import us.deloitteinnovation.profile.ProfileConfiguration;

/**
 * ACA Common Application Configuration
 *importing ProfileConfigurationACA for including new preprod profile as well
 * @author ltornquist
 * @since 6/8/2015
 */
@SpringBootApplication
@ComponentScan(basePackages = {"us.deloitteinnovation.aca"})
@Import({ProfileConfiguration.class, JpaConfiguration.class, PrintVendorFileIngestionConfiguration.class,PrintVendorFileIngestionJobConfiguration.class})
public class CommonConfiguration {}