//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.09 at 10:20:32 PM IST 
//


package us.gov.treasury.irs.common.impl;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import us.gov.treasury.irs.common.ACABatchManifestDetail;
import us.gov.treasury.irs.common.ACABulkBatchManifestDetail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACABulkBatchManifestDetailType", propOrder = {
    "acaBatchManifestDetail",
    "bulkExchangeFile"
})
@XmlRootElement(name = "ACABulkBatchManifestDetail")
public class ACABulkBatchManifestDetailImpl
    implements ACABulkBatchManifestDetail
{

    @XmlElement(name = "ACABatchManifestDetail", required = true, type = ACABatchManifestDetailImpl.class)
    protected ACABatchManifestDetailImpl acaBatchManifestDetail;
    @XmlElement(name = "BulkExchangeFile", required = true)
    @XmlMimeType("*/*")
    protected DataHandler bulkExchangeFile;

    public ACABatchManifestDetail getACABatchManifestDetail() {
        return acaBatchManifestDetail;
    }

    public void setACABatchManifestDetail(ACABatchManifestDetail value) {
        this.acaBatchManifestDetail = ((ACABatchManifestDetailImpl) value);
    }

    public DataHandler getBulkExchangeFile() {
        return bulkExchangeFile;
    }

    public void setBulkExchangeFile(DataHandler value) {
        this.bulkExchangeFile = value;
    }

}
