package us.deloitteinnovation.aca.batch.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.export.step4.Step4109495Data;
import us.deloitteinnovation.aca.constants.PrintVendorConstants;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.deloitteinnovation.aca.util.Convert;
import us.deloitteinnovation.profile.ProfileProperties;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

/**
 */
public abstract class ExportUtil {

    /**
     *
     * @return  The directory into which the XML output file should be written.
     * @throws IllegalArgumentException If the XML output folder key does not exist in the properties file, or the directory does not exist.
     */
    public static File getOutputDirectory(String state, ProfileProperties profileProperties) {
        String key = state + "_XML_OUTPUT_FOLDER" ;
        String outputDir = profileProperties.getProperty(key) ;
        if (outputDir == null) {
            throw new IllegalArgumentException("XML output directory property not found using key '" + key + "'") ;
        }
        File dir = new File(outputDir) ;

        if (! dir.exists()) {
            throw new IllegalArgumentException("XML output directory '" + dir.getAbsolutePath() + "' does not exist.  " +
                    "Please create the directory with correct write permissions before XML export.") ;
        }
        return dir ;
    }
    
    public static File getPrintVendorOutputDirectory(ProfileProperties profileProperties) {
        String outputDir = profileProperties.getProperty("PRINT_VENDOR_XML_OUTPUT_FOLDER") ;
        if (outputDir == null) {
            throw new IllegalArgumentException("XML output directory property not found using key "+outputDir) ;
        }
        File dir = new File(outputDir) ;

        if (! dir.exists()) {
            throw new IllegalArgumentException("XML output directory '" + dir.getAbsolutePath() + "' does not exist.  " +
                    "Please create the directory with correct write permissions before XML export.") ;
        }
        return dir ;
    }

