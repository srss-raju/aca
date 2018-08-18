package us.deloitteinnovation.aca.web.opsportal.dto;

import java.util.Date;

/**
 * Created by sdalavi on 3/31/2016.
 */
public class IrsTransmittalDetailsDto {
    private Long transmissionId;
    private Date transferDate;
    private String transmissionFileName;
    private String transmissionReceiptId;
    private Date transmissionDate;
    private String transmissionAckStatus;
    private Date transmissionAckDate;
    private Date updatedDate;
    private String updatedBy;
    private boolean recordVisible;
    private boolean rejectedStatusCorrection;
    private boolean rejectedStatusResend;
    private int taxYear;

    public Long getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(Long transmissionId) {
        this.transmissionId = transmissionId;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransmissionFileName() {
        return transmissionFileName;
    }

    public void setTransmissionFileName(String transmissionFileName) {
        this.transmissionFileName = transmissionFileName;
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

    public boolean isRecordVisible() {
        return recordVisible;
    }

    public void setRecordVisible(boolean recordVisible) {
        this.recordVisible = recordVisible;
    }

    public boolean isRejectedStatusCorrection() {
        return rejectedStatusCorrection;
    }

    public void setRejectedStatusCorrection(boolean rejectedStatusCorrection) {
        this.rejectedStatusCorrection = rejectedStatusCorrection;
    }

    public boolean isRejectedStatusResend() {
        return rejectedStatusResend;
    }

    public void setRejectedStatusResend(boolean rejectedStatusResend) {
        this.rejectedStatusResend = rejectedStatusResend;
    }

    public int getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(int taxYear) {
        this.taxYear = taxYear;
    }
}