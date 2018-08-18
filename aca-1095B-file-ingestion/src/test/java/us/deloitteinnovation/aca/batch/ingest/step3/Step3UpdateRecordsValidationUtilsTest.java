package us.deloitteinnovation.aca.batch.ingest.step3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.utils.Step3UpdateRecordsValidationUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by tthakore on 10/14/2016.
 */
public class Step3UpdateRecordsValidationUtilsTest {

    private static Logger logger = LoggerFactory.getLogger(Step3OriginalRecordsValidationUtilsTest.class);

    Step3UpdateRecordsValidationUtils step3UpdateRecordsValidationUtils;
    private Environment env;

    @Before
    public void initConfig() {
        step3UpdateRecordsValidationUtils = new Step3UpdateRecordsValidationUtils();
        env = mock(Environment.class);
    }

    /* positive path scenario */
    @Test
    @Ignore
    public void validateUpdateRecordTest() {
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
        filerDataDto.getId().setTaxYear("2015");
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
        filerDataDto1.getId().setTaxYear("2015");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("U");
        uidlist.add(filerDataDto1);

        parent.getFilersWithSameIdsInFile().addAll(uidlist);
        parent.setFilersWithSameIdsInDB(new ArrayList<>());
        ReflectionTestUtils.setField(step3UpdateRecordsValidationUtils, "env", env);
        try {
            step3UpdateRecordsValidationUtils.validateUpdateRecord(filerDataDto1, parent);
        } catch (Exception e) {
            logger.error("Test case failed for validationForOriginalsScenario1Test.");
        }
        Assert.assertEquals(1, parent.getAcceptedRecordList().size());
    }

    /* when there is no master record in DB or file*/
    @Test
    @Ignore
    public void validateUpdateRecordForNoOriginalTest() {
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
        filerDataDto1.setCorrection("U");
        uidlist.add(filerDataDto1);

        parent.setFilersWithSameIdsInFile(uidlist);
        parent.setFilersWithSameIdsInDB(new ArrayList<>());
        ReflectionTestUtils.setField(step3UpdateRecordsValidationUtils, "env", env);
        try {
            step3UpdateRecordsValidationUtils.validateUpdateRecord(filerDataDto1, parent);
        } catch (Exception e) {
            logger.error("Test case failed for validationForOriginalsScenario1Test.");
        }
        Assert.assertEquals(1, parent.getRejectedRecordList().size());
    }


    /* when there is  master record in DB*/
    @Test
    @Ignore
    public void validateUpdateRecordForInDBOriginalTest() {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2015");
        parent.setFilerStatus("R");
        parent.setRowNumber("0");

        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.setRowNumber("0");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("U");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        parent.getFilersWithSameIdsInDB().add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("U");
        uidlist.add(filerDataDto1);

        parent.setFilersWithSameIdsInFile(uidlist);

        ReflectionTestUtils.setField(step3UpdateRecordsValidationUtils, "env", env);
        try {
            step3UpdateRecordsValidationUtils.validateUpdateRecord(filerDataDto1, parent);
        } catch (Exception e) {
            logger.error("Test case failed for validationForOriginalsScenario1Test.");
        }
        Assert.assertEquals(1, parent.getAcceptedRecordList().size());
    }

    /* when there is  master record in File*/
    @Test
    @Ignore
    public void validateUpdateRecordForInFileOriginalTest() {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2015");
        parent.setFilerStatus("R");
        parent.setRowNumber("0");

        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.setRowNumber("1");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecordStatus("PASSED");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        uidlist.add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("U");
        uidlist.add(filerDataDto1);

        parent.setFilersWithSameIdsInFile(uidlist);
        ReflectionTestUtils.setField(step3UpdateRecordsValidationUtils, "env", env);
        try {
            step3UpdateRecordsValidationUtils.validateUpdateRecord(filerDataDto1, parent);
        } catch (Exception e) {
            logger.error("Test case failed for validationForOriginalsScenario1Test.");
        }
        Assert.assertEquals(1, parent.getAcceptedRecordList().size());
    }

    /* should not accept update when filer_status C and try to change responsible person with U*/
    @Test
    @Ignore
    public void validateUpdateForCoveredPersonChangeRUIDTest() {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2015");
        parent.setFilerStatus("R");
        parent.setRowNumber("0");

        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.setResponsiblePersonUniqueId("123456689");
        filerDataDto.setRowNumber("1");
        filerDataDto.setFilerStatus("C");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        parent.getFilersWithSameIdsInDB().add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto.setResponsiblePersonUniqueId("123457689");
        filerDataDto1.setFilerStatus("C");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("U");
        uidlist.add(filerDataDto1);

        parent.setFilersWithSameIdsInFile(uidlist);
        ReflectionTestUtils.setField(step3UpdateRecordsValidationUtils, "env", env);
        try {
            step3UpdateRecordsValidationUtils.validateUpdateRecord(filerDataDto1, parent);
        } catch (Exception e) {
            logger.error("Test case failed for validationForOriginalsScenario1Test.");
        }
        Assert.assertEquals(0, parent.getAcceptedRecordList().size());
    }


    /* should  accept update when filer_status C and  keeps same responsible person for U records*/
    @Test
    @Ignore
    public void validateUpdateForCoveredPersonSameRUIDTest() {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2015");
        parent.setFilerStatus("R");
        parent.setRowNumber("0");

        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.setResponsiblePersonUniqueId("123456689");
        filerDataDto.setRowNumber("0");
        filerDataDto.setFilerStatus("C");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        parent.getFilersWithSameIdsInDB().add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setResponsiblePersonUniqueId("123456689");
        filerDataDto1.setFilerStatus("C");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("U");
        uidlist.add(filerDataDto1);

        parent.setFilersWithSameIdsInFile(uidlist);
        ReflectionTestUtils.setField(step3UpdateRecordsValidationUtils, "env", env);
        try {
            step3UpdateRecordsValidationUtils.validateUpdateRecord(filerDataDto1, parent);
        } catch (Exception e) {
            logger.error("Test case failed for validationForOriginalsScenario1Test.");
        }
        Assert.assertEquals(1, parent.getAcceptedRecordList().size());
    }

    @Test
    public void validForSingleRecordCheckTest() {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2015");
        parent.setFilerStatus("R");
        parent.setRowNumber("0");
        List<Step3FilerDataDto> uidlist = new ArrayList<>();
        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setResponsiblePersonUniqueId("123456689");
        filerDataDto1.setFilerStatus("C");
        filerDataDto1.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto1.setCorrection("U");
        uidlist.add(filerDataDto1);

        parent.setFilersWithSameIdsInFile(uidlist);
        ReflectionTestUtils.setField(step3UpdateRecordsValidationUtils, "env", env);
        step3UpdateRecordsValidationUtils.validForSingleRecordCheck(filerDataDto1, parent);
        Assert.assertEquals(1, parent.getRejectedRecordList().size());
    }



}
