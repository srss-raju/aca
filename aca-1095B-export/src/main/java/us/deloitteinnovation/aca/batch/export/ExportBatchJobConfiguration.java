package us.deloitteinnovation.aca.batch.export;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;

/**
 * Defines the Job to be run for a full Batch XML Export.  It is explicitly defined here, outside of ExportBatchJobConfiguration,
 * so that different Jobs can be defined an not automatically run.
 */
@Configuration
public class ExportBatchJobConfiguration {

    public static final String BATCH_EXECUTION_CONTINUE = "CONTINUE";
    public static final String BATCH_EXECUTION_FINISHED = "COMPLETED";
    @Autowired
    JobBuilderFactory jobs;
    @Autowired
    ExportBatchJobExecutionListener exportBatchJobExecutionListener;
    @Autowired
    Step step1OriginalConvertFilers;
    @Autowired
    Step step1CorrectionConvertFilers;
    @Autowired
    Step step1ReplacementsConvertFilers;
    @Autowired
    Step step2ConvertPayers;
    @Autowired
    Step step3CollateAndCreateManifests;
    @Autowired
    Step step4WriteXml;
    @Autowired
    Step step5CleanupBatchMetaData;
    @Autowired
    JobExecutionDecider xmlRenderingDecider;
    @Autowired
    ExportBatchJobExecutionPrintVendorListener exportBatchJobExecutionPrintVendorListener;
    @Autowired
    Step step1OriginalFilersForPrintVendor;
    @Autowired
    Step step2PrintVendorConvertPayers;
    @Autowired
    Step step3PrintVendorCollateAndCreateManifests;
    @Autowired
    Step step4PrintVendorWriteXml;
    @Autowired
    ExportBatchPrintVendorListener exportBatchJobExecutionPrintVendorParamListener;
    
    @Autowired
    ExportPdfListener exportPdfListener;
    @Autowired
    Step stepExportPdf;

