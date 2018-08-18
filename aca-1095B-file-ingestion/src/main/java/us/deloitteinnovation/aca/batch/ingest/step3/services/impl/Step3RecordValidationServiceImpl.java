package us.deloitteinnovation.aca.batch.ingest.step3.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import us.deloitteinnovation.aca.batch.constants.BatchQueryConstants;
import us.deloitteinnovation.aca.batch.dto.FilerCoverageDto;
import us.deloitteinnovation.aca.batch.ingest.step3.dto.*;
import us.deloitteinnovation.aca.batch.ingest.step3.services.FileIngestionService;
import us.deloitteinnovation.aca.batch.ingest.step3.services.Step3RecordValidationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tthakore on 9/1/2016.
 */
public class Step3RecordValidationServiceImpl implements Step3RecordValidationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FileIngestionService fileIngestionDao;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    Step3RecordsUIDValidationResultMap step3RecordsUIDValidationResultMap;
    


    @Override
    public List<Step3FilerDataDto> getRecordsByUID(String sourceCd, long sourceUID, String taxYear) {

        StringBuilder sql = new StringBuilder("select "+ BatchQueryConstants.STAGING_COLUMNS_NAME+" from filer_demographics_staging where source_cd = ? and source_unique_id = ? and tax_year = ? order by row_number ASC");
        List<Step3FilerDataDto> list = this.jdbcTemplate.query(sql.toString(),
                new Object[]{sourceCd, sourceUID, taxYear}, new Step3FilerDataDtoMapper());
        return list;
    }

    @Override
    public List<Step3FilerDataDto> getRecordsBySSN(String uid,  String taxYear, String sourceCd) {

        List<Step3FilerDataDto> list;
            StringBuilder sql = new StringBuilder("select "+ BatchQueryConstants.STAGING_COLUMNS_NAME+" from filer_demographics_staging where RECIPIENT_SSN = ? and tax_year = ? and source_cd = ?  order by row_number ASC");
            list = this.jdbcTemplate.query(sql.toString(),
                    new Object[]{uid, taxYear, sourceCd}, new Step3FilerDataDtoMapper());
        return list;
    }


    @Override
    public List<Step3FilerDataDto> getRecordsByTIN(String uid, String taxYear, String sourceCd) {

        List<Step3FilerDataDto> list;

            StringBuilder sql = new StringBuilder("select "+ BatchQueryConstants.STAGING_COLUMNS_NAME+" from filer_demographics_staging where RECIPIENT_TIN = ? and  tax_year = ? and source_cd = ?   order by row_number ASC");
            list = this.jdbcTemplate.query(sql.toString(),
                    new Object[]{uid, taxYear, sourceCd}, new Step3FilerDataDtoMapper());
        return list;
    }

    @Override
    public List<Step3FilerDataDto> getRecordsFromFileByStateandTaxYear(String state, String taxYear) {
        List<Step3FilerDataDto> list;
        StringBuilder sql = new StringBuilder("select distinct source_unique_id,  source_cd , tax_year , filer_status, row_number from filer_demographics_staging fds where left(fds.SOURCE_CD,2) = ? and tax_year = ?  and RECORD_SOURCE = 'FILE'   order by row_number ASC ");
        this.jdbcTemplate.query(sql.toString(),
                new Object[]{state, taxYear}, new Step3InitialRecordListMapper(step3RecordsUIDValidationResultMap));
        list = new ArrayList<>(step3RecordsUIDValidationResultMap.getIntialRecordsMap().values());
        return list;
    }

    @Override
    public void bulkInsertFD(List<Step3FilerDataDto> filerDemographics) {
        fileIngestionDao.bulkInsertFD(filerDemographics);
    }

    @Override
    public void bulkInsertFCS(FilerCoverageDto filerDemographics) {
        fileIngestionDao.bulkInsertFCS(filerDemographics);
    }

    @Override
    public void bulkInsertBusinessLogs(List<BusinessValidationRuleDto> businessLogList) {
        fileIngestionDao.bulkInsertBusinessLogs(businessLogList);
    }

    @Override
    public void bulkInsertExceptionReport(List<BusinessValidationRuleDto> exceptionReportListList)
    {
        fileIngestionDao.bulkInsertExceptionReport(exceptionReportListList);
    }

    @Override
    public void bulkUpdateFD(List<Step3FilerDataDto> filerDemographics) {
        fileIngestionDao.bulkUpdateFD(filerDemographics);
    }

    @Override
    public void bulkUpdateFormStatus(List<Step3FilerDataDto> filerDemographics){
        fileIngestionDao.bulkUpdateFormStatus(filerDemographics);
    }

    @Override
    public List<Step3FilerDataDto> getCoveredPersonListbyUID(String sourceCd, long sourceUID, String taxYear) {
        String filerStatus = "C";
        StringBuilder sql = new StringBuilder("select "+ BatchQueryConstants.STAGING_COLUMNS_NAME+" from filer_demographics_staging where source_cd = ? and RESPONSIBLE_PERSON_UNIQUE_ID = ? and tax_year = ? and filer_status= ? order by row_number ASC");
        List<Step3FilerDataDto> list = this.jdbcTemplate.query(sql.toString(),
                new Object[]{sourceCd, sourceUID, taxYear,filerStatus}, new Step3FilerDataDtoMapper());
        return list;
    }

    @Override
    public void bulkDeleteFromCoverage(FilerCoverageDto filerDemographics)
    {
        fileIngestionDao.bulkDeleteFromCoverage(filerDemographics);
    }

    @Override
    public void bulkUpdateVersionNo(List<Step3FilerDataDto> otherRecordsToUpdate)
    {
        fileIngestionDao.bulkUpdateVersionNo(otherRecordsToUpdate);
    }

}
