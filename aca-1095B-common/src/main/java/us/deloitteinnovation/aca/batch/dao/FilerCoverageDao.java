package us.deloitteinnovation.aca.batch.dao;

import org.springframework.stereotype.Component;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicDto;
import us.deloitteinnovation.aca.batch.dto.FilerDemographicPKDto;

import java.util.List;
import java.util.Set;

/**
 * Created by rgopalani on 10/12/2015.
 */
@Component
public interface FilerCoverageDao {
    /**
     * @param filerDemographics
     */
    public void bulkInsert(final List<? extends FilerDemographicDto> filerDemographics);

    /**
     * @param filerDemographic
     */
    public int update(final FilerDemographicDto filerDemographic);

    public int[] daleteAll(final Set<FilerDemographicPKDto> ids);
}
