//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.common.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.ACAReportingServiceDetail;
import us.gov.treasury.irs.common.ReportPeriodDate;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACAReportingServiceDetailType", propOrder = {
    "reportPeriodDate"
})
@XmlRootElement(name = "ACAReportingServiceDetail")
public class ACAReportingServiceDetailImpl
    implements ACAReportingServiceDetail
{

    @XmlElement(name = "ReportPeriodDate", required = true, type = ReportPeriodDateImpl.class)
    protected ReportPeriodDateImpl reportPeriodDate;

    public ReportPeriodDate getReportPeriodDate() {
        return reportPeriodDate;
    }

    public void setReportPeriodDate(ReportPeriodDate value) {
        this.reportPeriodDate = ((ReportPeriodDateImpl) value);
    }

}