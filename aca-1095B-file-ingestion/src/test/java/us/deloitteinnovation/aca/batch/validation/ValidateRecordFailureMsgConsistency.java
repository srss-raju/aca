package us.deloitteinnovation.aca.batch.validation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.AbstractFileIngestionTestCase;
import us.deloitteinnovation.aca.batch.util.DatabaseCleanupUtil;
import us.deloitteinnovation.aca.batch.util.FileIngestionUtil;
import us.deloitteinnovation.aca.batch.util.MockDatUtil;
import us.deloitteinnovation.aca.batch.util.StateCode;
import us.deloitteinnovation.aca.entity.ExceptionReport;
import us.deloitteinnovation.aca.entity.FilerDemographicCW;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;
import us.deloitteinnovation.aca.repository.CWFilerDemographicRepository;
import us.deloitteinnovation.aca.repository.ExceptionReportRepository;
import us.deloitteinnovation.profile.ProfileProperties;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/** <p>
 *     Test case to validate that exception message ER3.1.2.1 for correction and update records should be identical.
 * </p>
 *
 * @author yaojia
 */
public class ValidateRecordFailureMsgConsistency extends AbstractFileIngestionTestCase {

    private static final FilerDemographicPK PK = new FilerDemographicPK(60016271671L, "ARDHSDSS", 2015);
    private static final String CASE1_FILENAME = "ARDHSDSS12022016_01_2015.dat";
    private static final String CASE1_C_1 = "T60016001671|60016271671|2015|C|David||Lee||616161251||11281980|||456 Satellite Blvd|B|Alam Rock|AR|46101||C|MEDICAID|04012015|05302015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final String CASE1_U_1 = "T60016001671|60016271671|2015|U|David||Lee||616161251||11281980|||456 Satellite Blvd|C|Alam Rock|AR|46101||C|MEDICAID|06012015|08012015|||||||||||Arkansas State|88-8018011|7701234534|400 Marietta St||Alam Rock|AR|USA|46123|R||E|Y";
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    @Autowired
    MockDatUtil mockDatUtil;
    @Autowired
    ProfileProperties profileProperties;
    @Autowired
    CWFilerDemographicRepository filerDemographicRepository;
    @Autowired
    ExceptionReportRepository exceptionReportRepository;
    @Autowired
    DatabaseCleanupUtil databaseCleanupUtil;
    @Autowired
    FileIngestionUtil fileIngestionUtil;

    @Test
    public void test() throws Exception {
        /* Write file into AR input directory and ingest it */
        mockDatUtil.writeToInputDatFile(Arrays.asList(CASE1_C_1, CASE1_U_1), StateCode.AR, CASE1_FILENAME);

        /* Assert ingestion succeeded */
        try {
            assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2015));

        /* Assert no record inserted into FD */
            FilerDemographicCW filer = filerDemographicRepository.findOne(PK);
            assertNull(filer);

        /* Assert exception message found and identical for both records */
            List<ExceptionReport> exceptionForCorrection = exceptionReportRepository.findBySourceUniqueIdAndRowNumber(PK.getSourceUniqueId(), ONE);
            assertNotNull(exceptionForCorrection);
            assertTrue(!exceptionForCorrection.isEmpty());

            List<ExceptionReport> exceptionForUpdate = exceptionReportRepository.findBySourceUniqueIdAndRowNumber(PK.getSourceUniqueId(), TWO);
            assertNotNull(exceptionForUpdate);
            assertTrue(!exceptionForUpdate.isEmpty());

            assertEquals(exceptionForCorrection.get(ZERO).getExDetails(), exceptionForUpdate.get(ZERO).getExDetails());

        /* Clean-up */
            exceptionReportRepository.delete(exceptionForCorrection.get(ZERO).getExceptionReportId());
            exceptionReportRepository.delete(exceptionForUpdate.get(ZERO).getExceptionReportId());

        } finally {
            String inputFolderPath = profileProperties.getProperty("AR_INPUT_FOLDER");
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + CASE1_FILENAME).toPath());
        }
    }
}
