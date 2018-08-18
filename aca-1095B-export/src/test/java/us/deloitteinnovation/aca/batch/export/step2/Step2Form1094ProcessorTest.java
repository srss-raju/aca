package us.deloitteinnovation.aca.batch.export.step2;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.test.MetaDataInstanceFactory;
import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static us.deloitteinnovation.aca.batch.constants.BatchExportConstants.JobPropertiesKeys.YEAR;

/**
 *
 */
public class Step2Form1094ProcessorTest {


    Step2Form1094Processor step2Form1094bProcessor ;

    public static void assertStep2Form1094b(Step2Form1094Dto dto) {
        assertNotNull(dto);
        assertNotNull(dto.getSourceSystemConfig().getProviderIdentificationNumber());
        Form1094BUpstreamDetailType form1094b = dto.getForm1094BUpstreamDetailType();
        assertNotNull(form1094b);
        assertEquals(dto.getSourceSystemConfig().getProviderIdentificationNumber(), form1094b.getEmployerEIN());
        assertNotNull(form1094b.getTaxYr());
        assertNotNull(form1094b.getMailingAddressGrp());
        assertNotNull(form1094b.getMailingAddressGrp().getUSAddressGrp());
        assertNotNull(form1094b.getTINRequestTypeCd());
        assertThat("TINRequest Type Code doesn't match", form1094b.getTINRequestTypeCd().toString(), equalTo("BUSINESS_TIN"));
    }

    public static void assertUsAddressGroup(USAddressGrpType usAddress) {
        assertNotNull(usAddress);
        assertNotNull(usAddress.getAddressLine1Txt());
        assertNotNull(usAddress.getCityNm());
        assertNotNull(usAddress.getUSStateCd());
        assertNotNull(usAddress.getUSZIPCd());
    }

    @Before
    public void before() throws Exception {
        step2Form1094bProcessor = new Step2Form1094Processor() ;
        step2Form1094bProcessor.stepExecution = MetaDataInstanceFactory.createStepExecution() ;
        step2Form1094bProcessor.stepExecution.getJobExecution().getExecutionContext().put(YEAR, 2015) ;
    }

    @Test
    public void processMinimal() throws Exception {
        Step2Form1094Dto dto = new Step2Form1094Dto() ;
        SourceSystemConfig systemConfig = new SourceSystemConfig() ;
        systemConfig.setSourceCd("AAAAAAA");
        systemConfig.setReturnAddressLine1("Department of Human Services");
        systemConfig.setProviderName("Department of Human Services");
        systemConfig.setReturnAddressLine1("Department of Human Services");
        systemConfig.setReturnAddressCity("Miami");
        systemConfig.setReturnAddressState("Florida");
        systemConfig.setStateAbbreviation("FL");
        systemConfig.setReturnAddressLine1("111111111");
        systemConfig.setReturnAddressCity("222222");
        systemConfig.setReturnAddressZip("123456789");
        systemConfig.setProviderIdentificationNumber("1234567890");
        systemConfig.setProviderAddressLine1(systemConfig.getReturnAddressLine1());
        systemConfig.setProviderAddressLine2(systemConfig.getReturnAddressLine2());
        systemConfig.setProviderCityOrTown(systemConfig.getReturnAddressCity());
        systemConfig.setProviderZipOrPostalCode(systemConfig.getReturnAddressZip());
        systemConfig.setProviderStateOrProvince(systemConfig.getStateAbbreviation());
        systemConfig.setProviderIdentificationNumber("00066631");
        systemConfig.setProviderContactFirstName("Jane");
        systemConfig.setProviderContactLastName("Doe");
        systemConfig.setProviderContactNo(5123456789l);
        dto.setSourceSystemConfig(systemConfig);
        Step2Form1094Dto processedDto  = step2Form1094bProcessor.process(dto) ;
        assertStep2Form1094b(processedDto);
    }

}
