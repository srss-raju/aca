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
import us.gov.treasury.irs.common.DependentInfoErrorsGrp;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.EPDPersonErrorDtlType;
import us.gov.treasury.irs.common.EmployerMECErrorDtl;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DependentInfoErrorsGrpType", propOrder = {
    "dependentInfoErrorDtl",
    "employerMECErrorDtls",
    "epdErrorDetails"
})
@XmlRootElement(name = "DependentInfoErrorsGrp")
public class DependentInfoErrorsGrpImpl
    implements DependentInfoErrorsGrp
{

    @XmlElement(name = "DependentInfoErrorDtl", type = EPDPersonErrorDtlTypeImpl.class)
    protected EPDPersonErrorDtlTypeImpl dependentInfoErrorDtl;
    @XmlElement(name = "EmployerMECErrorDtl", type = EmployerMECErrorDtlImpl.class)
    protected List<EmployerMECErrorDtl> employerMECErrorDtls;
    @XmlElement(name = "EPDErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> epdErrorDetails;

    public EPDPersonErrorDtlType getDependentInfoErrorDtl() {
        return dependentInfoErrorDtl;
    }

    public void setDependentInfoErrorDtl(EPDPersonErrorDtlType value) {
        this.dependentInfoErrorDtl = ((EPDPersonErrorDtlTypeImpl) value);
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
