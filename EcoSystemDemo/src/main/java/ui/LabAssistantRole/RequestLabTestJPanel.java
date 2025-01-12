package ui.LabAssistantRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.LabTestWorkRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class RequestLabTestJPanel extends JPanel {
    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private Organization organization;
    private Enterprise enterprise;

    private JComboBox<Patient> patientComboBox;
    private JTextField testNameField;
    private JTextArea instructionsArea;
    private JButton btnRequestTest;
    private JButton btnBack;

    public RequestLabTestJPanel(JPanel userProcessContainer, UserAccount userAccount, Organization organization, Enterprise enterprise) {
        this.userProcessContainer = userProcessContainer;
        this.userAccount = userAccount;
        this.organization = organization;
        this.enterprise = enterprise;

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        patientComboBox = new JComboBox<>();
        populatePatientComboBox();

        testNameField = new JTextField(20);
        instructionsArea = new JTextArea(5, 20);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        btnRequestTest = createButton("Request Test");
        btnBack = createButton("Back");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel headerLabel = new JLabel("Request Lab Test");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

       // addFormRow(formPanel, "Patient:", patientComboBox, gbc, 0);
        addFormRow(formPanel, "Test Name:", testNameField, gbc, 1);
        addFormRow(formPanel, "Instructions:", new JScrollPane(instructionsArea), gbc, 2);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnBack);
        buttonPanel.add(btnRequestTest);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnRequestTest.addActionListener((ActionEvent e) -> {
            if (validateInputs()) {
                Patient patient = (Patient) patientComboBox.getSelectedItem();
                String testName = testNameField.getText().trim();
                String instructions = instructionsArea.getText().trim();

                LabTestWorkRequest request = new LabTestWorkRequest();
                request.setPatient(patient);
                request.setTestResult(testName);
                request.setMessage(instructions);
                request.setSender(userAccount);
                request.setStatus("Pending");
                request.setRequestDate(new Date());

                userAccount.getWorkQueue().getWorkRequestList().add(request);
                JOptionPane.showMessageDialog(this, "Lab test requested successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Navigate back
                userProcessContainer.remove(this);
                CardLayout layout = (CardLayout) userProcessContainer.getLayout();
                layout.previous(userProcessContainer);
            }
        });

        btnBack.addActionListener((ActionEvent e) -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });
    }

    private void populatePatientComboBox() {
        for (UserAccount userAccount : organization.getUserAccountDirectory().getUserAccountList()) {
            if (userAccount.getPatient() != null) {
                patientComboBox.addItem(userAccount.getPatient());
            }
        }
    }

    private boolean validateInputs() {
        

        if (testNameField.getText().trim().isEmpty()) {
            showError("Please enter the test name.");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    private void addFormRow(JPanel panel, String label, JComponent component, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
}
