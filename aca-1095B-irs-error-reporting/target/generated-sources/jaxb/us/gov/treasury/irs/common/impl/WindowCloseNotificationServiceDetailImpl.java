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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.BooleanStringType;
import us.gov.treasury.irs.common.WindowCloseNotificationServiceDetail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WindowCloseNotificationServiceDetailType", propOrder = {
    "serviceNm",
    "serviceVersionNum",
    "serviceWindowCloseInd"
})
@XmlRootElement(name = "WindowCloseNotificationServiceDetail")
public class WindowCloseNotificationServiceDetailImpl
    implements WindowCloseNotificationServiceDetail
{

    @XmlElement(name = "ServiceNm", required = true)
    protected String serviceNm;
    @XmlElement(name = "ServiceVersionNum", required = true)
    protected String serviceVersionNum;
    @XmlElement(name = "ServiceWindowCloseInd", required = true)
    @XmlSchemaType(name = "string")
    protected BooleanStringType serviceWindowCloseInd;

    public String getServiceNm() {
        return serviceNm;
    }

    public void setServiceNm(String value) {
        this.serviceNm = value;
    }

    public String getServiceVersionNum() {
        return serviceVersionNum;
    }

    public void setServiceVersionNum(String value) {
        this.serviceVersionNum = value;
    }

    public BooleanStringType getServiceWindowCloseInd() {
        return serviceWindowCloseInd;
    }

    public void setServiceWindowCloseInd(BooleanStringType value) {
        this.serviceWindowCloseInd = value;
    }

}