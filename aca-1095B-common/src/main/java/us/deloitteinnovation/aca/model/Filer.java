package us.deloitteinnovation.aca.model;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Filer {
	private int id;
	private long sourceUniqueId;
	private String sourceCd;
	private String correction="";
	private String recipientName;
	private String recipientFirstName;
	private String recipientLastName;
	private String recipientMiddleName;
	private String recipientSuffix;
	private String recipientSSN;
	private String recipientTIN;
	// TODO Why store this as a string when it is a java.sql.Date in the database?  Also converted two different ways.  see getDOBDateFormat
	private String recipientDOB;
	private String recipientAddLine1;
	private String recipientAddLine2;
	private String recipientCity;
	private String recipientState;
	private String recipientZip;
	private String recipientZip4;
	private String taxYear;
	private String policyOrigin;
	private String providerName;
	private String providerEIN;
	private String providerAddLine1;
	private String providerAddLine2;
	private String providerCity;
	private String providerState;
	private String providerZip;
	private String providerCountry;
	private Long providerContactNo;
	private String employerName;
	private String employerEIN;
	private String employerAddressLine1;
	private String employerAddressLine2;
	private String employerCity;
	private String employerState;
	private String employerZIP;
	private String responsiblePersonUniqueId;
	/** Sequence number assigned to this Filer.  TODO Should this be incremented on each edit? */
	private Long filerDemoSeq ;
	private String jan;
	private String feb;
	private String mar;
	private String apr;
	private String may;
	private String jun;
	private String jul;
	private String aug;
	private String sep;
	private String oct;
	private String nov;
	private String dec;
	private TreeMap<String, CoveredPerson> coveredpersons = null;
	private int coveredPersonSize;
	private String filerStatus;
	private boolean isExceptionFound = false;
	private String maskedSSN;
    private ByteArrayOutputStream byteArrayOutputStream;
	private String formStatus = null;
	private String updatedBy;
	private Date updatedDate;
	private String printStatus;
	private String shopIdentifier;
	private String irsTransmittalStatus=null;
	private int correctionIndicator;
	List<Filer> coveredPersonList = new ArrayList<>();

	public long getSourceUniqueId() {
		return sourceUniqueId;
	}
	public void setSourceUniqueId(long sourceUniqueId) {
		this.sourceUniqueId = sourceUniqueId;
	}
	public String getCorrection() {
		return correction;
	}
	public void setCorrection(String correction) {
		this.correction = correction;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getRecipientSSN() {
		return recipientSSN;
	}
	public void setRecipientSSN(String recipientSSN) {
		this.recipientSSN = recipientSSN;
	}
	public String getRecipientDOB() {
		return getDOBDateFormat(recipientDOB) ;
	}
	public void setRecipientDOB(String recipientDOB) {
		this.recipientDOB = recipientDOB;
	}
	public String getRecipientAddLine1() {
		return recipientAddLine1;
	}
	public void setRecipientAddLine1(String recipientAddLine1) {
		this.recipientAddLine1 = recipientAddLine1;
	}
	public String getRecipientAddLine2() {	return recipientAddLine2;	}
	public void setRecipientAddLine2(String recipientAddLine2) {this.recipientAddLine2 = (recipientAddLine2 != null)?recipientAddLine2:"";	}
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
	public String getRecipientZip() {
		return recipientZip;
	}
	public void setRecipientZip(String recipientZip) {
		this.recipientZip = recipientZip;
	}
	public String getPolicyOrigin() {
		return policyOrigin;
	}
	public void setPolicyOrigin(String policyOrigin) {
		this.policyOrigin = policyOrigin;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getProviderEIN() {
		return providerEIN;
	}
	public void setProviderEIN(String providerEIN) {
		this.providerEIN = providerEIN;
	}
	public String getProviderAddLine1() {
		return providerAddLine1;
	}
	public void setProviderAddLine1(String providerAddLine1) {
		this.providerAddLine1 = providerAddLine1;
	}
	public String getProviderCity() {
		return providerCity;
	}
	public void setProviderCity(String providerCity) {
		this.providerCity = providerCity;
	}
	public String getProviderState() {
		return providerState;
	}
	public void setProviderState(String providerState) {
		this.providerState = providerState;
	}
	public String getProviderZip() {
		return providerZip;
	}
	public void setProviderZip(String providerZip) {
		this.providerZip = providerZip;
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
	public String getMay() {
		return may;
	}
	public void setMay(String may) {
		this.may = may;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSourceCd() {
		return sourceCd;
	}
	public void setSourceCd(String sourceCd) {
		this.sourceCd = sourceCd;
	}

	public TreeMap<String, CoveredPerson> getCoveredpersons() {
		return coveredpersons;
	}
	public void setCoveredpersons(TreeMap<String, CoveredPerson> coveredpersons) {
		this.coveredpersons = coveredpersons;
	}
	public int getCoveredPersonSize() {
		return coveredPersonSize;
	}
	public void setCoveredPersonSize(int coveredPersonSize) {
		this.coveredPersonSize = coveredPersonSize;
	}

	public String getRecipientMergedAddress(){return this.recipientCity+" "+this.recipientState+" "+this.recipientZip;}

	public String getFilerStatus() {
		return filerStatus;
	}

	public void setFilerStatus(String filerStatus) {
		this.filerStatus = filerStatus;
	}
	public boolean isExceptionFound() {
		return isExceptionFound;
	}

	public void setIsExceptionFound(boolean isExceptionFound) {
		this.isExceptionFound = isExceptionFound;
	}
	public String getMaskedSSN() {
		return (recipientSSN != null)?recipientSSN.replaceAll ("^[0-9]{5}", "xxx-xx-"):"";
	}

	public void setMaskedSSN(String maskedSSN) {
		this.maskedSSN = maskedSSN;
	}
	public Long getProviderContactNo() {
		return providerContactNo;
	}

	public void setProviderContactNo(Long providerContactNo) {
		this.providerContactNo = providerContactNo;
	}
	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getEmployerEIN() {
		return employerEIN;
	}

	public void setEmployerEIN(String employerEIN) {
		this.employerEIN = employerEIN;
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
		this.employerAddressLine2 = (employerAddressLine2 != null)?employerAddressLine2:"";
	}

	public String getEmployerCity() {
		return employerCity;
	}

	public void setEmployerCity(String employerCity) {
		this.employerCity = employerCity;
	}

	public String getEmployerState() {
		return employerState;
	}

	public void setEmployerState(String employerState) {
		this.employerState = employerState;
	}

	public String getEmployerZIP() {
		return employerZIP;
	}

	public void setEmployerZIP(String employerZIP) {
		this.employerZIP = employerZIP;
	}
	public String getProviderAddLine2() {
		return providerAddLine2;
	}

	public void setProviderAddLine2(String providerAddLine2) {
		this.providerAddLine2 = (providerAddLine2 != null)?providerAddLine2:"";
	}

	public ByteArrayOutputStream getByteArrayOutputStream() {
		return byteArrayOutputStream;
	}

	public void setByteArrayOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
		this.byteArrayOutputStream = byteArrayOutputStream;
	}

	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
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

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	// TODO What is this method?  What does it do any why? Should it throw an exception or not?

	public String getDOBDateFormat(String date)
	{
		SimpleDateFormat formatterOld = new SimpleDateFormat("yyyy-mm-dd");
		SimpleDateFormat formatterNew = new SimpleDateFormat("mm/dd/yyyy");
		String formattedDate = "";
		try {

			formattedDate = formatterNew.format(formatterOld.parse(date));
		}
		catch (ParseException e) {
			//throw new RuntimeException("Attempt to parse date '" + date + "'", e) ;
		}
		return formattedDate;
	}
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

	public String getRecipientMiddleName() {
		return recipientMiddleName;
	}

	public void setRecipientMiddleName(String recipientMiddleName) {
		this.recipientMiddleName = recipientMiddleName;
	}

	public String getRecipientSuffix() {
		return recipientSuffix;
	}

	public void setRecipientSuffix(String recipientSuffix) {
		this.recipientSuffix = recipientSuffix;
	}

	public String getRecipientTIN() {
		return recipientTIN;
	}

	public void setRecipientTIN(String recipientTIN) {
		this.recipientTIN = recipientTIN;
	}

	public String getRecipientZip4() {
		return recipientZip4;
	}

	public void setRecipientZip4(String recipientZip4) {
		this.recipientZip4 = recipientZip4;
	}

	public String getTaxYear() {
		return taxYear;
	}

	public void setTaxYear(String taxYear) {
		this.taxYear = taxYear;
	}

	public String getShopIdentifier() {
		return shopIdentifier;
	}

	public void setShopIdentifier(String shopIdentifier) {
		this.shopIdentifier = shopIdentifier;
	}
	public String getIrsTransmittalStatus() {
		return irsTransmittalStatus;
	}

	public void setIrsTransmittalStatus(String irsTransmittalStatus) {
		this.irsTransmittalStatus = irsTransmittalStatus;
	}

	public Long getFilerDemoSeq() {
		return filerDemoSeq;
	}

	public void setFilerDemoSeq(Long filerDemoSeq) {
		this.filerDemoSeq = filerDemoSeq;
	}

	public void setProviderCountry(String providerCountry) {
		this.providerCountry = providerCountry;
	}
	public String getProviderCountry() {
		return providerCountry;
	}

	public void setResponsiblePersonUniqueId(String responsiblePersonUniqueId) {
		this.responsiblePersonUniqueId = responsiblePersonUniqueId;
	}

	public String getResponsiblePersonUniqueId() {
		return responsiblePersonUniqueId;
	}
	public int getCorrectionIndicator() {
		return correctionIndicator;
	}
	public void setCorrectionIndicator(int correctionIndicator) {
		this.correctionIndicator = correctionIndicator;
	}
    public List<Filer> getCoveredPersonList() {
        return coveredPersonList;
    }
    public void setCoveredPersonList(List<Filer> coveredPersonList) {
        this.coveredPersonList = coveredPersonList;
    }

}

