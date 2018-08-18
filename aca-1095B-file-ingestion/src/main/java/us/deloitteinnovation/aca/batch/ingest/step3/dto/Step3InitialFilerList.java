package us.deloitteinnovation.aca.batch.ingest.step3.dto;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by tthakore on 11/4/2016.
 */
@Component
public class Step3InitialFilerList {


    public ArrayList<Step3FilerDataDto> getStep3InitialFilerList() {
        return step3InitialFilerList;
    }

    public void setStep3InitialFilerList(ArrayList<Step3FilerDataDto> step3InitialFilerList) {
        this.step3InitialFilerList = step3InitialFilerList;
    }

    ArrayList<Step3FilerDataDto> step3InitialFilerList = new ArrayList<>();
}
