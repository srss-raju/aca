package us.deloitteinnovation.aca.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;
import us.deloitteinnovation.aca.entity.IrsTransmissionDetails;

import java.sql.Timestamp;

import static org.junit.Assert.assertNotNull;

/**
 */
public class IrsTransmissionDetailsRepositoryIntegTest extends AbstractCommonTestConfig {

    @Autowired
    IrsTransmissionDetailsRepository irsTransmissionDetailsRepository ;

    @Test
    public void saveMinimal() throws Exception {
        saveAndAssert(irsTransmissionDetailsRepository, "O", 2015, "filename.xml", "XXXXXXXXX", "irsTransmissionSaveMin") ;
    }

    public static IrsTransmissionDetails saveAndAssert(IrsTransmissionDetailsRepository repo, String transmissionType, int year, String filename, String sourceCode, String updatedBy) {
        IrsTransmissionDetails details = new IrsTransmissionDetails() ;
        details.setSourceCd(sourceCode);
        details.setTaxYear(year);
        details.setTransferDate(new Timestamp(System.currentTimeMillis()));
        details.setTransmissionFileName(filename);
        details.setTransmissionFormType("B");
        details.setTransmissionTypeCd(transmissionType);
        details.setTransmissionTypeCd("O");
        details.setTransmissionDate(new Timestamp(System.currentTimeMillis()));
        details.setTransmissionAckDate(new Timestamp(System.currentTimeMillis()));
        details.setUpdatedBy(updatedBy);
        details.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        repo.save(details) ;
        assertNotNull(details.getTransmissionId()) ;
        assertNotNull(repo.getLatestTransactionId("XXXXXXXXX", "O"));
        return details ;

    }
}