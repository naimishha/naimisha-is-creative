package ui.DoctorRole;

import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.AppointmentWorkRequest;
import Business.WorkQueue.DoctorWorkRequest;
import Business.WorkQueue.WorkRequest;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Date;



public class AppointmentJPanel extends JPanel {
    private JPanel userProcessContainer;
    private Patient patient;
    private JLabel patientIDLabel;
    private UserAccount doctor;
    private JComboBox<Patient> patientCombo; 
    
    private JDateChooser appointmentDate;
    private JComboBox<String> timeSlotCombo;
    private JComboBox<String> appointmentTypeCombo;
    private JTextArea notesArea;
    private JButton btnSchedule;
    private JButton btnBack;



public AppointmentJPanel(JPanel userProcessContainer, Patient patient, UserAccount doctor) {
    this.userProcessContainer = userProcessContainer;
    this.patient = patient;
    this.doctor = doctor;
    initComponents();
    setupLayout();
    setupListeners();
}

private void initComponents() {
    // Initialize components
    patientIDLabel = new JLabel("Patient ID: " + patient.getId()); // Assuming `getId()` gets the patient ID.
    patientIDLabel.setFont(new Font("Arial", Font.BOLD, 16));
    patientIDLabel.setForeground(new Color(70, 130, 180));

    // Existing initialization code...
    appointmentDate = new JDateChooser();
    appointmentDate.setMinSelectableDate(new Date());

    patientCombo = new JComboBox<>();
    populatePatientComboBox();  
    styleComboBox(patientCombo);

    timeSlotCombo = new JComboBox<>(new String[]{
        "Select Time",
        "09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM",
        "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM",
        "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM",
        "04:00 PM", "04:30 PM", "05:00 PM"
    });

    appointmentTypeCombo = new JComboBox<>(new String[]{
        "Select Type",
        "Regular Checkup",
        "Follow-up",
        "Specialist Consultation",
        "Telemedicine"
    });

    notesArea = new JTextArea(5, 20);
    notesArea.setLineWrap(true);
    notesArea.setWrapStyleWord(true);

    btnSchedule = createStyledButton("Schedule Appointment", new Color(70, 130, 180));
    btnBack = createStyledButton("Back", new Color(108, 117, 125));

    styleComponents();
}

private void styleComponents() {
    // Style date chooser
    appointmentDate.setFont(new Font("Arial", Font.PLAIN, 14));
    appointmentDate.setPreferredSize(new Dimension(300, 35));

    // Style combos
    styleComboBox(timeSlotCombo);
    styleComboBox(appointmentTypeCombo);

    // Style text area
    notesArea.setFont(new Font("Arial", Font.PLAIN, 14));
}
    // Override toString in Patient class or add this renderer
    private class PatientRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Patient) {
                Patient patient = (Patient) value;
                value = "ID: " + patient.getId() + " - " + patient.getName();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }


private void populatePatientComboBox() {
        // Get all patients from doctor's work queue
        for (WorkRequest request : doctor.getWorkQueue().getWorkRequestList()) {
            if (request instanceof DoctorWorkRequest) {
                DoctorWorkRequest dr = (DoctorWorkRequest) request;
                if (dr.getPatient() != null) {
                    patientCombo.addItem(dr.getPatient());
                }
            }
        }
    }


private void setupLayout() {
    setLayout(new BorderLayout(10, 10));
    setBorder(new EmptyBorder(20, 20, 20, 20));

    // Header Panel
    JPanel headerPanel = new JPanel(new BorderLayout());
    JLabel titleLabel = new JLabel("Schedule Appointment - " + patient.getName());
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(btnBack, BorderLayout.WEST);
    headerPanel.add(titleLabel, BorderLayout.CENTER);

    // Patient ID Panel
    JPanel patientIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    patientIDPanel.add(patientIDLabel);
    headerPanel.add(patientIDPanel, BorderLayout.SOUTH);

    add(headerPanel, BorderLayout.NORTH);

    // Form Panel
    JPanel formPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Add patient selector at the top of the form
    gbc.gridx = 0;
    gbc.gridy = 0;
    formPanel.add(new JLabel("Select Patient:"), gbc);
    gbc.gridx = 1;
    formPanel.add(patientCombo, gbc);

        // Adjust other components' grid positions
    addFormRow(formPanel, "Appointment Date:", appointmentDate, gbc, 1);
    addFormRow(formPanel, "Time Slot:", timeSlotCombo, gbc, 2);
    addFormRow(formPanel, "Appointment Type:", appointmentTypeCombo, gbc, 3);
    addFormRow(formPanel, "Notes:", new JScrollPane(notesArea), gbc, 4);

    add(formPanel, BorderLayout.CENTER);

    // Button Panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.add(btnSchedule);
    add(buttonPanel, BorderLayout.SOUTH);
}


    private void setupListeners() {
        
        btnSchedule.addActionListener(e -> {
            if (validateInputs()) {
                Patient selectedPatient = (Patient) patientCombo.getSelectedItem();
                if (selectedPatient != null) {
                    AppointmentWorkRequest appointment = new AppointmentWorkRequest();
                    appointment.setPatient(selectedPatient);
                    appointment.setAppointmentDate(appointmentDate.getDate());
                    appointment.setTimeSlot(timeSlotCombo.getSelectedItem().toString());
                    appointment.setAppointmentType(appointmentTypeCombo.getSelectedItem().toString());
                    appointment.setNotes(notesArea.getText().trim());
                    appointment.setSender(doctor);
                    appointment.setStatus("Scheduled");
                    appointment.setRequestDate(new Date());

                    // Add to work queue
                    doctor.getWorkQueue().getWorkRequestList().add(appointment);

                    // Debug print
                    System.out.println("Added appointment: " + appointment);
                    System.out.println("Work queue size: " + doctor.getWorkQueue().getWorkRequestList().size());

                    JOptionPane.showMessageDialog(this,
                        "Appointment scheduled successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                    // Refresh parent panel
                    Component[] components = userProcessContainer.getComponents();
                    for (Component component : components) {
                        if (component instanceof DoctorWorkAreaJPanel) {
                            ((DoctorWorkAreaJPanel) component).refreshData();
                            break;
                        }
                    }

                    // Return to previous panel
                    userProcessContainer.remove(this);
                    CardLayout layout = (CardLayout) userProcessContainer.getLayout();
                    layout.previous(userProcessContainer);
                }
            }
        });
    }
        
    private boolean validateInputs() {

        if (patientCombo.getSelectedItem() == null) {
            showError("Please select a patient");
            return false;
        }
        if (appointmentDate.getDate() == null) {
            showError("Please select appointment date");
            return false;
        }

        if (timeSlotCombo.getSelectedIndex() == 0) {
            showError("Please select time slot");
            return false;
        }

        if (appointmentTypeCombo.getSelectedIndex() == 0) {
            showError("Please select appointment type");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Validation Error",
            JOptionPane.ERROR_MESSAGE);
    }

    private void refreshParentPanel() {
        Component[] components = userProcessContainer.getComponents();
        for (Component component : components) {
            if (component instanceof DoctorWorkAreaJPanel) {
                ((DoctorWorkAreaJPanel) component).refreshData();
                break;
            }
        }
    }

    // Helper methods for styling
    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(300, 35));
    }

    private void addFormRow(JPanel panel, String label, JComponent component, 
            GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }
}