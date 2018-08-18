package us.deloitteinnovation.aca.batch.export.step3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Step3ProcessorResult {
    /** List of pairings between Form 1094B and multiple 1095B forms.*/
    List<Step3Form109495Pairing> pairings = new ArrayList<>();

    public List<Step3Form109495Pairing> getPairings() {
        return pairings;
    }

    public void setPairings(List<Step3Form109495Pairing> pairings) {
        this.pairings = pairings;
    }
}
