package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ritmukherjee on 11/30/2015.
 */
public class IndividualFormInfo implements Serializable{


    private static final long serialVersionUID = -6386074745141885618L;
    //1. ID attributes
    private String customerID;

    ///2. Info attributes
    private String firstName;
    private String lastName;
    private String uidNumber;
    private String uidType;
    private int taxYear;
    private String dob;
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


    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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



    public List<Form1095BInfo> getHistoricForms() {
        return historicForms;
    }

    public void setHistoricForms(List<Form1095BInfo> historicForms) {
        this.historicForms = historicForms;
    }
}
