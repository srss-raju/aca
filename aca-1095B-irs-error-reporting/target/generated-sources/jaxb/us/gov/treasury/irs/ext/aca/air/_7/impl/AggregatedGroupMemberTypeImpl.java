//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.ext.aca.air._7.AggregatedGroupMemberType;
import us.gov.treasury.irs.ext.aca.air._7.OtherCompletePersonNameType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggregatedGroupMemberType", propOrder = {
    "names",
    "ein"
})
public class AggregatedGroupMemberTypeImpl
    implements AggregatedGroupMemberType
{

    @XmlElement(name = "Names", required = true, type = OtherCompletePersonNameTypeImpl.class)
    protected OtherCompletePersonNameTypeImpl names;
    @XmlElement(name = "EIN", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String ein;

    public OtherCompletePersonNameType getNames() {
        return names;
    }

    public void setNames(OtherCompletePersonNameType value) {
        this.names = ((OtherCompletePersonNameTypeImpl) value);
    }

    public String getEIN() {
        return ein;
    }

    public void setEIN(String value) {
        this.ein = value;
    }

}