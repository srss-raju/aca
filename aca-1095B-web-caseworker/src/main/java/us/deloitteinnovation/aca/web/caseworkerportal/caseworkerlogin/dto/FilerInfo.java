package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ritmukherjee on 10/13/2015.
 */
public class FilerInfo implements Serializable {


    private static final long serialVersionUID = -5746484586856076598L;

    /*filerID:Primary Key*/
    private String filerID;

    /*filer personal info*/
    private String recipientFirstName;
    private String recipientLastName;
    /*private int taxYear;*/
    private String recipientDob;
    private String recepientCaseApplicationId;

    private String status;

    /*filer unique social number */
    private String uidNumber;
    private String uidType;

    private String recipientSsn;
    private String recipientTin;



    private String recepientAddressLine1;
    private String recepientAddressLine2;
    private String recepientCity;
    private String recepientState;
    private String recepientZip4;
    private String recepientZip5;

    private Character communicationPreference;
    private String correction;
    private Date correctionDt;
    private String eMail;
    private String comments;

    //employment related
    private String employerIdentificationNumber;
    private String employerName;
    private String employerAddressLine1;
    private String employerAddressLine2;
    private String employerCityOrTown;
    private String employerStateOrProvince;
    private String employerZipOrPostalCode;
    private String employerCountry;
    private Long employerContactNo;


    //other relevant
    private Character filerStatus;
    private String languagePreference;
    private Character policyOrigin;
    private String programName;

    //policy Releted
    private String providerIdentificationNumber;
    private String providerName;
    private String providerAddressLine1;
    private String providerAddressLine2;
    private String providerCityOrTown;
    private String providerCountry;
    private String providerStateOrProvince;
    private String providerZipOrPostalCode;
    private Long providerContactNo;

    // Non-visible info


    //maintaining Reference of Form1095BInfo
    private Form1095BInfo form1095BInfo;

    //maintaining List of pdfs
    private Pdfs pdfs;

    //maintaining list of coverages
    private Coverages coverages;


    public String getRecipientFirstName() {
        return recipientFirstName;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }

    public String getRecipientLastName() {
        return recipientLastName;
    }

    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
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

   /* public int getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(int taxYear) {
        this.taxYear = taxYear;
    }*/


    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public Character getCommunicationPreference() {
        return communicationPreference;
    }

