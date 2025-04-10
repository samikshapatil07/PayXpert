package service;
import java.util.List;

import entity.FinancialRecord;
import entity.Payroll;
import entity.Tax;

public class ReportGenerator {

    public static void generatePayrollReport(List<Payroll> payrolls) {
        System.out.println("=== Payroll Report ===");
        for (Payroll payroll : payrolls) {
            System.out.printf("EmpID: %d | Period: %s to %s | Net Salary: ₹%.2f%n",
                    payroll.getemployeeID(),
                    payroll.getpayPeriodStartDate(),
                    payroll.getpayPeriodEndDate(),
                    payroll.getnetSalary());
        }
    }

    public static void generateTaxSummary(List<Tax> taxes) {
        System.out.println("=== Tax Summary ===");
        for (Tax tax : taxes) {
            System.out.printf("EmpID: %d | Year: %d | Tax: ₹%.2f%n",
                    tax.getEmployeeID(),
                    tax.getTaxYear(),
                    tax.getTaxAmount());
        }
    }

    public static void generateFinancialStatement(List<FinancialRecord> records) {
        System.out.println("=== Financial Statement ===");
        for (FinancialRecord record : records) {
            System.out.printf("EmpID: %d | Date: %s | Type: %s | ₹%.2f | %s%n",
                    record.getEmployeeID(),
                    record.getrecordDate(),
                    record.getrecordType(),
                    record.getamount(),
                    record.getdescription());
        }
    }
}
