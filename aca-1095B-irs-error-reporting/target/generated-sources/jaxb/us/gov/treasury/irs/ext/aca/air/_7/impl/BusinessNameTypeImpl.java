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
import us.gov.treasury.irs.ext.aca.air._7.BusinessNameType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessNameType", propOrder = {
    "businessNameLine1Txt",
    "businessNameLine2Txt"
})
public class BusinessNameTypeImpl
    implements BusinessNameType
{

    @XmlElement(name = "BusinessNameLine1Txt", required = true)
    protected String businessNameLine1Txt;
    @XmlElement(name = "BusinessNameLine2Txt")
    protected String businessNameLine2Txt;

    public String getBusinessNameLine1Txt() {
        return businessNameLine1Txt;
    }

    public void setBusinessNameLine1Txt(String value) {
        this.businessNameLine1Txt = value;
    }

    public String getBusinessNameLine2Txt() {
        return businessNameLine2Txt;
    }

    public void setBusinessNameLine2Txt(String value) {
        this.businessNameLine2Txt = value;
    }

}
