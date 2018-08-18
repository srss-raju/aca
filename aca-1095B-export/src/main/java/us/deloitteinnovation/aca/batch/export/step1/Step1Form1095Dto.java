package us.deloitteinnovation.aca.batch.export.step1;

import us.deloitteinnovation.aca.entity.IrsRecordDetails1095BPK;
import us.deloitteinnovation.aca.model.Filer;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

/**
 * <p>
 * Encapsulation of the Form1095BUpstreamDetailType and the SOAP XML rendered from it.
 * Used for mid-term storage of Form 1095 data, along with its XML, during the Job.
 * </p>
 *
 * <p>
 * Object is uniquely identified by the Filer's source code and source unique id.
 * </p>
 */
public class Step1Form1095Dto {

    /**
     * Filer or "Payee" detailed in the FORM 1095B.  Data pulled from FILER_DEMOGRAPHICS.
     */
    Filer filer;

    /**
     * Created during Step1 processor.
     */
    Form1095BUpstreamDetailType form1095BUpstreamDetailType;

    /** If the job is for corrections, this will be the primary key of the IRS_RECORD_DETAILS_1095B table.  Its key contains
     * the record id, submission id, and transmission id. */
    IrsRecordDetails1095BPK irsRecordDetails1095BPK ;

    /** If the job is for corrections, this should be the receipt id of the original transmission. */
    String receiptId;

    /**
     * SOAP XML rendered in String format.
     */
    byte[] rawXml;

    public Step1Form1095Dto() {
    }

    /**
     * @param filer
     */
    public Step1Form1095Dto(Filer filer) {
        assert (filer != null);
        this.filer = filer;
    }

    public Form1095BUpstreamDetailType getForm1095BUpstreamDetailType() {
        return form1095BUpstreamDetailType;
    }

    public void setForm1095BUpstreamDetailType(Form1095BUpstreamDetailType form1095BUpstreamDetailType) {
        this.form1095BUpstreamDetailType = form1095BUpstreamDetailType;
    }

    public byte[] getRawXml() {
        return rawXml;
    }

    public void setRawXml(byte[] rawXml) {
        this.rawXml = rawXml;
    }

    public Filer getFiler() {
        return filer;
    }

    public void setFiler(Filer filer) {
        this.filer = filer;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public IrsRecordDetails1095BPK getIrsRecordDetails1095BPK() {
        return irsRecordDetails1095BPK;
    }

    public void setIrsRecordDetails1095BPK(IrsRecordDetails1095BPK irsRecordDetails1095BPK) {
        this.irsRecordDetails1095BPK = irsRecordDetails1095BPK;
    }
}
