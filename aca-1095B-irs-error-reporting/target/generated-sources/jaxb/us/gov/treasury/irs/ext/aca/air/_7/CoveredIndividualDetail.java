//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.common.EPDErrorDetailType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Covered IndividualType&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2014-04-09&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;All the elements associated with an covered
 * 						individual on exchange generated monthly report
 * 					&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for CoveredIndividualDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CoveredIndividualDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}CompletePersonNameDetail"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SSN" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}BirthDt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}TINValidateNameResponseDetail"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}CoverageStartDt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}CoverageEndDt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ErrorDetail" maxOccurs="99" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface CoveredIndividualDetail {


    /**
     * Gets the value of the completePersonNameDetail property.
     * 
     * @return
     *     possible object is
     *     {@link CompletePersonNameDetailType }
     *     
     */
    CompletePersonNameDetailType getCompletePersonNameDetail();

    /**
     * Sets the value of the completePersonNameDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompletePersonNameDetailType }
     *     
     */
    void setCompletePersonNameDetail(CompletePersonNameDetailType value);

    /**
     * Gets the value of the ssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getSSN();

    /**
     * Sets the value of the ssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setSSN(String value);

    /**
     * Gets the value of the birthDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getBirthDt();

    /**
     * Sets the value of the birthDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setBirthDt(XMLGregorianCalendar value);

    /**
     * Gets the value of the tinValidateNameResponseDetail property.
     * 
     * @return
     *     possible object is
     *     {@link TINValidateNameResponseDetailType }
     *     
     */
    TINValidateNameResponseDetailType getTINValidateNameResponseDetail();

    /**
     * Sets the value of the tinValidateNameResponseDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link TINValidateNameResponseDetailType }
     *     
     */
    void setTINValidateNameResponseDetail(TINValidateNameResponseDetailType value);

    /**
     * Gets the value of the coverageStartDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getCoverageStartDt();

    /**
     * Sets the value of the coverageStartDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setCoverageStartDt(XMLGregorianCalendar value);

    /**
     * Gets the value of the coverageEndDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getCoverageEndDt();

    /**
     * Sets the value of the coverageEndDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setCoverageEndDt(XMLGregorianCalendar value);

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
