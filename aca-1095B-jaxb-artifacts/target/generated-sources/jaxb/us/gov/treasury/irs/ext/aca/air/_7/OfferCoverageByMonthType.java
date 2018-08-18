//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Offer Coverage By Month Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Offer Coverage By Month Type&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfferCoverageByMonthType", propOrder = {
    "janOfferCd",
    "febOfferCd",
    "marOfferCd",
    "aprOfferCd",
    "mayOfferCd",
    "junOfferCd",
    "julOfferCd",
    "augOfferCd",
    "sepOfferCd",
    "octOfferCd",
    "novOfferCd",
    "decOfferCd"
})
public class OfferCoverageByMonthType {

    @XmlElement(name = "JanOfferCd")
    protected String janOfferCd;
    @XmlElement(name = "FebOfferCd")
    protected String febOfferCd;
    @XmlElement(name = "MarOfferCd")
    protected String marOfferCd;
    @XmlElement(name = "AprOfferCd")
    protected String aprOfferCd;
    @XmlElement(name = "MayOfferCd")
    protected String mayOfferCd;
    @XmlElement(name = "JunOfferCd")
    protected String junOfferCd;
    @XmlElement(name = "JulOfferCd")
    protected String julOfferCd;
    @XmlElement(name = "AugOfferCd")
    protected String augOfferCd;
    @XmlElement(name = "SepOfferCd")
    protected String sepOfferCd;
    @XmlElement(name = "OctOfferCd")
    protected String octOfferCd;
    @XmlElement(name = "NovOfferCd")
    protected String novOfferCd;
    @XmlElement(name = "DecOfferCd")
    protected String decOfferCd;

    /**
     * Gets the value of the janOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJanOfferCd() {
        return janOfferCd;
    }

    /**
     * Sets the value of the janOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJanOfferCd(String value) {
        this.janOfferCd = value;
    }

    /**
     * Gets the value of the febOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFebOfferCd() {
        return febOfferCd;
    }

    /**
     * Sets the value of the febOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFebOfferCd(String value) {
        this.febOfferCd = value;
    }

    /**
     * Gets the value of the marOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarOfferCd() {
        return marOfferCd;
    }

    /**
     * Sets the value of the marOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarOfferCd(String value) {
        this.marOfferCd = value;
    }

    /**
     * Gets the value of the aprOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAprOfferCd() {
        return aprOfferCd;
    }

    /**
     * Sets the value of the aprOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAprOfferCd(String value) {
        this.aprOfferCd = value;
    }

    /**
     * Gets the value of the mayOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMayOfferCd() {
        return mayOfferCd;
    }

    /**
     * Sets the value of the mayOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMayOfferCd(String value) {
        this.mayOfferCd = value;
    }

    /**
     * Gets the value of the junOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJunOfferCd() {
        return junOfferCd;
    }

    /**
     * Sets the value of the junOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJunOfferCd(String value) {
        this.junOfferCd = value;
    }

    /**
     * Gets the value of the julOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJulOfferCd() {
        return julOfferCd;
    }

    /**
     * Sets the value of the julOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJulOfferCd(String value) {
        this.julOfferCd = value;
    }

    /**
     * Gets the value of the augOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAugOfferCd() {
        return augOfferCd;
    }

    /**
     * Sets the value of the augOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAugOfferCd(String value) {
        this.augOfferCd = value;
    }

    /**
     * Gets the value of the sepOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSepOfferCd() {
        return sepOfferCd;
    }

    /**
     * Sets the value of the sepOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSepOfferCd(String value) {
        this.sepOfferCd = value;
    }

    /**
     * Gets the value of the octOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOctOfferCd() {
        return octOfferCd;
    }

    /**
     * Sets the value of the octOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOctOfferCd(String value) {
        this.octOfferCd = value;
    }

    /**
     * Gets the value of the novOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNovOfferCd() {
        return novOfferCd;
    }

    /**
     * Sets the value of the novOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNovOfferCd(String value) {
        this.novOfferCd = value;
    }

    /**
     * Gets the value of the decOfferCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecOfferCd() {
        return decOfferCd;
    }

    /**
     * Sets the value of the decOfferCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecOfferCd(String value) {
        this.decOfferCd = value;
    }

}