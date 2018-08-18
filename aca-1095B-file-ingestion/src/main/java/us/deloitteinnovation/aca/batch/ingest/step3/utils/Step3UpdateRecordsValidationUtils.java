package us.deloitteinnovation.aca.batch.ingest.step3.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import us.deloitteinnovation.aca.batch.ingest.step3.Step3DataValidationException;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import static us.deloitteinnovation.aca.constants.CommonDataConstants.FORMAT_PLACEHOLDER_STR;

/**
 * Created by tthakore on 10/10/2016.
 * <p/>
 * this class will be responsible for validation of update records for file ingestion
 */
public class Step3UpdateRecordsValidationUtils {

    private static Logger logger = LoggerFactory.getLogger(Step3UpdateRecordsValidationUtils.class);

    @Autowired
    private Environment env;


    public void validateUpdateRecord(Step3FilerDataDto record, Step3FilerDataDto parent) throws Step3DataValidationException {

        try {

            if (!validForSingleRecordCheck(record, parent))
                return;

            if (!validForDatabaseRecordCheck(record, parent))
                return;

            if (!validForFileRecordCheck(record, parent))
                return;

        } catch (Exception e) {
            logger.error("Error in record Step3UpdateRecordsValidationUtils:validateCorrectedRecord performCrossRecordValidation", e);
            Step3DataValidationException step3DataValidationException = new Step3DataValidationException();
            step3DataValidationException.setParent(parent.getFilersWithSameIdsInFile());
            step3DataValidationException.setErrorMessage("Exception in processing record for given source unique id hence all the records with given source unique id will be skipped : " + record.getSourceUniqueId());
            throw step3DataValidationException;
        }
    }


    /**
     * check for validation if there is no record in the database and  file has no original record we have to reject the record.
     **/
    public Boolean validForSingleRecordCheck(Step3FilerDataDto record, Step3FilerDataDto parent) {
          /*If there is no record exists for current item in db or file it means it is a unique record scenario 1. it means no IID and UID matched with any db record.*/
        if (parent.getFilersWithSameIdsInDB().isEmpty()) {
            // check if there is only one record with "U" and there is no original in file.
            if (parent.getFilersWithSameIdsInFile().size() == 1) {
                if (parent.getFilersWithSameIdsInFile().get(0).getRowNumber().equals(record.getRowNumber())) {
                    markRecordAsFailed(record, parent, "ERROR.ER3.1.2.1", record.getSourceUniqueId());
                    return false;
                }
            } else {
                Integer originalRecordIndex = getOriginalRecordIndex(record, parent);
                //In case original record is after current record for update reject the record.
                if ((originalRecordIndex == -1) || (Integer.valueOf(record.getRowNumber()) < originalRecordIndex)) {
                    markRecordAsFailed(record, parent, "ERROR.ER3.1.2.1", record.getSourceUniqueId());
                    return false;
                }
            }
        }
        return true;
    }


    protected Boolean validForDatabaseRecordCheck(Step3FilerDataDto record, Step3FilerDataDto parent) {
        /*IF there is any record exists  in the database for same UID we have to perform cross record validation on that record to verify we are getting same SSN, DOB and TIN or else reject the record*/
        for (Step3FilerDataDto item : parent.getFilersWithSameIdsInDB()) {

            /* If there is any correction record in the file prior to this record and is passed */
            /* that record will override the record in DB. */
            for (int idx=0; idx<parent.getFilersWithSameIdsInFile().size(); idx++) {
                Step3FilerDataDto toScan = parent.getFilersWithSameIdsInFile().get(idx);
                if ("C".equalsIgnoreCase(toScan.getOriginalCorrectionCode()) && "PASSED".equalsIgnoreCase(toScan.getRecordStatus())) {
                    item = toScan;
                }
            }

            String itemSid = item.getSIDValue();
            String itemTid = item.getTIDValue();
            String itemUid = item.getUIDValue();
            String recordSid = record.getSIDValue();
            String recordTid = record.getTIDValue();
            String recordUid = record.getUIDValue();

            if(!proccessDBValidationRule(itemSid, itemTid, itemUid, recordSid, recordTid, recordUid, record, item, parent))
                return false;
        }
        return true;
    }

    protected Boolean validForFileRecordCheck(Step3FilerDataDto record, Step3FilerDataDto parent) {
        Boolean isValidRecord = false;

         /*IF there are record in file with same  then loop through them and check for validation scenario 2 and 3 */
        for (Step3FilerDataDto item : parent.getFilersWithSameIdsInFile()) {

            // if there is no original record in file or db for update we have to reject the record.
            if ((item.getUIDValue().equals(record.getUIDValue())) && item.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_O)
                    && item.getRecordStatus().equals(CommonDataConstants.VALIDATION_RULE_PASSED) && (Integer.valueOf(record.getRowNumber()) > Integer.valueOf(item.getRowNumber()))){
                isValidRecord = true;
            }

            /* If there is any correction record in the file prior to this record and is passed */
            /* that record will override the record in DB. */
            for (int idx=0; idx<parent.getFilersWithSameIdsInFile().size(); idx++) {
                Step3FilerDataDto toScan = parent.getFilersWithSameIdsInFile().get(idx);
                if ("C".equalsIgnoreCase(toScan.getOriginalCorrectionCode()) && "PASSED".equalsIgnoreCase(toScan.getRecordStatus())) {
                    item = toScan;
                }
            }

            String itemSid = item.getSIDValue();
            String itemTid = item.getTIDValue();
            String itemUid = item.getUIDValue();
            String recordSid = record.getSIDValue();
            String recordTid = record.getTIDValue();
            String recordUid = record.getUIDValue();
            if(!processFileValidationRules(itemSid,itemTid,itemUid,recordSid,recordTid,recordUid,item,record,parent))
                return false;

        }

