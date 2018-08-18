package us.deloitteinnovation.aca.batch.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import us.deloitteinnovation.aca.batch.service.PrintVendorFilerCoveredPersonService;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.CoveredPersonMapper;
import us.deloitteinnovation.aca.model.Filer;

/**
 * Created by RajeshKumar B on 10/28/2016.
 */

public class PrintVendorFilerCoveredPersonServiceImpl implements PrintVendorFilerCoveredPersonService {
    private static final Logger logger =
            LoggerFactory.getLogger(PrintVendorFilerCoveredPersonServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CoveredPerson> getCoveredPersonList(long responsiblePersonUniqueId, String taxYear, String sourceCd){
        List<CoveredPerson> rows = null;
        String sqlStatment = "SELECT * FROM FILER_DEMOGRAPHICS WHERE RESPONSIBLE_PERSON_UNIQUE_ID = " + responsiblePersonUniqueId + " AND FILER_STATUS = 'C' AND STATUS = 'ACTIVE'";
        try{
            rows = jdbcTemplate.query(sqlStatment, new CoveredPersonMapper());
        }catch(Exception ex){
            logger.error("", ex);
        }
        return rows;
    }

	@Override
	public List<CoveredPerson> getCoveredPersonList(Filer filer) {
		List<CoveredPerson> rows = null;
        String sqlStatment = "SELECT * FROM FILER_DEMOGRAPHICS WHERE RESPONSIBLE_PERSON_UNIQUE_ID = " + filer.getSourceUniqueId() + " AND FILER_STATUS = 'C' AND STATUS = 'ACTIVE' AND TAX_YEAR = '"+filer.getTaxYear()+"' AND SOURCE_CD LIKE '%"+filer.getSourceCd()+"%'";
        try{
            rows = jdbcTemplate.query(sqlStatment, new CoveredPersonMapper());
        }catch(Exception ex){
            logger.error("", ex);
        }
        return rows;
	}
}
