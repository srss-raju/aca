package us.deloitteinnovation.aca.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bhchaganti on 4/4/2016.
 */
@Entity
@Table(name = "IRS_1095_XML")
public class Irs1095XML {

    @EmbeddedId
    private Irs1095XMLPK id;

    @Transient
    private Long sourceUniqueId;

    @Transient
    private String sourceCd;

    @Transient
    private Integer taxYear;

    @Column(name = "IRS_TRANSMISSION_STATUS_CD", nullable = false)
    private String irsTransmissionStatusCd;

    @Column(name = "IRS_1095B_XML")
    private String irs1095BXml;


    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;


    @Transient
    private Long filerDemoSeq;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Irs1095XML that = (Irs1095XML) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


    public Irs1095XMLPK getId() {
        return id;
    }

    public void setId(Irs1095XMLPK id) {
        this.id = id;
    }

    public String getIrsTransmissionStatusCd() {
        return irsTransmissionStatusCd;
    }

    public void setIrsTransmissionStatusCd(String irsTransmissionStatusCd) {
        this.irsTransmissionStatusCd = irsTransmissionStatusCd;
    }

    public String getIrs1095BXml() {
        return irs1095BXml;
    }

    public void setIrs1095BXml(String irs1095BXml) {
        this.irs1095BXml = irs1095BXml;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getFilerDemoSeq() {
        return filerDemoSeq;
    }

    public void setFilerDemoSeq(Long filerDemoSeq) {
        this.filerDemoSeq = filerDemoSeq;
    }

    public Long getSourceUniqueId() {
        return sourceUniqueId;
    }

    public void setSourceUniqueId(Long sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public String getSourceCd() {
        return sourceCd;
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
}
