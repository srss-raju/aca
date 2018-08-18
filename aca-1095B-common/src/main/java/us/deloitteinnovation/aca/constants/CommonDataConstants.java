package us.deloitteinnovation.aca.constants;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by tthakore on 10/28/2015.
 */
public interface CommonDataConstants {

    DateFormat MMDDYYYY = new SimpleDateFormat("MMddyyyy");

    String UPDATED_DATE = "UPDATED_DATE";
    String UPDATED_BY = "UPDATED_BY";
    String SOURCE_CD = "SOURCE_CD";
    String SOURCE_CD_ID = "SOURCE_CD_ID";
    String SUPPORT_VIEW_PDF = "SUPPORT_VIEW_PDF";
    String NOT_FOUND_MESSAGE = "NOT_FOUND_MESSAGE";
    String INCORRECT_ADDRESS_MESSAGE = "INCORRECT_ADDRESS_MESSAGE";
    String RETURN_ADDRESS_LINE1 = "RETURN_ADDRESS_LINE1";
    String RETURN_ADDRESS_LINE2 = "RETURN_ADDRESS_LINE2";
    String RETURN_ADDRESS_CITY = "RETURN_ADDRESS_CITY";
    String RETURN_ADDRESS_STATE = "RETURN_ADDRESS_STATE";
    String RETURN_ADDRESS_ZIP = "RETURN_ADDRESS_ZIP";
    String PRINT_PREFERENCES = "PRINT_PREFERENCES";
    String LANGUAGE_PREFERENCE = "LANGUAGE_PREFERENCE";
    String COVERAGE_SEQ_NO = "COVERAGE_SEQ_NO";
    String COVER_MESSAGE_EN = "COVER_MESSAGE_EN";
    String COVER_MESSAGE_SP = "COVER_MESSAGE_SP";
    String COVER_MESSAGE_VT = "COVER_MESSAGE_VT";
    String LANGUAGE_CD = "LANGUAGE_CD";
    String SOURCE_UNIQUE_ID = "SOURCE_UNIQUE_ID";
    String STATE_ABBREVIATION = "STATE_ABBREVIATION";
    String CASE_APPLICATION_ID = "CASE_APPLICATION_ID";
    String PROGRAM_NAME = "PROGRAM_NAME";

    String STANDARD_DATE_FORMAT="MM/dd/yyyy";
    String STANDARD_DATE_TIME_FORMAT="MM/dd/yyyy hh:mm:ss a";
    String INPUT_DATE_FORMAT="yyyy-MM-dd";

    /*used for ui output json string*/
    String _JAN = "Jan";
    String _FEB = "Feb";
    String _MAR = "Mar";
    String _APR = "Apr";
    String _MAY = "May";
    String _JUN = "Jun";
    String _JUL = "Jul";
    String _AUG = "Aug";
    String _SEP = "Sep";
    String _OCT = "Oct";
    String _NOV = "Nov";
    String _DEC = "Dec";
    /*used for DATABASE mapping*/
    String JAN = "JAN";
    String FEB = "FEB";
    String MAR = "MAR";
    String APR = "APR";
    String MAY = "MAY";
    String JUN = "JUN";
    String JUL = "JUL";
    String AUG = "AUG";
    String SEP = "SEP";
    String OCT = "OCT";
    String NOV = "NOV";
    String DEC = "DEC";
    String COMMENTS = "COMMENTS";
    String TRANSMITTER_CONTROL_CODE = "TRANSMITTER_CONTROL_CODE" ;
    String RECIPIENT_FIRST_NAME = "RECIPIENT_FIRST_NAME";
    String RECIPIENT_MIDDLE_NAME = "RECIPIENT_MIDDLE_NAME";
    String RECIPIENT_LAST_NAME = "RECIPIENT_LAST_NAME";
    String RECIPIENT_SSN = "RECIPIENT_SSN";
    String RECIPIENT_TIN = "RECIPIENT_TIN";
    String RECIPIENT_DOB = "RECIPIENT_DOB";
    String RECIPIENT_ADDRESS_LINE_1 = "RECIPIENT_ADDRESS_LINE_1";
    String RECIPIENT_ADDRESS_LINE_2 = "RECIPIENT_ADDRESS_LINE_2";
    String RECIPIENT_CITY = "RECIPIENT_CITY";
    String RECIPIENT_STATE = "RECIPIENT_STATE";
    String RECIPIENT_ZIP_5 = "RECIPIENT_ZIP_5";
    String PROVIDER_NAME = "PROVIDER_NAME";
    String PROVIDER_IDENTIFICATION_NUMBER = "PROVIDER_IDENTIFICATION_NUMBER";
    String PROVIDER_CONTACT_FIRST_NAME = "PROVIDER_CONTACT_FIRST_NAME";
    String PROVIDER_CONTACT_LAST_NAME = "PROVIDER_CONTACT_LAST_NAME";
    String PROVIDER_CONTACT_NO = "PROVIDER_CONTACT_NO";
    String PROVIDER_ADDRESS_LINE_1 = "PROVIDER_ADDRESS_LINE_1";
    String PROVIDER_ADDRESS_LINE_2 = "PROVIDER_ADDRESS_LINE_2";
    String PROVIDER_CITY_OR_TOWN = "PROVIDER_CITY_OR_TOWN";
    String PROVIDER_STATE_OR_PROVINCE = "PROVIDER_STATE_OR_PROVINCE";
    String PROVIDER_ZIP_OR_POSTAL_CODE = "PROVIDER_ZIP_OR_POSTAL_CODE";
    String PROVIDER_COUNTRY = "PROVIDER_COUNTRY";
    String DELOITTE_TEST_TCC = "DELOITTE_TEST_TCC" ;
    String SOFTWARE_ID = "SOFTWARE_ID" ;
    String TEST_TCC = "TEST_TCC" ;
    String[] COVERAGE_MONTHS = {JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC};
    String CUSTOMER_UID_TYPE_SSN = "SSN";
    String CUSTOMER_UID_TYPE_TIN = "TIN";
    String CUSTOMER_UID_TYPE_OTHER = "OTHER";
    String EMPTY_VALUE = "";
    String UNDERSCORE_VALUE = "_";
    String OBLIQUE_VALUE = "/";
    char COVERED = '1';
    char NOT_COVERED = '0';
    String FORMAT_PLACEHOLDER_STR = "%s";
    String INVALID_SEARCH_CRITERIA = "Invalid Search Criteria";
    String INSUFFICIENT_SEARCH_CRITERIA = "Insufficient Criteria";
    String UPDATE_SUCCESS = " Records updated successfully";
    String UPDATE_FAILURE = " Records not updated ";
    /*user roles*/
    String CASEWORKER_RW = "1095B_Caseworker ReadWrite";
    int STATE_CODE_START_INDEX = 0;
    int STATE_CODE_END_INDEX = 2;
    String UNDER_SCORE = "_";
    String CAPTCHA_KEY = "secret=6Lf5GRITAAAAAFIyDx_tEIcT6FbRKtAyZBklUe5i&response=";
    String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";
    String DEK = "Z2hmamRrNTY0NzM4";
    String PBE_WITH_MD5_AND_TRIPLE_DES = "PBEWithMD5AndTripleDES";
    /**
     * Adding correction code to reffer in code
     **/
    String CORRECTION_CODE_O = "O";
    String CORRECTION_CODE_U = "U";
    String CORRECTION_CODE_C = "C";

