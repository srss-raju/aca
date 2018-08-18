package us.deloitteinnovation.aca.batch.export;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.batch.BatchTestUtil;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dao.BatchInfoDao;
import us.deloitteinnovation.aca.batch.dao.FilerDemographicsDao;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095WriterTest;
import us.deloitteinnovation.aca.batch.export.step4.Step4109495Data;
import us.deloitteinnovation.aca.entity.BatchExportEntityConstants;
import us.deloitteinnovation.aca.entity.IrsTransmissionDetails;
import us.deloitteinnovation.aca.entity.SourceSystemConfig;
import us.deloitteinnovation.aca.entity.SourceSystemConfigPK;
import us.deloitteinnovation.aca.repository.*;
import us.deloitteinnovation.profile.ProfileProperties;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("QA")
@SpringApplicationConfiguration(classes = {ExportIntegTestConfiguration.class})
public class BatchExportIrsScenario8IntegTest {

    @Autowired
    JobBuilderFactory                       jobs;
    @Autowired
    JobLauncher                             jobLauncher;
    @Autowired
    JobRepository                           jobRepository;
    @Autowired
    StepBuilderFactory                      stepBuilderFactory;
    @Autowired
    ExportJob1095Repository                 exportJobRepository;
    @Autowired
    FlatFileItemReader<FilerDemographicDto> applicationReader;
    @Autowired
    BatchInfoDao                            batchInfoDao;
    @Autowired
    FilerDemographicsDao                    filerDemographicsDao;
    @Autowired
    FilerDemographicTestRepository          filerDemographicsTestRepository;
    @Autowired
    FilerCoverageSourceTestRepository       filerCoverageSourceTestRepository;
    @Autowired
    PrintDetailsTestRespository             printDetailsTestRespository;
    @Autowired
    SourceSystemConfigRepository            sourceSystemConfigRepository;
    @Autowired
    IrsRecordDetails1095BRepository         irsRecordDetails1095BRepository;
    @Autowired
    IrsTransmissionDetailsRepository        irsTransmissionDetailsRepository;
    @Autowired
    Step                                    step1OriginalConvertFilers;
    @Autowired
    Step                                    step1CorrectionConvertFilers ;
    @Autowired
    Step                                    step2ConvertPayers;
    @Autowired
    Step                                    step3CollateAndCreateManifests;
    @Autowired
    Step                                    step4WriteXml;
    @Autowired
    ExportBatchJobExecutionListener         exportBatchJobExecutionListener ;
    @Autowired
    ProfileProperties                       profileProperties;
    @Autowired
    BatchInfoDao                            batchinfoDao;

    private static final String SOURCE_CODE = "MD_SCEN8" ;

    SourceSystemConfig sourceSystemConfig ;

    @Before
    public void before() throws Exception {
        irsRecordDetails1095BRepository.deleteBySourceCode(SOURCE_CODE);
        printDetailsTestRespository.deleteBySourceCode(SOURCE_CODE);
        filerCoverageSourceTestRepository.deleteBySourceCode(SOURCE_CODE);
        filerDemographicsTestRepository.deleteBySourceCode(SOURCE_CODE);
        sourceSystemConfig = findOrCreate() ;
    }

    @Test
    @Ignore
    public void scenario_8_1_Test() throws Exception {
        scenario8TestHelper("MDSCEN8_03102016_01.dat", 1, "8-1") ;
    }

    @Test
    @Ignore
    public void scenario_8_2_Test() throws Exception {
        scenario8TestHelper("MDSCEN8_05032016_02.dat", 1, "8-2") ;
    }


    public JobExecution scenario8TestHelper(String filename, int filers, String testScenario) throws Exception {
        loadFilers(filename, filers, true) ;
        JobExecution jobExecution = executeJob(exportOriginalXmlForIrs(), testScenario) ;
        File outputDir = new File(profileProperties.getProperty("MD_XML_OUTPUT_FOLDER"));

        List<Step4109495Data> list = ExportUtil.getForm109495FilenamesForStep4(jobExecution);
        for (Step4109495Data data : list) {
            BatchTestUtil.validateIrsAcaXml(data.getFilename());
        }
        StepExecution stepExecution = ExportUtil.getStepExecutionByName(jobExecution, "step4WriteXml");
        List<String> manifests = ExportUtil.getManifestFilenames(stepExecution);
        for (String manifest : manifests) {
            BatchTestUtil.validateIrsAcaXml(new File(outputDir, manifest));
        }
        return jobExecution;
    }

    /**
     * Runs the exportXmlForIrsScenario6 job.  Used in both 6 and 6c scenarios.
     *
     * @return
     * @throws Exception
     */
    protected JobExecution executeJob(Job job, String scenario) throws Exception {
        Map<String, JobParameter> params = Maps.newHashMap();
        params.put("uniqueTimeStamp", new JobParameter(System.currentTimeMillis(), true));
        params.put(BatchExportConstants.JobPropertiesKeys.STATE, new JobParameter("MD", false));
        params.put(BatchExportConstants.JobPropertiesKeys.YEAR, new JobParameter(2015L, false));
        params.put(BatchExportConstants.JobPropertiesKeys.TEST_SCENARIO, new JobParameter(scenario, false));
        JobParameters jobParameters = new JobParameters(params);
        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(exportOriginalXmlForIrs());
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        return jobExecution;
    }

