package us.deloitteinnovation.aca.batch.dto;

import org.hibernate.validator.constraints.NotBlank;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by rgopalani on 10/20/2015.
 */
public class FilerDemographicPKDto implements Serializable {
    private static final long serialVersionUID = 1L;


    //@Digits(fraction = 0, integer = 15, message = "{rid.length.invalid}")
    //@NotNull(message = "{rid.length.invalid}")
    @NotBlank(message = "{rid.length.invalid}")
    @Pattern.List({
            @Pattern(regexp = "^.{1,15}$", message = "{rid.length.invalid}"),
            @Pattern(regexp = "\\d+", message = "{rid.content.invalid}")
//            @Pattern(regexp = "^[0-9]+[1-9]+$", message = "{rid.content.all.zeros.error}")
    })
    private String sourceUniqueId;

    private String sourceCd;

    @NotBlank(message = ErrorMessageConstants.TAX_YEAR_LENGTH_LIMIT)
    @Pattern.List({
            @Pattern(regexp = "^[0-9]{4}$", message = ErrorMessageConstants.TAX_YEAR_LENGTH_LIMIT),
            @Pattern(regexp = "(2015|2016)", message = "{tax.year.invalid}")
    })
    private String taxYear;

    public FilerDemographicPKDto() {
    }

    public String getSourceUniqueId() {
        return this.sourceUniqueId;
    }

    public void setSourceUniqueId(String sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public String getSourceCd() {
        return this.sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public String getTaxYear() {
        return this.taxYear;
    }

    public void setTaxYear(String taxYear) {
        this.taxYear = taxYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilerDemographicPKDto that = (FilerDemographicPKDto) o;

        if (getSourceUniqueId() != null ? !getSourceUniqueId().equals(that.getSourceUniqueId()) : that.getSourceUniqueId() != null)
            return false;
        return !(getSourceCd() != null ? !getSourceCd().equals(that.getSourceCd()) : that.getSourceCd() != null);

    }

    @Override
    public int hashCode() {
        int result = getSourceUniqueId() != null ? getSourceUniqueId().hashCode() : 0;
        result = 31 * result + (getSourceCd() != null ? getSourceCd().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return sourceUniqueId + "-" + sourceCd;
    }
}
