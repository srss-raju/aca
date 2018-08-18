package us.deloitteinnovation.aca.batch.export.step2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.FlatFileImportTestConfiguration;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.export.ExportBatchConfiguration;
import us.deloitteinnovation.aca.batch.export.ExportJob1094RepositoryInMemory;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095WriterTest;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.deloitteinnovation.profile.ProfileConfiguration;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static us.deloitteinnovation.aca.batch.constants.BatchExportConstants.JobPropertiesKeys.YEAR;

/**
 * @see Step2Form1094Writer
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@SpringApplicationConfiguration(classes = {ProfileConfiguration.class, FlatFileImportTestConfiguration.class})
public class Step2Form1094WriterTest {

    //private static final Logger LOG = LoggerFactory.getLogger(Step2Form1094WriterTest.class) ;

    ExportJob1094RepositoryInMemory exportJob1094RepositoryInMemory;
    private Jaxb2Marshaller jaxb2Marshaller;

    @Autowired
    FlatFileItemReader<FilerDemographicDto> applicationReader;

    Step2Form1094Processor step2Form1094bProcessor;

    Step2Form1094Writer step2Form1094bWriter;

    /**
     * Ensures the required member variables are not null.
     * @param withXml
     */
    public static void assertForm1094bNotNull(Step2Form1094Dto withXml) {
        assertNotNull(withXml);
        assertNotNull(withXml.getRawXml());
        assertNotNull(withXml.getSourceSystemConfig().getProviderIdentificationNumber());
        assertNotNull(withXml.getForm1094BUpstreamDetailType());
        assertNotNull(withXml.getForm1094BUpstreamDetailType().getLineNum());
        assertNotNull(withXml.getForm1094BUpstreamDetailType().getTaxYr());
    }

    @Before
    public void before() {
        step2Form1094bWriter = new Step2Form1094Writer();
        ExportBatchConfiguration config = new ExportBatchConfiguration();
        jaxb2Marshaller = step2Form1094bWriter.jaxb2Marshaller = config.jaxb2Marshaller();
        step2Form1094bWriter.exportJob1094Repository = exportJob1094RepositoryInMemory = new ExportJob1094RepositoryInMemory();
        step2Form1094bProcessor = new Step2Form1094Processor();
        step2Form1094bProcessor.stepExecution = MetaDataInstanceFactory.createStepExecution();
        step2Form1094bProcessor.stepExecution.getJobExecution().getExecutionContext().put(YEAR, 2015);
    }

    /**
     * Writes a Form1094 with the minimum amount of data required.
     * @throws Exception
     */
    @Test
    public void writeMinimum() throws Exception {
        List<Step2Form1094Dto> list = new ArrayList();
        Step2Form1094Dto dto;
        dto = new Step2Form1094Dto();
        Form1094BUpstreamDetailType form1094B = new Form1094BUpstreamDetailType();
        dto.setForm1094BUpstreamDetailType(form1094B);
        dto.setSourceSystemConfig(getSourceSystemConfig("123456789"));
        list.add(step2Form1094bProcessor.process(dto));
        step2Form1094bWriter.write(list);
        Step2Form1094Dto withXml = exportJob1094RepositoryInMemory.getForm1094bById("123456789");
        assertForm1094bNotNull(withXml);
    }

    /**
     * Writes a Form1094 with the most data available.
     * @throws Exception
     */
    @Test
    public void writeFull() throws Exception {
        // At the time being, the "full" data set is the same as the minimal.  This method remains in case that changes.
        List<FilerDemographicDto> filers = Step1Form1095WriterTest.importFilers("classpath:INFSSICE02042016_01.dat", applicationReader);
        FilerDemographicDto filerDto = filers.get(0);
        Filer filer = BatchUtils.convert(filerDto);
        filer.setSourceCd("GAwriteFull");
        List<Step2Form1094Dto> list = new ArrayList();
        Step2Form1094Dto dto;
        dto = new Step2Form1094Dto();
        dto.setSourceSystemConfig(getSourceSystemConfig(filer.getProviderEIN()));
        Form1094BUpstreamDetailType form1094B = new Form1094BUpstreamDetailType();
        dto.setForm1094BUpstreamDetailType(form1094B);
        list.add(step2Form1094bProcessor.process(dto));
        step2Form1094bWriter.write(list);
        Step2Form1094Dto withXml = exportJob1094RepositoryInMemory.getForm1094bById(filer.getProviderEIN());
        assertForm1094bNotNull(withXml);
    }

    /**
     * @return A SourceSystemConfig for Arkansas.
     */
    public static SourceSystemConfig getSourceSystemConfig(String ein) {
        SourceSystemConfig config = new SourceSystemConfig();
        config.setSourceCd("ARDHSDSS");
        config.setProviderName("Department of Human Services");
        config.setReturnAddressLine1("Department of Human Services");
        config.setReturnAddressCity("Little Rock");
        config.setReturnAddressState("Arkansas");
        config.setStateAbbreviation("AR");
        config.setReturnAddressZip("72203-8076");
        config.setProviderAddressLine1(config.getReturnAddressLine1());
        config.setProviderAddressLine2(config.getReturnAddressLine2());
        config.setProviderCityOrTown(config.getReturnAddressCity());
        config.setProviderZipOrPostalCode(config.getReturnAddressZip());
        config.setProviderStateOrProvince(config.getStateAbbreviation());
        config.setProviderIdentificationNumber(ein);
        config.setProviderContactFirstName("Jane");
        config.setProviderContactLastName("Doe");
        config.setProviderContactNo(5123456789l);
        return config;
    }
}