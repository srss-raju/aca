package us.deloitteinnovation.aca.batch.export.step4;

import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

/**
 * Encapsulation of data required for writing a manifest to XML.
 */
public class Step4ManifestData {
    public ACATrnsmtManifestReqDtlType acaTrnsmtManifestReqDtlType ;

    /** TCC to use for these transmissions. */
    public String tcc ;

    public ACATrnsmtManifestReqDtlType getAcaTrnsmtManifestReqDtlType() {
        return acaTrnsmtManifestReqDtlType;
    }

    public void setAcaTrnsmtManifestReqDtlType(ACATrnsmtManifestReqDtlType acaTrnsmtManifestReqDtlType) {
        this.acaTrnsmtManifestReqDtlType = acaTrnsmtManifestReqDtlType;
    }

    public String getTcc() {
        return tcc;
    }

    public void setTcc(String tcc) {
        this.tcc = tcc;
    }
}
