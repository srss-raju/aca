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
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.ext.aca.air._7.RecipientPolicyCoverageDtl;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecipientPolicyCoverageDtlType", propOrder = {
    "qhpPolicyNum",
    "issuerNm",
    "policyCoverageStartDt",
    "policyCoverageEndDt"
})
@XmlRootElement(name = "RecipientPolicyCoverageDtl")
public class RecipientPolicyCoverageDtlImpl
    implements RecipientPolicyCoverageDtl
{

    @XmlElement(name = "QHPPolicyNum", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String qhpPolicyNum;
    @XmlElement(name = "IssuerNm", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String issuerNm;
    @XmlElement(name = "PolicyCoverageStartDt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar policyCoverageStartDt;
    @XmlElement(name = "PolicyCoverageEndDt", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar policyCoverageEndDt;

    public String getQHPPolicyNum() {
        return qhpPolicyNum;
    }

    public void setQHPPolicyNum(String value) {
        this.qhpPolicyNum = value;
    }

    public String getIssuerNm() {
        return issuerNm;
    }

    public void setIssuerNm(String value) {
        this.issuerNm = value;
    }

    public XMLGregorianCalendar getPolicyCoverageStartDt() {
        return policyCoverageStartDt;
    }

    public void setPolicyCoverageStartDt(XMLGregorianCalendar value) {
        this.policyCoverageStartDt = value;
    }

    public XMLGregorianCalendar getPolicyCoverageEndDt() {
        return policyCoverageEndDt;
    }

    public void setPolicyCoverageEndDt(XMLGregorianCalendar value) {
        this.policyCoverageEndDt = value;
    }

}