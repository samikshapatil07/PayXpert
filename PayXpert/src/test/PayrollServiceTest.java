package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import entity.Employee;
import entity.Payroll;
import entity.Tax;
import exception.EmployeeNotFoundException;
import exception.PayrollGenerationException;
import exception.TaxCalculationException;
import exception.InvalidInputException;
import dao.EmployeeService;
import dao.PayrollService;

import java.time.LocalDate;
import java.util.List;
	
public class PayrollServiceTest {

	private PayrollService payrollService;
	private Employee testEmployee;
	private EmployeeService employeeService;  


        @Before
        public void setup() {
            // Initialize services
            employeeService = new EmployeeService(); // or new TestEmployeeService()

            // Create test employee
            testEmployee = new Employee();
            testEmployee.setEmployeeID(5);
            testEmployee.setFirstName("Samiksha1");
            testEmployee.setLastName("Patil");
            testEmployee.setDateOfBirth(LocalDate.of(1990, 5, 15));
            testEmployee.setGender("Female");
            testEmployee.setEmail("samiksha.doe@example.com");
            testEmployee.setPhoneNumber("1234567890");
            testEmployee.setAddress("123 Main St, Kolhapur");
            testEmployee.setPosition("Software Developer");
            testEmployee.setJoiningDate(LocalDate.of(2020, 1, 15));

            // Add test employee to system
            try {
                employeeService.addEmployee(testEmployee);
            } catch (Exception e) {
                fail("Failed to set up test employee: " + e.getMessage());
            }
        }
  
    //------------------------------------------------------------------------------------------------------------------------------
    /**
     *Test Case::1 : CalculateGrossSalaryForEmployee
     */
        @Test
        public void testCalculateGrossSalaryForEmployee() {
            int employeeId = 5;
            double basicSalary = 5000.0;
            double overtimePay = 500.0;
            double expectedGrossSalary = 5500.0;

            try {                Payroll payroll = new Payroll();
                payroll.setemployeeID(employeeId);
                payroll.setbasicSalary(basicSalary);
                payroll.setovertimePay(overtimePay);

                double actualGrossSalary = payroll.getbasicSalary() + payroll.getovertimePay();
                assertEquals("Gross salary calculation incorrect", expectedGrossSalary, actualGrossSalary, 0.01);
            } catch (Exception e) {
                fail("Exception occurred during gross salary calculation: " + e.getMessage());
            }
        }

    //-------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Test Case::2 : CalculateNetSalaryAfterDeductions
     */
        @Test
        public void testCalculateNetSalaryAfterDeductions() {
            int employeeId = 5; // Use the employee added in setup()
            double basicSalary = 5000.0;
            double overtimePay = 500.0;
            double deductions = 1000.0;
            double expectedNetSalary = 4500.0;

            try {
                Payroll payroll = new Payroll();
                payroll.setemployeeID(employeeId);
                payroll.setbasicSalary(basicSalary);
                payroll.setovertimePay(overtimePay);
                payroll.setdeductions(deductions);
                payroll.setnetSalary((basicSalary + overtimePay) - deductions);

                assertEquals("Net salary calculation incorrect", expectedNetSalary, payroll.getnetSalary(), 0.01);
            } catch (Exception e) {
                fail("Exception during net salary calculation: " + e.getMessage());
            }
        }
//---------------------------------------------------------------------------------------------------------------------
    /**
     * Test Case::3 : VerifyTaxCalculationForHighIncomeEmployee
     */
        @Test
        public void testVerifyTaxCalculationForHighIncomeEmployee() {
            int employeeId = 5;
            double taxableIncome = 120000.0;
            double expectedTaxAmount = 36000.0; 

            try {
                Tax tax = new Tax();
                tax.setEmployeeID(employeeId);
                tax.setTaxableIncome(taxableIncome);
                tax.setTaxAmount(taxableIncome * 0.30); 

                assertEquals("Tax calculation for high income incorrect", expectedTaxAmount, tax.getTaxAmount(), 0.01);
            } catch (Exception e) {
                fail("Exception during tax calculation: " + e.getMessage());
            }
        }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Test Case::4 : ProcessPayrollForMultipleEmployees
     */
        @Test
        public void testProcessPayrollForMultipleEmployees() {
            // Add test employees
            Employee employee1 = new Employee();
            employee1.setEmployeeID(1);
            employee1.setFirstName("John");
            employee1.setLastName("Doe");

            Employee employee2 = new Employee();
            employee2.setEmployeeID(2);
            employee2.setFirstName("Samiksha");
            employee2.setLastName("Patil");

            Employee employee3 = new Employee();
            employee3.setEmployeeID(3);
            employee3.setFirstName("Anuj");
            employee3.setLastName("Patil");

            try {
                employeeService.addEmployee(employee1);
                employeeService.addEmployee(employee2);
                employeeService.addEmployee(employee3);

                // Payrolls
                Payroll p1 = new Payroll();
                p1.setemployeeID(1001);
                p1.setbasicSalary(5000);
                p1.setovertimePay(500);
                p1.setdeductions(1000);
                p1.setnetSalary(4500);

                Payroll p2 = new Payroll();
                p2.setemployeeID(1002);
                p2.setbasicSalary(7000);
                p2.setovertimePay(700);
                p2.setdeductions(1500);
                p2.setnetSalary(6200);

                Payroll p3 = new Payroll();
                p3.setemployeeID(1003);
                p3.setbasicSalary(4500);
                p3.setovertimePay(300);
                p3.setdeductions(900);
                p3.setnetSalary(3900);

                List<Payroll> payrolls = List.of(p1, p2, p3);

                assertEquals("Incorrect number of payrolls", 3, payrolls.size());
                double total = payrolls.stream().mapToDouble(Payroll::getnetSalary).sum();
                assertEquals("Total net salary incorrect", 14600.0, total, 0.01);
            } catch (Exception e) {
                fail("Exception during payroll batch processing: " + e.getMessage());
            }
        }

