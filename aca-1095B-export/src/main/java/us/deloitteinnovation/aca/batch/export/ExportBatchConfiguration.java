package us.deloitteinnovation.aca.batch.export;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;

import us.deloitteinnovation.aca.JpaConfiguration;
import us.deloitteinnovation.aca.batch.dao.BatchInfoDao;
import us.deloitteinnovation.aca.batch.dao.IRSTransmittalDetailsDao;
import us.deloitteinnovation.aca.batch.dao.impl.BatchInfoDaoImpl;
import us.deloitteinnovation.aca.batch.dao.impl.IRSTransmittalDetailsDaoImpl;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095CorrectionsMapper;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095OriginalsMapper;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095PrintVendorOriginalsMapper;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095PrintVendorProcessor;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095PrintVendorWriter;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Processor;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Writer;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Mapper;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094PrintVendorWriter;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Processor;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Writer;
import us.deloitteinnovation.aca.batch.export.step3.Step3Form109495Pairing;
import us.deloitteinnovation.aca.batch.export.step3.Step3Form109495Processor;
import us.deloitteinnovation.aca.batch.export.step3.Step3Form109495Reader;
import us.deloitteinnovation.aca.batch.export.step3.Step3Form109495Writer;
import us.deloitteinnovation.aca.batch.export.step3.Step3PrintVendorForm109495Reader;
import us.deloitteinnovation.aca.batch.export.step3.Step3PrintVendorForm109495Writer;
import us.deloitteinnovation.aca.batch.export.step3.Step3ProcessorResult;
import us.deloitteinnovation.aca.batch.export.step3.XMLRenderingDecider;
import us.deloitteinnovation.aca.batch.export.step4.Step4Form109495HeaderAndXmlDto;
import us.deloitteinnovation.aca.batch.export.step4.Step4Form109495PrintVendorReader;
import us.deloitteinnovation.aca.batch.export.step4.Step4Form109495PrintVendorWriter;
import us.deloitteinnovation.aca.batch.export.step4.Step4Form109495Processor;
import us.deloitteinnovation.aca.batch.export.step4.Step4Form109495Reader;
import us.deloitteinnovation.aca.batch.export.step4.Step4Form109495Writer;
import us.deloitteinnovation.aca.batch.export.step4.Step4ManifestData;
import us.deloitteinnovation.aca.batch.export.step5.Step5BatchHistoryCleanupTasklet;
import us.deloitteinnovation.aca.batch.export.steppdf.ExportPdfDto;
import us.deloitteinnovation.aca.batch.export.steppdf.ExportPdfFilerMapper;
import us.deloitteinnovation.aca.batch.export.steppdf.ExportPdfJdbcRepository;
import us.deloitteinnovation.aca.batch.export.steppdf.ExportPdfMapper;
import us.deloitteinnovation.aca.batch.export.steppdf.ExportPdfProcessor;
import us.deloitteinnovation.aca.batch.export.steppdf.ExportPdfReader;
import us.deloitteinnovation.aca.batch.export.steppdf.ExportPdfWriter;
import us.deloitteinnovation.aca.batch.listener.Step1ProcessorListener;
import us.deloitteinnovation.aca.batch.listener.Step1ProcessorPrintVendorListener;
import us.deloitteinnovation.aca.batch.listener.Step3ExecutionListener;
import us.deloitteinnovation.aca.batch.listener.Step4ExecutionListener;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;
import us.deloitteinnovation.aca.batch.service.FilerCoveredPersonService;
import us.deloitteinnovation.aca.batch.service.PrintVendorFilerCoveredPersonService;
import us.deloitteinnovation.aca.batch.service.TransmissionIdReaderService;
import us.deloitteinnovation.aca.batch.service.impl.BatchInfoServiceImpl;
import us.deloitteinnovation.aca.batch.service.impl.FilerCoveredPersonServiceImpl;
import us.deloitteinnovation.aca.batch.service.impl.PrintVendorFilerCoveredPersonServiceImpl;
import us.deloitteinnovation.aca.batch.service.impl.TransmissionIdReaderServiceImpl;
import us.deloitteinnovation.aca.constants.PrintVendorConstants;
import us.deloitteinnovation.aca.model.FilerExportMapper;
import us.deloitteinnovation.aca.model.FilerExportPrintVendorMapper;
import us.deloitteinnovation.aca.model.FilerMapper;
import us.deloitteinnovation.aca.model.FilerWithCountCoveredMapper;
import us.deloitteinnovation.aca.model.FilerWithExportCountCoveredMapper;
import us.deloitteinnovation.aca.model.FilerWithExportCountCoveredPrintVendorMapper;
import us.deloitteinnovation.aca.repository.IrsRecordDetails1095BJdbcRepository;
import us.deloitteinnovation.aca.repository.PrintVendorJdbcRepository;
import us.deloitteinnovation.profile.ProfileProperties;
import us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.Form109495BTrnsmtUpstreamType;

/**
 * <p>
 * The IRS XML Export batch consists of 3 steps in the Job.
 * <ul>
 * <li>Step 1: Convert the Filer (1095B) information into IRS transmission objects and render as XML.</li>
 * <ul>
 * <li>Read - Load Filers and their corresponding dependents from FILER_DEMOGRAPHICS. </li>
 * <li>Process - Convert Filers to associated IRS XML wrappers.</li>
 * <li>Write - Write the Filer 1095B objects with rendered XML (and file size) to the storage facade for later aggregation.</li>
 * </ul>
 * <li>Step 2: Convert Payer (1094B) information into IRS objects and render as XML.</li>
 * <ul>
 * <li>Read - Load unique Payers that correspond to the Filers from Step 1.</li>
 * <li>Process - Convert Payers to associated IRS XML wrappers.</li>
 * <li>Write - Write the Payer 1094B objects with rendered XML (and file size) to the storage facade for later aggregation.</li>
 * </ul>
 * <li>Step 3: Collate the Filer 1095B objects by size and create manifest/headers.</li>
 * <ul>
 * <li>Read - Load Filer 1095B and Payer 1094B objects for collation.</li>
 * <li>Process - Associate 10954B and 1095B into separate files, as needed, to maintain file size maximum.
 * Create and associate manifest/header XML for each file.</li>
 * <li>Write - Write each manifest/header and 1094B/1095b association to the storage facade.</li>
 * </ul>
 * <li>Step 4: Sanitize data as required by IRS, and write the XML to files.</li>
 * <ul>
 * <li>Read - Load manifest/header, corresponding Payer 1094B, and associated Filer 1095B objects.</li>
 * <li>Process - Sanitize each data element, removing special characters as per IRS requirements.</li>
 * <li>Write - Write each manifest, body, and corresponding files to XML.</li>
 * </ul>
 * </ul>
 * </p>
 *
 * @see Step1Form1095Processor
 * @see Step1Form1095Writer
 */
