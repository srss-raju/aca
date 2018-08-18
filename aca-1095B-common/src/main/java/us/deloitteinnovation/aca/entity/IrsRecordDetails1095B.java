package us.deloitteinnovation.aca.entity;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Date;

/**
 */
@Entity
@BatchSize(size=200)
@Table(name = "IRS_RECORD_DETAILS_1095B")
public class IrsRecordDetails1095B {

    @EmbeddedId
    private IrsRecordDetails1095BPK id;
    @Column(name = "RECORD_STATUS", length = 45)
    private String recordStatus;
    @Column(name = "SOURCE_CD", nullable = false, length = 15)
    private String sourceCode;
    @Column(name = "SOURCE_UNIQUE_ID", nullable = false, length = 15)
    private Long sourceUniqueId;
    @Column(name = "FILER_DEMO_SEQ", nullable = false)
    private Long filerDemoSeq;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE", nullable = false)
    private Date updatedDate;
    
    @Column(name = "UPDATED_BY", nullable = false, length = 45)
    private String updatedBy;

    public IrsRecordDetails1095BPK getId() {
        return id;
    }

    public void setId(IrsRecordDetails1095BPK id) {
        this.id = id;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Long getSourceUniqueId() {
        return sourceUniqueId;
    }

    public void setSourceUniqueId(Long sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public Long getFilerDemoSeq() {
        return filerDemoSeq;
    }

    public void setFilerDemoSeq(Long filerDemoSeq) {
        this.filerDemoSeq = filerDemoSeq;
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

        IrsRecordDetails1095B that = (IrsRecordDetails1095B) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
