package us.deloitteinnovation.aca.batch.validation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import us.deloitteinnovation.aca.batch.AbstractFileIngestionTestCase;
import us.deloitteinnovation.aca.batch.util.DatabaseCleanupUtil;
import us.deloitteinnovation.aca.batch.util.FileIngestionUtil;
import us.deloitteinnovation.aca.batch.util.MockDatUtil;
import us.deloitteinnovation.aca.batch.util.StateCode;
import us.deloitteinnovation.aca.entity.FilerDemographicCW;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;
import us.deloitteinnovation.aca.repository.CWFilerDemographicRepository;
import us.deloitteinnovation.aca.repository.FilerCoverageSourceRepository;
import us.deloitteinnovation.profile.ProfileProperties;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by yaojia on 12/8/2016.
 */
public class ValidateCorrectionIndicatorNotNull extends AbstractFileIngestionTestCase {

    private static final String AR_FILENAME = "ARDHSDSS99992016_98_2016.dat";
    private static final FilerDemographicPK AR_PK_RECORD1 = new FilerDemographicPK(7299076L, "ARDHSDSS", 2016);
    private static final FilerDemographicPK AR_PK_RECORD2 = new FilerDemographicPK(7299077L, "ARDHSDSS", 2016);
    private static final String AR_O_RECORD1 =
            "1730934328|7299076|2016|O|Jacob|N|Jones||520960063|520960063|03071984||Jacob@test.com|98f The Oaks|Green Close|Little Rock|AR|72201|3434|C|CHIP|01012016|06302016|||||||||||Medicaid|123456789|2123546698|3rd Floor|Long Road|Fort Worth|AR|USA|72202|R|7299076|P|Y";
    private static final String AR_O_RECORD2 =
            "1730934329|7299077|2016|O|Andrea|KL|Jacobs||008341421|008341421|03081984|||98f The Oaks|Green Close|Little Rock|AR|72201|3434|C|CHIP|01012016|06302016|||||||||||Medicaid|123456789|2123546698|3rd Floor|Long Road|Fort Worth|AR|USA|72202|C|7299076|P|Y";
    private static final int EXPECTED_CORRECTION_INDICATOR = 0;

    @Autowired
    MockDatUtil mockDatUtil;
    @Autowired
    ProfileProperties profileProperties;
    @Autowired
    CWFilerDemographicRepository filerDemographicRepository;
    @Autowired
    FilerCoverageSourceRepository filerCoverageSourceRepository;
    @Autowired
    DatabaseCleanupUtil databaseCleanupUtil;
    @Autowired
    FileIngestionUtil fileIngestionUtil;

    @Test
    public void test() throws Exception {
        /* Write the files into a temp folder for reading */
        mockDatUtil.writeToInputDatFile(Arrays.asList(AR_O_RECORD1, AR_O_RECORD2), StateCode.AR, AR_FILENAME);

        try {
            /* Load O file. Assert exit without error */
            assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2016));

            /* Validate filer records inserted */
            FilerDemographicCW filer1 = filerDemographicRepository.findOne(AR_PK_RECORD1);
            FilerDemographicCW filer2 = filerDemographicRepository.findOne(AR_PK_RECORD2);
            Assert.notNull(filer1);
            Assert.notNull(filer2);

            /* Validate correction indicator */
            /* if correction_indicator is null, both validate objects should be null */
            FilerDemographicCW validate1 = filerDemographicRepository.findByIdAndCorrectionIndicatorIsNull(AR_PK_RECORD1);
            FilerDemographicCW validate2 = filerDemographicRepository.findByIdAndCorrectionIndicatorIsNull(AR_PK_RECORD2);
            assertNull(validate1);
            assertNull(validate2);

            /* Assert correction_indicator is zero */
            assertEquals(EXPECTED_CORRECTION_INDICATOR, (long) filer1.getCorrectionIndicator());
            assertEquals(EXPECTED_CORRECTION_INDICATOR, (long) filer1.getCorrectionIndicator());
        }

        finally {
            /* Clean-up created records */
            databaseCleanupUtil.cleanUpDatabase(Arrays.asList(AR_PK_RECORD1, AR_PK_RECORD2));
            String inputFolderPath = profileProperties.getProperty("AR_INPUT_FOLDER");
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + AR_FILENAME).toPath());
        }

    }
}
