package us.deloitteinnovation.aca.batch.export.step1;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import us.deloitteinnovation.aca.model.FilerWithExportCountCoveredMapper;
import us.deloitteinnovation.aca.model.FilerWithExportCountCoveredPrintVendorMapper;

/**
 * Delegate to FilerMapper, as it is the only input data needed in Step 1.
 * @see FilerWithExportCountCoveredMapper
 */
@Component
public class Step1Form1095PrintVendorOriginalsMapper implements RowMapper<Step1Form1095Dto> {

    @Autowired
    FilerWithExportCountCoveredPrintVendorMapper filerWithExportCountCoveredPrintVendorMapper;

    @Override
    public Step1Form1095Dto mapRow(ResultSet resultSet, int i) throws SQLException {
        Step1Form1095Dto dto = new Step1Form1095Dto() ;
        dto.filer = filerWithExportCountCoveredPrintVendorMapper.mapRow(resultSet, i) ;
        return dto ;
    }
}
