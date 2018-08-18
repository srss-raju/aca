package us.deloitteinnovation.aca.batch.ingest.step3.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import us.deloitteinnovation.aca.batch.ingest.step3.Step3DataValidationException;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.text.MessageFormat;
import java.util.List;

import static us.deloitteinnovation.aca.constants.CommonDataConstants.FORMAT_PLACEHOLDER_STR;

/**
 * Created by tthakore on 10/27/2016.
 */
public class Step3CorrectedRecordsValidationUtils {

    private static Logger logger = LoggerFactory.getLogger(Step3CorrectedRecordsValidationUtils.class);
    @Autowired
    private Environment env;

    public void validateCorrectedRecord(Step3FilerDataDto record, Step3FilerDataDto parent) throws Step3DataValidationException {
        try {
            if (!validForSingleRecordCheck(record, parent))
                return;

            if (!validForDatabaseRecordCheck(record, parent))
                return;

            if (!validForFileRecordCheck(record, parent))
                return;

        } catch (Exception e) {
            logger.error("Error in record Step3CorrectedRecordsValidationUtils:validateCorrectedRecord performCrossRecordValidation", e);
            Step3DataValidationException step3DataValidationException = new Step3DataValidationException();
            step3DataValidationException.setParent(parent.getFilersWithSameIdsInFile());
            step3DataValidationException.setErrorMessage("Exception in processing record for given source unique id hence all the records with given source unique id will be skipped : " + record.getSourceUniqueId());
            throw step3DataValidationException;
        }


    }

