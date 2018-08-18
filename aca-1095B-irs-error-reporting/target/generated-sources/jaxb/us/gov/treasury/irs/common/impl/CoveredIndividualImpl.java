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
import us.gov.treasury.irs.common.CoveredIndividual;
import us.gov.treasury.irs.common.EPDPersonType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoveredIndividualType", propOrder = {
    "insuredPerson",
    "coverageStartDt",
    "coverageEndDt"
})
@XmlRootElement(name = "CoveredIndividual")
public class CoveredIndividualImpl
    implements CoveredIndividual
{

    @XmlElement(name = "InsuredPerson", required = true, type = EPDPersonTypeImpl.class)
    protected EPDPersonTypeImpl insuredPerson;
    @XmlElement(name = "CoverageStartDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar coverageStartDt;
    @XmlElement(name = "CoverageEndDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar coverageEndDt;

    public EPDPersonType getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(EPDPersonType value) {
        this.insuredPerson = ((EPDPersonTypeImpl) value);
    }

    public XMLGregorianCalendar getCoverageStartDt() {
        return coverageStartDt;
    }

    public void setCoverageStartDt(XMLGregorianCalendar value) {
        this.coverageStartDt = value;
    }

    public XMLGregorianCalendar getCoverageEndDt() {
        return coverageEndDt;
    }

    public void setCoverageEndDt(XMLGregorianCalendar value) {
        this.coverageEndDt = value;
    }

}
