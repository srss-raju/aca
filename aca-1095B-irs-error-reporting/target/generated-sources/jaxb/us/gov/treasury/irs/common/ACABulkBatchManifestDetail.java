//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.common;

import javax.activation.DataHandler;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:common" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Affordable Care Act (ACA) HHS Batch Manifest Detail Group&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2013-01-01&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;Global type for the ACA HHS (includes Bulk File) Batch Manifest Detail Type&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for ACABulkBatchManifestDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ACABulkBatchManifestDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ACABatchManifestDetail"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}BulkExchangeFile"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface ACABulkBatchManifestDetail {


    /**
     * Gets the value of the acaBatchManifestDetail property.
     * 
     * @return
     *     possible object is
     *     {@link ACABatchManifestDetail }
     *     
     */
    ACABatchManifestDetail getACABatchManifestDetail();

    /**
     * Sets the value of the acaBatchManifestDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ACABatchManifestDetail }
     *     
     */
    void setACABatchManifestDetail(ACABatchManifestDetail value);

    /**
     * Gets the value of the bulkExchangeFile property.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    DataHandler getBulkExchangeFile();

    /**
     * Sets the value of the bulkExchangeFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    void setBulkExchangeFile(DataHandler value);

}