package us.deloitteinnovation.aca.batch.export.step1;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.batch.export.TransmissionIdStack;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;
import us.deloitteinnovation.aca.batch.export.step3.Step3Form109495Pairing;
import us.deloitteinnovation.aca.batch.export.step3.Step3ProcessorResult;
import us.deloitteinnovation.aca.batch.export.step4.Step4109495Data;
import us.deloitteinnovation.aca.batch.export.step4.Step4Form109495HeaderAndXmlDto;
import us.deloitteinnovation.aca.batch.export.step4.Step4ManifestData;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.constants.PrintVendorConstants;
import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;
import us.deloitteinnovation.aca.entity.PrintDetail;
import us.deloitteinnovation.aca.entity.PrintDetailPK;
import us.deloitteinnovation.aca.exception.PrintVendorFileNameVerificationDecider;
import us.deloitteinnovation.aca.jaxb.JaxbUtils;
import us.deloitteinnovation.aca.repository.*;
import us.deloitteinnovation.profile.ProfileProperties;
import us.gov.treasury.irs.common.BinaryFormatCodeType;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;
import us.gov.treasury.irs.msg.acauibusinessheader.TransmitterACAUIBusinessHeaderType;
import us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.Form109495BTrnsmtUpstreamType;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static us.deloitteinnovation.aca.batch.export.ExportUtil.*;
import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.*;

/**
 * Renders each Form1095BUpstreamDetailType as encapsulated within the list of Form1095bProcessDto objects.
 * Stores the entire Form1095bProcessDto, along with rendered XML to the ExportJob1095Repository.
 *
 * @see Form1095BUpstreamDetailType
 * @see ExportJob1095Repository
 * @see Step1Form1095Dto
 */
@Component
public class Step1Form1095PrintVendorWriter implements ItemWriter<Step1Form1095Dto> {
    private static final Logger LOG = LoggerFactory.getLogger(Step1Form1095PrintVendorWriter.class);
    private static Pattern FORM_1094_END_ELEMENT_MATCHER = Pattern.compile("</.*?Form1094BUpstreamDetail>");
    @Autowired
    @Qualifier("jaxb2FragmentMarshaller")
    protected Jaxb2Marshaller jaxb2FragmentMarshaller;
    StepExecution stepExecution;
    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService ;
    @Autowired
    TransmissionIdStack transmissionIdStack;
    @Autowired
    BatchInfoService batchInfoService;
    @Autowired
    PrintVendorJdbcRepository printVendorJdbcRepository;
    @Autowired
    @Qualifier("jaxb2Marshaller")
    Jaxb2Marshaller                     jaxb2Marshaller;
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
    us.gov.treasury.irs.msg.acauibusinessheader.ObjectFactory  headerObjectFactory = new us.gov.treasury.irs.msg.acauibusinessheader.ObjectFactory() ;
    @Autowired
    PrintVendorFileNameVerificationDecider printVendorFileNameVerificationDecider;
    String currentFileName;
    BatchInfoDto batchInfo = null;
    private ObjectFactory air7ObjectFactory = new ObjectFactory();
    private ObjectFactory step3Air7ObjectFactory = new ObjectFactory();
    private us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.ObjectFactory messageObjectFactory = new us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.ObjectFactory();

