package us.deloitteinnovation.aca.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import us.deloitteinnovation.aca.JpaConfiguration;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.constants.FileIngestionConstants;
import us.deloitteinnovation.aca.batch.dao.BatchInfoDao;
import us.deloitteinnovation.aca.batch.dao.impl.BatchInfoDaoImpl;
import us.deloitteinnovation.aca.batch.dto.BatchCountDto;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.exception.BatchImportException;
import us.deloitteinnovation.aca.batch.exception.FileVerificationSkipper;
import us.deloitteinnovation.aca.batch.file.filter.FileExtensionFilter;
import us.deloitteinnovation.aca.batch.ingest.step1.Step1RefreshStagingTasklet;
import us.deloitteinnovation.aca.batch.ingest.step2.FileNameVerificationDecider;
import us.deloitteinnovation.aca.batch.ingest.step2.Step2VerifyFilerProcessor;
import us.deloitteinnovation.aca.batch.ingest.step2.Step2VerifyFilerWriter;
import us.deloitteinnovation.aca.batch.ingest.step3.Step3DataValidationException;
import us.deloitteinnovation.aca.batch.ingest.step3.Step3FileIngestionCRVProcessor;
import us.deloitteinnovation.aca.batch.ingest.step3.Step3FileIngestionCRVReader;
import us.deloitteinnovation.aca.batch.ingest.step3.Step3FileIngestionCRVWriter;
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
import us.deloitteinnovation.aca.batch.ingest.step4.Step4UpdateBatchCountsProcessor;
import us.deloitteinnovation.aca.batch.ingest.step4.Step4UpdateBatchCountsReader;
import us.deloitteinnovation.aca.batch.ingest.step4.Step4UpdateBatchCountsWriter;
import us.deloitteinnovation.aca.batch.listener.FilerVerificationSkipListener;
import us.deloitteinnovation.aca.batch.listener.ImportFilersJobExecutionListener;
import us.deloitteinnovation.aca.batch.mapper.DefaultLineMapper;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;
import us.deloitteinnovation.aca.batch.service.FileImportMailerService;
import us.deloitteinnovation.aca.batch.service.ReportGenerationService;
import us.deloitteinnovation.aca.batch.service.impl.BatchInfoServiceImpl;
import us.deloitteinnovation.aca.batch.service.impl.FileImportMailerServiceImpl;
import us.deloitteinnovation.aca.batch.service.impl.ReportGenerationServiceImpl;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.entity.FilerDemographicStagingEntity;
import us.deloitteinnovation.profile.ProfileProperties;

import javax.sql.DataSource;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;


