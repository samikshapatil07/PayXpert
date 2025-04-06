package entity;

import java.time.LocalDate;
import java.time.Period;

public class Employee {
	private int employeeID;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String position;
    private LocalDate joiningDate;
    private LocalDate terminationDate;
    
 // Default constructor
    public Employee() {}
    // Parameterized constructor
    public Employee(int employeeID, String firstName, String lastName, LocalDate dateOfBirth,
                   String gender, String email, String phoneNumber, String address,
                   String position, LocalDate joiningDate, LocalDate terminationDate) {
        this.employeeID = employeeID;
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.position = position;
        this.joiningDate = joiningDate;
        this.terminationDate = terminationDate;
 }
    //all getter setter
    //for employeeID:
        public int getEmployeeID() {
            return employeeID;
        }
        public void setEmployeeID(int employeeID) {
            this.employeeID = employeeID;
        }
      //for firstName:
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
      //for lastName
        public String getLastName() {
            return lastName;
        }
                public void setLastName(String lastName) {
            this.lastName = lastName;
        }
      //for dateOfBirth
        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }
                public void setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }
      //for gender
        public String getGender() {
            return gender;
        }
                public void setGender(String gender) {
            this.gender = gender;
        }
      //for email
        public String getEmail() {
            return email;
        }
                public void setEmail(String email) {
            this.email = email;
        }
      //for phoneNumber
        public String getPhoneNumber() {
            return phoneNumber;
        }
                public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
      //for address
        public String getAddress() {
            return address;
        }
                public void setAddress(String address) {
            this.address = address;
        }
      //for position
        public String getPosition() {
            return position;
        }
                public void setPosition(String position) {
            this.position = position;
        }
      //for joiningDate
        public LocalDate getJoiningDate() {
            return joiningDate;
        }
                public void setJoiningDate(LocalDate joiningDate) {
            this.joiningDate = joiningDate;
        }
      //for terminationDate
        public LocalDate getTerminationDate() {
            return terminationDate;
        }
        public void setTerminationDate(LocalDate terminationDate) {
            this.terminationDate = terminationDate;
        }
    // Calculate Age method
    public int calculateAge() {
        if (dateOfBirth == null) {
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
    @Override
    public String toString() {
        return "Employee [employeeID=" + employeeID + ", firstName=" + firstName + ", lastName=" + lastName + 
               ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", email=" + email + 
               ", phoneNumber=" + phoneNumber + ", position=" + position + "]";
    }
}


