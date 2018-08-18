package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.entity.FilerDemographicCW;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.security.IAuthenticationHolder;
import us.deloitteinnovation.aca.security.UserSession;
import us.deloitteinnovation.aca.util.LoggingUtil;
import us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto.*;
import us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.util.CaseWorkerUtility;
import us.deloitteinnovation.aca.web.caseworkerportal.services.ICaseworkerPortalService;
import us.deloitteinnovation.aca.web.caseworkerportal.vo.SearchFilerInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ritmukherjee on 10/12/2015.
 */

@Controller
@RequestMapping("/api/caseworkerportal")
public class CaseworkerLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaseworkerLoginController.class);

    @Autowired
    ICaseworkerPortalService caseworkerPortalService;

    @Autowired
    CaseWorkerUtility caseWorkerUtility;

    @Autowired
    IAuthenticationHolder authenticationHolder;

    /**
     * @param searchFilerInfo
     * @return
     * @name searchFiler
     * @author R. Mukherjee
     * @purpose Search list of filers based on SSN+TaxYear/TIN+YaxYear/LasyName+DOB+Taxyear
     * @since 10/12/2015
     */
    @RequestMapping(value = "/searchFiler", method = RequestMethod.POST)
    @ResponseBody
    public String searchFiler(@RequestBody SearchFilerInfo searchFilerInfo,HttpServletRequest request) {
        DateTime requestStart = new DateTime();
        boolean _SSN_Search=false;
        boolean _TIN_Search=false;
        String searchInformationMessage = "";
        UserSession userSession = authenticationHolder.getUserSession();
        Filers filers = new Filers();
        try {
            searchFilerInfo.setState(userSession.getState());//

            /*ObjectMapper responsible to convert Java object to JSON object*/

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            List<FilerDemographicCW> demographics = null;
            if (searchFilerInfo.getTaxYear() == 0) {
                searchInformationMessage = CommonDataConstants.INVALID_SEARCH_CRITERIA;
            } else {
                if (searchFilerInfo.getSsn() != null && !searchFilerInfo.getSsn().equals(CommonDataConstants.EMPTY_VALUE)) {
                    _SSN_Search=true;
                    demographics = caseworkerPortalService.findFilersSSN_TaxYear(searchFilerInfo.getSsn(),searchFilerInfo.getState(), searchFilerInfo.getTaxYear());
                } else {
                    if (searchFilerInfo.getTin() != null && !searchFilerInfo.getTin().equals(CommonDataConstants.EMPTY_VALUE)) {
                        _TIN_Search=true;
                        demographics = caseworkerPortalService.findFilersTIN_TaxYear(searchFilerInfo.getTin(),searchFilerInfo.getState(), searchFilerInfo.getTaxYear());
                    } else {

                        if ((searchFilerInfo.getUserLname() != null && !searchFilerInfo.getUserLname().equals(CommonDataConstants.EMPTY_VALUE) )&&
                                (searchFilerInfo.getDob() != null && !searchFilerInfo.getDob().equals(CommonDataConstants.EMPTY_VALUE))) {

                            if(searchFilerInfo.getUserFname()==null || CommonDataConstants.EMPTY_VALUE.equals(searchFilerInfo.getUserFname())) {
                                demographics = caseworkerPortalService.findFilersLastName_DOB_TaxYear(searchFilerInfo.getUserLname(), searchFilerInfo.getState(), searchFilerInfo.getDob(), searchFilerInfo.getTaxYear());
                            }
                            else{
                                demographics = caseworkerPortalService.findFilersFirstName_LastName_DOB_TaxYear(searchFilerInfo.getUserFname(),searchFilerInfo.getUserLname(), searchFilerInfo.getState(), searchFilerInfo.getDob(), searchFilerInfo.getTaxYear());
                            }
                        } else {
                            searchInformationMessage = CommonDataConstants.INSUFFICIENT_SEARCH_CRITERIA;
                        }
                    }

                }

            }

            if (demographics != null) {
                /*conditions when search result returns only one record*/
                if(demographics.size()==1){
                    FilerDemographicCW demographic=demographics.get(0);

                    boolean isFilerValid=caseworkerPortalService.validateFilerInformation(searchFilerInfo,demographic);

                    if(isFilerValid==true){
                        filers.setFilers(caseWorkerUtility.getFilerDto(demographics, searchFilerInfo));
                    }
                }
                /*conditions when search result returns multiple records*/
                else{


                    if(_SSN_Search==true||_TIN_Search==true){
                        List<FilerInfo> retrieveFilers=caseWorkerUtility.getFilerDto(demographics,searchFilerInfo);
                        boolean isFilerValid= caseworkerPortalService.validateGroupFilers(retrieveFilers, searchFilerInfo);
                        if(isFilerValid==true){
                            filers.setFilers(retrieveFilers);
                        }
                        else{

                            filers.setFilers(null);
                        }
                    }else {
                        filers.setFilers(caseWorkerUtility.getFilerDto(demographics, searchFilerInfo));
                    }
                }

                searchInformationMessage = mapper.writeValueAsString(filers.getFilers());
            }
        }  catch (JsonProcessingException | RuntimeException je) {
            LOGGER.error("Failed to search filer demographic data. ", je);
        }
        String sourceUniqueIds = "";
        if(filers != null && filers.getFilers() != null && filers.getFilers().size() > 0) {
            for(FilerInfo filerInfo: filers.getFilers()) {
                String[] filerIds = filerInfo.getFilerID().split(CommonDataConstants.UNDERSCORE_VALUE);
                sourceUniqueIds = sourceUniqueIds + " " + filerIds[1];
            }
        }
        LoggingUtil.logRequest("CW_Search", requestStart, request, userSession, sourceUniqueIds);
        return searchInformationMessage;
    }


    /**
     * @param formID
     * @return json string with form details
     * @throws JsonProcessingException
     * @name getForm
     * @author R. Mukherjee
     * @purpose The method responsible to find out Form Details include all customers
     * @since 11.25.2015
     */
    @RequestMapping(value = "/getForm", method = RequestMethod.POST)
    @ResponseBody
    public String getForm(@RequestParam(value = "formID", required = true) String formID,HttpServletRequest request,HttpServletResponse response) {
        DateTime requestStart = new DateTime();
        /*Retrieve UserSession ------------------------------------------------*/
        UserSession userSession = authenticationHolder.getUserSession();
        String[] parts = formID.split(CommonDataConstants.UNDER_SCORE);
        String sourceCd = parts[0];
        String sourceUniqueId=parts[1];
        /*  #TEMP# #STATE_VALIDATION# assignment operator temporary user for swagger based access*/
        String stateCd=  formID.substring(CommonDataConstants.STATE_CODE_START_INDEX, CommonDataConstants.STATE_CODE_END_INDEX);
        if(userSession!=null) {
            if(userSession.getState()!=null) {
                if (!stateCd.equals(userSession.getState())) {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                }
            }
        }
        String viewFormMessage = CommonDataConstants.EMPTY_VALUE;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ViewFormInfo viewFormInfo = caseworkerPortalService.getViewFormDetails(formID, userSession,stateCd);
        try {
           /* Covert: DTO<ViewFormInfo>:JSON<viewFormMessage>*/
            viewFormMessage = mapper.writeValueAsString(viewFormInfo);
        } catch (JsonProcessingException e) {
            LOGGER.error("[ERROR] Inside [" + LOGGER.getClass().getName() + "] service method :[getForm, error" + e.getMessage() + "]");
        } catch (RuntimeException re) {
            LOGGER.error("[ERROR] Inside class [" + LOGGER.getClass().toString() + "] and EXCEPTION:" + re.toString());
        }
        LoggingUtil.logRequest("CW_View", requestStart, request, userSession, sourceUniqueId);
        return viewFormMessage;
    }
    /**
     * @param customerID
     * @return
     * @author R. Mukherjee
     * @purpose responsible for view FilerDemographic existing data
     * @since 11/09/2015
     */
    @RequestMapping(value = "/viewCustomerExistingData", method = RequestMethod.POST)
    @ResponseBody
    public String viewCustomerExistingData(@RequestParam(value = "customerID") String customerID, HttpServletRequest request, HttpServletResponse response) {
        DateTime requestStart = new DateTime();
        String customerInformation = CommonDataConstants.EMPTY_VALUE;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        UserSession userSession = authenticationHolder.getUserSession();
        String[] parts = customerID.split(CommonDataConstants.UNDER_SCORE);
        String sourceCd = parts[0];
        String sourceUniqueId=parts[1];
        try {
            /* #TEMP# #STATE_VALIDATION#  assignment operator temporary user for swagger based access*/
            String stateCd=  customerID.substring(CommonDataConstants.STATE_CODE_START_INDEX, CommonDataConstants.STATE_CODE_END_INDEX);
            if(userSession!=null) {
                if(userSession.getState()!=null) {
                    if (!stateCd.equals(userSession.getState())) {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                    /*return HttpStatus.FORBIDDEN.toString();*/
                    }
                }
            }
            CorrectCustomerInfo customerInfo = caseworkerPortalService.getExistingCustomerInfo(customerID, userSession);
            if (customerInfo == null) {

                customerInformation = "Customer Information is not available";
            } else {

                customerInformation = mapper.writeValueAsString(customerInfo);
            }
        } catch (JsonProcessingException je) {
            LOGGER.error("[ERROR] Inside class [" + LOGGER.getClass().toString() + "] and EXCEPTION:" , je);
        } catch (RuntimeException re) {
            LOGGER.error("[ERROR] Inside class [" + LOGGER.getClass().toString() + "] and EXCEPTION:" , re);
        }
        LoggingUtil.logRequest("CW_Edit", requestStart, request, userSession, sourceUniqueId);
        return customerInformation;
    }


    /**
     * @param correctCustomerInfo
     * @return
     * @author R. Mukherjee
     * @purpose This method is responsible to update FilerDemographic details
     * @since 11/09/2015
     */
    @RequestMapping(value = "/saveFilerDemographicsData", method = RequestMethod.POST)
    @ResponseBody
    public String saveFilerDemographicsData(@RequestBody CorrectCustomerInfo correctCustomerInfo,HttpServletRequest request,HttpServletResponse response) {
        DateTime requestStart = new DateTime();
        String updateInformation = CommonDataConstants.EMPTY_VALUE;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String[] parts = correctCustomerInfo.getCurrentForm().getFormID().split(CommonDataConstants.UNDER_SCORE);
        String sourceUniqueId=parts[1];
        UserSession userSession = authenticationHolder.getUserSession();
        try {
            /*=========================START:Getting Logged-in Caseworker info from session=========================*/
            String userInfo = userSession.getFirstName() + CommonDataConstants.EMPTY_VALUE + userSession.getLastName();
            LOGGER.info("[INFO]Logged-in user is: \t" + userInfo);
            /*=========================START:Getting Logged-in Caseworker info from session=========================*/

            /*  #STATE_VALIDATION# assignment operator temporary user for swagger based access*/
            if (userSession.getState() != null) {
                String stateCd = correctCustomerInfo.getCurrentForm().getFormID().substring(CommonDataConstants.STATE_CODE_START_INDEX, CommonDataConstants.STATE_CODE_END_INDEX);
                if (!stateCd.equals(userSession.getState())) {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                }
            }

             /*   #USER_ROLE_VALIDATION# assignment operator temporary user for swagger based access*/
            if (userSession.getRole() != null) {
                if (!CommonDataConstants.CASEWORKER_RW.equalsIgnoreCase(userSession.getRole())) {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                }
                else{
                    RecordUpdateInfo recordUpdateInfo = caseworkerPortalService.updateExistingCustomerInfo(correctCustomerInfo,userInfo,userSession );
                    updateInformation = mapper.writeValueAsString(recordUpdateInfo);
                }
            }

        } catch (JsonProcessingException | RuntimeException je) {
            LOGGER.error("Failed to save filer demographic data. ", je);
        }
        LoggingUtil.logRequest("CW_Editsubmit", requestStart, request, userSession, sourceUniqueId);
        return updateInformation;
    }





    /**
     * @Purpose The purpose of this method is if auditSeqNo does not supply its fectch pdf from FILER_DEMOGRAPHIC
     *          else fetch record from A_FILER_DEMOGRAPHIC
     * @author R. Mukherjee
     * @param pdfID
     * @return byte[]
     * @throws RuntimeException
     * @since 11/12/2015
     */
    @RequestMapping(value = "/viewCustomerPdf", method = RequestMethod.POST)
    @ResponseBody
    public String viewPdf(
            @RequestParam(value = "pdfID") String pdfID,
            @RequestParam(value = "auditSequenceNo",required = false)String auditSequenceNo, HttpServletRequest request, HttpServletResponse response) {
        DateTime requestStart = new DateTime();
        ObjectMapper mapper = new ObjectMapper();
        String authentication = "";
        UserSession userSession = authenticationHolder.getUserSession();

        String[] parts = pdfID.split(CommonDataConstants.UNDER_SCORE);
        String sourceCd = parts[0];
        String sourceUniqueId=parts[1];
        if(userSession!=null) {
            String stateCd = sourceCd.substring(CommonDataConstants.STATE_CODE_START_INDEX, CommonDataConstants.STATE_CODE_END_INDEX);
            if (!stateCd.equals(userSession.getState())) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        }
        List<FilerDemographicCW> filerDemographicCWs;
        FilerDemographicCW filerDemographicCW;
        ViewPrintInfo printInfo = new ViewPrintInfo();
        try {

            filerDemographicCWs = caseworkerPortalService.getCustomerPDF(pdfID, auditSequenceNo);
            filerDemographicCW = filerDemographicCWs.get(0);
            if (filerDemographicCWs != null) {
                List<CoveredPerson> coveredPersons = null;
                printInfo.setDataValues(filerDemographicCW);
                printInfo.setCorrectionIndicator(caseworkerPortalService.getCorrectionIndicator(filerDemographicCW.getId().getSourceUniqueId(),filerDemographicCW.getId().getSourceCd(),String.valueOf(filerDemographicCW.getId().getTaxYear())));
                coveredPersons = caseworkerPortalService.getCoveredPersonListWithTaxYR(filerDemographicCW.getId().getSourceUniqueId(), filerDemographicCW.getId().getSourceCd(), String.valueOf(filerDemographicCW.getId().getTaxYear()));
                if (coveredPersons != null && coveredPersons.size() > 0) {
                    printInfo.setCoveredPersonList(coveredPersons);
                }
            }
            authentication = mapper.writeValueAsString(printInfo);

        } catch (Exception re) {
            LOGGER.error("[ERROR] Inside class [" + LOGGER.getClass().toString() + "] and EXCEPTION:" ,re);
        }

        LoggingUtil.logRequest("CW_View", requestStart, request, userSession, sourceUniqueId);
        return authentication;
    }





    @RequestMapping(value = "/requestMail", method = RequestMethod.POST)
    @ResponseBody
    public String requestMail(@RequestParam(value = "customerID", required = true) String customerID,
                       HttpServletRequest request, HttpServletResponse response) {
        DateTime requestStart = new DateTime();
        UserSession userSession = authenticationHolder.getUserSession();

        String[] parts = customerID.split(CommonDataConstants.UNDER_SCORE);
        String sourceCd = parts[0];
        String sourceUniqueId=parts[1];
        String stateCd = sourceCd.substring(CommonDataConstants.STATE_CODE_START_INDEX, CommonDataConstants.STATE_CODE_END_INDEX);
        if (!stateCd.equals(userSession.getState())) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        } else {
            try {
                String userInfo = userSession.getFirstName() + CommonDataConstants.EMPTY_VALUE + userSession.getLastName();;
                LOGGER.info("User Info-----------" + userInfo);
                int mailStatus = this.caseworkerPortalService.updateMailStatus(customerID, userInfo);
                if (mailStatus == 1) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    LoggingUtil.logRequest("CW_Search", requestStart, request, userSession, sourceUniqueId);
                    return mapper.writeValueAsString(CommonDataConstants.PrintStatus.READY_TO_MAIL);
                }

            }catch(JsonProcessingException je){
                LOGGER.error("[ERROR] Inside class [" + LOGGER.getClass().toString() + "] and Service :pdf regenerate service JASON conversion error");
            }
        }
        return null;
    }
}