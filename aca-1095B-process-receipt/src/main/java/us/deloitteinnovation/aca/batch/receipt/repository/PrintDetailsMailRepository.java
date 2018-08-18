package us.deloitteinnovation.aca.batch.receipt.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.entity.FilerDemographicPK;
import us.deloitteinnovation.aca.entity.PrintDetail;
import us.deloitteinnovation.aca.repository.PrintVendorUpdateCOMBatchSetter;

@Transactional
public class PrintDetailsMailRepository extends JdbcDaoSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintDetailsMailRepository.class);

    public int[] updatePrintDetailsStatus(final List<PrintDetail> printDetailList, boolean isCOR) {

        if (isCOR) {
        	return updateCORFileStatus(printDetailList);
        } else {
        	return updateCOMFileStatus(printDetailList);
        }

    }

    private Timestamp createSQLDate() {
        final Calendar cal = Calendar.getInstance();
        return new Timestamp(cal.getTimeInMillis());
    }

    public void updatePrintDetailsForEmptyFiles(List<String> emptyFiles) {
        try {
            final String sql = "UPDATE PRINT_DETAILS SET PRINT_STATUS = ?, ACKNOWLEDGE_DATE =? WHERE PRINT_FILE_NAME = ?";
            super.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    LOGGER.info("File name " + emptyFiles.get(i));
                    ps.setString(1, "RESEND");
                    ps.setTimestamp(2, createSQLDate());
                    ps.setString(3, emptyFiles.get(i));
                }

                @Override
                public int getBatchSize() {
                    return emptyFiles.size();
                }
            });
        } catch (final Exception e) {
            LOGGER.error("Error while updating print details for empty files ", e);
        }
    }

    public boolean isPrintXMLExists(String xmlFileName) {

        try {
            final String sql = "SELECT COUNT(*) FROM BATCH_INFO WHERE FILENAME=?";
            final int count = super.getJdbcTemplate().queryForObject(sql, new Object[] { xmlFileName }, Integer.class);
            LOGGER.info("Total {} records exist with file name {} ", count, xmlFileName);
            return count > 0;
        } catch (final Exception e) {
            LOGGER.error("Error while reading  record count ", e);
        }
        return false;
    }

    public int getTotalRecordsCount(String xmlFileName) {

        try {
            final String sql = "SELECT TOTAL_COUNT FROM BATCH_INFO WHERE FILENAME=?";
            return super.getJdbcTemplate().queryForObject(sql, new Object[] { xmlFileName }, Integer.class);
        } catch (final Exception e) {
            LOGGER.error("Error while reading total count ", e);
        }
        return 0;
    }

    public List<FilerDemographic> getRequiredDetailsFromPrintDetails(final String xmlFileName) {
        LOGGER.info("Reading details for the file {} ", xmlFileName);
        final String sql = "SELECT SOURCE_UNIQUE_ID, SOURCE_CD, TAX_YEAR FROM PRINT_DETAILS WHERE PRINT_FILE_NAME=?";

        return super.getJdbcTemplate().query(sql, (PreparedStatementSetter) ps -> ps.setString(1, xmlFileName), new FilerDemographicMapper());

    }

    static class FilerDemographicMapper implements RowMapper<FilerDemographic> {
        @Override
        public FilerDemographic mapRow(ResultSet rs, int rowNum) throws SQLException {

            final FilerDemographic filerDemographic = new FilerDemographic();
            final FilerDemographicPK id = new FilerDemographicPK();
            id.setSourceUniqueId(rs.getLong(1));
            id.setSourceCd(rs.getString(2));
            id.setTaxYear(rs.getInt(3));
            filerDemographic.setId(id);
            filerDemographic.setStatus(null);

            return filerDemographic;
        }
    }
    
    private int[] updateCORFileStatus(final List<PrintDetail> printDetailList){
    	String sql = "UPDATE PRINT_DETAILS SET PRINT_STATUS = ?, ACKNOWLEDGE_DATE =? WHERE SOURCE_UNIQUE_ID = ? AND TAX_YEAR = ? AND SOURCE_CD = ? AND PRINT_FILE_NAME = ?";
    	try {
            super.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    final PrintDetail printDetail = printDetailList.get(i);
                    ps.setString(1, printDetail.getPrintStatus());
                    ps.setTimestamp(2, createSQLDate());
                    ps.setLong(3, printDetail.getId().getSourceUniqueId());
                    ps.setInt(4, Integer.parseInt(printDetail.getId().getTaxYear()));
                    ps.setString(5, printDetail.getId().getSourceCd());
                    ps.setString(6, printDetail.getId().getPrintFileName());

                    LOGGER.info("Updating record with File name {} , sourceUniqueId {} , taxYear {} , sourceCd {} ", printDetail.getId().getPrintFileName(),
                            printDetail.getId().getSourceUniqueId(), printDetail.getId().getTaxYear(), printDetail.getId().getSourceCd());
                }

                @Override
                public int getBatchSize() {
                    return printDetailList.size();
                }
            });
        } catch (final Exception e) {
            LOGGER.error("Error while updating printDetails status ", e);
        }
    	return null;
    }
    
    private int[] updateCOMFileStatus(final List<PrintDetail> printDetailList){
    	String sql = "UPDATE PRINT_DETAILS SET PRINT_STATUS = ?, MAILED_DATE=? WHERE SOURCE_UNIQUE_ID = ? AND TAX_YEAR = ? AND SOURCE_CD = ? AND PRINT_FILE_NAME = ? AND PRINT_STATUS=?";
    	
    	PrintVendorUpdateCOMBatchSetter setter = new PrintVendorUpdateCOMBatchSetter(printDetailList);
        int[] recordsUpdated = super.getJdbcTemplate().batchUpdate(sql, setter);
        return recordsUpdated;
    }
}
