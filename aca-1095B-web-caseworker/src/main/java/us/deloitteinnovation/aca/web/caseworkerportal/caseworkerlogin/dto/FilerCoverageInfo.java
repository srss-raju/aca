package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ritmukherjee on 10/13/2015.
 */
public class FilerCoverageInfo  implements Serializable{


    private static final long serialVersionUID = -7397394068005716230L;

    /*coverageID:Primary Key*/
    private String coverageID;
    private Date origCoverageBeginDt;

    private Date origCoverageEndDt;

    private String comments;

    /*covered months*/
/*    private String jan;
    private String feb;
    private String mar;
    private String apr;
    private String may;
    private String jun;
    private String jul;
    private String aug;
    private String sep;
    private String oct;
    private String nov;
    private String dec;*/


    MonthCoverageInfo[] coverageInfos=new MonthCoverageInfo[12];



    private String updatedBy;

    private Date updatedDt;

    public Date getOrigCoverageBeginDt() {
        return origCoverageBeginDt;
    }

    public void setOrigCoverageBeginDt(Date origCoverageBeginDt) {
        this.origCoverageBeginDt = origCoverageBeginDt;
    }

    public Date getOrigCoverageEndDt() {
        return origCoverageEndDt;
    }

    public void setOrigCoverageEndDt(Date origCoverageEndDt) {
        this.origCoverageEndDt = origCoverageEndDt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }




    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

   /* public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getFeb() {
        return feb;
    }

    public void setFeb(String feb) {
        this.feb = feb;
    }

    public String getMar() {
        return mar;
    }

    public void setMar(String mar) {
        this.mar = mar;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getMay() {
        return may;
    }

    public void setMay(String may) {
        this.may = may;
    }

    public String getJun() {
        return jun;
    }

    public void setJun(String jun) {
        this.jun = jun;
    }

    public String getJul() {
        return jul;
    }

    public void setJul(String jul) {
        this.jul = jul;
    }

    public String getAug() {
        return aug;
    }

    public void setAug(String aug) {
        this.aug = aug;
    }

    public String getSep() {
        return sep;
    }

    public void setSep(String sep) {
        this.sep = sep;
    }

    public String getOct() {
        return oct;
    }

    public void setOct(String oct) {
        this.oct = oct;
    }

    public String getNov() {
        return nov;
    }

    public void setNov(String nov) {
        this.nov = nov;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

   */ public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getCoverageID() {
        return coverageID;
    }

    public void setCoverageID(String coverageID) {
        this.coverageID = coverageID;
    }
}