        if (isValidRecord) {
            markRecordAsPassed(record, parent);
        } else {
            markRecordAsFailed(record, parent, "UPDATE.ER_CV5.1.4.1", record.getSourceUniqueId());
        }
        return true;
    }

    public void markRecordAsFailed(Step3FilerDataDto record, Step3FilerDataDto parent, String validatiomsg, String uidToDisplay) {
        markRecordAsFailed(record, parent, validatiomsg, uidToDisplay, false);
    }

    private void markRecordAsFailed(Step3FilerDataDto record, Step3FilerDataDto parent, String validatiomsg, String uidToDisplay, boolean appendRid) {
        String message = env.getProperty(validatiomsg);
        StringBuilder bdMessage = new StringBuilder();
        if (null != message && message.contains(FORMAT_PLACEHOLDER_STR)) {
            bdMessage.append(String.format(message, uidToDisplay));
        } else {
            bdMessage.append(message);
        }
        logger.warn("Validation rule for Update failed. " + validatiomsg);
        record.setRecordStatus(CommonDataConstants.VALIDATION_RULE_FAILED);
        if (appendRid) {
            bdMessage.append(String.valueOf(uidToDisplay));
        }
        record.setBDMessage(bdMessage.toString());
        /* Add item to rejected records bucket*/
        parent.getRejectedRecordList().add(record);
    }

    /**
     * Function to retrive information of original record index. takes parent record and current record as input parameter
     **/
    public int getOriginalRecordIndex(Step3FilerDataDto record, Step3FilerDataDto parent) {
        int recordIndex = -1;
        for (Step3FilerDataDto recordInFile : parent.getFilersWithSameIdsInFile()) {
            if (Integer.valueOf(record.getRowNumber()) > Integer.valueOf(recordInFile.getRowNumber()) && recordInFile.getSourceUniqueId().equals(record.getSourceUniqueId())
                    && recordInFile.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_O)
                    && recordInFile.getRecordStatus().equals(CommonDataConstants.VALIDATION_RULE_PASSED)) {
                recordIndex = Integer.valueOf(recordInFile.getRowNumber());
            }
        }
        return recordIndex;
    }

    protected Boolean proccessDBValidationRule(String itemSid, String itemTid, String itemUid, String recordSid, String recordTid,
                                               String recordUid, Step3FilerDataDto record, Step3FilerDataDto item, Step3FilerDataDto parent) {
        if (itemUid.equals(recordUid)) {
            if (!(itemSid.equals(recordSid)) || !(itemTid.equals(recordTid)) || !(item.getRecipientDob().equals(record.getRecipientDob()))) {//Test to check same SSN different RID's and reject record DB record.
                markRecordAsFailed(record, parent, "UPDATE.ER_CV5.1.5.1", record.getSourceUniqueId());
                return false;
            }
            if ("C".equals(record.getFilerStatus()) && !checkForCoveredPersonValidation(record, item, "DB")) {
                markRecordAsFailed(record, parent, "UPDATE.ER_CV5.1.7.1", record.getSourceUniqueId());
                return false;
            }
            markRecordAsPassed(record, parent);
            return false;// if you get a record in DB for an update and it is a valid record to update in db no need to check against file.
        }
        return true;
    }

    protected Boolean processFileValidationRules(String itemSid,String itemTid, String itemUid, String recordSid,
                                                    String recordTid,String recordUid,Step3FilerDataDto item,Step3FilerDataDto record,Step3FilerDataDto parent)
    {
        if ((Integer.valueOf(record.getRowNumber()) > Integer.valueOf(item.getRowNumber()))
                && (itemUid.equals(recordUid)))// check item is not the same item
        {
            if (!(itemSid.equals(recordSid))
                    || !(itemTid.equals(recordTid))
                        || !(item.getRecipientDob().equals(record.getRecipientDob()))) {//Test to check same SSN different RID's and reject record same file.
                markRecordAsFailed(record, parent, "UPDATE.ER_CV5.1.5.1", record.getSourceUniqueId());
                return false;
            }
            if ("C".equals(record.getFilerStatus())
                    && !checkForCoveredPersonValidation(record, item, "FILE")) {
                markRecordAsFailed(record, parent, "UPDATE.ER_CV5.1.7.1", record.getSourceUniqueId());
                return false;
            }
        }
        return true;
    }

    protected void markRecordAsPassed(Step3FilerDataDto record, Step3FilerDataDto parent) {
        record.setRecordStatus(CommonDataConstants.VALIDATION_RULE_PASSED);
        record.setBDMessage(env.getProperty("UPDATE.ACCEPTED"));

        /* Add item to Accepted records bucket*/
        parent.getAcceptedRecordList().add(record);


    }

    protected Boolean checkForCoveredPersonValidation(Step3FilerDataDto record, Step3FilerDataDto item, String itemType) {
        Boolean recordValidForValidation = true;

        if (itemType == "DB" && !item.getResponsiblePersonUniqueId().equals(record.getResponsiblePersonUniqueId())) {
            recordValidForValidation = false;
        }

        if (itemType == "FILE" && !item.getResponsiblePersonUniqueId().equals(record.getResponsiblePersonUniqueId())
                    &&  item.getRecordStatus() != null
                            && item.getRecordStatus().equals(CommonDataConstants.VALIDATION_RULE_PASSED)) {// If item is not validation passed we can not compare it with current record comaprison has to be done with valid record.
            recordValidForValidation = false;
        }
        return recordValidForValidation;
    }
}
