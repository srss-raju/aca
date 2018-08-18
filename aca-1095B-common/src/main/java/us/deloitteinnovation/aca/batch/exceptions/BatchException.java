package us.deloitteinnovation.aca.batch.exceptions;

import us.deloitteinnovation.aca.model.Filer;

/**
 * Created by tthakore on 11/23/2015.
 */
public class BatchException extends Exception {

    public Filer getFilerObj() {
        return filerObj;
    }

    public void setFilerObj(Filer filerObj) {
        this.filerObj = filerObj;
    }

    private Filer filerObj;

    public BatchException(String message){
        super(message);
    }
}
