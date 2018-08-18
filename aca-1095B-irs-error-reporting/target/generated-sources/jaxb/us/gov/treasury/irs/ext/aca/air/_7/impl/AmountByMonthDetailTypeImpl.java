//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7.impl;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.ext.aca.air._7.AmountByMonthDetailType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountByMonthDetailType", propOrder = {
    "januaryAmt",
    "februaryAmt",
    "marchAmt",
    "aprilAmt",
    "mayAmt",
    "juneAmt",
    "julyAmt",
    "augustAmt",
    "septemberAmt",
    "octoberAmt",
    "novemberAmt",
    "decemberAmt"
})
public class AmountByMonthDetailTypeImpl
    implements AmountByMonthDetailType
{

    @XmlElement(name = "JanuaryAmt")
    protected BigDecimal januaryAmt;
    @XmlElement(name = "FebruaryAmt")
    protected BigDecimal februaryAmt;
    @XmlElement(name = "MarchAmt")
    protected BigDecimal marchAmt;
    @XmlElement(name = "AprilAmt")
    protected BigDecimal aprilAmt;
    @XmlElement(name = "MayAmt")
    protected BigDecimal mayAmt;
    @XmlElement(name = "JuneAmt")
    protected BigDecimal juneAmt;
    @XmlElement(name = "JulyAmt")
    protected BigDecimal julyAmt;
    @XmlElement(name = "AugustAmt")
    protected BigDecimal augustAmt;
    @XmlElement(name = "SeptemberAmt")
    protected BigDecimal septemberAmt;
    @XmlElement(name = "OctoberAmt")
    protected BigDecimal octoberAmt;
    @XmlElement(name = "NovemberAmt")
    protected BigDecimal novemberAmt;
    @XmlElement(name = "DecemberAmt")
    protected BigDecimal decemberAmt;

    public BigDecimal getJanuaryAmt() {
        return januaryAmt;
    }

    public void setJanuaryAmt(BigDecimal value) {
        this.januaryAmt = value;
    }

    public BigDecimal getFebruaryAmt() {
        return februaryAmt;
    }

    public void setFebruaryAmt(BigDecimal value) {
        this.februaryAmt = value;
    }

    public BigDecimal getMarchAmt() {
        return marchAmt;
    }

    public void setMarchAmt(BigDecimal value) {
        this.marchAmt = value;
    }

    public BigDecimal getAprilAmt() {
        return aprilAmt;
    }

    public void setAprilAmt(BigDecimal value) {
        this.aprilAmt = value;
    }

    public BigDecimal getMayAmt() {
        return mayAmt;
    }

    public void setMayAmt(BigDecimal value) {
        this.mayAmt = value;
    }

    public BigDecimal getJuneAmt() {
        return juneAmt;
    }

    public void setJuneAmt(BigDecimal value) {
        this.juneAmt = value;
    }

    public BigDecimal getJulyAmt() {
        return julyAmt;
    }

    public void setJulyAmt(BigDecimal value) {
        this.julyAmt = value;
    }

    public BigDecimal getAugustAmt() {
        return augustAmt;
    }

    public void setAugustAmt(BigDecimal value) {
        this.augustAmt = value;
    }

    public BigDecimal getSeptemberAmt() {
        return septemberAmt;
    }

    public void setSeptemberAmt(BigDecimal value) {
        this.septemberAmt = value;
    }

    public BigDecimal getOctoberAmt() {
        return octoberAmt;
    }

    public void setOctoberAmt(BigDecimal value) {
        this.octoberAmt = value;
    }

    public BigDecimal getNovemberAmt() {
        return novemberAmt;
    }

    public void setNovemberAmt(BigDecimal value) {
        this.novemberAmt = value;
    }

    public BigDecimal getDecemberAmt() {
        return decemberAmt;
    }

    public void setDecemberAmt(BigDecimal value) {
        this.decemberAmt = value;
    }

}
