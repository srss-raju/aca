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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.BinaryFormatCodeType;
import us.gov.treasury.irs.ext.aca.air._7.ACAAttachmentDetailType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACAAttachmentDetailType", propOrder = {
    "binaryFormatCd",
    "checksumAugmentationNum",
    "attachmentByteSizeNum",
    "documentSystemFileNm",
    "systemDocumentId",
    "documentSequenceNum"
})
public class ACAAttachmentDetailTypeImpl
    implements ACAAttachmentDetailType
{

    @XmlElement(name = "BinaryFormatCd", namespace = "urn:us:gov:treasury:irs:common", required = true)
    @XmlSchemaType(name = "string")
    protected BinaryFormatCodeType binaryFormatCd;
    @XmlElement(name = "ChecksumAugmentationNum", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String checksumAugmentationNum;
    @XmlElement(name = "AttachmentByteSizeNum", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected BigInteger attachmentByteSizeNum;
    @XmlElement(name = "DocumentSystemFileNm", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String documentSystemFileNm;
    @XmlElement(name = "SystemDocumentId", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected String systemDocumentId;
    @XmlElement(name = "DocumentSequenceNum", namespace = "urn:us:gov:treasury:irs:common", required = true)
    protected BigInteger documentSequenceNum;

    public BinaryFormatCodeType getBinaryFormatCd() {
        return binaryFormatCd;
    }

    public void setBinaryFormatCd(BinaryFormatCodeType value) {
        this.binaryFormatCd = value;
    }

    public String getChecksumAugmentationNum() {
        return checksumAugmentationNum;
    }

    public void setChecksumAugmentationNum(String value) {
        this.checksumAugmentationNum = value;
    }

    public BigInteger getAttachmentByteSizeNum() {
        return attachmentByteSizeNum;
    }

    public void setAttachmentByteSizeNum(BigInteger value) {
        this.attachmentByteSizeNum = value;
    }

    public String getDocumentSystemFileNm() {
        return documentSystemFileNm;
    }

    public void setDocumentSystemFileNm(String value) {
        this.documentSystemFileNm = value;
    }

    public String getSystemDocumentId() {
        return systemDocumentId;
    }

    public void setSystemDocumentId(String value) {
        this.systemDocumentId = value;
    }

    public BigInteger getDocumentSequenceNum() {
        return documentSequenceNum;
    }

    public void setDocumentSequenceNum(BigInteger value) {
        this.documentSequenceNum = value;
    }

}