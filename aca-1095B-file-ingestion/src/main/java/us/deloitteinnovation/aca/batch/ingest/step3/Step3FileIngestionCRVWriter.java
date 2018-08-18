package us.deloitteinnovation.aca.batch.ingest.step3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.BusinessValidationRuleDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.services.Step3RecordValidationService;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.util.*;

/**
 * Created by tthakore on 8/31/2016.
 */
@Component
public class Step3FileIngestionCRVWriter implements ItemWriter<Step3FilerDataDto> {

    private static Logger logger = LoggerFactory.getLogger(Step3FileIngestionCRVWriter.class);

    @Autowired
    Step3RecordValidationService step3RecordValidationService;

    ArrayList<Step3FilerDataDto> insertForFD;
    ArrayList<Step3FilerDataDto> updateForFD;
    ArrayList<Step3FilerDataDto> recordsToMarkRegenerate;
    ArrayList<FilerCoverageDto> coverageInfoTransectionData;
    ArrayList<BusinessValidationRuleDto> businessValidationRuleDtos;
    ArrayList<BusinessValidationRuleDto> exceptionReportList;
    Map<String, Step3FilerDataDto> otherRecordsToUpdateVersionFD;


    /**
     * writer will be writing to multiple tables on the basis of data received.
     **/
    @Override
    public void write(List<? extends Step3FilerDataDto> list) throws Exception {
        logger.info("start of Step3FileIngestionCRVWriter writer.");
        insertForFD = new ArrayList<>();
        updateForFD = new ArrayList<>();
        coverageInfoTransectionData = new ArrayList<>();
        businessValidationRuleDtos = new ArrayList<>();
        exceptionReportList = new ArrayList<>();
        recordsToMarkRegenerate = new ArrayList<>();
        otherRecordsToUpdateVersionFD = new HashMap<>();


        try {
            for (Step3FilerDataDto item : list) {
                for (Step3FilerDataDto acceptedRecord : item.getRecordsForFD()) {
                    updateRecordsForFD(acceptedRecord);
                }

                for (Step3FilerDataDto record : item.getAcceptedRecordList()) {
                    updateBusinessValidationAndCoverageList(record);
                }

                for (Step3FilerDataDto record : item.getRejectedRecordList()) {
                    BusinessValidationRuleDto businessValidationRuleDto = getBusinessRuleDTO(record);
                    businessValidationRuleDtos.add(businessValidationRuleDto);
                    exceptionReportList.add(businessValidationRuleDto);
                }

            }

            logger.info("inserting values in DB from writer...");
            step3RecordValidationService.bulkInsertFD(insertForFD);
            updateForFD = updateIrsTransmissionCdForResponsible(updateForFD);
            step3RecordValidationService.bulkUpdateFD(updateForFD);
            step3RecordValidationService.bulkInsertBusinessLogs(businessValidationRuleDtos);
            step3RecordValidationService.bulkInsertExceptionReport(exceptionReportList);
            step3RecordValidationService.bulkUpdateFormStatus(recordsToMarkRegenerate);
            updateCoverageSource(list);
            step3RecordValidationService.bulkUpdateVersionNo(new ArrayList<>(otherRecordsToUpdateVersionFD.values()));
            logger.info("start of Step3FileIngestionCRVWriter writer.");
        } catch (Exception e) {

            Step3DataValidationException step3DataValidationException = new Step3DataValidationException();
            List<Step3FilerDataDto> dataList = new ArrayList<>();
            dataList.addAll(list);
            step3DataValidationException.setErrorMessage(e.getMessage());
            step3DataValidationException.setParent(dataList);

            logger.error("Exception in writing records into database", e);
            throw step3DataValidationException;

        }
    }

    protected BusinessValidationRuleDto getBusinessRuleDTO(Step3FilerDataDto step3FilerDataDto) {
        /*setup current date and time*/
        BusinessValidationRuleDto businessValidationRuleDto = new BusinessValidationRuleDto();
        businessValidationRuleDto.setSourceCd(step3FilerDataDto.getSourceCd());
        businessValidationRuleDto.setSourceUniqueId(Long.valueOf(step3FilerDataDto.getSourceUniqueId()));
        businessValidationRuleDto.setBatchId(step3FilerDataDto.getBatchInfo().getBatchId());
        businessValidationRuleDto.setDob(step3FilerDataDto.getRecipientDob());
        businessValidationRuleDto.setBusinessDecision(step3FilerDataDto.getRecordStatus());
        businessValidationRuleDto.setBusinessRule(step3FilerDataDto.getBDMessage());
        businessValidationRuleDto.setUid((step3FilerDataDto.getRecipientSsn() != null) ? step3FilerDataDto.getRecipientSsn() : step3FilerDataDto.getRecipientTin());
        businessValidationRuleDto.setTaxYear(step3FilerDataDto.getId().getTaxYear());
        businessValidationRuleDto.setRowNumber(Integer.valueOf(step3FilerDataDto.getRowNumber()));
        businessValidationRuleDto.setUpdatedBy(step3FilerDataDto.getUpdatedBy());
        businessValidationRuleDto.setUpdatedDate(step3FilerDataDto.getUpdatedDt());
        businessValidationRuleDto.setCorrectionCode(step3FilerDataDto.getOriginalCorrectionCode());
        return businessValidationRuleDto;
    }

