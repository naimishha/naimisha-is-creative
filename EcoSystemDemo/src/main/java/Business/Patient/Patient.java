package Business.Patient;

import Business.WorkQueue.LabTestWorkRequest;
import Business.WorkQueue.PrescriptionWorkRequest;
import java.util.ArrayList;

public class Patient {
    private String name;
    private int age;
    private String gender;
    private String contact;
    private String id;
    private ArrayList<Object[]> medicalHistory;
    private ArrayList<LabTestWorkRequest> labTestHistory;
    private ArrayList<PrescriptionWorkRequest> prescriptionHistory;
    
    public Patient() {
        this.medicalHistory = new ArrayList<>();
        this.labTestHistory = new ArrayList<>();
        this.prescriptionHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Object[]> getMedicalHistory() {
        return medicalHistory;
    }

    public ArrayList<LabTestWorkRequest> getLabTestHistory() {
        return labTestHistory;
    }

    public ArrayList<PrescriptionWorkRequest> getPrescriptionHistory() {
        return prescriptionHistory;
    }
}