package us.deloitteinnovation.aca.entity;


import us.deloitteinnovation.aca.constants.CommonEntityConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the filer_coverage database table.
 * 
 */
@Entity
@Table(name="filer_coverage_source")
public class FilerCoverage implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FilerCoveragePK id;
	private Character jan;
	private Character feb;
	private Character mar;
	private Character apr;
	private Character may;
	private Character jun;
	private Character jul;
	private Character aug;
	private Character sep;
	private Character oct;
	private Character nov;
	private Character dec;
	private String comments;


	@Column(name= CommonEntityConstants.TAX_YEAR)
	private int taxYear;

	@Temporal(TemporalType.DATE)
	@Column(name= CommonEntityConstants.POLICY_COVERAGE_BEGIN_DT)
	private Date origCoverageBeginDt;

	@Temporal(TemporalType.DATE)
	@Column(name= CommonEntityConstants.ORIG_COVERAGE_END_DT)
	private Date origCoverageEndDt;


	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDt;

	@Column(name="CASE_APPLICATION_ID")
	private String caseApplicationId;


	@Column(name="FILER_DEMO_SEQ")
	private Long filerDemoSeq;


	@Column(name=CommonEntityConstants.POLICY_PROGRAM_NAME)
	private String programName;

	public FilerCoverage() {
	}

	public FilerCoveragePK getId() {
		return id;
	}

	public void setId(FilerCoveragePK id) {
		this.id = id;
	}

	public Character getApr() {
		return this.apr;
	}

	public void setApr(Character apr) {
		this.apr = apr;
	}

	public Character getAug() {
		return this.aug;
	}

	public void setAug(Character aug) {
		this.aug = aug;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Character getDec() {
		return this.dec;
	}

	public void setDec(Character dece) {
		this.dec = dece;
	}

	public Character getFeb() {
		return this.feb;
	}

	public void setFeb(Character feb) {
		this.feb = feb;
	}

	public Character getJan() {
		return this.jan;
	}

	public void setJan(Character jan) {
		this.jan = jan;
	}

	public Character getJul() {
		return this.jul;
	}

	public void setJul(Character jul) {
		this.jul = jul;
	}

	public Character getJun() {
		return this.jun;
	}

	public void setJun(Character jun) {
		this.jun = jun;
	}

	public Character getMar() {
		return this.mar;
	}

	public void setMar(Character mar) {
		this.mar = mar;
	}

	public Character getMay() {
		return this.may;
	}

	public void setMay(Character may) {
		this.may = may;
	}

	public Character getNov() {
		return this.nov;
	}

	public void setNov(Character nov) {
		this.nov = nov;
	}

	public Character getOct() {
		return this.oct;
	}

	public void setOct(Character oct) {
		this.oct = oct;
	}

	public Character getSep() {
		return sep;
	}

	public void setSep(Character sep) {
		this.sep = sep;
	}
	public Date getOrigCoverageBeginDt() {
		return this.origCoverageBeginDt;
	}

	public void setOrigCoverageBeginDt(Date origCoverageBeginDt) {
		this.origCoverageBeginDt = origCoverageBeginDt;
	}

	public Date getOrigCoverageEndDt() {
		return this.origCoverageEndDt;
	}

	public void setOrigCoverageEndDt(Date origCoverageEndDt) {
		this.origCoverageEndDt = origCoverageEndDt;
	}

	public Long getFilerDemoSeq() {
		return filerDemoSeq;
	}

	public void setFilerDemoSeq(Long filerDemoSeq) {
		this.filerDemoSeq = filerDemoSeq;
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


	public String getCaseApplicationId() {
		return caseApplicationId;
	}

	public void setCaseApplicationId(String caseApplicationId) {
		this.caseApplicationId = caseApplicationId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public int getTaxYear() {
		return taxYear;
	}

	public void setTaxYear(int taxYear) {
		this.taxYear = taxYear;
	}
}