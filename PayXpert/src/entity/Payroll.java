package entity;
import java.time.LocalDate;
public class Payroll {
	    private int payrollID;
	    private int employeeID;
	    private LocalDate payPeriodStartDate;
	    private LocalDate payPeriodEndDate;
	    private double basicSalary;
	    private double overtimePay;
	    private double deductions;
	    private double netSalary;
	    // Default constructor
public Payroll() {}
	 // Parameterized constructor
	    public Payroll(int payrollID, int employeeID, LocalDate payPeriodStartDate, 
	                  LocalDate payPeriodEndDate, double basicSalary, double overtimePay, 
	                  double deductions, double netSalary) {
	        this.payrollID = payrollID;
	        this.employeeID = employeeID;
	        this.payPeriodStartDate = payPeriodStartDate;
	        this.payPeriodEndDate = payPeriodEndDate;
	        this.basicSalary = basicSalary;
	        this.overtimePay = overtimePay;
	        this.deductions = deductions;
	        this.netSalary = calculateNetSalary();
	    }
	    private double calculateNetSalary() {
	        return basicSalary + overtimePay - deductions;
	    }

	    // Getters and Setter
	    //payrollID
	    public int getPayrollID() {
	        return payrollID;
	    }
	    public void setPayrollID(int payrollID) {
	        this.payrollID = payrollID;
	    }
	    //employeeID

	    public int getemployeeID() {
	        return employeeID;
	    }
	    public void setemployeeID(int employeeID) {
	        this.employeeID = employeeID;
	    }
	    //payPeriodStartDate
	    public LocalDate getpayPeriodStartDate() {
	        return payPeriodStartDate;
	    }
	    public void setpayPeriodStartDate(LocalDate payPeriodStartDate) {
	        this.payPeriodStartDate = payPeriodStartDate;
	    }
	    //payPeriodEndDate
	    public LocalDate getpayPeriodEndDate() {
	        return payPeriodEndDate;
	    }
	    public void setpayPeriodEndDate(LocalDate payPeriodEndDate) {
	        this.payPeriodEndDate = payPeriodEndDate;
	    }
	    //basicSalary
	    public double getbasicSalary() {
	        return basicSalary;
	    }
	    public void setbasicSalary(double basicSalary) {
	        this.basicSalary = basicSalary;
	    }
	    //overtimePay
	    public double getovertimePay() {
	        return overtimePay;
	    }
	    public void setovertimePay(double d) {
	        this.overtimePay = d;
	    }
	    //deductions
	    public double getdeductions() {
	        return deductions;
	    }
	    public void setdeductions(double d) {
	        this.deductions = d;
	    }
	    //netSalary
	    public double getnetSalary() {
	        return netSalary;
	    }
	    public void setnetSalary(double d) {
	        this.netSalary = d;
	    }
	    
	    @Override
	    public String toString() {
	        return "Payroll [payrollID=" + payrollID + ", employeeID=" + employeeID + 
	               ", payPeriodStartDate=" + payPeriodStartDate + ", payPeriodEndDate=" + payPeriodEndDate + 
	               ", basicSalary=" + basicSalary + ", overtimePay=" + overtimePay + 
	               ", deductions=" + deductions + ", netSalary=" + netSalary + "]";
	    }
	}