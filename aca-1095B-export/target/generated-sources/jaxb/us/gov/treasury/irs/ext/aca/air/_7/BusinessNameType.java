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
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Business Name Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2008-01-08&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;EFileTypes, December 14, 2007&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Type for a Business Name, 2 Name Lines&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for BusinessNameType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessNameType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}BusinessNameLine1Txt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}BusinessNameLine2Txt" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessNameType", propOrder = {
    "businessNameLine1Txt",
    "businessNameLine2Txt"
})
public class BusinessNameType {

    @XmlElement(name = "BusinessNameLine1Txt", required = true)
    protected String businessNameLine1Txt;
    @XmlElement(name = "BusinessNameLine2Txt")
    protected String businessNameLine2Txt;

    /**
     * Gets the value of the businessNameLine1Txt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessNameLine1Txt() {
        return businessNameLine1Txt;
    }

    /**
     * Sets the value of the businessNameLine1Txt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessNameLine1Txt(String value) {
        this.businessNameLine1Txt = value;
    }

    /**
     * Gets the value of the businessNameLine2Txt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessNameLine2Txt() {
        return businessNameLine2Txt;
    }

    /**
     * Sets the value of the businessNameLine2Txt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessNameLine2Txt(String value) {
        this.businessNameLine2Txt = value;
    }

}
