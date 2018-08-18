package us.deloitteinnovation.aca.batch.ingest.step3;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3RecordsUIDValidationResultMap;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3ValidationMapDto;
import us.deloitteinnovation.aca.batch.ingest.step3.utils.Step3CorrectedRecordsValidationUtils;
import us.deloitteinnovation.aca.batch.ingest.step3.utils.Step3OriginalRecordsValidationUtils;
import us.deloitteinnovation.aca.batch.ingest.step3.utils.Step3UpdateRecordsValidationUtils;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.util.List;

/**
 * <p/>
 * Step3FileIngestionCRVProcessor is created to process each record one by one and implement validation rule according to correction code
 * "O","U","C" once validation rules are implemented. perfor business validation rule gets called. which creates final record to update in filer
 * demographics.
 * <li>
 * Step1 :- Get the container object which is from intial unique uid list to the processor
 * Step2 :- Check for the correction code and implement respective validation implementation logic
 * Step3 :- Once validation rule implemented get list of accepted records and perform usiness balidation that is merge or replace records to update in filer demograpics.
 * Step4 :- Perform merging of record or replacement of record
 * Step5  :-pass record to writer to write it to DB.
 * </li>
 *
 * @see Step3FileIngestionCRVReader
 * @see Step3FileIngestionCRVWriter
 * @see Step3OriginalRecordsValidationUtils
 * @see Step3CorrectedRecordsValidationUtils
 * @see Step3UpdateRecordsValidationUtils
 * </p>
 */
@Component
public class Step3FileIngestionCRVProcessor implements ItemProcessor<Step3FilerDataDto, Step3FilerDataDto> {


    private static Logger logger = LoggerFactory.getLogger(Step3FileIngestionCRVProcessor.class);


    @Autowired
    Step3RecordsUIDValidationResultMap step3RecordsUIDValidationResultMap;

    @Autowired
    private Step3OriginalRecordsValidationUtils step3OriginalRecordsValidationUtils;

    @Autowired
    private Step3UpdateRecordsValidationUtils step3UpdateRecordsValidationUtils;


    @Autowired
    private Step3CorrectedRecordsValidationUtils step3CorrectedRecordsValidationUtils;

    @Autowired
    private Environment env;

    @Override
    public Step3FilerDataDto process(Step3FilerDataDto item) throws Exception {


        logger.info("start of Step3FileIngestionCRVProcessor process function.....");
        try {
            for (Step3FilerDataDto step3FilerDataDto : item.getFilersWithSameIdsInFile()) {
                performCrossRecordValidation(item, step3FilerDataDto);
            }
            if (!item.getAcceptedRecordList().isEmpty()) {
                Step3ValidationMapDto step3ValidationMapDto = step3RecordsUIDValidationResultMap.getMapDtoMap().get(item.getUIDValue());
                step3ValidationMapDto.setValidationStatus(CommonDataConstants.VALIDATION_RULE_PASSED);
                step3RecordsUIDValidationResultMap.getMapDtoMap().put(item.getUIDValue(), step3ValidationMapDto);
            } else {
                Step3ValidationMapDto step3ValidationMapDto = step3RecordsUIDValidationResultMap.getMapDtoMap().get(item.getUIDValue());
                step3ValidationMapDto.setValidationStatus(CommonDataConstants.VALIDATION_RULE_FAILED);
                step3RecordsUIDValidationResultMap.getMapDtoMap().put(item.getUIDValue(), step3ValidationMapDto);
            }
            performBusinessOperationsOnAcceptedRecord(item);
        } catch (Exception e) {
            logger.error("Error in record processor ", e);
            Step3DataValidationException step3DataValidationException = new Step3DataValidationException();
            step3DataValidationException.setParent(item.getFilersWithSameIdsInFile());
            step3DataValidationException.setErrorMessage("Exception in processing record for given source unique id hence all the records with given source unique id will be skipped : " + item.getSourceUniqueId());
            throw step3DataValidationException;
        }
        logger.info("end of Step3FileIngestionCRVProcessor process function.....");
        return item;
    }

