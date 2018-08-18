package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;

/**
 * Created by ritmukherjee on 12/9/2015.
 */
public class ViewCurrentFormDataInfo implements Serializable{

    private static final long serialVersionUID = 5653895646725878402L;
    /*Combination of SourceCd and SourceUniqueId*/
    private String currentPdfID;

    /*pdf Status*/
    private String pdfStatus;
     /*Personal Info attributes*/
    private String firstName;
    private String lastName;
    private String uidNumber;
    private String uidType;
    private int taxYear;
    private String dob;

    /* address*/
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;


    /* Comments*/
    private String comments;

    public String getCurrentPdfID() {
        return currentPdfID;
    }

    public void setCurrentPdfID(String currentPdfID) {
        this.currentPdfID = currentPdfID;
    }

    public String getPdfStatus() {
        return pdfStatus;
    }

    public void setPdfStatus(String pdfStatus) {
        this.pdfStatus = pdfStatus;
    }

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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
