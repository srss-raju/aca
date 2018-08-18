package us.deloitteinnovation.aca;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import us.deloitteinnovation.aca.exception.PrintVendorFileNameVerificationDecider;
import us.deloitteinnovation.aca.exception.PrintVendorImportFilersJobExecutionListener;


/**
 * Configuration for jobs and flows.
 */

@Configuration
@Import(PrintVendorFileIngestionConfiguration.class)
public class PrintVendorFileIngestionJobConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(PrintVendorFileIngestionJobConfiguration.class);
    @Autowired
    public PrintVendorImportFilersJobExecutionListener printVendorImportFilersJobExecutionListener;
    @Autowired
    JobBuilderFactory jobs;
    
    @Autowired
    PrintVendorFileNameVerificationDecider printVendorFileNameVerificationDecider;


    

}
