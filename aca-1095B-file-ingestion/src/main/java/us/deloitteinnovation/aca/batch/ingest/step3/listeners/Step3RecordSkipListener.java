package us.deloitteinnovation.aca.batch.ingest.step3.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.ingest.step3.Step3DataValidationException;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.BusinessValidationRuleDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.services.Step3RecordValidationService;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tthakore on 11/24/2015.
 */
public class Step3RecordSkipListener implements SkipListener {


    private static Logger logger = LoggerFactory.getLogger(Step3RecordSkipListener.class);
    @Autowired
    Step3RecordValidationService step3RecordValidationService;

    @Override
    public void onSkipInRead(Throwable throwable) {
        logger.error("Error in reading record so this record will be skipped.----->", throwable) ;
        markRecordAsRejected(((Step3DataValidationException)throwable).getParent());
    }

    @Override
    public void onSkipInWrite(Object o, Throwable throwable) {
        logger.error("Error in writing  record so this record will be skipped.----->", throwable) ;
       // markRecordAsRejected(((Step3DataValidationException) throwable).getParent());
    }

    @Override
    public void onSkipInProcess(Object o, Throwable throwable) {
        logger.error("Error in processing  file so this record will be skipped.----->", throwable) ;
        markRecordAsRejected(((Step3DataValidationException) throwable).getParent());
    }


    public void markRecordAsRejected(List<Step3FilerDataDto> step3FilerDataDtos)
    {
            List<BusinessValidationRuleDto> businessRuleList = new ArrayList<>();
            for(Step3FilerDataDto item : step3FilerDataDtos)
            {
                item.setRecordStatus(CommonDataConstants.VALIDATION_RULE_FAILED);
                item.setBDMessage("Exception while in CRV. updated status from Skip listener after skipping record.");
                businessRuleList.add(getBusinessRuleDTO(item));
            }
        step3RecordValidationService.bulkInsertBusinessLogs(businessRuleList);
    }

    private BusinessValidationRuleDto getBusinessRuleDTO(Step3FilerDataDto step3FilerDataDto) {
        /*setup current date and time*/
        BusinessValidationRuleDto businessValidationRuleDto = new BusinessValidationRuleDto();
        businessValidationRuleDto.setSourceCd(step3FilerDataDto.getSourceCd());
        businessValidationRuleDto.setSourceUniqueId(Long.valueOf(step3FilerDataDto.getSourceUniqueId()));
        businessValidationRuleDto.setBatchId(step3FilerDataDto.getBatchInfo().getBatchId());
        businessValidationRuleDto.setDob(step3FilerDataDto.getRecipientDob());
        businessValidationRuleDto.setBusinessDecision(step3FilerDataDto.getRecordStatus());
        businessValidationRuleDto.setBusinessRule(step3FilerDataDto.getBDMessage());
        businessValidationRuleDto.setTaxYear(step3FilerDataDto.getId().getTaxYear());
        businessValidationRuleDto.setRowNumber(Integer.valueOf(step3FilerDataDto.getRowNumber()));
        businessValidationRuleDto.setUpdatedBy(step3FilerDataDto.getUpdatedBy());
        businessValidationRuleDto.setUpdatedDate(step3FilerDataDto.getUpdatedDt());
        businessValidationRuleDto.setCorrectionCode(step3FilerDataDto.getCorrection());
        return businessValidationRuleDto;
    }
}
