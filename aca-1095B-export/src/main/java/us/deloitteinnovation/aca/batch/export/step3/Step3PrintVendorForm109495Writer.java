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
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;
import us.deloitteinnovation.aca.batch.export.step4.Step4109495Data;
import us.deloitteinnovation.aca.entity.*;
import us.deloitteinnovation.aca.jaxb.JaxbUtils;
import us.deloitteinnovation.aca.repository.*;
import us.deloitteinnovation.profile.ProfileProperties;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;
import us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.Form109495BTrnsmtUpstreamType;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.genericSetLong;

/**
 * Write the provided 1094B and associated 1095B filers to the provided outputStream as XML.
 */
@Transactional
public class Step3PrintVendorForm109495Writer implements ItemWriter<Step3ProcessorResult> {

    private static final Logger LOG = LoggerFactory.getLogger(Step3PrintVendorForm109495Writer.class);

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
                String filename = ExportUtil.createFileNameForPrintVendor(stepExecution);

                // Add all Form 1095B XML Types
                form1095BXmlList = new ArrayList<>(pairing.getStep1Form1095bDtoList().size());
                int recordCount = 1;
                String form1095xml ;
                String prettyPrintedXML;
                for (Step1Form1095Dto form1095bDto : pairing.getStep1Form1095bDtoList()) {

                   form1095xml = JaxbUtils.renderToString(jaxb2FragmentMarshaller,
                   air7ObjectFactory.createForm1095BUpstreamDetail(form1095bDto.getForm1095BUpstreamDetailType())) ;

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
                    LOG.debug("Step 3 writing XML to {}", outputFile.getAbsoluteFile());
                }

                writeForm1094(outputFile, rootElementDoc, form1095BXmlList);

                // Subtract 1 from record count, as the count will be incremented on each loop
                ExportUtil.addForm109495FilenameForStep4(stepExecution.getJobExecution(), new Step4109495Data(outputFile, submissionId, recordCount - 1));
            }
            submissionId++;
        }
    }

    //TODO: 1/12/2017 @Rajesh Bongurala : Please delete these below commented methods if they are not being used.
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
    /*@Transactional(propagation = Propagation.REQUIRED)
    protected IrsTransmissionDetails createAndSaveIrsTransmissionDetails(Step3Form109495Pairing pairing, int year, String transmissionTypeCode, int submissionId, String filename) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Saving data for IRS transmission type {}, to filename {}, with {} 1095B forms.", transmissionTypeCode, filename, pairing.getStep1Form1095bDtoList().size());
        }

        //Retrieve original transmission id if present
        Integer originalTransmissionId = null;
        Integer originalSubmissionId = null;

        *//* Get the original transmission and submission id's in case the transmission type is Correction or Replacement *//*
        if(transmissionTypeCode.equals("C") || transmissionTypeCode.equals("R")) {

            if(pairing.getStep1Form1095bDtoList().size() > 0) {

                originalTransmissionId = pairing.getStep1Form1095bDtoList().get(0).getIrsRecordDetails1095BPK().getTransmissionId();
                originalSubmissionId = pairing.getStep1Form1095bDtoList().get(0).getIrsRecordDetails1095BPK().getSubmissionId();
            }
        }

        IrsTransmissionDetails irsTransmissionDetails = createTransmission(pairing.getStep2Form1094bDto().getSourceSystemConfig().getSourceCd(), year, transmissionTypeCode, filename);
        saveIrsTransmissionDetails(irsTransmissionDetails);
        IrsSubmissionDetails irsSubmissionDetails = createSubmission(irsTransmissionDetails.getTransmissionId(), submissionId, originalTransmissionId, originalSubmissionId);
        saveIrsSubmissionDetails(irsSubmissionDetails);
        createAndSaveDetails(irsTransmissionDetails.getTransmissionId(), submissionId, pairing.getStep1Form1095bDtoList());
        updateIrs1095XmlStatus(pairing.getStep1Form1095bDtoList(), transmissionTypeCode);

        if(transmissionTypeCode.equals("C")) {
            updateRecordDetailsEndStatus(originalTransmissionId, pairing.getStep1Form1095bDtoList());
        } else if (transmissionTypeCode.equals("R")){
            updateRecordDetailsEndStatus(originalTransmissionId, null);
        }

        return irsTransmissionDetails;
    }*/


    /**
     * Updates the records of a transmission in IRS_RECORD_DETAILS_1095B
     * with end status 'XC'
     * @param originalTransmissionId
     * @return void
     */
   /* private void updateRecordDetailsEndStatus(Integer originalTransmissionId, List<Step1Form1095Dto> step1Form1095DtoList) {

        List<Long> sourceUniqueIdList = new ArrayList();
        if( step1Form1095DtoList != null && ! step1Form1095DtoList.isEmpty()) {
            for (Step1Form1095Dto step1Form1095Dto : step1Form1095DtoList) {

                sourceUniqueIdList.add(step1Form1095Dto.getFiler().getSourceUniqueId());
            }
        }
        irsRecordDetails1095BJdbcRepository.updateEndStatusForRecords(originalTransmissionId,sourceUniqueIdList);
    }*/

    @Transactional(propagation = Propagation.REQUIRED)
    protected void saveIrsTransmissionDetails(IrsTransmissionDetails irsTransmissionDetails) {
        irsTransmissionDetailsRepository.save(irsTransmissionDetails);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected void saveIrsSubmissionDetails(IrsSubmissionDetails irsSubmissionDetails) {
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
    /*protected IrsSubmissionDetails createSubmission(Integer transmissionId, int submissionId, Integer originalTransmissionId, Integer originalSubmissionId) {
        IrsSubmissionDetailsPK id = new IrsSubmissionDetailsPK();
        id.setTransmissionId(transmissionId);
        id.setSubmissionId(submissionId);

        IrsSubmissionDetails details = new IrsSubmissionDetails();
        details.setId(id);
        details.setUpdatedDate(new Date());
        details.setUpdatedBy(UPDATED_BY);
        details.setOriginalTransmissionId( originalTransmissionId != null ? new Long( originalTransmissionId ) : null );
        details.setOriginalSubmissionId( originalSubmissionId != null ? new Long(originalSubmissionId) : null );

        return details;
    }*/

    /**
     * @param transmissionId
     * @param submissionId
     * @param form1095bDtoList List of Form 1095B to saveIrsTransmissionDetails to IRS Transmittal table.
     * @return List of newly created and persisted IrsRecordDetails1095B objects.
     */
   /* @Transactional(propagation = Propagation.REQUIRED)
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
            actualRecordId = getActualRecordId(form1095bDto.getForm1095BUpstreamDetailType().getRecordId().intValueExact());
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
    }*/

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
