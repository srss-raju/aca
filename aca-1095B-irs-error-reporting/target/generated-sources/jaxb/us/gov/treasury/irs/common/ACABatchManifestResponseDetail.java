//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.common;

import java.math.BigInteger;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:common" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Affordable Care Act (ACA) Batch Manifest Response Group&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2013-01-01&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;Global type for the ACA Batch Manifest Response Detail Type&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for ACABatchManifestResponseDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ACABatchManifestResponseDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ResponseCd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ResponseCodeDescription"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SystemDocumentId"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SequenceNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ErrorMessageDetail"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface ACABatchManifestResponseDetail {


    /**
     * Gets the value of the responseCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getResponseCd();

    /**
     * Sets the value of the responseCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setResponseCd(String value);

    /**
     * Gets the value of the responseCodeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getResponseCodeDescription();

    /**
     * Sets the value of the responseCodeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setResponseCodeDescription(String value);

    /**
     * Gets the value of the systemDocumentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getSystemDocumentId();

    /**
     * Sets the value of the systemDocumentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setSystemDocumentId(String value);

    /**
     * Gets the value of the sequenceNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    BigInteger getSequenceNum();

    /**
     * Sets the value of the sequenceNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    void setSequenceNum(BigInteger value);

    /**
     * Gets the value of the errorMessageDetail property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorMessageDetail }
     *     
     */
    ErrorMessageDetail getErrorMessageDetail();

    /**
     * Sets the value of the errorMessageDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorMessageDetail }
     *     
     */
    void setErrorMessageDetail(ErrorMessageDetail value);

}
