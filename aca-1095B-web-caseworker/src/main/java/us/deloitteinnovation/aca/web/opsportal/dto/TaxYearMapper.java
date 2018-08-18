package us.deloitteinnovation.aca.web.opsportal.dto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yaojia on 1/5/2017.
 */
public class TaxYearMapper implements RowMapper<TaxYearDto> {
    @Override
    public TaxYearDto mapRow(ResultSet rs, int i) throws SQLException {
        TaxYearDto dto = new TaxYearDto();
        dto.setTaxYear(rs.getInt("TAX_YEAR"));
        return dto;
    }
}
