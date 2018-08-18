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
 * 					&lt;DictionaryEntryNm&gt;Month Indicator Group Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;A group which contains indicators for each month of the year.&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for MonthIndGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MonthIndGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JanuaryInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}FebruaryInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MarchInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AprilInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MayInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JuneInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JulyInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AugustInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}SeptemberInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}OctoberInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}NovemberInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}DecemberInd" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface CoveredIndividualMonthlyIndGrp {


    /**
     * Gets the value of the januaryInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getJanuaryInd();

    /**
     * Sets the value of the januaryInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setJanuaryInd(String value);

    /**
     * Gets the value of the februaryInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getFebruaryInd();

    /**
     * Sets the value of the februaryInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setFebruaryInd(String value);

    /**
     * Gets the value of the marchInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getMarchInd();

    /**
     * Sets the value of the marchInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setMarchInd(String value);

    /**
     * Gets the value of the aprilInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getAprilInd();

    /**
     * Sets the value of the aprilInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setAprilInd(String value);

    /**
     * Gets the value of the mayInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getMayInd();

    /**
     * Sets the value of the mayInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setMayInd(String value);

    /**
     * Gets the value of the juneInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getJuneInd();

    /**
     * Sets the value of the juneInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setJuneInd(String value);

    /**
     * Gets the value of the julyInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getJulyInd();

    /**
     * Sets the value of the julyInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setJulyInd(String value);

    /**
     * Gets the value of the augustInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getAugustInd();

    /**
     * Sets the value of the augustInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setAugustInd(String value);

    /**
     * Gets the value of the septemberInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getSeptemberInd();

    /**
     * Sets the value of the septemberInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setSeptemberInd(String value);

    /**
     * Gets the value of the octoberInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getOctoberInd();

    /**
     * Sets the value of the octoberInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setOctoberInd(String value);

    /**
     * Gets the value of the novemberInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getNovemberInd();

    /**
     * Sets the value of the novemberInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setNovemberInd(String value);

    /**
     * Gets the value of the decemberInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getDecemberInd();

    /**
     * Sets the value of the decemberInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setDecemberInd(String value);

}
