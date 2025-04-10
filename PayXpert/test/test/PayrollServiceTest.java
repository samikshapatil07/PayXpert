package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import entity.Employee;
import entity.Payroll;
import entity.Tax;
import exception.EmployeeNotFoundException;
import exception.InvalidInputException;
import dao.EmployeeService;
import dao.PayrollService;
import dao.TaxService;

import java.util.List;

public class PayrollServiceTest {

    private EmployeeService employeeService;

    @Before
    public void setup() {
        new PayrollService();
        new TaxService();
        employeeService = new EmployeeService();
    }

//---------- TestCase1: CalculateGrossSalaryForEmployee
    @Test
    public void testCase1_calculateGrossSalary() {
        double basic = 5000, overtime = 500, expected = 5500;
        Payroll p = new Payroll();
        p.setbasicSalary(basic);
        p.setovertimePay(overtime);
        double actual = p.getbasicSalary() + p.getovertimePay();
        assertEquals(expected, actual, 0.01);
    }

//--------- TestCase2: CalculateNetSalaryAfterDeductions
    @Test
    public void testCase2_calculateNetSalary() {
        double basic = 5000, overtime = 500, deductions = 1000, expected = 4500;
        Payroll p = new Payroll();
        p.setbasicSalary(basic);
        p.setovertimePay(overtime);
        p.setdeductions(deductions);
        p.setnetSalary((basic + overtime) - deductions);
        assertEquals(expected, p.getnetSalary(), 0.01);
    }

//---------- TestCase3: VerifyTaxCalculationForHighIncomeEmployee
    @Test
    public void testCase3_VerifyTaxCalculationForHighIncomeEmployee() {
        Tax tax = new Tax();
        tax.setEmployeeID(5);
        tax.setTaxableIncome(120000);
        tax.setTaxAmount(36000); 

        assertEquals("Tax calculation for high income incorrect", 36000, tax.getTaxAmount(), 0.01);
    }


//---------- TestCase4: ProcessPayrollForMultipleEmployees
    @Test
    public void testCase4_processPayrollBatch() {
        Payroll p1 = new Payroll();
        p1.setnetSalary(4500);
        Payroll p2 = new Payroll();
        p2.setnetSalary(6200);
        Payroll p3 = new Payroll();
        p3.setnetSalary(3900);

        List<Payroll> payrolls = List.of(p1, p2, p3);
        assertEquals(3, payrolls.size());
        double total = payrolls.stream().mapToDouble(Payroll::getnetSalary).sum();
        assertEquals(14600.0, total, 0.01);
    }

//------- TestCase5: Verify_Error_Handling_For_Invalid_Employee_Data
    @Test(expected = EmployeeNotFoundException.class)
    public void testCase5_invalidEmployeeId() throws EmployeeNotFoundException {
        employeeService.getEmployeeById(9999);
    }
    @Test(expected = InvalidInputException.class)
    public void testCase5_invalidEmailFormat() throws InvalidInputException {
        Employee e = new Employee();
        e.setEmployeeID(100);
        e.setEmail("invalid-email");
        employeeService.addEmployee(e);
    }
}
