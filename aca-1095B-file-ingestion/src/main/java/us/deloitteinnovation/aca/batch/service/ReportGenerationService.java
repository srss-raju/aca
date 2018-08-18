package us.deloitteinnovation.aca.batch.service;

public interface ReportGenerationService {

    public void generateMetadataReport(String generatedReportName, String originalInputFileName, int batchId, String errorMessage) throws Exception;

    public void generateRecordLevelReport(String outputFolder, int batchId) throws Exception;
}
