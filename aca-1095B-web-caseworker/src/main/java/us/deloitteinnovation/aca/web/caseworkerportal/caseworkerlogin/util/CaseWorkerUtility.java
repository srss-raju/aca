package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.entity.*;
import us.deloitteinnovation.aca.security.UserSession;
import us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto.*;
import us.deloitteinnovation.aca.web.caseworkerportal.vo.SearchFilerInfo;

import java.text.SimpleDateFormat;
import java.util.*;

import static us.deloitteinnovation.aca.constants.CommonDataConstants.MAGIC_NUMBER_ZERO;

/**
 * Created by ritmukherjee on 10/13/2015.
 */
@Component
public class CaseWorkerUtility {
    private static final Logger logger =
            LoggerFactory.getLogger(CaseWorkerUtility.class);


    /**
     * @param demographics
     * @return List<FilerInfo></FilerInfo>
     * @purpose This Utilitity method is responsible to convert ENTITY:List<FilerDemographic> to DTO  List<FilerInfo></FilerInfo>
     * @since 10.12.2015
     */
    public List<FilerInfo> getFilerDto(List<FilerDemographicCW> demographics,SearchFilerInfo searchFilerInfo) {
        logger.info("[INFO]Inside :: " + logger.toString() + " EntityObject to DTO conversion begin");
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_FORMAT);
        Filers filers = new Filers();
        try {


            Iterator demographicIterator = demographics.iterator();
            while (demographicIterator.hasNext()) {


                FilerDemographicCW demographic = (FilerDemographicCW) demographicIterator.next();

                FilerInfo filerInfo = new FilerInfo();

                /*setting Primary Key:FilerID*/
                filerInfo.setFilerID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getTaxYear());



                /*setting primary info*/
                filerInfo.setRecipientFirstName(demographic.getRecepientFirstName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientFirstName());
                filerInfo.setRecipientLastName(demographic.getRecepientLastName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientLastName());
                filerInfo.setRecipientDob(demographic.getRecepientDob() == null ? CommonDataConstants.EMPTY_VALUE : dateFormat.format(demographic.getRecepientDob()));


                /*mapping SSN & TIN according to New requirement*/
                filerInfo.setRecipientSsn(demographic.getRecepientSsn() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientSsn().replaceAll("^[0-9]{5}", "xxx-xx-"));
                filerInfo.setRecipientTin(demographic.getRecepientTin() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientTin().replaceAll("^[0-9]{5}", "xxx-xx-"));

                /*mapping state to maintain statewise searching*/
                filerInfo.setRecepientState(demographic.getRecepientState() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientState());

                /*mapping filer status*/
                filerInfo.setFilerStatus(demographic.getFilerStatus() == null ? Character.valueOf('\0') : demographic.getFilerStatus());

                filerInfo.setStatus(demographic.getStatus());

                filers.addFiler(filerInfo);
            }

            if(filers.getFilers() != null)
                 logger.info("[INFO]Conversion end with processing of file:" + filers.getFilers().size());
        } catch (Exception e) {
            logger.error("ERROR:error at " + logger.getClass().getName() + "Error:", e);
        }
        return filers.getFilers();
    }

    /**
     * @param demographic
     * @param demographicAudits
     * @return Form1095bDetails
     */
    public Form1095bDetails getForm1095bDetails(FilerDemographic demographic, List<FilerDemographicAudit> demographicAudits) {


        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_FORMAT);
        Form1095bDetails form1095bDetails = new Form1095bDetails();
        /*Mapping Primary Key*/
        form1095bDetails.setFormID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId()+ CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getTaxYear());


        /*Mapping Personal info*/
        form1095bDetails.setFirstName(demographic.getRecepientFirstName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientFirstName());
        form1095bDetails.setLastName(demographic.getRecepientLastName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientLastName());

        /*mapping user id*/
        form1095bDetails.setUidNumber((demographic.getRecepientSsn() == null ? demographic.getRecepientTin() : demographic.getRecepientSsn()).replaceAll("^[0-9]{5}", "xxx-xx-"));
        form1095bDetails.setUidType(demographic.getRecepientSsn() == null ? CommonDataConstants.CUSTOMER_UID_TYPE_TIN : CommonDataConstants.CUSTOMER_UID_TYPE_SSN);

        form1095bDetails.setTaxYear(demographic.getId().getTaxYear());
        form1095bDetails.setLastMailRequestedDate("");

        /*Set Current form info*/
        Form1095BInfo currentForm1095BInfo = new Form1095BInfo();
            /*1. Setting ForomID:combination of Sourcecd & SourceUniqueId*/
        currentForm1095BInfo.setFormID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId()+ CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getTaxYear());
            /*2. Setting GeneratedBy*/
        currentForm1095BInfo.setGeneratedBy(demographic.getUpdatedBy() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getUpdatedBy());
            /*3. setting LastMofifiedDate*/
        currentForm1095BInfo.setLastModifiedDate(
                (demographic.getUpdatedDt() == null ? CommonDataConstants.EMPTY_VALUE :
                        dateFormat.format(demographic.getUpdatedDt())));
            /*4. Setting AddressLine 1*/
        currentForm1095BInfo.setAddressLine1(demographic.getRecepientAddressLine1());
            /*5. Setting AddressLine 2*/
        currentForm1095BInfo.setAddressLine2(demographic.getRecepientAddressLine2());
            /*6. Setting city*/
        currentForm1095BInfo.setCity(demographic.getRecepientCity());
            /*7. Setting state*/
        currentForm1095BInfo.setState(demographic.getRecepientState());
            /*8. setting zipcode*/
        currentForm1095BInfo.setZipcode(demographic.getRecepientZip5() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientZip5());
            /*9. Setting Comments*/
        currentForm1095BInfo.setComments(demographic.getComments() == null ? CommonDataConstants.EMPTY_VALUE :
                demographic.getComments());
        form1095bDetails.setCurrentForm(currentForm1095BInfo);

