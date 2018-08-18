package us.deloitteinnovation.aca.batch.ingest.step3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.test.util.ReflectionTestUtils;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.utils.Step3OriginalRecordsValidationUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by tthakore on 9/15/2016. utils test
 */


public class Step3OriginalRecordsValidationUtilsTest {

    private static Logger logger = LoggerFactory.getLogger(Step3OriginalRecordsValidationUtilsTest.class);
    Step3OriginalRecordsValidationUtils step3OriginalRecordsValidationUtils;
    private Environment env;

    @Before
    public void initConfig()
    {
        step3OriginalRecordsValidationUtils = new Step3OriginalRecordsValidationUtils();
        env = mock(Environment.class);

    }


    @Test
    public void validationForOriginalsScenario1Test()
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
        filerDataDto.setRowNumber("2");
        filerDataDto.setFilerStatus("R");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        uidlist.add(filerDataDto);

        parent.setFilersWithSameIdsInFile(uidlist);
        parent.setFilersWithSameIdsInDB(new ArrayList<>());
        ReflectionTestUtils.setField(step3OriginalRecordsValidationUtils, "env", env);
        try{
            step3OriginalRecordsValidationUtils.validateOriginalRecord(filerDataDto, parent);
        }
        catch(Exception e)
        {
            logger.error("Test case failed for validationForOriginalsScenario1Test.");
        }
        Assert.assertEquals(1, parent.getAcceptedRecordList().size());
    }

    @Test
    public void markRecordAsFailedTest()
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
        filerDataDto.setRowNumber("2");
        filerDataDto.setFilerStatus("R");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        uidlist.add(filerDataDto);

        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("123456789");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.setRowNumber("3");
        ReflectionTestUtils.setField(step3OriginalRecordsValidationUtils, "env", env);
        step3OriginalRecordsValidationUtils.markRecordAsFailed(filerDataDto, filerDataDto1, parent, "ORIGINAL.ER_CV5.1.1.1",true,0);

        Assert.assertEquals(1, parent.getRejectedRecordList().size());
    }



}
