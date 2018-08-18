package us.deloitteinnovation.aca.web.citizenportal.citizenlogin.dto;

/**
 * Created by tthakore on 9/23/2016.
 */
public class StateInfoDTO {

    private String year;
    private String staticStatus;


    public String getStaticStatus() {
        return staticStatus;
    }

    public void setStaticStatus(String staticStatus) {
        this.staticStatus = staticStatus;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
