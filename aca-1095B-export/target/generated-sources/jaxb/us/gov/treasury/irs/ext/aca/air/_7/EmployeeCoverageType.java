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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Employee Coverage Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2008-09-19&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;All the elements associated with an employee roster coverage in exchange generated monthly report&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for EmployeeCoverageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeCoverageType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}ApplicableCoverageMonthNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}OtherCompletePersonName"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SSN"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}EmployerQHPPremiumContriAmt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}EmployeeSHOPPolicy" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeCoverageType", propOrder = {
    "applicableCoverageMonthNum",
    "otherCompletePersonName",
    "ssn",
    "employerQHPPremiumContriAmt",
    "employeeSHOPPolicy"
})
public class EmployeeCoverageType {

    @XmlElement(name = "ApplicableCoverageMonthNum", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int applicableCoverageMonthNum;
    @XmlElement(name = "OtherCompletePersonName", required = true)
    protected OtherCompletePersonNameType otherCompletePersonName;
    @XmlElement(name = "SSN", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String ssn;
    @XmlElement(name = "EmployerQHPPremiumContriAmt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected BigDecimal employerQHPPremiumContriAmt;
    @XmlElement(name = "EmployeeSHOPPolicy")
    protected EmployeeSHOPPolicyType employeeSHOPPolicy;

    /**
     * Gets the value of the applicableCoverageMonthNum property.
     * 
     */
    public int getApplicableCoverageMonthNum() {
        return applicableCoverageMonthNum;
    }

    /**
     * Sets the value of the applicableCoverageMonthNum property.
     * 
     */
    public void setApplicableCoverageMonthNum(int value) {
        this.applicableCoverageMonthNum = value;
    }

    /**
     * Gets the value of the otherCompletePersonName property.
     * 
     * @return
     *     possible object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    public OtherCompletePersonNameType getOtherCompletePersonName() {
        return otherCompletePersonName;
    }

    /**
     * Sets the value of the otherCompletePersonName property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    public void setOtherCompletePersonName(OtherCompletePersonNameType value) {
        this.otherCompletePersonName = value;
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
     * Gets the value of the employerQHPPremiumContriAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEmployerQHPPremiumContriAmt() {
        return employerQHPPremiumContriAmt;
    }

    /**
     * Sets the value of the employerQHPPremiumContriAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEmployerQHPPremiumContriAmt(BigDecimal value) {
        this.employerQHPPremiumContriAmt = value;
    }

    /**
     * Gets the value of the employeeSHOPPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeSHOPPolicyType }
     *     
     */
    public EmployeeSHOPPolicyType getEmployeeSHOPPolicy() {
        return employeeSHOPPolicy;
    }

    /**
     * Sets the value of the employeeSHOPPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeSHOPPolicyType }
     *     
     */
    public void setEmployeeSHOPPolicy(EmployeeSHOPPolicyType value) {
        this.employeeSHOPPolicy = value;
    }

}
