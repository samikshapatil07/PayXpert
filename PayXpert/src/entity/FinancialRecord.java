package entity;
import java.time.LocalDate;
public class FinancialRecord {
	private int recordID;
    private int employeeID;
    private LocalDate recordDate;
    private String description;
    private double amount;
    private String recordType;
    
    // Default constructor
    public FinancialRecord() {}
    
    // Parameterized constructor
    public FinancialRecord(int recordID, int employeeID, LocalDate recordDate, 
                          String description, double amount, String recordType) {
        this.recordID = recordID;
        this.employeeID = employeeID;
        this.recordDate = recordDate;
        this.description = description;
        this.amount = amount;
        this.recordType = recordType;
    }
    
    // Getters and Setters
    //for recordID
    public int getrecordID() {
        return recordID;
    }
    public void setrecordID(int recordID) {
        this.recordID = recordID;
    }
    //for employeeID
    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    //for recordDate:
    public LocalDate getrecordDate() {
        return recordDate;
    }
    public void setrecordDate(LocalDate recordDate) {
         this.recordDate = recordDate;
    }
     //for description
     public String getdescription() {
            return description;
        }
      public void setdescription(String description) {
            this.description = description;
        }
     //for amount
      public double getamount() {
          return amount;
      }
              public void setamount(double amount) {
          this.amount = amount;
      }
     //for recordType
      public String getrecordType() {
              return recordType;
              }
       public void setrecordType(String recordType) {
                  this.recordType = recordType;
              }
}
