//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:05 PM IST 
//


package us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Form109495BTransmittalUpstream_QNAME = new QName("urn:us:gov:treasury:irs:msg:form1094-1095Btransmitterupstreammessage", "Form109495BTransmittalUpstream");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Form109495BTrnsmtUpstreamType }
     * 
     */
    public Form109495BTrnsmtUpstreamType createForm109495BTrnsmtUpstreamType() {
        return new Form109495BTrnsmtUpstreamType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Form109495BTrnsmtUpstreamType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:us:gov:treasury:irs:msg:form1094-1095Btransmitterupstreammessage", name = "Form109495BTransmittalUpstream")
    public JAXBElement<Form109495BTrnsmtUpstreamType> createForm109495BTransmittalUpstream(Form109495BTrnsmtUpstreamType value) {
        return new JAXBElement<Form109495BTrnsmtUpstreamType>(_Form109495BTransmittalUpstream_QNAME, Form109495BTrnsmtUpstreamType.class, null, value);
    }

}
