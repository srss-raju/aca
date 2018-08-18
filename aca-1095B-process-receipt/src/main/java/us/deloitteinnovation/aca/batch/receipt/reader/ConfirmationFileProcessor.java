package us.deloitteinnovation.aca.batch.receipt.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.receipt.dto.StepResult;
import us.deloitteinnovation.aca.batch.receipt.repository.PrintDetailsMailRepository;
import us.deloitteinnovation.aca.batch.receipt.util.ProcessReceiptConstants;
import us.deloitteinnovation.aca.batch.receipt.util.ProcessReceiptUtil;
import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;
import us.deloitteinnovation.aca.entity.PrintDetail;
import us.deloitteinnovation.aca.entity.PrintDetailPK;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;

/**
 *
 * @author ThirupathiReddy V
 *
 */
public class ConfirmationFileProcessor implements ItemProcessor<File, StepResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationFileProcessor.class);

    @Autowired
    PrintDetailsMailRepository printDetailsMailRepository;

    private StepExecution stepExecution;
    
    @Override
    public StepResult process(File file) throws Exception {
        final StepResult stepResult = new StepResult();
        
        LOGGER.info("Processing the files received from the reader ");

        LOGGER.info("Processing the file {} from the location {} ", file.getName(), file.getAbsolutePath());

        final boolean isValid = validateFile(file, stepResult);
        stepResult.setValid(isValid);// This flag useful to process in writer
        if (isValid) {
            if (isEmptyFile(file)) {
                LOGGER.info("Found empty file and  logging the exception in exception report");
                final PrintVendorExceptionReportDto exceptionReport = new PrintVendorExceptionReportDto();
                exceptionReport.setExDetails(file.getName() + " contains no records");
                exceptionReport.setRowNumber(0);
                exceptionReport.setSourceUniqueId(0);
                stepResult.getExceptionReport().add(exceptionReport);
            } else {
                preparePrintDetails(file, stepResult);
            }
        }

        stepResult.setProcessedFile(file);
        return stepResult;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    boolean isCOR() {

        final String fileType = (String) stepExecution.getJobExecution().getExecutionContext()
                .get(BatchExportConstants.StepExecutionContextKeys.PROCESS_RECEIPT_FILE_TYPE);

        return "COR".equalsIgnoreCase(fileType);
    }

    String getBaseFileName(File file) {

        final String baseFile = file.getName().substring(file.getName().indexOf('_') + 1);
        LOGGER.info("Base file name {} ", baseFile);
        return baseFile;
    }

    boolean isFileExistsInDB(String xmlFileName, File file, StepResult stepResult, String baseFile) {

        LOGGER.info("Checking DB for the printXML record existance with name {} ", xmlFileName);
        if(printDetailsMailRepository.isPrintXMLExists(xmlFileName)){
			if(isCOR()) {
				if(isCORCOMFileExistsInDB("COR_" + baseFile)) {
					logException(stepResult, file.getName() + " already processed");
					stepResult.setCORCOM(true);
					return false;
				}
				return true;
			} else if(!isCOR()) {
				if(isCORCOMFileExistsInDB("COR_" + baseFile)) {
					if(isCORCOMFileExistsInDB(file.getName())) {
						logException(stepResult, file.getName() + " already processed");
						stepResult.setCORCOM(true);
						return false;
					}
					return true;
				} else {
					logException(stepResult, file.getName() + " corresponding COR file is not yet processed");
					stepResult.setCORCOM(true);
					return false;
				}
			}
        }
        return false;
    }
    
    boolean isCORCOMFileExistsInDB(String baseFile) {
        LOGGER.info("Checking DB for the COR File record existance with name {} ", baseFile);
        return printDetailsMailRepository.isPrintXMLExists(baseFile);
    }

    private boolean validateFile(File file, StepResult stepResult) {
        final String baseFile = getBaseFileName(file);
        final String xmlFileName = baseFile.replace(".csv", ".xml");

        if (!isFileExistsInDB(xmlFileName, file, stepResult,baseFile)) {
        	if(!stepResult.isCORCOM()){
        		LOGGER.error("{} does not have a corresponding print file", file.getName());
                logException(stepResult, file.getName() + " does not have a corresponding print file");
        	}
            return false;
        } else if (isEmptyFile(file)) {
            return true; // file is empty we are treating it as valid file and in the processor we are changing PRINT_STATUS to RESEND FORM_STATUS to null
        } else if (!isCountValid(xmlFileName, stepResult, file)) {
            return false;
        } else if (!isStatusAndRIDExistsForAllRecords(file)) {
            LOGGER.error("{}  is missing RID or status values.", file.getName());
            logException(stepResult, file.getName() + " is missing RID or status values");
            return false;
        } else if (!isRecordCountMatchingWithActualRecords(file)) {
            logException(stepResult, file.getName() + " record count and actual records in the file are not matching");
            return false;
        } else if (!checkStatus(file)) {
            logException(stepResult, file.getName() + " contains invalid status");
            return false;
        }

        return true;

    }

    private boolean checkStatus(File file) {
    	boolean isValid = false;
    	boolean isCOR = isCOR();
    	isValid = isStatusValid(isValid, file,isCOR);
    	return isValid;
	}
    
    private boolean isStatusValid(boolean isValid, File file, boolean isCOR){
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();// skip first line , which contains header information such as total record count

                while ((line = br.readLine()) != null) {
                	String status = line.split(",")[1];
                	if(isCOR){
                		isValid = ProcessReceiptUtil.getCORStatus().contains(status);
                	}else{
                		isValid = ProcessReceiptUtil.getCOMStatus().contains(status);
                	}
                	if(!isValid){
                		break;
                	}
                }

            }catch (final Exception e) {
                LOGGER.debug("Ignore status", e);
            }

    	return isValid;
    }

	boolean isCountValid(String xmlFileName, StepResult stepResult, File file) {
        if (isCOR()) {
            // Record count match verification only for COR files
            LOGGER.info("Record exists with name {} in the database and reading total count ", xmlFileName);
            final int totalRecordCount = printDetailsMailRepository.getTotalRecordsCount(xmlFileName);
            LOGGER.info("Total {} records found for the printXML {} ", totalRecordCount, xmlFileName);

            final int recordCountFromFile = getRecordCountFromCORFile(file);
            LOGGER.info("Total {} records found in the header of the COR file ", recordCountFromFile);
            if (recordCountFromFile == -1) {
                LOGGER.error("{} is missing a value for the record count.", file.getName());
                logException(stepResult, file.getName() + " is missing a value for the record count.");
                return false;
            } else if (totalRecordCount != recordCountFromFile) {
                LOGGER.error("{}  does not contain the exact number of records as the corresponding print file.", file.getName());
                logException(stepResult, file.getName() + " does not contain the exact number of records as the corresponding print file.");
                return false;
            }
        } else {
            final int recordCountFromFile = getRecordCountFromCOMFile(file);
            if (recordCountFromFile == -1) {
                LOGGER.error("{} is missing a value for the record count.", file.getName());
                logException(stepResult, file.getName() + " is missing a value for the record count.");
                return false;
            }
            
            final String requisitionId = getRequisitionIdFromCOMFile(file);
            if (requisitionId != null) {
            	if(requisitionId.trim().length() != 20){
            		LOGGER.error("{}  file must contain Requisition ID length only 20 alpha numeric characters", file.getName());
		            logException(stepResult, file.getName() + " file must contain Requisition ID length only 20 alpha numeric characters");
        			return false;
        		}
            	stepResult.setRequisitionId(requisitionId.trim());
            }else{
            	LOGGER.error("{} is missing a value for the record count.", file.getName());
                logException(stepResult, file.getName() + " is missing a value for the requisitionID.");
                return false;
            }
            
        }
        return true;
    }
	
    private boolean isRecordCountMatchingWithActualRecords(File file) {
        final int recordCount = isCOR() ? getRecordCountFromCORFile(file) : getRecordCountFromCOMFile(file);
        final int totalRecords = getTotalRecordsInFile(file);
        return recordCount == totalRecords - 1;
    }

    int getTotalRecordsInFile(File file) {

        List<String> list = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(file.getAbsolutePath()))) {
            list = br.lines().filter(s -> s.trim().length() > 0).collect(Collectors.toList());
        } catch (final IOException e) {
            LOGGER.error("Error while reading lines from the file ", e);
        }
        LOGGER.info("Total {} record(s) found in the file {}  [including header ]", list.size(), file.getAbsolutePath());
        return list.size();
    }

    private boolean isStatusAndRIDExistsForAllRecords(File file) {

        boolean isValid = true;

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();// skip first line , which contains header information such as total record count

                while ((line = br.readLine()) != null) {
                    if (line.split(",").length != 2) {
                        isValid = false;
                        break;
                    } else {
                        // ACAB-2363 - without RID but with status scenario
                        try {
                            Long.parseLong(line.split(",")[0]);
                        } catch (final Exception e) {
                            LOGGER.debug("Ignoring parsing exception ", e);
                            isValid = false;
                            break;
                        }
                    }
                }

            }

        } catch (final Exception e) {
            LOGGER.debug("Ignore status and rid exception", e);
        }
        return isValid;
    }

    private int getRecordCountFromCORFile(File file) {

        int recordCountInHeader = 0;

        try {

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                recordCountInHeader = Integer.parseInt(br.readLine());
            }

        } catch (final Exception e) {
            recordCountInHeader = -1;// This is to log exception if the record count header missing
            LOGGER.debug("Ignore exception", e);
        }

        return recordCountInHeader;

    }

    private int getRecordCountFromCOMFile(File file) {

        int recordCountInHeader = 0;

        try {

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                recordCountInHeader = Integer.parseInt(br.readLine().split(",")[0]);
            }

        } catch (final Exception e) {
            recordCountInHeader = -1;// This is to log exception if the record count header missing
            LOGGER.debug("Ignore exception", e);
        }

        return recordCountInHeader;

    }
    
    private String getRequisitionIdFromCOMFile(File file) {
        String requisitionId;
        try {

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            	requisitionId = br.readLine().split(",")[1];
            }

        } catch (final Exception e) {
        	requisitionId = null;// This is to log exception if the record count header missing
            LOGGER.debug("Ignore exception", e);
        }
        LOGGER.info("requisitionId ---->> "+requisitionId);
        return requisitionId;

    }

    private void logException(StepResult stepResult, String message) {

        final PrintVendorExceptionReportDto exceptionReport = new PrintVendorExceptionReportDto();
        exceptionReport.setExDetails(message);
        stepResult.getExceptionReport().add(exceptionReport);

    }

    private void preparePrintDetails(File file, StepResult stepResult) throws IOException {

        int totalRecords = 0;
        int failedRecords = 0;
        int successRecords = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();// SKIP Header
            String status = null;

            while ((line = reader.readLine()) != null) {
                totalRecords++;// use this as rowNumber as well
                final String[] rowArray = line.trim().split(",");

                if (rowArray.length == 2) {

                    if ("MAIL PENDING".equalsIgnoreCase(rowArray[1]) || "MAILED".equalsIgnoreCase(rowArray[1])) {
                        successRecords++;
                        if ("MAIL PENDING".equalsIgnoreCase(rowArray[1])){
                        	status = "MAIL_PENDING";
                        }else{
                        	status = "MAILED";
                        }
                        
                    } else {
                        failedRecords++;
                        status = rowArray[1].toUpperCase();
                    }

                    addPrintDetails(stepResult, file, rowArray[0], status);
                } else {
                    failedRecords++;
                    LOGGER.error("Received invalid data in record ");
                }
            }
        }

        stepResult.setTotalRecords(totalRecords);
        stepResult.setFailedRecords(failedRecords);
        stepResult.setSuccessRecords(successRecords);

        LOGGER.info("Total Records {} , failedRecords {} , successRecords {} ", totalRecords, failedRecords, successRecords);

    }

    private void addPrintDetails(StepResult stepResult, File file, String sourceUniqueId, String printStatus) {

        final PrintDetail printDetail = new PrintDetail();
        final PrintDetailPK id = new PrintDetailPK();
        final String baseFile = getBaseFileName(file);
        final String fileName = baseFile.replace(".csv", ".xml");
        id.setPrintFileName(fileName);
        id.setSourceUniqueId(Long.valueOf(sourceUniqueId));
        id.setTaxYear(String.valueOf(getTaxYear(file)));
        id.setSourceCd(getSourceId(getStateCode(file)));

        printDetail.setId(id);
        printDetail.setPrintStatus(printStatus);

        if ("RESEND".equalsIgnoreCase(printStatus)) {
            updateFilerDemographicStatusForResend(stepResult, printDetail);
        }

        stepResult.getPrintDetails().add(printDetail);

    }

    boolean isEmptyFile(File file) {
        // this logic need to be changed if each file must contains one header line
        final int totalRecords = getTotalRecordsInFile(file);
        return totalRecords == 0;
    }

    private static String getSourceId(String state) {
        String sourceCd = null;
        if ("AR".equals(state)) {
            sourceCd = ProcessReceiptConstants.ARDHSDSS;
        } else if ("IN".equals(state)) {
            sourceCd = ProcessReceiptConstants.INFSSICE;
        } else if ("LA".equals(state)) {
            sourceCd = ProcessReceiptConstants.LADHHEES;
        }
        return sourceCd;
    }

    private static int getTaxYear(File inputFile) {
        final Calendar calendar = Calendar.getInstance();
        try {
            final String[] fileNameArray = inputFile.getName().split("_");

            final SimpleDateFormat sdf = new SimpleDateFormat("yy");
            final Date date = sdf.parse(fileNameArray[1].substring(0, 2));
            calendar.setTime(date);

        } catch (final ParseException parseException) {
            LOGGER.error("Error while parsing the date", parseException);
        }
        return calendar.get(Calendar.YEAR);

    }

    private static String getStateCode(File inputFile) {
        final String[] fileNameArray = inputFile.getName().split("_");
        return fileNameArray[1].substring(2, 4);
    }

    private void updateFilerDemographicStatusForResend(StepResult stepResult, PrintDetail printDetail) {
        final FilerDemographic filerDemographic = new FilerDemographic();
        final FilerDemographicPK filerDemographicPK = new FilerDemographicPK();
        filerDemographicPK.setSourceUniqueId(printDetail.getId().getSourceUniqueId());
        filerDemographicPK.setSourceCd(printDetail.getId().getSourceCd());
        filerDemographicPK.setTaxYear(Integer.parseInt(printDetail.getId().getTaxYear()));
        filerDemographic.setId(filerDemographicPK);
        filerDemographic.setFormStatus(null);
        stepResult.getFilerDemographicList().add(filerDemographic);
    }
}
