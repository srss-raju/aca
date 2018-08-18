package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritmukherjee on 10/13/2015.
 */
public class Filers implements Serializable {


    private static final long serialVersionUID = 6787414466285873452L;
    private List<FilerInfo> filers;

    public List<FilerInfo> getFilers() {
        return filers;
    }

    public void setFilers(List<FilerInfo> filers) {
        this.filers = filers;
    }

    public void addFiler(FilerInfo filerInfo){
        if(null==filers){
            this.filers=new ArrayList<>();
        }
        this.filers.add(filerInfo);
    }
}
