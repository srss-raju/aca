package us.deloitteinnovation.aca.batch.receipt;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessReceiptBatchJobConfiguration {

	@Autowired
	JobBuilderFactory jobs;

	@Autowired
	Step process1095BReceiptFile;
	
	@Autowired
    Step cleanupBatchMetaData;
	
	@Autowired
	ProcessReceiptListener processReceiptListener;
	
	@Autowired
    Step confirmationFileProcessStep;
	
	@Bean
	public Job process1095BReceiptFiles() {
		return jobs.get("process1095BReceiptFiles").preventRestart()
				.incrementer(new RunIdIncrementer())
				.listener(processReceiptListener)
				.flow(process1095BReceiptFile)
				.next(cleanupBatchMetaData)
				.end()
				.build();
	}
	
	@Bean
    public Job processConfirmationFiles() {
        return jobs.get("processConfirmationFiles").preventRestart().incrementer(new RunIdIncrementer()).listener(processReceiptListener)
                .flow(confirmationFileProcessStep).next(cleanupBatchMetaData).end().build();
    }

}
