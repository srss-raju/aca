package us.deloitteinnovation.aca.batch.ingest.step3.dto;

/**
 * Created by tthakore on 9/21/2016.
 */
public class Step3ValidationMapDto {


    private  String uid;


    private  String validationStatus;


    public String getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
