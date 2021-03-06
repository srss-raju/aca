//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.common.EPDErrorDetailType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Covered Individual Monthly 1095C Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2014-04-09&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;All the elements associated with an covered individual on exchange generated monthly 10905C report.&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for CoveredIndvidualMonthly1095CDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CoveredIndvidualMonthly1095CDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}CoveredIndividualName"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SSN" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}BirthDt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}CoveredIndivYearRound1095CInd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}CoveredIndivMonthly1095CIndGrp"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ErrorDetail" maxOccurs="99" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoveredIndvidualMonthly1095CDetailType", propOrder = {
    "coveredIndividualName",
    "ssn",
    "birthDt",
    "coveredIndivYearRound1095CInd",
    "coveredIndivMonthly1095CIndGrp",
    "errorDetail"
})
public class CoveredIndvidualMonthly1095CDetailType {

    @XmlElement(name = "CoveredIndividualName", required = true)
    protected OtherCompletePersonNameType coveredIndividualName;
    @XmlElement(name = "SSN", namespace = "urn:us:gov:treasury:irs:common")
    protected String ssn;
    @XmlElement(name = "BirthDt", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthDt;
    @XmlElement(name = "CoveredIndivYearRound1095CInd", required = true)
    protected String coveredIndivYearRound1095CInd;
    @XmlElement(name = "CoveredIndivMonthly1095CIndGrp", required = true)
    protected MonthlyCoveredIndivIndGrpType coveredIndivMonthly1095CIndGrp;
    @XmlElement(name = "ErrorDetail")
    protected List<EPDErrorDetailType> errorDetail;

    /**
     * Gets the value of the coveredIndividualName property.
     * 
     * @return
     *     possible object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    public OtherCompletePersonNameType getCoveredIndividualName() {
        return coveredIndividualName;
    }

    /**
     * Sets the value of the coveredIndividualName property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    public void setCoveredIndividualName(OtherCompletePersonNameType value) {
        this.coveredIndividualName = value;
    }

    /**
     * Gets the value of the ssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSSN() {
        return ssn;
    }

    /**
     * Sets the value of the ssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSSN(String value) {
        this.ssn = value;
    }

    /**
     * Gets the value of the birthDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthDt() {
        return birthDt;
    }

    /**
     * Sets the value of the birthDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthDt(XMLGregorianCalendar value) {
        this.birthDt = value;
    }

    /**
     * Gets the value of the coveredIndivYearRound1095CInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoveredIndivYearRound1095CInd() {
        return coveredIndivYearRound1095CInd;
    }

    /**
     * Sets the value of the coveredIndivYearRound1095CInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoveredIndivYearRound1095CInd(String value) {
        this.coveredIndivYearRound1095CInd = value;
    }

    /**
     * Gets the value of the coveredIndivMonthly1095CIndGrp property.
     * 
     * @return
     *     possible object is
     *     {@link MonthlyCoveredIndivIndGrpType }
     *     
     */
    public MonthlyCoveredIndivIndGrpType getCoveredIndivMonthly1095CIndGrp() {
        return coveredIndivMonthly1095CIndGrp;
    }

    /**
     * Sets the value of the coveredIndivMonthly1095CIndGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonthlyCoveredIndivIndGrpType }
     *     
     */
    public void setCoveredIndivMonthly1095CIndGrp(MonthlyCoveredIndivIndGrpType value) {
        this.coveredIndivMonthly1095CIndGrp = value;
    }

    /**
     * Gets the value of the errorDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EPDErrorDetailType }
     * 
     * 
     */
    public List<EPDErrorDetailType> getErrorDetail() {
        if (errorDetail == null) {
            errorDetail = new ArrayList<EPDErrorDetailType>();
        }
        return this.errorDetail;
    }

}
