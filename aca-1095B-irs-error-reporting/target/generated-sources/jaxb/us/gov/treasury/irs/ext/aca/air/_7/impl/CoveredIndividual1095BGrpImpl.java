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
import us.gov.treasury.irs.ext.aca.air._7.CoveredIndividual1095BDetail;
import us.gov.treasury.irs.ext.aca.air._7.CoveredIndividual1095BGrp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoveredIndividual1095BGrpType", propOrder = {
    "coveredIndividual1095BDetails"
})
@XmlRootElement(name = "CoveredIndividual1095BGrp")
public class CoveredIndividual1095BGrpImpl
    implements CoveredIndividual1095BGrp
{

    @XmlElement(name = "CoveredIndividual1095BDetail", required = true, type = CoveredIndividual1095BDetailImpl.class)
    protected List<CoveredIndividual1095BDetail> coveredIndividual1095BDetails;

    public List<CoveredIndividual1095BDetail> getCoveredIndividual1095BDetails() {
        if (coveredIndividual1095BDetails == null) {
            coveredIndividual1095BDetails = new ArrayList<CoveredIndividual1095BDetail>();
        }
        return this.coveredIndividual1095BDetails;
    }

}
