package us.deloitteinnovation.aca.batch.export.step4;

import us.deloitteinnovation.aca.batch.export.step3.Form10945Type;
import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import java.io.File;

/**
 * <p>
 * Encapsulation of the data necessary for a 109495B Header and Manifest.
 * </p>
 */
public class Step4Form109495HeaderAndXmlDto {

    public SourceSystemConfig     sourceSystem;
    /**
     * “O” – Original, “C” – Corrections, “R” - Replacement
     */
    public TransmissionTypeCdType transmissionType;

    // TODO Many of these things should be in a constants file.
    /**
     * Name of the transmitter.
     */
    public BusinessNameType          transmitterName;
    /**
     * The address, name of the company to contact, and phone number to call
     */
    public CompanyInformationGrpType contactCompany;
    /**
     * Identifies if software was developed by a vendor or in-house as well as contact information
     */
    public VendorInformationGrpType  softwareDeveloper;
    /**
     * The Software ID assigned to the software when the software was registered with the IRS
     */
    public String                    softwareId;
    public int                       paymentYear;
    public boolean isPriorYearFiling = false;
    /**
     * Reference to the Receipt ID of the transmission for which replacement transmissions are submitted
     */
    String priorTransmissionRef;
    /**
     * The total number of Forms 1094 (payers) included in the entire transmission
     */
    public int    total1094Forms;
    /**
     * The total number of Forms 1095 (payees) included in the entire transmission
     */
    public int    total1095Forms;
    /**
     * Will contain “1094/1095B” if Forms 1094/1095-B are included in the transmission or “1094/1095C” if Forms 1094/1095-C are included in the transmission
     */
    public Form10945Type form10945Type = Form10945Type.FORM_10945_B;
    /**
     * The MD5 Checksum computed on the Form Data File attached to the transmission
     */
    public String formDataMd5;
    /**
     * The size in bytes of the Form Data File attached to the transmission
     */
    public long  formDataSize;

    /**
     * Form data file attached to the transmission.
     */
    public File formDataFile ;

    /**
     * Manifest information in IRS format.
     */
    ACATrnsmtManifestReqDtlType acaTrnsmtManifestReqDtlType;


    public SourceSystemConfig getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(SourceSystemConfig sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public TransmissionTypeCdType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionTypeCdType transmissionType) {
        this.transmissionType = transmissionType;
    }

//    public String getTransmitterEin() {
//        return transmitterEin;
//    }
//
//    public void setTransmitterEin(String transmitterEin) {
//        this.transmitterEin = transmitterEin;
//    }

    public CompanyInformationGrpType getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(CompanyInformationGrpType contactCompany) {
        this.contactCompany = contactCompany;
    }

    public VendorInformationGrpType getSoftwareDeveloper() {
        return softwareDeveloper;
    }

    public void setSoftwareDeveloper(VendorInformationGrpType softwareDeveloper) {
        this.softwareDeveloper = softwareDeveloper;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public int getPaymentYear() {
        return paymentYear;
    }

    public void setPaymentYear(int paymentYear) {
        this.paymentYear = paymentYear;
    }

    public boolean isPriorYearFiling() {
        return isPriorYearFiling;
    }

    public void setPriorYearFiling(boolean priorYearFiling) {
        isPriorYearFiling = priorYearFiling;
    }

    public String getPriorTransmissionRef() {
        return priorTransmissionRef;
    }

    public void setPriorTransmissionRef(String priorTransmissionRef) {
        this.priorTransmissionRef = priorTransmissionRef;
    }

    public int getTotal1094Forms() {
        return total1094Forms;
    }

    public void setTotal1094Forms(int total1094Forms) {
        this.total1094Forms = total1094Forms;
    }

    public int getTotal1095Forms() {
        return total1095Forms;
    }

    public void setTotal1095Forms(int total1095Forms) {
        this.total1095Forms = total1095Forms;
    }

    public Form10945Type getForm10945Type() {
        return form10945Type;
    }

    public void setForm10945Type(Form10945Type form10945Type) {
        this.form10945Type = form10945Type;
    }

    public String getFormDataMd5() {
        return formDataMd5;
    }

    public void setFormDataMd5(String formDataMd5) {
        this.formDataMd5 = formDataMd5;
    }

    public long getFormDataSize() {
        return formDataSize;
    }

    public void setFormDataSize(long formDataSize) {
        this.formDataSize = formDataSize;
    }

    public File getFormDataFile() {
        return formDataFile;
    }

    public void setFormDataFile(File formDataFile) {
        this.formDataFile = formDataFile;
    }

    public BusinessNameType getTransmitterName() {
        return transmitterName;
    }

    public void setTransmitterName(BusinessNameType transmitterName) {
        this.transmitterName = transmitterName;
    }

    public ACATrnsmtManifestReqDtlType getAcaTrnsmtManifestReqDtlType() {
        return acaTrnsmtManifestReqDtlType;
    }

    public void setAcaTrnsmtManifestReqDtlType(ACATrnsmtManifestReqDtlType acaTrnsmtManifestReqDtlType) {
        this.acaTrnsmtManifestReqDtlType = acaTrnsmtManifestReqDtlType;
    }

}