    protected void checkAndMarkRegenerate(Step3FilerDataDto item, List<Step3FilerDataDto> recordsToMarkRegenerate) {
        for (Step3FilerDataDto responsiblePerson : item.getResposiblePersonList()) {
            if (responsiblePerson.getFormStatus() != null && responsiblePerson.getFormStatus().equals(CommonDataConstants.FormStatus.FORM_STATUS_GENERATED) && "0".equals(responsiblePerson.getRowNumber())) {
                recordsToMarkRegenerate.add(responsiblePerson);
            }
            // Updating correction indicator for responsible person in case of covered person.
            if ("R".equals(responsiblePerson.getFilerStatus())) {
                if (responsiblePerson.getFormStatus() == null) {
                    responsiblePerson.setCorrectionIndicator("0");
                } else {
                    if (responsiblePerson.getIrsTransmissionStatusCD() != null && !"DT".equals(responsiblePerson.getIrsTransmissionStatusCD())) {
                        responsiblePerson.setCorrectionIndicator("2");
                        responsiblePerson.setIrsTransmissionStatusCD("CO");
                    } else {
                        responsiblePerson.setCorrectionIndicator("1");
                    }
                }
            }
        }
    }

    protected void updateRecordsForFD(Step3FilerDataDto acceptedRecord) {
        if (acceptedRecord.getIsRecordExistsInDB()) {
            updateForFD.add(acceptedRecord);
            createVersionUpdateList(acceptedRecord);
        } else {
            insertForFD.add(acceptedRecord);
            createVersionUpdateList(acceptedRecord);
        }
    }

