package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;

/**
 * Created by ritmukherjee on 1/4/2016.
 */
public class RecordUpdateInfo implements Serializable {


    private static final long serialVersionUID = 82026625545575791L;

    private int updateStatus;
    private boolean error;
    private String message;

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
