package us.deloitteinnovation.aca.batch.dataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;
import us.deloitteinnovation.aca.constants.CommonDataConstants;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.CoveredPersonMapper;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.model.FilerWithCountCoveredMapper;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

@Service("coveredPersonDataService")
public class CoveredPersonDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CoveredPersonDataService.class);

    public CoveredPersonDataService(){}

    public CoveredPersonDataService(JdbcTemplate jdbcTemplate){
        jdbcTemplate = this.jdbcTemplate;
    }

    public List<CoveredPerson> getCoveredPersonList(long responsiblePersonUniqueId){
        if (logger.isInfoEnabled()) {
            logger.info("getCoveredPersonList responsiblePersonUniqueId {}", responsiblePersonUniqueId) ;
        }
    	  List<CoveredPerson> rows = null;
        String sqlStatment = "SELECT * FROM FILER_DEMOGRAPHICS WHERE RESPONSIBLE_PERSON_UNIQUE_ID = ? AND FILER_STATUS = 'C'";
        try{
        	 rows = jdbcTemplate.query(sqlStatment, new Object[]{responsiblePersonUniqueId}, new CoveredPersonMapper());
        }catch(Exception ex){
            logger.error("", ex);
        }
		return rows; 	
    }

    /**
     *
     * @param sourceCd
     * @param sourceUniqueId
     * @return
     */
    public Filer getFiler(String sourceCd, long sourceUniqueId) {
        Filer filer = this.getFilerDetails(sourceCd,sourceUniqueId);
        return filer;
    }

    /**
     *
     * @param sourceCd
     * @param sourceUniqueId
     * @return
     */
    private Filer getFilerDetails(String sourceCd, long sourceUniqueId) {
        if (logger.isInfoEnabled()) {
            logger.info("getFilerDetails sourceCd {} sourceUniqueId {}", sourceCd, sourceUniqueId) ;
        }

        Filer filer =this.jdbcTemplate.queryForObject("select fd.*, countcovered from (SELECT RESPONSIBLE_PERSON_UNIQUE_ID as id,  count(RESPONSIBLE_PERSON_UNIQUE_ID) as countcovered " +
                " FROM FILER_DEMOGRAPHICS  a  GROUP BY RESPONSIBLE_PERSON_UNIQUE_ID) as mytable, FILER_DEMOGRAPHICS fd where " +
                " fd.RESPONSIBLE_PERSON_UNIQUE_ID = id and SOURCE_UNIQUE_ID=? and SOURCE_CD=? and (FILER_STATUS='R' or FILER_STATUS='N')",new Object[]{sourceUniqueId, sourceCd}, new FilerWithCountCoveredMapper());



        TreeMap<String, CoveredPerson> coveredPeople = new TreeMap<String, CoveredPerson>();

//        if((!filer.getFilerStatus().equals(null)) && filer.getFilerStatus().equals("R"))
//            coveredPeople.put(ApplicationConstants.COVERED_PERSON_PREFIX + ApplicationConstants.FIRST_COVERED_PERSON,
//                    Convert.getCoveredPersonFromFiler(filer));
//
//        if(filer.getCoveredPersonSize()>1) {
//            List<CoveredPerson> coveredPersonList = this.getCoveredPersonList(filer.getSourceUniqueId());
//            int i=2;
//            for (CoveredPerson coveredPerson : coveredPersonList) {
//                coveredPeople.put(ApplicationConstants.COVERED_PERSON_PREFIX+i, coveredPerson);
//                i++;
//            }
//
//        }

        filer.setCoveredpersons(coveredPeople);
        filer.setCoveredPersonSize(coveredPeople.size());
        return filer;
    }

    /**
     *
     * @param result
     */
    public void updateFilerDemographics(final Filer result){
        if (logger.isInfoEnabled()) {
            logger.info("updateFilerDemographics sourceCd {} sourceUniqueId {}", result.getSourceCd(), result.getSourceUniqueId()) ;
        }

        this.jdbcTemplate.update("update FILER_DEMOGRAPHICS set  FORM_STATUS = ?, UPDATED_BY=? , UPDATED_DATE = ? where SOURCE_UNIQUE_ID = ? and SOURCE_CD = ?",new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                if(result.isExceptionFound()){
                    ps.setBinaryStream(1,null);
                    ps.setString(2, CommonDataConstants.FormStatus.GENERATION_FAILED);
                    result.setFormStatus(CommonDataConstants.FormStatus.GENERATION_FAILED);
                }
                else {
                    byte[] byteArray = result.getByteArrayOutputStream().toByteArray();
                    ps.setBinaryStream(1,new ByteArrayInputStream(byteArray),byteArray.length);
                    ps.setString(2, result.getFormStatus());
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                Date currentDate = new Date();
                String currentTime = simpleDateFormat.format(currentDate);
                ps.setString(3, result.getUpdatedBy());
                ps.setString(4, currentTime);
                ps.setLong(5, result.getSourceUniqueId());
                ps.setString(6,	result.getSourceCd());
            }
        });
    }
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}