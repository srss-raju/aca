package us.deloitteinnovation.aca.batch.mapper;

import org.springframework.jdbc.core.RowMapper;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rgopalani on 10/21/2015.
 */
public class SourceUniqueIdFDMapper implements RowMapper<FilerDemographicDto> {
    @Override
    public FilerDemographicDto mapRow(ResultSet resultSet, int i) throws SQLException {
        FilerDemographicDto filerDemographicDto = new FilerDemographicDto();
        FilerDemographicPKDto filerDemographicPKDto = new FilerDemographicPKDto();
        filerDemographicPKDto.setSourceUniqueId(resultSet.getString(BatchConstants.EX_SOURCE_UNIQUE_ID_NUMBER));
        filerDemographicDto.setId(filerDemographicPKDto);
        return filerDemographicDto;
    }
}