    protected void updateBusinessValidationAndCoverageList(Step3FilerDataDto record) {
        BusinessValidationRuleDto businessValidationRuleDto = getBusinessRuleDTO(record);
        if ((record.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_U) && record.getIsCoverageChangesAvailable()) || record.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_O)) {

        }

        //we have to mark form status as regenerate for responsible person in case if filer_status is C and record are there in DB.
        if (record.getFilerStatus().equals(String.valueOf(CommonDataConstants.FilerStatus.FILER_STATUS_C))) {
            checkAndMarkRegenerate(record, recordsToMarkRegenerate);
        }
        businessValidationRuleDtos.add(businessValidationRuleDto);
    }

    public void updateCoverageSource(List<? extends Step3FilerDataDto> list) {
        for (Step3FilerDataDto item : list) {
            for (Step3FilerDataDto record : item.getAcceptedRecordList()) {
                if ((record.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_U) && record.getIsCoverageChangesAvailable()) || record.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_O)) {
                    step3RecordValidationService.bulkInsertFCS(record.getFilerCoverage());
                } else if (record.getCorrection().equals(CommonDataConstants.CORRECTION_CODE_C)) {
                    FilerCoverageDto filerCoverageDto = new FilerCoverageDto();
                    filerCoverageDto.setId(new FilerDemographicPKDto());
                    filerCoverageDto.getId().setSourceUniqueId(record.getSourceUniqueId());
                    filerCoverageDto.getId().setSourceCd(record.getSourceCd());
                    filerCoverageDto.getId().setTaxYear(record.getTaxYear());
                    step3RecordValidationService.bulkDeleteFromCoverage(filerCoverageDto);
                    if ((!record.getFilerCoverage().getOrigCoverageBeginDt().toString().equals(CommonDataConstants.COVERAGE_CLEAR_DATE) && !record.getFilerCoverage().getOrigCoverageEndDt().toString().equals(CommonDataConstants.COVERAGE_CLEAR_DATE))
                            && (!record.getFilerCoverage().getOrigCoverageBeginDt().toString().equals(CommonDataConstants.RECORD_INACTIVE_DATE) && !record.getFilerCoverage().getOrigCoverageEndDt().toString().equals(CommonDataConstants.RECORD_INACTIVE_DATE))) {
                        step3RecordValidationService.bulkInsertFCS(record.getFilerCoverage());
                    }
                }
            }
        }

    }


    /**
     * createVersionUpdateList will be creating a list from accepted records with responsible person, covered perso and covered
     * person saring the same responsible person unique id.
     * we will be updating lates version no and adding them to otherRecordsToUpdateVersionFD so that we can update
     * version for whole set (i.e. responsible person + covered persons)
     * @param acceptedRecord :- accepted record we are using.
     *
     * **/
    public void createVersionUpdateList(Step3FilerDataDto acceptedRecord) {

        int currentVersion = acceptedRecord.getVersionNumber();
        if (!acceptedRecord.getCoveredPersonList().isEmpty()) {
            for (Step3FilerDataDto coveredPerson : acceptedRecord.getCoveredPersonList()) {
                if ("DB".equals(coveredPerson.getRecordSource()) && !acceptedRecord.getUIDValue().equals(coveredPerson.getUIDValue())) {
                    coveredPerson.setVersionNumber(currentVersion);
                    otherRecordsToUpdateVersionFD.put(coveredPerson.getUIDValue(), coveredPerson);
                }
            }
        }
        if (!acceptedRecord.getResposiblePersonList().isEmpty()) {
            for (Step3FilerDataDto responsiblePerson : acceptedRecord.getResposiblePersonList()) {
                if ("DB".equals(responsiblePerson.getRecordSource()) && !acceptedRecord.getUIDValue().equals(responsiblePerson.getUIDValue())) {
                    responsiblePerson.setVersionNumber(currentVersion);
                    otherRecordsToUpdateVersionFD.put(responsiblePerson.getUIDValue(), responsiblePerson);
                }
            }

            for (Step3FilerDataDto recordWithSameResponsibleID : acceptedRecord.getCoveredPersonListSharingSameResponsible()) {
                if ("DB".equals(recordWithSameResponsibleID.getRecordSource()) && !acceptedRecord.getUIDValue().equals(recordWithSameResponsibleID.getUIDValue())) {
                    recordWithSameResponsibleID.setVersionNumber(currentVersion);
                    otherRecordsToUpdateVersionFD.put(recordWithSameResponsibleID.getUIDValue(), recordWithSameResponsibleID);
                }
            }

        }

    }

    private ArrayList<Step3FilerDataDto> updateIrsTransmissionCdForResponsible(Collection<Step3FilerDataDto> items) {
        ArrayList<Step3FilerDataDto> newUpdateList = new ArrayList<>();
        for (Step3FilerDataDto acceptedRecord : items) {
            if ("C".equalsIgnoreCase(acceptedRecord.getFilerStatus())
                    && ("C".equalsIgnoreCase(acceptedRecord.getCorrection()) || "U".equalsIgnoreCase(acceptedRecord.getCorrection()))) {
                updateIrsTransmissionCdInResponsibleList(acceptedRecord, items, newUpdateList);
            }
        }
        newUpdateList.addAll(items);
        return newUpdateList;
    }
    private void updateIrsTransmissionCdInResponsibleList(Step3FilerDataDto record, Collection<Step3FilerDataDto> items, Collection<Step3FilerDataDto> newItems) {
        Step3FilerDataDto responsibleInUpload = findBySourceUniqueId(items, record.getResponsiblePersonUniqueId());
        if (responsibleInUpload != null
                && null != responsibleInUpload.getIrsTransmissionStatusCD()
                && !"DT".equalsIgnoreCase(responsibleInUpload.getIrsTransmissionStatusCD())
                && isResponsible(responsibleInUpload)) {
            responsibleInUpload.setIrsTransmissionStatusCD("CO");
        } else {
            Step3FilerDataDto responsibleInDb = findBySourceUniqueId(record.getResposiblePersonList(), record.getResponsiblePersonUniqueId(), "DB");
            if (responsibleInDb != null
                    && null != responsibleInDb.getIrsTransmissionStatusCD()
                    && !"DT".equalsIgnoreCase(responsibleInDb.getIrsTransmissionStatusCD())
                    && isResponsible(responsibleInDb)) {
                responsibleInDb.setIrsTransmissionStatusCD("CO");
                newItems.add(responsibleInDb);
            }
        }
    }
    private Step3FilerDataDto findBySourceUniqueId(Collection<Step3FilerDataDto> source, String sourceUniqueId, String recordSource) {
        for (Step3FilerDataDto responsible: source) {
            if (recordSource.equalsIgnoreCase(responsible.getRecordSource()) && responsible.getSourceUniqueId().equalsIgnoreCase(sourceUniqueId)) {
                return responsible;
            }
        }
        return null;
    }
    private Step3FilerDataDto findBySourceUniqueId(Collection<Step3FilerDataDto> source, String sourceUniqueId) {
        for (Step3FilerDataDto responsible: source) {
            if (responsible.getSourceUniqueId().equalsIgnoreCase(sourceUniqueId)) {
                return responsible;
            }
        }
        return null;
    }

    private boolean isResponsible(Step3FilerDataDto dto) {
        return "R".equalsIgnoreCase(dto.getFilerStatus()) || "N".equalsIgnoreCase(dto.getFilerStatus());
    }

}
