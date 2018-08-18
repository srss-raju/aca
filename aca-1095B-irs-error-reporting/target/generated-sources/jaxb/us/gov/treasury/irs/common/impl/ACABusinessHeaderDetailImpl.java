//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.common.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.common.ACABusinessHeaderDetail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACABusinessHeaderType", propOrder = {
    "acaBusinessCorrelationId",
    "timestamp"
})
@XmlRootElement(name = "ACABusinessHeaderDetail")
public class ACABusinessHeaderDetailImpl
    implements ACABusinessHeaderDetail
{

    @XmlElement(name = "ACABusinessCorrelationId", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String acaBusinessCorrelationId;
    @XmlElement(name = "Timestamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlAttribute(name = "version")
    protected String version;

    public String getACABusinessCorrelationId() {
        return acaBusinessCorrelationId;
    }

    public void setACABusinessCorrelationId(String value) {
        this.acaBusinessCorrelationId = value;
    }

    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    public String getVersion() {
        if (version == null) {
            return "1.0";
        } else {
            return version;
        }
    }

    public void setVersion(String value) {
        this.version = value;
    }

}