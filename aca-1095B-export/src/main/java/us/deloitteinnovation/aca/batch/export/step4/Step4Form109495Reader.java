package us.deloitteinnovation.aca.batch.export.step4;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.batch.export.ExportJob1095Repository;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.entity.Irs109495XMLDetailsEntity;
import us.deloitteinnovation.aca.repository.Irs109495XMLDetailsRepository;

import us.deloitteinnovation.aca.repository.IrsTransmissionDetailsRepository;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static us.deloitteinnovation.aca.batch.export.ExportUtil.*;

/**
 * TODO:
 * I'm undecided as to whether I want a step 4 at all.  The XML output COULD take place in Step 3.  However,
 * using a Step 4 allows for a clear restart point for XML export in a "replay" scenario.  The problem I have right now is
 * how do I "persist/write" the split list of 1095s that should be divided into multiple files between step 3 and 4.  Should
 * they be stored by source unique id?  For now, I might use the ExecutionContext as a memory bridge, and come up with a long term
 * solution later.
 *<p>Load manifest/header data.</p>
 */
public class Step4Form109495Reader implements ItemReader<Step4Form109495HeaderAndXmlDto>{
    private static final Logger LOG = LoggerFactory.getLogger(Step4Form109495Reader.class) ;

    @Autowired
    ExportJob1095Repository exportJobRepository ;

    @Autowired
    Irs109495XMLDetailsRepository irs109495XMLDetailsRepository;

    @Autowired
    SourceSystemConfigDataService sourceSystemConfigDataService ;

    @Autowired
    IrsTransmissionDetailsRepository irsTransmissionDetailsRepository;

    /** Stores the current count of 109495B file names processing. */
    StepExecution stepExecution ;

    @Override
    public Step4Form109495HeaderAndXmlDto read() throws Exception {
        /* Get the list of Step4109495Data from DB here */
        List<Irs109495XMLDetailsEntity> form109495bEntities = irs109495XMLDetailsRepository.findByManifestCreated(Boolean.FALSE);
        List<Step4109495Data> form109495bFiles = new ArrayList<>();

        for(Irs109495XMLDetailsEntity form109495Entity : form109495bEntities){
            Step4109495Data step4109495Data = new Step4109495Data();
            step4109495Data.setFilename(new File(form109495Entity.getXmlFilePath()));
            step4109495Data.setForm1094bCount(form109495Entity.getForm1094BCount());
            step4109495Data.setForm1095bCount(form109495Entity.getForm1095BCount());
            step4109495Data.setTransmissionTypeCd(form109495Entity.getTransmissionTypeCd());
            step4109495Data.setTransmissionId(form109495Entity.getTransmissionId());
            form109495bFiles.add(step4109495Data);
        }

        if (CollectionUtils.isEmpty(form109495bFiles)) {
            LOG.info("Step 4 Reader found no files stored within the JobExecutionContext.") ;
            return null ;
        }

        String state = ExportUtil.getState(stepExecution.getJobExecution().getExecutionContext()) ;
        Integer year = ExportUtil.getYear(stepExecution.getJobExecution().getExecutionContext()) ;
        int filenamesCounter = getForm109495FilenamesCounterForStep4(stepExecution) ;

        // If we have read all files in the list, we are done
        if (form109495bFiles.size() == 0) {
            return null ;
        }

        Step4Form109495HeaderAndXmlDto dto = new Step4Form109495HeaderAndXmlDto() ;
        dto.sourceSystem = sourceSystemConfigDataService.getByState(state, year);

        Step4109495Data fileData = form109495bFiles.get(0) ;
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
        dto.transmissionType = TransmissionTypeCdType.valueOf(fileData.getTransmissionTypeCd());
        dto.isPriorYearFiling = ExportUtil.getPriorYearFilingFlag(year);
        dto.paymentYear = year ;
        // TODO Get from DB or constant
        dto.softwareDeveloper = getVendorInfo() ;
        dto.softwareId = dto.sourceSystem.getSoftwareId();

//        Store the original receipt id in the execution context for replacements
        if("R".equals(dto.transmissionType.toString())) {
            String originalReceiptId = irsTransmissionDetailsRepository.getOriginalReceiptIdForTransmissionId(fileData.getTransmissionId());
            stepExecution.getJobExecution().getExecutionContext().put(BatchExportConstants.JobPropertiesKeys.RECEIPT_ID, originalReceiptId);
        }
        incrementForm109495FilenamesCounterForStep4(stepExecution) ;
        return dto ;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution ;
    }
}


