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
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by yaojia on 11/29/2016.
 */
public class ValidateIrsCodeUpdated extends AbstractFileIngestionTestCase {
    private static final int STREET_NO_LIMIT = 9999;
    private static final String AR_FILENAME = "ARDHSDSS99992016_99_2016.dat";
    private static final FilerDemographicPK AR_PK_RECORD1 = new FilerDemographicPK(3399076L, "ARDHSDSS", 2016);
    private static final FilerDemographicPK AR_PK_RECORD2 = new FilerDemographicPK(3399077L, "ARDHSDSS", 2016);
    private static final String AR_O_RECORD1 =
            "XYZ999|3399076|2016|O|Charlie||Douglas||677057122||05051985||tc@xyz.com|1 ABC Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|01012016|12312016|||||||||||abc|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|R||P|Y";
    private static final String AR_O_RECORD2 =
            "XYZ999|3399077|2016|O|Delta||Douglas||723451723||05051986||tc@xyz.com|1 ABC Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|01012016|12312016|||||||||||bcd|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|C|3399076|P|Y";
    private static final String AR_C_RECORD1_TMPL =
            "XYZ999|3399076|2016|C|Charlie||Douglas||677057122||05051985||tc@xyz.com|%d BCDC Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|01012016|12312016|||||||||||abc|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|R||P|Y";
    private static final String AR_C_RECORD2_TMPL =
            "XYZ999|3399077|2016|U|Delta||Douglas||723451723||05051986||tc@xyz.com|%d BCD Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|01012016|12312016|||||||||||bcd|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|C|3399076|P|Y";

