package us.deloitteinnovation.aca.batch.dto;


import us.gov.treasury.irs.ext.aca.air._7.TransmitterErrorDetailGrp;

/**
 * Created by tthakore on 4/1/2016.
 */
public class TransmitterErrorDetailGrpDTO {

    private long recepientUID;
    private String sourceCD;
    private String recordStatus = "";
    private Integer submissionID;
    private Integer recordID;
    private TransmitterErrorDetailGrp transmitterErrorDetailGrp;



    public long getRecepientUID() {
        return recepientUID;
    }

    public void setRecepientUID(long recepientUID) {
        this.recepientUID = recepientUID;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String submissionStatus) {
        this.recordStatus = submissionStatus;
    }

    public TransmitterErrorDetailGrp getTransmitterErrorDetailGrp() {
        return transmitterErrorDetailGrp;
    }

    public void setTransmitterErrorDetailGrp(TransmitterErrorDetailGrp transmitterErrorDetailGrp) {
        this.transmitterErrorDetailGrp = transmitterErrorDetailGrp;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    public Integer getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(Integer submissionID) {
        this.submissionID = submissionID;
    }

    public String getSourceCD() {
        return sourceCD;
    }

    public void setSourceCD(String sourceCD) {
        this.sourceCD = sourceCD;
    }



}
