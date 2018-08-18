package us.deloitteinnovation.aca.web.citizenportal.repository;

import org.springframework.stereotype.Repository;
import us.deloitteinnovation.aca.entity.SourceSystemConfig;

import java.util.List;

/**
 * Created by tthakore on 10/20/2015.
 */
@Repository
public interface SourceSysConfRepository  extends  BaseRepository<SourceSystemConfig, String > {


    List<SourceSystemConfig> findBystateAbbreviation(String state);
}
