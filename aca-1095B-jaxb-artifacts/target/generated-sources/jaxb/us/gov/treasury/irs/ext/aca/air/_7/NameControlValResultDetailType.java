//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.NameControlTypeCodeType;
import us.gov.treasury.irs.common.NameControlValResultCodeType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Name Control Validation Result Detail Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2012-11-01&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;The type for the Name Control Validation Results detail&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for NameControlValResultDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NameControlValResultDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}FileSourceCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}NameControlTypeCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}NameControlValResultCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}TaxpayerValidityCd" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameControlValResultDetailType", propOrder = {
    "fileSourceCd",
    "nameControlTypeCd",
    "nameControlValResultCd",
    "taxpayerValidityCd"
})
public class NameControlValResultDetailType {

    @XmlElement(name = "FileSourceCd", namespace = "urn:us:gov:treasury:irs:common")
    protected String fileSourceCd;
    @XmlElement(name = "NameControlTypeCd", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "string")
    protected NameControlTypeCodeType nameControlTypeCd;
    @XmlElement(name = "NameControlValResultCd", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "string")
    protected NameControlValResultCodeType nameControlValResultCd;
    @XmlElement(name = "TaxpayerValidityCd", namespace = "urn:us:gov:treasury:irs:common")
    protected BigInteger taxpayerValidityCd;

    /**
     * Gets the value of the fileSourceCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileSourceCd() {
        return fileSourceCd;
    }

    /**
     * Sets the value of the fileSourceCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileSourceCd(String value) {
        this.fileSourceCd = value;
    }

    /**
     * Gets the value of the nameControlTypeCd property.
     * 
     * @return
     *     possible object is
     *     {@link NameControlTypeCodeType }
     *     
     */
    public NameControlTypeCodeType getNameControlTypeCd() {
        return nameControlTypeCd;
    }

    /**
     * Sets the value of the nameControlTypeCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameControlTypeCodeType }
     *     
     */
    public void setNameControlTypeCd(NameControlTypeCodeType value) {
        this.nameControlTypeCd = value;
    }

    /**
     * Gets the value of the nameControlValResultCd property.
     * 
     * @return
     *     possible object is
     *     {@link NameControlValResultCodeType }
     *     
     */
    public NameControlValResultCodeType getNameControlValResultCd() {
        return nameControlValResultCd;
    }

    /**
     * Sets the value of the nameControlValResultCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameControlValResultCodeType }
     *     
     */
    public void setNameControlValResultCd(NameControlValResultCodeType value) {
        this.nameControlValResultCd = value;
    }

    /**
     * Gets the value of the taxpayerValidityCd property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTaxpayerValidityCd() {
        return taxpayerValidityCd;
    }

    /**
     * Sets the value of the taxpayerValidityCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTaxpayerValidityCd(BigInteger value) {
        this.taxpayerValidityCd = value;
    }

}
