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
import us.gov.treasury.irs.common.TINRequestTypeCodeType;
import us.gov.treasury.irs.ext.aca.air._7.BusinessAddressGrpType;
import us.gov.treasury.irs.ext.aca.air._7.OtherCompletePersonNameType;
import us.gov.treasury.irs.ext.aca.air._7.ResponsibleIndividualGrp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponsibleIndividualGrpType", propOrder = {
    "responsibleIndividualName",
    "personNameControlTxt",
    "tinRequestTypeCd",
    "ssn",
    "birthDt",
    "mailingAddressGrp",
    "policyOriginCd",
    "shopIdentificationNum"
})
@XmlRootElement(name = "ResponsibleIndividualGrp")
public class ResponsibleIndividualGrpImpl
    implements ResponsibleIndividualGrp
{

    @XmlElement(name = "ResponsibleIndividualName", type = OtherCompletePersonNameTypeImpl.class)
    protected OtherCompletePersonNameTypeImpl responsibleIndividualName;
    @XmlElement(name = "PersonNameControlTxt")
    protected String personNameControlTxt;
    @XmlElement(name = "TINRequestTypeCd", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "string")
    protected TINRequestTypeCodeType tinRequestTypeCd;
    @XmlElement(name = "SSN", namespace = "urn:us:gov:treasury:irs:common")
    protected String ssn;
    @XmlElement(name = "BirthDt", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthDt;
    @XmlElement(name = "MailingAddressGrp", type = BusinessAddressGrpTypeImpl.class)
    protected BusinessAddressGrpTypeImpl mailingAddressGrp;
    @XmlElement(name = "PolicyOriginCd")
    protected String policyOriginCd;
    @XmlElement(name = "SHOPIdentificationNum", namespace = "urn:us:gov:treasury:irs:common")
    protected String shopIdentificationNum;

    public OtherCompletePersonNameType getResponsibleIndividualName() {
        return responsibleIndividualName;
    }

    public void setResponsibleIndividualName(OtherCompletePersonNameType value) {
        this.responsibleIndividualName = ((OtherCompletePersonNameTypeImpl) value);
    }

    public String getPersonNameControlTxt() {
        return personNameControlTxt;
    }

    public void setPersonNameControlTxt(String value) {
        this.personNameControlTxt = value;
    }

    public TINRequestTypeCodeType getTINRequestTypeCd() {
        return tinRequestTypeCd;
    }

    public void setTINRequestTypeCd(TINRequestTypeCodeType value) {
        this.tinRequestTypeCd = value;
    }

    public String getSSN() {
        return ssn;
    }

    public void setSSN(String value) {
        this.ssn = value;
    }

    public XMLGregorianCalendar getBirthDt() {
        return birthDt;
    }

    public void setBirthDt(XMLGregorianCalendar value) {
        this.birthDt = value;
    }

    public BusinessAddressGrpType getMailingAddressGrp() {
        return mailingAddressGrp;
    }

    public void setMailingAddressGrp(BusinessAddressGrpType value) {
        this.mailingAddressGrp = ((BusinessAddressGrpTypeImpl) value);
    }

    public String getPolicyOriginCd() {
        return policyOriginCd;
    }

    public void setPolicyOriginCd(String value) {
        this.policyOriginCd = value;
    }

    public String getSHOPIdentificationNum() {
        return shopIdentificationNum;
    }

    public void setSHOPIdentificationNum(String value) {
        this.shopIdentificationNum = value;
    }

}
