package us.deloitteinnovation.aca.batch.ingest.step3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.utils.Step3CorrectedRecordsValidationUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by tthakore on 11/8/2016.
 */
public class Step3CorrectedRecordsValidationUtilsTest {

    private static Logger logger = LoggerFactory.getLogger(Step3CorrectedRecordsValidationUtilsTest.class);

    public Step3CorrectedRecordsValidationUtils step3CorrectedRecordsValidationUtils;

    private Environment env;

    @Before
    public void initConfig()
    {
        step3CorrectedRecordsValidationUtils = new Step3CorrectedRecordsValidationUtils();
        env = mock(Environment.class);
    }

    /* positive path scenario */
    @Test
    public void validateCorrectedRecordTest(){
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2015");
        parent.setFilerStatus("R");
        parent.setRowNumber("1");

        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.setRowNumber("1");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        filerDataDto.setRecordStatus("PASSED");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        uidlist.add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("C");


        FilerCoverageDto filerCoverageDto = new FilerCoverageDto();
        filerCoverageDto.setOrigCoverageEndDt(new Date(System.currentTimeMillis()));
        filerCoverageDto.setOrigCoverageBeginDt(new Date(System.currentTimeMillis()));
        filerDataDto1.addFilerCoverage(filerCoverageDto);
        uidlist.add(filerDataDto1);

        parent.setFilersWithSameIdsInFile(uidlist);
        parent.setFilersWithSameIdsInDB(new ArrayList<>());
        ReflectionTestUtils.setField(step3CorrectedRecordsValidationUtils, "env", env);
        try{
            step3CorrectedRecordsValidationUtils.validateCorrectedRecord(filerDataDto1, parent);
        }
        catch(Exception e)
        {
            logger.error("Test case failed for validationForOriginalsScenario1Test.");
        }
        Assert.assertEquals(1, parent.getAcceptedRecordList().size());
    }
    /* when there is no master record in DB or file*/
    @Test
    public void validateCorrectedRecordForNoOriginalTest(){
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2015");
        parent.setFilerStatus("R");
        parent.setRowNumber("1");

        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.setRowNumber("1");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("U");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        uidlist.add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("C");
        uidlist.add(filerDataDto1);

        parent.setFilersWithSameIdsInFile(uidlist);
        parent.setFilersWithSameIdsInDB(new ArrayList<>());
        ReflectionTestUtils.setField(step3CorrectedRecordsValidationUtils, "env", env);
        try{
            step3CorrectedRecordsValidationUtils.validateCorrectedRecord(filerDataDto1, parent);
        }
        catch(Exception e)
        {
            logger.error("Test case failed for validationForOriginalsScenario1Test.");
        }
        Assert.assertEquals(0, parent.getAcceptedRecordList().size());
    }


    /*to validate if there is only one record of specific uid in file and there is no record in DB.*/
    @Test
    @Ignore("Need to check later") //TODO: Check why this test fails intermittently
    public void validForSingleRecordCheckTest()
    {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2015");
        parent.setFilerStatus("R");
        parent.setRowNumber("1");


        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.setRowNumber("1");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("C");
        parent.getFilersWithSameIdsInFile().add(filerDataDto);
        ReflectionTestUtils.setField(step3CorrectedRecordsValidationUtils, "env", env);
        step3CorrectedRecordsValidationUtils.validForSingleRecordCheck(filerDataDto, parent);
        Assert.assertEquals("FAILED", filerDataDto.getRecordStatus());
    }

    @Test
    public void getOriginalRecordIndexTest()
    {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2016");
        parent.setFilerStatus("R");
        parent.setRowNumber("1");


        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2016");
        filerDataDto.setRowNumber("1");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        filerDataDto.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInFile().add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.getId().setTaxYear("2016");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("O");
        filerDataDto1.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInFile().add(filerDataDto1);
        ReflectionTestUtils.setField(step3CorrectedRecordsValidationUtils, "env", env);
        step3CorrectedRecordsValidationUtils.getOriginalRecordIndex(filerDataDto1,parent);
    }

    @Test
    public void getOriginalRecordDBTest()
    {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2016");
        parent.setFilerStatus("R");
        parent.setRowNumber("1");


        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2016");
        filerDataDto.setRowNumber("0");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        filerDataDto.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInDB().add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.getId().setTaxYear("2016");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("U");
        filerDataDto1.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInFile().add(filerDataDto1);
        Assert.assertEquals("123456789", step3CorrectedRecordsValidationUtils.getOriginalRecordDB(filerDataDto1, parent.getFilersWithSameIdsInDB()).getSourceUniqueId());
    }

