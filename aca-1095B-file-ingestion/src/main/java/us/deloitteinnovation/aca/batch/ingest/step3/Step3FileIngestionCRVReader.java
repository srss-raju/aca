package us.deloitteinnovation.aca.batch.ingest.step3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3FilerDataDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.Step3InitialFilerList;
import us.deloitteinnovation.aca.batch.ingest.step3.listeners.Step3FIExecutionListener;
import us.deloitteinnovation.aca.batch.ingest.step3.services.Step3RecordValidationService;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.util.*;

/**
 * <p>
 *     Step3FileIngestionCRVReader is created to read records provided by Step3InitialFilerList which contains unique list od uid's(sourcecd_sourceuiniqueid_taxyear)
 *     in file. Purpose :-
 *  <li>
 *     Step1 :- Read all uid one by one available in file.
 *     Step2 :- get list of all matching uid's from filer_demographics_staging table (DB and File)
 *     Step3 :- Loop throught each record and get matching records with ssn/tin.
 *     Step4 :- fill responsible persons and covered persons list in record.
 *     Step4 :- Create unique list of all the records with same uid but different line number and db
 *     Step5  :- Add file and Db lists to container object which we received in read function. i.e nextUID here and pass object to processor to process.
 *  </li>
 *
 *  @see Step3FileIngestionCRVProcessor
 *  @see Step3FileIngestionCRVWriter
 *  @see us.deloitteinnovation.aca.batch.ingest.step3.listeners.Step3FIExecutionListener
 * </p>
 */
@Component
public class Step3FileIngestionCRVReader extends ListItemReader<Step3FilerDataDto>  {


    private final static Logger LOGGER = LoggerFactory.getLogger(Step3FileIngestionCRVReader.class);

    @Autowired
    Step3RecordValidationService step3RecordValidationService;


    public  Step3FileIngestionCRVReader(Step3InitialFilerList list) {
        super(list.getStep3InitialFilerList());
    }

    @Override
    public Step3FilerDataDto read() {
        Step3FilerDataDto nextUID = null;
        try{
            nextUID = super.read();
            Step3FilerDataDto filerDataDto = nextUID;
            if(nextUID != null)
                createUIDListByRowNumber(filerDataDto);
        }
        catch (Exception e)
        {
            LOGGER.error("error in Step3FileIngestionCRVReader read ",e);
            Step3DataValidationException step3DataValidationException = new Step3DataValidationException();
            step3DataValidationException.setErrorMessage(e.getMessage());
            throw step3DataValidationException;

        }


        LOGGER.info("reading record with UID " + ((nextUID != null) ? nextUID.getUIDValue() : "N/A"));
        return ((nextUID != null)?nextUID:null);
    }


    /**
     * this function will create list of UID's(source cd and source unique id)  and IID(ssn/TIN dob and tax year) for each record.
     ***/
    protected void createUIDListByRowNumber(Step3FilerDataDto filerDataDto) throws Step3DataValidationException {

        List<Step3FilerDataDto> iidList = new ArrayList<>();
        List<Step3FilerDataDto> uidList = step3RecordValidationService.getRecordsByUID(filerDataDto.getSourceCd(), Long.valueOf(filerDataDto.getSourceUniqueId()), filerDataDto.getId().getTaxYear());

        try {
            for (Step3FilerDataDto item : uidList) {
                updateItemWithData(item, iidList,filerDataDto);
            }

            /* get the list of people with same responsible person unique id as  source unique id of current record it can be filer_status=C  or FILER_STATUS as O*/
            Map<String, Step3FilerDataDto> recordMapByRowNo = new LinkedHashMap<>();// we are using linked hash map here to maintain the order of the records by which they are getting inserted.
            List<Step3FilerDataDto> fdRecordList = new ArrayList<>();

            Iterator uidIterator = uidList.iterator();
            Iterator iidIterator = iidList.iterator();

            arrangeFilersByIds(uidIterator, recordMapByRowNo, fdRecordList);
            arrangeFilersByIds(iidIterator, recordMapByRowNo, fdRecordList);

            filerDataDto.setFilersWithSameIdsInFile(new ArrayList<Step3FilerDataDto>(recordMapByRowNo.values()));
            filerDataDto.setFilersWithSameIdsInDB(fdRecordList);
        } catch (Exception e) {
            LOGGER.error("Error in reading file so this file will be skipped.----->", e) ;
            Step3DataValidationException step3DataValidationException = new Step3DataValidationException();
            step3DataValidationException.setParent(uidList);
            step3DataValidationException.setErrorMessage("Error in reading record for given source unique id hence all the records with given source unique id will be skipped : " + filerDataDto.getSourceUniqueId());
            throw step3DataValidationException;
        }
    }

    /**
     * arrangeFilersByIds()
     * arranges all the records received on basis of UID and IID into two collections
     * 1. From File
     * 2. From DB and passes  it to parent record for business rule validation.
     * used map to filter same records from IID list and UIL list by row number , means if record exists in
     **/
    protected void arrangeFilersByIds(Iterator iterator, Map recordMapByRowNo, List<Step3FilerDataDto> fdRecordList){
        while (iterator.hasNext()) {
            Step3FilerDataDto element = (Step3FilerDataDto) iterator.next();
            if (!"0".equals(element.getRowNumber()) && !recordMapByRowNo.containsKey(element.getRowNumber())) {
                recordMapByRowNo.put(element.getRowNumber(), element);
                iterator.remove();
            } else if (recordMapByRowNo.containsKey(element.getRowNumber())) {
                iterator.remove();
            } else if ("0".equals(element.getRowNumber())) {
                updateRecordList(fdRecordList, element);
            }
        }
    }


