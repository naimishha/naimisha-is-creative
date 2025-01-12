package Business.UserAccount;

import Business.Employee.Employee;
import Business.Patient.Patient;
import Business.Role.Role;
import Business.WorkQueue.WorkQueue;

public class UserAccount {
    private String username;
    private String password;
    private Employee employee;
    private Patient patient;  // Add Patient object
    private Role role;
    private WorkQueue workQueue;  // WorkQueue for task management
    
    public UserAccount() {
        workQueue = new WorkQueue(); // Initialize WorkQueue in the constructor
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public WorkQueue getWorkQueue() {
        if (workQueue == null) {  // Safety check to ensure WorkQueue is never null
            workQueue = new WorkQueue();
        }
        return workQueue;
    }

    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }
    
    public Patient getPatient() {
        // Safe getter for Patient; if not set, returns null
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // Override toString for better usability in JComboBoxes and logs
    @Override
    public String toString() {
        return username != null ? username : "Unknown User";
    }
}
