package us.deloitteinnovation.aca.batch.receipt.reader;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.receipt.util.ProcessReceiptConstants;
import us.deloitteinnovation.profile.ProfileProperties;

public class ConfirmationFileReader implements ItemReader<File> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationFileReader.class);

    private StepExecution stepExecution;

    @Autowired
    ProfileProperties profileProperties;

    @Override
    public File read() throws Exception {

        final String fileType = (String) stepExecution.getJobExecution().getExecutionContext()
                .get(BatchExportConstants.StepExecutionContextKeys.PROCESS_RECEIPT_FILE_TYPE);

        LOGGER.info("Processing {}  files ", fileType);

        return readConfirmationFiles(fileType);
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    File readConfirmationFiles(String fileType) throws FileNotFoundException {

        final String inputDirLocation = profileProperties.getProperty(ProcessReceiptConstants.PRINTRECEIPTINPUTFILELOCATION);

        LOGGER.info("Reading files from  {}  location  ", inputDirLocation);

        final File fileDir = new File(inputDirLocation);

        if (!fileDir.exists()) {
            LOGGER.error("Unable to locate folder in the file system  :: {} ", inputDirLocation);
            throw new FileNotFoundException("Unable to locate folder in the file system :: " + inputDirLocation);
        }

        final File[] files = fileDir.listFiles(new NameFilter(fileType));

        LOGGER.info("Total {} files found  ", files.length);

        if (files.length > 0) {
            sort(files);
            LOGGER.info("Sending file to the processor {}", files[0].getName());
            return files[0];
        } else {
            LOGGER.info("There are no file(s) to send to processor");
            return null;
        }

    }

    /**
     * Sort records in the folder and take the old file first
     *
     * @param files
     */
    void sort(File[] files) {
        Arrays.sort(files, (o1, o2) -> Long.valueOf(o1.lastModified()).compareTo(o2.lastModified()));
    }

    static class NameFilter implements FileFilter {

        private final String fileType;

        public NameFilter(String fileType) {
            this.fileType = fileType;
        }

        @Override
        public boolean accept(File file) {
            // read CSV files only
            return file.getName().toLowerCase().startsWith(fileType.toLowerCase()) && file.getName().toLowerCase().endsWith(".csv");
        }
    }

}
