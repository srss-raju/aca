package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ritmukherjee on 11/4/2015.
 * updated by R. Mukherjee on 11/23/2015 <v 14.1></v>
 */
public class CustomerInfo implements Serializable {


    private static final long serialVersionUID = -1881393291830460368L;

    /*primary key*/
    private String customerID;


    /*pdfStatus added at V14-1*/
    private String pdfStatus;

    /* customer personal details*/
    private String firstName;
    private String lastName;
    private String Dob;

    /*user id*/
    private String uidNumber;
    private String uidType;


    /* address*/
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;


    /*Has-A FilerCoverageInfo*/

    private LinkedList<MonthCoverageInfo> filerCoverageInfo = new LinkedList<MonthCoverageInfo>();

    /* Comments*/
    private String comments;

    /*helper flags added according to requirement of UI*/
    private boolean isPdfAvailable;
    private boolean isMailEnable;

    /*coverage Source Infos has added : <v14.1 >*/
    private List<CoverageSourceInfo> coverageSources;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
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

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        this.Dob = dob;
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

    public LinkedList<MonthCoverageInfo> getFilerCoverageInfo() {
        return filerCoverageInfo;
    }

    public void setFilerCoverageInfo(LinkedList<MonthCoverageInfo> filerCoverageInfo) {
        this.filerCoverageInfo = filerCoverageInfo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPdfStatus() {
        return pdfStatus;
    }

    public void setPdfStatus(String pdfStatus) {
        this.pdfStatus = pdfStatus;
    }

    public List<CoverageSourceInfo> getCoverageSources() {
        return coverageSources;
    }

    public void setCoverageSources(List<CoverageSourceInfo> coverageSources) {
        this.coverageSources = coverageSources;
    }

    public boolean isPdfAvailable() {
        return isPdfAvailable;
    }

    public void setIsPdfAvailable(boolean isPdfAvailable) {
        this.isPdfAvailable = isPdfAvailable;
    }

    public boolean isMailEnable() {
        return isMailEnable;
    }

    public void setIsMailEnable(boolean isMailEnable) {
        this.isMailEnable = isMailEnable;
    }
}
