package us.deloitteinnovation.aca.web.caseworkerportal.services.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.batch.dataservice.CoveredPersonDataService;
import us.deloitteinnovation.aca.batch.dataservice.IrsTransmittalDetailsDS;
import us.deloitteinnovation.aca.batch.dataservice.PrintDetailsUpdateDS;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.entity.*;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.CoveredPersonMapper;
import us.deloitteinnovation.aca.model.FilerMapper;
import us.deloitteinnovation.aca.repository.CWFilerDemographicRepository;
import us.deloitteinnovation.aca.repository.FilerCoverageSourceRepository;
import us.deloitteinnovation.aca.repository.FilerDemographicRepositoryCW;
import us.deloitteinnovation.aca.security.UserSession;
import us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto.*;
import us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.util.CaseWorkerUtility;
import us.deloitteinnovation.aca.web.caseworkerportal.repository.*;
import us.deloitteinnovation.aca.web.caseworkerportal.services.ICaseworkerPortalService;
import us.deloitteinnovation.aca.web.caseworkerportal.vo.SearchFilerInfo;
import us.deloitteinnovation.aca.web.common.MessageService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static us.deloitteinnovation.aca.constants.CommonDataConstants.*;

//persistence import

/**
 * Created by ritmukherjee on 10/10/2015.
 */


