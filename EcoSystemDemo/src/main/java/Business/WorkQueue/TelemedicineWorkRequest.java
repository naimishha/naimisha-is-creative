package Business.WorkQueue;

public class TelemedicineWorkRequest extends WorkRequest {
    private String vitals;
    private String notes;
    private String sessionDuration;
    private String consultationType;  // Video/Audio
    
    public TelemedicineWorkRequest() {
        super();
    }

    public String getVitals() {
        return vitals;
    }

    public void setVitals(String vitals) {
        this.vitals = vitals;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(String sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public String getConsultationType() {
        return consultationType;
    }

    public void setConsultationType(String consultationType) {
        this.consultationType = consultationType;
    }
}