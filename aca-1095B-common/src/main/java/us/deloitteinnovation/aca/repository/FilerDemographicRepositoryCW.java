package us.deloitteinnovation.aca.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;

import java.util.Date;
import java.util.List;

/**
 * Created by ritmukherjee on 11/4/2015.
 */
@Repository
public interface FilerDemographicRepositoryCW extends CrudRepository<FilerDemographic,FilerDemographicPK> {

  /* 1.  select from Filer_Demographic based on sourceUniqueId,sourceCd*/
  @Async
  @Query("select t from FilerDemographic t" +
          "  where   (t.id.sourceUniqueId) = (:sourceUniqueId)  "+
          " AND  lower(t.id.sourceCd) = lower(:sourceCd)")
  List<FilerDemographic> getCustomerDetails(
          @Param("sourceUniqueId") long sourceUniqueId,
          @Param("sourceCd") String sourceCd);//

  /*2.  select from Filer_Demographic based on sourceUniqueId,sourceCd*/
  @Async
  @Query("select t from FilerDemographic t" +
          "  where   (t.responsiblePersonUniqueId) = (:responsiblePersonUniqueId)")
  List<FilerDemographic> getFormFilerDetails(
          @Param("responsiblePersonUniqueId") long responsiblePersonUniqueId);//




  @Async
  @Query("select t from FilerDemographic t" +
          "  where lower(t.id.sourceCd) = lower(:sourceCd) and  (t.responsiblePersonUniqueId) = (select t.responsiblePersonUniqueId from FilerDemographic t where   " +
          "(t.id.sourceUniqueId) = (:sourceUniqueId) " +
          " AND  lower(t.id.sourceCd) = lower(:sourceCd) " +
          "AND t.id.sourceCd like CONCAT(:stateCd, '%'))" +
          " AND t.id.sourceCd like CONCAT(:stateCd, '%') ")
  List<FilerDemographic> getFormFilerDetailsAllCovered(
          @Param("sourceUniqueId") long sourceUniqueId,
          @Param("sourceCd") String sourceCd,
          @Param("stateCd") String stateCd);//


  @Async
  @Query("select t from FilerDemographic t" +
          "  where lower(t.id.sourceCd) = lower(:sourceCd) and  (t.responsiblePersonUniqueId) = (select t.responsiblePersonUniqueId from FilerDemographic t where   " +
          "(t.id.sourceUniqueId) = (:sourceUniqueId) " +
          " AND  lower(t.id.sourceCd) = lower(:sourceCd)) ")
  List<FilerDemographic> getFormFilerDetailsAllCovered(
          @Param("sourceUniqueId") long sourceUniqueId,
          @Param("sourceCd") String sourceCd);//

//

  /*update Filers personal info FilerDemographics table*/
  @Modifying
  @Transactional
  @Query("update FilerDemographic t set " +
          " t.recepientFirstName=(:recepientFirstName)" +","+
          " t.recepientLastName=(:recepientLastName)" + ","+
          " t.recepientAddressLine1=(:recepientAddressLine1)" +","+
          " t.recepientAddressLine2=(:recepientAddressLine2)" +","+
          " t.recepientCity=(:recepientCity)" +","+
          " t.recepientState=(:recepientState)" +","+
          " t.recepientZip5=(:recepientZip5)" +","+
          " t.comments=(:comments) " +","+
          " t.formStatus=(:formStatus)"+","+
          " t.updatedBy=(:updatedBy)"+","+
          " t.updatedDt=(:updatedDt)"+
          " where   (t.id.sourceUniqueId) = (:sourceUniqueId)  "+
          " AND  lower(t.id.sourceCd) = lower(:sourceCd)")
  int updateCustomerDemographicDetails(
          @Param("recepientFirstName") String recepientFirstName,
          @Param("recepientLastName") String recepientLastName,
          @Param("recepientAddressLine1") String recepientAddressLine1,
          @Param("recepientAddressLine2") String recepientAddressLine2,
          @Param("recepientCity") String recepientCity,
          @Param("recepientState") String recepientState,
          @Param("recepientZip5") String recepientZip5,
          @Param("comments") String comments,
          @Param("formStatus") String formStatus,
          @Param("updatedBy") String updatedBy,
          @Param("updatedDt") Date updatedDt,
          @Param("sourceUniqueId") long sourceUniqueId,
          @Param("sourceCd") String sourceCd);

