package us.deloitteinnovation.aca.batch.export.step3;

import java.util.List;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.export.ExportJob1094Repository;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.batch.export.TransmissionIdStack;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;
import us.deloitteinnovation.aca.model.SourceSystemConfig;


/**
 * Load Filer 1095B and Payer 1094B objects by EIN for collation.  The EIN number should be stored within the JobContext
 * using key FORM_1094_EIN during Step 2.
 */
public class Step3PrintVendorForm109495Reader implements ItemReader<Step3Form109495Pairing> {
    @Autowired
    ExportJob1094Repository       exportJob1094Repository;
    @Autowired
    ExportJob1095Repository exportJobRepository;
    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService;
    @Autowired
    TransmissionIdStack transmissionIdStack;
    /**
     * Flag to determine when the read() method should stop reading elements.
     */
    boolean readStepComplete = false;

    StepExecution stepExecution;

    @Override
    public Step3Form109495Pairing read() throws Exception {
        // If our read step is complete, stop now
        if (readStepComplete)
            return null;

        List<Step2Form1094Dto> form1094Data=(List<Step2Form1094Dto>)stepExecution.getJobExecution().getExecutionContext().get("FORM1094BList");
        Step3Form109495Pairing dto = new Step3Form109495Pairing();
        dto.setStep2Form1094bDto(form1094Data.get(0));
        dto.setStep1Form1095bDtoList((List<Step1Form1095Dto>) stepExecution.getJobExecution().getExecutionContext().get("FORM1095BList"));

        // Since there is only 1 Form 1094B per read step, set as complete
        readStepComplete = true;
        return dto;
    } 


    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution ;
    }
}
