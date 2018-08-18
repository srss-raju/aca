package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.entity.FilerDemographicCW;
import us.deloitteinnovation.aca.model.CoveredPerson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tthakore on 10/5/2015.
 */
public class ViewPrintInfo {


    private static final Logger logger = LoggerFactory.getLogger(ViewPrintInfo.class);
    private boolean bFormView;//PDF or mail true for pdf.

    private String stateContactInfo;//normal message
    private String errorMessage;//like user not found

    private String taxYear;

    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    private String generatedDate;

    private String formStatus;

    private String policyOrigin;

    //recipient info
    private String recipientFirstName;
    private String recipientLastName;
    private String uidNumber;
    private String uidType;
    private String recipientDOB;
    private String recipientZip;
    private String recipientEmail;


    private String recipientMiddleName;
    private String recipientSuffixName;
    private String recipientDOBAddressLine1;
    private String recipientDOBAddressLine2;
    private String recipientCity;
    private String recipientState;
    private String correctionIndicator = "0";

    // provider info
    private  String providerName;
    private  String providerEIN;
    private  String providerContactNo;
    private String providerDOBAddressLine1;
    private String providerDOBAddressLine2;
    private String providerCity;
    private String providerState;
    private String providerZipCode5;

    private List<CoverageInfoDTO> coverageInfoDTOs = new ArrayList<CoverageInfoDTO>();



    public String getRecipientDOB() {
        return recipientDOB;
    }

    public void setRecipientDOB(String recipientDOB) {
        this.recipientDOB = recipientDOB;
    }

    public String getRecipientFirstName() {
        return recipientFirstName;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }

