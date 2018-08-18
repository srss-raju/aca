//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.math.BigInteger;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.common.EPDErrorDetailType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DescriptionTxt&gt;T Record Detail&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for TransmitterRecordDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransmitterRecordDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}UniqueTransmitterId"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}TaxYr"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}TransmitterControlCd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}TotalPayeeRecordCnt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}TotalPayerRecordCnt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ActualReceiptDt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AdjustedReceiptDt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ShipmentRecordNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}PriorYrFilingInd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ErrorDetail" maxOccurs="99" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface TransmitterRecordDetail {


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
     * Gets the value of the taxYr property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getTaxYr();

    /**
     * Sets the value of the taxYr property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setTaxYr(XMLGregorianCalendar value);

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
     * Gets the value of the totalPayeeRecordCnt property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    BigInteger getTotalPayeeRecordCnt();

    /**
     * Sets the value of the totalPayeeRecordCnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    void setTotalPayeeRecordCnt(BigInteger value);

    /**
     * Gets the value of the totalPayerRecordCnt property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    BigInteger getTotalPayerRecordCnt();

    /**
     * Sets the value of the totalPayerRecordCnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    void setTotalPayerRecordCnt(BigInteger value);

    /**
     * Gets the value of the actualReceiptDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getActualReceiptDt();

    /**
     * Sets the value of the actualReceiptDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setActualReceiptDt(XMLGregorianCalendar value);

    /**
     * Gets the value of the adjustedReceiptDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getAdjustedReceiptDt();

    /**
     * Sets the value of the adjustedReceiptDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setAdjustedReceiptDt(XMLGregorianCalendar value);

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
     * Gets the value of the priorYrFilingInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getPriorYrFilingInd();

    /**
     * Sets the value of the priorYrFilingInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setPriorYrFilingInd(String value);

    /**
     * Gets the value of the errorDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EPDErrorDetailType }
     * 
     * 
     */
    List<EPDErrorDetailType> getErrorDetails();

}
