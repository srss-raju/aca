package us.deloitteinnovation.aca.batch.invalidaddress;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvalidAddressBatchJobConfiguration {

	@Autowired
	JobBuilderFactory jobs;

	@Autowired
    Step cleanupBatchMetaData;
	
	@Autowired
    Step invalidAddressStep;
	
	@Autowired
	InvalidAddressListener invalidAddressListener;
	
	@Bean
    public Job invalidAddressFiles() {
        return jobs.get("invalidAddressFiles").preventRestart().incrementer(new RunIdIncrementer()).listener(invalidAddressListener).flow(invalidAddressStep).next(cleanupBatchMetaData).end().build();
    }

}
