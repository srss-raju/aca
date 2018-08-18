package us.deloitteinnovation.batch.objectstore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;
import us.deloitteinnovation.aca.entity.IrsTransmissionDetails;
import us.deloitteinnovation.aca.repository.IrsTransmissionDetailsRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by tthakore on 3/29/2016.
 */


public class ErrorReportingObjectStoreTest {

    ErrorReportingObjectStore errorReportingObjectStore;

    @Autowired
    public JdbcTemplate jdbcTemplate;


    private IrsTransmissionDetailsRepository irsTransmissionService;

    @Before
    public void intializeProcess() {
        errorReportingObjectStore = errorReportingObjectStore.getInstance();
        irsTransmissionService = mock(IrsTransmissionDetailsRepository.class);
    }

    @Test
    public void getErrorFileFolderTest() throws IOException {
        Assert.assertEquals("/opt/ACA-DATA/AS/In/XML/", errorReportingObjectStore.getErrorFileFolder("AS"));
        Assert.assertEquals("/opt/ACA-DATA/AS/out/Reports/", errorReportingObjectStore.getOutPutFolderPath());
    }

    @Test
    public void getReceiptIdFromFileNameTest() {
        Assert.assertEquals("1095B-16-00010874", errorReportingObjectStore.getReceiptIdFromFileName("Ack_1095B-16-00010874_03-23-2016_02-27-08PM_UTC.xml"));
    }

    @Test
    public void getTransmissionIDFromReceiptIDTest() {
        List<IrsTransmissionDetails> list = new ArrayList<>();
        IrsTransmissionDetails item = new IrsTransmissionDetails();
        item.setTransmissionId(180);
        list.add(item);
        errorReportingObjectStore.setIrsTransmissionService(irsTransmissionService);
        Mockito.when(irsTransmissionService.findByTransmissionReceiptId("1095B-16-00010874")).thenReturn(list);
        Assert.assertEquals(Integer.valueOf("180"), errorReportingObjectStore.getTransmissionInfoFromReceiptID("1095B-16-00010874").getTransmissionId());
    }
}
