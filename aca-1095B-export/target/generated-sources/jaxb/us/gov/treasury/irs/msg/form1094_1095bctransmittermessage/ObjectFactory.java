//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:05 PM IST 
//


package us.gov.treasury.irs.msg.form1094_1095bctransmittermessage;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the us.gov.treasury.irs.msg.form1094_1095bctransmittermessage package. 
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

    private final static QName _FormBCTransmitterSubmissionDtl_QNAME = new QName("urn:us:gov:treasury:irs:msg:form1094-1095BCtransmittermessage", "FormBCTransmitterSubmissionDtl");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: us.gov.treasury.irs.msg.form1094_1095bctransmittermessage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FormBCTrnsmtSubmissionDtlType }
     * 
     */
    public FormBCTrnsmtSubmissionDtlType createFormBCTrnsmtSubmissionDtlType() {
        return new FormBCTrnsmtSubmissionDtlType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormBCTrnsmtSubmissionDtlType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:us:gov:treasury:irs:msg:form1094-1095BCtransmittermessage", name = "FormBCTransmitterSubmissionDtl")
    public JAXBElement<FormBCTrnsmtSubmissionDtlType> createFormBCTransmitterSubmissionDtl(FormBCTrnsmtSubmissionDtlType value) {
        return new JAXBElement<FormBCTrnsmtSubmissionDtlType>(_FormBCTransmitterSubmissionDtl_QNAME, FormBCTrnsmtSubmissionDtlType.class, null, value);
    }

}
