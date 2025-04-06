package main;

import dao.EmployeeService;
//import dao.FinancialRecordService;
import dao.PayrollService;
//import dao.TaxService;
import entity.Employee;
//import exception.EmployeeNotFoundException;
//import exception.InvalidInputException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class MainModule {
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    // Service instances
    private static EmployeeService employeeService = new EmployeeService();
    public static void main(String[] args) {
        try {
            boolean exit = false;
            while (!exit) {
                System.out.println("\n==== PayXpert: Payroll Management System ====");
                System.out.println("1. Employee Management");
                System.out.println("2. Payroll Processing");
                System.out.println("3. Tax Management");
                System.out.println("4. Financial Records");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        manageEmployees();
                        break;
                    case 2:
                        managePayroll();
                        break;
                    case 3:
                        manageTax();
                        break;
                    case 4:
                        manageFinancialRecords();
                        break;
                    case 0:
                        exit = true;
                        System.out.println("Thank you for using PayXpert");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    private static void manageEmployees() {
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
                // Add employee logic
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
                    // View employee logic
                    System.out.print("Enter employee ID: ");
                    int empId = Integer.parseInt(scanner.nextLine());
                    Employee emp = employeeService.getEmployeeById(empId);
                    System.out.println("Employee details: " + emp);
                    break;
                    

                case 3:
                    // Update Employee
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
                    // Delete Employee
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
    //------------------------------------------------------------------------------------------------------------------
    private static void managePayroll() {
        try {
            System.out.println("\n==== Payroll Management ====");
            System.out.println("1. Generate Payroll");
            System.out.println("2. View Payroll");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1:
                    // Generate payroll logic
                    System.out.print("Enter employee ID: ");
                    int empId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter JoiningDate (yyyy-MM-dd): ");
                    Date startDate = dateFormat.parse(scanner.nextLine());
                    System.out.print("Enter TerminationDate (yyyy-MM-dd): ");
                    Date endDate = dateFormat.parse(scanner.nextLine());
                    
				    PayrollService.generatePayroll (empId ,  startDate,  endDate);
                    System.out.println("Payroll generated successfully!");
                    break;
                    
                case 2:
                    // View payroll logic
                    System.out.print("View Payroll -To be implemented");
               
                    break;
                                        
                case 0:
                    return;
                    
                default:
                    System.out.println("Invalid choice");
            }
        } catch (Exception e) {
            System.out.println("Error in payroll management: " + e.getMessage());
        }
    }
    
    private static void manageTax() {
        System.out.println("Tax management - functionality to be implemented");
    }
    
    private static void manageFinancialRecords() {
        System.out.println("Financial records management - functionality to be implemented");
    }
}