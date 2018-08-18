package us.deloitteinnovation.aca.web.citizenportal.citizenlogin.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tthakore on 10/12/2015.
 */
public class StateList {



    private String stateName;
    private String stateCode;
    private List<StateInfoDTO> stateInfo = new ArrayList<>();



    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public List<StateInfoDTO> getStateInfo() {
        return stateInfo;
    }

}
