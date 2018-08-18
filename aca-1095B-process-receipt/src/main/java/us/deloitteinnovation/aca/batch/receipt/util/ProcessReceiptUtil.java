package us.deloitteinnovation.aca.batch.receipt.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.deloitteinnovation.aca.batch.receipt.dto.ProcessReceiptDto;
import us.deloitteinnovation.aca.entity.PrintDetail;
import us.deloitteinnovation.aca.entity.PrintDetailPK;
import us.deloitteinnovation.profile.ProfileProperties;

/**
 * 
 * @author rbongurala
 *
 */
public class ProcessReceiptUtil {
	private static final Logger logger = LoggerFactory.getLogger(ProcessReceiptUtil.class);
	
	static Set<String> corStatus = new HashSet<String>();
	static Set<String> comStatus = new HashSet<String>();

	public ProcessReceiptDto readReceiptFile(ProfileProperties profileProperties) {
		String inputDir = profileProperties.getProperty(ProcessReceiptConstants.PRINTRECEIPTINPUTFILELOCATION) ;
		logger.info("Input files location ------->>"+inputDir);
		if (inputDir == null) {
            throw new IllegalArgumentException("XML output directory property not found using key '" + ProcessReceiptConstants.PRINTRECEIPTINPUTFILELOCATION + "'") ;
        }
		File folder = new File(inputDir);
		logger.info("folder ------->>"+folder);
		File[] listOfFiles = folder.listFiles();
		logger.info("listOfFiles ------->>"+listOfFiles);
		List<PrintDetail> printDetails = new ArrayList<PrintDetail>();
		List<String> emptyFiles = new ArrayList<String>();
		
		for (File file : listOfFiles) {
		    if (file.isFile() && file.getName().contains("COR")) {
		    		preparePrintDetails(printDetails,emptyFiles, file,true);
		    }
		}
		ProcessReceiptDto processReceiptDto = new ProcessReceiptDto();
		processReceiptDto.setPrintDetails(printDetails);
		processReceiptDto.setEmptyFiles(emptyFiles);
		processReceiptDto.setCOR(true);
		return processReceiptDto;
	}
	
	private void preparePrintDetails(List<PrintDetail> printDetails,List<String> emptyFiles, File file, boolean isCORFile){
		BufferedReader br = null;
		String fileName = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
			while ((ProcessReceiptConstants.LINE = br.readLine()) != null) {
				if (!StringUtils.isEmpty(ProcessReceiptConstants.LINE)) {
					PrintDetail printDetail = new PrintDetail();
					PrintDetailPK id = new PrintDetailPK();
					String[] receipt = ProcessReceiptConstants.LINE.split(ProcessReceiptConstants.SPLITBY);
					fileName = isCORFile ? (file.getName().replace("COR_", ""))	: (file.getName().replace("COM_", ""));
					printDetail.getId().setPrintFileName(fileName.replace(".csv", ""));
					id.setSourceUniqueId(Long.valueOf(receipt[0]));
					id.setTaxYear(String.valueOf(getTaxYear(file)));
					id.setSourceCd(getSourceId(getStateCode(file)));
					printDetail.setId(id);
					printDetail.setPrintStatus(receipt[1]);
					printDetails.add(printDetail);
				}else{
					fileName = isCORFile ? (file.getName().replace("COR_", ""))	: (file.getName().replace("COM_", ""));
					emptyFiles.add(fileName.replace(".csv", ""));
				}
			}
		} catch (Exception e) {
			logger.info("Error message: " + e);
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					logger.info("Error message: " + e);
				}
			}
		}
	}

	public ProcessReceiptDto readMailFile(ProfileProperties profileProperties) {
		String inputDir = profileProperties.getProperty(ProcessReceiptConstants.PRINTRECEIPTINPUTFILELOCATION) ;
		if (inputDir == null) {
            throw new IllegalArgumentException("XML output directory property not found using key '" + ProcessReceiptConstants.PRINTRECEIPTINPUTFILELOCATION + "'") ;
        }
		File folder = new File(inputDir);
		File[] listOfFiles = folder.listFiles();
		List<PrintDetail> printDetails = new ArrayList<PrintDetail>();
		List<String> emptyFiles = new ArrayList<String>();
		
		for (File file : listOfFiles) {
		    if (file.isFile() && file.getName().contains("COM")) {
		    		preparePrintDetails(printDetails,emptyFiles, file,false);
		    }
		}
		ProcessReceiptDto processReceiptDto = new ProcessReceiptDto();
		processReceiptDto.setPrintDetails(printDetails);
		processReceiptDto.setEmptyFiles(emptyFiles);
		processReceiptDto.setCOR(false);
		return processReceiptDto;
	}
	

	private static String getSourceId(String state){
		String sourceCd = null;
		if("AR".equals(state)){
			sourceCd = ProcessReceiptConstants.ARDHSDSS;
		}else if("IN".equals(state)){
			sourceCd = ProcessReceiptConstants.INFSSICE;
		}else if("LA".equals(state)){
			sourceCd = ProcessReceiptConstants.LADHHEES;
		}
		return sourceCd;
	}
	
	private static int getTaxYear(File inputFile) {
		Calendar calendar=Calendar.getInstance();
		try {
			String[] fileNameArray = inputFile.getName().split("_");

			SimpleDateFormat sdf = new SimpleDateFormat("MMddyyHHmm");
			Date date = sdf.parse(fileNameArray[1].substring(4));
			calendar.setTime(date);
			
		} catch (ParseException parseException) {
			logger.error("Error while parsing the date");
		}
		logger.info("Year ---->> "+calendar.get(Calendar.YEAR));
		return calendar.get(Calendar.YEAR);

	}
	
	
	private static String getStateCode(File inputFile){
		String[] fileNameArray = inputFile.getName().split("_");
		return fileNameArray[1].substring(2, 4);
	}
	
	public static Set<String> getCORStatus(){
		corStatus.add("Mail Pending");
		corStatus.add("Resend");
		return corStatus;
	}
	
	public static Set<String> getCOMStatus(){
		comStatus.add("Mailed");
		return comStatus;
	}
}
