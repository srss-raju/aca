package us.deloitteinnovation.aca.batch.export;

import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pebradley on 4/5/16.
 */
public class ExportJob1094RepositoryInMemory implements ExportJob1094Repository {

    /** Key is EIN, value is the 1094 data. */
    Map<String, Step2Form1094Dto> form1094Map = new HashMap<>() ;

    @Override
    public Step2Form1094Dto getForm1094bById(String ein) {
        return form1094Map.get(ein);
    }

    @Override
    public void save(Step2Form1094Dto form1094B) {
        form1094Map.put(form1094B.getSourceSystemConfig().getProviderIdentificationNumber(), form1094B) ;
    }

    /**
     * Clears all in-memory data storage maps.  Useful for testing.
     */
    protected void clearAll() {
        form1094Map.clear() ;
    }

}
