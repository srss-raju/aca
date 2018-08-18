package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

/**
 * Created by ritmukherjee on 10/27/2015.
 */
public class FormPdfInfo {

    /*pseudo column pdfId*/
    private String pdfId;

    private String formStatus;



    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public String getPdfId() {
        return pdfId;
    }

    public void setPdfId(String pdfId) {
        this.pdfId = pdfId;
    }




}
