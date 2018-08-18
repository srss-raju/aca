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
 * 					&lt;DictionaryEntryNm&gt;Monthly Safe Harbor Cd&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;A group for all months&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for MonthlySafeHarborCdType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MonthlySafeHarborCdType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JanSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}FebSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MarSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AprSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MaySafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JunSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JulSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AugSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}SepSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}OctSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}NovSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}DecSafeHarborCd" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface MonthlySafeHarborCdType {


    /**
     * Gets the value of the janSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getJanSafeHarborCd();

    /**
     * Sets the value of the janSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setJanSafeHarborCd(String value);

    /**
     * Gets the value of the febSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getFebSafeHarborCd();

    /**
     * Sets the value of the febSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setFebSafeHarborCd(String value);

    /**
     * Gets the value of the marSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getMarSafeHarborCd();

    /**
     * Sets the value of the marSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setMarSafeHarborCd(String value);

    /**
     * Gets the value of the aprSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getAprSafeHarborCd();

    /**
     * Sets the value of the aprSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setAprSafeHarborCd(String value);

    /**
     * Gets the value of the maySafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getMaySafeHarborCd();

    /**
     * Sets the value of the maySafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setMaySafeHarborCd(String value);

    /**
     * Gets the value of the junSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getJunSafeHarborCd();

    /**
     * Sets the value of the junSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setJunSafeHarborCd(String value);

    /**
     * Gets the value of the julSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getJulSafeHarborCd();

    /**
     * Sets the value of the julSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setJulSafeHarborCd(String value);

    /**
     * Gets the value of the augSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getAugSafeHarborCd();

    /**
     * Sets the value of the augSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setAugSafeHarborCd(String value);

    /**
     * Gets the value of the sepSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getSepSafeHarborCd();

    /**
     * Sets the value of the sepSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setSepSafeHarborCd(String value);

    /**
     * Gets the value of the octSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getOctSafeHarborCd();

    /**
     * Sets the value of the octSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setOctSafeHarborCd(String value);

    /**
     * Gets the value of the novSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getNovSafeHarborCd();

    /**
     * Sets the value of the novSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setNovSafeHarborCd(String value);

    /**
     * Gets the value of the decSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getDecSafeHarborCd();

    /**
     * Sets the value of the decSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setDecSafeHarborCd(String value);

}
