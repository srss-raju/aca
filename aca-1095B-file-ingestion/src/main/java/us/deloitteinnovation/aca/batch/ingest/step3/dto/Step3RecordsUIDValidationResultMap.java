package us.deloitteinnovation.aca.batch.ingest.step3.dto;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tthakore on 9/21/2016.
 */
public class Step3RecordsUIDValidationResultMap {

    public Map<String, Step3ValidationMapDto> getMapDtoMap() {
        return mapDtoMap;
    }

    private Map<String, Step3ValidationMapDto>  mapDtoMap = new HashMap<String, Step3ValidationMapDto>();


    public Map<String, Step3FilerDataDto> getIntialRecordsMap () {
        return intialRecordsMap;
    }

    private Map<String, Step3FilerDataDto>  intialRecordsMap = new LinkedHashMap<>();
}
