package us.deloitteinnovation.aca.entity;

import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Primary key class for the filer_demographics_staging table.
 */
public class FilerDemographicStagingPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "filerdemographics_staging_seq")
    @SequenceGenerator(name = "filerdemographics_staging_seq", sequenceName = "filerdemographics_staging_seq")
    @Column(name = "ROW_ID")
    private int rowId;

    @Digits(fraction = 0, integer = 15, message = ErrorMessageConstants.RECIPIENT_UNIQUE_ID_REQ_NUM)
    @NotNull(message = ErrorMessageConstants.RECIPIENT_UNIQUE_ID_REQ)
    @Column(name = CommonEntityConstants.RECIPIENT_UNIQUE_ID)
    private long sourceUniqueId;

    @NotNull(message = ErrorMessageConstants.RRECIPIENT_STATE_CODE_REQ)
    @Column(name = "SOURCE_CD")
    private String sourceCd;

    public FilerDemographicStagingPK() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public long getSourceUniqueId() {
        return sourceUniqueId;
    }

    public void setSourceUniqueId(long sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }
}
