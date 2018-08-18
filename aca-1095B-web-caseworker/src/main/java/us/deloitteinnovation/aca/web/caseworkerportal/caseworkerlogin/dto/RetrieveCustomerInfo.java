package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;

/**
 * Created by ritmukherjee on 12/14/2015.
 */
public class RetrieveCustomerInfo implements Serializable{


    private static final long serialVersionUID = -8916004342130829805L;

    /*Searched customerID:Primary Key*/
    private String customerID;

     /*retrieve Customer's personal info*/
    private String recipientFirstName;
    private String recipientLastName;
    private String recipientDob;

    /*retrieve customer's unique info*/
    private String recipientSsn;
    private String recipientTin;

    private String recepientState;
    private Character filerStatus;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getRecipientFirstName() {
        return recipientFirstName;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }

    public String getRecipientLastName() {
        return recipientLastName;
    }

    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
    }

    public String getRecipientDob() {
        return recipientDob;
    }

    public void setRecipientDob(String recipientDob) {
        this.recipientDob = recipientDob;
    }

    public String getRecipientSsn() {
        return recipientSsn;
    }

    public void setRecipientSsn(String recipientSsn) {
        this.recipientSsn = recipientSsn;
    }

    public String getRecipientTin() {
        return recipientTin;
    }

    public void setRecipientTin(String recipientTin) {
        this.recipientTin = recipientTin;
    }

    public String getRecepientState() {
        return recepientState;
    }

    public void setRecepientState(String recepientState) {
        this.recepientState = recepientState;
    }

    public Character getFilerStatus() {
        return filerStatus;
    }

    public void setFilerStatus(Character filerStatus) {
        this.filerStatus = filerStatus;
    }
}
