package us.deloitteinnovation.aca.web.citizenportal.citizenlogin.dto;

import us.deloitteinnovation.aca.entity.FilerDemographicCP;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tthakore on 9/28/2015.
 * this class will provide the informatin of the pdf requested
 */
public class ReceiveFormInfo implements Serializable {

    private static final long serialVersionUID = 752647115562271001L;

    private String lastrequestDate = "";

    private String message = "";

    // cache for prnt info
    private String printStatus;

    private Date printDate;





    public String getLastrequestDate() {
        return lastrequestDate;
    }

    public void setLastrequestDate(String lastrequestDate) {
        this.lastrequestDate = lastrequestDate;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }







}
