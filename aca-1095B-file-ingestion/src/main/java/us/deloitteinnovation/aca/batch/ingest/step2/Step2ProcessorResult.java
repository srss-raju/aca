package us.deloitteinnovation.aca.batch.ingest.step2;

import us.deloitteinnovation.aca.batch.dto.ExceptionReportDto;
import us.deloitteinnovation.aca.entity.FilerDemographicStagingEntity;

import java.util.Set;

/**
 * Encapsulates the results of the processors
 * so that writer can consume this for writing
 * results to database.
 */
public class Step2ProcessorResult {

    private Set<ExceptionReportDto> exceptionReportSet;
    private FilerDemographicStagingEntity filerDemographicStagingEntity;


    /**
     * Constructor for this class
     * Constructs an instance of this class with set of
     * exception report dto's initialized
     *
     * @param exceptionReportSet
     */
    public Step2ProcessorResult(Set<ExceptionReportDto> exceptionReportSet) {
        this.exceptionReportSet = exceptionReportSet;
    }

    /**
     * @return Set containing the exception report dto's
     */
    public Set<ExceptionReportDto> getExceptionReportSet() {
        return exceptionReportSet;
    }


    /**
     * Sets the value of exceptionReportSet
     *
     * @param exceptionReportSet
     */
    public void setExceptionReportSet(Set<ExceptionReportDto> exceptionReportSet) {
        this.exceptionReportSet = exceptionReportSet;
    }

    /**
     *
     * @return the filerDemographicStagingEntity property of this class.
     */
    public FilerDemographicStagingEntity getFilerDemographicStagingEntity() {
        return filerDemographicStagingEntity;
    }

    /**
     * Sets the value of filerDemographicStagingEntity
     * @param filerDemographicStagingEntity
     */
    public void setFilerDemographicStagingEntity(FilerDemographicStagingEntity filerDemographicStagingEntity) {
        this.filerDemographicStagingEntity = filerDemographicStagingEntity;
    }

    /**
     * Helper method to populate exception reports set
     *
     * @param exceptionReport
     */
    public void addExceptionReport(ExceptionReportDto exceptionReport) {

        this.getExceptionReportSet().add(exceptionReport);
    }
}
