//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.msg.acabusinessheader;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import us.gov.treasury.irs.ext.aca.air._7.ACABulkBusinessHeaderRequestType;
import us.gov.treasury.irs.ext.aca.air._7.impl.ACABulkBusinessHeaderRequestTypeImpl;
import us.gov.treasury.irs.msg.acabusinessheader.impl.ACABusinessHeaderResponseImpl;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the us.gov.treasury.irs.msg.acabusinessheader package. 
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
    private final static QName _ACABusinessHeader_QNAME = new QName("urn:us:gov:treasury:irs:msg:acabusinessheader", "ACABusinessHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: us.gov.treasury.irs.msg.acabusinessheader
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ACABusinessHeaderResponse }
     * 
     */
    public ACABusinessHeaderResponse createACABusinessHeaderResponse() {
        return new ACABusinessHeaderResponseImpl();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ACABulkBusinessHeaderRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:us:gov:treasury:irs:msg:acabusinessheader", name = "ACABusinessHeader")
    public JAXBElement<ACABulkBusinessHeaderRequestType> createACABusinessHeader(ACABulkBusinessHeaderRequestType value) {
        return new JAXBElement<ACABulkBusinessHeaderRequestType>(_ACABusinessHeader_QNAME, ((Class) ACABulkBusinessHeaderRequestTypeImpl.class), null, ((ACABulkBusinessHeaderRequestTypeImpl) value));
    }

}