package us.deloitteinnovation.aca.web.citizenportal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import us.deloitteinnovation.aca.entity.PrintDetail;
import us.deloitteinnovation.aca.entity.PrintDetailPK;

/**
 * Created by tthakore on 10/26/2015.
 */
public interface PrintDetailsRepository extends BaseRepository<PrintDetail,PrintDetailPK> {

    @Async
    @Query("select t  from PrintDetail t where t.id.sourceCd = :srcCd AND t.id.sourceUniqueId = :srcUID ")
    PrintDetail getPrintDetails(@Param("srcCd") String srcCd, @Param("srcUID") long srcUID );
}
