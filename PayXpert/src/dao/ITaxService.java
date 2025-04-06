package dao;

import entity.Tax;
import exception.EmployeeNotFoundException;
import exception.TaxCalculationException;

import java.util.List;

public interface ITaxService {
    Tax calculateTax(int employeeId, int taxYear) throws TaxCalculationException, EmployeeNotFoundException;
    Tax getTaxById(int taxId) throws TaxCalculationException;
    List<Tax> getTaxesForEmployee(int employeeId) throws EmployeeNotFoundException;
    List<Tax> getTaxesForYear(int taxYear);
}