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
 * 					&lt;DictionaryEntryNm&gt;Offer Coverage By Month Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;Offer Coverage By Month Type&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for OfferCoverageByMonthType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OfferCoverageByMonthType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JanOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}FebOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MarOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AprOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MayOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JunOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JulOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AugOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}SepOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}OctOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}NovOfferCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}DecOfferCd" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface MonthlyOfferCoverageGrp {


    /**
     * Gets the value of the janOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getJanOfferCd();

    /**
     * Sets the value of the janOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setJanOfferCd(String value);

    /**
     * Gets the value of the febOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getFebOfferCd();

    /**
     * Sets the value of the febOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setFebOfferCd(String value);

    /**
     * Gets the value of the marOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getMarOfferCd();

    /**
     * Sets the value of the marOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setMarOfferCd(String value);

    /**
     * Gets the value of the aprOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getAprOfferCd();

    /**
     * Sets the value of the aprOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setAprOfferCd(String value);

    /**
     * Gets the value of the mayOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getMayOfferCd();

    /**
     * Sets the value of the mayOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setMayOfferCd(String value);

    /**
     * Gets the value of the junOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getJunOfferCd();

    /**
     * Sets the value of the junOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setJunOfferCd(String value);

    /**
     * Gets the value of the julOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getJulOfferCd();

    /**
     * Sets the value of the julOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setJulOfferCd(String value);

    /**
     * Gets the value of the augOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getAugOfferCd();

    /**
     * Sets the value of the augOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setAugOfferCd(String value);

    /**
     * Gets the value of the sepOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getSepOfferCd();

    /**
     * Sets the value of the sepOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setSepOfferCd(String value);

    /**
     * Gets the value of the octOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getOctOfferCd();

    /**
     * Sets the value of the octOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setOctOfferCd(String value);

    /**
     * Gets the value of the novOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getNovOfferCd();

    /**
     * Sets the value of the novOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setNovOfferCd(String value);

    /**
     * Gets the value of the decOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getDecOfferCd();

    /**
     * Sets the value of the decOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setDecOfferCd(String value);

}
