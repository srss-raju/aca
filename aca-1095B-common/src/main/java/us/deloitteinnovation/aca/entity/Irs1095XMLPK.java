package us.deloitteinnovation.aca.entity;

import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by bhchaganti on 4/5/2016.
 */

/**
 * The primary key class for the irs_1095_xml database table.
 */
@Embeddable
public class Irs1095XMLPK implements Serializable {

    private static final long serialVersionUID = 1L;

    //@BigIntegerLength(min = 15, max = 15, message = ErrorMessageConstants.RECIPIENT_UNIQUE_ID_LENGTH_LIMIT)
    @Digits(fraction = 0, integer = 15, message = ErrorMessageConstants.RECIPIENT_UNIQUE_ID_REQ_NUM)
    @NotNull(message = ErrorMessageConstants.RECIPIENT_UNIQUE_ID_REQ)
    @Column(name = CommonEntityConstants.RECIPIENT_UNIQUE_ID)
    private long sourceUniqueId;
    @Column(name = "SOURCE_CD")
    private String sourceCd;

    @Column(name = "TAX_YEAR")
    private Integer taxYear;

    public Irs1095XMLPK(){}

    public Irs1095XMLPK(final long suid, final String scd, final Integer taxYear) {

        setSourceUniqueId(suid);
        setSourceCd(scd);
        setTaxYear(taxYear);
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

    public Integer getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(Integer taxYear) {
        this.taxYear = taxYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        us.deloitteinnovation.aca.entity.Irs1095XMLPK that = (us.deloitteinnovation.aca.entity.Irs1095XMLPK) o;

        if (sourceUniqueId != that.sourceUniqueId) return false;
        return sourceCd != null ? sourceCd.equals(that.sourceCd) : that.sourceCd == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (sourceUniqueId ^ (sourceUniqueId >>> 32));
        result = 31 * result + (sourceCd != null ? sourceCd.hashCode() : 0);
        return result;
    }
}
