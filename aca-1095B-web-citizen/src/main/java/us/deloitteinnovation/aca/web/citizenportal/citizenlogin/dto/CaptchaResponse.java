package us.deloitteinnovation.aca.web.citizenportal.citizenlogin.dto;

/**
 * Created by tthakore on 12/16/2015.
 */
public class CaptchaResponse {
    private String success;
    private String[] error_codes;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    public String[] getError_codes() {
        return error_codes;
    }

    public void setError_codes(String[] error_codes) {
        this.error_codes = error_codes;
    }




}
