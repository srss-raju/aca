package us.deloitteinnovation.aca.web.caseworkerportal.services;

import us.deloitteinnovation.aca.entity.FilerDemographicCW;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.security.UserSession;
import us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto.*;
import us.deloitteinnovation.aca.web.caseworkerportal.vo.SearchFilerInfo;

import java.util.List;

/**
 * Created by ritmukherjee on 10/10/2015.
 */

public interface ICaseworkerPortalService {
  //  List<FilerDemographic> findAllUsersForDemography(String SSN);

    /*List<FilerDemographic> findFilersSSN_TaxYear(String SSN, int taxYear);*/


    List<FilerDemographicCW> findFilersSSN_TaxYear(String SSN, String recepientState, int taxYear) throws RuntimeException;

    List<FilerDemographicCW> findFilersTIN_TaxYear(String TIN, String recepientState, int taxYear) throws RuntimeException;

    List<FilerDemographicCW> findFilersLastName_DOB_TaxYear(String recepientLastName, String recepientState, String recepientDOB, int taxYear)throws RuntimeException;

    String getMailRequestValidity(long sourceUniqueId, String sourceCd);

    List<FilerDemographicCW> findFilersFirstName_LastName_DOB_TaxYear(String recepientFirstName, String recepientLastName, String recepientState, String recepientDOB, int taxYear) throws RuntimeException;

    public String getDocumentRequestValidty(String documentId);

    IndividualFormInfo getIndividualFormDetails(String customerID);

    public Boolean validateGroupFilers(List<FilerInfo> filers, SearchFilerInfo searchFilerInfo);

    ViewCurrentFormDataInfo getViewCurrentFormDataDetails(String formID);

/*    ViewFormInfo getViewFormDetails(String formID, UserSession userSession);*/

    ViewFormInfo getViewFormDetails(String formID, UserSession userSession, String stateCd);


    List<CoverageSourceInfo> getCoverageSourceInfo(String customerID);

    CorrectCustomerInfo getExistingCustomerInfo(String customerID, UserSession userSession);

    RecordUpdateInfo updateExistingCustomerInfo(CorrectCustomerInfo correctCustomerInfo, String userInfo, UserSession userSession);

    FormPdfInfo viewCustomerPdfDetails(String customerID);



    Boolean validateFilerInformation(SearchFilerInfo filerInfo, FilerDemographicCW demographic);

    List<FilerDemographicCW> getCustomerPDF(String pdfID, String a_seq_no);

    int updateMailStatus(String customerId,
                         String userInfo);


    List<CoveredPerson> getCoveredPersonListWithTaxYR(long responsiblePersonUniqueId, String sourceCd, String taxYear);
    String getCorrectionIndicator(long sourceuniqueID, String sourceCd, String taxYear);
}
