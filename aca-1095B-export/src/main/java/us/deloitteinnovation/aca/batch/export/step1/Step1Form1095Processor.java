package us.deloitteinnovation.aca.batch.export.step1;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.jaxb.ACA1095BDetailBuilder;
import us.deloitteinnovation.aca.jaxb.JaxbUtils;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.gov.treasury.irs.common.TINRequestTypeCodeType;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static us.deloitteinnovation.aca.batch.export.ExportUtil.sanitize;
import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.*;

/**
 * Converts Filer Demographics into FORM 1095B AIR7 objects for export to the IRS as XML SOAP message(s).
 */
public class Step1Form1095Processor implements ItemProcessor<Step1Form1095Dto, Step1Form1095Dto> {
    private static final Logger                                           logger     = LoggerFactory.getLogger(Step1Form1095Processor.class);
    /**
     * For Correction and Replacements, the original receipt id provided by the IRS.
     */
    String originalReceiptId = null;
    StepExecution stepExecution;
    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService;
    private ObjectFactory airFactory = new ObjectFactory();

    /**
     * <p>Since this step has no idea how many Form 1095B elements will be in a document, or in what order,
     * the recordId field is not set.</p>
     *
     * @param form1095BProcessDto
     * @return
     * @throws Exception
     */
    @Override
    public Step1Form1095Dto process(Step1Form1095Dto form1095BProcessDto) throws Exception {

        Filer filer = form1095BProcessDto.getFiler();

        /************************************/
        /** Part 1: Responsible Individual  */
        /************************************/
        final ResponsibleIndividualGrpType respInd = airFactory.createResponsibleIndividualGrpType();

        /** 1. Name of responsible individual */
        final OtherCompletePersonNameType respIndName = airFactory.createOtherCompletePersonNameType();
        respIndName.setPersonFirstNm(sanitize(filer.getRecipientFirstName()));
        respIndName.setPersonMiddleNm(sanitize(filer.getRecipientMiddleName()));
        respIndName.setPersonLastNm(sanitize(filer.getRecipientLastName()));
        respIndName.setSuffixNm(sanitize(filer.getRecipientSuffix()));
        respInd.setResponsibleIndividualName(respIndName);

        /** 2 Social security number (SSN) */
        if (StringUtils.isNotEmpty(filer.getRecipientSSN())) {
            respInd.setTINRequestTypeCd(TINRequestTypeCodeType.INDIVIDUAL_TIN);
            respInd.setSSN(sanitize(filer.getRecipientSSN()));
        } else if (StringUtils.isNotEmpty(filer.getRecipientTIN())) {
            respInd.setTINRequestTypeCd(TINRequestTypeCodeType.INDIVIDUAL_TIN);
            respInd.setSSN(sanitize(filer.getRecipientTIN()));
        } else {
            /** 3 Date of birth (If SSN is not available) */
            // TODO Should we send TIN here instead?
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = formatter.parse(filer.getRecipientDOB());
                genericSetDate(respInd, "setBirthDt", date);
            } catch (ParseException e) {
                // TODO Should this be fatal?  Why in the hell are we storing DOB as a string in DB?  Lukas Bradley
                logger.info("Parse exception on Filer DOB {} record source cd {} source id {} is empty.", filer.getRecipientDOB(),
                        filer.getSourceCd(), filer.getSourceUniqueId(), e);
            }
        }

        final USAddressGrpType respIndAddr = airFactory.createUSAddressGrpType();
        BusinessAddressGrpType busAdd = new BusinessAddressGrpType();
        busAdd.setUSAddressGrp(respIndAddr);
        respInd.setMailingAddressGrp(busAdd);
        /** 4 Street address (including apartment no.) */
        respIndAddr.setAddressLine1Txt(sanitize(filer.getRecipientAddLine1()));
        String line2 = sanitize(filer.getRecipientAddLine2());
        if (StringUtils.isNotEmpty(line2))
            respIndAddr.setAddressLine2Txt(line2);

        /** 5 City or town */
        respIndAddr.setCityNm(sanitize(filer.getRecipientCity()));

