package Business.WorkQueue;

import Business.Patient.Patient;

public class LabTestWorkRequest extends WorkRequest {
    private String department; // Radiology, Pathology, Blood Bank
    private String testResult;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public void setPatient(Patient patient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPatient'");
    }
}
