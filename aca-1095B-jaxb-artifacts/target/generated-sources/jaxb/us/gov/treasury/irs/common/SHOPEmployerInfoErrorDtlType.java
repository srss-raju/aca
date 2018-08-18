//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:common" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;SHOP Employer Information Error Detail Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2013-10-31&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Content Type for elements associated with SHOP employer in the policy on exchange generated monthly report&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for SHOPEmployerInfoErrorDtlType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SHOPEmployerInfoErrorDtlType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}BusinessNameErrorDtl"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}BusinessAddressErrorDtl"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}EPDErrorDetail" maxOccurs="99" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SHOPEmployerInfoErrorDtlType", propOrder = {
    "businessNameErrorDtl",
    "businessAddressErrorDtl",
    "epdErrorDetail"
})
public class SHOPEmployerInfoErrorDtlType {

    @XmlElement(name = "BusinessNameErrorDtl", required = true)
    protected BusinessNameErrorDtlType businessNameErrorDtl;
    @XmlElement(name = "BusinessAddressErrorDtl", required = true)
    protected BusinessAddressErrorDtlType businessAddressErrorDtl;
    @XmlElement(name = "EPDErrorDetail")
    protected List<EPDErrorDetailType> epdErrorDetail;

    /**
     * Gets the value of the businessNameErrorDtl property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessNameErrorDtlType }
     *     
     */
    public BusinessNameErrorDtlType getBusinessNameErrorDtl() {
        return businessNameErrorDtl;
    }

    /**
     * Sets the value of the businessNameErrorDtl property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessNameErrorDtlType }
     *     
     */
    public void setBusinessNameErrorDtl(BusinessNameErrorDtlType value) {
        this.businessNameErrorDtl = value;
    }

    /**
     * Gets the value of the businessAddressErrorDtl property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessAddressErrorDtlType }
     *     
     */
    public BusinessAddressErrorDtlType getBusinessAddressErrorDtl() {
        return businessAddressErrorDtl;
    }

    /**
     * Sets the value of the businessAddressErrorDtl property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessAddressErrorDtlType }
     *     
     */
    public void setBusinessAddressErrorDtl(BusinessAddressErrorDtlType value) {
        this.businessAddressErrorDtl = value;
    }

    /**
     * Gets the value of the epdErrorDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the epdErrorDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEPDErrorDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EPDErrorDetailType }
     * 
     * 
     */
    public List<EPDErrorDetailType> getEPDErrorDetail() {
        if (epdErrorDetail == null) {
            epdErrorDetail = new ArrayList<EPDErrorDetailType>();
        }
        return this.epdErrorDetail;
    }

}
