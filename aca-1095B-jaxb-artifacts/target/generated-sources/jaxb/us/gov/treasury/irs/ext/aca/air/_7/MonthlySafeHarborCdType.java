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
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Monthly Safe Harbor Cd&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;A group for all months&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonthlySafeHarborCdType", propOrder = {
    "janSafeHarborCd",
    "febSafeHarborCd",
    "marSafeHarborCd",
    "aprSafeHarborCd",
    "maySafeHarborCd",
    "junSafeHarborCd",
    "julSafeHarborCd",
    "augSafeHarborCd",
    "sepSafeHarborCd",
    "octSafeHarborCd",
    "novSafeHarborCd",
    "decSafeHarborCd"
})
public class MonthlySafeHarborCdType {

    @XmlElement(name = "JanSafeHarborCd")
    protected String janSafeHarborCd;
    @XmlElement(name = "FebSafeHarborCd")
    protected String febSafeHarborCd;
    @XmlElement(name = "MarSafeHarborCd")
    protected String marSafeHarborCd;
    @XmlElement(name = "AprSafeHarborCd")
    protected String aprSafeHarborCd;
    @XmlElement(name = "MaySafeHarborCd")
    protected String maySafeHarborCd;
    @XmlElement(name = "JunSafeHarborCd")
    protected String junSafeHarborCd;
    @XmlElement(name = "JulSafeHarborCd")
    protected String julSafeHarborCd;
    @XmlElement(name = "AugSafeHarborCd")
    protected String augSafeHarborCd;
    @XmlElement(name = "SepSafeHarborCd")
    protected String sepSafeHarborCd;
    @XmlElement(name = "OctSafeHarborCd")
    protected String octSafeHarborCd;
    @XmlElement(name = "NovSafeHarborCd")
    protected String novSafeHarborCd;
    @XmlElement(name = "DecSafeHarborCd")
    protected String decSafeHarborCd;

    /**
     * Gets the value of the janSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJanSafeHarborCd() {
        return janSafeHarborCd;
    }

    /**
     * Sets the value of the janSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJanSafeHarborCd(String value) {
        this.janSafeHarborCd = value;
    }

    /**
     * Gets the value of the febSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFebSafeHarborCd() {
        return febSafeHarborCd;
    }

    /**
     * Sets the value of the febSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFebSafeHarborCd(String value) {
        this.febSafeHarborCd = value;
    }

    /**
     * Gets the value of the marSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarSafeHarborCd() {
        return marSafeHarborCd;
    }

    /**
     * Sets the value of the marSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarSafeHarborCd(String value) {
        this.marSafeHarborCd = value;
    }

    /**
     * Gets the value of the aprSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAprSafeHarborCd() {
        return aprSafeHarborCd;
    }

    /**
     * Sets the value of the aprSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAprSafeHarborCd(String value) {
        this.aprSafeHarborCd = value;
    }

    /**
     * Gets the value of the maySafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaySafeHarborCd() {
        return maySafeHarborCd;
    }

    /**
     * Sets the value of the maySafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaySafeHarborCd(String value) {
        this.maySafeHarborCd = value;
    }

    /**
     * Gets the value of the junSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJunSafeHarborCd() {
        return junSafeHarborCd;
    }

    /**
     * Sets the value of the junSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJunSafeHarborCd(String value) {
        this.junSafeHarborCd = value;
    }

    /**
     * Gets the value of the julSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJulSafeHarborCd() {
        return julSafeHarborCd;
    }

    /**
     * Sets the value of the julSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJulSafeHarborCd(String value) {
        this.julSafeHarborCd = value;
    }

    /**
     * Gets the value of the augSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAugSafeHarborCd() {
        return augSafeHarborCd;
    }

    /**
     * Sets the value of the augSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAugSafeHarborCd(String value) {
        this.augSafeHarborCd = value;
    }

    /**
     * Gets the value of the sepSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSepSafeHarborCd() {
        return sepSafeHarborCd;
    }

    /**
     * Sets the value of the sepSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSepSafeHarborCd(String value) {
        this.sepSafeHarborCd = value;
    }

    /**
     * Gets the value of the octSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOctSafeHarborCd() {
        return octSafeHarborCd;
    }

    /**
     * Sets the value of the octSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOctSafeHarborCd(String value) {
        this.octSafeHarborCd = value;
    }

    /**
     * Gets the value of the novSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNovSafeHarborCd() {
        return novSafeHarborCd;
    }

    /**
     * Sets the value of the novSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNovSafeHarborCd(String value) {
        this.novSafeHarborCd = value;
    }

    /**
     * Gets the value of the decSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecSafeHarborCd() {
        return decSafeHarborCd;
    }

    /**
     * Sets the value of the decSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecSafeHarborCd(String value) {
        this.decSafeHarborCd = value;
    }

}
