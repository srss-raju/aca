package us.deloitteinnovation.aca.batch.exception;

/**
 * Created by bhchaganti on 9/21/2016.
 */
public class Step2VerifyFilerException extends Exception {

    Integer exceptionRowNumber;

    Long sourceUniqueId;

    public Step2VerifyFilerException() {
        super();
    }

    public Step2VerifyFilerException(String message, Exception exception) {
        super(message, exception);
    }

    public Step2VerifyFilerException(String message) {
        super(message);
    }

    public Step2VerifyFilerException(Throwable e) {
        super(e);
    }

    public Integer getExceptionRowNumber() {
        return exceptionRowNumber;
    }

    public void setExceptionRowNumber(Integer exceptionRowNumber) {
        this.exceptionRowNumber = exceptionRowNumber;
    }

    public Long getSourceUniqueId() {
        return sourceUniqueId;
    }

    public void setSourceUniqueId(Long sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }
}
