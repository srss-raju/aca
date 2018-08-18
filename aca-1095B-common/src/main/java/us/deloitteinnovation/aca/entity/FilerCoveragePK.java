package us.deloitteinnovation.aca.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


/**
 * The primary key class for the filer_coverage database table.
 */
@Embeddable
public class FilerCoveragePK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "SOURCE_UNIQUE_ID", nullable = false)
    private long   sourceUniqueId;
    @Column(name = "SOURCE_CD", nullable = false)
    private String sourceCd;
    @Column(name = "COVERAGE_SEQ_NO", nullable = false)
    private Long   coverageSeqNo;

    public FilerCoveragePK() {
    }

    public long getSourceUniqueId() {
        return this.sourceUniqueId;
    }

    public void setSourceUniqueId(long sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public String getSourceCd() {
        return this.sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public Long getCoverageSeqNo() {
        return coverageSeqNo;
    }

    public void setCoverageSeqNo(Long coverageSeqNo) {
        this.coverageSeqNo = coverageSeqNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilerCoveragePK that = (FilerCoveragePK) o;

        if (sourceUniqueId != that.sourceUniqueId) return false;
        if (sourceCd != null ? !sourceCd.equals(that.sourceCd) : that.sourceCd != null) return false;
        return coverageSeqNo != null ? coverageSeqNo.equals(that.coverageSeqNo) : that.coverageSeqNo == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (sourceUniqueId ^ (sourceUniqueId >>> 32));
        result = 31 * result + (sourceCd != null ? sourceCd.hashCode() : 0);
        result = 31 * result + (coverageSeqNo != null ? coverageSeqNo.hashCode() : 0);
        return result;
    }
}