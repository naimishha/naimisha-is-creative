package Business.WorkQueue;

import Business.Patient.Patient;

public class PrescriptionWorkRequest extends WorkRequest {
    private String medicineName;
    private String dosage;
    private String duration;
    private String instructions;
    private String medicineId;  // Added
    private boolean fulfilled;
    private double price;       // Added
    private Patient patient;    // Added patient reference

    // Constructor
    public PrescriptionWorkRequest() {
        super();
    }

    // Getters and Setters
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // Overriding toString for better logging or debugging
    @Override
    public String toString() {
        return "PrescriptionWorkRequest{" +
                "medicineName='" + medicineName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", duration='" + duration + '\'' +
                ", instructions='" + instructions + '\'' +
                ", medicineId='" + medicineId + '\'' +
                ", fulfilled=" + fulfilled +
                ", price=" + price +
                ", patient=" + (patient != null ? patient.getName() : "N/A") +
                '}';
    }
}
