package us.deloitteinnovation.aca.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bhchaganti on 1/19/2017.
 */
@Entity
@Table(name="IRS_109495_XML_DETAILS")
public class Irs109495XMLDetailsEntity {

    @Id
    @Column(name="TRANSMISSION_ID")
    private Integer transmissionId;

    @Column(name="XML_FILE_PATH")
    private String xmlFilePath;

    @Column(name="FORM_1094B_COUNT")
    private Integer form1094BCount;

    @Column(name="FORM_1095B_COUNT")
    private Integer form1095BCount;

    @Column(name="MANIFEST_CREATED")
    private Boolean manifestCreated;

    @Column(name="TRANSMISSION_TYPE_CD")
    private String transmissionTypeCd;

    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;



    public Integer getTransmissionId() {
        return transmissionId;
    }

    public void setTransmissionId(Integer transmissionId) {
        this.transmissionId = transmissionId;
    }

    public String getXmlFilePath() {
        return xmlFilePath;
    }

    public void setXmlFilePath(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public Integer getForm1094BCount() {
        return form1094BCount;
    }

    public void setForm1094BCount(Integer form1094BCount) {
        this.form1094BCount = form1094BCount;
    }

    public Integer getForm1095BCount() {
        return form1095BCount;
    }

    public void setForm1095BCount(Integer form1095BCount) {
        this.form1095BCount = form1095BCount;
    }

    public Boolean getManifestCreated() {
        return manifestCreated;
    }

    public void setManifestCreated(Boolean manifestCreated) {
        this.manifestCreated = manifestCreated;
    }

    public String getTransmissionTypeCd() {
        return transmissionTypeCd;
    }

    public void setTransmissionTypeCd(String transmissionTypeCd) {
        this.transmissionTypeCd = transmissionTypeCd;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Irs109495XMLDetailsEntity that = (Irs109495XMLDetailsEntity) o;

        if (!transmissionId.equals(that.transmissionId)) return false;
        if (xmlFilePath != null ? !xmlFilePath.equals(that.xmlFilePath) : that.xmlFilePath != null) return false;
        if (form1094BCount != null ? !form1094BCount.equals(that.form1094BCount) : that.form1094BCount != null)
            return false;
        if (form1095BCount != null ? !form1095BCount.equals(that.form1095BCount) : that.form1095BCount != null)
            return false;
        return manifestCreated.equals(that.manifestCreated);
    }

    @Override
    public int hashCode() {
        int result = transmissionId.hashCode();
        result = 31 * result + (xmlFilePath != null ? xmlFilePath.hashCode() : 0);
        result = 31 * result + (form1094BCount != null ? form1094BCount.hashCode() : 0);
        result = 31 * result + (form1095BCount != null ? form1095BCount.hashCode() : 0);
        result = 31 * result + manifestCreated.hashCode();
        return result;
    }
}