/*Temporarily commented:due to unavailability of A_FILER_DEMOGRAPHICS*/

        List<Form1095BInfo> historicAuditList = new ArrayList<>();
        for (FilerDemographicAudit historicDemographicAudit : demographicAudits) {
            Form1095BInfo historicForm1095BInfo = new Form1095BInfo();

            historicForm1095BInfo.setFormID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId()+ CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getTaxYear());
            //* Setting GeneratedBy*//*
            historicForm1095BInfo.setGeneratedBy(historicDemographicAudit.getUpdatedBy() == null ? CommonDataConstants.EMPTY_VALUE :
                    historicDemographicAudit.getUpdatedBy());
             /*Setting LastModifiedDate*/
            historicForm1095BInfo.setLastModifiedDate(
                    (historicDemographicAudit.getUpdatedDt() == null ? CommonDataConstants.EMPTY_VALUE :
                            dateFormat.format(historicDemographicAudit.getUpdatedDt())));
              /*Setting AddressLine 1*/
            historicForm1095BInfo.setAddressLine1(historicDemographicAudit.getRecepientAddressLine1() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getRecepientAddressLine1());
             /*Setting AddressLine 2*/
            historicForm1095BInfo.setAddressLine2(historicDemographicAudit.getRecepientAddressLine2() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getRecepientAddressLine2());
             /*Setting city*/
            historicForm1095BInfo.setCity(historicDemographicAudit.getRecepientCity() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getRecepientCity());
           /* Setting state*/
            historicForm1095BInfo.setState(historicDemographicAudit.getRecepientState() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getRecepientState());
            /* Setting zipcode*/
            historicForm1095BInfo.setZipcode(historicDemographicAudit.getRecepientZip5() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getRecepientZip5());
            //* Setting Comments*//*
            historicForm1095BInfo.setComments(historicDemographicAudit.getComments() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getComments());

            historicAuditList.add(historicForm1095BInfo);
        }

        /*setting History List*/
        form1095bDetails.setHistoricForms(historicAuditList);


        return form1095bDetails;
    }


    /**
     * @param demographic
     * @return Form1095bDetails
     */
    public IndividualFormInfo getIndividualFormDetails(FilerDemographic demographic) {


        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_FORMAT);
        SimpleDateFormat dateTimeFormat=new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_TIME_FORMAT);

       /* populate DTO:IndividualFormInfo*/
        IndividualFormInfo individualFormInfo = new IndividualFormInfo();

        if (demographic != null) {
        /*Mapping Primary Key*/
            individualFormInfo.setCustomerID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId());


        /*Mapping Personal info*/
            individualFormInfo.setFirstName(demographic.getRecepientFirstName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientFirstName());
            individualFormInfo.setLastName(demographic.getRecepientLastName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientLastName());

        /*mapping user id*/
            individualFormInfo.setUidNumber((demographic.getRecepientSsn() == null ? demographic.getRecepientTin() : demographic.getRecepientSsn()).replaceAll("^[0-9]{5}", "xxx-xx-"));
            individualFormInfo.setUidType(demographic.getRecepientSsn() == null ? CommonDataConstants.CUSTOMER_UID_TYPE_TIN : CommonDataConstants.CUSTOMER_UID_TYPE_SSN);

        /*mapping taxYear*/
            individualFormInfo.setTaxYear(demographic.getId().getTaxYear());



//       /* mapping  DOB  */
            individualFormInfo.setDob(demographic.getRecepientDob() == null ? CommonDataConstants.EMPTY_VALUE :
                    dateFormat.format(demographic.getRecepientDob()));

        /*Set Current form info*/
            Form1095BInfo currentForm1095BInfo = new Form1095BInfo();
            /*1. Setting ForomID:combination of Sourcecd & SourceUniqueId*/
            currentForm1095BInfo.setFormID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId()+ CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getTaxYear());
            /*2. Setting GeneratedBy*/
            currentForm1095BInfo.setGeneratedBy(demographic.getUpdatedBy() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getUpdatedBy());
            /*3. setting LastMofifiedDate*/
            currentForm1095BInfo.setLastModifiedDate(
                    (demographic.getUpdatedDt() == null ? CommonDataConstants.EMPTY_VALUE :
                            dateTimeFormat.format(demographic.getUpdatedDt())));
            /*4. Setting AddressLine 1*/
            currentForm1095BInfo.setAddressLine1(demographic.getRecepientAddressLine1()==null?CommonDataConstants.EMPTY_VALUE:demographic.getRecepientAddressLine1());
            /*5. Setting AddressLine 2*/
            currentForm1095BInfo.setAddressLine2(demographic.getRecepientAddressLine2()==null?CommonDataConstants.EMPTY_VALUE:demographic.getRecepientAddressLine2());
            /*6. Setting city*/
            currentForm1095BInfo.setCity(demographic.getRecepientCity()==null?CommonDataConstants.EMPTY_VALUE:demographic.getRecepientCity());
            /*7. Setting state*/
            currentForm1095BInfo.setState(demographic.getRecepientState()==null?CommonDataConstants.EMPTY_VALUE:demographic.getRecepientState());
            /*8. setting zipcode*/
            currentForm1095BInfo.setZipcode(demographic.getRecepientZip5() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientZip5());
            /*9. Setting Comments*/
            currentForm1095BInfo.setComments(demographic.getComments() == null ? CommonDataConstants.EMPTY_VALUE :
                    demographic.getComments());
            individualFormInfo.setCurrentForm(currentForm1095BInfo);