    public void setCommunicationPreference(Character communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public Date getCorrectionDt() {
        return correctionDt;
    }

    public void setCorrectionDt(Date correctionDt) {
        this.correctionDt = correctionDt;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }


    public Form1095BInfo getForm1095BInfo() {
        return form1095BInfo;
    }

    public void setForm1095BInfo(Form1095BInfo form1095BInfo) {
        this.form1095BInfo = form1095BInfo;
    }

    public String getEmployerIdentificationNumber() {
        return employerIdentificationNumber;
    }

    public void setEmployerIdentificationNumber(String employerIdentificationNumber) {
        this.employerIdentificationNumber = employerIdentificationNumber;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerAddressLine1() {
        return employerAddressLine1;
    }

    public void setEmployerAddressLine1(String employerAddressLine1) {
        this.employerAddressLine1 = employerAddressLine1;
    }

    public String getEmployerAddressLine2() {
        return employerAddressLine2;
    }

    public void setEmployerAddressLine2(String employerAddressLine2) {
        this.employerAddressLine2 = employerAddressLine2;
    }

    public String getEmployerCityOrTown() {
        return employerCityOrTown;
    }

    public void setEmployerCityOrTown(String employerCityOrTown) {
        this.employerCityOrTown = employerCityOrTown;
    }

    public String getEmployerStateOrProvince() {
        return employerStateOrProvince;
    }

    public void setEmployerStateOrProvince(String employerStateOrProvince) {
        this.employerStateOrProvince = employerStateOrProvince;
    }

    public Long getEmployerContactNo() {
        return employerContactNo;
    }

    public void setEmployerContactNo(Long employerContactNo) {
        this.employerContactNo = employerContactNo;
    }

    public String getEmployerCountry() {
        return employerCountry;
    }

    public void setEmployerCountry(String employerCountry) {
        this.employerCountry = employerCountry;
    }

    public Character getFilerStatus() {
        return filerStatus;
    }

    public void setFilerStatus(Character filerStatus) {
        this.filerStatus = filerStatus;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public Character getPolicyOrigin() {
        return policyOrigin;
    }

    public void setPolicyOrigin(Character policyOrigin) {
        this.policyOrigin = policyOrigin;
    }

    public String getEmployerZipOrPostalCode() {
        return employerZipOrPostalCode;
    }

    public void setEmployerZipOrPostalCode(String employerZipOrPostalCode) {
        this.employerZipOrPostalCode = employerZipOrPostalCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProviderIdentificationNumber() {
        return providerIdentificationNumber;
    }

    public void setProviderIdentificationNumber(String providerIdentificationNumber) {
        this.providerIdentificationNumber = providerIdentificationNumber;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderAddressLine1() {
        return providerAddressLine1;
    }

    public void setProviderAddressLine1(String providerAddressLine1) {
        this.providerAddressLine1 = providerAddressLine1;
    }

    public String getProviderAddressLine2() {
        return providerAddressLine2;
    }

    public void setProviderAddressLine2(String providerAddressLine2) {
        this.providerAddressLine2 = providerAddressLine2;
    }

    public String getProviderCityOrTown() {
        return providerCityOrTown;
    }

    public void setProviderCityOrTown(String providerCityOrTown) {
        this.providerCityOrTown = providerCityOrTown;
    }

    public String getProviderCountry() {
        return providerCountry;
    }

    public void setProviderCountry(String providerCountry) {
        this.providerCountry = providerCountry;
    }

    public String getProviderStateOrProvince() {
        return providerStateOrProvince;
    }

    public void setProviderStateOrProvince(String providerStateOrProvince) {
        this.providerStateOrProvince = providerStateOrProvince;
    }

    public String getProviderZipOrPostalCode() {
        return providerZipOrPostalCode;
    }

    public void setProviderZipOrPostalCode(String providerZipOrPostalCode) {
        this.providerZipOrPostalCode = providerZipOrPostalCode;
    }

    public Long getProviderContactNo() {
        return providerContactNo;
    }

    public void setProviderContactNo(Long providerContactNo) {
        this.providerContactNo = providerContactNo;
    }

    public String getFilerID() {
        return filerID;
    }

    public void setFilerID(String filerID) {
        this.filerID = filerID;
    }

    public Pdfs getPdfs() {
        return pdfs;
    }

    public void setPdfs(Pdfs pdfs) {
        this.pdfs = pdfs;
    }

    public String getRecepientCaseApplicationId() {
        return recepientCaseApplicationId;
    }

    public void setRecepientCaseApplicationId(String recepientCaseApplicationId) {
        this.recepientCaseApplicationId = recepientCaseApplicationId;
    }

    public String getRecepientAddressLine1() {
        return recepientAddressLine1;
    }

    public void setRecepientAddressLine1(String recepientAddressLine1) {
        this.recepientAddressLine1 = recepientAddressLine1;
    }

    public String getRecepientAddressLine2() {
        return recepientAddressLine2;
    }

    public void setRecepientAddressLine2(String recepientAddressLine2) {
        this.recepientAddressLine2 = recepientAddressLine2;
    }

    public String getRecepientCity() {
        return recepientCity;
    }

    public void setRecepientCity(String recepientCity) {
        this.recepientCity = recepientCity;
    }

    public String getRecepientState() {
        return recepientState;
    }

    public void setRecepientState(String recepientState) {
        this.recepientState = recepientState;
    }

    public String getRecepientZip4() {
        return recepientZip4;
    }

    public void setRecepientZip4(String recepientZip4) {
        this.recepientZip4 = recepientZip4;
    }

    public String getRecepientZip5() {
        return recepientZip5;
    }

    public void setRecepientZip5(String recepientZip5) {
        this.recepientZip5 = recepientZip5;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Coverages getCoverages() {
        return coverages;
    }

    public void setCoverages(Coverages coverages) {
        this.coverages = coverages;
    }

    public String getRecipientSsn() {
        return recipientSsn;
    }

    public void setRecipientSsn(String recipientSsn) {
        this.recipientSsn = recipientSsn;
    }

    public String getRecipientTin() {
        return recipientTin;
    }

    public void setRecipientTin(String recipientTin) {
        this.recipientTin = recipientTin;
    }

    public String getRecipientDob() {
        return recipientDob;
    }

    public void setRecipientDob(String recipientDob) {
        this.recipientDob = recipientDob;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
