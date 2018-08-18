package us.deloitteinnovation.aca.entity;

import javax.persistence.*;
import java.io.Serializable;



/**
 * The persistent class for the exception_report database table.
 * 
 */
@Entity
@Table(name="exception_report")
public class ExceptionReport implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="ex_repo_seq")
	@SequenceGenerator(name="ex_repo_seq",sequenceName="ex_repo_seq")
	@Column(name="EXCEPTION_REPORT_ID")
	private int exceptionReportId;

	@Column(name="EX_DETAILS")
	private String exDetails;

	@Column(name="ROW_NUMBER")
	private int rowNumber;

	@Column(name="SOURCE_UNIQUE_ID")
	private long sourceUniqueId;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="BATCH_ID")
	private BatchInfo batchInfo;

	public ExceptionReport() {
	}

	public int getExceptionReportId() {
		return this.exceptionReportId;
	}

	public void setExceptionReportId(int exceptionReportId) {
		this.exceptionReportId = exceptionReportId;
	}

	public String getExDetails() {
		return this.exDetails;
	}

	public void setExDetails(String exDetails) {
		this.exDetails = exDetails;
	}

	public int getRowNumber() {
		return this.rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public long getSourceUniqueId() {
		return this.sourceUniqueId;
	}

	public void setSourceUniqueId(long sourceUniqueId) {
		this.sourceUniqueId = sourceUniqueId;
	}

	public BatchInfo getBatchInfo() {
		return batchInfo;
	}

	public void setBatchInfo(BatchInfo batchInfo) {
		this.batchInfo = batchInfo;
	}

}