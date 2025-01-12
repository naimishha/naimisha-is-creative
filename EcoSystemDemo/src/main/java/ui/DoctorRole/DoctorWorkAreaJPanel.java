package ui.DoctorRole;

import Business.Employee.Employee;
import Business.Enterprise.Enterprise;
import Business.Organization.DoctorOrganization;
import Business.Organization.Organization;
import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.AppointmentWorkRequest;
import Business.WorkQueue.DoctorWorkRequest;
import Business.WorkQueue.WorkRequest;
import Business.WorkQueue.LabTestWorkRequest;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DoctorWorkAreaJPanel extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private DoctorOrganization organization;
    private Enterprise enterprise;
    private UserAccount userAccount;

    // UI Components
    private JTabbedPane tabbedPane;
    private JTable patientTable;
    private JTable appointmentTable;
    private DefaultTableModel patientModel;
    private DefaultTableModel appointmentModel;
    private JButton btnViewPatient;
    private JButton btnPrescribe;
    private JButton btnStartTelemedicine;
    private JButton btnRefresh;
    private JButton btnAddPatient;
    private JButton btnScheduleAppointment;

    public DoctorWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, 
            Organization organization, Enterprise enterprise) {
        this.userProcessContainer = userProcessContainer;
        this.organization = (DoctorOrganization) organization;
        this.enterprise = enterprise;
        this.userAccount = account;

        System.out.println("DoctorWorkAreaJPanel initialized with userAccount: " + account);

        if (account == null) {
            throw new IllegalArgumentException("UserAccount cannot be null");
        }
        

        initializeComponents();
        setupLayout();
        populateTables();
        setupListeners();
    }

    private void initializeComponents() {
        // Initialize tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Initialize tables
        String[] patientColumns = {"Patient ID", "Name", "Age", "Gender", "Status"};
        patientModel = new DefaultTableModel(patientColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        patientTable = new JTable(patientModel);
        styleTable(patientTable);

        String[] appointmentColumns = {"Appointment ID", "Patient", "Date", "Type", "Status"};
        appointmentModel = new DefaultTableModel(appointmentColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        appointmentTable = new JTable(appointmentModel);
        styleTable(appointmentTable);

        // Initialize buttons
        btnViewPatient = createStyledButton("View Patient Details", new Color(70, 130, 180));
        btnPrescribe = createStyledButton("Prescribe Medicine", new Color(46, 139, 87));
        btnStartTelemedicine = createStyledButton("Start Telemedicine", new Color(106, 90, 205));
        btnRefresh = createStyledButton("Refresh", new Color(108, 117, 125));
        btnAddPatient = createStyledButton("Add New Patient", new Color(70, 130, 180));
        btnScheduleAppointment = createStyledButton("Schedule Appointment", new Color(46, 139, 87));
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionBackground(new Color(220, 230, 240));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
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

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Doctor Workspace - " + userAccount.getEmployee().getName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(btnRefresh, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Patients Tab
        JPanel patientsPanel = new JPanel(new BorderLayout(10, 10));
        patientsPanel.add(new JScrollPane(patientTable), BorderLayout.CENTER);

        JPanel patientButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        patientButtonPanel.add(btnViewPatient);
        patientButtonPanel.add(btnPrescribe);
        patientButtonPanel.add(btnAddPatient);
        patientsPanel.add(patientButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Patients", patientsPanel);

        // Appointments Tab
        JPanel appointmentsPanel = new JPanel(new BorderLayout(10, 10));
        appointmentsPanel.add(new JScrollPane(appointmentTable), BorderLayout.CENTER);

        JPanel appointmentButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        appointmentButtonPanel.add(btnStartTelemedicine);
        appointmentButtonPanel.add(btnScheduleAppointment);
        appointmentsPanel.add(appointmentButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Appointments", appointmentsPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnViewPatient.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                    "Please select a patient to view",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            // Get selected patient
            DoctorWorkRequest request = getSelectedPatientRequest(selectedRow);
            if (request != null) {
                PatientDetailsJPanel patientDetails = new PatientDetailsJPanel(userProcessContainer, request.getPatient());
                userProcessContainer.add("PatientDetails", patientDetails);
                CardLayout layout = (CardLayout) userProcessContainer.getLayout();
                layout.next(userProcessContainer);
            }
        });

        btnPrescribe.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                    "Please select a patient to prescribe medicine",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            // Get selected patient
            DoctorWorkRequest request = getSelectedPatientRequest(selectedRow);
            if (request != null) {
                PrescriptionJPanel prescriptionPanel = new PrescriptionJPanel(
                    userProcessContainer, 
                    request.getPatient(), 
                    userAccount
                );
                userProcessContainer.add("Prescription", prescriptionPanel);
                CardLayout layout = (CardLayout) userProcessContainer.getLayout();
                layout.next(userProcessContainer);
            }
        });
        btnAddPatient.addActionListener(e -> {
            AddPatientJPanel addPatient = new AddPatientJPanel(userProcessContainer, this);
            userProcessContainer.add("AddPatient", addPatient);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        });

        btnScheduleAppointment.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                        "Please select a patient first",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Get selected patient
            DoctorWorkRequest request = getSelectedPatientRequest(selectedRow);
            if (request != null) {
                AppointmentJPanel appointmentPanel = new AppointmentJPanel(
                    userProcessContainer, 
                    request.getPatient(),  // Pass the selected patient
                    userAccount
                );
                userProcessContainer.add("AppointmentPanel", appointmentPanel);
                CardLayout layout = (CardLayout) userProcessContainer.getLayout();
                layout.next(userProcessContainer);
            }
        });

        btnStartTelemedicine.addActionListener(e -> {
            int selectedRow = appointmentTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                        "Please select an appointment to start telemedicine",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        
            // Get the selected appointment and verify it's a telemedicine appointment
            AppointmentWorkRequest appointment = null;
            String appointmentId = (String) appointmentTable.getValueAt(selectedRow, 0);
        
            for (WorkRequest request : userAccount.getWorkQueue().getWorkRequestList()) {
                if (request instanceof AppointmentWorkRequest) {
                    AppointmentWorkRequest appt = (AppointmentWorkRequest) request;
                    if (appt.getAppointmentType().equals("Telemedicine")) {
                        appointment = appt;
                        break;
                    }
                }
            }
        
            if (appointment == null || !appointment.getAppointmentType().equals("Telemedicine")) {
                JOptionPane.showMessageDialog(this,
                        "Selected appointment is not a telemedicine session",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Create and show telemedicine panel
            TelemedicineJPanel telemedicinePanel = new TelemedicineJPanel(
                    userProcessContainer,
                    appointment.getPatient(),
                    userAccount
            );
            userProcessContainer.add("TelemedicineSession", telemedicinePanel);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        });
        btnRefresh.addActionListener(e -> populateTables());
    }
    public void refreshData() {
        System.out.println("Refreshing data..."); // Debug print
        populateTables();
    }
    public UserAccount getUserAccount() {
        return userAccount;
    }

    private DoctorWorkRequest getSelectedPatientRequest(int selectedRow) {
        String patientId = (String) patientTable.getValueAt(selectedRow, 0);
        
        for (WorkRequest request : userAccount.getWorkQueue().getWorkRequestList()) {
            if (request instanceof DoctorWorkRequest) {
                DoctorWorkRequest doctorRequest = (DoctorWorkRequest) request;
                if (doctorRequest.getPatient().getId().equals(patientId)) {
                    return doctorRequest;
                }
            }
        }
        return null;
    }

    private void populateTables() {
        patientModel.setRowCount(0);
        appointmentModel.setRowCount(0);
    
        // Populate Patient Table
        for (WorkRequest request : userAccount.getWorkQueue().getWorkRequestList()) {
            if (request instanceof DoctorWorkRequest) {
                DoctorWorkRequest doctorRequest = (DoctorWorkRequest) request;
                Patient p = doctorRequest.getPatient();
                if (p != null) {
                    Object[] row = {
                        p.getId(),
                        p.getName(),
                        p.getAge(),
                        p.getGender(),
                        doctorRequest.getStatus()
                    };
                    patientModel.addRow(row);
                }
            }
            // Add appointments to appointment table
            if (request instanceof AppointmentWorkRequest) {
                AppointmentWorkRequest appt = (AppointmentWorkRequest) request;
                Patient p = appt.getPatient();
                if (p != null) {
                    Object[] row = {
                        p.getId(),  // Show patient ID in appointments
                        p.getName(),
                        appt.getAppointmentDate(),
                        appt.getAppointmentType(),
                        appt.getStatus()
                    };
                    appointmentModel.addRow(row);
                }
            }
        }
    }
    

    public void populateRequestTable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'populateRequestTable'");
    }
}
