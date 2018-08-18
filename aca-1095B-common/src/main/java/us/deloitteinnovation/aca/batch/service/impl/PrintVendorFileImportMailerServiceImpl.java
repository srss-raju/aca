package us.deloitteinnovation.aca.batch.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.service.PrintVendorExceptionReportService;
import us.deloitteinnovation.aca.batch.service.PrintVendorFileImportMailerService;
import us.deloitteinnovation.aca.constants.ErrorMessageConstants;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;
import us.deloitteinnovation.profile.ProfileProperties;

import java.util.List;

/**
 * Created by bhchaganti on 9/1/2016.
 */
public class PrintVendorFileImportMailerServiceImpl implements PrintVendorFileImportMailerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintVendorFileImportMailerServiceImpl.class);


    @Autowired
    private MailSender javaMailSender;

    @Autowired
    private ProfileProperties profileProperties;

    @Autowired
    private PrintVendorExceptionReportService exceptionReportService;

    @Override
    public void sendMail(BatchInfoDto batchInfoDto, JobExecution jobExecution) {

        final String subject = profileProperties.getProperty(BatchConstants.MAIL_SUBJECT);
        final String emailTo = profileProperties.getProperty(BatchConstants.MAIL_TO);
        if (StringUtils.isBlank(emailTo)) {
            LOGGER.warn(String.format("No to addresses found for active profile." +
                    "  Email with subject: \"%s\" will not be sent.", subject));
            return;
        }
        List<PrintVendorExceptionReportDto> exceptionReportDtoList = this.exceptionReportService.getExceptionReport(batchInfoDto);
        SimpleMailMessage message = new SimpleMailMessage();
        String[] emailToArray = emailTo.split(",");
        message.setTo(emailToArray);
        message.setSubject(subject);
        message.setFrom(profileProperties.getProperty(BatchConstants.MAIL_FROM));

        StringBuffer errorMessage = new StringBuffer();
        errorMessage.append(ErrorMessageConstants.BATCH_FAIL_ERROR_MESSAGE);
        if (exceptionReportDtoList.size() > 0) {
            for (PrintVendorExceptionReportDto exceptionReportDto : exceptionReportDtoList) {
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
        javaMailSender.send(message);

    }
}
