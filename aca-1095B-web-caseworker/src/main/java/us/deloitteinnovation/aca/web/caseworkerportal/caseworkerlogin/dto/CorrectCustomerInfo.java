package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ritmukherjee on 11/9/2015.
 */
public class CorrectCustomerInfo implements Serializable {


    private static final long serialVersionUID = 101999642532688956L;



    CoveredFilerInfo filerInfo;

    private Form1095BInfo currentForm;


    /*covered months*/
    private LinkedList<MonthCoverageInfo> filerCoverageInfo=new LinkedList<MonthCoverageInfo>();

    /*coverage Source Infos has added : <v14.1 >*/
    private List<CoverageSourceInfo> coverageSources;


    /* Comments*/
    private String comments;

    /* coverage updated */
    private boolean coverageUpdated;






    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }




    public LinkedList<MonthCoverageInfo> getFilerCoverageInfo() {
        return filerCoverageInfo;
    }

    public void setFilerCoverageInfo(LinkedList<MonthCoverageInfo> filerCoverageInfo) {
        this.filerCoverageInfo = filerCoverageInfo;
    }

    public List<CoverageSourceInfo> getCoverageSources() {
        return coverageSources;
    }

    public void setCoverageSources(List<CoverageSourceInfo> coverageSources) {
        this.coverageSources = coverageSources;
    }

    public CoveredFilerInfo getFilerInfo() {
        return filerInfo;
    }

    public void setFilerInfo(CoveredFilerInfo filerInfo) {
        this.filerInfo = filerInfo;
    }

    public Form1095BInfo getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(Form1095BInfo currentForm) {
        this.currentForm = currentForm;
    }

    public boolean isCoverageUpdated() {
        return coverageUpdated;
    }

    public void setCoverageUpdated(boolean coverageUpdated) {
        this.coverageUpdated = coverageUpdated;
    }
}