    /**
     * check for validation if there is no record in the database and  file has no original record for corresponding
     * correction we have to reject the record.
     **/
    public Boolean validForSingleRecordCheck(Step3FilerDataDto record, Step3FilerDataDto parent) {
          /*If there is no record exists for current item in db or file it means it is a unique record scenario 1. it means no IID and UID matched with any db record.*/
        if (parent.getFilersWithSameIdsInDB().isEmpty()) {
            // check if there is only one record with "U" and there is no original in file.
            if (parent.getFilersWithSameIdsInFile().size() == 1 && parent.getFilersWithSameIdsInFile().get(0).getRowNumber().equals(record.getRowNumber())) {
                markRecordAsFailed(record, parent, "ERROR.ER3.1.2.1", record.getSourceUniqueId());
                return false;
            } else {
                Integer originalRecordIndex = getOriginalRecordIndex(record, parent);
                //In case original record is after current record for correction reject the record.
                if ((originalRecordIndex == -1) || (Integer.valueOf(record.getRowNumber()) < originalRecordIndex)) {
                    markRecordAsFailed(record, parent, "ERROR.ER3.1.2.1", record.getSourceUniqueId());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Function to retrieve information of original record index. takes parent record and current record as input parameters.
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

    /**
     * Function to retrive information of original record index. takes parent record and current record as input parameters.
     **/
    public Step3FilerDataDto getOriginalRecordDB(Step3FilerDataDto record, List<Step3FilerDataDto> dbList) {
        Step3FilerDataDto recordIndex = null;
        for (Step3FilerDataDto recordInFile : dbList) {
            if (record.getUIDValue().equals(recordInFile.getUIDValue())) {
                recordIndex = recordInFile;
            }
        }
        return (recordIndex != null) ? recordIndex : null;
    }

    /**
     * Function loops through records in db and check if it is valid record or not if it is not a valid recrd mark it as rejected.
     **/
    public Boolean validForDatabaseRecordCheck(Step3FilerDataDto record, Step3FilerDataDto parent) {
        /*IF there is any record exists  in the database for same UID we have to perform cross record validation on that record to verify we are getting same SSN, DOB and TIN or else reject the record*/

        // check for record in DB with same SSN/TIN but different RID and reject the record if same ssn exists for other record.
        if (!checkForSameSsnDifferentRIDDB(record, parent))
            return false;


        for (Step3FilerDataDto item : parent.getFilersWithSameIdsInDB()) {

            String itemUid = item.getUIDValue();
            String recordUid = record.getUIDValue();
            if (!processDBValidationRule(itemUid, recordUid, record, item, parent))
                return false;
        }
        return true;
    }

    /**
     * Function compares DB validation rule for current record and marks record as either accepted or rejected
     **/
    public Boolean processDBValidationRule(String itemUid,
                                           String recordUid, Step3FilerDataDto record, Step3FilerDataDto item, Step3FilerDataDto parent) {
        if (itemUid.equals(recordUid)) {
            if ("C".equals(record.getFilerStatus()) && !checkForCoveredPersonValidation(record, item, "DB")) {
                markRecordAsFailed(record, parent, "UPDATE.ER_CV5.1.7.1", record.getSourceUniqueId());
                return false;
            }
            if (!isValidForInActivationDBRrecord(record, parent)) {
                return false;
            }

            if (!isValidForActivationRuleCrecordforDB(record, parent)) {
                return false;
            }
            markRecordAsPassed(record, parent);
            return false;// if you get a record in DB for an correction and it is a valid record to update in db no need to check for file.
        }

        return true;
    }


    /**
     * Function compares DB records and check if there is any record with same ssn in DB with different RID.
     * Also if there is nothing in DB we have to make sure that there is no maching SSN wich is going to get update from file as well.
     **/
    public Boolean checkForSameSsnDifferentRIDDB(Step3FilerDataDto record, Step3FilerDataDto parent) {
        for (Step3FilerDataDto item : parent.getFilersWithSameIdsInDB()) {

            String itemUid = item.getUIDValue();
            String itemSid = item.getSIDValue();
            String itemTid = item.getTIDValue();
            String recordUid = record.getUIDValue();
            String recordSid = record.getSIDValue();
            String recordTid = record.getTIDValue();
            if (!itemUid.equals(recordUid)) {
                if ((itemSid.equals(recordSid) && !"NA".equals(recordSid)) || (itemTid.equals(recordTid) && !"NA".equals(recordTid))) {//Test to check same SSN different RID's and reject record DB record.
                    markRecordAsFailed(record, parent, "CORRECTION.ER_CV5.1.6.2", item.getSourceUniqueId(), true);
                    return false;
                }
            }
        }
        return checkForSameSsnDifferentRIDFILE(record, parent);
    }

    /**
     * Function compares DB records and check if there is any record with same ssn in DB with different RID.
     **/
    public Boolean checkForSameSsnDifferentRIDFILE(Step3FilerDataDto record, Step3FilerDataDto parent) {
        for (Step3FilerDataDto item : parent.getFilersWithSameIdsInFile()) {

            String itemUid = item.getUIDValue();
            String itemSid = item.getSIDValue();
            String itemTid = item.getTIDValue();
            String recordUid = record.getUIDValue();
            String recordSid = record.getSIDValue();
            String recordTid = record.getTIDValue();
            if (!itemUid.equals(recordUid)) {
                if ((itemSid.equals(recordSid) && !"NA".equals(recordSid)) || (itemTid.equals(recordTid) && !"NA".equals(recordTid))) {//Test to check same SSN different RID's and reject record DB record.
                    markRecordAsFailed(record, parent, "CORRECTION.ER_CV5.1.6.3", item.getSourceUniqueId(), true);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * validForFileRecordCheck()  Check if you are trying to send SSN/TIN that already present in the FILE and record is accepted
     **/
    public Boolean validForFileRecordCheck(Step3FilerDataDto record, Step3FilerDataDto parent) {
        Boolean isValidRecord = false;

        /*Check if you are trying to send SSN/TIN that already present in the FILE and record is accepted*/
        if (!checkForSameSsnDifferentRIDFILE(record, parent))
            return false;

         /*IF there are record in file with same  then loop through them and check for validation scenario 2 and 3 */
        for (Step3FilerDataDto item : parent.getFilersWithSameIdsInFile()) {
            String itemUid = item.getUIDValue();
            String recordUid = record.getUIDValue();
            if (!processFileValidationRules(itemUid, recordUid, item, record, parent))
                return false;

            //commenting validation of active inactive record in file. since we have to decide wether it is allowed as part of same file or not.
            if (!isValidForActivationFILERrecord(record, parent)) {
                return false;
            }

            // if there is no original record in file or db for update we have to reject the record.
            if ((itemUid.equals(recordUid)) && item.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_O)
                    && item.getRecordStatus().equals(CommonDataConstants.VALIDATION_RULE_PASSED) && (Integer.valueOf(record.getRowNumber()) > Integer.valueOf(item.getRowNumber()))) {
                isValidRecord = true;
            }
        }

        if (isValidRecord) {
            markRecordAsPassed(record, parent);
        } else {
            markRecordAsFailed(record, parent, "UPDATE.ER_CV5.1.4.1", record.getSourceUniqueId());
        }
        return true;
    }


    /**
     * processFileValidationRules will be checking validation rule for C record.
     **/
    protected Boolean processFileValidationRules(String itemUid,
                                                 String recordUid, Step3FilerDataDto item, Step3FilerDataDto record, Step3FilerDataDto parent) {
        if ((Integer.valueOf(record.getRowNumber()) > Integer.valueOf(item.getRowNumber()))
                && (itemUid.equals(recordUid)) && "C".equals(record.getFilerStatus())
                && !checkForCoveredPersonValidation(record, item, "FILE")) {
            markRecordAsFailed(record, parent, "UPDATE.ER_CV5.1.7.1", record.getSourceUniqueId());
            return false;
        }
        return true;
    }

    /**
     * checkForCoveredPersonValidation() function is created to validate covered person validation tule
     * since user is not allowed to change responsible person with C.
     *
     * @param record   :- record to process
     * @param item     :- to check against.
     * @param itemType :- DB vs File.
     **/
    protected Boolean checkForCoveredPersonValidation(Step3FilerDataDto record, Step3FilerDataDto item, String itemType) {
        Boolean recordValidForValidation = true;

        if (itemType == "DB" && !item.getResponsiblePersonUniqueId().equals(record.getResponsiblePersonUniqueId())) {
            recordValidForValidation = false;
        }

        if (itemType == "FILE" && !item.getResponsiblePersonUniqueId().equals(record.getResponsiblePersonUniqueId())
                && item.getRecordStatus() != null
                && item.getRecordStatus().equals(CommonDataConstants.VALIDATION_RULE_PASSED)) {// If item is not validation passed we can not compare it with current record comaprison has to be done with valid record.
            recordValidForValidation = false;
        }
        return recordValidForValidation;
    }


    /**
     * isValidForActivationDBRrecord() will check records in database and check wether record is valid for activation rule or not. Checks are saperate for file and DB
     * so they are handeled in different methods
     **/
    public Boolean isValidForInActivationDBRrecord(Step3FilerDataDto record, Step3FilerDataDto parent) {


        if ("R".equals(record.getFilerStatus()) && !record.getCoveredPersonList().isEmpty()
                && (CommonDataConstants.RECORD_INACTIVE_DATE.equals(record.getFilerCoverage().getOrigCoverageBeginDt().toString())
                && CommonDataConstants.RECORD_INACTIVE_DATE.equals(record.getFilerCoverage().getOrigCoverageEndDt().toString()))) {
            isInactivationForCoveredPersonAvailableInFile(record.getCoveredPersonList(), record);
            for (Step3FilerDataDto item : record.getCoveredPersonList()) {
                if ("ACTIVE".equals(item.getStatus())) {
                    // the reason we have this condition is to separate out logic for activation between file and DB so that if in future if you have different validation messages you can put it on correct place.
                    if (CommonDataConstants.RECORD_VALIDATION_TYPE_DB.equals(item.getRecordSource())) {
                        markRecordAsFailed(record, parent, "CORRECTION.ER_CV5.2.3.1", record.getSourceUniqueId());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * *  isValidForActivationFILERrecord() will check records in File and check wether record is valid for activation rule or not. Checks are saperate for file and DB
     * so they are handled in different methods
     **/
    public Boolean isValidForActivationFILERrecord(Step3FilerDataDto record, Step3FilerDataDto parent) {
        if ((CommonDataConstants.RECORD_INACTIVE_DATE.equals(record.getFilerCoverage().getOrigCoverageBeginDt().toString())
                && CommonDataConstants.RECORD_INACTIVE_DATE.equals(record.getFilerCoverage().getOrigCoverageEndDt().toString()))) {
            markRecordAsFailed(record, parent, "CORRECTION.ER_CV5.2.5.1", record.getSourceUniqueId());
            return false;
        }
        return true;
    }

    /**
     * Below function will take care of  Rule no 5.2.4 ICD  if records are there in db
     **/
    public Boolean isValidForActivationRuleCrecordforDB(Step3FilerDataDto record, Step3FilerDataDto parent) {
        Step3FilerDataDto recordInDb = getOriginalRecordDB(record, parent.getFilersWithSameIdsInDB());
        if ("C".equals(record.getFilerStatus()) && (recordInDb != null)
                && CommonDataConstants.RECORD_STATUS_INACTIVE.equals(recordInDb.getStatus())) {

            checkForRpersonActivationinFile(record, record.getResposiblePersonList());
            for (Step3FilerDataDto item : record.getResposiblePersonList()) {
                if (CommonDataConstants.RECORD_VALIDATION_TYPE_DB.equals(item.getRecordSource()) && CommonDataConstants.RECORD_STATUS_INACTIVE.equals(item.getStatus())
                        && !(CommonDataConstants.RECORD_INACTIVE_DATE.equals(record.getFilerCoverage().getOrigCoverageBeginDt().toString())
                        && !CommonDataConstants.RECORD_INACTIVE_DATE.equals(record.getFilerCoverage().getOrigCoverageEndDt().toString()))) {
                    markRecordAsFailed(record, parent, "CORRECTION.ER_CV5.2.4.1", item.getSourceUniqueId());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * markRecordAsPassed() is a generic function to mark available record as passed.
     **/
    protected void markRecordAsPassed(Step3FilerDataDto record, Step3FilerDataDto parent) {
        record.setRecordStatus(CommonDataConstants.VALIDATION_RULE_PASSED);
        record.setBDMessage(env.getProperty("CORRECTION.ACCEPTED"));
        /* Add item to Accepted records bucket*/
        parent.getAcceptedRecordList().add(record);
    }


    /**
     * *  markRecordAsFailed() is a generic function to mark all the record as failed which are passed to it. and adding the record to rejected record list
     *
     * @param record       : record which we are marking as failed
     * @param parent       : parent container of record to mark passed or failed.
     * @param validatiomsg : validation message property name to be dispplayed from property files.
     * @param uidToDisplay : source unique id we want to display in business decision log table
     **/
    public void markRecordAsFailed(Step3FilerDataDto record, Step3FilerDataDto parent, String validatiomsg, String uidToDisplay) {

        markRecordAsFailed(record, parent, validatiomsg, uidToDisplay, false);
    }

    private void markRecordAsFailed(Step3FilerDataDto record, Step3FilerDataDto parent, String validatiomsg, String uidToDisplay, boolean appendRid) {
        String message = env.getProperty(validatiomsg);
        StringBuilder bdMessage = new StringBuilder();
        if (message.contains(FORMAT_PLACEHOLDER_STR)) {
            bdMessage.append(String.format(message, uidToDisplay));
        } else {
            bdMessage.append(message);
        }
        logger.warn("Validation rule for Correction failed. " + validatiomsg);
        record.setRecordStatus(CommonDataConstants.VALIDATION_RULE_FAILED);
        if (appendRid) {
            bdMessage.append(String.valueOf(uidToDisplay));
        }
        record.setBDMessage(bdMessage.toString());
        /* Add item to rejected records bucket*/
        parent.getRejectedRecordList().add(record);
    }

    /**
     * isInactivationForCoveredPersonAvailableInFile() loops through every covered person record and check if
     * user is trying to make covered person inactive in the file if yes mark db record as inactive since if it is a valid record.
     * and it should be in the row prior to current row for R record
     *
     * @param coveredPersonList : list of covered person
     * @param record            : actual responsible person.
     **/
    public void isInactivationForCoveredPersonAvailableInFile(List<Step3FilerDataDto> coveredPersonList, Step3FilerDataDto record) {
        for (Step3FilerDataDto coveredPerson : coveredPersonList) {
            if ("FILE".equals(coveredPerson.getRecordSource())
                    && "C".equals(coveredPerson.getCorrection())
                    && Integer.parseInt(record.getRowNumber()) > Integer.parseInt(coveredPerson.getRowNumber())
                    && (CommonDataConstants.RECORD_INACTIVE_DATE.equals(coveredPerson.getFilerCoverage().getOrigCoverageBeginDt().toString())
                    && CommonDataConstants.RECORD_INACTIVE_DATE.equals(coveredPerson.getFilerCoverage().getOrigCoverageEndDt().toString()))) {
                runCorrectionRuleForRecord(coveredPersonList, coveredPerson);
            }
        }
    }

    /**
     * runCorrectionRuleForRecord() runs correction rule on the record to mark db record as inactive.
     * if it is a valid correction to mark record as inactive.
     *
     * @param coveredPersonList : list of covered person
     * @param record            : actual covered  person in file.
     **/
    public void runCorrectionRuleForRecord(List<Step3FilerDataDto> coveredPersonList, Step3FilerDataDto record) {
        for (Step3FilerDataDto coveredPerson : coveredPersonList) {
            if (coveredPerson.getUIDValue().equals(record.getUIDValue())
                    && "DB".equals(coveredPerson.getRecordSource())) {
                record.setStatus("INACTIVE");
                coveredPerson.setStatus("INACTIVE");
            }
        }
    }

    public void  checkForRpersonActivationinFile( Step3FilerDataDto record, List<Step3FilerDataDto> responsiblePersonList)
    {
        for (Step3FilerDataDto responsiblePerson : responsiblePersonList) {
            if ("FILE".equals(responsiblePerson.getRecordSource())
                    && "C".equals(responsiblePerson.getCorrection())
                    && Integer.parseInt(record.getRowNumber()) > Integer.parseInt(responsiblePerson.getRowNumber())
                    && !CommonDataConstants.RECORD_INACTIVE_DATE.equals(responsiblePerson.getFilerCoverage().getOrigCoverageBeginDt().toString())
                    && !CommonDataConstants.RECORD_INACTIVE_DATE.equals(responsiblePerson.getFilerCoverage().getOrigCoverageEndDt().toString())) {
                runCorrectionRuleForResponsiblePersonRecord(responsiblePersonList, responsiblePerson);
            }
        }
    }


    /**
     * runCorrectionRuleForResponsiblePersonRecord() runs correction rules for responsible person rule on the record to mark db record as active.
     * if it is a valid correction to mark record as active.
     *
     * @param coveredPersonList : list of covered person
     * @param record            : actual covered  person in file.
     **/
    public void runCorrectionRuleForResponsiblePersonRecord(List<Step3FilerDataDto> coveredPersonList, Step3FilerDataDto record) {
        for (Step3FilerDataDto responsiblePerson : coveredPersonList) {
            if (responsiblePerson.getUIDValue().equals(record.getUIDValue())
                    && "DB".equals(responsiblePerson.getRecordSource())
                     && "INACTIVE".equals(responsiblePerson.getStatus())) {
                record.setStatus("ACTIVE");
                responsiblePerson.setStatus("ACTIVE");
            }
        }
    }
}
