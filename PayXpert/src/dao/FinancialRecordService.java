package dao;

import entity.FinancialRecord;
import exception.DatabaseConnectionException;
import exception.EmployeeNotFoundException;
import exception.FinancialRecordException;
import util.DBConnUtil;

//import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinancialRecordService implements IFinancialRecordService {
    
    private final IEmployeeService employeeService;
    
    public FinancialRecordService() {
        this.employeeService = new EmployeeService();
    }
    
    private Connection getConnection() throws DatabaseConnectionException {
        return DBConnUtil.getConnection(); 
    }

    @Override
    public boolean addFinancialRecord(int employeeId, String description, double amount, String recordType) 
            throws FinancialRecordException, EmployeeNotFoundException {
        
        // Check if employee exists
        employeeService.getEmployeeById(employeeId);
        
        String sql = "INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, employeeId);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setString(3, description);
            stmt.setDouble(4, amount);
            stmt.setString(5, recordType);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | DatabaseConnectionException e) {
            throw new FinancialRecordException("Error adding financial record: " + e.getMessage());
        }
    }

    @Override
    public FinancialRecord getFinancialRecordById(int recordId) throws FinancialRecordException {
        String sql = "SELECT * FROM FinancialRecord WHERE RecordID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, recordId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                FinancialRecord record = new FinancialRecord();
                record.setrecordID(rs.getInt("RecordID"));
                record.setEmployeeID(rs.getInt("EmployeeID"));
                record.setrecordDate(rs.getDate("RecordDate").toLocalDate());
                record.setdescription(rs.getString("Description"));
                record.setamount(rs.getDouble("Amount"));
                record.setrecordType(rs.getString("RecordType"));
                
                return record;
            } else {
                throw new FinancialRecordException("Financial record with ID " + recordId + " not found");
            }
            
        } catch (SQLException | DatabaseConnectionException e) {
            throw new FinancialRecordException("Error retrieving financial record: " + e.getMessage());
        }
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForEmployee(int employeeId) throws EmployeeNotFoundException {
        // Check if employee exists
        employeeService.getEmployeeById(employeeId);
        
        List<FinancialRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM FinancialRecord WHERE EmployeeID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                FinancialRecord record = new FinancialRecord();
                record.setrecordID(rs.getInt("RecordID"));
                record.setEmployeeID(rs.getInt("EmployeeID"));
                record.setrecordDate(rs.getDate("RecordDate").toLocalDate());
                record.setdescription(rs.getString("Description"));
                record.setamount(rs.getDouble("Amount"));
                record.setrecordType(rs.getString("RecordType"));
                
                records.add(record);
            }
            
            return records;
            
        } catch (SQLException | DatabaseConnectionException e) {
            throw new EmployeeNotFoundException("Error retrieving financial records for employee: " + e.getMessage());
        }
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForDate(LocalDate recordDate) {
        List<FinancialRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM FinancialRecord WHERE RecordDate = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(recordDate));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                FinancialRecord record = new FinancialRecord();
                record.setrecordID(rs.getInt("RecordID"));
                record.setEmployeeID(rs.getInt("EmployeeID"));
                record.setrecordDate(rs.getDate("RecordDate").toLocalDate());
                record.setdescription(rs.getString("Description"));
                record.setamount(rs.getDouble("Amount"));
                record.setrecordType(rs.getString("RecordType"));
                
                records.add(record);
            }
            
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error retrieving financial records for date: " + e.getMessage());
        }
        
        return records;
    }
}