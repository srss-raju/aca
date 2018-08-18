package us.deloitteinnovation.batch.processor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;
import us.deloitteinnovation.aca.batch.processor.IrsErrorReportingAppProcessor;
import us.deloitteinnovation.aca.entity.IrsTransmissionDetails;
import us.gov.treasury.irs.ext.aca.air._7.ACATransmitterSubmissionDetail;
import us.gov.treasury.irs.ext.aca.air._7.SubmissionLevelStatusCodeType;
import us.gov.treasury.irs.ext.aca.air._7.TransmitterErrorDetailGrp;
import us.gov.treasury.irs.ext.aca.air._7.impl.ACATransmitterSubmissionDetailImpl;
import us.gov.treasury.irs.ext.aca.air._7.impl.TransmitterErrorDetailGrpImpl;
import us.gov.treasury.irs.msg.form1094_1095bctransmittermessage.FormBCTransmitterSubmissionDtl;
import us.gov.treasury.irs.msg.form1094_1095bctransmittermessage.impl.FormBCTransmitterSubmissionDtlImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

/**
 * Created by tthakore on 4/12/2016.
 */
public class IrsErrorReportingAppProcessorTest {

    IrsErrorReportingAppProcessor irsErrorReportingAppProcessor;
    ErrorReportingObjectStore errorReportingObjectStore;

    @Before
    public void initialiseValues()
    {
        irsErrorReportingAppProcessor = new IrsErrorReportingAppProcessor();
        errorReportingObjectStore = mock(ErrorReportingObjectStore.getInstance().getClass());
    }

    @Test
    public void getRecordInfoKeyTest()
    {
        TransmitterErrorDetailGrp transmitterErrorDetailGrp = new TransmitterErrorDetailGrpImpl();
        transmitterErrorDetailGrp.setUniqueSubmissionId("1095B-15-00000012|1");
        Mockito.when(errorReportingObjectStore.getCurrentTransmissionID()).thenReturn(180);
        ReflectionTestUtils.setField(irsErrorReportingAppProcessor, "errorReportingObjectStore", errorReportingObjectStore);
        Assert.assertEquals("180|1", irsErrorReportingAppProcessor.getRecordInfoKey(transmitterErrorDetailGrp));
    }

    @Test
    public void processTest() throws Exception
    {
        FormBCTransmitterSubmissionDtl item = new FormBCTransmitterSubmissionDtlImpl();
        IrsTransmissionDetails irsTransmissionDetails = new IrsTransmissionDetails();
        errorReportingObjectStore.receiptTransmissionIDMap = new HashMap<>();
        errorReportingObjectStore.currentTransmissionRecordMap = new HashMap<>();
        errorReportingObjectStore.setCurrentFileName("Ack_1095B-15-00000012_04-06-2016_05-07-12PM_UTC.xml");
        Mockito.when(errorReportingObjectStore.getCurrentTransmissionID()).thenReturn(180);
        irsTransmissionDetails.setTransmissionId(180);
        errorReportingObjectStore.receiptTransmissionIDMap.put("1095B-15-00000012", irsTransmissionDetails);
        Mockito.when(errorReportingObjectStore.getReceiptIdFromFileName(errorReportingObjectStore.getCurrentFileName())).thenReturn("1095B-15-00000012");
        ACATransmitterSubmissionDetailImpl acaTransmitterSubmissionDetail = new ACATransmitterSubmissionDetailImpl();
        List<TransmitterErrorDetailGrp> list = new ArrayList<>();
        TransmitterErrorDetailGrp itemGrp = new TransmitterErrorDetailGrpImpl();
        itemGrp.setSubmissionLevelStatusCd(SubmissionLevelStatusCodeType.ACCEPTED_WITH_ERRORS);
        itemGrp.setUniqueSubmissionId("1095B-15-00000012|1");
        list.add(itemGrp);
        item.setACATransmitterSubmissionDetail(acaTransmitterSubmissionDetail);
        ReflectionTestUtils.setField(acaTransmitterSubmissionDetail, "transmitterErrorDetailGrps", list);
        ReflectionTestUtils.setField(irsErrorReportingAppProcessor, "errorReportingObjectStore", errorReportingObjectStore);
        Assert.assertEquals(SubmissionLevelStatusCodeType.ACCEPTED_WITH_ERRORS, irsErrorReportingAppProcessor.process(item).getTransmitterErrorDetailGrps().get(0).getTransmitterErrorDetailGrp().getSubmissionLevelStatusCd());
    }
}