@Configuration
@EnableBatchProcessing
@Import(JpaConfiguration.class)
public class ExportBatchConfiguration {
    static final Logger LOGGER = LoggerFactory.getLogger(ExportBatchConfiguration.class);
    @Autowired
    JobBuilderFactory  jobs;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    DataSource         dataSource;
    @Autowired
    ProfileProperties  profileProperties;
    @Autowired
    JdbcTemplate       jdbcTemplate;

    /**
     * @return
     */
    @Bean
    public Step step1OriginalConvertFilers(ItemReader<Step1Form1095Dto> step1OriginalFilersReader,
                                           Step1Form1095Processor step1Form1095bProcessor,
                                           Step1Form1095Writer step1Form1095bWriter,
                                           Step1ProcessorListener step1ProcessorListener) {
        return stepBuilderFactory.get("step1OriginalConvertFilers").<Step1Form1095Dto, Step1Form1095Dto>chunk(10000)
                .reader(step1OriginalFilersReader)
                .processor(step1Form1095bProcessor)
                .writer(step1Form1095bWriter)
                .listener(step1ProcessorListener)
                .build();
    }

    /**
     * @return
     */
    @Bean
    public Step step1CorrectionConvertFilers(ItemReader<Step1Form1095Dto> step1CorrectedFilersReader,
                                             Step1Form1095Processor step1Form1095bProcessor,
                                             Step1Form1095Writer step1Form1095bWriter,
                                             Step1ProcessorListener step1ProcessorListener) {
        return stepBuilderFactory.get("step1CorrectionConvertFilers").<Step1Form1095Dto, Step1Form1095Dto>chunk(10000)
                .reader(step1CorrectedFilersReader)
                .processor(step1Form1095bProcessor)
                .writer(step1Form1095bWriter)
                .listener(step1ProcessorListener)
                .build();
    }

    /**
     * @return
     */
    @Bean
    public Step step1ReplacementsConvertFilers(ItemReader<Step1Form1095Dto> step1ReplacementsFilersReader,
                                             Step1Form1095Processor step1Form1095bProcessor,
                                             Step1Form1095Writer step1Form1095bWriter,
                                             Step1ProcessorListener step1ProcessorListener) {
        return stepBuilderFactory.get("step1ReplacementsConvertFilers").<Step1Form1095Dto, Step1Form1095Dto>chunk(10000)
                .reader(step1ReplacementsFilersReader)
                .processor(step1Form1095bProcessor)
                .writer(step1Form1095bWriter)
                .listener(step1ProcessorListener)
                .build();
    }

    @Bean
    public Step step2ConvertPayers(JdbcPagingItemReader<Step2Form1094Dto> step2ConvertPayersReader,
                                   Step2Form1094Processor step2Form1094bProcessor,
                                   Step2Form1094Writer step2Form1094bWriter) {
        return stepBuilderFactory.get("step2ConvertPayers").<Step2Form1094Dto, Step2Form1094Dto>chunk(10)
                .reader(step2ConvertPayersReader)
                .processor(step2Form1094bProcessor)
                .writer(step2Form1094bWriter)
                .build();
    }

    @Bean
    public Step step3CollateAndCreateManifests(Step3Form109495Reader step3Form10945bReader,
                                               Step3Form109495Processor step3Form10945bProcessor,
                                               Step3Form109495Writer step3Form10945bWriter,
                                               Step3ExecutionListener step3ExecutionListener) {
        return stepBuilderFactory.get("step3CollateAndCreateManifests").<Step3Form109495Pairing, Step3ProcessorResult>chunk(1)
                .reader(step3Form10945bReader)
                .processor(step3Form10945bProcessor)
                .writer(step3Form10945bWriter)
                .listener(step3ExecutionListener)
                .build();
    }

    @Bean
    public Step step4WriteXml(Step4Form109495Reader step4Form10945bReader,
                              Step4Form109495Processor step4Form10945bProcessor,
                              Step4Form109495Writer step4Form10945bWriter
                              ) {
        return stepBuilderFactory.get("step4WriteXml").<Step4Form109495HeaderAndXmlDto, Step4ManifestData>chunk(1)
                .reader(step4Form10945bReader)
                .processor(step4Form10945bProcessor)
                .writer(step4Form10945bWriter)
                .build();
    }

    @Bean
    public Step step5CleanupBatchMetaData(Step5BatchHistoryCleanupTasklet cleanupTasklet){
        return stepBuilderFactory
                .get("step5CleanupBatchMetaData")
                .tasklet(cleanupTasklet)
                .build();
    }

    @Bean
    public FilerMapper filerMapper() {
        return new FilerMapper();
    }

    @Bean
    public FilerExportMapper filerExportMapper() {
        return new FilerExportMapper();
    }

    @Bean
    public FilerWithCountCoveredMapper filerWithCountCoveredMapper() {
        return new FilerWithCountCoveredMapper();
    }

    @Bean
    public FilerWithExportCountCoveredMapper filerWithCountCoveredExportMapper() {
        return new FilerWithExportCountCoveredMapper();
    }

    @Bean
    public Step1Form1095OriginalsMapper step1Form1095bMapper() {
        return new Step1Form1095OriginalsMapper();
    }

    @Bean
    public Step1Form1095CorrectionsMapper step1Form1095CorrectionsMapper() {
        return new Step1Form1095CorrectionsMapper();
    }
    
