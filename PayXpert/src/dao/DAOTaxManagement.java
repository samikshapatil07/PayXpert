package dao;

import entity.Tax;
import exception.EmployeeNotFoundException;
import exception.TaxCalculationException;

import java.util.Scanner;
public class DAOTaxManagement {
    private static final TaxService taxService = new TaxService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void manageTax() {
        System.out.println("\n--- Tax Calculation ---");
        try {
            System.out.print("Enter Employee ID: ");
            int empId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Tax Year (e.g., 2024): ");
            int taxYear = Integer.parseInt(scanner.nextLine());

            Tax tax = taxService.calculateTax(empId, taxYear);

            System.out.println("\nTax Record Created Successfully:");
            printTax(tax);

        } catch (TaxCalculationException | EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printTax(Tax tax) {
        System.out.println("------------------------------------------");
        System.out.println("Tax ID         : " + tax.getTaxID());
        System.out.println("Employee ID    : " + tax.getEmployeeID());
        System.out.println("Tax Year       : " + tax.getTaxYear());
        System.out.printf("Taxable Income : ₹%.2f%n", tax.getTaxableIncome());
        System.out.printf("Tax Amount     : ₹%.2f%n", tax.getTaxAmount());
        System.out.println("------------------------------------------");
    }
}
