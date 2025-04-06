package util;

import java.time.LocalDate;
//import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import entity.Payroll;

public class ReportGenerator {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
    /**
     * Generates an income statement for the company
     * 
     * @param payrollList List of all payrolls
     * @param startDate Start date of the report period
     * @param endDate End date of the report period
     * @param outputFile The file to write the income statement to
     * @throws IOException if writing to file fails
     */
    public static void generateIncomeStatement(List<Payroll> payrollList, 
                                              LocalDate startDate, LocalDate endDate, 
                                              String outputFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println("===========================================");
            writer.println("              INCOME STATEMENT             ");
            writer.println("===========================================");
            writer.println();
            writer.println("Period: " + startDate.format(DATE_FORMATTER) + 
                           " to " + endDate.format(DATE_FORMATTER));
            writer.println();
            
            double totalBasicSalary = 0;
            double totalOvertimePay = 0;
            double totalDeductions = 0;
            double totalNetSalary = 0;
            int employeeCount = payrollList.size();
            
            for (Payroll payroll : payrollList) {
                totalBasicSalary += payroll.getbasicSalary();
                totalOvertimePay += payroll.getovertimePay();
                totalDeductions += payroll.getdeductions();
                totalNetSalary += payroll.getnetSalary();
            }
            
            writer.println("Number of Employees: " + employeeCount);
            writer.println();
            writer.println("EXPENSES:");
            writer.println("Basic Salaries: $" + String.format("%.2f", totalBasicSalary));
            writer.println("Overtime Pay: $" + String.format("%.2f", totalOvertimePay));
            writer.println("Total Gross Payroll: $" + String.format("%.2f", totalBasicSalary + totalOvertimePay));
            writer.println();
            writer.println("Total Deductions: $" + String.format("%.2f", totalDeductions));
            writer.println();
            writer.println("Total Net Payroll: $" + String.format("%.2f", totalNetSalary));
            writer.println();
            writer.println("Average Net Salary: $" + 
                          String.format("%.2f", employeeCount > 0 ? totalNetSalary / employeeCount : 0));
            writer.println();
            writer.println("===========================================");
            writer.println("Generated on: " + LocalDate.now().format(DATE_FORMATTER));
        }
    }
}
    