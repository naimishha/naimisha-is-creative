package ui.DoctorRole;

import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.DoctorWorkRequest;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Date;

public class TelemedicineJPanel extends JPanel {
    private JPanel userProcessContainer;
    private Patient patient;
    private UserAccount doctor;
    
    // UI Components
    private JPanel videoPanel;  // Placeholder for video feed
    private JTextArea notesArea;
    private JTextField txtBP;    // Blood Pressure
    private JTextField txtTemp;  // Temperature
    private JTextField txtHR;    // Heart Rate
    private JButton btnStartCall;
    private JButton btnEndCall;
    private JButton btnSaveNotes;
    private JButton btnBack;
    private boolean isCallActive = false;

    public TelemedicineJPanel(JPanel userProcessContainer, Patient patient, UserAccount doctor) {
        this.userProcessContainer = userProcessContainer;
        this.patient = patient;
        this.doctor = doctor;
        initializeComponents();
        setupLayout();
        setupListeners();
    }

    private void initializeComponents() {
        // Initialize video panel placeholder
        videoPanel = new JPanel();
        videoPanel.setBackground(Color.DARK_GRAY);
        videoPanel.setPreferredSize(new Dimension(640, 480));
        
        // Initialize vitals input fields
        txtBP = new JTextField(10);
        txtTemp = new JTextField(10);
        txtHR = new JTextField(10);
        
        // Initialize notes area
        notesArea = new JTextArea(5, 30);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        
        // Initialize buttons
        btnStartCall = createStyledButton("Start Call", new Color(46, 139, 87));
        btnEndCall = createStyledButton("End Call", new Color(220, 53, 69));
        btnEndCall.setEnabled(false);
        btnSaveNotes = createStyledButton("Save Notes", new Color(70, 130, 180));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));

        styleComponents();
    }

    private void styleComponents() {
        Font inputFont = new Font("Arial", Font.PLAIN, 14);
        
        txtBP.setFont(inputFont);
        txtTemp.setFont(inputFont);
        txtHR.setFont(inputFont);
        notesArea.setFont(inputFont);
        
        // Add placeholder text to video panel
        JLabel placeholderLabel = new JLabel("Video Feed", SwingConstants.CENTER);
        placeholderLabel.setForeground(Color.WHITE);
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, 24));
        videoPanel.setLayout(new GridBagLayout());
        videoPanel.add(placeholderLabel);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Telemedicine Session - " + patient.getName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(btnBack, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Video and Controls Panel
        JPanel videoControlPanel = new JPanel(new BorderLayout(10, 10));
        videoControlPanel.add(videoPanel, BorderLayout.CENTER);
        
        // Call Control Buttons
        JPanel callControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        callControlPanel.add(btnStartCall);
        callControlPanel.add(btnEndCall);
        videoControlPanel.add(callControlPanel, BorderLayout.SOUTH);

        mainPanel.add(videoControlPanel, BorderLayout.CENTER);

        // Right Side Panel (Vitals and Notes)
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        
        // Vitals Panel
        JPanel vitalsPanel = new JPanel(new GridBagLayout());
        vitalsPanel.setBorder(BorderFactory.createTitledBorder("Patient Vitals"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        addFormRow(vitalsPanel, "Blood Pressure:", txtBP, gbc, 0);
        addFormRow(vitalsPanel, "Temperature:", txtTemp, gbc, 1);
        addFormRow(vitalsPanel, "Heart Rate:", txtHR, gbc, 2);
        
        rightPanel.add(vitalsPanel, BorderLayout.NORTH);

        // Notes Panel
        JPanel notesPanel = new JPanel(new BorderLayout(5, 5));
        notesPanel.setBorder(BorderFactory.createTitledBorder("Session Notes"));
        notesPanel.add(new JScrollPane(notesArea), BorderLayout.CENTER);
        notesPanel.add(btnSaveNotes, BorderLayout.SOUTH);
        
        rightPanel.add(notesPanel, BorderLayout.CENTER);

        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnStartCall.addActionListener(e -> {
            startCall();
        });

        btnEndCall.addActionListener(e -> {
            endCall();
        });

        btnSaveNotes.addActionListener(e -> {
            saveConsultationNotes();
        });

        btnBack.addActionListener(e -> {
            if (isCallActive) {
                int response = JOptionPane.showConfirmDialog(this,
                    "Active call will be ended. Do you want to continue?",
                    "End Call",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (response != JOptionPane.YES_OPTION) {
                    return;
                }
                endCall();
            }
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });
    }

    private void startCall() {
        isCallActive = true;
        btnStartCall.setEnabled(false);
        btnEndCall.setEnabled(true);
        // Add video call initialization logic here
        videoPanel.setBackground(Color.BLACK);
        JLabel connectingLabel = new JLabel("Connecting...", SwingConstants.CENTER);
        connectingLabel.setForeground(Color.WHITE);
        connectingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        videoPanel.removeAll();
        videoPanel.add(connectingLabel);
        videoPanel.revalidate();
        videoPanel.repaint();
    }

    private void endCall() {
        isCallActive = false;
        btnStartCall.setEnabled(true);
        btnEndCall.setEnabled(false);
        videoPanel.setBackground(Color.DARK_GRAY);
        JLabel endedLabel = new JLabel("Call Ended", SwingConstants.CENTER);
        endedLabel.setForeground(Color.WHITE);
        endedLabel.setFont(new Font("Arial", Font.BOLD, 24));
        videoPanel.removeAll();
        videoPanel.add(endedLabel);
        videoPanel.revalidate();
        videoPanel.repaint();
    }

    private void saveConsultationNotes() {
        DoctorWorkRequest request = new DoctorWorkRequest();
        request.setPatient(patient);
        request.setSender(doctor);
        request.setRequestDate(new Date());
        request.setStatus("Completed");
        request.setNotes(notesArea.getText().trim());
        request.setTelemedicine(true);
        request.setBloodPressure(txtBP.getText().trim());
        request.setTemperature(txtTemp.getText().trim());
        request.setHeartRate(txtHR.getText().trim());

        // Add to work queue
        doctor.getWorkQueue().getWorkRequestList().add(request);

        JOptionPane.showMessageDialog(this,
            "Consultation notes saved successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
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