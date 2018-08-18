package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.IrsTransmissionDetails;

import java.util.List;

/**
 */
@Transactional
public interface IrsTransmissionDetailsRepository extends CrudRepository<IrsTransmissionDetails, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE IrsTransmissionDetails SET TRANSMISSION_ACK_STATUS = :status, TRANSMISSION_ACK_DATE = GETDATE(), UPDATED_DATE = GETDATE(), UPDATED_BY = :updatedBy " +
            "WHERE TRANSMISSION_ID = :transmissionId")
    void updateStatus(@Param("transmissionId") Integer transmissionId, @Param("status") String status, @Param("updatedBy") String updatedBy) ;

    @Transactional
    @Modifying
    @Query("UPDATE IrsTransmissionDetails SET TRANSMISSION_RECEIPT_ID = :receiptId, TRANSMISSION_ACK_STATUS = :status, TRANSMISSION_ACK_DATE = GETDATE(), UPDATED_DATE = GETDATE(), UPDATED_BY = :updatedBy " +
            "WHERE TRANSMISSION_ID = :transmissionId")
    void updateStatus(@Param("transmissionId") Integer transmissionId, @Param("receiptId") String receiptId, @Param("status") String status, @Param("updatedBy") String updatedBy) ;


    @Transactional(readOnly = true)
    IrsTransmissionDetails findByTransmissionFileName(String filename) ;

    @Transactional(readOnly = true)
    List<IrsTransmissionDetails> findByTransmissionReceiptId(String transmissionReceiptId) ;

    @Transactional(readOnly = true)
    @Query(value ="SELECT top 1 TRANSMISSION_ID FROM IRS_TRANSMISSION_DETAILS WHERE SOURCE_CD = :sourceCd AND TRANSMISSION_TYPE_CD = :jobTypeLetter ORDER BY TRANSMISSION_DATE DESC", nativeQuery = true)
    Integer getLatestTransactionId(@Param("sourceCd") String sourceCd, @Param("jobTypeLetter")String jobTypeLetter);

    @Transactional(readOnly = true)
    @Query(value = ";WITH RecQry AS " +
            "( " +
            "    SELECT transmission_id, original_transmission_id " +
            "      FROM irs_submission_details where transmission_id = :ctid" +
            "    UNION ALL " +
            "    SELECT a.transmission_id, a.original_transmission_id " +
            "      FROM irs_submission_details a INNER JOIN RecQry b" +
            "        ON a.transmission_id = b.original_transmission_id " +
            ") " +
            "SELECT distinct tdetails.transmission_receipt_id FROM RecQry rqry " +
            "JOIN " +
            "IRS_TRANSMISSION_DETAILS tdetails " +
            "on(rqry.transmission_id = tdetails.transmission_id) " +
            "where original_transmission_id is null", nativeQuery = true)
        String getOriginalReceiptIdForTransmissionId(@Param("ctid")Integer currentTransmissionId);
        }

