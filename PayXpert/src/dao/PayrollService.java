package dao;

import entity.Employee;
import entity.Payroll;
import exception.DatabaseConnectionException;
import exception.EmployeeNotFoundException;
import exception.PayrollGenerationException;
import util.DBConnUtil;

import java.sql.*;
import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;

public class PayrollService implements IPayrollService {

    private final IEmployeeService employeeService;

    public PayrollService() {
        this.employeeService = new EmployeeService();
    }

    private Connection getConnection() throws DatabaseConnectionException {
        return DBConnUtil.getConnection();
    }

    @Override
    public Payroll generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate)
            throws PayrollGenerationException, EmployeeNotFoundException {

        employeeService.getEmployeeById(employeeId);

        double basicSalary = 5000.0;
        double overtimePay = 1000.0;
        double deductions = 1500.0;
        double netSalary = basicSalary + overtimePay - deductions;

        Payroll payroll = new Payroll();
        payroll.setemployeeID(employeeId);
        payroll.setpayPeriodStartDate(startDate);
        payroll.setpayPeriodEndDate(endDate);
        payroll.setbasicSalary(basicSalary);
        payroll.setovertimePay(overtimePay);
        payroll.setdeductions(deductions);
        payroll.setnetSalary(netSalary);

        String sql = "INSERT INTO Payroll (EmployeeID, PayPeriodStartDate, PayPeriodEndDate, " +
                     "BasicSalary, OvertimePay, Deductions, NetSalary) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, payroll.getemployeeID());
            stmt.setDate(2, Date.valueOf(payroll.getpayPeriodStartDate()));
            stmt.setDate(3, Date.valueOf(payroll.getpayPeriodEndDate()));
            stmt.setDouble(4, payroll.getbasicSalary());
            stmt.setDouble(5, payroll.getovertimePay());
            stmt.setDouble(6, payroll.getdeductions());
            stmt.setDouble(7, payroll.getnetSalary());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    payroll.setPayrollID(rs.getInt(1));
                }
                return payroll;
            } else {
                throw new PayrollGenerationException("Failed to generate payroll record");
            }

        } catch (SQLException | DatabaseConnectionException e) {
            throw new PayrollGenerationException("Error generating payroll: " + e.getMessage());
        }
    }

    @Override
    public Payroll getPayrollById(int payrollId) throws PayrollGenerationException {
        String sql = "SELECT * FROM Payroll WHERE PayrollID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payrollId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractPayrollFromResultSet(rs);
            } else {
                throw new PayrollGenerationException("Payroll with ID " + payrollId + " not found");
            }

        } catch (SQLException | DatabaseConnectionException e) {
            throw new PayrollGenerationException("Error retrieving payroll: " + e.getMessage());
        }
    }

    @Override
    public void getPayrollsForEmployee(int employeeId) throws EmployeeNotFoundException {
        employeeService.getEmployeeById(employeeId);

        String sql = "SELECT * FROM Payroll WHERE EmployeeID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                Payroll payroll = extractPayrollFromResultSet(rs);
                System.out.println(payroll);
            }

            if (!found) {
                System.out.println("No payroll records found for Employee ID: " + employeeId);
            }

        } catch (SQLException | DatabaseConnectionException e) {
            throw new EmployeeNotFoundException("Error retrieving payrolls for employee: " + e.getMessage());
        }
    }

 
    private Payroll extractPayrollFromResultSet(ResultSet rs) throws SQLException {
        Payroll payroll = new Payroll();
        payroll.setPayrollID(rs.getInt("PayrollID"));
        payroll.setemployeeID(rs.getInt("EmployeeID"));
        payroll.setpayPeriodStartDate(rs.getDate("PayPeriodStartDate").toLocalDate());
        payroll.setpayPeriodEndDate(rs.getDate("PayPeriodEndDate").toLocalDate());
        payroll.setbasicSalary(rs.getDouble("BasicSalary"));
        payroll.setovertimePay(rs.getDouble("OvertimePay"));
        payroll.setdeductions(rs.getDouble("Deductions"));
        payroll.setnetSalary(rs.getDouble("NetSalary"));
        return payroll;
    }

    public double calculateNetSalary(double basicSalary, double overtimePay, double deductions) {
        return basicSalary + overtimePay - deductions;
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        return null; 
    }

	@Override
	public void getPayrollsForPeriod(LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		
	}

	
} 
