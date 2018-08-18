package us.deloitteinnovation.aca.batch.export.step2;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.model.SourceSystemConfigMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Delegates mapping for a SourceSystemConfig.
 */
@Component
public class Step2Form1094Mapper implements RowMapper<Step2Form1094Dto>{

    SourceSystemConfigMapper sourceSystemConfigMapper = new SourceSystemConfigMapper() ;

    @Override
    public Step2Form1094Dto mapRow(ResultSet resultSet, int i) throws SQLException {
        Step2Form1094Dto dto = new Step2Form1094Dto() ;
        dto.sourceSystemConfig = sourceSystemConfigMapper.mapRow(resultSet, i) ;
        return dto;
    }
}
