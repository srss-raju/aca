package us.deloitteinnovation.aca.web.citizenportal.citizenlogin.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.entity.FilerDemographicCP;
import us.deloitteinnovation.aca.entity.SourceSystemConfig;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.web.citizenportal.citizenlogin.dto.CaptchaResponse;
import us.deloitteinnovation.aca.web.citizenportal.citizenlogin.dto.StateInfoDTO;
import us.deloitteinnovation.aca.web.citizenportal.citizenlogin.dto.StateList;
import us.deloitteinnovation.aca.web.citizenportal.citizenlogin.dto.ViewPrintInfo;
import us.deloitteinnovation.aca.web.citizenportal.services.ICitizenPortalService;
import us.deloitteinnovation.aca.web.citizenportal.vo.UserInfo;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * this is the citizenlogin class for ACA web app this class will be entry point for the rest service calls ui will be makin
 * Created by tthakore on 9/28/2015.
 */
@RestController
@RequestMapping("api")
@SessionAttributes("documentReff")
public class CitizenLoginController {
    private static final Logger logger = LoggerFactory.getLogger(CitizenLoginController.class);
    private static final Logger CP_LOGGER = LoggerFactory.getLogger("cplogger");

    private ICitizenPortalService citizenportalservice;


    @Autowired
    public CitizenLoginController(ICitizenPortalService citizenportalservice) {
        this.citizenportalservice = citizenportalservice;
    }


    @RequestMapping(value = "/getavailablestates", method = RequestMethod.GET)
    public @ResponseBody String getAvailableStates(HttpServletRequest request) {
        Date requestStartTime = new Date();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        String availableStatesList = "";
        List<StateList> statelist = new ArrayList<>();
        Map<String, StateList> stringStateInfoDTOMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<SourceSystemConfig> result = citizenportalservice.getSelectedStateList();
            for (SourceSystemConfig item : result) {
                if (!stringStateInfoDTOMap.containsKey(item.getStateAbbreviation().toLowerCase())) {
                    StateList state = new StateList();
                    StateInfoDTO stateInfo = new StateInfoDTO();
                    state.setStateName(item.getStateName());
                    state.setStateCode(item.getStateAbbreviation());
                    stateInfo.setYear(String.valueOf(item.getId().getTaxYear()));
                    stateInfo.setStaticStatus(item.getStaticStatus());
                    state.getStateInfo().add(stateInfo);
                    stringStateInfoDTOMap.put(item.getStateAbbreviation().toLowerCase(), state);
                } else {
                    StateList state = stringStateInfoDTOMap.get(item.getStateAbbreviation().toLowerCase());
                    StateInfoDTO stateInfo = new StateInfoDTO();
                    stateInfo.setYear(String.valueOf(item.getId().getTaxYear()));
                    stateInfo.setStaticStatus(item.getStaticStatus());
                    state.getStateInfo().add(stateInfo);
                }

            }
            statelist.addAll(stringStateInfoDTOMap.values());
            availableStatesList = mapper.writeValueAsString(statelist);
        } catch (Exception e) {
            logger.error("ERROR : Error in getting pdf ", e);
        }
        if (CP_LOGGER.isInfoEnabled()) {
            Date requestEndTime = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss.SSS");
            StringBuilder logString = new StringBuilder();
            logString.append("CP_Visit - Request Start Time: " + dateFormat.format(requestStartTime) + ",");
            logString.append(" Request End Time: " + dateFormat.format(requestEndTime) + ",");
            logString.append(" Duration: " + (requestEndTime.getTime() - requestStartTime.getTime()) + ",");
            logString.append(" IP Address: " + ipAddress);
            CP_LOGGER.info(logString.toString());
        }
        return availableStatesList;
    }

    /**
     * below function will work as authentication function for  the user who is logging in to the ACA client system
     * return type of this function will be viewprintform in in json format.
     **/
    @RequestMapping(value = "/getauthenticationdata", method = RequestMethod.POST)
    public @ResponseBody String getUserAuthInJSON(@RequestBody UserInfo uInfo, HttpServletRequest request) {

        ObjectMapper mapper = new ObjectMapper();
        String authentication = "";
        Date requestStartTime = new Date();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        try {
            FilerDemographicCP results = citizenportalservice.verifyUserInformation(uInfo);
            ViewPrintInfo printInfo = new ViewPrintInfo();
            if (results != null) {

                if (results.isUserFound() && verifyReceptcha(uInfo.getgRecaptchaResponse()))
                {
                    List<CoveredPerson> coveredPersons;
                    printInfo.setDataValues(results, uInfo.getUidType(), getCorrectionIndicator(results));
                    coveredPersons = getCoveredPerson(results);
                    /* condition to check covered person list for sending it to UI  */
                    if (coveredPersons != null && !coveredPersons.isEmpty()) {
                        printInfo.setCoveredPersonList(coveredPersons);
                    }
                } else {
                    printInfo.setSystemConfigValues(results.getSourcesystemconfig(), results.isUserFound());
                }
            }
            // convert user object to json string, and save to a file
            authentication = mapper.writeValueAsString(printInfo);
            if (CP_LOGGER.isInfoEnabled()) {
                Date requestEndTime = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss.SSS");
                StringBuilder logString = new StringBuilder();
                logString.append("CP_Search - Request Start Time: " + dateFormat.format(requestStartTime) + ",");
                logString.append(" Request End Time: " + dateFormat.format(requestEndTime) + ",");
                logString.append(" Duration: " + (requestEndTime.getTime() - requestStartTime.getTime()) + ",");
                logString.append(" IP Address: " + ipAddress + ",");
                logString.append(" Selected State: " + uInfo.getUserSelectedState() + ",");
                logString.append(" Selected Tax Year: " + uInfo.getUserSelectedTaxYear() + ",");
                logString.append(" Source Unique Id: " + ((results != null && results.getId() != null) ? results.getId().getSourceUniqueId() : null));
                CP_LOGGER.info(logString.toString());
            }
        } catch (Exception e) {
            logger.error("ERROR: not able to authenticate ", e);
        }
        return authentication;
    }

    private String getCorrectionIndicator(FilerDemographicCP results){
      return  citizenportalservice.getCorrectionIndicator(results.getId().getSourceUniqueId(),results.getId().getSourceCd(),String.valueOf(results.getId().getTaxYear()));
    }

    private List<CoveredPerson> getCoveredPerson(FilerDemographicCP results)
    {
        return citizenportalservice.getCoveredPersonListWithTaxYR(results.getId().getSourceUniqueId(), results.getId().getSourceCd(), String.valueOf(results.getId().getTaxYear()));
    }

    private Boolean verifyReceptcha(String str) {
        CaptchaResponse responseVal = null;
        try {
            logger.info("[INFO] inside verifyReceptchs service  :Start");
            URL url = new URL(CommonDataConstants.CAPTCHA_URL);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String urlParameters = CommonDataConstants.CAPTCHA_KEY + str;

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            logger.info(response.toString());
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String result = response.toString().replace("-", "_");
            responseVal = mapper.readValue(result, CaptchaResponse.class);

        } catch (Exception e) {
            logger.error("Error while verifying  captcha from google .", e);
        }
        logger.info("[INFO] inside verifyReceptchs service  :End");
        return responseVal != null && "true".equalsIgnoreCase(responseVal.getSuccess());
    }


}
