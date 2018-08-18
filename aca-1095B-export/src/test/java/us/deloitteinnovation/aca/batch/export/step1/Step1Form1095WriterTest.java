package us.deloitteinnovation.aca.batch.export.step1;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.FlatFileImportTestConfiguration;
import us.deloitteinnovation.aca.batch.BatchTestUtil;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.export.ExportBatchConfiguration;
import us.deloitteinnovation.aca.batch.export.ExportIntegTestConfiguration;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.repository.Irs1095XMLRepository;
import us.deloitteinnovation.profile.ProfileConfiguration;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * @see Step1Form1095Writer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {ProfileConfiguration.class, FlatFileImportTestConfiguration.class, ExportIntegTestConfiguration.class})

public class Step1Form1095WriterTest {

    private static final Logger LOG = LoggerFactory.getLogger(Step1Form1095WriterTest.class) ;

    Step1Form1095Writer             step1Form1095bWriter;
    //ExportJob1095RepositoryInMemory exportRepository ;
    @Autowired
    ExportJob1095Repository exportJobRepository;

    private Jaxb2Marshaller jaxb2Marshaller;

    @Autowired
    Irs1095XMLRepository irs1095XMLRepository;

    @Autowired
    protected Jaxb2Marshaller jaxb2FragmentMarshaller;

    @Autowired
    FlatFileItemReader<FilerDemographicDto> applicationReader ;

    Step1Form1095Processor step1Form1095bProcessor ;

    /**
     * Ensures the required member variables are not null.
     * @param withXml
     */
    public static void assertForm1095bNotNull(Step1Form1095Dto withXml) {
        assertNotNull(withXml) ;
        assertNotNull(withXml.getRawXml()) ;
        assertTrue("XML should be rendered in bytes", withXml.getRawXml().length > 0) ;
        assertNotNull(withXml.getForm1095BUpstreamDetailType()) ;
        assertNotNull(withXml.getFiler()) ;
       assertNotNull(withXml.getFiler().getSourceCd()) ;
        assertNotNull(withXml.getFiler().getSourceUniqueId()) ;
    }

    /**
     * Reads a list of Filers from the given resourceLocation.  Does not persist the DTOs.
     * @param resourceLocation
     * @param applicationReader
     * @return
     * @throws Exception
     */
    public static List<FilerDemographicDto> importFilers(String resourceLocation, FlatFileItemReader<FilerDemographicDto> applicationReader) throws Exception{
        StaticApplicationContext context = new StaticApplicationContext() ;
        Resource resource = context.getResource(resourceLocation);
        applicationReader.setResource(resource);
        applicationReader.open(new ExecutionContext());
        List<FilerDemographicDto> list = new ArrayList<>() ;
        FilerDemographicDto dto ;
        while( (dto = applicationReader.read()) != null) {
            list.add(dto);
        }
        applicationReader.close();
        return list ;
    }

    @Before
    public void before() {
        step1Form1095bWriter = new Step1Form1095Writer();
        ExportBatchConfiguration config = new ExportBatchConfiguration() ;
        step1Form1095bWriter.jaxb2FragmentMarshaller = jaxb2FragmentMarshaller;
        step1Form1095bWriter.exportJobRepository = exportJobRepository ;
        step1Form1095bProcessor = new Step1Form1095Processor() ;
        step1Form1095bProcessor.stepExecution = MetaDataInstanceFactory.createStepExecution();
    }

    @Test
    @Ignore
    public void writeMinimum() throws Exception {
        List<Step1Form1095Dto> list = new ArrayList() ;
        Step1Form1095Dto dto ;
        dto = new Step1Form1095Dto() ;
        list.add(dto) ;
        Filer filer = new Filer() ;
        filer.setSourceCd("writeMinimum");
        filer.setSourceUniqueId(123456789);
        filer.setTaxYear("2015");
        dto.setFiler(filer) ;
        Form1095BUpstreamDetailType form1095B = new Form1095BUpstreamDetailType() ;
        dto.setForm1095BUpstreamDetailType(form1095B);
        step1Form1095bWriter.stepExecution = BatchTestUtil.createStepExecutionMock(BatchExportConstants.JobNames.ORIGINALS);
        step1Form1095bWriter.write(list);
        Step1Form1095Dto withXml = exportJobRepository.getForm1095bById(dto.getFiler().getSourceCd(), dto.getFiler().getSourceUniqueId(), new Integer(2015)) ;
        assertForm1095bNotNull(withXml);
    }


    @Test@Ignore("Test it later")
    public void writeFull() throws Exception {
        List<FilerDemographicDto> filers = importFilers("classpath:INFSSICE02042016_01.dat", applicationReader) ;
        FilerDemographicDto filerDto = filers.get(0) ;
        Filer filer = BatchUtils.convert(filerDto) ;
        filer.setSourceCd("GAwriteFull");
        List<Step1Form1095Dto> list = new ArrayList() ;
        Step1Form1095Dto dto ;
        dto = new Step1Form1095Dto() ;
        dto.setFiler(filer) ;
        Form1095BUpstreamDetailType form1095B = new Form1095BUpstreamDetailType() ;
        dto.setForm1095BUpstreamDetailType(form1095B);
        list.add(step1Form1095bProcessor.process(dto)) ;
        step1Form1095bWriter.stepExecution = BatchTestUtil.createStepExecutionMock(BatchExportConstants.JobNames.ORIGINALS);
        step1Form1095bWriter.write(list);
        Step1Form1095Dto withXml = exportJobRepository.getForm1095bById(dto.getFiler().getSourceCd(), dto.getFiler().getSourceUniqueId(), new Integer(2015)) ;
        assertForm1095bNotNull(withXml);
    }

}
