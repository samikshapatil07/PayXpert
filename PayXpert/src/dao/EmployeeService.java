package dao;

import entity.Employee; 
import exception.DatabaseConnectionException;
import exception.EmployeeNotFoundException;
import exception.InvalidInputException;
import util.DBConnUtil; // keep this
// Removed import for DBPropertyUtil

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements IEmployeeService {
    
    private Connection getConnection() throws DatabaseConnectionException {
        return DBConnUtil.getConnection(); 
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException {
        String sql = "SELECT * FROM Employee WHERE EmployeeID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeID(rs.getInt("EmployeeID"));
                employee.setFirstName(rs.getString("FirstName"));
                employee.setLastName(rs.getString("LastName"));
                employee.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
                employee.setGender(rs.getString("Gender"));
                employee.setEmail(rs.getString("Email"));
                employee.setPhoneNumber(rs.getString("PhoneNumber"));
                employee.setAddress(rs.getString("Address"));
                employee.setPosition(rs.getString("Position"));
                employee.setJoiningDate(rs.getDate("JoiningDate").toLocalDate());
                
                Date terminationDate = rs.getDate("TerminationDate");
                if (terminationDate != null) {
                    employee.setTerminationDate(terminationDate.toLocalDate());
                }
                
                return employee;
            } else {
                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found");
            }
            
        } catch (SQLException | DatabaseConnectionException e) {
            throw new EmployeeNotFoundException("Error retrieving employee: " + e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeID(rs.getInt("EmployeeID"));
                employee.setFirstName(rs.getString("FirstName"));
                employee.setLastName(rs.getString("LastName"));
                employee.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
                employee.setGender(rs.getString("Gender"));
                employee.setEmail(rs.getString("Email"));
                employee.setPhoneNumber(rs.getString("PhoneNumber"));
                employee.setAddress(rs.getString("Address"));
                employee.setPosition(rs.getString("Position"));
                employee.setJoiningDate(rs.getDate("JoiningDate").toLocalDate());
                
                Date terminationDate = rs.getDate("TerminationDate");
                if (terminationDate != null) {
                    employee.setTerminationDate(terminationDate.toLocalDate());
                }
                
                employees.add(employee);
            }
            
        } catch (SQLException | DatabaseConnectionException e) {
            System.err.println("Error retrieving all employees: " + e.getMessage());
        }
        
        return employees;
    }

    @Override
    public boolean addEmployee(Employee employee) throws InvalidInputException {
        if (employee == null) {
            throw new InvalidInputException("Employee cannot be null");
        }
        
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            throw new InvalidInputException("First name cannot be empty");
        }
        
        String sql = "INSERT INTO Employee (FirstName, LastName, DateOfBirth, Gender, Email, " +
                     "PhoneNumber, Address, Position, JoiningDate, TerminationDate) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setDate(3, Date.valueOf(employee.getDateOfBirth()));
            stmt.setString(4, employee.getGender());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getPhoneNumber());
            stmt.setString(7, employee.getAddress());
            stmt.setString(8, employee.getPosition());
            stmt.setDate(9, Date.valueOf(employee.getJoiningDate()));
            
            if (employee.getTerminationDate() != null) {
                stmt.setDate(10, Date.valueOf(employee.getTerminationDate()));
            } else {
                stmt.setNull(10, Types.DATE);
            }
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | DatabaseConnectionException e) {
            throw new InvalidInputException("Error adding employee: " + e.getMessage());
        }
    }

    @Override
    public boolean updateEmployee(Employee employee) throws EmployeeNotFoundException, InvalidInputException {
        if (employee == null) {
            throw new InvalidInputException("Employee cannot be null");
        }
        
        // Check if employee exists
        try {
            getEmployeeById(employee.getEmployeeID());
        } catch (EmployeeNotFoundException e) {
            throw e; // Re-throw if employee doesn't exist
        }
        
        String sql = "UPDATE Employee SET FirstName = ?, LastName = ?, DateOfBirth = ?, " +
                     "Gender = ?, Email = ?, PhoneNumber = ?, Address = ?, Position = ?, " +
                     "JoiningDate = ?, TerminationDate = ? WHERE EmployeeID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setDate(3, Date.valueOf(employee.getDateOfBirth()));
            stmt.setString(4, employee.getGender());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getPhoneNumber());
            stmt.setString(7, employee.getAddress());
            stmt.setString(8, employee.getPosition());
            stmt.setDate(9, Date.valueOf(employee.getJoiningDate()));
            
            if (employee.getTerminationDate() != null) {
                stmt.setDate(10, Date.valueOf(employee.getTerminationDate()));
            } else {
                stmt.setNull(10, Types.DATE);
            }
            
            stmt.setInt(11, employee.getEmployeeID());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | DatabaseConnectionException e) {
            throw new InvalidInputException("Error updating employee: " + e.getMessage());
        }
    }

    @Override
    public boolean removeEmployee(int employeeId) throws EmployeeNotFoundException {
        // Check if employee exists
        try {
            getEmployeeById(employeeId);
        } catch (EmployeeNotFoundException e) {
            throw e; // Re-throw if employee doesn't exist
        }
        
        String sql = "DELETE FROM Employee WHERE EmployeeID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, employeeId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | DatabaseConnectionException e) {
            throw new EmployeeNotFoundException("Error removing employee: " + e.getMessage());
        }
    }
}
