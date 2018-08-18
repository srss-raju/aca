package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.IrsSubmissionDetails;
import us.deloitteinnovation.aca.entity.IrsSubmissionDetailsPK;

import java.util.Date;

/**
 */
@Transactional
public interface IrsSubmissionDetailsRepository extends CrudRepository<IrsSubmissionDetails,IrsSubmissionDetailsPK> {


    @Transactional
    @Modifying
    @Query(value = "UPDATE IRS_SUBMISSION_DETAILS SET SUBMISSION_STATUS = :submissionStatus,  UPDATED_DATE = :updatedDate , UPDATED_BY = :updatedBy WHERE SUBMISSION_ID = :submissionID AND TRANSMISSION_ID=:transmissionId" ,nativeQuery = true)
    void updateSubmissionStatus(@Param("transmissionId") Integer transmissionId, @Param("submissionID") Integer submissionID, @Param("submissionStatus") String submissionStatus,@Param("updatedDate") Date updatedDate, @Param("updatedBy") String updatedBy);


}