        /** 6 State or province */
        final String recipientState = filer.getRecipientState();
        try {
            final StateType stateType = StateType.fromValue(recipientState);
            respIndAddr.setUSStateCd(stateType);
        } catch (final Exception ex) {
            logger.warn(String.format(
                    "Invalid recipient state: [%s].  No corresponding StateType found.",
                        recipientState));
            return null;    // skip this item
        }

        /** 7 Country and ZIP or foreign postal code */
        respIndAddr.setUSZIPCd(sanitize(filer.getRecipientZip()));
        respIndAddr.setUSZIPExtensionCd(sanitize(filer.getRecipientZip4()));

        /** 8 Enter letter identifying Origin of the Policy (see instructions for codes) */
        respInd.setPolicyOriginCd(sanitize(filer.getPolicyOrigin()));
        /** 9 Small Business Health Options Program (SHOP) Marketplace identifier, if applicable */
        setAttributeIfExist(respInd, "setSHOPIdentificationNum", sanitize(filer.getShopIdentifier()), String.class);

        /*************************************************************/
        /** Part II: Employer Sponsored Coverage (see instructions) **/
        /*************************************************************/

        // Employer information not supported

        CorrectedRecordInfoGrpType correctedRecord = null;
        /** Corrected Record Info Group */
        if (BatchExportConstants.isJobCorrections(stepExecution)) {
            correctedRecord = airFactory.createCorrectedRecordInfoGrpType();
            correctedRecord.setCorrectedUniqueRecordId(createOriginalRecordId(form1095BProcessDto));
            correctedRecord.setCorrectedRecordPayeeTIN(sanitize(filer.getRecipientTIN()));
            OtherCompletePersonNameType correctedRecordPersonName = airFactory.createOtherCompletePersonNameType();
            correctedRecordPersonName.setPersonFirstNm(sanitize(filer.getRecipientFirstName()));
            correctedRecordPersonName.setPersonMiddleNm(sanitize(filer.getRecipientMiddleName()));
            correctedRecordPersonName.setPersonLastNm(sanitize(filer.getRecipientLastName()));
            correctedRecordPersonName.setSuffixNm(sanitize(filer.getRecipientSuffix()));
            correctedRecord.setCorrectedRecordPayeeName(correctedRecordPersonName);
        }

