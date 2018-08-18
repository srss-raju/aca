package us.deloitteinnovation.aca.web.caseworkerportal.caseworkerlogin.dto;

/**
 * Created by ritmukherjee on 11/10/2015.
 */
public class MonthCoverageInfo {
    private String month;
    private Character covered;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Character getCovered() {
        return covered;
    }

    public void setCovered(Character covered) {
        this.covered = covered;
    }
}
