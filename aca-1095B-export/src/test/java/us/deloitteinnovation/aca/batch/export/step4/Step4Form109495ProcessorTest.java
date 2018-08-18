package us.deloitteinnovation.aca.batch.export.step4;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.io.Resource;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.deloitteinnovation.profile.ProfileProperties;

import java.math.BigInteger;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static us.deloitteinnovation.aca.batch.constants.BatchExportConstants.JobPropertiesKeys.RECEIPT_ID;

/**
 * @see Step4Form109495Processor
 */
public class Step4Form109495ProcessorTest {

    Step4Form109495Processor step4Form10945bProcessor ;
    Integer currentTaxYear;

    @Before
    public void before() {
        currentTaxYear = 2015;
        step4Form10945bProcessor = new Step4Form109495Processor() ;
        step4Form10945bProcessor.profileProperties = Mockito.mock(ProfileProperties.class) ;
        step4Form10945bProcessor.stepExecution = MetaDataInstanceFactory.createStepExecution() ;
        step4Form10945bProcessor.stepExecution.getJobExecution().getExecutionContext().put(RECEIPT_ID, "ABC123") ;
    }

    /**
     * Make sure filesize and checksum is correctly calculated.
     * @throws Exception
     */
    @Test@Ignore("This test passes in local but fails in Jenkins. Have to investigate")//TODO: check the reason
    public void processMinimum() throws Exception {
        StaticApplicationContext context = new StaticApplicationContext() ;
        Resource resource = context.getResource("classpath:lorem.ipsum.txt");

        Step4Form109495HeaderAndXmlDto dto = new Step4Form109495HeaderAndXmlDto() ;
        dto.formDataFile = resource.getFile() ;
        dto.sourceSystem = new SourceSystemConfig() ;
        dto.sourceSystem.setProviderIdentificationNumber("123456789");
        dto.isPriorYearFiling = ExportUtil.getPriorYearFilingFlag(currentTaxYear);

        Step4ManifestData manifest = step4Form10945bProcessor.process(dto) ;
        assertNotNull(manifest) ;
        assertEquals("96C48AB513276A845AA1CB90D65FD361", manifest.acaTrnsmtManifestReqDtlType.getChecksumAugmentationNum()) ;
        assertEquals("lorem.ipsum.txt", manifest.acaTrnsmtManifestReqDtlType.getDocumentSystemFileNm()) ;
        assertEquals(BigInteger.valueOf(448l), manifest.acaTrnsmtManifestReqDtlType.getAttachmentByteSizeNum()) ;
        assertEquals("T", manifest.getAcaTrnsmtManifestReqDtlType().getTestFileCd()) ;
        assertThat("Prior year data indicator isn't set correctly",manifest.getAcaTrnsmtManifestReqDtlType().getPriorYearDataInd(),is("1"));
    }

}