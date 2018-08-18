package us.deloitteinnovation.aca.entity;


import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the filer_demographics database table.
 *..
 */
@Entity
@BatchSize(size=200)
@Table(name="filer_demographics")
public class FilerDemographicIrsStatusUpdate implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private FilerDemographicPK id;

	public FilerDemographicPK getId() {
		return this.id;
	}

	public void setId(FilerDemographicPK filerDemographicPK) {
		 this.id = filerDemographicPK;
	}


	@Column(name="IRS_TRANSMISSION_STATUS_CD")
	private String irsTransmissionCode;


	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDt;



	public String getIrsTransmissionCode() {
		return irsTransmissionCode;
	}

	public void setIrsTransmissionCode(String irsTransmissionCode) {
		this.irsTransmissionCode = irsTransmissionCode;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}
}