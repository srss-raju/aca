package us.deloitteinnovation.aca.batch.export.step3;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;
import us.deloitteinnovation.aca.batch.export.step4.Step4109495Data;
import us.deloitteinnovation.aca.entity.*;
import us.deloitteinnovation.aca.jaxb.JaxbUtils;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.repository.*;
import us.deloitteinnovation.profile.ProfileProperties;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;
import us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.Form109495BTrnsmtUpstreamType;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.*;

/**
 * Write the provided 1094B and associated 1095B filers to the provided outputStream as XML.
 */
@Transactional
public class Step3Form109495Writer implements ItemWriter<Step3ProcessorResult> {

    private static final Logger LOG = LoggerFactory.getLogger(Step3Form109495Writer.class);

    /**
     * String constant to use for entities updated in this class.
     */
    private static final String UPDATED_BY = "System";
    private static final String TRANSMISSION_STATUS_CD = "XG";

    @Autowired
    @Qualifier("jaxb2Marshaller")
    Jaxb2Marshaller                     jaxb2Marshaller;
    @Autowired
    @Qualifier("jaxb2FragmentMarshaller")
    Jaxb2Marshaller                     jaxb2FragmentMarshaller ;
    @Autowired
    ExportJob1095Repository             exportJobRepository;
    @Autowired
    IrsTransmissionDetailsRepository    irsTransmissionDetailsRepository;
    @Autowired
    IrsSubmissionDetailsRepository      irsSubmissionDetailsRepository;
    @Autowired
    IrsRecordDetails1095BRepository     irsRecordDetails1095BRepository;
    @Autowired
    IrsRecordDetails1095BJdbcRepository irsRecordDetails1095BJdbcRepository;
    @Autowired
    Irs1095XMLRepository irs1095XMLRepository;
    @Autowired
    Irs109495XMLDetailsRepository irs109495XMLDetailsRepository;
    @Autowired
    ProfileProperties                   profileProperties;

    StepExecution stepExecution;
    private us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.ObjectFactory
            messageObjectFactory =
            new us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.ObjectFactory();


    private    ObjectFactory air7ObjectFactory             = new ObjectFactory();

    /** Regular expression to find the closing element of the Form 1094 detail. */
    private static Pattern       FORM_1094_END_ELEMENT_MATCHER = Pattern.compile("</.*?Form1094BUpstreamDetail>") ;