/*Temporarily commented:due to unavailability of A_FILER_DEMOGRAPHICS*/

            List<Form1095BInfo> historicAuditList = new ArrayList<>();
            for (FilerDemographicAudit historicDemographicAudit : demographic.getDemographicAudits()) {
                Form1095BInfo historicForm1095BInfo = new Form1095BInfo();

                historicForm1095BInfo.setFormID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId()+ CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getTaxYear());
                //* Setting GeneratedBy*//*
                historicForm1095BInfo.setGeneratedBy(historicDemographicAudit.getUpdatedBy() == null ? CommonDataConstants.EMPTY_VALUE :
                        historicDemographicAudit.getUpdatedBy());
             /*Setting LastModifiedDate*/
                historicForm1095BInfo.setLastModifiedDate(
                        (historicDemographicAudit.getUpdatedDt() == null ? CommonDataConstants.EMPTY_VALUE :
                                dateTimeFormat.format(historicDemographicAudit.getUpdatedDt())));
              /*Setting AddressLine 1*/
                historicForm1095BInfo.setAddressLine1(historicDemographicAudit.getRecepientAddressLine1() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getRecepientAddressLine1());
             /*Setting AddressLine 2*/
                historicForm1095BInfo.setAddressLine2(historicDemographicAudit.getRecepientAddressLine2() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getRecepientAddressLine2());
             /*Setting city*/
                historicForm1095BInfo.setCity(historicDemographicAudit.getRecepientCity() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getRecepientCity());
           /* Setting state*/
                historicForm1095BInfo.setState(historicDemographicAudit.getRecepientState() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getRecepientState());
            /* Setting zipcode*/
                historicForm1095BInfo.setZipcode(historicDemographicAudit.getRecepientZip5() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getRecepientZip5());
                //* Setting Comments*//*
                historicForm1095BInfo.setComments(historicDemographicAudit.getComments() == null ? CommonDataConstants.EMPTY_VALUE : historicDemographicAudit.getComments());

                historicAuditList.add(historicForm1095BInfo);
            }

        /*setting History List*/
            individualFormInfo.setHistoricForms(historicAuditList);
        }

        return individualFormInfo;
    }

    /**
     * @Purpose Utility Method changes Demographics data to DTO
     * @param demographics current version of demographics
     * @param auditDemographics audit version of demographics
     * @param sourceUniqueId
     * @param sourceCd
     * @return
     */
    public ViewFormInfo viewFormDetails(List<FilerDemographicCW> demographics,
                                        List<AuditFilerDemographic> auditDemographics,
                                        long sourceUniqueId,
                                        String sourceCd,
                                        int taxYear) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_FORMAT);
        SimpleDateFormat dateTimeFormat=new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_TIME_FORMAT);

        /*ViewFormInfo: DTO class responsible to produce view Form output*/
        ViewFormInfo viewFormInfo = new ViewFormInfo();


        /*responsibleFiler:is that Filer whose FILER_STATUS ='R' and SOURCE_UNIQUE_ID=RESPONSIBLE_PERSON_UNIQUE_ID*/
        FilerDemographicCW responsibleFiler = null;
        FilerDemographicCW provideFiler=null;

        /**********************************START: Make list of all covered persons details******************************/

        /*List<CoveredFilerInfo>: List of all valid covered persons Form*/
        List<CoveredFilerInfo> coveredFilerInfos = new ArrayList<CoveredFilerInfo>();
        for (FilerDemographicCW demographic : demographics) {
            CoveredFilerInfo coveredFilerInfo = new CoveredFilerInfo();

            /*Mapping Primary Key*/
            coveredFilerInfo.setFilerID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId()+ CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getTaxYear());


            /*Mapping Personal info*/
            coveredFilerInfo.setFirstName(demographic.getRecepientFirstName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientFirstName());
            coveredFilerInfo.setLastName(demographic.getRecepientLastName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientLastName());

              /*mapping user id*/
            coveredFilerInfo.setUidNumber(
                    (StringUtils.isEmpty(demographic.getRecepientSsn()) == true ?
                            (StringUtils.isEmpty(demographic.getRecepientTin()) == true ? "" : demographic.getRecepientTin().replaceAll("^[0-9]{5}", "xxx-xx-"))
                            : demographic.getRecepientSsn().replaceAll("^[0-9]{5}", "xxx-xx-")));

            coveredFilerInfo.setUidType(
                    StringUtils.isEmpty(demographic.getRecepientSsn()) == true ?
                            (StringUtils.isEmpty(demographic.getRecepientTin()) == true ? CommonDataConstants.CUSTOMER_UID_TYPE_OTHER
                                    : CommonDataConstants.CUSTOMER_UID_TYPE_TIN)
                            : CommonDataConstants.CUSTOMER_UID_TYPE_SSN);

            /*mapping DOB*/
                coveredFilerInfo.setDob(demographic.getRecepientDob() == null ? CommonDataConstants.EMPTY_VALUE : dateFormat.format(demographic.getRecepientDob()));

           /* mapping filer Status*/
            coveredFilerInfo.setFilerStatus(demographic.getFilerStatus() == null ? '\0' : demographic.getFilerStatus());


            /* setting LastModifiedDate*/
            coveredFilerInfo.setLastModifiedDate(
                    (demographic.getUpdatedDt() == null ? CommonDataConstants.EMPTY_VALUE :
                            dateFormat.format(demographic.getUpdatedDt())));

            coveredFilerInfo.setTaxYear(demographic.getId().getTaxYear() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getId().getTaxYear().toString());

            coveredFilerInfo.setEmail(demographic.getEMail());
            coveredFilerInfo.setStatus(demographic.getStatus());
            coveredFilerInfo.setFormStatus(demographic.getFormStatus() == null ? "":demographic.getFormStatus());
            coveredFilerInfo.setPrintStatus(demographic.getLatestPrintStatus());

            /*condition to determine responsible filer*/
            if (Long.valueOf(demographic.getResponsiblePersonUniqueId()) == demographic.getId().getSourceUniqueId()||
                    demographic.getFilerStatus().equals(CommonDataConstants.FilerStatus.FILER_STATUS_R)
                    ) {
                responsibleFiler = demographic;
            }

            /*condition to determine data Provided filer(R/C/N)*/
            if(demographic.getId().getSourceUniqueId()==sourceUniqueId &&demographic.getId().getSourceCd().equalsIgnoreCase(sourceCd)){
                provideFiler=demographic;
            }

            coveredFilerInfos.add(coveredFilerInfo);

        }
        /**********************************END: Make list of all covered persons details******************************/

        /**********************************START: Make Details of form******************************/
         /*Set Current form info*/
        Form1095BInfo currentForm1095BInfo = new Form1095BInfo();
        FilerDemographicCW source = null;
        if(responsibleFiler!=null) {
            source = responsibleFiler;
            /*1. Setting ForomID:combination of Sourcecd & SourceUniqueId*/
            currentForm1095BInfo.setFormID(responsibleFiler.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + responsibleFiler.getId().getSourceUniqueId() + CommonDataConstants.UNDERSCORE_VALUE + responsibleFiler.getId().getTaxYear());
            /*2. Setting GeneratedBy*/
            currentForm1095BInfo.setGeneratedBy(responsibleFiler.getUpdatedBy() == null ? CommonDataConstants.EMPTY_VALUE : responsibleFiler.getUpdatedBy());
            /*3. setting LastMofifiedDate*/
            currentForm1095BInfo.setLastModifiedDate(
                    (responsibleFiler.getUpdatedDt() == null ? CommonDataConstants.EMPTY_VALUE :
                            dateTimeFormat.format(responsibleFiler.getUpdatedDt())));
            /*4. Setting AddressLine 1*/
            currentForm1095BInfo.setAddressLine1(responsibleFiler.getRecepientAddressLine1() == null ? CommonDataConstants.EMPTY_VALUE : responsibleFiler.getRecepientAddressLine1());
            /*5. Setting AddressLine 2*/
            currentForm1095BInfo.setAddressLine2(responsibleFiler.getRecepientAddressLine2() == null ? CommonDataConstants.EMPTY_VALUE : responsibleFiler.getRecepientAddressLine2());
            /*6. Setting city*/
            currentForm1095BInfo.setCity(responsibleFiler.getRecepientCity() == null ? CommonDataConstants.EMPTY_VALUE : responsibleFiler.getRecepientCity());
            /*7. Setting state*/
            currentForm1095BInfo.setState(responsibleFiler.getRecepientState() == null ? CommonDataConstants.EMPTY_VALUE : responsibleFiler.getRecepientState());
            /*8. setting zipcode*/
            currentForm1095BInfo.setZipcode(responsibleFiler.getRecepientZip5() == null ? CommonDataConstants.EMPTY_VALUE : responsibleFiler.getRecepientZip5());
            /*9. Setting Comments*/
            currentForm1095BInfo.setComments(responsibleFiler.getComments() == null ? CommonDataConstants.EMPTY_VALUE :
                    responsibleFiler.getComments());

            /*setting pdf status<FilerDemographic.FORM_STATUS>*/
            currentForm1095BInfo.setPdfStatus(responsibleFiler.getFormStatus() == null ? CommonDataConstants.EMPTY_VALUE : responsibleFiler.getFormStatus());

            currentForm1095BInfo.setEmail(responsibleFiler.getEMail() == null ? CommonDataConstants.EMPTY_VALUE : responsibleFiler.getEMail());
            currentForm1095BInfo.setStatus(responsibleFiler.getStatus());
            currentForm1095BInfo.setFormStatus(responsibleFiler.getFormStatus() == null ? "":responsibleFiler.getFormStatus());
            currentForm1095BInfo.setPrintStatus(responsibleFiler.getLatestPrintStatus());
        }
        else{
             /*1. Setting FormID:combination of Sourcecd & SourceUniqueId*/
            if(provideFiler!=null) {
                source = provideFiler;
                currentForm1095BInfo.setFormID(provideFiler.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + provideFiler.getId().getSourceUniqueId() + CommonDataConstants.UNDERSCORE_VALUE + responsibleFiler.getId().getTaxYear());
            /*2. Setting GeneratedBy*/
                currentForm1095BInfo.setGeneratedBy(provideFiler.getUpdatedBy() == null ? CommonDataConstants.EMPTY_VALUE : provideFiler.getUpdatedBy());
            /*3. setting LastMofifiedDate*/
                currentForm1095BInfo.setLastModifiedDate(
                        (provideFiler.getUpdatedDt() == null ? CommonDataConstants.EMPTY_VALUE :
                                dateTimeFormat.format(provideFiler.getUpdatedDt())));
            /*4. Setting AddressLine 1*/
                currentForm1095BInfo.setAddressLine1(provideFiler.getRecepientAddressLine1() == null ? CommonDataConstants.EMPTY_VALUE : provideFiler.getRecepientAddressLine1());
            /*5. Setting AddressLine 2*/
                currentForm1095BInfo.setAddressLine2(provideFiler.getRecepientAddressLine2() == null ? CommonDataConstants.EMPTY_VALUE : provideFiler.getRecepientAddressLine2());
            /*6. Setting city*/
                currentForm1095BInfo.setCity(provideFiler.getRecepientCity() == null ? CommonDataConstants.EMPTY_VALUE : provideFiler.getRecepientCity());
            /*7. Setting state*/
                currentForm1095BInfo.setState(provideFiler.getRecepientState() == null ? CommonDataConstants.EMPTY_VALUE : provideFiler.getRecepientState());
            /*8. setting zipcode*/
                currentForm1095BInfo.setZipcode(provideFiler.getRecepientZip5() == null ? CommonDataConstants.EMPTY_VALUE : provideFiler.getRecepientZip5());
            /*9. Setting Comments*/
                currentForm1095BInfo.setComments(provideFiler.getComments() == null ? CommonDataConstants.EMPTY_VALUE :
                        provideFiler.getComments());

                currentForm1095BInfo.setEmail(responsibleFiler.getEMail() == null ? CommonDataConstants.EMPTY_VALUE : responsibleFiler.getEMail());

            /*setting pdf status<FilerDemographic.FORM_STATUS>*/
                currentForm1095BInfo.setPdfStatus(provideFiler.getFormStatus() == null ? CommonDataConstants.EMPTY_VALUE : provideFiler.getFormStatus());


                currentForm1095BInfo.setStatus(responsibleFiler.getStatus());
                currentForm1095BInfo.setFormStatus(responsibleFiler.getFormStatus() == null ? "":responsibleFiler.getFormStatus());
                currentForm1095BInfo.setPrintStatus(responsibleFiler.getLatestPrintStatus());
            }
        }

        /* Set date information from PRINT_DETAILS */
        if (null != source) {
            List<PrintDetail> printDetails = source.getPrintDetails();
            if (null != printDetails && !printDetails.isEmpty()) {
                Collections.sort(printDetails);

                /* Nullity check */
                Date mailedDate = printDetails.get(MAGIC_NUMBER_ZERO).getMailedDate();
                Date createdDate = printDetails.get(MAGIC_NUMBER_ZERO).getCreatedDate();
                Date acknowledgeDate = printDetails.get(MAGIC_NUMBER_ZERO).getAcknowledgeDate();
                if (null != mailedDate) {
                    currentForm1095BInfo.setMailedDate(dateFormat.format(mailedDate));
                }
                if (null != createdDate) {
                    currentForm1095BInfo.setPrintDate(dateFormat.format(createdDate));
                }
                if (null != acknowledgeDate) {
                    currentForm1095BInfo.setAcknowledgeDate(dateFormat.format(acknowledgeDate));
                }
            }
        }

        /**********************************END: Make Details of Current form******************************/

        /**********************************START: Make List of historic forms******************************/
        /*Set List of Historic Audit List*/
        List<Form1095BInfo> historicAuditList = new ArrayList<>();

        /* Append historical forms */
        for (AuditFilerDemographic auditFiler : auditDemographics) {
            historicAuditList.add(new Form1095BInfo(auditFiler));
        }


        /**********************************END: Make List of historic forms******************************/
        /*setting all property viewFormInfo*/
        viewFormInfo.setCurrentForm(currentForm1095BInfo);
        viewFormInfo.setCoveredFilers(coveredFilerInfos);
        viewFormInfo.setHistoricForms(historicAuditList);

        return viewFormInfo;
    }

    /**
     * @param demographic
     * @return
     * @purpose this method convert entity object FilerDemographic to DTO object CustomerInfo
     * @since 11/05/2014
     */
    public CustomerInfo getCustomerInfo(FilerDemographicCW demographic) {

        /*RESPONSIBLE TO CHANGE DATE TO STRING*/
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_FORMAT);

       /* Empty bean object Populated*/
        CustomerInfo customerInfo = new CustomerInfo();

        /*setting customer-id*/
        customerInfo.setCustomerID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId());

       /* setting pdfStatus :<added V14.1>*/
        customerInfo.setPdfStatus(demographic.getFormStatus() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getFormStatus());

        /*setting personal info*/
        customerInfo.setFirstName(demographic.getRecepientFirstName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientFirstName());
        customerInfo.setLastName(demographic.getRecepientLastName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientLastName());
        customerInfo.setDob(demographic.getRecepientDob() == null ? CommonDataConstants.EMPTY_VALUE : dateFormat.format(demographic.getRecepientDob()));

        /*mapping user id*/
        customerInfo.setUidNumber((demographic.getRecepientSsn() == null ? demographic.getRecepientTin() : demographic.getRecepientSsn()).replaceAll("^[0-9]{5}", "xxx-xx-"));
        customerInfo.setUidType(demographic.getRecepientSsn() == null ? CommonDataConstants.CUSTOMER_UID_TYPE_TIN : CommonDataConstants.CUSTOMER_UID_TYPE_SSN);

