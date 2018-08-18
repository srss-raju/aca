package us.deloitteinnovation.aca.batch.listener;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.constants.FileIngestionConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.ExceptionReportDto;
import us.deloitteinnovation.aca.batch.service.ExceptionReportService;
import us.deloitteinnovation.aca.entity.FilerDemographicStagingEntity;

/**
 * Created by bhchaganti on 9/21/2016.
 */
public class FilerVerificationSkipListener implements SkipListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(us.deloitteinnovation.aca.batch.listener.FilerVerificationSkipListener.class);
    @Autowired
    ExceptionReportService exceptionReportService;
    @Autowired
    BatchInfoDto batchInfoDto;
    StringBuilder errorMessageSb;

    @Override
    public void onSkipInRead(Throwable throwable) {
        errorMessageSb = new StringBuilder();

        if (throwable instanceof FlatFileParseException) {
            FlatFileParseException flatFileParseException = (FlatFileParseException) throwable;

            errorMessageSb.append("Error occurred while processing line#: " + Integer.valueOf(flatFileParseException.getLineNumber() - FileIngestionConstants.LINES_TO_SKIP));
            errorMessageSb.append("\nFaulty line: " + flatFileParseException.getInput());

            ExceptionReportDto exceptionReportDto = new ExceptionReportDto();
            exceptionReportDto.setBatchInfo(this.batchInfoDto);
            exceptionReportDto.setRowNumber(flatFileParseException.getLineNumber() - FileIngestionConstants.LINES_TO_SKIP);
            exceptionReportDto.setExDetails(errorMessageSb.toString());
            exceptionReportService.save(exceptionReportDto);

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Skipped erroneous line#: " + flatFileParseException.getLineNumber());
            }
        } else if (throwable instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) throwable;
            errorMessageSb.append("Constraint Violation occurred");
            errorMessageSb.append("Violation message:" + constraintViolationException.getMessage());

            ExceptionReportDto exceptionReportDto = new ExceptionReportDto();
            exceptionReportDto.setBatchInfo(this.batchInfoDto);
            exceptionReportDto.setExDetails(errorMessageSb.toString());
            exceptionReportService.save(exceptionReportDto);

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Constraint violation, skipped reading this record");
            }
        } else if (throwable instanceof Exception) {

            Exception exception = (Exception) throwable;
            errorMessageSb.append("Skipping reading this record");
            errorMessageSb.append("Message: " + exception.getMessage());

            ExceptionReportDto exceptionReportDto = new ExceptionReportDto();
            exceptionReportDto.setBatchInfo(this.batchInfoDto);
            exceptionReportDto.setExDetails(errorMessageSb.toString());
            exceptionReportService.save(exceptionReportDto);

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Exception occurred, skipped reading this record");
            }
        }

    }

    @Override
    public void onSkipInWrite(Object o, Throwable throwable) {

        FilerDemographicStagingEntity skippedEntity = (FilerDemographicStagingEntity) o;
        errorMessageSb = new StringBuilder();
        Exception exception = (Exception) throwable;
        errorMessageSb.append("Skipped writing record " + skippedEntity.getSourceUniqueId());
        errorMessageSb.append("Exception Message: " + exception.getMessage());

        ExceptionReportDto exceptionReportDto = new ExceptionReportDto();
        exceptionReportDto.setBatchInfo(this.batchInfoDto);
        exceptionReportDto.setExDetails(errorMessageSb.toString());
        exceptionReportDto.setSourceUniqueId(skippedEntity.getSourceUniqueId());
        exceptionReportService.save(exceptionReportDto);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Skipped writing: " + skippedEntity.getSourceUniqueId());
        }

    }

    @Override
    public void onSkipInProcess(Object o, Throwable throwable) {

    }
}
