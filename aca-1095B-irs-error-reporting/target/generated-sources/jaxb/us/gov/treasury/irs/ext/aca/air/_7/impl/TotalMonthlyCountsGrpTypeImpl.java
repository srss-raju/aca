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
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.impl.EPDErrorDetailTypeImpl;
import us.gov.treasury.irs.ext.aca.air._7.MonthCountDetail;
import us.gov.treasury.irs.ext.aca.air._7.TotalMonthlyCountsGrpType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TotalMonthlyCountsGrpType", propOrder = {
    "monthCountDetails",
    "errorDetails"
})
public class TotalMonthlyCountsGrpTypeImpl
    implements TotalMonthlyCountsGrpType
{

    @XmlElement(name = "MonthCountDetail", required = true, type = MonthCountDetailImpl.class)
    protected List<MonthCountDetail> monthCountDetails;
    @XmlElement(name = "ErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> errorDetails;

    public List<MonthCountDetail> getMonthCountDetails() {
        if (monthCountDetails == null) {
            monthCountDetails = new ArrayList<MonthCountDetail>();
        }
        return this.monthCountDetails;
    }

    public List<EPDErrorDetailType> getErrorDetails() {
        if (errorDetails == null) {
            errorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.errorDetails;
    }

}