    //to perform cross record validation
    public void performCrossRecordValidation(Step3FilerDataDto item, Step3FilerDataDto step3FilerDataDto) throws Step3DataValidationException {
        try {
            if (step3FilerDataDto.getSourceCd().equals(item.getSourceCd()) && step3FilerDataDto.getSourceUniqueId().equals(item.getSourceUniqueId()))// process record only for same source cd and source unique id.
            {
                if (step3FilerDataDto.getCorrection() == null) {
                    step3FilerDataDto.setRecordStatus(CommonDataConstants.VALIDATION_RULE_FAILED);
                    step3FilerDataDto.setBDMessage("Invalid correction code for record" + String.valueOf(step3FilerDataDto.getSourceUniqueId()));
                /* Add item to rejected records bucket*/
                    item.getRejectedRecordList().add(step3FilerDataDto);
                } else if (step3FilerDataDto.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_O))// business rules for original will go over here
                {
                    // verify ICD Scenario 1-4 for originals
                    step3OriginalRecordsValidationUtils.validateOriginalRecord(step3FilerDataDto, item);

                } else if (step3FilerDataDto.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_U))// business rules for updates will go over here
                {
                    step3UpdateRecordsValidationUtils.validateUpdateRecord(step3FilerDataDto, item);

                } else if (step3FilerDataDto.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_C))// business rules for correction will go over here
                {
                    step3CorrectedRecordsValidationUtils.validateCorrectedRecord(step3FilerDataDto, item);
                } else {
                    step3FilerDataDto.setRecordStatus(CommonDataConstants.VALIDATION_RULE_FAILED);
                    step3FilerDataDto.setBDMessage("Invalid correction code for record" + String.valueOf(step3FilerDataDto.getSourceUniqueId()));
                    /* Add item to rejected records bucket*/
                    item.getRejectedRecordList().add(step3FilerDataDto);
                }
            }
        } catch (Exception e) {
            logger.error("Error in record processor performCrossRecordValidation", e);
            Step3DataValidationException step3DataValidationException = new Step3DataValidationException();
            step3DataValidationException.setParent(item.getFilersWithSameIdsInFile());
            step3DataValidationException.setErrorMessage("Exception in processing record for given source unique id hence all the records with given source unique id will be skipped : " + item.getSourceUniqueId());
            throw step3DataValidationException;
        }

    }

    //protected void validationForOriginals(Step3FilerDataDto record, Step3FilerDataDto parent) throws Exception
    protected void performBusinessOperationsOnAcceptedRecord(Step3FilerDataDto item) {
        Step3FilerDataDto masterRecord = getMasterRecord(item);// no record for originals in db.

        for (Step3FilerDataDto acceptedRecord : item.getAcceptedRecordList()) {
            if (acceptedRecord.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_U)) {
                mergeCoverageInfo(masterRecord, acceptedRecord);
                updateRecipientInfo(masterRecord, acceptedRecord);
                updateIrsTransmissionCode(masterRecord, acceptedRecord, item);
            } else if (acceptedRecord.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_C)) {
                replaceFilersData(masterRecord, acceptedRecord);
                updateIrsTransmissionCode(masterRecord, acceptedRecord, item);
            }
        }
        if (masterRecord != null && !item.getAcceptedRecordList().isEmpty()) {
            item.getRecordsForFD().add(masterRecord);
        }
    }

    protected Step3FilerDataDto getMasterRecord(Step3FilerDataDto parentItem) {
        // check for master record in DB
        for (Step3FilerDataDto item : parentItem.getFilersWithSameIdsInDB()) {
            if (item.getUIDValue().equals(parentItem.getUIDValue())) {
                Step3FilerDataDto toReturn = new Step3FilerDataDto(item);
                toReturn.setIsRecordExistsInDB(Boolean.TRUE);
                setVersionNoAndCorIndicatorDB(toReturn);
                return toReturn;
            }
        }
        // check for original record in file.
        for (Step3FilerDataDto item : parentItem.getAcceptedRecordList()) {
            if (item.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_O) && item.getRecordStatus().equals(CommonDataConstants.VALIDATION_RULE_PASSED)) {
                Step3FilerDataDto toReturn = new Step3FilerDataDto(item);
                toReturn.setIsRecordExistsInDB(Boolean.FALSE);
                setVersionNoAndCorIndicatorFILE(toReturn);
                return toReturn;
            }
        }
        return null;
    }


    // merge coverage with or condition if anyone is 1 final record will be 1. no update if there is no change in coverage.
    protected void mergeCoverageInfo(Step3FilerDataDto masterRecord, Step3FilerDataDto slaveRecord) {

        boolean changeincoverage = false;
        if (!masterRecord.getJan().equals(slaveRecord.getJan())) {
            masterRecord.setJan(getMonthORValue(masterRecord.getJan(), slaveRecord.getJan()));
            changeincoverage = true;
        }
        if (!masterRecord.getFeb().equals(slaveRecord.getFeb())) {
            masterRecord.setFeb(getMonthORValue(masterRecord.getFeb(), slaveRecord.getFeb()));
            changeincoverage = true;
        }
        if (!masterRecord.getMar().equals(slaveRecord.getMar())) {
            masterRecord.setMar(getMonthORValue(masterRecord.getMar(), slaveRecord.getMar()));
            changeincoverage = true;
        }
        if (!masterRecord.getApr().equals(slaveRecord.getApr())) {
            masterRecord.setApr(getMonthORValue(masterRecord.getApr(), slaveRecord.getApr()));
            changeincoverage = true;
        }
        if (!masterRecord.getMay().equals(slaveRecord.getMay())) {
            masterRecord.setMay(getMonthORValue(masterRecord.getMay(), slaveRecord.getMay()));
            changeincoverage = true;
        }
        if (!masterRecord.getJun().equals(slaveRecord.getJun())) {
            masterRecord.setJun(getMonthORValue(masterRecord.getJun(), slaveRecord.getJun()));
            changeincoverage = true;
        }
        if (!masterRecord.getJul().equals(slaveRecord.getJul())) {
            masterRecord.setJul(getMonthORValue(masterRecord.getJul(), slaveRecord.getJul()));
            changeincoverage = true;
        }
        if (!masterRecord.getAug().equals(slaveRecord.getAug())) {
            masterRecord.setAug(getMonthORValue(masterRecord.getAug(), slaveRecord.getAug()));
            changeincoverage = true;
        }
        if (!masterRecord.getSep().equals(slaveRecord.getSep())) {
            masterRecord.setSep(getMonthORValue(masterRecord.getSep(), slaveRecord.getSep()));
            changeincoverage = true;
        }

        if (!masterRecord.getOct().equals(slaveRecord.getOct())) {
            masterRecord.setOct(getMonthORValue(masterRecord.getOct(), slaveRecord.getOct()));
            changeincoverage = true;
        }

        if (!masterRecord.getNov().equals(slaveRecord.getNov())) {
            masterRecord.setNov(getMonthORValue(masterRecord.getNov(), slaveRecord.getNov()));
            changeincoverage = true;
        }

        if (!masterRecord.getDec().equals(slaveRecord.getDec())) {
            masterRecord.setDec(getMonthORValue(masterRecord.getDec(), slaveRecord.getDec()));
            changeincoverage = true;
        }

        slaveRecord.setIsCoverageChangesAvailable(changeincoverage);
    }

    protected void updateRecipientInfo(Step3FilerDataDto masterRecord, Step3FilerDataDto slaveRecord) {

        masterRecord.getBatchInfo().setBatchId(slaveRecord.getBatchInfo().getBatchId());

        // entering recipient info
        masterRecord.setRecipientFirstName(slaveRecord.getRecipientFirstName());
        masterRecord.setRecipientLastName(slaveRecord.getRecipientLastName());
        masterRecord.setRecipientMiddleName(slaveRecord.getRecipientMiddleName());
        masterRecord.setRecepientSuffixName(slaveRecord.getRecepientSuffixName());
        masterRecord.setRecipientAddressLine1(slaveRecord.getRecipientAddressLine1());
        masterRecord.setRecipientAddressLine2(slaveRecord.getRecipientAddressLine2());
        masterRecord.setRecipientZip5(slaveRecord.getRecepientZip5());
        masterRecord.setRecipientZip4(slaveRecord.getRecepientZip4());
        masterRecord.setRecipientState(slaveRecord.getRecipientState());
        masterRecord.setRecipientCity(slaveRecord.getRecipientCity());
        masterRecord.seteMail(slaveRecord.getEMail());

        // updating employer information
        masterRecord.setPolicyOrigin(slaveRecord.getPolicyOrigin());
        masterRecord.setShopIdentifier(slaveRecord.getShopIdentifier());
        masterRecord.setEmployerName(slaveRecord.getEmployerName());
        masterRecord.setEmployerIdentificationNumber(slaveRecord.getEmployerIdentificationNumber());
        masterRecord.setEmployerContactNo(slaveRecord.getEmployerContactNo());
        masterRecord.setEmployerAddressLine1(slaveRecord.getEmployerAddressLine1());
        masterRecord.setEmployerAddressLine2(slaveRecord.getEmployerAddressLine2());
        masterRecord.setEmployerCityOrTown(slaveRecord.getEmployerCityOrTown());
        masterRecord.setEmployerStateOrProvince(slaveRecord.getEmployerStateOrProvince());
        masterRecord.setEmployerCountry(slaveRecord.getEmployerCountry());
        masterRecord.setZipOrPostalCode(slaveRecord.getZipOrPostalCode());

        //updating provider information
        masterRecord.setProviderName(slaveRecord.getProviderName());
        masterRecord.setProviderIdentificationNumber(slaveRecord.getProviderIdentificationNumber());
        masterRecord.setProviderContactNo(slaveRecord.getProviderContactNo());
        masterRecord.setProviderAddressLine2(slaveRecord.getProviderAddressLine2());
        masterRecord.setProviderAddressLine1(slaveRecord.getProviderAddressLine1());
        masterRecord.setProviderCityOrTown(slaveRecord.getProviderCityOrTown());
        masterRecord.setProviderStateOrProvince(slaveRecord.getProviderStateOrProvince());
        masterRecord.setProviderCountry(slaveRecord.getProviderCountry());
        masterRecord.setProviderZipOrPostalCode(slaveRecord.getProviderZipOrPostalCode());


        // all the statuses and comments related info
        masterRecord.setCommunicationPreference(slaveRecord.getCommunicationPreference());
        masterRecord.setComments(slaveRecord.getComments());
        masterRecord.setUpdatedDt(slaveRecord.getUpdatedDt());
        masterRecord.setUpdatedBy(slaveRecord.getUpdatedBy());
        masterRecord.setCorrectionDt(slaveRecord.getCorrectionDt());
        masterRecord.setCorrection(slaveRecord.getCorrection());
        masterRecord.setFormStatus((masterRecord.getFormStatus() == null) ? null : CommonDataConstants.FormStatus.FORM_STATUS_REGENERATE);
        masterRecord.setResponsiblePersonUniqueId(slaveRecord.getResponsiblePersonUniqueId());
        masterRecord.setMailedForm(slaveRecord.getMailedForm());
        masterRecord.setFilerStatus(slaveRecord.getFilerStatus());
        if ("C".equals(slaveRecord.getCorrection()) && slaveRecord.getFilerCoverage().getOrigCoverageBeginDt().toString().equals(CommonDataConstants.RECORD_INACTIVE_DATE)
                && slaveRecord.getFilerCoverage().getOrigCoverageEndDt().toString().equals(CommonDataConstants.RECORD_INACTIVE_DATE)) {
            masterRecord.setStatus(CommonDataConstants.RECORD_STATUS_INACTIVE);
            slaveRecord.setBDMessage(env.getProperty("CORRECTION.WR_CV5.2.1.1"));
        } else if ("C".equals(slaveRecord.getCorrection()) && CommonDataConstants.RECORD_STATUS_INACTIVE.equals(masterRecord.getStatus())) {
            masterRecord.setStatus(CommonDataConstants.RECORD_STATUS_ACTIVE);
            slaveRecord.setBDMessage(env.getProperty("CORRECTION.WR_CV5.2.2.1"));
        }

        //if filer_status is "C" and master record is from db in that case we have to update responsible person list
        if ("DB".equals(masterRecord.getRecordSource()) && "C".equals(masterRecord.getFilerStatus()) && masterRecord.getResposiblePersonList().isEmpty()) {
            masterRecord.getResposiblePersonList().addAll(slaveRecord.getResposiblePersonList());
            masterRecord.getCoveredPersonListSharingSameResponsible().addAll(slaveRecord.getCoveredPersonListSharingSameResponsible());
        } else if ("DB".equals(masterRecord.getRecordSource()) && "R".equals(masterRecord.getFilerStatus()) && masterRecord.getCoveredPersonList().isEmpty()) {
            masterRecord.getCoveredPersonList().addAll(slaveRecord.getCoveredPersonList());
        }

    }

    private String getMonthORValue(String master, String slave) {
        String finalValue;
        finalValue = ("1".equals(master)) || ("1".equals(slave)) ? "1" : "0";
        return (finalValue.length() > 0) ? finalValue : "";
    }


    protected void replaceFilersData(Step3FilerDataDto masterRecord, Step3FilerDataDto slaveRecord) {

        // replace ssn , dob and tin
        masterRecord.setRecipientDob(slaveRecord.getRecipientDob());
        masterRecord.setRecipientSsn(slaveRecord.getRecipientSsn());
        masterRecord.setRecipientTin(slaveRecord.getRecipientTin());


        if ((slaveRecord.getFilerCoverage().getOrigCoverageBeginDt().toString().equals(CommonDataConstants.COVERAGE_CLEAR_DATE) && slaveRecord.getFilerCoverage().getOrigCoverageEndDt().toString().equals(CommonDataConstants.COVERAGE_CLEAR_DATE))
                || (slaveRecord.getFilerCoverage().getOrigCoverageBeginDt().toString().equals(CommonDataConstants.RECORD_INACTIVE_DATE) && slaveRecord.getFilerCoverage().getOrigCoverageEndDt().toString().equals(CommonDataConstants.RECORD_INACTIVE_DATE))) {
            masterRecord.setJan(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setFeb(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setMar(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setApr(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setMay(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setJun(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setJul(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setAug(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setSep(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setOct(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setNov(String.valueOf(CommonDataConstants.NOT_COVERED));
            masterRecord.setDec(String.valueOf(CommonDataConstants.NOT_COVERED));
        } else {
            // replace months
            masterRecord.setJan(slaveRecord.getJan());
            masterRecord.setFeb(slaveRecord.getFeb());
            masterRecord.setMar(slaveRecord.getMar());
            masterRecord.setApr(slaveRecord.getApr());
            masterRecord.setMay(slaveRecord.getMay());
            masterRecord.setJun(slaveRecord.getJun());
            masterRecord.setJul(slaveRecord.getJul());
            masterRecord.setAug(slaveRecord.getAug());
            masterRecord.setSep(slaveRecord.getSep());
            masterRecord.setOct(slaveRecord.getOct());
            masterRecord.setNov(slaveRecord.getNov());
            masterRecord.setDec(slaveRecord.getDec());
        }


        // update other info
        updateRecipientInfo(masterRecord, slaveRecord);
    }

    /**
     * Responsibility for below function is to set respective version no and correction indicator for record which is already there in DB.
     **/
    public void setVersionNoAndCorIndicatorDB(Step3FilerDataDto item) {
        item.setVersionNumber(item.getVersionNumber() + 1);
        updateCorrectionIndicator(item);
    }

    public void setVersionNoAndCorIndicatorFILE(Step3FilerDataDto item) {

        if ("R".equals(item.getFilerStatus())) {
            item.setVersionNumber(0);
            updateCorrectionIndicator(item);
        } else if ("C".equals(item.getFilerStatus())) {
            if (!item.getResposiblePersonList().isEmpty() && ((Step3FilerDataDto) item.getResposiblePersonList().get(0)).getRecordSource().equals("DB")) {
                item.setVersionNumber(((Step3FilerDataDto) item.getResposiblePersonList().get(0)).getVersionNumber() + 1);
            } else {
                item.setVersionNumber(0);
            }
        }
    }

    public void updateCorrectionIndicator(Step3FilerDataDto item) {
        if (item.getFormStatus() == null) {
            item.setCorrectionIndicator("0");
        } else {
            if (item.getIrsTransmissionStatusCD() != null && !"DT".equals(item.getIrsTransmissionStatusCD())) {
                item.setCorrectionIndicator("2");
                item.setIrsTransmissionStatusCD("CO");
            } else {
                item.setCorrectionIndicator("1");
            }
        }
    }


    /* Logics for story ACAB-2545 */
    protected void updateIrsTransmissionCode(Step3FilerDataDto masterRecord, Step3FilerDataDto slaveRecord, Step3FilerDataDto item) {
        /* If IRS_TRANSMISSION_CODE if not NULL or DT and FILER_STATUS is R or N, change IRS_TRANSMISSION_CODE to CO */
        if (null != masterRecord.getIrsTransmissionStatusCD() && !"DT".equalsIgnoreCase(masterRecord.getIrsTransmissionStatusCD())
                && ("R".equalsIgnoreCase(slaveRecord.getFilerStatus()) || "N".equalsIgnoreCase(slaveRecord.getFilerStatus()))) {
            masterRecord.setIrsTransmissionStatusCD("CO");
        }

            /* The other scenario, when current record is a covered person, requires information of other records in the batch */
            /* and therefore will be handled in writer */



    }

}
