package us.deloitteinnovation.aca.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.beans.factory.annotation.Autowired;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.service.FilerCoveredPersonService;
import us.deloitteinnovation.aca.model.CoveredPerson;
import us.deloitteinnovation.aca.model.Filer;
import us.deloitteinnovation.aca.util.Convert;

import java.util.List;
import java.util.TreeMap;

/**
 * Loads dependents for all Filers loaded in the Step 1 reader.  I would prefer to have that done within the reader itself,
 * this this code was already written, so I'm leaving it as-is.
 */
public class Step1ProcessorListener implements ItemProcessListener<Step1Form1095Dto, Step1Form1095Dto>{

    private static final Logger LOG = LoggerFactory.getLogger(Step1ProcessorListener.class) ;

    @Autowired
    FilerCoveredPersonService coveredPersonDataService;

    @Override
    public void beforeProcess(Step1Form1095Dto dto) {
        Filer filer = dto.getFiler() ;
        TreeMap<String, CoveredPerson> coveredPeople = new TreeMap<String, CoveredPerson>();

        if((!filer.getFilerStatus().equals(null)) && filer.getFilerStatus().equals("R"))
            coveredPeople.put("CP1", Convert.getCoveredPersonFromFiler(filer));

        if(filer.getCoveredPersonSize()>1) {
            List<CoveredPerson> coveredPersonList = coveredPersonDataService.getCoveredPersonList(filer.getSourceUniqueId(), filer.getTaxYear(), filer.getSourceCd());
            int i=2;
            for (CoveredPerson coveredPerson : coveredPersonList) {
                coveredPeople.put("CP"+i, coveredPerson);
                i++;
            }

        }

        filer.setCoveredpersons(coveredPeople);
        filer.setCoveredPersonSize(coveredPeople.size());
    }

    @Override
    public void afterProcess(Step1Form1095Dto item, Step1Form1095Dto result) {

    }

    @Override
    public void onProcessError(Step1Form1095Dto item, Exception e) {
        LOG.error("Step1 processor on filer source cd {}, source unique id {}", item.getFiler().getSourceCd(), item.getFiler().getSourceUniqueId(), e) ;
    }
}
