package us.deloitteinnovation.aca.batch.export.steppdf;


import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.constants.PrintVendorConstants;
import us.deloitteinnovation.aca.model.Filer;

/**
 * RajeshKumar B
 */
public class ExportPdfReader implements ItemReader<ExportPdfDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportPdfReader.class);

    @Autowired
    ExportPdfJdbcRepository exportPdfJdbcRepository;
    StepExecution stepExecution;

    @Override
    public ExportPdfDto read() throws Exception {
        LOGGER.debug("Export PDF Reader");
        ExportPdfDto dto = new ExportPdfDto();
        String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext());
        String startDate = (String)stepExecution.getJobExecution().getExecutionContext().get("STARTDATE");
        String endDate = (String)stepExecution.getJobExecution().getExecutionContext().get("ENDDATE");
        Long year = (Long)stepExecution.getJobExecution().getExecutionContext().get("YEAR");
        int offset  = (int)stepExecution.getJobExecution().getExecutionContext().get("OFFSET");

        List<Filer> responsibleCoveredPersons = exportPdfJdbcRepository.getCoveredPersons(state, String.valueOf(year), startDate, endDate,offset,PrintVendorConstants.RECORDSSIZE);
        if(CollectionUtils.isNotEmpty(responsibleCoveredPersons)){
                dto.setCoveredResponsiblePersonList(responsibleCoveredPersons);
                offset = offset + PrintVendorConstants.RECORDSSIZE;
                stepExecution.getJobExecution().getExecutionContext().put("OFFSET",offset);
        }else{
            return null;  
        }
        return dto;
    }


    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution ;
    }
}

