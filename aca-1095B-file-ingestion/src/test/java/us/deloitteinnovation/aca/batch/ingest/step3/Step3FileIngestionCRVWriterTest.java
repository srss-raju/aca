package us.deloitteinnovation.aca.batch.ingest.step3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.BusinessValidationRuleDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.services.Step3RecordValidationService;
import us.deloitteinnovation.aca.batch.ingest.step3.services.impl.Step3RecordValidationServiceImpl;

import javax.print.DocFlavor;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

/**
 * Created by tthakore on 10/21/2016.
 */
public class Step3FileIngestionCRVWriterTest {

    Step3FileIngestionCRVWriter step3FileIngestionCRVWriter;
    Step3RecordValidationService step3RecordValidationService;

    @Before
    public void initConfig() {
        step3FileIngestionCRVWriter = new Step3FileIngestionCRVWriter();
        // step3RecordValidationService = new Step3RecordValidationServiceImpl();
        step3RecordValidationService = mock(Step3RecordValidationServiceImpl.class);
    }

    @Test
    public void checkAndMarkRegenerateTest()
    {
        List<Step3FilerDataDto> recordsToMarkRegenerate = new ArrayList<>();
        Step3FilerDataDto item = new Step3FilerDataDto();
        item.setFormStatus("GENERATED");
        item.setRowNumber("0");
        Step3FilerDataDto coveredPerson = new Step3FilerDataDto();
        coveredPerson.getResposiblePersonList().add(item);
        step3FileIngestionCRVWriter.checkAndMarkRegenerate(coveredPerson, recordsToMarkRegenerate);
        Assert.assertEquals(1, recordsToMarkRegenerate.size());
    }

    @Test
    public void checkAndMarkNULLTest()
    {
        List<Step3FilerDataDto> recordsToMarkRegenerate = new ArrayList<>();
        Step3FilerDataDto item = new Step3FilerDataDto();
        item.setRowNumber("0");
        Step3FilerDataDto coveredPerson = new Step3FilerDataDto();
        coveredPerson.getResposiblePersonList().add(item);
        step3FileIngestionCRVWriter.checkAndMarkRegenerate(coveredPerson, recordsToMarkRegenerate);
        Assert.assertEquals(0, recordsToMarkRegenerate.size());
    }

    @Test
    public void getBusinessRuleDTOTest()
    {
        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.setBatchInfo(new BatchInfoDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.setResponsiblePersonUniqueId("123456689");
        filerDataDto.setRowNumber("1");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecipientDob(new Date(System.currentTimeMillis()));
        filerDataDto.setCorrection("O");
        filerDataDto.setRecordStatus("PASSED");
        filerDataDto.setBDMessage("this record is passed");
        BusinessValidationRuleDto businessValidationRuleDto= step3FileIngestionCRVWriter.getBusinessRuleDTO(filerDataDto);
        Assert.assertEquals("this record is passed", businessValidationRuleDto.getBusinessRule());
        Assert.assertEquals(123456789, businessValidationRuleDto.getSourceUniqueId());
        Assert.assertEquals("TXCCCHHH",businessValidationRuleDto.getSourceCd());
    }

    @Test
    public void updateRecordsForFDUpdateTest()
    {
        Step3FilerDataDto acceptedRecord = new Step3FilerDataDto();
        ArrayList<Step3FilerDataDto> insertForFD = new ArrayList<>();
        ArrayList<Step3FilerDataDto> updateForFD = new ArrayList<>();
        Map<String, Step3FilerDataDto> otherRecordsToUpdateVersionFD = new HashMap<>();

        acceptedRecord.setIsRecordExistsInDB(true);
        acceptedRecord.setId(new FilerDemographicPKDto());
        acceptedRecord.getId().setSourceCd("TXCCCHHH");
        acceptedRecord.getId().setSourceUniqueId("123456789");
        acceptedRecord.getId().setTaxYear("2016");


        ReflectionTestUtils.setField(step3FileIngestionCRVWriter, "insertForFD", insertForFD);
        ReflectionTestUtils.setField(step3FileIngestionCRVWriter, "updateForFD", updateForFD);
        ReflectionTestUtils.setField(step3FileIngestionCRVWriter, "otherRecordsToUpdateVersionFD", otherRecordsToUpdateVersionFD);
        step3FileIngestionCRVWriter.updateRecordsForFD(acceptedRecord);
        Assert.assertEquals(1, updateForFD.size());
    }

    @Test
    public void updateRecordsForFDOriginalTest()
    {
        Step3FilerDataDto acceptedRecord = new Step3FilerDataDto();
        ArrayList<Step3FilerDataDto> insertForFD = new ArrayList<>();
        ArrayList<Step3FilerDataDto> updateForFD = new ArrayList<>();
        Map<String, Step3FilerDataDto> otherRecordsToUpdateVersionFD = new HashMap<>();

        acceptedRecord.setIsRecordExistsInDB(true);
        acceptedRecord.setId(new FilerDemographicPKDto());
        acceptedRecord.getId().setSourceCd("TXCCCHHH");
        acceptedRecord.getId().setSourceUniqueId("123456789");
        acceptedRecord.getId().setTaxYear("2016");


        ReflectionTestUtils.setField(step3FileIngestionCRVWriter, "insertForFD", insertForFD);
        ReflectionTestUtils.setField(step3FileIngestionCRVWriter, "updateForFD", updateForFD);
        ReflectionTestUtils.setField(step3FileIngestionCRVWriter, "otherRecordsToUpdateVersionFD", otherRecordsToUpdateVersionFD);
        step3FileIngestionCRVWriter.updateRecordsForFD(acceptedRecord);
        Assert.assertEquals(1, updateForFD.size());
    }
}
