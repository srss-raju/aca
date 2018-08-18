package us.deloitteinnovation.aca.batch.ingest.step3.dto;

import java.util.Date;

/**
 * Created by tthakore on 9/9/2016.
 */
public class BusinessValidationRuleDto {
    private static final long serialVersionUID = 1L;

    private String taxYear;
    private String sourceCd;
    private long sourceUniqueId;
    private String businessDecision;
    private String businessRule;
    private String uid;
    private Date dob;
    private String correctionCode;
    private int batchId;
    private int rowNumber;
    private String updatedBy;
    private String updatedDate;

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(String taxYear) {
        this.taxYear = taxYear;
    }

    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public long getSourceUniqueId() {
        return sourceUniqueId;
    }

    public void setSourceUniqueId(long sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public String getBusinessDecision() {
        return businessDecision;
    }

    public void setBusinessDecision(String businessDecision) {
        this.businessDecision = businessDecision;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCorrectionCode() {
        return correctionCode;
    }

    public void setCorrectionCode(String correctionCode) {
        this.correctionCode = correctionCode;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getBusinessRule() {
        return businessRule;
    }

    public void setBusinessRule(String businessRule) {
        this.businessRule = businessRule;
    }
}
