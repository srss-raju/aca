package us.deloitteinnovation.aca.batch.export;

import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;

/**
 * Persistence methods for 1094 XML.
 */
public interface ExportJob1094Repository {
    Step2Form1094Dto getForm1094bById(String ein) ;

    void save(Step2Form1094Dto form1094B) ;
}
