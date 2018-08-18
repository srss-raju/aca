package us.deloitteinnovation.aca.batch.ingest.step3.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3RecordsUIDValidationResultMap;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3ValidationMapDto;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

/**
 * Created by tthakore on 9/14/2016.
 */
public class Step3OriginalRecordsValidationUtils {

    private static Logger logger = LoggerFactory.getLogger(Step3OriginalRecordsValidationUtils.class);
    @Autowired
    Step3RecordsUIDValidationResultMap step3RecordsUIDValidationResultMap;
    @Autowired
    private Environment env;

    public void validateOriginalRecord(Step3FilerDataDto record, Step3FilerDataDto parent) throws Exception{
        try{
            if(record.getFilerStatus().equals("C"))
            {
               if(!checkForCoveredPersonValidation(record))
               {
                   markRecordAsFailed(record,record, parent, "ORIGINAL.ER_CV5.1.3.1",false, Long.valueOf(record.getResponsiblePersonUniqueId()));
                   return;
               }
            }

             /*If there is no record exists for current item in db or file it means it is a unique record scenario 1. it means no IID and UID matched with any db record.*/
            if (parent.getFilersWithSameIdsInDB().size() == 0 && parent.getFilersWithSameIdsInFile().size() == 1) {
                if (parent.getFilersWithSameIdsInFile().get(0).getRowNumber().equals(record.getRowNumber())) {
                    markRecordAsPassed(record, parent);
                    return;
                }
            }

        /*IF there is any record exists in DB for same IID or UID it means we have to reject the record with valid reason. below code does check for that.
        * scenario 5.1.1 & 5.1.2*/
            for (Step3FilerDataDto item : parent.getFilersWithSameIdsInDB()) {
                String item_sid = item.getSIDValue();
                String item_tid = item.getTIDValue();
                String item_uid = item.getUIDValue();
                String record_sid = record.getSIDValue();
                String record_tid = record.getTIDValue();
                String record_uid = record.getUIDValue();

                if((item_uid.equals(record_uid)))
                {
                    markRecordAsFailed(record, item, parent, "ORIGINAL.ER_CV5.1.2.2", false, 0, true);
                    return;
                }
                else if (!item_sid.equals("NA") && (!item_uid.equals(record_uid)) &&  (item_sid.equals(record_sid)) ) {//Test to check same SSN different RID's and reject record DB record.
                    markRecordAsFailed(record, item, parent, "ORIGINAL.ER_CV5.1.1.2", false, 0, true);
                    return;
                }
                else if (!item_tid.equals("NA") && (!item_uid.equals(record_uid)) &&  (item_tid.equals(record_tid)) ) {//Test to check same TIN different RID's and reject record DB record
                    markRecordAsFailed(record, item, parent, "ORIGINAL.ER3.1.4", false, 0, true);
                    return;
                }
            }
        /*IF there are record in file with same ids then loop through them and check for validation scenario 2 and 3. There will be "O","U" or "C"
         * records in below list so we have to make sure that if we gets single U then also we mark it as eligible for insert.*/

            for (Step3FilerDataDto item : parent.getFilersWithSameIdsInFile()) {
                String item_sid = item.getSIDValue();
                String item_tid = item.getTIDValue();
                String item_uid = item.getUIDValue();
                String record_sid = record.getSIDValue();
                String record_tid = record.getTIDValue();
                String record_uid = record.getUIDValue();


                if(!item.getRowNumber().equals(record.getRowNumber()) && item.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_O))// check item is not the same item
                {

                    if((item_uid.equals(record_uid)))
                    {
                        markRecordAsFailed(record, item, parent, "ORIGINAL.ER_CV5.1.2.1", false, 0, true);
                        return;
                    }
                    else if (!item_sid.equals("NA") && (item_sid.equals(record_sid)) && (!item_uid.equals(record_uid))) {//Test to check same SSN different RID's and reject record same file.
                        markRecordAsFailed(record, item, parent, "ORIGINAL.ER_CV5.1.1.1", true, 0, true);
                        return;
                    }  else if (!item_tid.equals("NA") && (item_tid.equals(record_tid)) && (!item_uid.equals(record_uid))) {//Test to check same TIN different RID's and reject record same file.
                        markRecordAsFailed(record, item, parent, "ORIGINAL.ER_CV5.1.1.1", true, 0, true);
                        return;
                    }
                }
            }

            markRecordAsPassed(record,parent);
        }catch (Exception e)
        {
            logger.error("Error in processor for CRV step3 record source_unique_id is "+record.getSourceUniqueId()+" and row number is "+record.getRowNumber());
            throw  e;
        }

    }

    protected Boolean checkForCoveredPersonValidation(Step3FilerDataDto record)
    {
        Boolean recordValidForValidation = true;
        if(record.getResposiblePersonList().size() > 0)
        {
            for(Step3FilerDataDto item : record.getResposiblePersonList())
            {
                if(item.getRecordSource().equals("DB"))
                {
                    recordValidForValidation = true;
                    break;
                }
                else
                {
                    Step3ValidationMapDto step3ValidationMapDto = step3RecordsUIDValidationResultMap.getMapDtoMap().get(item.getUIDValue());
                    recordValidForValidation = (step3ValidationMapDto.getValidationStatus().equals("PASSED"))?true:false;
                }
            }
        }
        else
        {
            recordValidForValidation = false;
        }

        return recordValidForValidation;
    }

    public void markRecordAsFailed(Step3FilerDataDto record, Step3FilerDataDto item, Step3FilerDataDto parent, String validatiomsg, Boolean isDisplayMultipleRID, long uidToDisplay) {

        markRecordAsFailed(record, item, parent, validatiomsg, isDisplayMultipleRID, uidToDisplay, false);
    }

    protected void markRecordAsPassed(Step3FilerDataDto record, Step3FilerDataDto parent) {
        record.setRecordStatus(CommonDataConstants.VALIDATION_RULE_PASSED);
        record.setBDMessage(env.getProperty("ORIGINAL.ACCEPTED"));

                        /* Add item to Accepted records bucket*/
        parent.getAcceptedRecordList().add(record);
    }

    private void markRecordAsFailed(Step3FilerDataDto record, Step3FilerDataDto item, Step3FilerDataDto parent, String validatiomsg, Boolean isDisplayMultipleRID, long uidToDisplay, boolean appendRid) {
        record.setRecordStatus(CommonDataConstants.VALIDATION_RULE_FAILED);
        StringBuffer bdMessage = new StringBuffer();
        bdMessage.append(env.getProperty(validatiomsg));
        if (isDisplayMultipleRID)
            bdMessage.append(" " + String.valueOf(item.getSourceUniqueId()) + "," + String.valueOf(record.getSourceUniqueId()));
        else {
            if (appendRid) {
                if (uidToDisplay == 0) {
                    bdMessage.append(" " + String.valueOf(item.getSourceUniqueId()));
                } else {
                    bdMessage.append(" " + String.valueOf(uidToDisplay));
                }
            }
        }
        record.setBDMessage(bdMessage.toString());
        /* Add item to rejected records bucket*/
        parent.getRejectedRecordList().add(record);
    }
}
