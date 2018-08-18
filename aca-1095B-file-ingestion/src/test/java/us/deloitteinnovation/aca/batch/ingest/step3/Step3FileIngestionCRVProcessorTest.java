package us.deloitteinnovation.aca.batch.ingest.step3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3RecordsUIDValidationResultMap;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3ValidationMapDto;
import us.deloitteinnovation.aca.batch.ingest.step3.utils.Step3OriginalRecordsValidationUtils;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by tthakore on 9/12/2016.
 */
public class Step3FileIngestionCRVProcessorTest {


    public Step3FileIngestionCRVProcessor step3FileIngestionCRVProcessor;
    Step3OriginalRecordsValidationUtils step3OriginalRecordsValidationUtils;



    @Before
    public void initConfig() {
        step3FileIngestionCRVProcessor = new Step3FileIngestionCRVProcessor();
        step3OriginalRecordsValidationUtils = mock(Step3OriginalRecordsValidationUtils.class);

    }

    @Test
    public void validationForOriginalsScenario1Test() {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        Step3RecordsUIDValidationResultMap step3RecordsUIDValidationResultMap = new Step3RecordsUIDValidationResultMap();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123456789");
        parent.getId().setTaxYear("2015");
        parent.setCorrection("O");
        parent.setRecordStatus(CommonDataConstants.VALIDATION_RULE_PASSED);
        parent.setRowNumber("1");
        parent.setFilerStatus("R");

        Step3ValidationMapDto step3ValidationMapDto = new Step3ValidationMapDto();
        step3ValidationMapDto.setUid("TXCCCHHH_123456789_2015");
        step3ValidationMapDto.setValidationStatus("PASSED");

        step3RecordsUIDValidationResultMap.getMapDtoMap().put("TXCCCHHH_123456789_2015", step3ValidationMapDto);

        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2015");
        filerDataDto.setRowNumber("2");
        filerDataDto.setCorrection("O");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecordStatus("PASSED");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        uidlist.add(filerDataDto);

        parent.setFilersWithSameIdsInFile(uidlist);
        parent.setFilersWithSameIdsInDB(new ArrayList<>());
        parent.getAcceptedRecordList().addAll(uidlist);
        try {
            ReflectionTestUtils.setField(step3FileIngestionCRVProcessor, "step3OriginalRecordsValidationUtils", step3OriginalRecordsValidationUtils);
            ReflectionTestUtils.setField(step3FileIngestionCRVProcessor, "step3RecordsUIDValidationResultMap", step3RecordsUIDValidationResultMap);
            step3FileIngestionCRVProcessor.process(parent);
        } catch (Exception e) {

        }
        Assert.assertEquals(1, parent.getAcceptedRecordList().size());
    }


    @Test
    public void performBusinessOperationsOnAcceptedRecordForOTest() {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123489");
        parent.getId().setTaxYear("2015");
        parent.setRowNumber("1");
        parent.setFilerStatus("R");

        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2015");
        filerDataDto.setCorrection("O");
        filerDataDto.setRecordStatus(CommonDataConstants.VALIDATION_RULE_PASSED);
        filerDataDto.setRowNumber("2");
        filerDataDto.setFilerStatus("R");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        uidlist.add(filerDataDto);
        parent.getAcceptedRecordList().addAll(uidlist);


        step3FileIngestionCRVProcessor.performBusinessOperationsOnAcceptedRecord(parent);
        Assert.assertEquals(1, parent.getRecordsForFD().size());
    }


    @Test
    public void getMasterRecordDBTest() {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123456789");
        parent.getId().setTaxYear("2015");
        parent.setRowNumber("1");
        parent.setFilerStatus("R");

        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2015");
        filerDataDto.setCorrection("O");
        filerDataDto.setRowNumber("0");
        filerDataDto.setFilerStatus("R");
        parent.getFilersWithSameIdsInDB().add(filerDataDto);

        Step3FilerDataDto masterRecord = step3FileIngestionCRVProcessor.getMasterRecord(parent);
        Assert.assertEquals("123456789", masterRecord.getSourceUniqueId());
        Assert.assertEquals(true, masterRecord.getIsRecordExistsInDB());
    }

