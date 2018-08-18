package us.deloitteinnovation.aca.batch.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rgopalani on 10/20/2015.
 */
public class BatchInfoDto implements Serializable {

    private int batchId;

    private String agencyCd;

    private int exceptionReportId;

    private Date receiveDt;

    private String stateCd;

    private String systemCd;

    private int totalCount;

    private int totalFail;

    private int totalPass;

    private String fileVersion;

    private String fileName;

    private String sourceCode;

    private String batchType;
    
    private String requisitionId;

    public BatchInfoDto() {
    }

    public int getBatchId() {
        return this.batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getAgencyCd() {
        return this.agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public int getExceptionReportId() {
        return this.exceptionReportId;
    }

    public void setExceptionReportId(int exceptionReportId) {
        this.exceptionReportId = exceptionReportId;
    }

    public Date getReceiveDt() {
        return this.receiveDt;
    }

    public void setReceiveDt(Date receiveDt) {
        this.receiveDt = receiveDt;
    }

    public String getStateCd() {
        return this.stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public String getSystemCd() {
        return this.systemCd;
    }

    public void setSystemCd(String systemCd) {
        this.systemCd = systemCd;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalFail() {
        return this.totalFail;
    }

    public void setTotalFail(int totalFail) {
        this.totalFail = totalFail;
    }

    public int getTotalPass() {
        return this.totalPass;
    }

    public void setTotalPass(int totalPass) {
        this.totalPass = totalPass;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + batchId;
        result = prime * result + (fileName == null ? 0 : fileName.hashCode());
        result = prime * result + (fileVersion == null ? 0 : fileVersion.hashCode());
        result = prime * result + (stateCd == null ? 0 : stateCd.hashCode());
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
        final BatchInfoDto other = (BatchInfoDto) obj;
        if (batchId != other.batchId) {
            return false;
        }
        if (fileName == null) {
            if (other.fileName != null) {
                return false;
            }
        } else if (!fileName.equals(other.fileName)) {
            return false;
        }
        if (fileVersion == null) {
            if (other.fileVersion != null) {
                return false;
            }
        } else if (!fileVersion.equals(other.fileVersion)) {
            return false;
        }
        if (stateCd == null) {
            if (other.stateCd != null) {
                return false;
            }
        } else if (!stateCd.equals(other.stateCd)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BatchInfoDto [batchId=" + batchId + ", stateCd=" + stateCd + ", systemCd=" + systemCd + ", totalCount=" + totalCount + ", totalFail="
                + totalFail + ", totalPass=" + totalPass + ", fileName=" + fileName + "]";
    }

	public String getRequisitionId() {
		return requisitionId;
	}

	public void setRequisitionId(String requisitionId) {
		this.requisitionId = requisitionId;
	}

}
