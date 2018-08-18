package us.deloitteinnovation.aca.web.opsportal.dto;

/**
 * Created by sdalavi on 4/4/2016.
 */
public class ResponseDto {
    private int statusCode;
    private String statusMessage;
    private int count;
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
