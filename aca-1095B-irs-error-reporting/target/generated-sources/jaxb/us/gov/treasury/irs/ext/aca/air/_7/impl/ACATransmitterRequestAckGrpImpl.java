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
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.ext.aca.air._7.ACATransmitterRequestAckGrp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACATransmitterRequestAckGrpType", propOrder = {
    "receiptId",
    "timestamp"
})
@XmlRootElement(name = "ACATransmitterRequestAckGrp")
public class ACATransmitterRequestAckGrpImpl
    implements ACATransmitterRequestAckGrp
{

    @XmlElement(name = "ReceiptId", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String receiptId;
    @XmlElement(name = "Timestamp", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String value) {
        this.receiptId = value;
    }

    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

}
