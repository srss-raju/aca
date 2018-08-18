package us.deloitteinnovation.aca.batch.constants;

/**
 * Constants file to be used for the file ingestion process.
 */
public interface FileIngestionConstants {

    String IMPORT_FILERS_JOB_NAME = "importFilers";
    String BATCH_ARG_STATE = "STATE";
    String BATCH_ARG_TAX_YEAR = "YEAR";
    Integer LINES_TO_SKIP = 1;
    String RECORD_SOURCE_FILE = "FILE";
    String STATUS_ACTIVE = "ACTIVE";
    String STATUS_INACTIVE = "INACTIVE";
    String CORRECTION_CODE_ORIGINAL = "O";
    String CORRECTION_CODE_UPDATE = "U";
    String CORRECTION_CODE_CORRECTION = "C";
    String COVERAGE_DATE_ALL_NINES = "99999999";
    String COVERAGE_DATE_ALL_SIXES = "66666666";
    String BATCH_TYPE_FILE_INGESTION = "File Ingestion";
    int NUM_FILES_TO_PROCESS = 1;
    int THROTTLE_LIMIT = 4;
    String FILER_STATUS_RESPONSIBLE_PERSON = "R";
    String FILER_STATUS_COVERED_PERSON = "C";


    interface FlowConstants {
        String REFRESH_STAGING_JOB_FLOW = "refreshStagingJobFlow";
        String VERIFY_AND_VALIDATE_JOB_FLOW = "verifyAndValidateJobFlow";
        String FLOW_STATUS_PASSED = "PASSED";
        String FLOW_STATUS_FAILED = "FAILED";
    }

    interface JobConstants {
        String JOB_STATUS_COMPLETED = "COMPLETED";
        String JOB_STATUS_FAILED = "FAILED";
    }

    interface StepConstants {
        String REFRESH_STAGING_TASKLET_STEP = "step1RefreshStagingTasklet";
        String VERIFY_FILER_DATA_STEP = "step2VerifyFilerData";
        String VALIDATE_FILER_DATA_STEP = "step3ValidateFilerData";
        String UPDATE_BATCH_COUNTS_STEP = "step4UpdateBatchCounts";
    }
}
