package us.deloitteinnovation.aca.batch.validation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.AbstractFileIngestionTestCase;
import us.deloitteinnovation.aca.batch.util.DatabaseCleanupUtil;
import us.deloitteinnovation.aca.batch.util.FileIngestionUtil;
import us.deloitteinnovation.aca.batch.util.MockDatUtil;
import us.deloitteinnovation.aca.batch.util.StateCode;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;
import us.deloitteinnovation.aca.repository.CWFilerDemographicRepository;
import us.deloitteinnovation.aca.repository.FilerCoverageSourceRepository;
import us.deloitteinnovation.profile.ProfileProperties;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by yaojia on 12/6/2016.
 */
public class ValidateAddressLine1WithForwardSlash extends AbstractFileIngestionTestCase {

    private static final FilerDemographicPK PK1 = new FilerDemographicPK(60016001691L, "ARDHSDSS", 2015);
    private static final FilerDemographicPK PK2 = new FilerDemographicPK(70037656563L, "ARDHSDSS", 2015);
    private static final FilerDemographicPK PK3 = new FilerDemographicPK(30083032096L, "ARDHSDSS", 2015);
    private static final String CASE_FILENAME_1 = "ARDHSDSS12022015_02_2015.dat";
    private static final String CASE_FILENAME_2 = "ARDHSDSS12022015_78_2015.dat";
    private static final String CASE_VALID1 = "T60016001691|60016001691|2015|O|David||Lee||616161241||10281980|||13 1/2 Street|A|Alam Rock|AR|46101||C|MEDICAID-A|01012015|03012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE_VALID2 = "T60016001691|60016001691|2015|U|David||Lee||616161241||10281980|||Euston Rd|Platform 9 3/4, Kings Cross|London|AA|46101||C|MEDICAID-A|01012015|03012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE_INVALID1 = "T60016001691|70037656563|2015|O|Dbvid||Lee||048569487|||||13 1//2 Street|A|Alam Rock|AR|46101||C|MEDICAID-A|01012015|03012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE_INVALID2 = "T60016001691|30083032096|2015|O|Dcvid||Lee||213708975|||||Euston Rd|Platform 9 3//4, Kings Cross|London|AA|46101||C|MEDICAID-A|01012015|03012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";



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
    public void positiveTest() throws Exception {
        String inputFolderPath = profileProperties.getProperty("AR_INPUT_FOLDER");

        /* Ingest 1 Original record followed by 3 additional U records */
        mockDatUtil.writeToInputDatFile(Arrays.asList(CASE_VALID1, CASE_VALID2), StateCode.AR, CASE_FILENAME_1);

        /* Assert file ingested */
        try {
            assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2015));
            assertTrue(filerDemographicRepository.exists(PK1));

        } finally {
        /* Clean up*/
            databaseCleanupUtil.cleanUpDatabase(Collections.singletonList(PK1));
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + CASE_FILENAME_1).toPath());
        }
    }

    @Test
    public void negativeTest() throws Exception {
        String inputFolderPath = profileProperties.getProperty("AR_INPUT_FOLDER");

        /* Ingest 1 Original record followed by 3 additional U records */
        mockDatUtil.writeToInputDatFile(Arrays.asList(CASE_INVALID1, CASE_INVALID2), StateCode.AR, CASE_FILENAME_2);

        /* Assert not file ingested */
        try {
            assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2015));
            assertFalse(filerDemographicRepository.exists(PK2));
            assertFalse(filerDemographicRepository.exists(PK3));

        } finally {
            /* Clean up*/
            databaseCleanupUtil.cleanUpDatabase(Arrays.asList(PK2, PK3));
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + CASE_FILENAME_2).toPath());
        }

    }
}
