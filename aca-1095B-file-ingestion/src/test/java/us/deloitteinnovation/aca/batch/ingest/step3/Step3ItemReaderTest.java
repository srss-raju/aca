package us.deloitteinnovation.aca.batch.ingest.step3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3InitialFilerList;
import us.deloitteinnovation.aca.batch.ingest.step3.services.Step3RecordValidationService;
import us.deloitteinnovation.aca.batch.ingest.step3.services.impl.Step3RecordValidationServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by tthakore on 9/11/2016.
 */

public class Step3ItemReaderTest {

    Step3RecordValidationService step3RecordValidationService;
    Step3FileIngestionCRVReader step3ItemReader;

    @Before
    public void initConfig() {
        step3ItemReader = new Step3FileIngestionCRVReader(new Step3InitialFilerList());

        step3RecordValidationService = mock(Step3RecordValidationServiceImpl.class);
    }

    @Test
    public void createUIDListByRowNumberTest() {
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
        filerDataDto.setRowNumber("1");
        filerDataDto.getId().setTaxYear("2015");
        filerDataDto.setRecipientSsn("123456789");
        filerDataDto.setFilerStatus("R");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        uidlist.add(filerDataDto);


        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("987654321");
        filerDataDto1.setRecipientSsn("123456789");
        filerDataDto1.setRowNumber("2");
        filerDataDto1.setFilerStatus("R");
        List<Step3FilerDataDto> iidlist1 = new ArrayList<Step3FilerDataDto>();
        iidlist1.add(filerDataDto1);

        Mockito.when(step3RecordValidationService.getRecordsByUID(parent.getSourceCd(), Long.valueOf(parent.getSourceUniqueId()), parent.getId().getTaxYear())).thenReturn(uidlist);
        Mockito.when(step3RecordValidationService.getRecordsBySSN("123456789", "2015", "TXCCCHHH")).thenReturn(iidlist1);
        Mockito.when(step3RecordValidationService.getRecordsByTIN("123456789", "2015", "TXCCCHHH")).thenReturn(iidlist1);
        ReflectionTestUtils.setField(step3ItemReader, "step3RecordValidationService", step3RecordValidationService);
        try{
            step3ItemReader.createUIDListByRowNumber(parent);
        }catch (Exception e)
        {

        }

        Assert.assertEquals(2, parent.getFilersWithSameIdsInFile().size());
    }

    @Test
    public void arrangeFilersByIdsTest() {
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
        filerDataDto.setRowNumber("2");
        filerDataDto.setFilerStatus("R");
        List<Step3FilerDataDto> uidlist = new ArrayList<Step3FilerDataDto>();
        uidlist.add(filerDataDto);


        Step3FilerDataDto filerDataDto1 = new Step3FilerDataDto();
        filerDataDto1.setId(new FilerDemographicPKDto());
        filerDataDto1.getId().setSourceCd("TXCCCHHH");
        filerDataDto1.getId().setSourceUniqueId("987654321");
        filerDataDto1.setRecipientSsn("123456789");
        filerDataDto1.setFilerStatus("R");
        filerDataDto1.setRowNumber("3");
        uidlist.add(filerDataDto1);

       try{
           step3ItemReader.arrangeFilersByIds(uidlist.iterator(), new HashMap<String, Step3FilerDataDto>(), new ArrayList<>());
       }catch (Exception e){}
        Assert.assertEquals(0, uidlist.size());
    }

    @Test
    public void updateRecordListTest()
    {
        List<Step3FilerDataDto> fdRecordList = new ArrayList<>();
        Step3FilerDataDto uid1 = new Step3FilerDataDto();
        uid1.setId(new FilerDemographicPKDto());
        uid1.getId().setSourceCd("TXCCCHHH");
        uid1.getId().setSourceUniqueId("123456789");
        uid1.getId().setTaxYear("2015");
        uid1.setRowNumber("0");
        uid1.setFilerStatus("R");
        fdRecordList.add(uid1);

        Step3FilerDataDto uid2 = new Step3FilerDataDto();
        uid2.setId(new FilerDemographicPKDto());
        uid2.getId().setSourceCd("TXCCCHHH");
        uid2.getId().setSourceUniqueId("1234567819");
        uid2.setRowNumber("0");
        uid2.setFilerStatus("R");
        fdRecordList.add(uid2);

        Step3FilerDataDto element = new Step3FilerDataDto();
        element.setId(new FilerDemographicPKDto());
        element.getId().setSourceCd("TXCCCHHH");
        element.getId().setSourceUniqueId("1239");
        element.setRowNumber("0");
        element.setFilerStatus("R");
        step3ItemReader.updateRecordList(fdRecordList, element);
        Assert.assertEquals(3,fdRecordList.size());
    }

