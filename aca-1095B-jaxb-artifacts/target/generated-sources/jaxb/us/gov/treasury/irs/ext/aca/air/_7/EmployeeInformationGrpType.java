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
import us.gov.treasury.irs.common.TINRequestTypeCodeType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Employee Information Group Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Employee Information Group Details&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for EmployeeInformationGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeInformationGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}OtherCompletePersonName" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}PersonNameControlTxt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}TINRequestTypeCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SSN" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MailingAddressGrp" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeInformationGrpType", propOrder = {
    "otherCompletePersonName",
    "personNameControlTxt",
    "tinRequestTypeCd",
    "ssn",
    "mailingAddressGrp"
})
public class EmployeeInformationGrpType {

    @XmlElement(name = "OtherCompletePersonName")
    protected OtherCompletePersonNameType otherCompletePersonName;
    @XmlElement(name = "PersonNameControlTxt")
    protected String personNameControlTxt;
    @XmlElement(name = "TINRequestTypeCd", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "string")
    protected TINRequestTypeCodeType tinRequestTypeCd;
    @XmlElement(name = "SSN", namespace = "urn:us:gov:treasury:irs:common")
    protected String ssn;
    @XmlElement(name = "MailingAddressGrp")
    protected BusinessAddressGrpType mailingAddressGrp;

    /**
     * Gets the value of the otherCompletePersonName property.
     * 
     * @return
     *     possible object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    public OtherCompletePersonNameType getOtherCompletePersonName() {
        return otherCompletePersonName;
    }

    /**
     * Sets the value of the otherCompletePersonName property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    public void setOtherCompletePersonName(OtherCompletePersonNameType value) {
        this.otherCompletePersonName = value;
    }

    /**
     * Gets the value of the personNameControlTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonNameControlTxt() {
        return personNameControlTxt;
    }

    /**
     * Sets the value of the personNameControlTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonNameControlTxt(String value) {
        this.personNameControlTxt = value;
    }

    /**
     * Gets the value of the tinRequestTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link TINRequestTypeCodeType }
     *     
     */
    public TINRequestTypeCodeType getTINRequestTypeCd() {
        return tinRequestTypeCd;
    }

    /**
     * Sets the value of the tinRequestTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link TINRequestTypeCodeType }
     *     
     */
    public void setTINRequestTypeCd(TINRequestTypeCodeType value) {
        this.tinRequestTypeCd = value;
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
     * Gets the value of the mailingAddressGrp property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessAddressGrpType }
     *     
     */
    public BusinessAddressGrpType getMailingAddressGrp() {
        return mailingAddressGrp;
    }

    /**
     * Sets the value of the mailingAddressGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessAddressGrpType }
     *     
     */
    public void setMailingAddressGrp(BusinessAddressGrpType value) {
        this.mailingAddressGrp = value;
    }

}
