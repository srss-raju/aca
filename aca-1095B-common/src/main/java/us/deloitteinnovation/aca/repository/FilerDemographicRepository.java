package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.entity.FilerDemographicCP;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;

import java.util.Date;
import java.util.List;

/**
 * Created by tthakore on 9/28/2015.
 */
@Transactional(propagation = Propagation.REQUIRED)
public interface FilerDemographicRepository extends CrudRepository<FilerDemographic, FilerDemographicPK> {

    @Async
    @Query(value = "select t from us.deloitteinnovation.aca.entity.FilerDemographicCP t " +
            " where lower(t.recepientLastName) = lower(:lastName)  " +
            " AND  lower( t.recepientSsn) = lower( :recpssn)" +
            " AND  t.recepientDob = :dob " +
            " AND lower(t.recepientCity) = lower(:city) " +
            " AND lower(t.recepientState) = lower(:state) " +
            " AND lower(t.recepientZip5) = lower(:zipcode) AND SUBSTRING(upper(t.id.sourceCd), 1, 2) = upper(:userSelectedState) AND t.status = 'ACTIVE' AND t.id.taxYear= :taxYear", nativeQuery = false)
    List<FilerDemographicCP> verifyUserInformationSSN(@Param("lastName") String lastName,
                                                      @Param("recpssn") String recpssn,
                                                      @Param("dob") Date dob,
                                                      @Param("city") String city,
                                                      @Param("state") String State,
                                                      @Param("zipcode") String zipcode,
                                                      @Param("userSelectedState") String userSelectedState,
                                                      @Param("taxYear") Integer taxYear);

    @Async
    @Query("select t from us.deloitteinnovation.aca.entity.FilerDemographicCP t " +
            " where lower(t.recepientLastName) = lower(:lastName)  " +
            " AND  lower( t.recepientTin) = lower( :recpTin)" +
            " AND  t.recepientDob = :dob " +
            " AND lower(t.recepientCity) = lower(:city) " +
            " AND lower(t.recepientState) = lower(:state) " +
            " AND lower(t.recepientZip5) = lower(:zipcode) AND SUBSTRING(upper(t.id.sourceCd), 1, 2) = upper(:userSelectedState) AND t.status = 'ACTIVE'  AND t.id.taxYear= :taxYear")
    List<FilerDemographicCP> verifyUserInformationTIN(@Param("lastName") String lastName,
                                                      @Param("recpTin") String recpTin,
                                                      @Param("dob") Date dob,
                                                      @Param("city") String city,
                                                      @Param("state") String State,
                                                      @Param("zipcode") String zipcode,
                                                      @Param("userSelectedState") String userSelectedState,
                                                      @Param("taxYear") Integer taxYear);


    @Async
    @Modifying
    @Query("UPDATE us.deloitteinnovation.aca.entity.FilerDemographicCP " +
            "SET IRS_TRANSMISSION_STATUS_CD = :status, " +
            "UPDATED_BY = :updatedBy, " +
            "UPDATED_DATE = :updatedDate " +
            "WHERE " +
            "SOURCE_CD = :sourceCd " +
            "AND " +
            "SOURCE_UNIQUE_ID = :sourceUniqueId " +
            "AND " +
            "TAX_YEAR = :taxYear")
    void updateIrsStatusCD(@Param("sourceCd") String sourceCode,
                           @Param("sourceUniqueId") Long sourceUniqueId,
                           @Param("taxYear") String taxYear,
                           @Param("updatedBy")String updatedBy,
                           @Param("updatedDate") Date date,
                           @Param("status") String status);

}
