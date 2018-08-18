package us.deloitteinnovation.aca.batch.export.step4;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import us.deloitteinnovation.aca.batch.BatchTestUtil;
import us.deloitteinnovation.aca.batch.export.ExportBatchConfiguration;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.ExportJob1095RepositoryInMemory;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.batch.export.step3.Form10945Type;
import us.deloitteinnovation.aca.jaxb.JaxbUtils;
import us.deloitteinnovation.profile.ProfileProperties;
import us.gov.treasury.irs.common.BinaryFormatCodeType;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static us.deloitteinnovation.aca.batch.constants.BatchExportConstants.JobPropertiesKeys.STATE;
import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.genericSetLong;

/**
 * @see Step4Form109495Writer
 */
@Ignore("Ignored since Jenkins system doesn't allow to write to external folders")
public class Step4Form109495WriterTest {

    private static final Logger LOG = LoggerFactory.getLogger(Step4Form109495WriterTest.class) ;

    Step4Form109495Writer step4Form10945bWriter ;

    Jaxb2Marshaller jaxb2Marshaller ;

    ExportJob1095Repository exportJobRepository ;

    @Before
    public void before() throws Exception {
        ProfileProperties props = mock(ProfileProperties.class) ;
        Path path = Files.createTempDirectory("acaStep4") ;
        when(props.getProperty(eq("batch.export.xml.test"))).thenReturn("true") ;
        when(props.getProperty(endsWith("_XML_OUTPUT_FOLDER"))).thenReturn(path.toFile().getAbsolutePath()) ;
        step4Form10945bWriter = new Step4Form109495Writer();
        step4Form10945bWriter.profileProperties = props ;
        step4Form10945bWriter.stepExecution = MetaDataInstanceFactory.createStepExecution() ;
        ExportBatchConfiguration config = new ExportBatchConfiguration() ;
        jaxb2Marshaller = step4Form10945bWriter.jaxb2Marshaller = config.jaxb2Marshaller() ;
        step4Form10945bWriter.exportJobRepository = exportJobRepository = new ExportJob1095RepositoryInMemory() ;
    }

    @Test
    public void writeMinimal() throws Exception {

        step4Form10945bWriter.stepExecution.getJobExecution().getExecutionContext().put(STATE, "GA") ;
        ACATrnsmtManifestReqDtlType manifest ;
        manifest = new ACATrnsmtManifestReqDtlType() ;
        manifest.setPaymentYr(JaxbUtils.createXmlGregorianCalendarNoTimezone(2015));
        manifest.setPriorYearDataInd("0");
        manifest.setTestFileCd("T");
        manifest.setTransmissionTypeCd(TransmissionTypeCdType.O) ;
        BusinessNameType transmitter = new BusinessNameType() ;
        transmitter.setBusinessNameLine1Txt("TheState");
        manifest.setTransmitterNameGrp(transmitter);
        CompanyInformationGrpType contactCompany = new CompanyInformationGrpType() ;
        contactCompany.setCompanyNm("Company Name") ;
        OtherCompletePersonNameType person = new OtherCompletePersonNameType() ;
        person.setPersonFirstNm("Joe");
        person.setPersonLastNm("Whoever");
        contactCompany.setContactNameGrp(person);
        contactCompany.setContactPhoneNum("4045551212");
        BusinessAddressGrpType address = new BusinessAddressGrpType() ;
        USAddressGrpType usAddress = new USAddressGrpType() ;
        usAddress.setAddressLine1Txt("Address Line 1");
        usAddress.setCityNm("Atlanta");
        usAddress.setUSStateCd(StateType.GA);
        usAddress.setUSZIPCd("30312");
        address.setUSAddressGrp(usAddress);
        contactCompany.setMailingAddressGrp(address);
        manifest.setCompanyInformationGrp(contactCompany) ;
        manifest.setEIN("123456789");

        VendorInformationGrpType vendor = new VendorInformationGrpType() ;
        vendor.setContactNameGrp(person);
        vendor.setContactPhoneNum("4045551212");
        vendor.setVendorCd("V");
        manifest.setVendorInformationGrp(vendor);

        genericSetLong(manifest, "setTotalPayeeRecordCnt", 1);
        genericSetLong(manifest, "setTotalPayerRecordCnt", 1);
        manifest.setSoftwareId("ABC123");

        manifest.setFormTypeCd(Form10945Type.FORM_10945_B.getValue());
        manifest.setBinaryFormatCd(BinaryFormatCodeType.APPLICATION_XML);
        manifest.setAttachmentByteSizeNum(BigInteger.valueOf(123456789L));
        manifest.setDocumentSystemFileNm("1094B_Request_BBBBB_20160308T133743000Z.xml") ;
        manifest.setChecksumAugmentationNum("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        Step4ManifestData manifestData = new Step4ManifestData() ;
        manifestData.acaTrnsmtManifestReqDtlType = manifest ;
        manifestData.tcc = "BB9RB" ;
        step4Form10945bWriter.write(Collections.singletonList(manifestData)) ;
        // Parse the XML back into objects, assert on data.
        File outputDir = ExportUtil.getOutputDirectory("GA",step4Form10945bWriter.profileProperties) ;
        List<String> list = ExportUtil.getManifestFilenames(step4Form10945bWriter.stepExecution) ;
        for (String filename : list ) {
            BatchTestUtil.validateIrsAcaXml(new File(outputDir, filename)) ;
        }
    }

}