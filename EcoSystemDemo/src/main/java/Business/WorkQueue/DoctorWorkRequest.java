package Business.WorkQueue;

import Business.Patient.Patient;
import java.util.Date;

public class DoctorWorkRequest extends WorkRequest {
    private Patient patient;
    private Date appointmentDate;
    private String appointmentType;
    // Additional fields for patient care
    private String diagnosis;
    private String treatment;
    private String notes;
    private boolean isUrgent;
    // Fields for vitals
    private String bloodPressure;
    private String temperature;
    private String heartRate;
    // Fields for telemedicine
    private boolean isTelemedicine;
    private String meetingLink;

    public DoctorWorkRequest() {
        super();
    }
    
    // Basic getters and setters
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    
    public Date getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(Date date) { this.appointmentDate = date; }
    
    public String getAppointmentType() { return appointmentType; }
    public void setAppointmentType(String type) { this.appointmentType = type; }
    
    // Additional getters and setters
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    
    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public boolean isUrgent() { return isUrgent; }
    public void setUrgent(boolean urgent) { isUrgent = urgent; }
    
    // Vitals getters and setters
    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }
    
    public String getTemperature() { return temperature; }
    public void setTemperature(String temperature) { this.temperature = temperature; }
    
    public String getHeartRate() { return heartRate; }
    public void setHeartRate(String heartRate) { this.heartRate = heartRate; }
    
    // Telemedicine getters and setters
    public boolean isTelemedicine() { return isTelemedicine; }
    public void setTelemedicine(boolean telemedicine) { isTelemedicine = telemedicine; }
    
    public String getMeetingLink() { return meetingLink; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }

    @Override
    public String toString() {
        return "Appointment for " + patient.getName() + " on " + appointmentDate;
    }
}