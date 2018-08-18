package us.deloitteinnovation.aca.batch.dto;

import java.io.Serializable;

public class ExceptionReportDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int exceptionReportId;

    private String exDetails;

    private String whiteSpaceDiffForErrors;

    private String whiteSpaceDiffForRowNumber;

    private int rowNumber;

    private long sourceUniqueId;

    private BatchInfoDto batchInfo;

    public ExceptionReportDto() {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (batchInfo == null ? 0 : batchInfo.hashCode());
        result = prime * result + (exDetails == null ? 0 : exDetails.hashCode());
        result = prime * result + rowNumber;
        result = prime * result + (int) (sourceUniqueId ^ sourceUniqueId >>> 32);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExceptionReportDto other = (ExceptionReportDto) obj;
        if (batchInfo == null) {
            if (other.batchInfo != null) {
                return false;
            }
        } else if (!batchInfo.equals(other.batchInfo)) {
            return false;
        }
        if (exDetails == null) {
            if (other.exDetails != null) {
                return false;
            }
        } else if (!exDetails.equals(other.exDetails)) {
            return false;
        }
        if (rowNumber != other.rowNumber) {
            return false;
        }
        if (sourceUniqueId != other.sourceUniqueId) {
            return false;
        }
        return true;
    }

}
