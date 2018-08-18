package us.deloitteinnovation.aca.web.opsportal.dto;

/**
 * Created by sdalavi on 3/29/2016.
 */
public class StatusDto {
    private String statusCd;
    private String statusDesc;
    private String typeCd;

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }
}
