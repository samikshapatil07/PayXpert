package exception;

public class FinancialRecordException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public FinancialRecordException() {
        super();
    }
    
    public FinancialRecordException(String message) {
        super(message);
    }
    
    public FinancialRecordException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public FinancialRecordException(Throwable cause) {
        super(cause);
    }
}