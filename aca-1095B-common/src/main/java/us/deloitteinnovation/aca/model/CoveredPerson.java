package us.deloitteinnovation.aca.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * All month coverage values will either use CommonEntityConstants.COVERED or CommonEntityConstants.UNCOVERED
 */
public class CoveredPerson {
	private String name;
	private String firstName;
	private String lastName;
	private String middleName;
	private String suffix;
	private String ssn;
	private String tin;
	private String dob;
	/** Values of all are either CommonEntityConstants.UNCHECKED or CommonEntityConstants.CHECKED */
	private String all;
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The SSN with the first 5 digits masked as 'xxx-xx-'
     */
	public String getSsn() {
		return (ssn != null)?ssn.replaceAll ("^[0-9]{5}", "xxx-xx-"):"";
	}

	/** @return The actual SSN, with no masking of the first 5 digits. */
	public String getSsnPlainText() {
		return ssn ;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getDob() {

		return (dob != null)?getDOBDateFormat(dob):"";
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
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

	public String getDOBDateFormat(String date)
	{
		String formattedDate = "";
		if(date.contains("/"))
		{
			return date;
		}
		else
		{
			SimpleDateFormat formatterOld = new SimpleDateFormat("yyyy-mm-dd");
			SimpleDateFormat formatterNew = new SimpleDateFormat("mm/dd/yyyy");
			try {

				formattedDate = formatterNew.format(formatterOld.parse(date));
			}
			catch (ParseException e) {

			}
		}


		return formattedDate;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

}
