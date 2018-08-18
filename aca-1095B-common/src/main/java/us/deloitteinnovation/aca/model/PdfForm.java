package us.deloitteinnovation.aca.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Date;

public class PdfForm {
	private File pdf;
	private long id;
	private String sourceCd;
	private Date updatedDate;
	private String updatedBy;
	private InputStream inputStream;
	private ByteArrayOutputStream byteArrayOutputStream;
	private String formStatus ;

	// this code is related to print details
	private Date printUpdatedDate;
	private Date printLastMailReqDate;
	private Integer batchID;
	private String printReason;
	private String printStatus;
	private Date printDate;
	private String printFileName;
	private String printupdatedBy;
	private boolean isExceptionFound = false;

	public PdfForm() {
		// Intentionally left blank
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public File getPdf() {
		return pdf;
	}
	public void setPdf(File pdf) {
		this.pdf = pdf;
	}
	public String getSourceCd() {
		return sourceCd;
	}
	public void setSourceCd(String sourceCd) {
		this.sourceCd = sourceCd;
	}

	public Date getPrintUpdatedDate() {
		return printUpdatedDate;
	}

	public void setPrintUpdatedDate(Date printUpdatedDate) {
		this.printUpdatedDate = printUpdatedDate;
	}

	public Date getPrintLastMailReqDate() {
		return printLastMailReqDate;
	}

	public void setPrintLastMailReqDate(Date printLastMailReqDate) {
		this.printLastMailReqDate = printLastMailReqDate;
	}

	public Integer getBatchID() {
		return batchID;
	}

	public void setBatchID(Integer batchID) {
		this.batchID = batchID;
	}

	public String getPrintReason() {
		return printReason;
	}

	public void setPrintReason(String printReason) {
		this.printReason = printReason;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public Date getPrintDate() {
		return printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	public String getPrintFileName() {
		return printFileName;
	}

	public void setPrintFileName(String printFileName) {
		this.printFileName = printFileName;
	}

	public String getPrintupdatedBy() {
		return printupdatedBy;
	}

	public void setPrintupdatedBy(String printupdatedBy) {
		this.printupdatedBy = printupdatedBy;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ByteArrayOutputStream getByteArrayOutputStream() {
		return byteArrayOutputStream;
	}

	public void setByteArrayOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
		this.byteArrayOutputStream = byteArrayOutputStream;
	}

	public String getFormStatus() {

		return formStatus;
	}

	public void setFormStatus(String formStatus) {

		this.formStatus = formStatus;
	}
	public boolean isExceptionFound() {
		return isExceptionFound;
	}

	public void setIsExceptionFound(boolean isExceptionFound) {
		this.isExceptionFound = isExceptionFound;
	}


}
