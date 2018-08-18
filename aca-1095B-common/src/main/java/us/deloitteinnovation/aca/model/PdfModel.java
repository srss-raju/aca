package us.deloitteinnovation.aca.model;

import java.sql.Date;

/**
 * Created by tthakore on 11/28/2015.
 */
public class PdfModel {

    private String sourceCD;
    private  byte[] pdfData;
    private Long sourceUniqueID;
    private String mailForm;
    private Date updatedDate;



    public byte[] getPdfData() {
        return pdfData;
    }
    public void setPdfData(byte[] pdfData) {
        this.pdfData = pdfData;
    }

    public String getSourceCD() {
        return sourceCD.substring(0,8);
    }
    public void setSourceCD(String sourceCD) {
        this.sourceCD = sourceCD;
    }

    public Long getSourceUniqueID() {
        return sourceUniqueID;
    }

    public void setSourceUniqueID(Long sourceUniqueID) {
        this.sourceUniqueID = sourceUniqueID;
    }
    public String getMailForm() {
        return mailForm;
    }

    public void setMailForm(String mailForm) {
        this.mailForm = (mailForm != null)?mailForm:"";
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