    @Bean
    public Step1Form1095PrintVendorOriginalsMapper step1Form1095PrintVendorOriginalsMapper() {
        return new Step1Form1095PrintVendorOriginalsMapper();
    }
    
    @Bean
    public FilerWithExportCountCoveredPrintVendorMapper filerWithExportCountCoveredPrintVendorMapper() {
        return new FilerWithExportCountCoveredPrintVendorMapper();
    }
    
    @Bean
    public FilerExportPrintVendorMapper filerExportPrintVendorMapper() {
        return new FilerExportPrintVendorMapper();
    }

    @Bean
    public Step1ProcessorListener step1ProcessorListener() {
        return new Step1ProcessorListener();
    }
    
    @Bean
    public Step1ProcessorPrintVendorListener step1ProcessorPrintVendorListener() {
        return new Step1ProcessorPrintVendorListener();
    }

    @Bean
    public Step3ExecutionListener step3ExecutionListener() {return new Step3ExecutionListener(); }

    @Bean
    public Step4ExecutionListener step4ExecutionListener() {return new Step4ExecutionListener(); }

    @Bean
    public Step2Form1094Mapper step2Form1094bMapper() {
        return new Step2Form1094Mapper();
    }

    @Bean
    public XMLRenderingDecider xmlRenderingDecider(){ return new XMLRenderingDecider(); }

    /**
     * Load Filers and their corresponding dependents from FILER_DEMOGRAPHICS for an original IRS transmission.
     *
     * @return
     */
    @Bean
    @StepScope
    public JdbcCursorItemReader<Step1Form1095Dto> step1OriginalFilersReader(@Value("#{jobExecutionContext[STATE]}") String state,
                                                                            @Value("#{jobExecutionContext[YEAR]}") String year) {
        JdbcCursorItemReader reader = new JdbcCursorItemReader();
        reader.setDataSource(dataSource);
        reader.setFetchSize(100);
        // TODO Needs join on 1095B details to ensure the row has not been sent before
        String sql = "with cte as ( SELECT RESPONSIBLE_PERSON_UNIQUE_ID ,count(1) as countcovered FROM FILER_DEMOGRAPHICS a GROUP BY RESPONSIBLE_PERSON_UNIQUE_ID ) " +
                "select fd.SOURCE_UNIQUE_ID ,fd.SOURCE_CD ,fd.TAX_YEAR, fd.RECIPIENT_FIRST_NAME ,fd.RECIPIENT_MIDDLE_NAME ,fd.RECIPIENT_LAST_NAME ,fd.RECIPIENT_SUFFIX_NAME ," +
                "fd.RECIPIENT_SSN ,fd.RECIPIENT_TIN ,fd.RECIPIENT_DOB ,fd.RECIPIENT_ADDRESS_LINE_1 ,fd.RECIPIENT_ADDRESS_LINE_2 ,fd.RECIPIENT_CITY ,fd.RECIPIENT_STATE ," +
                "fd.RECIPIENT_ZIP_5 ,fd.RECIPIENT_ZIP_4 ,fd.POLICY_ORIGIN ,fd.SHOP_IDENTIFIER ,fd.PROVIDER_NAME ,fd.PROVIDER_IDENTIFICATION_NUMBER ,fd.PROVIDER_CONTACT_NO ," +
                "fd.PROVIDER_ADDRESS_LINE_1 ,fd.PROVIDER_ADDRESS_LINE_2 ,fd.PROVIDER_CITY_OR_TOWN ,fd.PROVIDER_STATE_OR_PROVINCE ,fd.PROVIDER_COUNTRY ,fd.PROVIDER_ZIP_OR_POSTAL_CODE ," +
                "fd.FILER_DEMO_SEQ ,fd.FILER_STATUS, fd.JAN ,fd.FEB ,fd.MAR ,fd.APR ,fd.MAY ,fd.JUN ,fd.JUL ,fd.AUG ,fd.SEP ,fd.OCT ,fd.NOV ,fd.DEC ,fd.RESPONSIBLE_PERSON_UNIQUE_ID," +
                "cte.countcovered from FILER_DEMOGRAPHICS fd inner join cte on cte.RESPONSIBLE_PERSON_UNIQUE_ID = fd.RESPONSIBLE_PERSON_UNIQUE_ID WHERE left(SOURCE_CD,2) = '"+ state +"' " +
                "AND FD.STATUS = 'ACTIVE' AND fd.IRS_TRANSMISSION_STATUS_CD IS NULL AND fd.FILER_STATUS in ('R','N') AND fd.TAX_YEAR = "+year;
        reader.setSql(sql);
        reader.setRowMapper(step1Form1095bMapper());
        return reader;
    }

