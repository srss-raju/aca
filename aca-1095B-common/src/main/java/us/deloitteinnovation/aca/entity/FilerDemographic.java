package us.deloitteinnovation.aca.entity;


import org.hibernate.validator.constraints.NotEmpty;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;
import us.deloitteinnovation.aca.validator.AcceptedChar;
import us.deloitteinnovation.aca.validator.AcceptedValues;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the filer_demographics database table.
 *..
 */
@Entity
@Table(name="filer_demographics")
public class FilerDemographic implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FilerDemographicPK id;

	@Column(name = CommonEntityConstants.COMMENTS)
	private String comments;

	@NotNull(message = ErrorMessageConstants.COMM_PREFERENCE_REQ)
	@NotEmpty(message = ErrorMessageConstants.COMM_PREFERENCE_REQ)
	@AcceptedValues(acceptValues = {"E", "P","B"}, message = ErrorMessageConstants.COMM_PREFERENCE_VALUE)
	@Column(name= CommonEntityConstants.COMM_PREFERENCE)
	private Character communicationPreference;

	@NotNull(message = ErrorMessageConstants.CORRECTION_REQ)
	@NotEmpty(message = ErrorMessageConstants.CORRECTION_REQ)
	@AcceptedValues(acceptValues = {"V", "O","C"}, message = ErrorMessageConstants.CORRECTION_REQ_VALUE)
	@Column(name=CommonEntityConstants.CORRECTION)
	private String correction;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CORRECTION_DATE")
	private Date correctionDt;

	@Pattern(regexp = "[a-zA-Z0-9@. ]{0,30}", message = ErrorMessageConstants.EMAIL_VALUE)
	@Column(name=CommonEntityConstants.EMAIL)
	private String eMail;

	@Pattern(regexp = "[a-zA-Z0-9 ]{0,30}", message = ErrorMessageConstants.EMPLOYER_ADDRESS_LINE_1_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_ADDRESS_LINE_1)
	private String employerAddressLine1;

	@Pattern(regexp = "[a-zA-Z0-9 ]{0,30}", message = ErrorMessageConstants.EMPLOYER_ADDRESS_LINE_2_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_ADDRESS_LINE_2)
	private String employerAddressLine2;

	@Pattern(regexp = "[a-zA-Z ]{0,25}", message = ErrorMessageConstants.EMPLOYER_CITY_OR_TOWN_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_CITY_OR_TOWN)
	private String employerCityOrTown;

	@Column(name="EMPLOYER_CONTACT_NO")
	private Long employerContactNo;

	@Pattern(regexp = "[a-zA-Z ]{0,25}", message = ErrorMessageConstants.EMPLOYER_COUNTRY_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_COUNTRY)
	private String employerCountry;

	//@LongLength(min = 0, max = 25, message = ErrorMessageConstants.EMPLOYER_IDENTIFICATION_NUMBER_REQ_VALUE)
	@Digits(fraction = 0, integer = 25, message = ErrorMessageConstants.EMPLOYER_IDENTIFICATION_NUMBER_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_IDENTIFICATION_NUMBER)
	private String employerIdentificationNumber;

	@Pattern(regexp = "[a-zA-Z0-9 ]{0,30}", message = ErrorMessageConstants.EMPLOYER_NAME_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_NAME)
	private String employerName;

	@Pattern(regexp = "[a-zA-Z ]{0,25}", message = ErrorMessageConstants.EMPLOYER_STATE_OR_PROVINCE_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_STATE_OR_PROVINCE)
	private String employerStateOrProvince;

	@Digits(fraction = 0, integer = 25, message = ErrorMessageConstants.EMPLOYER_ZIP_OR_FOREIGN_POSTAL_CODE_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_ZIP_OR_FOREIGN_POSTAL_CODE)
	private String employerZipOrPostalCode;

	@NotNull(message = ErrorMessageConstants.FILLER_STATUS_REQ)
	@NotEmpty(message = ErrorMessageConstants.FILLER_STATUS_REQ)
	@AcceptedChar(acceptValues = {'R', 'N', 'C'}, message = ErrorMessageConstants.FILLER_STATUS_VALUE)
	@Column(name=CommonEntityConstants.FILLER_STATUS,  columnDefinition = "char")
	private Character filerStatus;

	@Column(name="RECIPIENT_LANGUAGE_PREFERENCE")
	private String languagePreference;

	@NotNull(message = ErrorMessageConstants.POLICY_ORIGIN_REQ)
    @NotEmpty(message = ErrorMessageConstants.POLICY_ORIGIN_REQ)
    @AcceptedValues(acceptValues = {"A", "B", "C", "D", "E", "F"}, message = ErrorMessageConstants.POLICY_ORIGIN_REQ_VALUE)
    @Column(name=CommonEntityConstants.POLICY_ORIGIN,  columnDefinition = "char")
	private Character policyOrigin;

	@Pattern(regexp = "[a-zA-Z0-9 ]{0,30}", message = ErrorMessageConstants.PROVIDER_ADDRESS_LINE_1_REQ_VALUE)
    @Column(name=CommonEntityConstants.PROVIDER_ADDRESS_LINE_1)
	private String providerAddressLine1;

	@Pattern(regexp = "[a-zA-Z0-9 ]{0,30}", message = ErrorMessageConstants.PROVIDER_ADDRESS_LINE_2_REQ_VALUE)
    @Column(name=CommonEntityConstants.PROVIDER_ADDRESS_LINE_2)
	private String providerAddressLine2;

	@Pattern(regexp = "[a-zA-Z ]{0,25}", message = ErrorMessageConstants.PROVIDER_CITY_OR_TOWN_REQ_VALUE)
    @Column(name=CommonEntityConstants.PROVIDER_CITY_OR_TOWN)
	private String providerCityOrTown;

	@Column(name="PROVIDER_CONTACT_NO")
	private Long providerContactNo;

	@Pattern(regexp = "[a-zA-Z ]{0,25}", message = ErrorMessageConstants.PROVIDER_COUNTRY_REQ_VALUE)
    @Column(name=CommonEntityConstants.PROVIDER_COUNTRY)
	private String providerCountry;

	//@LongLength(min = 0, max = 25, message = ErrorMessageConstants.PROVIDER_IDENTIFICATION_NUMBER_REQ_VALUE)
    @Digits(fraction = 0, integer = 25, message = ErrorMessageConstants.PROVIDER_IDENTIFICATION_NUMBER_REQ_VALUE)
    @Column(name=CommonEntityConstants.PROVIDER_IDENTIFICATION_NUMBER)
	private String providerIdentificationNumber;

	@Pattern(regexp = "[a-zA-Z0-9 ]{0,25}", message = ErrorMessageConstants.PROVIDER_NAME_REQ_VALUE)
    @Column(name=CommonEntityConstants.PROVIDER_NAME)
	private String providerName;

	@Pattern(regexp = "[a-zA-Z ]{0,25}", message = ErrorMessageConstants.PROVIDER_STATE_OR_PROVINCE_REQ_VALUE)
    @Column(name=CommonEntityConstants.PROVIDER_STATE_OR_PROVINCE)
	private String providerStateOrProvince;

	//@IntegerLength(min = 0, max = 25, message = ErrorMessageConstants.PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE_REQ_VALUE)
    @Digits(fraction = 0, integer = 25, message = ErrorMessageConstants.PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE_REQ_VALUE)
    @Column(name=CommonEntityConstants.PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE)
	private String providerZipOrPostalCode;

	@NotNull(message = ErrorMessageConstants.RECIPIENT_ADDRESS_LINE_1_REQ)
    @NotEmpty(message = ErrorMessageConstants.RECIPIENT_ADDRESS_LINE_1_REQ)
    @Pattern(regexp = "[a-zA-Z0-9 ]{1,30}", message = ErrorMessageConstants.RECIPIENT_ADDRESS_LINE_1_REQ_VALUE)
    @Column(name=CommonEntityConstants.RECIPIENT_ADDRESS_LINE_1)
	private String recepientAddressLine1;

	@Pattern(regexp = "[a-zA-Z0-9 ]{0,30}", message = ErrorMessageConstants.RECIPIENT_ADDRESS_LINE_2_REQ_VALUE)
    @Column(name=CommonEntityConstants.RECIPIENT_ADDRESS_LINE_2)
	private String recepientAddressLine2;

	@NotNull(message = ErrorMessageConstants.RECIPIENT_CITY_REQ)
    @NotEmpty(message = ErrorMessageConstants.RECIPIENT_CITY_REQ)
    @Pattern(regexp = "[a-zA-Z ]{1,25}", message = ErrorMessageConstants.RECIPIENT_CITY_REQ_VALUE)
    @Column(name=CommonEntityConstants.RECIPIENT_CITY)
	private String recepientCity;

	@NotNull(message = ErrorMessageConstants.RECIPIENT_DOB_REQ)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name= CommonDataConstants.RECIPIENT_DOB)
	private Date recepientDob;

	@NotNull(message = ErrorMessageConstants.RECIPIENT_FIRST_NAME_REQ)
    @NotEmpty(message = ErrorMessageConstants.RECIPIENT_FIRST_NAME_REQ)
    @Pattern(regexp = "[a-zA-Z ]{3,30}", message = ErrorMessageConstants.RECIPIENT_FIRST_NAME_REQ_VALUE)
    @Column(name=CommonEntityConstants.RECIPIENT_FIRST_NAME)
	private String recepientFirstName;

	@NotNull(message = ErrorMessageConstants.RECIPIENT_LAST_NAME_REQ)
    @NotEmpty(message = "RECIPIENT_LAST_NAME is required field.")
    @Pattern(regexp = "[a-zA-Z ]{3,30}", message = ErrorMessageConstants.RECIPIENT_LAST_NAME_REQ_VALUE)
    @Column(name=CommonEntityConstants.RECIPIENT_LAST_NAME)
	private String recepientLastName;

	@Pattern(regexp = "[a-zA-Z ]{0,30}", message = ErrorMessageConstants.RECIPIENT_MIDDLE_NAME_REQ_VALUE)
    @Column(name=CommonEntityConstants.RECIPIENT_MIDDLE_NAME)
	private String recepientMiddleName;

	@NotNull(message = ErrorMessageConstants.RECIPIENT_SSN_REQ)
    @NotEmpty(message = ErrorMessageConstants.RECIPIENT_SSN_REQ)
    @Pattern(regexp = "([1-57-8][0-9]{2}|0([1-9][0-9]|[0-9][1-9])|6([0-57-9][0-9]|[0-9][0-57-9]))([1-9][0-9]|[0-9][1-9])([1-9]\\d{3}|\\d[1-9]\\d{2}|\\d{2}[1-9]\\d|\\d{3}[1-9])", message = ErrorMessageConstants.RECIPIENT_SSN_REQ_VALUE)
    @Column(name=CommonDataConstants.RECIPIENT_SSN)
	private String recepientSsn;

	@NotNull(message = ErrorMessageConstants.RRECIPIENT_STATE_CODE_REQ)
    @NotEmpty(message = ErrorMessageConstants.RRECIPIENT_STATE_CODE_REQ)
    @Pattern(regexp = "[a-zA-Z ]{2}", message = ErrorMessageConstants.RECIPIENT_STATE_CODE_REQ_VALUE)
    @Column(name=CommonEntityConstants.RECIPIENT_STATE_CODE)
	private String recepientState;

	@Pattern(regexp = "[a-zA-Z0-9 ]{0,4}", message = ErrorMessageConstants.RECIPIENT_NAME_SUFFIX_REQ_VALUE)
    @Column(name=CommonEntityConstants.RECIPIENT_NAME_SUFFIX)
	private String recepientSuffixName;

	@Column(name="RECIPIENT_TIN")
	private String recepientTin;

	//@IntegerLength(min = 4, max = 4, message = ErrorMessageConstants.RECIPIENT_ZIP_4_REQ_VALUE)
    @Digits(fraction = 0, integer = 4, message = ErrorMessageConstants.RECIPIENT_ZIP_4_REQ_VALUE)
    @Column(name=CommonEntityConstants.RECIPIENT_ZIP_4)
	private String recepientZip4;

	@NotNull(message = ErrorMessageConstants.RECIPIENT_ZIP_5_REQ)
    //@IntegerLength(min = 5, max = 5, message = ErrorMessageConstants.RECIPIENT_ZIP_5_REQ_VALUE)
    @Digits(fraction = 0, integer = 5, message = ErrorMessageConstants.RECIPIENT_ZIP_5_REQ_VALUE)
    @Column(name=CommonEntityConstants.RECIPIENT_ZIP_5)
	private String recepientZip5;

	//@LongLength(min = 0, max = 15, message = ErrorMessageConstants.RESPONSIBLE_PERSON_UNIQUE_REQ_VALUE)
    @Digits(fraction = 0, integer = 15, message = ErrorMessageConstants.RESPONSIBLE_PERSON_UNIQUE_REQ_VALUE)
    @Column(name=CommonEntityConstants.RESPONSIBLE_PERSON_UNIQUE)
	private Long responsiblePersonUniqueId;

	@Pattern(regexp = "[a-zA-Z0-9 ]{0,30}", message = ErrorMessageConstants.POLICY_SHOP_IDENTIFIER_REQ_VALUE)
	@Column(name=CommonEntityConstants.POLICY_SHOP_IDENTIFIER)
	private String shopIdentifier;

	/*//@IntegerLength(min = 4, max = 4, message = ErrorMessageConstants.TAX_YEAR_LENGTH_LIMIT)
	@NotNull(message = ErrorMessageConstants.TAX_YEAR_REQ)
    @Digits(fraction = 0, integer = 4, message = ErrorMessageConstants.TAX_YEAR_REQ_NUM)
    @Column(name=CommonEntityConstants.TAX_YEAR)
	private Integer taxYear;*/

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDt;

	@Column(name = "FILER_DEMO_SEQ")
	private Long filerDemoSeq;

	@Column(name = "FORM_STATUS")
	private String formStatus;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "JAN")
	private Character jan;
	@Column(name = "FEB")
	private Character feb;
	@Column(name = "MAR")
	private Character mar;
	@Column(name = "APR")
	private Character apr;
	@Column(name = "MAY")
	private Character may;
	@Column(name = "JUN")
	private Character jun;
	@Column(name = "JUL")
	private Character jul;
	@Column(name = "AUG")
	private Character aug;
	@Column(name = "SEP")
	private Character sep;
	@Column(name = "OCT")
	private Character oct;
	@Column(name = "NOV")
	private Character nov;
	@Column(name = "DEC")
	private Character dec;



	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumns({
			@JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD"),
			@JoinColumn(name="SOURCE_UNIQUE_ID", referencedColumnName="SOURCE_UNIQUE_ID"),
			@JoinColumn(name="TAX_YEAR", referencedColumnName="TAX_YEAR")
	})
	private List<PrintDetail> printDetail;


	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="BATCH_ID")
	private BatchInfo batchInfo;
	@Column(name="MAILED_FORM")
	private String mailedForm;
