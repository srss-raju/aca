package us.deloitteinnovation.aca.batch.export.step3;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.FlatFileImportTestConfiguration;
import us.deloitteinnovation.aca.batch.BatchTestUtil;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.export.ExportBatchConfiguration;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Processor;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095WriterTest;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Processor;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094WriterTest;
import us.deloitteinnovation.aca.batch.export.step4.Step4109495Data;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;
import us.deloitteinnovation.aca.jaxb.JaxbUtils;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.repository.IrsRecordDetails1095BJdbcRepository;
import us.deloitteinnovation.aca.repository.IrsRecordDetails1095BRepository;
import us.deloitteinnovation.aca.repository.IrsSubmissionDetailsRepository;
import us.deloitteinnovation.aca.repository.IrsTransmissionDetailsRepository;
import us.deloitteinnovation.aca.util.Convert;
import us.deloitteinnovation.profile.ProfileConfiguration;
import us.deloitteinnovation.profile.ProfileProperties;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;
import us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.Form109495BTrnsmtUpstreamType;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static us.deloitteinnovation.aca.batch.constants.BatchExportConstants.JobPropertiesKeys.STATE;
import static us.deloitteinnovation.aca.batch.constants.BatchExportConstants.JobPropertiesKeys.YEAR;
import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.genericSetLong;

