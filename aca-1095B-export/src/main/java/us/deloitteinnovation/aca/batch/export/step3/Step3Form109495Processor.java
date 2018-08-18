package us.deloitteinnovation.aca.batch.export.step3;

import org.springframework.batch.item.ItemProcessor;
import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;

import java.util.List;

/**
 * <p>Associate 10954B and 1095B into separate files, as needed, to maintain file size maximum.
 * Create and associate manifest/header XML for each file.</p>
 *
 * <p>ACA Form 10945B header document as defined by the AIR_Composition_and_Reference_Guide version 4.1</p>
 */
public class Step3Form109495Processor implements ItemProcessor<Step3Form109495Pairing, Step3ProcessorResult> {

    /** Maximum number of Form 1095B forms allowed per XML output. */
    int maxForm1095bPerDoc = 25000 ;
    
    /**
     * Associate 10954B and 1095B into separate files, as needed, to maintain file size maximum.
     * @param step3ProcessorPairing Pair of one Form 1094B and all possible Form 1095Bs to render with it.
     * @return The provided Form 1094B with Form 1095Bs split as needed to meet XML file requirements.
     */
    @Override
    public Step3ProcessorResult process(Step3Form109495Pairing step3ProcessorPairing) throws Exception {
        Step3ProcessorResult result = new Step3ProcessorResult() ;

        Step2Form1094Dto form1094bDto = step3ProcessorPairing.getStep2Form1094bDto() ;
        List<Step1Form1095Dto> form1095List = step3ProcessorPairing.getStep1Form1095bDtoList() ;

        Step3Form109495Pairing innerPair = new Step3Form109495Pairing(form1094bDto) ;
        result.getPairings().add(innerPair) ;
        int count = 0 ;
        for (Step1Form1095Dto form1095bDto : form1095List) {
            // Once we have our maximum per document, store the current innerPair and continue with a new one.
            if (count == maxForm1095bPerDoc) {
                count = 0 ;
                innerPair = new Step3Form109495Pairing(form1094bDto) ;
                result.getPairings().add(innerPair) ;
            }
            innerPair.step1Form1095bDtoList.add(form1095bDto) ;
            count++ ;
        }

        return result;
    }

    public int getMaxForm1095bPerDoc() {
        return maxForm1095bPerDoc;
    }

    public void setMaxForm1095bPerDoc(int maxForm1095bPerDoc) {
        this.maxForm1095bPerDoc = maxForm1095bPerDoc;
    }

}
