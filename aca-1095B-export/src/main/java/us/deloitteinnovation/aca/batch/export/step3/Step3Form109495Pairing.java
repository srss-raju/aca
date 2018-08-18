package us.deloitteinnovation.aca.batch.export.step3;

import us.deloitteinnovation.aca.batch.export.step1.Step1Form1095Dto;
import us.deloitteinnovation.aca.batch.export.step2.Step2Form1094Dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulation of a single Form 1094 and multiple corresponding Form 1095s.  There may be more 1095s in this object
 * than can be successfully rendered as XML in a single 100MB file.
 */
public class Step3Form109495Pairing {

    Step2Form1094Dto step2Form1094bDto ;

    public List<Step1Form1095Dto> step1Form1095bDtoList ;

    public Step3Form109495Pairing() {
        // Intentionally blank
    }

    public Step3Form109495Pairing(Step2Form1094Dto form1094bDto) {
        this.step2Form1094bDto = form1094bDto ;
        step1Form1095bDtoList = new ArrayList<>() ;
    }

    public Step2Form1094Dto getStep2Form1094bDto() {
        return step2Form1094bDto;
    }

    public void setStep2Form1094bDto(Step2Form1094Dto step2Form1094bDto) {
        this.step2Form1094bDto = step2Form1094bDto;
    }

    public List<Step1Form1095Dto> getStep1Form1095bDtoList() {
        return step1Form1095bDtoList;
    }

    public void setStep1Form1095bDtoList(List<Step1Form1095Dto> step1Form1095bDtoList) {
        this.step1Form1095bDtoList = step1Form1095bDtoList;
    }


}
