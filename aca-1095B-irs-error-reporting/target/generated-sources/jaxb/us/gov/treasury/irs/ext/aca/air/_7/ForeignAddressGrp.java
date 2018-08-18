//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;



/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Foreign Address Group Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2013-01-25&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;A group that wraps detail associated with a generic foreign address&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for ForeignAddressGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ForeignAddressGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AddressLine1Txt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AddressLine2Txt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}CityNm" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}CountryCd"/&gt;
 *           &lt;element ref="{urn:us:gov:treasury:irs:common}CountryNm"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ForeignProvinceNm" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ForeignPostalCd" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface ForeignAddressGrp {


    /**
     * Gets the value of the addressLine1Txt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getAddressLine1Txt();

    /**
     * Sets the value of the addressLine1Txt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setAddressLine1Txt(String value);

    /**
     * Gets the value of the addressLine2Txt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getAddressLine2Txt();

    /**
     * Sets the value of the addressLine2Txt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setAddressLine2Txt(String value);

    /**
     * Gets the value of the cityNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getCityNm();

    /**
     * Sets the value of the cityNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setCityNm(String value);

    /**
     * Gets the value of the countryNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getCountryNm();

    /**
     * Sets the value of the countryNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setCountryNm(String value);

    /**
     * Gets the value of the countryCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getCountryCd();

    /**
     * Sets the value of the countryCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setCountryCd(String value);

    /**
     * Gets the value of the foreignProvinceNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getForeignProvinceNm();

    /**
     * Sets the value of the foreignProvinceNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setForeignProvinceNm(String value);

    /**
     * Gets the value of the foreignPostalCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getForeignPostalCd();

    /**
     * Sets the value of the foreignPostalCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setForeignPostalCd(String value);

}