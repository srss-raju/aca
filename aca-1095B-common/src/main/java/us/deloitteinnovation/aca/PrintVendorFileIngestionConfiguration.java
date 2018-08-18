package us.deloitteinnovation.aca;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.*;
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
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dao.BatchInfoDao;
import us.deloitteinnovation.aca.batch.dao.PrintVendorExceptionReportDao;
import us.deloitteinnovation.aca.batch.dao.impl.BatchInfoDaoImpl;
import us.deloitteinnovation.aca.batch.dao.impl.PrintVendorExceptionReportDaoImpl;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.mapper.DefaultLineMapper;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;
import us.deloitteinnovation.aca.batch.service.PrintVendorExceptionReportService;
import us.deloitteinnovation.aca.batch.service.PrintVendorFileImportMailerService;
import us.deloitteinnovation.aca.batch.service.PrintVendorFileIngestionService;
import us.deloitteinnovation.aca.batch.service.impl.BatchInfoServiceImpl;
import us.deloitteinnovation.aca.batch.service.impl.PrintVendorExceptionReportServiceImpl;
import us.deloitteinnovation.aca.batch.service.impl.PrintVendorFileImportMailerServiceImpl;
import us.deloitteinnovation.aca.batch.service.impl.PrintVendorFileIngestionServiceImpl;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.exception.PrintVendorBatchImportException;
import us.deloitteinnovation.aca.exception.PrintVendorFileExtensionFilter;
import us.deloitteinnovation.aca.exception.PrintVendorFileNameVerificationDecider;
import us.deloitteinnovation.aca.exception.PrintVendorImportFilersJobExecutionListener;
import us.deloitteinnovation.profile.ProfileProperties;

import javax.sql.DataSource;
import java.io.File;
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
public class PrintVendorFileIngestionConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(PrintVendorFileIngestionConfiguration.class);

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
        } catch (PrintVendorBatchImportException e) {
            LOGGER.error(e.getMessage());
        } finally {
            reader.close();
        }
        return reader;
    }

    private Resource[] getFileSystemResource(
            String inputFileFolder
    ) throws PrintVendorBatchImportException {
        Resource[] fileSystemResource = null;
        File fileDirectory = new File(inputFileFolder);

        File[] fileListInSourceFolder = fileDirectory.listFiles(new PrintVendorFileExtensionFilter(BatchConstants.FILE_FORMAT_EXTENSION));

        fileSystemResource = new FileSystemResource[fileListInSourceFolder.length];
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
    @JobScope
    public PrintVendorImportFilersJobExecutionListener printVendorImportFilersJobExecutionListener() {
        return new PrintVendorImportFilersJobExecutionListener();
    }


    /* Not sure if we need this. This is a duplicate configuration file.
    Needs to be deleted if not require.
    @Bean
    public Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }*/

    @Bean
    @JobScope
    public PrintVendorFileNameVerificationDecider printVendorFileNameVerificationDecider() {
        return new PrintVendorFileNameVerificationDecider();
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
        lineTokenizer.setStrict(false);
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
    public PrintVendorFileImportMailerService applicationMailerService() {
        PrintVendorFileImportMailerService printVendorFileImportMailerService = new PrintVendorFileImportMailerServiceImpl();
        return printVendorFileImportMailerService;
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
    public PrintVendorExceptionReportService exceptionReportService() {
        return new PrintVendorExceptionReportServiceImpl();
    }

    @Bean
    public PrintVendorExceptionReportDao exceptionReportDao() {
    	PrintVendorExceptionReportDaoImpl exceptionReportDao = new PrintVendorExceptionReportDaoImpl();
        exceptionReportDao.setJdbcTemplate(jdbcTemplate);
        return exceptionReportDao;
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


    

    @Bean
    public PrintVendorFileIngestionService fileIngestionDao() {
    	PrintVendorFileIngestionServiceImpl fileIngestionService = new PrintVendorFileIngestionServiceImpl();
        fileIngestionService.setJdbcTemplate(jdbcTemplate);
        return fileIngestionService;
    }


}
