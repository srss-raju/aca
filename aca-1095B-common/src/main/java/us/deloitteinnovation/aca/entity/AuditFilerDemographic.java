package us.deloitteinnovation.aca.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by yaojia on 11/11/2016.
 */
@Entity
@Table(name="A_FILER_DEMOGRAPHICS")
public class AuditFilerDemographic {

    @Column(name = "SOURCE_UNIQUE_ID")
    private Long sourceUniqueId;
    @Column(name = "SOURCE_CD")
    private String sourceCd;
    @Column(name = "A_SEQ_NO")
    @Id
    private Integer id;
    @Column(name = "BATCH_ID")
    private Integer batchId;
    @Column(name = "TAX_YEAR")
    private Integer taxYear;
    @Column(name = "RECIPIENT_FIRST_NAME")
    private String recipientFirstName;
    @Column(name = "RECIPIENT_MIDDLE_NAME")
    private String recipientMiddleName;
    @Column(name = "RECIPIENT_LAST_NAME")
    private String recipientLastName;
    @Column(name = "RECIPIENT_SUFFIX_NAME")
    private String recipientSuffixName;
    @Column(name = "RECIPIENT_SSN")
    private String recipientSsn;
    @Column(name = "RECIPIENT_TIN")
    private String recipientTin;
    @Column(name = "RECIPIENT_DOB")
    private Date recipientDob;
    @Column(name = "RECIPIENT_ADDRESS_LINE_1")
    private String recipientAddressLine1;
    @Column(name = "RECIPIENT_ADDRESS_LINE_2")
    private String recipientAddressLine2;
    @Column(name = "RECIPIENT_CITY")
    private String recipientCity;
    @Column(name = "RECIPIENT_STATE")
    private String recipientState;
    @Column(name = "RECIPIENT_ZIP_5")
    private String recipientZip5;
    @Column(name = "RECIPIENT_ZIP_4")
    private String recipientZip4;
    @Column(name = "RECIPIENT_E_MAIL")
    private String recipientEmail;
    @Column(name = "RECIPIENT_LANGUAGE_PREFERENCE")
    private String recipientLanguagePreference;
    @Column(name = "POLICY_ORIGIN")
    private char policyOrigin;
    @Column(name = "SHOP_IDENTIFIER")
    private String shopIdentifier;
    @Column(name = "EMPLOYER_NAME")
    private String employerName;
    @Column(name = "EMPLOYER_IDENTIFICATION_NUMBER")
    private String employerIdentificationNumber;
    @Column(name = "EMPLOYER_CONTACT_NO")
    private Long employerContactNo;
    @Column(name = "EMPLOYER_ADDRESS_LINE_1")
    private String employerAddressLine1;
    @Column(name = "EMPLOYER_ADDRESS_LINE_2")
    private String employerAddressLine2;
    @Column(name = "EMPLOYER_CITY_OR_TOWN")
    private String employerCityOrTown;
    @Column(name = "EMPLOYER_STATE_OR_PROVINCE")
    private String employerStateOrProvince;
    @Column(name = "EMPLOYER_COUNTRY")
    private String employerCountry;
    @Column(name = "EMPLOYER_ZIP_OR_POSTAL_CODE")
    private String employerZipOrPostalCode;
    @Column(name = "PROVIDER_NAME")
    private String providerName;
    @Column(name = "PROVIDER_IDENTIFICATION_NUMBER")
    private String providerIdentificationNumber;
    @Column(name = "PROVIDER_CONTACT_NO")
    private Long providerContactNo;
    @Column(name = "PROVIDER_ADDRESS_LINE_1")
    private String providerAddressLine1;
    @Column(name = "PROVIDER_ADDRESS_LINE_2")
    private String providerAddressLine2;
    @Column(name = "PROVIDER_CITY_OR_TOWN")
    private String providerCityOrTown;
    @Column(name = "PROVIDER_STATE_OR_PROVINCE")
    private String providerStateOrProvince;
    @Column(name = "PROVIDER_COUNTRY")
    private String providerCountry;
    @Column(name = "PROVIDER_ZIP_OR_POSTAL_CODE")
    private String providerZipOrPostalCode;
    @Column(name = "FILER_STATUS")
    private char filerStatus;
    @Column(name = "COMMUNICATION_PREFERENCE")
    private char communicationPreference;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Column(name = "UPDATED_DATE")
    private Timestamp updatedDate;
    @Column(name = "CORRECTION_DATE")
    private Timestamp correctionDate;
    @Column(name = "CORRECTION_CODE")
    private String correctionCode;
    @Column(name = "JAN")
    private char jan;
    @Column(name = "FEB")
    private char feb;
    @Column(name = "MAR")
    private char mar;
    @Column(name = "APR")
    private char apr;
    @Column(name = "MAY")
    private char may;
    @Column(name = "JUN")
    private char jun;
    @Column(name = "JUL")
    private char jul;
    @Column(name = "AUG")
    private char aug;
    @Column(name = "SEP")
    private char sep;
    @Column(name = "OCT")
    private char oct;
    @Column(name = "NOV")
    private char nov;
    @Column(name = "DEC")
    private char dec;
    @Column(name = "FORM_STATUS")
    private String formStatus;
    @Column(name = "FILER_DEMO_SEQ")
    private Long filerdemoSeq;
    @Column(name = "RESPONSIBLE_PERSON_UNIQUE_ID")
    private Long responsiblePersonUniqueId;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "MAILED_FORM")
    private String mailedForm;
    @Column(name = "CHANGEDATE")
    private Timestamp changedate;
    @Column(name = "ACTIVITY")
    private String activity;
    @Column(name = "IRS_TRANSMISSION_STATUS_CD")
    private String irsTransmissionStatusCd;
    @Column(name = "RECORD_CREATED_DATE")
    private Timestamp recordCreatedDate;
    @Column(name = "CORRECTION_INDICATOR")
    private Integer correctionIndicator;
    @Column(name = "RECORD_VERSION")
    private Integer recordVersion;


    /* Getters and setters */
    public Long getSourceUniqueId() {
        return sourceUniqueId;
    }

    public void setSourceUniqueId(Long sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(Integer taxYear) {
        this.taxYear = taxYear;
    }

    public String getRecipientFirstName() {
        return recipientFirstName;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }

    public String getRecipientMiddleName() {
        return recipientMiddleName;
    }

    public void setRecipientMiddleName(String recipientMiddleName) {
        this.recipientMiddleName = recipientMiddleName;
    }

    public String getRecipientLastName() {
        return recipientLastName;
    }

    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
    }

    public String getRecipientSuffixName() {
        return recipientSuffixName;
    }

    public void setRecipientSuffixName(String recipientSuffixName) {
        this.recipientSuffixName = recipientSuffixName;
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

    public Date getRecipientDob() {
        return recipientDob;
    }

    public void setRecipientDob(Date recipientDob) {
        this.recipientDob = recipientDob;
    }

    public String getRecipientAddressLine1() {
        return recipientAddressLine1;
    }

    public void setRecipientAddressLine1(String recipientAddressLine1) {
        this.recipientAddressLine1 = recipientAddressLine1;
    }

    public String getRecipientAddressLine2() {
        return recipientAddressLine2;
    }

    public void setRecipientAddressLine2(String recipientAddressLine2) {
        this.recipientAddressLine2 = recipientAddressLine2;
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public String getRecipientState() {
        return recipientState;
    }

    public void setRecipientState(String recipientState) {
        this.recipientState = recipientState;
    }

    public String getRecipientZip5() {
        return recipientZip5;
    }

    public void setRecipientZip5(String recipientZip5) {
        this.recipientZip5 = recipientZip5;
    }

    public String getRecipientZip4() {
        return recipientZip4;
    }

    public void setRecipientZip4(String recipientZip4) {
        this.recipientZip4 = recipientZip4;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientLanguagePreference() {
        return recipientLanguagePreference;
    }

    public void setRecipientLanguagePreference(String recipientLanguagePreference) {
        this.recipientLanguagePreference = recipientLanguagePreference;
    }

    public char getPolicyOrigin() {
        return policyOrigin;
    }

    public void setPolicyOrigin(char policyOrigin) {
        this.policyOrigin = policyOrigin;
    }

    public String getShopIdentifier() {
        return shopIdentifier;
    }

    public void setShopIdentifier(String shopIdentifier) {
        this.shopIdentifier = shopIdentifier;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerIdentificationNumber() {
        return employerIdentificationNumber;
    }

    public void setEmployerIdentificationNumber(String employerIdentificationNumber) {
        this.employerIdentificationNumber = employerIdentificationNumber;
    }

    public Long getEmployerContactNo() {
        return employerContactNo;
    }

    public void setEmployerContactNo(Long employerContactNo) {
        this.employerContactNo = employerContactNo;
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

    public String getEmployerCountry() {
        return employerCountry;
    }

    public void setEmployerCountry(String employerCountry) {
        this.employerCountry = employerCountry;
    }

    public String getEmployerZipOrPostalCode() {
        return employerZipOrPostalCode;
    }

    public void setEmployerZipOrPostalCode(String employerZipOrPostalCode) {
        this.employerZipOrPostalCode = employerZipOrPostalCode;
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

    public char getFilerStatus() {
        return filerStatus;
    }

    public void setFilerStatus(char filerStatus) {
        this.filerStatus = filerStatus;
    }

    public char getCommunicationPreference() {
        return communicationPreference;
    }

    public void setCommunicationPreference(char communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Timestamp getCorrectionDate() {
        return correctionDate;
    }

    public void setCorrectionDate(Timestamp correctionDate) {
        this.correctionDate = correctionDate;
    }

    public String getCorrectionCode() {
        return correctionCode;
    }

    public void setCorrectionCode(String correctionCode) {
        this.correctionCode = correctionCode;
    }

    public char getJan() {
        return jan;
    }

    public void setJan(char jan) {
        this.jan = jan;
    }

    public char getFeb() {
        return feb;
    }

    public void setFeb(char feb) {
        this.feb = feb;
    }

    public char getMar() {
        return mar;
    }

    public void setMar(char mar) {
        this.mar = mar;
    }

    public char getApr() {
        return apr;
    }

    public void setApr(char apr) {
        this.apr = apr;
    }

    public char getMay() {
        return may;
    }

    public void setMay(char may) {
        this.may = may;
    }

    public char getJun() {
        return jun;
    }

    public void setJun(char jun) {
        this.jun = jun;
    }

    public char getJul() {
        return jul;
    }

    public void setJul(char jul) {
        this.jul = jul;
    }

    public char getAug() {
        return aug;
    }

    public void setAug(char aug) {
        this.aug = aug;
    }

    public char getSep() {
        return sep;
    }

    public void setSep(char sep) {
        this.sep = sep;
    }

    public char getOct() {
        return oct;
    }

    public void setOct(char oct) {
        this.oct = oct;
    }

    public char getNov() {
        return nov;
    }

    public void setNov(char nov) {
        this.nov = nov;
    }

    public char getDec() {
        return dec;
    }

    public void setDec(char dec) {
        this.dec = dec;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public Long getFilerdemoSeq() {
        return filerdemoSeq;
    }

    public void setFilerdemoSeq(Long filerdemoSeq) {
        this.filerdemoSeq = filerdemoSeq;
    }

    public Long getResponsiblePersonUniqueId() {
        return responsiblePersonUniqueId;
    }

    public void setResponsiblePersonUniqueId(Long responsiblePersonUniqueId) {
        this.responsiblePersonUniqueId = responsiblePersonUniqueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMailedForm() {
        return mailedForm;
    }

    public void setMailedForm(String mailedForm) {
        this.mailedForm = mailedForm;
    }

    public Timestamp getChangedate() {
        return changedate;
    }

    public void setChangedate(Timestamp changedate) {
        this.changedate = changedate;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getIrsTransmissionStatusCd() {
        return irsTransmissionStatusCd;
    }

    public void setIrsTransmissionStatusCd(String irsTransmissionStatusCd) {
        this.irsTransmissionStatusCd = irsTransmissionStatusCd;
    }

    public Timestamp getRecordCreatedDate() {
        return recordCreatedDate;
    }

    public void setRecordCreatedDate(Timestamp recordCreatedDate) {
        this.recordCreatedDate = recordCreatedDate;
    }

    public Integer getCorrectionIndicator() {
        return correctionIndicator;
    }

    public void setCorrectionIndicator(Integer correctionIndicator) {
        this.correctionIndicator = correctionIndicator;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }
}
