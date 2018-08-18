package us.deloitteinnovation.aca.batch.receipt.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.receipt.dto.StepResult;
import us.deloitteinnovation.aca.batch.receipt.repository.PrintDetailsMailRepository;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;
import us.deloitteinnovation.aca.batch.service.PrintVendorExceptionReportService;
import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;
import us.deloitteinnovation.aca.repository.PrintVendorJdbcRepository;

/**
 *
 * @author ThirupathiReddy V
 *
 */
public class ConfirmationFileWriter implements ItemWriter<StepResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationFileWriter.class);

    @Autowired
    BatchInfoService batchInfoService;

    @Autowired
    PrintVendorExceptionReportService printVendorExceptionReportService;

    private StepExecution stepExecution;

    @Autowired
    PrintDetailsMailRepository printDetailsMailRepository;

    @Autowired
    PrintVendorJdbcRepository printVendorJdbcRepository;

    @Override
    public void write(List<? extends StepResult> items) throws Exception {

        LOGGER.info("Executing writer with the items size {}  ", items.size());

        final boolean isCOR = isCOR();

        LOGGER.info("Processing COR file ? {} ", isCOR);

        final List<String> emptyFiles = new ArrayList<>();
        
        int[] recordsUpdated = null;

        if (items.isEmpty()) {
            LOGGER.info("No records received in the writer.job executing completed.");
            return;
        }

        final StepResult stepResult = items.get(0);// since chunk size is 1 items size always one

        if (stepResult.isValid()) {
            if (stepResult.getPrintDetails().isEmpty()) {
                final File emptyFile = stepResult.getProcessedFile();
                final String baseFile = emptyFile.getName().substring(emptyFile.getName().indexOf('_') + 1);
                LOGGER.info("Base file name {} ", baseFile);

                emptyFiles.add(baseFile.replace(".csv", ".xml"));

                LOGGER.info("Adding empty file to the list with file name {} ", emptyFile.getName());
            } else {

            	recordsUpdated = printDetailsMailRepository.updatePrintDetailsStatus(stepResult.getPrintDetails(), isCOR);

                printVendorJdbcRepository.updateFilerDemographicStatus(stepResult.getFilerDemographicList());
            }
        } else {
            LOGGER.warn("Received invalid confirmation file.");
        }

        final File processedFile = stepResult.getProcessedFile();

        final BatchInfoDto batchInfoDto = getBatchInfoDto(processedFile, stepResult, recordsUpdated);

        saveExceptionReport(items, batchInfoDto);

        if (isCOR) {
            printDetailsMailRepository.updatePrintDetailsForEmptyFiles(emptyFiles);
            updateFilerDemographicStatusForEmptyFiles(emptyFiles);
        }

        LOGGER.info("Renaming processed file to {}", processedFile.getAbsolutePath() + ".bkp");
        final boolean isBackupDone = processedFile.renameTo(new File(processedFile.getAbsolutePath() + ".bkp"));

        if (isBackupDone) {
            LOGGER.debug("Backup completed successfully");
        }

        LOGGER.info("#########    Batch execution completed successfully   #########");

    }

    private void updateFilerDemographicStatusForEmptyFiles(List<String> emptyFiles) {
        if (emptyFiles.isEmpty()) {
            return;
        }

        final String xmlFileName = emptyFiles.get(0);
        final List<FilerDemographic> filerDemographicList = printDetailsMailRepository.getRequiredDetailsFromPrintDetails(xmlFileName);
        printVendorJdbcRepository.updateFilerDemographicStatus(filerDemographicList);
    }

    BatchInfoDto getBatchInfoDto(File processedFile, StepResult stepResult, int[] recordsUpdated) {
    	int failCount = 0;
    	int passCount = 0;
        final BatchInfoDto batchInfoDto = new BatchInfoDto();
        batchInfoDto.setReceiveDt(new Date());

        final int batchId = batchInfoService.save(batchInfoDto);
        batchInfoDto.setBatchId(batchId);
        batchInfoDto.setBatchType(((String) stepExecution.getJobExecution().getExecutionContext().get(BatchExportConstants.StepExecutionContextKeys.PROCESS_RECEIPT_FILE_TYPE)).toUpperCase());
        batchInfoDto.setFileName(processedFile.getName());
        batchInfoDto.setTotalCount(stepResult.getTotalRecords());
        batchInfoDto.setTotalPass(stepResult.getSuccessRecords());
        batchInfoDto.setTotalFail(stepResult.getFailedRecords());
        batchInfoDto.setRequisitionId(stepResult.getRequisitionId());
        if(!isCOR()){
        	if(recordsUpdated != null){
        		for (int i = 0; i < recordsUpdated.length; i++) {
            		if (0 == recordsUpdated[i]) {
            			failCount++;
            		}else{
            			passCount++;
            		}
            	}
            	batchInfoDto.setTotalCount(passCount+failCount);
                batchInfoDto.setTotalPass(passCount);
                batchInfoDto.setTotalFail(failCount);
        	}
        }

        batchInfoService.updatePrintAndProcess(batchInfoDto);

        return batchInfoDto;
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

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    boolean isCOR() {

        final String fileType = (String) stepExecution.getJobExecution().getExecutionContext()
                .get(BatchExportConstants.StepExecutionContextKeys.PROCESS_RECEIPT_FILE_TYPE);

        return "COR".equalsIgnoreCase(fileType);
    }
}
