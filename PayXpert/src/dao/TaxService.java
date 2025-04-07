package dao;

import entity.Tax;
import exception.DatabaseConnectionException;
import exception.EmployeeNotFoundException;
import exception.TaxCalculationException;
import util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaxService implements ITaxService {

    private final IEmployeeService employeeService;

    public TaxService() {
        this.employeeService = new EmployeeService();
    }

    private Connection getConnection() throws DatabaseConnectionException {
        return DBConnUtil.getConnection(); 
    }

    @Override
    public Tax calculateTax(int employeeId, int taxYear) throws TaxCalculationException, EmployeeNotFoundException {
        employeeService.getEmployeeById(employeeId);

        String payrollQuery = "SELECT SUM(GrossSalary) AS TotalIncome, SUM(Deductions) AS TotalDeductions " +
                              "FROM Payroll WHERE EmployeeID = ? AND YEAR(PayPeriodEndDate) = ?";

        double totalIncome = 0.0;
        double totalDeductions = 0.0;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(payrollQuery)) {

            stmt.setInt(1, employeeId);
            stmt.setInt(2, taxYear);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalIncome = rs.getDouble("TotalIncome");
                totalDeductions = rs.getDouble("TotalDeductions");
            }

            double taxableIncome = totalIncome - totalDeductions;
            double taxAmount = calculateTaxAmount(taxableIncome);

            String insertSql = "INSERT INTO Tax (EmployeeID, TaxYear, TaxableIncome, TaxAmount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setInt(1, employeeId);
                insertStmt.setInt(2, taxYear);
                insertStmt.setDouble(3, taxableIncome);
                insertStmt.setDouble(4, taxAmount);

                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int taxId = generatedKeys.getInt(1);
                        Tax tax = new Tax();
                        tax.setTaxID(taxId);
                        tax.setEmployeeID(employeeId);
                        tax.setTaxYear(taxYear);
                        tax.setTaxableIncome(taxableIncome);
                        tax.setTaxAmount(taxAmount);
                        return tax;
                    }
                }
                throw new TaxCalculationException("Failed to insert tax record.");
            }

        } catch (SQLException | DatabaseConnectionException e) {
            throw new TaxCalculationException("Error calculating tax: " + e.getMessage());
        }
    }

    private double calculateTaxAmount(double taxableIncome) {
        if (taxableIncome <= 250000) {
            return 0;
        } else if (taxableIncome <= 500000) {
            return taxableIncome * 0.05;
        } else if (taxableIncome <= 1000000) {
            return taxableIncome * 0.20;
        } else {
            return taxableIncome * 0.30;
        }
    }

    @Override
    public Tax getTaxById(int taxId) throws TaxCalculationException {
        String sql = "SELECT * FROM Tax WHERE TaxID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, taxId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Tax tax = new Tax();
                tax.setTaxID(rs.getInt("TaxID"));
                tax.setEmployeeID(rs.getInt("EmployeeID"));
                tax.setTaxYear(rs.getInt("TaxYear"));
                tax.setTaxableIncome(rs.getDouble("TaxableIncome"));
                tax.setTaxAmount(rs.getDouble("TaxAmount"));

                return tax;
            } else {
                throw new TaxCalculationException("Tax with ID " + taxId + " not found");
            }

        } catch (SQLException | DatabaseConnectionException e) {
            throw new TaxCalculationException("Error retrieving tax: " + e.getMessage());
        }
    }

    @Override
    public List<Tax> getTaxesForEmployee(int employeeId) throws EmployeeNotFoundException {
        employeeService.getEmployeeById(employeeId);

        List<Tax> taxes = new ArrayList<>();
        String sql = "SELECT * FROM Tax WHERE EmployeeID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tax tax = new Tax();
                tax.setTaxID(rs.getInt("TaxID"));
                tax.setEmployeeID(rs.getInt("EmployeeID"));
                tax.setTaxYear(rs.getInt("TaxYear"));
                tax.setTaxableIncome(rs.getDouble("TaxableIncome"));
                tax.setTaxAmount(rs.getDouble("TaxAmount"));

                taxes.add(tax);
            }

            return taxes;

        } catch (SQLException | DatabaseConnectionException e) {
            throw new EmployeeNotFoundException("Error retrieving taxes for employee: " + e.getMessage());
        }
    }

    @Override
    public List<Tax> getTaxesForYear(int taxYear) {
        List<Tax> taxes = new ArrayList<>();
        String sql = "SELECT * FROM Tax WHERE TaxYear = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, taxYear);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tax tax = new Tax();
                tax.setTaxID(rs.getInt("TaxID"));
                tax.setEmployeeID(rs.getInt("EmployeeID"));
                tax.setTaxYear(rs.getInt("TaxYear"));
                tax.setTaxableIncome(rs.getDouble("TaxableIncome"));
                tax.setTaxAmount(rs.getDouble("TaxAmount"));

                taxes.add(tax);
            }

        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error retrieving taxes for year: " + e.getMessage());
        }

        return taxes;
    }
}
