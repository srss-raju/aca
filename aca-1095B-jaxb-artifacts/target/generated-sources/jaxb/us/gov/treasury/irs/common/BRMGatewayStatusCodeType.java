//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BRMGatewayStatusCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BRMGatewayStatusCodeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NEW"/&gt;
 *     &lt;enumeration value="INPROCESS"/&gt;
 *     &lt;enumeration value="CHUNKED"/&gt;
 *     &lt;enumeration value="ERROR"/&gt;
 *     &lt;enumeration value="CHUNKS RECEIVED"/&gt;
 *     &lt;enumeration value="RESPONSE FILE CONSOLIDATED"/&gt;
 *     &lt;enumeration value="BRM GATEWAY NOTIFIED"/&gt;
 *     &lt;enumeration value="RESPONSE SUBMITTED TO CMS"/&gt;
 *     &lt;enumeration value="COMPLETED"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "BRMGatewayStatusCodeType")
@XmlEnum
public enum BRMGatewayStatusCodeType {

    NEW("NEW"),
    INPROCESS("INPROCESS"),
    CHUNKED("CHUNKED"),
    ERROR("ERROR"),
    @XmlEnumValue("CHUNKS RECEIVED")
    CHUNKS_RECEIVED("CHUNKS RECEIVED"),
    @XmlEnumValue("RESPONSE FILE CONSOLIDATED")
    RESPONSE_FILE_CONSOLIDATED("RESPONSE FILE CONSOLIDATED"),
    @XmlEnumValue("BRM GATEWAY NOTIFIED")
    BRM_GATEWAY_NOTIFIED("BRM GATEWAY NOTIFIED"),
    @XmlEnumValue("RESPONSE SUBMITTED TO CMS")
    RESPONSE_SUBMITTED_TO_CMS("RESPONSE SUBMITTED TO CMS"),
    COMPLETED("COMPLETED");
    private final String value;

    BRMGatewayStatusCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BRMGatewayStatusCodeType fromValue(String v) {
        for (BRMGatewayStatusCodeType c: BRMGatewayStatusCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
