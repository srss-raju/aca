package us.deloitteinnovation.aca.batch.receipt;

import java.io.File;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import us.deloitteinnovation.aca.JpaConfiguration;
import us.deloitteinnovation.aca.batch.receipt.dto.ProcessReceiptDto;
import us.deloitteinnovation.aca.batch.receipt.dto.StepResult;
import us.deloitteinnovation.aca.batch.receipt.reader.ConfirmationFileProcessor;
import us.deloitteinnovation.aca.batch.receipt.reader.ConfirmationFileReader;
import us.deloitteinnovation.aca.batch.receipt.reader.ConfirmationFileWriter;
import us.deloitteinnovation.aca.batch.receipt.repository.PrintDetailsMailRepository;
import us.deloitteinnovation.aca.batch.receipt.step.ProcessReceiptProcessor;
import us.deloitteinnovation.aca.batch.receipt.step.ProcessReceiptReader;
import us.deloitteinnovation.aca.batch.receipt.step.ProcessReceiptWriter;
import us.deloitteinnovation.aca.batch.receipt.util.BatchHistoryCleanupTasklet;
import us.deloitteinnovation.aca.batch.receipt.util.ProcessReceiptConstants;
import us.deloitteinnovation.aca.repository.PrintVendorJdbcRepository;
import us.deloitteinnovation.profile.ProfileProperties;

/**
 * This file reads the csv file sent by Print Vendor and updates the status in database according to information provided in the file
 *
 * @author RajeshKumar B
 *
 */
@Configuration
@EnableBatchProcessing
@Import(JpaConfiguration.class)
public class ProcessReceiptConfiguration {

    static final Logger LOGGER = LoggerFactory.getLogger(ProcessReceiptConfiguration.class);

    @Autowired
    JobBuilderFactory jobs;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    DataSource dataSource;
    @Autowired
    ProfileProperties profileProperties;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public PrintDetailsMailRepository printDetailsMailRepository() {
        final PrintDetailsMailRepository repo = new PrintDetailsMailRepository();
        repo.setJdbcTemplate(jdbcTemplate);
        return repo;
    }

    @Bean
    @StepScope
    public ProcessReceiptReader processReceiptReader() {
        return new ProcessReceiptReader();
    }

    @Bean
    @StepScope
    public ProcessReceiptProcessor processReceiptProcessor() {
        return new ProcessReceiptProcessor();
    }

    @Bean
    @StepScope
    public ProcessReceiptWriter processReceiptWriter() {
        return new ProcessReceiptWriter();
    }

    @Bean
    public ProcessReceiptListener processReceiptListener() {
        return new ProcessReceiptListener();
    }

    @Bean
    @StepScope
    public BatchHistoryCleanupTasklet batchHistoryCleanupTasklet() {
        final BatchHistoryCleanupTasklet step5Tasklet = new BatchHistoryCleanupTasklet();
        step5Tasklet.setJdbcTemplate(jdbcTemplate);
        return step5Tasklet;
    }

    @Bean
    public Step process1095BReceiptFile(ProcessReceiptReader readReceiptFile, ProcessReceiptProcessor processReceiptProcessor,
            ProcessReceiptWriter processReceiptWriter, ProcessReceiptListener processReceiptListener) {
        return stepBuilderFactory.get("process1095BReceiptFile").<ProcessReceiptDto, ProcessReceiptDto> chunk(ProcessReceiptConstants.RECORDSSIZE)
                .reader(readReceiptFile).processor(processReceiptProcessor).writer(processReceiptWriter).listener(processReceiptListener).build();
    }

    @Bean
    public Step cleanupBatchMetaData(BatchHistoryCleanupTasklet cleanupTasklet) {
        return stepBuilderFactory.get("cleanupBatchMetaData").tasklet(cleanupTasklet).build();
    }

    @Bean
    public Step confirmationFileProcessStep(ConfirmationFileReader confirmationFileReader, ConfirmationFileProcessor confirmationFileProcessor,
            ConfirmationFileWriter confirmationFileWriter, ProcessReceiptListener processReceiptListener) {
        return stepBuilderFactory.get("confirmationFileProcessStep").<File, StepResult> chunk(1).reader(confirmationFileReader)
                .processor(confirmationFileProcessor).writer(confirmationFileWriter).build();
    }

    @Bean
    public ConfirmationFileReader confirmationFileReader() {
        return new ConfirmationFileReader();
    }

    @Bean
    public ConfirmationFileProcessor confirmationFileProcessor() {
        return new ConfirmationFileProcessor();
    }

    @Bean
    public ConfirmationFileWriter confirmationFileWriter() {
        return new ConfirmationFileWriter();
    }

    @Bean
    public PrintVendorJdbcRepository printVendorJdbcRepository() {
        final PrintVendorJdbcRepository printVendorJdbcRepository = new PrintVendorJdbcRepository();
        printVendorJdbcRepository.setJdbcTemplate(jdbcTemplate);
        return printVendorJdbcRepository;
    }

}
