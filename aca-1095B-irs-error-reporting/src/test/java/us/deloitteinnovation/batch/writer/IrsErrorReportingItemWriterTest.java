package us.deloitteinnovation.batch.writer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;
import us.deloitteinnovation.aca.batch.dto.ErrorDetailsDTO;
import us.deloitteinnovation.aca.batch.dto.TransmitterErrorDetailGrpDTO;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;
import us.deloitteinnovation.aca.batch.writer.IrsErrorReportingItemWriter;
import us.deloitteinnovation.aca.batch.writer.IrsErrorReportingService;
import us.gov.treasury.irs.ext.aca.air._7.SubmissionLevelStatusCodeType;
import us.gov.treasury.irs.ext.aca.air._7.TransmitterErrorDetailGrp;
import us.gov.treasury.irs.ext.aca.air._7.impl.TransmitterErrorDetailGrpImpl;

import static org.mockito.Mockito.mock;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tthakore on 4/12/2016.
 */
public class IrsErrorReportingItemWriterTest {

    IrsErrorReportingItemWriter irsErrorReportingItemWriter;

    IrsErrorReportingService irsErrorReportingService;
    private ErrorReportingObjectStore errorReportingObjectStore;


    @Before
    public void initialiseProcess()
    {
        irsErrorReportingItemWriter = new IrsErrorReportingItemWriter();
        irsErrorReportingService = mock(IrsErrorReportingService.class);
        errorReportingObjectStore = mock(ErrorReportingObjectStore.getInstance().getClass());
    }


    @Test
    public void getDateStringTest()
    {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMddYYYY");
        Assert.assertEquals( outputDateFormat.format(date), irsErrorReportingItemWriter.getDateString());
    }

    @Test
    public void writeTest(){
        try{
            List<ErrorDetailsDTO> errorDetailsDTOs = new ArrayList<>();
            ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO();
            List<TransmitterErrorDetailGrpDTO> transmitterErrorDetailGrps = new ArrayList<>();
            TransmitterErrorDetailGrpDTO transmitterErrorDetailGrp = new TransmitterErrorDetailGrpDTO();
            TransmitterErrorDetailGrp transmitterErrorDetailGrp1 = new TransmitterErrorDetailGrpImpl();
            transmitterErrorDetailGrp1.setSubmissionLevelStatusCd(SubmissionLevelStatusCodeType.ACCEPTED_WITH_ERRORS);
            transmitterErrorDetailGrp1.setUniqueSubmissionId("1095TESTB-15-00000012|1");
            transmitterErrorDetailGrp.setTransmitterErrorDetailGrp(transmitterErrorDetailGrp1);
            transmitterErrorDetailGrps.add(transmitterErrorDetailGrp);
            errorDetailsDTO.setTransmitterErrorDetailGrps(transmitterErrorDetailGrps);
            errorDetailsDTOs.add(errorDetailsDTO);


            Mockito.doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    return null;
                }
            }).when(irsErrorReportingService).updateDBStatus(errorDetailsDTOs.get(0));
           irsErrorReportingItemWriter.write(errorDetailsDTOs);
        }
        catch (Exception e)
        {

        }
    }

    @Test
    @Ignore
    public void generateReportFileTest() {
        try {
            ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO();
            List<TransmitterErrorDetailGrpDTO> transmitterErrorDetailGrps = new ArrayList<>();
            TransmitterErrorDetailGrpDTO transmitterErrorDetailGrp = new TransmitterErrorDetailGrpDTO();
            TransmitterErrorDetailGrp transmitterErrorDetailGrp1 = new TransmitterErrorDetailGrpImpl();
            transmitterErrorDetailGrp1.setSubmissionLevelStatusCd(SubmissionLevelStatusCodeType.ACCEPTED_WITH_ERRORS);
            transmitterErrorDetailGrp1.setUniqueSubmissionId("1095TESTB-15-00000012|1");
            transmitterErrorDetailGrp.setTransmitterErrorDetailGrp(transmitterErrorDetailGrp1);
            transmitterErrorDetailGrps.add(transmitterErrorDetailGrp);
            errorDetailsDTO.setTransmitterErrorDetailGrps(transmitterErrorDetailGrps);
            errorDetailsDTO.setReceiptID("1095TESTB-15-00000012");
            errorDetailsDTO.setTransmissionID("1");
            Mockito.when(errorReportingObjectStore.getCurrentFileName()).thenReturn("Ack_1095TESTB-15-00000012_03-23-2016_02-27-08PM_UTC.xml");
            Mockito.when(errorReportingObjectStore.getOutPutFolderPath()).thenReturn("C:\\projects\\ACA_development\\1095b\\aca-1095B-irs-error-reporting\\src\\main\\resources\\xmls\\");
            Mockito.when(errorReportingObjectStore.getReceiptIdFromFileName(errorReportingObjectStore.getCurrentFileName())).thenReturn("1095TESTB-15-00000012");
            Mockito.when(errorReportingObjectStore.getRecordErrorCount()).thenReturn(1);
            Mockito.when(errorReportingObjectStore.getSubmissionErrorCount()).thenReturn(1);
            ReflectionTestUtils.setField(irsErrorReportingItemWriter, "errorReportingObjectStore", errorReportingObjectStore);
            irsErrorReportingItemWriter.generateReportFile(errorDetailsDTO);
        } catch (Exception e) {

            //
        }
    }
}
