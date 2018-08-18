package us.deloitteinnovation.aca.batch.export.steppdf;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

/**
 * Converts Filer Demographics into FORM 1095B AIR7 objects for export to the IRS as XML SOAP message(s).
 */
public class ExportPdfProcessor implements ItemProcessor<ExportPdfDto, ExportPdfDto> {
    StepExecution stepExecution;
    @Override
    public ExportPdfDto process(ExportPdfDto exportPdfDto) throws Exception {
        return exportPdfDto;
    }
    
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
