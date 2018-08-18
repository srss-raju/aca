//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:05 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.EPDErrorDetailType;


/**
 * Month by month Policy information
 * 			
 * 
 * <p>Java class for MonthlyPolicyInformationDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MonthlyPolicyInformationDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}TotalHsldMonthlyPremiumAmt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SLCSPAdjMonthlyPremiumAmt"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}HouseholdAPTCAmt"/&gt;
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
@XmlType(name = "MonthlyPolicyInformationDetailType", propOrder = {
    "totalHsldMonthlyPremiumAmt",
    "slcspAdjMonthlyPremiumAmt",
    "householdAPTCAmt",
    "errorDetail"
})
public class MonthlyPolicyInformationDetailType {

    @XmlElement(name = "TotalHsldMonthlyPremiumAmt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected BigDecimal totalHsldMonthlyPremiumAmt;
    @XmlElement(name = "SLCSPAdjMonthlyPremiumAmt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected BigDecimal slcspAdjMonthlyPremiumAmt;
    @XmlElement(name = "HouseholdAPTCAmt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected BigDecimal householdAPTCAmt;
    @XmlElement(name = "ErrorDetail")
    protected List<EPDErrorDetailType> errorDetail;

    /**
     * Gets the value of the totalHsldMonthlyPremiumAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalHsldMonthlyPremiumAmt() {
        return totalHsldMonthlyPremiumAmt;
    }

    /**
     * Sets the value of the totalHsldMonthlyPremiumAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalHsldMonthlyPremiumAmt(BigDecimal value) {
        this.totalHsldMonthlyPremiumAmt = value;
    }

    /**
     * Gets the value of the slcspAdjMonthlyPremiumAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSLCSPAdjMonthlyPremiumAmt() {
        return slcspAdjMonthlyPremiumAmt;
    }

    /**
     * Sets the value of the slcspAdjMonthlyPremiumAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSLCSPAdjMonthlyPremiumAmt(BigDecimal value) {
        this.slcspAdjMonthlyPremiumAmt = value;
    }

    /**
     * Gets the value of the householdAPTCAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHouseholdAPTCAmt() {
        return householdAPTCAmt;
    }

    /**
     * Sets the value of the householdAPTCAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHouseholdAPTCAmt(BigDecimal value) {
        this.householdAPTCAmt = value;
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