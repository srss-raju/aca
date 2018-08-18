package us.deloitteinnovation.aca.jaxb;

import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import java.math.BigInteger;
import java.util.List;

import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.genericSetLong;
import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.genericSetYear;

public class ACA1095BDetailBuilder {

    private ObjectFactory objectFactory = new ObjectFactory();

    private Form1095BUpstreamDetailType
            form1095B = objectFactory.createForm1095BUpstreamDetailType();

    public ACA1095BDetailBuilder setRecordId(final long recordId) {
        genericSetLong(form1095B, "setRecordId", recordId);
        return this;
    }

    public ACA1095BDetailBuilder setTestScenarioId(final String testScenarioId) {
        form1095B.setTestScenarioId(testScenarioId);
        return this;
    }

    public ACA1095BDetailBuilder setCorrectedInd(final boolean isCorrected) {
        form1095B.setCorrectedInd(isCorrected ? "1" : "0");
        return this;
    }

    public ACA1095BDetailBuilder setCorrectedRecordInfoGrp(
            final CorrectedRecordInfoGrpType correctedRecordInfoGrp) {
        form1095B.setCorrectedRecordInfoGrp(correctedRecordInfoGrp);
        return this;
    }

    public ACA1095BDetailBuilder setTaxYr(final int taxYr) {
        genericSetYear(form1095B, "setTaxYr", taxYr);
        return this;
    }

    public ACA1095BDetailBuilder setResponsibleIndividualGrp(
            final ResponsibleIndividualGrpType responsibleIndividualGrp) {
        form1095B.setResponsibleIndividualGrp(responsibleIndividualGrp);
        return this;
    }

    public ACA1095BDetailBuilder setSponsoringEmployerInfoGrp(
            final SponsoringEmployerInfoGrpType sponsoringEmployerInfoGrp) {
        form1095B.setSponsoringEmployerInfoGrp(sponsoringEmployerInfoGrp);
        return this;
    }

    public ACA1095BDetailBuilder setIssuerInfoGrp(
            final IssuerInfoGrpType issuerInfoGrp) {
        form1095B.setIssuerInfoGrp(issuerInfoGrp);
        return this;
    }

    public ACA1095BDetailBuilder addCoveredIndividualGrp(
            final EmployerCoveredIndividualType coveredIndividual) {
        form1095B.getCoveredIndividualGrp().add(coveredIndividual);
        return this;
    }
    public ACA1095BDetailBuilder addCoveredIndividualGrps(
            final List<EmployerCoveredIndividualType> coveredIndividuals) {
        form1095B.getCoveredIndividualGrp().addAll(coveredIndividuals);
        return this;
    }
    public ACA1095BDetailBuilder setRecordType(final String recordType) {
        form1095B.setRecordType(recordType);


        return this;
    }

    public ACA1095BDetailBuilder setLineNum(final long lineNum) {
        form1095B.setLineNum(BigInteger.valueOf(lineNum));
        return this;
    }

    public Form1095BUpstreamDetailType build() {
        form1095B.setRecordType(String.class.getSimpleName());
        return form1095B;
    }

}
