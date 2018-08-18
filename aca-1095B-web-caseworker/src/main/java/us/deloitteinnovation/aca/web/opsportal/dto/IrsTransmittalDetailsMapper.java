package us.deloitteinnovation.aca.web.opsportal.dto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sdalavi on 3/31/2016.
 */
public class IrsTransmittalDetailsMapper  implements RowMapper<IrsTransmittalDetailsDto> {
    @Override
    public IrsTransmittalDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        IrsTransmittalDetailsDto irsTransmittalDetailsDto = new IrsTransmittalDetailsDto();
        irsTransmittalDetailsDto.setTransmissionId(rs.getLong("TRANSMISSION_ID"));
        irsTransmittalDetailsDto.setTransferDate(rs.getTimestamp("TRANSFER_DATE"));
        irsTransmittalDetailsDto.setTransmissionFileName(rs.getString("TRANSMISSION_FILE_NAME"));
        irsTransmittalDetailsDto.setTransmissionReceiptId(rs.getString("TRANSMISSION_RECEIPT_ID"));
        irsTransmittalDetailsDto.setTransmissionDate(rs.getTimestamp("TRANSMISSION_DATE"));
        irsTransmittalDetailsDto.setTransmissionAckStatus(rs.getString("TRANSMISSION_ACK_STATUS"));
        irsTransmittalDetailsDto.setTransmissionAckDate(rs.getTimestamp("TRANSMISSION_ACK_DATE"));
        irsTransmittalDetailsDto.setUpdatedDate(rs.getTimestamp("UPDATED_DATE"));
        irsTransmittalDetailsDto.setUpdatedBy(rs.getString("UPDATED_BY"));
        irsTransmittalDetailsDto.setRecordVisible(rs.getBoolean("RECORD_VISIBLE"));
        irsTransmittalDetailsDto.setTaxYear(rs.getInt("TAX_YEAR"));
        return irsTransmittalDetailsDto;
    }
}