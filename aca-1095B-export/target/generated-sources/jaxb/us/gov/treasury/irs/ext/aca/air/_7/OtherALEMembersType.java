//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:05 PM IST 
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
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Other ALE Members of Aggregated ALE Group Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Other ALE Members of Aggregated ALE Group type at anytime during the year&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for OtherALEMembersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OtherALEMembersType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}BusinessName" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}BusinessNameControlTxt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}TINRequestTypeCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}EIN" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OtherALEMembersType", propOrder = {
    "businessName",
    "businessNameControlTxt",
    "tinRequestTypeCd",
    "ein"
})
public class OtherALEMembersType {

    @XmlElement(name = "BusinessName")
    protected BusinessNameType businessName;
    @XmlElement(name = "BusinessNameControlTxt")
    protected String businessNameControlTxt;
    @XmlElement(name = "TINRequestTypeCd", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "string")
    protected TINRequestTypeCodeType tinRequestTypeCd;
    @XmlElement(name = "EIN", namespace = "urn:us:gov:treasury:irs:common")
    protected String ein;

    /**
     * Gets the value of the businessName property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessNameType }
     *     
     */
    public BusinessNameType getBusinessName() {
        return businessName;
    }

    /**
     * Sets the value of the businessName property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessNameType }
     *     
     */
    public void setBusinessName(BusinessNameType value) {
        this.businessName = value;
    }

    /**
     * Gets the value of the businessNameControlTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessNameControlTxt() {
        return businessNameControlTxt;
    }

    /**
     * Sets the value of the businessNameControlTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessNameControlTxt(String value) {
        this.businessNameControlTxt = value;
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
     * Gets the value of the ein property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEIN() {
        return ein;
    }

    /**
     * Sets the value of the ein property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEIN(String value) {
        this.ein = value;
    }

}
