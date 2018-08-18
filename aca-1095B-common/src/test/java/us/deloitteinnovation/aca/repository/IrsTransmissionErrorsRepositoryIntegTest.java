package us.deloitteinnovation.aca.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;
import us.deloitteinnovation.aca.entity.IrsTransmissionDetails;
import us.deloitteinnovation.aca.entity.IrsTransmissionErrors;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 */
public class IrsTransmissionErrorsRepositoryIntegTest extends AbstractCommonTestConfig {

    @Autowired
    IrsTransmissionDetailsRepository irsTransmissionDetailsRepository ;

    @Autowired
    IrsTransmissionErrorsRepository irsTransmissionErrorsRepository ;

    @Test
    public void saveMinimal() throws Exception {
        String updatedBy = "irsSubmissionSaveMin" ;
        IrsTransmissionDetails transmission = IrsTransmissionDetailsRepositoryIntegTest.saveAndAssert(irsTransmissionDetailsRepository, "O", 2015, "abcdef.xml", "GGGGGGG", updatedBy) ;
        saveAndAssert(irsTransmissionErrorsRepository, transmission.getTransmissionId(), "123456", "CODE", "ERROR!", "THAT ERROR!", "1095B-123456", 1, updatedBy) ;
    }

    /**
     * Helper method to create the minimum data for an Error object.
     * @param repo
     * @param transmissionId
     * @param errorId
     * @param errorMsgCode
     * @param errorMsgText
     * @param errorElementName
     * @param receiptId
     * @param recordId
     * @param updatedBy
     * @return
     */
    public static IrsTransmissionErrors saveAndAssert(IrsTransmissionErrorsRepository repo, Integer transmissionId, String errorId, String errorMsgCode, String errorMsgText,
                                                      String errorElementName, String receiptId, int recordId, String updatedBy) {
        IrsTransmissionErrors error = new IrsTransmissionErrors() ;
        error.setTransmissionId(transmissionId);
        error.setSubmissionId(1);
        error.setRecordId(recordId);
        error.setErrorId(errorId);
        error.setErrorMsgCode(errorMsgCode);
        error.setErrorMsgText(errorMsgText);
        error.setErrorElementName(errorElementName);
        error.setReceiptId(receiptId);
        error.setUpdatedDate(new Date());
        error.setUpdatedBy(updatedBy);
        repo.save(error) ;
        assertNotNull(error.getId()) ;
        assertNotNull(error.getUpdatedDate()) ;
        return error ;
    }
}