    /**
     * Renders the Form1095bProcessDto data as XML and stores it within a Step1Form1095Dto object
     * in the ExportJob1095Repository.
     *
     * @param aca1095Forms
     * @throws Exception
     */
    @Override
    public void write(List<? extends Step1Form1095Dto> aca1095Forms) throws Exception {
        String xmlString;
        List<PrintDetail> printDetailList = new ArrayList<>();
        List<FilerDemographic> filerDemographicList = new ArrayList<>();
        PrintVendorConstants.FILECOUNT = PrintVendorConstants.FILECOUNT + 1;
        batchInfo = new BatchInfoDto();
    	batchInfo.setReceiveDt(new Date());
        int batchId = batchInfoService.save(batchInfo);
        LOG.info("Batch Id  ----->>> "+batchId);
        batchInfo.setBatchId(batchId);
        currentFileName = ExportUtil.createFileNameForPrintVendor(stepExecution);
        for (Step1Form1095Dto step1Form1095Dto : aca1095Forms) {
            xmlString = writeAsXml(step1Form1095Dto.form1095BUpstreamDetailType, this.jaxb2FragmentMarshaller, this.air7ObjectFactory);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Form 1095B for source code {} and source unique id {}:\n{}", step1Form1095Dto.getFiler().getSourceCd(), step1Form1095Dto.getFiler().getSourceUniqueId(), xmlString);
            }
            step1Form1095Dto.setRawXml(xmlString.getBytes(Charset.forName("UTF-8")));
            PrintDetail printDetail = new PrintDetail();
            createPrintDetailsData(printDetail, printDetailList,step1Form1095Dto,currentFileName);
            FilerDemographic filerDemographic = new FilerDemographic();
            updateFilerDemographicStatus(filerDemographic,filerDemographicList,step1Form1095Dto);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("Step1 write finished size {}", aca1095Forms.size());
        }
        stepExecution.getJobExecution().getExecutionContext().put("FORM1095BList", aca1095Forms) ;
        stepExecution.getJobExecution().getExecutionContext().put("PRINTDETAILSLIST", printDetailList) ;
        stepExecution.getJobExecution().getExecutionContext().put("FILERDEMOSTATUS", filerDemographicList) ;
        stepExecution.getJobExecution().getExecutionContext().put("FILECOUNT", PrintVendorConstants.FILECOUNT) ;

