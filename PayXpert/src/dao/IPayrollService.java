package dao;

import entity.Employee;
import entity.Payroll;
import exception.EmployeeNotFoundException;
import exception.PayrollGenerationException;

import java.time.LocalDate;

public interface IPayrollService {
    Payroll generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate) throws PayrollGenerationException, EmployeeNotFoundException;
    Payroll getPayrollById(int payrollId) throws PayrollGenerationException;
    void getPayrollsForEmployee(int employeeId) throws EmployeeNotFoundException;
    void getPayrollsForPeriod(LocalDate startDate, LocalDate endDate);
	Employee getEmployeeById(int employeeId);
}