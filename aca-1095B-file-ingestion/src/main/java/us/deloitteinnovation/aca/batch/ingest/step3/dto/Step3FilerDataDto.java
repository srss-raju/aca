package us.deloitteinnovation.aca.batch.ingest.step3.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tthakore on 8/31/2016.
 */
public class Step3FilerDataDto extends FilerDemographicDto {

    private String rowNumber;// row number for the record if record from DB then 0 else actual row no of file.

    private String recordSource;// FILE or DB

    private String recordStatus;// PASSED or FAILED

    private String businessDecisionMSG; // business decision message for record validation

    private Boolean isRecordExistsInDB  = false; // In writer we are checking whether to update record or insert into filer demographics this will help us determine that update.

    private String status;

    private String originalCorrectionCode; // This is to avoid overlap actual correction code of record from file in case of update it is written only once.

    private Boolean isCoverageChangesAvailable = false;// To verify whether there are coverage changes available in update or not.

    private Integer versionNumber = 0;

    private String correctionIndicator;

    private String irsTransmissionStatusCD;



    private List<Step3FilerDataDto> filersWithSameIdsInFile = new ArrayList<>();//List of records  with same ids(UID or SID or TID) in file.

    private List<Step3FilerDataDto> filersWithSameIdsInDB = new ArrayList<Step3FilerDataDto>(); //List of records  with same ids(UID or SID or TID) in DB.
    private List<Step3FilerDataDto> acceptedRecordList = new ArrayList<Step3FilerDataDto>();//list od records which will be getting accepted for UID
    private List<Step3FilerDataDto> rejectedRecordList = new ArrayList<Step3FilerDataDto>();////list od records which will be getting rejected for UID
    private List<Step3FilerDataDto> recordsForFD = new ArrayList<Step3FilerDataDto>();// for n number of updates only one record will goto filer demographics this list will hold those records.
    private List<Step3FilerDataDto> resposiblePersonList = new ArrayList<Step3FilerDataDto>();//// list of responsible person for given covered person in file and DB.
    private List<Step3FilerDataDto> coveredPersonList = new ArrayList<Step3FilerDataDto>();//// list of covered person for given covered person in file and DB.
    private List<Step3FilerDataDto> coveredPersonListSharingSameResponsible = new ArrayList<Step3FilerDataDto>();//// list of covered person for given covered person in file and DB.


    public Step3FilerDataDto() {
    }

    /* This is a constructor to deep-clone another object */
    public Step3FilerDataDto(Step3FilerDataDto another) {
        super(another);

        /* Clone immutable objects */
        this.rowNumber = another.getRowNumber();
        this.recordSource = another.getRecordSource();
        this.recordStatus = another.getRecordStatus();
        this.businessDecisionMSG = another.getBDMessage();
        this.isRecordExistsInDB = another.getIsRecordExistsInDB();
        this.status = another.getStatus();
        this.originalCorrectionCode = another.getOriginalCorrectionCode();
        this.isCoverageChangesAvailable = another.getIsCoverageChangesAvailable();
        this.versionNumber = another.getVersionNumber();
        this.correctionIndicator = another.getCorrectionIndicator();
        this.irsTransmissionStatusCD = another.getIrsTransmissionStatusCD();

        /* Clone mutable objects */
        cloneInternalLists(another.getFilersWithSameIdsInFile(), this.filersWithSameIdsInFile);
        cloneInternalLists(another.getFilersWithSameIdsInDB(), this.filersWithSameIdsInDB);
        cloneInternalLists(another.getAcceptedRecordList(), this.acceptedRecordList);
        cloneInternalLists(another.getRejectedRecordList(), this.rejectedRecordList);
        cloneInternalLists(another.getRecordsForFD(), this.recordsForFD);
        cloneInternalLists(another.getResposiblePersonList(), this.resposiblePersonList);
        cloneInternalLists(another.getCoveredPersonList(), this.coveredPersonList);
        cloneInternalLists(another.getCoveredPersonListSharingSameResponsible(), this.coveredPersonListSharingSameResponsible);
    }

    private void cloneInternalLists(List<Step3FilerDataDto> targetList, List<Step3FilerDataDto> thisList) {
        if (thisList == null) {
            thisList = new ArrayList<>();
        }
        for (Step3FilerDataDto dto : targetList) {
            thisList.add(new Step3FilerDataDto(dto));
        }
    }


    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(String recordSource) {
        this.recordSource = recordSource;
    }

