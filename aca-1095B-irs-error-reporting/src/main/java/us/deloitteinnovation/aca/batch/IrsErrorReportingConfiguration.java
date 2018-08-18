package us.deloitteinnovation.aca.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import us.deloitteinnovation.aca.batch.dto.ErrorDetailsDTO;
import us.deloitteinnovation.aca.batch.listeners.IrsErrorHandlingProtocolListener;
import us.deloitteinnovation.aca.batch.listeners.ItemReaderListener;
import us.deloitteinnovation.aca.batch.listeners.RecordSkipListener;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;
import us.deloitteinnovation.aca.batch.processor.IrsErrorReportingAppProcessor;
import us.deloitteinnovation.aca.batch.writer.IrsErrorReportingItemWriter;
import us.deloitteinnovation.aca.batch.writer.IrsErrorReportingService;
import us.deloitteinnovation.aca.repository.IrsErrorCodeOwnerMapRepository;
import us.deloitteinnovation.aca.repository.IrsTransmissionDetailsRepository;
import us.deloitteinnovation.aca.repository.IrsTransmissionStatusRepository;
import us.gov.treasury.irs.msg.form1094_1095bctransmittermessage.FormBCTransmitterSubmissionDtl;
import us.gov.treasury.irs.msg.form1094_1095bctransmittermessage.impl.FormBCTransmitterSubmissionDtlImpl;

import java.io.IOException;

/**
 * Created by tthakore on 3/25/2016.
 */

@Configuration
@EnableBatchProcessing
public class IrsErrorReportingConfiguration {

    private static Logger logger = LoggerFactory.getLogger(IrsErrorReportingConfiguration.class);

    private String stateName;


    @Autowired
    JobBuilderFactory jobs;

    @Autowired
    StepBuilderFactory stepBuilderFactory;


    @Autowired
    IrsTransmissionDetailsRepository irsTransmissionService;

    @Autowired
    IrsTransmissionStatusRepository irsTransmissionStatusService;

    @Autowired
    IrsErrorCodeOwnerMapRepository irsErrorCodeOwnerMapService;


    /**
     * primary usage of below function is to create a job for error report generation*
     *
     * @param errorReportGenarationStep error report generation step
     * @param protocolListener          this  is the listener for before and after job listeners this will be used to calculate actual time of whole job execution.
     * @return Job
     */
    @Bean
    public Job errorReportGenerationJob(Step errorReportGenarationStep, final IrsErrorHandlingProtocolListener protocolListener ) {

        ErrorReportingObjectStore.getInstance().setIrsTransmissionService(irsTransmissionService);
        ErrorReportingObjectStore.getInstance().setIrsTransmissionStatusService(irsTransmissionStatusService);
        ErrorReportingObjectStore.getInstance().setIrsErrorCodeOwnerMapService(irsErrorCodeOwnerMapService);
        return jobs.get("aca1095IrsErrorReportingJob").preventRestart()
                .listener(protocolListener)
                .incrementer(new RunIdIncrementer())
                .flow(errorReportGenarationStep)
                .end()
                .build();
    }


    /**
     * @param errorReportWriter  will be the item writer which generates dat file as error report an update status into db.
     * @param errorXmlFileReader this is MultiResourceItemReader item reader since there will be more then one file to read so the
     *                           target is to read one file at a time process it write it and then
     *                           move to next file.
     * @param errorXmlProcessor  this will convert output of XML object to ErrorDetailsDTO which will be then processed by writer.
     * @param itemReaderListener this item reader listener is used to get currently processed file name take transmission id out
     *                           of it and load all the records for that specific transmission id to update there status as ACCEPTED or ERROR.
     * @return Step returns steps for error report job this step  containes readers writters and processors to read and process xml.
     **/
    @Bean
    public Step errorReportGenarationStep(MultiResourceItemReader errorXmlFileReader, ItemProcessor errorXmlProcessor, ItemWriter errorReportWriter, ItemReaderListener itemReaderListener, RecordSkipListener recordSkipListener) {
        //Added skip listner for the batch job reason in case there are multiple files and one file is failing batch job should not stop if you want to see why specific file did not get processed goto logs with info level and check there.
        Step step = stepBuilderFactory.get("aca1095IrsErrorReportingStep").chunk(1)
                .reader(errorXmlFileReader)
                .listener(itemReaderListener)
                .processor(errorXmlProcessor)
                .writer(errorReportWriter).faultTolerant()
                .skip(Exception.class).noRetry(Exception.class)
                .skipLimit(Integer.MAX_VALUE).listener(recordSkipListener)
                .build();

        return step;

    }


    /**
     * @return ItemProcessor<FormBCTransmitterSubmissionDtl,ErrorDetailsDTO>
     * item processor for above step takes  FormBCTransmitterSubmissionDtl and ouputs ErrorDetailsDTO to generate xml and map status to DB.
     */
    @Bean
    public ItemProcessor<FormBCTransmitterSubmissionDtl, ErrorDetailsDTO> errorXmlProcessor() {
        IrsErrorReportingAppProcessor irsErrorReportingAppProcessor = new IrsErrorReportingAppProcessor();
        return irsErrorReportingAppProcessor;

    }

    /**
     * @param state this is will be state abbreviation which will be passed through job paramater.
     *              this function contains StaxEventItemReader which is being used to read error xml and generate XML object using JAXB unmarshaller
     * @return MultiResourceItemReader
     **/
    @Bean
    @Qualifier("reader")
    @StepScope
    public MultiResourceItemReader errorXmlFileReader(@Value("#{jobParameters[state]}") String state) {
        StaxEventItemReader itemReader = new StaxEventItemReader();
        MultiResourceItemReader multiResourceItemReader = new MultiResourceItemReader();
        try {
            stateName = state;
            Jaxb2Marshaller marshaller1 = new Jaxb2Marshaller();
            marshaller1.setClassesToBeBound(FormBCTransmitterSubmissionDtlImpl.class);
            marshaller1.setSupportDtd(true);
            itemReader.setFragmentRootElementName("FormBCTransmitterSubmissionDtl");
            itemReader.setUnmarshaller(marshaller1);
            multiResourceItemReader.setDelegate(itemReader);
            multiResourceItemReader.setResources(getErrorXmlFiles(state));


        } catch (IOException ex) {
          logger.error("Error in MultiResourceItemReader",ex);
        }
        return multiResourceItemReader;
    }

    /**
     * @return standard item writer for the job.
     */

    @Bean
    public ItemWriter<ErrorDetailsDTO> errorReportWriter() {
        logger.info("Start of ItemWriter");
        IrsErrorReportingItemWriter applicationWriter = new IrsErrorReportingItemWriter();
        logger.info("End of ItemWriter");
        return applicationWriter;
    }

    /*all the beans are defined below.*/
    @Bean
    public IrsErrorReportingService irsErrorReportingService() {
    return new IrsErrorReportingService();
}

    @Bean
    public IrsErrorHandlingProtocolListener protocolListener() {
        IrsErrorHandlingProtocolListener irsErrorHandlingProtocolListener = new IrsErrorHandlingProtocolListener();
        return irsErrorHandlingProtocolListener;
    }

    public Resource[] getErrorXmlFiles(String stateCode) throws IOException {
        return ErrorReportingObjectStore.getInstance().getAvailableErrorXml(stateCode);
    }

    @Bean
    ItemReaderListener itemReaderListener() {
        return new ItemReaderListener();
    }

    @Bean
    public RecordSkipListener recordSkipListener(){return new RecordSkipListener();}
}
