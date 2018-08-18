package us.deloitteinnovation.aca.batch.export.steppdf;

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
import org.springframework.stereotype.Component;

import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.profile.ProfileProperties;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;


/**
 * Renders each Form1095BUpstreamDetailType as encapsulated within the list of Form1095bProcessDto objects.
 * Stores the entire Form1095bProcessDto, along with rendered XML to the ExportJob1095Repository.
 *
 * @see Form1095BUpstreamDetailType
 * @see ExportJob1095Repository
 * @see Step1Form1095Dto
 */
@Component
public class ExportPdfWriter implements ItemWriter<ExportPdfDto> {
    private static final Logger LOG = LoggerFactory.getLogger(ExportPdfWriter.class);

    StepExecution stepExecution;

    @Autowired
    ProfileProperties profileProperties;
    
    @Autowired
    BatchInfoService batchInfoService;
    
    BatchInfoDto batchInfo = null;

    
    @Override
    public void write(List<? extends ExportPdfDto> aca1095Forms) throws Exception {
    	String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext());
    	String startDate = (String)stepExecution.getJobExecution().getExecutionContext().get("STARTDATE");
    	String endDate = (String)stepExecution.getJobExecution().getExecutionContext().get("ENDDATE");
    	Long year = (Long)stepExecution.getJobExecution().getExecutionContext().get("YEAR");
    	
    	int size = 0;
    		
    	String outputLocation = null;
    	batchInfo = new BatchInfoDto();
    	batchInfo.setReceiveDt(new Date());
        int batchId = batchInfoService.save(batchInfo);
        LOG.info("Batch Id  ----->>> "+batchId);
        batchInfo.setBatchId(batchId);
    	
    	if("AR".equals(state)){
    		outputLocation = "/opt/ACA1095B-Data/Arkansas/Data/Out/pdf/";
    	}else if("IN".equals(state)){
    		outputLocation = "/opt/ACA1095B-Data/Indiana/Data/Out/pdf/";
    	}else if("LA".equals(state)){
    		outputLocation = "/opt/ACA1095B-Data/Louisiana/Data/Out/pdf/";
    	}
    	
    	// Create directory if not existed
    	File outputDirectory = new File(outputLocation);
		if (!outputDirectory.exists()) {
			outputDirectory.mkdir();
		}
		
    	LOG.info(" --- In ExportPdfWriter ---");
    	ExportPdfDto dto = aca1095Forms.get(0);
    	List<Filer> coveredPersons = null;
    	Filer prevResponsibleVo = null;
    	
    	for(Filer vo:dto.coveredResponsiblePersonList){
    	    if("C".equals(vo.getFilerStatus())){
    	        if(coveredPersons == null){
    	            coveredPersons = new ArrayList<Filer>();
    	            coveredPersons.add(vo);
    	        }else{
    	            coveredPersons.add(vo);
    	        }
    	    }else{
    	        prevResponsibleVo = vo;
    	        coveredPersons = null;
    	        size++;
    	    }
    	    prevResponsibleVo.setCoveredPersonList(coveredPersons);
    	}
    	
    	for(Filer vo:dto.coveredResponsiblePersonList){
    	    GeneratePdf.exportPdf(outputLocation, vo, String.valueOf(year));
    	}
    	
    	batchInfo.setFileName(size+"_"+PdfUtil.returnDate(startDate)+"_"+PdfUtil.returnDate(endDate));
        batchInfo.setReceiveDt(new Date());
        batchInfo.setBatchType(ExportPdfConstants.PDF_BATCH_TYPE);
        batchInfo.setStateCd(state);
        batchInfo.setAgencyCd(ExportUtil.getAgencyCode(state));
        batchInfo.setSystemCd(ExportUtil.getSystemCode(state));
    	batchInfoService.updatePrintAndProcess(batchInfo);
    }
    
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

}
