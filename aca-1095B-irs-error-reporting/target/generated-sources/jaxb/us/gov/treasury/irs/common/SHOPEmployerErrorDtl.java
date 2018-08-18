//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.common;

import java.util.List;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:common" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;SHOP Employer Error Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2013-04-15&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;SHOP employer policy error details in the exchange generated monthly report&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for SHOPEmployerErrorDtlType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SHOPEmployerErrorDtlType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}EIN"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SHOPEmployerInfoErrorDtl" maxOccurs="12"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}SHOPEmployeeErrorDtl" maxOccurs="1000"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:common}EPDErrorDetail" maxOccurs="99" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface SHOPEmployerErrorDtl {


    /**
     * Gets the value of the ein property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getEIN();

    /**
     * Sets the value of the ein property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setEIN(String value);

    /**
     * Gets the value of the shopEmployerInfoErrorDtls property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shopEmployerInfoErrorDtls property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSHOPEmployerInfoErrorDtls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SHOPEmployerInfoErrorDtl }
     * 
     * 
     */
    List<SHOPEmployerInfoErrorDtl> getSHOPEmployerInfoErrorDtls();

    /**
     * Gets the value of the shopEmployeeErrorDtls property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shopEmployeeErrorDtls property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSHOPEmployeeErrorDtls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SHOPEmployeeErrorDtl }
     * 
     * 
     */
    List<SHOPEmployeeErrorDtl> getSHOPEmployeeErrorDtls();

    /**
     * Gets the value of the epdErrorDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the epdErrorDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEPDErrorDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EPDErrorDetailType }
     * 
     * 
     */
    List<EPDErrorDetailType> getEPDErrorDetails();

}