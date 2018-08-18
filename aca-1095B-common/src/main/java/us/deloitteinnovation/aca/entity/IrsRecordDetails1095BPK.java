package us.deloitteinnovation.aca.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 */
@Embeddable
public class IrsRecordDetails1095BPK implements Serializable {

    @Column(name = "RECORD_ID", nullable = false)
    private Integer recordId;
    @Column(name = "SUBMISSION_ID", nullable = false)
    private Integer submissionId;
    @Column(name = "TRANSMISSION_ID", nullable = false)
    private Integer transmissionId;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Integer submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(Integer transmissionId) {
        this.transmissionId = transmissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IrsRecordDetails1095BPK that = (IrsRecordDetails1095BPK) o;

        if (recordId != null ? !recordId.equals(that.recordId) : that.recordId != null) return false;
        if (submissionId != null ? !submissionId.equals(that.submissionId) : that.submissionId != null) return false;
        return transmissionId != null ? transmissionId.equals(that.transmissionId) : that.transmissionId == null;

    }

    @Override
    public int hashCode() {
        int result = recordId != null ? recordId.hashCode() : 0;
        result = 31 * result + (submissionId != null ? submissionId.hashCode() : 0);
        result = 31 * result + (transmissionId != null ? transmissionId.hashCode() : 0);
        return result;
    }
}
