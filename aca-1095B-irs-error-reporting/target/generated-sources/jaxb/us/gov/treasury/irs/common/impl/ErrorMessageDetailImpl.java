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
import us.gov.treasury.irs.common.ErrorMessageDetail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorMessageDetailType", propOrder = {
    "errorMessageCd",
    "errorMessageTxt",
    "xpathContent"
})
@XmlRootElement(name = "ErrorMessageDetail")
public class ErrorMessageDetailImpl
    implements ErrorMessageDetail
{

    @XmlElement(name = "ErrorMessageCd", required = true)
    protected String errorMessageCd;
    @XmlElement(name = "ErrorMessageTxt")
    protected String errorMessageTxt;
    @XmlElement(name = "XpathContent")
    protected String xpathContent;

    public String getErrorMessageCd() {
        return errorMessageCd;
    }

    public void setErrorMessageCd(String value) {
        this.errorMessageCd = value;
    }

    public String getErrorMessageTxt() {
        return errorMessageTxt;
    }

    public void setErrorMessageTxt(String value) {
        this.errorMessageTxt = value;
    }

    public String getXpathContent() {
        return xpathContent;
    }

    public void setXpathContent(String value) {
        this.xpathContent = value;
    }

}