    @Test
    public void updateRecordListNTest()
    {
        List<Step3FilerDataDto> fdRecordList = new ArrayList<>();
        Step3FilerDataDto uid1 = new Step3FilerDataDto();
        uid1.setId(new FilerDemographicPKDto());
        uid1.getId().setSourceCd("TXCCCHHH");
        uid1.getId().setSourceUniqueId("123456789");
        uid1.getId().setTaxYear("2015");
        uid1.setRowNumber("0");
        uid1.setFilerStatus("R");
        fdRecordList.add(uid1);

        Step3FilerDataDto uid2 = new Step3FilerDataDto();
        uid2.setId(new FilerDemographicPKDto());
        uid2.getId().setSourceCd("TXCCCHHH");
        uid2.getId().setSourceUniqueId("123456789");
        uid2.setRowNumber("0");
        uid2.getId().setTaxYear("2015");
        uid2.setFilerStatus("R");
        fdRecordList.add(uid2);

        Step3FilerDataDto element = new Step3FilerDataDto();
        element.setId(new FilerDemographicPKDto());
        element.getId().setSourceCd("TXCCCHHH");
        element.getId().setSourceUniqueId("123456789");
        element.setRowNumber("0");
        element.getId().setTaxYear("2015");
        element.setFilerStatus("R");
        step3ItemReader.updateRecordList(fdRecordList, element);
        Assert.assertEquals(2,fdRecordList.size());
    }

    @Test
    public void updateResposiblePersonDetailsTest()
    {

        Step3FilerDataDto step3FilerDataDto = new Step3FilerDataDto();
        step3FilerDataDto.setId(new FilerDemographicPKDto());
        step3FilerDataDto.getId().setSourceCd("TXCCCHHH");
        step3FilerDataDto.getId().setSourceUniqueId("123456789");
        step3FilerDataDto.setResponsiblePersonUniqueId("123456789");
        step3FilerDataDto.getId().setTaxYear("2016");

        Step3FilerDataDto step3FilerDataDto1 = new Step3FilerDataDto();
        step3FilerDataDto1.setId(new FilerDemographicPKDto());
        step3FilerDataDto.getId().setSourceCd("TXCCCHHH");
        step3FilerDataDto.getId().setSourceUniqueId("12345222");
        step3FilerDataDto.getId().setTaxYear("2016");

        List<Step3FilerDataDto> list = new ArrayList<>();
        list.add(step3FilerDataDto1);

        Mockito.when(step3RecordValidationService.getRecordsByUID(step3FilerDataDto.getSourceCd(), Long.valueOf(step3FilerDataDto.getResponsiblePersonUniqueId()), step3FilerDataDto.getTaxYear())).thenReturn(list);
        ReflectionTestUtils.setField(step3ItemReader, "step3RecordValidationService", step3RecordValidationService);
        step3ItemReader.updateResposiblePersonDetails(step3FilerDataDto);

        Assert.assertEquals(1, step3FilerDataDto.getResposiblePersonList().size());
    }

    @Test
    public void updateCoveredPersonDetails()
    {
        Step3FilerDataDto step3FilerDataDto = new Step3FilerDataDto();
        step3FilerDataDto.setId(new FilerDemographicPKDto());
        step3FilerDataDto.getId().setSourceCd("TXCCCHHH");
        step3FilerDataDto.getId().setSourceUniqueId("123456789");
        step3FilerDataDto.setResponsiblePersonUniqueId("123456789");
        step3FilerDataDto.getId().setTaxYear("2016");
        step3FilerDataDto.setCorrection("U");

        Step3FilerDataDto step3FilerDataDto1 = new Step3FilerDataDto();
        step3FilerDataDto1.setId(new FilerDemographicPKDto());
        step3FilerDataDto1.getId().setSourceCd("TXCCCHHH");
        step3FilerDataDto1.getId().setSourceUniqueId("12345222");
        step3FilerDataDto1.getId().setTaxYear("2016");
        step3FilerDataDto1.setFilerStatus("C");
        List<Step3FilerDataDto> list = new ArrayList<>();
        list.add(step3FilerDataDto1);

        Mockito.when(step3RecordValidationService.getCoveredPersonListbyUID(step3FilerDataDto.getSourceCd(),Long.valueOf(step3FilerDataDto.getResponsiblePersonUniqueId()),step3FilerDataDto.getTaxYear())).thenReturn(list);
        ReflectionTestUtils.setField(step3ItemReader, "step3RecordValidationService", step3RecordValidationService);
        step3ItemReader.updateCoveredPersonDetails(step3FilerDataDto);

        Assert.assertEquals(1, step3FilerDataDto.getCoveredPersonList().size());
    }

