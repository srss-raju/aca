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
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.ext.aca.air._7.OtherCompletePersonNameType;
import us.gov.treasury.irs.ext.aca.air._7.SHOPExchangePersonType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SHOPExchangePersonType", propOrder = {
    "otherCompletePersonName",
    "ssn"
})
public class SHOPExchangePersonTypeImpl
    implements SHOPExchangePersonType
{

    @XmlElement(name = "OtherCompletePersonName", required = true, type = OtherCompletePersonNameTypeImpl.class)
    protected OtherCompletePersonNameTypeImpl otherCompletePersonName;
    @XmlElement(name = "SSN", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String ssn;

    public OtherCompletePersonNameType getOtherCompletePersonName() {
        return otherCompletePersonName;
    }

    public void setOtherCompletePersonName(OtherCompletePersonNameType value) {
        this.otherCompletePersonName = ((OtherCompletePersonNameTypeImpl) value);
    }

    public String getSSN() {
        return ssn;
    }

    public void setSSN(String value) {
        this.ssn = value;
    }

}
