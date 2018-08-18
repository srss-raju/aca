package us.deloitteinnovation.aca.web.opsportal.dto;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sdalavi on 3/31/2016.
 */
@Component
public class StateMapper implements RowMapper<StateDto> {

    @Override
    public StateDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        StateDto stateDto = new StateDto();
        stateDto.setStateCode(rs.getString("STATE_ABBREVIATION"));
        stateDto.setStateName(rs.getString("STATE_NAME"));
        return stateDto;
    }

}
