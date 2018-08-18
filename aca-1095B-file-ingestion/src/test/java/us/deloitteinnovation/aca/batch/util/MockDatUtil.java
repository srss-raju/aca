package us.deloitteinnovation.aca.batch.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import us.deloitteinnovation.profile.ProfileProperties;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Collection;

/**
 * Created by yaojia on 11/29/2016.
 */

@Component
public class MockDatUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MockDatUtil.class);

    private static final String CONFIG_STATE_INPUT_FOLDER_FORMAT = "%s_INPUT_FOLDER";
    private static final String CONFIG_STATE_OUTPUT_FOLDER_FORMAT = "%s_OUTPUT_FOLDER";
    private static final String CONFIG_STATE_XML_OUTPUT_FOLDER_FORMAT = "%s_XML_OUTPUT_FOLDER";

    @Autowired
    ProfileProperties profileProperties;

    public File writeToInputDatFile(Collection<String> records, StateCode state, String datFileName) throws Exception {
        /* Validate Inputs */
        Assert.notNull(records);
        Assert.notNull(state);

        /* Route state config */
        String stateParameter;
        switch(state) {
            case AR:
            case IN:
            case LA:
                stateParameter = state.name();
                break;
            default:
                throw new Exception("Unacceptable state code: " + state.name());
        }
        String inputFolderPath = profileProperties.getProperty(
                String.format(CONFIG_STATE_INPUT_FOLDER_FORMAT, stateParameter));

        /* Prepare folder */
        File inputFolder = new File(inputFolderPath);
        if (!inputFolder.exists()) {
            inputFolder.mkdirs();
        }

        /* Write .dat file */
        Files.deleteIfExists(new File(inputFolder.getAbsolutePath() + "/" + datFileName).toPath());
        PrintWriter out = new PrintWriter(inputFolder.getAbsolutePath() + "/" + datFileName);
        out.println(Integer.toString(records.size()));
        for (String s : records) {
            out.println(s);
        }
        out.close();


        /* Return created directory */
        LOGGER.debug("Mock .dat file created at " + inputFolder.getAbsolutePath() + "/" + datFileName);
        return inputFolder;

    }
}
