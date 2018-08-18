package us.deloitteinnovation.aca.entity;

import javax.persistence.*;
import java.util.Date;

/**
 */
@Entity
@Table(name = "IRS_TRANSMISSION_ERRORS")
public class IrsTransmissionErrors {

    @Id
    @Column(name = "TRANSMISSION_ERROR_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ERROR_ID", nullable = false, length = 45)
    private String  errorId;
    @Column(name = "ERROR_MSG_CODE", nullable = false, length = 45)
    private String  errorMsgCode;
    @Column(name = "ERROR_MSG_TEXT", nullable = false, length = 450)
    private String  errorMsgText;
    @Column(name = "ERROR_ELEMENT_NAME", nullable = false, length = 450)
    private String  errorElementName;
    @Column(name = "RECEIPT_ID", nullable = false, length = 45)
    private String  receiptId;
    @Column(name = "SUBMISSION_ID", nullable = false)
    private Integer submissionId;
    @Column(name = "RECORD_ID", nullable = false)
    private Integer recordId;
    @Column(name = "TRANSMISSION_ID", nullable = false)
    private Integer transmissionId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE", nullable = false)
    private Date    updatedDate;
    @Column(name = "UPDATED_BY", nullable = false, length = 45)
    private String  updatedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getErrorMsgCode() {
        return errorMsgCode;
    }

    public void setErrorMsgCode(String errorMsgCode) {
        this.errorMsgCode = errorMsgCode;
    }

    public String getErrorMsgText() {
        return errorMsgText;
    }

    public void setErrorMsgText(String errorMsgText) {
        this.errorMsgText = errorMsgText;
    }

    public String getErrorElementName() {
        return errorElementName;
    }

    public void setErrorElementName(String errorElementName) {
        this.errorElementName = errorElementName;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public Integer getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Integer submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(Integer transmissionId) {
        this.transmissionId = transmissionId;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}


