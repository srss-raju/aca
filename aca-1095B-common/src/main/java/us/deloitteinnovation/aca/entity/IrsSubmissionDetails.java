package us.deloitteinnovation.aca.entity;

import javax.persistence.*;
import java.util.Date;

/**
 */
@Entity
@Table(name = "IRS_SUBMISSION_DETAILS")
public class IrsSubmissionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer irsSubmissionDetailsId;
    @Column(name = "SUBMISSION_ID", nullable = false)
    private Integer submissionId;
    @Column(name = "TRANSMISSION_ID", nullable = false)
    private Integer transmissionId;
    @Column(name = "SUBMISSION_STATUS", length = 45)
    private String                 submissionStatus;
    @Column(name = "ORIGINAL_SUBMISSION_ID", length = 15)
    private Long                   originalSubmissionId;
    @Column(name = "ORIGINAL_TRANSMISSION_ID")
    private Long originalTransmissionId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE", nullable=false)
    private Date                   updatedDate;
    @Column(name = "UPDATED_BY", nullable = false, length = 45)
    private String                 updatedBy;

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public Long getOriginalSubmissionId() {
        return originalSubmissionId;
    }

    public void setOriginalSubmissionId(Long originalSubmissionId) {
        this.originalSubmissionId = originalSubmissionId;
    }

    public Long getOriginalTransmissionId() {
        return originalTransmissionId;
    }

    public void setOriginalTransmissionId(Long originalTransmissionId) {
        this.originalTransmissionId = originalTransmissionId;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public Integer getIrsSubmissionDetailsId() {
        return irsSubmissionDetailsId;
    }

    public void setIrsSubmissionDetailsId(Integer irsSubmissionDetailsId) {
        this.irsSubmissionDetailsId = irsSubmissionDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IrsSubmissionDetails that = (IrsSubmissionDetails) o;

        return irsSubmissionDetailsId != null ? irsSubmissionDetailsId.equals(that.irsSubmissionDetailsId) : that.irsSubmissionDetailsId == null;

    }

    @Override
    public int hashCode() {
        return irsSubmissionDetailsId != null ? irsSubmissionDetailsId.hashCode() : 0;
    }
}
