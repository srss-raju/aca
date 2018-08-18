package us.deloitteinnovation.aca.jaxb;

import us.gov.treasury.irs.common.TINRequestTypeCodeType;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import java.math.BigInteger;

import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.genericSetLong;
import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.genericSetYear;

/**
 * Created by rgopalani on 12/28/2015.
 */
public class ACA1094BDetailBuilder {
    private ObjectFactory objectFactory = new ObjectFactory();
    private Form1094BUpstreamDetailType form1094 = objectFactory.createForm1094BUpstreamDetailType();

    public ACA1094BDetailBuilder setTaxYr(final int taxYr) {
        genericSetYear(form1094, "setTaxYr", taxYr);
        return this;
    }

    public ACA1094BDetailBuilder setBusinessNameType(BusinessNameType businessNameType){
        form1094.setBusinessName(businessNameType);
        return this;
    }
    public ACA1094BDetailBuilder setBusinessAddressGrpType(BusinessAddressGrpType businessAddressGrpType){
        form1094.setMailingAddressGrp(businessAddressGrpType);
        return this;
    }

    public ACA1094BDetailBuilder setEmployerEIN(String employerEIN){
        form1094.setEmployerEIN(employerEIN);
        return this;
    }

    /**
     * For every record, recordType must be “String” (word) or “” and lineNum must be 0 (zero).
     * @return
     */
    public Form1094BUpstreamDetailType build() {
        form1094.setRecordType("String");
        form1094.setLineNum(BigInteger.ZERO);
        return form1094;
    }

    public ACA1094BDetailBuilder setSubmissionId(long submissionId){
        genericSetLong(form1094, "setSubmissionId", submissionId);
        return this;
    }

    public ACA1094BDetailBuilder setTINRequestTypeCd(TINRequestTypeCodeType tinRequestTypeCd){
        form1094.setTINRequestTypeCd(tinRequestTypeCd);
        return this;
    }
}
