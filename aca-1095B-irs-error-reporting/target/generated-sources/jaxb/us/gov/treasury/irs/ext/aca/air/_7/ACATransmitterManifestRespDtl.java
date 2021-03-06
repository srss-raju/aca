//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.math.BigInteger;
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.common.ErrorMessageDetail;
import us.gov.treasury.irs.common.SubmissionStatusCodeType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Affordable Care Act (ACA) Transmitter Response Manifest Detail Group&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2014-11-03&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;Affordable Care Act (ACA) Transmitter Manifest Response Detail Group&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for ACATrnsmtManifestRespDtlType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ACATrnsmtManifestRespDtlType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}UniqueTransmitterId"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}TransmitterControlCd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ShipmentRecordNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ReceiptId"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}FormTypeCd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}Timestamp"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SubmissionStatusCd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ErrorMessageDetail"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}DocumentSystemFileNm"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ChecksumAugmentationNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}AttachmentByteSizeNum" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface ACATransmitterManifestRespDtl {


    /**
     * Gets the value of the uniqueTransmitterId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getUniqueTransmitterId();

    /**
     * Sets the value of the uniqueTransmitterId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setUniqueTransmitterId(String value);

    /**
     * Gets the value of the transmitterControlCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getTransmitterControlCd();

    /**
     * Sets the value of the transmitterControlCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setTransmitterControlCd(String value);

    /**
     * Gets the value of the shipmentRecordNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getShipmentRecordNum();

    /**
     * Sets the value of the shipmentRecordNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setShipmentRecordNum(String value);

    /**
     * Gets the value of the receiptId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getReceiptId();

    /**
     * Sets the value of the receiptId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setReceiptId(String value);

    /**
     * Gets the value of the formTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getFormTypeCd();

    /**
     * Sets the value of the formTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setFormTypeCd(String value);

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getTimestamp();

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setTimestamp(XMLGregorianCalendar value);

    /**
     * Gets the value of the submissionStatusCd property.
     * 
     * @return
     *     possible object is
     *     {@link SubmissionStatusCodeType }
     *     
     */
    SubmissionStatusCodeType getSubmissionStatusCd();

    /**
     * Sets the value of the submissionStatusCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubmissionStatusCodeType }
     *     
     */
    void setSubmissionStatusCd(SubmissionStatusCodeType value);

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

    /**
     * Gets the value of the documentSystemFileNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getDocumentSystemFileNm();

    /**
     * Sets the value of the documentSystemFileNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setDocumentSystemFileNm(String value);

    /**
     * Gets the value of the checksumAugmentationNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getChecksumAugmentationNum();

    /**
     * Sets the value of the checksumAugmentationNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setChecksumAugmentationNum(String value);

    /**
     * Gets the value of the attachmentByteSizeNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    BigInteger getAttachmentByteSizeNum();

    /**
     * Sets the value of the attachmentByteSizeNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    void setAttachmentByteSizeNum(BigInteger value);

}
