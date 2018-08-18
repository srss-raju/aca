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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import us.gov.treasury.irs.ext.aca.air._7.CorrectedRecordInfoGrp;
import us.gov.treasury.irs.ext.aca.air._7.CoveredIndividualGrp;
import us.gov.treasury.irs.ext.aca.air._7.EmployeeInfoGrp;
import us.gov.treasury.irs.ext.aca.air._7.EmployeeOfferAndCoverageGrp;
import us.gov.treasury.irs.ext.aca.air._7.Form1095CUpstreamDetail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Form1095CUpstreamDetailType", propOrder = {
    "recordId",
    "testScenarioId",
    "correctedInd",
    "correctedRecordInfoGrp",
    "taxYr",
    "employeeInfoGrp",
    "aleContactPhoneNum",
    "startMonthNumberCd",
    "employeeOfferAndCoverageGrp",
    "coveredIndividualInd",
    "coveredIndividualGrps"
})
@XmlRootElement(name = "Form1095CUpstreamDetail")
public class Form1095CUpstreamDetailImpl
    implements Form1095CUpstreamDetail
{

    @XmlElement(name = "RecordId", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger recordId;
    @XmlElement(name = "TestScenarioId")
    protected String testScenarioId;
    @XmlElement(name = "CorrectedInd", required = true)
    protected String correctedInd;
    @XmlElement(name = "CorrectedRecordInfoGrp", type = CorrectedRecordInfoGrpImpl.class)
    protected CorrectedRecordInfoGrpImpl correctedRecordInfoGrp;
    @XmlElement(name = "TaxYr", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "gYear")
    protected XMLGregorianCalendar taxYr;
    @XmlElement(name = "EmployeeInfoGrp", type = EmployeeInfoGrpImpl.class)
    protected EmployeeInfoGrpImpl employeeInfoGrp;
    @XmlElement(name = "ALEContactPhoneNum")
    protected String aleContactPhoneNum;
    @XmlElement(name = "StartMonthNumberCd")
    protected String startMonthNumberCd;
    @XmlElement(name = "EmployeeOfferAndCoverageGrp", type = EmployeeOfferAndCoverageGrpImpl.class)
    protected EmployeeOfferAndCoverageGrpImpl employeeOfferAndCoverageGrp;
    @XmlElement(name = "CoveredIndividualInd")
    protected String coveredIndividualInd;
    @XmlElement(name = "CoveredIndividualGrp", type = CoveredIndividualGrpImpl.class)
    protected List<CoveredIndividualGrp> coveredIndividualGrps;
    @XmlAttribute(name = "recordType", required = true)
    protected String recordType;
    @XmlAttribute(name = "lineNum", required = true)
    protected BigInteger lineNum;

    public BigInteger getRecordId() {
        return recordId;
    }

    public void setRecordId(BigInteger value) {
        this.recordId = value;
    }

    public String getTestScenarioId() {
        return testScenarioId;
    }

    public void setTestScenarioId(String value) {
        this.testScenarioId = value;
    }

    public String getCorrectedInd() {
        return correctedInd;
    }

    public void setCorrectedInd(String value) {
        this.correctedInd = value;
    }

    public CorrectedRecordInfoGrp getCorrectedRecordInfoGrp() {
        return correctedRecordInfoGrp;
    }

    public void setCorrectedRecordInfoGrp(CorrectedRecordInfoGrp value) {
        this.correctedRecordInfoGrp = ((CorrectedRecordInfoGrpImpl) value);
    }

    public XMLGregorianCalendar getTaxYr() {
        return taxYr;
    }

    public void setTaxYr(XMLGregorianCalendar value) {
        this.taxYr = value;
    }

    public EmployeeInfoGrp getEmployeeInfoGrp() {
        return employeeInfoGrp;
    }

    public void setEmployeeInfoGrp(EmployeeInfoGrp value) {
        this.employeeInfoGrp = ((EmployeeInfoGrpImpl) value);
    }

    public String getALEContactPhoneNum() {
        return aleContactPhoneNum;
    }

    public void setALEContactPhoneNum(String value) {
        this.aleContactPhoneNum = value;
    }

    public String getStartMonthNumberCd() {
        return startMonthNumberCd;
    }

    public void setStartMonthNumberCd(String value) {
        this.startMonthNumberCd = value;
    }

    public EmployeeOfferAndCoverageGrp getEmployeeOfferAndCoverageGrp() {
        return employeeOfferAndCoverageGrp;
    }

    public void setEmployeeOfferAndCoverageGrp(EmployeeOfferAndCoverageGrp value) {
        this.employeeOfferAndCoverageGrp = ((EmployeeOfferAndCoverageGrpImpl) value);
    }

    public String getCoveredIndividualInd() {
        return coveredIndividualInd;
    }

    public void setCoveredIndividualInd(String value) {
        this.coveredIndividualInd = value;
    }

    public List<CoveredIndividualGrp> getCoveredIndividualGrps() {
        if (coveredIndividualGrps == null) {
            coveredIndividualGrps = new ArrayList<CoveredIndividualGrp>();
        }
        return this.coveredIndividualGrps;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String value) {
        this.recordType = value;
    }

    public BigInteger getLineNum() {
        return lineNum;
    }

    public void setLineNum(BigInteger value) {
        this.lineNum = value;
    }

}