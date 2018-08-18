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
import us.gov.treasury.irs.common.TINRequestTypeCodeType;
import us.gov.treasury.irs.ext.aca.air._7.BusinessNameType;
import us.gov.treasury.irs.ext.aca.air._7.OtherALEMembersGrp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OtherALEMembersType", propOrder = {
    "businessName",
    "businessNameControlTxt",
    "tinRequestTypeCd",
    "ein"
})
@XmlRootElement(name = "OtherALEMembersGrp")
public class OtherALEMembersGrpImpl
    implements OtherALEMembersGrp
{

    @XmlElement(name = "BusinessName", type = BusinessNameTypeImpl.class)
    protected BusinessNameTypeImpl businessName;
    @XmlElement(name = "BusinessNameControlTxt")
    protected String businessNameControlTxt;
    @XmlElement(name = "TINRequestTypeCd", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "string")
    protected TINRequestTypeCodeType tinRequestTypeCd;
    @XmlElement(name = "EIN", namespace = "urn:us:gov:treasury:irs:common")
    protected String ein;

    public BusinessNameType getBusinessName() {
        return businessName;
    }

    public void setBusinessName(BusinessNameType value) {
        this.businessName = ((BusinessNameTypeImpl) value);
    }

    public String getBusinessNameControlTxt() {
        return businessNameControlTxt;
    }

    public void setBusinessNameControlTxt(String value) {
        this.businessNameControlTxt = value;
    }

    public TINRequestTypeCodeType getTINRequestTypeCd() {
        return tinRequestTypeCd;
    }

    public void setTINRequestTypeCd(TINRequestTypeCodeType value) {
        this.tinRequestTypeCd = value;
    }

    public String getEIN() {
        return ein;
    }

    public void setEIN(String value) {
        this.ein = value;
    }

}
