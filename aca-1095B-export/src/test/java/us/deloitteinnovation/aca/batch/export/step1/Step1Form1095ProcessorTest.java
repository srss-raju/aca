package us.deloitteinnovation.aca.batch.export.step1;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import us.deloitteinnovation.aca.batch.BatchTestUtil;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.export.ExportBatchConfiguration;
import us.deloitteinnovation.aca.model.Filer;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 * @see Step1Form1095Processor
 */
public class Step1Form1095ProcessorTest {

    Step1Form1095Processor aca1095bExportProcessor;

    Jaxb2Marshaller jaxb2Marshaller;

    @Before
    public void before() {
        aca1095bExportProcessor = new Step1Form1095Processor() ;
        ExportBatchConfiguration config = new ExportBatchConfiguration() ;
        jaxb2Marshaller = config.jaxb2Marshaller() ;
        aca1095bExportProcessor.stepExecution = BatchTestUtil.createStepExecutionMock(BatchExportConstants.JobNames.ORIGINALS) ;
    }

    /**
     * Tests the absolute minimum number of data values necessary during a process() call.
     * @throws Exception
     */
    @Test@Ignore
    public void processMinimumValues() throws Exception {
        Step1Form1095Dto dto = new Step1Form1095Dto() ;
        Filer filer = new Filer() ;
        filer.setRecipientDOB("2013-01-01");
        filer.setSourceCd("GA11111111");
        filer.setRecipientState(StateType.GA.value());
        filer.setProviderState(StateType.GA.value());
        filer.setTaxYear("2015");
        dto.setFiler(filer) ;
        Step1Form1095Dto dto2 = aca1095bExportProcessor.process(dto) ;
        assert1095bDetailNotNull(dto2.getForm1095BUpstreamDetailType()) ;
    }


    protected void assert1095bDetailNotNull(Form1095BUpstreamDetailType detail) {
        assertNotNull(detail);
        assertNotNull(detail.getCoveredIndividualGrp());
        assertNotNull(detail.getIssuerInfoGrp());
        assertNotNull(detail.getLineNum());
        assertNotNull(detail.getResponsibleIndividualGrp());
        assertNotNull(detail.getTaxYr());
    }

    @Test@Ignore
    public void specialCharacters() throws Exception {
        Step1Form1095Dto dto = new Step1Form1095Dto() ;
        Filer filer = new Filer() ;
        filer.setRecipientAddLine1("Apt #1");
        filer.setRecipientAddLine2("Line ' &  < -- ‘ ");
        filer.setRecipientDOB("2013-01-01");
        filer.setSourceCd("GA11111111");
        filer.setRecipientState(StateType.GA.value());
        filer.setProviderState(StateType.GA.value());
        filer.setProviderCity("Alam Rock[") ;
        filer.setTaxYear("2015");
        dto.setFiler(filer) ;
        Step1Form1095Dto dto2 = aca1095bExportProcessor.process(dto) ;
        Form1095BUpstreamDetailType detail = dto2.getForm1095BUpstreamDetailType() ;
        assert1095bDetailNotNull(detail) ;
        USAddressGrpType addr = detail.getResponsibleIndividualGrp().getMailingAddressGrp().getUSAddressGrp() ;
        assertEquals("Apt 1", addr.getAddressLine1Txt()) ;
        assertEquals("Line", addr.getAddressLine2Txt()) ;
        assertEquals("Alam Rock", dto2.getForm1095BUpstreamDetailType().getIssuerInfoGrp().getMailingAddressGrp().getUSAddressGrp().getCityNm()) ;
    }
}