@Service
@Transactional
public class CaseworkerPortalServiceImpl implements ICaseworkerPortalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaseworkerPortalServiceImpl.class);

    private static final int CORRECTION_INDICATOR_ZERO = 0;
    private static final int CORRECTION_INDICATOR_ONE = 1;
    private static final int CORRECTION_INDICATOR_TWO = 2;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IrsTransmittalDetailsDS irsttransmittaldetails;

    @Autowired
    private CoveredPersonDataService coveredPersonDataService;


    @Autowired
    private MessageService messageService;
    @Autowired
    private PrintDetailsUpdateDS printDetailsUpdateDS;

    @Autowired
    FilerMapper filerMapper;

    private FilerDemographicRepositoryCW caseWorkerFilerDemographicRepositoryCW;


    FilerCoverageSourceRepositoryCW caseWorkerFilerCoverageSourceRepositoryCW;

    private FilerCoverageSourceRepository filerCoverageSourceRepository;

    AuditFilerDemographicRepositoryCW caseWorkerAuditFilerDemographicRepositoryCW;

    @Autowired
    AuditFilerDemographicRepository auditFilerDemographicRepository;


    @Autowired
    PrintDetailRepositoryCW printDetailRepositoryCW;


    CWFilerDemographicRepository cwFilerDemographicRepository;

    @Autowired
    public CaseworkerPortalServiceImpl(FilerDemographicRepositoryCW caseWorkerFilerDemographicRepositoryCW,
                                       FilerCoverageSourceRepositoryCW caseWorkerFilerCoverageSourceRepositoryCW,
                                       FilerCoverageSourceRepository filerCoverageSourceRepository,
                                       AuditFilerDemographicRepositoryCW caseWorkerAuditFilerDemographicRepositoryCW,
                                       CWFilerDemographicRepository cwFilerDemographicRepository) {
        this.caseWorkerFilerDemographicRepositoryCW = caseWorkerFilerDemographicRepositoryCW;
        this.caseWorkerFilerCoverageSourceRepositoryCW = caseWorkerFilerCoverageSourceRepositoryCW;
        this.caseWorkerAuditFilerDemographicRepositoryCW = caseWorkerAuditFilerDemographicRepositoryCW;
        this.cwFilerDemographicRepository = cwFilerDemographicRepository;
        this.filerCoverageSourceRepository = filerCoverageSourceRepository;
    }

    @Autowired
    private JdbcTemplate template;

    @Autowired
    CaseWorkerUtility caseWorkerUtility;


    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method is to find out List<FilerDemographic></FilerDemographic> based on provided criteria
     *
     * @param ssn
     * @param taxYear
     * @return List:Filers
     */
    @Override
    public List<FilerDemographicCW> findFilersSSN_TaxYear(String ssn, String recepientState, int taxYear) throws RuntimeException {
        return cwFilerDemographicRepository.findByRecepientSsnAndId_TaxYearAndId_SourceCdStartingWith(ssn, taxYear, recepientState);
    }

    /**
     * This method is to find out FilerDemographics based on provided criteria
     *
     * @param TIN
     * @param taxYear
     * @return List of filers
     */
    @Override
    public List<FilerDemographicCW> findFilersTIN_TaxYear(String TIN, String recepientState, int taxYear) throws RuntimeException {

        LOGGER.info("Inside class::" + LOGGER.toString() + ", Method :[findFilersTIN_TaxYear(String TIN, int taxYear)]");
        LOGGER.debug("{DEBUG]Inside class:" + LOGGER.toString() + ", Method :[findFilersSSN_TaxYear(String " + TIN + ", int taxYear)]");
        List<FilerDemographicCW> filerDemographicCWs = cwFilerDemographicRepository.findFilersTIN_TaxYear(TIN, recepientState, taxYear);
        return filerDemographicCWs;
    }

    /**
     * @param recepientLastName
     * @param recepientDOB
     * @param taxYear
     * @return LIst of filers
     * @throws
     * @purpose This method is to find out FilerDemographics based on provided criteria
     */
    @Override
    public List<FilerDemographicCW> findFilersLastName_DOB_TaxYear(String recepientLastName, String recepientState, String recepientDOB, int taxYear) throws RuntimeException {

        LOGGER.info("Inside class:" + LOGGER.toString() + ", Method :[findFilersLastName_DOB_TaxYear(String recepientLastName, String recepientDOB, int taxYear)]");
        Date dt = new Date();
        String[] parts = recepientDOB.split(CommonDataConstants.OBLIQUE_VALUE);

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(CommonDataConstants.INPUT_DATE_FORMAT);

            dt = formatter.parse(parts[2] + "-" + parts[0] + "-" + parts[1]);
        } catch (ParseException e) {
            LOGGER.error("{ERROR]Parsing Exception:" + e.toString());
            e.printStackTrace();
        }
        List<FilerDemographicCW> filerDemographicCWs = cwFilerDemographicRepository.findFilersLAST_NAME_DOB_TaxYear(recepientLastName, recepientState, dt, taxYear);
        return filerDemographicCWs;
    }


    /**
     * @param recepientFirstName
     * @param recepientLastName
     * @param recepientDOB
     * @param taxYear
     * @return LIst of filers
     * @throws
     * @purpose This method is to find out FilerDemographics based on provided criteria
     */
    @Override
    public List<FilerDemographicCW> findFilersFirstName_LastName_DOB_TaxYear(String recepientFirstName, String recepientLastName, String recepientState, String recepientDOB, int taxYear) throws RuntimeException {

        LOGGER.info("Inside class:" + LOGGER.toString() + ", Method :[findFilersLastName_DOB_TaxYear(String recepientLastName, String recepientDOB, int taxYear)]");

        Date dt = new Date();

        String[] parts = recepientDOB.split(CommonDataConstants.OBLIQUE_VALUE);


        try {
            SimpleDateFormat formatter = new SimpleDateFormat(CommonDataConstants.INPUT_DATE_FORMAT);

            dt = formatter.parse(parts[2] + "-" + parts[0] + "-" + parts[1]);
        } catch (ParseException e) {
            LOGGER.error("{ERROR]Parsing Exception:" + e.toString());
        }

        List<FilerDemographicCW> filerDemographicCWs = cwFilerDemographicRepository.findFilersFirstName_LastName_DOB_TaxYear(recepientLastName, recepientFirstName, recepientState, dt, taxYear);
        return filerDemographicCWs;
    }

    /**
     * @param documentId
     * @return String
     * @throws RuntimeException
     * @purpose Based on document Id try to find out day difference between present day and last mail requested date
     * @author R. Mukherhee
     * @since 14.10.2015
     */
    @Override
    public String getDocumentRequestValidty(String documentId) {

        LOGGER.info("Inside class:" + LOGGER.toString() + ", Method :getDocumentRequestValidty( " + documentId + ")");
        String[] parts = documentId.split("_");
/*        String sourceCd = parts[0] + "_" + parts[1].toString();
        long sourceUniqueId = (Long.valueOf(parts[2]));*/

        long sourceUniqueId = 0;
        String sourceCd = "";
        if (parts.length > 0) {
            if (parts.length == 3) {
                sourceCd = parts[0] + "_" + parts[1].toString();
                sourceUniqueId = (Long.valueOf(parts[2]));
            }
            if (parts.length == 2) {
                sourceCd = parts[0];
                sourceUniqueId = (Long.valueOf(parts[1]));
            }
        }

        String mailRequestValidity = "";
        List<FilerDemographic> results = new ArrayList<FilerDemographic>();
        CustomerInfo customerInfo = new CustomerInfo();
        FilerDemographic filerDemographic = new FilerDemographic();
        try {
            results = this.caseWorkerFilerDemographicRepositoryCW.getCustomerDetails(sourceUniqueId, sourceCd);
        } catch (RuntimeException re) {
            LOGGER.error("[ERROR]Inside :class:" + LOGGER.getClass().getName() + ",method:getCustomerInfo()] error is:" + re.getMessage());
        }
        return mailRequestValidity;

    }

    /**
     * {Helper Method: compare between last mail requested date and present date}
     *
     * @param sourceUniqueId
     * @param sourceCd
     * @return
     */
    @Override
    public String getMailRequestValidity(long sourceUniqueId, String sourceCd) {
        LOGGER.info("[INFO]Inside class:" + LOGGER.toString() + ", Method :getMailRequestValidity");

        String status = "";
        Date lastmaildate = new Date();
        int diffInDays = 0;

        try {

            StringBuilder sb = new StringBuilder();
            sb.append("select t from " + FilerDemographic.class.getName());
            sb.append(" t where t.id.sourceUniqueId = :sourceUniqueId AND ");
            sb.append("  t.id.sourceCd = :sourceCd");
            String query = sb.toString();


            List<FilerDemographic> demographics = entityManager.createQuery(query)
                    .setParameter("sourceUniqueId", sourceUniqueId)
                    .setParameter("sourceCd", sourceCd)
                    .getResultList();




            //Mathemetical compare the fifference of day
            diffInDays = (int) ((new Date().getTime() - lastmaildate.getTime())
                    / (1000 * 60 * 60 * 24));


        } catch (Exception e) {
            LOGGER.error("Inside class:" + LOGGER.toString() + ", Method :getMailRequestValidity: Error:" + e.getMessage());
        }


        if (diffInDays >= 5) {
            status = "valid request";
        } else {
            status = "request not allowed within 5 days";
        }
        return status;
    }

    /**
     * @param customerID
     * @return IndividualFormInfo
     * @throws RuntimeException
     * @author R. Mukherjee
     * @purpose This Service method responsible to populate:
     * <IndividualFormInfo>
     * 1. Form details of Each type Customer:Responsible/Covered/Non Covered
     * 2. <Form1095BInfo></Form1095BInfo>
     * 3. <List>Audit:<Form1095BInfo></Form1095BInfo></List>
     * </IndividualFormInfo>
     * @since 11/30/2015
     */
    @Override
    public IndividualFormInfo getIndividualFormDetails(String customerID) {
        LOGGER.debug("[DEBUG]Inside class:" + LOGGER.toString() + ", Method :getIndividualFormDetails");

        String[] parts = customerID.split("_");
/*        String sourceCd = parts[0] + "_" + parts[1].toString();
        long sourceUniqueId = (Long.valueOf(parts[2]));*/

        long sourceUniqueId = 0;
        String sourceCd = "";
        if (parts.length > 0) {
            if (parts.length == 3) {
                sourceCd = parts[0] + "_" + parts[1].toString();
                sourceUniqueId = (Long.valueOf(parts[2]));
            }
            if (parts.length == 2) {
                sourceCd = parts[0];
                sourceUniqueId = (Long.valueOf(parts[1]));
            }
        }

        IndividualFormInfo individualFormInfo = new IndividualFormInfo();
        List<FilerDemographic> demographicResults = new ArrayList<FilerDemographic>();
        FilerDemographic filerDemographic = new FilerDemographic();
        try {

           /* fetch particular Filers demographic details*/
            demographicResults = this.caseWorkerFilerDemographicRepositoryCW.getCustomerDetails(sourceUniqueId, sourceCd);
            if (demographicResults.size() > 0) {
                filerDemographic = demographicResults.get(0);
            }

            /*fetch historic audit details of the filer*/
            List<FilerDemographicAudit> demographicAudits = this.caseWorkerAuditFilerDemographicRepositoryCW.getCustomerAuditDetails(sourceUniqueId, sourceCd);

            individualFormInfo = caseWorkerUtility.getIndividualFormDetails(filerDemographic);

        } catch (RuntimeException re) {
            LOGGER.error("[ERROR]Inside :class:" + LOGGER.getClass().getName() + ",method:viewPdfInfo()] error is:" + re.getMessage());
        }
        LOGGER.debug("[DEBUG]Execution complete:class:" + LOGGER.getClass() + "method:getIndividualFormDetails()");
        return individualFormInfo;

    }


    /**
     * @param formID
     * @param userSession
     * @return ViewFormInfo
     * @throws RuntimeException
     * @author R. Mukherjee
     * @purpose This method responsible to populate:
     * <ViewFormInfo>
     * 1. <List>All type Customers(Responsible Customer,Covered Person, Non Covered Person)</List>
     * 2. <Form1095BInfo></Form1095BInfo>
     * 3. <List>Audit:<Form1095BInfo></Form1095BInfo></List>
     * </ViewFormInfo>
     * @since 11/25/2015
     */

    @Override
    public ViewFormInfo getViewFormDetails(String formID, UserSession userSession, String stateCd) {
        LOGGER.debug("[DEBUG]Inside class:" + LOGGER.toString() + ", Method :getViewFormDetails()");

        ViewFormInfo viewFormInfo = new ViewFormInfo();
        try {

            FilerDemographicPK filerDemographicPK = new FilerDemographicPK(formID);

            /* Get a list of current covered persons */
            List<FilerDemographicCW> currentDemographicResults =
                    cwFilerDemographicRepository.findByresponsiblePersonUniqueIdAndId_SourceCdAndId_TaxYear(
                            filerDemographicPK.getSourceUniqueId(),
                            filerDemographicPK.getSourceCd(),
                            filerDemographicPK.getTaxYear());

            /* Get a list of audited responsible persons */
            List<AuditFilerDemographic> auditFilerDemographicsResults =
                    auditFilerDemographicRepository.findBySourceUniqueIdAndSourceCdAndTaxYear(
                            filerDemographicPK.getSourceUniqueId(),
                            filerDemographicPK.getSourceCd(),
                            filerDemographicPK.getTaxYear());

            /* Compile payload object */
            viewFormInfo = this.caseWorkerUtility.viewFormDetails(
                    currentDemographicResults,
                    auditFilerDemographicsResults,
                    filerDemographicPK.getSourceUniqueId(),
                    filerDemographicPK.getSourceCd(),
                    filerDemographicPK.getTaxYear());

        } catch (Exception e) {
            LOGGER.error("Failed in getViewFormDetails. ", e);
        }
        LOGGER.info("[INFO]Execution complete:class:" + LOGGER.getClass() + "method:getViewFormDetails()");
        return viewFormInfo;

    }




    /**
     * @param customerID
     * @return List<CoverageSourceInfo>
     * @throws RuntimeException
     * @author R. Mukherjee
     * @purpose This service method based on customerID fetch List<CoverageSourceInfo>
     * @since 11.05.2015
     */
    @Override
    public List<CoverageSourceInfo> getCoverageSourceInfo(String customerID) {

      /*  splitting customerID to SourceUniqueId & sourceCd*/
        String[] parts = customerID.split("_");
       /* String sourceCd = parts[0] + "_" + parts[1].toString();
        long sourceUniqueId = (Long.valueOf(parts[2]));*/
        long sourceUniqueId = 0;
        String sourceCd = "";
        if (parts.length > 0) {
            if (parts.length == 3) {
                sourceCd = parts[0] + "_" + parts[1].toString();
                sourceUniqueId = (Long.valueOf(parts[2]));
            }
            if (parts.length == 2) {
                sourceCd = parts[0];
                sourceUniqueId = (Long.valueOf(parts[1]));
            }
        }

        LOGGER.info("[INFO]Inside class:" + LOGGER.toString() + ", Method :getCoverageSourceInfo(" + sourceUniqueId + "," + sourceCd + ",");

        List<FilerCoverage> results = new ArrayList<FilerCoverage>();
        List<CoverageSourceInfo> coverages = new ArrayList<CoverageSourceInfo>();

        try {
            results = this.caseWorkerFilerCoverageSourceRepositoryCW.getFilerCoverageSources(sourceUniqueId, sourceCd);
            if (results.size() > 0) {

                coverages = caseWorkerUtility.getCustomerSourceCoverages(results);
            }

        } catch (RuntimeException re) {
            LOGGER.error("[ERROR]Inside :class:" + LOGGER.getClass().getName() + ",method:[getCustomerInfo()] error is:", re);
        }
        LOGGER.debug("[INFO]Execution complete:class:[" + LOGGER.getClass() + "].method:[getCoverageSSourceInfo())]");
        return coverages;
    }


    /**
     * @param customerID
     * @param userSession
     * @return CorrectCustomerInfo
     * @throws RuntimeException
     * @author R. Mukherjee
     * @purpose This method is responsible to fetch existing customer information for correction purpose,
     * @since 11/09/20145
     */
    @Override
    public CorrectCustomerInfo getExistingCustomerInfo(String customerID, UserSession userSession) {

      /*  splitting customerID to SourceUniqueId & sourceCd*/
        String[] parts = customerID.split("_");
        /*String sourceCd = parts[0] + "_" + parts[1].toString();
        long sourceUniqueId = (Long.valueOf(parts[2]));*/

        long sourceUniqueId = 0;
        String sourceCd = "";
        Integer taxYear = 0;
        if (parts.length > 0) {
            if (parts.length == 3) {
                sourceCd = parts[0];
                sourceUniqueId = (Long.valueOf(parts[1]));
                taxYear = (Integer.valueOf(parts[2]));
            }
        }
        LOGGER.debug("[DEBUG]Inside class:" + LOGGER.toString() + ", Method :getCustomerInfo(" + sourceUniqueId + "," + sourceCd + ","+ taxYear);
        List<FilerDemographicCW> results = new ArrayList<>();
        CorrectCustomerInfo customerInfo = new CorrectCustomerInfo();
        FilerDemographicCW customerDemographic = new FilerDemographicCW();
        FilerDemographicCW responsibleDemographic = new FilerDemographicCW();


        try {
            results = this.cwFilerDemographicRepository.getCustomerDetails(sourceUniqueId, sourceCd, taxYear);

            /*getting responsible filer of that customer*/
            responsibleDemographic = cwFilerDemographicRepository.getResponsibleFiler(sourceUniqueId, sourceCd, taxYear);
            if (results.size() > 0) {
                customerDemographic = results.get(0);

                customerInfo = caseWorkerUtility.getExistingCustomerInfo(customerDemographic, responsibleDemographic, userSession);
            }


        } catch (RuntimeException re) {
            LOGGER.error("[ERROR]Inside :class:" + LOGGER.getClass().getName() + ",method:getCustomerInfo()] error is:" , re);
        }
        LOGGER.debug("[DEBUG]Execution complete:class:" + LOGGER.getClass() + "method:getCustomerInfo()");
        return customerInfo;
    }


    /**
     * @param correctCustomerInfo
     * @param userInfo
     * @param userSession
     * @return
     * @throws RuntimeException
     * @author R. Mukherjee
     * @purpose This method is responsible to retrieve existing customer info FilerDemographic
     * @since 12/07/2016
     */
    @Override
    public RecordUpdateInfo updateExistingCustomerInfo(CorrectCustomerInfo correctCustomerInfo, String userInfo, UserSession userSession) {
        if (LOGGER.isInfoEnabled())
            LOGGER.info("Inside class:[" + LOGGER.getClass().getName() + "], method [updateExistingCustomerInfo()] ");
        RecordUpdateInfo recordUpdateInfo = new RecordUpdateInfo();
        recordUpdateInfo.setUpdateStatus(MAGIC_NUMBER_ZERO);
        recordUpdateInfo.setError(Boolean.TRUE);

        /*=========================Retrieve filer's personal Information and form information from object'===============*/
        /* Obtain the responsible person */
        FilerDemographicPK responsiblePk = new FilerDemographicPK(correctCustomerInfo.getCurrentForm().getFormID());
        FilerDemographicCW responsible = cwFilerDemographicRepository.findOne(responsiblePk);

        /* If responsible person does not exist, i.e. formId invalid */
        if (null == responsible) {
            recordUpdateInfo.setMessage("Invalid form ID");
            return recordUpdateInfo;
        }

        /* Stop the process if filer XML created */
        if ("XC".equalsIgnoreCase(responsible.getIrsTransmissionStatusCd())) {
            recordUpdateInfo.setMessage("Cannot modify record during XML status lifecycle: XC");
            return recordUpdateInfo;
        }


       /* _updateDate: Current date has used to maintain update time*/
        Date _updateDate = new java.sql.Date(System.currentTimeMillis());

        MonthCoverageInfo[] demographicCoverageInfos = new MonthCoverageInfo[CommonDataConstants.COVERAGE_MONTHS.length];

        for (int i = 0; i < demographicCoverageInfos.length; i++) {
            MonthCoverageInfo monthCoverageInfo = new MonthCoverageInfo();
            monthCoverageInfo.setMonth(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).get(i));
            monthCoverageInfo.setCovered('0');
            demographicCoverageInfos[i] = monthCoverageInfo;
        }


        try {
            /* FIRST DO UPDATE OF COVERAGE_SOURCE___________________________________________________________________   */
            /*===================START:update FILER_COVERAGE_SOURCE based on provided details by caseworker============*/
            for (CoverageSourceInfo coverageSourceInfo : correctCustomerInfo.getCoverageSources()) {

                String[] coverage_Parts = coverageSourceInfo.getCoverageSourceId().split(CommonDataConstants.UNDERSCORE_VALUE);
                String coverage_SourceCd = CommonDataConstants.EMPTY_VALUE;
                long coverage_SourceUniqueId = 0;
                long coverage_coverageSequenceNo = 0;


                if (coverage_Parts.length > 0) {
                    if (coverage_Parts.length == 4) {
                        coverage_SourceCd = coverage_Parts[0] + CommonDataConstants.UNDERSCORE_VALUE + coverage_Parts[1].toString();
                        coverage_SourceUniqueId = (Long.valueOf(coverage_Parts[2]));
                        coverage_coverageSequenceNo = (Long.valueOf(coverage_Parts[3]));
                    }
                    if (coverage_Parts.length == 3) {
                        coverage_SourceCd = coverage_Parts[0];
                        coverage_SourceUniqueId = (Long.valueOf(coverage_Parts[1]));
                        coverage_coverageSequenceNo = (Long.valueOf(coverage_Parts[2]));
                    }
                }
                for (int i = 0; i < coverageSourceInfo.getFilerSourceInfo().size(); i++) {
                    MonthCoverageInfo monthCoverageInfo = coverageSourceInfo.getFilerSourceInfo().get(i);
                    if (monthCoverageInfo.getCovered() == CommonDataConstants.COVERED) {
                        demographicCoverageInfos[i].setMonth(monthCoverageInfo.getMonth());
                        demographicCoverageInfos[i].setCovered(monthCoverageInfo.getCovered());
                    }
                }

                if (correctCustomerInfo.isCoverageUpdated()) {
                    int _updateCoverageSourceStatus = caseWorkerFilerCoverageSourceRepositoryCW.updateCustomerCoverageSourceDetails(
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.JAN)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.FEB)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.MAR)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.APR)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.MAY)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.JUN)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.JUL)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.AUG)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.SEP)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.OCT)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.NOV)).getCovered(),
                            coverageSourceInfo.getFilerSourceInfo().get(Arrays.asList(CommonDataConstants.COVERAGE_MONTHS).indexOf(CommonDataConstants.DEC)).getCovered(),
                            coverageSourceInfo.getComments(), userInfo, _updateDate, coverage_SourceUniqueId, coverage_SourceCd, coverage_coverageSequenceNo

                    );
                    if (LOGGER.isDebugEnabled())
                        LOGGER.debug("Update happen for :" + coverageSourceInfo.getCoverageSourceId() + ", update status is:" + _updateCoverageSourceStatus);
                }
            }

            /*===================END:update FILER_COVERAGE_SOURCE based on provided details by caseworker============*/
            /*====================START: Update FILER_DEMOGRAPHICS details of customer=========================== */

            /* Obtain a list of covered person */
            List<FilerDemographicCW> covered = cwFilerDemographicRepository.findByresponsiblePersonUniqueIdAndId_SourceCdAndId_TaxYear(
                    responsiblePk.getSourceUniqueId(), responsiblePk.getSourceCd(), responsiblePk.getTaxYear());

            /* Compile all affected persons (responsible and covered) into a key map for easy lookup */
            Map<Long, FilerDemographicCW> affectedPersons = new HashMap<>();
            affectedPersons.put(responsiblePk.getSourceUniqueId(), responsible);
            for (FilerDemographicCW coveredPerson: covered) {
                affectedPersons.put(coveredPerson.getId().getSourceUniqueId(), coveredPerson);
            }

            /* Update the subject in key map */
            FilerDemographicPK subjectPk = new FilerDemographicPK(correctCustomerInfo.getFilerInfo().getFilerID());
            FilerDemographicCW subject = affectedPersons.get(subjectPk.getSourceUniqueId());
            subject.setRecepientFirstName(correctCustomerInfo.getFilerInfo().getFirstName());
            subject.setRecepientLastName(correctCustomerInfo.getFilerInfo().getLastName());
            subject.setRecepientDob(new SimpleDateFormat("MM/dd/yyyy").parse(correctCustomerInfo.getFilerInfo().getDob()));
            subject.setEMail(correctCustomerInfo.getCurrentForm().getEmail());

            /* Update address and comment info */
            for (Map.Entry<Long, FilerDemographicCW> entry : affectedPersons.entrySet()) {
                entry.getValue().setRecepientAddressLine1(correctCustomerInfo.getCurrentForm().getAddressLine1());
                entry.getValue().setRecepientAddressLine2(correctCustomerInfo.getCurrentForm().getAddressLine2());
                entry.getValue().setRecepientCity(correctCustomerInfo.getCurrentForm().getCity());
                entry.getValue().setRecepientState(correctCustomerInfo.getCurrentForm().getState());
                entry.getValue().setRecepientZip5(correctCustomerInfo.getCurrentForm().getZipcode());
                entry.getValue().setComments(correctCustomerInfo.getCurrentForm().getComments());
                entry.getValue().setUpdatedBy(userInfo);
            }

            /* Update the TIMESTAMP and VERSION_NUM of all persons in the key map */
            int version = responsible.getRecordVersion() + 1;
            Date now = new Date();
            for (Map.Entry<Long, FilerDemographicCW> entry : affectedPersons.entrySet()) {
                entry.getValue().setUpdatedDt(now);
                entry.getValue().setRecordVersion(version);
                entry.getValue().setUpdatedBy(userInfo);
            }

            /* Update the FORM_STATUS of responsible person conditionally; NULL -> NULL; otherwise -> REGENERATE */
            if (null != responsible.getFormStatus() && !"null".equalsIgnoreCase(responsible.getFormStatus())) {
                responsible.setFormStatus("REGENERATE");
            }

            /* Update Correction Indicator */
            String irsTransimissionStatusCode = responsible.getIrsTransmissionStatusCd();
            int targetCorrectionIndicator = responsible.getCorrectionIndicator();
            if (null != responsible.getFormStatus() && !"null".equalsIgnoreCase(responsible.getFormStatus())) {
                switch (responsible.getCorrectionIndicator()) {
                    case CORRECTION_INDICATOR_ZERO:
                    case CORRECTION_INDICATOR_ONE:
                        targetCorrectionIndicator = conditionalCorrectionIndicator(irsTransimissionStatusCode);
                        break;
                    default:
                        // Do nothing
                        break;
                }
            }
            for (Map.Entry<Long, FilerDemographicCW> entry : affectedPersons.entrySet()) {
                entry.getValue().setCorrectionIndicator(targetCorrectionIndicator);
            }

            /* Update IrsTransmissionCOde */
            if (null != irsTransimissionStatusCode && !"DT".equalsIgnoreCase(irsTransimissionStatusCode)) {
                irsTransimissionStatusCode = "CO";
                responsible.setIrsTransmissionStatusCd(irsTransimissionStatusCode);
            }

            /* Update coverageSource info */
            updateCoverageSource(affectedPersons, correctCustomerInfo.getCoverageSources());

            /* Iterate through the key map and persist all persons */
            for (Map.Entry<Long, FilerDemographicCW> entry : affectedPersons.entrySet()) {
                cwFilerDemographicRepository.save(entry.getValue());
            }

            /*====================END: Update FILER_DEMOGRAPHICS details of customer=========================== */

            recordUpdateInfo.setUpdateStatus(1);
            recordUpdateInfo.setError(Boolean.FALSE);

        } catch (ParseException pe) {
            LOGGER.error("Failed to parse updated filer DOB: " + correctCustomerInfo.getFilerInfo().getDob());
            recordUpdateInfo.setMessage(pe.getMessage());

        } catch (Exception e) {
            LOGGER.error("Failed to save filer data.", e);
            recordUpdateInfo.setMessage(e.getMessage());
        }
        LOGGER.info("[INFO]Execution complete:class:" + LOGGER.getClass() + "method:updateExistingCustomerInfo()");
        return recordUpdateInfo;
    }

    private int conditionalCorrectionIndicator(String irsTransimissionStatusCode) {
        return null == irsTransimissionStatusCode || "dt".equalsIgnoreCase(irsTransimissionStatusCode)
                ? CORRECTION_INDICATOR_ONE
                : CORRECTION_INDICATOR_TWO;
    }

    private void updateCoverageSource(Map<Long, FilerDemographicCW> filerMap, List<CoverageSourceInfo> coverageSourceInfos) {
        Map<Long, Boolean> resetMap = new HashMap<>();

        /* Iterate through all updates made to coverageSources */
        for (CoverageSourceInfo coverageInfo : coverageSourceInfos) {
            FilerDemographicPK filerPk = new FilerDemographicPK(coverageInfo.getCoverageSourceId());
            long filerId = filerPk.getSourceUniqueId();

            /* If the update subject is valid,  update his/her coverageSource */
            if (filerMap.containsKey(filerId)) {
                FilerDemographicCW filer = filerMap.get(filerId);

                /* If if subject hasn't been reset, reset it */
                if (!resetMap.containsKey(filerId) || null == resetMap.get(filerId) || !resetMap.get(filerId)) {
                    resetCoverage(filer);
                    resetMap.put(filerId, Boolean.TRUE);
                }

                /* Append coverageSource data */
                appendCoverage(filer, coverageInfo.getFilerSourceInfo());
            }
        }
    }

    private void resetCoverage(FilerDemographicCW filer) {
        filer.setJan('0');
        filer.setFeb('0');
        filer.setMar('0');
        filer.setApr('0');
        filer.setMay('0');
        filer.setJun('0');
        filer.setJul('0');
        filer.setAug('0');
        filer.setSep('0');
        filer.setOct('0');
        filer.setNov('0');
        filer.setDec('0');
    }

    private void appendCoverage(FilerDemographicCW filer, Collection<MonthCoverageInfo> monthCoverageInfos) {
        for (MonthCoverageInfo month : monthCoverageInfos) {
            if (_JAN.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setJan('1');
            }
            if (_FEB.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setFeb('1');
            }
            if (_MAR.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setMar('1');
            }
            if (_APR.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setApr('1');
            }
            if (_MAY.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setMay('1');
            }
            if (_JUN.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setJun('1');
            }
            if (_JUL.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setJul('1');
            }
            if (_AUG.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setAug('1');
            }
            if (_SEP.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setSep('1');
            }
            if (_OCT.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setOct('1');
            }
            if (_NOV.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setNov('1');
            }
            if (_DEC.equals(month.getMonth()) && '1' == month.getCovered()) {
                filer.setDec('1');
            }
        }
    }

    /**
     * @param customerID
     * @return
     * @throws RuntimeException
     * @author R. Mukherjee
     * @purpose This method take a customer id as input and return FormPdfInfo as DTO object
     * @since 11/12/2015
     */
    @Override
    public FormPdfInfo viewCustomerPdfDetails(String customerID) {

        String[] parts = customerID.split("_");
   /*     String sourceCd = parts[0] + "_" + parts[1].toString();
        long sourceUniqueId = (Long.valueOf(parts[2]));*/

        long sourceUniqueId = 0;
        String sourceCd = "";
        if (parts.length > 0) {
            if (parts.length == 3) {
                sourceCd = parts[0] + "_" + parts[1].toString();
                sourceUniqueId = (Long.valueOf(parts[2]));
            }
            if (parts.length == 2) {
                sourceCd = parts[0];
                sourceUniqueId = (Long.valueOf(parts[1]));
            }
        }
        LOGGER.info("[INFO]Inside class:" + LOGGER.toString() + ", Method :viewCustomerPdfDetails(" + sourceUniqueId + "," + sourceCd + ",");
        List<FilerDemographic> results = new ArrayList<FilerDemographic>();
        FilerDemographic filerDemographic = new FilerDemographic();
        FormPdfInfo formPdfInfo = new FormPdfInfo();
        try {
            results = this.caseWorkerFilerDemographicRepositoryCW.getCustomerDetails(sourceUniqueId, sourceCd);
            if (results.size() > 0) {
                filerDemographic = results.get(0);
            }
            formPdfInfo = caseWorkerUtility.getCustomerPdfInfo(filerDemographic);

        } catch (RuntimeException re) {
            LOGGER.error("[ERROR]Inside :class:{" + LOGGER.getClass().getName() + "],method:[viewCustomerPdfDetails] error is:" + re.getMessage());
        }
        LOGGER.info("[INFO]Execution complete:class:[" + LOGGER.getClass() + "], method:[viewCustomerPdfDetails]");
        return formPdfInfo;
    }


    @Override
    public Boolean validateGroupFilers(List<FilerInfo> filers, SearchFilerInfo searchFilerInfo) {
        Boolean isFilerValidated = true;

        for (FilerInfo filerInfo : filers) {
            if (searchFilerInfo.getUserFname() != null)
                if (!searchFilerInfo.getUserFname().equalsIgnoreCase(filerInfo.getRecipientFirstName())) {
                    isFilerValidated = false;
                }

            if (searchFilerInfo.getUserLname() != null)
                if (!searchFilerInfo.getUserLname().equalsIgnoreCase(filerInfo.getRecipientLastName())) {
                    isFilerValidated = false;
                }
        }

        return isFilerValidated;
    }

    @Override
    public Boolean validateFilerInformation(SearchFilerInfo filerInfo, FilerDemographicCW demographic) {
        LOGGER.debug("[DEBUG] validation os search result started");
        Boolean isFilerValidated = true;

        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_FORMAT);

        /* Situation 1 :#SSN BASED SEARCH# Caseworker does not provided firstName and lastName*/
        if ((filerInfo.getUserFname() == null || filerInfo.getUserFname().equals(CommonDataConstants.EMPTY_VALUE))
                && (filerInfo.getUserLname() == null || filerInfo.getUserLname().equals(CommonDataConstants.EMPTY_VALUE))) {
            isFilerValidated = true;
        }

        /* #Situation 2: #SSN BASED SEARCH# provided TIN does not match with retrieved filer's TIN*/
        if (!(filerInfo.getTin() == null) && !(filerInfo.getTin().equals(CommonDataConstants.EMPTY_VALUE)) && !(filerInfo.getTin().equals(demographic.getRecepientTin()))) {
            isFilerValidated = false;
        }

        /* #Situation 3: #SSN BASED SEARCH# #TIN BASED SEARCH # provided FirstName does not match with retrieved filer's FirstName*/
        if (!(filerInfo.getUserFname() == null) && !(filerInfo.getUserFname().equals(CommonDataConstants.EMPTY_VALUE))
                && !(filerInfo.getUserFname().equalsIgnoreCase(demographic.getRecepientFirstName()))) {
            isFilerValidated = false;
        }

        /* #Situation 4: #SSN BASED SEARCH# #TIN BASED SEARCH #  provided LastName does not match with retrieved filer's LastName*/
        if (!(filerInfo.getUserLname() == null) && !(filerInfo.getUserLname().equals(CommonDataConstants.EMPTY_VALUE))
                && !(filerInfo.getUserLname().equalsIgnoreCase(demographic.getRecepientLastName()))) {
            isFilerValidated = false;
        }


        /*Situation 5: #SSN BASED SEARCH# #TIN BASED SEARCH # Caseworker provided dob doesnot match with retrieved DOB*/
        if (!(filerInfo.getDob() == null) && !(filerInfo.getDob().equals(CommonDataConstants.EMPTY_VALUE))
                && !(dateFormat.format(demographic.getRecepientDob()).equalsIgnoreCase(filerInfo.getDob()))) {
            isFilerValidated = false;
        }

        LOGGER.debug("[DEBUG] validation os search result started");
        LOGGER.info("[INFO] The filer's validation status is " + isFilerValidated);
        return isFilerValidated;
    }






