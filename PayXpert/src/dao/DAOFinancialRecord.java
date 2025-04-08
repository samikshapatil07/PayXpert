package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBConnUtil;

public class DAOFinancialRecord {

    public static void generateFinancialReport(int empId) {
    	
        try (Connection conn = DBConnUtil.getConnection()) {

            double totalIncome = 0;
            double totalDeductions = 0;
            double totalTaxPaid = 0;

            // Fetch financial records
            String recordQuery = "SELECT * FROM FinancialRecord WHERE EmployeeID = ?";
            try (PreparedStatement recStmt = conn.prepareStatement(recordQuery)) {
                recStmt.setInt(1, empId);
                ResultSet recRs = recStmt.executeQuery();

                while (recRs.next()) {
                    double amount = recRs.getDouble("Amount");
                    String type = recRs.getString("RecordType");

                    if ("income".equalsIgnoreCase(type)) {
                        totalIncome += amount;
                    } else if ("expense".equalsIgnoreCase(type)) {
                        totalDeductions += amount;
                    }
                }
            }

            // Fetch tax records
            String taxQuery = "SELECT SUM(TaxAmount) AS TotalTax FROM Tax WHERE EmployeeID = ?";
            try (PreparedStatement taxStmt = conn.prepareStatement(taxQuery)) {
                taxStmt.setInt(1, empId);
                ResultSet taxRs = taxStmt.executeQuery();
                if (taxRs.next()) {
                    totalTaxPaid = taxRs.getDouble("TotalTax");
                }
            }

            // Fetch employee name
            String empName = "Unknown";
            String nameQuery = "SELECT FirstName FROM Employee WHERE EmployeeID = ?";
            try (PreparedStatement nameStmt = conn.prepareStatement(nameQuery)) {
                nameStmt.setInt(1, empId);
                ResultSet nameRs = nameStmt.executeQuery();
                if (nameRs.next()) {
                    empName = nameRs.getString("FirstName");
                }
            }

            // Output
            System.out.println("\n--- Financial Report ---");
            System.out.println("Employee: " + empName + " (ID: " + empId + ")");
            System.out.printf("  Total Income    : ₹%.2f%n", totalIncome);
            System.out.printf("  Total Deductions: ₹%.2f%n", totalDeductions);
            System.out.printf("  Net Income      : ₹%.2f%n", (totalIncome - totalDeductions));
            System.out.printf("  Total Tax Paid  : ₹%.2f%n", totalTaxPaid);

        } catch (SQLException e) {
            System.out.println("Error generating financial report: " + e.getMessage());
        }
    }
}
