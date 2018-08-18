package us.deloitteinnovation.aca.batch.export.step4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.entity.PrintDetail;
import us.deloitteinnovation.aca.exception.PrintVendorFileNameVerificationDecider;
import us.deloitteinnovation.aca.jaxb.JaxbUtils;
import us.deloitteinnovation.aca.repository.PrintVendorJdbcRepository;
import us.deloitteinnovation.profile.ProfileProperties;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;
import us.gov.treasury.irs.msg.acauibusinessheader.TransmitterACAUIBusinessHeaderType;
import us.deloitteinnovation.aca.entity.FilerDemographic;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class Step4Form109495PrintVendorWriter implements ItemWriter<Step4ManifestData> {

    private static final Logger LOG = LoggerFactory.getLogger(Step4Form109495PrintVendorWriter.class) ;

    @Autowired
    @Qualifier("jaxb2Marshaller")
    protected Jaxb2Marshaller jaxb2Marshaller;

    @Autowired
    ExportJob1095Repository exportJobRepository ;
    
    @Autowired
    PrintVendorJdbcRepository printVendorJdbcRepository;
    
    @Autowired
    PrintVendorFileNameVerificationDecider printVendorFileNameVerificationDecider;

    us.gov.treasury.irs.msg.acauibusinessheader.ObjectFactory
        headerObjectFactory = new us.gov.treasury.irs.msg.acauibusinessheader.ObjectFactory() ;

    ObjectFactory
            air7ObjectFactory = new ObjectFactory();

    StepExecution stepExecution ;

    @Autowired
    ProfileProperties profileProperties;

    @Override
    public void write(List<? extends Step4ManifestData> list) throws Exception {
        String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext()) ;
        File outputDir = ExportUtil.getOutputDirectory(state, profileProperties) ;
 
        TransmitterACAUIBusinessHeaderType header ;
        JAXBElement<TransmitterACAUIBusinessHeaderType> rootElementDoc ;
        ACABulkBusinessHeaderRequestType headerRequestType ;

        for (Step4ManifestData manifestData : list) {
            ACATrnsmtManifestReqDtlType manifest = manifestData.acaTrnsmtManifestReqDtlType ;
            headerRequestType = air7ObjectFactory.createACABulkBusinessHeaderRequestType() ;
            headerRequestType.setTimestamp(JaxbUtils.createXmlGregorianCalendarNoTimezone(new Date()));
            headerRequestType.setUniqueTransmissionId(generateUniqueTransmissionId(manifestData.getTcc()));

            header = headerObjectFactory.createTransmitterACAUIBusinessHeaderType() ; 
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
            }catch(Exception excpetion){
            	LOG.error(excpetion.getMessage());
            	printVendorFileNameVerificationDecider.decide(stepExecution.getJobExecution(), stepExecution);
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
        this.stepExecution = stepExecution ;
    }
}
