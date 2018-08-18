package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ritmukherjee on 10/29/2015.
 */
public class Form1095bDetails implements Serializable{


    private static final long serialVersionUID = 7790258457994554562L;

    //1. ID attributes

    private String formID;

    ///2. Info attributes
    private String firstName;
    private String lastName;
    private String uidNumber;
    private String uidType;
    private int taxYear;
    private String recepientDob;
    private String lastMailRequestedDate;

    //3. Current pdf attribute
    private Form1095BInfo currentForm;

    //historic pdf files
    private List<Form1095BInfo> historicForms;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getUidNumber() {
        return uidNumber;
    }

    public void setUidNumber(String uidNumber) {
        this.uidNumber = uidNumber;
    }

    public String getUidType() {
        return uidType;
    }

    public void setUidType(String uidType) {
        this.uidType = uidType;
    }

    public int getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(int taxYear) {
        this.taxYear = taxYear;
    }

    public String getRecepientDob() {
        return recepientDob;
    }

    public void setRecepientDob(String recepientDob) {
        this.recepientDob = recepientDob;
    }

    public String getLastMailRequestedDate() {
        return lastMailRequestedDate;
    }

    public void setLastMailRequestedDate(String lastMailRequestedDate) {
        this.lastMailRequestedDate = lastMailRequestedDate;
    }

    public Form1095BInfo getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(Form1095BInfo currentForm) {
        this.currentForm = currentForm;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Form1095BInfo> getHistoricForms() {
        return historicForms;
    }

    public void setHistoricForms(List<Form1095BInfo> historicForms) {
        this.historicForms = historicForms;
    }
}
