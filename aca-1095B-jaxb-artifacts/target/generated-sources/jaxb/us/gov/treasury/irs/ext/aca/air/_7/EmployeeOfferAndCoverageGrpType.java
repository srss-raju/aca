//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
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
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Employee Offer and Coverage Group Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Employee Offer and Coverage Group Type&lt;/DescriptionTxt&gt;&lt;DataElementId/&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for EmployeeOfferAndCoverageGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeOfferAndCoverageGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AnnualOfferOfCoverageCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MonthlyOfferCoverageGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AnnlShrLowestCostMthlyPremAmt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MonthlyShareOfLowestCostMonthlyPremGrp" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AnnualSafeHarborCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MonthlySafeHarborGrp" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeOfferAndCoverageGrpType", propOrder = {
    "annualOfferOfCoverageCd",
    "monthlyOfferCoverageGrp",
    "annlShrLowestCostMthlyPremAmt",
    "monthlyShareOfLowestCostMonthlyPremGrp",
    "annualSafeHarborCd",
    "monthlySafeHarborGrp"
})
public class EmployeeOfferAndCoverageGrpType {

    @XmlElement(name = "AnnualOfferOfCoverageCd")
    protected String annualOfferOfCoverageCd;
    @XmlElement(name = "MonthlyOfferCoverageGrp")
    protected OfferCoverageByMonthType monthlyOfferCoverageGrp;
    @XmlElement(name = "AnnlShrLowestCostMthlyPremAmt")
    protected BigDecimal annlShrLowestCostMthlyPremAmt;
    @XmlElement(name = "MonthlyShareOfLowestCostMonthlyPremGrp")
    protected AmountByMonthDetailType monthlyShareOfLowestCostMonthlyPremGrp;
    @XmlElement(name = "AnnualSafeHarborCd")
    protected String annualSafeHarborCd;
    @XmlElement(name = "MonthlySafeHarborGrp")
    protected MonthlySafeHarborCdType monthlySafeHarborGrp;

    /**
     * Gets the value of the annualOfferOfCoverageCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnualOfferOfCoverageCd() {
        return annualOfferOfCoverageCd;
    }

    /**
     * Sets the value of the annualOfferOfCoverageCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnualOfferOfCoverageCd(String value) {
        this.annualOfferOfCoverageCd = value;
    }

    /**
     * Gets the value of the monthlyOfferCoverageGrp property.
     * 
     * @return
     *     possible object is
     *     {@link OfferCoverageByMonthType }
     *     
     */
    public OfferCoverageByMonthType getMonthlyOfferCoverageGrp() {
        return monthlyOfferCoverageGrp;
    }

    /**
     * Sets the value of the monthlyOfferCoverageGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferCoverageByMonthType }
     *     
     */
    public void setMonthlyOfferCoverageGrp(OfferCoverageByMonthType value) {
        this.monthlyOfferCoverageGrp = value;
    }

    /**
     * Gets the value of the annlShrLowestCostMthlyPremAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAnnlShrLowestCostMthlyPremAmt() {
        return annlShrLowestCostMthlyPremAmt;
    }

    /**
     * Sets the value of the annlShrLowestCostMthlyPremAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAnnlShrLowestCostMthlyPremAmt(BigDecimal value) {
        this.annlShrLowestCostMthlyPremAmt = value;
    }

    /**
     * Gets the value of the monthlyShareOfLowestCostMonthlyPremGrp property.
     * 
     * @return
     *     possible object is
     *     {@link AmountByMonthDetailType }
     *     
     */
    public AmountByMonthDetailType getMonthlyShareOfLowestCostMonthlyPremGrp() {
        return monthlyShareOfLowestCostMonthlyPremGrp;
    }

    /**
     * Sets the value of the monthlyShareOfLowestCostMonthlyPremGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountByMonthDetailType }
     *     
     */
    public void setMonthlyShareOfLowestCostMonthlyPremGrp(AmountByMonthDetailType value) {
        this.monthlyShareOfLowestCostMonthlyPremGrp = value;
    }

    /**
     * Gets the value of the annualSafeHarborCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnualSafeHarborCd() {
        return annualSafeHarborCd;
    }

    /**
     * Sets the value of the annualSafeHarborCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnualSafeHarborCd(String value) {
        this.annualSafeHarborCd = value;
    }

    /**
     * Gets the value of the monthlySafeHarborGrp property.
     * 
     * @return
     *     possible object is
     *     {@link MonthlySafeHarborCdType }
     *     
     */
    public MonthlySafeHarborCdType getMonthlySafeHarborGrp() {
        return monthlySafeHarborGrp;
    }

    /**
     * Sets the value of the monthlySafeHarborGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonthlySafeHarborCdType }
     *     
     */
    public void setMonthlySafeHarborGrp(MonthlySafeHarborCdType value) {
        this.monthlySafeHarborGrp = value;
    }

}