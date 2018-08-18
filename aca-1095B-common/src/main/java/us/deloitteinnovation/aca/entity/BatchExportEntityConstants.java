package us.deloitteinnovation.aca.entity;

/**
 */
public interface BatchExportEntityConstants {

    String TRANSMIT_ID        = "TRANSMIT_ID";
    String TRANSMIT_1094_ID   = "TRANSMIT_1094_ID";
    String TRANSMIT_FILE_NAME = "TRANSMIT_FILE_NAME";
    String TRANSFER_DATE      = "TRANSFER_DATE";
    String TRANSMIT_DATE      = "TRANSMIT_DATE";
    String TRANSMIT_STATUS    = "TRANSMIT_STATUS";
    String RECORD_ID          = "RECORD_ID";

    interface FilerXmlStatus {
        String CREATED         = "CREATED";
        String ORIGINAL        = "OR";
        String PROCESSING      = "PROCESSING";
        String ACCEPTED        = "ACCEPTED";
        String REJECTED        = "REJECTED";
        String POTENTIAL_ERROR = "POTENTIAL_ERROR";
        String ERROR           = "ERROR";
        String REPLACE         = "RP";
        String CORRECTION      = "CORRECTION";
        String CORRECTED       = "CO";
    }
}
