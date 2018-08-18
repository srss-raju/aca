package us.deloitteinnovation.aca.batch.export.step3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.deloitteinnovation.aca.util.StaticTimer;

import java.util.List;

import static us.deloitteinnovation.aca.constants.CommonDataConstants.MAGIC_NUMBER_ZERO;

/**
 * Load Filer 1095B and Payer 1094B objects by EIN for collation.  The EIN number should be stored within the JobContext
 * using key FORM_1094_EIN during Step 2.
 */
public class Step3Form109495Reader implements ItemReader<Step3Form109495Pairing> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Step3Form109495Reader.class);
    private static final int READING_CHUNK_SIZE = 25000;

    @Autowired
    ExportJob1094Repository exportJob1094Repository;
    @Autowired
    ExportJob1095Repository exportJobRepository;
    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService;
    @Autowired
    TransmissionIdStack transmissionIdStack;

    StepExecution stepExecution;
    private int pageNum = MAGIC_NUMBER_ZERO;

    @Override
    public Step3Form109495Pairing read() throws Exception {
        LOGGER.debug("Step3 Reader started.");
        StaticTimer.start();
        String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext());
        Integer year = ExportUtil.getYear(stepExecution.getJobExecution().getExecutionContext());
        SourceSystemConfig config = sourceSystemConfigDataService.getByState(state, year);


        Integer currentTransmissionId = null;

        if(! transmissionIdStack.isStackEmpty()){
            currentTransmissionId = transmissionIdStack.peekTransmissionId();
        }
        List<Step1Form1095Dto> step1Form1095Dtos = exportJobRepository.getForm1095bBySourceCode(config.getSourceCd(), currentTransmissionId, year, stepExecution, pageNum, READING_CHUNK_SIZE);

        // If no more 1095B remaining, return null to end reading
        if (MAGIC_NUMBER_ZERO == step1Form1095Dtos.size()) {
            return null;
        } else {
            pageNum++;
        }
        Step3Form109495Pairing dto = new Step3Form109495Pairing();
        dto.setStep2Form1094bDto(exportJob1094Repository.getForm1094bById(config.getProviderIdentificationNumber()));
        dto.setStep1Form1095bDtoList(step1Form1095Dtos);

        StaticTimer.stop(Step3Form109495Reader.class);
        return dto;
    }


    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution ;
    }
}
