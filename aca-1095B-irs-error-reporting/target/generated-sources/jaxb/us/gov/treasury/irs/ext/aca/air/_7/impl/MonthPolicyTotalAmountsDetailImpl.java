//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.ext.aca.air._7.MonthPolicyTotalAmountsDetail;
import us.gov.treasury.irs.ext.aca.air._7.MonthlyPolicyInformationDetailType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonthPolicyInformationDetailType", propOrder = {
    "monthNum",
    "monthlyPolicyInformationDetail"
})
@XmlRootElement(name = "MonthPolicyTotalAmountsDetail")
public class MonthPolicyTotalAmountsDetailImpl
    implements MonthPolicyTotalAmountsDetail
{

    @XmlElement(name = "MonthNum")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int monthNum;
    @XmlElement(name = "MonthlyPolicyInformationDetail", required = true, type = MonthlyPolicyInformationDetailTypeImpl.class)
    protected MonthlyPolicyInformationDetailTypeImpl monthlyPolicyInformationDetail;

    public int getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(int value) {
        this.monthNum = value;
    }

    public MonthlyPolicyInformationDetailType getMonthlyPolicyInformationDetail() {
        return monthlyPolicyInformationDetail;
    }

    public void setMonthlyPolicyInformationDetail(MonthlyPolicyInformationDetailType value) {
        this.monthlyPolicyInformationDetail = ((MonthlyPolicyInformationDetailTypeImpl) value);
    }

}
