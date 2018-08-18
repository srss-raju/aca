package us.deloitteinnovation.aca.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bhchaganti on 4/4/2016.
 */
@Entity
@Table(name = "IRS_TRANSMISSION_STATUS")
public class IrsTransmissionStatus {

    @EmbeddedId
    private IrsTransmissionStatusPK id;

    /*@Column(name = "SOURCE_UNIQUE_ID", nullable = false)
    private Long sourceUniqueId;

    @Column(name="SOURCE_CD", nullable=false)
    private String sourceCd;*/

    @Column(name = "STATUS_DESC")
    private String statusDesc;


    public IrsTransmissionStatusPK getId() {
        return id;
    }

    public void setId(IrsTransmissionStatusPK id) {
        this.id = id;
    }



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

}
