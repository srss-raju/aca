package us.deloitteinnovation.aca.batch.validation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by yaojia on 1/18/2017.
 */
public class ValidateCorrectionOverridesCoverages extends AbstractFileIngestionTestCase {

    private static final String IN_FILENAME1 = "INFSSICE01032017_61_2016.dat";
    private static final String IN_FILENAME2 = "INFSSICE01032017_63_2016.dat";
    private static final String IN_FILENAME3 = "INFSSICE01032017_69_2016.dat";
    private static final FilerDemographicPK IN_PK_RECORD1 = new FilerDemographicPK(3899076L, "INFSSICE", 2016);
    private static final String IN_O_RECORD1 =
            "XYZ999|3899076|2016|O|Charlie||Douglas||675057122||05051985||tc@xyz.com|1 ABC Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|01012016|03312016|||||||||||abc|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|R||P|Y";
    private static final String IN_U_RECORD2 =
            "XYZ999|3899076|2016|U|Charlie||Douglas||675057122||05051985||tc@xyz.com|2 ABC Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|04262016|05312016|||||||||||bcd|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|R||P|Y";
    private static final String IN_C_RECORD3 =
            "XYZ999|3899076|2016|C|Charlie||Douglas||675057122||05051985||tc@xyz.com|3 ABC Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|09012016|12312016|||||||||||bcd|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|R||P|Y";
    private static final String IN_U_RECORD3 =
            "XYZ999|3899076|2016|U|Charlie||Douglas||675057122||05051985||tc@xyz.com|4 ABC Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|01012016|03312016|||||||||||bcd|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|R||P|Y";


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
        String inputFolderPath = profileProperties.getProperty("IN_INPUT_FOLDER");

        try {
        /* Ingest first O file and assert successful */
            mockDatUtil.writeToInputDatFile(Arrays.asList(IN_O_RECORD1), StateCode.IN, IN_FILENAME1);
            assertTrue(fileIngestionUtil.executeFileIngestion("IN", 2016));
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + IN_FILENAME1).toPath());

        /* Ingest second U file and assert successful */
            mockDatUtil.writeToInputDatFile(Arrays.asList(IN_U_RECORD2), StateCode.IN, IN_FILENAME2);
            assertTrue(fileIngestionUtil.executeFileIngestion("IN", 2016));
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + IN_FILENAME2).toPath());

        /* Ingest third C,U file and assert successful */
            mockDatUtil.writeToInputDatFile(Arrays.asList(IN_C_RECORD3, IN_U_RECORD3), StateCode.IN, IN_FILENAME3);
            assertTrue(fileIngestionUtil.executeFileIngestion("IN", 2016));
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + IN_FILENAME3).toPath());

        /* Fetch filer and check coverage */
            FilerDemographicCW filer = filerDemographicRepository.findOne(IN_PK_RECORD1);
            assertNotNull(filer);
            assertEquals("U", filer.getCorrection());

            assertEquals(new Character('1'), filer.getJan());
            assertEquals(new Character('1'), filer.getFeb());
            assertEquals(new Character('1'), filer.getMar());
            assertEquals(new Character('0'), filer.getApr());
            assertEquals(new Character('0'), filer.getMay());
            assertEquals(new Character('0'), filer.getJun());
            assertEquals(new Character('0'), filer.getJul());
            assertEquals(new Character('0'), filer.getAug());
            assertEquals(new Character('1'), filer.getSep());
            assertEquals(new Character('1'), filer.getOct());
            assertEquals(new Character('1'), filer.getNov());
            assertEquals(new Character('1'), filer.getDec());

        } finally {
            databaseCleanupUtil.cleanUpDatabase(Collections.singletonList(IN_PK_RECORD1));
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + IN_FILENAME1).toPath());
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + IN_FILENAME2).toPath());
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + IN_FILENAME3).toPath());
        }



    }
}
