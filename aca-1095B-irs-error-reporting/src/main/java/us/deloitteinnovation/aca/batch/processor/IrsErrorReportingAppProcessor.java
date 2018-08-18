package us.deloitteinnovation.aca.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import us.deloitteinnovation.aca.batch.constants.IrsErrorReportingContants;
import us.deloitteinnovation.aca.batch.dto.ErrorDetailsDTO;
import us.deloitteinnovation.aca.batch.dto.TransmitterErrorDetailGrpDTO;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095B;
import us.gov.treasury.irs.ext.aca.air._7.TransmitterErrorDetailGrp;
import us.gov.treasury.irs.msg.form1094_1095bctransmittermessage.FormBCTransmitterSubmissionDtl;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by tthakore on 3/30/2016.
 */
public class IrsErrorReportingAppProcessor implements ItemProcessor<FormBCTransmitterSubmissionDtl, ErrorDetailsDTO> {


    private static Logger logger = LoggerFactory.getLogger(IrsErrorReportingAppProcessor.class);
    ErrorReportingObjectStore errorReportingObjectStore = ErrorReportingObjectStore.getInstance();

    /**
     * @param item FormBCTransmitterSubmissionDtl
     *             maps FormBCTransmitterSubmissionDtl to  ErrorDetailsDTO to be used by writer to write in db tables and report file.
     * @return ErrorDetailsDTO
     **/
    public ErrorDetailsDTO process(FormBCTransmitterSubmissionDtl item) throws Exception {
        logger.info("Inside processor function...............................");
        ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO();
        String receiptID = errorReportingObjectStore.getReceiptIdFromFileName(errorReportingObjectStore.getCurrentFileName());
        String transmissionID = errorReportingObjectStore.receiptTransmissionIDMap.get(receiptID).getTransmissionId().toString();
        Integer submissionErrorCount = 0;
        Integer recordErrorCount = 0;
        Integer acceptedRecordCount = 0;
        errorDetailsDTO.setTransmissionID(transmissionID);
        errorDetailsDTO.setReceiptID(receiptID);
        errorDetailsDTO.setTransmitterErrorDetailGrps(new ArrayList<TransmitterErrorDetailGrpDTO>());
        ArrayList<String> errorRecordsKeyList = new ArrayList<>();
        for (TransmitterErrorDetailGrp transmitterErrorDetail : item.getACATransmitterSubmissionDetail().getTransmitterErrorDetailGrps()) {
            String recordInfoKey = getRecordInfoKey(transmitterErrorDetail);
            TransmitterErrorDetailGrpDTO transmitterErrorDetailGrpDTO = new TransmitterErrorDetailGrpDTO();

            if (transmitterErrorDetail.getUniqueRecordId() != null ) {
                if(errorReportingObjectStore.currentTransmissionRecordMap.size() > 0)
                {
                    if (!validateKeyForRecordID(recordInfoKey)) {
                        logger.error("<-----Record not available in IRS_RECORD_DETAILS_1095B table for key {} Record will not marked as error/accepted in table.----->",recordInfoKey);
                        logger.error("<-------File Will not get processed in case of record not found hence skipping current file processing-------->");
                        errorReportingObjectStore.getSkippedFileNameList().add(errorReportingObjectStore.getCurrentFileName());
                        return  new ErrorDetailsDTO();
                    } else {
                        transmitterErrorDetailGrpDTO.setRecepientUID(errorReportingObjectStore.currentTransmissionRecordMap.get(recordInfoKey).getSourceUniqueId());
                        transmitterErrorDetailGrpDTO.setSourceCD(errorReportingObjectStore.currentTransmissionRecordMap.get(recordInfoKey).getSourceCode());
                        transmitterErrorDetailGrpDTO.setRecordStatus(errorReportingObjectStore.transmissionStatusesMap.get(IrsErrorReportingContants.ERROR).getId().getStatusCD());
                        transmitterErrorDetailGrpDTO.setTransmitterErrorDetailGrp(transmitterErrorDetail);
                        errorDetailsDTO.getTransmitterErrorDetailGrps().add(transmitterErrorDetailGrpDTO);
                        errorRecordsKeyList.add(recordInfoKey);
                        recordErrorCount += 1;
                    }
                }
                else
                {

                    errorReportingObjectStore.getSkippedFileNameList().add(errorReportingObjectStore.getCurrentFileName());
                    logger.error("<-------File Will not get processed in case of record not found so skipping current file processing. There are no records on IRS_RECORDS_DETAILS_1095B table with given transmission id. {} -------->",recordInfoKey);
                    return  new ErrorDetailsDTO();
                }
            } else if (transmitterErrorDetail.getUniqueSubmissionId() != null) {
                transmitterErrorDetailGrpDTO.setTransmitterErrorDetailGrp(transmitterErrorDetail);
                errorDetailsDTO.getTransmitterErrorDetailGrps().add(transmitterErrorDetailGrpDTO);
                if(transmitterErrorDetail.getSubmissionLevelStatusCd() != null)errorDetailsDTO.setSubmissionLvlStatusCd(transmitterErrorDetail.getSubmissionLevelStatusCd().value());
                if(transmitterErrorDetailGrpDTO.getTransmitterErrorDetailGrp() != null && transmitterErrorDetailGrpDTO.getTransmitterErrorDetailGrp().getErrorMessageDetails().size() > 0)submissionErrorCount += 1;
            }
        }
        /*remove all the error record from map to get other accepted records.*/
        for(String key :errorRecordsKeyList)
        {
            errorReportingObjectStore.currentTransmissionRecordMap.remove(key);
        }


        Iterator itr = errorReportingObjectStore.currentTransmissionRecordMap.values().iterator();
        acceptedRecordCount = errorReportingObjectStore.currentTransmissionRecordMap.values().size();
        while (itr.hasNext()) {
            IrsRecordDetails1095B irsRecordDetails1095B = (IrsRecordDetails1095B) itr.next();
            TransmitterErrorDetailGrpDTO transmitterErrorDetailGrpDTO = new TransmitterErrorDetailGrpDTO();
            transmitterErrorDetailGrpDTO.setRecepientUID(irsRecordDetails1095B.getSourceUniqueId());
            transmitterErrorDetailGrpDTO.setSourceCD(irsRecordDetails1095B.getSourceCode());
            transmitterErrorDetailGrpDTO.setSubmissionID(irsRecordDetails1095B.getId().getSubmissionId());
            transmitterErrorDetailGrpDTO.setRecordID(irsRecordDetails1095B.getId().getRecordId());
            //this has to come from DB it is temporarily hard coded
            transmitterErrorDetailGrpDTO.setRecordStatus(errorReportingObjectStore.transmissionStatusesMap.get(IrsErrorReportingContants.ACCEPTED).getId().getStatusCD());
            errorDetailsDTO.getAcceptedRecord().add(transmitterErrorDetailGrpDTO);
        }
        errorReportingObjectStore.currentTransmissionRecordMap.clear();
        errorReportingObjectStore.setAcceptedRecordCount(acceptedRecordCount);
        errorReportingObjectStore.setSubmissionErrorCount(submissionErrorCount);
        errorReportingObjectStore.setRecordErrorCount(recordErrorCount);
        logger.info("End of processor method...............................");
        return errorDetailsDTO;
    }

    public String getRecordInfoKey(TransmitterErrorDetailGrp transmitterErrorDetail) {
        String recordInfoKey = "";
        if (transmitterErrorDetail.getUniqueSubmissionId() != null) {
            recordInfoKey = errorReportingObjectStore.getCurrentTransmissionID() + "|" + transmitterErrorDetail.getUniqueSubmissionId().split("\\|")[1];
        } else if (transmitterErrorDetail.getUniqueRecordId() != null) {
            recordInfoKey = errorReportingObjectStore.getCurrentTransmissionID() + "|" + transmitterErrorDetail.getUniqueRecordId().split("\\|")[1] + "|" + transmitterErrorDetail.getUniqueRecordId().split("\\|")[2];
        }
        return recordInfoKey;
    }

    Boolean validateKeyForRecordID(String key) {
        return (errorReportingObjectStore.currentTransmissionRecordMap.get(key) != null);
    }


}