  @Transactional
  @Modifying
  @Query(value = "UPDATE FILER_DEMOGRAPHICS SET RECIPIENT_FIRST_NAME = (:recepientFirstName)," +
          " RECIPIENT_LAST_NAME = (:recepientLastName)," +
          " RECIPIENT_ADDRESS_LINE_1 = (:recepientAddressLine1),"+
          " RECIPIENT_ADDRESS_LINE_2 = (:recepientAddressLine2),"+
          " RECIPIENT_CITY = (:recepientCity),"+
          " RECIPIENT_STATE = (:recepientState),"+
          " RECIPIENT_ZIP_5 = (:recepientZip5),"+
          " COMMENTS = (:comments),"+
          " JAN = (:jan),"+
          " FEB = (:feb),"+
          " MAR = (:mar),"+
          " APR = (:apr),"+
          " MAY = (:may),"+
          " JUN = (:jun),"+
          " JUL = (:jul),"+
          " AUG = (:aug),"+
          " SEP = (:sep),"+
          " OCT = (:oct),"+
          " NOV = (:nov),"+
          " DEC = (:dec),"+
          " FORM_STATUS = (:formStatus),"+
          " UPDATED_BY = (:updatedBy),"+
          " UPDATED_DATE = (:updatedDt),"+
          " IRS_TRANSMISSION_STATUS_CD = (:irsTransmissionStatusCd)," +
          " FILER_DEMO_SEQ = (NEXT VALUE FOR SEQUENCE_NO_SEQ)"+
          " WHERE SOURCE_UNIQUE_ID = (:sourceUniqueId)"+
          " AND lower(SOURCE_CD) = lower(:sourceCd)" +
          " AND TAX_YEAR = (:taxYear)", nativeQuery = true)
  int updateFilerCustomerDemographicDetails(@Param("recepientFirstName") String recepientFirstName,
                                            @Param("recepientLastName") String recepientLastName,
                                            @Param("recepientAddressLine1") String recepientAddressLine1,
                                            @Param("recepientAddressLine2") String recepientAddressLine2,
                                            @Param("recepientCity") String recepientCity,
                                            @Param("recepientState") String recepientState,
                                            @Param("recepientZip5") String recepientZip5,
                                            @Param("comments") String comments,
                                            @Param("jan") Character jan,
                                            @Param("feb") Character feb,
                                            @Param("mar") Character mar,
                                            @Param("apr") Character apr,
                                            @Param("may") Character may,
                                            @Param("jun") Character jun,
                                            @Param("jul") Character jul,
                                            @Param("aug") Character aug,
                                            @Param("sep") Character sep,
                                            @Param("oct") Character oct,
                                            @Param("nov") Character nov,
                                            @Param("dec") Character dec,
                                            @Param("formStatus") String formStatus,
                                            @Param("updatedBy") String updatedBy,
                                            @Param("updatedDt") Date updatedDt,
                                            @Param("irsTransmissionStatusCd") String irsTransmissionStatusCd,
                                            @Param("sourceUniqueId") long sourceUniqueId,
                                            @Param("sourceCd") String sourceCd,
                                            @Param("taxYear") int taxYear);

  /*update Filers personal info FilerDemographics table*/
  @Modifying
  @Transactional
  @Query(value = "UPDATE FILER_DEMOGRAPHICS SET RECIPIENT_FIRST_NAME = (:recepientFirstName)," +
          " RECIPIENT_LAST_NAME = (:recepientLastName)," +
          " JAN = (:jan),"+
          " FEB = (:feb),"+
          " MAR = (:mar),"+
          " APR = (:apr),"+
          " MAY = (:may),"+
          " JUN = (:jun),"+
          " JUL = (:jul),"+
          " AUG = (:aug),"+
          " SEP = (:sep),"+
          " OCT = (:oct),"+
          " NOV = (:nov),"+
          " DEC = (:dec),"+
          " UPDATED_BY = (:updatedBy),"+
          " UPDATED_DATE = (:updatedDt),"+
          " FILER_DEMO_SEQ = (NEXT VALUE FOR SEQUENCE_NO_SEQ)"+
          " WHERE SOURCE_UNIQUE_ID = (:sourceUniqueId)"+
          " AND lower(SOURCE_CD) = lower(:sourceCd)" +
          " AND TAX_YEAR = (:taxYear)", nativeQuery = true)
  int updateCoveredCustomerDemographicDetails(
          @Param("recepientFirstName") String recepientFirstName,
          @Param("recepientLastName") String recepientLastName,
          @Param("jan") Character jan,
          @Param("feb") Character feb,
          @Param("mar") Character mar,
          @Param("apr") Character apr,
          @Param("may") Character may,
          @Param("jun") Character jun,
          @Param("jul") Character jul,
          @Param("aug") Character aug,
          @Param("sep") Character sep,
          @Param("oct") Character oct,
          @Param("nov") Character nov,
          @Param("dec") Character dec,
          @Param("updatedBy") String updatedBy,
          @Param("updatedDt") Date updatedDt,
          @Param("sourceUniqueId") long sourceUniqueId,
          @Param("sourceCd") String sourceCd,
          @Param("taxYear") int taxYear);



