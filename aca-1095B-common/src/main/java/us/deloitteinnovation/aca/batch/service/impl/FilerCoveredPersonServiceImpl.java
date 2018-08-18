package us.deloitteinnovation.aca.batch.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import us.deloitteinnovation.aca.batch.service.FilerCoveredPersonService;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.CoveredPersonMapper;
import us.deloitteinnovation.aca.model.Filer;

import java.util.List;

/**
 * Created by rgopalani on 12/28/2015.
 */

public class FilerCoveredPersonServiceImpl implements FilerCoveredPersonService {
    private static final Logger logger =
            LoggerFactory.getLogger(FilerCoveredPersonServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CoveredPerson> getCoveredPersonList(long responsiblePersonUniqueId, String taxYear, String sourceCd){
        List<CoveredPerson> rows = null;
        String sqlStatment =
                "SELECT * FROM " +
                "FILER_DEMOGRAPHICS " +
                "WHERE " +
                "RESPONSIBLE_PERSON_UNIQUE_ID = " + responsiblePersonUniqueId + " " +
                "AND FILER_STATUS = 'C' " +
                "AND STATUS = 'ACTIVE' " +
                "AND SOURCE_CD = '"+ sourceCd +"' AND TAX_YEAR="+taxYear;
        try{
            //TODO Change this call to use a PreparedStatment instead of the regular Statement
            rows = jdbcTemplate.query(sqlStatment, new CoveredPersonMapper());
        }catch(Exception ex){
            logger.error("", ex);
        }
        return rows;
    }

	@Override
	public List<CoveredPerson> getCoveredPersonList(Filer filer) {
		List<CoveredPerson> rows = null;
        String sqlStatment = "SELECT * FROM FILER_DEMOGRAPHICS WHERE RESPONSIBLE_PERSON_UNIQUE_ID = " + filer.getSourceUniqueId() + " AND FILER_STATUS = 'C' AND STATUS = 'ACTIVE'";
        try{
            //TODO Change this call to use a PreparedStatment instead of the regular Statement
            rows = jdbcTemplate.query(sqlStatment, new CoveredPersonMapper());
        }catch(Exception ex){
            logger.error("", ex);
        }
        return rows;
	}
}