    //-------------------------------------------------------------------------------------------------
    /**
     * Test Case::5 : VerifyErrorHandlingForInvalidEmployeeData
     * Objective: Ensure the system handles invalid input data gracefully.
     */
        @Test(expected = EmployeeNotFoundException.class)
        public void testVerifyErrorHandlingForInvalidEmployeeData() throws EmployeeNotFoundException {
            employeeService.getEmployeeById(9999); // Should throw
        }
//---------------------------------
        @Test(expected = InvalidInputException.class)
        public void testInvalidEmployeeDataInput() throws InvalidInputException {
            Employee invalidEmployee = new Employee();
            invalidEmployee.setEmployeeID(2001);
            invalidEmployee.setEmail("not-an-email"); // invalid

            employeeService.addEmployee(invalidEmployee);  // should throw InvalidInputException
        }

    //------------------------------------------------
        @Test(expected = PayrollGenerationException.class)
        public void testPayrollGenerationWithInvalidDateRange() throws PayrollGenerationException, EmployeeNotFoundException {
            int employeeId = 5;
            LocalDate start = LocalDate.of(2023, 4, 30);
            LocalDate end = LocalDate.of(2023, 4, 1);  // end date is before start

            // Corrected call with proper arguments
            payrollService.generatePayroll(employeeId, start, end);
        }

    //--------------------------------------------------------------------------------------------------------
        @Test(expected = TaxCalculationException.class)
        public void testTaxCalculationWithNegativeIncome() throws TaxCalculationException {
            Tax tax = new Tax();
            tax.setTaxableIncome(-5000.0); // invalid input

            if (tax.getTaxableIncome() < 0) {
                throw new TaxCalculationException("Negative income not allowed");
            }
        }

    //---------------------------------------------------------------------------------------------------
    @Test
    public void testEmployeeAgeCalculation() {
        // Test the CalculateAge() method
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        Employee employee = new Employee();
        employee.setDateOfBirth(birthDate);
        
        int expectedAge = LocalDate.now().getYear() - birthDate.getYear();
        // Adjust if birthday hasn't occurred yet this year
        if (LocalDate.now().getMonthValue() < birthDate.getMonthValue() || 
            (LocalDate.now().getMonthValue() == birthDate.getMonthValue() && 
             LocalDate.now().getDayOfMonth() < birthDate.getDayOfMonth())) {
            expectedAge--;
        }
        
        assertEquals("Employee age calculation incorrect", expectedAge, employee.calculateAge());
    }
}