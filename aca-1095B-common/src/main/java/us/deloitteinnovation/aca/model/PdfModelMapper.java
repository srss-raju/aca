package us.deloitteinnovation.aca.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tthakore on 11/28/2015.
 */
public class PdfModelMapper implements RowMapper<PdfModel> {
    @Override
    public PdfModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        PdfModel pdfModel = new PdfModel();
        pdfModel.setSourceCD(rs.getString("SOURCE_CD"));
        pdfModel.setSourceUniqueID(rs.getLong("SOURCE_UNIQUE_ID"));
        pdfModel.setUpdatedDate(rs.getDate("UPDATED_DATE"));
        pdfModel.setMailForm(rs.getString("MAILED_FORM"));
        return pdfModel;
    }
}
