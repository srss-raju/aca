//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.common;



/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:common" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Other Complete Person Name Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2014-06-04&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;CACInd&gt;Yes&lt;/CACInd&gt;
 * 					&lt;Status&gt;Active&lt;/Status&gt;
 * 					&lt;DescriptionTxt&gt;Global type definition for person's full name where all elements are optional.&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for OtherCompletePersonNameType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OtherCompletePersonNameType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}PersonFirstNm"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}PersonMiddleNm" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}PersonLastNm"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SuffixNm" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface OtherCompletePersonName {


    /**
     * Gets the value of the personFirstNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getPersonFirstNm();

    /**
     * Sets the value of the personFirstNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setPersonFirstNm(String value);

    /**
     * Gets the value of the personMiddleNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getPersonMiddleNm();

    /**
     * Sets the value of the personMiddleNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setPersonMiddleNm(String value);

    /**
     * Gets the value of the personLastNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getPersonLastNm();

    /**
     * Sets the value of the personLastNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setPersonLastNm(String value);

    /**
     * Gets the value of the suffixNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getSuffixNm();

    /**
     * Sets the value of the suffixNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setSuffixNm(String value);

}
