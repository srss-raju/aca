package us.deloitteinnovation.aca.web.caseworkerportal.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import us.deloitteinnovation.aca.entity.FilerCoverage;
import us.deloitteinnovation.aca.entity.FilerCoveragePK;

import java.util.Date;
import java.util.List;

/**
 * Created by ritmukherjee on 11/18/2015.
 */

public interface FilerCoverageSourceRepositoryCW extends BaseRepository<FilerCoverage,FilerCoveragePK> {

/*   asynchronus method which fetch List<FilerCoverage> based on sourceUniqueId,sourceCd*/
    @Async
    @Query("select t from FilerCoverage t" +
            "  where   (t.id.sourceUniqueId) = (:sourceUniqueId)  "+
            " AND  lower(t.id.sourceCd) = lower(:sourceCd)")
    List<FilerCoverage> getFilerCoverageSources(
            @Param("sourceUniqueId") long sourceUniqueId,
            @Param("sourceCd") String sourceCd);


    /*update FilerDemographic with coverage details*/

    @Modifying
    @Transactional
    @Query("update FilerCoverage t set " +
            " t.jan=(:jan)" +","+
            " t.feb=(:feb)" + ","+
            " t.mar=(:mar)" +","+
            " t.apr=(:apr)" +","+
            " t.may=(:may)" +","+
            " t.jun=(:jun)" +","+
            " t.jul=(:jul)" +","+
            " t.aug=(:aug)" + ","+
            " t.sep=(:sep)" +","+
            " t.oct=(:oct)" +","+
            " t.nov=(:nov)" +","+
            " t.dec=(:dec)" +","+
            " t.comments=(:comments) " +","+
            " t.updatedBy=(:updatedBy)"+","+
            " t.updatedDt=(:updatedDt)"+
            " where   (t.id.sourceUniqueId) = (:sourceUniqueId)  "+
            " AND  lower(t.id.sourceCd) = lower(:sourceCd) " +
            " AND  lower(t.id.coverageSeqNo) = lower(:coverageSeqNo)")
    int updateCustomerCoverageSourceDetails(
            @Param("jan") Character jan,
            @Param("feb") Character feb,
            @Param("mar") Character mar,
            @Param("apr") Character apr,
            @Param("may") Character may,
            @Param("jun") Character jun,
            @Param("jul") Character jul,
            @Param("aug") Character aug,
            @Param("sep") Character sep,
            @Param("oct") Character oct,
            @Param("nov") Character nov,
            @Param("dec") Character dec,
            @Param("comments") String comments,
            @Param("updatedBy") String updatedBy,
            @Param("updatedDt") Date updatedDt,
            @Param("sourceUniqueId") long sourceUniqueId,
            @Param("sourceCd") String sourceCd,
            @Param("coverageSeqNo") long coverageSeqNo);

}
