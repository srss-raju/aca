package us.deloitteinnovation.aca.web.opsportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.XmlConstants;
import us.deloitteinnovation.aca.security.UserSession;
import us.deloitteinnovation.aca.web.opsportal.data.IOpsPortalDataService;
import us.deloitteinnovation.aca.web.opsportal.dto.*;

import java.util.Date;
import java.util.List;

/**
 * Created by sdalavi on 3/28/2016.
 */
@RestController
@RequestMapping("opsportal")
public class OpsController {
    private static final Logger logger = LoggerFactory.getLogger(OpsController.class);
    @Autowired
    private IOpsPortalDataService opsPortalDataService;

    public void setOpsPortalDataService(IOpsPortalDataService opsPortalDataService) {
        this.opsPortalDataService = opsPortalDataService;
    }

    @RequestMapping(value="/getStates", method = RequestMethod.GET)
    public List<StateDto> getStates() {
        List<StateDto> stateDtos = opsPortalDataService.getTransmissionStates();
        return stateDtos;
    }

    @RequestMapping(value="/getYears", method = RequestMethod.GET)
    public List<TaxYearDto> getYears() {
        return opsPortalDataService.getTransmissionYears();
    }

    @RequestMapping(value="/getTransmissionStatuses", method = RequestMethod.GET)
    public List<StatusDto> getTransmissionStatuses() {
        List<StatusDto> statusDtos = opsPortalDataService.getTransmissionStatuses(XmlConstants.TYPE_CD_LCMUI);
        return statusDtos;
    }

    @RequestMapping(value="/getTransmissionRecords", method = RequestMethod.GET)
    public List<IrsTransmittalDetailsDto> getTransmissionRecords(
            @RequestParam String stateCd, @RequestParam Integer taxYear) {

        return opsPortalDataService.getTransmissionRecords(stateCd, taxYear);
    }

    @RequestMapping(value="/saveTransmissionRecord", method = RequestMethod.POST)
    public @ResponseBody
        ResponseDto saveTransmissionRecord(@RequestBody IrsTransmittalDetailsDto irsTransmittalDetailsDto) {
            String userName = null;
            UserSession userSession = null;
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if (securityContext != null && securityContext.getAuthentication() != null) {
                Object principal = securityContext.getAuthentication().getPrincipal();
                if (principal != null && principal instanceof UserSession) {
                    userSession = (UserSession) securityContext.getAuthentication().getPrincipal();
                    userName = userSession.getFirstName() + CommonDataConstants.EMPTY_VALUE + userSession.getLastName();
                }
            }
            if(userName == null) {
                logger.error("User is not logged in");
                ResponseDto responseDto = new ResponseDto();
                responseDto.setStatusCode(401);
                responseDto.setStatusMessage("User is not logged in");
                return responseDto;
            }
            irsTransmittalDetailsDto.setUpdatedBy(userName);
            irsTransmittalDetailsDto.setUpdatedDate(new Date());
            int countCode = opsPortalDataService.saveTransmissionRecord(irsTransmittalDetailsDto);
            ResponseDto responseDto = new ResponseDto();
            if(countCode > 0) {
                responseDto.setCount(countCode);
                responseDto.setStatusMessage("Duplicate Receipt Id");
            } else {
                responseDto.setStatusCode(1);
                responseDto.setStatusMessage("Successfully saved a record");
            }

        return responseDto;
    }

    @RequestMapping(value="/rejectResendCorrection", method = RequestMethod.POST)
    public @ResponseBody
        ResponseDto rejectResendCorrection(@RequestBody IrsTransmittalDetailsDto irsTransmittalDetailsDto) {
            String userName = null;
            UserSession userSession = null;
            ResponseDto responseDto = null;
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if (securityContext != null && securityContext.getAuthentication() != null) {
                Object principal = securityContext.getAuthentication().getPrincipal();
                if (principal != null && principal instanceof UserSession) {
                    userSession = (UserSession) securityContext.getAuthentication().getPrincipal();
                    userName = userSession.getFirstName() + CommonDataConstants.EMPTY_VALUE + userSession.getLastName();
                }
            }
            if(userName == null) {
                logger.error("User is not logged in");
                responseDto = new ResponseDto();
                responseDto.setStatusCode(401);
                responseDto.setStatusMessage("User is not logged in");
                return responseDto;
            }
            if(irsTransmittalDetailsDto.isRejectedStatusCorrection() || irsTransmittalDetailsDto.isRejectedStatusResend()) {
                String recordStatus = null;
                if(irsTransmittalDetailsDto.isRejectedStatusCorrection()) {
                    recordStatus = XmlConstants.STATUS_CD_RC;
                } else if(irsTransmittalDetailsDto.isRejectedStatusResend()) {
                    recordStatus = XmlConstants.STATUS_CD_RR;
                }
                int responseCode = opsPortalDataService.rejectResendCorrection(irsTransmittalDetailsDto.getTransmissionId(),
                        recordStatus, userName, irsTransmittalDetailsDto.getTaxYear());
                responseDto = new ResponseDto();
                responseDto.setStatusCode(responseCode);
                if(responseCode > 0) {
                    responseDto.setStatusMessage("Corrected records exist.");
                } else {
                    responseDto.setStatusMessage("Successfully saved a record");
                }
            }
        return responseDto;
    }
}
