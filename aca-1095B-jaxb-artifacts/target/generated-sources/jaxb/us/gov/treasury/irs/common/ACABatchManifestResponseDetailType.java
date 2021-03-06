//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.common;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:common" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Affordable Care Act (ACA) Batch Manifest Response Group&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2013-01-01&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Global type for the ACA Batch Manifest Response Detail Type&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACABatchManifestResponseDetailType", propOrder = {
    "responseCd",
    "responseCodeDescription",
    "systemDocumentId",
    "sequenceNum",
    "errorMessageDetail"
})
public class ACABatchManifestResponseDetailType {

    @XmlElement(name = "ResponseCd", required = true)
    protected String responseCd;
    @XmlElement(name = "ResponseCodeDescription", required = true)
    protected String responseCodeDescription;
    @XmlElement(name = "SystemDocumentId", required = true)
    protected String systemDocumentId;
    @XmlElement(name = "SequenceNum", required = true)
    protected BigInteger sequenceNum;
    @XmlElement(name = "ErrorMessageDetail", required = true)
    protected ErrorMessageDetailType errorMessageDetail;

    /**
     * Gets the value of the responseCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseCd() {
        return responseCd;
    }

    /**
     * Sets the value of the responseCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseCd(String value) {
        this.responseCd = value;
    }

    /**
     * Gets the value of the responseCodeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseCodeDescription() {
        return responseCodeDescription;
    }

    /**
     * Sets the value of the responseCodeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseCodeDescription(String value) {
        this.responseCodeDescription = value;
    }

    /**
     * Gets the value of the systemDocumentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemDocumentId() {
        return systemDocumentId;
    }

    /**
     * Sets the value of the systemDocumentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemDocumentId(String value) {
        this.systemDocumentId = value;
    }

    /**
     * Gets the value of the sequenceNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSequenceNum() {
        return sequenceNum;
    }

    /**
     * Sets the value of the sequenceNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSequenceNum(BigInteger value) {
        this.sequenceNum = value;
    }

    /**
     * Gets the value of the errorMessageDetail property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorMessageDetailType }
     *     
     */
    public ErrorMessageDetailType getErrorMessageDetail() {
        return errorMessageDetail;
    }

    /**
     * Sets the value of the errorMessageDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorMessageDetailType }
     *     
     */
    public void setErrorMessageDetail(ErrorMessageDetailType value) {
        this.errorMessageDetail = value;
    }

}
