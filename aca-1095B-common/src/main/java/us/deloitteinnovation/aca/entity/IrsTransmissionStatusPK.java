package us.deloitteinnovation.aca.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by bhchaganti on 4/5/2016.
 */

/**
 * The primary key class for the irs_1095_xml database table.
 */
@Embeddable
public class IrsTransmissionStatusPK implements Serializable {

    private static final long serialVersionUID = 1L;




    @Column(name ="TYPE_CD")
    private String typeCD;
    @Column(name = "STATUS_CD")
    private String statusCD;

    public IrsTransmissionStatusPK(){}

    public IrsTransmissionStatusPK(final String suid, final String scd) {

        setTypeCD(suid);
        setStatusCD(scd);
    }


    public String getTypeCD() {
        return typeCD;
    }

    public void setTypeCD(String typeCD) {
        this.typeCD = typeCD;
    }

    public String getStatusCD() {
        return statusCD;
    }

    public void setStatusCD(String statusCD) {
        this.statusCD = statusCD;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        us.deloitteinnovation.aca.entity.IrsTransmissionStatusPK that = (us.deloitteinnovation.aca.entity.IrsTransmissionStatusPK) o;

        if ( typeCD != that.typeCD) return false;
        return statusCD != null ? statusCD.equals(that.statusCD) : that.statusCD == null;

    }
    @Override
    public int hashCode() {
        int result = typeCD != null ? typeCD.hashCode() : 0;
        result = 31 * result + (statusCD != null ? statusCD.hashCode() : 0);
        return result;
    }

}
