package dao;

import entity.Employee;

import java.time.LocalDate;
import java.util.Scanner;

public class DAOEmployee {
    private static Scanner scanner = new Scanner(System.in);
    private static EmployeeService employeeService = new EmployeeService();

    public static void manageEmployees() {
        try {
            System.out.println("\n==== Employee Management ====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter EmployeeID: ");
                    int employeeID = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter FirstName: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Enter LastName: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Enter DateOfBirth (yyyy-MM-dd): ");
                    LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());

                    System.out.print("Enter Gender: ");
                    String gender = scanner.nextLine();

                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter PhoneNumber: ");
                    String phoneNumber = scanner.nextLine();

                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();

                    System.out.print("Enter Position: ");
                    String position = scanner.nextLine();

                    System.out.print("Enter JoiningDate (yyyy-MM-dd): ");
                    LocalDate joiningDate = LocalDate.parse(scanner.nextLine());

                    System.out.print("Enter TerminationDate (yyyy-MM-dd): ");
                    String terminationInput = scanner.nextLine();
                    LocalDate terminationDate = null;
                    if (!terminationInput.isBlank()) {
                        terminationDate = LocalDate.parse(terminationInput);
                    }

                    Employee newEmployee = new Employee();
                    newEmployee.setEmployeeID(employeeID);
                    newEmployee.setFirstName(firstName);
                    newEmployee.setLastName(lastName);
                    newEmployee.setDateOfBirth(dateOfBirth);
                    newEmployee.setGender(gender);
                    newEmployee.setEmail(email);
                    newEmployee.setPhoneNumber(phoneNumber);
                    newEmployee.setAddress(address);
                    newEmployee.setPosition(position);
                    newEmployee.setJoiningDate(joiningDate);
                    newEmployee.setTerminationDate(terminationDate);

                    employeeService.addEmployee(newEmployee);
                    System.out.println("Employee added successfully!");
                    break;

                case 2:
                    System.out.print("Enter employee ID: ");
                    int empId = Integer.parseInt(scanner.nextLine());
                    Employee emp = employeeService.getEmployeeById(empId);
                    System.out.println("Employee details: " + emp);
                    break;

                case 3:
                    System.out.print("Enter employee ID to update: ");
                    int updateId = Integer.parseInt(scanner.nextLine());
                    Employee existingEmp = employeeService.getEmployeeById(updateId);
                    if (existingEmp == null) {
                        System.out.println("Employee not found.");
                        break;
                    }

                    System.out.print("Enter new FirstName (" + existingEmp.getFirstName() + "): ");
                    String newFirstName = scanner.nextLine();
                    if (!newFirstName.isBlank()) existingEmp.setFirstName(newFirstName);

                    System.out.print("Enter new LastName (" + existingEmp.getLastName() + "): ");
                    String newLastName = scanner.nextLine();
                    if (!newLastName.isBlank()) existingEmp.setLastName(newLastName);

                    System.out.print("Enter new Email (" + existingEmp.getEmail() + "): ");
                    String newEmail = scanner.nextLine();
                    if (!newEmail.isBlank()) existingEmp.setEmail(newEmail);

                    System.out.print("Enter new PhoneNumber (" + existingEmp.getPhoneNumber() + "): ");
                    String newPhone = scanner.nextLine();
                    if (!newPhone.isBlank()) existingEmp.setPhoneNumber(newPhone);

                    System.out.print("Enter new Address (" + existingEmp.getAddress() + "): ");
                    String newAddress = scanner.nextLine();
                    if (!newAddress.isBlank()) existingEmp.setAddress(newAddress);

                    System.out.print("Enter new Position (" + existingEmp.getPosition() + "): ");
                    String newPosition = scanner.nextLine();
                    if (!newPosition.isBlank()) existingEmp.setPosition(newPosition);

                    employeeService.updateEmployee(existingEmp);
                    System.out.println("Employee updated successfully!");
                    break;

                case 4:
                    System.out.print("Enter employee ID to delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    employeeService.removeEmployee(deleteId);
                    System.out.println("Employee deleted successfully!");
                    break;
                case 0:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        } catch (Exception e) {
            System.out.println("Error in employee management: " + e.getMessage());
        }
    }
}
