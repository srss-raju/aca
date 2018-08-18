package us.deloitteinnovation.aca.batch.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bhchaganti on 5/16/2016.
 */
@Service("filerCoveredPersonService")
public interface TransmissionIdReaderService {
     List<Integer> getTransmissionIds(String sourceCd, String jobType);
}