/**
 * @see Step3Form109495Writer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@SpringApplicationConfiguration(classes = {ProfileConfiguration.class, FlatFileImportTestConfiguration.class})
public class Step3Form109495WriterTest {

    private static final Logger LOG = LoggerFactory.getLogger(Step3Form109495WriterTest.class) ;

    Jaxb2Marshaller jaxb2Marshaller;
    Jaxb2Marshaller jaxb2FragmentMarshaller;

    Step1Form1095Processor step1Form1095bProcessor ;

    Step2Form1094Processor step2Form1094bProcessor ;

    Step3Form109495Writer step3Form10945bWriter ;

    private us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.ObjectFactory
            messageObjectFactory =
            new us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.ObjectFactory();

    private ObjectFactory air7ObjectFactory = new ObjectFactory();

    @Autowired
    FlatFileItemReader<FilerDemographicDto> applicationReader ;

    @Autowired
    ProfileProperties profileProperties ;

    @Before
    public void before() {
        JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution(BatchExportConstants.JobNames.ORIGINALS, 1L, 1L) ;
        step1Form1095bProcessor = new Step1Form1095Processor() ;
        step1Form1095bProcessor.beforeStep(MetaDataInstanceFactory.createStepExecution(jobExecution, "step1", 1L));
        step2Form1094bProcessor = new Step2Form1094Processor() ;
        step2Form1094bProcessor.beforeStep(MetaDataInstanceFactory.createStepExecution(jobExecution, "step2", 1L));
        step3Form10945bWriter = new Step3Form109495Writer() ;
        step3Form10945bWriter.profileProperties = this.profileProperties ;
        step3Form10945bWriter.irsRecordDetails1095BRepository = Mockito.mock(IrsRecordDetails1095BRepository.class) ;
        step3Form10945bWriter.irsSubmissionDetailsRepository = Mockito.mock(IrsSubmissionDetailsRepository.class) ;
        step3Form10945bWriter.irsTransmissionDetailsRepository = Mockito.mock(IrsTransmissionDetailsRepository.class) ;
        step3Form10945bWriter.irsRecordDetails1095BJdbcRepository = Mockito.mock(IrsRecordDetails1095BJdbcRepository.class) ;
        step3Form10945bWriter.stepExecution = MetaDataInstanceFactory.createStepExecution(jobExecution, "step3", 1L) ;
        // Just put AR so it finds and output directory
        step3Form10945bWriter.stepExecution.getJobExecution().getExecutionContext().put(STATE, "AR") ;
        step3Form10945bWriter.stepExecution.getJobExecution().getExecutionContext().put(YEAR, 2015) ;
        ExportBatchConfiguration config = new ExportBatchConfiguration() ;
        jaxb2Marshaller = step3Form10945bWriter.jaxb2Marshaller = config.jaxb2Marshaller() ;
        jaxb2FragmentMarshaller = step3Form10945bWriter.jaxb2FragmentMarshaller = config.jaxb2FragmentMarshaller() ;
    }

    /**
     * Writes a Form10945B with the minimum amount of data required.
     * @throws Exception
     */
    @Test
    @Ignore("Jenkins build servers do not have the proper out XML directores created")
    public void writeMinimum() throws Exception {
        // Add a single Filer 1095B form
        List<FilerDemographicDto> filers = Step1Form1095WriterTest.importFilers("classpath:INFSSICE02042016_01.dat", applicationReader) ;
        FilerDemographicDto filerDto = filers.get(0) ;
        Filer filer = BatchUtils.convert(filerDto) ;
        filer.setSourceCd("GAStep3WriteMin");
        // Add the filer as covered
        TreeMap<String, CoveredPerson> coveredPeople = new TreeMap<>();
        coveredPeople.put("HopefullyDoesntMatter", Convert.getCoveredPersonFromFiler(filer));
        filer.setCoveredpersons(coveredPeople) ;
        filer.setTaxYear("2015");
        Step1Form1095Dto form1095bDto = new Step1Form1095Dto() ;
        form1095bDto.setFiler(filer) ;
        step1Form1095bProcessor.process(form1095bDto) ;

        // Create a 1094B
        Step2Form1094Dto step2Form1094bDto = new Step2Form1094Dto() ;
        step2Form1094bDto.setSourceSystemConfig(Step2Form1094WriterTest.getSourceSystemConfig(filer.getProviderEIN()));
        step2Form1094bProcessor.process(step2Form1094bDto) ;

        // Set after the process() call, or they will get over-written
        step2Form1094bDto.getForm1094BUpstreamDetailType().getForm1095BUpstreamDetail().add(form1095bDto.getForm1095BUpstreamDetailType()) ;

        Step3ProcessorResult step3Form10945bDto = new Step3ProcessorResult() ;
        Step3Form109495Pairing pairing = new Step3Form109495Pairing(new Step2Form1094Dto()) ;
        step3Form10945bDto.pairings.add(pairing);
        pairing.step2Form1094bDto = step2Form1094bDto ;
        pairing.step1Form1095bDtoList.add(form1095bDto) ;

        step3Form10945bWriter.write(Collections.singletonList(step3Form10945bDto));

        // Parse the XML back into objects, assert on data.
        File outputDir = ExportUtil.getOutputDirectory("AR",profileProperties) ;
        List<Step4109495Data> list = ExportUtil.getForm109495FilenamesForStep4(step3Form10945bWriter.stepExecution.getJobExecution()) ;
        for (Step4109495Data data : list ) {
            BatchTestUtil.validateIrsAcaXml(data.getFilename()) ;
        }
    }

    @Test
    public void testCreateFormDataFilename() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(1972, 10, 1, 23, 22, 53) ;
        String filename = step3Form10945bWriter.createFormDataFilename("HELLO", "whatever", dateTime) ;
        assertEquals("1094HELLO_Request_whatever_19721001T232253000Z.xml", filename);
    }

    @Test
    public void replaceRecordId() throws Exception {
        String xml = "<HOwdy><RecordId>Anything 12345</RecordId><date/><RecordId>Only first should be replaced.</RecordId></HOwdy>" ;
        String xml2 = "<HOwdy><RecordId>24</RecordId><date/><RecordId>Only first should be replaced.</RecordId></HOwdy>" ;
        assertEquals(xml2, step3Form10945bWriter.replaceRecordId(xml, 24)) ;
    }

    @Ignore("Ignored for now. Can be re-enabled if xml rendering is to be tested.")
    @Test
    public void writeForm1094() throws Exception {
        File tempFile = File.createTempFile("Step3Form109495WriterTest.writeForm1094", "xml") ;
        Form1094BUpstreamDetailType form1094 = new Form1094BUpstreamDetailType() ;
        genericSetLong(form1094, "setForm1095BAttachedCnt", 3);
        Form109495BTrnsmtUpstreamType rootElement = messageObjectFactory.createForm109495BTrnsmtUpstreamType();
        rootElement.getForm1094BUpstreamDetail().add(form1094);
        JAXBElement<Form109495BTrnsmtUpstreamType> rootElementDoc = messageObjectFactory.createForm109495BTransmittalUpstream(rootElement);
        List<String> form1095List = new ArrayList<>() ;

        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("us.gov.treasury.irs", "us.deloitteinnovation.aca.batch.export");
        Map<String, Object> map = new HashMap<>();
        map.put("jaxb.formatted.output", true);
        map.put(Marshaller.JAXB_FRAGMENT, Boolean.TRUE) ;
        jaxb2Marshaller.setMarshallerProperties(map);

        Form1095BUpstreamDetailType form1095 ;
        form1095 = new Form1095BUpstreamDetailType() ;
        genericSetLong(form1095, "setRecordId", 1);
        form1095List.add(JaxbUtils.renderToStringNoNamespace(jaxb2Marshaller, air7ObjectFactory.createForm1095BUpstreamDetail(form1095)));
        form1095 = new Form1095BUpstreamDetailType() ;
        genericSetLong(form1095, "setRecordId", 1);
        form1095List.add(JaxbUtils.renderToStringNoNamespace(jaxb2Marshaller, air7ObjectFactory.createForm1095BUpstreamDetail(form1095)));
        form1095 = new Form1095BUpstreamDetailType() ;
        genericSetLong(form1095, "setRecordId", 1);
        form1095List.add(JaxbUtils.renderToStringNoNamespace(jaxb2Marshaller, air7ObjectFactory.createForm1095BUpstreamDetail(form1095)));

        step3Form10945bWriter.writeForm1094(tempFile, rootElementDoc, form1095List);
        // Read the temp file as a string
        String form1094FromFile = FileUtils.readFileToString(tempFile) ;
        // Create a string of the form1094 rendered together
        genericSetLong(form1094, "setForm1095BAttachedCnt", 3);
        form1095 = new Form1095BUpstreamDetailType() ;
        genericSetLong(form1095, "setRecordId", 1);
        form1094.getForm1095BUpstreamDetail().add(form1095) ;
        form1095 = new Form1095BUpstreamDetailType() ;
        genericSetLong(form1095, "setRecordId", 1);
        form1094.getForm1095BUpstreamDetail().add(form1095) ;
        form1095 = new Form1095BUpstreamDetailType() ;
        genericSetLong(form1095, "setRecordId", 1);
        form1094.getForm1095BUpstreamDetail().add(form1095) ;
        String form1094String = JaxbUtils.renderToString(jaxb2Marshaller, rootElementDoc) ;
        assertEquals(form1094String, form1094FromFile) ;
    }

}