    /**
     * Inserts a single correction record
     * @throws Exception
     */
    @Test
    @Ignore
    public void scenario_8_2_C_Test() throws Exception {
        // First run scen 6 so users are inserted, processed, and data inserted into irs 1095b detail.  Does not output XML.
        JobExecution jobExecution = scenario8TestHelper("MDSCEN8_03102016_02.dat", 1, "8-2") ;
        // Clear the memory map because we just ran scenario 8 as an original
        exportJobRepository.clearAll();
        // Update Records
        List<FilerDemographicDto> filers = loadFilers("MDSCEN8_03102016_04.dat", 1, false) ;
        for (FilerDemographicDto filer : filers) {
            // For those filers, mark their irs 1095B details row as corrected
            irsRecordDetails1095BRepository.updateStatus(filer.getId().getSourceCd(), Long.parseLong(filer.getId().getSourceUniqueId()), BatchExportEntityConstants.FilerXmlStatus.CORRECTED);
        }

        // Mark the 1095B rows from scen 8 as accepted
        List<Step4109495Data> list = ExportUtil.getForm109495FilenamesForStep4(jobExecution);
        // There should be only 1 file
        assertEquals(1, list.size()) ;
        IrsTransmissionDetails details;
        String receiptId = "1095B-16-00015827" ;
        for (Step4109495Data data : list) {
            details = irsTransmissionDetailsRepository.findByTransmissionFileName(data.getFilename().getName());
            assertNotNull(details);
            irsTransmissionDetailsRepository.updateStatus(details.getTransmissionId(), receiptId, BatchExportEntityConstants.FilerXmlStatus.ACCEPTED, "scenario8cTest");
        }
        // Retrieve those corrected rows, so we can updated by source unique, code and filer demo seq (TODO ?)
        Map<String, JobParameter> params = Maps.newHashMap();
        params.put("uniqueTimeStamp", new JobParameter(System.currentTimeMillis(), true));
        params.put(BatchExportConstants.JobPropertiesKeys.RECEIPT_ID, new JobParameter(receiptId, false));
        params.put(BatchExportConstants.JobPropertiesKeys.TEST_SCENARIO, new JobParameter("8C-2", false));
        JobParameters jobParameters = new JobParameters(params);
        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(exportCorrectionXmlForIrs());
        jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    protected List<FilerDemographicDto> loadFilers(String filename, int anticipatedFilers, boolean insert)  throws Exception {
        BatchInfoDto batchInfo = BatchTestUtil.createBatchInfo(batchinfoDao, SOURCE_CODE, "BatchExportIrsScenario8IntegTest");
        List<FilerDemographicDto> filers = Step1Form1095WriterTest.importFilers("classpath:" + filename, applicationReader);
        for (FilerDemographicDto filer : filers) {
            filer.getId().setSourceCd(SOURCE_CODE);
            filer.setBatchInfo(batchInfo);
        }
        assertEquals(anticipatedFilers, filers.size());
        if (insert)
            filerDemographicsDao.bulkInsert(filers) ;
        else
            filerDemographicsDao.bulkUpdate(filers) ;
        return filers ;
    }

    public Job exportOriginalXmlForIrs() {
        return jobs.get(BatchExportConstants.JobNames.ORIGINALS).preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(exportBatchJobExecutionListener)
                .flow(step1OriginalConvertFilers)
                .next(step2ConvertPayers)
                .next(step3CollateAndCreateManifests)
                .next(step4WriteXml)
                .end()
                .build();
    }

    protected Job exportCorrectionXmlForIrs() {
        return jobs.get(BatchExportConstants.JobNames.CORRECTIONS).preventRestart()
                .listener(exportBatchJobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .flow(step1CorrectionConvertFilers)
                .next(step2ConvertPayers)
                .next(step3CollateAndCreateManifests)
                .next(step4WriteXml)
                .end()
                .build();
    }

    private SourceSystemConfig findOrCreate() {
        SourceSystemConfig config = sourceSystemConfigRepository.findOne(SOURCE_CODE);
        // If not found, let's create one
        if (config == null) {
            config = new SourceSystemConfig();
            config.setId(new SourceSystemConfigPK());
            config.getId().setSourceCd(SOURCE_CODE);
            config.setProviderIdentificationNumber("000000810");
            config.setStateAbbreviation("MD");
            config.setReturnAddressLine1("65 Willow Lane");
            config.setReturnAddressCity("Baltimore");
            config.setReturnAddressZip("21244");
            config.setReturnAddressState("MD");
            config.setPrintPreferences("N");
            config.setProviderName("Patttesteight Medicare");
            config.setProviderAddressLine1("65 Willow Lane");
            config.setProviderCityOrTown(config.getReturnAddressCity());
            config.setProviderZipOrPostalCode(config.getReturnAddressZip());
            config.setProviderContactFirstName("Elizabeth");
            config.setProviderContactLastName("Santanova");
            config.setProviderContactNo(5556332273L);
            config.setTestTcc("BB9RB");
            config.setDeloitteTestTcc("BB9RB");
            config.setSoftwareId("15A0001491");
            sourceSystemConfigRepository.save(config);
        }
        return config ;
    }

}