    @Test
    public void updateCoveredPersonDetailsWithO()
    {
        Step3FilerDataDto step3FilerDataDto = new Step3FilerDataDto();
        step3FilerDataDto.setId(new FilerDemographicPKDto());
        step3FilerDataDto.getId().setSourceCd("TXCCCHHH");
        step3FilerDataDto.getId().setSourceUniqueId("123456789");
        step3FilerDataDto.setResponsiblePersonUniqueId("123456789");
        step3FilerDataDto.getId().setTaxYear("2016");
        step3FilerDataDto.setCorrection("O");

        Step3FilerDataDto step3FilerDataDto1 = new Step3FilerDataDto();
        step3FilerDataDto1.setId(new FilerDemographicPKDto());
        step3FilerDataDto1.getId().setSourceCd("TXCCCHHH");
        step3FilerDataDto1.getId().setSourceUniqueId("12345222");
        step3FilerDataDto1.getId().setTaxYear("2016");
        step3FilerDataDto1.setFilerStatus("C");
        List<Step3FilerDataDto> list = new ArrayList<>();
        list.add(step3FilerDataDto1);

        Mockito.when(step3RecordValidationService.getRecordsByUID(step3FilerDataDto.getSourceCd(),Long.valueOf(step3FilerDataDto.getResponsiblePersonUniqueId()),step3FilerDataDto.getTaxYear())).thenReturn(list);
        ReflectionTestUtils.setField(step3ItemReader, "step3RecordValidationService", step3RecordValidationService);
        step3ItemReader.updateCoveredPersonDetails(step3FilerDataDto);

        Assert.assertEquals(0, step3FilerDataDto.getCoveredPersonList().size());
    }

    @Test
    public void updateCoveredPersonDetailsNoCovered()
    {
        Step3FilerDataDto step3FilerDataDto = new Step3FilerDataDto();
        step3FilerDataDto.setId(new FilerDemographicPKDto());
        step3FilerDataDto.getId().setSourceCd("TXCCCHHH");
        step3FilerDataDto.getId().setSourceUniqueId("123456789");
        step3FilerDataDto.setResponsiblePersonUniqueId("123456789");
        step3FilerDataDto.getId().setTaxYear("2016");
        step3FilerDataDto.setCorrection("O");

        Step3FilerDataDto step3FilerDataDto1 = new Step3FilerDataDto();
        step3FilerDataDto1.setId(new FilerDemographicPKDto());
        step3FilerDataDto1.getId().setSourceCd("TXCCCHHH");
        step3FilerDataDto1.getId().setSourceUniqueId("12345222");
        step3FilerDataDto1.getId().setTaxYear("2016");
        step3FilerDataDto1.setFilerStatus("R");
        List<Step3FilerDataDto> list = new ArrayList<>();
        list.add(step3FilerDataDto1);

        Mockito.when(step3RecordValidationService.getRecordsByUID(step3FilerDataDto.getSourceCd(),Long.valueOf(step3FilerDataDto.getResponsiblePersonUniqueId()),step3FilerDataDto.getTaxYear())).thenReturn(list);
        ReflectionTestUtils.setField(step3ItemReader, "step3RecordValidationService", step3RecordValidationService);
        step3ItemReader.updateCoveredPersonDetails(step3FilerDataDto);

        Assert.assertEquals(0, step3FilerDataDto.getCoveredPersonList().size());
    }


    @Test
    public void updateItemWithDataTest()
    {
        Step3FilerDataDto item =new Step3FilerDataDto();
        item.setId(new FilerDemographicPKDto());
        item.setRecipientSsn("123456789");
        item.setRecipientTin("123456789");
        item.getId().setTaxYear("2016");
        List<Step3FilerDataDto> iidList = new ArrayList<>();
        Step3FilerDataDto filerDataDto = new Step3FilerDataDto();
        filerDataDto.setId(new FilerDemographicPKDto());
        filerDataDto.getId().setSourceCd("TXCCCHHH");

        List<Step3FilerDataDto> ssnList = new ArrayList<>();
        Step3FilerDataDto ssnRecord = new Step3FilerDataDto();
        ssnList.add(ssnRecord);

        List<Step3FilerDataDto> tinList = new ArrayList<>();
        Step3FilerDataDto tinRecord = new Step3FilerDataDto();
        tinList.add(tinRecord);



        Mockito.when(step3RecordValidationService.getRecordsBySSN("123456789", item.getId().getTaxYear(), filerDataDto.getSourceCd())).thenReturn(ssnList);
        Mockito.when(step3RecordValidationService.getRecordsByTIN("123456789", item.getId().getTaxYear(), filerDataDto.getSourceCd())).thenReturn(tinList);
        ReflectionTestUtils.setField(step3ItemReader, "step3RecordValidationService", step3RecordValidationService);
        step3ItemReader.updateItemWithData(item, iidList, filerDataDto);
        Assert.assertEquals(2, iidList.size());
    }
}