    /**
     * Load Filers and their corresponding dependents from FILER_DEMOGRAPHICS for a correction IRS transmission.
     *
     * @return
     */
    @Bean
    @StepScope
    public JdbcCursorItemReader<Step1Form1095Dto> step1CorrectedFilersReader(@Value("#{jobExecutionContext[STATE]}") String state,
                                                                             @Value("#{jobExecutionContext[RECEIPT_ID]}") String originalReceiptId,
                                                                             @Value("#{jobExecutionContext[YEAR]}") String year) {
        JdbcCursorItemReader reader = new JdbcCursorItemReader();
        reader.setDataSource(dataSource);
        String sql;
        // TODO Make sure the correction receipt, submission, and record id is included in query.
        if (StringUtils.isNotEmpty(originalReceiptId)) {
            // TODO hacking count covered so all will query for dependents.
            sql = "select fd_head.*, 2 as countcovered, r_details.record_id, r_details.submission_id, r_details.transmission_id, r_details.RECORD_STATUS, t_details.TRANSMISSION_RECEIPT_ID " +
                    "from filer_demographics fd, irs_record_details_1095b r_details, IRS_TRANSMISSION_DETAILS t_details, filer_demographics fd_head " +
                    "where t_details.TRANSMISSION_RECEIPT_ID = '" + originalReceiptId + "' and t_details.transmission_id = r_details.transmission_id " +
                    "and fd_head.source_cd = r_details.source_cd and fd_head.source_unique_id = r_details.source_unique_id " +
                    "and fd.source_cd = fd_head.source_cd and fd_head.SOURCE_UNIQUE_ID = fd.RESPONSIBLE_PERSON_UNIQUE_ID " +
                    "and fd.IRS_TRANSMISSION_STATUS_CD = 'CO' AND fd.FILER_STATUS in ('R','N')";
        } else {
            // To send a correction, we need the head of household as a filer.  There are instances where a correction could be provided for a dependent covered person,
            // but the head of house hold is in ORIGINAL state.  Therefore, we select all Filers (head of household *and* dependents) in Correction state, whose head of household
            // IRS DETAILS row has been sent, but is not in a state where corrected data has (possibly) been sent.
            // TODO this doesn't work either.  Consider dependent 1 corrected.  IRS correction sent for household.  Dependent 2 is corrected.  record in corrected state, doesn't get picked up.
            // UNLESS we join on the second record detail in a CORRECTION state.  (Correction is state of detail on a correction transmission?
            sql =   "with cte as (SELECT RESPONSIBLE_PERSON_UNIQUE_ID,  count(RESPONSIBLE_PERSON_UNIQUE_ID) as countcovered FROM FILER_DEMOGRAPHICS  a GROUP BY RESPONSIBLE_PERSON_UNIQUE_ID ) " +
                    "select DISTINCT fd.SOURCE_UNIQUE_ID ,fd.SOURCE_CD ,fd.TAX_YEAR, fd.RECIPIENT_FIRST_NAME ,fd.RECIPIENT_MIDDLE_NAME ,fd.RECIPIENT_LAST_NAME ,fd.RECIPIENT_SUFFIX_NAME , " +
                    "fd.RECIPIENT_SSN ,fd.RECIPIENT_TIN ,fd.RECIPIENT_DOB ,fd.RECIPIENT_ADDRESS_LINE_1 ,fd.RECIPIENT_ADDRESS_LINE_2 ,fd.RECIPIENT_CITY ,fd.RECIPIENT_STATE , " +
                    "fd.RECIPIENT_ZIP_5 ,fd.RECIPIENT_ZIP_4 ,fd.POLICY_ORIGIN ,fd.SHOP_IDENTIFIER ,fd.PROVIDER_NAME ,fd.PROVIDER_IDENTIFICATION_NUMBER ,fd.PROVIDER_CONTACT_NO , " +
                    "fd.PROVIDER_ADDRESS_LINE_1 ,fd.PROVIDER_ADDRESS_LINE_2 ,fd.PROVIDER_CITY_OR_TOWN ,fd.PROVIDER_STATE_OR_PROVINCE ,fd.PROVIDER_COUNTRY ,fd.PROVIDER_ZIP_OR_POSTAL_CODE , " +
                    "fd.FILER_DEMO_SEQ ,fd.FILER_STATUS, fd.JAN ,fd.FEB ,fd.MAR ,fd.APR ,fd.MAY ,fd.JUN ,fd.JUL ,fd.AUG ,fd.SEP ,fd.OCT ,fd.NOV ,fd.DEC ,fd.RESPONSIBLE_PERSON_UNIQUE_ID, " +
                    "cte.countcovered, t_details.transmission_id, t_details.TRANSMISSION_RECEIPT_ID, r_details.record_id, r_details.submission_id " +
                    "from FILER_DEMOGRAPHICS fd inner join cte on cte.RESPONSIBLE_PERSON_UNIQUE_ID = fd.RESPONSIBLE_PERSON_UNIQUE_ID, IRS_TRANSMISSION_DETAILS t_details, " +
                    "irs_record_details_1095b r_details, IRS_SUBMISSION_DETAILS s_details WHERE  " +
                    "SUBSTRING(fd.SOURCE_CD,0,3) = '"+state+"' and (fd.FILER_STATUS='R' or fd.FILER_STATUS='N') and fd.STATUS='ACTIVE' " +
                    "and fd.IRS_TRANSMISSION_STATUS_CD = 'CO' and fd.SOURCE_UNIQUE_ID = r_details.source_unique_id " +
                    "and r_details.transmission_id = t_details.transmission_id and r_details.RECORD_STATUS IN ('AC', 'ER') and " +
                    "r_details.TRANSMISSION_ID = (SELECT TOP 1 TRANSMISSION_ID FROM IRS_RECORD_DETAILS_1095B WHERE SOURCE_UNIQUE_ID = fd.SOURCE_UNIQUE_ID " +
                    "AND SUBSTRING(fd.SOURCE_CD,0,3) = '"+state+"' AND RECORD_STATUS IN ('AC', 'ER') AND TRANSMISSION_ID in (SELECT TRANSMISSION_ID FROM IRS_TRANSMISSION_DETAILS WHERE TAX_YEAR = "+year+") ORDER BY TRANSMISSION_ID DESC) AND fd.TAX_YEAR = "+year;
        }
        reader.setSql(sql);
        reader.setRowMapper(step1Form1095CorrectionsMapper());
        return reader;
    }


