package us.deloitteinnovation.aca.batch.validation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.AbstractFileIngestionTestCase;
import us.deloitteinnovation.aca.batch.util.DatabaseCleanupUtil;
import us.deloitteinnovation.aca.batch.util.FileIngestionUtil;
import us.deloitteinnovation.aca.batch.util.MockDatUtil;
import us.deloitteinnovation.aca.batch.util.StateCode;
import us.deloitteinnovation.aca.entity.FilerCoverage;
import us.deloitteinnovation.aca.entity.FilerDemographicCW;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;
import us.deloitteinnovation.aca.repository.CWFilerDemographicRepository;
import us.deloitteinnovation.aca.repository.FilerCoverageSourceRepository;
import us.deloitteinnovation.profile.ProfileProperties;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yaojia on 12/3/2016.
 */
public class ValidateOriginalCoverageSourceCreated extends AbstractFileIngestionTestCase {

    private static final FilerDemographicPK PK = new FilerDemographicPK(60016001671L, "ARDHSDSS", 2015);
    private static final String CASE_FILENAME_1 = "ARDHSDSS12022015_02_2015.dat";
    private static final String CASE_O_1 = "T60016001671|60016001671|2015|O|David||Lee||616161241||10281980|||456 Satellite Blvd|A|Alam Rock|AR|46101||C|MEDICAID-A|01012015|03012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE_U_1 = "T60016001671|60016001671|2015|U|David||Lee||616161241||10281980|||456 Satellite Blvd|B|Alam Rock|AR|46101||C|MEDICAID-B|04012015|05012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE_U_2 = "T60016001671|60016001671|2015|U|David||Lee||616161241||10281980|||456 Satellite Blvd|C|Alam Rock|AR|46101||C|MEDICAID-C|06012015|07012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE_U_3 = "T60016001671|60016001671|2015|U|David||Lee||616161241||10281980|||456 Satellite Blvd|D|Alam Rock|AR|46101||C|MEDICAID-D|08012015|09012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";

    private static final String CASE_FILENAME_2 = "ARDHSDSS12022015_03_2015.dat";
    private static final String CASE_C_1 = "T60016001671|60016001671|2015|C|David||Lee||616161241||10281980|||456 Satellite Blvd|E|Alam Rock|AR|46101||C|MEDICAID-E|10012015|10302015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE_U_4 = "T60016001671|60016001671|2015|U|David||Lee||616161241||10281980|||456 Satellite Blvd|F|Alam Rock|AR|46101||C|MEDICAID-F|11012015|12012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final int BATCH1_SIZE = 4;
    private static final int BATCH2_SIZE = 2;


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
        String inputFolderPath = profileProperties.getProperty("AR_INPUT_FOLDER");

        /* Ingest 1 Original record followed by 3 additional U records */
        mockDatUtil.writeToInputDatFile(Arrays.asList(CASE_O_1, CASE_U_1, CASE_U_2, CASE_U_3), StateCode.AR, CASE_FILENAME_1);

        /* Assert file ingested */
        assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2015));
        try {
            FilerDemographicCW filer = filerDemographicRepository.findOne(PK);
            assertNotNull(filer);
            assertEquals("D", filer.getRecepientAddressLine2());

        /* Assert coverage is correct */
            assertEquals(new Character('1'), filer.getJan());
            assertEquals(new Character('1'), filer.getFeb());
            assertEquals(new Character('1'), filer.getMar());
            assertEquals(new Character('1'), filer.getApr());
            assertEquals(new Character('1'), filer.getMay());
            assertEquals(new Character('1'), filer.getJun());
            assertEquals(new Character('1'), filer.getJul());
            assertEquals(new Character('1'), filer.getAug());
            assertEquals(new Character('1'), filer.getSep());
            assertEquals(new Character('0'), filer.getOct());

            List<FilerCoverage> coverages = filerCoverageSourceRepository
                    .findById_SourceUniqueIdAndId_SourceCdAndTaxYear(PK.getSourceUniqueId(), PK.getSourceCd(), PK.getTaxYear());
            assertEquals(BATCH1_SIZE, coverages.size());

        /* Delete first file to prevent double writing */
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + CASE_FILENAME_1).toPath());

        /* Ingest 1 Correction record followed by 1 additional U records */
            mockDatUtil.writeToInputDatFile(Arrays.asList(CASE_C_1, CASE_U_4), StateCode.AR, CASE_FILENAME_2);

        /* Assert file ingested */
            assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2015));
            filer = filerDemographicRepository.findOne(PK);
            assertNotNull(filer);
            assertEquals("F", filer.getRecepientAddressLine2());

        /* Assert coverage is correct */
            assertEquals(new Character('0'), filer.getJan());
            assertEquals(new Character('0'), filer.getMar());
            assertEquals(new Character('0'), filer.getMay());
            assertEquals(new Character('0'), filer.getJun());
            assertEquals(new Character('0'), filer.getSep());
            assertEquals(new Character('1'), filer.getOct());
            assertEquals(new Character('1'), filer.getNov());
            assertEquals(new Character('1'), filer.getDec());

            coverages = filerCoverageSourceRepository
                    .findById_SourceUniqueIdAndId_SourceCdAndTaxYear(PK.getSourceUniqueId(), PK.getSourceCd(), PK.getTaxYear());
            assertEquals(BATCH2_SIZE, coverages.size());

        } finally {
            databaseCleanupUtil.cleanUpDatabase(Collections.singletonList(PK));
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + CASE_FILENAME_1).toPath());
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + CASE_FILENAME_2).toPath());
        }
    }
}
