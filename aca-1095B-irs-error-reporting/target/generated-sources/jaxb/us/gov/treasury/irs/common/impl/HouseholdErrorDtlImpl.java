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
import us.gov.treasury.irs.common.AssociatedPolicyErrorDtl;
import us.gov.treasury.irs.common.DependentInfoErrorsGrp;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.HouseholdErrorDtl;
import us.gov.treasury.irs.common.PrimaryInfoErrorsGrp;
import us.gov.treasury.irs.common.SpouseInfoErrorsGrp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HouseholdErrorDtlType", propOrder = {
    "primaryInfoErrorsGrp",
    "spouseInfoErrorsGrp",
    "dependentInfoErrorsGrps",
    "associatedPolicyErrorDtls",
    "epdErrorDetails"
})
@XmlRootElement(name = "HouseholdErrorDtl")
public class HouseholdErrorDtlImpl
    implements HouseholdErrorDtl
{

    @XmlElement(name = "PrimaryInfoErrorsGrp", type = PrimaryInfoErrorsGrpImpl.class)
    protected PrimaryInfoErrorsGrpImpl primaryInfoErrorsGrp;
    @XmlElement(name = "SpouseInfoErrorsGrp", type = SpouseInfoErrorsGrpImpl.class)
    protected SpouseInfoErrorsGrpImpl spouseInfoErrorsGrp;
    @XmlElement(name = "DependentInfoErrorsGrp", type = DependentInfoErrorsGrpImpl.class)
    protected List<DependentInfoErrorsGrp> dependentInfoErrorsGrps;
    @XmlElement(name = "AssociatedPolicyErrorDtl", type = AssociatedPolicyErrorDtlImpl.class)
    protected List<AssociatedPolicyErrorDtl> associatedPolicyErrorDtls;
    @XmlElement(name = "EPDErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> epdErrorDetails;

    public PrimaryInfoErrorsGrp getPrimaryInfoErrorsGrp() {
        return primaryInfoErrorsGrp;
    }

    public void setPrimaryInfoErrorsGrp(PrimaryInfoErrorsGrp value) {
        this.primaryInfoErrorsGrp = ((PrimaryInfoErrorsGrpImpl) value);
    }

    public SpouseInfoErrorsGrp getSpouseInfoErrorsGrp() {
        return spouseInfoErrorsGrp;
    }

    public void setSpouseInfoErrorsGrp(SpouseInfoErrorsGrp value) {
        this.spouseInfoErrorsGrp = ((SpouseInfoErrorsGrpImpl) value);
    }

    public List<DependentInfoErrorsGrp> getDependentInfoErrorsGrps() {
        if (dependentInfoErrorsGrps == null) {
            dependentInfoErrorsGrps = new ArrayList<DependentInfoErrorsGrp>();
        }
        return this.dependentInfoErrorsGrps;
    }

    public List<AssociatedPolicyErrorDtl> getAssociatedPolicyErrorDtls() {
        if (associatedPolicyErrorDtls == null) {
            associatedPolicyErrorDtls = new ArrayList<AssociatedPolicyErrorDtl>();
        }
        return this.associatedPolicyErrorDtls;
    }

    public List<EPDErrorDetailType> getEPDErrorDetails() {
        if (epdErrorDetails == null) {
            epdErrorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.epdErrorDetails;
    }

}