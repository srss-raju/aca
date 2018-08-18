package us.deloitteinnovation.aca.web.caseworkerportal.vo;

import java.io.Serializable;

/**
 * Created by ritmukherjee on 10/15/2015.
 */
public class SearchFilerInfo implements Serializable {


    private static final long serialVersionUID = -8110405196443353409L;
    private String userFname ;
    private String userLname;
    private String dob ;
    private String ssn ;
    private String tin;
    private String state;
    private int taxYear;

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public int getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(int taxYear) {
        this.taxYear = taxYear;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
