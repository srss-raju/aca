package us.deloitteinnovation.aca.entity;

import javax.persistence.*;
import java.util.Date;


/**
 */
@Entity
@Table(name = "IRS_TRANSMISSION_DETAILS")
public class IrsTransmissionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSMISSION_ID")
    private Integer transmissionId;
    @Column(name = "TRANSFER_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date    transferDate;
    @Column(name = "TRANSMISSION_FORM_TYPE", nullable = false, length = 1)
    private String  transmissionFormType;
    @Column(name = "TAX_YEAR", nullable = false)
    private Integer taxYear;
    @Column(name = "SOURCE_CD", nullable = false, length = 45)
    private String  sourceCd;
    @Column(name = "TRANSMISSION_FILE_NAME", nullable = false, length = 45)
    private String  transmissionFileName;
    @Column(name = "TRANSMISSION_TYPE_CD", nullable = false, length = 1)
    private String  transmissionTypeCd;
    @Column(name = "TRANSMISSION_RECEIPT_ID", length = 45)
    private String  transmissionReceiptId;
    @Column(name = "TRANSMISSION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date    transmissionDate;
    /**
     * @see BatchExportEntityConstants.FilerXmlStatus
     */
    @Column(name = "TRANSMISSION_ACK_STATUS", length = 45)
    private String  transmissionAckStatus;
    @Column(name = "TRANSMISSION_ACK_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date    transmissionAckDate;
    @Column(name = "UPDATED_DATE", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date    updatedDate;
    @Column(name = "UPDATED_BY", nullable = false, length = 45)
    private String  updatedBy;

    public Integer getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(Integer transmissionId) {
        this.transmissionId = transmissionId;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransmissionFormType() {
        return transmissionFormType;
    }

    public void setTransmissionFormType(String transmissionFormType) {
        this.transmissionFormType = transmissionFormType;
    }

    public Integer getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(Integer taxYear) {
        this.taxYear = taxYear;
    }

    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public String getTransmissionFileName() {
        return transmissionFileName;
    }

    public void setTransmissionFileName(String transmissionFileName) {
        this.transmissionFileName = transmissionFileName;
    }

    public String getTransmissionTypeCd() {
        return transmissionTypeCd;
    }

    public void setTransmissionTypeCd(String transmissionTypeCd) {
        this.transmissionTypeCd = transmissionTypeCd;
    }

    public String getTransmissionReceiptId() {
        return transmissionReceiptId;
    }

    public void setTransmissionReceiptId(String transmissionReceiptId) {
        this.transmissionReceiptId = transmissionReceiptId;
    }

    public Date getTransmissionDate() {
        return transmissionDate;
    }

    public void setTransmissionDate(Date transmissionDate) {
        this.transmissionDate = transmissionDate;
    }

    public String getTransmissionAckStatus() {
        return transmissionAckStatus;
    }

    public void setTransmissionAckStatus(String transmissionAckStatus) {
        this.transmissionAckStatus = transmissionAckStatus;
    }

    public Date getTransmissionAckDate() {
        return transmissionAckDate;
    }

    public void setTransmissionAckDate(Date transmissionAckDate) {
        this.transmissionAckDate = transmissionAckDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IrsTransmissionDetails that = (IrsTransmissionDetails) o;

        return transmissionId != null ? transmissionId.equals(that.transmissionId) : that.transmissionId == null;

    }

    @Override
    public int hashCode() {
        return transmissionId != null ? transmissionId.hashCode() : 0;
    }
}