    String RECORD_VALIDATION_TYPE_DB = "DB";
    String RECORD_VALIDATION_TYPE_FILE = "FILE";
    /**
     * Constants for validation rule passed or failed
     **/
    String VALIDATION_RULE_PASSED = "PASSED";
    String VALIDATION_RULE_FAILED = "FAILED";
    String RECORD_STATUS_ACTIVE = "ACTIVE";
    String RECORD_STATUS_INACTIVE = "INACTIVE";

    //default inactive date and coverage clean date
    String RECORD_INACTIVE_DATE = "6666-12-31";
    String COVERAGE_CLEAR_DATE = "9999-12-31";

    int MAGIC_NUMBER_ZERO = 0;
    String TAX_YEAR = "TAX_YEAR";


    /** FILER_DEMOGRAPHICS.FORM_STATUS constants */
    interface FormStatus {
        /** After iniitial data import, PDF form status will not have been generated. */
        String FORM_STATUS_NOT_GENERATED = "NOT GENERATED";
        /** PDF has been generated by the engine and stored within the binary data field on FILER_DEMOGRAPHICS. */
        String FORM_STATUS_GENERATED = "GENERATED";


        /** Filer information has been modified in some way (probably by a case worker), and needs a REGENERATE. */
        String FORM_STATUS_REGENERATE = "REGENERATE";



        /** An error occurred during PDF generation, and was unable to continue. */
        String GENERATION_FAILED = "GENERATION_FAILED";
    }
    /** PRINT_DETAILS.PRINT_STATUS constants. */
    interface PrintStatus {
        /** Name of the column within PRINT_DETAILS.  Value will be NULL on initial import. */
        String PRINT_STATUS = "PRINT_STATUS" ;
        /** After QA has validated initial import, status set to READY_TO_MAIL. */
        String READY_TO_MAIL = "READY_TO_MAIL" ;
        /** State after READY_TO_MAIL and file has been sent to the print vendor. */
        String MAIL_PENDING = "MAIL_PENDING" ;
        /** State after MAIL_PENDING when Print Vendor Completes Mailing to the Recipient */
        String MAIL_COMPLETE = "MAIL_COMPLETE" ;
    }

    /** Filer_Status constants */
    interface FilerStatus {
        char FILER_STATUS_R = 'R';
        /** Covered. Non head of household. */
        char FILER_STATUS_C = 'C';

        /** Not covered. */
        char FILER_STATUS_N = 'N';
    }
    interface PdfReportTypes {
        String DAILY="daily";
        String INITIAL="initial";

        /** Actual File suffix used on the PDF output files.  I'm concerned about collitions with lowercase constants,
         * so I'm explicitly defining the filename suffixes here.*/
        interface FileSuffix {
            String DAILY="Daily";
            String INITIAL="Initial";
        }
    }
    //Spring Profiles
    public static final String SPRING_PROFILE_NO_SWAGGER = "no-swagger";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_QA = "qa";
    public static final String SPRING_PROFILE_PREPROD = "preprod";
    public static final String SPRING_PROFILE_DEV = "dev";
    public static final String SPRING_PROFILE_LOCAL = "local";
    public static final String SPRING_PROFILE_TEST = "test";
    public static final String NOT = "!";
    String SEND_STRING_PARAMETERS_AS_UNICODE = "sendStringParametersAsUnicode";

}
