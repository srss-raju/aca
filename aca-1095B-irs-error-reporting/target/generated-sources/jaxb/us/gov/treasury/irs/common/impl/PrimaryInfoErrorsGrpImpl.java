//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.common.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.EmployerMECErrorDtl;
import us.gov.treasury.irs.common.PersonInformationErrorDtlType;
import us.gov.treasury.irs.common.PrimaryInfoErrorsGrp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrimaryInfoErrorsGrpType", propOrder = {
    "primaryInfoErrorDtl",
    "employerMECErrorDtls",
    "epdErrorDetails"
})
@XmlRootElement(name = "PrimaryInfoErrorsGrp")
public class PrimaryInfoErrorsGrpImpl
    implements PrimaryInfoErrorsGrp
{

    @XmlElement(name = "PrimaryInfoErrorDtl", type = PersonInformationErrorDtlTypeImpl.class)
    protected PersonInformationErrorDtlTypeImpl primaryInfoErrorDtl;
    @XmlElement(name = "EmployerMECErrorDtl", type = EmployerMECErrorDtlImpl.class)
    protected List<EmployerMECErrorDtl> employerMECErrorDtls;
    @XmlElement(name = "EPDErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> epdErrorDetails;

    public PersonInformationErrorDtlType getPrimaryInfoErrorDtl() {
        return primaryInfoErrorDtl;
    }

    public void setPrimaryInfoErrorDtl(PersonInformationErrorDtlType value) {
        this.primaryInfoErrorDtl = ((PersonInformationErrorDtlTypeImpl) value);
    }

    public List<EmployerMECErrorDtl> getEmployerMECErrorDtls() {
        if (employerMECErrorDtls == null) {
            employerMECErrorDtls = new ArrayList<EmployerMECErrorDtl>();
        }
        return this.employerMECErrorDtls;
    }

    public List<EPDErrorDetailType> getEPDErrorDetails() {
        if (epdErrorDetails == null) {
            epdErrorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.epdErrorDetails;
    }

}
