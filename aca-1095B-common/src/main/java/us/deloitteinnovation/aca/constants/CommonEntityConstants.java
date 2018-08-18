package us.deloitteinnovation.aca.constants;

/**
 * Created by rgopalani on 9/29/2015.
 */
public interface CommonEntityConstants {

    String SOURCE_CD="SOURCE_CD";
    String RECIPIENT_UNIQUE_ID="SOURCE_UNIQUE_ID";
    String FILER_DEMO_SEQ = "FILER_DEMO_SEQ" ;
    String TAX_YEAR="TAX_YEAR";
    String CORRECTION="CORRECTION_CODE";
    String COMMENTS="COMMENTS";
    String RECIPIENT_FIRST_NAME="RECIPIENT_FIRST_NAME";
    String RECIPIENT_MIDDLE_NAME="RECIPIENT_MIDDLE_NAME";
    String RECIPIENT_LAST_NAME="RECIPIENT_LAST_NAME";
    String RECIPIENT_NAME_SUFFIX="RECIPIENT_SUFFIX_NAME";
    String RECIPIENT_ADDRESS_LINE_1="RECIPIENT_ADDRESS_LINE_1";
    String RECIPIENT_ADDRESS_LINE_2="RECIPIENT_ADDRESS_LINE_2";
    String RECIPIENT_STATE_CODE="RECIPIENT_STATE";
    String RECIPIENT_CITY="RECIPIENT_CITY";
    String SEQUENCE_NO="SEQUENCE_NO";
    String RECIPIENT_ZIP_5="RECIPIENT_ZIP_5";
    String RECIPIENT_ZIP_4="RECIPIENT_ZIP_4";
    String POLICY_ORIGIN="POLICY_ORIGIN";
    String POLICY_PROGRAM_NAME="PROGRAM_NAME";
    String POLICY_COVERAGE_BEGIN_DT="ORIG_COVERAGE_BEGIN_DATE";
    String ORIG_COVERAGE_BEGIN_DT="ORIG_COVERAGE_BEGIN_DATE";
    String ORIG_COVERAGE_END_DT ="ORIG_COVERAGE_END_DATE";
    String POLICY_SHOP_IDENTIFIER="SHOP_IDENTIFIER";
    String EMPLOYER_NAME="EMPLOYER_NAME";
    String EMPLOYER_IDENTIFICATION_NUMBER="EMPLOYER_IDENTIFICATION_NUMBER";
    String EMPLOYER_ADDRESS_LINE_1="EMPLOYER_ADDRESS_LINE_1";
    String EMPLOYER_ADDRESS_LINE_2="EMPLOYER_ADDRESS_LINE_2";
    String EMPLOYER_CITY_OR_TOWN="EMPLOYER_CITY_OR_TOWN";
    String EMPLOYER_STATE_OR_PROVINCE="EMPLOYER_STATE_OR_PROVINCE";
    String EMPLOYER_COUNTRY="EMPLOYER_COUNTRY";
    String EMPLOYER_ZIP_OR_FOREIGN_POSTAL_CODE="EMPLOYER_ZIP_OR_POSTAL_CODE";
    String PROVIDER_NAME="PROVIDER_NAME";
    String PROVIDER_IDENTIFICATION_NUMBER="PROVIDER_IDENTIFICATION_NUMBER";
    String PROVIDER_CONTACT_NUMBER="PROVIDER_CONTACT_NUMBER";
    String PROVIDER_ADDRESS_LINE_1="PROVIDER_ADDRESS_LINE_1";
    String PROVIDER_ADDRESS_LINE_2="PROVIDER_ADDRESS_LINE_2";
    String PROVIDER_CITY_OR_TOWN="PROVIDER_CITY_OR_TOWN";
    String PROVIDER_STATE_OR_PROVINCE="PROVIDER_STATE_OR_PROVINCE";
    String PROVIDER_COUNTRY="PROVIDER_COUNTRY";
    String PROVIDER_ZIP_OR_FOREIGN_POSTAL_CODE="PROVIDER_ZIP_OR_POSTAL_CODE";
    String FILLER_STATUS="FILER_STATUS";
    String STATUS="STATUS";
    String STATUS_ACTIVE="ACTIVE";
    String STATUS_INACTIVE="INACTIVE";
    String INSERT_INTO_BUSINESS_RULE_LOG = "INSERT_INTO_BUSINESS_RULE_LOG";
    String RESPONSIBLE_PERSON_UNIQUE="RESPONSIBLE_PERSON_UNIQUE_ID";
    String COMM_PREFERENCE= "COMMUNICATION_PREFERENCE";
    String IRS_TRANSMISSION_STATUS_CD = "IRS_TRANSMISSION_STATUS_CD";
    String EMAIL="RECIPIENT_E_MAIL";
    String DATE_FORMAT="MMDDYYYY";
    String ALL_NINES_DATE_FLAG = "99999999";
    String CONVERTED_ALL_NINES_DATE_FLAG="12319999";
    String ALL_ZEROS_DATE_FLAG = "00000000";
    String ALL_SIXES_DATE_FLAG = "66666666";
    String CONVERTED_ALL_SIXES_DATE_FLAG="12316666";
    String DATE_FORMAT_REGEX="^(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(19|20)\\d\\d$";
    String JOB_IMPORT_APPLICATION="importApplications";
    String STEP_IMPORT_APPLICATION="applicationJobStep";
    String RECIPIENT_LANGUAGE_PREFERENCE="RECIPIENT_LANGUAGE_PREFERENCE";
    String EMPLOYER_CONTACT_NO="EMPLOYER_CONTACT_NO";
    String PATH_TO_FILE="pathToFile";
    int FILE_FORMAT_CODE_LENGTH=5;
    int STATE_CODE_INDEX=0;
    int AGENCY_CODE_INDEX=1;
    int SYSTEM_CODE_INDEX=2;
    int FILE_RECEIVE_DATE_INDEX=3;
    int FILE_RECEIVE_VERSION_INDEX=4;
    int FILE_EXTENSION_LENGTH=4;
    int FILE_FORMAT_START_INDEX=0;
    String FILE_FORMAT_CODE_SPLITTER="_";
    String FILE_FORMAT_EXTENSION=".dat";
    String EMPTY="";
    String RECEIVE_DATE="receiveDate";
    String AGENCY_CODE="agencyCd";
    String STATE_CODE="stateCd";
    String SYSTEM_CODE="systemCd";
    String BATCH_ID="batchId";
    String TOTAL_COUNT="totalCount";
    String TOTAL_FAIL="totalFail";
    String TOTAL_PASS="totalPass";
    String EX_DETAILS="exDetails";
    String CORRECTION_DATE="CORRECTION_DATE";
    String PROVIDER_CONTACT_NO="PROVIDER_CONTACT_NO";
	String CHECKED = "check";
	String UNCHECKED = "UnChecked";
	String COVERED = "1";
	String CORRECTED = "C";
    String COMPLETE = "COMPLETE";
    String UNCOVERED = "0";
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
	String NAME="NAME";
	String SSN="SSN";
	String DOB="DOB";
	String ALL="ALL";

    /** Date the row was last updated */
    String UPDATED_DATE = "UPDATED_DATE" ;
    /** Person or system that last updated the row */
    String UPDATED_BY = "UPDATED_BY" ;
    
    String SYSTEM="System";
}

