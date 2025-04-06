package dao;

import entity.FinancialRecord;
import exception.EmployeeNotFoundException;
import exception.FinancialRecordException;

import java.time.LocalDate;
import java.util.List;

public interface IFinancialRecordService {
    boolean addFinancialRecord(int employeeId, String description, double amount, String recordType) throws FinancialRecordException, EmployeeNotFoundException;
    FinancialRecord getFinancialRecordById(int recordId) throws FinancialRecordException;
    List<FinancialRecord> getFinancialRecordsForEmployee(int employeeId) throws EmployeeNotFoundException;
    List<FinancialRecord> getFinancialRecordsForDate(LocalDate recordDate);
}