    @Bean
    @StepScope
    public JdbcCursorItemReader<Step1Form1095Dto> step1ReplacementsFilersReader(@Value("#{jobExecutionContext[STATE]}") String state,
                                                                                @Value("#{jobExecutionContext[RECEIPT_ID]}") String originalReceiptId,
                                                                                @Value("#{jobExecutionContext[YEAR]}") String year) {
        JdbcCursorItemReader reader = new JdbcCursorItemReader();
        reader.setDataSource(dataSource);
        String sql;
        // TODO The replacements query will need to be revisited, along with the step 3 writer, once the replacement state logic is finalized
        if (StringUtils.isNotEmpty(originalReceiptId)) {

            sql = "select fd_head.*, 2 as countcovered, r_details.record_id, r_details.submission_id, r_details.transmission_id, r_details.RECORD_STATUS, t_details.TRANSMISSION_RECEIPT_ID " +
                    "from filer_demographics fd, irs_record_details_1095b r_details, IRS_TRANSMISSION_DETAILS t_details, filer_demographics fd_head " +
                    "where t_details.TRANSMISSION_RECEIPT_ID = '" + originalReceiptId + "' and t_details.transmission_id = r_details.transmission_id " +
                    "and fd_head.source_cd = r_details.source_cd and fd_head.source_unique_id = r_details.source_unique_id " +
                    "and fd.source_cd = fd_head.source_cd and fd_head.SOURCE_UNIQUE_ID = fd.RESPONSIBLE_PERSON_UNIQUE_ID " +
                    "and fd_head.IRS_TRANSMISSION_STATUS_CD IN ('RC', 'RR') AND fd.FILER_STATUS in ('R','N')";
        } else {

            sql = "with cte as (SELECT RESPONSIBLE_PERSON_UNIQUE_ID,  count(RESPONSIBLE_PERSON_UNIQUE_ID) as countcovered FROM FILER_DEMOGRAPHICS  a GROUP BY RESPONSIBLE_PERSON_UNIQUE_ID )" +
                    "select DISTINCT fd.SOURCE_UNIQUE_ID ,fd.SOURCE_CD ,fd.TAX_YEAR, fd.RECIPIENT_FIRST_NAME ,fd.RECIPIENT_MIDDLE_NAME ,fd.RECIPIENT_LAST_NAME ,fd.RECIPIENT_SUFFIX_NAME , " +
                    "fd.RECIPIENT_SSN ,fd.RECIPIENT_TIN ,fd.RECIPIENT_DOB ,fd.RECIPIENT_ADDRESS_LINE_1 ,fd.RECIPIENT_ADDRESS_LINE_2 ,fd.RECIPIENT_CITY ,fd.RECIPIENT_STATE , " +
                    "fd.RECIPIENT_ZIP_5 ,fd.RECIPIENT_ZIP_4 ,fd.POLICY_ORIGIN ,fd.SHOP_IDENTIFIER ,fd.PROVIDER_NAME ,fd.PROVIDER_IDENTIFICATION_NUMBER ,fd.PROVIDER_CONTACT_NO , " +
                    "fd.PROVIDER_ADDRESS_LINE_1 ,fd.PROVIDER_ADDRESS_LINE_2 ,fd.PROVIDER_CITY_OR_TOWN ,fd.PROVIDER_STATE_OR_PROVINCE ,fd.PROVIDER_COUNTRY ,fd.PROVIDER_ZIP_OR_POSTAL_CODE , " +
                    "fd.FILER_DEMO_SEQ ,fd.FILER_STATUS, fd.JAN ,fd.FEB ,fd.MAR ,fd.APR ,fd.MAY ,fd.JUN ,fd.JUL ,fd.AUG ,fd.SEP ,fd.OCT ,fd.NOV ,fd.DEC ,fd.RESPONSIBLE_PERSON_UNIQUE_ID, " +
                    "cte.countcovered, t_details.transmission_id, t_details.TRANSMISSION_RECEIPT_ID, r_details.record_id, r_details.submission_id " +
                    "from FILER_DEMOGRAPHICS fd inner join cte on cte.RESPONSIBLE_PERSON_UNIQUE_ID = fd.RESPONSIBLE_PERSON_UNIQUE_ID, IRS_TRANSMISSION_DETAILS t_details, " +
                    "irs_record_details_1095b r_details, IRS_SUBMISSION_DETAILS s_details WHERE  " +
                    "SUBSTRING(fd.SOURCE_CD,0,3) = '"+state+"' and (fd.FILER_STATUS='R' or fd.FILER_STATUS='N') and fd.STATUS='ACTIVE' " +
                    "and fd.IRS_TRANSMISSION_STATUS_CD in ('RC', 'RR') and fd.SOURCE_UNIQUE_ID = r_details.source_unique_id " +
                    "and r_details.transmission_id = t_details.transmission_id and r_details.RECORD_STATUS IN ('RC', 'RR') and " +
                    "r_details.TRANSMISSION_ID = (SELECT TOP 1 TRANSMISSION_ID FROM IRS_RECORD_DETAILS_1095B WHERE SOURCE_UNIQUE_ID = fd.SOURCE_UNIQUE_ID " +
                    "AND SUBSTRING(fd.SOURCE_CD,0,3) = '"+state+"' AND RECORD_STATUS IN ('RC', 'RR') AND TRANSMISSION_ID in (SELECT TRANSMISSION_ID FROM IRS_TRANSMISSION_DETAILS WHERE TAX_YEAR = "+year+") ORDER BY TRANSMISSION_ID DESC) AND fd.TAX_YEAR = "+year;
        }
        reader.setSql(sql);
        reader.setRowMapper(step1Form1095bMapper());
        return reader;
    }


    @Bean
    @StepScope
    public Step1Form1095Processor step1Form1095bProcessor(@Value("#{jobExecutionContext[RECEIPT_ID]}") String originalReceiptId) {
        Step1Form1095Processor step1Form1095bProcessor = new Step1Form1095Processor();
        step1Form1095bProcessor.setOriginalReceiptId(originalReceiptId);
        return step1Form1095bProcessor;
    }

    @Bean
    @StepScope
    public Step1Form1095Writer step1Form1095bWriter() {
        return new Step1Form1095Writer();
    }

