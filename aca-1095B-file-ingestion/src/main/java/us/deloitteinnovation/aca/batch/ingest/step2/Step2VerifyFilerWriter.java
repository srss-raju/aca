package us.deloitteinnovation.aca.batch.ingest.step2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.exception.Step2VerifyFilerException;
import us.deloitteinnovation.aca.entity.FilerDemographicStagingEntity;
import us.deloitteinnovation.aca.repository.FilerDemographicStagingRepository;

import java.util.List;

/**
 * <P>
 *     Writer class to write the processor results to database.
 * </P>
 */
public class Step2VerifyFilerWriter implements ItemWriter<FilerDemographicStagingEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Step2VerifyFilerWriter.class);
    JobExecution jobExecution;

    @Autowired
    FilerDemographicStagingRepository filerDemographicStagingRepository;

    /**
     * <p>Iterates through the list of processor results
     * and writes the results to either staging or exception
     * report, but not to both at a time for a given result.</p>
     *
     * @param list
     * @throws Exception
     */
    @Override
    public void write(List<? extends FilerDemographicStagingEntity> list) throws Exception {

            for (FilerDemographicStagingEntity stagingEntity : list) {
                //FilerDemographicStagingEntity filerDemographicStagingEntity = stagingEntity.getFilerDemographicStagingEntity();
                if (stagingEntity != null) {

                    try {
                        writeToStaging(stagingEntity);
                    }catch(Exception ex){
                        String message = "Writing failed";
                        Step2VerifyFilerException step2VerifyFilerException = new Step2VerifyFilerException(message.concat(ex.getLocalizedMessage()));
                        step2VerifyFilerException.setExceptionRowNumber(stagingEntity.getRowNumber());
                        step2VerifyFilerException.setSourceUniqueId(stagingEntity.getSourceUniqueId());
                        throw step2VerifyFilerException;
                    }
                }
            }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Wrote {} record(s) to staging", list.size());
            }
    }

    /*
    * Helper method to write filer demographic entities to the staging table.
    * */
    private void writeToStaging(FilerDemographicStagingEntity filerDemographicStagingEntity) {
            filerDemographicStagingRepository.save(filerDemographicStagingEntity);
    }
}
