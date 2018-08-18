//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.common.EPDErrorDetailType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Covered Individual 1095B Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2014-04-09&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;All the elements associated with an covered individual generated 1095B report.&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for CoveredIndividual1095BDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CoveredIndividual1095BDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}CoveredIndividualName"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SSN" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}BirthDt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}TINTypeCd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}CvrIndivTINValidateNameRespDtl"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}CoveredIndividualYearRoundInd"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}CoveredIndividualMonthIndGrp"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ErrorDetail" maxOccurs="99" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoveredIndividual1095BDetailType", propOrder = {
    "coveredIndividualName",
    "ssn",
    "birthDt",
    "tinTypeCd",
    "cvrIndivTINValidateNameRespDtl",
    "coveredIndividualYearRoundInd",
    "coveredIndividualMonthIndGrp",
    "errorDetail"
})
public class CoveredIndividual1095BDetailType {

    @XmlElement(name = "CoveredIndividualName", required = true)
    protected OtherCompletePersonNameType coveredIndividualName;
    @XmlElement(name = "SSN", namespace = "urn:us:gov:treasury:irs:common")
    protected String ssn;
    @XmlElement(name = "BirthDt", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthDt;
    @XmlElement(name = "TINTypeCd", required = true)
    protected String tinTypeCd;
    @XmlElement(name = "CvrIndivTINValidateNameRespDtl", required = true)
    protected TINValidateNameResponseDetailType cvrIndivTINValidateNameRespDtl;
    @XmlElement(name = "CoveredIndividualYearRoundInd", required = true)
    protected String coveredIndividualYearRoundInd;
    @XmlElement(name = "CoveredIndividualMonthIndGrp", required = true)
    protected MonthlyIndicatorGrpType coveredIndividualMonthIndGrp;
    @XmlElement(name = "ErrorDetail")
    protected List<EPDErrorDetailType> errorDetail;

    /**
     * Gets the value of the coveredIndividualName property.
     * 
     * @return
     *     possible object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    public OtherCompletePersonNameType getCoveredIndividualName() {
        return coveredIndividualName;
    }

    /**
     * Sets the value of the coveredIndividualName property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    public void setCoveredIndividualName(OtherCompletePersonNameType value) {
        this.coveredIndividualName = value;
    }

    /**
     * Gets the value of the ssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSSN() {
        return ssn;
    }

    /**
     * Sets the value of the ssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSSN(String value) {
        this.ssn = value;
    }

    /**
     * Gets the value of the birthDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthDt() {
        return birthDt;
    }

    /**
     * Sets the value of the birthDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthDt(XMLGregorianCalendar value) {
        this.birthDt = value;
    }

    /**
     * Gets the value of the tinTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTINTypeCd() {
        return tinTypeCd;
    }

    /**
     * Sets the value of the tinTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTINTypeCd(String value) {
        this.tinTypeCd = value;
    }

    /**
     * Gets the value of the cvrIndivTINValidateNameRespDtl property.
     * 
     * @return
     *     possible object is
     *     {@link TINValidateNameResponseDetailType }
     *     
     */
    public TINValidateNameResponseDetailType getCvrIndivTINValidateNameRespDtl() {
        return cvrIndivTINValidateNameRespDtl;
    }

    /**
     * Sets the value of the cvrIndivTINValidateNameRespDtl property.
     * 
     * @param value
     *     allowed object is
     *     {@link TINValidateNameResponseDetailType }
     *     
     */
    public void setCvrIndivTINValidateNameRespDtl(TINValidateNameResponseDetailType value) {
        this.cvrIndivTINValidateNameRespDtl = value;
    }

    /**
     * Gets the value of the coveredIndividualYearRoundInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoveredIndividualYearRoundInd() {
        return coveredIndividualYearRoundInd;
    }

    /**
     * Sets the value of the coveredIndividualYearRoundInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoveredIndividualYearRoundInd(String value) {
        this.coveredIndividualYearRoundInd = value;
    }

    /**
     * Gets the value of the coveredIndividualMonthIndGrp property.
     * 
     * @return
     *     possible object is
     *     {@link MonthlyIndicatorGrpType }
     *     
     */
    public MonthlyIndicatorGrpType getCoveredIndividualMonthIndGrp() {
        return coveredIndividualMonthIndGrp;
    }

    /**
     * Sets the value of the coveredIndividualMonthIndGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonthlyIndicatorGrpType }
     *     
     */
    public void setCoveredIndividualMonthIndGrp(MonthlyIndicatorGrpType value) {
        this.coveredIndividualMonthIndGrp = value;
    }

    /**
     * Gets the value of the errorDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EPDErrorDetailType }
     * 
     * 
     */
    public List<EPDErrorDetailType> getErrorDetail() {
        if (errorDetail == null) {
            errorDetail = new ArrayList<EPDErrorDetailType>();
        }
        return this.errorDetail;
    }

}