/*        *//*setting tax year*//*
        customerInfo.setTaxYear(demographic.getTaxYear());*/

        /*setting address*/
            /*1. Setting AddressLine 1*/
        customerInfo.setAddressLine1(demographic.getRecepientAddressLine1() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientAddressLine1());
            /*2. Setting AddressLine 2*/
        customerInfo.setAddressLine2(demographic.getRecepientAddressLine2() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientAddressLine2());
            /*3. Setting city*/
        customerInfo.setCity(demographic.getRecepientCity() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientCity());
            /*4. Setting state*/
        customerInfo.setState(demographic.getRecepientState() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientState());
            /*5. setting zipcode*/
        customerInfo.setZipCode(demographic.getRecepientZip5() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientZip5());

//        customerInfo.setCoverageID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId());

        customerInfo.setFilerCoverageInfo(this.getMonthCoverageDetails(demographic));

        customerInfo.setComments(demographic.getComments() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getComments());

        customerInfo.setCoverageSources(this.getCustomerSourceCoverages(demographic.getFilerCoverages()));

        /*setting helper flags*/

        /*setting idPdfAvailable*/
        if (customerInfo.getPdfStatus().equals(CommonDataConstants.FormStatus.FORM_STATUS_GENERATED) || customerInfo.getPdfStatus().equals(CommonDataConstants.FormStatus.FORM_STATUS_REGENERATE)) {
            customerInfo.setIsPdfAvailable(true);
        } else if (customerInfo.getPdfStatus().equals(CommonDataConstants.FormStatus.FORM_STATUS_NOT_GENERATED)) {
            customerInfo.setIsPdfAvailable(false);
        }

        // TODO Shouldn't this check on PRINT_DETAILS status instead? lbradley 2 Feb 2016
        /*setting idPdfAvailable*/
        if (customerInfo.getPdfStatus().equals(CommonDataConstants.FormStatus.FORM_STATUS_GENERATED)) {
            customerInfo.setIsMailEnable(true);
        } else if (customerInfo.getPdfStatus().equals(CommonDataConstants.FormStatus.FORM_STATUS_REGENERATE)) {
            customerInfo.setIsMailEnable(false);
        }

        return customerInfo;
    }


    public CorrectCustomerInfo getExistingCustomerInfo(FilerDemographicCW demographic, FilerDemographicCW responsibleDemographic, UserSession userSession) {

        /*RESPONSIBLE TO DO CHANGE DATE TO STRING*/
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_FORMAT);
        SimpleDateFormat dateTimeFormat=new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_TIME_FORMAT);
        CorrectCustomerInfo customerInfo = new CorrectCustomerInfo();

        CoveredFilerInfo coveredFilerInfo = new CoveredFilerInfo();

            /*Mapping Primary Key*/
        coveredFilerInfo.setFilerID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId()+ CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getTaxYear());


            /*Mapping Personal info*/
        coveredFilerInfo.setFirstName(demographic.getRecepientFirstName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientFirstName());
        coveredFilerInfo.setLastName(demographic.getRecepientLastName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientLastName());

            /*mapping user id*/
        coveredFilerInfo.setUidNumber(
                (StringUtils.isEmpty(demographic.getRecepientSsn()) == true ?
                        (StringUtils.isEmpty(demographic.getRecepientTin()) == true ? "" : demographic.getRecepientTin().replaceAll("^[0-9]{5}", "xxx-xx-"))
                        : demographic.getRecepientSsn().replaceAll("^[0-9]{5}", "xxx-xx-")));

        coveredFilerInfo.setUidType(
                StringUtils.isEmpty(demographic.getRecepientSsn()) == true ?
                        (StringUtils.isEmpty(demographic.getRecepientTin()) == true ? CommonDataConstants.CUSTOMER_UID_TYPE_OTHER
                                                                                    : CommonDataConstants.CUSTOMER_UID_TYPE_TIN)
                        : CommonDataConstants.CUSTOMER_UID_TYPE_SSN);



            /*mapping DOB*/
        coveredFilerInfo.setDob(demographic.getRecepientDob() == null ? CommonDataConstants.EMPTY_VALUE : dateFormat.format(demographic.getRecepientDob()));


          /* setting LastModifiedDate*/
        coveredFilerInfo.setLastModifiedDate(
                (demographic.getUpdatedDt() == null ? CommonDataConstants.EMPTY_VALUE :
                        dateTimeFormat.format(demographic.getUpdatedDt())));

       /* *//*setting LastMailedDate*//*
        coveredFilerInfo.setLastMailedDate(demographic.getPrintDetail() == null ? CommonDataConstants.EMPTY_VALUE :
                (demographic.getPrintDetail().getLastMailRequestedDate() == null ? CommonDataConstants.EMPTY_VALUE :
                        dateFormat.format(demographic.getPrintDetail().getLastMailRequestedDate())));*/

        coveredFilerInfo.setFilerStatus(demographic.getFilerStatus() == null ? '\0' : demographic.getFilerStatus());

        coveredFilerInfo.setEmail(demographic.getEMail());

        coveredFilerInfo.setStatus(demographic.getStatus());
        coveredFilerInfo.setFormStatus(demographic.getFormStatus() == null ? null : demographic.getFormStatus());
        coveredFilerInfo.setPrintStatus(demographic.getLatestPrintStatus());

        customerInfo.setFilerInfo(coveredFilerInfo);

           /*-----------------------------Set Current form info-------------------------------------------*/
        Form1095BInfo currentForm1095BInfo = new Form1095BInfo();
        if(responsibleDemographic!=null ) {
            /* Setting ForomID:combination of Sourcecd & SourceUniqueId*/
            currentForm1095BInfo.setFormID(responsibleDemographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + responsibleDemographic.getId().getSourceUniqueId() + CommonDataConstants.UNDERSCORE_VALUE + responsibleDemographic.getId().getTaxYear());

            currentForm1095BInfo.setPdfStatus(responsibleDemographic.getFormStatus() == null ? CommonDataConstants.EMPTY_VALUE : responsibleDemographic.getFormStatus());

            currentForm1095BInfo.setMailStatus(responsibleDemographic.getMailedForm() == null ? CommonDataConstants.EMPTY_VALUE : responsibleDemographic.getMailedForm());
            /* Setting GeneratedBy*/
            currentForm1095BInfo.setGeneratedBy(responsibleDemographic.getUpdatedBy() == null ? CommonDataConstants.EMPTY_VALUE : responsibleDemographic.getUpdatedBy());
            /* setting LastMofifiedDate*/
            currentForm1095BInfo.setLastModifiedDate(
                    (responsibleDemographic.getUpdatedDt() == null ? CommonDataConstants.EMPTY_VALUE :
                            dateTimeFormat.format(responsibleDemographic.getUpdatedDt())));
            /* Setting AddressLine 1*/
            currentForm1095BInfo.setAddressLine1(responsibleDemographic.getRecepientAddressLine1() == null ? CommonDataConstants.EMPTY_VALUE : responsibleDemographic.getRecepientAddressLine1());
            /* Setting AddressLine 2*/
            currentForm1095BInfo.setAddressLine2(responsibleDemographic.getRecepientAddressLine2() == null ? CommonDataConstants.EMPTY_VALUE : responsibleDemographic.getRecepientAddressLine2());
            /* Setting city*/
            currentForm1095BInfo.setCity(responsibleDemographic.getRecepientCity() == null ? CommonDataConstants.EMPTY_VALUE : responsibleDemographic.getRecepientCity());
            /* Setting state*/
            currentForm1095BInfo.setState(responsibleDemographic.getRecepientState() == null ? CommonDataConstants.EMPTY_VALUE : responsibleDemographic.getRecepientState());
            /* setting zipcode*/
            currentForm1095BInfo.setZipcode(responsibleDemographic.getRecepientZip5() == null ? CommonDataConstants.EMPTY_VALUE : responsibleDemographic.getRecepientZip5());
            /* Setting Comments*/
            currentForm1095BInfo.setComments(responsibleDemographic.getComments() == null ? CommonDataConstants.EMPTY_VALUE :
                    responsibleDemographic.getComments());

            currentForm1095BInfo.setEmail(responsibleDemographic.getEMail() == null ? CommonDataConstants.EMPTY_VALUE : responsibleDemographic.getEMail());

            /* transmission status cd */
            currentForm1095BInfo.setIrsTransmissionStatusCd(responsibleDemographic.getIrsTransmissionStatusCd());

            currentForm1095BInfo.setStatus(responsibleDemographic.getStatus());
            currentForm1095BInfo.setFormStatus(responsibleDemographic.getFormStatus() == null ? "" : responsibleDemographic.getFormStatus());
            currentForm1095BInfo.setPrintStatus(responsibleDemographic.getLatestPrintStatus());
        }

        /*setting form attribute*/
        customerInfo.setCurrentForm(currentForm1095BInfo);

        customerInfo.setFilerCoverageInfo(this.getMonthCoverageDetails(demographic));

            /*setting comments*/
        customerInfo.setComments(demographic.getComments() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getComments());

        customerInfo.setCoverageSources(this.getCustomerSourceCoverages(demographic.getFilerCoverages()));

        return customerInfo;
    }

    public LinkedList<MonthCoverageInfo> getMonthCoverageDetails(FilerDemographicCW demographic) {
        LinkedList<MonthCoverageInfo> monthCoverageInfos = new LinkedList<MonthCoverageInfo>();

        if (demographic != null) {
        /*1. January Month:*/
            MonthCoverageInfo janMonth = new MonthCoverageInfo();
            janMonth.setMonth(CommonDataConstants._JAN);
            janMonth.setCovered(demographic.getJan() == null ? CommonDataConstants.NOT_COVERED : demographic.getJan());

        /*2. February Month:*/
            MonthCoverageInfo febMonth = new MonthCoverageInfo();
            febMonth.setMonth(CommonDataConstants._FEB);
            febMonth.setCovered(demographic.getFeb() == null ? CommonDataConstants.NOT_COVERED : demographic.getFeb());

        /*3. March Month:*/
            MonthCoverageInfo marMonth = new MonthCoverageInfo();
            marMonth.setMonth(CommonDataConstants._MAR);
            marMonth.setCovered(demographic.getMar() == null ? CommonDataConstants.NOT_COVERED : demographic.getMar());

        /*4. April Month:*/
            MonthCoverageInfo aprMonth = new MonthCoverageInfo();
            aprMonth.setMonth(CommonDataConstants._APR);
            aprMonth.setCovered(demographic.getApr() == null ? CommonDataConstants.NOT_COVERED : demographic.getApr());

        /*5. May Month:*/
            MonthCoverageInfo mayMonth = new MonthCoverageInfo();
            mayMonth.setMonth(CommonDataConstants._MAY);
            mayMonth.setCovered(demographic.getMay() == null ? CommonDataConstants.NOT_COVERED : demographic.getMay());

        /*6. June Month:*/
            MonthCoverageInfo junMonth = new MonthCoverageInfo();
            junMonth.setMonth(CommonDataConstants._JUN);
            junMonth.setCovered(demographic.getJun() == null ? CommonDataConstants.NOT_COVERED : demographic.getJun());

        /*7. July Month:*/
            MonthCoverageInfo julMonth = new MonthCoverageInfo();
            julMonth.setMonth(CommonDataConstants._JUL);
            julMonth.setCovered(demographic.getJul() == null ? CommonDataConstants.NOT_COVERED : demographic.getJul());

        /*8. August Month:*/
            MonthCoverageInfo augMonth = new MonthCoverageInfo();
            augMonth.setMonth(CommonDataConstants._AUG);
            augMonth.setCovered(demographic.getAug() == null ? CommonDataConstants.NOT_COVERED : demographic.getAug());

        /*9. September Month:*/
            MonthCoverageInfo sepMonth = new MonthCoverageInfo();
            sepMonth.setMonth(CommonDataConstants._SEP);
            sepMonth.setCovered(demographic.getSep() == null ? CommonDataConstants.NOT_COVERED : demographic.getSep());

        /*10. October Month:*/
            MonthCoverageInfo octMonth = new MonthCoverageInfo();
            octMonth.setMonth(CommonDataConstants._OCT);
            octMonth.setCovered(demographic.getOct() == null ? CommonDataConstants.NOT_COVERED : demographic.getOct());

        /*11. November Month:*/
            MonthCoverageInfo novMonth = new MonthCoverageInfo();
            novMonth.setMonth(CommonDataConstants._NOV);
            novMonth.setCovered(demographic.getNov() == null ? CommonDataConstants.NOT_COVERED : demographic.getNov());

        /*12. December Month:*/
            MonthCoverageInfo decMonth = new MonthCoverageInfo();
            decMonth.setMonth(CommonDataConstants._DEC);
            decMonth.setCovered(demographic.getDec() == null ? CommonDataConstants.NOT_COVERED : demographic.getDec());


        /*adding all twelve months coverage details*/
            monthCoverageInfos.add(0, janMonth);
            monthCoverageInfos.add(1, febMonth);
            monthCoverageInfos.add(2, marMonth);
            monthCoverageInfos.add(3, aprMonth);
            monthCoverageInfos.add(4, mayMonth);
            monthCoverageInfos.add(5, junMonth);
            monthCoverageInfos.add(6, julMonth);
            monthCoverageInfos.add(7, augMonth);
            monthCoverageInfos.add(8, sepMonth);
            monthCoverageInfos.add(9, octMonth);
            monthCoverageInfos.add(10, novMonth);
            monthCoverageInfos.add(11, decMonth);
        }
        return monthCoverageInfos;

    }


    /**
     * @param coverage
     * @return
     * @author R.Mukherjee
     * @purpose utility method convert desired format of month for UI
     * @since 12/18/2015
     */
    public LinkedList<MonthCoverageInfo> getCoveragSourceDetails(FilerCoverage coverage) {
        LinkedList<MonthCoverageInfo> monthCoverageInfos = new LinkedList<MonthCoverageInfo>();

        if (coverage != null) {
        /*1. January Month:*/
            MonthCoverageInfo janMonth = new MonthCoverageInfo();
            janMonth.setMonth(CommonDataConstants._JAN);
            janMonth.setCovered(coverage.getJan() == null ? CommonDataConstants.NOT_COVERED : coverage.getJan());

        /*2. February Month:*/
            MonthCoverageInfo febMonth = new MonthCoverageInfo();
            febMonth.setMonth(CommonDataConstants._FEB);
            febMonth.setCovered(coverage.getFeb() == null ? CommonDataConstants.NOT_COVERED : coverage.getFeb());

        /*3. March Month:*/
            MonthCoverageInfo marMonth = new MonthCoverageInfo();
            marMonth.setMonth(CommonDataConstants._MAR);
            marMonth.setCovered(coverage.getMar() == null ? CommonDataConstants.NOT_COVERED : coverage.getMar());

        /*4. April Month:*/
            MonthCoverageInfo aprMonth = new MonthCoverageInfo();
            aprMonth.setMonth(CommonDataConstants._APR);
            aprMonth.setCovered(coverage.getApr() == null ? CommonDataConstants.NOT_COVERED : coverage.getApr());

        /*5. May Month:*/
            MonthCoverageInfo mayMonth = new MonthCoverageInfo();
            mayMonth.setMonth(CommonDataConstants._MAY);
            mayMonth.setCovered(coverage.getMay() == null ? CommonDataConstants.NOT_COVERED : coverage.getMay());

        /*6. June Month:*/
            MonthCoverageInfo junMonth = new MonthCoverageInfo();
            junMonth.setMonth(CommonDataConstants._JUN);
            junMonth.setCovered(coverage.getJun() == null ? CommonDataConstants.NOT_COVERED : coverage.getJun());

        /*7. July Month:*/
            MonthCoverageInfo julMonth = new MonthCoverageInfo();
            julMonth.setMonth(CommonDataConstants._JUL);
            julMonth.setCovered(coverage.getJul() == null ? CommonDataConstants.NOT_COVERED : coverage.getJul());

        /*8. August Month:*/
            MonthCoverageInfo augMonth = new MonthCoverageInfo();
            augMonth.setMonth(CommonDataConstants._AUG);
            augMonth.setCovered(coverage.getAug() == null ? CommonDataConstants.NOT_COVERED : coverage.getAug());

        /*9. September Month:*/
            MonthCoverageInfo sepMonth = new MonthCoverageInfo();
            sepMonth.setMonth(CommonDataConstants._SEP);
            sepMonth.setCovered(coverage.getSep() == null ? CommonDataConstants.NOT_COVERED : coverage.getSep());

        /*10. October Month:*/
            MonthCoverageInfo octMonth = new MonthCoverageInfo();
            octMonth.setMonth(CommonDataConstants._OCT);
            octMonth.setCovered(coverage.getOct() == null ? CommonDataConstants.NOT_COVERED : coverage.getOct());

        /*11. November Month:*/
            MonthCoverageInfo novMonth = new MonthCoverageInfo();
            novMonth.setMonth(CommonDataConstants._NOV);
            novMonth.setCovered(coverage.getNov() == null ? CommonDataConstants.NOT_COVERED : coverage.getNov());

        /*12. December Month:*/
            MonthCoverageInfo decMonth = new MonthCoverageInfo();
            decMonth.setMonth(CommonDataConstants._DEC);
            decMonth.setCovered(coverage.getDec() == null ? CommonDataConstants.NOT_COVERED : coverage.getDec());


        /*adding all twelve months coverage details*/
            monthCoverageInfos.add(0, janMonth);
            monthCoverageInfos.add(1, febMonth);
            monthCoverageInfos.add(2, marMonth);
            monthCoverageInfos.add(3, aprMonth);
            monthCoverageInfos.add(4, mayMonth);
            monthCoverageInfos.add(5, junMonth);
            monthCoverageInfos.add(6, julMonth);
            monthCoverageInfos.add(7, augMonth);
            monthCoverageInfos.add(8, sepMonth);
            monthCoverageInfos.add(9, octMonth);
            monthCoverageInfos.add(10, novMonth);
            monthCoverageInfos.add(11, decMonth);
        }
        return monthCoverageInfos;

    }

    /**
     * @param coverages
     * @return
     * @author R.Mukherjee
     * @purpose this method convert List<FilerCoverage> :entity to List<CoverageSourceInfo>:dto
     * @since 12.18.2015
     */
    public List<CoverageSourceInfo> getCustomerSourceCoverages(List<FilerCoverage> coverages) {

        /*RESPONSIBLE TO DO CHANGE DATE TO STRING*/
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_FORMAT);
        SimpleDateFormat dateTimeFormat=new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_TIME_FORMAT);

        List<CoverageSourceInfo> coverageSourceInfos = new ArrayList<CoverageSourceInfo>();
        if (coverages != null && coverages.size() != 0) {
            CoverageSourceInfo coverageSourceInfo = null;
            for (FilerCoverage filerCoverage : coverages) {
                coverageSourceInfo = new CoverageSourceInfo();

                /*setting primary key of each source coverage*/
                coverageSourceInfo.setCoverageSourceId(filerCoverage.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + filerCoverage.getId().getSourceUniqueId() + CommonDataConstants.UNDERSCORE_VALUE + filerCoverage.getId().getCoverageSeqNo());

                /*convert FilerCoverage to CoverageSourceInfo */
                coverageSourceInfo.setFilerSourceInfo(this.getCoveragSourceDetails(filerCoverage));

               /*setting caseID of each siurce coverage*/
                coverageSourceInfo.setCaseID(filerCoverage.getCaseApplicationId() == null ? CommonDataConstants.EMPTY_VALUE : filerCoverage.getCaseApplicationId());

               /* setting last modified date of each source*/
                coverageSourceInfo.setLastModifiedDate(filerCoverage.getUpdatedDt()==null?CommonDataConstants.EMPTY_VALUE:dateTimeFormat.format(filerCoverage.getUpdatedDt()));

                /*setting program Name*/
                coverageSourceInfo.setSource(filerCoverage.getProgramName()== null ? CommonDataConstants.EMPTY_VALUE :filerCoverage.getProgramName());

                /*setting comments of each source coverage*/
                coverageSourceInfo.setComments(filerCoverage.getComments()== null ? CommonDataConstants.EMPTY_VALUE :filerCoverage.getComments());


                coverageSourceInfos.add(coverageSourceInfo);
            }
        }
        return coverageSourceInfos;
    }

    /**
     * @param
     * @return
     * @author R.Mukherjee
     * @purpose This method responsible to accept FilerDemographic object and return FormPdfInfo
     * @since 12/15/2015
     */
    public FormPdfInfo getCustomerPdfInfo(FilerDemographic demographic) {

        FormPdfInfo formPdfInfo = new FormPdfInfo();

        if (demographic != null) {
        /*set PseudoId of FormPdf*/
            formPdfInfo.setPdfId(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId());

        /* set Form byte[]*/
       //     formPdfInfo.setForm1095(demographic.getForm1095());

        /*set form status*/
            formPdfInfo.setFormStatus(demographic.getFormStatus() == null ? "":demographic.getFormStatus());
        }
        return formPdfInfo;
    }


    public ViewCurrentFormDataInfo viewCurrentFormDataDetails(FilerDemographic demographic,long sourceUniqueId,String sourceCd) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_FORMAT);
        SimpleDateFormat dateTimeFormat=new SimpleDateFormat(CommonDataConstants.STANDARD_DATE_TIME_FORMAT);
        ViewCurrentFormDataInfo viewCurrentFormDataInfo=new ViewCurrentFormDataInfo();

            if(demographic!=null) {

        /*setting customer-id*/
                viewCurrentFormDataInfo.setCurrentPdfID(demographic.getId().getSourceCd() + CommonDataConstants.UNDERSCORE_VALUE + demographic.getId().getSourceUniqueId());

        /*setting personal info*/
                viewCurrentFormDataInfo.setFirstName(demographic.getRecepientFirstName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientFirstName());
                viewCurrentFormDataInfo.setLastName(demographic.getRecepientLastName() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientLastName());
                viewCurrentFormDataInfo.setDob(demographic.getRecepientDob() == null ? CommonDataConstants.EMPTY_VALUE : dateFormat.format(demographic.getRecepientDob()));

       /* setting pdf status*/
                viewCurrentFormDataInfo.setPdfStatus(demographic.getFormStatus() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getFormStatus());


        /*mapping user id*/
                viewCurrentFormDataInfo.setUidNumber((demographic.getRecepientSsn() == null ? demographic.getRecepientTin() : demographic.getRecepientSsn()).replaceAll("^[0-9]{5}", "xxx-xx-"));
                viewCurrentFormDataInfo.setUidType(demographic.getRecepientSsn() == null ? CommonDataConstants.CUSTOMER_UID_TYPE_TIN : CommonDataConstants.CUSTOMER_UID_TYPE_SSN);

        /*setting address*/
            /*1. Setting AddressLine 1*/
                viewCurrentFormDataInfo.setAddressLine1(demographic.getRecepientAddressLine1() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientAddressLine1());
            /*2. Setting AddressLine 2*/
                viewCurrentFormDataInfo.setAddressLine2(demographic.getRecepientAddressLine2() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientAddressLine2());
            /*3. Setting city*/
                viewCurrentFormDataInfo.setCity(demographic.getRecepientCity() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientCity());
            /*4. Setting state*/
                viewCurrentFormDataInfo.setState(demographic.getRecepientState() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientState());
            /*5. setting zipcode*/
                viewCurrentFormDataInfo.setZipCode(demographic.getRecepientZip5() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getRecepientZip5());


            /*setting comments*/
                viewCurrentFormDataInfo.setComments(demographic.getComments() == null ? CommonDataConstants.EMPTY_VALUE : demographic.getComments());

            }
        return viewCurrentFormDataInfo;
    }
}