        /** Issuer Info Group */
        String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext());
        SourceSystemConfig config = sourceSystemConfigDataService.getByState(state, Integer.valueOf(filer.getTaxYear()));
        final IssuerInfoGrpType issuerInfo = airFactory.createIssuerInfoGrpType();
        final BusinessNameType issuerName = airFactory.createBusinessNameType();
        issuerName.setBusinessNameLine1Txt(sanitize(filer.getProviderName()));
        issuerInfo.setBusinessName(issuerName);
        final Long providerContactNo = filer.getProviderContactNo();
        final String contactPhoneNum = providerContactNo != null ? providerContactNo.toString() : "";
        issuerInfo.setContactPhoneNum(contactPhoneNum);
        issuerInfo.setEIN(sanitize(filer.getProviderEIN()));
        final BusinessAddressGrpType issuerAddrGrp = ExportUtil.getBusinessAddressFromFilerDemographics(filer);
        issuerInfo.setMailingAddressGrp(issuerAddrGrp);

        issuerInfo.setTINRequestTypeCd(TINRequestTypeCodeType.BUSINESS_TIN);
        List<EmployerCoveredIndividualType> employerCoveredIndividualTypes = new ArrayList<>();

        if (filer.getCoveredpersons() != null && filer.getCoveredpersons().size() > 0) {
            Iterator<Map.Entry<String, CoveredPerson>> entries = filer.getCoveredpersons().entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                CoveredPerson coveredPerson = (CoveredPerson) entry.getValue();
                EmployerCoveredIndividualType employerCoveredIndividualType = airFactory.createEmployerCoveredIndividualType();
                OtherCompletePersonNameType coveredIndividualName = airFactory.createOtherCompletePersonNameType();
                coveredIndividualName.setPersonFirstNm(sanitize(coveredPerson.getFirstName()));
                coveredIndividualName.setPersonLastNm(sanitize(coveredPerson.getLastName()));
                coveredIndividualName.setPersonMiddleNm(sanitize(coveredPerson.getMiddleName()));
                coveredIndividualName.setSuffixNm(sanitize(coveredPerson.getSuffix()));
                employerCoveredIndividualType.setCoveredIndividualName(coveredIndividualName);
                if (StringUtils.isNotEmpty(coveredPerson.getSsnPlainText())) {
                    employerCoveredIndividualType.setSSN(sanitize(coveredPerson.getSsnPlainText()));
                    employerCoveredIndividualType.setTINRequestTypeCd(TINRequestTypeCodeType.INDIVIDUAL_TIN);
                } else if (StringUtils.isNotEmpty(coveredPerson.getTin())) {
                    employerCoveredIndividualType.setSSN(sanitize(coveredPerson.getTin()));
                    employerCoveredIndividualType.setTINRequestTypeCd(TINRequestTypeCodeType.INDIVIDUAL_TIN);
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        Date date = formatter.parse(coveredPerson.getDob());
                        genericSetDate(employerCoveredIndividualType, "setBirthDt", date);
                    } catch (ParseException e) {
                        // TODO If this fails, should we throw an error?
                        logger.info(e.getMessage());
                    }
                }
                boolean coveredAllMonths = CommonEntityConstants.CHECKED.equalsIgnoreCase(coveredPerson.getAll());
                if (coveredAllMonths) {
                    employerCoveredIndividualType.setCoveredIndividualAnnualInd("1");
                } else {
                    employerCoveredIndividualType.setCoveredIndividualAnnualInd("0");
                    MonthIndGrpType monthIndGrpType = airFactory.createMonthIndGrpType();
                    monthIndGrpType.setJanuaryInd(coveredPerson.getJan());
                    monthIndGrpType.setFebruaryInd(coveredPerson.getFeb());
                    monthIndGrpType.setMarchInd(coveredPerson.getMar());
                    monthIndGrpType.setAprilInd(coveredPerson.getApr());
                    monthIndGrpType.setMayInd(coveredPerson.getMay());
                    monthIndGrpType.setJuneInd(coveredPerson.getJun());
                    monthIndGrpType.setJulyInd(coveredPerson.getJul());
                    monthIndGrpType.setAugustInd(coveredPerson.getAug());
                    monthIndGrpType.setSeptemberInd(coveredPerson.getSep());
                    monthIndGrpType.setOctoberInd(coveredPerson.getOct());
                    monthIndGrpType.setNovemberInd(coveredPerson.getNov());
                    monthIndGrpType.setDecemberInd(coveredPerson.getDec());
                    employerCoveredIndividualType.setCoveredIndividualMonthlyIndGrp(monthIndGrpType);
                }
                employerCoveredIndividualTypes.add(employerCoveredIndividualType);
            }
        }

        // Record id should not be set here.  It can only be known on FORM 1094B association/output.
        final Form1095BUpstreamDetailType form1095 = new ACA1095BDetailBuilder()
                .setCorrectedInd(BatchExportConstants.isJobCorrections(stepExecution))
                .setRecordId(0) // Hard coding this to '0' since this is being replaced in Step 3
                .setCorrectedRecordInfoGrp(correctedRecord)
                .setIssuerInfoGrp(issuerInfo)
                // Line number is always zero
                .setLineNum(0)
                .setResponsibleIndividualGrp(respInd)
                .setTaxYr(Integer.parseInt(filer.getTaxYear()))
                // TODO Fill in test scenario when we have test ids
                .setTestScenarioId(null)
                .addCoveredIndividualGrps(employerCoveredIndividualTypes)
                .build();
        form1095BProcessDto.setForm1095BUpstreamDetailType(form1095);
        return form1095BProcessDto;
    }


    /**
     * @param form1095BProcessDto
     * @return Combination of the receipt id | submission id | record id.
     */
    protected String createOriginalRecordId(Step1Form1095Dto form1095BProcessDto) {
        StringBuilder b = new StringBuilder(form1095BProcessDto.receiptId);
        b.append("|");
        b.append(form1095BProcessDto.irsRecordDetails1095BPK.getSubmissionId());
        b.append("|");
        b.append(form1095BProcessDto.irsRecordDetails1095BPK.getRecordId());
        return b.toString();
    }

    public String getOriginalReceiptId() {
        return originalReceiptId;
    }

    public void setOriginalReceiptId(String originalReceiptId) {
        this.originalReceiptId = originalReceiptId;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
