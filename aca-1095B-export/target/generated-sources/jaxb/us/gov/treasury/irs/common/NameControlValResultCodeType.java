//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:05 PM IST 
//


package us.gov.treasury.irs.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NameControlValResultCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NameControlValResultCodeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="EXACT_MATCH"/&gt;
 *     &lt;enumeration value="PROXIMAL_MATCH"/&gt;
 *     &lt;enumeration value="NO_MATCH"/&gt;
 *     &lt;enumeration value="TIN_NOT_FOUND"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "NameControlValResultCodeType")
@XmlEnum
public enum NameControlValResultCodeType {

    EXACT_MATCH,
    PROXIMAL_MATCH,
    NO_MATCH,
    TIN_NOT_FOUND;

    public String value() {
        return name();
    }

    public static NameControlValResultCodeType fromValue(String v) {
        return valueOf(v);
    }

}
