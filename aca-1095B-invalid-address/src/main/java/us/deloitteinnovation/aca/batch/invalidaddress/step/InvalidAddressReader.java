package us.deloitteinnovation.aca.batch.invalidaddress.step;

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
import us.deloitteinnovation.aca.batch.invalidaddress.util.InvalidAddressConstants;
import us.deloitteinnovation.profile.ProfileProperties;

public class InvalidAddressReader  implements ItemReader<File> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidAddressReader.class);

    private StepExecution stepExecution;

    @Autowired
    ProfileProperties profileProperties;

    @Override
    public File read() throws Exception {
    	String inputDirLocation = null;
    	String state = (String) stepExecution.getJobExecution().getExecutionContext().get(BatchExportConstants.JobPropertiesKeys.STATE);
    	if("AR".equalsIgnoreCase(state)){
    		inputDirLocation = profileProperties.getProperty(InvalidAddressConstants.AR_INVALID_ADDRESSINPUTFILELOCATION) ;
    	}else if("IN".equalsIgnoreCase(state)){
    		inputDirLocation = profileProperties.getProperty(InvalidAddressConstants.IN_INVALID_ADDRESSINPUTFILELOCATION) ;
    	}else if("LA".equalsIgnoreCase(state)){
    		inputDirLocation = profileProperties.getProperty(InvalidAddressConstants.LA_INVALID_ADDRESSINPUTFILELOCATION) ;
    	}
    	
    	if (inputDirLocation == null) {
            throw new IllegalArgumentException("Invalid Location  --- >> "+inputDirLocation) ;
        }
    	LOGGER.info("Reading files from  {}  location  ", inputDirLocation);

        final File fileDir = new File(inputDirLocation);

        if (!fileDir.exists()) {
            LOGGER.error("Unable to locate folder in the file system  :: {} ", inputDirLocation);
            throw new FileNotFoundException("Unable to locate folder in the file system :: " + inputDirLocation);
        }

        final File[] files = fileDir.listFiles(new NameFilter());

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

        @Override
        public boolean accept(File file) {
            // read CSV files only
            return file.getName().toLowerCase().endsWith(".dat");
        }
    }
    
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
    
}
