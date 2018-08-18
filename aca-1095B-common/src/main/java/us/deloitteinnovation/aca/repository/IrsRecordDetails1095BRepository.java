package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095B;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095BPK;

import java.util.Date;
import java.util.List;

/**
 */
@Transactional(propagation = Propagation.REQUIRED)
public interface IrsRecordDetails1095BRepository extends CrudRepository<IrsRecordDetails1095B, IrsRecordDetails1095BPK> {

    @Transactional
    @Modifying
    void deleteBySourceCode(String sourceCode);

    @Transactional
    @Modifying
    @Query("UPDATE IrsRecordDetails1095B SET RECORD_STATUS = :status WHERE SOURCE_CD = :sourceCd AND SOURCE_UNIQUE_ID = :sourceUniqueId")
    void updateStatus(@Param("sourceCd") String sourceCode, @Param("sourceUniqueId") Long sourceUniqueId, @Param("status") String status);

    @Transactional
    @Modifying
    @Query("UPDATE IrsRecordDetails1095B SET RECORD_STATUS = :status WHERE TRANSMISSION_ID = :transmissionId AND SUBMISSION_ID = :submissionId AND RECORD_ID = :recordId")
    void updateStatus(@Param("transmissionId") Integer transmissionId, @Param("submissionId") Integer submissionId, @Param("recordId") Integer recordId, @Param("status") String status);

    @Transactional
    @Query("select t from  IrsRecordDetails1095B t where t.id.transmissionId = :transmissionId")
    List<IrsRecordDetails1095B> getRecordsDetailsTransactionID(@Param("transmissionId") Integer transmissionId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE IrsRecordDetails1095B SET RECORD_STATUS = :status,UPDATED_BY= :updatedBy, UPDATED_DATE =:updatedDate WHERE TRANSMISSION_ID = :transmissionId AND SUBMISSION_ID = :submissionId AND RECORD_ID = :recordId")
    void updateStatus(@Param("transmissionId") Integer transmissionId, @Param("submissionId") Integer submissionId, @Param("recordId") Integer recordId, @Param("status") String status, @Param("updatedDate") Date updatedDate, @Param("updatedBy") String updatedBy);


    @Async
    @Modifying(clearAutomatically = true)
    @Query("UPDATE IrsRecordDetails1095B SET RECORD_STATUS = :status,UPDATED_BY= :updatedBy, UPDATED_DATE =:updatedDate WHERE SOURCE_CD = :sourceCd AND SOURCE_UNIQUE_ID = :sourceUniqueId")
    void updateStatus(@Param("sourceCd") String sourceCode, @Param("sourceUniqueId") Long sourceUniqueId, @Param("status") String status, @Param("updatedDate") Date updatedDate, @Param("updatedBy") String updatedBy);

    //TODO: The test case for this method fails when we do not select/include the columns whose nullable property is set to false. How should we refactor this to select only required columns and ignore the rest?
    @Transactional
    @Query(value = "SELECT " +
            "TOP 1 * FROM IRS_RECORD_DETAILS_1095B " +
            "WHERE " +
            "SOURCE_CD = :sourceCd " +
            "AND " +
            "SOURCE_UNIQUE_ID = :sourceUniqueId " +
            "AND " +
            "RECORD_STATUS IN ('AC', 'ER') " +
            "ORDER BY " +
            "TRANSMISSION_ID DESC", nativeQuery = true)
    IrsRecordDetails1095B fetchOriginalTransmissionDetailsForCorrections(@Param("sourceCd")String sourceCd, @Param("sourceUniqueId")long sourceUniqueId);

    @Transactional
    @Query(value = "SELECT " +
            "TOP 1 * FROM IRS_RECORD_DETAILS_1095B " +
            "WHERE " +
            "SOURCE_CD = :sourceCd " +
            "AND " +
            "SOURCE_UNIQUE_ID = :sourceUniqueId " +
            "AND " +
            "RECORD_STATUS IN ('RC', 'RR') " +
            "ORDER BY " +
            "TRANSMISSION_ID DESC", nativeQuery = true)
    IrsRecordDetails1095B fetchOriginalTransmissionDetailsForReplacements(@Param("sourceCd")String sourceCd, @Param("sourceUniqueId")long sourceUniqueId);
}
