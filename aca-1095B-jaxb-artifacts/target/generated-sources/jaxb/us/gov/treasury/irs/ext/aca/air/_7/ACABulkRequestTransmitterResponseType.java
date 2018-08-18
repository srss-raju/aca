//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.ErrorMessageDetailType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;ACA (Affordable Care Act) Bulk Request Transmitter Response Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2014-04-23&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Message for the SOA service for a bulk request from transmitter&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for ACABulkRequestTransmitterResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ACABulkRequestTransmitterResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}TransmissionStatusCd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ReceiptId"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ErrorMessageDetail" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" fixed="1.0" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACABulkRequestTransmitterResponseType", propOrder = {
    "transmissionStatusCd",
    "receiptId",
    "errorMessageDetail"
})
public class ACABulkRequestTransmitterResponseType {

    @XmlElement(name = "TransmissionStatusCd", required = true)
    @XmlSchemaType(name = "string")
    protected TransmissionStatusCodeType transmissionStatusCd;
    @XmlElement(name = "ReceiptId", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String receiptId;
    @XmlElement(name = "ErrorMessageDetail", namespace = "urn:us:gov:treasury:irs:common")
    protected ErrorMessageDetailType errorMessageDetail;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the transmissionStatusCd property.
     * 
     * @return
     *     possible object is
     *     {@link TransmissionStatusCodeType }
     *     
     */
    public TransmissionStatusCodeType getTransmissionStatusCd() {
        return transmissionStatusCd;
    }

    /**
     * Sets the value of the transmissionStatusCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransmissionStatusCodeType }
     *     
     */
    public void setTransmissionStatusCd(TransmissionStatusCodeType value) {
        this.transmissionStatusCd = value;
    }

    /**
     * Gets the value of the receiptId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiptId() {
        return receiptId;
    }

    /**
     * Sets the value of the receiptId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiptId(String value) {
        this.receiptId = value;
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

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "1.0";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}