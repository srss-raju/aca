//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:05 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Company Information Group Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2014-11-06&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;Global type for the Company Information &lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for CompanyInformationGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompanyInformationGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}CompanyNm"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MailingAddressGrp"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ContactNameGrp"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ContactPhoneNum"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompanyInformationGrpType", propOrder = {
    "companyNm",
    "mailingAddressGrp",
    "contactNameGrp",
    "contactPhoneNum"
})
public class CompanyInformationGrpType {

    @XmlElement(name = "CompanyNm", required = true)
    protected String companyNm;
    @XmlElement(name = "MailingAddressGrp", required = true)
    protected BusinessAddressGrpType mailingAddressGrp;
    @XmlElement(name = "ContactNameGrp", required = true)
    protected OtherCompletePersonNameType contactNameGrp;
    @XmlElement(name = "ContactPhoneNum", required = true)
    protected String contactPhoneNum;

    /**
     * Gets the value of the companyNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyNm() {
        return companyNm;
    }

    /**
     * Sets the value of the companyNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyNm(String value) {
        this.companyNm = value;
    }

    /**
     * Gets the value of the mailingAddressGrp property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessAddressGrpType }
     *     
     */
    public BusinessAddressGrpType getMailingAddressGrp() {
        return mailingAddressGrp;
    }

    /**
     * Sets the value of the mailingAddressGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessAddressGrpType }
     *     
     */
    public void setMailingAddressGrp(BusinessAddressGrpType value) {
        this.mailingAddressGrp = value;
    }

    /**
     * Gets the value of the contactNameGrp property.
     * 
     * @return
     *     possible object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    public OtherCompletePersonNameType getContactNameGrp() {
        return contactNameGrp;
    }

    /**
     * Sets the value of the contactNameGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link OtherCompletePersonNameType }
     *     
     */
    public void setContactNameGrp(OtherCompletePersonNameType value) {
        this.contactNameGrp = value;
    }

    /**
     * Gets the value of the contactPhoneNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactPhoneNum() {
        return contactPhoneNum;
    }

    /**
     * Sets the value of the contactPhoneNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactPhoneNum(String value) {
        this.contactPhoneNum = value;
    }

}
