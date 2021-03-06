//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;



/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Business Address Group Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2008-01-08&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;Type for a Exchange Periodic Data Business address &lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for BusinessAddressGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessAddressGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}USAddressGrp"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ForeignAddressGrp"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface BusinessAddressGrpType {


    /**
     * Gets the value of the foreignAddressGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ForeignAddressGrp }
     *     
     */
    ForeignAddressGrp getForeignAddressGrp();

    /**
     * Sets the value of the foreignAddressGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ForeignAddressGrp }
     *     
     */
    void setForeignAddressGrp(ForeignAddressGrp value);

    /**
     * Gets the value of the usAddressGrp property.
     * 
     * @return
     *     possible object is
     *     {@link USAddressGrp }
     *     
     */
    USAddressGrp getUSAddressGrp();

    /**
     * Sets the value of the usAddressGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link USAddressGrp }
     *     
     */
    void setUSAddressGrp(USAddressGrp value);

}
