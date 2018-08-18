package us.deloitteinnovation.aca.entity;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;
import us.deloitteinnovation.aca.validator.AcceptedValues;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the filer_demographics database table.
 * ..
 */
@Entity
@Table(name = "filer_demographics_staging")
@Transactional(isolation = Isolation.SERIALIZABLE)
public class FilerDemographicStagingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /* Extra columns for staging entity*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rowId;
    @Column(name = "RECORD_SOURCE")
    private String recordSource;
    @Column(name = "ROW_NUMBER")
    private Integer rowNumber;
    @NotNull
    @Column(name = "ORIG_COVERAGE_BEGIN_DATE")
    @DateTimeFormat(pattern = BatchConstants.DATE_FORMAT)
    private Date originalCoverageBeginDt;
    @NotNull
    @Column(name = "ORIG_COVERAGE_END_DATE")
    @DateTimeFormat(pattern = BatchConstants.DATE_FORMAT)
    private Date originalCoverageEndDt;
    @Digits(fraction = 0, integer = 15, message = ErrorMessageConstants.RECIPIENT_UNIQUE_ID_REQ_NUM)
    @NotNull(message = ErrorMessageConstants.RECIPIENT_UNIQUE_ID_REQ)
    @Column(name = CommonEntityConstants.RECIPIENT_UNIQUE_ID)
    private long sourceUniqueId;
    @NotNull(message = ErrorMessageConstants.RRECIPIENT_STATE_CODE_REQ)
    @Column(name = "SOURCE_CD")
    private String sourceCd;
    @Column(name = "BATCH_ID")
    private Integer batchId;
    @Transient
    private boolean active = true;
    @Column(name = CommonEntityConstants.COMMENTS)
    private String comments;
    @Column(name = CommonEntityConstants.COMM_PREFERENCE)
    private Character communicationPreference;
    @Column(name = CommonEntityConstants.CORRECTION)
    private String correction;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CORRECTION_DATE")
    private Date correctionDt;
    @Column(name = "PROGRAM_NAME")
    private String programName;
    /* Extra columns end */
    @Column(name = CommonEntityConstants.EMAIL)
    private String eMail;
    @Column(name = CommonEntityConstants.EMPLOYER_ADDRESS_LINE_1)
    private String employerAddressLine1;
    @Column(name = CommonEntityConstants.EMPLOYER_ADDRESS_LINE_2)
    private String employerAddressLine2;
    @Column(name = CommonEntityConstants.EMPLOYER_CITY_OR_TOWN)
    private String employerCityOrTown;
    @Column(name = "EMPLOYER_CONTACT_NO")
    private Long employerContactNo;
    @Column(name = CommonEntityConstants.EMPLOYER_COUNTRY)
    private String employerCountry;
    @Column(name = CommonEntityConstants.EMPLOYER_IDENTIFICATION_NUMBER)
    private String employerIdentificationNumber;
    @Column(name = CommonEntityConstants.EMPLOYER_NAME)
    private String employerName;
    @Column(name = CommonEntityConstants.EMPLOYER_STATE_OR_PROVINCE)
    private String employerStateOrProvince;
    @Column(name = CommonEntityConstants.EMPLOYER_ZIP_OR_FOREIGN_POSTAL_CODE)
    private String employerZipOrPostalCode;
    @Column(name = CommonEntityConstants.FILLER_STATUS)
    private String filerStatus;
    @Column(name = "RECIPIENT_LANGUAGE_PREFERENCE")
    private String languagePreference;
    @NotNull(message = ErrorMessageConstants.POLICY_ORIGIN_REQ_VALUE)
    @NotEmpty(message = ErrorMessageConstants.POLICY_ORIGIN_REQ_VALUE)
    @AcceptedValues(acceptValues = {"A", "B", "C", "D", "E", "F", "G"}, message = ErrorMessageConstants.POLICY_ORIGIN_REQ_VALUE)
    @Column(name = "POLICY_ORIGIN")
    private String policyOrigin;
    @Column(name = CommonEntityConstants.PROVIDER_ADDRESS_LINE_1)
    private String providerAddressLine1;
    @Column(name = CommonEntityConstants.PROVIDER_ADDRESS_LINE_2)
    private String providerAddressLine2;
    @Column(name = CommonEntityConstants.PROVIDER_CITY_OR_TOWN)
    private String providerCityOrTown;
    @Column(name = "PROVIDER_CONTACT_NO")
    private Long providerContactNo;
    @Column(name = CommonEntityConstants.PROVIDER_COUNTRY)
    private String providerCountry;
    @Column(name = CommonEntityConstants.PROVIDER_IDENTIFICATION_NUMBER)
    private String providerIdentificationNumber;
    @Column(name = CommonEntityConstants.PROVIDER_NAME)
    private String providerName;
    @Column(name = CommonEntityConstants.PROVIDER_STATE_OR_PROVINCE)
    private String providerStateOrProvince;
    @Column(name = CommonEntityConstants.PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE)
    private String providerZipOrPostalCode;
    @Column(name = CommonEntityConstants.RECIPIENT_ADDRESS_LINE_1)
    private String recipientAddressLine1;
    @Column(name = CommonEntityConstants.RECIPIENT_ADDRESS_LINE_2)
    private String recipientAddressLine2;
    @Column(name = CommonEntityConstants.RECIPIENT_CITY)
    private String recipientCity;
    @Column(name = CommonDataConstants.RECIPIENT_DOB)
    private Date recipientDob;
    @Column(name = CommonEntityConstants.RECIPIENT_FIRST_NAME)
    private String recipientFirstName;
    @Column(name = CommonEntityConstants.RECIPIENT_LAST_NAME)
    private String recipientLastName;
    @Column(name = CommonEntityConstants.RECIPIENT_MIDDLE_NAME)
    private String recipientMiddleName;
    @Column(name = CommonDataConstants.RECIPIENT_SSN)
    private String recipientSsn;
    @Column(name = CommonEntityConstants.RECIPIENT_STATE_CODE)
    private String recipientState;
    @Column(name = CommonEntityConstants.RECIPIENT_NAME_SUFFIX)
    private String recepientSuffixName;
    @Column(name = "RECIPIENT_TIN")
    private String recipientTin;
    @Column(name = CommonEntityConstants.RECIPIENT_ZIP_4)
    private String recepientZip4;
    @Column(name = CommonEntityConstants.RECIPIENT_ZIP_5)
    private String recepientZip5;
    @Column(name = CommonEntityConstants.RESPONSIBLE_PERSON_UNIQUE)
    private Long responsiblePersonUniqueId;
    @Column(name = CommonEntityConstants.POLICY_SHOP_IDENTIFIER)
    private String shopIdentifier;
    @Column(name = CommonEntityConstants.TAX_YEAR)
    private Integer taxYear;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDt;
    @Column(name = "FILER_DEMO_SEQ")
    private Long filerDemoSeq;
    @Column(name = "FORM_STATUS")
    private String formStatus;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "JAN")
    private String jan;
    @Column(name = "FEB")
    private String feb;
    @Column(name = "MAR")
    private String mar;
    @Column(name = "APR")
    private String apr;
    @Column(name = "MAY")
    private String may;
    @Column(name = "JUN")
    private String jun;
    @Column(name = "JUL")
    private String jul;
    @Column(name = "AUG")
    private String aug;
    @Column(name = "SEP")
    private String sep;
    @Column(name = "OCT")
    private String oct;
    @Column(name = "NOV")
    private String nov;
    @Column(name = "DEC")
    private String dec;
    @Column(name = "MAILED_FORM")
    private String mailedForm;
    @Column(name = "IRS_TRANSMISSION_STATUS_CD")
    private String irsTransmissionStatusCd;
    /*	@OneToOne(cascade = CascadeType.ALL)
        @JoinColumns({
                @JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD", insertable=false, updatable=false)
        })*/
    @Transient
    private SourceSystemConfig sourcesystemconfig;
    @Transient
    private int lineNumber;
    @Transient
    private boolean isUserFound = false;
    @Column(name = "CASE_APPLICATION_ID")
    private String caseApplicationId;

    public FilerDemographicStagingEntity() {
    }

    public Integer getRowId() {
        return rowId;
    }


    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }


    public long getSourceUniqueId() {
        return sourceUniqueId;
    }

    public void setSourceUniqueId(long sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public Long getFilerDemoSeq() {
        return filerDemoSeq;
    }


    public void setFilerDemoSeq(Long filerDemoSeq) {
        this.filerDemoSeq = filerDemoSeq;
    }

    public String getComments() {
        return this.comments;
    }


    public void setComments(String comments) {
        this.comments = comments;
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

    public Character getCommunicationPreference() {
        return this.communicationPreference;
    }

    public void setCommunicationPreference(Character communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public String getCorrection() {
        return this.correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public Date getCorrectionDt() {
        return this.correctionDt;
    }

    public void setCorrectionDt(Date correctionDt) {
        this.correctionDt = correctionDt;
    }

    public String getEMail() {
        return this.eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getEmployerAddressLine1() {
        return this.employerAddressLine1;
    }

    public void setEmployerAddressLine1(String employerAddressLine1) {
        this.employerAddressLine1 = employerAddressLine1;
    }

    public String getEmployerAddressLine2() {
        return this.employerAddressLine2;
    }

    public void setEmployerAddressLine2(String employerAddressLine2) {
        this.employerAddressLine2 = employerAddressLine2;
    }

    public String getEmployerCityOrTown() {
        return this.employerCityOrTown;
    }

    public void setEmployerCityOrTown(String employerCityOrTown) {
        this.employerCityOrTown = employerCityOrTown;
    }

    public Long getEmployerContactNo() {
        return this.employerContactNo;
    }

    public void setEmployerContactNo(Long employerContactNo) {
        this.employerContactNo = employerContactNo;
    }

    public String getEmployerCountry() {
        return this.employerCountry;
    }

    public void setEmployerCountry(String employerCountry) {
        this.employerCountry = employerCountry;
    }

    public String getEmployerIdentificationNumber() {
        return this.employerIdentificationNumber;
    }

    public void setEmployerIdentificationNumber(String employerIdentificationNumber) {
        this.employerIdentificationNumber = employerIdentificationNumber;
    }

    public String getEmployerName() {
        return this.employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerStateOrProvince() {
        return this.employerStateOrProvince;
    }

    public void setEmployerStateOrProvince(String employerStateOrProvince) {
        this.employerStateOrProvince = employerStateOrProvince;
    }


    public String getFilerStatus() {
        return this.filerStatus;
    }

    public void setFilerStatus(String filerStatus) {
        this.filerStatus = filerStatus;
    }

    public String getLanguagePreference() {
        return this.languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public String getPolicyOrigin() {
        return this.policyOrigin;
    }

    public void setPolicyOrigin(String policyOrigin) {
        this.policyOrigin = policyOrigin;
    }

    public String getProviderAddressLine1() {
        return this.providerAddressLine1;
    }

    public void setProviderAddressLine1(String providerAddressLine1) {
        this.providerAddressLine1 = providerAddressLine1;
    }

    public String getProviderAddressLine2() {
        return this.providerAddressLine2;
    }

    public void setProviderAddressLine2(String providerAddressLine2) {
        this.providerAddressLine2 = providerAddressLine2;
    }

    public String getProviderCityOrTown() {
        return this.providerCityOrTown;
    }

    public void setProviderCityOrTown(String providerCityOrTown) {
        this.providerCityOrTown = providerCityOrTown;
    }

    public Long getProviderContactNo() {
        return this.providerContactNo;
    }

    public void setProviderContactNo(Long providerContactNo) {
        this.providerContactNo = providerContactNo;
    }

    public String getProviderCountry() {
        return this.providerCountry;
    }

    public void setProviderCountry(String providerCountry) {
        this.providerCountry = providerCountry;
    }

    public String getProviderIdentificationNumber() {
        return this.providerIdentificationNumber;
    }

    public void setProviderIdentificationNumber(String providerIdentificationNumber) {
        this.providerIdentificationNumber = providerIdentificationNumber;
    }

    public String getProviderName() {
        return this.providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderStateOrProvince() {
        return this.providerStateOrProvince;
    }

    public void setProviderStateOrProvince(String providerStateOrProvince) {
        this.providerStateOrProvince = providerStateOrProvince;
    }

    public String getProviderZipOrPostalCode() {
        return this.providerZipOrPostalCode;
    }

    public void setProviderZipOrPostalCode(String providerZipOrPostalCode) {
        this.providerZipOrPostalCode = providerZipOrPostalCode;
    }

    public String getRecipientAddressLine1() {
        return this.recipientAddressLine1;
    }

    public void setRecipientAddressLine1(String recipientAddressLine1) {
        this.recipientAddressLine1 = recipientAddressLine1;
    }

    public String getRecipientAddressLine2() {
        return this.recipientAddressLine2;
    }

    public void setRecipientAddressLine2(String recipientAddressLine2) {
        this.recipientAddressLine2 = recipientAddressLine2;
    }

    public String getRecipientCity() {
        return this.recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public Date getRecipientDob() {
        return this.recipientDob;
    }

    public void setRecipientDob(Date recipientDob) {
        this.recipientDob = recipientDob;
    }

    public String getRecipientFirstName() {
        return this.recipientFirstName;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }

    public String getRecipientLastName() {
        return this.recipientLastName;
    }

    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
    }

    public String getRecipientMiddleName() {
        return this.recipientMiddleName;
    }

    public void setRecipientMiddleName(String recipientMiddleName) {
        this.recipientMiddleName = recipientMiddleName;
    }

    public String getRecipientSsn() {
        return this.recipientSsn;
    }

    public void setRecipientSsn(String recipientSsn) {
        this.recipientSsn = recipientSsn;
    }

    public String getRecipientState() {
        return this.recipientState;
    }

    public void setRecipientState(String recipientState) {
        this.recipientState = recipientState;
    }

    public String getRecepientSuffixName() {
        return this.recepientSuffixName;
    }

    public void setRecepientSuffixName(String recipientSuffixName) {
        this.recepientSuffixName = recipientSuffixName;
    }

    public String getRecipientTin() {
        return this.recipientTin;
    }

    public void setRecipientTin(String recipientTin) {
        this.recipientTin = recipientTin;
    }

    public String getRecipientZip4() {
        return this.recepientZip4;
    }

    public void setRecipientZip4(String recipientZip4) {
        this.recepientZip4 = recipientZip4;
    }

    public String getRecipientZip5() {
        return this.recepientZip5;
    }

    public Long getResponsiblePersonUniqueId() {
        return this.responsiblePersonUniqueId;
    }

    public void setResponsiblePersonUniqueId(Long responsiblePersonUniqueId) {
        this.responsiblePersonUniqueId = responsiblePersonUniqueId;
    }

    public String getShopIdentifier() {
        return this.shopIdentifier;
    }

    public void setShopIdentifier(String shopIdentifier) {
        this.shopIdentifier = shopIdentifier;
    }

    public Integer getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(Integer taxYear) {
        this.taxYear = taxYear;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDt() {
        return this.updatedDt;
    }


    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getEmployerZipOrPostalCode() {
        return employerZipOrPostalCode;
    }

    public void setEmployerZipOrPostalCode(String employerZipOrPostalCode) {
        this.employerZipOrPostalCode = employerZipOrPostalCode;
    }

    /**
     * @return the eMail
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * @param eMail the eMail to set
     */
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }


    public boolean isUserFound() {
        return isUserFound;
    }


    public void setIsUserFound(boolean isUserFound) {
        this.isUserFound = isUserFound;
    }


    public SourceSystemConfig getSourcesystemconfig() {
        return sourcesystemconfig;
    }

    public void setSourcesystemconfig(SourceSystemConfig sourcesystemconfig) {
        this.sourcesystemconfig = sourcesystemconfig;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getFeb() {
        return feb;
    }

    public void setFeb(String feb) {
        this.feb = feb;
    }

    public String getMar() {
        return mar;
    }

    public void setMar(String mar) {
        this.mar = mar;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getJun() {
        return jun;
    }

    public void setJun(String jun) {
        this.jun = jun;
    }

    public String getJul() {
        return jul;
    }

    public void setJul(String jul) {
        this.jul = jul;
    }

    public String getAug() {
        return aug;
    }

    public void setAug(String aug) {
        this.aug = aug;
    }

    public String getSep() {
        return sep;
    }

    public void setSep(String sep) {
        this.sep = sep;
    }

    public String getOct() {
        return oct;
    }

    public void setOct(String oct) {
        this.oct = oct;
    }

    public String getNov() {
        return nov;
    }

    public void setNov(String nov) {
        this.nov = nov;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }


    public String getMay() {
        return may;
    }

    public void setMay(String may) {
        this.may = may;
    }

    public String getMailedForm() {
        return mailedForm;
    }

    public void setMailedForm(String mailedForm) {
        this.mailedForm = mailedForm;
    }

    public String getIrsTransmissionStatusCd() {
        return irsTransmissionStatusCd;
    }

    public void setIrsTransmissionStatusCd(String irsTransmissionStatusCd) {
        this.irsTransmissionStatusCd = irsTransmissionStatusCd;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(String recordSource) {
        this.recordSource = recordSource;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Date getOriginalCoverageBeginDt() {
        return originalCoverageBeginDt;
    }

    public void setOriginalCoverageBeginDt(Date originalCoverageBeginDt) {
        this.originalCoverageBeginDt = originalCoverageBeginDt;
    }

    public Date getOriginalCoverageEndDt() {
        return originalCoverageEndDt;
    }

    public void setOriginalCoverageEndDt(Date originalCoverageEndDt) {
        this.originalCoverageEndDt = originalCoverageEndDt;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCaseApplicationId() {
        return caseApplicationId;
    }

    public void setCaseApplicationId(String caseApplicationId) {
        this.caseApplicationId = caseApplicationId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }


}