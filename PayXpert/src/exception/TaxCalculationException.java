// Tax calculation exception

package exception;

public class TaxCalculationException extends Exception {
private static final long serialVersionUID = 1L;

public TaxCalculationException() {
  super();
}

public TaxCalculationException(String message) {
  super(message);
}

public TaxCalculationException(String message, Throwable cause) {
  super(message, cause);
}

public TaxCalculationException(Throwable cause) {
  super(cause);
}
}