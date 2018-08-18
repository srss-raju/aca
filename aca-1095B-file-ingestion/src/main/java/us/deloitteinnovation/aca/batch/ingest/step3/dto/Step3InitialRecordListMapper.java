package us.deloitteinnovation.aca.batch.ingest.step3.dto;

import org.springframework.jdbc.core.RowMapper;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tthakore on 9/6/2016.
 */




public class Step3InitialRecordListMapper implements RowMapper<Step3FilerDataDto> {

    Step3RecordsUIDValidationResultMap step3RecordsUIDValidationResultMap;



    public Step3InitialRecordListMapper( Step3RecordsUIDValidationResultMap map) {
        step3RecordsUIDValidationResultMap = map;
    }

    @Override
    public Step3FilerDataDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Step3FilerDataDto filer = new Step3FilerDataDto();
        filer.setId(new FilerDemographicPKDto());

        filer.getId().setSourceCd(rs.getString("SOURCE_CD"));
        filer.getId().setSourceUniqueId(String.valueOf(rs.getLong("SOURCE_UNIQUE_ID")));
        filer.getId().setTaxYear(rs.getString("TAX_YEAR"));

        // we have created another map to save validation status because we do not need all other properties of Step3FilerDataDto to just maintain status of validation
        Step3ValidationMapDto step3ValidationMapDto = new Step3ValidationMapDto();
        step3ValidationMapDto.setUid(filer.getUIDValue());
        step3ValidationMapDto.setValidationStatus("NA");

        if(!step3RecordsUIDValidationResultMap.getIntialRecordsMap().containsKey(filer.getUIDValue()))
        {
            step3RecordsUIDValidationResultMap.getIntialRecordsMap().put(filer.getUIDValue(),filer);
            step3RecordsUIDValidationResultMap.getMapDtoMap().put(filer.getUIDValue(),step3ValidationMapDto);
        }

        return filer;
    }
}
