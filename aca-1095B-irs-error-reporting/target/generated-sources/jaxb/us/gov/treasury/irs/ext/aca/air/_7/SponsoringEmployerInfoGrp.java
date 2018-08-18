//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import us.gov.treasury.irs.common.TINRequestTypeCodeType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Sponsoring Employer Information Group Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;Type definition for employer providing employer sponsored coverage&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for SponsoringEmployerInfoGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SponsoringEmployerInfoGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}BusinessName" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}BusinessNameControlTxt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}EIN" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}TINRequestTypeCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MailingAddressGrp" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface SponsoringEmployerInfoGrp {


    /**
     * Gets the value of the businessName property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessNameType }
     *     
     */
    BusinessNameType getBusinessName();

    /**
     * Sets the value of the businessName property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessNameType }
     *     
     */
    void setBusinessName(BusinessNameType value);

    /**
     * Gets the value of the businessNameControlTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getBusinessNameControlTxt();

    /**
     * Sets the value of the businessNameControlTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setBusinessNameControlTxt(String value);

    /**
     * Gets the value of the ein property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getEIN();

    /**
     * Sets the value of the ein property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setEIN(String value);

    /**
     * Gets the value of the tinRequestTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link TINRequestTypeCodeType }
     *     
     */
    TINRequestTypeCodeType getTINRequestTypeCd();

    /**
     * Sets the value of the tinRequestTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link TINRequestTypeCodeType }
     *     
     */
    void setTINRequestTypeCd(TINRequestTypeCodeType value);

    /**
     * Gets the value of the mailingAddressGrp property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessAddressGrpType }
     *     
     */
    BusinessAddressGrpType getMailingAddressGrp();

    /**
     * Sets the value of the mailingAddressGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessAddressGrpType }
     *     
     */
    void setMailingAddressGrp(BusinessAddressGrpType value);

}
