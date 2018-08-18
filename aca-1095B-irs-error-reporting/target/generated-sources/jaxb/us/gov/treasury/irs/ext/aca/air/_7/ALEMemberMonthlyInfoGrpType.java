//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.math.BigInteger;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
 * 					&lt;DictionaryEntryNm&gt;Monthly Applicable Large Employer Group Type&lt;/DictionaryEntryNm&gt;
 * 					&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;
 * 					&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;
 * 					&lt;VersionEffectiveBeginDt&gt;2015-01-27&lt;/VersionEffectiveBeginDt&gt;
 * 					&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;
 * 					&lt;DescriptionTxt&gt;Monthly Applicable Large Employer Group Information&lt;/DescriptionTxt&gt;
 * 				&lt;/Component&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for ALEMemberMonthlyInfoGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ALEMemberMonthlyInfoGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MinEssentialCvrOffrCd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ALEMemberFTECnt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}TotalEmployeeCnt" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}AggregatedGroupInd" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ALESect4980HTrnstReliefCd" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public interface ALEMemberMonthlyInfoGrpType {


    /**
     * Gets the value of the minEssentialCvrOffrCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getMinEssentialCvrOffrCd();

    /**
     * Sets the value of the minEssentialCvrOffrCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setMinEssentialCvrOffrCd(String value);

    /**
     * Gets the value of the aleMemberFTECnt property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    BigInteger getALEMemberFTECnt();

    /**
     * Sets the value of the aleMemberFTECnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    void setALEMemberFTECnt(BigInteger value);

    /**
     * Gets the value of the totalEmployeeCnt property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    BigInteger getTotalEmployeeCnt();

    /**
     * Sets the value of the totalEmployeeCnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    void setTotalEmployeeCnt(BigInteger value);

    /**
     * Gets the value of the aggregatedGroupInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getAggregatedGroupInd();

    /**
     * Sets the value of the aggregatedGroupInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setAggregatedGroupInd(String value);

    /**
     * Gets the value of the aleSect4980HTrnstReliefCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getALESect4980HTrnstReliefCd();

    /**
     * Sets the value of the aleSect4980HTrnstReliefCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setALESect4980HTrnstReliefCd(String value);

}