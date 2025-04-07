package entity;
public class ValidationService {

    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".") && !email.startsWith("@") && !email.endsWith(".");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 10) return false;
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static boolean isValidEmployee(Employee employee) {
        return employee != null &&
               isValidEmail(employee.getEmail()) &&
               isValidPhoneNumber(employee.getPhoneNumber()) &&
               employee.getFirstName() != null &&
               employee.getLastName() != null &&
               employee.getDateOfBirth() != null &&
               employee.getJoiningDate() != null;
    }
}
