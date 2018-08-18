package us.deloitteinnovation.aca.batch.export;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.deloitteinnovation.aca.batch.BatchTestUtil;
import us.deloitteinnovation.aca.batch.ExportTestConfiguration;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dao.FilerDemographicsDao;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.export.step4.Step4109495Data;
import us.deloitteinnovation.aca.entity.Irs109495XMLDetailsEntity;
import us.deloitteinnovation.aca.repository.FilerDemographicRepository;
import us.deloitteinnovation.aca.repository.Irs109495XMLDetailsRepository;
import us.deloitteinnovation.aca.repository.SourceSystemConfigRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Integration test to export all Arkansas filers.  Assumes that the users have already been loaded
 * into the database.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {ExportTestConfiguration.class})
public class BatchExportArkansasIntegTest {

    @Autowired
            JobBuilderFactory jobs;
    @Autowired
            JobLauncher jobLauncher;
    @Autowired
            JobRepository jobRepository;
    @Autowired
            Irs109495XMLDetailsRepository irs109495XMLDetailsRepository;
    @Autowired
            Job aca1095ExportOriginals;
    @Autowired
            Job aca1095ExportCorrections;
    @Autowired
            Job aca1095ExportReplacements;
    @Autowired
            Job acaGenerate1095Originals;
    @Autowired
            Job acaGenerate109495Originals;
    @Autowired
            Job acaGenerate1095Corrections;
    @Autowired
            Job acaGenerate109495Corrections;
    @Autowired
            Job acaGenerate1095Replacements;
    @Autowired
            Job acaGenerate109495Replacements;
    @Autowired
            Job acaGenerate1095OriginalsForPrintVendor;
    @Autowired
            Job acaGenerateManifests;



    Map<String, JobParameter> params;
    JobParameters jobParameters;
    JobLauncherTestUtils jobLauncherTestUtils;
    Long taxYear;

    @Before
    public void before() {

        params = Maps.newHashMap();
        taxYear = 2015L;
        params.put("uniqueTimeStamp", new JobParameter(System.currentTimeMillis(), true));
        params.put(BatchExportConstants.JobPropertiesKeys.STATE, new JobParameter("AR", false));
        params.put(BatchExportConstants.JobPropertiesKeys.YEAR, new JobParameter(taxYear, false));
        jobParameters = new JobParameters(params);
        jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);

    }

    /* Originals as single Job*/
    @Test
    @Ignore("We don't use this in production. Enable this test when we run this as a single job")
    public void exportAllArkansasOriginals() throws Exception {
        Job jobToExecute = aca1095ExportOriginals;
        executeJob(jobToExecute, true);
    }

   /* Corrections as single job*/
    @Test
    @Ignore("We don't use this in production. Enable this test when we run this as a single job")
    public void exportAllArkansasCorrections() throws Exception {
        Job jobToExecute = aca1095ExportCorrections;
        executeJob(jobToExecute, true);
    }

    /* Replacements as single job*/
    @Test
    @Ignore("We don't use this in production. Enable this test when we run this as a single job")
    public void exportAllArkansasReplacements() throws Exception {
        Job jobToExecute = aca1095ExportReplacements;
        executeJob(jobToExecute, true);
    }



    /* Originals */
    @Test
    public void job1OriginalsCreate1095XMLs() throws Exception {
        Job jobToExecute = acaGenerate1095Originals;
        executeJob(jobToExecute);
    }

    @Test
    public void job2OriginalsCollate1094And1095() throws Exception {
        Job jobToExecute = acaGenerate109495Originals;
        executeJob(jobToExecute,true);
    }



    /* Corrections */
    @Test
    public void job1CorrectionsCreate1095XMLs() throws Exception {
        Job jobToExecute = acaGenerate1095Corrections;
        executeJob(jobToExecute);
    }

    @Test
    public void job2CorrectionsCollate1094And1095() throws Exception {
        Job jobToExecute = acaGenerate109495Corrections;
        executeJob(jobToExecute, true);
    }


    /* Replacements */
    @Test
    public void job1ReplacementsCreate1095XMLs() throws Exception {
        Job jobToExecute = acaGenerate1095Replacements;
        executeJob(jobToExecute);
    }

    @Test
    public void job2ReplacementsCollate1094And1095() throws Exception {
        Job jobToExecute = acaGenerate109495Replacements;
        executeJob(jobToExecute, true);
    }

    /* Create Manifests */

    @Test
    public void job3CreateManifests() throws Exception {
        Job jobToExecute = acaGenerateManifests;
        executeJob(jobToExecute);
    }


    @Test
    public void exportPrintVendorJob() throws Exception {
        Job jobToExecute = acaGenerate1095OriginalsForPrintVendor;
        executeJob(jobToExecute);
    }

    @Test
    @Ignore("@Rajesh Bongurala: TODO: Not sure if this can be refactored similar to above tests. " +
            "@Rajesh Bongurala: TODO: Please execute this test case and enable it if running fine.")
    public void exportPrintVendorParamJob() throws Exception {
        Map<String, JobParameter> params = Maps.newHashMap();
        params.put("uniqueTimeStamp", new JobParameter(System.currentTimeMillis(), true));
        params.put(BatchExportConstants.JobPropertiesKeys.STATE, new JobParameter("AR", false));
        params.put(BatchExportConstants.JobPropertiesKeys.YEAR, new JobParameter(2016L, false));
        params.put(BatchExportConstants.StepExecutionContextKeys.PRINT_VENDOR_XML_FREQUENCY, new JobParameter("D", false));
        params.put(BatchExportConstants.StepExecutionContextKeys.PRINT_VENDOR_XML_MAILSTATUS, new JobParameter("Y", false));
        JobParameters jobParameters = new JobParameters(params);

        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(acaGenerate1095OriginalsForPrintVendor);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }



    private void executeJob(Job jobToExecute) throws Exception {
        executeJob(jobToExecute, false);
    }

    private void executeJob(Job jobToExecute, Boolean validateXML) throws Exception {
        jobLauncherTestUtils.setJob(jobToExecute);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        if(validateXML) {
            List<Irs109495XMLDetailsEntity> form109495bEntities = irs109495XMLDetailsRepository.findByManifestCreated(Boolean.FALSE);
            List<Step4109495Data> list = new ArrayList<>();

            for(Irs109495XMLDetailsEntity form109495Entity : form109495bEntities){
                Step4109495Data step4109495Data = new Step4109495Data();
                step4109495Data.setFilename(new File(form109495Entity.getXmlFilePath()));
                step4109495Data.setForm1094bCount(form109495Entity.getForm1094BCount());
                step4109495Data.setForm1095bCount(form109495Entity.getForm1095BCount());
                list.add(step4109495Data);
            }

            for (Step4109495Data data : list) {
                BatchTestUtil.validateIrsAcaXml(data.getFilename(), taxYear);
            }
        }
    }
}

