package us.deloitteinnovation.aca.batch.dataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tthakore on 1/8/2016.
 */

@Service("irsTransmittalDetailsDS")
public class IrsTransmittalDetailsDS {



    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static Logger logger = LoggerFactory.getLogger(IrsTransmittalDetailsDS.class);
    public IrsTransmittalDetailsDS(){}

    public IrsTransmittalDetailsDS(JdbcTemplate jdbcTemplate){
        jdbcTemplate = this.jdbcTemplate;
    }


    public String getTransmitStatus(String sourceCd, long sourceUniqueId)
    {
        String transmitStatus = "";
        try{

        // List<String> transmitStatusList =  this.jdbcTemplate.queryForList("select TRANSMIT_STATUS from IRS_TRANSMITTAL_DETAILS where SOURCE_CD = '" + sourceCd + "'  and source_unique_id = " + sourceUniqueId, String.class);

            StringBuilder builder = new StringBuilder("select TRANSMISSION_RECEIPT_ID from irs_transmission_details itd inner join ");
            builder.append("irs_record_details_1095b ird on ird.TRANSMISSION_ID = itd.TRANSMISSION_ID inner join ");
            builder.append("filer_demographics fd on fd.source_unique_id = ird.source_unique_id and fd.source_cd = ird.source_cd ");
            builder.append("where fd.source_unique_id = '"+sourceUniqueId+"' and fd.source_cd = '"+sourceCd+"' order by itd.transfer_date DESC");
            List<String> transmitStatusList =  this.jdbcTemplate.queryForList(builder.toString(), String.class);

            if (transmitStatusList.isEmpty()) {
               transmitStatus = "";
            } else {
                transmitStatus = (transmitStatusList.get(0) != null &&  ((String)transmitStatusList.get(0)).length() > 0)?"COMPLETE":"";
            }
        }catch(Exception ex){
            logger.error("--------------------------------------------------------------------------");
            logger.error("Error in IrsTransmittalDetailsDS class method getTransmitStatus()", ex.getStackTrace().toString());
            logger.error("--------------------------------------------------------------------------");
        }
        return transmitStatus;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
