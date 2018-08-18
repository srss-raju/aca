package us.deloitteinnovation.aca.batch.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.entity.*;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.repository.FilerDemographicRepository;
import us.deloitteinnovation.aca.repository.Irs1095XMLRepository;
import us.deloitteinnovation.aca.repository.IrsRecordDetails1095BRepository;
import us.gov.treasury.irs.ext.aca.air._7.*;
import us.gov.treasury.irs.ext.aca.air.ty16.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static us.deloitteinnovation.aca.batch.export.step1.IrsReflectionAdaptor.genericSetLong;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.MAGIC_NUMBER_ZERO;

/**
 * Created by bhchaganti on 4/4/2016.
 */

public class ExportJobRepositoryInDB implements ExportJob1095Repository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportJob1095RepositoryInMemory.class);

    @Autowired
    Irs1095XMLRepository irs1095XMLRepository;

    @Autowired
    FilerDemographicRepository filerDemographicRepository;

    @Autowired
    IrsRecordDetails1095BRepository irsRecordDetails1095BRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Step1Form1095Dto getForm1095bById(String sourceCode, long sourceId, Integer taxYear) {

        Filer filer = new Filer();
        filer.setSourceCd(sourceCode);
        filer.setSourceUniqueId(sourceId);
        filer.setTaxYear(taxYear.toString());

        Form1095BUpstreamDetailType form1095BUpstreamDetailType = new Form1095BUpstreamDetailType();

        Irs1095XMLPK irs1095XMLPK = new Irs1095XMLPK(sourceId, sourceCode, taxYear);
        Irs1095XML irs1095XML = irs1095XMLRepository.findOne(irs1095XMLPK);

        Step1Form1095Dto dto = new Step1Form1095Dto();
        dto.setFiler(filer);
        dto.setRawXml(irs1095XML.getIrs1095BXml().getBytes());
        dto.setForm1095BUpstreamDetailType(form1095BUpstreamDetailType);
        return dto;
    }


    @Override
    public List<Step1Form1095Dto> getForm1095bBySourceCode(String sourceCd, Integer currentTransmissionId, Integer taxYear, StepExecution stepExecution) {
        String irsTransmissionStatusCd = BatchExportConstants.getStatusFromTransmissionCode(BatchExportConstants.getJobTypeLetter(stepExecution));

        // Generate un-paged fetch query
        String sql = getForm1095bBySourceCodeSqlQuery(sourceCd, currentTransmissionId, taxYear, stepExecution, irsTransmissionStatusCd, Boolean.FALSE, 0, 0);
        // Fetch records
        List<Irs1095XML> irs1095XMLList = this.jdbcTemplate.query(sql, new IRSXmlEntityMapper());
        // Append record detail and return
        return getForm1095RecordDetails(sourceCd, irsTransmissionStatusCd, irs1095XMLList);
    }

    private String getForm1095bBySourceCodeSqlQuery(String sourceCd, Integer currentTransmissionId, Integer taxYear, StepExecution stepExecution, String irsTransmissionStatusCd, boolean paged, int pageNum, int pageSize) {
        String sql;
        if (irsTransmissionStatusCd.equals(BatchExportEntityConstants.FilerXmlStatus.REPLACE)) {
           /* Replacement transmissions will get records of a given transmission id */
            sql = "SELECT irsxml.SOURCE_UNIQUE_ID, irsxml.SOURCE_CD, irsxml.TAX_YEAR, irsxml.IRS_1095B_XML, fd.FILER_DEMO_SEQ " +
                    "from dbo.IRS_1095_XML irsxml " +
                    "join " +
                    "FILER_DEMOGRAPHICS fd on (irsxml.SOURCE_CD = fd.SOURCE_CD and irsxml.SOURCE_UNIQUE_ID = fd.SOURCE_UNIQUE_ID and irsxml.TAX_YEAR = fd.TAX_YEAR) " +
                    "join " +
                    "dbo.IRS_RECORD_DETAILS_1095B rdetails " +
                    "on(irsxml.source_unique_id = rdetails.source_unique_id and irsxml.SOURCE_CD = rdetails.SOURCE_CD) " +
                    "WHERE irsxml.IRS_TRANSMISSION_STATUS_CD = '" + irsTransmissionStatusCd + "' " +
                    "AND rdetails.TRANSMISSION_ID =" + currentTransmissionId + "  AND irsxml.SOURCE_CD = '" + sourceCd + "' AND irsxml.TAX_YEAR="+taxYear;

        } else if (irsTransmissionStatusCd.equals(BatchExportEntityConstants.FilerXmlStatus.CORRECTED)) {
            /* Correction transmissions will get records of all transmission id's */
            sql = "SELECT irsxml.SOURCE_UNIQUE_ID, irsxml.SOURCE_CD, irsxml.TAX_YEAR, irsxml.IRS_1095B_XML, fd.FILER_DEMO_SEQ " +
                    "from dbo.IRS_1095_XML irsxml " +
                    "join " +
                    "FILER_DEMOGRAPHICS fd on (irsxml.SOURCE_CD = fd.SOURCE_CD and irsxml.SOURCE_UNIQUE_ID = fd.SOURCE_UNIQUE_ID and irsxml.TAX_YEAR = fd.TAX_YEAR) " +
                    "WHERE irsxml.IRS_TRANSMISSION_STATUS_CD = '" + irsTransmissionStatusCd + "' " +
                    "AND irsxml.SOURCE_CD = '" + sourceCd + "' AND irsxml.TAX_YEAR="+taxYear;
        } else {
            /* For Originals there will be no tranmissions generated. So no need to iterate by transmissions */
            sql = "select irsxml.SOURCE_UNIQUE_ID, irsxml.SOURCE_CD, irsxml.TAX_YEAR, irsxml.IRS_1095B_XML, FILER_DEMO_SEQ " +
                    "FROM IRS_1095_XML irsxml " +
                    "join FILER_DEMOGRAPHICS fd on (irsxml.SOURCE_CD = fd.SOURCE_CD and irsxml.SOURCE_UNIQUE_ID = fd.SOURCE_UNIQUE_ID and irsxml.TAX_YEAR = fd.TAX_YEAR) " +
                    "where irsxml.SOURCE_CD = '" + sourceCd + "' and irsxml.IRS_TRANSMISSION_STATUS_CD = '" + irsTransmissionStatusCd + "' AND irsxml.TAX_YEAR="+taxYear;
        }

        if (paged) {
            // Since previously processed records have status changed to XG, hereby only select TOP pageSize records
            sql = sql + String.format(" order by SOURCE_UNIQUE_ID offset %d rows fetch next %d rows only", MAGIC_NUMBER_ZERO, pageSize);
        }

        return sql.intern();
    }

    private List<Step1Form1095Dto> getForm1095RecordDetails(String sourceCd, String irsTransmissionStatusCd, List<Irs1095XML> irs1095XMLList) {

        List<Step1Form1095Dto> list1095Dto = new ArrayList<>();
        Integer intRecordId = 0;
        Step1Form1095Dto dto;

        for (Irs1095XML irs1095XML : irs1095XMLList) {
            dto = new Step1Form1095Dto();
            Filer filer = new Filer();
            Form1095BUpstreamDetailType form1095BUpstreamDetailType = new Form1095BUpstreamDetailType();
            IrsRecordDetails1095B irsRecordDetails1095B = null;
            if (irsTransmissionStatusCd.equals(BatchExportEntityConstants.FilerXmlStatus.CORRECTED)) {
                irsRecordDetails1095B = irsRecordDetails1095BRepository.fetchOriginalTransmissionDetailsForCorrections(irs1095XML.getSourceCd(), irs1095XML.getSourceUniqueId());
            } else if (irsTransmissionStatusCd.equals(BatchExportEntityConstants.FilerXmlStatus.REPLACE)) {
                irsRecordDetails1095B = irsRecordDetails1095BRepository.fetchOriginalTransmissionDetailsForReplacements(irs1095XML.getSourceCd(), irs1095XML.getSourceUniqueId());
            }
            if (irsRecordDetails1095B != null) {
                IrsRecordDetails1095BPK irsRecordDetails1095BPK = new IrsRecordDetails1095BPK();
                irsRecordDetails1095BPK.setRecordId(irsRecordDetails1095B.getId().getRecordId());
                irsRecordDetails1095BPK.setSubmissionId(irsRecordDetails1095B.getId().getSubmissionId());
                irsRecordDetails1095BPK.setTransmissionId(irsRecordDetails1095B.getId().getTransmissionId());
                dto.setIrsRecordDetails1095BPK(irsRecordDetails1095BPK);
            }
            filer.setSourceCd(sourceCd);
            filer.setSourceUniqueId(irs1095XML.getSourceUniqueId());
            filer.setFilerDemoSeq(irs1095XML.getFilerDemoSeq());
            filer.setTaxYear(irs1095XML.getTaxYear().toString());

            intRecordId++;
            try {
                genericSetLong(form1095BUpstreamDetailType, "setRecordId", intRecordId);
            } catch (Exception e) {
                LOGGER.error("Failed to set RecordId for form1095BUpstreamDetailType", e);
            }

            dto.setFiler(filer);
            dto.setRawXml(irs1095XML.getIrs1095BXml().getBytes());
            dto.setForm1095BUpstreamDetailType(form1095BUpstreamDetailType);

            list1095Dto.add(dto);
        }
        return list1095Dto;
    }

    @Override
    public List<Step1Form1095Dto> getForm1095bBySourceCode(String sourceCd, Integer currentTransmissionId, Integer taxYear, StepExecution stepExecution, int pageNum, int pageSize) {
        String irsTransmissionStatusCd = BatchExportConstants.getStatusFromTransmissionCode(BatchExportConstants.getJobTypeLetter(stepExecution));
        // Generate un-paged fetch query
        String sql = getForm1095bBySourceCodeSqlQuery(sourceCd, currentTransmissionId, taxYear, stepExecution, irsTransmissionStatusCd, Boolean.TRUE, pageNum, pageSize);
        // Fetch records
        List<Irs1095XML> irs1095XMLList = this.jdbcTemplate.query(sql, new IRSXmlEntityMapper());
        LOGGER.debug(String.format("Page %d read: %d items / %d (page size)", pageNum, irs1095XMLList.size(), pageSize));
        // Append record detail and return
        return getForm1095RecordDetails(sourceCd, irsTransmissionStatusCd, irs1095XMLList);
    }

    @Override
    public void save(Step1Form1095Dto form1095Bdto, StepExecution stepExecution) {

        Irs1095XML irs1095XMLEntity = new Irs1095XML();
        Irs1095XMLPK irs1095XMLPK = new Irs1095XMLPK(
                form1095Bdto.getFiler().getSourceUniqueId(),
                form1095Bdto.getFiler().getSourceCd(),
                Integer.parseInt(form1095Bdto.getFiler().getTaxYear()));
        irs1095XMLEntity.setId(irs1095XMLPK);

        irs1095XMLEntity.setIrsTransmissionStatusCd(BatchExportConstants.getStatusFromTransmissionCode(BatchExportConstants.getJobTypeLetter(stepExecution)));
        // TODO: Get the actual Status code from IRS_TRANSMISSION_STATUS table
        irs1095XMLEntity.setIrs1095BXml(new String(form1095Bdto.getRawXml()));
        irs1095XMLEntity.setUpdatedBy("System");
        irs1095XMLEntity.setUpdatedDate(new java.util.Date());

        /*
        * Replacements job will be called in 2 cases. 1. Reject Resend and 2. Reject Corrected
        * For replacements, only insert records into IRS_1095_XML if and only if they do not exist.
        * Because in Reject Resend scenario, we do not want to re-generate filer XML's but use the already
        * generated filer XML's. Whereas for Reject Corrected scenario, we re-generate filer XML's. Hence the
        * below check. Not sure if this can be handled in a better way.
        *
        * */

        if ("R".equalsIgnoreCase(BatchExportConstants.getJobTypeLetter(stepExecution))) {
            Irs1095XML irs1095XML = irs1095XMLRepository.findByTransmissionStatusCode(form1095Bdto.getFiler().getSourceUniqueId(),
                    form1095Bdto.getFiler().getSourceCd(),
                    BatchExportConstants.
                            getStatusFromTransmissionCode(BatchExportConstants.getJobTypeLetter(stepExecution)));
            //Save only if the entity doesn't already exist
            if (irs1095XML == null) {
                irs1095XMLRepository.save(irs1095XMLEntity);
            }
        } else {
            irs1095XMLRepository.save(irs1095XMLEntity);
        }
        /* Update Filer Demographics with appropriate status */
        filerDemographicRepository.updateIrsStatusCD(form1095Bdto.getFiler().getSourceCd(), form1095Bdto.getFiler().getSourceUniqueId(), form1095Bdto.getFiler().getTaxYear(),"System", new Date(), "XC");
    }

    /**
     * Clears all in-memory data storage maps.  Useful for testing.
     */
    public void clearAll() {

        irs1095XMLRepository.deleteAll();
    }
}

