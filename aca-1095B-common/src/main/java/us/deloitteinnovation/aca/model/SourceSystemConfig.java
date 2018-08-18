package us.deloitteinnovation.aca.model;

import java.sql.Date;

public class SourceSystemConfig {
    private int    sourceCdId;
    private String sourceCd;
    private Integer taxYear;
    private String supportViewPdf;
    private String notFoundMessage;
    private String incorrectAddressMessage;
    private String returnAddressLine1;
    private String returnAddressLine2;
    private String returnAddressCity;
    private String returnAddressState;
    private String returnAddressZip;
    /**
     * TCC to utilize in production transmission.
     */
    private String transmitterControlCode;
    private String updatedBy;
    private Date   updatedDate;
    private String printPreferences;
    private String languagePreferences;
    private String stateAbbreviation;
    private String stateName;
    private String providerName;
    private String providerIdentificationNumber;
    private String providerContactFirstName ;
    private String providerContactLastName ;
    private Long   providerContactNo;
    private String providerAddressLine1;
    private String providerAddressLine2;
    private String providerCityOrTown;
    private String providerStateOrProvince;
    private String providerCountry;
    private String providerZipOrPostalCode;
    /**
     * TCC provided by the IRS used by the state for testing.
     */
    private String testTcc;
    /**
     * TCC provided by the IRS used by Deloitte for testing.
     */
    private String deloitteTestTcc;
    /** Software id assigned to Deloitte by the IRS. */
    private String softwareId ;

    public int getSourceCdId() {
        return sourceCdId;
    }

    public void setSourceCdId(int sourceCdId) {
        this.sourceCdId = sourceCdId;
    }

    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public void setTaxYear(Integer taxYear) {
        this.taxYear = taxYear;
    }

    public Integer getTaxYear() {
        return taxYear;
    }

    public String getSupportViewPdf() {
        return supportViewPdf;
    }

    public void setSupportViewPdf(String supportViewPdf) {
        this.supportViewPdf = supportViewPdf;
    }

    public String getNotFoundMessage() {
        return notFoundMessage;
    }

    public void setNotFoundMessage(String notFoundMessage) {
        this.notFoundMessage = notFoundMessage;
    }

    public String getIncorrectAddressMessage() {
        return incorrectAddressMessage;
    }

    public void setIncorrectAddressMessage(String incorrectAddressMessage) {
        this.incorrectAddressMessage = incorrectAddressMessage;
    }

    public String getReturnAddressCity() {
        return returnAddressCity;
    }

    public void setReturnAddressCity(String returnAddressCity) {
        this.returnAddressCity = returnAddressCity;
    }

    public String getReturnAddressState() {
        return returnAddressState;
    }

    public void setReturnAddressState(String returnAddressState) {
        this.returnAddressState = returnAddressState;
    }

    public String getReturnAddressZip() {
        return returnAddressZip;
    }

    public void setReturnAddressZip(String returnAddressZip) {
        this.returnAddressZip = returnAddressZip;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getPrintPreferences() {
        return printPreferences;
    }

    public void setPrintPreferences(String printPreferences) {
        this.printPreferences = printPreferences;
    }

    public String getLanguagePreferences() {
        return languagePreferences;
    }

    public void setLanguagePreferences(String languagePreferences) {
        this.languagePreferences = languagePreferences;
    }

    public String getReturnAddressMerged() {
        return this.returnAddressCity + " " + this.returnAddressState + " " + this.returnAddressZip;
    }

    public String getReturnAddressLine1() {
        return returnAddressLine1;
    }

    public void setReturnAddressLine1(String returnAddressLine1) {
        this.returnAddressLine1 = returnAddressLine1;
    }

    public String getReturnAddressLine2() {
        return returnAddressLine2;
    }

    public void setReturnAddressLine2(String returnAddressLine2) {
        this.returnAddressLine2 = returnAddressLine2;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getTransmitterControlCode() {
        return transmitterControlCode;
    }

    public void setTransmitterControlCode(String transmitterControlCode) {
        this.transmitterControlCode = transmitterControlCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderIdentificationNumber() {
        return providerIdentificationNumber;
    }

    public void setProviderIdentificationNumber(String providerIdentificationNumber) {
        this.providerIdentificationNumber = providerIdentificationNumber;
    }

    public String getProviderContactFirstName() {
        return providerContactFirstName;
    }

    public void setProviderContactFirstName(String providerContactFirstName) {
        this.providerContactFirstName = providerContactFirstName;
    }

    public String getProviderContactLastName() {
        return providerContactLastName;
    }

    public void setProviderContactLastName(String providerContactLastName) {
        this.providerContactLastName = providerContactLastName;
    }

    public Long getProviderContactNo() {
        return providerContactNo;
    }

    public void setProviderContactNo(Long providerContactNo) {
        this.providerContactNo = providerContactNo;
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

    public String getProviderStateOrProvince() {
        return providerStateOrProvince;
    }

    public void setProviderStateOrProvince(String providerStateOrProvince) {
        this.providerStateOrProvince = providerStateOrProvince;
    }

    public String getProviderCountry() {
        return providerCountry;
    }

    public void setProviderCountry(String providerCountry) {
        this.providerCountry = providerCountry;
    }

    public String getProviderZipOrPostalCode() {
        return providerZipOrPostalCode;
    }

    public void setProviderZipOrPostalCode(String providerZipOrPostalCode) {
        this.providerZipOrPostalCode = providerZipOrPostalCode;
    }

    public String getTestTcc() {
        return testTcc;
    }

    public void setTestTcc(String testTcc) {
        this.testTcc = testTcc;
    }

    public String getDeloitteTestTcc() {
        return deloitteTestTcc;
    }

    public void setDeloitteTestTcc(String deloitteTestTcc) {
        this.deloitteTestTcc = deloitteTestTcc;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }
}
