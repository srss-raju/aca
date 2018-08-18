package us.deloitteinnovation.aca.batch.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.ExceptionReportDto;
import us.deloitteinnovation.aca.batch.service.ExceptionReportService;
import us.deloitteinnovation.aca.batch.service.FileImportMailerService;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;
import us.deloitteinnovation.profile.ProfileProperties;

import java.util.List;

/**
 * Implementation class for sending email notifications
 */
@Component
public class FileImportMailerServiceImpl implements FileImportMailerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileImportMailerServiceImpl.class);


    @Autowired
    private MailSender javaMailSender;

    @Autowired
    private ProfileProperties profileProperties;

    @Autowired
    private ExceptionReportService exceptionReportService;

    @Override
    public void sendMail(BatchInfoDto batchInfoDto, JobExecution jobExecution) {

        final String subject = profileProperties.getProperty(BatchConstants.MAIL_SUBJECT);
        final String emailTo = profileProperties.getProperty(BatchConstants.MAIL_TO);
        if (StringUtils.isBlank(emailTo)) {
            LOGGER.warn(String.format("No to addresses found for active profile." +
                    "  Email with subject: \"%s\" will not be sent.", subject));
            return;
        }
        List<ExceptionReportDto> exceptionReportDtoList = this.exceptionReportService.getExceptionReport(batchInfoDto);
        SimpleMailMessage message = new SimpleMailMessage();
        String[] emailToArray = emailTo.split(",");
        message.setTo(emailToArray);
        message.setSubject(subject);
        message.setFrom(profileProperties.getProperty(BatchConstants.MAIL_FROM));

        StringBuffer errorMessage = new StringBuffer();
        errorMessage.append(ErrorMessageConstants.BATCH_FAIL_ERROR_MESSAGE);
        if (exceptionReportDtoList.size() > 0) {
            for (ExceptionReportDto exceptionReportDto : exceptionReportDtoList) {
                errorMessage.append(exceptionReportDto.getExDetails() + BatchConstants.NEW_LINE_STR);
            }
        } else {
            List<Throwable> failureExceptions = jobExecution.getAllFailureExceptions();
            for (Throwable throwable : failureExceptions) {
                errorMessage.append(ExceptionUtils.getRootCauseMessage(throwable) + BatchConstants.NEW_LINE_STR);
            }
        }
        message.setText(errorMessage.toString());
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Email message: " + errorMessage);
        }
        try {
            javaMailSender.send(message);
        } catch (Exception ex) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Could not send email with error");
            }
        }
    }
}
