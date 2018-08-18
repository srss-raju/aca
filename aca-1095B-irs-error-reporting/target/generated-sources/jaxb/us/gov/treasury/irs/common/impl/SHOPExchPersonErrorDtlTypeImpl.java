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
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.CompletePersonNameErrorDtl;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.SHOPExchPersonErrorDtlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SHOPExchPersonErrorDtlType", propOrder = {
    "completePersonNameErrorDtl",
    "epdErrorDetails"
})
public class SHOPExchPersonErrorDtlTypeImpl
    implements SHOPExchPersonErrorDtlType
{

    @XmlElement(name = "CompletePersonNameErrorDtl", type = CompletePersonNameErrorDtlImpl.class)
    protected CompletePersonNameErrorDtlImpl completePersonNameErrorDtl;
    @XmlElement(name = "EPDErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> epdErrorDetails;

    public CompletePersonNameErrorDtl getCompletePersonNameErrorDtl() {
        return completePersonNameErrorDtl;
    }

    public void setCompletePersonNameErrorDtl(CompletePersonNameErrorDtl value) {
        this.completePersonNameErrorDtl = ((CompletePersonNameErrorDtlImpl) value);
    }

    public List<EPDErrorDetailType> getEPDErrorDetails() {
        if (epdErrorDetails == null) {
            epdErrorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.epdErrorDetails;
    }

}
