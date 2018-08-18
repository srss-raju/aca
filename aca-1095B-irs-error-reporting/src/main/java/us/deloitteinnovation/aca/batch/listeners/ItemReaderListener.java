package us.deloitteinnovation.aca.batch.listeners;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095B;
import us.deloitteinnovation.aca.repository.IrsRecordDetails1095BRepository;

import java.io.IOException;
import java.util.List;

/**
 * Created by tthakore on 4/1/2016.
 */
public class ItemReaderListener implements org.springframework.batch.core.ItemReadListener {

    private static Logger logger = LoggerFactory.getLogger(ItemReaderListener.class);

    @Autowired
    MultiResourceItemReader errorXmlFileReader;

    @Autowired
    IrsRecordDetails1095BRepository irsRecordDetails1095BRepository;


    ErrorReportingObjectStore errorReportingObjectStore = ErrorReportingObjectStore.getInstance();

    @Override
    public void beforeRead() {
    }

    /**
     * afterRead(Object obj)
     *
     * @param obj FormBCTransmitterSubmissionDtl gets generated once staxeventitem reader reads xml file
     *            task is to set current file name and transmission id.
     *            Once we have current file name and transmission id we will get all the records of that specific transmission and map them with error in processor
     **/
    @Override
    public void afterRead(Object obj) {
        try {
            errorReportingObjectStore.setCurrentFileName(errorXmlFileReader.getCurrentResource().getFile().getName().toString());
            String receiptID = errorReportingObjectStore.getReceiptIdFromFileName(errorXmlFileReader.getCurrentResource().getFile().getName().toString());
            String transmissionID = errorReportingObjectStore.receiptTransmissionIDMap.get(receiptID).getTransmissionId().toString();
            getAllRecordsFromReceiptID(transmissionID);

            logger.error("<=========  currently processing file name = "+errorReportingObjectStore.getCurrentFileName());
        } catch (Exception e) {
            logger.error("Error in setting current file name to singleton object", e);
            throw new IllegalArgumentException("current filename can not be set.");
        }
    }

    @Override
    public void onReadError(Exception var1) {
    }


    /**
     * getAllRecordsFromReceiptID(String transmissionID)
     *
     * @param transmissionID gets transmission id to fetch all the records for that specific transmission to be mapped.
     **/
    public void getAllRecordsFromReceiptID(String transmissionID) throws Exception{

        List<IrsRecordDetails1095B> currentTransmissionRecordList = irsRecordDetails1095BRepository.getRecordsDetailsTransactionID(Integer.valueOf(transmissionID));
        for (IrsRecordDetails1095B item : currentTransmissionRecordList) {
            String recordPK = item.getId().getTransmissionId() + "|" + item.getId().getSubmissionId() + "|" + item.getId().getRecordId();
            errorReportingObjectStore.currentTransmissionRecordMap.put(recordPK, item);
        }
        if (currentTransmissionRecordList.size() == 0) {
            logger.warn("No record with given transmission id is found in IRS_RECORD_DETAILS_1095B table");
        }

    }
}
