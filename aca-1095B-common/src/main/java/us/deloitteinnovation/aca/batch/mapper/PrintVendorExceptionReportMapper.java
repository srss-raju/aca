package us.deloitteinnovation.aca.batch.mapper;

import org.springframework.jdbc.core.RowMapper;
import us.deloitteinnovation.aca.batch.constants.BatchConstants;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by rgopalani on 11/3/2015.
 */
public class PrintVendorExceptionReportMapper implements RowMapper {
    @Override
    public PrintVendorExceptionReportDto mapRow(ResultSet resultSet, int i) throws SQLException {
    	PrintVendorExceptionReportDto exceptionReportDto = new PrintVendorExceptionReportDto();
        exceptionReportDto.setExceptionReportId(resultSet.getInt(BatchConstants.EXCEPTION_REPORT_ID));
        BatchInfoDto batchInfoDto = new BatchInfoDto();
        batchInfoDto.setBatchId(resultSet.getInt(BatchConstants.BATCHID));
        exceptionReportDto.setBatchInfo(batchInfoDto);
        String exDetails = resultSet.getString(BatchConstants.EX_DETAIL);
        String errorDetails = getExDetails(exDetails);
        String SOURCE_UNIQUE_ID = String.valueOf(resultSet.getLong(CommonDataConstants.SOURCE_UNIQUE_ID));
        String whiteSpaceDiffForError = getWhiteSpaceDiff(SOURCE_UNIQUE_ID);
        String whiteSpaceDiffForRowNumber = getWhiteSpaceDiff(String.valueOf(resultSet.getInt(BatchConstants.ROW_NUMBER)));
        exceptionReportDto.setSourceUniqueId(resultSet.getLong(CommonDataConstants.SOURCE_UNIQUE_ID));
        exceptionReportDto.setWhiteSpaceDiffForErrors(whiteSpaceDiffForError);
        exceptionReportDto.setWhiteSpaceDiffForRowNumber(whiteSpaceDiffForRowNumber);
        exceptionReportDto.setExDetails(errorDetails);
        exceptionReportDto.setRowNumber(resultSet.getInt(BatchConstants.ROW_NUMBER));
        return exceptionReportDto;
    }

    /**
     * @param SOURCE_UNIQUE_ID
     * @return
     */
    private String getWhiteSpaceDiff(String SOURCE_UNIQUE_ID) {
        char[] bytes = new char[BatchConstants.WHITE_SPACE_DIFF_LENGTH - SOURCE_UNIQUE_ID.length()];
        Arrays.fill(bytes, BatchConstants.EMPTY_CHAR);
        return new String(bytes);
    }

    /**
     * @param exDetails
     * @return
     */
    private String getExDetails(String exDetails) {
        exDetails = exDetails.replace(BatchConstants.DELIMITED_EX_DETAILS, BatchConstants.WHITE_SPACE);
        exDetails = exDetails.replaceAll(BatchConstants.REMOVE_WHITE_SPACE, BatchConstants.WHITE_SPACE);
        exDetails = exDetails.replaceAll(BatchConstants.NEW_LINE_STR, BatchConstants.COMMA);
        exDetails = exDetails.trim();
        String errorDetails = BatchConstants.EMPTY;
        String[] errors = exDetails.split(BatchConstants.COMMA);
        for (String error : errors) {
            errorDetails = errorDetails + error.trim();
            errorDetails = errorDetails + BatchConstants.NEW_LINE_STR + "                                                                            ";
        }
        return errorDetails;
    }
}