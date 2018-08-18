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
import us.gov.treasury.irs.ext.aca.air._7.BusinessAddressGrpType;
import us.gov.treasury.irs.ext.aca.air._7.BusinessNameType;
import us.gov.treasury.irs.ext.aca.air._7.GovtEntityEmployerInfoGrp;
import us.gov.treasury.irs.ext.aca.air._7.OtherCompletePersonNameType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GovtEntityEmployerInfoGrpType", propOrder = {
    "businessName",
    "businessNameControlTxt",
    "tinRequestTypeCd",
    "employerEIN",
    "mailingAddressGrp",
    "contactNameGrp",
    "contactPhoneNum"
})
@XmlRootElement(name = "GovtEntityEmployerInfoGrp")
public class GovtEntityEmployerInfoGrpImpl
    implements GovtEntityEmployerInfoGrp
{

    @XmlElement(name = "BusinessName", type = BusinessNameTypeImpl.class)
    protected BusinessNameTypeImpl businessName;
    @XmlElement(name = "BusinessNameControlTxt")
    protected String businessNameControlTxt;
    @XmlElement(name = "TINRequestTypeCd", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "string")
    protected TINRequestTypeCodeType tinRequestTypeCd;
    @XmlElement(name = "EmployerEIN", namespace = "urn:us:gov:treasury:irs:common")
    protected String employerEIN;
    @XmlElement(name = "MailingAddressGrp", type = BusinessAddressGrpTypeImpl.class)
    protected BusinessAddressGrpTypeImpl mailingAddressGrp;
    @XmlElement(name = "ContactNameGrp", type = OtherCompletePersonNameTypeImpl.class)
    protected OtherCompletePersonNameTypeImpl contactNameGrp;
    @XmlElement(name = "ContactPhoneNum")
    protected String contactPhoneNum;

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

    public String getEmployerEIN() {
        return employerEIN;
    }

    public void setEmployerEIN(String value) {
        this.employerEIN = value;
    }

    public BusinessAddressGrpType getMailingAddressGrp() {
        return mailingAddressGrp;
    }

    public void setMailingAddressGrp(BusinessAddressGrpType value) {
        this.mailingAddressGrp = ((BusinessAddressGrpTypeImpl) value);
    }

    public OtherCompletePersonNameType getContactNameGrp() {
        return contactNameGrp;
    }

    public void setContactNameGrp(OtherCompletePersonNameType value) {
        this.contactNameGrp = ((OtherCompletePersonNameTypeImpl) value);
    }

    public String getContactPhoneNum() {
        return contactPhoneNum;
    }

    public void setContactPhoneNum(String value) {
        this.contactPhoneNum = value;
    }

}
