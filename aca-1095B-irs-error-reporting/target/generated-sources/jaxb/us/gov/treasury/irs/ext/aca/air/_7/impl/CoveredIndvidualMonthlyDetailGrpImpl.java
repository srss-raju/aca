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
import us.gov.treasury.irs.ext.aca.air._7.CoveredIndvidualMonthlyDetail;
import us.gov.treasury.irs.ext.aca.air._7.CoveredIndvidualMonthlyDetailGrp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoveredIndvidualMonthlyDetailGrpType", propOrder = {
    "coveredIndvidualMonthlyDetails"
})
@XmlRootElement(name = "CoveredIndvidualMonthlyDetailGrp")
public class CoveredIndvidualMonthlyDetailGrpImpl
    implements CoveredIndvidualMonthlyDetailGrp
{

    @XmlElement(name = "CoveredIndvidualMonthlyDetail", required = true, type = CoveredIndvidualMonthlyDetailImpl.class)
    protected List<CoveredIndvidualMonthlyDetail> coveredIndvidualMonthlyDetails;

    public List<CoveredIndvidualMonthlyDetail> getCoveredIndvidualMonthlyDetails() {
        if (coveredIndvidualMonthlyDetails == null) {
            coveredIndvidualMonthlyDetails = new ArrayList<CoveredIndvidualMonthlyDetail>();
        }
        return this.coveredIndvidualMonthlyDetails;
    }

}
