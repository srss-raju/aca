package us.deloitteinnovation.aca.batch.ingest.step3;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import us.deloitteinnovation.aca.JpaConfiguration;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3InitialFilerList;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3RecordsUIDValidationResultMap;
import us.deloitteinnovation.aca.batch.ingest.step3.listeners.Step3FIExecutionListener;
import us.deloitteinnovation.aca.batch.ingest.step3.listeners.Step3RecordSkipListener;
import us.deloitteinnovation.aca.batch.ingest.step3.services.FileIngestionService;
import us.deloitteinnovation.aca.batch.ingest.step3.services.Step3RecordValidationService;
import us.deloitteinnovation.aca.batch.ingest.step3.services.impl.FileIngestionServiceImpl;
import us.deloitteinnovation.aca.batch.ingest.step3.services.impl.Step3RecordValidationServiceImpl;
import us.deloitteinnovation.aca.batch.ingest.step3.utils.Step3CorrectedRecordsValidationUtils;
import us.deloitteinnovation.aca.batch.ingest.step3.utils.Step3OriginalRecordsValidationUtils;
import us.deloitteinnovation.aca.batch.ingest.step3.utils.Step3UpdateRecordsValidationUtils;
import us.deloitteinnovation.profile.ProfileProperties;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by bhchaganti on 8/17/2016.
 */

@Configuration
@EnableBatchProcessing
@Import(JpaConfiguration.class)
@PropertySource("classpath:ValidationMessages.properties")
public class Step3IntgTestConfiguration {

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

    /**
     * Record Level validation for CRV configured from here
     * step3 in file ingestion process.
     **/

    @Bean
    public Step step3ValidateFilerData(Step3FileIngestionCRVReader step3FileIngestionCRVReader,
                                       Step3FileIngestionCRVProcessor step3FileIngestionCRVProcessor,
                                       Step3FileIngestionCRVWriter step3FileIngestionCRVWriter,
                                       Step3FIExecutionListener step3FIExecutionListener,Step3RecordSkipListener step3RecordSkipListener) {

        return stepBuilderFactory.get("step3ValidateFilerData").listener(step3FIExecutionListener).<Step3FilerDataDto, Step3FilerDataDto>chunk(500)
                .reader(step3FileIngestionCRVReader)
                .processor(step3FileIngestionCRVProcessor)
                .writer(step3FileIngestionCRVWriter).faultTolerant()
                .skip(Exception.class).noRetry(Step3DataValidationException.class)
                .skipLimit(Integer.MAX_VALUE).listener(step3RecordSkipListener)
                .build();
    }

    @Bean
    @StepScope
    public Step3InitialFilerList step3InitialFilerList(){
        return new Step3InitialFilerList();
    }


    @Bean
    @StepScope
    public Step3FileIngestionCRVReader step3FileIngestionCRVReader(Step3InitialFilerList step3InitialFilerList) {
        return new Step3FileIngestionCRVReader(step3InitialFilerList);
    }

    @Bean
    @StepScope
    public Step3FileIngestionCRVProcessor step3FileIngestionCRVProcessor() {
        return new Step3FileIngestionCRVProcessor();
    }

    @Bean
    @StepScope
    public Step3FileIngestionCRVWriter step3FileIngestionCRVWriter() {
        return new Step3FileIngestionCRVWriter();
    }



    @Bean
    @StepScope
    public Step3RecordValidationService step3RecordValidationService() {
        return new Step3RecordValidationServiceImpl();
    }

    @Bean
    @StepScope
    public Step3FIExecutionListener step3FIExecutionListener() {
        return new Step3FIExecutionListener();
    }

    @Bean
    public List<Step3FilerDataDto> initialStep3FilerList() {
        return new ArrayList<Step3FilerDataDto>();
    }

   /* @Bean
    @StepScope
    public FileIngestionService fileIngestionService() {
        FileIngestionServiceImpl fileIngestionService = new FileIngestionServiceImpl();
        fileIngestionService.setJdbcTemplate(jdbcTemplate);
        return fileIngestionService;
    }*/
    @Bean
    public Step3RecordSkipListener step3RecordSkipListener(){
        return new Step3RecordSkipListener();
    }

    @Bean
    @StepScope
    public Step3OriginalRecordsValidationUtils step3OriginalRecordsValidationUtils(){
        return new Step3OriginalRecordsValidationUtils();
    }

    @Bean
    @StepScope
    public Step3RecordsUIDValidationResultMap step3RecordsUIDValidationResultMap(){
        return new Step3RecordsUIDValidationResultMap();
    }

    @Bean
    @StepScope
    public Step3UpdateRecordsValidationUtils step3UpdateRecordsValidationUtils(){
        return new Step3UpdateRecordsValidationUtils();
    }

    /* Added bean for correction validation utils*/
    @Bean
    @StepScope
    public Step3CorrectedRecordsValidationUtils step3CorrectedRecordsValidationUtils() {
        return new Step3CorrectedRecordsValidationUtils();
    }

}
