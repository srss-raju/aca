package us.deloitteinnovation.aca.batch.dto;

/**
 * Created by bhchaganti on 11/11/2016.
 */
public class BatchCountDto {

    private int countInBusinessDecisionLog;
    private int countInExceptionReport;

    public int getCountInBusinessDecisionLog() {
        return countInBusinessDecisionLog;
    }

    public void setCountInBusinessDecisionLog(int countInBusinessDecisionLog) {
        this.countInBusinessDecisionLog = countInBusinessDecisionLog;
    }

    public int getCountInExceptionReport() {
        return countInExceptionReport;
    }

    public void setCountInExceptionReport(int countInExceptionReport) {
        this.countInExceptionReport = countInExceptionReport;
    }
}
