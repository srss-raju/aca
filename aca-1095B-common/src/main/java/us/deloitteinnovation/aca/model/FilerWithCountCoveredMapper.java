package us.deloitteinnovation.aca.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps a result set to a Filer, including the "countcovered" amount.
 * @see FilerMapper
 */
@Component
public class FilerWithCountCoveredMapper implements RowMapper<Filer> {

    private static final Logger LOG = LoggerFactory.getLogger(FilerWithCountCoveredMapper.class) ;

    @Autowired
    FilerMapper filerMapper;

    @Override
    public Filer mapRow(ResultSet rs, int rowNum) throws SQLException {

        /*TODO: adding below condition because in some cases filer mapper is not getting injected into this class and getting null
        * pointer exception which stops the process
        * not sure  why we are injecting filer mapper instead of creating new instance since we need new instance of mapper every time
        * Please test irs xml generation after below change.
        *
        * Reason for keeping this condition is from web we are doing new Object for this class and that is not spring framework managed instance so FilerMapper will not get autowired here.
        * */
        if(filerMapper == null)
        {
            filerMapper = new FilerMapper();
        }
        Filer filer = filerMapper.mapRow(rs, rowNum);
        filer.setCoveredPersonSize(rs.getInt("countcovered"));
        return filer;
    }

    public FilerMapper getFilerMapper() {
        return filerMapper;
    }

    public void setFilerMapper(FilerMapper filerMapper) {
        this.filerMapper = filerMapper;
    }
}
