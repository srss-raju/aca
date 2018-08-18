//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.common;

import java.util.List;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:common" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Tax Household Coverage Error Detail Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2013-06-19&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;Tax household errors in exchange generated monthly report&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for TaxHouseholdCoverageErrorDtlType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxHouseholdCoverageErrorDtlType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element ref="{urn:us:gov:treasury:irs:common}HouseholdErrorDtl" minOccurs="0"/&gt;
 *           &lt;element ref="{urn:us:gov:treasury:irs:common}OtherRelevantAdultErrorDtl" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}EPDErrorDetail" maxOccurs="99" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface TaxHouseholdCoverageErrorDtl {


    /**
     * Gets the value of the otherRelevantAdultErrorDtl property.
     * 
     * @return
     *     possible object is
     *     {@link PersonInformationErrorDtlType }
     *     
     */
    PersonInformationErrorDtlType getOtherRelevantAdultErrorDtl();

    /**
     * Sets the value of the otherRelevantAdultErrorDtl property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInformationErrorDtlType }
     *     
     */
    void setOtherRelevantAdultErrorDtl(PersonInformationErrorDtlType value);

    /**
     * Gets the value of the householdErrorDtl property.
     * 
     * @return
     *     possible object is
     *     {@link HouseholdErrorDtl }
     *     
     */
    HouseholdErrorDtl getHouseholdErrorDtl();

    /**
     * Sets the value of the householdErrorDtl property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseholdErrorDtl }
     *     
     */
    void setHouseholdErrorDtl(HouseholdErrorDtl value);

    /**
     * Gets the value of the epdErrorDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the epdErrorDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEPDErrorDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EPDErrorDetailType }
     * 
     * 
     */
    List<EPDErrorDetailType> getEPDErrorDetails();

}