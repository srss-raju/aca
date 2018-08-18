package us.deloitteinnovation.aca.batch.invalidaddress.step;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.invalidaddress.util.InvalidAddressConstants;
import us.deloitteinnovation.aca.batch.invalidaddress.util.InvalidAddressUtil;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;
import us.deloitteinnovation.aca.batch.service.PrintVendorExceptionReportService;
import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;
import us.deloitteinnovation.aca.repository.PrintVendorJdbcRepository;

public class InvalidAddressWriter  implements ItemWriter<StepResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidAddressWriter.class);
    
    @Autowired
    PrintVendorJdbcRepository printVendorJdbcRepository;
    
    @Autowired
    BatchInfoService batchInfoService;
    
    @Autowired
    PrintVendorExceptionReportService printVendorExceptionReportService;
    
    
    @Override
    public void write(List<? extends StepResult> stepResult) throws Exception {
    		StepResult result  = stepResult.get(0); 
    		BatchInfoDto batchInfoDto = updateBatchInfoDetails(result.getInvalidAddressFile(), result);
    		if(result.isValid()){
    			updateFilerDemographics(result, batchInfoDto);
    		}else{
    			saveExceptionReport(stepResult, batchInfoDto);
    		}
    		LOGGER.info("Renaming processed file to {}", result.getInvalidAddressFile().getAbsolutePath() + ".bkp");
            final boolean isBackupDone = result.getInvalidAddressFile().renameTo(new File(result.getInvalidAddressFile().getAbsolutePath() + ".bkp"));

            if (isBackupDone) {
                LOGGER.debug("Backup completed successfully");
            }
		
    }
    
    BatchInfoDto updateBatchInfoDetails(File processedFile, StepResult stepResult) {
    	BufferedReader br = null;
    	int count = 0;
    	try {
			br = new BufferedReader(new FileReader(stepResult.getInvalidAddressFile()));
			count = Integer.parseInt(br.readLine());
		} catch (Exception e) {
			LOGGER.info("Error message: " + e);
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.info("Error message: " + e);
				}
			}
		}
    	
        final BatchInfoDto batchInfoDto = new BatchInfoDto();
        batchInfoDto.setReceiveDt(new Date());

        final int batchId = batchInfoService.save(batchInfoDto);
        batchInfoDto.setBatchId(batchId);
        batchInfoDto.setBatchType(BatchExportConstants.StepExecutionContextKeys.INVALID_ADDRESS);
        batchInfoDto.setFileName(processedFile.getName());
        if(stepResult.isValid()){
        	batchInfoDto.setTotalCount(count);
        	batchInfoDto.setTotalPass(count);
        	batchInfoDto.setTotalFail(0);
        	batchInfoDto.setStateCd(batchInfoDto.getFileName().split("_")[0]);
        }else{
        	batchInfoDto.setTotalCount(0);
        	batchInfoDto.setTotalPass(0);
        	batchInfoDto.setTotalFail(0);
        }
        batchInfoService.updatePrintAndProcess(batchInfoDto);
        return batchInfoDto;
    }
    
    private void updateFilerDemographics(StepResult result, BatchInfoDto batchInfoDto) {
		BufferedReader br = null;
		List<PrintVendorExceptionReportDto> printVendorExceptionReportDtoList = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(result.getInvalidAddressFile()));
			String line;
			while ((line = br.readLine()) != null) {
				try{
					String[] invalidAddress = line.trim().split(InvalidAddressConstants.SPLITBY);
					if(result.getTaxYearFromFileName().trim().equalsIgnoreCase(invalidAddress[1].trim())){
    					final FilerDemographic filerDemographic = new FilerDemographic();
    			        final FilerDemographicPK filerDemographicPK = new FilerDemographicPK();
    			        filerDemographicPK.setSourceUniqueId(Long.valueOf(invalidAddress[0].trim()));
    			        filerDemographicPK.setSourceCd(getSourceCd(result.getInvalidAddressFile().getName()));
    			        filerDemographicPK.setTaxYear(Integer.parseInt(invalidAddress[1].trim()));
    			        filerDemographic.setId(filerDemographicPK);
    			        filerDemographic.setFormStatus(InvalidAddressConstants.STATUS);
    			        result.getFilerDemographicList().add(filerDemographic);
					}
				}catch (Exception e) {
					LOGGER.info("Error message: " + e);
				}
			}
		} catch (Exception e) {
			LOGGER.info("Error message: " + e);
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.info("Error message: " + e);
				}
			}
		}
		
		int[] recordsUpdated = printVendorJdbcRepository.updateFilerDemographicStatus(result.getFilerDemographicList());
		
		StringBuilder fileName = new StringBuilder(result.getSourceCdFromFileName().toUpperCase())
		.append("_INVALID_ADDRESS_").append(InvalidAddressUtil.getDate())
		.append("_").append(result.getVersionFromFileName())
		.append("_").append(result.getTaxYearFromFileName())
		.append(".dat");
		
		int rowCount = 0;
        int failCount = 0;
        
		if (recordsUpdated != null && recordsUpdated.length != 0) {
			for (int i = 0; i < recordsUpdated.length; i++) {
				rowCount++;
				if (0 == recordsUpdated[i]) {
					PrintVendorExceptionReportDto exceptionReport = new PrintVendorExceptionReportDto();
					exceptionReport.setExDetails("The tax year and RID provided in row "+rowCount+"  cannot be found in the database");
					exceptionReport.setRowNumber(rowCount);
					exceptionReport.setBatchInfo(batchInfoDto);
					exceptionReport.setSourceUniqueId(result.getFilerDemographicList().get(i).getId().getSourceUniqueId());
					printVendorExceptionReportDtoList.add(exceptionReport);
					failCount++;
				}
			}
		}
		rowCount = rowCount + result.getFailureCount();
		failCount = failCount + result.getFailureCount();
		printVendorExceptionReportService.saveReport(printVendorExceptionReportDtoList);
        batchInfoDto.setTotalCount(rowCount);
        batchInfoDto.setTotalPass(rowCount-failCount);
        batchInfoDto.setTotalFail(failCount);
        batchInfoDto.setFileName(fileName.toString());
        batchInfoDto.setFileVersion(result.getVersionFromFileName());
        batchInfoDto.setStateCd(result.getState());
        batchInfoDto.setAgencyCd(InvalidAddressUtil.getAgencyCode(result.getState()));
        batchInfoDto.setSystemCd(InvalidAddressUtil.getSystemCode(result.getState()));
        batchInfoService.updatePrintAndProcess(batchInfoDto);
	}

    private String getSourceCd(String fileName){
    	return fileName.split("_")[0];
    }
    
    private void saveExceptionReport(List<? extends StepResult> items, BatchInfoDto batchInfoDto) {

        LOGGER.info("Logging exception reports into DB");

        for (final StepResult stepResult : items) {

            final List<PrintVendorExceptionReportDto> exceptionReportList = stepResult.getExceptionReport();

            if (exceptionReportList.isEmpty()) {
                continue;
            }

            for (final PrintVendorExceptionReportDto exceptionReport : exceptionReportList) {
                exceptionReport.setBatchInfo(batchInfoDto);
                printVendorExceptionReportService.saveReport(exceptionReport);

            }

        }

    }
    
}
