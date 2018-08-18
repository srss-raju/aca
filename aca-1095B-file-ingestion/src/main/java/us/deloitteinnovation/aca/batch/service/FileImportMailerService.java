package us.deloitteinnovation.aca.batch.service;

import org.springframework.batch.core.JobExecution;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;

/**
 * Created by bhchaganti on 9/1/2016.
 */
public interface FileImportMailerService {

    void sendMail(BatchInfoDto batchInfoDto, JobExecution jobExecution);
}
