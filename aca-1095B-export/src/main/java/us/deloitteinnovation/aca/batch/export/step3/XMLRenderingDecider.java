package us.deloitteinnovation.aca.batch.export.step3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.export.ExportJob1094Repository;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.batch.export.TransmissionIdStack;
import us.deloitteinnovation.aca.batch.service.TransmissionIdReaderService;
import us.deloitteinnovation.aca.entity.BatchExportEntityConstants;
import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.deloitteinnovation.aca.repository.Irs1095XMLRepository;
import us.deloitteinnovation.aca.util.StaticTimer;

import java.util.List;


/**
 * Created by bhchaganti on 5/11/2016.
 */
public class XMLRenderingDecider implements JobExecutionDecider {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLRenderingDecider.class);

    @Autowired
    ExportJob1094Repository exportJob1094Repository;
    @Autowired
    ExportJob1095Repository exportJobRepository;
    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService;
    @Autowired
    TransmissionIdStack transmissionIdStack;
    @Autowired
    TransmissionIdReaderService transmissionIdReaderService;
    @Autowired
    Irs1095XMLRepository irs1095XMLRepository;

    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

        LOGGER.debug("XMLRenderingDecider started.");
        StaticTimer.start();
        String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext());
        Integer year = ExportUtil.getYear(stepExecution.getJobExecution().getExecutionContext());
        SourceSystemConfig config = sourceSystemConfigDataService.getByState(state, year);
        String jobType = BatchExportConstants.getStatusFromTransmissionCode(BatchExportConstants.getJobTypeLetter(stepExecution));

        if (null == state || null == year || state.length() == 0) {
            LOGGER.error(String.format("Invalid combination of state code %s and tax year %s", state, year));
            return FlowExecutionStatus.FAILED;
        }

        if (jobType.equals(BatchExportEntityConstants.FilerXmlStatus.REPLACE)) {
            if (transmissionIdStack == null || transmissionIdStack.isStackEmpty()) {
                List<Integer> transmissionIds = transmissionIdReaderService.getTransmissionIds(config.getSourceCd(), jobType);
                transmissionIdStack.buildStack(transmissionIds);
            }
        }

        long form1095bCount = irs1095XMLRepository.countById_SourceCdAndId_TaxYear(config.getSourceCd(), year);
        LOGGER.debug(String.format("%d 1095B forms found.", form1095bCount));

        StaticTimer.stop(XMLRenderingDecider.class);
        if (form1095bCount == 0) {
            return FlowExecutionStatus.STOPPED;
        } else
            return FlowExecutionStatus.COMPLETED;
    }
}
