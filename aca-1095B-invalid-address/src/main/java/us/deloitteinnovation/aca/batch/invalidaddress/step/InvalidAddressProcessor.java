package us.deloitteinnovation.aca.batch.invalidaddress.step;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;

public class InvalidAddressProcessor implements ItemProcessor<File, StepResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidAddressProcessor.class);
    
    private StepExecution stepExecution;

    @Override
    public StepResult process(File file) throws Exception {
    	final StepResult stepResult = new StepResult();
    	LOGGER.info("Processing the files received from the reader ");
        LOGGER.info("Processing the file {} from the location {} ", file.getName(), file.getAbsolutePath());
        String state = (String) stepExecution.getJobExecution().getExecutionContext().get(BatchExportConstants.JobPropertiesKeys.STATE);
        
        isYearMatch(file, stepResult);
        final boolean isValid = validateFile(file, stepResult);
        stepResult.setValid(isValid);
        stepResult.setInvalidAddressFile(file);
        stepResult.setSourceCdFromFileName(file.getName().split("_")[0]);
        stepResult.setVersionFromFileName(file.getName().split("_")[4]);
        stepResult.setTaxYearFromFileName(getTaxYear(file));
        stepResult.setState(state);
    	return stepResult;
    }
    
    private boolean validateFile(File file, StepResult stepResult) {

        if (isEmptyFile(file)) {
        	 LOGGER.error(file.getName() + " is empty");
             logException(stepResult, file.getName() + " is empty");
            return false; 
        } else if (!isYearAndSUIDExistsForAllRecords(file, stepResult)) {
            LOGGER.error(file.getName()+" is missing Recipient Unique ID or Year values.");
            return false;
        }  

        return true;

    }
    
    private String getTaxYear(File file) {
         return file.getName().split("_")[5].substring(0, 4);
    }
    
    
    private void isYearMatch(File file, StepResult stepResult) {
        int failureTaxYearCount = 0;
        long taxYearFromFileName = Long.parseLong(getTaxYear(file));
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                long taxYear = 0 ;

                while ((line = br.readLine()) != null) {
                    if (line.trim().split(",").length != 2) {
                    } else {
                        try {
                            taxYear = Long.parseLong(line.split(",")[1].trim());
                            if(taxYearFromFileName != taxYear){
                                failureTaxYearCount++;
                            }
                        } catch (final Exception e) {
                            LOGGER.debug("Ignoring parsing exception ", e);
                            failureTaxYearCount++;
                        }
                    }
                }

            }

        } catch (final Exception e) {
            LOGGER.debug("Ignore tax year exception", e);
        }
        stepResult.setFailureCount(failureTaxYearCount);
    }
    
    private boolean isYearAndSUIDExistsForAllRecords(File file, StepResult stepResult) {

        boolean isValid = true;
        int failureCount = stepResult.getFailureCount();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = br.readLine()) != null) {
                    if (line.trim().split(",").length != 2) {
                        failureCount++;
                    } else {
                        // ACAB-2363 - without RID but with status scenario
                        try {
                            Long.parseLong(line.split(",")[0].trim());
                        } catch (final Exception e) {
                            LOGGER.debug("Ignoring parsing exception ", e);
                            failureCount++;
                        }
                    }
                }
                stepResult.setFailureCount(failureCount);
            }

        } catch (final Exception e) {
            LOGGER.debug("Ignore status and rid exception", e);
        }
        return isValid;
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
    
    
    private void logException(StepResult stepResult, String message) {

        final PrintVendorExceptionReportDto exceptionReport = new PrintVendorExceptionReportDto();
        exceptionReport.setExDetails(message);
        stepResult.getExceptionReport().add(exceptionReport);

    }
    
    boolean isEmptyFile(File file) {
        // this logic need to be changed if each file must contains one header line
        final int totalRecords = getTotalRecordsInFile(file);
        return totalRecords == 0;
    }
    
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
    
}
