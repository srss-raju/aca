package us.deloitteinnovation.aca.exception;


public class PrintVendorBatchImportException extends Exception {
    public PrintVendorBatchImportException() {
        super();
    }

    public PrintVendorBatchImportException(String message, Exception exception) {
        super(message, exception);
    }

    public PrintVendorBatchImportException(String message) {
        super(message);
    }

    public PrintVendorBatchImportException(Throwable e) {
        super(e);
    }
}
