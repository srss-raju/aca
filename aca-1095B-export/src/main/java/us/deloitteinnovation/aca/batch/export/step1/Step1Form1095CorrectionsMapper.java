package us.deloitteinnovation.aca.batch.export.step1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095BPK;
import us.deloitteinnovation.aca.model.FilerWithCountCoveredMapper;
import us.deloitteinnovation.aca.model.FilerWithExportCountCoveredMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reads correction specific information along with Filer Demographics.
 * @see FilerWithCountCoveredMapper
 */
@Component
public class Step1Form1095CorrectionsMapper implements RowMapper<Step1Form1095Dto> {

   /* @Autowired
    FilerWithCountCoveredMapper filerMapper ;*/

    @Autowired
    FilerWithExportCountCoveredMapper filerWithCountCoveredExportMapper;

    @Override
    public Step1Form1095Dto mapRow(ResultSet resultSet, int i) throws SQLException {
        Step1Form1095Dto dto = new Step1Form1095Dto() ;
        dto.filer = filerWithCountCoveredExportMapper.mapRow(resultSet, i) ;

        String receiptId = "";
        IrsRecordDetails1095BPK id = new IrsRecordDetails1095BPK() ;
        id.setRecordId(resultSet.getInt("RECORD_ID"));
        id.setSubmissionId(resultSet.getInt("SUBMISSION_ID"));
        id.setTransmissionId(resultSet.getInt("TRANSMISSION_ID"));
        dto.irsRecordDetails1095BPK = id ;

        receiptId = resultSet.getString("TRANSMISSION_RECEIPT_ID") ;

        if(StringUtils.isNotBlank(receiptId)){
            dto.receiptId =receiptId ;
        }else{
            dto.receiptId = " "; //TODO will receipt ID be always provided even through file ingestion?
        }


        return dto ;
    }
}
