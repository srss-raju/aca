package us.deloitteinnovation.aca.batch.dao;

import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by rgopalani on 10/12/2015.
 */
public interface FilerDemographicsDao {

    int[][] bulkInsert(final List<FilerDemographicDto> filerDemographics);

    int[] bulkUpdate(final List<FilerDemographicDto> filerDemographics);

    int update(final FilerDemographicDto filerDemographic);

    int clearCoverage(final FilerDemographicDto filerDemographic);

    int updateCoverage(final FilerDemographicDto filerDemographic);

    List<FilerDemographicDto> getFilerDemographic(final FilerDemographicDto filerDemographic);

    List<FilerDemographicDto> findAll(final Set<FilerDemographicPKDto> ids);

    void updateTransmissionStatusCodes(final Set<FilerDemographicPKDto> ids, final String transmissionStatusCode);

    void bulkToggleActive(final Collection<FilerDemographicPKDto> idsToDeactivate, boolean active);

    void bulkMarkRegenerate(final Collection<FilerDemographicPKDto> idsToMarkRegenerate);

    long filerDemoSeqCurrentValue();
}
