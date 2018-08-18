package us.deloitteinnovation.aca.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.batch.constants.IrsErrorReportingContants;
import us.deloitteinnovation.aca.batch.dto.ErrorDetailsDTO;
import us.deloitteinnovation.aca.batch.dto.TransmitterErrorDetailGrpDTO;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;
import us.deloitteinnovation.aca.entity.IrsTransmissionErrors;
import us.deloitteinnovation.aca.repository.*;
import us.gov.treasury.irs.common.ErrorMessageDetail;

import java.util.Date;


/**
 * Created by tthakore on 4/8/2016.
 */
@Transactional(propagation = Propagation.REQUIRED)
public class IrsErrorReportingService {
    private ErrorReportingObjectStore errorReportingObjectStore = ErrorReportingObjectStore.getInstance();
    private static Logger logger = LoggerFactory.getLogger(IrsErrorReportingService.class);

    @Autowired
    IrsRecordDetails1095BRepository irsRecordDetails1095BRepository;

    @Autowired
    IrsTransmissionErrorsRepository irsTransmissionErrorsRepository;

    @Autowired
    IrsSubmissionDetailsRepository irsSubmissionDetailsRepository;

    @Autowired
    FilerDemographicIrsErrorRepository filerDemographicRepository;

    @Autowired
    Irs1095XMLRepository irs1095XML;


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDBStatus(ErrorDetailsDTO item) {
        if (logger.isDebugEnabled()) {
            logger.debug("Start of generateReportFile..... ");
        }
        Integer transmissionID = errorReportingObjectStore.getCurrentTransmissionID();
        for (TransmitterErrorDetailGrpDTO errorObject : item.getTransmitterErrorDetailGrps()) {
            Integer submissionID = (errorObject.getTransmitterErrorDetailGrp().getUniqueSubmissionId() != null) ? Integer.valueOf(errorObject.getTransmitterErrorDetailGrp().getUniqueSubmissionId().split("\\|")[1]) : Integer.valueOf(errorObject.getTransmitterErrorDetailGrp().getUniqueRecordId().split("\\|")[1]);
            Integer recordID = (errorObject.getTransmitterErrorDetailGrp().getUniqueSubmissionId() != null) ? 0 : Integer.valueOf(errorObject.getTransmitterErrorDetailGrp().getUniqueRecordId().split("\\|")[2]);
            String errorID = (errorObject.getTransmitterErrorDetailGrp().getUniqueSubmissionId() != null) ? errorObject.getTransmitterErrorDetailGrp().getUniqueSubmissionId() : errorObject.getTransmitterErrorDetailGrp().getUniqueRecordId();
            Long sourceUID = errorObject.getRecepientUID();
            String sourceCD = errorObject.getSourceCD();
            if (errorObject.getTransmitterErrorDetailGrp().getErrorMessageDetails() != null && errorObject.getTransmitterErrorDetailGrp().getErrorMessageDetails().size() > 0) {
                for (ErrorMessageDetail obj : errorObject.getTransmitterErrorDetailGrp().getErrorMessageDetails()) {
                    String eMsgCode = obj.getErrorMessageCd();
                    String eMsgText = obj.getErrorMessageTxt();
                    String xPath = (obj.getXpathContent() != null) ? obj.getXpathContent() : "";
                    // inserting irs  error record into error details table.item
                    IrsTransmissionErrors irsTransmissionErrors = new IrsTransmissionErrors();
                    irsTransmissionErrors.setErrorElementName(xPath);
                    irsTransmissionErrors.setErrorId(errorID);
                    irsTransmissionErrors.setErrorMsgCode(eMsgCode);
                    irsTransmissionErrors.setErrorMsgText(eMsgText);
                    irsTransmissionErrors.setReceiptId(errorReportingObjectStore.getReceiptIdFromFileName(errorReportingObjectStore.getCurrentFileName()));
                    irsTransmissionErrors.setRecordId(recordID);
                    irsTransmissionErrors.setSubmissionId(submissionID);
                    irsTransmissionErrors.setTransmissionId(transmissionID);
                    irsTransmissionErrors.setUpdatedDate(new Date(System.currentTimeMillis()));
                    irsTransmissionErrors.setUpdatedBy(IrsErrorReportingContants.IRS_ERROR_MANAGEMENT_SYSTEM);
                    logger.info("inserting records into  irsTransmissionErrors ..... ");
                    irsTransmissionErrorsRepository.save(irsTransmissionErrors);
                    logger.info("updating error record status ..... " + errorObject.getRecepientUID());
                    if (errorObject.getTransmitterErrorDetailGrp().getSubmissionLevelStatusCd() != null) {

                        String submissionLvlStatusCode = getSubmissionLvlStatusCode(errorObject.getTransmitterErrorDetailGrp().getSubmissionLevelStatusCd().value());
                        logger.info("updating  records into  irsSubmissionDetails ..... ");
                        // if submissionLvlStatusCode  then we have submission status to update into irs submission details table

                        irsSubmissionDetailsRepository.updateSubmissionStatus(transmissionID, submissionID, submissionLvlStatusCode, new Date(System.currentTimeMillis()), IrsErrorReportingContants.IRS_ERROR_MANAGEMENT_SYSTEM);
                    }
                    //update filer demographics with IRS_TRANSMISSION_STATUS_CD as error.
                    if (recordID != 0) {
                        // update error record status in database for irs_transmission_record table this status should come from cache so that we can use db mappings in future .
                        logger.info("updating error records into  irsRecordDetails1095B ..... ");
                        irsRecordDetails1095BRepository.updateStatus(transmissionID, submissionID, recordID, IrsErrorReportingContants.ERROR, new Date(System.currentTimeMillis()), IrsErrorReportingContants.IRS_ERROR_MANAGEMENT_SYSTEM);
                        logger.info("updating error records into   filerDemographic ..... ");
                        filerDemographicRepository.updateIrsStatusCD(sourceCD, sourceUID, errorReportingObjectStore.transmissionStatusesMap.get(IrsErrorReportingContants.ERROR).getId().getStatusCD(), new Date(System.currentTimeMillis()), IrsErrorReportingContants.IRS_ERROR_MANAGEMENT_SYSTEM, IrsErrorReportingContants.CORRECTED);
                        logger.info("updating error status to irs xml table");
                        irs1095XML.updateCorrectionStatus(sourceUID, sourceCD, IrsErrorReportingContants.CORRECTION, new Date(System.currentTimeMillis()), IrsErrorReportingContants.IRS_ERROR_MANAGEMENT_SYSTEM);
                    }
                }
            } else if (errorObject.getTransmitterErrorDetailGrp().getErrorMessageDetails() != null) {
                // if this condition is true it means we have a record with no error message details but it might have submission status so we have to update this into database as discussed with tejal and pushpanjali(QA)
                if (errorObject.getTransmitterErrorDetailGrp().getSubmissionLevelStatusCd() != null) {
                    String submissionLvlStatusCode = getSubmissionLvlStatusCode(errorObject.getTransmitterErrorDetailGrp().getSubmissionLevelStatusCd().value());
                    logger.info("updating  records into  irsSubmissionDetails ..... ");
                    // if submissionLvlStatusCode  then we have submission status to update into irs submission details table
                    irsSubmissionDetailsRepository.updateSubmissionStatus(transmissionID, submissionID, submissionLvlStatusCode, new Date(System.currentTimeMillis()), IrsErrorReportingContants.IRS_ERROR_MANAGEMENT_SYSTEM);
                }
            }
        }


        for (TransmitterErrorDetailGrpDTO acceptedObject : item.getAcceptedRecord()) {

            logger.info("updating accepted record into filerDemographicRepository status .....{} ", acceptedObject.getRecepientUID());
            filerDemographicRepository.updateIrsStatusCD(acceptedObject.getSourceCD(), acceptedObject.getRecepientUID(), errorReportingObjectStore.transmissionStatusesMap.get(IrsErrorReportingContants.ACCEPTED).getId().getStatusCD(), new Date(System.currentTimeMillis()), IrsErrorReportingContants.IRS_ERROR_MANAGEMENT_SYSTEM,IrsErrorReportingContants.CORRECTED);
            logger.info("updating success records into    irsRecordDetails1095B ..... {}", acceptedObject.getRecepientUID());
            irsRecordDetails1095BRepository.updateStatus(transmissionID, acceptedObject.getSubmissionID(), acceptedObject.getRecordID(), acceptedObject.getRecordStatus(), new Date(System.currentTimeMillis()), IrsErrorReportingContants.IRS_ERROR_MANAGEMENT_SYSTEM);

        }
    }

    /**
     *
     *
     *
     *  **/
    public String getSubmissionLvlStatusCode(String statusDesc) {
        String status = "";
        switch (statusDesc) {
            case IrsErrorReportingContants.ACCEPTED_STATUS:
                status = errorReportingObjectStore.transmissionStatusesMap.get(IrsErrorReportingContants.ACCEPTED).getId().getStatusCD();
                break;
            case IrsErrorReportingContants.ACCEPTED_WITH_ERRORS_STATUS:
                status = errorReportingObjectStore.transmissionStatusesMap.get(IrsErrorReportingContants.ACCEPTED_WITH_ERRORS).getId().getStatusCD();
                break;
            case IrsErrorReportingContants.REJECTED_STATUS:
                status = errorReportingObjectStore.transmissionStatusesMap.get(IrsErrorReportingContants.REJECTED).getId().getStatusCD();
                break;
        }

        return status;
    }
}
