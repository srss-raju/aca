//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:05 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Amount By Month Detail Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2015-01-06&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;A group that wraps monthly amount&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for AmountByMonthDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AmountByMonthDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JanuaryAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}FebruaryAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MarchAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AprilAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MayAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JuneAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JulyAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AugustAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}SeptemberAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}OctoberAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}NovemberAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}DecemberAmt" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountByMonthDetailType", propOrder = {
    "januaryAmt",
    "februaryAmt",
    "marchAmt",
    "aprilAmt",
    "mayAmt",
    "juneAmt",
    "julyAmt",
    "augustAmt",
    "septemberAmt",
    "octoberAmt",
    "novemberAmt",
    "decemberAmt"
})
public class AmountByMonthDetailType {

    @XmlElement(name = "JanuaryAmt")
    protected BigDecimal januaryAmt;
    @XmlElement(name = "FebruaryAmt")
    protected BigDecimal februaryAmt;
    @XmlElement(name = "MarchAmt")
    protected BigDecimal marchAmt;
    @XmlElement(name = "AprilAmt")
    protected BigDecimal aprilAmt;
    @XmlElement(name = "MayAmt")
    protected BigDecimal mayAmt;
    @XmlElement(name = "JuneAmt")
    protected BigDecimal juneAmt;
    @XmlElement(name = "JulyAmt")
    protected BigDecimal julyAmt;
    @XmlElement(name = "AugustAmt")
    protected BigDecimal augustAmt;
    @XmlElement(name = "SeptemberAmt")
    protected BigDecimal septemberAmt;
    @XmlElement(name = "OctoberAmt")
    protected BigDecimal octoberAmt;
    @XmlElement(name = "NovemberAmt")
    protected BigDecimal novemberAmt;
    @XmlElement(name = "DecemberAmt")
    protected BigDecimal decemberAmt;

    /**
     * Gets the value of the januaryAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getJanuaryAmt() {
        return januaryAmt;
    }

    /**
     * Sets the value of the januaryAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setJanuaryAmt(BigDecimal value) {
        this.januaryAmt = value;
    }

    /**
     * Gets the value of the februaryAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFebruaryAmt() {
        return februaryAmt;
    }

    /**
     * Sets the value of the februaryAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFebruaryAmt(BigDecimal value) {
        this.februaryAmt = value;
    }

    /**
     * Gets the value of the marchAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMarchAmt() {
        return marchAmt;
    }

    /**
     * Sets the value of the marchAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMarchAmt(BigDecimal value) {
        this.marchAmt = value;
    }

    /**
     * Gets the value of the aprilAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAprilAmt() {
        return aprilAmt;
    }

    /**
     * Sets the value of the aprilAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAprilAmt(BigDecimal value) {
        this.aprilAmt = value;
    }

    /**
     * Gets the value of the mayAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMayAmt() {
        return mayAmt;
    }

    /**
     * Sets the value of the mayAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMayAmt(BigDecimal value) {
        this.mayAmt = value;
    }

    /**
     * Gets the value of the juneAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getJuneAmt() {
        return juneAmt;
    }

    /**
     * Sets the value of the juneAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setJuneAmt(BigDecimal value) {
        this.juneAmt = value;
    }

    /**
     * Gets the value of the julyAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getJulyAmt() {
        return julyAmt;
    }

    /**
     * Sets the value of the julyAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setJulyAmt(BigDecimal value) {
        this.julyAmt = value;
    }

    /**
     * Gets the value of the augustAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAugustAmt() {
        return augustAmt;
    }

    /**
     * Sets the value of the augustAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAugustAmt(BigDecimal value) {
        this.augustAmt = value;
    }

    /**
     * Gets the value of the septemberAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSeptemberAmt() {
        return septemberAmt;
    }

    /**
     * Sets the value of the septemberAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSeptemberAmt(BigDecimal value) {
        this.septemberAmt = value;
    }

    /**
     * Gets the value of the octoberAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOctoberAmt() {
        return octoberAmt;
    }

    /**
     * Sets the value of the octoberAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOctoberAmt(BigDecimal value) {
        this.octoberAmt = value;
    }

    /**
     * Gets the value of the novemberAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNovemberAmt() {
        return novemberAmt;
    }

    /**
     * Sets the value of the novemberAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNovemberAmt(BigDecimal value) {
        this.novemberAmt = value;
    }

    /**
     * Gets the value of the decemberAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDecemberAmt() {
        return decemberAmt;
    }

    /**
     * Sets the value of the decemberAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDecemberAmt(BigDecimal value) {
        this.decemberAmt = value;
    }

}
