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
import us.gov.treasury.irs.common.ExemptionErrorDtl;
import us.gov.treasury.irs.common.IRSHouseholdErrorDtl;
import us.gov.treasury.irs.common.InsurancePolicyErrorDtl;
import us.gov.treasury.irs.common.TaxHouseholdErrorDtl;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IRSHouseholdErrorDtlType", propOrder = {
    "irsGroupIdentificationNum",
    "taxHouseholdErrorDtls",
    "exemptionErrorDtls",
    "insurancePolicyErrorDtls",
    "epdErrorDetails"
})
@XmlRootElement(name = "IRSHouseholdErrorDtl")
public class IRSHouseholdErrorDtlImpl
    implements IRSHouseholdErrorDtl
{

    @XmlElement(name = "IRSGroupIdentificationNum", required = true)
    protected String irsGroupIdentificationNum;
    @XmlElement(name = "TaxHouseholdErrorDtl", type = TaxHouseholdErrorDtlImpl.class)
    protected List<TaxHouseholdErrorDtl> taxHouseholdErrorDtls;
    @XmlElement(name = "ExemptionErrorDtl", type = ExemptionErrorDtlImpl.class)
    protected List<ExemptionErrorDtl> exemptionErrorDtls;
    @XmlElement(name = "InsurancePolicyErrorDtl", type = InsurancePolicyErrorDtlImpl.class)
    protected List<InsurancePolicyErrorDtl> insurancePolicyErrorDtls;
    @XmlElement(name = "EPDErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> epdErrorDetails;

    public String getIRSGroupIdentificationNum() {
        return irsGroupIdentificationNum;
    }

    public void setIRSGroupIdentificationNum(String value) {
        this.irsGroupIdentificationNum = value;
    }

    public List<TaxHouseholdErrorDtl> getTaxHouseholdErrorDtls() {
        if (taxHouseholdErrorDtls == null) {
            taxHouseholdErrorDtls = new ArrayList<TaxHouseholdErrorDtl>();
        }
        return this.taxHouseholdErrorDtls;
    }

    public List<ExemptionErrorDtl> getExemptionErrorDtls() {
        if (exemptionErrorDtls == null) {
            exemptionErrorDtls = new ArrayList<ExemptionErrorDtl>();
        }
        return this.exemptionErrorDtls;
    }

    public List<InsurancePolicyErrorDtl> getInsurancePolicyErrorDtls() {
        if (insurancePolicyErrorDtls == null) {
            insurancePolicyErrorDtls = new ArrayList<InsurancePolicyErrorDtl>();
        }
        return this.insurancePolicyErrorDtls;
    }

    public List<EPDErrorDetailType> getEPDErrorDetails() {
        if (epdErrorDetails == null) {
            epdErrorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.epdErrorDetails;
    }

}
