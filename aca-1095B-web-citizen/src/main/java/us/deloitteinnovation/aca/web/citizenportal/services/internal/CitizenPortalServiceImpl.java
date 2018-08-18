package us.deloitteinnovation.aca.web.citizenportal.services.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.FilerDemographicCP;
import us.deloitteinnovation.aca.entity.PrintDetail;
import us.deloitteinnovation.aca.entity.SourceSystemConfig;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.CoveredPersonMapper;
import us.deloitteinnovation.aca.repository.FilerDemographicRepository;
import us.deloitteinnovation.aca.web.citizenportal.common.CitizenPortalConstants;
import us.deloitteinnovation.aca.web.citizenportal.repository.PrintDetailsRepository;
import us.deloitteinnovation.aca.web.citizenportal.repository.SelectedStatesRepository;
import us.deloitteinnovation.aca.web.citizenportal.repository.SourceSysConfRepository;
import us.deloitteinnovation.aca.web.citizenportal.services.ICitizenPortalService;
import us.deloitteinnovation.aca.web.citizenportal.vo.UserInfo;

import javax.persistence.NoResultException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * this will be the service class for user  auth service
 * Created by tthakore on 9/28/2015.
 */

@Service("citizenPortalService")
@Transactional
public class CitizenPortalServiceImpl implements ICitizenPortalService {

    private static final Logger logger =
            LoggerFactory.getLogger(CitizenPortalServiceImpl.class);

    private FilerDemographicRepository citizenPortalServiceRepository;

    private SelectedStatesRepository selectedStatesRepository;
    private SourceSysConfRepository systemcofigmsg;


    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Autowired
    public CitizenPortalServiceImpl(FilerDemographicRepository citizenPortalServiceRepository,
                                    SelectedStatesRepository selectedStatesRepository,
                                    SourceSysConfRepository systemcofigmsg
                                   ) {
        this.citizenPortalServiceRepository = citizenPortalServiceRepository;

        this.selectedStatesRepository = selectedStatesRepository;
        this.systemcofigmsg = systemcofigmsg;
    }

    @Override
    public FilerDemographicCP verifyUserInformation(UserInfo userinfo){
        logger.info("[Info]  verifyUserInformation" + userinfo.toString());
        List<FilerDemographicCP> results = new ArrayList<FilerDemographicCP>();
        FilerDemographicCP blankObject;
        try{
            if("SSN".equalsIgnoreCase(userinfo.getUidType()))
            {
                results = this.citizenPortalServiceRepository.verifyUserInformationSSN(userinfo.getUserLname(),
                        userinfo.getUidNumber(),
                        userinfo.getDOBDateFormat(),
                        userinfo.getCity(), userinfo.getState(), userinfo.getZipcode(), userinfo.getUserSelectedState(), Integer.valueOf(userinfo.getUserSelectedTaxYear()));
            }
            else
            {
                results = this.citizenPortalServiceRepository.verifyUserInformationTIN(userinfo.getUserLname(),
                        userinfo.getUidNumber(),
                        userinfo.getDOBDateFormat(),
                        userinfo.getCity(), userinfo.getState(), userinfo.getZipcode(), userinfo.getUserSelectedState(), Integer.valueOf(userinfo.getUserSelectedTaxYear()));
            }
        }
        catch (Exception e)
        {
            logger.error("Error in verify user..",e);
        }

        if(!results.isEmpty() &&  validateUserInformation(userinfo, results) && !(((FilerDemographicCP) results.get(0)).getFilerStatus().equals("C")) && (((FilerDemographicCP) results.get(0)).getFormStatus().length() > 0))
        {
            logger.info("<---------------form status is----------------->" + ((FilerDemographicCP) results.get(0)).getFormStatus());
            FilerDemographicCP filerDemographicinfo = results.get(0);
            filerDemographicinfo.setSourcesystemconfig(getSystemMessages(userinfo.getUserSelectedState()));
            filerDemographicinfo.setIsUserFound(true);
            logger.info("[Info]  user found for this record ");
           return filerDemographicinfo;
        }
        else if(!results.isEmpty() && ((FilerDemographicCP) results.get(0)).getFilerStatus().equals("C"))
        {

                blankObject = new FilerDemographicCP();
                blankObject.setSourcesystemconfig(getSystemMessages(userinfo.getUserSelectedState()));
                blankObject.getSourcesystemconfig().setNotFoundMessage(blankObject.getSourcesystemconfig().getNotFoundMessage() + " or Please contact your head of the house hold.");
                blankObject.setIsUserFound(false);
            logger.info("[Info]  user found for this record  user type C");
        }
        else
        {
            blankObject = new FilerDemographicCP();
            blankObject.setSourcesystemconfig(getSystemMessages(userinfo.getUserSelectedState()));
            blankObject.setIsUserFound(false);
            logger.info("[Info] no user found for this record.");
        }
        return blankObject;
    }