    @Test
    public void getMasterRecordFileTest() {
        Step3FilerDataDto parent = new Step3FilerDataDto();
        parent.setId(new FilerDemographicPKDto());
        parent.getId().setSourceCd("TXCCCHHH");
        parent.getId().setSourceUniqueId("123456789");
        parent.getId().setTaxYear("2015");
        parent.setCorrection("O");
        parent.setRowNumber("1");
        parent.setFilerStatus("R");

        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");
        filerDataDto.getId().setSourceUniqueId("123456789");
        filerDataDto.getId().setTaxYear("2015");
        filerDataDto.setCorrection("O");
        filerDataDto.setRowNumber("2");
        filerDataDto.setFilerStatus("R");
        filerDataDto.setRecordStatus(CommonDataConstants.VALIDATION_RULE_PASSED);
        parent.getAcceptedRecordList().add(filerDataDto);

        Step3FilerDataDto masterRecord = step3FileIngestionCRVProcessor.getMasterRecord(parent);
        Assert.assertEquals("123456789", masterRecord.getSourceUniqueId());
        Assert.assertEquals(false, masterRecord.getIsRecordExistsInDB());
    }

    @Test
    public void mergeCoverageInfoTest() {
        Step3FilerDataDto mainRecord = new Step3FilerDataDto();
        Step3FilerDataDto subsequentRecord = new Step3FilerDataDto();
        mainRecord.setJan("1");
        mainRecord.setFeb("1");
        mainRecord.setMar("1");
        mainRecord.setApr("1");
        mainRecord.setMay("0");
        mainRecord.setJun("0");
        mainRecord.setJul("0");
        mainRecord.setAug("0");
        mainRecord.setSep("1");
        mainRecord.setOct("1");
        mainRecord.setNov("1");
        mainRecord.setDec("1");
        subsequentRecord.setJan("0");
        subsequentRecord.setFeb("0");
        subsequentRecord.setMar("0");
        subsequentRecord.setApr("0");
        subsequentRecord.setMay("0");
        subsequentRecord.setJun("0");
        subsequentRecord.setJul("0");
        subsequentRecord.setAug("0");
        subsequentRecord.setSep("1");
        subsequentRecord.setOct("1");
        subsequentRecord.setNov("1");
        subsequentRecord.setDec("1");
        step3FileIngestionCRVProcessor.mergeCoverageInfo(mainRecord, subsequentRecord);
        Assert.assertEquals("1", mainRecord.getJan());
        Assert.assertEquals("0", mainRecord.getMay());
        Assert.assertEquals("1", mainRecord.getDec());
    }

    @Test
    public void doNotmergeCoverageInfoTest() {
        Step3FilerDataDto mainRecord = new Step3FilerDataDto();
        Step3FilerDataDto subsequentRecord = new Step3FilerDataDto();
        mainRecord.setJan("0");
        mainRecord.setFeb("0");
        mainRecord.setMar("0");
        mainRecord.setApr("0");
        mainRecord.setMay("0");
        mainRecord.setJun("0");
        mainRecord.setJul("0");
        mainRecord.setAug("0");
        mainRecord.setSep("1");
        mainRecord.setOct("1");
        mainRecord.setNov("1");
        mainRecord.setDec("1");
        subsequentRecord.setJan("0");
        subsequentRecord.setFeb("0");
        subsequentRecord.setMar("0");
        subsequentRecord.setApr("0");
        subsequentRecord.setMay("0");
        subsequentRecord.setJun("0");
        subsequentRecord.setJul("0");
        subsequentRecord.setAug("0");
        subsequentRecord.setSep("1");
        subsequentRecord.setOct("1");
        subsequentRecord.setNov("1");
        subsequentRecord.setDec("1");
        step3FileIngestionCRVProcessor.mergeCoverageInfo(mainRecord, subsequentRecord);
        Assert.assertEquals(false, subsequentRecord.getIsCoverageChangesAvailable());

    }

    @Test
    public void updateRecipientInfoTest() {
        Step3FilerDataDto mainRecord = new Step3FilerDataDto();
        Step3FilerDataDto subsequentRecord = new Step3FilerDataDto();

        mainRecord.setRecipientLastName("lname1");
        mainRecord.setRecipientFirstName("fname1");
        mainRecord.setBatchInfo(new BatchInfoDto());

        subsequentRecord.setRecipientLastName("lname2");
        subsequentRecord.setRecipientFirstName("fname2");
        subsequentRecord.setBatchInfo(new BatchInfoDto());

        step3FileIngestionCRVProcessor.updateRecipientInfo(mainRecord, subsequentRecord);
        Assert.assertEquals("lname2", mainRecord.getRecipientLastName());
        Assert.assertEquals("fname2", mainRecord.getRecipientFirstName());
    }

