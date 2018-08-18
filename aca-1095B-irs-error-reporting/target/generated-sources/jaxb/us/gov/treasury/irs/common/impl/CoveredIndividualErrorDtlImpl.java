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
import us.gov.treasury.irs.common.CoveredIndividualErrorDtl;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.EPDPersonErrorDtlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoveredIndividualErrorDtlType", propOrder = {
    "insuredPersonErrorDtl",
    "epdErrorDetails"
})
@XmlRootElement(name = "CoveredIndividualErrorDtl")
public class CoveredIndividualErrorDtlImpl
    implements CoveredIndividualErrorDtl
{

    @XmlElement(name = "InsuredPersonErrorDtl", type = EPDPersonErrorDtlTypeImpl.class)
    protected EPDPersonErrorDtlTypeImpl insuredPersonErrorDtl;
    @XmlElement(name = "EPDErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> epdErrorDetails;

    public EPDPersonErrorDtlType getInsuredPersonErrorDtl() {
        return insuredPersonErrorDtl;
    }

    public void setInsuredPersonErrorDtl(EPDPersonErrorDtlType value) {
        this.insuredPersonErrorDtl = ((EPDPersonErrorDtlTypeImpl) value);
    }

    public List<EPDErrorDetailType> getEPDErrorDetails() {
        if (epdErrorDetails == null) {
            epdErrorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.epdErrorDetails;
    }

}
