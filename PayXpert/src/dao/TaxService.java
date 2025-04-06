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
        
        // Simple tax calculation logic
        double taxableIncome = 60000.0; // Placeholder value
        double taxAmount = taxableIncome * 0.20; // Simplified tax calculation (20% tax rate)
        
        Tax tax = new Tax();
        tax.setEmployeeID(employeeId);
        tax.setTaxYear(taxYear);
        tax.setTaxableIncome(taxableIncome);
        tax.setTaxAmount(taxAmount);
        
        // Save to database
        String sql = "INSERT INTO Tax (EmployeeID, TaxYear, TaxableIncome, TaxAmount) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, tax.getEmployeeID());
            stmt.setInt(2, tax.getTaxYear());
            stmt.setDouble(3, tax.getTaxableIncome());
            stmt.setDouble(4, tax.getTaxAmount());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    tax.setTaxID(rs.getInt(1));
                }
                return tax;
            } else {
                throw new TaxCalculationException("Failed to create tax record");
            }
            
        } catch (SQLException | DatabaseConnectionException e) {
            throw new TaxCalculationException("Error calculating tax: " + e.getMessage());
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
        // Check if employee exists
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