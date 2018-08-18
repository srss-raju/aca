package us.deloitteinnovation.aca.batch.export.step2;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import us.deloitteinnovation.aca.batch.export.ExportUtil;
import us.deloitteinnovation.aca.jaxb.ACA1094BDetailBuilder;
import us.gov.treasury.irs.common.TINRequestTypeCodeType;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

/**
 * Converts Filer related information (such as payee and employer) into FORM 1094B AIR7 objects for export to the IRS as XML SOAP message(s).
 */
public class Step2Form1094Processor implements ItemProcessor<Step2Form1094Dto, Step2Form1094Dto> {

    StepExecution stepExecution ;

    /**
     * @param provider
     * @return  Same DTO object provided as parameter, with the Form1094BUpstreamDetailType created and set.
     */
    @Override
    public Step2Form1094Dto process(Step2Form1094Dto provider) throws Exception {
        Integer year = ExportUtil.getYear(stepExecution.getJobExecution().getExecutionContext()) ;
        if (year == null || year == 0)
            throw new IllegalArgumentException("A tax year must be provided to Step2Form1094Processor.  Please ensure" +
                    " it is correctly defined within the JobParameters.") ;

        final Form1094BUpstreamDetailType form1094=new ACA1094BDetailBuilder()
                .setTaxYr(year)
                .setEmployerEIN(provider.getSourceSystemConfig().getProviderIdentificationNumber())
                .build();

        BusinessNameType businessNameType = new BusinessNameType() ;
        businessNameType.setBusinessNameLine1Txt(ExportUtil.sanitize(provider.getSourceSystemConfig().getProviderName()));
        form1094.setBusinessName(businessNameType);
        form1094.setTINRequestTypeCd(TINRequestTypeCodeType.BUSINESS_TIN);
        form1094.setContactNameGrp(ExportUtil.getContactName(provider.getSourceSystemConfig()));
        form1094.setContactPhoneNum("" + provider.getSourceSystemConfig().getProviderContactNo());
        form1094.setMailingAddressGrp(ExportUtil.getBusinessAddressFromSourceSystemConfig(provider.getSourceSystemConfig()));
        form1094.setTestScenarioId(ExportUtil.getTestScenario(stepExecution.getJobExecution().getExecutionContext()));
        provider.form1094BUpstreamDetailType = form1094 ;
        return provider ;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution ;
    }
}
