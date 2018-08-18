package us.deloitteinnovation.aca.entity;


import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the filer_demographics database table.
 *..
 */
@Entity
@Table(name="filer_demographics")
public class FilerDemographicCP implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private FilerDemographicPK id;
	@Transient
	private SourceSystemConfig sourcesystemconfig;
	@Transient
    private int lineNumber;
	@Transient
	private boolean isUserFound = false;

	public FilerDemographicPK getId() {
		return this.id;
	}


	@Column(name=CommonEntityConstants.RECIPIENT_MIDDLE_NAME)
	private String recepientMiddleName;

	@Column(name=CommonEntityConstants.RECIPIENT_ADDRESS_LINE_1)
	private String recepientAddressLine1;

	@Column(name=CommonEntityConstants.RECIPIENT_ADDRESS_LINE_2)
	private String recepientAddressLine2;



	@Column(name=CommonEntityConstants.RECIPIENT_CITY)
	private String recepientCity;

	@Column(name= CommonDataConstants.RECIPIENT_DOB)
	private Date recepientDob;


	@Column(name=CommonEntityConstants.RECIPIENT_FIRST_NAME)
	private String recepientFirstName;

	@Column(name=CommonEntityConstants.RECIPIENT_LAST_NAME)
	private String recepientLastName;

	@Column(name=CommonDataConstants.RECIPIENT_SSN)
	private String recepientSsn;

	@Column(name=CommonEntityConstants.RECIPIENT_STATE_CODE)
	private String recepientState;

	@Column(name="RECIPIENT_TIN")
	private String recepientTin;

	@Column(name=CommonEntityConstants.RECIPIENT_ZIP_5)
	private String recepientZip5;



	@Column(name="FILER_STATUS")
	private String filerStatus;

	@Column(name="UPDATED_BY")
	private String updatedBy;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDt;

	@Column(name="STATUS")
	private String status;

	@Column(name="FORM_STATUS")
	private String formStatus;

	@Column(name="IRS_TRANSMISSION_STATUS_CD")
	private String irsTransmissionCode;

	@Column(name=CommonEntityConstants.PROVIDER_ADDRESS_LINE_1)
	private String providerAddressLine1;

	@Column(name=CommonEntityConstants.PROVIDER_ADDRESS_LINE_2)
	private String providerAddressLine2;

	@Column(name=CommonEntityConstants.PROVIDER_CITY_OR_TOWN)
	private String providerCityOrTown;

	@Column(name="PROVIDER_CONTACT_NO")
	private Long providerContactNo;

	@Column(name=CommonEntityConstants.PROVIDER_COUNTRY)
	private String providerCountry;

	@Column(name=CommonEntityConstants.PROVIDER_IDENTIFICATION_NUMBER)
	private String providerIdentificationNumber;

	@Column(name=CommonEntityConstants.PROVIDER_NAME)
	private String providerName;

	@Column(name=CommonEntityConstants.PROVIDER_STATE_OR_PROVINCE)
	private String providerStateOrProvince;

	@Column(name=CommonEntityConstants.PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE)
	private String providerZipOrPostalCode;


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

	@Column(name = "POLICY_ORIGIN")
	private String policyOrigin;



	public Character getDec() {
		return dec;
	}

	public void setDec(Character dec) {
		this.dec = dec;
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

	public Character getMay() {
		return may;
	}

	public void setMay(Character may) {
		this.may = may;
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




	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}
	public void setId(FilerDemographicPK id) {
		this.id = id;
	}
	public boolean isUserFound() {
		return isUserFound;
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

	public String getRecepientMiddleName() {
		return recepientMiddleName;
	}

	public void setRecepientMiddleName(String recepientMiddleName) {
		this.recepientMiddleName = recepientMiddleName;
	}


	public String getRecepientCity() {
		return recepientCity;
	}

	public void setRecepientCity(String recepientCity) {
		this.recepientCity = recepientCity;
	}

	public Date getRecepientDob() {
		return recepientDob;
	}

	public void setRecepientDob(Date recepientDob) {
		this.recepientDob = recepientDob;
	}

	public String getRecepientFirstName() {
		return recepientFirstName;
	}

	public void setRecepientFirstName(String recepientFirstName) {
		this.recepientFirstName = recepientFirstName;
	}

	public String getRecepientLastName() {
		return recepientLastName;
	}

	public void setRecepientLastName(String recepientLastName) {
		this.recepientLastName = recepientLastName;
	}


	public String getRecepientSsn() {
		return recepientSsn;
	}

	public void setRecepientSsn(String recepientSsn) {
		this.recepientSsn = recepientSsn;
	}

	public String getRecepientState() {
		return recepientState;
	}

	public void setRecepientState(String recepientState) {
		this.recepientState = recepientState;
	}

	public String getRecepientTin() {
		return recepientTin;
	}

	public void setRecepientTin(String recepientTin) {
		this.recepientTin = recepientTin;
	}

	public String getRecepientZip5() {
		return recepientZip5;
	}

	public void setRecepientZip5(String recepientZip5) {
		this.recepientZip5 = recepientZip5;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDt() {
		return updatedDt;
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

	public String getFilerStatus() {
		return filerStatus;
	}

	public void setFilerStatus(String filerStatus) {
		this.filerStatus = filerStatus;
	}
	public FilerDemographicCP() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getFormStatus() {
		return (formStatus != null )?formStatus:"";
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}

	public String getIrsTransmissionCode() {
		return irsTransmissionCode;
	}

	public void setIrsTransmissionCode(String irsTransmissionCode) {
		this.irsTransmissionCode = irsTransmissionCode;
	}

	public String getProviderZipOrPostalCode() {
		return providerZipOrPostalCode;
	}

	public void setProviderZipOrPostalCode(String providerZipOrPostalCode) {
		this.providerZipOrPostalCode = providerZipOrPostalCode;
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

	public Long getProviderContactNo() {
		return providerContactNo;
	}

	public void setProviderContactNo(Long providerContactNo) {
		this.providerContactNo = providerContactNo;
	}

	public String getProviderCountry() {
		return providerCountry;
	}

	public void setProviderCountry(String providerCountry) {
		this.providerCountry = providerCountry;
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

	public String getProviderStateOrProvince() {
		return providerStateOrProvince;
	}

	public void setProviderStateOrProvince(String providerStateOrProvince) {
		this.providerStateOrProvince = providerStateOrProvince;
	}

	public String getPolicyOrigin() {
		return policyOrigin;
	}

	public void setPolicyOrigin(String policyOrigin) {
		this.policyOrigin = policyOrigin;
	}
}