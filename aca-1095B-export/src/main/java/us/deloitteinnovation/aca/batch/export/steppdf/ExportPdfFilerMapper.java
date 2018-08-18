package us.deloitteinnovation.aca.batch.export.steppdf;

import static us.deloitteinnovation.aca.constants.CommonDataConstants.APR;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.AUG;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.DEC;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.FEB;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.JAN;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.JUL;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.JUN;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.MAR;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.MAY;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.NOV;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.OCT;
import static us.deloitteinnovation.aca.constants.CommonDataConstants.SEP;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import us.deloitteinnovation.aca.constants.CommonEntityConstants;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.model.FilerExportPrintVendorMapper;

public class ExportPdfFilerMapper  implements RowMapper<Filer> {

	 private static Logger LOGGER = LoggerFactory.getLogger(ExportPdfFilerMapper.class);

	 private static Logger logger = LoggerFactory.getLogger(FilerExportPrintVendorMapper.class);

	    @Override
	    public Filer mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Filer filer = new Filer();
	        filer.setSourceCd(rs.getString("SOURCE_CD"));
	        filer.setSourceUniqueId(rs.getLong("SOURCE_UNIQUE_ID"));

	        if (logger.isDebugEnabled()) {
	            logger.debug("FilerMapper on source cd {} and source unique id {}", filer.getSourceCd(), filer.getSourceUniqueId()) ;
	        }

	        String name ;
	        if (rs.getString("RECIPIENT_MIDDLE_NAME") == null )
	            name = rs.getString("RECIPIENT_FIRST_NAME") +" "+ rs.getString("RECIPIENT_LAST_NAME");
	        else
	            name = rs.getString("RECIPIENT_FIRST_NAME") +" "+rs.getString("RECIPIENT_MIDDLE_NAME")+" "+ rs.getString("RECIPIENT_LAST_NAME");

	        filer.setRecipientFirstName(rs.getString("RECIPIENT_FIRST_NAME"));
	        filer.setRecipientLastName(rs.getString("RECIPIENT_LAST_NAME"));
	        filer.setRecipientMiddleName(rs.getString("RECIPIENT_MIDDLE_NAME"));
	        filer.setRecipientSuffix(rs.getString("RECIPIENT_SUFFIX_NAME"));
	        filer.setRecipientTIN(rs.getString("RECIPIENT_TIN"));
	        filer.setTaxYear(rs.getString("TAX_YEAR"));
	        filer.setShopIdentifier(rs.getString("SHOP_IDENTIFIER"));
	        filer.setFilerDemoSeq(rs.getLong("FILER_DEMO_SEQ"));
	        String ssn = rs.getString("RECIPIENT_SSN");
	        filer.setJan(determineMonthStatus(rs.getString(JAN)));
	        filer.setFeb(determineMonthStatus(rs.getString(FEB)));
	        filer.setMar(determineMonthStatus(rs.getString(MAR)));
	        filer.setApr(determineMonthStatus(rs.getString(APR)));
	        filer.setMay(determineMonthStatus(rs.getString(MAY)));
	        filer.setJun(determineMonthStatus(rs.getString(JUN)));
	        filer.setJul(determineMonthStatus(rs.getString(JUL)));
	        filer.setAug(determineMonthStatus(rs.getString(AUG)));
	        filer.setSep(determineMonthStatus(rs.getString(SEP)));
	        filer.setOct(determineMonthStatus(rs.getString(OCT)));
	        filer.setNov(determineMonthStatus(rs.getString(NOV)));
	        filer.setDec(determineMonthStatus(rs.getString(DEC)));
	        filer.setId(rs.getInt("FILER_DEMO_SEQ"));
	        filer.setFilerStatus(rs.getString("FILER_STATUS"));
	        filer.setRecipientName(name);
	        filer.setRecipientDOB(rs.getDate("RECIPIENT_DOB").toString());
	        filer.setRecipientSSN(ssn);
	        filer.setRecipientAddLine1(rs.getString("RECIPIENT_ADDRESS_LINE_1"));
	        filer.setRecipientAddLine2(rs.getString("RECIPIENT_ADDRESS_LINE_2"));
	        filer.setRecipientCity(rs.getString("RECIPIENT_CITY"));
	        filer.setRecipientState(rs.getString("RECIPIENT_STATE"));
	        filer.setRecipientZip(rs.getString("RECIPIENT_ZIP_5"));
	        filer.setRecipientZip4(rs.getString("RECIPIENT_ZIP_4"));
	        filer.setProviderName(rs.getString("PROVIDER_NAME"));
	        filer.setProviderEIN(rs.getString("PROVIDER_IDENTIFICATION_NUMBER"));
	        filer.setProviderAddLine1(rs.getString("PROVIDER_ADDRESS_LINE_1"));
	        filer.setProviderAddLine2(rs.getString("PROVIDER_ADDRESS_LINE_2"));
	        filer.setProviderCity(rs.getString("PROVIDER_CITY_OR_TOWN"));
	        filer.setProviderState(rs.getString("PROVIDER_STATE_OR_PROVINCE"));
	        filer.setProviderZip(rs.getString("PROVIDER_ZIP_OR_POSTAL_CODE"));
	        filer.setProviderContactNo(rs.getLong("PROVIDER_CONTACT_NO"));
	        filer.setPolicyOrigin(rs.getString("POLICY_ORIGIN"));
	        filer.setShopIdentifier(rs.getString("SHOP_IDENTIFIER"));
	        filer.setProviderZip(rs.getString("PROVIDER_ZIP_OR_POSTAL_CODE"));
	        filer.setProviderCountry(rs.getString("PROVIDER_COUNTRY"));
	        filer.setProviderAddLine2(rs.getString("PROVIDER_ADDRESS_LINE_2"));
	        filer.setResponsiblePersonUniqueId(rs.getString("RESPONSIBLE_PERSON_UNIQUE_ID"));
	        filer.setFormStatus(rs.getString("FORM_STATUS"));
	        filer.setIrsTransmittalStatus(rs.getString("IRS_TRANSMISSION_STATUS_CD"));
	        filer.setCorrectionIndicator(rs.getInt("CORRECTION_INDICATOR"));
	        filer.setUpdatedBy(rs.getString("UPDATED_BY"));
	        filer.setEmployerName(rs.getString("EMPLOYER_NAME"));
	        filer.setEmployerEIN(rs.getString("EMPLOYER_IDENTIFICATION_NUMBER"));
	        filer.setEmployerAddressLine1(rs.getString("EMPLOYER_ADDRESS_LINE_1"));
	        filer.setEmployerAddressLine2(rs.getString("EMPLOYER_ADDRESS_LINE_2"));
	        filer.setEmployerCity(rs.getString("EMPLOYER_CITY_OR_TOWN"));
	        filer.setEmployerState(rs.getString("EMPLOYER_STATE_OR_PROVINCE"));
	        filer.setEmployerZIP(rs.getString("EMPLOYER_ZIP_OR_POSTAL_CODE"));
	        return filer;
	    }
	    
	    /**
	     *
	     * @param monthValue
	     * @return Returns CommonEntityConstants.COVERED if the monthValue equals COVERED.  Otherwise returns UNCOVERED.
	     */
	    protected String determineMonthStatus(String monthValue) {
	        if (CommonEntityConstants.COVERED.equals(monthValue))
	            return CommonEntityConstants.COVERED ;
	        return CommonEntityConstants.UNCOVERED ;
	    }
}
