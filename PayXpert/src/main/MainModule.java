package main;

import dao.DAOEmployee;
import dao.DAOPayroll;
import dao.DAOTaxManagement;
import dao.DAOFinancialRecord;
import java.util.Scanner;

public class MainModule {
    private static final Scanner scanner = new Scanner(System.in);

   
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
                        DAOEmployee.manageEmployees();
                        break;
                    case 2:
                        DAOPayroll.managePayroll();
                        break;
                    case 3:
                    	DAOTaxManagement.manageTax();
                        break;
                    case 4:
                    	System.out.print("Enter Employee ID to view Financial Report: ");
                        int empId = scanner.nextInt();
                        DAOFinancialRecord.generateFinancialReport(empId);
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
}
