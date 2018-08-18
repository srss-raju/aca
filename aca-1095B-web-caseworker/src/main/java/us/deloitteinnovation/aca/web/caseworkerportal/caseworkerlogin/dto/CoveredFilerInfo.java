package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;

/**
 * Created by ritmukherjee on 11/24/2015.
 */
public class CoveredFilerInfo implements Serializable{

    private static final long serialVersionUID = -8846033124676060541L;

    /*pseudo key filerID*/
    private String filerID;

    /* filer personal details*/
    private String firstName;
    private String lastName;

    /*filer unique id*/
    private String uidNumber;
    private String uidType;

   /* filer's statistical details'*/
    private String Dob;
    private String taxYear;
    private String lastMailedDate;
    private String lastModifiedDate;

    /* filer spesific address*/
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;

    /*filer status R/N/C*/
    private char filerStatus;

    private String status;
    private String formStatus;


    private String printStatus;

    private String email;


    public String getFilerID() {
        return filerID;
    }

    public void setFilerID(String filerID) {
        this.filerID = filerID;
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

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(String taxYear) {
        this.taxYear = taxYear;
    }

    public String getLastMailedDate() {
        return lastMailedDate;
    }

    public void setLastMailedDate(String lastMailedDate) {
        this.lastMailedDate = lastMailedDate;
    }

    public char getFilerStatus() {
        return filerStatus;
    }

    public void setFilerStatus(char filerStatus) {
        this.filerStatus = filerStatus;
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

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

}
