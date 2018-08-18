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
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Applicable Large Employer Member Information Group Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Applicable Large Employer Member Information Group Type&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for ALEMemberInformationGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ALEMemberInformationGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}YearlyALEMemberDetail" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JanALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}FebALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MarALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AprALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MayALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JunALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}JulALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AugALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}SeptALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}OctALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}NovALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}DecALEMonthlyInfoGrp" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ALEMemberInformationGrpType", propOrder = {
    "yearlyALEMemberDetail",
    "janALEMonthlyInfoGrp",
    "febALEMonthlyInfoGrp",
    "marALEMonthlyInfoGrp",
    "aprALEMonthlyInfoGrp",
    "mayALEMonthlyInfoGrp",
    "junALEMonthlyInfoGrp",
    "julALEMonthlyInfoGrp",
    "augALEMonthlyInfoGrp",
    "septALEMonthlyInfoGrp",
    "octALEMonthlyInfoGrp",
    "novALEMonthlyInfoGrp",
    "decALEMonthlyInfoGrp"
})
public class ALEMemberInformationGrpType {

    @XmlElement(name = "YearlyALEMemberDetail")
    protected ALEMemberAnnualInfoGrpType yearlyALEMemberDetail;
    @XmlElement(name = "JanALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType janALEMonthlyInfoGrp;
    @XmlElement(name = "FebALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType febALEMonthlyInfoGrp;
    @XmlElement(name = "MarALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType marALEMonthlyInfoGrp;
    @XmlElement(name = "AprALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType aprALEMonthlyInfoGrp;
    @XmlElement(name = "MayALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType mayALEMonthlyInfoGrp;
    @XmlElement(name = "JunALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType junALEMonthlyInfoGrp;
    @XmlElement(name = "JulALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType julALEMonthlyInfoGrp;
    @XmlElement(name = "AugALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType augALEMonthlyInfoGrp;
    @XmlElement(name = "SeptALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType septALEMonthlyInfoGrp;
    @XmlElement(name = "OctALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType octALEMonthlyInfoGrp;
    @XmlElement(name = "NovALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType novALEMonthlyInfoGrp;
    @XmlElement(name = "DecALEMonthlyInfoGrp")
    protected ALEMemberMonthlyInfoGrpType decALEMonthlyInfoGrp;

    /**
     * Gets the value of the yearlyALEMemberDetail property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberAnnualInfoGrpType }
     *     
     */
    public ALEMemberAnnualInfoGrpType getYearlyALEMemberDetail() {
        return yearlyALEMemberDetail;
    }

    /**
     * Sets the value of the yearlyALEMemberDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberAnnualInfoGrpType }
     *     
     */
    public void setYearlyALEMemberDetail(ALEMemberAnnualInfoGrpType value) {
        this.yearlyALEMemberDetail = value;
    }

    /**
     * Gets the value of the janALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getJanALEMonthlyInfoGrp() {
        return janALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the janALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setJanALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.janALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the febALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getFebALEMonthlyInfoGrp() {
        return febALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the febALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setFebALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.febALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the marALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getMarALEMonthlyInfoGrp() {
        return marALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the marALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setMarALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.marALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the aprALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getAprALEMonthlyInfoGrp() {
        return aprALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the aprALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setAprALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.aprALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the mayALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getMayALEMonthlyInfoGrp() {
        return mayALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the mayALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setMayALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.mayALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the junALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getJunALEMonthlyInfoGrp() {
        return junALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the junALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setJunALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.junALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the julALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getJulALEMonthlyInfoGrp() {
        return julALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the julALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setJulALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.julALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the augALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getAugALEMonthlyInfoGrp() {
        return augALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the augALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setAugALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.augALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the septALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getSeptALEMonthlyInfoGrp() {
        return septALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the septALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setSeptALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.septALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the octALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getOctALEMonthlyInfoGrp() {
        return octALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the octALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setOctALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.octALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the novALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getNovALEMonthlyInfoGrp() {
        return novALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the novALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setNovALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.novALEMonthlyInfoGrp = value;
    }

    /**
     * Gets the value of the decALEMonthlyInfoGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public ALEMemberMonthlyInfoGrpType getDecALEMonthlyInfoGrp() {
        return decALEMonthlyInfoGrp;
    }

    /**
     * Sets the value of the decALEMonthlyInfoGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ALEMemberMonthlyInfoGrpType }
     *     
     */
    public void setDecALEMonthlyInfoGrp(ALEMemberMonthlyInfoGrpType value) {
        this.decALEMonthlyInfoGrp = value;
    }

}