/*	@OneToOne(cascade = CascadeType.ALL,mappedBy = "filerDemographic")
	@JoinColumns({
			@JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD"),
			@JoinColumn(name="SOURCE_UNIQUE_ID", referencedColumnName="SOURCE_UNIQUE_ID")
	})
	private FilerCoverage filerCoverage;*/
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumns({
			@JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD"),
			@JoinColumn(name="SOURCE_UNIQUE_ID", referencedColumnName="SOURCE_UNIQUE_ID"),
			@JoinColumn(name="TAX_YEAR", referencedColumnName="TAX_YEAR")
	})
	private List<FilerCoverage> filerCoverages;
	//added for audit Mapping
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumns({
			@JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD"),
			@JoinColumn(name="SOURCE_UNIQUE_ID", referencedColumnName="SOURCE_UNIQUE_ID"),
			@JoinColumn(name="TAX_YEAR", referencedColumnName="TAX_YEAR")
	})
	private List<FilerDemographicAudit> demographicAudits;

	/*changed to relationship One-to-one*/
	@Column(name = "IRS_TRANSMISSION_STATUS_CD")
	private String irsTransmissionStatusCd;

	/*	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
		@JoinColumns({
                @JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD"),
                @JoinColumn(name="SOURCE_UNIQUE_ID", referencedColumnName="SOURCE_UNIQUE_ID")
        })
        private List<FormPdf1095B> pdfFormData;*/
	@Transient
	private SourceSystemConfig sourcesystemconfig;


