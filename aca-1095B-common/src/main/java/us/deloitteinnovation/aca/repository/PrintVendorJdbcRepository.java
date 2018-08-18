package us.deloitteinnovation.aca.repository;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.entity.PrintDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 */
@Transactional
public class PrintVendorJdbcRepository extends JdbcDaoSupport {

    public void insertPrintDetails(final List<PrintDetail> printDetailList) {

        final String sql = "INSERT INTO [dbo].[PRINT_DETAILS]([SOURCE_UNIQUE_ID] ,[SOURCE_CD],[LAST_MAIL_REQUESTED_DATE],[BATCH_ID],[PRINT_REASON],[PRINT_STATUS],[TAX_YEAR],[PRINT_FILE_NAME],[UPDATED_BY],[CREATED_DATE],[ORIGINAL_FORM_STATUS],[CORRECTION_INDICATOR]) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        super.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                final PrintDetail printDetailsObject = printDetailList.get(i);
                ps.setLong(1, printDetailsObject.getId().getSourceUniqueId());
                ps.setString(2, printDetailsObject.getId().getSourceCd());
                ps.setTimestamp(3, createSQLDate());
                ps.setInt(4, printDetailsObject.getBatchId());
                ps.setString(5, printDetailsObject.getPrintReason());
                ps.setString(6, printDetailsObject.getPrintStatus());
                ps.setInt(7, Integer.parseInt(printDetailsObject.getId().getTaxYear()));
                ps.setString(8, printDetailsObject.getId().getPrintFileName());
                ps.setString(9, printDetailsObject.getUpdatedBy());
                ps.setTimestamp(10, createSQLDate());
                ps.setString(11, printDetailsObject.getOriginalFormStatus());
                ps.setInt(12, printDetailsObject.getCorrectionIndicator());

            }

            @Override
            public int getBatchSize() {
                return printDetailList.size();
            }
        });
    }

    private Timestamp createSQLDate() {
        final Calendar cal = Calendar.getInstance();
        final java.sql.Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
        return timestamp;
    }

    public Long getFilerDemographicrecordCount(String state, String year) {
        final StringBuilder queryBuilder = new StringBuilder("select count(*) from FILER_DEMOGRAPHICS where FILER_DEMOGRAPHICS.TAX_YEAR = ");
        queryBuilder
                .append(year)
                .append(" and FILER_DEMOGRAPHICS.SOURCE_CD like '")
                .append(state)
                .append("%'  and FILER_DEMOGRAPHICS.FILER_STATUS IN ('N','R') and (FILER_DEMOGRAPHICS.FORM_STATUS IS NULL) and FILER_DEMOGRAPHICS.STATUS='ACTIVE'");

        return super.getJdbcTemplate().queryForObject(queryBuilder.toString(), Long.class);
    }

    public Long getFilerDemographicrecordCount(String state, String year, String mailStatus, String frequency) {
        final StringBuilder queryBuilder = new StringBuilder("select count(*) from FILER_DEMOGRAPHICS where FILER_DEMOGRAPHICS.TAX_YEAR = ");
        queryBuilder.append(year).append(" and FILER_DEMOGRAPHICS.MAILED_FORM = '").append(mailStatus).append("' and FILER_DEMOGRAPHICS.SOURCE_CD like '")
                .append(state).append("%'  and FILER_DEMOGRAPHICS.FILER_STATUS In ('N','R') and FILER_DEMOGRAPHICS.STATUS='ACTIVE'");
        if ("D".equalsIgnoreCase(frequency)) {
            queryBuilder.append(" and (FILER_DEMOGRAPHICS.FORM_STATUS IS NULL OR FILER_DEMOGRAPHICS.FORM_STATUS='REGENERATE' )");
        } else {
            queryBuilder.append(" and (FILER_DEMOGRAPHICS.FORM_STATUS IS NULL)");
        }
        return super.getJdbcTemplate().queryForObject(queryBuilder.toString(), Long.class);
    }

    public int[] updateFilerDemographicStatus(final List<FilerDemographic> filerDemographicList) {
        final String sql = "UPDATE FILER_DEMOGRAPHICS SET FORM_STATUS = ?, UPDATED_DATE=? WHERE SOURCE_UNIQUE_ID = ? AND SOURCE_CD = ? AND TAX_YEAR = ? AND FILER_STATUS='R'";
        
        PrintVendorUpdateBatchSetter setter = new PrintVendorUpdateBatchSetter(filerDemographicList);
        int[] recordsUpdated = super.getJdbcTemplate().batchUpdate(sql, setter);
        return recordsUpdated;
    }
}