/**
 * Configuration for various steps, readers, writers, processors and listeners
 * 1. Bean definitions should stay here
 * 2. Utility methods should move to a util package
 */

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@Import(JpaConfiguration.class)
@PropertySource("classpath:ValidationMessages.properties")
public class FileIngestionConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(us.deloitteinnovation.aca.batch.FileIngestionConfiguration.class);

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


    /* Step definitions */
    @Bean
    public Step step1RefreshStaging(Step1RefreshStagingTasklet step1RefreshStagingTasklet) {

        return stepBuilderFactory
                .get(FileIngestionConstants.StepConstants.REFRESH_STAGING_TASKLET_STEP)
                .tasklet(step1RefreshStagingTasklet)
                .build();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public Step step2VerifyFilerData(MultiResourceItemReader multiResourceItemReader,
                                     ItemProcessor step2VerifyFilerProcessor,
                                     ItemWriter step2VerifyFilerWriter,
                                     SkipPolicy fileVerificationSkipper
    ) {
        return stepBuilderFactory.get(FileIngestionConstants.StepConstants.VERIFY_FILER_DATA_STEP)
                .<FilerDemographicDto, FilerDemographicDto>chunk(500)
                .reader(multiResourceItemReader)
                .processor(step2VerifyFilerProcessor)
                .writer(step2VerifyFilerWriter)
                .faultTolerant()
                .skipPolicy(fileVerificationSkipper)
                .build();
    }

    /**
     * Record Level validation for CRV configured from here
     * step3 in file ingestion process.
     **/

    @Bean
    @Qualifier("step3ValidateFilerData")
    public Step step3ValidateFilerData(Step3FileIngestionCRVReader step3FileIngestionCRVReader,
                                       Step3FileIngestionCRVProcessor step3FileIngestionCRVProcessor,
                                       Step3FileIngestionCRVWriter step3FileIngestionCRVWriter,
                                       Step3FIExecutionListener step3FIExecutionListener,
                                       Step3RecordSkipListener step3RecordSkipListener) {

        return stepBuilderFactory.get(FileIngestionConstants.StepConstants.VALIDATE_FILER_DATA_STEP).listener(step3FIExecutionListener).<Step3FilerDataDto, Step3FilerDataDto>chunk(500)
                .reader(step3FileIngestionCRVReader)
                .processor(step3FileIngestionCRVProcessor)
                .writer(step3FileIngestionCRVWriter).faultTolerant()
                .skip(Exception.class).noRetry(Step3DataValidationException.class)
                .skipLimit(Integer.MAX_VALUE).listener(step3RecordSkipListener)
                .build();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public Step step4UpdateBatchCounts(Step4UpdateBatchCountsReader step4UpdateBatchCountsReader,
                                       Step4UpdateBatchCountsProcessor step4UpdateBatchCountsProcessor,
                                       Step4UpdateBatchCountsWriter step4UpdateBatchCountsWriter) {
        return stepBuilderFactory.get(FileIngestionConstants.StepConstants.UPDATE_BATCH_COUNTS_STEP)
                .<BatchCountDto, BatchCountDto>chunk(1)
                .reader(step4UpdateBatchCountsReader)
                .processor(step4UpdateBatchCountsProcessor)
                .writer(step4UpdateBatchCountsWriter)
                .build();
    }

    /* Step scope beans*/
    @Bean
    @StepScope
    public Step1RefreshStagingTasklet step1RefreshStagingTasklet() {
        Step1RefreshStagingTasklet step1RefreshStagingTasklet = new Step1RefreshStagingTasklet();
        step1RefreshStagingTasklet.setJdbcTemplate(jdbcTemplate);
        return step1RefreshStagingTasklet;
    }

    @Bean
    @Qualifier("multiResourceItemReader")
    @StepScope
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public MultiResourceItemReader multiResourceItemReader(
            FlatFileItemReader<FilerDemographicDto> applicationReader,
            @Value("#{jobParameters[STATE]}") String state
    ) {
        String stateInputFolderKey = state + BatchConstants.INPUT_FILE_FOLDER;
        String stateInputFolder = profileProperties.getProperty(stateInputFolderKey);
        MultiResourceItemReader reader = new MultiResourceItemReader();
        try {
            Resource[] fileSystemResource = this.getFileSystemResource(stateInputFolder);
            reader.setResources(fileSystemResource);
            reader.setDelegate(applicationReader);
        } catch (BatchImportException e) {
            LOGGER.error(e.getMessage());
        } finally {
            reader.close();
        }
        return reader;
    }

    private Resource[] getFileSystemResource(
            String inputFileFolder
    ) throws BatchImportException {
        Resource[] fileSystemResource = null;
        File fileDirectory = new File(inputFileFolder);

        File[] fileListInSourceFolder = fileDirectory.listFiles(new FileExtensionFilter(BatchConstants.FILE_FORMAT_EXTENSION));

        /* Pick up the oldest file;no matter which state it belongs to */
        Arrays.sort(fileListInSourceFolder, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
            }
        });

        fileSystemResource = new FileSystemResource[FileIngestionConstants.NUM_FILES_TO_PROCESS];
        int i = 0;
        for (File file : fileListInSourceFolder) {

            fileSystemResource[i] = new FileSystemResource(file.getAbsolutePath());
            break;
        }
        return fileSystemResource;
    }

    /**
     * @return
     */
    @Bean
    @Qualifier("applicationReader")
    @StepScope
    public FlatFileItemReader<FilerDemographicDto> applicationReader() {
        FlatFileItemReader<FilerDemographicDto> reader = new FlatFileItemReader<FilerDemographicDto>();
        reader.setLineMapper(lineMapper());
        reader.setLinesToSkip(1);
        reader.setStrict(true);
        return reader;
    }

    @Bean
    public LineMapper<FilerDemographicDto> lineMapper() {
        return new DefaultLineMapper<>();
    }

    @Bean
    public DelimitedLineTokenizer delimitedLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(true);
        lineTokenizer.setQuoteCharacter('¬');//Use this instead of regular quotes to avoid open ended double quotes
        lineTokenizer.setNames(new String[]{
                BatchConstants.RECIPIENT_CASE_APPLICATION_ID,
                BatchConstants.RECIPIENT_UNIQUE_ID,
                BatchConstants.TAX_YEAR,
                CommonEntityConstants.CORRECTION,
                BatchConstants.RECIPIENT_FIRST_NAME,
                BatchConstants.RECIPIENT_MIDDLE_NAME,
                BatchConstants.RECIPIENT_LAST_NAME,
                BatchConstants.RECIPIENT_NAME_SUFFIX,
                CommonDataConstants.RECIPIENT_SSN,
                CommonDataConstants.RECIPIENT_TIN,
                CommonDataConstants.RECIPIENT_DOB,
                BatchConstants.RECIPIENT_LANGUAGE_PREFERENCE,
                BatchConstants.EMAIL,
                BatchConstants.RECIPIENT_ADDRESS_LINE_1,
                BatchConstants.RECIPIENT_ADDRESS_LINE_2,
                BatchConstants.RECIPIENT_CITY,
                BatchConstants.RECIPIENT_STATE_CODE,
                BatchConstants.RECIPIENT_ZIP_5,
                BatchConstants.RECIPIENT_ZIP_4,
                BatchConstants.POLICY_ORIGIN,
                BatchConstants.POLICY_PROGRAM_NAME,
                BatchConstants.POLICY_COVERAGE_BEGIN_DT,
                BatchConstants.POLICY_COVERAGE_END_DT,
                BatchConstants.POLICY_SHOP_IDENTIFIER,
                BatchConstants.EMPLOYER_NAME,
                BatchConstants.EMPLOYER_IDENTIFICATION_NUMBER,
                BatchConstants.EMPLOYER_CONTACT_NO,
                BatchConstants.EMPLOYER_ADDRESS_LINE_1,
                BatchConstants.EMPLOYER_ADDRESS_LINE_2,
                BatchConstants.EMPLOYER_CITY_OR_TOWN,
                BatchConstants.EMPLOYER_STATE_OR_PROVINCE,
                BatchConstants.EMPLOYER_COUNTRY,
                BatchConstants.EMPLOYER_ZIP_OR_FOREIGN_POSTAL_CODE,
                BatchConstants.PROVIDER_NAME,
                BatchConstants.PROVIDER_IDENTIFICATION_NUMBER,
                BatchConstants.PROVIDER_CONTACT_NUMBER,
                BatchConstants.PROVIDER_ADDRESS_LINE_1,
                BatchConstants.PROVIDER_ADDRESS_LINE_2,
                BatchConstants.PROVIDER_CITY_OR_TOWN,
                BatchConstants.PROVIDER_STATE_OR_PROVINCE,
                BatchConstants.PROVIDER_COUNTRY,
                BatchConstants.PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE,
                BatchConstants.FILER_STATUS,
                BatchConstants.RESPONSIBLE_PERSON_UNIQUE_ID,
                BatchConstants.COMM_PREFERENCE,
                BatchConstants.MAILED_FORM});
        return lineTokenizer;
    }

    @Bean
    @StepScope
    public ItemProcessor<FilerDemographicDto, FilerDemographicStagingEntity> step2VerifyFilerProcessor() {
        LOGGER.info("Start of ItemProcessor");
        ItemProcessor applicationProcessor = new Step2VerifyFilerProcessor();
        LOGGER.info("End of ItemProcessor");
        return applicationProcessor;
    }

    @Bean
    @StepScope
    public ItemWriter<FilerDemographicStagingEntity> step2VerifyFilerWriter() {
        LOGGER.info("Start of ItemWriter");
        Step2VerifyFilerWriter applicationWriter = new Step2VerifyFilerWriter();
        LOGGER.info("End of ItemWriter");
        return applicationWriter;
    }

    @Bean
    @StepScope
    public Step3InitialFilerList step3InitialFilerList() {
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
    public Step3RecordSkipListener step3RecordSkipListener() {
        return new Step3RecordSkipListener();
    }

    @Bean
    @StepScope
    public Step3OriginalRecordsValidationUtils step3OriginalRecordsValidationUtils() {
        return new Step3OriginalRecordsValidationUtils();
    }

    @Bean
    @StepScope
    public Step3UpdateRecordsValidationUtils step3UpdateRecordsValidationUtils() {
        return new Step3UpdateRecordsValidationUtils();
    }

    @Bean
    @StepScope
    public Step3RecordsUIDValidationResultMap step3RecordsUIDValidationResultMap() {
        return new Step3RecordsUIDValidationResultMap();
    }

    /* Added bean for correction validation utils*/
    @Bean
    @StepScope
    public Step3CorrectedRecordsValidationUtils step3CorrectedRecordsValidationUtils() {
        return new Step3CorrectedRecordsValidationUtils();
    }

    @Bean
    @StepScope
    public Step4UpdateBatchCountsReader step4UpdateBatchCountsReader() {
        return new Step4UpdateBatchCountsReader();
    }

    @Bean
    @StepScope
    public Step4UpdateBatchCountsProcessor step4UpdateBatchCountsProcessor() {
        return new Step4UpdateBatchCountsProcessor();
    }

    @Bean
    @StepScope
    public Step4UpdateBatchCountsWriter step4UpdateBatchCountsWriter() {
        return new Step4UpdateBatchCountsWriter();
    }

    /**
     * @return
     */
    @Bean
    @JobScope
    public ImportFilersJobExecutionListener importFilersJobExecutionListener() {
        return new ImportFilersJobExecutionListener();
    }

    @Bean
    @JobScope
    public FileNameVerificationDecider fileNameVerificationDecider() {
        return new FileNameVerificationDecider();
    }

    @Bean
    public Validator mvcValidator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public SkipPolicy fileVerificationSkipper() {
        return new FileVerificationSkipper();
    }

    @Bean
    JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(profileProperties.getProperty(BatchConstants.MAIL_HOST));
        int port = Integer.parseInt(profileProperties.getProperty(BatchConstants.MAIL_PORT));
        javaMailSender.setPort(port);
        /*javaMailSender.setUsername(profileProperties.getProperty(BatchConstants.MAIL_USER_NAME));
        javaMailSender.setPassword(profileProperties.getProperty(BatchConstants.MAIL_PASSWORD));*/
        javaMailSender.setProtocol(profileProperties.getProperty(BatchConstants.MAIL_PROTOCOL));
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", profileProperties.getProperty(BatchConstants.MAIL_PROTOCOL));
        javaMailProperties.put("mail.smtp.auth", false);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.debug", true);
        javaMailProperties.put("mail.smtp.ssl.trust", profileProperties.getProperty(BatchConstants.MAIL_HOST));
        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean
      public FileImportMailerService applicationMailerService() {
        FileImportMailerService fileImportMailerService = new FileImportMailerServiceImpl();
        return fileImportMailerService;
    }

    /**
     * @return
     */
    @Bean
    @JobScope
    public BatchInfoDto getBatchInfo(BatchInfoService batchInfoService) {
        LOGGER.info("Start of BatchInfo");
        BatchInfoDto batchInfo = new BatchInfoDto();
        batchInfo.setReceiveDt(new Date());
        int batchId = batchInfoService.save(batchInfo);
        batchInfo.setBatchId(batchId);
        LOGGER.info("End of BatchInfo");
        return batchInfo;
    }



    @Bean
    public BatchInfoService batchInfoService() {
        return new BatchInfoServiceImpl();
    }

    @Bean
    public BatchInfoDao batchInfoDao() {
        BatchInfoDaoImpl batchInfoDao = new BatchInfoDaoImpl();
        batchInfoDao.setJdbcTemplate(jdbcTemplate);
        return batchInfoDao;
    }


 /*   @Bean
    public FileIngestionService fileIngestionDao() {
        FileIngestionServiceImpl fileIngestionService = new FileIngestionServiceImpl();
        fileIngestionService.setJdbcTemplate(jdbcTemplate);
        return fileIngestionService;
    }*/


    @Bean
    public FilerVerificationSkipListener filerVerificationSkipListener() {
        return new FilerVerificationSkipListener();
    }


    @Bean
    public ReportGenerationService reportGenerationService() {
        final ReportGenerationServiceImpl reportGenerationService = new ReportGenerationServiceImpl();
        reportGenerationService.setJdbcTemplate(jdbcTemplate);
        return reportGenerationService;
    }

}
