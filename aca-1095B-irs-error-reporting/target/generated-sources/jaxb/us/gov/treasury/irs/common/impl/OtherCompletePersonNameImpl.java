//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.common.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.OtherCompletePersonName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OtherCompletePersonNameType", propOrder = {
    "personFirstNm",
    "personMiddleNm",
    "personLastNm",
    "suffixNm"
})
@XmlRootElement(name = "OtherCompletePersonName")
public class OtherCompletePersonNameImpl
    implements OtherCompletePersonName
{

    @XmlElement(name = "PersonFirstNm", required = true)
    protected String personFirstNm;
    @XmlElement(name = "PersonMiddleNm")
    protected String personMiddleNm;
    @XmlElement(name = "PersonLastNm", required = true)
    protected String personLastNm;
    @XmlElement(name = "SuffixNm")
    protected String suffixNm;

    public String getPersonFirstNm() {
        return personFirstNm;
    }

    public void setPersonFirstNm(String value) {
        this.personFirstNm = value;
    }

    public String getPersonMiddleNm() {
        return personMiddleNm;
    }

    public void setPersonMiddleNm(String value) {
        this.personMiddleNm = value;
    }

    public String getPersonLastNm() {
        return personLastNm;
    }

    public void setPersonLastNm(String value) {
        this.personLastNm = value;
    }

    public String getSuffixNm() {
        return suffixNm;
    }

    public void setSuffixNm(String value) {
        this.suffixNm = value;
    }

}
