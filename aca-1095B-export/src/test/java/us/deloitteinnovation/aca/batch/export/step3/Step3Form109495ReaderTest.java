package us.deloitteinnovation.aca.batch.export.step3;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.MetaDataInstanceFactory;
import us.deloitteinnovation.aca.batch.BatchTestUtil;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.export.ExportJob1094RepositoryInMemory;
import us.deloitteinnovation.aca.batch.export.ExportJob1095RepositoryInMemory;
import us.deloitteinnovation.aca.batch.export.ExportJobRepositoryInDB;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.model.SourceSystemConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static us.deloitteinnovation.aca.batch.constants.BatchExportConstants.JobPropertiesKeys.STATE;

/**
 * @see Step3Form109495Reader
 */
public class Step3Form109495ReaderTest {

    ExportJobRepositoryInDB exportJobRepository ;
    ExportJob1094RepositoryInMemory exportJob1094RepositoryInMemory ;

    Step3Form109495Reader step3Form10945bReader ;

    @Before
    public void before() {
        step3Form10945bReader = new Step3Form109495Reader() ;
        step3Form10945bReader.exportJob1094Repository = exportJob1094RepositoryInMemory = new ExportJob1094RepositoryInMemory() ;
        step3Form10945bReader.exportJobRepository = exportJobRepository = new ExportJobRepositoryInDB();
        step3Form10945bReader.sourceSystemConfigDataService = mock(SourceSystemConfigDataService.class) ;
        step3Form10945bReader.stepExecution = BatchTestUtil.createStepExecutionMock(BatchExportConstants.JobNames.ORIGINALS);
        step3Form10945bReader.stepExecution.getJobExecution().getExecutionContext().put(STATE, "XY") ;
    }

    /**
     * Ensures that the read() method pairs 1094 and 95 forms by EIN number.
     * @throws Exception
     */
    @Test@Ignore
    public void read() throws Exception {
        StepExecution stepExecution  = BatchTestUtil.createStepExecutionMock(BatchExportConstants.JobNames.ORIGINALS);
        String ein = "1234567890" ;
        String state = "XY" ;
        Integer year = 2015;
        SourceSystemConfig config = new SourceSystemConfig() ;
        config.setProviderIdentificationNumber(ein);
        config.setSourceCd(state + "000000");
        when(step3Form10945bReader.sourceSystemConfigDataService.getByState(state, year)).thenReturn(config);

        exportJob1094RepositoryInMemory.save(createForm1094WithEin("123681267", "ffffaaa"));
        exportJob1094RepositoryInMemory.save(createForm1094WithEin(ein, config.getSourceCd()));
        exportJob1094RepositoryInMemory.save(createForm1094WithEin("213841212", "DDDDD"));
        // Make source codes different to show only ein is used
        exportJobRepository.save(createForm1095("123681267", "ffffaaa", 12345L), stepExecution);
        exportJobRepository.save(createForm1095(ein, config.getSourceCd(), 234262L), stepExecution);
        exportJobRepository.save(createForm1095("1234813712", "EFGH", 9876L), stepExecution);
        exportJobRepository.save(createForm1095(ein, config.getSourceCd(), 23332L), stepExecution);
        exportJobRepository.save(createForm1095("9234134512", "IJKL", 23332L), stepExecution);

        Step3Form109495Pairing pairing = step3Form10945bReader.read() ;
        assertNotNull(pairing) ;
        assertNotNull(pairing.step2Form1094bDto) ;
        assertNotNull(pairing.step1Form1095bDtoList) ;
        assertEquals(2, pairing.step1Form1095bDtoList.size()) ;
        for (Step1Form1095Dto dto : pairing.step1Form1095bDtoList) {
            assertEquals(ein, dto.getFiler().getProviderEIN()) ;
        }
    }


    protected Step2Form1094Dto createForm1094WithEin(String ein, String sourceCode) {
        Step2Form1094Dto dto = new Step2Form1094Dto() ;
        SourceSystemConfig config = new SourceSystemConfig() ;
        config.setSourceCd(sourceCode);
        config.setProviderIdentificationNumber(ein);
        dto.setSourceSystemConfig(config);
        return dto ;
    }

    protected Step1Form1095Dto createForm1095(String ein, String sourceCd, Long sourceUniqueId) {
        Step1Form1095Dto dto = new Step1Form1095Dto() ;
        Filer filer = new Filer() ;
        filer.setSourceCd(sourceCd) ;
        filer.setSourceUniqueId(sourceUniqueId);
        filer.setProviderEIN(ein);
        filer.setTaxYear("2016");
        dto.setFiler(filer) ;
        dto.setRawXml(new String("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><1094>1094 Content<1095>1095 Content</1095></1094>").getBytes());
        return dto ;
    }
}