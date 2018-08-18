package us.deloitteinnovation.aca.batch.export.step4;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.batch.export.TransmissionIdStack;
import us.deloitteinnovation.aca.jaxb.JaxbUtils;
import us.deloitteinnovation.aca.repository.IrsTransmissionDetailsRepository;
import us.deloitteinnovation.profile.ProfileProperties;
import us.gov.treasury.irs.common.BinaryFormatCodeType;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;

import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.genericSetLong;

/**
 * Sanitize each data element, removing special characters as per IRS requirements.
 */
public class Step4Form109495Processor implements ItemProcessor<Step4Form109495HeaderAndXmlDto, Step4ManifestData> {

    @Autowired
    ProfileProperties profileProperties;
    @Autowired
    TransmissionIdStack transmissionIdStack;
    @Autowired
    IrsTransmissionDetailsRepository irsTransmissionDetailsRepository;
    StepExecution stepExecution ;
    private ObjectFactory air7ObjectFactory = new ObjectFactory();

    @Override
    public Step4ManifestData process(Step4Form109495HeaderAndXmlDto dto) throws Exception {
        validateFile(dto.formDataFile);
        dto.formDataSize = dto.formDataFile.length();
        MessageDigest md = MessageDigest.getInstance("MD5");
        dto.formDataMd5 = (new HexBinaryAdapter()).marshal(md.digest(FileUtils.readFileToByteArray(dto.formDataFile)));

        String originalReceiptId = ExportUtil.getReceiptId(stepExecution.getJobExecution().getExecutionContext()) ;
        Step4ManifestData manifestData = new Step4ManifestData();
        manifestData.acaTrnsmtManifestReqDtlType = createManifestHeader(dto, originalReceiptId, dto.total1094Forms, dto.total1095Forms);

        // TODO Switch to correct TCC in non-test mode
        if (ExportUtil.isTestXml(profileProperties))
            manifestData.tcc = dto.getSourceSystem().getTestTcc();
        else
            manifestData.tcc = dto.getSourceSystem().getTransmitterControlCode();
        return manifestData;
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
    protected ACATrnsmtManifestReqDtlType createManifestHeader(Step4Form109495HeaderAndXmlDto dto, String originalReceiptId, int total1094Forms, int total1095Forms) {
        ACATrnsmtManifestReqDtlType manifest = air7ObjectFactory.createACATrnsmtManifestReqDtlType();
        manifest.setPaymentYr(JaxbUtils.createXmlGregorianCalendarNoTimezone(dto.paymentYear));

        // Only set the original receipt if the transmission is a REPLACEMENT
        if ("R".equals(dto.getTransmissionType().value())) {

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

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution ;
    }
}
