package us.deloitteinnovation.aca.web.citizenportal.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import us.deloitteinnovation.aca.entity.SourceSystemConfig;

import java.util.List;

/**
 * Created by tthakore on 10/12/2015.
 */
@Repository
public interface SelectedStatesRepository extends  BaseRepository<SourceSystemConfig, Integer > {
    //public List<SourceSystemConfig> findDistinctreturnAddressStateBy();
@Async
    @Query(value = "select t from us.deloitteinnovation.aca.entity.SourceSystemConfig t  where   t.supportViewPdf != 'NO'", nativeQuery=false)
public List<SourceSystemConfig> getAvailableStates();

}
