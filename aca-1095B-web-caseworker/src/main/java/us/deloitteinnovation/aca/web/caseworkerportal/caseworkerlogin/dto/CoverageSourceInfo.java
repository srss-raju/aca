package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by ritmukherjee on 11/17/2015.
 */
public class CoverageSourceInfo implements Serializable {


    private static final long serialVersionUID = -377449459202800653L;

    /* PseudoKey of Filer_coverage_source table*/
    private String CoverageSourceId;

    /*covered months*/
    private LinkedList<MonthCoverageInfo> filerSourceInfo =new LinkedList<MonthCoverageInfo>();

    /*program name added according to v15*/
    private String source;

    /*case id of each source*/
    private String caseID;

    /*comments of each source*/
    private String comments;

    private String lastModifiedDate;


    public String getCoverageSourceId() {
        return CoverageSourceId;
    }

    public void setCoverageSourceId(String coverageSourceId) {
        CoverageSourceId = coverageSourceId;
    }

    public LinkedList<MonthCoverageInfo> getFilerSourceInfo() {
        return filerSourceInfo;
    }

    public void setFilerSourceInfo(LinkedList<MonthCoverageInfo> filerSourceInfo) {
        this.filerSourceInfo = filerSourceInfo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }
}