    @Test
    public void validForDatabaseRecordCheckTest()
    {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2016");
        parent.setFilerStatus("R");
        parent.setRowNumber("1");


        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2016");
        filerDataDto.setRowNumber("0");
        filerDataDto.setRecordSource("DB");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        filerDataDto.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInDB().add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.getId().setTaxYear("2016");
        filerDataDto.setRecordSource("FILE");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("C");
        filerDataDto1.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInFile().add(filerDataDto1);
        ReflectionTestUtils.setField(step3CorrectedRecordsValidationUtils, "env", env);
        step3CorrectedRecordsValidationUtils.validForDatabaseRecordCheck(filerDataDto1, parent);
    }

    @Test
    public void processDBValidationRuleTest()
    {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2016");
        parent.setFilerStatus("R");
        parent.setRowNumber("1");


        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2016");
        filerDataDto.setRowNumber("0");
        filerDataDto.setRecordSource("DB");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        filerDataDto.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInDB().add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.getId().setTaxYear("2016");
        filerDataDto.setRecordSource("FILE");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("C");
        filerDataDto1.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInFile().add(filerDataDto1);
        ReflectionTestUtils.setField(step3CorrectedRecordsValidationUtils, "env", env);

        for (Step3FilerDataDto item : parent.getFilersWithSameIdsInDB()) {

            String itemUid = item.getUIDValue();
            String recordUid = filerDataDto1.getUIDValue();
            step3CorrectedRecordsValidationUtils.processDBValidationRule(itemUid, recordUid, filerDataDto1, item, parent);
        }

        Assert.assertEquals(1, parent.getAcceptedRecordList().size());

    }

    @Test
    public void checkForSameSsnDifferentRIDDBTest()
    {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2016");
        parent.setFilerStatus("R");
        parent.setRowNumber("1");


        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2016");
        filerDataDto.setRowNumber("0");
        filerDataDto.setRecordSource("DB");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        filerDataDto.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInDB().add(filerDataDto);


        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.getId().setTaxYear("2016");
        filerDataDto.setRecordSource("FILE");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("C");
        filerDataDto1.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInFile().add(filerDataDto1);
        ReflectionTestUtils.setField(step3CorrectedRecordsValidationUtils, "env", env);
        Assert.assertEquals(true, step3CorrectedRecordsValidationUtils.checkForSameSsnDifferentRIDDB(filerDataDto1, parent));
    }

    @Test
    public void checkForSameSsnDifferentRIDFILETest()
    {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2016");
        parent.setFilerStatus("R");
        parent.setRowNumber("1");


        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2016");
        filerDataDto.setRowNumber("0");
        filerDataDto.setRecordSource("DB");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        filerDataDto.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInDB().add(filerDataDto);


        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.getId().setTaxYear("2016");
        filerDataDto.setRecordSource("FILE");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("C");
        filerDataDto1.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInFile().add(filerDataDto1);
        ReflectionTestUtils.setField(step3CorrectedRecordsValidationUtils, "env", env);
        Assert.assertEquals(true, step3CorrectedRecordsValidationUtils.checkForSameSsnDifferentRIDFILE(filerDataDto1, parent));
    }

    @Test
    @Ignore("Ignore for now") //TODO: Check why this test fails intermittently.
    public void validForFileRecordCheckTest()
    {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2016");
        parent.setFilerStatus("R");
        parent.setRowNumber("1");


        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2016");
        filerDataDto.setRowNumber("0");
        filerDataDto.setRecordSource("DB");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        filerDataDto.setRecordStatus("PASSED");
        parent.getFilersWithSameIdsInDB().add(filerDataDto);


        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.getId().setTaxYear("2016");
        filerDataDto.setRecordSource("FILE");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("C");
        filerDataDto1.setRecordStatus("PASSED");
        FilerCoverageDto filerCoverageDto = new FilerCoverageDto();
        filerCoverageDto.setOrigCoverageEndDt(new Date(System.currentTimeMillis()));
        filerCoverageDto.setOrigCoverageBeginDt(new Date(System.currentTimeMillis()));
        filerDataDto1.addFilerCoverage(filerCoverageDto);
        parent.getFilersWithSameIdsInFile().add(filerDataDto1);
        ReflectionTestUtils.setField(step3CorrectedRecordsValidationUtils, "env", env);
        step3CorrectedRecordsValidationUtils.validForFileRecordCheck(filerDataDto1, parent);
       // Assert.assertEquals(true, );
    }
}
