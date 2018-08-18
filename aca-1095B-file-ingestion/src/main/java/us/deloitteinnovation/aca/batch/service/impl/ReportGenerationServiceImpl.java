package us.deloitteinnovation.aca.batch.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.service.ReportGenerationService;
import us.deloitteinnovation.aca.entity.ExceptionReport;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
public class ReportGenerationServiceImpl extends JdbcDaoSupport implements ReportGenerationService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportGenerationServiceImpl.class);
    private static final String ROW_TEMPLATE = "%10s\t\t\t\t\t%16s\t\t\t%s\n";
    private static final String DECORATOR_LITERAL = "\n-------------------------------------------------------------------------------------------------\n";

    @Override
    public void generateMetadataReport(final String generatedReportName,final String originalInputFileName,final int batchId,final String errorMessage) throws Exception {
        LOG.info("Error report generated at location {} ", generatedReportName);
        String formattedError=errorMessage.replace("\n", "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
        final Path path = Paths.get(generatedReportName);
        final String template = errorReportTemplate();
        final String mergedReport = String.format(template, originalInputFileName, batchId, formattedError, 0, 0, 0);
        
        if (LOG.isDebugEnabled()) {
            LOG.debug(DECORATOR_LITERAL);
            LOG.debug("\n{} ", mergedReport);
            LOG.debug(DECORATOR_LITERAL);
        }
        
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(mergedReport);
        }
    }

    public static String errorReportTemplate() {
        final StringBuilder reportContent = new StringBuilder();
        reportContent.append("Filename: %25s\t\t");
        reportContent.append("Batch Id:%s\t\t\t%s\n");
        reportContent.append("\nSummary\n");
        reportContent.append("\t\t\t\t\t Total rows Received : %d\n");
        reportContent.append("\t\t\t\t\t Total rows Correct  : %d\n");
        reportContent.append("\t\t\t\t\t Total rows Rejected : %d\n");
        reportContent.append("\n\nRejected records details\n\n");
        reportContent.append("ROW NUMBER\t\t\t\t\tSOURCE UNIQUE ID\t\t\tEXCEPTION DETAILS\n");
        return reportContent.toString();
    }

    @Override
    public void generateRecordLevelReport(String outputFolder, int batchId) throws Exception {
        final BatchInfoDto batchInfoDto = getBatchInfoDetails(batchId);

        LOG.info("Received BatchInfoDto {} ", batchInfoDto);

        if (batchInfoDto.getBatchId() != batchId || batchInfoDto.getFileName() == null) {
            LOG.info("There are no field level exceptions");
            return;
        }

        final List<ExceptionReport> exceptionList = getExceptionReportList(batchId);
        if (exceptionList.isEmpty()) {
            LOG.info("There are no field level exceptions");
        }
        LOG.debug("Total exceptions  {} ", exceptionList.size());

        final String template = errorReportTemplate();

        final String mergedReport = String.format(template, batchInfoDto.getFileName(), batchId, "", batchInfoDto.getTotalCount(), batchInfoDto.getTotalPass(),
                batchInfoDto.getTotalFail());

        StringBuilder builder = new StringBuilder();
        builder.append(mergedReport);
        for (final ExceptionReport report : exceptionList) {
            builder = builder.append(String.format(ROW_TEMPLATE, report.getRowNumber(), report.getSourceUniqueId(), report.getExDetails()));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(DECORATOR_LITERAL);
            LOG.debug("\n{}\n\n", builder.toString());
            LOG.debug(DECORATOR_LITERAL);
        }
        final Path path = Paths.get(outputFolder + File.separator + batchInfoDto.getFileName().replace(".dat", "_ErrorReport.dat"));

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(builder.toString());
        }

    }

    BatchInfoDto getBatchInfoDetails(int batchId) {
        final String sql = "SELECT BATCH_ID, TOTAL_COUNT, TOTAL_PASS, TOTAL_FAIL, FILENAME FROM BATCH_INFO WHERE BATCH_ID=?";
        return super.getJdbcTemplate().queryForObject(sql, new Integer[]{batchId}, (rs, rowNum) -> {
            final BatchInfoDto batchInfoDto = new BatchInfoDto();
            batchInfoDto.setBatchId(rs.getInt(1));
            batchInfoDto.setTotalCount(rs.getInt(2));
            batchInfoDto.setTotalPass(rs.getInt(3));
            batchInfoDto.setTotalFail(rs.getInt(4));
            batchInfoDto.setFileName(rs.getString(5));
            return batchInfoDto;
        });
    }

    List<ExceptionReport> getExceptionReportList(int batchId) {
        final String sql = "SELECT ROW_NUMBER, EX_DETAILS, SOURCE_UNIQUE_ID FROM EXCEPTION_REPORT WHERE BATCH_ID=? ORDER BY ROW_NUMBER";
        LOG.info("Reading exception report details with batchId {} ", batchId);
        return super.getJdbcTemplate().query(sql, (PreparedStatementSetter) ps -> ps.setInt(1, batchId), new ExceptionReportMapper());
    }

    class ExceptionReportMapper implements RowMapper<ExceptionReport> {
        @Override
        public ExceptionReport mapRow(ResultSet rs, int rowNum) throws SQLException {
            final ExceptionReport exceptionReport = new ExceptionReport();
            exceptionReport.setRowNumber(rs.getInt(1));
            exceptionReport.setExDetails(rs.getString(2));
            exceptionReport.setSourceUniqueId(rs.getLong(3));
            return exceptionReport;
        }
    }
    
 

}
