package us.deloitteinnovation.aca.model;

import org.springframework.jdbc.core.RowMapper;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Kkethireddy on 25-10-2015.
 */
public class FilerCoverageMapper  implements RowMapper<FilerCoverage> {

    @Override
    public FilerCoverage mapRow(ResultSet rs, int rowNum) throws SQLException {
        FilerCoverage filerCoverage = new FilerCoverage();
        filerCoverage.setSourceUniqueId(rs.getString(CommonDataConstants.SOURCE_UNIQUE_ID));
        filerCoverage.setSourceCd(rs.getString(CommonDataConstants.SOURCE_CD));
        filerCoverage.setJan(rs.getString(CommonDataConstants.JAN));
        filerCoverage.setFeb(rs.getString(CommonDataConstants.FEB));
        filerCoverage.setMar(rs.getString(CommonDataConstants.MAR));
        filerCoverage.setApr(rs.getString(CommonDataConstants.APR));
        filerCoverage.setMay(rs.getString(CommonDataConstants.MAY));
        filerCoverage.setJun(rs.getString(CommonDataConstants.JUN));
        filerCoverage.setJul(rs.getString(CommonDataConstants.JUL));
        filerCoverage.setAug(rs.getString(CommonDataConstants.AUG));
        filerCoverage.setSep(rs.getString(CommonDataConstants.SEP));
        filerCoverage.setOct(rs.getString(CommonDataConstants.OCT));
        filerCoverage.setNov(rs.getString(CommonDataConstants.NOV));
        filerCoverage.setDec(rs.getString(CommonDataConstants.DEC));
        filerCoverage.setComments(rs.getString(CommonDataConstants.COMMENTS));
        filerCoverage.setUpdatedBy(rs.getString(CommonDataConstants.UPDATED_BY));
        return filerCoverage;
    }
}
