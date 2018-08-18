package us.deloitteinnovation.aca.entity;

import us.deloitteinnovation.aca.constants.CommonDataConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the print_details database table.
 * 
 */
@Entity
@Table(name="print_details")
public class PrintDetail implements Serializable, Comparable<PrintDetail> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PrintDetailPK id;

	@Column(name="BATCH_ID")
	private Integer batchId;



	@Column(name="PRINT_REASON")
	private String printReason;

	@Column(name= CommonDataConstants.PrintStatus.PRINT_STATUS)
	private String printStatus;
	
	@Column(name="ORIGINAL_FORM_STATUS")
	private String originalFormStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_MAIL_REQUESTED_DATE")
	private Date lastMailRequestedDate;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDt;

	@Column(name="CORRECTION_INDICATOR")
	private Integer correctionIndicator;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MAILED_DATE")
    private Date mailedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ACKNOWLEDGE_DATE")
    private Date acknowledgeDate;

	public PrintDetail() {
	}


	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}





	public String getPrintReason() {
		return this.printReason;
	}

	public void setPrintReason(String printReason) {
		this.printReason = printReason;
	}

	public String getPrintStatus() {
		return this.printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDt() {
		return this.updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}


	public PrintDetailPK getId() {
		return id;
	}

	public void setId(PrintDetailPK id) {
		this.id = id;
	}

	public Date getLastMailRequestedDate() {
		return lastMailRequestedDate;
	}

	public void setLastMailRequestedDate(Date lastMailRequestedDate) {
		this.lastMailRequestedDate = lastMailRequestedDate;
	}


	public String getOriginalFormStatus() {
		return originalFormStatus;
	}


	public void setOriginalFormStatus(String originalFormStatus) {
		this.originalFormStatus = originalFormStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public int getCorrectionIndicator() {
		return correctionIndicator;
	}


	public void setCorrectionIndicator(int correctionIndicator) {
		this.correctionIndicator = correctionIndicator;
	}

    public Date getMailedDate() {
        return mailedDate;
    }

    public void setMailedDate(Date mailedDate) {
        this.mailedDate = mailedDate;
    }

    public Date getAcknowledgeDate() {
        return acknowledgeDate;
    }

    public void setAcknowledgeDate(Date acknowledgeDate) {
        this.acknowledgeDate = acknowledgeDate;
    }

    @Override
    public int compareTo(PrintDetail o) {
        return this.getCreatedDate().compareTo(o.getCreatedDate());
    }

}