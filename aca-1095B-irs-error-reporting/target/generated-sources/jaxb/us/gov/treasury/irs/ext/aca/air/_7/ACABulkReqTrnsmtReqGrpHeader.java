//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;



/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;ACA Bulk Request Transmitter Request Group Header Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2012-09-01&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;Description&gt;A group header that provides ACA Bulk form submission request message related information&lt;/Description&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
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
public interface ACABulkReqTrnsmtReqGrpHeader {


    /**
     * Gets the value of the userSiteMinderSessionTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getUserSiteMinderSessionTxt();

    /**
     * Sets the value of the userSiteMinderSessionTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setUserSiteMinderSessionTxt(String value);

    /**
     * Gets the value of the authorizationTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getAuthorizationTxt();

    /**
     * Sets the value of the authorizationTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setAuthorizationTxt(String value);

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getUserId();

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setUserId(String value);

    /**
     * Gets the value of the acaBusinessCorrelationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getACABusinessCorrelationId();

    /**
     * Sets the value of the acaBusinessCorrelationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setACABusinessCorrelationId(String value);

    /**
     * Gets the value of the messageSentTs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getMessageSentTs();

    /**
     * Sets the value of the messageSentTs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setMessageSentTs(String value);

}
