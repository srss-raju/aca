package us.deloitteinnovation.aca.batch.dto;

/**
 */
public class IRSTransmittalDetailsDto {
    private String sourceUniqueId;
    private String sourceCd;
    private String batchId;
    private String transmitReason;
    private String transmitStatus;
    private String transmitFileName;
    private String updatedBy;
    private String updatedDate;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public String getSourceUniqueId() {
        return sourceUniqueId;
    }

    public void setSourceUniqueId(String sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public String getTransmitFileName() {
        return transmitFileName;
    }

    public void setTransmitFileName(String transmitFileName) {
        this.transmitFileName = transmitFileName;
    }

    public String getTransmitReason() {
        return transmitReason;
    }

    public void setTransmitReason(String transmitReason) {
        this.transmitReason = transmitReason;
    }

    public String getTransmitStatus() {
        return transmitStatus;
    }

    public void setTransmitStatus(String transmitStatus) {
        this.transmitStatus = transmitStatus;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
