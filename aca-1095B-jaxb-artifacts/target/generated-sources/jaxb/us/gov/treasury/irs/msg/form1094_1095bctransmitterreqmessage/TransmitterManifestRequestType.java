//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.msg.form1094_1095bctransmitterreqmessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.ext.aca.air._7.ACATrnsmtManifestReqDtlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:msg:form1094-1095BCtransmitterreqmessage" xmlns:air7.0="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Transmitter Manifest Request Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;1&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2014-11-04&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Transmitter manifest detail type for 1094-1095B/C Forms Upstream&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for TransmitterManifestRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransmitterManifestRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ACATransmitterManifestReqDtl"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransmitterManifestRequestType", propOrder = {
    "acaTransmitterManifestReqDtl"
})
public class TransmitterManifestRequestType {

    @XmlElement(name = "ACATransmitterManifestReqDtl", namespace = "urn:us:gov:treasury:irs:ext:aca:air:7.0", required = true)
    protected ACATrnsmtManifestReqDtlType acaTransmitterManifestReqDtl;

    /**
     * Gets the value of the acaTransmitterManifestReqDtl property.
     * 
     * @return
     *     possible object is
     *     {@link ACATrnsmtManifestReqDtlType }
     *     
     */
    public ACATrnsmtManifestReqDtlType getACATransmitterManifestReqDtl() {
        return acaTransmitterManifestReqDtl;
    }

    /**
     * Sets the value of the acaTransmitterManifestReqDtl property.
     * 
     * @param value
     *     allowed object is
     *     {@link ACATrnsmtManifestReqDtlType }
     *     
     */
    public void setACATransmitterManifestReqDtl(ACATrnsmtManifestReqDtlType value) {
        this.acaTransmitterManifestReqDtl = value;
    }

}
