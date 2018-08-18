package us.deloitteinnovation.aca.batch.receipt.dto;

import java.util.List;

import us.deloitteinnovation.aca.entity.PrintDetail;

public class ProcessReceiptDto {
	
	private List<PrintDetail> printDetails;
	private List<String> emptyFiles;
	private boolean isCOR;

	public List<PrintDetail> getPrintDetails() {
		return printDetails;
	}

	public void setPrintDetails(List<PrintDetail> printDetails) {
		this.printDetails = printDetails;
	}

	public List<String> getEmptyFiles() {
		return emptyFiles;
	}

	public void setEmptyFiles(List<String> emptyFiles) {
		this.emptyFiles = emptyFiles;
	}
	
	public boolean isCOR() {
		return isCOR;
	}

	public void setCOR(boolean isCOR) {
		this.isCOR = isCOR;
	}
	
}
