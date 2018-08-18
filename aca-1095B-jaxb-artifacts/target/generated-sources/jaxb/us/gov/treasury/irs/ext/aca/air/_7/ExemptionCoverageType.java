//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Exemption Coverage Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2013-05-17&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;All the elements associated with an Exemption coverage on a month&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for ExemptionCoverageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExemptionCoverageType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ApplicableCoverageMonthNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ExemptPerson"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ExemptionCertificateNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}MonthsExemptStartDt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}MonthsExemptEndDt"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExemptionCoverageType", propOrder = {
    "applicableCoverageMonthNum",
    "exemptPerson",
    "exemptionCertificateNum",
    "monthsExemptStartDt",
    "monthsExemptEndDt"
})
public class ExemptionCoverageType {

    @XmlElement(name = "ApplicableCoverageMonthNum", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int applicableCoverageMonthNum;
    @XmlElement(name = "ExemptPerson", required = true)
    protected EPDPersonType exemptPerson;
    @XmlElement(name = "ExemptionCertificateNum", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String exemptionCertificateNum;
    @XmlElement(name = "MonthsExemptStartDt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar monthsExemptStartDt;
    @XmlElement(name = "MonthsExemptEndDt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar monthsExemptEndDt;

    /**
     * Gets the value of the applicableCoverageMonthNum property.
     * 
     */
    public int getApplicableCoverageMonthNum() {
        return applicableCoverageMonthNum;
    }

    /**
     * Sets the value of the applicableCoverageMonthNum property.
     * 
     */
    public void setApplicableCoverageMonthNum(int value) {
        this.applicableCoverageMonthNum = value;
    }

    /**
     * Gets the value of the exemptPerson property.
     * 
     * @return
     *     possible object is
     *     {@link EPDPersonType }
     *     
     */
    public EPDPersonType getExemptPerson() {
        return exemptPerson;
    }

    /**
     * Sets the value of the exemptPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link EPDPersonType }
     *     
     */
    public void setExemptPerson(EPDPersonType value) {
        this.exemptPerson = value;
    }

    /**
     * Gets the value of the exemptionCertificateNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExemptionCertificateNum() {
        return exemptionCertificateNum;
    }

    /**
     * Sets the value of the exemptionCertificateNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExemptionCertificateNum(String value) {
        this.exemptionCertificateNum = value;
    }

    /**
     * Gets the value of the monthsExemptStartDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMonthsExemptStartDt() {
        return monthsExemptStartDt;
    }

    /**
     * Sets the value of the monthsExemptStartDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMonthsExemptStartDt(XMLGregorianCalendar value) {
        this.monthsExemptStartDt = value;
    }

    /**
     * Gets the value of the monthsExemptEndDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMonthsExemptEndDt() {
        return monthsExemptEndDt;
    }

    /**
     * Sets the value of the monthsExemptEndDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMonthsExemptEndDt(XMLGregorianCalendar value) {
        this.monthsExemptEndDt = value;
    }

}