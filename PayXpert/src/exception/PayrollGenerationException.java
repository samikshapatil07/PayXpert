// Payroll generation exception

//Invalid input exception

package exception;

public class PayrollGenerationException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public PayrollGenerationException() {
     super();
 }
 
 public PayrollGenerationException(String message) {
     super(message);
 }
 
 public PayrollGenerationException(String message, Throwable cause) {
     super(message, cause);
 }
 
 public PayrollGenerationException(Throwable cause) {
     super(cause);
 }
}