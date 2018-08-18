package us.deloitteinnovation.aca.batch.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import us.deloitteinnovation.aca.batch.constants.FileIngestionConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.ExceptionReportDto;
import us.deloitteinnovation.aca.batch.service.ExceptionReportService;
import us.deloitteinnovation.aca.batch.service.FileImportMailerService;

/**
 * <p>Specify the skip policy for erroneous records</p>
 */
public class FileVerificationSkipper implements SkipPolicy {

    private final static Logger LOGGER = LoggerFactory.getLogger(us.deloitteinnovation.aca.batch.exception.FileVerificationSkipper.class);
    @Autowired
    ExceptionReportService exceptionReportService;
    @Autowired
    BatchInfoDto batchInfoDto;
    @Autowired
    FileImportMailerService fileImportMailerService;
    @Autowired
    Environment env;

    @Override
    public boolean shouldSkip(Throwable throwable, int i) throws SkipLimitExceededException {

        StringBuilder errorMessageSb = new StringBuilder();

        if (throwable instanceof FlatFileParseException) {
            FlatFileParseException flatFileParseException = (FlatFileParseException) throwable;
            errorMessageSb.append(env.getProperty("faulty.line.error"));
            ExceptionReportDto exceptionReportDto = new ExceptionReportDto();
            exceptionReportDto.setBatchInfo(this.batchInfoDto);
            exceptionReportDto.setRowNumber(Integer.valueOf(flatFileParseException.getLineNumber() - FileIngestionConstants.LINES_TO_SKIP));
            exceptionReportDto.setExDetails(errorMessageSb.toString());
            exceptionReportService.save(exceptionReportDto);

            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Skipped erroneous line#: " + Integer.valueOf(flatFileParseException.getLineNumber() - FileIngestionConstants.LINES_TO_SKIP));
            }
            return true;
        } else if (throwable instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) throwable;
            errorMessageSb.append(env.getProperty("faulty.line.error"));
            ExceptionReportDto exceptionReportDto = new ExceptionReportDto();
            exceptionReportDto.setBatchInfo(this.batchInfoDto);
            exceptionReportDto.setExDetails(errorMessageSb.toString());
            exceptionReportService.save(exceptionReportDto);

            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Constraint violation: " + constraintViolationException.getMessage());
            }
            return true;
        } else if (throwable instanceof Step2VerifyFilerException) {

            Step2VerifyFilerException exception = (Step2VerifyFilerException) throwable;
            errorMessageSb.append(env.getProperty("faulty.line.error"));
            ExceptionReportDto exceptionReportDto = new ExceptionReportDto();
            exceptionReportDto.setBatchInfo(this.batchInfoDto);
            exceptionReportDto.setRowNumber(exception.getExceptionRowNumber());
            exceptionReportDto.setSourceUniqueId(exception.getSourceUniqueId());
            exceptionReportDto.setExDetails(errorMessageSb.toString());
            exceptionReportService.save(exceptionReportDto);

            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Exception occurred, so skipping this record " + exception.getLocalizedMessage());
            }
            return true;
        } else {
            return false;
        }
    }
}
