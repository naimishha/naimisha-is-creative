package ui.DoctorRole;

import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.AppointmentWorkRequest;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class ScheduleAppointmentJPanel extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private Patient patient;
    private UserAccount doctor;
    
    private JDateChooser appointmentDate;
    private JComboBox<String> timeSlotCombo;
    private JComboBox<String> appointmentTypeCombo;
    private JTextArea txtNotes;
    private JButton btnSchedule;
    private JButton btnBack;

    public ScheduleAppointmentJPanel(JPanel userProcessContainer, Patient patient, UserAccount doctor) {
        this.userProcessContainer = userProcessContainer;
        this.patient = patient;
        this.doctor = doctor;
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        appointmentDate = new JDateChooser();
        appointmentDate.setMinSelectableDate(new Date());
        
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

        txtNotes = new JTextArea();
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);

        styleComponents();

        btnSchedule = createButton("Schedule Appointment", new Color(70, 130, 180));
        btnBack = createButton("Back", new Color(108, 117, 125));
    }

    private void styleComponents() {
        Font inputFont = new Font("Arial", Font.PLAIN, 14);
        Dimension fieldSize = new Dimension(250, 35);

        appointmentDate.setFont(inputFont);
        appointmentDate.setPreferredSize(fieldSize);
        
        timeSlotCombo.setFont(inputFont);
        timeSlotCombo.setPreferredSize(fieldSize);
        
        appointmentTypeCombo.setFont(inputFont);
        appointmentTypeCombo.setPreferredSize(fieldSize);
        
        txtNotes.setFont(inputFont);
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
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add form components
        addFormRow(formPanel, "Appointment Date:", appointmentDate, gbc, 0);
        addFormRow(formPanel, "Time Slot:", timeSlotCombo, gbc, 1);
        addFormRow(formPanel, "Appointment Type:", appointmentTypeCombo, gbc, 2);
        addFormRow(formPanel, "Notes:", new JScrollPane(txtNotes), gbc, 3);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSchedule);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnSchedule.addActionListener(e -> {
            System.out.println("Schedule button clicked."); // Debug log
    
            if (validateInputs()) {
                System.out.println("Inputs validated."); // Debug log
    
                AppointmentWorkRequest appointment = new AppointmentWorkRequest();
                appointment.setAppointmentDate(appointmentDate.getDate());
                appointment.setTimeSlot(timeSlotCombo.getSelectedItem().toString());
                appointment.setAppointmentType(appointmentTypeCombo.getSelectedItem().toString());
                appointment.setNotes(txtNotes.getText().trim());
                appointment.setSender(doctor);
    
                // Check if patient is null
                if (patient == null) {
                    System.out.println("Patient object is null."); // Debug log
                    showError("Patient information is missing.");
                    return;
                }
    
                appointment.setPatient(patient); // Ensure patient is passed correctly here.
                appointment.setStatus("Scheduled");
                appointment.setRequestDate(new Date());
    
                // Add to doctor's work queue
                doctor.getWorkQueue().getWorkRequestList().add(appointment);
                System.out.println("Appointment added to work queue."); // Debug log
    
                JOptionPane.showMessageDialog(this,
                    "Appointment scheduled successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
    
                // Navigate back to the previous panel
                userProcessContainer.remove(this);
                CardLayout layout = (CardLayout) userProcessContainer.getLayout();
                layout.previous(userProcessContainer);
            }
        });
    
        btnBack.addActionListener(e -> {
            System.out.println("Back button clicked."); // Debug log
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });
        

        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });
    }

    private boolean validateInputs() {
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

        if (appointmentDate.getDate().before(new Date())) {
            showError("Cannot schedule appointment in the past");
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

    private void addFormRow(JPanel panel, String label, JComponent component, 
            GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if (component instanceof JScrollPane) {
            component.setPreferredSize(new Dimension(250, 100));
        }
        panel.add(component, gbc);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }
}