package us.deloitteinnovation.aca.batch.receipt.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.entity.PrintDetail;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;

public class StepResult {

    private File processedFile;

    public File getProcessedFile() {
        return processedFile;
    }

    public void setProcessedFile(File processedFile) {
        this.processedFile = processedFile;
    }

    private List<PrintDetail> printDetails = new ArrayList<PrintDetail>();

    public List<PrintDetail> getPrintDetails() {
        return printDetails;
    }

    public void setPrintDetails(List<PrintDetail> printDetails) {
        this.printDetails = printDetails;
    }

    List<PrintVendorExceptionReportDto> exceptionReport = new ArrayList<PrintVendorExceptionReportDto>();

    public List<PrintVendorExceptionReportDto> getExceptionReport() {
        return exceptionReport;
    }

    public void setExceptionReport(List<PrintVendorExceptionReportDto> exceptionReport) {
        this.exceptionReport = exceptionReport;
    }

    int totalRecords;
    int failedRecords;
    int successRecords;
    private String requisitionId;

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getFailedRecords() {
        return failedRecords;
    }

    public void setFailedRecords(int failedRecords) {
        this.failedRecords = failedRecords;
    }

    public int getSuccessRecords() {
        return successRecords;
    }

    public void setSuccessRecords(int successRecords) {
        this.successRecords = successRecords;
    }

    private List<FilerDemographic> filerDemographicList = new ArrayList<FilerDemographic>();

    public List<FilerDemographic> getFilerDemographicList() {
        return filerDemographicList;
    }

    public void setFilerDemographicList(List<FilerDemographic> filerDemographicList) {
        this.filerDemographicList = filerDemographicList;
    }

    private boolean isValid;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

	public String getRequisitionId() {
		return requisitionId;
	}

	public void setRequisitionId(String requisitionId) {
		this.requisitionId = requisitionId;
	}
	
	boolean isCORCOM = false;

	public boolean isCORCOM() {
		return isCORCOM;
	}

	public void setCORCOM(boolean isCORCOM) {
		this.isCORCOM = isCORCOM;
	}
	
	

}