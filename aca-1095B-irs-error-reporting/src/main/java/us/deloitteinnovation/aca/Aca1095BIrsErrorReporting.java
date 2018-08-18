package us.deloitteinnovation.aca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.deloitteinnovation.aca.batch.IrsErrorReportingConfiguration;

/**
 * Created by tthakore on 3/25/2016.
 * This will be intial class for IRS error report generation.
 * primary task of this class is of intialise spring framework and import common module.
 */

@SpringBootApplication
@ComponentScan(basePackages = "us.deloitteinnovation.aca", excludeFilters = @ComponentScan.Filter(Configuration.class))
@Import({CommonConfiguration.class, IrsErrorReportingConfiguration.class})
public class Aca1095BIrsErrorReporting {

    public static void main(String[] args) {
        // System.exit is common for Batch applications since the exit code can be used to
        // drive a workflow followed export module for coding practice.
        System.exit(SpringApplication.exit(SpringApplication.run(Aca1095BIrsErrorReporting.class, args)));
    }
}
