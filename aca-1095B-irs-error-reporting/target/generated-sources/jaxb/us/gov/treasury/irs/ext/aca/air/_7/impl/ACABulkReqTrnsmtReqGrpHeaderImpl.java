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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import us.gov.treasury.irs.ext.aca.air._7.ACABulkReqTrnsmtReqGrpHeader;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACABulkReqTrnsmtReqGrpHeaderType", propOrder = {
    "userSiteMinderSessionTxt",
    "authorizationTxt",
    "userId",
    "acaBusinessCorrelationId",
    "messageSentTs"
})
@XmlRootElement(name = "ACABulkReqTrnsmtReqGrpHeader")
public class ACABulkReqTrnsmtReqGrpHeaderImpl
    implements ACABulkReqTrnsmtReqGrpHeader
{

    @XmlElement(name = "UserSiteMinderSessionTxt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String userSiteMinderSessionTxt;
    @XmlElement(name = "AuthorizationTxt", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String authorizationTxt;
    @XmlElement(name = "UserId", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String userId;
    @XmlElement(name = "ACABusinessCorrelationId", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String acaBusinessCorrelationId;
    @XmlElement(name = "MessageSentTs", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String messageSentTs;

    public String getUserSiteMinderSessionTxt() {
        return userSiteMinderSessionTxt;
    }

    public void setUserSiteMinderSessionTxt(String value) {
        this.userSiteMinderSessionTxt = value;
    }

    public String getAuthorizationTxt() {
        return authorizationTxt;
    }

    public void setAuthorizationTxt(String value) {
        this.authorizationTxt = value;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String value) {
        this.userId = value;
    }

    public String getACABusinessCorrelationId() {
        return acaBusinessCorrelationId;
    }

    public void setACABusinessCorrelationId(String value) {
        this.acaBusinessCorrelationId = value;
    }

    public String getMessageSentTs() {
        return messageSentTs;
    }

    public void setMessageSentTs(String value) {
        this.messageSentTs = value;
    }

}
