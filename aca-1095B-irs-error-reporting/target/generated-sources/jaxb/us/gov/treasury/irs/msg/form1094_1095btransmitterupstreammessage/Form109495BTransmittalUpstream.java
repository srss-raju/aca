//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage;

import java.util.List;
import us.gov.treasury.irs.ext.aca.air._7.Form1094BUpstreamDetail;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:msg:form1094-1095Btransmitterupstreammessage" xmlns:air7.0="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Form109495B Transmission Upstream Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;1&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2014-11-04&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial Version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;The elements associated with 1094B/ 1095BForms EOY report Transmission&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for Form109495BTrnsmtUpstreamType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Form109495BTrnsmtUpstreamType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}Form1094BUpstreamDetail" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface Form109495BTransmittalUpstream {


    /**
     * Gets the value of the form1094BUpstreamDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the form1094BUpstreamDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getForm1094BUpstreamDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Form1094BUpstreamDetail }
     * 
     * 
     */
    List<Form1094BUpstreamDetail> getForm1094BUpstreamDetails();

}
