package us.deloitteinnovation.aca.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 */
@Embeddable
public class IrsSubmissionDetailsPK implements Serializable {

    @Column(name = "SUBMISSION_ID", nullable = false)
    private Integer submissionId;

    @Column(name = "TRANSMISSION_ID", nullable = false)
    private Integer transmissionId;

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

        IrsSubmissionDetailsPK that = (IrsSubmissionDetailsPK) o;

        if (submissionId != null ? !submissionId.equals(that.submissionId) : that.submissionId != null) return false;
        return transmissionId != null ? transmissionId.equals(that.transmissionId) : that.transmissionId == null;

    }

    @Override
    public int hashCode() {
        int result = submissionId != null ? submissionId.hashCode() : 0;
        result = 31 * result + (transmissionId != null ? transmissionId.hashCode() : 0);
        return result;
    }
}
