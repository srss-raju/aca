//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.impl.EPDErrorDetailTypeImpl;
import us.gov.treasury.irs.ext.aca.air._7.MonthPolicyTotalAmountsDetail;
import us.gov.treasury.irs.ext.aca.air._7.MonthlyPolicyInformationDetailType;
import us.gov.treasury.irs.ext.aca.air._7.RecipientPolicyInfoDetail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxHouseholdPolicyInformationGrpType", propOrder = {
    "monthPolicyTotalAmountsDetails",
    "annualPolicyTotalAmountsDetail",
    "errorDetails"
})
@XmlRootElement(name = "RecipientPolicyInfoDetail")
public class RecipientPolicyInfoDetailImpl
    implements RecipientPolicyInfoDetail
{

    @XmlElement(name = "MonthPolicyTotalAmountsDetail", required = true, type = MonthPolicyTotalAmountsDetailImpl.class)
    protected List<MonthPolicyTotalAmountsDetail> monthPolicyTotalAmountsDetails;
    @XmlElement(name = "AnnualPolicyTotalAmountsDetail", required = true, type = MonthlyPolicyInformationDetailTypeImpl.class)
    protected MonthlyPolicyInformationDetailTypeImpl annualPolicyTotalAmountsDetail;
    @XmlElement(name = "ErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> errorDetails;

    public List<MonthPolicyTotalAmountsDetail> getMonthPolicyTotalAmountsDetails() {
        if (monthPolicyTotalAmountsDetails == null) {
            monthPolicyTotalAmountsDetails = new ArrayList<MonthPolicyTotalAmountsDetail>();
        }
        return this.monthPolicyTotalAmountsDetails;
    }

    public MonthlyPolicyInformationDetailType getAnnualPolicyTotalAmountsDetail() {
        return annualPolicyTotalAmountsDetail;
    }

    public void setAnnualPolicyTotalAmountsDetail(MonthlyPolicyInformationDetailType value) {
        this.annualPolicyTotalAmountsDetail = ((MonthlyPolicyInformationDetailTypeImpl) value);
    }

    public List<EPDErrorDetailType> getErrorDetails() {
        if (errorDetails == null) {
            errorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.errorDetails;
    }

}
