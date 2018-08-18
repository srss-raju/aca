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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;ACA Bulk Request Transmitter Request Group Header Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2012-09-01&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;Description&gt;A group header that provides ACA Bulk form submission request message related information&lt;/Description&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for ACABulkReqTrnsmtReqGrpHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ACABulkReqTrnsmtReqGrpHeaderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}UserSiteMinderSessionTxt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}AuthorizationTxt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}UserId"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ACABusinessCorrelationId"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}MessageSentTs"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACABulkReqTrnsmtReqGrpHeaderType", propOrder = {
    "userSiteMinderSessionTxt",
    "authorizationTxt",
    "userId",
    "acaBusinessCorrelationId",
    "messageSentTs"
})
public class ACABulkReqTrnsmtReqGrpHeaderType {

    @XmlElement(name = "UserSiteMinderSessionTxt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String userSiteMinderSessionTxt;
    @XmlElement(name = "AuthorizationTxt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String authorizationTxt;
    @XmlElement(name = "UserId", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String userId;
    @XmlElement(name = "ACABusinessCorrelationId", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String acaBusinessCorrelationId;
    @XmlElement(name = "MessageSentTs", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String messageSentTs;

    /**
     * Gets the value of the userSiteMinderSessionTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserSiteMinderSessionTxt() {
        return userSiteMinderSessionTxt;
    }

    /**
     * Sets the value of the userSiteMinderSessionTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserSiteMinderSessionTxt(String value) {
        this.userSiteMinderSessionTxt = value;
    }

    /**
     * Gets the value of the authorizationTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationTxt() {
        return authorizationTxt;
    }

    /**
     * Sets the value of the authorizationTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationTxt(String value) {
        this.authorizationTxt = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the acaBusinessCorrelationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACABusinessCorrelationId() {
        return acaBusinessCorrelationId;
    }

    /**
     * Sets the value of the acaBusinessCorrelationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACABusinessCorrelationId(String value) {
        this.acaBusinessCorrelationId = value;
    }

    /**
     * Gets the value of the messageSentTs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageSentTs() {
        return messageSentTs;
    }

    /**
     * Sets the value of the messageSentTs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageSentTs(String value) {
        this.messageSentTs = value;
    }

}
