package us.deloitteinnovation.aca.batch.invalidaddress.step;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import us.deloitteinnovation.aca.entity.FilerDemographic;
import us.deloitteinnovation.aca.exception.PrintVendorExceptionReportDto;

/**
 * 
 * @author rbongurala
 *
 */
public class StepResult {

	List<PrintVendorExceptionReportDto> exceptionReport = new ArrayList<PrintVendorExceptionReportDto>();

    public List<PrintVendorExceptionReportDto> getExceptionReport() {
        return exceptionReport;
    }

    public void setExceptionReport(List<PrintVendorExceptionReportDto> exceptionReport) {
        this.exceptionReport = exceptionReport;
    }
    
    private boolean isValid;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
    
    private File invalidAddressFile;

	public File getInvalidAddressFile() {
		return invalidAddressFile;
	}

	public void setInvalidAddressFile(File invalidAddressFile) {
		this.invalidAddressFile = invalidAddressFile;
	}
	
	private List<FilerDemographic> filerDemographicList = new ArrayList<FilerDemographic>();

    public List<FilerDemographic> getFilerDemographicList() {
        return filerDemographicList;
    }

    public void setFilerDemographicList(List<FilerDemographic> filerDemographicList) {
        this.filerDemographicList = filerDemographicList;
    }
    
    private int failureCount;

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }
    
    private String state;
    private String sourceCdFromFileName;
    private String taxYearFromFileName;
    private String versionFromFileName;

    public String getSourceCdFromFileName() {
        return sourceCdFromFileName;
    }

    public void setSourceCdFromFileName(String sourceCdFromFileName) {
        this.sourceCdFromFileName = sourceCdFromFileName;
    }

    public String getTaxYearFromFileName() {
        return taxYearFromFileName;
    }

    public void setTaxYearFromFileName(String taxYearFromFileName) {
        this.taxYearFromFileName = taxYearFromFileName;
    }

    public String getVersionFromFileName() {
        return versionFromFileName;
    }

    public void setVersionFromFileName(String versionFromFileName) {
        this.versionFromFileName = versionFromFileName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
}