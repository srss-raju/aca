package us.deloitteinnovation.aca.batch.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import us.deloitteinnovation.aca.batch.constants.BatchExportConstants;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;

import java.util.*;

/**
 */
public class ExportJob1095RepositoryInMemory implements ExportJob1095Repository {

    /** Key is sourceCode_sourceId, value is the 1095 data. */
    Map<String, Step1Form1095Dto> form1095Map = new HashMap<>() ;

    /** Secondary map of Form 1095B data stored by 1094B provider Source Code. */
    Map<String, Set<Step1Form1095Dto>> form1095MapBySourceCode = new HashMap<>() ;


    @Override
    public Step1Form1095Dto getForm1095bById(String sourceCode, long sourceId, Integer taxYear) {
        return form1095Map.get(get1095Key(sourceCode,sourceId));
    }

    @Override
    public List<Step1Form1095Dto> getForm1095bBySourceCode(String sourceCode, Integer currentTansmissionId, Integer taxYear, StepExecution stepExecution) {
        Set<Step1Form1095Dto> set = this.form1095MapBySourceCode.get(sourceCode) ;
        if (set == null)
            return Collections.EMPTY_LIST ;
        // Copy to new list so that the underlying map set is not disturbed
        return new ArrayList(set) ;
    }

    @Override
    public List<Step1Form1095Dto> getForm1095bBySourceCode(String sourceCode, Integer currentTansmissionId, Integer taxYear, StepExecution stepExecution, int pageNum, int pageSize) {
        List<Step1Form1095Dto> returnlist = new ArrayList<>();
        Set<Step1Form1095Dto> set = this.form1095MapBySourceCode.get(sourceCode);

        // if no result available, return empty array list
        if (null == set) {
            return returnlist;
        }

        // Otherwise
        else {
            returnlist = new ArrayList<>(set);
            int startIdx = pageNum * pageSize;
            int endIdx = (pageNum + 1) * pageSize - 1;

            // If no more result available, return empty array list
            if (startIdx >= returnlist.size()) {
                return new ArrayList<>();
            }
            // If remaining element count < page size, read til the end
            else if (returnlist.size() -1 < endIdx) {
                endIdx = returnlist.size() - 1;
            }

            // Return sublist from startIdx to endIdx (inclusive on both sides)
            return returnlist.subList(startIdx, endIdx + 1);
        }
    }

    @Override
    public void save(Step1Form1095Dto form1094B, StepExecution stepExecution) {
        form1095Map.put(get1095Key(form1094B), form1094B);
        save1095bBySourceCode(form1094B.getFiler().getSourceCd(), form1094B) ;
    }
    

    /**
     * @param sourceCode
     * @param sourceId
     * @return   Combination of sourceCode_sourceId
     */
    protected String get1095Key(String sourceCode, long sourceId) {
        return sourceCode + "_" + sourceId ;
    }

    /**
     * @return   Combination of sourceCode_sourceId
     */
    protected String get1095Key(Step1Form1095Dto form) {
        return get1095Key(form.getFiler().getSourceCd(), form.getFiler().getSourceUniqueId());
    }

    /**
     * Stores Form 1095B data by provider EIN.  For non-in-memory persistence, this method will not be required.
     * @param sourceCode
     * @param form
     */
    protected void save1095bBySourceCode(String sourceCode, Step1Form1095Dto form) {
        Set<Step1Form1095Dto> set = this.form1095MapBySourceCode.get(sourceCode) ;
        if (set == null) {
            set = new HashSet<>();
            this.form1095MapBySourceCode.put(sourceCode, set) ;
        }
        set.add(form) ;
    }

    /**
     * Clears all in-memory data storage maps.  Useful for testing.
     */
    public void clearAll() {
        form1095Map.clear() ;
        form1095MapBySourceCode.clear() ;
    }
}
