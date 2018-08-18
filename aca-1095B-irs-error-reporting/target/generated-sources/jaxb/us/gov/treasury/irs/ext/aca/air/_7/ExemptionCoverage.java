//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Exemption Coverage Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2013-05-17&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;All the elements associated with an Exemption coverage on a month&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for ExemptionCoverageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExemptionCoverageType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ApplicableCoverageMonthNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ExemptPerson"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ExemptionCertificateNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}MonthsExemptStartDt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}MonthsExemptEndDt"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface ExemptionCoverage {


    /**
     * Gets the value of the applicableCoverageMonthNum property.
     * 
     */
    int getApplicableCoverageMonthNum();

    /**
     * Sets the value of the applicableCoverageMonthNum property.
     * 
     */
    void setApplicableCoverageMonthNum(int value);

    /**
     * Gets the value of the exemptPerson property.
     * 
     * @return
     *     possible object is
     *     {@link EPDPersonType }
     *     
     */
    EPDPersonType getExemptPerson();

    /**
     * Sets the value of the exemptPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link EPDPersonType }
     *     
     */
    void setExemptPerson(EPDPersonType value);

    /**
     * Gets the value of the exemptionCertificateNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getExemptionCertificateNum();

    /**
     * Sets the value of the exemptionCertificateNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setExemptionCertificateNum(String value);

    /**
     * Gets the value of the monthsExemptStartDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getMonthsExemptStartDt();

    /**
     * Sets the value of the monthsExemptStartDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setMonthsExemptStartDt(XMLGregorianCalendar value);

    /**
     * Gets the value of the monthsExemptEndDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    XMLGregorianCalendar getMonthsExemptEndDt();

    /**
     * Sets the value of the monthsExemptEndDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    void setMonthsExemptEndDt(XMLGregorianCalendar value);

}
