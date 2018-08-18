package us.deloitteinnovation.aca.batch.dataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.model.PdfForm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Persistence methods for {@link us.deloitteinnovation.aca.entity.PrintDetail} objects.
 */
@Service("printDetailsUpdateDS")
@Transactional
public class PrintDetailsUpdateDS {
    private static final Logger logger = LoggerFactory.getLogger(PrintDetailsUpdateDS.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PrintDetailsUpdateDS(){}

    public PrintDetailsUpdateDS(JdbcTemplate jdbcTemplate){
        jdbcTemplate = this.jdbcTemplate;
    }

    /**
     *
     * @param pdfForm
     */
    @Transactional
    public void insertOrUpdatePrintDetails(PdfForm pdfForm){
        if (logger.isDebugEnabled()) {
           logger.debug("insertOrUpdatePrintDetails by PdfForm source cd {} source id {}", pdfForm.getSourceCd(), pdfForm.getId()) ;
        }
        // If there was an error in generating the PDF for the filer, do not store a filename.
        String filenameToPersist = (pdfForm.isExceptionFound() ? null : pdfForm.getPrintFileName() ) ;
        this.insertOrUpdatePrintDetails(pdfForm.getPrintStatus(), pdfForm.getPrintLastMailReqDate(),
                pdfForm.getUpdatedBy(), pdfForm.getPrintUpdatedDate(), filenameToPersist, pdfForm.getId(), pdfForm.getSourceCd()) ;
    }

    /**
     *
     * @param filer
     * @param filename  Filename of the PDF to which the form was last written.
     */
    @Transactional
    public void insertOrUpdatePrintDetails(Filer filer, String filename) {
        if (logger.isDebugEnabled()) {
            logger.debug("insertOrUpdatePrintDetails by Filer source cd {} source id {}", filer.getSourceCd(), filer.getSourceUniqueId()) ;
        }
        // If there was an error in generating the PDF for the filer, do not store a filename.
        String filenameToPersist = (filer.isExceptionFound() ? null : filename ) ;
        Date currentDate = new Date();
       // this.insertOrUpdatePrintDetails(filer.getPrintStatus(), filer.getPRfiler.getUpdatedBy(), currentDate, filenameToPersist, filer.getSourceUniqueId(), filer.getSourceCd()) ;
    }

    /**
      * Helper method to update the PRINT_DETAILS table by source code and unique id.
      * @param printStatus
      * @param updatedBy
      * @param filename  Filename of the PDF to which the form was last written.  Use NULL if there was an error writing file.
      * @param sourceUniqueId
      * @param sourceCode
      * @return Number of rows updated.
      */
    protected int insertOrUpdatePrintDetails(final String printStatus, final Date lastMailRequestedDate,
                                             final String updatedBy, final Date updatedDate, final String filename, final Long sourceUniqueId, final String sourceCode) {
        StringBuilder updateSql = new StringBuilder("update PRINT_DETAILS set PRINT_STATUS = ?, UPDATED_BY = ?, UPDATED_DATE = ?, PRINT_FILE_NAME = ? ");
        if(lastMailRequestedDate != null) {
            updateSql.append(", LAST_MAIL_REQUESTED_DATE = ? ");
        }
        updateSql.append("where SOURCE_UNIQUE_ID=? and SOURCE_CD=?");
        int rows = this.jdbcTemplate.update(updateSql.toString(),new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, printStatus);
                ps.setString(2, updatedBy);
                ps.setTimestamp(3, new Timestamp(updatedDate.getTime()));
                ps.setString(4, filename);
                if(lastMailRequestedDate != null) {
                    ps.setTimestamp(5, new Timestamp(lastMailRequestedDate.getTime()));
                    ps.setLong(6, sourceUniqueId);
                    ps.setString(7,	sourceCode);
                } else {
                    ps.setLong(5, sourceUniqueId);
                    ps.setString(6,	sourceCode);
                }
            }
        });

        // If nothing was updated, then the row doesn't exist.  So insert it.
        if (rows == 0) {
            StringBuilder createSql = new StringBuilder("insert into PRINT_DETAILS(SOURCE_UNIQUE_ID, SOURCE_CD, PRINT_STATUS, UPDATED_BY, UPDATED_DATE, PRINT_FILE_NAME");
            StringBuilder valueString = new StringBuilder(" values (?, ?, ?, ?, ?, ?");
            Object[] values = null;
            if(lastMailRequestedDate != null) {
                createSql.append(", LAST_MAIL_REQUESTED_DATE");
                valueString.append(", ?");
                values = new Object[]{sourceUniqueId, sourceCode, printStatus, updatedBy, updatedDate, filename, lastMailRequestedDate};
            } else {
                values = new Object[]{sourceUniqueId, sourceCode, printStatus, updatedBy, updatedDate, filename};
            }
            createSql.append(") ");
            valueString.append(") ");
            rows = jdbcTemplate.update(createSql.append(valueString).toString(), values);
        }

        return rows ;
    }

    public Integer getRecordCount(String stateName, Integer range){
        Integer count = jdbcTemplate.queryForObject("select count(fd.SOURCE_CD) from FILER_DEMOGRAPHICS fd, PRINT_DETAILS pd  where SUBSTRING(fd.SOURCE_CD,0,3) = ? and pd.PRINT_STATUS = ? and pd.SOURCE_UNIQUE_ID=fd.SOURCE_UNIQUE_ID and pd.SOURCE_CD=fd.SOURCE_CD",
                    new Object[]{stateName, CommonDataConstants.PrintStatus.READY_TO_MAIL}, Integer.class);
        return count/range + ((count % range == 0) ? 0 : 1);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
