package us.deloitteinnovation.aca.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the print_details database table.
 * 
 */
@Embeddable
public class PrintDetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 2L;

	@Column(name="SOURCE_UNIQUE_ID")
	private long sourceUniqueId;

	@Column(name="SOURCE_CD")
	private String sourceCd;



	@Column(name="TAX_YEAR")
	private String taxYear;



	@Column(name="PRINT_FILE_NAME")
	private String printFileName;

	public PrintDetailPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PrintDetailPK)) {
			return false;
		}
		PrintDetailPK castOther = (PrintDetailPK)other;
		return 
			(this.sourceUniqueId == castOther.sourceUniqueId)
			&& this.sourceCd.equals(castOther.sourceCd);
	}

	public String getTaxYear() {
		return taxYear;
	}

	public void setTaxYear(String taxYear) {
		this.taxYear = taxYear;
	}
	public String getPrintFileName() {
		return printFileName;
	}

	public void setPrintFileName(String printFileName) {
		this.printFileName = printFileName;
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + (int) this.sourceUniqueId;
		hash = hash * prime + this.sourceCd.hashCode();
		
		return hash;
	}
}