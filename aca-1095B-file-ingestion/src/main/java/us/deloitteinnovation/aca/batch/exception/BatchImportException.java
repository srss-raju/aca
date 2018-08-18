package us.deloitteinnovation.aca.batch.exception;


public class BatchImportException extends Exception {
    public BatchImportException() {
        super();
    }

    public BatchImportException(String message, Exception exception) {
        super(message, exception);
    }

    public BatchImportException(String message) {
        super(message);
    }

    public BatchImportException(Throwable e) {
        super(e);
    }
}
