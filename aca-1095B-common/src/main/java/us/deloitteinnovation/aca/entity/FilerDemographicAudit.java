package us.deloitteinnovation.aca.entity;

import us.deloitteinnovation.aca.constants.CommonEntityConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ritmukherjee on 10/30/2015.
 */

@Entity
@Table(name="A_FILER_DEMOGRAPHICS")
@Deprecated
public class FilerDemographicAudit implements Serializable {

    private static final long serialVersionUID = 1406555636819934006L;

    @Id
    @Column(name="A_SEQ_NO")
    private int auditSeqNo;

    @Column(name="SOURCE_UNIQUE_ID")
    private long sourceUniqueId;

    @Column(name="SOURCE_CD")
    private String sourceCd;

    @Column(name=CommonEntityConstants.RECIPIENT_ADDRESS_LINE_1)
    private String recepientAddressLine1;

    @Column(name=CommonEntityConstants.RECIPIENT_ADDRESS_LINE_2)
    private String recepientAddressLine2;

    @Column(name=CommonEntityConstants.RECIPIENT_CITY)
    private String recepientCity;

    @Column(name=CommonEntityConstants.RECIPIENT_STATE_CODE)
    private String recepientState;

    @Column(name=CommonEntityConstants.RECIPIENT_ZIP_4)
    private String recepientZip4;

    @Column(name=CommonEntityConstants.RECIPIENT_ZIP_5)
    private String recepientZip5;

    @Column(name = CommonEntityConstants.COMMENTS)
    private String comments;


    @Column(name="UPDATED_BY")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_DATE")
    private Date updatedDt;




//    @Column(name="MAILED_FORM")
//    private String mailedForm;

/*   May be used at future
--------------------------
 @OneToOne(cascade = CascadeType.ALL  )
    @JoinColumns({
            @JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD",insertable = false,updatable = false),
            @JoinColumn(name="SOURCE_UNIQUE_ID", referencedColumnName="SOURCE_UNIQUE_ID",insertable = false,updatable = false)
    })
    private FormPdf1095BAudit FormPdf1095BAudit;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="SOURCE_CD", referencedColumnName="SOURCE_CD"),
            @JoinColumn(name="SOURCE_UNIQUE_ID", referencedColumnName="SOURCE_UNIQUE_ID")
    })
    private List<FormPdf1095BAudit> formPdf1095BAuditList;*/

    public long getSourceUniqueId() {
        return sourceUniqueId;
    }

    public void setSourceUniqueId(long sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public String getRecepientAddressLine1() {
        return recepientAddressLine1;
    }

    public void setRecepientAddressLine1(String recepientAddressLine1) {
        this.recepientAddressLine1 = recepientAddressLine1;
    }

    public String getRecepientAddressLine2() {
        return recepientAddressLine2;
    }

    public void setRecepientAddressLine2(String recepientAddressLine2) {
        this.recepientAddressLine2 = recepientAddressLine2;
    }

    public String getRecepientCity() {
        return recepientCity;
    }

    public void setRecepientCity(String recepientCity) {
        this.recepientCity = recepientCity;
    }

    public String getRecepientState() {
        return recepientState;
    }

    public void setRecepientState(String recepientState) {
        this.recepientState = recepientState;
    }

    public String getRecepientZip4() {
        return recepientZip4;
    }

    public void setRecepientZip4(String recepientZip4) {
        this.recepientZip4 = recepientZip4;
    }

    public String getRecepientZip5() {
        return recepientZip5;
    }

    public void setRecepientZip5(String recepientZip5) {
        this.recepientZip5 = recepientZip5;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public int getAuditSeqNo() {
        return auditSeqNo;
    }

    public void setAuditSeqNo(int auditSeqNo) {
        this.auditSeqNo = auditSeqNo;
    }



    //    public String getMailedForm() {
//        return mailedForm;
//    }
//
//    public void setMailedForm(String mailedForm) {
//        this.mailedForm = mailedForm;
//    }
}
