package us.deloitteinnovation.aca.web.caseworkerportal.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.PrintDetail;
import us.deloitteinnovation.aca.entity.PrintDetailPK;

import java.util.Date;

/**
 * Created by rgopalani on 12/16/2015.
 */
@Repository
public interface PrintDetailRepositoryCW extends BaseRepository<PrintDetail,PrintDetailPK> {
    @Modifying
    @Transactional
    @Query("update PrintDetail pd set " +
            " pd.printStatus =(:printStatus) , "  +
            " pd.updatedBy =(:updatedBy) , "  +
            " pd.updatedDt =(:updatedDt),  "  +
            " pd.lastMailRequestedDate=(:lastMailRequestedDate) " +
            "  where   ( pd.id.sourceUniqueId) = (:sourceUniqueId)  "+
            " AND  lower( pd.id.sourceCd) = lower(:sourceCd)")
    int updateMailStatus(@Param("printStatus") String printStatus, @Param("updatedBy") String updatedBy, @Param("updatedDt") Date updatedDt,
                         @Param("lastMailRequestedDate") Date lastMailRequestedDate, @Param("sourceUniqueId") long sourceUniqueId,
                         @Param("sourceCd") String sourceCd);
}

