package us.deloitteinnovation.aca.exception;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.constants.FileIngestionConstants;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.service.PrintVendorExceptionReportService;
import us.deloitteinnovation.aca.batch.service.PrintVendorFileImportMailerService;
import us.deloitteinnovation.aca.batch.utils.BatchUtils;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;
import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.deloitteinnovation.profile.ProfileProperties;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Job execution decider which decides whether to go to the filer data verification step or flow
 * </p>
 * <p>
 * This decider is used while configuring the spring batch job to import filers.
 * </p>
 * <p>
 * Spring batch job goes to the verify step only if the FlowExecutionStatus returned by this decider is "PASSED"
 * </p>
 * <p>
 * Spring batch job stops execution and exits gracefully if the FlowExecutionStatus returned by this decider is "FAILED"
 * </p>
 */

public class PrintVendorFileNameVerificationDecider implements JobExecutionDecider {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintVendorFileNameVerificationDecider.class);
    String year;
    String state;
    boolean goAhead;
    List<String> validationErrorsList;
    String FLOW_EXECUTION_STATUS = FileIngestionConstants.FlowConstants.FLOW_STATUS_PASSED;


    @Autowired
    Validator validator;
    @Autowired
    BatchInfoDto batchInfoDto;
    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService;
    @Autowired
    PrintVendorFileImportMailerService fileImportMailerService;
    @Autowired
    PrintVendorExceptionReportService exceptionReportService;
    private String agencyCodeFromDB;
    private String systemCodeFromDB;
    @Autowired
    private Environment env;
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private ProfileProperties profileProperties;


    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

        goAhead = true;
        validationErrorsList = new ArrayList<>();
        state = jobExecution.getJobParameters().getString(FileIngestionConstants.BATCH_ARG_STATE);
        year = jobExecution.getJobParameters().getString(FileIngestionConstants.BATCH_ARG_TAX_YEAR);

        Assert.notNull(state, "Null value provided for parameter '" + FileIngestionConstants.BATCH_ARG_STATE + "'");
        Assert.hasLength(state, "Blank value provided for parameter '" + FileIngestionConstants.BATCH_ARG_STATE + "'");
        Assert.notNull(year, "Null value provided for parameter '" + FileIngestionConstants.BATCH_ARG_TAX_YEAR + "'");
        Assert.hasLength(year, "Blank value provided for parameter '" + FileIngestionConstants.BATCH_ARG_TAX_YEAR + "'");

        try { // Check to see if we currently support the US state for which this job is being run.
            SourceSystemConfig config = sourceSystemConfigDataService.getByState(state, Integer.valueOf(year));
            agencyCodeFromDB = config.getSourceCd().substring(state.length(), 5);
            systemCodeFromDB = config.getSourceCd().substring((state.length() + agencyCodeFromDB.length()), 8);
        } catch (EmptyResultDataAccessException e) {
            validationErrorsList.add(env.getProperty("filename.state.code.invalid"));
            goAhead = false;
        }

        //1. Determine the location of the file based on the input parameters.
        String stateInputFolderKey = state + BatchConstants.INPUT_FILE_FOLDER;
        String stateInputFolder = profileProperties.getProperty(stateInputFolderKey);
        File fileDirectory = new File(stateInputFolder);

        if (goAhead) {
            if (!fileDirectory.exists()) {
                validationErrorsList.add(stateInputFolder + ErrorMessageConstants.FILE_DIRECTORY_REQ);
                goAhead = false;
            }
        }

        //2. Read the file.
        File[] fileListInSourceFolder = null;
        if (goAhead) {
            fileListInSourceFolder = fileDirectory.listFiles(new PrintVendorFileExtensionFilter(BatchConstants.FILE_FORMAT_EXTENSION));
            if (fileListInSourceFolder.length == 0) {
                validationErrorsList.add(env.getProperty("filename.extension.invalid"));
                goAhead = false;
            }
        }

        //3. Populate input file meta data.
        PrintVendorInputFileMetaData inputFileMetaData = null;
        if (goAhead) {
            for (File file : fileListInSourceFolder) {

                if (file.exists()) {
                    // Check if the file is named in the format <StateCd>_<AgencyCd>_<SystemCd>_<MMDDYYYY>_<Ver#>_<TaxYear>.dat
                    String[] fileNameParts = file.getName().split("_");
                    if (fileNameParts.length == 3) {
                        inputFileMetaData = this.parseFileName(file);
                    } else {
                        validationErrorsList.add(env.getProperty("filename.format.invalid"));
                        goAhead = false;
                    }
                } else {
                    validationErrorsList.add(ErrorMessageConstants.FILE_EXISTS);
                    goAhead = false;
                }
                // We shall not process more than 1 file in the state input folder
                break;
            }
        }

        //4. Valid input file meta data.
        if (goAhead) {
            BindingResult bindingResult = BatchUtils.bindAndValidate(inputFileMetaData, validator);
            if (bindingResult.hasErrors()) {
                String errMsg = BatchUtils.buildValidationException(bindingResult);
                validationErrorsList.add(errMsg);
                goAhead = false;
            } else {
                // Check if the state code is valid and if yes
                // check if it has a matching system code and agency code as mentioned in the DB.
                if (!inputFileMetaData.getStateCode().equals(state)) {
                    validationErrorsList.add(env.getProperty("filename.state.code.invalid"));
                    goAhead = false;
                } else if (!inputFileMetaData.getAgencyCode().equals(agencyCodeFromDB)) {
                    validationErrorsList.add(env.getProperty("filename.agency.code.invalid"));
                    goAhead = false;
                } else if (!inputFileMetaData.getSystemCode().equals(systemCodeFromDB)) {
                    validationErrorsList.add(env.getProperty("filename.system.code.invalid"));
                    goAhead = false;
                } else if (!inputFileMetaData.getTaxYear().equals(year)) {
                    validationErrorsList.add(env.getProperty("filename.tax.year.invalid"));
                } else {
                    batchInfoDto.setStateCd(inputFileMetaData.getStateCode());
                    batchInfoDto.setAgencyCd(inputFileMetaData.getAgencyCode());
                    batchInfoDto.setSystemCd(inputFileMetaData.getSystemCode());
                    batchInfoDto.setFileName(inputFileMetaData.getFileName());
                    batchInfoDto.setFileVersion(inputFileMetaData.getVersion());
                    batchInfoDto.setReceiveDt(new Date());
                    batchInfoDto.setSourceCode(batchInfoDto.getStateCd().concat(batchInfoDto.getAgencyCd().concat(batchInfoDto.getSystemCd())));
                }

            }

        }

        if (goAhead && !(inputFileMetaData.getRecordCountInFile().equals(inputFileMetaData.getRecordCountInHeader()))) {

            validationErrorsList.add(env.getProperty("filename.record.count.mismatch"));
        }

        //5. Return FlowExecutionStatus.FAILED on failed validation
        if (validationErrorsList != null & !validationErrorsList.isEmpty()) {

            FLOW_EXECUTION_STATUS = FileIngestionConstants.FlowConstants.FLOW_STATUS_FAILED;
            //6. Log Exception on Failed validation
            this.logExceptions(validationErrorsList, batchInfoDto);
            //7. Send email with validation failure messages.
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Decider signalled Failed..Sending email..");
                LOGGER.info(validationErrorsList.toString());
            }
            fileImportMailerService.sendMail(batchInfoDto, jobExecution);
        }

        //8. Set exit status as COMPLETED
        jobExecution.setExitStatus(ExitStatus.COMPLETED);
        return new FlowExecutionStatus(FLOW_EXECUTION_STATUS);
    }


    private void logExceptions(List<String> validationErrorsList, BatchInfoDto batchInfoDto) {

    	PrintVendorExceptionReportDto exceptionReport = new PrintVendorExceptionReportDto();
        exceptionReport.setBatchInfo(batchInfoDto);
        exceptionReport.setExDetails(validationErrorsList.toString());
        exceptionReportService.saveReport(exceptionReport);

    }

    private PrintVendorInputFileMetaData parseFileName(File file) {

    	PrintVendorInputFileMetaData inputFileMetaData = new PrintVendorInputFileMetaData();
        String completeFileName = file.getName();
        // completeFileName = fileName + fileExtension
        String fileName = completeFileName.substring(0, completeFileName.lastIndexOf("."));
        String fileExtension = completeFileName.substring(completeFileName.lastIndexOf(".") + 1);
        String stateCode = fileName.substring(0, 2);
        String agencyCode = fileName.substring(2, 5);
        String systemCode = fileName.substring(5, 8);
        String date = fileName.substring(8, StringUtils.ordinalIndexOf(completeFileName, "_", 1));
        String version = fileName.substring(StringUtils.ordinalIndexOf(completeFileName, "_", 1) + 1,
                StringUtils.ordinalIndexOf(completeFileName, "_", 2));
        String year = fileName.substring(StringUtils.ordinalIndexOf(completeFileName, "_", 2) + 1);
        int recordCountInHeader = this.getRecordCountFromHeader(file);
        int recordCountInFile = this.getActualRecordCount(file);
        inputFileMetaData
                .setExtension(fileExtension)
                .setFileName(fileName)
                .setStateCode(stateCode)
                .setAgencyCode(agencyCode)
                .setSystemCode(systemCode)
                .setDate(date)
                .setVersion(version)
                .setTaxYear(year)
                .setRecordCountInHeader(recordCountInHeader)
                .setRecordCountInFile(recordCountInFile);

        return inputFileMetaData;
    }

    private int getRecordCountFromHeader(File file) {
        int linenumber;
        try {
            FileReader fr = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(fr);
            String line = StringUtils.trim(lnr.readLine());
            if (NumberUtils.isNumber(line)) {
                linenumber = Integer.parseInt(line);
            } else {
                linenumber = -1;
            }
            lnr.close();

        } catch (IOException e) {
            validationErrorsList.add(e.getMessage());
            goAhead = false;
            linenumber = -1;
        }
        return linenumber;
    }

    private int getActualRecordCount(File file) {
        int linenumber = -1; // Since we have to skip the first line to get to the first record.
        try {
            FileReader fr = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(fr);
            while (lnr.readLine() != null) {
                linenumber++;
            }
            lnr.close();
            if (linenumber == -1) {
                validationErrorsList.add(ErrorMessageConstants.FILE_EMPTY);
                goAhead = false;
            }

        } catch (IOException e) {
            validationErrorsList.add(e.getMessage());
            goAhead = false;
        }
        return linenumber;
    }
}
