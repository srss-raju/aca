package us.deloitteinnovation.aca.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by tthakore on 10/14/2015.
 */
@Entity
@Table(name = "SOURCE_SYSTEM_CONFIG")
public class SourceSystemConfig {


    @EmbeddedId
    private SourceSystemConfigPK id;

    @Basic
    @Column(name = "SUPPORT_VIEW_PDF")
    private String supportViewPdf;

    @Basic
    @Column(name="SOURCE_CD_ID")
    private Integer sourceCdId;

    @Basic
    @Column(name = "NOT_FOUND_MESSAGE", nullable = true, insertable = true, updatable = true, length = 500)
    private String notFoundMessage;

    @Basic
    @Column(name = "INCORRECT_ADDRESS_MESSAGE", nullable = true, insertable = true, updatable = true, length = 500)
    private String incorrectAddressMessage;

    @Basic
    @Column(name = "RETURN_ADDRESS_LINE1", nullable = true, insertable = true, updatable = true, length = 45)
    private String returnAddressLine1;

    @Basic
    @Column(name = "RETURN_ADDRESS_LINE2", nullable = true, insertable = true, updatable = true, length = 45)
    private String returnAddressLine2;

    @Basic
    @Column(name = "RETURN_ADDRESS_CITY", nullable = true, insertable = true, updatable = true, length = 45)
    private String returnAddressCity;

    @Basic
    @Column(name = "RETURN_ADDRESS_STATE", nullable = true, insertable = true, updatable = true, length = 45)
    private String returnAddressState;

    @Basic
    @Column(name = "RETURN_ADDRESS_ZIP", nullable = true, insertable = true, updatable = true, length = 45)
    private String returnAddressZip;
    /**
     * TCC to utilize in production transmission.
     */
    @Basic
    @Column(name = "TRANSMITTER_CONTROL_CODE", nullable = true, insertable = true, updatable = true, length = 45)
    private String transmitterControlCode;

    @Basic
    @Column(name = "UPDATED_BY", nullable = true, insertable = true, updatable = true, length = 45)
    private String updatedBy;

    @Basic
    @Column(name = "UPDATED_DATE", nullable = true, insertable = true, updatable = true)
    private Date   updatedDate;

    @Basic
    @Column(name = "PRINT_PREFERENCES", nullable = false, insertable = true, updatable = true, length = 1)
    private String printPreferences;

    @Basic
    @Column(name = "LANGUAGE_PREFERENCE", nullable = true, insertable = true, updatable = true, length = 2)
    private String languagePreferences;

    @Basic
    @Column(name = "STATE_ABBREVIATION", nullable = false, insertable = true, updatable = true, length = 2)
    private String stateAbbreviation;

    @Basic
    @Column(name = "STATE_NAME", nullable = true, insertable = true, updatable = true, length = 2)
    private String stateName;

    @Basic
    @Column(name = "PROVIDER_NAME", nullable = true, insertable = true, updatable = true, length = 45)
    private String providerName;

    @Basic
    @Column(name = "PROVIDER_IDENTIFICATION_NUMBER", nullable = true, insertable = true, updatable = true, length = 25)
    private String providerIdentificationNumber;

    @Basic
    @Column(name = "PROVIDER_CONTACT_FIRST_NAME", nullable = true, insertable = true, updatable = true)
    private String providerContactFirstName ;


    @Basic
    @Column(name = "PROVIDER_CONTACT_LAST_NAME", nullable = true, insertable = true, updatable = true)
    private String providerContactLastName ;

    @Basic
    @Column(name = "PROVIDER_CONTACT_NO", nullable = true, insertable = true, updatable = true)
    private Long   providerContactNo;

    @Basic
    @Column(name = "PROVIDER_ADDRESS_LINE_1", nullable = true, insertable = true, updatable = true, length = 30)
    private String providerAddressLine1;

    @Basic
    @Column(name = "PROVIDER_ADDRESS_LINE_2", nullable = true, insertable = true, updatable = true, length = 30)
    private String providerAddressLine2;

    @Basic
    @Column(name = "PROVIDER_CITY_OR_TOWN", nullable = true, insertable = true, updatable = true, length = 25)
    private String providerCityOrTown;

    @Basic
    @Column(name = "PROVIDER_STATE_OR_PROVINCE", nullable = true, insertable = true, updatable = true, length = 25)
    private String providerStateOrProvince;

    @Basic
    @Column(name = "PROVIDER_COUNTRY", nullable = true, insertable = true, updatable = true, length = 25)
    private String providerCountry;

    @Basic
    @Column(name = "PROVIDER_ZIP_OR_POSTAL_CODE", nullable = true, insertable = true, updatable = true, length = 25)
    private String providerZipOrPostalCode;

    @Basic
    @Column(name = "TEST_TCC", nullable = true, insertable = true, updatable = true, length = 45)
    private String testTcc ;

    @Basic
    @Column(name = "DELOITTE_TEST_TCC", nullable = true, insertable = true, updatable = true, length = 45)
    private String deloitteTestTcc ;
    /** Software id assigned to Deloitte by the IRS. */

    @Basic
    @Column(name = "SOFTWARE_ID", nullable = true, insertable = true, updatable = true, length = 45)
    private String softwareId ;


    @Basic
    @Column(name = "CITIZEN_PORTAL_UI_STATUS", nullable = true, insertable = true, updatable = true, length = 45)
    private String staticStatus;



    public SourceSystemConfigPK getId() {
        return id;
    }

    public void setId(SourceSystemConfigPK id) {
        this.id = id;
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


    public String getReturnAddressLine1() {
        return returnAddressLine1;
    }

    public void setReturnAddressLine1(String returnAddressLine1) {
        this.returnAddressLine1 = returnAddressLine1;
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


    public String getLanguagePreference() {
        return languagePreferences;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreferences = languagePreference;
    }


    public String getTransmitterControlCode() {
        return transmitterControlCode;
    }

    @Basic
    @Column(name = "TRANSMITTER_CONTROL_CODE", nullable = true, insertable = true, updatable = true, length = 45)
    public void setTransmitterControlCode(String transmitterControlCode) {
        this.transmitterControlCode = transmitterControlCode;
    }


    public String getStateAbbreviation() {return stateAbbreviation;}

    public void setStateAbbreviation(String stateAbbreviation) {this.stateAbbreviation = stateAbbreviation;}


    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }


    public String getReturnAddressLine2() {
        return returnAddressLine2;
    }

    public void setReturnAddressLine2(String returnAddressLine2) {
        this.returnAddressLine2 = returnAddressLine2;
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


    public String getStaticStatus() {
        return staticStatus;
    }


    public void setStaticStatus(String staticStatus) {
        this.staticStatus = staticStatus;
    }



    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public Integer getSourceCdId() {
        return sourceCdId;
    }

    public void setSourceCdId(Integer sourceCdId) {
        this.sourceCdId = sourceCdId;
    }

    public String getLanguagePreferences() {
        return languagePreferences;
    }

    public void setLanguagePreferences(String languagePreferences) {
        this.languagePreferences = languagePreferences;
    }

}
