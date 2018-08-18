package us.deloitteinnovation.aca.batch.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import us.deloitteinnovation.aca.batch.service.TransmissionIdReaderService;
import us.deloitteinnovation.aca.entity.BatchExportEntityConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhchaganti on 5/16/2016.
 */
public class TransmissionIdReaderServiceImpl implements TransmissionIdReaderService {

    private static final Logger logger =
            LoggerFactory.getLogger(TransmissionIdReaderService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    String statusCodeCheck;

    @Override
    public List<Integer> getTransmissionIds(String sourceCd, String jobType) {
        List<Integer>transmissionIds =  new ArrayList<>();

        switch (jobType) {
            case BatchExportEntityConstants.FilerXmlStatus.CORRECTED : statusCodeCheck = "in ('AC', 'ER') AND irsxml.IRS_TRANSMISSION_STATUS_CD = 'CO'" ;
                break;
            case BatchExportEntityConstants.FilerXmlStatus.REPLACE : statusCodeCheck = "in ('RC', 'RR')";
                break;
            default: statusCodeCheck = "= NULL";
        }

        String sqlStatement = "SELECT DISTINCT TRANSMISSION_ID FROM IRS_RECORD_DETAILS_1095B rdetails, IRS_1095_XML irsxml WHERE rdetails.SOURCE_CD = '"+sourceCd+"' AND rdetails.SOURCE_UNIQUE_ID = irsxml.SOURCE_UNIQUE_ID \n" +
                "and rdetails.RECORD_STATUS "+statusCodeCheck;

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlStatement);

            while(rowSet.next()){
                transmissionIds.add(rowSet.getInt("TRANSMISSION_ID"));
            }
        } catch(Exception ex) {
            logger.error("", ex);
        }
        return transmissionIds;
    }
}