    /**
     * Load unique Payees that correspond to the Filers from Step 2.
     *
     * @return
     */
    @Bean
    @StepScope
    public JdbcPagingItemReader<Step2Form1094Dto> step2ConvertPayersReader(
            @Value("#{jobExecutionContext[STATE]}") String state,
            @Value("#{jobExecutionContext[YEAR]}") String year ) {

        JdbcPagingItemReader jdbcPagingItemReader = new JdbcPagingItemReader();
        jdbcPagingItemReader.setDataSource(dataSource);
        SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
        sqlPagingQueryProviderFactoryBean.setSelectClause("select *");
        sqlPagingQueryProviderFactoryBean.setFromClause("from dbo.source_system_config");
        sqlPagingQueryProviderFactoryBean.setWhereClause("where SUBSTRING(SOURCE_CD,0,3) = :STATE and TAX_YEAR = :YEAR");
        sqlPagingQueryProviderFactoryBean.setSortKey("source_cd_id");
        try {
            jdbcPagingItemReader.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObjectType().cast(sqlPagingQueryProviderFactoryBean.getObject()));
        } catch (Exception e) {
            LOGGER.error("Failed attempt to create PagingQueryProvider.", e);
            throw new RuntimeException("Failed attempt to create PagingQueryProvider.", e);
        }
        Map<String, Object> parameterValues = new HashMap();
        parameterValues.put("STATE", state);
        parameterValues.put("YEAR", year);
        jdbcPagingItemReader.setParameterValues(parameterValues);
        jdbcPagingItemReader.setPageSize(1);
        jdbcPagingItemReader.setRowMapper(step2Form1094bMapper());
        return jdbcPagingItemReader;
    }

    @Bean
    @StepScope
    public Step2Form1094Processor step2Form1094bProcessor() {
        Step2Form1094Processor step2Form1094bProcessor = new Step2Form1094Processor();
        return step2Form1094bProcessor;
    }

    @Bean
    @StepScope
    public Step2Form1094Writer step2Form1094bWriter() {
        Step2Form1094Writer step2Form1094bWriter = new Step2Form1094Writer();
        return step2Form1094bWriter;
    }

    @Bean
    @StepScope
    public Step3Form109495Reader step3Form10945bReader() {
        Step3Form109495Reader step3Form10945bReader = new Step3Form109495Reader();
        return step3Form10945bReader;
    }

    @Bean
    @StepScope
    public Step3Form109495Processor step3Form10945bProcessor() {
        Step3Form109495Processor step3Form10945bProcessor = new Step3Form109495Processor();
        return step3Form10945bProcessor;
    }

    @Bean
    @StepScope
    public Step3Form109495Writer step3Form10945bWriter() {
        Step3Form109495Writer step3Form10945bWriter = new Step3Form109495Writer();
        return step3Form10945bWriter;
    }

    @Bean
    @StepScope
    public Step4Form109495Reader step4Form10945bReader() {
        Step4Form109495Reader step4Form10945bReader = new Step4Form109495Reader();
        return step4Form10945bReader;
    }

    @Bean
    @StepScope
    public Step4Form109495Processor step4Form10945bProcessor() {
        Step4Form109495Processor step4Form10945bProcessor = new Step4Form109495Processor();
        return step4Form10945bProcessor;
    }

    @Bean
    @StepScope
    public Step4Form109495Writer step4Form10945bWriter() {
        Step4Form109495Writer step4Form10945bWriter = new Step4Form109495Writer();
        return step4Form10945bWriter;
    }

    @Bean
    @StepScope
    public Step5BatchHistoryCleanupTasklet step5BatchHistoryCleanupTasklet() {
        Step5BatchHistoryCleanupTasklet step5Tasklet = new Step5BatchHistoryCleanupTasklet();
        step5Tasklet.setJdbcTemplate(jdbcTemplate);
        return step5Tasklet;
    }


    @Bean
    public XStreamMarshaller xStreamMarshaller() {
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        Map aliases = new HashMap();
        aliases.put("Form109495BTrnsmtUpstreamType", Form109495BTrnsmtUpstreamType.class);
        xStreamMarshaller.setAliases(aliases);
        return xStreamMarshaller;
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
    public ExportBatchJobExecutionListener exportBatchJobExecutionListener() {return new ExportBatchJobExecutionListener() ;}

    /**
     * @return Storage mechanism for Form 1094 and 1095 data during Job processing.
     */
    @Bean
    public ExportJob1095Repository exportJob1095Repository() {
        return new ExportJob1095RepositoryInMemory();
    }

    @Bean
    public ExportJob1094Repository exportJob1094Repository() {
        return new ExportJob1094RepositoryInMemory();    }

    @Bean
    public ExportJob1095Repository exportJobRepository() { return new ExportJobRepositoryInDB();    }

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
    public IRSTransmittalDetailsDao irsTransmittalDetailsDao() {
        IRSTransmittalDetailsDaoImpl irsTransmittalDetailsDao = new IRSTransmittalDetailsDaoImpl();
        irsTransmittalDetailsDao.setJdbcTemplate(jdbcTemplate);
        return irsTransmittalDetailsDao;
    }

    @Bean
    public FilerCoveredPersonService filerCoveredPersonService() {
        return new FilerCoveredPersonServiceImpl();
    }
    
    @Bean
    public PrintVendorFilerCoveredPersonService printVendorFilerCoveredPersonService() {
        return new PrintVendorFilerCoveredPersonServiceImpl();
    }

    @Bean
    public TransmissionIdReaderService transmissionIdReaderService(){
        return new TransmissionIdReaderServiceImpl();
    }

    @Bean
    public TransmissionIdStack transmissionIdStack(){
        return new TransmissionIdStack();
    }

    @Bean
    public IrsRecordDetails1095BJdbcRepository irsRecordDetails1095BJdbcRepository() {
        IrsRecordDetails1095BJdbcRepository repo = new IrsRecordDetails1095BJdbcRepository();
        repo.setJdbcTemplate(jdbcTemplate);
        return repo;
    }
    
    @Bean
    public PrintVendorJdbcRepository printVendorJdbcRepository() {
    	PrintVendorJdbcRepository repo = new PrintVendorJdbcRepository();
        repo.setJdbcTemplate(jdbcTemplate);
        return repo;
    }
    
    @Bean
    public ExportPdfJdbcRepository exportPdfJdbcRepository() {
    	ExportPdfJdbcRepository repo = new ExportPdfJdbcRepository();
        repo.setJdbcTemplate(jdbcTemplate);
        return repo;
    }

    @Bean(name="jaxb2Marshaller")
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("us.gov.treasury.irs");
        Map<String, Object> map = new HashMap<>();
        map.put("jaxb.formatted.output", true);
        jaxb2Marshaller.setMarshallerProperties(map);
        return jaxb2Marshaller;
    }

    @Bean(name="jaxb2FragmentMarshaller")
    public Jaxb2Marshaller jaxb2FragmentMarshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("us.gov.treasury.irs");
        Map<String, Object> map = new HashMap<>();
        map.put("jaxb.formatted.output", true);
        map.put(Marshaller.JAXB_FRAGMENT, true);
        jaxb2Marshaller.setMarshallerProperties(map);
        return jaxb2Marshaller;
    }
    
    @Bean
    public Step1Form1095PrintVendorWriter step1Form1095PrintVendorWriter(){
    	return new Step1Form1095PrintVendorWriter();
    }
    
    @Bean
    @StepScope
    public Step1Form1095PrintVendorProcessor step1Form1095PrintVendorProcessor() {
        return new Step1Form1095PrintVendorProcessor();
    }
    
    @Bean
    @StepScope
    public Step1Form1095PrintVendorWriter step1Form1095bPrintVendorWriter() {
        return new Step1Form1095PrintVendorWriter();
    }
    
    @Bean
    @StepScope
    public Step2Form1094PrintVendorWriter step2Form1094PrintVendorWriter() {
        return new Step2Form1094PrintVendorWriter();
    }
    
    @Bean
    public ExportBatchJobExecutionPrintVendorListener exportBatchJobExecutionListenerForPrintVendor() {
    	return new ExportBatchJobExecutionPrintVendorListener() ;
    }
    
    @Bean
    public Step3PrintVendorForm109495Reader step3PrintVendorForm109495Reader() {
    	return new Step3PrintVendorForm109495Reader() ;
    }
    
    @Bean
    @StepScope
    public Step3PrintVendorForm109495Writer step3PrintVendorForm109495Writer() {
    	Step3PrintVendorForm109495Writer step3PrintVendorForm109495Writer = new Step3PrintVendorForm109495Writer();
        return step3PrintVendorForm109495Writer;
    }
    
    @Bean
    @StepScope
    public Step4Form109495PrintVendorReader step4Form109495PrintVendorReader() {
    	Step4Form109495PrintVendorReader step4Form109495PrintVendorReader = new Step4Form109495PrintVendorReader();
        return step4Form109495PrintVendorReader;
    }

    
    @Bean
    public Step4Form109495PrintVendorWriter step4Form109495PrintVendorWriter() {
    	return new Step4Form109495PrintVendorWriter() ;
    }
    
    @Bean
    public Step step1OriginalFilersForPrintVendor(ItemReader<Step1Form1095Dto> step1OriginalFilersReaderForPrintVendor,
                                                  Step1Form1095PrintVendorProcessor step1Form1095PrintVendorProcessor,
                                                  Step1Form1095PrintVendorWriter step1Form1095PrintVendorWriter,
                                                  Step1ProcessorPrintVendorListener step1ProcessorPrintVendorListener) {
        return stepBuilderFactory.get("step1OriginalFilersForPrintVendor").<Step1Form1095Dto, Step1Form1095Dto>chunk(PrintVendorConstants.RECORDSSIZE)
                .reader(step1OriginalFilersReaderForPrintVendor)
                .processor(step1Form1095PrintVendorProcessor)
                .writer(step1Form1095PrintVendorWriter)
                .listener(step1ProcessorPrintVendorListener)
                .build();
    }
    
    
    @Bean
    public Step step2PrintVendorConvertPayers(JdbcPagingItemReader<Step2Form1094Dto> step2ConvertPayersReader,
                                   Step2Form1094Processor step2Form1094bProcessor,
                                   Step2Form1094PrintVendorWriter step2Form1094PrintVendorWriter) {
        return stepBuilderFactory.get("step2PrintVendorConvertPayers").<Step2Form1094Dto, Step2Form1094Dto>chunk(PrintVendorConstants.RECORDSSIZE)
                .reader(step2ConvertPayersReader)
                .processor(step2Form1094bProcessor)
                .writer(step2Form1094PrintVendorWriter)
                .build();
    }
    
    @Bean
    public Step step3PrintVendorCollateAndCreateManifests(Step3PrintVendorForm109495Reader step3PrintVendorForm109495Reader,
                                               Step3Form109495Processor step3Form10945bProcessor,
                                               Step3PrintVendorForm109495Writer step3PrintVendorForm109495Writer) {
        return stepBuilderFactory.get("step3PrintVendorCollateAndCreateManifests").<Step3Form109495Pairing, Step3ProcessorResult>chunk(PrintVendorConstants.RECORDSSIZE)
                .reader(step3PrintVendorForm109495Reader)
                .processor(step3Form10945bProcessor)
                .writer(step3PrintVendorForm109495Writer)
                .build();
    }
    
    @Bean
    public Step step4PrintVendorWriteXml(Step4Form109495PrintVendorReader step4Form109495PrintVendorReader,
                              Step4Form109495Processor step4Form10945bProcessor,
                              Step4Form109495PrintVendorWriter step4Form109495PrintVendorWriter,
                              Step4ExecutionListener step4ExecutionListener) {
        return stepBuilderFactory.get("step4PrintVendorWriteXml").<Step4Form109495HeaderAndXmlDto, Step4ManifestData>chunk(PrintVendorConstants.RECORDSSIZE)
                .reader(step4Form109495PrintVendorReader)
                .processor(step4Form10945bProcessor)
                .writer(step4Form109495PrintVendorWriter)
                .listener(step4ExecutionListener)
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Step1Form1095Dto> step1OriginalFilersReaderForPrintVendor(@Value("#{jobExecutionContext[STATE]}") String state,@Value("#{jobExecutionContext[YEAR]}") String year
    		,@Value("#{jobExecutionContext[FREQUENCY]}") String frequency, @Value("#{jobExecutionContext[MAILSTATUS]}") String mailStatus) {
        
    	
    	JdbcPagingItemReader<Step1Form1095Dto> jdbcPagingItemReader = new JdbcPagingItemReader<Step1Form1095Dto>();
    	StringBuilder builder = new StringBuilder("%");
    	builder.append(state).append("%");
        jdbcPagingItemReader.setDataSource(dataSource);
        SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
        
        sqlPagingQueryProviderFactoryBean.setSelectClause("SELECT SOURCE_UNIQUE_ID, SOURCE_CD, BATCH_ID, TAX_YEAR, RECIPIENT_FIRST_NAME, RECIPIENT_MIDDLE_NAME, RECIPIENT_LAST_NAME, RECIPIENT_SUFFIX_NAME, RECIPIENT_SSN, RECIPIENT_TIN, RECIPIENT_DOB, RECIPIENT_ADDRESS_LINE_1, RECIPIENT_ADDRESS_LINE_2, RECIPIENT_CITY, RECIPIENT_STATE, RECIPIENT_ZIP_5, RECIPIENT_ZIP_4, RECIPIENT_E_MAIL, RECIPIENT_LANGUAGE_PREFERENCE, POLICY_ORIGIN, SHOP_IDENTIFIER, EMPLOYER_NAME, EMPLOYER_IDENTIFICATION_NUMBER, EMPLOYER_CONTACT_NO, EMPLOYER_ADDRESS_LINE_1, EMPLOYER_ADDRESS_LINE_2, EMPLOYER_CITY_OR_TOWN, EMPLOYER_STATE_OR_PROVINCE, EMPLOYER_COUNTRY, EMPLOYER_ZIP_OR_POSTAL_CODE, PROVIDER_NAME, PROVIDER_IDENTIFICATION_NUMBER, PROVIDER_CONTACT_NO, PROVIDER_ADDRESS_LINE_1, PROVIDER_ADDRESS_LINE_2, PROVIDER_CITY_OR_TOWN, PROVIDER_STATE_OR_PROVINCE, PROVIDER_COUNTRY, PROVIDER_ZIP_OR_POSTAL_CODE, FILER_STATUS, COMMUNICATION_PREFERENCE, COMMENTS, UPDATED_BY, UPDATED_DATE, CORRECTION_DATE, CORRECTION_CODE, FORM_STATUS, STATUS, FILER_DEMO_SEQ, JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, [DEC], fd.RESPONSIBLE_PERSON_UNIQUE_ID, MAILED_FORM, IRS_TRANSMISSION_STATUS_CD, RECORD_CREATED_DATE, CORRECTION_INDICATOR, CTE.COUNTCOVERED");
        sqlPagingQueryProviderFactoryBean.setFromClause("FROM FILER_DEMOGRAPHICS fd inner join (  SELECT RESPONSIBLE_PERSON_UNIQUE_ID ,count(1) as countcovered   FROM FILER_DEMOGRAPHICS a GROUP BY RESPONSIBLE_PERSON_UNIQUE_ID   ) CTE  on CTE.RESPONSIBLE_PERSON_UNIQUE_ID = fd.RESPONSIBLE_PERSON_UNIQUE_ID");
        if("D".equalsIgnoreCase(frequency)){
        	sqlPagingQueryProviderFactoryBean.setWhereClause("WHERE  TAX_YEAR = :YEAR AND STATUS='ACTIVE' AND (FORM_STATUS IS NULL OR FORM_STATUS='REGENERATE') AND FILER_STATUS IN ('N','R') AND SOURCE_CD LIKE :STATE AND MAILED_FORM = :MAILSTATUS");
        }else{
        	sqlPagingQueryProviderFactoryBean.setWhereClause("WHERE  TAX_YEAR = :YEAR AND STATUS='ACTIVE' AND (FORM_STATUS IS NULL) AND FILER_STATUS IN ('N','R') AND SOURCE_CD LIKE :STATE AND MAILED_FORM = :MAILSTATUS");
        }
        sqlPagingQueryProviderFactoryBean.setSortKey("SOURCE_UNIQUE_ID");
        
        try {
            jdbcPagingItemReader.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObjectType().cast(sqlPagingQueryProviderFactoryBean.getObject()));
        } catch (Exception e) {
            LOGGER.error("Failed attempt to create PagingQueryProvider.", e);
            throw new RuntimeException("Failed attempt to create PagingQueryProvider.", e);
        }
        LOGGER.debug("The sql is "+sqlPagingQueryProviderFactoryBean);
        Map<String, Object> parameterValues = new HashMap();
        parameterValues.put("YEAR", year);
        parameterValues.put("STATE", builder.toString());
        parameterValues.put("MAILSTATUS", mailStatus);
        jdbcPagingItemReader.setParameterValues(parameterValues);
        jdbcPagingItemReader.setFetchSize(PrintVendorConstants.RECORDSSIZE); 
        jdbcPagingItemReader.setPageSize(PrintVendorConstants.RECORDSSIZE);
        jdbcPagingItemReader.setRowMapper(step1Form1095PrintVendorOriginalsMapper());
        return jdbcPagingItemReader;
    } 
    
    @Bean
    public ExportBatchPrintVendorListener exportBatchJobExecutionPrintVendorParamListener() {
    	return new ExportBatchPrintVendorListener() ;
    }
    
    @Bean
    @StepScope
    public ExportPdfProcessor exportPdfProcessor() {
        return new ExportPdfProcessor();
    }
    
    @Bean
    @StepScope
    public ExportPdfWriter exportPdfWriter() {
        return new ExportPdfWriter();
    }
    
    @Bean
    public ExportPdfListener exportPdfListener() {
    	return new ExportPdfListener() ;
    }
    
    @Bean
    public ExportPdfFilerMapper exportPdfFilerMapper() {
    	return new ExportPdfFilerMapper() ;
    }
    
    @Bean
    public ExportPdfMapper exportPdfMapper() {
    	return new ExportPdfMapper() ;
    }
    
    @Bean
    public ExportPdfReader exportPdfReader() {
        return new ExportPdfReader() ;
    }
    
    @Bean
    public Step stepExportPdf(ExportPdfReader exportPdfReader,
            ExportPdfProcessor exportPdfProcessor,
            ExportPdfWriter exportPdfWriter,
            ExportPdfListener exportPdfListener) {
        return stepBuilderFactory.get("stepExportPdf").<ExportPdfDto, ExportPdfDto>chunk(1)
                .reader(exportPdfReader)
                .processor(exportPdfProcessor)
                .writer(exportPdfWriter)
                .listener(exportPdfListener)
                .build();
    }
}