    private static final String LA_FILENAME = "LADHHEES10992016_99_2016.dat";
    private static final FilerDemographicPK LA_PK_RECORD1 = new FilerDemographicPK(3399086L, "LADHHEES", 2016);
    private static final FilerDemographicPK LA_PK_RECORD2 = new FilerDemographicPK(3399087L, "LADHHEES", 2016);
    private static final String LA_O_RECORD1 =
            "XYZ999|3399086|2016|O|Charlie||Engstorm||403333368||05051985||tc@xyz.com|1 ABC Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|01012016|12312016|||||||||||abc|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|R||P|Y";
    private static final String LA_O_RECORD2 =
            "XYZ999|3399087|2016|O|Delta||Engstorm||643119849||05051986||tc@xyz.com|1 ABC Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|01012016|12312016|||||||||||bcd|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|C|3399086|P|Y";
    private static final String LA_C_RECORD1_TMPL =
            "XYZ999|3399086|2016|U|Charlie||Engstorm||403333368||05051985||tc@xyz.com|%d BCDC Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|01012016|12312016|||||||||||abc|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|R||P|Y";
    private static final String LA_C_RECORD2_TMPL =
            "XYZ999|3399087|2016|C|Delta||Engstorm||643119849||05051986||tc@xyz.com|%d BCD Street|Parkwood Pl|Denver|CO|30808|3080|C|MEDICAIDMEDICAID|01012016|12312016|||||||||||bcd|99-9999999|1234567890|1 XXX Street|Parkwood Pl|Fort Worth|CO|USA|46001|C|3399086|P|Y";


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
    public void testAr() throws Exception {
        /* Write the files into a temp folder for reading */
        mockDatUtil.writeToInputDatFile(Arrays.asList(AR_O_RECORD1, AR_O_RECORD2), StateCode.AR, AR_FILENAME);

        /* Load O file. Assert exit without error */
        assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2016));

        try {
        /* Assert IRS_TRANSMISSION_CD = NULL at this point time */
            FilerDemographicCW record1 = filerDemographicRepository.findOne(AR_PK_RECORD1);
            FilerDemographicCW record2 = filerDemographicRepository.findOne(AR_PK_RECORD2);
            Assert.isNull(record1.getIrsTransmissionStatusCd());
            Assert.isNull(record2.getIrsTransmissionStatusCd());

        /* Update one record IRS_TRANSMISSION_CD to DT */
            record1.setIrsTransmissionStatusCd("DT");
            filerDemographicRepository.save(record1);

        /* Load C Files while IRS_TRANSMISSION_CD is NULL.  */
            Random random = new Random();
            String correctionRecord1 = String.format(AR_C_RECORD1_TMPL, random.nextInt(STREET_NO_LIMIT));
            String correctionRecord2 = String.format(AR_C_RECORD2_TMPL, random.nextInt(STREET_NO_LIMIT));
            mockDatUtil.writeToInputDatFile(Arrays.asList(correctionRecord1, correctionRecord2), StateCode.AR, AR_FILENAME);
            assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2016));

        /* Assert IRS_TRANSMISSION_CD is still NULL */
            record1 = filerDemographicRepository.findOne(AR_PK_RECORD1);
            record2 = filerDemographicRepository.findOne(AR_PK_RECORD2);
            assertEquals("DT", record1.getIrsTransmissionStatusCd());
            Assert.isNull(record2.getIrsTransmissionStatusCd());

        /* Update IRS_TRANSMISSION_CD to not NULL. */
            record1.setIrsTransmissionStatusCd("PR");
            record2.setIrsTransmissionStatusCd("PR");
            filerDemographicRepository.save(Arrays.asList(record1, record2));

        /* Load C file while IRS_TRANSMISSION_CD is not NULL or DT*/
            correctionRecord1 = String.format(AR_C_RECORD1_TMPL, random.nextInt(STREET_NO_LIMIT));
            correctionRecord2 = String.format(AR_C_RECORD2_TMPL, random.nextInt(STREET_NO_LIMIT));
            mockDatUtil.writeToInputDatFile(Arrays.asList(correctionRecord1, correctionRecord2), StateCode.AR, AR_FILENAME);
            assertTrue(fileIngestionUtil.executeFileIngestion("AR", 2016));

        /* Assert the responsible's IRS_TRANSMISSION_CD is CO and the covered's is NULL */
            record1 = filerDemographicRepository.findOne(AR_PK_RECORD1);
            record2 = filerDemographicRepository.findOne(AR_PK_RECORD2);
            assertEquals("CO", record1.getIrsTransmissionStatusCd());
            assertEquals("PR", record2.getIrsTransmissionStatusCd());

        } finally {

        /* Clean-up created records */
            databaseCleanupUtil.cleanUpDatabase(Arrays.asList(AR_PK_RECORD1, AR_PK_RECORD2));
            String inputFolderPath = profileProperties.getProperty("AR_INPUT_FOLDER");
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + AR_FILENAME).toPath());
        }
    }

    @Test
    public void testLa() throws Exception {
        /* Prepare 2 sets of files for AR and LA, each containing 3 .dat files:  */
        /* an O, a U and a C, each containing record for one person */
        /* Write the files into a temp folder for reading */
        mockDatUtil.writeToInputDatFile(Arrays.asList(LA_O_RECORD1, LA_O_RECORD2), StateCode.LA, LA_FILENAME);

        /* Load O file. Assert exit without error */
        assertTrue(fileIngestionUtil.executeFileIngestion("LA", 2016));

        try {
        /* Assert IRS_TRANSMISSION_CD = NULL at this point time */
            FilerDemographicCW record1 = filerDemographicRepository.findOne(LA_PK_RECORD1);
            FilerDemographicCW record2 = filerDemographicRepository.findOne(LA_PK_RECORD2);
            Assert.isNull(record1.getIrsTransmissionStatusCd());
            Assert.isNull(record2.getIrsTransmissionStatusCd());

        /* Update one record IRS_TRANSMISSION_CD to DT */
            record2.setIrsTransmissionStatusCd("PR");
            filerDemographicRepository.save(record2);

        /* Load C Files while IRS_TRANSMISSION_CD is NULL.  */
            Random random = new Random();
            String correctionRecord2 = String.format(LA_C_RECORD2_TMPL, random.nextInt(STREET_NO_LIMIT));
            mockDatUtil.writeToInputDatFile(Collections.singletonList(correctionRecord2), StateCode.LA, LA_FILENAME);
            assertTrue(fileIngestionUtil.executeFileIngestion("LA", 2016));

        /* Assert IRS_TRANSMISSION_CD is still NULL */
            record1 = filerDemographicRepository.findOne(LA_PK_RECORD1);
            record2 = filerDemographicRepository.findOne(LA_PK_RECORD2);
            Assert.isNull(record1.getIrsTransmissionStatusCd());
            assertEquals("PR", record2.getIrsTransmissionStatusCd());

        } finally {

        /* Clean-up created records */
            databaseCleanupUtil.cleanUpDatabase(Arrays.asList(LA_PK_RECORD1, LA_PK_RECORD2));
            String inputFolderPath = profileProperties.getProperty("LA_INPUT_FOLDER");
            Files.deleteIfExists(new File(new File(inputFolderPath).getAbsolutePath() + "/" + LA_FILENAME).toPath());
        }
    }


}
