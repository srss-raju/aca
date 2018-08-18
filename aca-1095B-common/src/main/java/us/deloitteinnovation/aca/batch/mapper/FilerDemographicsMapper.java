package us.deloitteinnovation.aca.batch.mapper;

import org.springframework.jdbc.core.RowMapper;
import us.deloitteinnovation.aca.batch.dto.BatchInfoDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static us.deloitteinnovation.aca.batch.constants.BatchConstants.*;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.*;

/**
 * Created by rgopalani on 10/21/2015.
 */
public class FilerDemographicsMapper implements RowMapper<FilerDemographicDto> {

    @Override
    public FilerDemographicDto mapRow(final ResultSet rs, int i) throws SQLException {

        final FilerDemographicDto fd = new FilerDemographicDto();

        final FilerDemographicPKDto filerDemographicPKDto = new FilerDemographicPKDto();
        final Long sourceUniqueId = rs.getLong(SOURCE_UNIQUE_ID);
        filerDemographicPKDto.setSourceUniqueId(sourceUniqueId.toString());
        final String sourceCd = rs.getString(SOURCE_CD);
        filerDemographicPKDto.setSourceCd(sourceCd);
        filerDemographicPKDto.setTaxYear(rs.getString(us.deloitteinnovation.aca.constants.CommonDataConstants.TAX_YEAR));
        fd.setId(filerDemographicPKDto);

        final BatchInfoDto batchInfo = new BatchInfoDto();

        final int batchId = rs.getInt(BATCH_ID);
        batchInfo.setBatchId(batchId);
        fd.setBatchInfo(batchInfo);

        fd.setRecipientFirstName(rs.getString(CommonDataConstants.RECIPIENT_FIRST_NAME));
        fd.setRecipientMiddleName(rs.getString(CommonDataConstants.RECIPIENT_MIDDLE_NAME));
        fd.setRecipientFirstName(rs.getString(CommonDataConstants.RECIPIENT_FIRST_NAME));
        fd.setRecepientSuffixName(rs.getString(RECIPIENT_NAME_SUFFIX));
        fd.setRecipientSsn(rs.getString(RECIPIENT_SSN));
        fd.setRecipientTin(rs.getString(RECIPIENT_TIN));

        Date recipientDob = rs.getDate(RECIPIENT_DOB);
        fd.setRecipientDob(recipientDob);
        fd.setRecipientDobStr(MMDDYYYY.format(recipientDob));

        fd.setRecipientAddressLine1(rs.getString(CommonDataConstants.RECIPIENT_ADDRESS_LINE_1));
        fd.setRecipientAddressLine2(rs.getString(CommonDataConstants.RECIPIENT_ADDRESS_LINE_2));
        fd.setRecipientCity(rs.getString(CommonDataConstants.RECIPIENT_CITY));
        fd.setRecipientState(rs.getString(RECIPIENT_STATE));
        fd.setRecipientZip5(rs.getString(CommonDataConstants.RECIPIENT_ZIP_5));
        fd.setRecipientZip4(rs.getString(RECIPIENT_ZIP_4));



        return fd;
    }
}