    public String getRecipientLastName() {
        return recipientLastName;
    }

    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
    }



    public String getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(String taxYear) {
        this.taxYear = taxYear;
    }


    public boolean isbFormView() {
        return bFormView;
    }

    public void setbFormView(boolean bFormView) {
        this.bFormView = bFormView;
    }

    public String getMessage() {
        return stateContactInfo;
    }

    public void setMessage(String message) {
        this.stateContactInfo = message;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public String getUidType() {
        return uidType;
    }

    public void setUidType(String uidType) {
        this.uidType = uidType;
    }


    public String getMaskedValue(String value) {
      return  (value != null)?value.replaceAll("^[0-9]{5}", "xxx-xx-"):"";
    }
    public String getUidNumber() {
        return uidNumber;
    }

    public void setUidNumber(String uidNumber) {
        this.uidNumber = uidNumber;
    }
    public String getRecipientState() {
        return recipientState;
    }

    public void setRecipientState(String recipientState) {
        this.recipientState = recipientState;
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public String getRecipientDOBAddressLine2() {
        return recipientDOBAddressLine2;
    }

    public void setRecipientDOBAddressLine2(String recipientDOBAddressLine2) {
        this.recipientDOBAddressLine2 = recipientDOBAddressLine2;
    }

    public String getRecipientDOBAddressLine1() {
        return recipientDOBAddressLine1;
    }

    public void setRecipientDOBAddressLine1(String recipientDOBAddressLine1) {
        this.recipientDOBAddressLine1 = recipientDOBAddressLine1;
    }

    public String getRecipientSuffixName() {
        return recipientSuffixName;
    }

    public void setRecipientSuffixName(String recipientSuffixName) {
        this.recipientSuffixName = recipientSuffixName;
    }

    public String getRecipientMiddleName() {
        return recipientMiddleName;
    }

    public void setRecipientMiddleName(String recipientMiddleName) {
        this.recipientMiddleName = recipientMiddleName;
    }

    public String getRecipientZip() {
        return recipientZip;
    }

    public void setRecipientZip(String recipientZip) {
        this.recipientZip = recipientZip;
    }

    public List<CoverageInfoDTO> getCoverageInfoDTOs() {
        return coverageInfoDTOs;
    }

    public void setCoverageInfoDTOs(List<CoverageInfoDTO> coverageInfoDTOs) {
        this.coverageInfoDTOs = coverageInfoDTOs;
    }

    public String getProviderZipCode5() {
        return providerZipCode5;
    }

    public void setProviderZipCode5(String providerZipCode5) {
        this.providerZipCode5 = providerZipCode5;
    }

    public String getCorrectionIndicator() {
        return correctionIndicator;
    }

    public void setCorrectionIndicator(String correctionIndicator) {
        if(correctionIndicator.length() > 0)
        {
            this.correctionIndicator = correctionIndicator;
        }
        else
        {
            if (CommonDataConstants.FormStatus.FORM_STATUS_REGENERATE.equals(this.formStatus)) {
                correctionIndicator= "correctedstamp";
            } else {
                correctionIndicator= "none";
            }
        }
    }

    public String getProviderState() {
        return providerState;
    }

    public void setProviderState(String providerState) {
        this.providerState = providerState;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderEIN() {
        return providerEIN;
    }

    public void setProviderEIN(String providerEIN) {
        this.providerEIN = providerEIN;
    }

    public String getProviderContactNo() {
        return providerContactNo;
    }

    public void setProviderContactNo(String providerContactNo) {
        this.providerContactNo = providerContactNo;
    }

    public String getProviderDOBAddressLine1() {
        return providerDOBAddressLine1;
    }

    public void setProviderDOBAddressLine1(String providerDOBAddressLine1) {
        this.providerDOBAddressLine1 = providerDOBAddressLine1;
    }

    public String getProviderDOBAddressLine2() {
        return providerDOBAddressLine2;
    }

    public void setProviderDOBAddressLine2(String providerDOBAddressLine2) {
        this.providerDOBAddressLine2 = providerDOBAddressLine2;
    }

    public String getProviderCity() {
        return providerCity;
    }

    public void setProviderCity(String providerCity) {
        this.providerCity = providerCity;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getPolicyOrigin() {
        return policyOrigin;
    }

    public void setPolicyOrigin(String policyOrigin) {
        this.policyOrigin = policyOrigin;
    }

    public String getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(String formStatus) {
        this.formStatus = formStatus;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }



    public void setDataValues(FilerDemographicCW filerdemographic){
        logger.info("setDataValues start  ");
        this.recipientFirstName = filerdemographic.getRecepientFirstName();
        this.recipientLastName  = filerdemographic.getRecepientLastName();
        this.recipientDOB =       (filerdemographic.getRecepientDob() != null)?df.format(filerdemographic.getRecepientDob()):"";
        this.taxYear =   (filerdemographic.getId().getTaxYear() != null)?filerdemographic.getId().getTaxYear().toString():"";
        this.recipientCity  = filerdemographic.getRecepientCity() ;
        this.recipientDOBAddressLine1 = filerdemographic.getRecepientAddressLine1();
        this.recipientDOBAddressLine2 = filerdemographic.getRecepientAddressLine2();
        this.recipientState = filerdemographic.getRecepientState();
        this.correctionIndicator = String.valueOf(filerdemographic.getCorrectionIndicator());
        this.recipientZip = filerdemographic.getRecepientZip5();
        this.providerName =  filerdemographic.getProviderName();
        this.providerCity = filerdemographic.getProviderCityOrTown();
        this.providerContactNo = String.valueOf(filerdemographic.getProviderContactNo());
        this.providerEIN = filerdemographic.getProviderIdentificationNumber();
        this.providerZipCode5 = filerdemographic.getProviderZipOrPostalCode();
        this.providerDOBAddressLine1 = filerdemographic.getProviderAddressLine1();
        this.providerDOBAddressLine2 = filerdemographic.getProviderAddressLine2();
        this.policyOrigin =  String.valueOf(filerdemographic.getPolicyOrigin());
        this.formStatus = filerdemographic.getFormStatus();
        this.generatedDate = (filerdemographic.getUpdatedDt() != null)?df.format(filerdemographic.getUpdatedDt()):"";
        this.providerState = filerdemographic.getProviderStateOrProvince();
        this.recipientMiddleName = filerdemographic.getRecepientMiddleName();
        if(filerdemographic.getRecepientSsn() != null)
        {
            setUidType("SSN");
            setUidNumber(getMaskedValue(filerdemographic.getRecepientSsn()));
        }
        else if(filerdemographic.getRecepientTin() != null)
        {
            setUidType("TIN");
            setUidNumber(getMaskedValue(filerdemographic.getRecepientTin()));
        }


        CoverageInfoDTO coverageInfoDTO = new CoverageInfoDTO();
        coverageInfoDTO.setRecipientName(this.recipientFirstName+" "+this.recipientLastName);
        coverageInfoDTO.setRecipientDOB(this.recipientDOB);
        coverageInfoDTO.setRecipientUID(this.uidNumber);
        coverageInfoDTO.setJan(String.valueOf(filerdemographic.getJan()));
        coverageInfoDTO.setFeb(String.valueOf(filerdemographic.getFeb()));
        coverageInfoDTO.setMar(String.valueOf(filerdemographic.getMar()));
        coverageInfoDTO.setApr(String.valueOf(filerdemographic.getApr()));
        coverageInfoDTO.setMay(String.valueOf(filerdemographic.getMay()));
        coverageInfoDTO.setJun(String.valueOf(filerdemographic.getJun()));
        coverageInfoDTO.setJul(String.valueOf(filerdemographic.getJul()));
        coverageInfoDTO.setAug(String.valueOf(filerdemographic.getAug()));
        coverageInfoDTO.setSep(String.valueOf(filerdemographic.getSep()));
        coverageInfoDTO.setOct(String.valueOf(filerdemographic.getOct()));
        coverageInfoDTO.setNov(String.valueOf(filerdemographic.getNov()));
        coverageInfoDTO.setDec(String.valueOf(filerdemographic.getDec()));
        this.getCoverageInfoDTOs().add(coverageInfoDTO);
        logger.info("setDataValues end  ");
    }

    public void setCoveredPersonList(List<CoveredPerson> coveredPersonList)
    {
        for (CoveredPerson item : coveredPersonList) {
            CoverageInfoDTO coverageInfoDTO = new CoverageInfoDTO();
            coverageInfoDTO.setRecipientName(item.getFirstName()+" "+item.getLastName());
            coverageInfoDTO.setRecipientDOB(item.getDob());

            // If SSN presented, set UID=SSN; if SSN not presented but TIN is, set UID=TIN; otherwise UID=undefined
            coverageInfoDTO.setRecipientUID(null == item.getSsn() || item.getSsn().length() == 0
                    ? getMaskedValue(item.getTin()) : getMaskedValue(item.getSsn()));
            if (coverageInfoDTO.getRecipientUID().length() == 0) {
                coverageInfoDTO.setRecipientUID(null);
            }

            coverageInfoDTO.setJan(item.getJan());
            coverageInfoDTO.setFeb(item.getFeb());
            coverageInfoDTO.setMar(item.getMar());
            coverageInfoDTO.setApr(item.getApr());
            coverageInfoDTO.setMay(item.getMay());
            coverageInfoDTO.setJun(item.getJun());
            coverageInfoDTO.setJul(item.getJul());
            coverageInfoDTO.setAug(item.getAug());
            coverageInfoDTO.setSep(item.getSep());
            coverageInfoDTO.setOct(item.getOct());
            coverageInfoDTO.setNov(item.getNov());
            coverageInfoDTO.setDec(item.getDec());
            this.getCoverageInfoDTOs().add(coverageInfoDTO);
        }
    }


}
