//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:common" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Exchange Periodic Data Errror Detail Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2013-03-05&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;Description&gt;A group that provides EPD error message related information&lt;/Description&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for EPDErrorDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EPDErrorDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ErrorMessageCd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ErrorMessageTxt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ErrorValueTxt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}XpathContent" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EPDErrorDetailType", propOrder = {
    "errorMessageCd",
    "errorMessageTxt",
    "errorValueTxt",
    "xpathContent"
})
public class EPDErrorDetailType {

    @XmlElement(name = "ErrorMessageCd", required = true)
    protected String errorMessageCd;
    @XmlElement(name = "ErrorMessageTxt")
    protected String errorMessageTxt;
    @XmlElement(name = "ErrorValueTxt")
    protected String errorValueTxt;
    @XmlElement(name = "XpathContent")
    protected String xpathContent;

    /**
     * Gets the value of the errorMessageCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessageCd() {
        return errorMessageCd;
    }

    /**
     * Sets the value of the errorMessageCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessageCd(String value) {
        this.errorMessageCd = value;
    }

    /**
     * Gets the value of the errorMessageTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessageTxt() {
        return errorMessageTxt;
    }

    /**
     * Sets the value of the errorMessageTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessageTxt(String value) {
        this.errorMessageTxt = value;
    }

    /**
     * Gets the value of the errorValueTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorValueTxt() {
        return errorValueTxt;
    }

    /**
     * Sets the value of the errorValueTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorValueTxt(String value) {
        this.errorValueTxt = value;
    }

    /**
     * Gets the value of the xpathContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXpathContent() {
        return xpathContent;
    }

    /**
     * Sets the value of the xpathContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXpathContent(String value) {
        this.xpathContent = value;
    }

}