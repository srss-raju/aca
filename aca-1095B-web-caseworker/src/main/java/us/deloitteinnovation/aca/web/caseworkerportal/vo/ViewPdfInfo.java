package us.deloitteinnovation.aca.web.caseworkerportal.vo;

import java.io.Serializable;

/**
 * Created by ritmukherjee on 10/28/2015.
 */
public class ViewPdfInfo implements Serializable{


    private static final long serialVersionUID = 3217012538682821796L;
    private long sourceUniqueId;
    private String sourceCd;


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


    @Override
    public String toString() {
        return "ViewPdfInfo{" +
                "sourceUniqueId=" + sourceUniqueId +
                ", sourceCd='" + sourceCd + '\'' +
                '}';
    }
}
