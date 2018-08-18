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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.impl.EPDErrorDetailTypeImpl;
import us.gov.treasury.irs.ext.aca.air._7.BusinessAddressGrpType;
import us.gov.treasury.irs.ext.aca.air._7.CompletePersonNameDetailType;
import us.gov.treasury.irs.ext.aca.air._7.EmployeeDetail;
import us.gov.treasury.irs.ext.aca.air._7.TINValidateNameResponseDetailType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeDetailType", propOrder = {
    "employeeName",
    "employeeSSN",
    "tinTypeCd",
    "emplTINValidateNameRespDetail",
    "employeeMailingAddress",
    "errorDetails"
})
@XmlRootElement(name = "EmployeeDetail")
public class EmployeeDetailImpl
    implements EmployeeDetail
{

    @XmlElement(name = "EmployeeName", required = true, type = CompletePersonNameDetailTypeImpl.class)
    protected CompletePersonNameDetailTypeImpl employeeName;
    @XmlElement(name = "EmployeeSSN", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String employeeSSN;
    @XmlElement(name = "TINTypeCd", required = true)
    protected String tinTypeCd;
    @XmlElement(name = "EmplTINValidateNameRespDetail", required = true, type = TINValidateNameResponseDetailTypeImpl.class)
    protected TINValidateNameResponseDetailTypeImpl emplTINValidateNameRespDetail;
    @XmlElement(name = "EmployeeMailingAddress", required = true, type = BusinessAddressGrpTypeImpl.class)
    protected BusinessAddressGrpTypeImpl employeeMailingAddress;
    @XmlElement(name = "ErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> errorDetails;

    public CompletePersonNameDetailType getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(CompletePersonNameDetailType value) {
        this.employeeName = ((CompletePersonNameDetailTypeImpl) value);
    }

    public String getEmployeeSSN() {
        return employeeSSN;
    }

    public void setEmployeeSSN(String value) {
        this.employeeSSN = value;
    }

    public String getTINTypeCd() {
        return tinTypeCd;
    }

    public void setTINTypeCd(String value) {
        this.tinTypeCd = value;
    }

    public TINValidateNameResponseDetailType getEmplTINValidateNameRespDetail() {
        return emplTINValidateNameRespDetail;
    }

    public void setEmplTINValidateNameRespDetail(TINValidateNameResponseDetailType value) {
        this.emplTINValidateNameRespDetail = ((TINValidateNameResponseDetailTypeImpl) value);
    }

    public BusinessAddressGrpType getEmployeeMailingAddress() {
        return employeeMailingAddress;
    }

    public void setEmployeeMailingAddress(BusinessAddressGrpType value) {
        this.employeeMailingAddress = ((BusinessAddressGrpTypeImpl) value);
    }

    public List<EPDErrorDetailType> getErrorDetails() {
        if (errorDetails == null) {
            errorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.errorDetails;
    }

}