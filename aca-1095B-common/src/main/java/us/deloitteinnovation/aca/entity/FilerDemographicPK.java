package us.deloitteinnovation.aca.entity;


import org.springframework.util.Assert;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * The primary key class for the filer_demographics database table.
 * 
 */
@Embeddable
public class FilerDemographicPK implements Serializable {

	private static final long serialVersionUID = 1L;

	//@BigIntegerLength(min = 15, max = 15, message = ErrorMessageConstants.RECIPIENT_UNIQUE_ID_LENGTH_LIMIT)
    @Digits(fraction = 0, integer = 15, message = ErrorMessageConstants.RECIPIENT_UNIQUE_ID_REQ_NUM)
    @Column(name= CommonEntityConstants.RECIPIENT_UNIQUE_ID)
	private long sourceUniqueId;

	@Column(name="SOURCE_CD")
	private String sourceCd;


	//@IntegerLength(min = 4, max = 4, message = ErrorMessageConstants.TAX_YEAR_LENGTH_LIMIT)
	@Digits(fraction = 0, integer = 4, message = ErrorMessageConstants.TAX_YEAR_REQ_NUM)
	@Column(name = CommonEntityConstants.TAX_YEAR)
	private Integer taxYear;


	public FilerDemographicPK() {
		/* Default COnstructor */
	}

	public FilerDemographicPK(String formId) {
		List<String> components = Arrays.asList(formId.split("_"));
		Assert.isTrue(components.size() >= 2); // a formID should have at least 2 components
		this.sourceCd = components.get(0);
		this.sourceUniqueId = Long.parseLong(components.get(1));
		if (components.size() >= 3) {
			this.taxYear = Integer.parseInt(components.get(2));
		} else {
			this.taxYear = 2015;
		}
	}

	public FilerDemographicPK(long sourceUniqueId, String sourceCd, int taxYear) {
		this.sourceUniqueId = sourceUniqueId;
		this.sourceCd = sourceCd;
		this.taxYear = taxYear;
	}

	public FilerDemographicPK(long sourceUniqueId, String sourceCd) {
		this(sourceUniqueId, sourceCd, 2015);
	}

	public Integer getTaxYear() {
		return taxYear;
	}

	public void setTaxYear(Integer taxYear) {
		this.taxYear = taxYear;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FilerDemographicPK that = (FilerDemographicPK) o;

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