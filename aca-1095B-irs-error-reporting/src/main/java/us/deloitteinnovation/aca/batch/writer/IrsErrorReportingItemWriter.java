package us.deloitteinnovation.aca.batch.writer;

import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import us.deloitteinnovation.aca.batch.dto.ErrorDetailsDTO;
import us.deloitteinnovation.aca.batch.dto.TransmitterErrorDetailGrpDTO;
import us.deloitteinnovation.aca.batch.objectstore.ErrorReportingObjectStore;
import us.deloitteinnovation.aca.repository.FilerDemographicRepository;
import us.deloitteinnovation.aca.repository.IrsRecordDetails1095BRepository;
import us.deloitteinnovation.aca.repository.IrsSubmissionDetailsRepository;
import us.deloitteinnovation.aca.repository.IrsTransmissionErrorsRepository;
import us.gov.treasury.irs.common.ErrorMessageDetail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by tthakore on 3/30/2016.
 */
public class IrsErrorReportingItemWriter implements ItemWriter<ErrorDetailsDTO> {

    private ErrorReportingObjectStore errorReportingObjectStore = ErrorReportingObjectStore.getInstance();
    private static Logger logger = LoggerFactory.getLogger(IrsErrorReportingItemWriter.class);


    @Autowired
    IrsErrorReportingService irsErrorReportingService;

    @Override
    public void write(final List<? extends ErrorDetailsDTO> items) throws Exception {
        if(logger.isDebugEnabled())
        {
            logger.debug("Start of write..... ");
        }
        if (items.size() > 0 && items.get(0).getTransmissionID() != null) {
            generateReportFile(items.get(0));
            irsErrorReportingService.updateDBStatus(items.get(0));
            moveFileToArchiveFolder();
        } else {
            logger.error("IrsErrorReportingItemWriter :- File will not get processed either file has record which IRS_transmission_records table does not have  or there is exception in file or there is no file to process...... ");
        }
        if(logger.isDebugEnabled()) {
            logger.debug("End of write..... ");
        }
    }

