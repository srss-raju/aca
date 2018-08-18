package us.deloitteinnovation.aca.exception;

import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;

import java.io.Serializable;

public class PrintVendorExceptionReportDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int exceptionReportId;

    private String exDetails;

    private String whiteSpaceDiffForErrors;

    private String whiteSpaceDiffForRowNumber;

    private int rowNumber;


    private long sourceUniqueId;


    private BatchInfoDto batchInfo;

    public PrintVendorExceptionReportDto() {
    }

    public int getExceptionReportId() {
        return this.exceptionReportId;
    }

    public void setExceptionReportId(int exceptionReportId) {
        this.exceptionReportId = exceptionReportId;
    }

    public String getExDetails() {
        return this.exDetails;
    }

    public void setExDetails(String exDetails) {
        this.exDetails = exDetails;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public long getSourceUniqueId() {
        return this.sourceUniqueId;
    }

    public void setSourceUniqueId(long sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public BatchInfoDto getBatchInfo() {
        return batchInfo;
    }

    public void setBatchInfo(BatchInfoDto batchInfo) {
        this.batchInfo = batchInfo;
    }

    public String getWhiteSpaceDiffForErrors() {
        return whiteSpaceDiffForErrors;
    }

    public void setWhiteSpaceDiffForErrors(String whiteSpaceDiffForErrors) {
        this.whiteSpaceDiffForErrors = whiteSpaceDiffForErrors;
    }

    public String getWhiteSpaceDiffForRowNumber() {
        return whiteSpaceDiffForRowNumber;
    }

    public void setWhiteSpaceDiffForRowNumber(String whiteSpaceDiffForRowNumber) {
        this.whiteSpaceDiffForRowNumber = whiteSpaceDiffForRowNumber;
    }

}