    /**
     * The implementation assumes the following:
     * <ol>
     * <li>The Step2Form1094Dto list provided is the exact number that should be written to the XML file.</li>
     * <li>Each Step2Form1094Dto object has its corresponding 1095B data pre-populated.</li>
     * </ol>
     *
     * <p>
     * Since it is possible for Form 1095s to be pre-rendered as XML, the logic first endures all 1095s are rendered and
     * stored in a List as Strings.  If the XML has not been pre-rendered, it is marshalled to a String along with
     * the appropriate recordId.  If it has been pre-rendered, the RecordId element will be replaced via regular
     * expression with the correct record id.
     * </p>
     *
     * <p>
     *
     * </p>
     *
     * @param list
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void write(List<? extends Step3ProcessorResult> list) throws Exception {
        JAXBElement<Form109495BTrnsmtUpstreamType> rootElementDoc;
        Form109495BTrnsmtUpstreamType rootElement;
        Form1094BUpstreamDetailType form1094bXmlType;
        Step2Form1094Dto form1094bDto;
        List<String> form1095BXmlList;

        String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext());
        Integer year = ExportUtil.getYear(stepExecution.getJobExecution().getExecutionContext());

        String tcc;
        for (Step3ProcessorResult dto : list) {

            int submissionId = 1;
            /*  Hard coded this to 1 since we send only 1 submission per transmission.
                This can be properly initialized when we start supporting multiple submissions
                per transmission
             */
            for (Step3Form109495Pairing pairing : dto.getPairings()) {
                // Wrap the Form 1094B XML Type
                form1094bDto = pairing.getStep2Form1094bDto();
                form1094bXmlType = form1094bDto.getForm1094BUpstreamDetailType();
                genericSetLong(form1094bXmlType, "setSubmissionId", submissionId);
                genericSetLong(form1094bXmlType, "setForm1095BAttachedCnt", pairing.getStep1Form1095bDtoList().size());
                // In the future, if we support multiple 1094s per transmission, the TCC should not be stored on SourceSystemConfig
                if (ExportUtil.isTestXml(profileProperties))
                    tcc = form1094bDto.getSourceSystemConfig().getTestTcc();
                else
                    tcc = form1094bDto.getSourceSystemConfig().getTransmitterControlCode();
                String filename = createFormDataFilename("B", tcc, LocalDateTime.now());

                // Add all Form 1095B XML Types
                form1095BXmlList = new ArrayList<>(pairing.getStep1Form1095bDtoList().size());
                int recordCount = 1;
                String form1095xml ;
                String prettyPrintedXML;
                for (Step1Form1095Dto form1095bDto : pairing.getStep1Form1095bDtoList()) {
                    // If the XML has already been rendered, replace the record id and add it to the list
                    if (form1095bDto.getRawXml() != null && form1095bDto.getRawXml().length > 0) {
                        form1095xml = replaceRecordId(new String(form1095bDto.getRawXml()), recordCount);
                    } else {
                        // Otherwise, go ahead and render the 1095 XML
                        genericSetLong(form1095bDto.getForm1095BUpstreamDetailType(), "setRecordId", recordCount);
                        form1095xml = JaxbUtils.renderToString(jaxb2FragmentMarshaller,
                                air7ObjectFactory.createForm1095BUpstreamDetail(form1095bDto.getForm1095BUpstreamDetailType())) ;
                    }
                    prettyPrintedXML = ExportUtil.getPrettyPrintedXML(form1095xml);
                    form1095BXmlList.add(prettyPrintedXML) ;
                    recordCount++ ;
                }
                // Render the Form 1094 without the 1095s
                rootElement = messageObjectFactory.createForm109495BTrnsmtUpstreamType();
                rootElement.getForm1094BUpstreamDetail().add(form1094bXmlType);
                rootElementDoc = messageObjectFactory.createForm109495BTransmittalUpstream(rootElement);

                File outputDir = ExportUtil.getOutputDirectory(state, profileProperties);

                File outputFile = new File(outputDir, filename);
                if (LOG.isDebugEnabled()) {
                    LOG.debug(String.format("Step 3 writing XML to %s", outputFile.getAbsoluteFile().getName()));
                }

                writeForm1094(outputFile, rootElementDoc, form1095BXmlList);

                // Save IRS Transmittal data
                IrsTransmissionDetails irsTransmissionDetails = createAndSaveIrsTransmissionDetails(pairing, year, BatchExportConstants.getJobTypeLetter(stepExecution), submissionId, filename);

                /* Save the data for Job3 i.e. creating manifests
                Subtract 1 from record count, as the count will be incremented on each loop*/
                save109495Data(irsTransmissionDetails.getTransmissionId(),irsTransmissionDetails.getTransmissionTypeCd(),new Step4109495Data(outputFile, submissionId, recordCount - 1));
            }
            submissionId++;
        }
    }

    /**
     * Creates and saves IrsTransmissionDetails, IrsSubmissionDetails and all required IrsRecordDetails1095B.  Each method is separate
     * so that performance metrics can be recorded.
     *
     * @param pairing
     * @param year
     * @param transmissionTypeCode
     * @param submissionId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    protected IrsTransmissionDetails createAndSaveIrsTransmissionDetails(Step3Form109495Pairing pairing, int year, String transmissionTypeCode, int submissionId, String filename) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Saving data for IRS transmission type {}, to filename {}, with {} 1095B forms.", transmissionTypeCode, filename, pairing.getStep1Form1095bDtoList().size());
        }
        Integer originalSubmissionId = null;
        Set<Integer> originalTransmissionIdSet = null;

        if( pairing.getStep1Form1095bDtoList().size() > 0 ) {
            /*
            *  If it's a Correction transmission, get the set of all the original transmissions for which the corrections are being sent
            *  We are doing this because we are now consolidating all the corrections into a single transmission instead of individual
            *  correction transmissions
            */
            originalTransmissionIdSet = new TreeSet<Integer>();
            if("C".equals(transmissionTypeCode)) {

                for (Step1Form1095Dto dto : pairing.getStep1Form1095bDtoList()) {
                    originalTransmissionIdSet.add(dto.getIrsRecordDetails1095BPK().getTransmissionId());
                }
                /*
                * Since we always have 1 submission per transmission, hard coding this to 1 here
                * In case we support multiple submissions in future, we will have to get this value as
                * origialSubmissionId = pairing.getStep1Form1095bDtoList().get(0).getIrsRecordDetails1095BPK().getSubmissionId();
                */
                originalSubmissionId = 1;
           }  // If it's a Replacement transmission, get the original transmission id. Consolidations doesn't happen for Replacements
            else if("R".equals(transmissionTypeCode)){

                originalTransmissionIdSet.add(pairing.getStep1Form1095bDtoList().get(0).getIrsRecordDetails1095BPK().getTransmissionId());
                originalSubmissionId = 1;
            }
        }

        IrsTransmissionDetails irsTransmissionDetails = createTransmission(pairing.getStep2Form1094bDto().getSourceSystemConfig().getSourceCd(), year, transmissionTypeCode, filename);
        saveIrsTransmissionDetails(irsTransmissionDetails);
        List<IrsSubmissionDetails> irsSubmissionDetails = createSubmission(irsTransmissionDetails.getTransmissionId(), submissionId, originalTransmissionIdSet, originalSubmissionId);
        saveIrsSubmissionDetails(irsSubmissionDetails);
        createAndSaveDetails(irsTransmissionDetails.getTransmissionId(), submissionId, pairing.getStep1Form1095bDtoList());
        updateIrs1095XmlStatus(pairing.getStep1Form1095bDtoList(), transmissionTypeCode);

        if(transmissionTypeCode.equals("C")) {
            updateRecordDetailsEndStatus(originalTransmissionIdSet, pairing.getStep1Form1095bDtoList(),year);
        } else if (transmissionTypeCode.equals("R")){
            updateRecordDetailsEndStatus(originalTransmissionIdSet, null,year);
        }

        return irsTransmissionDetails;
    }


    protected void save109495Data (Integer transmissionId, String transmissionTypeCd, Step4109495Data step4109495Data){

        Irs109495XMLDetailsEntity irs109495XMLDetailsEntity = new Irs109495XMLDetailsEntity();
        irs109495XMLDetailsEntity.setTransmissionId(transmissionId);
        irs109495XMLDetailsEntity.setForm1094BCount(step4109495Data.getForm1094bCount());
        irs109495XMLDetailsEntity.setForm1095BCount(step4109495Data.getForm1095bCount());
        irs109495XMLDetailsEntity.setXmlFilePath(step4109495Data.getFilename().getAbsolutePath());
        irs109495XMLDetailsEntity.setManifestCreated(Boolean.FALSE);
        irs109495XMLDetailsEntity.setTransmissionTypeCd(transmissionTypeCd);
        irs109495XMLDetailsEntity.setUpdatedBy("System");
        irs109495XMLDetailsEntity.setUpdatedDate(new Date());

        irs109495XMLDetailsRepository.save(irs109495XMLDetailsEntity);
    }


    /**
     * Updates the records of a transmission in IRS_RECORD_DETAILS_1095B
     * with end status 'XC'
     * @param originalTransmissionIdSet
     * @return void
     */
    private void updateRecordDetailsEndStatus(Set<Integer> originalTransmissionIdSet, List<Step1Form1095Dto> step1Form1095DtoList, Integer year) {

        List<Long> sourceUniqueIdList = new ArrayList();
        if( step1Form1095DtoList != null && ! step1Form1095DtoList.isEmpty()) {
            for (Step1Form1095Dto step1Form1095Dto : step1Form1095DtoList) {

                sourceUniqueIdList.add(step1Form1095Dto.getFiler().getSourceUniqueId());
            }
        }
        irsRecordDetails1095BJdbcRepository.updateEndStatusForRecords(originalTransmissionIdSet,sourceUniqueIdList,year);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void saveIrsTransmissionDetails(IrsTransmissionDetails irsTransmissionDetails) {
        irsTransmissionDetailsRepository.save(irsTransmissionDetails);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void saveIrsSubmissionDetails(List<IrsSubmissionDetails> irsSubmissionDetails) {
            irsSubmissionDetailsRepository.save(irsSubmissionDetails);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void updateIrs1095XmlStatus(List<Step1Form1095Dto> step1Form1095DtoList, String transmissionTypeCode) {

        Irs1095XML irs1095XML;
        Irs1095XMLPK id;

        Set<Irs1095XML> irs1095XMLSet = new HashSet<>();
        for (Step1Form1095Dto dto : step1Form1095DtoList){
            id = new Irs1095XMLPK();
            id.setSourceUniqueId(dto.getFiler().getSourceUniqueId());
            id.setSourceCd(dto.getFiler().getSourceCd());
            id.setTaxYear(Integer.parseInt(dto.getFiler().getTaxYear()));

            irs1095XML = new Irs1095XML();
            irs1095XML.setId(id);
            irs1095XML.setUpdatedBy(UPDATED_BY);
            irs1095XML.setUpdatedDate(new Date());
            irs1095XML.setIrsTransmissionStatusCd(TRANSMISSION_STATUS_CD);
            irs1095XML.setIrs1095BXml(new String(dto.getRawXml()));
            irs1095XMLSet.add(irs1095XML);
        }
        irs1095XMLRepository.save(irs1095XMLSet);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected IrsTransmissionDetails createTransmission(String sourceCode, Integer taxYear, String transmissionTypeCode, String filename) {
        IrsTransmissionDetails details = new IrsTransmissionDetails();
        details.setUpdatedDate(new Date());
        details.setUpdatedBy(UPDATED_BY);
        details.setSourceCd(sourceCode);
        details.setTaxYear(taxYear);
        details.setTransmissionFormType("B");
        details.setTransferDate(new Date());
        details.setTransmissionTypeCd(transmissionTypeCode);
        details.setTransmissionFileName(filename);
        return details;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected List<IrsSubmissionDetails> createSubmission(Integer transmissionId, int submissionId, Set<Integer> originalTransmissionIdSet, Integer originalSubmissionId) {

        List<IrsSubmissionDetails> irsSubmissionDetailsList = new ArrayList<>();

        if( originalTransmissionIdSet.isEmpty()){

            IrsSubmissionDetails details = new IrsSubmissionDetails();
            details.setTransmissionId(transmissionId);
            details.setSubmissionId(submissionId);
            details.setUpdatedDate(new Date());
            details.setUpdatedBy(UPDATED_BY);
            details.setOriginalTransmissionId(null);
            details.setOriginalSubmissionId(null);

            irsSubmissionDetailsList.add(details);
        } else {

            for (Integer originalTransmissionId : originalTransmissionIdSet) {
                /*IrsSubmissionDetailsPK id = new IrsSubmissionDetailsPK();*/


                IrsSubmissionDetails details = new IrsSubmissionDetails();
                details.setTransmissionId(transmissionId);
                details.setSubmissionId(submissionId);
                details.setUpdatedDate(new Date());
                details.setUpdatedBy(UPDATED_BY);
                details.setOriginalTransmissionId(originalTransmissionId != null ? new Long(originalTransmissionId) : null);
                details.setOriginalSubmissionId(originalSubmissionId != null ? new Long(originalSubmissionId) : null);

                irsSubmissionDetailsList.add(details);
            }
        }

        return irsSubmissionDetailsList;
    }

    /**
     * @param transmissionId
     * @param submissionId
     * @param form1095bDtoList List of Form 1095B to saveIrsTransmissionDetails to IRS Transmittal table.
     * @return List of newly created and persisted IrsRecordDetails1095B objects.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    protected Set<IrsRecordDetails1095B> createAndSaveDetails(Integer transmissionId, int submissionId, List<Step1Form1095Dto> form1095bDtoList) {
        IrsRecordDetails1095B details;
        IrsRecordDetails1095BPK id;
        Integer actualRecordId;

        Set<IrsRecordDetails1095B> set = new HashSet<>();
        for (Step1Form1095Dto form1095bDto : form1095bDtoList) {
            details = new IrsRecordDetails1095B();
            id = new IrsRecordDetails1095BPK();
            id.setTransmissionId(transmissionId);
            id.setSubmissionId(submissionId);
            actualRecordId = getActualRecordId(genericReturnBigIntOrIntOrString(form1095bDto.getForm1095BUpstreamDetailType().getRecordId()));
            id.setRecordId(actualRecordId);
            details.setId(id);
            Filer filer = form1095bDto.getFiler();
            details.setSourceCode(filer.getSourceCd());
            details.setSourceUniqueId(filer.getSourceUniqueId());
            details.setUpdatedBy(UPDATED_BY);
            details.setUpdatedDate(new Date());
            details.setFilerDemoSeq(filer.getFilerDemoSeq());
            // TODO The IRS Transmission Status field is hard coded as Processing.  Not sure how this should be handled.
            //details.setRecordStatus("PR"); // Record status will be blank as it will be updated from front end.
            set.add(details);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Saving batch of {} Form 1095B data to IRS Record Details", set.size());
        }
        //irsRecordDetails1095BRepository.save(list);
        irsRecordDetails1095BJdbcRepository.saveInBatch(set);
        return set;
    }

    /**
     * The Form Data file naming convention includes the Form Type, a static indicator (Request), the transmitter’s TCC,
     * and the date and time (in GMT time) the transmitter submits the file. The name specifically includes the following components:
     * <p>
     * 1094(form type)_Request_(TCC)_(Date)T(TimeStamp)Z.xml
     * <p>
     * For example:
     * 1094B_Request_BY01G_20140101T010102000Z.xml
     * 1094C_Request_BY02G_20140101T010102000Z.xml
     *
     * @param formType
     * @param tcc      The Transmitter's Control Code.
     * @param dateTime Date to utilize in the filename.  Will almost always be the current date/time.  Will be set to UTC timezone.
     * @return
     */
    protected String createFormDataFilename(String formType, String tcc, LocalDateTime dateTime) {
        // TODO This UTC timezone logic is wrong.  Always renders as provided time, not UTC time.  Needs to be fixed.
        dateTime.atOffset(ZoneOffset.UTC);
        // Don't include the Zed for timezone, or it will render the UTC difference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssSSS'Z'");
        return "1094" + formType + "_Request_" + tcc + "_" + formatter.format(dateTime) + ".xml";
    }

    /**
     * @param xml
     * @param recordId
     * @return String with the first RecordId element contents replaced with the given recordId.
     */
    protected String replaceRecordId(String xml, int recordId) {
        // Match the first RecordId found and replace it.
        String xml2 = xml.replaceFirst("<RecordId>.*?</RecordId>", "<RecordId>" + recordId + "</RecordId>");
        return xml2.replaceFirst("<ns2:RecordId>.*?</ns2:RecordId>", "<ns2:RecordId>" + recordId + "</ns2:RecordId>");
    }

    /**
     * Outputs the combination of a single 1094 and one or more 1095s.  Since the 1095s have been pre-rendered as XML Strings,
     * the 1094 is rendered as an XML String first, split on the end Form1094BUpstreamDetail element, and the beginning written to the file.
     * Then all 1095s are written, followed by the closing 1094 XML.
     * @param file
     * @param form1094
     * @param form1095s
     * @throws IOException
     */
    protected void writeForm1094(File file, JAXBElement<Form109495BTrnsmtUpstreamType> form1094, List<String> form1095s) throws IOException {
        String form1094xml = JaxbUtils.renderToString(jaxb2Marshaller, form1094) ;
        // Determine the position of the 1094 closing element </Form1094BUpstreamDetail>
        Matcher matcher = FORM_1094_END_ELEMENT_MATCHER.matcher(form1094xml) ;
        matcher.find() ;
        int form1094elementEndPosition = matcher.start() ;
        FileWriter writer = null ;
        try {
            writer = new FileWriter(file) ;
            // Write the start of the 1094
            writer.write(form1094xml.substring(0,form1094elementEndPosition));
            // Write all 1095s
            for (String form1095 :form1095s) {
                writer.write(form1095) ;
            }
            // Write the end of the 1094
            writer.write(form1094xml.substring(form1094elementEndPosition)) ;
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    protected Integer getActualRecordId(Integer recordId){
        Integer actualRecordId ;
        actualRecordId = (recordId > 25000 ? recordId % 25000 : recordId); // Since we send 25000 records per transmission
        actualRecordId = (actualRecordId == 0 ? 25000 : actualRecordId);
        return actualRecordId;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
