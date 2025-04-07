package dao;

import java.time.LocalDate;
import java.util.Scanner;

public class DAOPayroll {
    private static final Scanner scanner = new Scanner(System.in);

    public static void managePayroll() {
        try {
            System.out.println("\n==== Payroll Management ====");
            System.out.println("1. Generate Payroll");
            System.out.println("2. View Payroll");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    generatePayroll();
                    break;
                case 2:
                    viewPayroll();
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

    private static void generatePayroll() {
        try {
            System.out.print("Enter employee ID: ");
            int empId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter JoiningDate (yyyy-MM-dd): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter TerminationDate (yyyy-MM-dd): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());

            PayrollService payrollService = new PayrollService();
            payrollService.generatePayroll(empId, startDate, endDate); // instance call

            System.out.println("Payroll generated successfully!");
        } catch (Exception e) {
            System.out.println("Failed to generate payroll: " + e.getMessage());
        }
    }


    private static void viewPayroll() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = Integer.parseInt(scanner.nextLine());
            PayrollService payrollService = new PayrollService();
            payrollService.getPayrollsForEmployee(empId);
        } catch (Exception e) {
            System.out.println("Error retrieving payroll: " + e.getMessage());
        }
    }
}