/*===============================View PDF : service methods Started=========================== */

    /**
     * @param formID
     * @return ViewFormInfo
     * @throws RuntimeException
     * @purpose This method responsible to populate:
     * <ViewCurrentFormDataInfo>
     * </ViewCurrentFormDataInfo>
     * @since 12/09/2015
     */

    @Override
    public ViewCurrentFormDataInfo getViewCurrentFormDataDetails(String formID) {
        LOGGER.debug("[DEBUG]Inside class:" + LOGGER.toString() + ", Method :getViewCurrentFormDataDetails()");

        String[] parts = formID.split("_");
        long sourceUniqueId = 0;
        String sourceCd = "";
        if (parts.length > 0) {
            if (parts.length == 3) {
                sourceCd = parts[0] + "_" + parts[1].toString();
                sourceUniqueId = (Long.valueOf(parts[2]));
            }
            if (parts.length == 2) {
                sourceCd = parts[0];
                sourceUniqueId = (Long.valueOf(parts[1]));
            }
        }

        String viewFormMessage = "";
        List<FilerDemographic> demographicResults = new ArrayList<FilerDemographic>();

        ViewCurrentFormDataInfo viewCurrentFormDataInfo = new ViewCurrentFormDataInfo();
        try {

           /* fetch particular Filers demographic details*/
            demographicResults = this.caseWorkerFilerDemographicRepositoryCW.getFormFilerDetailsAllCovered(sourceUniqueId, sourceCd);

            FilerDemographic filerDemographic = new FilerDemographic();

            demographicResults = this.caseWorkerFilerDemographicRepositoryCW.getCustomerDetails(sourceUniqueId, sourceCd);
            if (demographicResults.size() > 0) {
                filerDemographic = demographicResults.get(0);

            }
            viewCurrentFormDataInfo = this.caseWorkerUtility.viewCurrentFormDataDetails(filerDemographic, sourceUniqueId, sourceCd);

        } catch (RuntimeException re) {
            LOGGER.debug("[ERROR]Inside :class:" + LOGGER.getClass().getName() + ",method:getViewCurrentFormDataDetails()] error is:" + re.getMessage());
        }
        LOGGER.info("[INFO]Execution complete:class:" + LOGGER.getClass() + "method:getViewCurrentFormDataDetails()");
        return viewCurrentFormDataInfo;
    }

    /**
     * #SERVICE_METHOD#
     *
     * @param pdfID
     * @param a_seq_no
     * @return byte[]
     * @throws java.sql.SQLException
     * @purpose Service method responsible to fetch pdf
     */
    @Override
    public List<FilerDemographicCW> getCustomerPDF(String pdfID, String a_seq_no) {
        LOGGER.debug("[DEBUG]Inside class:[" + LOGGER.getClass().getName() + " ] getCustomerPDF starts");

        FilerDemographicCW filerDemographicCW;
        List<FilerDemographicCW> results = new ArrayList<FilerDemographicCW>();

        try {
            FilerDemographicPK key = new FilerDemographicPK(pdfID);
            if (a_seq_no == null || a_seq_no.equals(CommonDataConstants.EMPTY_VALUE)) {
                results = cwFilerDemographicRepository.getUserInformationFromFD(
                        key.getSourceUniqueId(), key.getSourceCd(), key.getTaxYear());
            } else {
                AuditFilerDemographic responsible = auditFilerDemographicRepository.findOne(Integer.parseInt(a_seq_no));

                /* If A_SEQ_NUM does not match pdfId */
                if (responsible.getSourceUniqueId() != key.getSourceUniqueId()
                        || !responsible.getTaxYear().equals(key.getTaxYear())
                        || !responsible.getSourceCd().equalsIgnoreCase(key.getSourceCd())) {
                    /* Should throw an exception here */
                    return results;
                }

                int versionNum = responsible.getRecordVersion();
                List<AuditFilerDemographic> covereds = auditFilerDemographicRepository
                        .findByResponsiblePersonUniqueIdAndSourceCdAndTaxYearAndRecordVersionOrderByFilerStatusDesc(
                                responsible.getSourceUniqueId(), responsible.getSourceCd(),
                                responsible.getTaxYear(), versionNum);
                for (AuditFilerDemographic covered : covereds) {
                    results.add(new FilerDemographicCW(covered));
                }
            }
        } catch (NoResultException e) {

            LOGGER.error("[ERROR] Exception happen inside class:[" + LOGGER.getClass().getName() + " ] at getCustomerPdf[]");
        } /**/


        return results;
    }

    @Override
    public int updateMailStatus(String customerId,
                                String userInfo) {
        String[] parts = customerId.split("_");
        long sourceUniqueId = 0;
        String sourceCd = "";
        Integer taxYear= 0;
        if (parts.length > 0) {
            if (parts.length == 3) {
                sourceCd = parts[0] ;
                sourceUniqueId = (Long.valueOf(parts[1]));
                taxYear = (Integer.valueOf(parts[2]));
            }
        }
        Date date = new Date();
        return cwFilerDemographicRepository.updateFormStatus(sourceUniqueId, sourceCd, taxYear, CommonDataConstants.FormStatus.FORM_STATUS_REGENERATE, userInfo, date);
    }

    public List<CoveredPerson> getCoveredPersonListWithTaxYR(long responsiblePersonUniqueId, String sourceCd, String taxYear) {
        List<CoveredPerson> rows = null;
        String sqlStatment = "SELECT * FROM FILER_DEMOGRAPHICS WHERE RESPONSIBLE_PERSON_UNIQUE_ID = " + responsiblePersonUniqueId
                + " AND FILER_STATUS = 'C' AND STATUS = 'ACTIVE' AND SOURCE_CD = '" + sourceCd + "' AND TAX_YEAR = " + taxYear;
        try {
            //TODO Change this call to use a PreparedStatment instead of the regular Statement
            rows = jdbcTemplate.query(sqlStatment, new CoveredPersonMapper());
        } catch (Exception ex) {
            LOGGER.error("", ex);
        }
        return rows;
    }

    public String getCorrectionIndicator(long sourceuniqueID, String sourceCd, String taxYear) {
        String corIndicator = "";
        corIndicator = getTransmitStatus(sourceCd, sourceuniqueID, taxYear);
        if (corIndicator == "COMPLETE") {
            corIndicator = "correctedcheckbox";
        }
        return corIndicator;
    }

    private String getTransmitStatus(String sourceCd, long sourceUniqueId, String taxYear)
    {
        String transmitStatus = "";
        try{

            // List<String> transmitStatusList =  this.jdbcTemplate.queryForList("select TRANSMIT_STATUS from IRS_TRANSMITTAL_DETAILS where SOURCE_CD = '" + sourceCd + "'  and source_unique_id = " + sourceUniqueId, String.class);

            StringBuilder builder = new StringBuilder("select TRANSMISSION_RECEIPT_ID from irs_transmission_details itd inner join ");
            builder.append("irs_record_details_1095b ird on ird.TRANSMISSION_ID = itd.TRANSMISSION_ID inner join ");
            builder.append("filer_demographics fd on fd.source_unique_id = ird.source_unique_id and fd.source_cd = ird.source_cd ");
            builder.append("where fd.source_unique_id = '"+sourceUniqueId+"' and fd.source_cd = '"+sourceCd+"' and fd.tax_year = "+taxYear+" order by itd.transfer_date DESC");
            List<String> transmitStatusList =  this.jdbcTemplate.queryForList(builder.toString(), String.class);

            if (transmitStatusList.isEmpty()) {
                transmitStatus = "";
            } else {
                transmitStatus = (transmitStatusList.get(0) != null &&  ((String)transmitStatusList.get(0)).length() > 0)?"COMPLETE":"";
            }
        }catch(Exception ex){
            LOGGER.error("--------------------------------------------------------------------------");
            LOGGER.error("Error in IrsTransmittalDetailsDS class method getTransmitStatus()", ex.getStackTrace().toString());
            LOGGER.error("--------------------------------------------------------------------------");
        }
        return transmitStatus;
    }

}
