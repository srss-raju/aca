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
import us.gov.treasury.irs.ext.aca.air._7.ACABatchSubmsnReqGrpDetailType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACABatchSubmsnReqGrpDetailType", propOrder = {
    "formSubmissionTypeCd",
    "requestSentTs",
    "requestId"
})
public class ACABatchSubmsnReqGrpDetailTypeImpl
    implements ACABatchSubmsnReqGrpDetailType
{

    @XmlElement(name = "FormSubmissionTypeCd", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String formSubmissionTypeCd;
    @XmlElement(name = "RequestSentTs", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String requestSentTs;
    @XmlElement(name = "RequestId", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String requestId;

    public String getFormSubmissionTypeCd() {
        return formSubmissionTypeCd;
    }

    public void setFormSubmissionTypeCd(String value) {
        this.formSubmissionTypeCd = value;
    }

    public String getRequestSentTs() {
        return requestSentTs;
    }

    public void setRequestSentTs(String value) {
        this.requestSentTs = value;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String value) {
        this.requestId = value;
    }

}
