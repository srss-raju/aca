package us.deloitteinnovation.aca.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the batch_info database table.
 */
@Entity
@Table(name = "batch_info")
public class BatchInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_info_seq")
    @SequenceGenerator(name = "batch_info_seq", sequenceName = "batch_info_seq")
    @Column(name = "BATCH_ID")
    private int batchId;

    @Column(name = "AGENCY_CD")
    private String agencyCd;

    @Column(name = "EXCEPTION_REPORT_ID")
    private Integer exceptionReportId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RECEIVE_DATE")
    private Date receiveDt;

    @Column(name = "STATE_CD")
    private String stateCd;

    @Column(name = "SYSTEM_CD")
    private String systemCd;

    @Column(name = "TOTAL_COUNT")
    private Integer totalCount;

    @Column(name = "TOTAL_FAIL")
    private Integer totalFail;

    @Column(name = "TOTAL_PASS")
    private Integer totalPass;

    @OneToMany(mappedBy = "batchInfo")
    private List<FilerDemographic> filerDemographics;

    @Column(name = "FILENAME")
    private String fileName;

    @Column(name = "REQUISITION_ID")
    private String requisitionId;


    @Column(name = "INVOICE_UID")
    private String invoiceUid;

    @Column(name = "BATCH_TYPE")
    private String batchType;

    @Transient
    private String sourceCode;

    public BatchInfo() {
    }

    public int getBatchId() {
        return this.batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getAgencyCd() {
        return this.agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public int getExceptionReportId() {
        return this.exceptionReportId;
    }

    public void setExceptionReportId(int exceptionReportId) {
        this.exceptionReportId = exceptionReportId;
    }

    public Date getReceiveDt() {
        return this.receiveDt;
    }

    public void setReceiveDt(Date receiveDt) {
        this.receiveDt = receiveDt;
    }

    public String getStateCd() {
        return this.stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public String getSystemCd() {
        return this.systemCd;
    }

    public void setSystemCd(String systemCd) {
        this.systemCd = systemCd;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalFail() {
        return this.totalFail;
    }

    public void setTotalFail(int totalFail) {
        this.totalFail = totalFail;
    }

    public int getTotalPass() {
        return this.totalPass;
    }

    public void setTotalPass(int totalPass) {
        this.totalPass = totalPass;
    }

    public int getInvoiceUid() {
        return Integer.parseInt(invoiceUid);
    }

    public void setInvoiceUid(int invoiceUid) {
        this.invoiceUid = Integer.toString(invoiceUid);
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    /**
     * @return the filerDemographics
     */
    public List<FilerDemographic> getFilerDemographics() {
        if (null == filerDemographics) {
            filerDemographics = new ArrayList<>();
        }
        return filerDemographics;
    }

    /**
     * @param filerDemographics the filerDemographics to set
     */
    public void setFilerDemographics(List<FilerDemographic> filerDemographics) {
        this.filerDemographics = filerDemographics;
    }


    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getRequisitionId() {
        return Integer.parseInt(requisitionId);
    }

    public void setRequisitionId(int requisitionId) {
        this.requisitionId = Integer.toString(requisitionId);
    }


}