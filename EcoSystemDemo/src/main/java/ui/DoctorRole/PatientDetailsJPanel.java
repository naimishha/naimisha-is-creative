package ui.DoctorRole;

import Business.Patient.Patient;
import Business.WorkQueue.LabTestWorkRequest;
import Business.WorkQueue.PrescriptionWorkRequest;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PatientDetailsJPanel extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private Patient patient;
    
    // Tables for different sections
    private JTable medicalHistoryTable;
    private JTable testResultsTable;
    private JTable prescriptionHistoryTable;
    private DefaultTableModel medicalHistoryModel;
    private DefaultTableModel testResultsModel;
    private DefaultTableModel prescriptionHistoryModel;
    
    // Patient Info Components
    private JLabel lblPatientName;
    private JTextArea medicalHistoryArea;
    private JButton btnBack;

    public PatientDetailsJPanel(JPanel userProcessContainer, Patient patient) {
        this.userProcessContainer = userProcessContainer;
        this.patient = patient;
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // Initialize basic components
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
        lblPatientName = new JLabel("Patient Details - " + patient.getName());
        lblPatientName.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Initialize medical history area
        medicalHistoryArea = new JTextArea(5, 30);
        medicalHistoryArea.setEditable(false);
        
        // Initialize tables
        setupTables();
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Panel with Patient Info
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.add(btnBack, BorderLayout.WEST);
        headerPanel.add(lblPatientName, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Create details panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add patient details
        addDetailRow(detailsPanel, "Patient ID:", patient.getId(), gbc, 0);
        addDetailRow(detailsPanel, "Name:", patient.getName(), gbc, 1);
        addDetailRow(detailsPanel, "Age:", String.valueOf(patient.getAge()), gbc, 2);
        addDetailRow(detailsPanel, "Gender:", patient.getGender(), gbc, 3);
        addDetailRow(detailsPanel, "Contact:", patient.getContact(), gbc, 4);

        // Create tabbed pane for different sections
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Add Details Tab
        tabbedPane.addTab("Patient Details", new JScrollPane(detailsPanel));

        // Add Medical History Tab
        JScrollPane medicalHistoryPane = new JScrollPane(medicalHistoryTable);
        tabbedPane.addTab("Medical History", medicalHistoryPane);

        // Add Test Results Tab
        JScrollPane testResultsPane = new JScrollPane(testResultsTable);
        tabbedPane.addTab("Test Results", testResultsPane);

        // Add Prescription History Tab
        JScrollPane prescriptionPane = new JScrollPane(prescriptionHistoryTable);
        tabbedPane.addTab("Prescription History", prescriptionPane);

        add(tabbedPane, BorderLayout.CENTER);

        // Add back button listener
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });
        
        // Populate tables
        populateTables();
    }

    private void setupTables() {
        // Medical History Table
        String[] medicalHistoryColumns = {"Date", "Condition", "Treatment", "Doctor"};
        medicalHistoryModel = new DefaultTableModel(medicalHistoryColumns, 0);
        medicalHistoryTable = new JTable(medicalHistoryModel);
        styleTable(medicalHistoryTable);

        // Test Results Table
        String[] testResultsColumns = {"Date", "Test Type", "Result", "Lab"};
        testResultsModel = new DefaultTableModel(testResultsColumns, 0);
        testResultsTable = new JTable(testResultsModel);
        styleTable(testResultsTable);

        // Prescription History Table
        String[] prescriptionColumns = {"Date", "Medicine", "Dosage", "Duration", "Doctor"};
        prescriptionHistoryModel = new DefaultTableModel(prescriptionColumns, 0);
        prescriptionHistoryTable = new JTable(prescriptionHistoryModel);
        styleTable(prescriptionHistoryTable);
    }

    private void addDetailRow(JPanel panel, String label, String value, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblLabel, gbc);

        gbc.gridx = 1;
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lblValue, gbc);
    }

    private void populateTables() {
        medicalHistoryModel.setRowCount(0);
        testResultsModel.setRowCount(0);
        prescriptionHistoryModel.setRowCount(0);

        // Populate Medical History
        for (Object[] historyRow : patient.getMedicalHistory()) {
            medicalHistoryModel.addRow(historyRow);
        }

        // Populate Test Results
        for (LabTestWorkRequest testRequest : patient.getLabTestHistory()) {
            Object[] row = {
                testRequest.getRequestDate(),
                testRequest.getTestResult(),
                testRequest.getTestResult(),
                testRequest.getReceiver().getEmployee().getName()
            };
            testResultsModel.addRow(row);
        }

        // Populate Prescription History
        for (PrescriptionWorkRequest prescription : patient.getPrescriptionHistory()) {
            Object[] row = {
                prescription.getRequestDate(),
                prescription.getMedicineName(),
                prescription.getDosage(),
                prescription.getDuration(),
                prescription.getSender().getEmployee().getName()
            };
            prescriptionHistoryModel.addRow(row);
        }
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
}