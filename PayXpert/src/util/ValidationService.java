package util;
import entity.Employee;
import exception.InvalidInputException;

public class ValidationService {
	       public void validateEmployee(Employee employee) throws InvalidInputException {
	        // Validate employee data (email format, name not empty, etc.)
	        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
	            throw new InvalidInputException("First name cannot be empty");
	        }
	        
	        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
	            throw new InvalidInputException("Last name cannot be empty");
	        }
	        
	        if (employee.getEmail() == null || !employee.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
	            throw new InvalidInputException("Invalid email format");
	        }
	        
	    }
	    
}