        form109495Pairing();

    }

    /**
     * Helper method to render a Form1095B as XML.
     *
     * @param form1095b
     * @param marshaller
     * @param air7ObjectFactory
     * @return Form 1095B data as an XML String.
     */
    protected static String writeAsXml(Form1095BUpstreamDetailType form1095b, Jaxb2Marshaller marshaller, ObjectFactory air7ObjectFactory) {
        // Wrap the IRS data object in the SOAP wrapper
        JAXBElement<Form1095BUpstreamDetailType> elementWrapper = air7ObjectFactory.createForm1095BUpstreamDetail(form1095b);
        StringWriter writer = new StringWriter();
        marshaller.marshal(elementWrapper, new StreamResult(writer));
        return writer.toString();
    }

	private void createPrintDetailsData(PrintDetail printDetail, List<PrintDetail> printDetailList,Step1Form1095Dto step1Form1095Dto,String filename){
        PrintDetailPK filerDemographicPK= new PrintDetailPK();
        filerDemographicPK.setSourceUniqueId(step1Form1095Dto.getFiler().getSourceUniqueId());
        filerDemographicPK.setSourceCd(step1Form1095Dto.getFiler().getSourceCd());
        filerDemographicPK.setTaxYear(step1Form1095Dto.getFiler().getTaxYear());
        printDetail.setId(filerDemographicPK);
        printDetail.setBatchId(batchInfo.getBatchId());
        printDetail.getId().setPrintFileName(filename);
        printDetail.setPrintStatus(CommonDataConstants.PrintStatus.READY_TO_MAIL);
        printDetail.setUpdatedBy(CommonEntityConstants.SYSTEM);
        printDetail.setUpdatedDt(new Date());
        printDetail.setPrintReason(step1Form1095Dto.getFiler().getUpdatedBy());
        printDetail.setOriginalFormStatus(step1Form1095Dto.getFiler().getFormStatus());
        printDetail.setCorrectionIndicator(step1Form1095Dto.getFiler().getCorrectionIndicator());
        printDetailList.add(printDetail);
    }

    private void updateFilerDemographicStatus(FilerDemographic filerDemographic, List<FilerDemographic> filerDemographicList, Step1Form1095Dto step1Form1095Dto) {
        FilerDemographicPK filerDemographicPK = new FilerDemographicPK();
        filerDemographicPK.setSourceUniqueId(step1Form1095Dto.getFiler().getSourceUniqueId());
        filerDemographicPK.setSourceCd(step1Form1095Dto.getFiler().getSourceCd());
        filerDemographicPK.setTaxYear(Integer.parseInt(step1Form1095Dto.getFiler().getTaxYear()));
        filerDemographic.setId(filerDemographicPK);
        filerDemographic.setFormStatus(CommonDataConstants.FormStatus.FORM_STATUS_GENERATED);
        filerDemographicList.add(filerDemographic);
    }

    private void form109495Pairing() throws Exception{
    	List<Step2Form1094Dto> form1094Data=(List<Step2Form1094Dto>)stepExecution.getJobExecution().getExecutionContext().get("FORM1094BList");
        Step3Form109495Pairing dto = new Step3Form109495Pairing();
        dto.setStep2Form1094bDto(form1094Data.get(0));
        dto.setStep1Form1095bDtoList((List<Step1Form1095Dto>) stepExecution.getJobExecution().getExecutionContext().get("FORM1095BList"));
        step3Process(dto);
    }

    public void step3Process(Step3Form109495Pairing step3ProcessorPairing) throws Exception {
        Step3ProcessorResult result = new Step3ProcessorResult() ;
        List<Step3ProcessorResult> list = new ArrayList<Step3ProcessorResult>();

        Step2Form1094Dto form1094bDto = step3ProcessorPairing.getStep2Form1094bDto() ;
        List<Step1Form1095Dto> form1095List = step3ProcessorPairing.getStep1Form1095bDtoList() ;

        Step3Form109495Pairing innerPair = new Step3Form109495Pairing(form1094bDto) ;
        result.getPairings().add(innerPair) ;
        int count = 0 ;
        for (Step1Form1095Dto form1095bDto : form1095List) {
            // Once we have our maximum per document, store the current innerPair and continue with a new one.
            if (count == 25000) {
                count = 0 ;
                innerPair = new Step3Form109495Pairing(form1094bDto) ;
                result.getPairings().add(innerPair) ;
            }
            innerPair.step1Form1095bDtoList.add(form1095bDto) ;
            count++ ;
        }
        list.add(result);
        step3Write(list);
    }
    
    public void step3Write(List<? extends Step3ProcessorResult> list) throws Exception {
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

                LOG.info("File Name -------->>"+currentFileName);
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

                File outputDir = ExportUtil.getPrintVendorOutputDirectory(profileProperties);

                File outputFile = new File(outputDir, currentFileName);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Step 3 writing XML to {}", outputFile.getAbsoluteFile());
                }

                writeForm1094(outputFile, rootElementDoc, form1095BXmlList);

                // Subtract 1 from record count, as the count will be incremented on each loop
                ExportUtil.addForm109495FilenameForStep4(stepExecution.getJobExecution(), new Step4109495Data(outputFile, submissionId, recordCount - 1));
            }
            submissionId++;
        }

        step4Read();
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

    public void step4Read() throws Exception {
        List<Step4109495Data> form109495bFiles = getForm109495FilenamesForStep4(stepExecution.getJobExecution()) ;


        String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext()) ;
        Integer year = ExportUtil.getYear(stepExecution.getJobExecution().getExecutionContext()) ;
        int filenamesCounter = getForm109495FilenamesCounterForStep4(stepExecution) ;

        Step4Form109495HeaderAndXmlDto dto = new Step4Form109495HeaderAndXmlDto() ;
        dto.sourceSystem = sourceSystemConfigDataService.getByState(state,year) ;

        Step4109495Data fileData = form109495bFiles.get(filenamesCounter) ;
        dto.formDataFile = fileData.filename ;
        dto.total1094Forms = fileData.form1094bCount ;
        dto.total1095Forms = fileData.form1095bCount ;
        dto.transmitterName = new BusinessNameType() ;
        dto.transmitterName.setBusinessNameLine1Txt(sanitize(dto.sourceSystem.getProviderName()));
        dto.contactCompany = new CompanyInformationGrpType() ;
        dto.contactCompany.setCompanyNm(dto.sourceSystem.getProviderName());
        dto.contactCompany.setContactNameGrp(getContactName(dto.getSourceSystem()));
        dto.contactCompany.setContactPhoneNum(Long.toString(dto.sourceSystem.getProviderContactNo()));
        dto.contactCompany.setMailingAddressGrp(getBusinessAddressFromSourceSystemConfig(dto.sourceSystem));
        //Set Transmission type as per the Job which is being run
        //dto.transmissionType = TransmissionTypeCdType.valueOf(BatchExportConstants.getJobTypeLetter(stepExecution));
        dto.isPriorYearFiling = false ;
        dto.paymentYear = year ;
        // TODO Get from DB or constant
        dto.softwareDeveloper = getVendorInfo() ;
        dto.softwareId = "15A0001491" ;

        incrementForm109495FilenamesCounterForStep4(stepExecution) ;
        step4Process( dto) ;
    }
    
    public void step4Process(Step4Form109495HeaderAndXmlDto dto) throws Exception {
        validateFile(dto.formDataFile);
        dto.formDataSize = dto.formDataFile.length();
        MessageDigest md = MessageDigest.getInstance("MD5");
        dto.formDataMd5 = (new HexBinaryAdapter()).marshal(md.digest(FileUtils.readFileToByteArray(dto.formDataFile)));

        String originalReceiptId = ExportUtil.getReceiptId(stepExecution.getJobExecution().getExecutionContext()) ;
        Step4ManifestData manifestData = new Step4ManifestData();
        List<Step4ManifestData> list = new ArrayList<Step4ManifestData>();

        manifestData.acaTrnsmtManifestReqDtlType = createManifestHeader(dto, originalReceiptId, dto.total1094Forms, dto.total1095Forms);

        // TODO Switch to correct TCC in non-test mode
        if (ExportUtil.isTestXml(profileProperties))
            manifestData.tcc = dto.getSourceSystem().getTestTcc();
        else
            manifestData.tcc = dto.getSourceSystem().getTransmitterControlCode();
        list.add(manifestData);
        step4write(list);
    }

    /**
     * Ensures that a Form 109495 file was provided to the processor and it exists.
     *
     * @param form10945File
     * @throws IllegalArgumentException If the file object is null, or the file does not exist.
     */
    protected void validateFile(File form10945File) {
        if (form10945File == null) {
            throw new IllegalArgumentException("Form 109495B file was not provided to Step 4 Processor. A file is required " +
                    "to calculate checksum and retrieve file size.");
        }

        if (!form10945File.exists()) {
            throw new IllegalArgumentException("Form 109495B file not found using path and filename '" + form10945File.getAbsolutePath() + "'.  A file is required " +
                    "to calculate checksum and retrieve file size.");
        }
    }

    /**
     * @param dto
     * @return The DTO information converted into a manifest.
     */
    protected ACATrnsmtManifestReqDtlType createManifestHeader(Step4Form109495HeaderAndXmlDto dto, String originalReceiptId, int total1094Forms, int total1095Forms) throws Exception {
        ACATrnsmtManifestReqDtlType manifest = air7ObjectFactory.createACATrnsmtManifestReqDtlType();
        manifest.setPaymentYr(JaxbUtils.createXmlGregorianCalendarNoTimezone(dto.paymentYear));

        // Only set the original receipt if the transmission is a REPLACEMENT
        if (BatchExportConstants.isJobReplacement(this.stepExecution)) {

            if(StringUtils.isBlank(originalReceiptId)) {

                Integer currentTransmissionId = transmissionIdStack.peekTransmissionId();
                String receiptId = irsTransmissionDetailsRepository.getOriginalReceiptIdForTransmissionId(currentTransmissionId);
                manifest.setOriginalReceiptId(receiptId);
            }else {
                manifest.setOriginalReceiptId(originalReceiptId);
            }
        }

        // “0” for current filing year, “1” for prior filing year
        manifest.setPriorYearDataInd(dto.isPriorYearFiling ? "1" : "0");
        manifest.setTransmissionTypeCd(dto.transmissionType);
        manifest.setEIN(dto.sourceSystem.getProviderIdentificationNumber());
        manifest.setTransmitterNameGrp(dto.transmitterName);
        manifest.setCompanyInformationGrp(dto.contactCompany);
        manifest.setVendorInformationGrp(dto.softwareDeveloper);
        // Determines if this is a test or production manifest.  “T” – AATS “P” – Production
        if (ExportUtil.isTestXml(profileProperties))
            manifest.setTestFileCd("T");
        else
            manifest.setTestFileCd("P");
        genericSetLong(manifest, "setTotalPayerRecordCnt", total1094Forms);
        genericSetLong(manifest, "setTotalPayeeRecordCnt", total1095Forms);
        manifest.setSoftwareId(dto.softwareId);
        manifest.setFormTypeCd(dto.form10945Type.toString());
        manifest.setBinaryFormatCd(BinaryFormatCodeType.APPLICATION_XML);
        manifest.setChecksumAugmentationNum(dto.formDataMd5);
        manifest.setAttachmentByteSizeNum(BigInteger.valueOf(dto.formDataSize));
        manifest.setDocumentSystemFileNm(dto.formDataFile.getName());
        return manifest;
    }

    public void step4write(List<? extends Step4ManifestData> list) throws Exception {
        String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext()) ;
        File outputDir = ExportUtil.getPrintVendorOutputDirectory(profileProperties) ;

        TransmitterACAUIBusinessHeaderType header ;
        JAXBElement<TransmitterACAUIBusinessHeaderType> rootElementDoc ;
        ACABulkBusinessHeaderRequestType headerRequestType ;

        for (Step4ManifestData manifestData : list) {
            ACATrnsmtManifestReqDtlType manifest = manifestData.acaTrnsmtManifestReqDtlType ;
            headerRequestType = air7ObjectFactory.createACABulkBusinessHeaderRequestType() ;
            headerRequestType.setTimestamp(JaxbUtils.createXmlGregorianCalendarNoTimezone(new Date()));
            headerRequestType.setUniqueTransmissionId(generateUniqueTransmissionId(manifestData.getTcc()));

            header = headerObjectFactory.createTransmitterACAUIBusinessHeaderType();
            header.setACATransmitterManifestReqDtl(manifest);
            header.setACABusinessHeader(headerRequestType);
            rootElementDoc = headerObjectFactory.createACAUIBusinessHeader(header) ;

            String filename = createManifestFilename(manifestData.acaTrnsmtManifestReqDtlType.getDocumentSystemFileNm()) ;
            File outputFile = new File(outputDir, filename) ;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Step 4 writing XML to {}", outputFile.getAbsoluteFile()) ;
            }

            List<PrintDetail> printDetailList = (List<PrintDetail>)stepExecution.getJobExecution().getExecutionContext().get("PRINTDETAILSLIST");
            List<FilerDemographic> filerDemographicList = (List<FilerDemographic>)stepExecution.getJobExecution().getExecutionContext().get("FILERDEMOSTATUS");
            LOG.debug("List of Print Details --->  "+printDetailList);

            try{
            	ExportUtil.writeXml(jaxb2Marshaller, outputFile, rootElementDoc);
            	ExportUtil.addManifestFilename(stepExecution, filename) ;
            	printVendorJdbcRepository.insertPrintDetails(printDetailList);
            	printVendorJdbcRepository.updateFilerDemographicStatus(filerDemographicList);

                batchInfo.setFileName(currentFileName);
                batchInfo.setTotalCount(printDetailList.size());
                batchInfo.setTotalPass(printDetailList.size());
                batchInfo.setReceiveDt(new Date());
                batchInfo.setBatchType(PrintVendorConstants.PRINT_XML_BATCH_TYPE);
                batchInfo.setStateCd(state);
                batchInfo.setAgencyCd(ExportUtil.getAgencyCode(state));
                batchInfo.setSystemCd(ExportUtil.getSystemCode(state));
            	batchInfoService.updatePrintAndProcess(batchInfo);
            }catch(Exception excpetion){
            	LOG.error(excpetion.getMessage());
            	printVendorFileNameVerificationDecider.decide(stepExecution.getJobExecution(), stepExecution);

                batchInfo.setFileName(currentFileName);
                batchInfo.setTotalFail(printDetailList.size());
                batchInfo.setReceiveDt(new Date());
                batchInfo.setBatchType(PrintVendorConstants.PRINT_XML_BATCH_TYPE);
            	batchInfoService.updatePrintAndProcess(batchInfo);
            }
        }
    }

    /**
     * See section 5.3.3 Uniquely Identifying a Transmission.
     * @param transmitterControlCode 5 characters alphanumeric field that will contain the transmitters TCC and is mandatory â€“
     *                                enter the TCC that the IRS assigned when the transmitter applied to eFile. Note, TCCs do not include lower case characters.
     * @return   Example 550e8400-e29b-41d4-a716-446655440000:SYS12:12ABC::T
     */
    protected String generateUniqueTransmissionId(String transmitterControlCode) {
        StringBuilder b = new StringBuilder() ;
        b.append(UUID.randomUUID().toString()) ;
        b.append(":SYS12:") ;
        b.append(transmitterControlCode) ;
        b.append("::T") ;
        return b.toString() ;
    }

    /**
     * The manifest filename isn't a required standard, but it is recommended to be:
     * Manifest_Form Data File Name (without extension)>.xml
     * @param form109495bFilename
     * @return
     */
    protected String createManifestFilename(String form109495bFilename) {
        return "Manifest_" + form109495bFilename ;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

}
