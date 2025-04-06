// Database connection exception
package exception;

public class DatabaseConnectionException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public DatabaseConnectionException() {
        super("Failed to connect to database!");
    }

    public DatabaseConnectionException(String message) {
        super(message);
    }
}