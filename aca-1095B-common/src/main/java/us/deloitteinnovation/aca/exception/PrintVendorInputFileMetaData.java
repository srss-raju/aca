package us.deloitteinnovation.aca.exception;

import us.deloitteinnovation.aca.validator.AcceptedValues;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * <p>
 * Class to encapsulate details of a file name.
 * </p>
 *
 * <p>
 * The name of a file carries certain information about state agencies
 * who provide these files.
 * </p>
 *
 * <p>
 * File name needs to be validated to check if it conforms to a particular
 * pattern.
 * </p>
 */

public class PrintVendorInputFileMetaData {

    private String fileName;

    @NotNull
    @PrintVendorUSStateCode(acceptValues = PrintVendorStateCode.class, message = "{filename.state.code.invalid}")
    private String stateCode;

    @NotNull
    @Pattern.List({
            @Pattern(regexp = "[a-zA-Z]{3}", message = "{filename.agency.code.invalid}"),
            @Pattern(regexp = "^(DHS|FSS|DHH)$", message = "{filename.agency.code.invalid}")
    })
    private String agencyCode;

    @NotNull
    @Pattern.List({
            @Pattern(regexp = "[a-zA-Z]{3}", message = "{filename.system.code.invalid}"),
            @Pattern(regexp = "^(DSS|ICE|EES)$", message = "{filename.system.code.invalid}")
    })
    private String systemCode;

    //TODO: If possible, make this variable a java.util.Date type and validate with appropriate annotation if available.
    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[012]|99)(0[1-9]|[129][0-9]|3[01])(20)\\d\\d$", message = "{filename.date.invalid}")
    private String date;

    @NotNull
    @Pattern(regexp = "(^([0-9][1-9])$|^([1-9][0-9])$)", message = "{filename.version.invalid}")
    private String version;

    @NotNull
    @Pattern(regexp = "(2015|2016)", message = "{filename.tax.year.invalid}")
    private String taxYear;

    @NotNull
    @AcceptedValues(acceptValues = {"dat"}, message = "{filename.extension.invalid}")
    private String extension;

    @NotNull(message = "{filename.record.count.invalid}")
    @Min(value = 1, message = "{filename.record.count.invalid}")
    private Integer recordCountInHeader;


    private Integer recordCountInFile;

    /**
     * Gets the fileName property of the input file.
     *
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the fileName property of the input file.
     * @param fileName name of the input file
     * @return {@link InputFileMetaData} object with the fileName property set to value specified by fileName parameter
     */
    public PrintVendorInputFileMetaData setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * Gets the stateCode property of the input file.
     * @return stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * Sets the stateCode property of the input file.
     * @param stateCode name of the input file
     * @return {@link InputFileMetaData} object with the stateCode property set to value specified by stateCode parameter
     */
    public PrintVendorInputFileMetaData setStateCode(String stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    /**
     * Gets the agencyCode property of the input file.
     * @return agencyCode
     */
    public String getAgencyCode() {
        return agencyCode;
    }

    /**
     * Sets the agencyCode property of the input file.
     * @param agencyCode name of the input file
     * @return {@link InputFileMetaData} object with the agencyCode property set to value specified by agencyCode parameter
     */
    public PrintVendorInputFileMetaData setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
        return this;
    }

    /**
     * Gets the systemCode property of the input file.
     * @return systemCode
     */
    public String getSystemCode() {
        return systemCode;
    }


    /**
     * Sets the systemCode property of the input file.
     * @param systemCode name of the input file
     * @return {@link InputFileMetaData} object with the systemCode property set to value specified by systemCode parameter
     */
    public PrintVendorInputFileMetaData setSystemCode(String systemCode) {
        this.systemCode = systemCode;
        return this;
    }

    /**
     * Gets the date property of the input file.
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date property of the input file.
     * @param date name of the input file
     * @return {@link InputFileMetaData} object with the date property set to value specified by date parameter
     */
    public PrintVendorInputFileMetaData setDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Gets the version property of the input file.
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version property of the input file.
     * @param version name of the input file
     * @return {@link InputFileMetaData} object with the version property set to value specified by version parameter
     */
    public PrintVendorInputFileMetaData setVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * Gets the taxYear property of the input file.
     * @return taxYear
     */
    public String getTaxYear() {
        return taxYear;
    }

    /**
     * Sets the taxYear property of the input file.
     * @param taxYear name of the input file
     * @return {@link InputFileMetaData} object with the taxYear property set to value specified by taxYear parameter
     */
    public PrintVendorInputFileMetaData setTaxYear(String taxYear) {
        this.taxYear = taxYear;
        return this;
    }

    /**
     * Gets the extension property of the input file.
     * @return extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the extension property of the input file.
     * @param extension name of the input file
     * @return {@link InputFileMetaData} object with the extension property set to value specified by extension parameter
     */
    public PrintVendorInputFileMetaData setExtension(String extension) {
        this.extension = extension;
        return this;
    }


    /**
     * Gets the recordCountInHeader property of the input file.
     * @return recordCountInHeader
     */
    public Integer getRecordCountInHeader() {
        return recordCountInHeader;
    }

    /**
     * Sets the recordCountInHeader property of the input file.
     * @param recordCountInHeader name of the input file
     * @return {@link InputFileMetaData} object with the recordCountInHeader property set to value specified by recordCountInHeader parameter
     */
    public PrintVendorInputFileMetaData setRecordCountInHeader(Integer recordCountInHeader) {
        this.recordCountInHeader = recordCountInHeader;
        return this;
    }

    /**
     * Gets the recordCountInFile property of the input file.
     * @return recordCountInFile
     */
    public Integer getRecordCountInFile() {
        return recordCountInFile;
    }


    /**
     * Sets the recordCountInFile property of the input file.
     * @param recordCountInFile name of the input file
     * @return {@link InputFileMetaData} object with the recordCountInFile property set to value specified by recordCountInFile parameter
     */
    public PrintVendorInputFileMetaData setRecordCountInFile(Integer recordCountInFile) {

        this.recordCountInFile = recordCountInFile;
        return this;
    }
}
