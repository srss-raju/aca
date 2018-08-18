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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Month Covered Individual Indicator Detail Group Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2014-05-01&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;A group that contains monthly covered individual indicators.&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for MonthCoveredIndivIndDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MonthCoveredIndivIndDetailType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MonthNum"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MonthCoveredIndividualInd"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonthCoveredIndivIndDetailType", propOrder = {
    "monthNum",
    "monthCoveredIndividualInd"
})
public class MonthCoveredIndivIndDetailType {

    @XmlElement(name = "MonthNum")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected int monthNum;
    @XmlElement(name = "MonthCoveredIndividualInd", required = true)
    protected String monthCoveredIndividualInd;

    /**
     * Gets the value of the monthNum property.
     * 
     */
    public int getMonthNum() {
        return monthNum;
    }

    /**
     * Sets the value of the monthNum property.
     * 
     */
    public void setMonthNum(int value) {
        this.monthNum = value;
    }

    /**
     * Gets the value of the monthCoveredIndividualInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonthCoveredIndividualInd() {
        return monthCoveredIndividualInd;
    }

    /**
     * Sets the value of the monthCoveredIndividualInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonthCoveredIndividualInd(String value) {
        this.monthCoveredIndividualInd = value;
    }

}
