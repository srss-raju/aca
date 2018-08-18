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
import us.gov.treasury.irs.common.BusinessAddressErrorDtl;
import us.gov.treasury.irs.common.BusinessNameErrorDtl;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.SHOPEmployerInfoErrorDtl;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SHOPEmployerInfoErrorDtlType", propOrder = {
    "businessNameErrorDtl",
    "businessAddressErrorDtl",
    "epdErrorDetails"
})
@XmlRootElement(name = "SHOPEmployerInfoErrorDtl")
public class SHOPEmployerInfoErrorDtlImpl
    implements SHOPEmployerInfoErrorDtl
{

    @XmlElement(name = "BusinessNameErrorDtl", required = true, type = BusinessNameErrorDtlImpl.class)
    protected BusinessNameErrorDtlImpl businessNameErrorDtl;
    @XmlElement(name = "BusinessAddressErrorDtl", required = true, type = BusinessAddressErrorDtlImpl.class)
    protected BusinessAddressErrorDtlImpl businessAddressErrorDtl;
    @XmlElement(name = "EPDErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> epdErrorDetails;

    public BusinessNameErrorDtl getBusinessNameErrorDtl() {
        return businessNameErrorDtl;
    }

    public void setBusinessNameErrorDtl(BusinessNameErrorDtl value) {
        this.businessNameErrorDtl = ((BusinessNameErrorDtlImpl) value);
    }

    public BusinessAddressErrorDtl getBusinessAddressErrorDtl() {
        return businessAddressErrorDtl;
    }

    public void setBusinessAddressErrorDtl(BusinessAddressErrorDtl value) {
        this.businessAddressErrorDtl = ((BusinessAddressErrorDtlImpl) value);
    }

    public List<EPDErrorDetailType> getEPDErrorDetails() {
        if (epdErrorDetails == null) {
            epdErrorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.epdErrorDetails;
    }

}
