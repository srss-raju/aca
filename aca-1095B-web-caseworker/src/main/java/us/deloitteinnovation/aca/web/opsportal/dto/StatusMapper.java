package us.deloitteinnovation.aca.web.opsportal.dto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sdalavi on 3/31/2016.
 */
public class StatusMapper implements RowMapper<StatusDto> {
    @Override
    public StatusDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        StatusDto statusDto = new StatusDto();
        statusDto.setStatusCd(rs.getString("STATUS_CD"));
        statusDto.setStatusDesc(rs.getString("STATUS_DESC"));
        statusDto.setTypeCd(rs.getString("TYPE_CD"));
        return statusDto;
    }

}