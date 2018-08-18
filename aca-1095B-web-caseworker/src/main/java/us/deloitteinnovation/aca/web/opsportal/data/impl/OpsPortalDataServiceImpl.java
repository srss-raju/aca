package us.deloitteinnovation.aca.web.opsportal.data.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.constants.XmlConstants;
import us.deloitteinnovation.aca.web.opsportal.data.IOpsPortalDataService;
import us.deloitteinnovation.aca.web.opsportal.dto.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by sdalavi on 3/28/2016.
 */
@Service("opsPortalService")
public class OpsPortalDataServiceImpl implements IOpsPortalDataService {
    private static final Logger logger = LoggerFactory.getLogger(OpsPortalDataServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<StateDto> getTransmissionStates() {
        String query = "select DISTINCT STATE_ABBREVIATION, STATE_NAME from SOURCE_SYSTEM_CONFIG WHERE STATE_NAME is not NULL";
        List<StateDto> stateDtos =this.jdbcTemplate.query(query, new Object[]{} , new StateMapper());
        return stateDtos;
    }

    @Override
    public List<TaxYearDto> getTransmissionYears() {
        String query = "select DISTINCT TAX_YEAR from SOURCE_SYSTEM_CONFIG WHERE STATE_NAME is not NULL ORDER BY TAX_YEAR DESC";
        return this.jdbcTemplate.query(query, new Object[]{}, new TaxYearMapper());
    }

    public List<StatusDto> getTransmissionStatuses(String typeCd) {
        List<StatusDto> statusDtos =this.jdbcTemplate.query("select STATUS_CD, STATUS_DESC, TYPE_CD" +
                " from IRS_TRANSMISSION_STATUS WHERE TYPE_CD LIKE ?", new Object[]{typeCd},
                new StatusMapper());
        return statusDtos;
    }

    @Override
    public List<IrsTransmittalDetailsDto> getTransmissionRecords(String stateCd, int taxYear) {
        String query = "select" +
                " TRANSMISSION_ID, TRANSFER_DATE, TRANSMISSION_FILE_NAME, TRANSMISSION_RECEIPT_ID, TRANSMISSION_DATE," +
                " TRANSMISSION_ACK_DATE, TRANSMISSION_ACK_STATUS, UPDATED_DATE, UPDATED_BY, RECORD_VISIBLE, TAX_YEAR" +
                " from IRS_TRANSMISSION_DETAILS" +
                " WHERE SUBSTRING(SOURCE_CD, 1, 2) = ?" +
                " AND TAX_YEAR = ?" +
                " ORDER BY TRANSFER_DATE DESC";
        List<IrsTransmittalDetailsDto> irsTransmittalDetailsDtos = this.jdbcTemplate.query(query,
                new Object[]{stateCd, taxYear}, new IrsTransmittalDetailsMapper());
        return irsTransmittalDetailsDtos;
    }

    @Transactional
    private int checkReceipt(JdbcTemplate jdbcTemplate, IrsTransmittalDetailsDto irsTransmittalDetailsDto) {
        // check for duplicate receipt id
        String checkReceiptSql = "SELECT count(*) as COUNT_RECEIPT from IRS_TRANSMISSION_DETAILS" +
                " where TRANSMISSION_ID != ? and TRANSMISSION_RECEIPT_ID = ?";
        int countReceipt = jdbcTemplate.queryForObject(checkReceiptSql,
                new Object[]{irsTransmittalDetailsDto.getTransmissionId(), irsTransmittalDetailsDto.getTransmissionReceiptId()},
                Integer.class);
        return countReceipt;
    }

    @Transactional
    public int saveTransmissionRecord(IrsTransmittalDetailsDto irsTransmittalDetailsDto) {
        SingleConnectionDataSource singleConnectionDataSource = null;
        JdbcTemplate singleConnJdbcTemplate;
        int checkReceipt = 0;
        try {
            singleConnectionDataSource = new SingleConnectionDataSource(this.jdbcTemplate.getDataSource().getConnection(), true);
            singleConnJdbcTemplate = new JdbcTemplate(singleConnectionDataSource);

            checkReceipt = this.checkReceipt(singleConnJdbcTemplate, irsTransmittalDetailsDto);
            if(checkReceipt > 0) {
                return checkReceipt;
            }
            final String recordStatus;
            if(irsTransmittalDetailsDto.getTransmissionAckStatus().equals(XmlConstants.STATUS_CD_AE)
                    || irsTransmittalDetailsDto.getTransmissionAckStatus().equals(XmlConstants.STATUS_CD_PA)) {;
                recordStatus = XmlConstants.STATUS_CD_PE;
            } else {
                recordStatus = irsTransmittalDetailsDto.getTransmissionAckStatus();
            }

            // update FILER_DEMOGRAPHICS records
            logger.info("before SET Context_Info 0x555555");
            singleConnJdbcTemplate.execute("SET Context_Info 0x555555");
            logger.info("after SET Context_Info 0x555555");

            StringBuilder filerUpdateSql = new StringBuilder("UPDATE FD set FD.IRS_TRANSMISSION_STATUS_CD = ?,");
            filerUpdateSql.append(" FD.UPDATED_DATE = ?, FD.UPDATED_BY = ? FROM FILER_DEMOGRAPHICS FD INNER JOIN");
            filerUpdateSql.append(" IRS_RECORD_DETAILS_1095B R ON R.SOURCE_UNIQUE_ID = FD.SOURCE_UNIQUE_ID AND R.SOURCE_CD = FD.SOURCE_CD");
            filerUpdateSql.append(" WHERE R.TRANSMISSION_ID = ? AND FD.IRS_TRANSMISSION_STATUS_CD != ? AND FD.TAX_YEAR = ?");
            int update = singleConnJdbcTemplate.update(filerUpdateSql.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, recordStatus);
                    ps.setTimestamp(2, new Timestamp(irsTransmittalDetailsDto.getUpdatedDate().getTime()));
                    ps.setString(3, irsTransmittalDetailsDto.getUpdatedBy());
                    ps.setLong(4, irsTransmittalDetailsDto.getTransmissionId());
                    ps.setString(5, XmlConstants.STATUS_CD_CO);
                    ps.setInt(6, irsTransmittalDetailsDto.getTaxYear());
                }
            });