  /*Added on 17/12/2015
update all filer's form status'*/
  @Modifying
  @Transactional
  @Query("update FilerDemographic t set " +
          " t.formStatus=(:formStatus)"+","+
          " t.irsTransmissionStatusCd = (:irsTransmissionStatusCd)" + ","+
          " t.comments=(:comments) " +","+
          " t.updatedBy=(:updatedBy)"+","+
          " t.updatedDt=(:updatedDt)"+
          " where   (t.id.sourceUniqueId) = (:sourceUniqueId)  "+
          " AND  lower(t.id.sourceCd) = lower(:sourceCd)")
  int updateResponsibleCustomeFormStatus(
          @Param("formStatus") String formStatus,
          @Param("irsTransmissionStatusCd") String irsTransmissionStatusCd,
          @Param("comments") String comments,
          @Param("updatedBy") String updatedBy,
          @Param("updatedDt") Date updatedDt,
          @Param("sourceUniqueId") long sourceUniqueId,
          @Param("sourceCd") String sourceCd);

  /*Added on 17/12/2015
  update all filer's form status'*/
  @Modifying
  @Transactional
  @Query("update FilerDemographic t set " +
          " t.updatedBy=(:updatedBy)"+","+
          " t.updatedDt=(:updatedDt)"+
          " where   (t.responsiblePersonUniqueId) = (:responsiblePersonUniqueId)")
  int updateCoveredCustomerFormStatus(
          @Param("updatedBy") String updatedBy,
          @Param("updatedDt") Date updatedDt,
          @Param("responsiblePersonUniqueId") long sourceUniqueId);




    /*update FilerDemographic with coverage details*/

  @Modifying
  @Transactional
  @Query("update FilerDemographic t set " +
          " t.jan=(:jan)" +","+
          " t.feb=(:feb)" + ","+
          " t.mar=(:mar)" +","+
          " t.apr=(:apr)" +","+
          " t.may=(:may)" +","+
          " t.jun=(:jun)" +","+
          " t.jul=(:jul)" +","+
          " t.aug=(:aug)" + ","+
          " t.sep=(:sep)" +","+
          " t.oct=(:oct)" +","+
          " t.nov=(:nov)" +","+
          " t.dec=(:dec)" +","+
          " t.formStatus=(:formStatus)"+
          " where   (t.id.sourceUniqueId) = (:sourceUniqueId)  "+
          " AND  lower(t.id.sourceCd) = lower(:sourceCd)")
  int updateCustomerCoverageDetails(
          @Param("jan") Character jan,
          @Param("feb") Character feb,
          @Param("mar") Character mar,
          @Param("apr") Character apr,
          @Param("may") Character may,
          @Param("jun") Character jun,
          @Param("jul") Character jul,
          @Param("aug") Character aug,
          @Param("sep") Character sep,
          @Param("oct") Character oct,
          @Param("nov") Character nov,
          @Param("dec") Character dec,
          @Param("formStatus") String formStatus,
          @Param("sourceUniqueId") long sourceUniqueId,
          @Param("sourceCd") String sourceCd);



/*update all customer's coverage source details
added on 17/12/2015   */

  @Modifying
  @Transactional
  @Query("update FilerDemographic t set " +
          " t.jan=(:jan)" +","+
          " t.feb=(:feb)" + ","+
          " t.mar=(:mar)" +","+
          " t.apr=(:apr)" +","+
          " t.may=(:may)" +","+
          " t.jun=(:jun)" +","+
          " t.jul=(:jul)" +","+
          " t.aug=(:aug)" + ","+
          " t.sep=(:sep)" +","+
          " t.oct=(:oct)" +","+
          " t.nov=(:nov)" +","+
          " t.dec=(:dec)" +","+
          " t.formStatus=(:formStatus)"+
          " where   (t.responsiblePersonUniqueId) = (:responsiblePersonUniqueId)")
  int updateAllCustomerCoverageDetails(
          @Param("jan") Character jan,
          @Param("feb") Character feb,
          @Param("mar") Character mar,
          @Param("apr") Character apr,
          @Param("may") Character may,
          @Param("jun") Character jun,
          @Param("jul") Character jul,
          @Param("aug") Character aug,
          @Param("sep") Character sep,
          @Param("oct") Character oct,
          @Param("nov") Character nov,
          @Param("dec") Character dec,
          @Param("formStatus") String formStatus,
          @Param("responsiblePersonUniqueId") long responsiblePersonUniqueId);


  @Override
  List<FilerDemographic> findAll();






}
