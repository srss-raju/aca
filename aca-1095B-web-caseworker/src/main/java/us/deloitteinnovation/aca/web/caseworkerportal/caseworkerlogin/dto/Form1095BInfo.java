package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.entity.AuditFilerDemographic;
import us.deloitteinnovation.aca.entity.FilerDemographicCW;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ritmukherjee on 10/14/2015.
 */
public class Form1095BInfo implements Serializable {
    private static final String FORM_ID_FORMAT_16 = "%s_%d_%d";
    private static final String FORM_ID_FORMAT_15 = "%s_%d";
    private static final int YEAR_2016 = 2016;
    private static final String DATE_FORMAT = "MM/dd/yyyy hh:mm:ss a";


    private static final long serialVersionUID = -2601761576947039030L;
    /*Combination of SourceCd and SourceUniqueId*/
    private String formID;
    private String generatedBy;
    private String lastModifiedDate;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;
    private String email;
    private String status;
    private String formStatus;
    private String printStatus;


    /*added on 14/12/2015*/
    private String lastMailRequestedDate;
    private String printDate;
    private String updatedDate;
    private String mailedDate;
    private String acknowledgeDate;

    private String mailStatus;
    private String pdfStatus;

    private boolean isPdfAvailable;

    private String comments;

    private String auditSequenceNo;

    private String irsTransmissionStatusCd;


    /* Constructor */

    public Form1095BInfo() {}

    public Form1095BInfo(AuditFilerDemographic auditFilerDemographic) {

        /* Generate proper form_id format */
        if (auditFilerDemographic.getTaxYear() < YEAR_2016) {
            setFormID(String.format(FORM_ID_FORMAT_15, auditFilerDemographic.getSourceCd(),
                    auditFilerDemographic.getSourceUniqueId()));
        } else {
            setFormID(String.format(FORM_ID_FORMAT_16, auditFilerDemographic.getSourceCd(),
                    auditFilerDemographic.getSourceUniqueId(), auditFilerDemographic.getTaxYear()));
        }

        /* Set values */
        setGeneratedBy(auditFilerDemographic.getUpdatedBy());
        Timestamp lastModified = auditFilerDemographic.getUpdatedDate();
        setLastModifiedDate(new SimpleDateFormat(DATE_FORMAT).format(lastModified));
        setAddressLine1(auditFilerDemographic.getRecipientAddressLine1());
        setAddressLine2(null == auditFilerDemographic.getRecipientAddressLine2()
                ? CommonDataConstants.EMPTY_VALUE
                : auditFilerDemographic.getRecipientAddressLine2());
        setCity(auditFilerDemographic.getRecipientCity());
        setState(auditFilerDemographic.getRecipientState());
        setZipcode(auditFilerDemographic.getRecipientZip5());
        setComments(null == auditFilerDemographic.getComments()
                ? CommonDataConstants.EMPTY_VALUE
                : auditFilerDemographic.getComments());
        setAuditSequenceNo(Integer.toString(auditFilerDemographic.getId()));
        setIsPdfAvailable(Boolean.TRUE);
    }

    /* Getters and Setters */

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAuditSequenceNo() {
        return auditSequenceNo;
    }

    public void setAuditSequenceNo(String auditSequenceNo) {
        this.auditSequenceNo = auditSequenceNo;
    }

    public String getLastMailRequestedDate() {
        return lastMailRequestedDate;
    }

    public void setLastMailRequestedDate(String lastMailRequestedDate) {
        this.lastMailRequestedDate = lastMailRequestedDate;
    }

    public String getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(String mailStatus) {
        this.mailStatus = mailStatus;
    }

    public String getPdfStatus() {
        return pdfStatus;
    }

    public void setPdfStatus(String pdfStatus) {
        this.pdfStatus = pdfStatus;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public boolean isPdfAvailable() {
        return isPdfAvailable;
    }

    public void setIsPdfAvailable(boolean isPdfAvailable) {
        this.isPdfAvailable = isPdfAvailable;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getIrsTransmissionStatusCd() {
        return irsTransmissionStatusCd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setIrsTransmissionStatusCd(String irsTransmissionStatusCd) {
        this.irsTransmissionStatusCd = irsTransmissionStatusCd;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMailedDate() {
        return mailedDate;
    }

    public void setMailedDate(String mailedDate) {
        this.mailedDate = mailedDate;
    }

    public String getAcknowledgeDate() {
        return acknowledgeDate;
    }

    public void setAcknowledgeDate(String acknowledgeDate) {
        this.acknowledgeDate = acknowledgeDate;
    }
}
