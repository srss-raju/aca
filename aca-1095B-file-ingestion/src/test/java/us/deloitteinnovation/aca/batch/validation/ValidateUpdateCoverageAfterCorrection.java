package us.deloitteinnovation.aca.batch.validation;

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

import static org.junit.Assert.*;

/**
 * Created by yaojia on 12/2/2016.
 */
public class ValidateUpdateCoverageAfterCorrection extends AbstractFileIngestionTestCase {
    private static final String CASE1_O_1 = "T60016001671|60016001671|2015|O|David||Lee||616161241||10281980|||456 Satellite Blvd|A|Alam Rock|AR|46101||C|MEDICAID|01012015|03012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE1_C_1 = "T60016001671|60016001671|2015|C|David||Lee||616161251||11281980|||456 Satellite Blvd|B|Alam Rock|AR|46101||C|MEDICAID|04012015|05302015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE1_U_1 = "T60016001671|60016001671|2015|U|David||Lee||616161251||11281980|||456 Satellite Blvd|C|Alam Rock|AR|46101||C|MEDICAID|06012015|08012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE1_U_2 = "T60016001671|60016001671|2015|U|David||Lee||616161241||10281980|||456 Satellite Blvd|D|Alam Rock|AR|46101||C|MEDICAID|09012015|10012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE1_FILENAME = "ARDHSDSS12022016_01_2015.dat";

    private static final String CASE2_O_1 = "T60016001671|60016001671|2015|O|David||Lee||616161241||10281980|||456 Satellite Blvd|E|Alam Rock|AR|46101||C|MEDICAID|01012015|03012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE2_U_1 = "T60016001671|60016001671|2015|U|David||Lee||616161241||10281980|||456 Satellite Blvd|F|Alam Rock|AR|46101||C|MEDICAID|04012015|05012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE2_U_2 = "T60016001671|60016001671|2015|U|David||Lee||616161241||10281980|||456 Satellite Blvd|G|Alam Rock|AR|46101||C|MEDICAID|06012015|07012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE2_U_3 = "T60016001671|60016001671|2015|U|David||Lee||616161241||10281980|||456 Satellite Blvd|H|Alam Rock|AR|46101||C|MEDICAID|08012015|09012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE2_FILENAME_1 = "ARDHSDSS12022016_02_2015.dat";

    private static final String CASE2_C_1 = "T60016001671|60016001671|2015|C|David||Lee||616161251||11281980|||456 Satellite Blvd|I|Alam Rock|AR|46101||C|MEDICAID|01012015|03012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE2_U_4 = "T60016001671|60016001671|2015|U|David||Lee||616161251||11281980|||456 Satellite Blvd|J|Alam Rock|AR|46101||C|MEDICAID|04012015|05012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE2_U_5 = "T60016001671|60016001671|2015|U|David||Lee||616161251||11281980|||456 Satellite Blvd|K|Alam Rock|AR|46101||C|MEDICAID|06012015|07012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE2_U_6 = "T60016001671|60016001671|2015|U|David||Lee||616161251||11281980|||456 Satellite Blvd|L|Alam Rock|AR|46101||C|MEDICAID|08012015|09012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE2_FILENAME_2 = "ARDHSDSS12022016_06_2015.dat";

    private static final FilerDemographicPK PK = new FilerDemographicPK(60016001671L, "ARDHSDSS", 2015);
    private static final int OLD_SSN = 616161241;
    private static final int NEW_SSN = 616161251;

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


    @org.junit.Test
    public void testCase1() throws Exception {
        /* Write case 1 file and ingest it */
        mockDatUtil.writeToInputDatFile(Arrays.asList(CASE1_O_1, CASE1_C_1, CASE1_U_1, CASE1_U_2), StateCode.AR, CASE1_FILENAME);
        assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2015));

        try {
        /* Assert subject SSN updated */
            FilerDemographicCW filer = filerDemographicRepository.findOne(PK);
            assertNotNull(filer);
            assertEquals(NEW_SSN, Integer.parseInt(filer.getRecepientSsn()));

        /* Assert Address Line2 updated to D (i.e. last U record accepted) */
            assertEquals("C", filer.getRecepientAddressLine2());

        /* Assert coverage data merged */
        /* Expected covered months: Apr, May, Jun and Jul */
            assertEquals(new Character('0'), filer.getJan());
            assertEquals(new Character('0'), filer.getMar());
            assertEquals(new Character('1'), filer.getApr());
            assertEquals(new Character('1'), filer.getJul());
            assertEquals(new Character('1'), filer.getAug());
            assertEquals(new Character('0'), filer.getOct());

        } finally {
            /* Clean up record in DB */
            databaseCleanupUtil.cleanUpDatabase(Collections.singletonList(PK));
            String inputFolderPath = profileProperties.getProperty("AR_INPUT_FOLDER");
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + CASE1_FILENAME).toPath());
        }


    }

    @org.junit.Test
    public void testCase2() throws Exception {
        /* Write case 2 file 1 and ingest it */
        mockDatUtil.writeToInputDatFile(Arrays.asList(CASE2_O_1, CASE2_U_1, CASE2_U_2, CASE2_U_3), StateCode.AR, CASE2_FILENAME_1);
        assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2015));

        try {
        /* Assert SSN not updated */
            FilerDemographicCW filer = filerDemographicRepository.findOne(PK);
            assertNotNull(filer);
            assertEquals(OLD_SSN, Integer.parseInt(filer.getRecepientSsn()));

        /* Assert Address Line2 updated to H (i.e. last U record accepted) */
            assertEquals("H", filer.getRecepientAddressLine2());

        /* Assert coverage data merged */

        /* Remove previous .dat file to prevent double-writing */
            String inputFolderPath = profileProperties.getProperty("AR_INPUT_FOLDER");
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + CASE2_FILENAME_1).toPath());

        /* Write case 2 file 2 and ingest it */
            mockDatUtil.writeToInputDatFile(Arrays.asList(CASE2_C_1, CASE2_U_4, CASE2_U_5, CASE2_U_6), StateCode.AR, CASE2_FILENAME_2);
            assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2015));

        /* Assert SSN updated */
            filer = filerDemographicRepository.findOne(PK);
            assertEquals(NEW_SSN, Integer.parseInt(filer.getRecepientSsn()));

        /* Assert Address Line 2 updated to L (i.e. last U record accepted) */
            assertEquals("L", filer.getRecepientAddressLine2());

        /* Assert coverage data merged */

        } finally {
            /* Clean up record in DB */
            databaseCleanupUtil.cleanUpDatabase(Collections.singletonList(PK));
            String inputFolderPath = profileProperties.getProperty("AR_INPUT_FOLDER");
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + CASE2_FILENAME_1).toPath());
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + CASE2_FILENAME_2).toPath());
        }
    }
}
