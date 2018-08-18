//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.ext.aca.air._7.impl;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.NameControlTypeCodeType;
import us.gov.treasury.irs.common.NameControlValResultCodeType;
import us.gov.treasury.irs.ext.aca.air._7.NameControlValResultDetail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameControlValResultDetailType", propOrder = {
    "fileSourceCd",
    "nameControlTypeCd",
    "nameControlValResultCd",
    "taxpayerValidityCd"
})
@XmlRootElement(name = "NameControlValResultDetail")
public class NameControlValResultDetailImpl
    implements NameControlValResultDetail
{

    @XmlElement(name = "FileSourceCd", namespace = "urn:us:gov:treasury:irs:common")
    protected String fileSourceCd;
    @XmlElement(name = "NameControlTypeCd", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "string")
    protected NameControlTypeCodeType nameControlTypeCd;
    @XmlElement(name = "NameControlValResultCd", namespace = "urn:us:gov:treasury:irs:common")
    @XmlSchemaType(name = "string")
    protected NameControlValResultCodeType nameControlValResultCd;
    @XmlElement(name = "TaxpayerValidityCd", namespace = "urn:us:gov:treasury:irs:common")
    protected BigInteger taxpayerValidityCd;

    public String getFileSourceCd() {
        return fileSourceCd;
    }

    public void setFileSourceCd(String value) {
        this.fileSourceCd = value;
    }

    public NameControlTypeCodeType getNameControlTypeCd() {
        return nameControlTypeCd;
    }

    public void setNameControlTypeCd(NameControlTypeCodeType value) {
        this.nameControlTypeCd = value;
    }

    public NameControlValResultCodeType getNameControlValResultCd() {
        return nameControlValResultCd;
    }

    public void setNameControlValResultCd(NameControlValResultCodeType value) {
        this.nameControlValResultCd = value;
    }

    public BigInteger getTaxpayerValidityCd() {
        return taxpayerValidityCd;
    }

    public void setTaxpayerValidityCd(BigInteger value) {
        this.taxpayerValidityCd = value;
    }

}
