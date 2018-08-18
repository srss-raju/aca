package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.entity.FilerDemographicCW;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;

import java.util.Date;
import java.util.List;

/**
 * Created by ritmukherjee on 11/4/2015.
 */
@Repository
public interface CWFilerDemographicRepository extends CrudRepository<FilerDemographicCW,FilerDemographicPK> {

  /* 1.  select from Filer_Demographic based on sourceUniqueId,sourceCd*/
    @Async
    @Query("select t from FilerDemographicCW t" +
            "  where   (t.id.sourceUniqueId) = (:sourceUniqueId)  "+
            " AND  lower(t.id.sourceCd) = lower(:sourceCd)")
    List<FilerDemographic> getCustomerDetails(
            @Param("sourceUniqueId") long sourceUniqueId,
            @Param("sourceCd") String sourceCd);//

    /*2.  select from Filer_Demographic based on sourceUniqueId,sourceCd*/
    @Async
    @Query("select t from FilerDemographicCW t" +
            "  where   (t.responsiblePersonUniqueId) = (:responsiblePersonUniqueId)")
    List<FilerDemographic> getFormFilerDetails(
            @Param("responsiblePersonUniqueId") long responsiblePersonUniqueId);//




    @Async
    @Query("select t from FilerDemographicCW " +
            " t where  t.recepientSsn= :recepientSsn AND "+
            "  t.id.sourceCd like CONCAT(:recepientState, '%') AND "+
            "  t.id.taxYear = :taxYear")
    List<FilerDemographicCW>  findFilersSSN_TaxYear(
            @Param("recepientSsn") String recepientSsn,
            @Param("recepientState") String recepientState,
            @Param("taxYear") int taxYear);


    @Async
    @Query("select t from FilerDemographicCW " +
            " t where t.recepientTin= :recepientTin AND "+
            "  t.id.sourceCd like CONCAT(:recepientState, '%') AND "+
            "  t.id.taxYear = :taxYear")
    List<FilerDemographicCW>  findFilersTIN_TaxYear(
            @Param("recepientTin") String recepientTin,
            @Param("recepientState") String recepientState,
            @Param("taxYear") int taxYear);

    @Async
    @Query("select t from FilerDemographicCW " +
            " t where t.recepientLastName= :recepientLastName AND "+
            "  t.id.sourceCd like CONCAT(:recepientState, '%') AND "+
            "  t.id.taxYear = :taxYear and t.recepientDob = :recepientDob")
    List<FilerDemographicCW>  findFilersLAST_NAME_DOB_TaxYear(
            @Param("recepientLastName") String recepientLastName,
            @Param("recepientState") String recepientState,
            @Param("recepientDob") Date recepientDob,
            @Param("taxYear") int taxYear);

    @Async
    @Query("select t from FilerDemographicCW " +
            " t where  t.recepientLastName= :recepientLastName AND "+
            "  t.id.sourceCd like CONCAT(:recepientState, '%') AND "+
            "  t.id.taxYear = :taxYear AND t.recepientDob = :recepientDob AND t.recepientFirstName = :recepientFirstName")
    List<FilerDemographicCW>  findFilersFirstName_LastName_DOB_TaxYear(
            @Param("recepientLastName") String recepientLastName,
            @Param("recepientFirstName") String recepientFirstName,
            @Param("recepientState") String recepientState,
            @Param("recepientDob") Date recepientDob,
            @Param("taxYear") int taxYear);

    @Async
    @Query("select t from FilerDemographicCW t" +
            "  where lower(t.id.sourceCd) = lower(:sourceCd) and  (t.responsiblePersonUniqueId) = (select t.responsiblePersonUniqueId from FilerDemographicCW t where   " +
            " (t.id.sourceUniqueId) = (:sourceUniqueId)  AND  t.id.taxYear = :taxYear " +
            " AND  lower(t.id.sourceCd) = lower(:sourceCd)) " +
            "  AND  t.id.taxYear = :taxYear ")
    List<FilerDemographicCW> getFormFilerDetailsAllCovered(
            @Param("sourceUniqueId") long sourceUniqueId,
            @Param("sourceCd") String sourceCd,
            @Param("taxYear") int taxYear);

    @Async
    @Query(value = "select t from FilerDemographicCW t " +
            " where "+
            "  UPPER(t.id.sourceCd) = UPPER(:sourceCd) " +
            "  AND t.id.taxYear= :taxYear AND t.id.sourceUniqueId= :sourceUniqueId ", nativeQuery = false)
    List<FilerDemographicCW> getUserInformationFromFD(
            @Param("sourceUniqueId") long sourceUniqueId,
            @Param("sourceCd") String sourceCd,
            @Param("taxYear") int taxYear);


    /* 1.  select from Filer_Demographic based on sourceUniqueId,sourceCd*/
    @Async
    @Query("select t from FilerDemographicCW t" +
            "  where   (t.id.sourceUniqueId) = (:sourceUniqueId)  "+
            " AND  lower(t.id.sourceCd) = lower(:sourceCd) AND  t.id.taxYear = :taxYear")
    List<FilerDemographicCW> getCustomerDetails(
            @Param("sourceUniqueId") long sourceUniqueId,
            @Param("sourceCd") String sourceCd,
            @Param("taxYear") Integer taxYear);


    @Async
    @Query("select t from FilerDemographicCW t" +
            "  where lower(t.id.sourceCd) = lower(:sourceCd) and  (t.id.sourceUniqueId) = (select t.responsiblePersonUniqueId from FilerDemographic t where   " +
            "(t.id.sourceUniqueId) = (:sourceUniqueId) " +
            " AND  lower(t.id.sourceCd) = lower(:sourceCd)   AND  t.id.taxYear = :taxYear) "+
            " AND (t.id.sourceUniqueId)=t.responsiblePersonUniqueId  AND  t.id.taxYear = :taxYear")
    FilerDemographicCW getResponsibleFiler(
            @Param("sourceUniqueId") long sourceUniqueId,
            @Param("sourceCd") String sourceCd,
            @Param("taxYear") Integer taxYear);






    @Modifying
    @Transactional
    @Query("update FilerDemographicCW pd set " +
            " pd.updatedBy =(:updatedBy) , "  +
            " pd.updatedDt =(:updatedDt), " +
            " pd.formStatus = :formStatus "  +
            "  where   ( pd.id.sourceUniqueId) = (:sourceUniqueId)  "+
            " AND  lower( pd.id.sourceCd) = lower(:sourceCd) and pd.id.taxYear=:taxYear ")
    int updateFormStatus(@Param("sourceUniqueId") long sourceUniqueId,
                          @Param("sourceCd") String sourceCd,
                          @Param("taxYear") Integer taxYear  ,
                          @Param("formStatus") String formStatus,
                          @Param("updatedBy") String updatedBy,
                          @Param("updatedDt") Date updatedDt);


    List<FilerDemographicCW> findByRecepientSsnAndId_TaxYearAndId_SourceCdStartingWith(String recepientSsn, int taxYear, String sourceCd);
    List<FilerDemographicCW> findByresponsiblePersonUniqueIdAndId_SourceCdAndId_TaxYear(long responsiblePersonUniqueId, String sourceCd, int taxYear);
    FilerDemographicCW findByIdAndCorrectionIndicatorIsNull(FilerDemographicPK id);

}
