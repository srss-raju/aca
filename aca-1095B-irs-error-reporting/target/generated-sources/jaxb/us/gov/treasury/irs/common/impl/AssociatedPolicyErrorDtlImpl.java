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
import us.gov.treasury.irs.common.EPDErrorDetailType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssociatedPolicyErrorDtlType", propOrder = {
    "epdErrorDetails"
})
@XmlRootElement(name = "AssociatedPolicyErrorDtl")
public class AssociatedPolicyErrorDtlImpl
    implements AssociatedPolicyErrorDtl
{

    @XmlElement(name = "EPDErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> epdErrorDetails;

    public List<EPDErrorDetailType> getEPDErrorDetails() {
        if (epdErrorDetails == null) {
            epdErrorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.epdErrorDetails;
    }

}
