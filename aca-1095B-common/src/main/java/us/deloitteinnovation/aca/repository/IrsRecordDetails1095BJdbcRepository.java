package us.deloitteinnovation.aca.repository;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095B;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095BPK;

import java.util.*;

/**
 */
@Transactional
public class IrsRecordDetails1095BJdbcRepository extends JdbcDaoSupport {

    public static final String SQL_RECORD_DETAILS_INSERT = "INSERT INTO dbo.IRS_RECORD_DETAILS_1095B " +
            "(RECORD_ID,SUBMISSION_ID,TRANSMISSION_ID,RECORD_STATUS,SOURCE_UNIQUE_ID,SOURCE_CD,FILER_DEMO_SEQ,UPDATED_DATE,UPDATED_BY)\n" +
            "VALUES (?,?,?,?,?,?,?,?,?)";
    /**
     * Below SQL is used to update the end state of the records to 'XC' for which
     * corrections/replacements have been successfully generated
     */
    private static final Logger LOG = LoggerFactory.getLogger(IrsRecordDetails1095BJdbcRepository.class);
    private static final Integer NUM_RECORDS_PER_BATCH = 1000;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(List<IrsRecordDetails1095B> list) {
        for (IrsRecordDetails1095B details : list) {
            super.getJdbcTemplate().update(SQL_RECORD_DETAILS_INSERT, toInsertArgs(details));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateEndStatusForRecords(Set<Integer> transmissionIdSet, List<Long> sourceUniqueIdList, Integer year) {

        /* For corrections mark only the corrected records' end status as 'XC'
            as all the records in a transmission might not get corrections
        */
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("updateddate", new Date());
        mapSqlParameterSource.addValue("updatedby", "System");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(super.getJdbcTemplate().getDataSource());

        if (sourceUniqueIdList != null && !sourceUniqueIdList.isEmpty()) {
            //Chop the list of source unique id's into chunks of 1000
            List<List<Long>> sourceUniqueIdSubList = Lists.partition(sourceUniqueIdList, NUM_RECORDS_PER_BATCH);
            //Iterate over the sub lists and update the source unique id's of each sub list.
            for (List<Long> sublist : sourceUniqueIdSubList) {
                mapSqlParameterSource.addValue("sourceuniqueidlist", sublist);
                namedParameterJdbcTemplate.update(
                        "UPDATE dbo.irs_record_details_1095b " +
                                "SET " +
                                "UPDATED_DATE = :updateddate, " +
                                "UPDATED_BY = :updatedby, " +
                                "record_status = 'XC' " +
                                "WHERE " +
                                "record_status != 'XC' " +
                                "AND " +
                                "transmission_id " +
                                "in (" +
                                "SELECT transmission_id " +
                                "FROM " +
                                "IRS_TRANSMISSION_DETAILS " +
                                "WHERE " +
                                "TAX_YEAR = " + year +
                                ") AND " +
                                "SOURCE_UNIQUE_ID IN (:sourceuniqueidlist)", mapSqlParameterSource);
                if (LOG.isInfoEnabled()) {
                    LOG.info("Updated batch of {} records as XC", sublist.size());
                }
            }
        } else {
        /* In case of Replacements mark the entire transmission's en status as 'XC'
        *  as the entire transmission will get rejected, if at all.
        *  There will be no situation where only some of the records of a transmission
        *  will get rejected */
        mapSqlParameterSource.addValue("transmissionidset", transmissionIdSet);
        namedParameterJdbcTemplate.update(
                "UPDATE dbo.irs_record_details_1095b " +
                "SET " +
                "UPDATED_DATE = :updateddate, " +
                "UPDATED_BY = :updatedby, " +
                "record_status = 'XC' " +
                "WHERE " +
                "transmission_id " +
                "IN (:transmissionidset)", mapSqlParameterSource);
        }
    }


    /**
     * Creates all input args first.  Sacrifices memory for speed.
     *
     * @param list
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveInBatch(Collection<IrsRecordDetails1095B> list) {
        long start = 0;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating args array for bulk insert of {} records.", list.size());
            start = System.currentTimeMillis();
        }
        List<Object[]> argList = new ArrayList<>(list.size());
        for (IrsRecordDetails1095B details : list) {
            argList.add(toInsertArgs(details));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Args array for bulk insert created in {}ms.  Sending args to database.", System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
        }
        super.getJdbcTemplate().batchUpdate(SQL_RECORD_DETAILS_INSERT, argList);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Bulk data save of {} records completed in {}ms.", argList.size(), System.currentTimeMillis() - start);
        }
    }

    protected Object[] toInsertArgs(IrsRecordDetails1095B details) {
        Object[] args = new Object[9];
        IrsRecordDetails1095BPK id = details.getId();
        args[0] = id.getRecordId();
        args[1] = id.getSubmissionId();
        args[2] = id.getTransmissionId();
        args[3] = details.getRecordStatus();
        args[4] = details.getSourceUniqueId();
        args[5] = details.getSourceCode();
        args[6] = details.getFilerDemoSeq();
        args[7] = details.getUpdatedDate();
        args[8] = details.getUpdatedBy();
        return args;
    }
}
