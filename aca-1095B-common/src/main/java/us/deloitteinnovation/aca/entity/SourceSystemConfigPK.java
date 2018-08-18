package us.deloitteinnovation.aca.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the print_details database table.
 * 
 */
@Embeddable
public class SourceSystemConfigPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SOURCE_CD")
	private String sourceCd;



	@Column(name="TAX_YEAR")
	private String taxYear;

	public SourceSystemConfigPK() {
	}

	public String getSourceCd() {
		return this.sourceCd;
	}
	public void setSourceCd(String sourceCd) {
		this.sourceCd = sourceCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SourceSystemConfigPK)) {
			return false;
		}
		SourceSystemConfigPK castOther = (SourceSystemConfigPK)other;
		return 
			this.sourceCd.equals(castOther.sourceCd);
	}

	public String getTaxYear() {
		return taxYear;
	}

	public void setTaxYear(String taxYear) {
		this.taxYear = taxYear;
	}


	public int hashCode() {
		final int prime = 31;
		int hash = 17;

		hash = hash * prime + this.sourceCd.hashCode();
		hash = hash * prime + this.taxYear.hashCode();
		
		return hash;
	}
}