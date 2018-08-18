package us.deloitteinnovation.aca.entity;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;
import us.deloitteinnovation.aca.validator.AcceptedChar;
import us.deloitteinnovation.aca.validator.AcceptedValues;
import us.deloitteinnovation.aca.validator.StateCode;
import us.deloitteinnovation.aca.validator.USStateCode;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the filer_demographics database table.
 *..
 */
@Entity
@Table(name="filer_demographics")
public class FilerDemographicCW implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FilerDemographicPK id;

	@Column(name = CommonEntityConstants.COMMENTS)
	private String comments;

	@NotNull(message = ErrorMessageConstants.COMM_PREFERENCE_REQ)
	@AcceptedChar(acceptValues = {'E', 'P','B'}, message = ErrorMessageConstants.COMM_PREFERENCE_VALUE)
	@Column(name= CommonEntityConstants.COMM_PREFERENCE)
	private Character communicationPreference;

	@NotNull(message = ErrorMessageConstants.CORRECTION_REQ)
	@NotEmpty(message = ErrorMessageConstants.CORRECTION_REQ)
	@AcceptedValues(acceptValues = {"U", "O","C"}, message = ErrorMessageConstants.CORRECTION_REQ_VALUE)
	@Column(name=CommonEntityConstants.CORRECTION)
	private String correction;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CORRECTION_DATE")
	private Date correctionDt;

	@Pattern(regexp = "[a-zA-Z0-9@. ]{0,30}", message = ErrorMessageConstants.EMAIL_VALUE)
	@Column(name=CommonEntityConstants.EMAIL)
	private String eMail;

	@Pattern(regexp = "^.{0,30}$", message = ErrorMessageConstants.EMPLOYER_ADDRESS_LINE_1_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_ADDRESS_LINE_1)
	private String employerAddressLine1;

	@Pattern(regexp = "^.{0,30}$", message = ErrorMessageConstants.EMPLOYER_ADDRESS_LINE_1_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_ADDRESS_LINE_2)
	private String employerAddressLine2;

	@Column(name=CommonEntityConstants.EMPLOYER_CITY_OR_TOWN)
	private String employerCityOrTown;

	@Column(name="EMPLOYER_CONTACT_NO")
	private Long employerContactNo;

	@Pattern(regexp = "^.{0,25}$", message = ErrorMessageConstants.EMPLOYER_COUNTRY_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_COUNTRY)
	private String employerCountry;

	@Pattern(regexp = "^[0-9]{0,25}$", message = ErrorMessageConstants.EMPLOYER_IDENTIFICATION_NUMBER_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_IDENTIFICATION_NUMBER)
	private String employerIdentificationNumber;

	@Pattern(regexp = "^.{0,25}$", message = ErrorMessageConstants.EMPLOYER_NAME_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_NAME)
	private String employerName;

	@Pattern(regexp = "^.{0,25}$", message = ErrorMessageConstants.EMPLOYER_STATE_OR_PROVINCE_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_STATE_OR_PROVINCE)
	private String employerStateOrProvince;

	@Digits(fraction = 0, integer = 25, message = ErrorMessageConstants.EMPLOYER_ZIP_OR_FOREIGN_POSTAL_CODE_REQ_VALUE)
	@Column(name=CommonEntityConstants.EMPLOYER_ZIP_OR_FOREIGN_POSTAL_CODE)
	private String employerZipOrPostalCode;

	@NotNull(message = ErrorMessageConstants.FILLER_STATUS_REQ)
	@AcceptedChar(acceptValues = {'R', 'N','C'}, message = ErrorMessageConstants.FILLER_STATUS_VALUE)
	@Column(name=CommonEntityConstants.FILLER_STATUS,  columnDefinition = "char")
	private Character filerStatus;

	@Column(name="RECIPIENT_LANGUAGE_PREFERENCE")
	private String languagePreference;

	@NotNull(message = ErrorMessageConstants.POLICY_ORIGIN_REQ)
	@AcceptedChar(acceptValues = {'C'}, message = ErrorMessageConstants.POLICY_ORIGIN_REQ_VALUE)
	@Column(name=CommonEntityConstants.POLICY_ORIGIN,  columnDefinition = "char")
	private Character policyOrigin;

	@NotNull(message = "{provider.address.line1.length.invalid}")
	@Pattern.List({
			@Pattern(regexp = "^.{1,35}$", message = "{provider.address.line1.length.invalid}"),
			@Pattern(regexp = ".*[a-zA-Z].*", message = "{provider.address.line1.spchar.num.error}"),
	})
	@Column(name=CommonEntityConstants.PROVIDER_ADDRESS_LINE_1)
	private String providerAddressLine1;

	@Pattern.List({
			@Pattern(regexp = "^.{0,35}$", message = "{provider.address.line2.length.invalid}"),
			@Pattern(regexp = ".*[a-zA-Z].*", message = "{provider.address.line2.spchar.num.error}"),
	})
	@Column(name=CommonEntityConstants.PROVIDER_ADDRESS_LINE_2)
	private String providerAddressLine2;

	@NotNull(message = "{provider.city.or.town.length.invalid}")
	@Pattern.List({
					@Pattern(regexp = "^.{1,22}$", message = "{provider.city.or.town.length.invalid}"),
					@Pattern(regexp = ".*[a-zA-Z].*", message = "{provider.city.or.town.spchar.num.error}"),
					@Pattern(regexp = "^[ A-Za-z0-9\\.\\-\\'\\,\\*]*$", message = "{provider.city.or.town.allowed.spchar.error}")})
	@Column(name=CommonEntityConstants.PROVIDER_CITY_OR_TOWN)
	private String providerCityOrTown;

	@Column(name="PROVIDER_CONTACT_NO")
	private Long providerContactNo;

	@NotNull(message = "{provider.country.length.invalid}")
	@Pattern(regexp = "^[A-z]{1,25}$", message = "{provider.country.length.invalid}")
	@Column(name=CommonEntityConstants.PROVIDER_COUNTRY)
	private String providerCountry;

	@NotNull(message = "{provider.ein.length.invalid}")
	@Pattern.List({
					@Pattern(regexp = "^.{1,10}$", message = "{provider.ein.length.invalid}"),
					@Pattern(regexp = "(\\d{9}|\\d{2}-\\d{7})", message = "{provider.ein.format.invalid}")})
	@Column(name=CommonEntityConstants.PROVIDER_IDENTIFICATION_NUMBER)
	private String providerIdentificationNumber;

	@NotBlank(message = "{provider.name.length.invalid}")
	@Pattern.List({
					@Pattern(regexp = "^.{1,75}$", message = "{provider.name.length.invalid}"),
					@Pattern(regexp = "(^[^\\&\\'\\\"\\#\\<\\>\\*\\^\\%\\$\\@\\!\\{\\}\\[\\]\\?\\/\\\\\\+\\=]+$)", message = "{provider.name.spchar.exclusion.error}"),
					@Pattern(regexp = "^(?:(?!--).)+$", message = "{provider.name.spchar.exclusion.error}"),
					@Pattern(regexp = ".*[a-zA-Z].*", message = "{provider.name.spchar.num.error}")})
	@Column(name=CommonEntityConstants.PROVIDER_NAME)
	private String providerName;

	@NotNull(message = "{provider.state.or.province.length.invalid}")
	@Pattern(regexp = "^.{1,2}$", message = "{provider.state.or.province.length.invalid}")
	@USStateCode(acceptValues = StateCode.class, message = "{provider.state.or.province.mismatch}")
	@Column(name=CommonEntityConstants.PROVIDER_STATE_OR_PROVINCE)
	private String providerStateOrProvince;

	@NotNull(message = "{provider.zip.length.invalid}")
	@Pattern.List({
					@Pattern(regexp = "[0-9]*", message = "{provider.zip.content.invalid}"),
					@Pattern(regexp = "^.{5,9}$", message = "{provider.zip.min.max.length.invalid}"),
					@Pattern(regexp = "^(?!00000).+", message = "{provider.zip.all.zeros.error}")})
	@Column(name=CommonEntityConstants.PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE)
	private String providerZipOrPostalCode;

	@NotNull(message = ErrorMessageConstants.RECIPIENT_ADDRESS_LINE_1_REQ)
	@NotEmpty(message = ErrorMessageConstants.RECIPIENT_ADDRESS_LINE_1_REQ)
	@Pattern.List({
			@Pattern(regexp = "^.{1,35}$", message = "{recipient.address.line1.length.invalid}"),
			@Pattern(regexp = ".*[a-zA-Z].*", message = "{recipient.address.line1.spchar.num.error}")})
	@Column(name=CommonEntityConstants.RECIPIENT_ADDRESS_LINE_1)
	private String recepientAddressLine1;

	@Pattern.List({
			@Pattern(regexp = "^.{0,35}$", message = "{recipient.address.line2.length.invalid}")})
	@Column(name=CommonEntityConstants.RECIPIENT_ADDRESS_LINE_2)
	private String recepientAddressLine2;

	@NotNull(message = "{recipient.city.length.invalid}")
	@Pattern.List({
			@Pattern(regexp = "^.{1,22}$", message = "{recipient.city.length.invalid}"),
			@Pattern(regexp = ".*[a-zA-Z].*", message = "{recipient.city.spchar.num.error}"),
			@Pattern(regexp = "^[ A-Za-z0-9\\.\\-\\'\\,]*$", message = "{recipient.city.allowed.spchar.error}")})
	@Column(name=CommonEntityConstants.RECIPIENT_CITY)
	private String recepientCity;

	@NotNull(message = ErrorMessageConstants.RECIPIENT_DOB_REQ)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= CommonDataConstants.RECIPIENT_DOB)
	private Date recepientDob;

	@NotBlank(message = "{recipient.first.name.length.invalid}")
	@Pattern.List({
					@Pattern(regexp = "^.{1,20}$", message = "{recipient.first.name.length.invalid}"),
					@Pattern(regexp = "(^[^\\&\\\"\\#\\<\\>\\*\\^\\%\\$\\@\\!\\{\\}\\[\\]\\?\\/\\\\\\+\\=]+$)", message = "{recipient.first.name.spchar.exclusion.error}"),
					@Pattern(regexp = "^(?:(?!--).)+$", message = "{recipient.first.name.spchar.exclusion.error}"),
					@Pattern(regexp = ".*[ a-zA-Z].*", message = "{recipient.first.name.spchar.num.error}")})
	@Column(name=CommonEntityConstants.RECIPIENT_FIRST_NAME)
	private String recepientFirstName;

	@NotBlank(message = "{recipient.last.name.length.invalid}")
	@Pattern.List({
					@Pattern(regexp = "^.{1,20}$", message = "{recipient.last.name.length.invalid}"),
					@Pattern(regexp = "(^[^\\&\\\"\\#\\<\\>\\*\\^\\%\\$\\@\\!\\{\\}\\[\\]\\?\\/\\\\\\+\\=]+$)", message = "{recipient.last.name.spchar.exclusion.error}"),
					@Pattern(regexp = "^(?:(?!--).)+$", message = "{recipient.last.name.spchar.exclusion.error}"),
					@Pattern(regexp = ".*[ a-zA-Z].*", message = "{recipient.last.name.spchar.num.error}")})
	@Column(name=CommonEntityConstants.RECIPIENT_LAST_NAME)
	private String recepientLastName;

	@Pattern.List({
					@Pattern(regexp = "^.{0,20}$", message = "{recipient.middle.name.length.invalid}"),
					@Pattern(regexp = "(^[^\\&\\\"\\#\\<\\>\\*\\^\\%\\$\\@\\!\\{\\}\\[\\]\\?\\/\\\\\\+\\=]+$)", message = "{recipient.middle.name.spchar.exclusion.error}"),
					@Pattern(regexp = "^(?:(?!--).)+$", message = "{recipient.middle.name.spchar.exclusion.error}"),
					@Pattern(regexp = ".*[ a-zA-Z].*", message = "{recipient.middle.name.spchar.num.error}")})
	@Column(name=CommonEntityConstants.RECIPIENT_MIDDLE_NAME)
	private String recepientMiddleName;

	@Pattern.List({
			@Pattern(regexp = "^\\d{9}$", message = "{recipient.ssn.length.invalid}"),
			@Pattern(regexp = "^(?!666).+", message = "{recipient.ssn.begin.value.error}"),
			@Pattern(regexp = "^(?!000).+", message = "{recipient.ssn.begin.value.error}"),
			@Pattern(regexp = "(^[0-8]\\w*$)", message = "{recipient.ssn.begin.value.error}")})
	@Column(name=CommonDataConstants.RECIPIENT_SSN)
	private String recepientSsn;

	@NotNull(message = "{recipient.state.code.invalid}")
	@Pattern(regexp = "[a-zA-Z]{2}", message = "{recipient.state.code.invalid}")
	@USStateCode(acceptValues = StateCode.class, message = "{recipient.state.code.mismatch}")
	@Column(name=CommonEntityConstants.RECIPIENT_STATE_CODE)
	private String recepientState;

	@Pattern.List({
					@Pattern(regexp = "^.{0,10}$", message = "{recipient.suffix.name.length.invalid}"),
					@Pattern(regexp = "(^[^\\&\\\"\\#\\<\\>\\*\\^\\%\\$\\@\\!\\{\\}\\[\\]\\?\\/\\\\\\+\\=]+$)", message = "{recipient.suffix.name.spchar.exclusion.error}"),
					@Pattern(regexp = "^(?:(?!--).)+$", message = "{recipient.suffix.name.spchar.exclusion.error}"),
					@Pattern(regexp = ".*[a-zA-Z].*", message = "{recipient.suffix.name.spchar.num.error}")})
	@Column(name=CommonEntityConstants.RECIPIENT_NAME_SUFFIX)
	private String recepientSuffixName;

	@Column(name="RECIPIENT_TIN")
	private String recepientTin;

	@Digits(fraction = 0, integer = 4, message = ErrorMessageConstants.RECIPIENT_ZIP_4_REQ_VALUE)
	@Column(name=CommonEntityConstants.RECIPIENT_ZIP_4)
	private String recepientZip4;

	@NotNull(message = ErrorMessageConstants.RECIPIENT_ZIP_5_REQ)
	@Digits(fraction = 0, integer = 5, message = ErrorMessageConstants.RECIPIENT_ZIP_5_REQ_VALUE)
	@Column(name=CommonEntityConstants.RECIPIENT_ZIP_5)
	private String recepientZip5;

	@Digits(fraction = 0, integer = 15, message = ErrorMessageConstants.RESPONSIBLE_PERSON_UNIQUE_REQ_VALUE)
	@Column(name=CommonEntityConstants.RESPONSIBLE_PERSON_UNIQUE)
	private Long responsiblePersonUniqueId;

	@Pattern(regexp = "^.{0}$", message = "{shop.identifier.value.error}")
	@Column(name=CommonEntityConstants.POLICY_SHOP_IDENTIFIER)
	private String shopIdentifier;



	@Column(name="CORRECTION_INDICATOR")
	private Integer correctionIndicator;



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



	@Column(name="MAILED_FORM")
	private String mailedForm;


	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumns({
			@JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD"),
			@JoinColumn(name="SOURCE_UNIQUE_ID", referencedColumnName="SOURCE_UNIQUE_ID"),
			@JoinColumn(name="TAX_YEAR", referencedColumnName="TAX_YEAR")
	})
	private List<FilerCoverage> filerCoverages;



	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumns({
			@JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD"),
			@JoinColumn(name="SOURCE_UNIQUE_ID", referencedColumnName="SOURCE_UNIQUE_ID"),
			@JoinColumn(name="TAX_YEAR", referencedColumnName="TAX_YEAR")
	})
	private List<PrintDetail> printDetails;


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

	@Column(name = "RECORD_VERSION")
	private int recordVersion;


	@Transient
	private SourceSystemConfig sourcesystemconfig;



	@Transient
	private int lineNumber;
	@Transient
	private boolean isUserFound = false;

	/* Constructor */
	public FilerDemographicCW() {
	}

	public FilerDemographicCW(AuditFilerDemographic auditFilerDemographic) {
		this.id = new FilerDemographicPK(auditFilerDemographic.getSourceUniqueId(), auditFilerDemographic.getSourceCd(), auditFilerDemographic.getTaxYear());
		this.comments = auditFilerDemographic.getComments();
		this.communicationPreference = auditFilerDemographic.getCommunicationPreference();
		this.correction = auditFilerDemographic.getCorrectionCode();
		this.correctionDt = auditFilerDemographic.getCorrectionDate();
		this.eMail = auditFilerDemographic.getRecipientEmail();
		this.employerAddressLine1 = auditFilerDemographic.getEmployerAddressLine1();
		this.employerAddressLine2 = auditFilerDemographic.getEmployerAddressLine2();
		this.employerCityOrTown = auditFilerDemographic.getEmployerCityOrTown();
		this.employerContactNo = auditFilerDemographic.getEmployerContactNo();
		this.employerCountry = auditFilerDemographic.getEmployerCountry();
		this.employerIdentificationNumber = auditFilerDemographic.getEmployerIdentificationNumber();
		this.employerName = auditFilerDemographic.getEmployerName();
		this.employerStateOrProvince = auditFilerDemographic.getEmployerStateOrProvince();
		this.employerZipOrPostalCode = auditFilerDemographic.getEmployerZipOrPostalCode();
		this.filerStatus = auditFilerDemographic.getFilerStatus();
		this.languagePreference = auditFilerDemographic.getRecipientLanguagePreference();
		this.policyOrigin = auditFilerDemographic.getPolicyOrigin();
		this.providerAddressLine1 = auditFilerDemographic.getProviderAddressLine1();
		this.providerAddressLine2 = auditFilerDemographic.getProviderAddressLine2();
		this.providerCityOrTown = auditFilerDemographic.getProviderCityOrTown();
		this.providerContactNo = auditFilerDemographic.getProviderContactNo();
		this.providerCountry = auditFilerDemographic.getProviderCountry();
		this.providerIdentificationNumber = auditFilerDemographic.getProviderIdentificationNumber();
		this.providerName = auditFilerDemographic.getProviderName();
		this.providerStateOrProvince = auditFilerDemographic.getProviderStateOrProvince();
		this.providerZipOrPostalCode = auditFilerDemographic.getProviderZipOrPostalCode();
		this.recepientAddressLine1 = auditFilerDemographic.getRecipientAddressLine1();
		this.recepientAddressLine2 = auditFilerDemographic.getRecipientAddressLine2();
		this.recepientCity = auditFilerDemographic.getRecipientCity();
		this.recepientDob = auditFilerDemographic.getRecipientDob();
		this.recepientFirstName = auditFilerDemographic.getRecipientFirstName();
		this.recepientLastName = auditFilerDemographic.getRecipientLastName();
		this.recepientMiddleName = auditFilerDemographic.getRecipientMiddleName();
		this.recepientSsn = auditFilerDemographic.getRecipientSsn();
		this.recepientState = auditFilerDemographic.getRecipientState();
		this.recepientSuffixName = auditFilerDemographic.getRecipientSuffixName();
		this.recepientTin = auditFilerDemographic.getRecipientTin();
		this.recepientZip4 = auditFilerDemographic.getRecipientZip4();
		this.recepientZip5 = auditFilerDemographic.getRecipientZip5();
		this.responsiblePersonUniqueId = auditFilerDemographic.getResponsiblePersonUniqueId();
		this.shopIdentifier = auditFilerDemographic.getShopIdentifier();
		this.correctionIndicator = auditFilerDemographic.getCorrectionIndicator();
		this.updatedBy = auditFilerDemographic.getUpdatedBy();
		this.updatedDt = auditFilerDemographic.getUpdatedDate();
		this.filerDemoSeq = auditFilerDemographic.getFilerdemoSeq();
		this.formStatus = auditFilerDemographic.getFormStatus();
		this.status = auditFilerDemographic.getStatus();
		this.jan = auditFilerDemographic.getJan();
		this.feb = auditFilerDemographic.getFeb();
		this.apr = auditFilerDemographic.getApr();
		this.mar = auditFilerDemographic.getMar();
		this.may = auditFilerDemographic.getMay();
		this.jun = auditFilerDemographic.getJun();
		this.jul = auditFilerDemographic.getJul();
		this.aug = auditFilerDemographic.getAug();
		this.sep = auditFilerDemographic.getSep();
		this.oct = auditFilerDemographic.getOct();
		this.nov = auditFilerDemographic.getNov();
		this.dec = auditFilerDemographic.getDec();
		this.mailedForm = auditFilerDemographic.getMailedForm();
		this.irsTransmissionStatusCd = auditFilerDemographic.getIrsTransmissionStatusCd();
		this.recordVersion = auditFilerDemographic.getRecordVersion();
	}

	/* Getter and setters */

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


	public Integer getCorrectionIndicator() {
		return correctionIndicator;
	}

	public void setCorrectionIndicator(Integer correctionIndicator) {
		this.correctionIndicator = correctionIndicator;
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


	public List<PrintDetail> getPrintDetails() {
		return printDetails;
	}

	public void setPrintDetails(List<PrintDetail> printDetails) {
		this.printDetails = printDetails;
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

	public String getLatestPrintStatus()
	{
		String printStatus="";

		//sort print details by date to get latest record.
		Collections.sort(printDetails, new Comparator<PrintDetail>() {
			public int compare(PrintDetail o1, PrintDetail o2) {
				Date a = o1.getCreatedDate();
				Date b = o2.getCreatedDate();
				if (a.after(b))
					return -1;
				else if (a.equals(b)) // it's equals
					return 0;
				else
					return 1;
			}
		});
		if(printDetails.size() > 0)
		{
			printStatus = printDetails.get(0).getPrintStatus();
		}
		return printStatus;
	}

	public int getRecordVersion() {
		return recordVersion;
	}

	public void setRecordVersion(int recordVersion) {
		this.recordVersion = recordVersion;
	}
}