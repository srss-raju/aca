package us.deloitteinnovation.aca.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.constants.CommonDataConstants;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SourceSystemConfigMapper implements RowMapper<SourceSystemConfig> {

    @Override
    public SourceSystemConfig mapRow(ResultSet resultSet, int i) throws SQLException {
        SourceSystemConfig sourceSystemConfig = new SourceSystemConfig();
        sourceSystemConfig.setUpdatedDate(resultSet.getDate(CommonDataConstants.UPDATED_DATE));
        sourceSystemConfig.setUpdatedBy(resultSet.getString(CommonDataConstants.UPDATED_BY));
        sourceSystemConfig.setSourceCd(resultSet.getString(CommonDataConstants.SOURCE_CD));
        sourceSystemConfig.setSourceCdId(resultSet.getInt(CommonDataConstants.SOURCE_CD_ID));
        sourceSystemConfig.setSupportViewPdf(resultSet.getString(CommonDataConstants.SUPPORT_VIEW_PDF));
        sourceSystemConfig.setNotFoundMessage(resultSet.getString(CommonDataConstants.NOT_FOUND_MESSAGE));
        sourceSystemConfig.setIncorrectAddressMessage(resultSet.getString(CommonDataConstants.INCORRECT_ADDRESS_MESSAGE));
        sourceSystemConfig.setReturnAddressLine1(resultSet.getString(CommonDataConstants.RETURN_ADDRESS_LINE1));
        sourceSystemConfig.setReturnAddressLine2(resultSet.getString(CommonDataConstants.RETURN_ADDRESS_LINE2));
        sourceSystemConfig.setReturnAddressCity(resultSet.getString(CommonDataConstants.RETURN_ADDRESS_CITY));
        sourceSystemConfig.setReturnAddressState(resultSet.getString(CommonDataConstants.RETURN_ADDRESS_STATE));
        sourceSystemConfig.setReturnAddressZip(resultSet.getString(CommonDataConstants.RETURN_ADDRESS_ZIP));
        sourceSystemConfig.setTransmitterControlCode(resultSet.getString(CommonDataConstants.TRANSMITTER_CONTROL_CODE));
        sourceSystemConfig.setPrintPreferences(resultSet.getString(CommonDataConstants.PRINT_PREFERENCES));
        sourceSystemConfig.setLanguagePreferences(resultSet.getString(CommonDataConstants.LANGUAGE_PREFERENCE));
        sourceSystemConfig.setStateAbbreviation(resultSet.getString(CommonDataConstants.STATE_ABBREVIATION));
        sourceSystemConfig.setProviderName(resultSet.getString(CommonDataConstants.PROVIDER_NAME));
        sourceSystemConfig.setProviderIdentificationNumber(resultSet.getString(CommonDataConstants.PROVIDER_IDENTIFICATION_NUMBER));
        sourceSystemConfig.setProviderContactFirstName(resultSet.getString(CommonDataConstants.PROVIDER_CONTACT_FIRST_NAME));
        sourceSystemConfig.setProviderContactLastName(resultSet.getString(CommonDataConstants.PROVIDER_CONTACT_LAST_NAME));
        sourceSystemConfig.setProviderContactNo(resultSet.getLong(CommonDataConstants.PROVIDER_CONTACT_NO));
        sourceSystemConfig.setProviderAddressLine1(resultSet.getString(CommonDataConstants.PROVIDER_ADDRESS_LINE_1));
        sourceSystemConfig.setProviderAddressLine2(resultSet.getString(CommonDataConstants.PROVIDER_ADDRESS_LINE_2));
        sourceSystemConfig.setProviderCityOrTown(resultSet.getString(CommonDataConstants.PROVIDER_CITY_OR_TOWN));
        sourceSystemConfig.setProviderStateOrProvince(resultSet.getString(CommonDataConstants.PROVIDER_STATE_OR_PROVINCE));
        sourceSystemConfig.setProviderCountry(resultSet.getString(CommonDataConstants.PROVIDER_COUNTRY));
        sourceSystemConfig.setProviderZipOrPostalCode(resultSet.getString(CommonDataConstants.PROVIDER_ZIP_OR_POSTAL_CODE));
        sourceSystemConfig.setTestTcc(resultSet.getString(CommonDataConstants.TEST_TCC));
        sourceSystemConfig.setDeloitteTestTcc(resultSet.getString(CommonDataConstants.DELOITTE_TEST_TCC));
        sourceSystemConfig.setSoftwareId(resultSet.getString(CommonDataConstants.SOFTWARE_ID));
        sourceSystemConfig.setTaxYear(resultSet.getInt(CommonDataConstants.TAX_YEAR));
        return sourceSystemConfig;
    }
}