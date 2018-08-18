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
import us.gov.treasury.irs.common.SHOPEmployerErrorDtl;
import us.gov.treasury.irs.common.SHOPExchangeError;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SHOPExchangeErrorType", propOrder = {
    "healthExchangeId",
    "shopEmployerErrorDtls",
    "epdErrorDetails"
})
@XmlRootElement(name = "SHOPExchangeError")
public class SHOPExchangeErrorImpl
    implements SHOPExchangeError
{

    @XmlElement(name = "HealthExchangeId", required = true)
    protected String healthExchangeId;
    @XmlElement(name = "SHOPEmployerErrorDtl", required = true, type = SHOPEmployerErrorDtlImpl.class)
    protected List<SHOPEmployerErrorDtl> shopEmployerErrorDtls;
    @XmlElement(name = "EPDErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> epdErrorDetails;

    public String getHealthExchangeId() {
        return healthExchangeId;
    }

    public void setHealthExchangeId(String value) {
        this.healthExchangeId = value;
    }

    public List<SHOPEmployerErrorDtl> getSHOPEmployerErrorDtls() {
        if (shopEmployerErrorDtls == null) {
            shopEmployerErrorDtls = new ArrayList<SHOPEmployerErrorDtl>();
        }
        return this.shopEmployerErrorDtls;
    }

    public List<EPDErrorDetailType> getEPDErrorDetails() {
        if (epdErrorDetails == null) {
            epdErrorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.epdErrorDetails;
    }

}