    public String getUIDValue() {
        return getSourceCd() + "_" + getSourceUniqueId() + "_" + this.getId().getTaxYear();
    }

    public List<Step3FilerDataDto> getFilersWithSameIdsInDB() {
        return filersWithSameIdsInDB;
    }

    public void setFilersWithSameIdsInDB(List<Step3FilerDataDto> filersWithSameIdsInDB) {
        this.filersWithSameIdsInDB = filersWithSameIdsInDB;
    }

    public List<Step3FilerDataDto> getFilersWithSameIdsInFile() {
        return filersWithSameIdsInFile;
    }

    public void setFilersWithSameIdsInFile(List<Step3FilerDataDto> filersWithSameIdsInFile) {
        this.filersWithSameIdsInFile = filersWithSameIdsInFile;
    }

    /**
     * this is to create id in case of ssn available since we have to check ssn and tin
     **/
    public String getSIDValue() {
        if (getRecipientSsn() != null && getRecipientSsn().length() > 0) {
            return "S_" + getRecipientSsn() + "_" + this.getId().getTaxYear();
        } else {
            return "NA";
        }
    }

    /**
     * this is to create id in case of tin available since we have to check ssn and tin
     **/
    public String getTIDValue() {
        if (getRecipientTin() != null && getRecipientTin().length() > 0) {
            return "T_" + getRecipientTin() + "_" + this.getId().getTaxYear();
        } else {
            return "NA";
        }
    }

    public String getBDMessage() {
        return businessDecisionMSG;
    }

    public void setBDMessage(String errorCode) {
        this.businessDecisionMSG = errorCode;
    }

    public List<Step3FilerDataDto> getAcceptedRecordList() {
        return acceptedRecordList;
    }

    public List<Step3FilerDataDto> getRejectedRecordList() {
        return rejectedRecordList;
    }

    public List<Step3FilerDataDto> getRecordsForFD() {
        return recordsForFD;
    }

    public String getSourceUniqueId() {
        return this.getId().getSourceUniqueId();
    }

    public String getSourceCd() {
        return this.getId().getSourceCd();
    }

    public List<Step3FilerDataDto> getResposiblePersonList() {
        return resposiblePersonList;
    }

    public String getTaxYear() {
        return this.getId().getTaxYear();
    }

    public Boolean getIsRecordExistsInDB() {
        return isRecordExistsInDB;
    }

    public void setIsRecordExistsInDB(Boolean isRecordExistsInDB) {
        this.isRecordExistsInDB = isRecordExistsInDB;
    }

    public String getCorrectionIndicator() {
        return correctionIndicator;
    }

    public void setCorrectionIndicator(String correctionIndicator) {
        this.correctionIndicator = correctionIndicator;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }


    public String getOriginalCorrectionCode() {
        return originalCorrectionCode;
    }
    public void setOriginalCorrectionCode(String originalCorrectionCode) {
        this.originalCorrectionCode = originalCorrectionCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Step3FilerDataDto> getCoveredPersonList() {
        return coveredPersonList;
    }

    public void setCoveredPersonList(List<Step3FilerDataDto> coveredPersonList) {
        this.coveredPersonList = coveredPersonList;
    }

    public Boolean getIsCoverageChangesAvailable() {
        return isCoverageChangesAvailable;
    }

    public void setIsCoverageChangesAvailable(Boolean isCoverageChangesAvailable) {
        this.isCoverageChangesAvailable = isCoverageChangesAvailable;
    }

    public String getIrsTransmissionStatusCD() {
        return irsTransmissionStatusCD;
    }

    public void setIrsTransmissionStatusCD(String irsTransmissionStatusCD) {
        this.irsTransmissionStatusCD = irsTransmissionStatusCD;
    }

    @Override
    public String toString() {
       return String.format("%s: %s", recordSource, rowNumber);
    }

    public List<Step3FilerDataDto> getCoveredPersonListSharingSameResponsible() {
        return coveredPersonListSharingSameResponsible;
    }

    public void setCoveredPersonListSharingSameResponsible(List<Step3FilerDataDto> coveredPersonListSharingSameResponsible) {
        this.coveredPersonListSharingSameResponsible = coveredPersonListSharingSameResponsible;
    }
}

