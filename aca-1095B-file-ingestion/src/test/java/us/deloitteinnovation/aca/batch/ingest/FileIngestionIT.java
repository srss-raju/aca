package us.deloitteinnovation.aca.batch.ingest;

import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.io.Resource;
import us.deloitteinnovation.aca.batch.AbstractFileIngestionTestCase;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.file.filter.FileExtensionFilter;
import us.deloitteinnovation.aca.batch.ingest.step2.FileNameVerificationDecider;
import us.deloitteinnovation.aca.batch.listener.ImportFilersJobExecutionListener;
import us.deloitteinnovation.aca.batch.service.BatchInfoService;
import us.deloitteinnovation.aca.batch.service.ExceptionReportService;
import us.deloitteinnovation.aca.repository.FilerDemographicStagingRepository;
import us.deloitteinnovation.profile.ProfileProperties;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by bhchaganti on 9/6/2016.
 */
public class FileIngestionIT extends AbstractFileIngestionTestCase {

    @Autowired
    public ImportFilersJobExecutionListener importFilersJobExecutionListener;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    JobBuilderFactory jobs;
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    ProfileProperties profileProperties;
    @Autowired
    FileNameVerificationDecider fileNameVerificationDecider;
    @Autowired
    Step step1RefreshStaging;
    @Autowired
    Step step2VerifyFilerData;
    @Autowired
    Step step3ValidateFilerData;
    @Autowired
    Step step4UpdateBatchCounts;

    @Autowired
    ExceptionReportService exceptionReportService;
    @Autowired
    BatchInfoService batchInfoService;
    @Autowired
    FilerDemographicStagingRepository filerDemographicStagingRepository;

    String state;
    String year;
    String stateInputFolder;
    String fileToTest;

    @Before
    public void before() throws Exception {

    }

    @Test
    public void testValidFileIngestion_AR_2016() throws Exception {
        state = "AR";
        year = "2016";
        fileToTest = "ARDHSDSS12092016_07_2016.dat";
        testValidFileIngestion();
    }

    @Test
    public void testValidFileIngestion_AR_2015() throws Exception {
        state = "AR";
        year = "2015";
        fileToTest = "ARDHSDSS12092016_07_2015.dat";
        testValidFileIngestion();
    }

    @Test
    public void testValidFileIngestion_IN_2016() throws Exception {
        state = "IN";
        year = "2016";
        fileToTest = "INFSSICE12092016_07_2016.dat";
        testValidFileIngestion();
    }

    @Test
    public void testValidFileIngestion_IN_2015() throws Exception {
        state = "IN";
        year = "2015";
        fileToTest = "INFSSICE12092016_07_2015.dat";
        testValidFileIngestion();
    }

    @Test
    public void testValidFileIngestion_LA_2016() throws Exception {
        state = "LA";
        year = "2016";
        fileToTest = "LADHHEES12092016_07_2016.dat";
        testValidFileIngestion();
    }

    @Test
    public void testValidFileIngestion_LA_2015() throws Exception {
        state = "LA";
        year = "2015";
        fileToTest = "LADHHEES12092016_07_2015.dat";
        testValidFileIngestion();
    }