    /** Payload status lifecycle for Original Filers as a single job */
    @Bean
    public Job aca1095ExportOriginals() {
        return jobs.get(BatchExportConstants.JobNames.ORIGINALS).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step1OriginalConvertFilers)
                .next(step2ConvertPayers)
                .next(xmlRenderingDecider)
                .on(String.valueOf(FlowExecutionStatus.COMPLETED))
                .to(step3CollateAndCreateManifests)
                .next(step4WriteXml)
                .next(step5CleanupBatchMetaData)
                .end()
                .build();
    }

    /**
     * Job 1 in Payload Status Lifecycle for Original Filers
     *
     * @return
     */
    @Bean
    public Job acaGenerate1095Originals() {
        return jobs.get(BatchExportConstants.JobNames.ORIGINAL1095S).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step1OriginalConvertFilers)
                .end()
                .build();
    }

    /**
     * Job 2 in Payload Status Lifecycle for Original Filers.
     *
     * @return
     */

    @Bean
    public Job acaGenerate109495Originals() {
        return jobs.get(BatchExportConstants.JobNames.ORIGINAL109495S).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step2ConvertPayers)
                .next(xmlRenderingDecider)
                .on(String.valueOf(FlowExecutionStatus.COMPLETED))
                .to(step3CollateAndCreateManifests)
                .end()
                .build();
    }


    /**
     * Payload Status Lifecycle for Corrected Filers as a single job
     *
     * @return
     */
    @Bean
    public Job aca1095ExportCorrections() {
        return jobs.get(BatchExportConstants.JobNames.CORRECTIONS).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step1CorrectionConvertFilers)
                .next(step2ConvertPayers)
                .next(xmlRenderingDecider)
                .on(String.valueOf(FlowExecutionStatus.COMPLETED))
                .to(step3CollateAndCreateManifests)
                .next(step4WriteXml)
                .next(step5CleanupBatchMetaData)
                .end()
                .build();
    }

    /**
     * Job 1 in Payload Status Lifecycle for Corrected Filers
     *
     * @return
     */
    @Bean
    public Job acaGenerate1095Corrections() {
        return jobs.get(BatchExportConstants.JobNames.CORRECTION1095S).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step1CorrectionConvertFilers)
                .end()
                .build();
    }

    /**
     * Job 2 in Payload Status Lifecycle for Corrected Filers
     *
     * @return
     */
    @Bean
    public Job acaGenerate109495Corrections() {
        return jobs.get(BatchExportConstants.JobNames.CORRECTION109495S).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step2ConvertPayers)
                .next(xmlRenderingDecider)
                .on(String.valueOf(FlowExecutionStatus.COMPLETED))
                .to(step3CollateAndCreateManifests)
                .end()
                .build();
    }


    /**
     * Payload Status Lifecycle for Replaced Filers as a single job
     *
     * @return
     */
    @Bean
    public Job aca1095ExportReplacements() {
        return jobs.get(BatchExportConstants.JobNames.REPLACEMENTS).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step1ReplacementsConvertFilers)
                .next(step2ConvertPayers)
                .next(xmlRenderingDecider)
                .on(String.valueOf(FlowExecutionStatus.COMPLETED))
                .to(step3CollateAndCreateManifests)
                .next(step4WriteXml)
                .on(BATCH_EXECUTION_CONTINUE)
                .to(step2ConvertPayers).from(step4WriteXml)
                .on(BATCH_EXECUTION_FINISHED)
                .to(step5CleanupBatchMetaData)
                .end()
                .build();
    }

    /**
     * Job 1 in Payload Status Lifecycle for Replaced Filers
     *
     * @return
     */
    @Bean
    public Job acaGenerate1095Replacements() {
        return jobs.get(BatchExportConstants.JobNames.REPLACEMENT1095S).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step1ReplacementsConvertFilers)
                .end()
                .build();
    }

    /**
     * Job 2 in Payload Status Lifecycle for Replaced Filers
     *
     * @return
     */
    @Bean
    public Job acaGenerate109495Replacements() {

        return jobs.get(BatchExportConstants.JobNames.REPLACEMENT109495S).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step2ConvertPayers)
                .next(xmlRenderingDecider)
                .on(String.valueOf(FlowExecutionStatus.COMPLETED))
                .to(step3CollateAndCreateManifests)
                .on(BATCH_EXECUTION_CONTINUE)
                .to(step2ConvertPayers)
                .from(step3CollateAndCreateManifests)
                .on(BATCH_EXECUTION_FINISHED)
                .to(step5CleanupBatchMetaData)
                .end()
                .build();
    }

    /**
     * Job 3 to generate Manifests
     */

    @Bean
    public Job acaGenerateManifests() {
        return jobs.get(BatchExportConstants.JobNames.GENERATEMANIFESTS).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step4WriteXml)
                .next(step5CleanupBatchMetaData)
                .end()
                .build();
    }
    
    @Bean
    public Job acaGenerate1095OriginalsForPrintVendor() {
        return jobs.get(BatchExportConstants.JobNames.ORIGINAL1095FILERS).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionPrintVendorListener)
                .flow(step1OriginalFilersForPrintVendor)
                .next(step2PrintVendorConvertPayers)
                .next(step3PrintVendorCollateAndCreateManifests)
                .next(step4PrintVendorWriteXml)
                .next(step5CleanupBatchMetaData)
                .end()
                .build();
    }
    @Bean
    public Job acaGenerate1095OriginalsForPrintVendorParam() {
        return jobs.get(BatchExportConstants.JobNames.ORIGINAL1095PRINTVENDORPARAMFILERS).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionPrintVendorParamListener)
                .flow(step2PrintVendorConvertPayers)
                .next(step1OriginalFilersForPrintVendor)
                .next(step5CleanupBatchMetaData)
                .end()
                .build();
    } 
    
    @Bean
    public Job exportPdf() {
        return jobs.get("exportPdf").preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportPdfListener)
                .flow(stepExportPdf)
                .next(step5CleanupBatchMetaData)
                .end()
                .build();
    }

    @Bean
    public Job exportPdfs() {
        return jobs.get("exportPdfs").preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportPdfListener)
                .flow(stepExportPdf)
                .next(step5CleanupBatchMetaData)
                .end()
                .build();
    }

}