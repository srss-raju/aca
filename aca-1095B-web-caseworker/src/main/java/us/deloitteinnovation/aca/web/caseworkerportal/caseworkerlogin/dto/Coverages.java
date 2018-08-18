package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritmukherjee on 10/29/2015.
 */
public class Coverages implements Serializable {


    private static final long serialVersionUID = -6897495565456385742L;
    private List<FilerCoverageInfo> coverageInfos=new ArrayList<>();

    public List<FilerCoverageInfo> getCoverageInfos() {
        return coverageInfos;
    }

    public void setCoverageInfos(List<FilerCoverageInfo> coverageInfos) {
        this.coverageInfos = coverageInfos;
    }

    public  void addCoverage(FilerCoverageInfo coverageInfo){
        if(coverageInfo!=null){
            this.getCoverageInfos().add(coverageInfo);
        }
    }
}
