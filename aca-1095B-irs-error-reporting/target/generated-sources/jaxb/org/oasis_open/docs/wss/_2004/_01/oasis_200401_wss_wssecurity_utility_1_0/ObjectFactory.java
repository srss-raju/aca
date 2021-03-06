//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.impl.AttributedDateTimeImpl;
import org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.impl.AttributedURIImpl;
import org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0.impl.TimestampImpl;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0 package. 
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

    private final static Void _useJAXBProperties = null;
    private final static QName _Created_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Created");
    private final static QName _Expires_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Expires");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_utility_1_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Timestamp }
     * 
     */
    public Timestamp createTimestamp() {
        return new TimestampImpl();
    }

    /**
     * Create an instance of {@link AttributedDateTime }
     * 
     */
    public AttributedDateTime createAttributedDateTime() {
        return new AttributedDateTimeImpl();
    }

    /**
     * Create an instance of {@link AttributedURI }
     * 
     */
    public AttributedURI createAttributedURI() {
        return new AttributedURIImpl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributedDateTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", name = "Created")
    public JAXBElement<AttributedDateTime> createCreated(AttributedDateTime value) {
        return new JAXBElement<AttributedDateTime>(_Created_QNAME, ((Class) AttributedDateTimeImpl.class), null, ((AttributedDateTimeImpl) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttributedDateTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", name = "Expires")
    public JAXBElement<AttributedDateTime> createExpires(AttributedDateTime value) {
        return new JAXBElement<AttributedDateTime>(_Expires_QNAME, ((Class) AttributedDateTimeImpl.class), null, ((AttributedDateTimeImpl) value));
    }

}
