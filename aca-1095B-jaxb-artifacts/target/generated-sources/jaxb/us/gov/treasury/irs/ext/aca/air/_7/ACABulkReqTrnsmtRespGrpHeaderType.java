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
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.common.SubmissionStatusCodeType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;ACA Bulk Request Transmitter Response Group Header Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2012-09-01&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;Description&gt;A group header that provides ACA Bulk form submission response message related information&lt;/Description&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for ACABulkReqTrnsmtRespGrpHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ACABulkReqTrnsmtRespGrpHeaderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ACABusinessCorrelationId"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}MessageSentTs"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ReceiptId"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SubmissionReceivedTs"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SubmissionStatusCd"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACABulkReqTrnsmtRespGrpHeaderType", propOrder = {
    "acaBusinessCorrelationId",
    "messageSentTs",
    "receiptId",
    "submissionReceivedTs",
    "submissionStatusCd"
})
public class ACABulkReqTrnsmtRespGrpHeaderType {

    @XmlElement(name = "ACABusinessCorrelationId", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String acaBusinessCorrelationId;
    @XmlElement(name = "MessageSentTs", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String messageSentTs;
    @XmlElement(name = "ReceiptId", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String receiptId;
    @XmlElement(name = "SubmissionReceivedTs", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar submissionReceivedTs;
    @XmlElement(name = "SubmissionStatusCd", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlSchemaType(name = "string")
    protected SubmissionStatusCodeType submissionStatusCd;

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
     * Gets the value of the submissionReceivedTs property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSubmissionReceivedTs() {
        return submissionReceivedTs;
    }

    /**
     * Sets the value of the submissionReceivedTs property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSubmissionReceivedTs(XMLGregorianCalendar value) {
        this.submissionReceivedTs = value;
    }

    /**
     * Gets the value of the submissionStatusCd property.
     * 
     * @return
     *     possible object is
     *     {@link SubmissionStatusCodeType }
     *     
     */
    public SubmissionStatusCodeType getSubmissionStatusCd() {
        return submissionStatusCd;
    }

    /**
     * Sets the value of the submissionStatusCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubmissionStatusCodeType }
     *     
     */
    public void setSubmissionStatusCd(SubmissionStatusCodeType value) {
        this.submissionStatusCd = value;
    }

}