    /**
     * @param item generates report file for received error xml.
     **/
    public void generateReportFile(ErrorDetailsDTO item) throws IOException {
        if(logger.isDebugEnabled())
        {
            logger.debug("Start of generateReportFile..... ");
        }

        String outputFileFolder = errorReportingObjectStore.getOutPutFolderPath();
        String fileName = errorReportingObjectStore.getReceiptIdFromFileName(errorReportingObjectStore.getCurrentFileName()) + "_" + getDateString() + "_report.dat";
        String transmissionID = (item.getTransmissionID());
        File file = new File(outputFileFolder);
        File exceptionReportFile = new File(file, fileName);
        FileWriter fw = new FileWriter(exceptionReportFile.getAbsolutePath());
        fw.write("Error File Name :-" + errorReportingObjectStore.getCurrentFileName() + "\n");
        fw.write("Original File Name :-" + errorReportingObjectStore.receiptTransmissionIDMap.get(item.getReceiptID()).getTransmissionFileName() + "\n");
        fw.write("Receipt Id :-" + item.getReceiptID() + "\n");
        fw.write("Transmission Id :-" + transmissionID + "\n");
       if(item.getSubmissionLvlStatusCd() != null)
       {
           fw.write("Submission Level Status :-" + item.getSubmissionLvlStatusCd() + "\n");
       }
        fw.write("Summary :- \n\t Total Errors Received :- " + (errorReportingObjectStore.getRecordErrorCount()+ errorReportingObjectStore.getSubmissionErrorCount())+ "\n");
        fw.write("\t Total Number of Records Errors :- " + errorReportingObjectStore.getRecordErrorCount() + "\n");
        fw.write("\t Total Number of Submission Errors :- " + errorReportingObjectStore.getSubmissionErrorCount() + "\n");
        fw.write("Error Details :- \n");
        //updated below string format to move error message text to last element and added tab to open .dat file into excel.
        String formatStr = "%-15s \t%-8s \t%-15s \t%-15s \t%-15s \t%-75s \t%-25s \t%-25s \t%-300s \t%-1000s%n";
        fw.write(String.format(formatStr, "SubmissionID", "Record ID", "Recipient Id", "Source Code", "Error Message Code", "Error Element","Responsible for Triage","Error Category","Error Message Text","Business Rule"));
        for (TransmitterErrorDetailGrpDTO errorObject : item.getTransmitterErrorDetailGrps()) {
            String submissionID = (errorObject.getTransmitterErrorDetailGrp().getUniqueSubmissionId() != null) ? errorObject.getTransmitterErrorDetailGrp().getUniqueSubmissionId().split("\\|")[1] : errorObject.getTransmitterErrorDetailGrp().getUniqueRecordId().split("\\|")[1];
            String recordID = (errorObject.getTransmitterErrorDetailGrp().getUniqueSubmissionId() != null) ? "" : errorObject.getTransmitterErrorDetailGrp().getUniqueRecordId().split("\\|")[2];
            String recepientID = String.valueOf(errorObject.getRecepientUID());
            String sourceCD = (errorObject.getSourceCD() != null)?errorObject.getSourceCD():"";
            if (errorObject.getTransmitterErrorDetailGrp().getErrorMessageDetails() != null && errorObject.getTransmitterErrorDetailGrp().getErrorMessageDetails().size() > 0) {
                for (ErrorMessageDetail obj : errorObject.getTransmitterErrorDetailGrp().getErrorMessageDetails()) {
                    String eMsgCode = obj.getErrorMessageCd();
                    String eMsgText = obj.getErrorMessageTxt();
                    String xPath = (obj.getXpathContent() != null)?obj.getXpathContent():"";
                    String triageOwner = (errorReportingObjectStore.irsErrorCodeOwnerMapEntity.get(eMsgCode) != null)?errorReportingObjectStore.irsErrorCodeOwnerMapEntity.get(eMsgCode).getErrorOwner():"N/A";
                    String buisinessRule = (errorReportingObjectStore.irsErrorCodeOwnerMapEntity.get(eMsgCode) != null)?errorReportingObjectStore.irsErrorCodeOwnerMapEntity.get(eMsgCode).getBusinessRule():"N/A";
                            logger.info("writing into report file sourceUnique ID {}", recepientID);
                    String errorCategory = (errorReportingObjectStore.irsErrorCodeOwnerMapEntity.get(eMsgCode) != null)?errorReportingObjectStore.irsErrorCodeOwnerMapEntity.get(eMsgCode).getErrorReportCategory():"N/A";

                    fw.write(String.format(formatStr, submissionID, recordID, recepientID, sourceCD, eMsgCode,  xPath,triageOwner,errorCategory,eMsgText,buisinessRule));
                }
            } else if (submissionID != null) {
                //commented  code to remove submission id  from report file which does not have error details grp and error message code.
               // fw.write(String.format(formatStr, submissionID, recordID, recepientID, sourceCD, "", "", ""));
            }
        }
        fw.close();
        if(logger.isDebugEnabled())
        {
            logger.debug("End of generateReportFile..... ");
        }
    }

    public String getDateString() {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMddYYYY");
        return outputDateFormat.format(date);
    }

    public void moveFileToArchiveFolder() {
        File xmlFile = new File(errorReportingObjectStore.getInputFolderPath()+"XML/"+ errorReportingObjectStore.getCurrentFileName());
        if (xmlFile.renameTo(new File(errorReportingObjectStore.getInputFolderPath() + "Processed/" + xmlFile.getName()))) {
            logger.info("File is moved successfully! filename :-" + errorReportingObjectStore.getCurrentFileName());
            xmlFile.delete();
        } else {
            logger.info("Error in moving and deleting file filename :- {}",errorReportingObjectStore.getCurrentFileName());
        }
        errorReportingObjectStore.getProcessedFileNameList().add(errorReportingObjectStore.getCurrentFileName());
    }
}