    /**
     * Helper method for writing an object to a given File.  The OutputStream associated with this operation is closed silently.
     * @param jaxb2Marshaller
     * @param outputFile
     * @param rootElementDoc
     * @throws IOException
     */
    public static void writeXml(Jaxb2Marshaller jaxb2Marshaller, File outputFile, Object rootElementDoc) throws IOException {
        FileOutputStream outputStream = null ;
        try {
            outputStream = new FileOutputStream(outputFile);
            Result xmlResult = new StreamResult(outputStream);
            jaxb2Marshaller.marshal(rootElementDoc, xmlResult);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * Removes ALL non alpha-numeric characters from the String.  Also removes all whitespace from front and end of the string,
     * as well as extra whitespace within.
     * @param value
     * @return  The value String provided, with all non-alpha-numerics and extra whitespace removed.
     */
    public static String sanitize(String value) {
        if (value == null || value.equals(""))
            return null ;
        String ret = new String(value) ;
        // Remove everything that is not alpha-numeric
        ret = ret.replaceAll("[^a-zA-Z0-9 ]", "") ;
        // Finally, strip out all unnecessary whitespace
        ret = ret.trim().replaceAll(" +", " ");
       return ret ;
    }

    /**
     * <p>
     * Stores the 109495B document data within the JobExecutionContext for use during Step 4.
     * If the filename list has not been initialized, it is created and stored on the JobExecutionContext.
     * </p>
     * <p>
     *  File data is serialized using the Step4109495Data#toString() method.
     * </p>
     * @param jobExecution JobExecution within which the filenames should be stored.
     * @param fileData
     * @see Step4109495Data#toString()
     */
    public static void addForm109495FilenameForStep4(JobExecution jobExecution, Step4109495Data fileData) {
        List<String> fileDataList = getForm109495FileDataForStep4AsStringList(jobExecution) ;
        if (fileDataList == null) {
            fileDataList = new ArrayList<>() ;
            jobExecution.getExecutionContext().put(BatchExportConstants.JobExecutionContextKeys.FORM_109495B_FILEDATA, fileDataList) ;
        }
        fileDataList.add(fileData.toString()) ;
    }

    /**
     * <p>
     *     Removes the 109495B document data within the JobExecutionContext so that the next iteration should
     *     not pick up this file again.
     * </p>
     * @param jobExecution JobExecution within which the filenames should be stored.
     */
    public static void removeForm109495FilenameForStep4(JobExecution jobExecution) {

        jobExecution.getExecutionContext().remove(BatchExportConstants.JobExecutionContextKeys.FORM_109495B_FILEDATA) ;
    }

    private static List<String> getForm109495FileDataForStep4AsStringList(JobExecution jobExecution) {
        return (List<String>) jobExecution.getExecutionContext().get(BatchExportConstants.JobExecutionContextKeys.FORM_109495B_FILEDATA) ;
    }

    /**
     *
     * @param jobExecution JobExecution within which the filenames should be stored.
     * @returns Retrieves all 109495B document filenames that were processed in Step 3. on the JobExecutionContext.
     * @see Step4109495Data#fromString(String)
     */
    public static List<Step4109495Data> getForm109495FilenamesForStep4(JobExecution jobExecution) {
        List<String> fileDataAsStringList = getForm109495FileDataForStep4AsStringList(jobExecution) ;
        List<Step4109495Data> fileDataList = new ArrayList<>(fileDataAsStringList.size()) ;
        for (String fileDataStr : fileDataAsStringList) {
            fileDataList.add(Step4109495Data.fromString(fileDataStr)) ;
        }
        return fileDataList ;
    }

    /**
     *
     * @param stepExecution StepExecution within which the filenames counter should be stored.
     * @returns The current filename processing count in Step 4.
     */
    public static int getForm109495FilenamesCounterForStep4(StepExecution stepExecution) {
        Integer counter =  (Integer) stepExecution.getExecutionContext().get(BatchExportConstants.StepExecutionContextKeys.FORM_109495B_FILENAMES_COUNTER) ;
        if (counter == null) {
            counter = 0 ;
            stepExecution.getExecutionContext().put(BatchExportConstants.StepExecutionContextKeys.FORM_109495B_FILENAMES_COUNTER, counter) ;
        }
        return counter ;
    }

    /**
     *
     * @param stepExecution StepExecution within which the filenames counter should be stored.
     * @returns The current filename processing count in Step 4 after being incremented.
     */
    public static int incrementForm109495FilenamesCounterForStep4(StepExecution stepExecution) {
        int count = getForm109495FilenamesCounterForStep4(stepExecution) ;
        stepExecution.getExecutionContext().putInt(BatchExportConstants.StepExecutionContextKeys.FORM_109495B_FILENAMES_COUNTER, ++count) ;
        return count ;
    }


    /**
     * <p>
     * Stores the manifest document filename within the JobExecutionContext.
     * </p>
     * <p>
     * At present, the only reason for doing so is for testing purposes.  We may wish to remove this, or only utilize it during test scenarios.
     * </p>
     * @param stepExecution JobExecution within which the filenames should be stored.
     * @param filename
     */
    public static void addManifestFilename(StepExecution stepExecution, String filename) {
        List<String> filenameList = getManifestFilenames(stepExecution) ;
        filenameList.add(filename) ;
    }

    /**
     * If the filename list has not been initialized, it is created and stored on the JobExecutionContext.
     * @param stepExecution
     * @return
     */
    public static List<String> getManifestFilenames(StepExecution stepExecution) {
        List<String> filenameList = (List<String>) stepExecution.getExecutionContext().get(BatchExportConstants.StepExecutionContextKeys.MANIFEST_FILENAMES) ;
        if (filenameList == null) {
            filenameList = new ArrayList<>() ;
            stepExecution.getExecutionContext().put(BatchExportConstants.StepExecutionContextKeys.MANIFEST_FILENAMES, filenameList) ;
        }
        return filenameList ;
    }

    /**
     * Helper to get a particular StepExecution from the JobExecution by name.
     * @param jobExecution
     * @param name
     * @return   Null if not found.
     */
    public static StepExecution getStepExecutionByName(JobExecution jobExecution, String name) {
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            if (stepExecution.getStepName().equals(name))
                return stepExecution ;
        }
        return null ;
    }


    /**
     * Determines if an IRS test scenario is being executed, and that the manifest should specify such.
     * If the test property is missing from the properties, then the run is considered to be a test (true is returned).
     * @param props
     * @return  True if the run is for a test scenario.  If property not found, also returns true.
     */
    public static boolean isTestXml(ProfileProperties props) {
        String isTestValue = props.getProperty("batch.export.xml.test") ;
        return (isTestValue == null ? true : Boolean.parseBoolean(isTestValue)) ;
    }


    /**
     *
     * @param config
     * @return
     */
    public static BusinessAddressGrpType getBusinessAddressFromSourceSystemConfig(SourceSystemConfig config) {
        BusinessAddressGrpType addressGrpType = new BusinessAddressGrpType() ;
        USAddressGrpType usAddress = new USAddressGrpType() ;
        usAddress.setAddressLine1Txt(sanitize(config.getProviderAddressLine1()));
        String line2 = sanitize(config.getProviderAddressLine2()) ;
        if (StringUtils.isNotEmpty(line2))
            usAddress.setAddressLine2Txt(line2);
        usAddress.setCityNm(sanitize(sanitize(config.getProviderCityOrTown())));
        usAddress.setUSStateCd(StateType.fromValue(config.getStateAbbreviation()));
        String zip = config.getProviderZipOrPostalCode() ;
        String [] zipFull = Convert.toZipCode(zip) ;
        usAddress.setUSZIPCd(zipFull[0]);
        usAddress.setUSZIPExtensionCd(zipFull[1]);
        addressGrpType.setUSAddressGrp(usAddress);
        return addressGrpType ;
    }
    
    public static BusinessAddressGrpType getBusinessAddressFromFilerDemographics(Filer filer) {
    	 BusinessAddressGrpType addressGrpType = new BusinessAddressGrpType() ;
    	 USAddressGrpType usAddress = new USAddressGrpType() ;
    	 usAddress.setAddressLine1Txt(sanitize(filer.getProviderAddLine1()));
    	 String line2 = sanitize(filer.getProviderAddLine2()) ;
    	 if (StringUtils.isNotEmpty(line2))
             usAddress.setAddressLine2Txt(line2);
    	 usAddress.setCityNm(sanitize(sanitize(filer.getProviderCity())));
    	 usAddress.setUSStateCd(StateType.fromValue(filer.getProviderState()));
    	 String zip = filer.getProviderZip() ;
         String [] zipFull = Convert.toZipCode(zip) ;
         usAddress.setUSZIPCd(zipFull[0]);
         usAddress.setUSZIPExtensionCd(zipFull[1]);
         addressGrpType.setUSAddressGrp(usAddress);
         return addressGrpType ;
	}

    public static OtherCompletePersonNameType getContactName(SourceSystemConfig config) {
        OtherCompletePersonNameType contact = new OtherCompletePersonNameType() ;
        contact.setPersonLastNm(sanitize(config.getProviderContactLastName()));
        contact.setPersonFirstNm(sanitize(config.getProviderContactFirstName()));
        return contact ;
    }

    // TODO Provide correct information here
    public static VendorInformationGrpType getVendorInfo() {
        VendorInformationGrpType vendor = new VendorInformationGrpType() ;
        vendor.setVendorCd("V");
        vendor.setContactPhoneNum("5102514425");
        OtherCompletePersonNameType name = new OtherCompletePersonNameType() ;
        name.setPersonLastNm("Rice");
        name.setPersonFirstNm("Matthew");
        vendor.setContactNameGrp(name);
        return vendor ;
    }

    public static Integer getYear(ExecutionContext executionContext) {
        // Job parameter may be a Long or a String.  Just parse as object to be safe.
        Object year = executionContext.get(BatchExportConstants.JobPropertiesKeys.YEAR) ;
        if (year == null)
            return null ;
        return Integer.parseInt(year.toString()) ;
    }

    public static String getReceiptId(ExecutionContext executionContext) {
        return (String) executionContext.get(BatchExportConstants.JobPropertiesKeys.RECEIPT_ID) ;
    }

    public static String getState(ExecutionContext executionContext) {
        return (String) executionContext.get(BatchExportConstants.JobPropertiesKeys.STATE) ;
    }
    public static String getFrequency(ExecutionContext executionContext) {
        return (String) executionContext.get(BatchExportConstants.StepExecutionContextKeys.PRINT_VENDOR_XML_FREQUENCY) ;
    }
    
    public static String getMailStatus(ExecutionContext executionContext) {
        return (String) executionContext.get(BatchExportConstants.StepExecutionContextKeys.PRINT_VENDOR_XML_MAILSTATUS) ;
    }

    public static String getTestScenario(ExecutionContext executionContext) {
        return (String) executionContext.get(BatchExportConstants.JobPropertiesKeys.TEST_SCENARIO) ;
    }



    public static String getPrettyPrintedXML(String form1095xml) {

        try
        {
            Document doc = DocumentHelper.parseText(form1095xml);
            StringWriter sw = new StringWriter();
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setSuppressDeclaration(true);
            format.setIndent(true);
            format.setIndentSize(3);
            format.setNewLineAfterDeclaration(false);
            XMLWriter xw = new XMLWriter(sw, format);
            xw.write(doc);

            return sw.toString().trim();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return form1095xml;
        }
    }
    
    public static String createFileNameForPrintVendor(StepExecution stepExecution){
        String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext());
        Integer year = ExportUtil.getYear(stepExecution.getJobExecution().getExecutionContext());
        String frequency = ExportUtil.getFrequency(stepExecution.getJobExecution().getExecutionContext());
        String mailStatus = ExportUtil.getMailStatus(stepExecution.getJobExecution().getExecutionContext());
        
        Integer totalRecordCount=Integer.parseInt((String)stepExecution.getJobExecution().getExecutionContext().get(BatchExportConstants.StepExecutionContextKeys.FORM_1095S_RECORD_COUNT));
        int totalFileCount = (totalRecordCount % PrintVendorConstants.RECORDSSIZE == 0) ? (totalRecordCount/PrintVendorConstants.RECORDSSIZE): Math.incrementExact(totalRecordCount /  PrintVendorConstants.RECORDSSIZE);
        
        String yearForFileName = year.toString().substring(year.toString().length() -2);
        DateFormat formatter = new SimpleDateFormat("MMddyyHHmm");
        StringBuilder printVendorXMLfFileName = new StringBuilder();
        printVendorXMLfFileName.append(yearForFileName).append(state).append(formatter.format(new Date()).toString());
        printVendorXMLfFileName.append("_");
        if(mailStatus.equals("Y")){
        	printVendorXMLfFileName.append("M");
        }else{
        	printVendorXMLfFileName.append("N");
        }
        if(frequency.equals("D")){
        	printVendorXMLfFileName.append("D");
        }else{
        	printVendorXMLfFileName.append("I");
        }
        printVendorXMLfFileName.append("_");
        printVendorXMLfFileName.append(appendZero(PrintVendorConstants.FILECOUNT));
        printVendorXMLfFileName.append("_");
        printVendorXMLfFileName.append(appendZero(totalFileCount));
        printVendorXMLfFileName.append(".xml");
        return printVendorXMLfFileName.toString();

    }
    
    private static String appendZero(int fileCount){
        String count = String.valueOf(fileCount);
        if(count.length() == 1){
            return "00"+count;
        }else if(count.length() == 2){
            return "0"+count;
        }
        return count;
    }
    
    public static String getAgencyCode(String state){
    	String agencyCode = null;
    	if("AR".equals(state)){
    		agencyCode = "DHS";
    	}else if("IN".equals(state)){
    		agencyCode = "FSS";
    	}else if("LA".equals(state)){
    		agencyCode = "DHH";
    	}
    	return agencyCode;
    }
    
    public static String getSystemCode(String state){
    	String systemCode = null;
    	if("AR".equals(state)){
    		systemCode = "DSS";
    	}else if("IN".equals(state)){
    		systemCode = "ICE";
    	}else if("LA".equals(state)){
    		systemCode = "EES";
    	}
    	return systemCode;
    }

    public static void main(String[]args) {
    }


    public static boolean getPriorYearFilingFlag(Integer year) {
       return ( year < ( Calendar.getInstance().get(Calendar.YEAR) -1 ) );
    }
}