    @Override
    public List<SourceSystemConfig> getSelectedStateList()
    {
        List<SourceSystemConfig> result;
        try{
            result = selectedStatesRepository.getAvailableStates();
        }
        catch(NoResultException e)
        {
            result = new ArrayList<>();
            logger.error("Exception in getSelectedStateList ", e);
        }
        return result;
    }

    private SourceSystemConfig getSystemMessages(String sourceCd)
    {
        List<SourceSystemConfig> systemConfigs;
        systemConfigs = systemcofigmsg.findBystateAbbreviation(sourceCd);
        if(!systemConfigs.isEmpty())
            return systemConfigs.get(0);
        return new SourceSystemConfig();
    }

    private Boolean validateUserInformation(UserInfo userinfo, List<FilerDemographicCP> results)
    {
        Boolean isUserValidated = true;
        if(!results.isEmpty())
        {
            FilerDemographicCP filer = results.get(0);
            if(userinfo.getUserFname().equals("") && userinfo.getStreetAddress1().equals("") && userinfo.getStreetAddress2().equals(""))
            {
                isUserValidated = true;
            }
            if((!userinfo.getUserFname().equals("")) && (!userinfo.getUserFname().equalsIgnoreCase(filer.getRecepientFirstName())))
                isUserValidated = false;

            if((!userinfo.getStreetAddress1().equals("")) && (!userinfo.getStreetAddress1().equalsIgnoreCase(filer.getRecepientAddressLine1())))
                isUserValidated = false;

            if((!userinfo.getStreetAddress2().equals("")) && (!userinfo.getStreetAddress2().equals(filer.getRecepientAddressLine2())))
                isUserValidated = false;

            if((!userinfo.getUserSelectedState().equals("")) && (!(userinfo.getUserSelectedState().equals(filer.getId().getSourceCd().substring(0,2)))))
                isUserValidated = false;
        }
       return isUserValidated;
    }

    @Override
    public List<CoveredPerson> getCoveredPersonListWithTaxYR(long responsiblePersonUniqueId, String sourceCd, String taxYear){
        List<CoveredPerson> rows = null;
        String sqlStatment = "SELECT * FROM FILER_DEMOGRAPHICS WHERE RESPONSIBLE_PERSON_UNIQUE_ID = " + responsiblePersonUniqueId
                + " AND FILER_STATUS = 'C' AND STATUS = 'ACTIVE' AND SOURCE_CD = '"+sourceCd+"' AND TAX_YEAR = "+taxYear;
        try{
            //TODO Change this call to use a PreparedStatment instead of the regular Statement
            rows = jdbcTemplate.query(sqlStatment, new CoveredPersonMapper());
        }catch(Exception ex){
            logger.error("", ex);
        }
        return rows;
    }

    @Override
    public String getCorrectionIndicator(long sourceuniqueID, String sourceCd, String taxYear){

     return getCorrectionIndicator(sourceCd, sourceuniqueID, taxYear);
    }

    private String getCorrectionIndicator(String sourceCd, long sourceUniqueId, String taxYear)
    {
        String correctionIndicator = "";
        try{


            StringBuilder builder = new StringBuilder("select CORRECTION_INDICATOR from filer_demographics ");
            builder.append(" where source_unique_id = '"+sourceUniqueId+"' and source_cd = '"+sourceCd+"' and tax_year = "+taxYear);
            List<String> correctionIdicatorList =  this.jdbcTemplate.queryForList(builder.toString(), String.class);

            if (correctionIdicatorList.isEmpty()) {
                correctionIndicator = "0";
            } else {
                correctionIndicator = (correctionIdicatorList.get(0) != null)?((String) correctionIdicatorList.get(0)):"0";
            }
        }catch(Exception ex){
            logger.error("--------------------------------------------------------------------------");
            logger.error("Error in IrsTransmittalDetailsDS class method getTransmitStatus()", ex);
            logger.error("--------------------------------------------------------------------------");
        }
        return correctionIndicator;
    }
}
