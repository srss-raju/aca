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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.common.EPDPersonType;
import us.gov.treasury.irs.common.ExemptionCoverage;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExemptionCoverageType", propOrder = {
    "applicableCoverageMonthNum",
    "exemptPerson",
    "exemptionCertificateNum",
    "monthsExemptStartDt",
    "monthsExemptEndDt"
})
@XmlRootElement(name = "ExemptionCoverage")
public class ExemptionCoverageImpl
    implements ExemptionCoverage
{

    @XmlElement(name = "ApplicableCoverageMonthNum")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int applicableCoverageMonthNum;
    @XmlElement(name = "ExemptPerson", required = true, type = EPDPersonTypeImpl.class)
    protected EPDPersonTypeImpl exemptPerson;
    @XmlElement(name = "ExemptionCertificateNum", required = true)
    protected String exemptionCertificateNum;
    @XmlElement(name = "MonthsExemptStartDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar monthsExemptStartDt;
    @XmlElement(name = "MonthsExemptEndDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar monthsExemptEndDt;

    public int getApplicableCoverageMonthNum() {
        return applicableCoverageMonthNum;
    }

    public void setApplicableCoverageMonthNum(int value) {
        this.applicableCoverageMonthNum = value;
    }

    public EPDPersonType getExemptPerson() {
        return exemptPerson;
    }

    public void setExemptPerson(EPDPersonType value) {
        this.exemptPerson = ((EPDPersonTypeImpl) value);
    }

    public String getExemptionCertificateNum() {
        return exemptionCertificateNum;
    }

    public void setExemptionCertificateNum(String value) {
        this.exemptionCertificateNum = value;
    }

    public XMLGregorianCalendar getMonthsExemptStartDt() {
        return monthsExemptStartDt;
    }

    public void setMonthsExemptStartDt(XMLGregorianCalendar value) {
        this.monthsExemptStartDt = value;
    }

    public XMLGregorianCalendar getMonthsExemptEndDt() {
        return monthsExemptEndDt;
    }

    public void setMonthsExemptEndDt(XMLGregorianCalendar value) {
        this.monthsExemptEndDt = value;
    }

}