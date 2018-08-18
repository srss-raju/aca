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
import us.gov.treasury.irs.common.TINRequestTypeCodeType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Form 1094-B Upstream Detail Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;2&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2015-04-20&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;Form 1094-B - Transmittal of Health Coverage Information Returns&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for Form1094BUpstreamDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Form1094BUpstreamDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}SubmissionId"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}OriginalUniqueSubmissionId" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}TestScenarioId" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}TaxYr"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}BusinessName" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}BusinessNameControlTxt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}TINRequestTypeCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}EmployerEIN"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ContactNameGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ContactPhoneNum" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MailingAddressGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}Form1095BAttachedCnt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}Form1095BUpstreamDetail" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JuratSignaturePIN" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}PersonTitleTxt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SignatureDt" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="recordType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="lineNum" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface Form1094BUpstreamDetail {


    /**
     * Gets the value of the submissionId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    BigInteger getSubmissionId();

    /**
     * Sets the value of the submissionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    void setSubmissionId(BigInteger value);

    /**
     * Gets the value of the originalUniqueSubmissionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getOriginalUniqueSubmissionId();

    /**
     * Sets the value of the originalUniqueSubmissionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setOriginalUniqueSubmissionId(String value);

    /**
     * Gets the value of the testScenarioId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getTestScenarioId();

    /**
     * Sets the value of the testScenarioId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setTestScenarioId(String value);

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
     * Gets the value of the employerEIN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getEmployerEIN();

    /**
     * Sets the value of the employerEIN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setEmployerEIN(String value);

    /**
     * Gets the value of the contactNameGrp property.
     * 
     * @return
     *     possible object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    OtherCompletePersonNameType getContactNameGrp();

    /**
     * Sets the value of the contactNameGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    void setContactNameGrp(OtherCompletePersonNameType value);

    /**
     * Gets the value of the contactPhoneNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getContactPhoneNum();

    /**
     * Sets the value of the contactPhoneNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setContactPhoneNum(String value);

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

    /**
     * Gets the value of the form1095BAttachedCnt property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    BigInteger getForm1095BAttachedCnt();

    /**
     * Sets the value of the form1095BAttachedCnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    void setForm1095BAttachedCnt(BigInteger value);

    /**
     * Gets the value of the form1095BUpstreamDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the form1095BUpstreamDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getForm1095BUpstreamDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Form1095BUpstreamDetail }
     * 
     * 
     */
    List<Form1095BUpstreamDetail> getForm1095BUpstreamDetails();

    /**
     * Gets the value of the juratSignaturePIN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getJuratSignaturePIN();

    /**
     * Sets the value of the juratSignaturePIN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setJuratSignaturePIN(String value);

    /**
     * Gets the value of the personTitleTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getPersonTitleTxt();

    /**
     * Sets the value of the personTitleTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setPersonTitleTxt(String value);

    /**
     * Gets the value of the signatureDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getSignatureDt();

    /**
     * Sets the value of the signatureDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setSignatureDt(XMLGregorianCalendar value);

    /**
     * Gets the value of the recordType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getRecordType();

    /**
     * Sets the value of the recordType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setRecordType(String value);

    /**
     * Gets the value of the lineNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    BigInteger getLineNum();

    /**
     * Sets the value of the lineNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    void setLineNum(BigInteger value);

}