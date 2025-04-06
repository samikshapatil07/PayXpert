package dao;

import entity.Employee;
import exception.EmployeeNotFoundException;
import exception.InvalidInputException;

import java.util.List;

public interface IEmployeeService {
    Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException;
    List<Employee> getAllEmployees();
    boolean addEmployee(Employee employee) throws InvalidInputException;
    boolean updateEmployee(Employee employee) throws EmployeeNotFoundException, InvalidInputException;
    boolean removeEmployee(int employeeId) throws EmployeeNotFoundException;
}