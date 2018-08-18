package us.deloitteinnovation.aca.batch.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tthakore on 4/1/2016.
 */
public class ErrorDetailsDTO {

    List<TransmitterErrorDetailGrpDTO> transmitterErrorDetailGrps = new ArrayList<>();
    List<TransmitterErrorDetailGrpDTO> acceptedRecord = new ArrayList<>();
    String transmissionID;
    String receiptID;
    private String submissionLvlStatusCd;


    public String getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    public String getTransmissionID() {
        return transmissionID;
    }

    public void setTransmissionID(String transmissionID) {
        this.transmissionID = transmissionID;
    }

    public List<TransmitterErrorDetailGrpDTO> getTransmitterErrorDetailGrps() {
        return transmitterErrorDetailGrps;
    }

    public void setTransmitterErrorDetailGrps(List<TransmitterErrorDetailGrpDTO> transmitterErrorDetailGrps) {
        this.transmitterErrorDetailGrps = transmitterErrorDetailGrps;
    }


    public List<TransmitterErrorDetailGrpDTO> getAcceptedRecord() {
        return acceptedRecord;
    }

    public void setAcceptedRecord(List<TransmitterErrorDetailGrpDTO> acceptedRecord) {
        this.acceptedRecord = acceptedRecord;
    }

    public String getSubmissionLvlStatusCd() {
        return submissionLvlStatusCd;
    }

    public void setSubmissionLvlStatusCd(String submissionLvlStatusCd) {
        this.submissionLvlStatusCd = submissionLvlStatusCd;
    }
}
