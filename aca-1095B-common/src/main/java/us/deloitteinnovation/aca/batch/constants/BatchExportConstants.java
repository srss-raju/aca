package us.deloitteinnovation.aca.batch.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import us.deloitteinnovation.aca.entity.BatchExportEntityConstants;

/**
 * Constants specific to the batch process for exporting Form 10945B data as SOAP XML for the IRS.
 */
public interface BatchExportConstants {

    static final Logger LOG = LoggerFactory.getLogger(BatchExportConstants.class) ;

	Integer FILECHUNK = 10;

    String EX_SOURCE_UNIQUE_ID_NUMBER = "sourceUniqueId";
    String PARAM_SOURCE_CD            = "sourceCd";
    String PARAM_UPDATED_BY           = "updatedBy";
    String PARAM_UPDATED_DATE         = "updatedDate";
    String PARAM_TRANSMIT_STATUS      = "transmitStatus";
    String PARAM_TRANSMIT_FILE_NAME   = "transmitFileName";

    /**
     * Job names passed to the Spring Batch launcher.  Used to determine functionality in steps.
     */
    interface JobNames {
        String ORIGINALS    = "aca1095ExportOriginals";
        String CORRECTIONS  = "aca1095ExportCorrections";
        String REPLACEMENTS = "aca1095ExportReplacements";
        String ORIGINAL1095S = "acaGenerate1095Originals";
        String ORIGINAL109495S = "acaGenerate109495Originals";
        String CORRECTION1095S = "acaGenerate1095Corrections";
        String CORRECTION109495S = "acaGenerate109495Corrections";
        String REPLACEMENT1095S = "acaGenerate1095Replacements";
        String REPLACEMENT109495S = "acaGenerate109495Replacements";
        String ORIGINAL1095FILERS = "acaGenerate1095OriginalsForPrintVendor";
		String ORIGINAL1095PRINTVENDORPARAMFILERS = "acaGenerate1095OriginalsForPrintVendorParam";
        String GENERATEMANIFESTS = "acaGenerateManifests";
    }

    interface JobPropertiesKeys {
        /**
         * Two letter abbreviation of the US State for which the Job is executing.
         */
        String STATE = "STATE";

        /**
         * EIN of the payee agency.
         */
        String EIN = "EIN";

        /**
         * Tax year as an Integer.
         */
        String YEAR = "YEAR";

        /**
         * IRS receipt id of the original transmission for correction or replacement execution.
         */
        String RECEIPT_ID = "RECEIPT_ID";

        /**
         * If running a test scenario, the test number should be set using TEST_SCENARIO.
         */
        String TEST_SCENARIO = "TEST_SCENARIO";
    }

    interface JobExecutionContextKeys {
        /**
         * Value should be a List of String filenames for which manifests should be created in Step 4.
Bat         */
        String FORM_109495B_FILEDATA = "FORM_109495B_FILEDATA";
    }

    interface StepExecutionContextKeys {

        /**
         * Names of the manifest files written in Step 4.
         */
        String MANIFEST_FILENAMES = "MANIFEST_FILENAMES";

        /**
         * Loop counter for iteration over Form 109495B files in Step 4.  Value will be an Integer.
         */
        String FORM_109495B_FILENAMES_COUNTER = "FORM_109495B_FILENAMES_COUNTER";

        /**
         * Loop counter for Print vendor XML file name.
         */
        String FORM_1095S_RECORD_COUNT = "FORM_1095S_RECORD_COUNT";
        /**
         * File counter for Print vendor XML file name.Value will be in Long
         */
        String PRINT_VENDOR_XML_FILE_NUMBER = "PRINT_VENDOR_XML_FILE_NUMBER";

		String PRINT_VENDOR_XML_FREQUENCY = "FREQUENCY";

		String PRINT_VENDOR_XML_MAILSTATUS = "MAILSTATUS";
		
		String PROCESS_RECEIPT_FILE_TYPE = "FILETYPE";
		
		String INVALID_ADDRESS = "ADDRESS_INVALID";

    }

    /**
     * @param stepExecution
     * @return True if the current job is a run of original export.
     */
    static boolean isJobOriginal(StepExecution stepExecution) {
        return ( JobNames.ORIGINALS.equalsIgnoreCase(stepExecution.getJobExecution().getJobInstance().getJobName()) ||
                JobNames.ORIGINAL109495S.equalsIgnoreCase(stepExecution.getJobExecution().getJobInstance().getJobName()) ||
                JobNames.ORIGINAL1095S.equalsIgnoreCase(stepExecution.getJobExecution().getJobInstance().getJobName()));
    }

    /**
     * @param stepExecution
     * @return True if the current job is a run of corrections.
     */
    static boolean isJobCorrections(StepExecution stepExecution) {
        return ( JobNames.CORRECTIONS.equalsIgnoreCase(stepExecution.getJobExecution().getJobInstance().getJobName()) ||
                JobNames.CORRECTION109495S.equalsIgnoreCase(stepExecution.getJobExecution().getJobInstance().getJobName())||
                JobNames.CORRECTION1095S.equalsIgnoreCase(stepExecution.getJobExecution().getJobInstance().getJobName()));
    }

    /**
     * @param stepExecution
     * @return True if the current job is a run of replacements.
     */
    static boolean isJobReplacement(StepExecution stepExecution) {
        return ( JobNames.REPLACEMENTS.equalsIgnoreCase(stepExecution.getJobExecution().getJobInstance().getJobName()) ||
                JobNames.REPLACEMENT109495S.equalsIgnoreCase(stepExecution.getJobExecution().getJobInstance().getJobName())||
                JobNames.REPLACEMENT1095S.equalsIgnoreCase(stepExecution.getJobExecution().getJobInstance().getJobName()));
    }


    /**
     * @param stepExecution
     * @return O, C, or R depending on if job is an Original, Correction, or Replacement.
     */
    static String getJobTypeLetter(StepExecution stepExecution) {
        String jobLetter = null;
        if (isJobOriginal(stepExecution))
            jobLetter = "O";
        else if (isJobCorrections(stepExecution))
            jobLetter = "C";
        else if (isJobReplacement(stepExecution))
            jobLetter = "R";

        if (jobLetter == null)
            throw new IllegalArgumentException("Job Type abbreviation not available for job type '" + stepExecution.getJobExecution().getJobInstance().getJobName() + "'");
        return jobLetter;
    }


    /**
     * Converts a transmission code to the appropriate IRS XML transmit status.
     * @param transmissionCode
     * @return
     */
    static String getStatusFromTransmissionCode(String transmissionCode) {
        String status = null ;
        switch (transmissionCode) {
            case "O":
                status = BatchExportEntityConstants.FilerXmlStatus.ORIGINAL ;
                break ;
            case "C":
                status = BatchExportEntityConstants.FilerXmlStatus.CORRECTED ;
                break ;
            case "R":
                status = BatchExportEntityConstants.FilerXmlStatus.REPLACE ;
                break ;
            default:
                throw new IllegalArgumentException("Status not available for transmissionCode ' " + transmissionCode + "'");
        }
        return status ;
    }

}


