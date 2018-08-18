package us.deloitteinnovation.aca.batch.export;

import org.springframework.batch.core.StepExecution;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;

import java.util.List;

/**
 */
public interface ExportJob1095Repository {

    Step1Form1095Dto getForm1095bById(String sourceCode, long sourceId, Integer taxYear);

    /**
     * @param sourceCode
     * @param currentTansmissionId
     *@param stepExecution  @return A list of Form 1095B data that corresponds to the given provider source code.
     */

    @Deprecated
    List<Step1Form1095Dto> getForm1095bBySourceCode(String sourceCode, Integer currentTansmissionId, Integer taxYear, StepExecution stepExecution);
    List<Step1Form1095Dto> getForm1095bBySourceCode(String sourceCode, Integer currentTansmissionId, Integer taxYear, StepExecution stepExecution, int pageNum, int pageSize);

    void save(Step1Form1095Dto form1094B, StepExecution stepExecution);


    void clearAll();
}