    /**
     * create a unique record list fdRecordList for DB.
     * Before adding any element into it
     * we are looping through it to make sure that records are not getting repeted for DB since DB should have only one id for ine record.
     * @param fdRecordList unique record list to update
     * @param element    element to check from db wether it exists inlist or not if not we will add it.
     * **/
    protected void updateRecordList(List<Step3FilerDataDto> fdRecordList, Step3FilerDataDto element)
    {
        if (!fdRecordList.isEmpty()) {
            Iterator<Step3FilerDataDto> fdRecordIterator = fdRecordList.iterator();
            List<Step3FilerDataDto> tempObjectContainer = new ArrayList<>();
            Boolean isItemInFD = false;
            while (fdRecordIterator.hasNext() ) {
                Step3FilerDataDto item = fdRecordIterator.next();
                if (item.getUIDValue().equals(element.getUIDValue()))
                     isItemInFD = true;
            }

            if(!isItemInFD)
                fdRecordList.add(element);
        } else {
            fdRecordList.add(element);
        }
    }

    /**
     * fill in list of responsible person from file or db into responsible person list.
     * makes a service call and gets all records matchinf with responsible person sourceunie id's
     *
     * **/
    public void updateResposiblePersonDetails(Step3FilerDataDto step3FilerDataDto) {

        List<Step3FilerDataDto> step3FilerDataDtos = step3RecordValidationService.getRecordsByUID(step3FilerDataDto.getSourceCd(), Long.valueOf(step3FilerDataDto.getResponsiblePersonUniqueId()), step3FilerDataDto.getId().getTaxYear());
        if (step3FilerDataDtos != null)
        {
            step3FilerDataDto.getResposiblePersonList().addAll(step3FilerDataDtos);
        }
    }


    /**
     * Purpose of below function it to fill value for covered person list. but we need covered person list only in  case of update or correction to either maintain version of record or
     * check for active inactive logic so avoiding original correction code.
     * **/
    protected void updateCoveredPersonDetails(Step3FilerDataDto step3FilerDataDto) {
        if("U".equals(step3FilerDataDto.getCorrection()) || "C".equals(step3FilerDataDto.getCorrection()))
        {
            List<Step3FilerDataDto> step3FilerDataDtos = step3RecordValidationService.getCoveredPersonListbyUID(step3FilerDataDto.getSourceCd(), Long.valueOf(step3FilerDataDto.getResponsiblePersonUniqueId()), step3FilerDataDto.getId().getTaxYear());
            if (step3FilerDataDtos != null)
            {
                for(Step3FilerDataDto coveredPerson : step3FilerDataDtos)
                {
                    if("C".equals(coveredPerson.getFilerStatus()))
                        step3FilerDataDto.getCoveredPersonList().add(coveredPerson);
                }

            }
        }
    }

    /**
     * This function will fill up data into iid list on the basis of uid list so that we can create a unique list of iid's and uid's.
     * also it makes service call to fill responsible person list and covered person list.
     * **/
    protected void updateItemWithData(Step3FilerDataDto item,  List<Step3FilerDataDto> iidList, Step3FilerDataDto filerDataDto)
    {
        String ssnValue = (item.getRecipientSsn() != null) ? item.getRecipientSsn() : "NA";
        if (ssnValue != "NA" && ssnValue.length() > 0) {
            iidList.addAll(step3RecordValidationService.getRecordsBySSN(ssnValue, item.getId().getTaxYear(), filerDataDto.getSourceCd()));
        }

        String tinValue = (item.getRecipientTin() != null) ? item.getRecipientTin() : "NA";
        if (tinValue != "NA" && tinValue.length() > 0) {
            iidList.addAll(step3RecordValidationService.getRecordsByTIN(tinValue, item.getId().getTaxYear(), filerDataDto.getSourceCd()));
        }

        if ("C".equals(item.getFilerStatus()) && "FILE".equals(item.getRecordSource())) {
            updateResposiblePersonDetails(item);
            updateSimilarCoveredPersonList(item);
        }

       if ("R".equals(item.getFilerStatus())
                        && "FILE".equals(item.getRecordSource())) {
            updateCoveredPersonDetails(item);
        }
    }

    /** In case of covered person there can be other records also which has the same responsible person we
     * need their list to update their version info..**/
    public void updateSimilarCoveredPersonList(Step3FilerDataDto item)
    {
        List<Step3FilerDataDto> step3FilerDataDtos = step3RecordValidationService.getCoveredPersonListbyUID(item.getSourceCd(), Long.valueOf(item.getResponsiblePersonUniqueId()), item.getId().getTaxYear());
        if (step3FilerDataDtos != null)
        {
            for(Step3FilerDataDto coveredPerson : step3FilerDataDtos)
            {
                if("C".equals(coveredPerson.getFilerStatus()) && CommonDataConstants.RECORD_VALIDATION_TYPE_DB.equals(coveredPerson.getRecordSource()))
                    item.getCoveredPersonListSharingSameResponsible().add(coveredPerson);
            }
        }
    }

}