            logger.info("before SET Context_Info 0x0");
            singleConnJdbcTemplate.execute("SET Context_Info 0x0");
            logger.info("after SET Context_Info 0x0");

            // update IRS_RECORD_DETAILS_1095B records
            StringBuilder recordsUpdateSql = new StringBuilder("UPDATE IRS_RECORD_DETAILS_1095B set RECORD_STATUS = ?, UPDATED_DATE = ?,");
            recordsUpdateSql.append(" UPDATED_BY = ? WHERE TRANSMISSION_ID = ?");
            update = singleConnJdbcTemplate.update(recordsUpdateSql.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, recordStatus);
                    ps.setTimestamp(2, new Timestamp(irsTransmittalDetailsDto.getUpdatedDate().getTime()));
                    ps.setString(3, irsTransmittalDetailsDto.getUpdatedBy());
                    ps.setLong(4, irsTransmittalDetailsDto.getTransmissionId());
                }
            });

            // only update when the status is rejected
            if(irsTransmittalDetailsDto.getTransmissionAckStatus().equals(XmlConstants.STATUS_CD_RJ)) {
                // update IRS_1095_XML records
                StringBuilder xmlUpdateSql = new StringBuilder("UPDATE XML set XML.IRS_TRANSMISSION_STATUS_CD = ?,");
                xmlUpdateSql.append(" XML.UPDATED_DATE = ?, XML.UPDATED_BY = ? FROM IRS_1095_XML XML INNER JOIN");
                xmlUpdateSql.append(" IRS_RECORD_DETAILS_1095B R ON R.SOURCE_UNIQUE_ID = XML.SOURCE_UNIQUE_ID AND R.SOURCE_CD = XML.SOURCE_CD");
                xmlUpdateSql.append(" WHERE R.TRANSMISSION_ID = ? AND XML.TAX_YEAR = ?");
                update = singleConnJdbcTemplate.update(xmlUpdateSql.toString(), new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, XmlConstants.STATUS_CD_RP);
                        ps.setTimestamp(2, new Timestamp(irsTransmittalDetailsDto.getUpdatedDate().getTime()));
                        ps.setString(3, irsTransmittalDetailsDto.getUpdatedBy());
                        ps.setLong(4, irsTransmittalDetailsDto.getTransmissionId());
                        ps.setInt(5, irsTransmittalDetailsDto.getTaxYear());
                    }
                });
            }

            // update IRS_SUBMISSION_DETAILS records
            StringBuilder submissionUpdateSql = new StringBuilder("UPDATE IRS_SUBMISSION_DETAILS set SUBMISSION_STATUS = ?, UPDATED_DATE = ?,");
            submissionUpdateSql.append(" UPDATED_BY = ? WHERE TRANSMISSION_ID = ?");
            update = singleConnJdbcTemplate.update(submissionUpdateSql.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, irsTransmittalDetailsDto.getTransmissionAckStatus());
                    ps.setTimestamp(2, new Timestamp(irsTransmittalDetailsDto.getUpdatedDate().getTime()));
                    ps.setString(3, irsTransmittalDetailsDto.getUpdatedBy());
                    ps.setLong(4, irsTransmittalDetailsDto.getTransmissionId());
                }
            });

            // update IRS_TRANSMISSION_DETAILS record
            StringBuilder transmissionUpdateSql = new StringBuilder("UPDATE IRS_TRANSMISSION_DETAILS set");
            transmissionUpdateSql.append(" TRANSMISSION_RECEIPT_ID = ?, TRANSMISSION_ACK_DATE = ?, TRANSMISSION_ACK_STATUS = ?, RECORD_VISIBLE = ?,");
            transmissionUpdateSql.append(" UPDATED_DATE = ?, UPDATED_BY = ?");
            if(irsTransmittalDetailsDto.getTransmissionAckStatus().equals(XmlConstants.STATUS_CD_PR)) {
                transmissionUpdateSql.append( ", TRANSMISSION_DATE = ?");
            }
            transmissionUpdateSql.append(" WHERE TRANSMISSION_ID = ?");
            update = singleConnJdbcTemplate.update(transmissionUpdateSql.toString(), new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    int count = 1;
                    ps.setString(count++, irsTransmittalDetailsDto.getTransmissionReceiptId());
                    ps.setTimestamp(count++, new Timestamp(irsTransmittalDetailsDto.getTransmissionAckDate().getTime()));
                    ps.setString(count++, irsTransmittalDetailsDto.getTransmissionAckStatus());
                    ps.setBoolean(count++, irsTransmittalDetailsDto.isRecordVisible());
                    ps.setTimestamp(count++, new Timestamp(irsTransmittalDetailsDto.getUpdatedDate().getTime()));
                    ps.setString(count++, irsTransmittalDetailsDto.getUpdatedBy());
                    if(irsTransmittalDetailsDto.getTransmissionAckStatus().equals(XmlConstants.STATUS_CD_PR)) {
                        ps.setTimestamp(count++, new Timestamp(irsTransmittalDetailsDto.getTransmissionAckDate().getTime()));
                    }
                    ps.setLong(count++, irsTransmittalDetailsDto.getTransmissionId());
                }
            });

        } catch (SQLException e) {
            logger.error("error occurred: ", e);
        } finally {
            if(singleConnectionDataSource != null) {
                singleConnectionDataSource.destroy();
            }
        }

        return checkReceipt;
    }

    @Transactional
    public int rejectResendCorrection(Long tranmissionId, String recordStatus, String userName, int taxYear) {
        SingleConnectionDataSource singleConnectionDataSource = null;
        JdbcTemplate singleConnJdbcTemplate;
        int countCorrection = 0;
        try {
            singleConnectionDataSource = new SingleConnectionDataSource(this.jdbcTemplate.getDataSource().getConnection(), true);
            singleConnJdbcTemplate = new JdbcTemplate(singleConnectionDataSource);
            // check if there is any corrected records on Filer Demographics on reject resend
            if(recordStatus.equals(XmlConstants.STATUS_CD_RR)) {
                StringBuilder filerCheck = new StringBuilder("SELECT count(*) as COUNT_FILER");
                filerCheck.append(" from FILER_DEMOGRAPHICS FD");
                filerCheck.append(" INNER JOIN IRS_RECORD_DETAILS_1095B R");
                filerCheck.append(" ON R.SOURCE_UNIQUE_ID = FD.SOURCE_UNIQUE_ID AND R.SOURCE_CD = FD.SOURCE_CD");
                filerCheck.append(" WHERE R.TRANSMISSION_ID = ? and FD.IRS_TRANSMISSION_STATUS_CD = ? and FD.TAX_YEAR = ?");
                countCorrection = singleConnJdbcTemplate.queryForObject(filerCheck.toString(),
                        new Object[]{tranmissionId, XmlConstants.STATUS_CD_CO, new Integer(taxYear)}, Integer.class);
                if(countCorrection > 0) {
                    return countCorrection;
                }
            }
            Date currentDate = new Date();
            // update IRS_RECORD_DETAILS_1095B records
            StringBuilder recordsUpdateSql = new StringBuilder("UPDATE IRS_RECORD_DETAILS_1095B set RECORD_STATUS = ?, UPDATED_DATE = ?,");
            recordsUpdateSql.append(" UPDATED_BY = ? WHERE TRANSMISSION_ID = ?");
            int update = singleConnJdbcTemplate.update(recordsUpdateSql.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, recordStatus);
                    ps.setTimestamp(2, new Timestamp(currentDate.getTime()));
                    ps.setString(3, userName);
                    ps.setLong(4, tranmissionId);
                }
            });
            // update FILER_DEMOGRAPHICS records
            singleConnJdbcTemplate.execute("SET Context_Info 0x555555");
            StringBuilder filerUpdateSql = new StringBuilder("UPDATE FD set FD.IRS_TRANSMISSION_STATUS_CD = ?,");
            filerUpdateSql.append(" FD.UPDATED_DATE = ?, FD.UPDATED_BY = ? FROM FILER_DEMOGRAPHICS FD INNER JOIN");
            filerUpdateSql.append(" IRS_RECORD_DETAILS_1095B R ON R.SOURCE_UNIQUE_ID = FD.SOURCE_UNIQUE_ID AND R.SOURCE_CD = FD.SOURCE_CD");
            filerUpdateSql.append(" WHERE R.TRANSMISSION_ID = ? AND FD.TAX_YEAR = ?");
            update = singleConnJdbcTemplate.update(filerUpdateSql.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, recordStatus);
                    ps.setTimestamp(2, new Timestamp(currentDate.getTime()));
                    ps.setString(3, userName);
                    ps.setLong(4, tranmissionId);
                    ps.setInt(5, taxYear);
                }
            });
            singleConnJdbcTemplate.execute("SET Context_Info 0x0");
        } catch (SQLException e) {
            logger.error("error occurred: ", e);
        } finally {
            if(singleConnectionDataSource != null) {
                singleConnectionDataSource.destroy();
            }
        }
        return countCorrection;
    }
}
