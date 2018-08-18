package us.deloitteinnovation.aca.batch.invalidaddress;

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
import us.deloitteinnovation.aca.batch.invalidaddress.repository.InvalidAddressRepository;
import us.deloitteinnovation.aca.batch.invalidaddress.step.InvalidAddressProcessor;
import us.deloitteinnovation.aca.batch.invalidaddress.step.InvalidAddressReader;
import us.deloitteinnovation.aca.batch.invalidaddress.step.InvalidAddressWriter;
import us.deloitteinnovation.aca.batch.invalidaddress.step.StepResult;
import us.deloitteinnovation.aca.batch.invalidaddress.util.BatchHistoryCleanupTasklet;
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
public class InvalidAddressConfiguration {

    static final Logger LOGGER = LoggerFactory.getLogger(InvalidAddressConfiguration.class);

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
    public InvalidAddressListener invalidAddressListener() {
        return new InvalidAddressListener();
    }

    @Bean
    public InvalidAddressRepository invalidAddressRepository() {
        final InvalidAddressRepository repo = new InvalidAddressRepository();
        repo.setJdbcTemplate(jdbcTemplate);
        return repo;
    }

    @Bean
    @StepScope
    public BatchHistoryCleanupTasklet batchHistoryCleanupTasklet() {
        final BatchHistoryCleanupTasklet step5Tasklet = new BatchHistoryCleanupTasklet();
        step5Tasklet.setJdbcTemplate(jdbcTemplate);
        return step5Tasklet;
    }

    @Bean
    public Step cleanupBatchMetaData(BatchHistoryCleanupTasklet cleanupTasklet) {
        return stepBuilderFactory.get("cleanupBatchMetaData").tasklet(cleanupTasklet).build();
    }

    @Bean
    public Step invalidAddressStep(InvalidAddressReader invalidAddressReader, InvalidAddressProcessor invalidAddressProcessor,
    		InvalidAddressWriter invalidAddressWriter, InvalidAddressListener invalidAddressListener) {
        return stepBuilderFactory.get("invalidAddressStep").<File, StepResult> chunk(1).reader(invalidAddressReader)
                .processor(invalidAddressProcessor).writer(invalidAddressWriter).build();
    }

    @Bean
    public InvalidAddressReader invalidAddressReader() {
        return new InvalidAddressReader();
    }

    @Bean
    public InvalidAddressProcessor invalidAddressProcessor() {
        return new InvalidAddressProcessor();
    }

    @Bean
    public InvalidAddressWriter invalidAddressWriter() {
        return new InvalidAddressWriter();
    }
    
    @Bean
    public PrintVendorJdbcRepository printVendorJdbcRepository() {
        final PrintVendorJdbcRepository repo = new PrintVendorJdbcRepository();
        repo.setJdbcTemplate(jdbcTemplate);
        return repo;
    }

}
