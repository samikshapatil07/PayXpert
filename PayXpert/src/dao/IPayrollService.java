package dao;

import entity.Employee;
import entity.Payroll;
import exception.EmployeeNotFoundException;
import exception.PayrollGenerationException;

import java.time.LocalDate;
import java.util.List;

public interface IPayrollService {
    Payroll generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate) throws PayrollGenerationException, EmployeeNotFoundException;
    Payroll getPayrollById(int payrollId) throws PayrollGenerationException;
    List<Payroll> getPayrollsForEmployee(int employeeId) throws EmployeeNotFoundException;
    List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate);
	Employee getEmployeeById(int employeeId);
}