    @Test
    public void getMasterRecordDB() {
        Step3FilerDataDto parentItem = new Step3FilerDataDto();
        parentItem.setId(new FilerDemographicPKDto());
        parentItem.getId().setTaxYear("2016");
        parentItem.getId().setSourceCd("TXCCCHHH");
        parentItem.getId().setSourceUniqueId("1234567891");

        Step3FilerDataDto child1 = new Step3FilerDataDto();
        child1.setId(new FilerDemographicPKDto());
        child1.getId().setTaxYear("2016");
        child1.getId().setSourceCd("TXCCCHHH");
        child1.getId().setSourceUniqueId("12345691");

        Step3FilerDataDto child2 = new Step3FilerDataDto();
        child2.setId(new FilerDemographicPKDto());
        child2.getId().setTaxYear("2016");
        child2.getId().setSourceCd("TXCCCHHH");
        child2.getId().setSourceUniqueId("1234567891");

        parentItem.getFilersWithSameIdsInDB().add(child1);
        parentItem.getFilersWithSameIdsInDB().add(child2);

        Step3FilerDataDto masterRecord = step3FileIngestionCRVProcessor.getMasterRecord(parentItem);
        Assert.assertEquals("TXCCCHHH_1234567891_2016", masterRecord.getUIDValue());
    }

    @Test
    public void getMasterRecordFile() {
        Step3FilerDataDto parentItem = new Step3FilerDataDto();
        parentItem.setId(new FilerDemographicPKDto());
        parentItem.getId().setTaxYear("2016");
        parentItem.getId().setSourceCd("TXCCCHHH");
        parentItem.getId().setSourceUniqueId("1234567891");

        Step3FilerDataDto child1 = new Step3FilerDataDto();
        child1.setId(new FilerDemographicPKDto());
        child1.getId().setTaxYear("2016");
        child1.setCorrection("O");
        child1.setRecordStatus(CommonDataConstants.VALIDATION_RULE_FAILED);
        child1.getId().setSourceCd("TXCCCHHH");
        child1.getId().setSourceUniqueId("12345691");

        Step3FilerDataDto child2 = new Step3FilerDataDto();
        child2.setId(new FilerDemographicPKDto());
        child2.getId().setTaxYear("2016");
        child2.setCorrection("O");
        child2.setRecordStatus(CommonDataConstants.VALIDATION_RULE_PASSED);
        child2.getId().setSourceCd("TXCCCHHH");
        child2.getId().setSourceUniqueId("1234567891");

        parentItem.getAcceptedRecordList().add(child1);
        parentItem.getAcceptedRecordList().add(child2);

        Step3FilerDataDto masterRecord = step3FileIngestionCRVProcessor.getMasterRecord(parentItem);
        Assert.assertEquals("TXCCCHHH_1234567891_2016", masterRecord.getUIDValue());
    }

    @Test
    public void replaceFilersData() {
        Step3FilerDataDto mainRecord = new Step3FilerDataDto();
        Step3FilerDataDto subsequentRecord = new Step3FilerDataDto();
        mainRecord.setJan("1");
        mainRecord.setFeb("1");
        mainRecord.setMar("1");
        mainRecord.setApr("1");
        mainRecord.setMay("0");
        mainRecord.setJun("0");
        mainRecord.setJul("0");
        mainRecord.setAug("0");
        mainRecord.setSep("1");
        mainRecord.setOct("1");
        mainRecord.setNov("1");
        mainRecord.setDec("1");
        mainRecord.setRecipientSsn("456923456");
        mainRecord.setBatchInfo(new BatchInfoDto());


        subsequentRecord.setJan("0");
        subsequentRecord.setFeb("0");
        subsequentRecord.setMar("0");
        subsequentRecord.setApr("0");
        subsequentRecord.setMay("0");
        subsequentRecord.setJun("0");
        subsequentRecord.setJul("0");
        subsequentRecord.setAug("0");
        subsequentRecord.setSep("1");
        subsequentRecord.setOct("1");
        subsequentRecord.setNov("1");
        subsequentRecord.setDec("1");
        subsequentRecord.setRecipientSsn("987654321");
        FilerCoverageDto filerCoverage = new FilerCoverageDto();
        filerCoverage.setOrigCoverageBeginDt(new Date(System.currentTimeMillis()));
        filerCoverage.setOrigCoverageEndDt(new Date(System.currentTimeMillis()));
        subsequentRecord.addFilerCoverage(filerCoverage);
        subsequentRecord.setBatchInfo(new BatchInfoDto());

        step3FileIngestionCRVProcessor.replaceFilersData(mainRecord, subsequentRecord);
        Assert.assertEquals("987654321", mainRecord.getRecipientSsn());
    }