/*	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({
			@JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD", insertable=false, updatable=false)
	})*/
	@Transient
    private int lineNumber;
	@Transient
	private boolean isUserFound = false;

	public FilerDemographic() {
	}

	public Long getFilerDemoSeq() {
		return filerDemoSeq;
	}

	public void setFilerDemoSeq(Long filerDemoSeq) {
		this.filerDemoSeq = filerDemoSeq;
	}

	public FilerDemographicPK getId() {
		return this.id;
	}

	public void setId(FilerDemographicPK id) {
		this.id = id;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Character getFilerStatus() {
		return this.filerStatus;
	}

	public void setFilerStatus(Character filerStatus) {
		this.filerStatus = filerStatus;
	}

	public String getLanguagePreference() {
		return this.languagePreference;
	}

	/*public Date getOrigCoverageBeginDt() {
		return this.origCoverageBeginDt;
	}

	public void setOrigCoverageBeginDt(Date origCoverageBeginDt) {
		this.origCoverageBeginDt = origCoverageBeginDt;
	}*/

	public void setLanguagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
	}

	public Character getPolicyOrigin() {
		return this.policyOrigin;
	}

	public void setPolicyOrigin(Character policyOrigin) {
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

	public String getRecepientAddressLine1() {
		return this.recepientAddressLine1;
	}

	public void setRecepientAddressLine1(String recepientAddressLine1) {
		this.recepientAddressLine1 = recepientAddressLine1;
	}

	public String getRecepientAddressLine2() {
		return this.recepientAddressLine2;
	}

	public void setRecepientAddressLine2(String recepientAddressLine2) {
		this.recepientAddressLine2 = recepientAddressLine2;
	}

	public String getRecepientCity() {
		return this.recepientCity;
	}

	public void setRecepientCity(String recepientCity) {
		this.recepientCity = recepientCity;
	}

	public Date getRecepientDob() {
		return this.recepientDob;
	}

	public void setRecepientDob(Date recepientDob) {
		this.recepientDob = recepientDob;
	}

	public String getRecepientFirstName() {
		return this.recepientFirstName;
	}

	public void setRecepientFirstName(String recepientFirstName) {
		this.recepientFirstName = recepientFirstName;
	}

	public String getRecepientLastName() {
		return this.recepientLastName;
	}

	public void setRecepientLastName(String recepientLastName) {
		this.recepientLastName = recepientLastName;
	}

	public String getRecepientMiddleName() {
		return this.recepientMiddleName;
	}

	public void setRecepientMiddleName(String recepientMiddleName) {
		this.recepientMiddleName = recepientMiddleName;
	}

	public String getRecepientSsn() {
		return this.recepientSsn;
	}

	public void setRecepientSsn(String recepientSsn) {
		this.recepientSsn = recepientSsn;
	}

	public String getRecepientState() {
		return this.recepientState;
	}

	public void setRecepientState(String recepientState) {
		this.recepientState = recepientState;
	}

	public String getRecepientSuffixName() {
		return this.recepientSuffixName;
	}

	public void setRecepientSuffixName(String recepientSuffixName) {
		this.recepientSuffixName = recepientSuffixName;
	}

	public String getRecepientTin() {
		return this.recepientTin;
	}

	public void setRecepientTin(String recepientTin) {
		this.recepientTin = recepientTin;
	}

	public String getRecepientZip4() {
		return this.recepientZip4;
	}

	public void setRecepientZip4(String recepientZip4) {
		this.recepientZip4 = recepientZip4;
	}

	public String getRecepientZip5() {
		return this.recepientZip5;
	}

	public void setRecepientZip5(String recepientZip5) {
		this.recepientZip5 = recepientZip5;
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

	/*public Integer getTaxYear() {
		return taxYear;
	}

	public void setTaxYear(Integer taxYear) {
		this.taxYear = taxYear;
	}*/

	public void setShopIdentifier(String shopIdentifier) {
		this.shopIdentifier = shopIdentifier;
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

	//	public String getZipOrPostalCode() {
//		return this.employerZipOrPostalCode;
//	}
//
//	public void setZipOrPostalCode(String zipOrPostalCode) {
//		this.employerZipOrPostalCode = zipOrPostalCode;
//	}

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

	/**
	 * @return the batchInfo
	 */
	public BatchInfo getBatchInfo() {
		return batchInfo;
	}

	/**
	 * @param batchInfo the batchInfo to set
	 */
	public void setBatchInfo(BatchInfo batchInfo) {
		this.batchInfo = batchInfo;
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

	public List<PrintDetail> getPrintDetail() {
		return printDetail;
	}

	public void setPrintDetail(List<PrintDetail>  printDetail) {
		this.printDetail = printDetail;
	}

	public List<FilerDemographicAudit> getDemographicAudits() {
		return demographicAudits;
	}

	public void setDemographicAudits(List<FilerDemographicAudit> demographicAudits) {
		this.demographicAudits = demographicAudits;
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

	public Character getJan() {
		return jan;
	}

	public void setJan(Character jan) {
		this.jan = jan;
	}

	public Character getFeb() {
		return feb;
	}

	public void setFeb(Character feb) {
		this.feb = feb;
	}

	public Character getMar() {
		return mar;
	}

	public void setMar(Character mar) {
		this.mar = mar;
	}

	public Character getApr() {
		return apr;
	}

	public void setApr(Character apr) {
		this.apr = apr;
	}

	public Character getJun() {
		return jun;
	}

	public void setJun(Character jun) {
		this.jun = jun;
	}

	public Character getJul() {
		return jul;
	}

	public void setJul(Character jul) {
		this.jul = jul;
	}

	public Character getAug() {
		return aug;
	}

	public void setAug(Character aug) {
		this.aug = aug;
	}

	public Character getSep() {
		return sep;
	}

	public void setSep(Character sep) {
		this.sep = sep;
	}

	public Character getOct() {
		return oct;
	}

	public void setOct(Character oct) {
		this.oct = oct;
	}

	public Character getNov() {
		return nov;
	}

	public void setNov(Character nov) {
		this.nov = nov;
	}

	public Character getDec() {
		return dec;
	}

	public void setDec(Character dec) {
		this.dec = dec;
	}

	public Character getMay() {
		return may;
	}

	public void setMay(Character may) {
		this.may = may;
	}

	public List<FilerCoverage> getFilerCoverages() {
		return filerCoverages;
	}

	public void setFilerCoverages(List<FilerCoverage> filerCoverages) {
		this.filerCoverages = filerCoverages;
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
}