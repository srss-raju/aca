package us.deloitteinnovation.aca.batch.dataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import us.deloitteinnovation.aca.model.SourceSystemConfig;
import us.deloitteinnovation.aca.model.SourceSystemConfigMapper;

@Service("sourceSystemConfigDataService")
public class SourceSystemConfigDataService {
    private static Logger logger = LoggerFactory.getLogger(SourceSystemConfigDataService.class);
    SourceSystemConfigMapper sourceSystemConfigMapper = new SourceSystemConfigMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SourceSystemConfigDataService() {
    }

    public SourceSystemConfigDataService(JdbcTemplate jdbcTemplate) {
        jdbcTemplate = this.jdbcTemplate;
    }

    public SourceSystemConfig getSourceSystemConfig(String sourceCd) {
        SourceSystemConfig sourceSystemConfig;
        String sourceCode = sourceCd.substring(0, 8);
        try {
            sourceSystemConfig = jdbcTemplate.queryForObject("SELECT * FROM SOURCE_SYSTEM_CONFIG WHERE SOURCE_CD = ?", new Object[]{sourceCode}, sourceSystemConfigMapper);
        } catch (Exception ex) {
            sourceSystemConfig = new SourceSystemConfig();
            sourceSystemConfig.setStateAbbreviation(sourceCd.substring(0, 2));
            logger.error("Attempting to get SOURCE_SYSTEM_CONFIG usind SOURCE_CD '{}'", sourceCode, ex);
        }
        return sourceSystemConfig;
    }

    /**
     *
     * @param state Two letter state abbreviation.  Will be matched to first two letters of the Source Code.
     * @return
     */
    public SourceSystemConfig getByState(String state, Integer taxYear) {
        return jdbcTemplate.queryForObject("SELECT * FROM SOURCE_SYSTEM_CONFIG WHERE SUBSTRING(SOURCE_CD,0,3) = ? AND TAX_YEAR = ?", new Object[]{state, taxYear}, sourceSystemConfigMapper);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
