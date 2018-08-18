package us.deloitteinnovation.aca.batch.export.steppdf;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import us.deloitteinnovation.aca.model.Filer;

/**
 */
@Transactional
public class ExportPdfJdbcRepository extends JdbcDaoSupport {

    public List<Filer> getCoveredPersons(String state, String year, String startDate, String endDate,int offset, int fetchSize) {
        final StringBuilder queryBuilder = new StringBuilder("select * from FILER_DEMOGRAPHICS f1 where TAX_YEAR = '");
        queryBuilder
                .append(year)
                .append("' and SOURCE_CD like '%")
                .append(state)
                .append("%' ")
                .append(" AND f1.RESPONSIBLE_PERSON_UNIQUE_ID in (")
                .append("select DISTINCT RESPONSIBLE_PERSON_UNIQUE_ID from FILER_DEMOGRAPHICS f2 where TAX_YEAR='")
                .append(year)
                .append("' and SOURCE_CD like '%")
                .append(state)
                .append("%' AND UPDATED_DATE BETWEEN '")
                .append(startDate).append(" 00:00:00'")
                .append(" AND '")
                .append(endDate).append(" 23:59:59'")
                .append(" ORDER BY RESPONSIBLE_PERSON_UNIQUE_ID ASC OFFSET ")
                .append(offset)
                .append("  ROWS FETCH NEXT ")
                .append(fetchSize)
                .append(" ROWS ONLY )")
                .append(" ORDER BY RESPONSIBLE_PERSON_UNIQUE_ID ASC, FILER_STATUS DESC");
        return super.getJdbcTemplate().query(queryBuilder.toString(), new ExportPdfFilerMapper());
    }
    
}
