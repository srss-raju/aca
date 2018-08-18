package us.deloitteinnovation.batch.listener;

/**
 * Created by tthakore on 4/12/2016.
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.exceptions.MockitoLimitations;
import org.springframework.test.util.ReflectionTestUtils;
import us.deloitteinnovation.aca.batch.listeners.ItemReaderListener;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095B;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095BPK;
import us.deloitteinnovation.aca.repository.IrsRecordDetails1095BRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;



public class ItemReaderListenerTest {

    ItemReaderListener itemReaderListener;
    IrsRecordDetails1095BRepository irsRecordDetails1095BRepository;
    ErrorReportingObjectStore errorReportingObjectStore;

    @Before
    public void intialiseValues()
    {
        itemReaderListener = new ItemReaderListener();
        irsRecordDetails1095BRepository = mock(IrsRecordDetails1095BRepository.class);
        errorReportingObjectStore = mock( ErrorReportingObjectStore.getInstance().getClass());
    }

    // checkes wether map values for records are getting inserted into object store or not.
    @Test
    public  void getAllRecordsFromReceiptIDTest()
    {
        try{
            List<IrsRecordDetails1095B> list = new ArrayList<>();
            IrsRecordDetails1095B item = new IrsRecordDetails1095B();
            item.setId(new IrsRecordDetails1095BPK());
            item.getId().setTransmissionId(180);
            item.getId().setRecordId(1);
            item.getId().setSubmissionId(1);
            list.add(item);
            Mockito.when(irsRecordDetails1095BRepository.getRecordsDetailsTransactionID(180)).thenReturn(list);
            errorReportingObjectStore.currentTransmissionRecordMap = new HashMap<>();
            ReflectionTestUtils.setField(itemReaderListener, "irsRecordDetails1095BRepository", irsRecordDetails1095BRepository);
            ReflectionTestUtils.setField(itemReaderListener, "errorReportingObjectStore", errorReportingObjectStore);
            itemReaderListener.getAllRecordsFromReceiptID("180");
            Assert.assertEquals(true, errorReportingObjectStore.currentTransmissionRecordMap.containsKey("180|1|1"));
            errorReportingObjectStore.currentTransmissionRecordMap.clear();
        }
        catch (Exception e){}

    }
}
