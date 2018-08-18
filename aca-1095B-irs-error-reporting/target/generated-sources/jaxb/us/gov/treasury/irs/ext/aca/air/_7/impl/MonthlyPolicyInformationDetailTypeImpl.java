//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.impl.EPDErrorDetailTypeImpl;
import us.gov.treasury.irs.ext.aca.air._7.MonthlyPolicyInformationDetailType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonthlyPolicyInformationDetailType", propOrder = {
    "totalHsldMonthlyPremiumAmt",
    "slcspAdjMonthlyPremiumAmt",
    "householdAPTCAmt",
    "errorDetails"
})
public class MonthlyPolicyInformationDetailTypeImpl
    implements MonthlyPolicyInformationDetailType
{

    @XmlElement(name = "TotalHsldMonthlyPremiumAmt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected BigDecimal totalHsldMonthlyPremiumAmt;
    @XmlElement(name = "SLCSPAdjMonthlyPremiumAmt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected BigDecimal slcspAdjMonthlyPremiumAmt;
    @XmlElement(name = "HouseholdAPTCAmt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected BigDecimal householdAPTCAmt;
    @XmlElement(name = "ErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> errorDetails;

    public BigDecimal getTotalHsldMonthlyPremiumAmt() {
        return totalHsldMonthlyPremiumAmt;
    }

    public void setTotalHsldMonthlyPremiumAmt(BigDecimal value) {
        this.totalHsldMonthlyPremiumAmt = value;
    }

    public BigDecimal getSLCSPAdjMonthlyPremiumAmt() {
        return slcspAdjMonthlyPremiumAmt;
    }

    public void setSLCSPAdjMonthlyPremiumAmt(BigDecimal value) {
        this.slcspAdjMonthlyPremiumAmt = value;
    }

    public BigDecimal getHouseholdAPTCAmt() {
        return householdAPTCAmt;
    }

    public void setHouseholdAPTCAmt(BigDecimal value) {
        this.householdAPTCAmt = value;
    }

    public List<EPDErrorDetailType> getErrorDetails() {
        if (errorDetails == null) {
            errorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.errorDetails;
    }

}