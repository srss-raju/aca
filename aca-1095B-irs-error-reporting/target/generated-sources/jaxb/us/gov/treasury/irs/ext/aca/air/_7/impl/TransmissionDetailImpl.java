//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.common.EPDErrorDetailType;
import us.gov.treasury.irs.common.impl.EPDErrorDetailTypeImpl;
import us.gov.treasury.irs.ext.aca.air._7.TransmissionDetail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransmissionDetailType", propOrder = {
    "taxYr",
    "replacementInd",
    "totalPayeeRecordCnt",
    "actualReceiptDt",
    "adjustedReceiptDt",
    "errorDetails"
})
@XmlRootElement(name = "TransmissionDetail")
public class TransmissionDetailImpl
    implements TransmissionDetail
{

    @XmlElement(name = "TaxYr", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlSchemaType(name = "gYear")
    protected XMLGregorianCalendar taxYr;
    @XmlElement(name = "ReplacementInd", required = true)
    protected String replacementInd;
    @XmlElement(name = "TotalPayeeRecordCnt", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger totalPayeeRecordCnt;
    @XmlElement(name = "ActualReceiptDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar actualReceiptDt;
    @XmlElement(name = "AdjustedReceiptDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar adjustedReceiptDt;
    @XmlElement(name = "ErrorDetail", type = EPDErrorDetailTypeImpl.class)
    protected List<EPDErrorDetailType> errorDetails;

    public XMLGregorianCalendar getTaxYr() {
        return taxYr;
    }

    public void setTaxYr(XMLGregorianCalendar value) {
        this.taxYr = value;
    }

    public String getReplacementInd() {
        return replacementInd;
    }

    public void setReplacementInd(String value) {
        this.replacementInd = value;
    }

    public BigInteger getTotalPayeeRecordCnt() {
        return totalPayeeRecordCnt;
    }

    public void setTotalPayeeRecordCnt(BigInteger value) {
        this.totalPayeeRecordCnt = value;
    }

    public XMLGregorianCalendar getActualReceiptDt() {
        return actualReceiptDt;
    }

    public void setActualReceiptDt(XMLGregorianCalendar value) {
        this.actualReceiptDt = value;
    }

    public XMLGregorianCalendar getAdjustedReceiptDt() {
        return adjustedReceiptDt;
    }

    public void setAdjustedReceiptDt(XMLGregorianCalendar value) {
        this.adjustedReceiptDt = value;
    }

    public List<EPDErrorDetailType> getErrorDetails() {
        if (errorDetails == null) {
            errorDetails = new ArrayList<EPDErrorDetailType>();
        }
        return this.errorDetails;
    }

}