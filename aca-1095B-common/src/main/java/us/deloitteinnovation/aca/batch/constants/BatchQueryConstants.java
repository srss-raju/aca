package us.deloitteinnovation.aca.batch.constants;

import static us.deloitteinnovation.aca.batch.constants.BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER;
import static us.deloitteinnovation.aca.batch.constants.BatchConstants.PARAM_SOURCE_CD;

/**
 * Created by rgopalani on 10/12/2015.
 */
public interface BatchQueryConstants {

    String INSERT_IRS_TRANSMIT_DETAILS = new StringBuilder(" INSERT INTO IRS_TRANSMITTAL_DETAILS")
            .append(" (BATCH_ID,SOURCE_UNIQUE_ID,SOURCE_CD,TRANSMIT_STATUS,TRANSMIT_FILE_NAME,UPDATED_BY,UPDATED_DATE) ")
            .append(" VALUES ")
            .append(" ( :batchId,:sourceUniqueId,:sourceCd,:transmitStatus,:transmitFileName,:updatedBy,:updatedDate) ").toString();

    String INSERT_FILER_DEMOGRAPHICS = new StringBuilder(" INSERT INTO FILER_DEMOGRAPHICS")
            .append(" (BATCH_ID, COMMENTS, COMMUNICATION_PREFERENCE, CORRECTION_CODE, CORRECTION_DATE, RECIPIENT_E_MAIL, EMPLOYER_ADDRESS_LINE_1, EMPLOYER_ADDRESS_LINE_2, EMPLOYER_CITY_OR_TOWN, EMPLOYER_CONTACT_NO, EMPLOYER_COUNTRY, EMPLOYER_IDENTIFICATION_NUMBER, EMPLOYER_NAME, EMPLOYER_STATE_OR_PROVINCE, FILER_STATUS, RECIPIENT_LANGUAGE_PREFERENCE, POLICY_ORIGIN,  PROVIDER_ADDRESS_LINE_1, PROVIDER_ADDRESS_LINE_2, PROVIDER_CITY_OR_TOWN, PROVIDER_CONTACT_NO, PROVIDER_COUNTRY, PROVIDER_IDENTIFICATION_NUMBER, PROVIDER_NAME, PROVIDER_STATE_OR_PROVINCE, PROVIDER_ZIP_OR_POSTAL_CODE, RECIPIENT_ADDRESS_LINE_1, RECIPIENT_ADDRESS_LINE_2,  RECIPIENT_CITY, RECIPIENT_DOB, RECIPIENT_FIRST_NAME, RECIPIENT_LAST_NAME, RECIPIENT_MIDDLE_NAME, RECIPIENT_SSN, RECIPIENT_STATE, RECIPIENT_SUFFIX_NAME, RECIPIENT_TIN, RECIPIENT_ZIP_4, RECIPIENT_ZIP_5, RESPONSIBLE_PERSON_UNIQUE_ID, SHOP_IDENTIFIER, TAX_YEAR, UPDATED_BY, UPDATED_DATE, EMPLOYER_ZIP_OR_POSTAL_CODE, JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC, STATUS, MAILED_FORM, SOURCE_CD, SOURCE_UNIQUE_ID,FILER_DEMO_SEQ ) ")
            .append(" VALUES ")
            .append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (NEXT VALUE FOR SEQUENCE_NO_SEQ) )").toString();



        String INSERT_FILER_DEMOGRAPHICS_FILE_INGESTION = new StringBuilder(" INSERT INTO FILER_DEMOGRAPHICS")
                .append(" (BATCH_ID, COMMENTS, COMMUNICATION_PREFERENCE, CORRECTION_CODE, CORRECTION_DATE, RECIPIENT_E_MAIL, EMPLOYER_ADDRESS_LINE_1, EMPLOYER_ADDRESS_LINE_2, EMPLOYER_CITY_OR_TOWN, EMPLOYER_CONTACT_NO, EMPLOYER_COUNTRY, EMPLOYER_IDENTIFICATION_NUMBER, EMPLOYER_NAME, EMPLOYER_STATE_OR_PROVINCE, FILER_STATUS, RECIPIENT_LANGUAGE_PREFERENCE, POLICY_ORIGIN,  PROVIDER_ADDRESS_LINE_1, PROVIDER_ADDRESS_LINE_2, PROVIDER_CITY_OR_TOWN, PROVIDER_CONTACT_NO, PROVIDER_COUNTRY, PROVIDER_IDENTIFICATION_NUMBER, PROVIDER_NAME, PROVIDER_STATE_OR_PROVINCE, PROVIDER_ZIP_OR_POSTAL_CODE, RECIPIENT_ADDRESS_LINE_1, RECIPIENT_ADDRESS_LINE_2,  RECIPIENT_CITY, RECIPIENT_DOB, RECIPIENT_FIRST_NAME, RECIPIENT_LAST_NAME, RECIPIENT_MIDDLE_NAME, RECIPIENT_SSN, RECIPIENT_STATE, RECIPIENT_SUFFIX_NAME, RECIPIENT_TIN, RECIPIENT_ZIP_4, RECIPIENT_ZIP_5, RESPONSIBLE_PERSON_UNIQUE_ID, SHOP_IDENTIFIER, TAX_YEAR, UPDATED_BY, UPDATED_DATE, EMPLOYER_ZIP_OR_POSTAL_CODE, JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC, STATUS, MAILED_FORM, SOURCE_CD,SOURCE_UNIQUE_ID,RECORD_CREATED_DATE,RECORD_VERSION,CORRECTION_INDICATOR,FILER_DEMO_SEQ ) ")
                .append(" VALUES ")
                .append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?, (NEXT VALUE FOR SEQUENCE_NO_SEQ) )").toString();



    String INSERT_FILER_COVERAGE = new StringBuilder(" INSERT  INTO   FILER_COVERAGE_SOURCE")
            .append(" (APR, AUG, COMMENTS, DEC, FEB, JAN, JUL, JUN, MAR, MAY, NOV, OCT, ORIG_COVERAGE_BEGIN_DATE, ORIG_COVERAGE_END_DATE, SEP, UPDATED_BY, UPDATED_DATE, PROGRAM_NAME,SOURCE_CD, SOURCE_UNIQUE_ID,CASE_APPLICATION_ID,TAX_YEAR,RECORD_CREATED_DATE,COVERAGE_SEQ_NO,FILER_DEMO_SEQ ) ")
            .append(" VALUES ")
            .append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,(NEXT VALUE FOR COVERAGE_SEQ),")
            .append("null)")
            .toString();

    // we are no longer using FILER_DEMO_SEQ in the FILER_COVERAGE_SOURCE table.
    String INSERT_FILER_COVERAGE_INCREMENT_SEQ = new StringBuilder(" INSERT  INTO   FILER_COVERAGE_SOURCE")
            .append(" (APR, AUG, COMMENTS, DEC, FEB, JAN, JUL, JUN, MAR, MAY, NOV, OCT, ORIG_COVERAGE_BEGIN_DATE,")
            .append(" ORIG_COVERAGE_END_DATE, SEP, UPDATED_BY, UPDATED_DATE, PROGRAM_NAME,SOURCE_CD, SOURCE_UNIQUE_ID,")
            .append(" CASE_APPLICATION_ID,COVERAGE_SEQ_NO,FILER_DEMO_SEQ) ")
            .append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,")
            .append( "(NEXT VALUE FOR COVERAGE_SEQ),(NEXT VALUE FOR SEQUENCE_NO_SEQ))")
            .toString();

    String INSERT_BATCH_INFO = new StringBuilder(" INSERT INTO BATCH_INFO")
            .append(" (BATCH_ID,RECEIVE_DATE) ")
            .append(" VALUES ")
            .append(" ( NEXT VALUE FOR BATCH_INFO_SEQ,:receiveDate) ").toString();

    String UPDATE_BATCH_INFO_CODES = new StringBuilder(" UPDATE BATCH_INFO")
            .append(" set ")
            .append(" AGENCY_CD=:agencyCd," +
                    "STATE_CD=:stateCd," +
                    "SYSTEM_CD=:systemCd," +
                    "FILE_VERSION=:fileVersion," +
                    "FILENAME=:fileName," +
                    "BATCH_TYPE=:batchType " +
                    "where " +
                    "batch_id=:batchId ").toString();

    String INSERT_EXCEPTION_REPORT = new StringBuilder(" INSERT INTO EXCEPTION_REPORT ")
            .append(" ( EXCEPTION_REPORT_ID,EX_DETAILS,BATCH_ID,source_unique_id, row_number ) ")
            .append(" VALUES ")
            .append(" ( NEXT VALUE FOR ex_repo_seq,?,?,?,?) ").toString();
    String UPDATE_BATCH_INFO_COUNTS = new StringBuilder(" UPDATE BATCH_INFO")
            .append(" set ")
            .append(" TOTAL_COUNT=:totalCount,TOTAL_FAIL=:totalFail,TOTAL_PASS=:totalPass where batch_id=:batchId ").toString();

    String INSERT_EXCEPTION_REPORT_ROW_NUM_SOURCE_UNIQUE_ID = new StringBuilder(" INSERT INTO EXCEPTION_REPORT ")
            .append(" ( EXCEPTION_REPORT_ID,EX_DETAILS,BATCH_ID,ROW_NUMBER,SOURCE_UNIQUE_ID) ")
            .append(" VALUES ")
            .append(" ( NEXT VALUE FOR ex_repo_seq,:exDetails,:batchId,:rowNumber,:sourceUniqueId) ").toString();

    String INSERT_EXCEPTION_REPORT_ROW_NUM = new StringBuilder(" INSERT INTO EXCEPTION_REPORT ")
            .append(" ( EXCEPTION_REPORT_ID,EX_DETAILS,BATCH_ID,ROW_NUMBER) ")
            .append(" VALUES ")
            .append(" ( NEXT VALUE FOR ex_repo_seq,:exDetails,:batchId,:rowNumber) ").toString();

    String UPDATE_FILER_DEMOGRAPHICS = new StringBuilder(" UPDATE FILER_DEMOGRAPHICS ")
            .append(" set ")
            .append(" BATCH_ID=:batchId,TAX_YEAR=:taxYear,RECIPIENT_FIRST_NAME=:recipientFirstName,")
            .append("RECIPIENT_MIDDLE_NAME=:recipientMiddleName,RECIPIENT_LAST_NAME=:recipientLastName,")
            .append("RECIPIENT_SUFFIX_NAME=:recipientSuffixName,RECIPIENT_SSN=:recipientSSN,")
            .append("RECIPIENT_TIN=:recipientTin,RECIPIENT_DOB=:recipientDOB,")
            .append("RECIPIENT_ADDRESS_LINE_1=:recipientAddressLine1,")
            .append("RECIPIENT_ADDRESS_LINE_2=:recipientAddressLine2,RECIPIENT_CITY=:recipientCity,")
            .append("RECIPIENT_STATE=:recipientState,RECIPIENT_ZIP_5=:recipientZip5,RECIPIENT_ZIP_4=:recipientZip4,")
            .append("RECIPIENT_E_MAIL=:recipientEmail,RECIPIENT_LANGUAGE_PREFERENCE=:recipientLanguagePreference,")
            .append("POLICY_ORIGIN=:policyOrigin,SHOP_IDENTIFIER=:shopIdentifier,EMPLOYER_NAME=:employerName,")
            .append("EMPLOYER_IDENTIFICATION_NUMBER=:employerIdentificationNumber,")
            .append("EMPLOYER_CONTACT_NO=:employerContactNo,EMPLOYER_ADDRESS_LINE_1=:employerAddressLine1,")
            .append("EMPLOYER_ADDRESS_LINE_2=:employerAddressLine2,EMPLOYER_CITY_OR_TOWN=:employerCityOrTown,")
            .append("EMPLOYER_STATE_OR_PROVINCE=:employerStateOrProvince,EMPLOYER_COUNTRY=:employerCountry,")
            .append("EMPLOYER_ZIP_OR_POSTAL_CODE=:employerZipOrPostalCode,PROVIDER_NAME=:providerName,")
            .append("PROVIDER_IDENTIFICATION_NUMBER=:providerIdentificationNumber,")
            .append("PROVIDER_CONTACT_NO=:providerContactNo,PROVIDER_ADDRESS_LINE_1=:providerAddressLine1,")
            .append("PROVIDER_ADDRESS_LINE_2=:providerAddressLine2,PROVIDER_CITY_OR_TOWN=:providerCityOrTown,")
            .append("PROVIDER_STATE_OR_PROVINCE=:providerStateOrProvince,PROVIDER_COUNTRY=:providerCountry,")
            .append("PROVIDER_ZIP_OR_POSTAL_CODE=:providerZipOrPostalCode,FILER_STATUS=:filerStatus,")
            .append("RESPONSIBLE_PERSON_UNIQUE_ID=:responsiblePersonUniqueId,FILER_DEMO_SEQ=(NEXT VALUE FOR SEQUENCE_NO_SEQ),")
            .append("UPDATED_BY=:updatedBy,UPDATED_DATE=:updatedDate,")
            .append("COMMUNICATION_PREFERENCE=:communicationPreference,CORRECTION_CODE=:correctionCode,")
            .append("MAILED_FORM=:mailedForm,")
            .append("JAN=:jan,FEB=:feb,MAR=:mar,APR=:apr,MAY=:may,JUN=:jun,JUL=:jul,AUG=:aug,SEP=:sep,OCT=:oct,NOV=:nov,DEC=:dec")
            .append(" where SOURCE_UNIQUE_ID=:sourceUniqueId and SOURCE_CD=:sourceCd")
            .toString();



        String UPDATE_FILER_DEMOGRAPHICS_FILE_INGESTION  = new StringBuilder(" UPDATE FILER_DEMOGRAPHICS ")
        .append(" set ")
        .append(" BATCH_ID=:batchId ,RECIPIENT_FIRST_NAME=:recipientFirstName,")
        .append("RECIPIENT_MIDDLE_NAME=:recipientMiddleName,RECIPIENT_LAST_NAME=:recipientLastName,")
        .append("RECIPIENT_SUFFIX_NAME=:recipientSuffixName,RECIPIENT_SSN=:recipientSSN,")
        .append("RECIPIENT_TIN=:recipientTin,RECIPIENT_DOB=:recipientDOB,")
        .append("RECIPIENT_ADDRESS_LINE_1=:recipientAddressLine1,")
        .append("RECIPIENT_ADDRESS_LINE_2=:recipientAddressLine2,RECIPIENT_CITY=:recipientCity,")
        .append("RECIPIENT_STATE=:recipientState,RECIPIENT_ZIP_5=:recipientZip5,RECIPIENT_ZIP_4=:recipientZip4,")
        .append("RECIPIENT_E_MAIL=:recipientEmail,RECIPIENT_LANGUAGE_PREFERENCE=:recipientLanguagePreference,")
        .append("POLICY_ORIGIN=:policyOrigin,SHOP_IDENTIFIER=:shopIdentifier,EMPLOYER_NAME=:employerName,")
        .append("EMPLOYER_IDENTIFICATION_NUMBER=:employerIdentificationNumber,")
        .append("EMPLOYER_CONTACT_NO=:employerContactNo,EMPLOYER_ADDRESS_LINE_1=:employerAddressLine1,")
        .append("EMPLOYER_ADDRESS_LINE_2=:employerAddressLine2,EMPLOYER_CITY_OR_TOWN=:employerCityOrTown,")
        .append("EMPLOYER_STATE_OR_PROVINCE=:employerStateOrProvince,EMPLOYER_COUNTRY=:employerCountry,")
        .append("EMPLOYER_ZIP_OR_POSTAL_CODE=:employerZipOrPostalCode,PROVIDER_NAME=:providerName,")
        .append("PROVIDER_IDENTIFICATION_NUMBER=:providerIdentificationNumber,")
        .append("PROVIDER_CONTACT_NO=:providerContactNo,PROVIDER_ADDRESS_LINE_1=:providerAddressLine1,")
        .append("PROVIDER_ADDRESS_LINE_2=:providerAddressLine2,PROVIDER_CITY_OR_TOWN=:providerCityOrTown,")
        .append("PROVIDER_STATE_OR_PROVINCE=:providerStateOrProvince,PROVIDER_COUNTRY=:providerCountry,")
        .append("PROVIDER_ZIP_OR_POSTAL_CODE=:providerZipOrPostalCode,FILER_STATUS=:filerStatus,")
                .append("IRS_TRANSMISSION_STATUS_CD=:irsTransmissionCd,")
        .append("RESPONSIBLE_PERSON_UNIQUE_ID=:responsiblePersonUniqueId,FILER_DEMO_SEQ=(NEXT VALUE FOR SEQUENCE_NO_SEQ),")
        .append("UPDATED_BY=:updatedBy,UPDATED_DATE=:updatedDate,")
        .append("COMMUNICATION_PREFERENCE=:communicationPreference,CORRECTION_CODE=:correctionCode,")
        .append("MAILED_FORM=:mailedForm, FORM_STATUS=:formStatus,")
        .append("JAN=:jan,FEB=:feb,MAR=:mar,APR=:apr,MAY=:may,JUN=:jun,JUL=:jul,AUG=:aug,SEP=:sep,OCT=:oct,NOV=:nov,DEC=:dec, STATUS =:status,  CORRECTION_INDICATOR=:corIndicator, RECORD_VERSION=:recordVersion  ")
        .append(" where SOURCE_UNIQUE_ID=:sourceUniqueId and SOURCE_CD=:sourceCd and TAX_YEAR=:taxYear  ")
        .toString();


    String MARK_RECORD_AS_REGENERATE= new StringBuilder(" UPDATE FILER_DEMOGRAPHICS ")
            .append(" set FORM_STATUS= :formStatus , CORRECTION_INDICATOR=:corCode ")
            .append(" where SOURCE_UNIQUE_ID=:sourceUniqueId and SOURCE_CD=:sourceCd and TAX_YEAR=:taxYear ")
            .toString();

    String UPDATE_FILER_DEMOGRAPHICS_VERSION = new StringBuilder(" UPDATE FILER_DEMOGRAPHICS ")
            .append(" set RECORD_VERSION = :recordVersion ")
            .append(" where SOURCE_UNIQUE_ID=:sourceUniqueId and SOURCE_CD=:sourceCd and TAX_YEAR=:taxYear ")
            .toString();

    String UPDATE_FILER_DEMOGRAPHICS_COVERAGE =
            new StringBuilder(" UPDATE FILER_DEMOGRAPHICS set ")
                    .append("JAN=:jan,FEB=:feb,MAR=:mar,APR=:apr,MAY=:may,JUN=:jun,JUL=:jul,AUG=:aug,SEP=:sep,OCT=:oct,NOV=:nov,DEC=:dec")
                    .append(" where SOURCE_UNIQUE_ID=:sourceUniqueId and SOURCE_CD=:sourceCd")
                    .toString();

    String TOGGLE_FILER_DEMOGRAPHICS_ACTIVE =
            new StringBuilder(" UPDATE FILER_DEMOGRAPHICS ")
                    .append(" set STATUS = ? ,UPDATED_BY=?,UPDATED_DATE=? ")
                    .append(" where SOURCE_UNIQUE_ID = ? and SOURCE_CD = ?")
                    .toString();

    String UPDATE_FILER_COVERAGE = new StringBuilder(" UPDATE FILER_COVERAGE_SOURCE ")
            .append(" set ")
            .append(" JAN=:jan,FEB=:feb,MAR=:mar,APR=:apr,MAY=:may,JUN=:jun,JUL=:jul,AUG=:aug,SEP=:sep,OCT=:oct,NOV=:nov,DEC=:dec,ORIG_COVERAGE_BEGIN_DATE=:originCoverageBeginDate,ORIG_COVERAGE_END_DATE=:originCoverageEndDate,CASE_APPLICATION_ID=:recipientCaseApplicationId,PROGRAM_NAME=:programName,UPDATED_BY=:updatedBy,UPDATED_DATE=:updatedDate where SOURCE_UNIQUE_ID=:sourceUniqueId and SOURCE_CD=:sourceCd").toString();

    String DELETE_FILER_COVERAGES_FOR_FD =
            new StringBuilder(" delete from FILER_COVERAGE_SOURCE ")
                    .append("  where SOURCE_UNIQUE_ID=:sourceUniqueId and SOURCE_CD=:sourceCd and TAX_YEAR=:taxYear").toString();

    String SELECT_FILER_DEMOGRAPHICS = new StringBuilder(" select SOURCE_UNIQUE_ID as  sourceUniqueId from FILER_DEMOGRAPHICS where RECIPIENT_SSN=:recipientSSN or RECIPIENT_TIN=:recipientTin ").toString();

    String SELECT_INDIVIDUALS_FOR_PK =
            " select RECIPIENT_SSN, RECIPIENT_TIN, RECIPIENT_DOB from FILER_DEMOGRAPHICS "
                    + " where SOURCE_UNIQUE_ID = :" + EX_SOURCE_UNIQUE_ID_NUMBER
                    + " and SOURCE_CD = :" + PARAM_SOURCE_CD;

    String EXCEPTION_REPORT_DETAILS = new StringBuilder(" select exception_report_id,batch_id,source_unique_id,ex_details,row_number from exception_report where batch_id=").toString();

    String EXCEPTION_REPORT_COUNT = new StringBuilder("select count(*) from EXCEPTION_REPORT where batch_id=:batchId").toString();

    String EXCEPTION_REPORT = new StringBuilder(
            " select exception_report_id,batch_id,source_unique_id,ex_details,row_number from exception_report where batch_id=:batchId")
            .toString();

    String CLEAR_EXCEPTION_REPORT = new StringBuilder("DELETE FROM EXCEPTION_REPORT").toString();


    String CACHE_LOAD_QUERY =
        "select"
        + " fd.SOURCE_UNIQUE_ID, fd.SOURCE_CD, RECIPIENT_SSN, RECIPIENT_TIN, RECIPIENT_DOB,"
        + " CASE_APPLICATION_ID, PROGRAM_NAME, ORIG_COVERAGE_BEGIN_DATE, ORIG_COVERAGE_END_DATE,"
        + " CORRECTION_CODE, FILER_STATUS, STATUS, RESPONSIBLE_PERSON_UNIQUE_ID, fd.IRS_TRANSMISSION_STATUS_CD,"
        + " fd.JAN, fd.FEB, fd.MAR, fd.APR, fd.MAY, fd.JUN, fd.JUL, fd.AUG, fd.SEP, fd.OCT, fd.NOV, fd.DEC"
        + " from dbo.FILER_DEMOGRAPHICS fd"
        + " left outer join dbo.FILER_COVERAGE_SOURCE fcs ON"
        + " fd.SOURCE_UNIQUE_ID = fcs.SOURCE_UNIQUE_ID"
        + " and fd.SOURCE_CD = fcs.SOURCE_CD"
        + " where fd.SOURCE_CD = ?"
        + " order by fd.SOURCE_UNIQUE_ID, fd.SOURCE_CD, fcs.ORIG_COVERAGE_BEGIN_DATE";

    String SELECT_FILER_DEMO_SEQ_CURRENT_VAL = "SELECT CAST(Current_Value AS BIGINT) FROM SYS.Sequences WHERE name='SEQUENCE_NO_SEQ'";

    String CLEAR_BATCH_INFO = "DELETE FROM BATCH_INFO";

    String GET_TOP_BATCH_ID = "SELECT TOP 1 BATCH_ID FROM BATCH_INFO order by BATCH_ID desc";

    String INSERT_INTO_BUSINESS_RULE_LOG = "insert into BUSINESS_DECISIONS_LOG ("
            + "TAX_YEAR  ,"
            + "SOURCE_CD  ,"
            + "SOURCE_UNIQUE_ID ,"
            + "BUSINESS_DECISION ,"
            + "BUSINESS_RULE ,"
            + "DOB ,"
            + "CORRECTION_CODE ,"
            + "BATCH_ID ,"
            + "ROW_NUMBER ,"
            + "UPDATED_BY  ,"
            + "UPDATED_DATE )"
            + "values (?,?,?,?,?,?,?,?,?,?,?)";

    String STAGING_COLUMNS_NAME ="[ROW_ID] "+
            "      ,[SOURCE_UNIQUE_ID] "+
            "      ,[SOURCE_CD] "+
            "      ,[BATCH_ID] "+
            "      ,[TAX_YEAR] "+
            "      ,[RECIPIENT_FIRST_NAME] "+
            "      ,[RECIPIENT_MIDDLE_NAME] "+
            "      ,[RECIPIENT_LAST_NAME] "+
            "      ,[RECIPIENT_SUFFIX_NAME] "+
            "      ,[RECIPIENT_SSN] "+
            "      ,[RECIPIENT_TIN] "+
            "      ,[RECIPIENT_DOB] "+
            "      ,[RECIPIENT_ADDRESS_LINE_1] "+
            "      ,[RECIPIENT_ADDRESS_LINE_2] "+
            "      ,[RECIPIENT_CITY] "+
            "      ,[RECIPIENT_STATE] "+
            "      ,[RECIPIENT_ZIP_5] "+
            "      ,[RECIPIENT_ZIP_4] "+
            "      ,[RECIPIENT_E_MAIL] "+
            "      ,[RECIPIENT_LANGUAGE_PREFERENCE] "+
            "      ,[POLICY_ORIGIN] "+
            "      ,[SHOP_IDENTIFIER] "+
            "      ,[EMPLOYER_NAME] "+
            "      ,[EMPLOYER_IDENTIFICATION_NUMBER] "+
            "      ,[EMPLOYER_CONTACT_NO] "+
            "      ,[EMPLOYER_ADDRESS_LINE_1] "+
            "      ,[EMPLOYER_ADDRESS_LINE_2] "+
            "      ,[EMPLOYER_CITY_OR_TOWN] "+
            "      ,[EMPLOYER_STATE_OR_PROVINCE] "+
            "      ,[EMPLOYER_COUNTRY] "+
            "      ,[EMPLOYER_ZIP_OR_POSTAL_CODE] "+
            "      ,[PROVIDER_NAME] "+
            "      ,[PROVIDER_IDENTIFICATION_NUMBER] "+
            "      ,[PROVIDER_CONTACT_NO] "+
            "      ,[PROVIDER_ADDRESS_LINE_1] "+
            "      ,[PROVIDER_ADDRESS_LINE_2] "+
            "      ,[PROVIDER_CITY_OR_TOWN] "+
            "      ,[PROVIDER_STATE_OR_PROVINCE] "+
            "      ,[PROVIDER_COUNTRY] "+
            "      ,[PROVIDER_ZIP_OR_POSTAL_CODE] "+
            "      ,[FILER_STATUS] "+
            "      ,[COMMUNICATION_PREFERENCE] "+
            "      ,[COMMENTS] "+
            "      ,[UPDATED_BY] "+
            "      ,[UPDATED_DATE] "+
            "      ,[CORRECTION_DATE] "+
            "      ,[CORRECTION_CODE] "+
            "      ,[FORM_STATUS] "+
            "      ,[STATUS] "+
            "      ,[FILER_DEMO_SEQ] "+
            "      ,[JAN] "+
            "      ,[FEB] "+
            "      ,[MAR] "+
            "      ,[APR] "+
            "      ,[MAY] "+
            "      ,[JUN] "+
            "      ,[JUL] "+
            "      ,[AUG] "+
            "      ,[SEP] "+
            "      ,[OCT] "+
            "      ,[NOV] "+
            "      ,[DEC] "+
            "      ,[RESPONSIBLE_PERSON_UNIQUE_ID] "+
            "      ,[MAILED_FORM] "+
            "      ,[IRS_TRANSMISSION_STATUS_CD] "+
            "      ,[ROW_NUMBER] "+
            "      ,[RECORD_SOURCE] "+
            "      ,[ORIG_COVERAGE_BEGIN_DATE] "+
            "      ,[ORIG_COVERAGE_END_DATE] "+
            "      ,[CASE_APPLICATION_ID] "+
            "      ,[PROGRAM_NAME] , [CORRECTION_INDICATOR], [RECORD_VERSION] ";
    String GET_FILE_NAME_FOR_BATCH_ID = "SELECT FILENAME FROM BATCH_INFO WHERE BATCH_ID = ";
    String GET_DISTINCT_EXCEPTION_ROW_NUM_FOR_BATCH = "select distinct ROW_NUMBER as SID from EXCEPTION_REPORT where BATCH_ID = ";
    
    String UPDATE_BATCH_INFO_PRINTANDPROCESS_XML = new StringBuilder(" UPDATE BATCH_INFO set ").append(" AGENCY_CD=:agencyCd,STATE_CD=:stateCd,SYSTEM_CD=:systemCd,FILE_VERSION=:fileVersion,FILENAME=:fileName, TOTAL_COUNT=:totalCount,TOTAL_FAIL=:totalFail,TOTAL_PASS=:totalPass,BATCH_TYPE=:batchType,REQUISITION_ID=:requisitionId where batch_id=:batchId ").toString();
    String GET_PASSED_COUNT_FROM_BUSINESS_LOG = "select count(bl.ROW_NUMBER) as SIDCOUNT from BUSINESS_DECISIONS_LOG bl where bl.BUSINESS_DECISION='PASSED' and bl.BATCH_ID = ";
    String GET_DISTINCT_COUNT_FROM_EXCEPTION_REPORT = "select count(distinct source_unique_id) as SIDCOUNT from EXCEPTION_REPORT where BATCH_ID = ";

}