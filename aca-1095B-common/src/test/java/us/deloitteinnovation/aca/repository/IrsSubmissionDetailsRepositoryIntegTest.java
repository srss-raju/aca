package us.deloitteinnovation.aca.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;
import us.deloitteinnovation.aca.entity.IrsSubmissionDetails;
import us.deloitteinnovation.aca.entity.IrsSubmissionDetailsPK;
import us.deloitteinnovation.aca.entity.IrsTransmissionDetails;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 */
public class IrsSubmissionDetailsRepositoryIntegTest extends AbstractCommonTestConfig {

    @Autowired
    IrsTransmissionDetailsRepository irsTransmissionDetailsRepository ;

    @Autowired
    IrsSubmissionDetailsRepository irsSubmissionDetailsRepository ;

    @Test
    public void saveMinimal() throws Exception {
        IrsTransmissionDetails transmission = IrsTransmissionDetailsRepositoryIntegTest.saveAndAssert(irsTransmissionDetailsRepository, "O", 2015, "abcdef.xml", "GGGGGGG", "submissionSaveMin") ;
        saveAndAssert(irsSubmissionDetailsRepository, transmission.getTransmissionId(), "irsSubmissionSaveMin") ;
    }

    public static IrsSubmissionDetails saveAndAssert(IrsSubmissionDetailsRepository repo, Integer transmissionId, String updatedBy) {
        IrsSubmissionDetailsPK id = new IrsSubmissionDetailsPK() ;
        id.setTransmissionId(transmissionId);
        id.setSubmissionId(1);
        IrsSubmissionDetails details = new IrsSubmissionDetails() ;
        //details.setId(1);
        details.setUpdatedBy(updatedBy);
        details.setUpdatedDate(new Date());
        repo.save(details) ;
        assertNotNull(details.getIrsSubmissionDetailsId()) ;
        return details ;
    }
}