    @Test
    public void cleanUpCoverage() {
        String dayDate = "31-12-6666";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date day = new Date();
        try {
            day = sdf.parse(dayDate);
        } catch (ParseException pe) {

        }
        Step3FilerDataDto mainRecord = new Step3FilerDataDto();
        Step3FilerDataDto subsequentRecord = new Step3FilerDataDto();
        mainRecord.setRecipientSsn("456923456");
        mainRecord.setBatchInfo(new BatchInfoDto());
        subsequentRecord.setRecipientSsn("987654321");
        FilerCoverageDto filerCoverage = new FilerCoverageDto();
        filerCoverage.setOrigCoverageBeginDt(day);
        filerCoverage.setOrigCoverageEndDt(day);
        subsequentRecord.addFilerCoverage(filerCoverage);
        subsequentRecord.setBatchInfo(new BatchInfoDto());

        step3FileIngestionCRVProcessor.replaceFilersData(mainRecord, subsequentRecord);
        Assert.assertEquals("0", mainRecord.getJan());
        Assert.assertEquals("0", mainRecord.getFeb());
        Assert.assertEquals("0", mainRecord.getMar());
        Assert.assertEquals("0", mainRecord.getApr());
        Assert.assertEquals("0", mainRecord.getMay());
        Assert.assertEquals("0", mainRecord.getJun());
        Assert.assertEquals("0", mainRecord.getJul());
        Assert.assertEquals("0", mainRecord.getAug());
        Assert.assertEquals("0", mainRecord.getSep());
        Assert.assertEquals("0", mainRecord.getOct());
        Assert.assertEquals("0", mainRecord.getNov());
        Assert.assertEquals("0", mainRecord.getDec());
    }

    @Test
    public void setVersionNoAndCorIndicatorDBTest() {
        Step3FilerDataDto item = new Step3FilerDataDto();
        item.setFilerStatus("R");
        item.setVersionNumber(0);
        step3FileIngestionCRVProcessor.setVersionNoAndCorIndicatorDB(item);
        Assert.assertEquals("0", item.getCorrectionIndicator());
        Assert.assertEquals( "1" , String.valueOf(item.getVersionNumber()));
    }

    @Test
    public void setVersionNoAndCorIndicator2DBTest() {
        Step3FilerDataDto item = new Step3FilerDataDto();
        item.setFilerStatus("R");
        item.setVersionNumber(0);
        item.setFormStatus("GENERATED");
        item.setIrsTransmissionStatusCD("XC");
        step3FileIngestionCRVProcessor.setVersionNoAndCorIndicatorDB(item);
        Assert.assertEquals("2", item.getCorrectionIndicator());
        Assert.assertEquals( "1" , String.valueOf(item.getVersionNumber()));
    }

    @Test
    public void setVersionNoAndCorIndicator1DBTest() {
        Step3FilerDataDto item = new Step3FilerDataDto();
        item.setFilerStatus("R");
        item.setVersionNumber(0);
        item.setFormStatus("GENERATED");
        item.setIrsTransmissionStatusCD(null);
        step3FileIngestionCRVProcessor.setVersionNoAndCorIndicatorDB(item);
        Assert.assertEquals("1", item.getCorrectionIndicator());
        Assert.assertEquals( "1" , String.valueOf(item.getVersionNumber()));
    }

    @Test
    public void setVersionNoAndCorIndicator2FILE() {
        Step3FilerDataDto item = new Step3FilerDataDto();
        item.setFilerStatus("R");
        item.setVersionNumber(0);
        item.setFormStatus("GENERATED");
        item.setIrsTransmissionStatusCD("XC");
        step3FileIngestionCRVProcessor.setVersionNoAndCorIndicatorFILE(item);
        Assert.assertEquals("2", item.getCorrectionIndicator());
        Assert.assertEquals( "0" , String.valueOf(item.getVersionNumber()));
    }

    @Test
    public void setVersionNoAndCorIndicator1FILE() {
        Step3FilerDataDto item = new Step3FilerDataDto();
        item.setFilerStatus("R");
        item.setVersionNumber(0);
        item.setFormStatus("GENERATED");
        item.setIrsTransmissionStatusCD(null);
        step3FileIngestionCRVProcessor.setVersionNoAndCorIndicatorFILE(item);
        Assert.assertEquals("1", item.getCorrectionIndicator());
        Assert.assertEquals( "0" , String.valueOf(item.getVersionNumber()));
    }

    @Test
    public void setVersionNoAndCorIndicatorFILE() {
        Step3FilerDataDto item = new Step3FilerDataDto();
        item.setFilerStatus("R");
        item.setVersionNumber(0);
        item.setIrsTransmissionStatusCD(null);
        step3FileIngestionCRVProcessor.setVersionNoAndCorIndicatorFILE(item);
        Assert.assertEquals("0", item.getCorrectionIndicator());
        Assert.assertEquals( "0" , String.valueOf(item.getVersionNumber()));
    }

}
