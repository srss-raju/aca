//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.23 at 02:25:24 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.EPDErrorDetailType;


/**
 * 
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;Component xmlns="urn:us:gov:treasury:irs:ext:aca:air:7.0" xmlns:irs="urn:us:gov:treasury:irs:common" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;&lt;DictionaryEntryNm&gt;Monthly Covered Individual Indicator Group Type&lt;/DictionaryEntryNm&gt;&lt;MajorVersionNum&gt;1&lt;/MajorVersionNum&gt;&lt;MinorVersionNum&gt;0&lt;/MinorVersionNum&gt;&lt;VersionEffectiveBeginDt&gt;2014-05-01&lt;/VersionEffectiveBeginDt&gt;&lt;VersionDescriptionTxt&gt;Initial version&lt;/VersionDescriptionTxt&gt;&lt;DescriptionTxt&gt;A group that wraps monthly covered individual indicators.&lt;/DescriptionTxt&gt;&lt;/Component&gt;
 * </pre>
 * 
 * 
 * <p>Java class for MonthlyCoveredIndivIndGrpType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MonthlyCoveredIndivIndGrpType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}MonthCoveredIndivIndDetail" maxOccurs="12"/&gt;
 *         &lt;element ref="{urn:us:gov:treasury:irs:ext:aca:air:7.0}ErrorDetail" maxOccurs="99" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonthlyCoveredIndivIndGrpType", propOrder = {
    "monthCoveredIndivIndDetail",
    "errorDetail"
})
public class MonthlyCoveredIndivIndGrpType {

    @XmlElement(name = "MonthCoveredIndivIndDetail", required = true)
    protected List<MonthCoveredIndivIndDetailType> monthCoveredIndivIndDetail;
    @XmlElement(name = "ErrorDetail")
    protected List<EPDErrorDetailType> errorDetail;

    /**
     * Gets the value of the monthCoveredIndivIndDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monthCoveredIndivIndDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonthCoveredIndivIndDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonthCoveredIndivIndDetailType }
     * 
     * 
     */
    public List<MonthCoveredIndivIndDetailType> getMonthCoveredIndivIndDetail() {
        if (monthCoveredIndivIndDetail == null) {
            monthCoveredIndivIndDetail = new ArrayList<MonthCoveredIndivIndDetailType>();
        }
        return this.monthCoveredIndivIndDetail;
    }

    /**
     * Gets the value of the errorDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EPDErrorDetailType }
     * 
     * 
     */
    public List<EPDErrorDetailType> getErrorDetail() {
        if (errorDetail == null) {
            errorDetail = new ArrayList<EPDErrorDetailType>();
        }
        return this.errorDetail;
    }

}
