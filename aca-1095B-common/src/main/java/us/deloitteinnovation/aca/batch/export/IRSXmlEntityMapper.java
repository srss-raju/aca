package us.deloitteinnovation.aca.batch.export;

import org.springframework.jdbc.core.RowMapper;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.entity.Irs1095XML;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by bhchaganti on 5/23/2016.
 */
public class IRSXmlEntityMapper  implements RowMapper<Irs1095XML>{

    @Override
    public Irs1095XML mapRow(ResultSet rs, int rowNum) throws SQLException {

        Irs1095XML irs1095XML = new Irs1095XML();

        irs1095XML.setSourceUniqueId(rs.getLong(CommonDataConstants.SOURCE_UNIQUE_ID));
        irs1095XML.setSourceCd(rs.getString(CommonDataConstants.SOURCE_CD));
        irs1095XML.setTaxYear(rs.getInt("TAX_YEAR"));
        irs1095XML.setIrs1095BXml(rs.getString("IRS_1095B_XML"));
        irs1095XML.setFilerDemoSeq(rs.getLong("FILER_DEMO_SEQ"));

        return irs1095XML;
    }
}


