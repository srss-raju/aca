package us.deloitteinnovation.aca.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.AbstractCommonTestConfig;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095B;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095BPK;
import us.deloitteinnovation.aca.entity.IrsTransmissionDetails;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 */
public class IrsRecordDetails1095BRepositoryIntegTest extends AbstractCommonTestConfig {

    @Autowired
    IrsRecordDetails1095BRepository irsRecordDetails1095BRepository ;

    @Autowired
    IrsTransmissionDetailsRepository irsTransmissionDetailsRepository ;

    @Test
    public void saveMinimal() throws Exception {
        String updatedBy = "irsRecordDetailsSaveMin" ;
        IrsTransmissionDetails transmission = IrsTransmissionDetailsRepositoryIntegTest.saveAndAssert(irsTransmissionDetailsRepository, "O", 2015, "abcdef.xml", "GGGGGGG", updatedBy) ;
        IrsRecordDetails1095BPK id = new IrsRecordDetails1095BPK() ;
        id.setTransmissionId(transmission.getTransmissionId());
        id.setSubmissionId(1);
        id.setRecordId(1);
        IrsRecordDetails1095B recordDetails = saveAndAssert(irsRecordDetails1095BRepository, transmission.getTransmissionId(), 1, 1, 12L, "AR00000", 123456L, updatedBy) ;
        IrsRecordDetails1095BPK id2 = new IrsRecordDetails1095BPK() ;
        id2.setRecordId(id.getRecordId());
        id2.setSubmissionId(id.getSubmissionId());
        id2.setTransmissionId(transmission.getTransmissionId());
        IrsRecordDetails1095B recordDetails2 = irsRecordDetails1095BRepository.findOne(id2) ;
        assertEquals(recordDetails.getUpdatedBy(), recordDetails2.getUpdatedBy()) ;
        IrsRecordDetails1095B recordDetails3 = irsRecordDetails1095BRepository.fetchOriginalTransmissionDetailsForCorrections("AR00000", 123456L);
        assertNotNull(recordDetails3);
        assertEquals(recordDetails.getId().getTransmissionId(), recordDetails3.getId().getTransmissionId());
    }

    public static IrsRecordDetails1095B saveAndAssert(IrsRecordDetails1095BRepository repo, Integer transmissionId,
                                                      int submissionId, int recordId, long filerDemoSeq,
                                                      String sourceCd, Long sourceUniqueId, String updatedBy) {
        IrsRecordDetails1095BPK id = new IrsRecordDetails1095BPK() ;
        id.setTransmissionId(transmissionId);
        id.setSubmissionId(submissionId);
        id.setRecordId(recordId);
        IrsRecordDetails1095B recordDetails = new IrsRecordDetails1095B() ;
        recordDetails.setId(id);
        recordDetails.setFilerDemoSeq(filerDemoSeq);
        recordDetails.setSourceCode(sourceCd);
        recordDetails.setSourceUniqueId(sourceUniqueId);
        recordDetails.setUpdatedBy(updatedBy);
        recordDetails.setUpdatedDate(new Date());
        recordDetails.setRecordStatus("AC");
        repo.save(recordDetails) ;
        return recordDetails ;
    }
}