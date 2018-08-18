package us.deloitteinnovation.aca.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps a result set to a Filer, including the "countcovered" amount.
 * @see FilerMapper
 */
@Component
public class FilerWithExportCountCoveredMapper implements RowMapper<Filer> {

    @Autowired
    FilerExportMapper filerExportMapper;

    @Override
    public Filer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Filer filer = filerExportMapper.mapRow(rs, rowNum);
        filer.setCoveredPersonSize(rs.getInt("countcovered"));
        return filer;
    }
}
