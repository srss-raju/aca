package us.deloitteinnovation.aca.batch.ingest.step3;

import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;

import java.util.List;

/**
 * Created by tthakore on 9/13/2016.
 */
public class Step3DataValidationException extends RuntimeException{

    private List<Step3FilerDataDto> parent;
    private String errorMessage="";


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Step3FilerDataDto> getParent() {
        return parent;
    }

    public void setParent(List<Step3FilerDataDto> parent) {
        this.parent = parent;
    }





}
