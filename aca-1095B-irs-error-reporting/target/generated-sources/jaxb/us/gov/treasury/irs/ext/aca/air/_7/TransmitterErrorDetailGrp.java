//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.util.List;
import us.gov.treasury.irs.common.ErrorMessageDetail;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;ACA Transmitter Error Detail Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2014-10-31&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;1094-1095B/C affordable Insurance Exchange response statement file from IRS to transmitter&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for TransmitterErrorDetailGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransmitterErrorDetailGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}SubmissionLevelStatusCd" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}UniqueSubmissionId"/&gt;
 *           &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}UniqueRecordId"/&gt;
 *           &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}SchemaErrorInfo"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ErrorMessageDetail" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface TransmitterErrorDetailGrp {


    /**
     * Gets the value of the submissionLevelStatusCd property.
     * 
     * @return
     *     possible object is
     *     {@link SubmissionLevelStatusCodeType }
     *     
     */
    SubmissionLevelStatusCodeType getSubmissionLevelStatusCd();

    /**
     * Sets the value of the submissionLevelStatusCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubmissionLevelStatusCodeType }
     *     
     */
    void setSubmissionLevelStatusCd(SubmissionLevelStatusCodeType value);

    /**
     * Gets the value of the schemaErrorInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getSchemaErrorInfo();

    /**
     * Sets the value of the schemaErrorInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setSchemaErrorInfo(String value);

    /**
     * Gets the value of the uniqueRecordId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getUniqueRecordId();

    /**
     * Sets the value of the uniqueRecordId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setUniqueRecordId(String value);

    /**
     * Gets the value of the uniqueSubmissionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getUniqueSubmissionId();

    /**
     * Sets the value of the uniqueSubmissionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setUniqueSubmissionId(String value);

    /**
     * Gets the value of the errorMessageDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorMessageDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorMessageDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorMessageDetail }
     * 
     * 
     */
    List<ErrorMessageDetail> getErrorMessageDetails();

}
