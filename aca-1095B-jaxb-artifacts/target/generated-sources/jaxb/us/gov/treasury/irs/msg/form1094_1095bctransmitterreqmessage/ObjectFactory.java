//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.msg.form1094_1095bctransmitterreqmessage;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the us.gov.treasury.irs.msg.form1094_1095bctransmitterreqmessage package. 
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

    private final static QName _TransmitterManifestRequest_QNAME = new QName("urn:us:gov:treasury:irs:msg:form1094-1095BCtransmitterreqmessage", "TransmitterManifestRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: us.gov.treasury.irs.msg.form1094_1095bctransmitterreqmessage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TransmitterManifestRequestType }
     * 
     */
    public TransmitterManifestRequestType createTransmitterManifestRequestType() {
        return new TransmitterManifestRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransmitterManifestRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:us:gov:treasury:irs:msg:form1094-1095BCtransmitterreqmessage", name = "TransmitterManifestRequest")
    public JAXBElement<TransmitterManifestRequestType> createTransmitterManifestRequest(TransmitterManifestRequestType value) {
        return new JAXBElement<TransmitterManifestRequestType>(_TransmitterManifestRequest_QNAME, TransmitterManifestRequestType.class, null, value);
    }

}
