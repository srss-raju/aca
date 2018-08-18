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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:common" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Window Close Notification Service Detail Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2013-01-01&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;Description&gt;Information regarding the window nofitication service detail.&lt;/Description&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for WindowCloseNotificationServiceDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WindowCloseNotificationServiceDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ServiceNm"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ServiceVersionNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ServiceWindowCloseInd"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WindowCloseNotificationServiceDetailType", propOrder = {
    "serviceNm",
    "serviceVersionNum",
    "serviceWindowCloseInd"
})
public class WindowCloseNotificationServiceDetailType {

    @XmlElement(name = "ServiceNm", required = true)
    protected String serviceNm;
    @XmlElement(name = "ServiceVersionNum", required = true)
    protected String serviceVersionNum;
    @XmlElement(name = "ServiceWindowCloseInd", required = true)
    @XmlSchemaType(name = "string")
    protected BooleanStringType serviceWindowCloseInd;

    /**
     * Gets the value of the serviceNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceNm() {
        return serviceNm;
    }

    /**
     * Sets the value of the serviceNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceNm(String value) {
        this.serviceNm = value;
    }

    /**
     * Gets the value of the serviceVersionNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceVersionNum() {
        return serviceVersionNum;
    }

    /**
     * Sets the value of the serviceVersionNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceVersionNum(String value) {
        this.serviceVersionNum = value;
    }

    /**
     * Gets the value of the serviceWindowCloseInd property.
     * 
     * @return
     *     possible object is
     *     {@link BooleanStringType }
     *     
     */
    public BooleanStringType getServiceWindowCloseInd() {
        return serviceWindowCloseInd;
    }

    /**
     * Sets the value of the serviceWindowCloseInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BooleanStringType }
     *     
     */
    public void setServiceWindowCloseInd(BooleanStringType value) {
        this.serviceWindowCloseInd = value;
    }

}