    @Test
    public void testFileIngestionWithNullYear() throws Exception {
        state = "AR";
        year = null;
        fileToTest = "ARDHSDSS01012016_100_2016.dat";
        copyFile(false);
        JobExecution jobExecution = executeJob(state, year);
        assertNotEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    @Test
    public void testFileIngestionWithNullState() throws Exception {
        state = null;
        year = "2016";
        fileToTest = "ARDHSDSS01012016_100_2016.dat";
        JobExecution jobExecution = executeJob(state, year);
        assertNotEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

    private void testValidFileIngestion() throws Exception {
        copyFile(true);
        JobExecution jobExecution = executeJob(state, year);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        // Batch Info table should have the file name for that batch_id

        int batchId = batchInfoService.getTopBatchId();
        BatchInfoDto batchInfoDto = new BatchInfoDto();
        batchInfoDto.setBatchId(batchId);
        String fileNameInBatchInfo = batchInfoService.getFileNameForBatchId(batchId);
        assertThat("File name captured in batch info is different than used", fileNameInBatchInfo, is(fileToTest));

        /*
        Number of distinct source unique id's in exception report and staging table should be equal to
        the number of source unique id's in the file.
        */

        String fileLocation = "classpath:" + "\\" + "valid" + "\\" + fileToTest;
        StaticApplicationContext context = new StaticApplicationContext();
        Resource resource = context.getResource(fileLocation);
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(resource.getFile()));
        lineNumberReader.skip(Long.MAX_VALUE);
        int numberOfRecordsInFile = lineNumberReader.getLineNumber();
        List<Integer> listOfRecordsInExceptionReport = exceptionReportService.getDistinctSourceUniqueIdForBatchId(batchId);
        List<Integer> listOfRecordsInStaging = filerDemographicStagingRepository.getRowNumbersFromStagingForBatchId(batchId);
        Set<Integer> setOfUniqueRows = new HashSet<Integer>();
        populateSet(setOfUniqueRows, listOfRecordsInExceptionReport, listOfRecordsInStaging);
        assertThat("Not all records in the file were processed", numberOfRecordsInFile, is(setOfUniqueRows.size()));
    }

    /*
     * Method to copy the file to be processed from the valid/invalid
     * folder to the state specific input folder
     */
    private void copyFile(boolean valid) throws Exception {
        String validIndicator = (valid ? "valid" : "invalid");
        //1. Form the source location path
        String inputResourceLocation = "classpath:" + "\\" + validIndicator + "\\" + fileToTest;
        Resource resource = applicationContext.getResource(inputResourceLocation);
        assertNotNull(resource);
        assertTrue(resource.exists());
        //2. Form the destination location path
        String stateInputFolderKey = state + BatchConstants.INPUT_FILE_FOLDER;
        stateInputFolder = profileProperties.getProperty(stateInputFolderKey);
        assertNotNull(stateInputFolder);
        assertTrue(new File(stateInputFolder).exists());
        //3. Copy existing files to an archive directory, since there should be only 1 file in IN folder
        File archiveDirectory = new File(stateInputFolder+File.separator+"archive");
        File[] filesToBeMoved = new File(stateInputFolder).listFiles(new FileExtensionFilter(BatchConstants.FILE_FORMAT_EXTENSION));
        for(File file : filesToBeMoved){
            FileUtils.moveToDirectory(file, archiveDirectory, true);
        }
        //4. Copy source file to destination
        FileUtils.copyFileToDirectory(resource.getFile(), new File(stateInputFolder));
        assertTrue(new File(stateInputFolder + File.separator + fileToTest).exists());
    }

    private JobExecution executeJob(String state, String year) throws Exception {
        Map<String, JobParameter> params = Maps.newHashMap();
        params.put("uniqueTimeStamp", new JobParameter(System.currentTimeMillis(), true));
        params.put(BatchExportConstants.JobPropertiesKeys.STATE, new JobParameter(state, false));
        params.put(BatchExportConstants.JobPropertiesKeys.YEAR, new JobParameter(year, false));
        JobParameters jobParameters = new JobParameters(params);

        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(ingestFile());
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        return jobExecution;
    }

    /*
     * Populates the set of unique records which from the combined list
     * of records from staging and
     * */
    private void populateSet(Set<Integer> setOfUniqueRecords, List<Integer> listOfRecordsInExceptionReport, List<Integer> listOfRecordsInStaging) {

        for (Integer i : listOfRecordsInExceptionReport) {
            setOfUniqueRecords.add(i);
        }
        for (Integer bi : listOfRecordsInStaging) {
            setOfUniqueRecords.add(bi);
        }
    }

    protected Job ingestFile() {

        Flow refreshStagingJobFlow = new FlowBuilder<Flow>("refreshStagingJobFlow")
                .start(step1RefreshStaging)
                .end();

        Flow verifyAndValidateJobFlow = new FlowBuilder<Flow>("verifyAndValidateJobFlow")
                .start(fileNameVerificationDecider)
                .on("PASSED")
                .to(step2VerifyFilerData)
                .next(step3ValidateFilerData)
                .next(step4UpdateBatchCounts)
                .from(fileNameVerificationDecider)
                .on("FAILED")
                .end()
                .build();

        return jobs.get("importFilers").preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(refreshStagingJobFlow)
                .on("COMPLETED")
                .to(verifyAndValidateJobFlow)
                .end()
                .listener(importFilersJobExecutionListener)
                .build();
    }

    @After
    public void after() throws Exception {
        cleanInputFolder();
    }

    /*
     * Cleans the state specific input folder once the
     * test is finished running
     * */
    private void cleanInputFolder() throws Exception {
        File file;
        try {
            file = new File(stateInputFolder + File.separator + fileToTest);

            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println(file.getName() + " could not be deleted!");
            }
            assertFalse(file.exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
