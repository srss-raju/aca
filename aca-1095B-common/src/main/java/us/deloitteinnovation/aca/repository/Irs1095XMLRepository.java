package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.Irs1095XML;
import us.deloitteinnovation.aca.entity.Irs1095XMLPK;

import java.util.Date;
import java.util.List;

;

/**
 * Created by bhchaganti on 4/4/2016.
 */
@Transactional
public interface Irs1095XMLRepository extends CrudRepository<Irs1095XML,Irs1095XMLPK>
{
    @Transactional
    @Modifying
    @Query(value = "UPDATE dbo.IRS_1095_XML SET IRS_TRANSMISSION_STATUS_CD = :irsTransmissionStatusCd, UPDATED_BY= :updatedBy , UPDATED_DATE = :updatedDate " +
            "WHERE SOURCE_CD = :sourceCd and SOURCE_UNIQUE_ID = :sourceUniqueId AND IRS_TRANSMISSION_STATUS_CD = :jobType", nativeQuery = true)
    void updateStatus( @Param("sourceCd") String sourceCd,
                       @Param("sourceUniqueId") Long suid,
                       @Param("jobType")String jobType,
                       @Param("irsTransmissionStatusCd") String irsTransmissionStatusCd,
                       @Param("updatedBy") String updatedBy,
                       @Param("updatedDate") Date date);

    @Transactional
    @Query(value = "select x.* from dbo.irs_1095_xml x, dbo.source_system_config ssc \n" +
            "  where x.source_cd = ssc.source_cd and ssc.provider_identification_number = :ein", nativeQuery = true)
    List<Irs1095XML> findByEin(@Param("ein") String ein);

    @Transactional
    @Query(value = "SELECT * FROM " +
            "dbo.IRS_1095_XML irsxml " +
            "join " +
            "dbo.IRS_RECORD_DETAILS_1095B rdetails " +
            "on(irsxml.source_unique_id = rdetails.source_unique_id and irsxml.SOURCE_CD = rdetails.SOURCE_CD) " +
            "WHERE irsxml.IRS_TRANSMISSION_STATUS_CD = :tscd " +
            "AND rdetails.TRANSMISSION_ID = :ctid AND irsxml.SOURCE_CD = :scd", nativeQuery = true)
    List<Irs1095XML> findBySourceCd(@Param("scd") String sourceCd, @Param("ctid")Integer currentTansmissionId, @Param("tscd") String transmissionStatusCd);


    @Transactional
    @Modifying
    @Query("UPDATE Irs1095XML SET  IRS_TRANSMISSION_STATUS_CD = :irsTransmissionStatusCd," +
            "UPDATED_DATE = :updatedDate, UPDATED_BY = :updatedBy where SOURCE_UNIQUE_ID = :sourceUniqueId and SOURCE_CD = :sourceCd" )
    void updateCorrectionStatus(@Param("sourceUniqueId") Long sourceUniqueId,
                      @Param("sourceCd") String sourceCd,
                      @Param("irsTransmissionStatusCd") String irsTransmissionStatusCd,
                      @Param("updatedDate") Date updatedDate,
                      @Param("updatedBy") String updatedBy);


    @Transactional
    @Modifying
    @Query(value ="DELETE FROM dbo.irs_1095_xml WHERE SOURCE_CD = :sourceCd AND SOURCE_UNIQUE_ID = :sourceUniqueId", nativeQuery = true)
    void clearAll(@Param("sourceCd")String sourceCd,
                  @Param("sourceUniqueId") Long sourceUniqueId);

    @Transactional
    @Query(value="SELECT fd.source_unique_id, fd.source_cd, irsxml.* " +
            "FROM " +
            "filer_demographics fd " +
            "join " +
            "irs_1095_XML irsxml " +
            "ON (fd.source_unique_id = irsxml.source_unique_id and fd.source_cd = irsxml.source_cd) " +
            "WHERE " +
            "fd.irs_transmission_status_cd = 'RR' " +
            "AND " +
            "irsxml.irs_transmission_status_cd = :statusFromTransmissionCode " +
            "AND " +
            "irsxml.SOURCE_CD = :sourceCd " +
            "AND " +
            "irsxml.SOURCE_UNIQUE_ID = :sourceUniqueId",
            nativeQuery = true)
    Irs1095XML findByTransmissionStatusCode(@Param("sourceUniqueId")long sourceUniqueId,
                                            @Param("sourceCd")String sourceCd,
                                            @Param("statusFromTransmissionCode")String statusFromTransmissionCode);

    @Transactional
    @Query(value="SELECT * FROM dbo.IRS_1095_XML WHERE SOURCE_CD = :scd AND IRS_TRANSMISSION_STATUS_CD = :tscd", nativeQuery = true)
    List<Irs1095XML> findBySourceCdForOriginals(@Param("scd")String sourceCd, @Param("tscd")String irsTransmissionStatusCd);

    Long countById_SourceCdAndId_TaxYear(String sourceCd, int taxYear);
}
