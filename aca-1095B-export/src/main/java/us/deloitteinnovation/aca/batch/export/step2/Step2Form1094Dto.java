package us.deloitteinnovation.aca.batch.export.step2;

import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

/**
 * <p>
 * Encapsulation of the Form1094BUpstreamDetailType and the SOAP XML rendered from it.
 * Used for mid-term storage of Form 1095 data, along with its XML, during the Job.
 * </p>
 * <p>As per the 1094B crosswalk document (1094b_ty2015v2_cw), the only two fields that are required are the year and the employer EIN.</p>
 * @see Step2Form1094Processor
 */
public class Step2Form1094Dto {

    /** The entity defining the 1094B organization. */
    SourceSystemConfig sourceSystemConfig ;

    /** IRS model object for export to XML. */
    Form1094BUpstreamDetailType form1094BUpstreamDetailType ;

    /** SOAP XML rendered in String format. */
    byte[] rawXml ;

    public SourceSystemConfig getSourceSystemConfig() {
        return sourceSystemConfig;
    }

    public void setSourceSystemConfig(SourceSystemConfig sourceSystemConfig) {
        this.sourceSystemConfig = sourceSystemConfig;
    }

    public Form1094BUpstreamDetailType getForm1094BUpstreamDetailType() {
        return form1094BUpstreamDetailType;
    }

    public void setForm1094BUpstreamDetailType(Form1094BUpstreamDetailType form1094BUpstreamDetailType) {
        this.form1094BUpstreamDetailType = form1094BUpstreamDetailType;
    }

    public byte[] getRawXml() {
        return rawXml;
    }

    public void setRawXml(byte[] rawXml) {
        this.rawXml = rawXml;
    }

}
