package ui.DoctorRole;

import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.DoctorWorkRequest;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AddPatientJPanel extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtContact;
    private JComboBox<String> genderCombo;
    private JTextArea txtMedicalHistory;
    private DoctorWorkAreaJPanel parentPanel;
    private JButton btnSave;
    private JButton btnBack;

    public AddPatientJPanel(JPanel userProcessContainer, DoctorWorkAreaJPanel parentPanel) {
        this.userProcessContainer = userProcessContainer;
        this.parentPanel = parentPanel; // Properly assign the parent panel
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        txtName = new JTextField();
        txtAge = new JTextField();
        txtContact = new JTextField();
        genderCombo = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other"});
        txtMedicalHistory = new JTextArea();
        txtMedicalHistory.setLineWrap(true);
        txtMedicalHistory.setWrapStyleWord(true);

        styleComponents();

        btnSave = createButton("Save", new Color(70, 130, 180));
        btnBack = createButton("Back", new Color(108, 117, 125));
    }

    private void styleComponents() {
        Font inputFont = new Font("Arial", Font.PLAIN, 14);
        Dimension fieldSize = new Dimension(250, 35);

        txtName.setFont(inputFont);
        txtName.setPreferredSize(fieldSize);
        txtAge.setFont(inputFont);
        txtAge.setPreferredSize(fieldSize);
        txtContact.setFont(inputFont);
        txtContact.setPreferredSize(fieldSize);
        genderCombo.setFont(inputFont);
        genderCombo.setPreferredSize(fieldSize);
        txtMedicalHistory.setFont(inputFont);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Add New Patient");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(btnBack, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add components to form
        addFormRow(formPanel, "Name:", txtName, gbc, 0);
        addFormRow(formPanel, "Age:", txtAge, gbc, 1);
        addFormRow(formPanel, "Gender:", genderCombo, gbc, 2);
        addFormRow(formPanel, "Contact:", txtContact, gbc, 3);
        addFormRow(formPanel, "Medical History:", new JScrollPane(txtMedicalHistory), gbc, 4);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnSave);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                // Allow only alphabets and space
                if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                    evt.consume(); // Ignore the event (disallows the character)
                    showError("Only alphabets are allowed in the Name field");
                }
            }
        });

        btnSave.addActionListener(e -> {
            if (validateInputs()) {
                // Create patient
                Patient patient = new Patient();
                patient.setName(txtName.getText().trim());
                patient.setAge(Integer.parseInt(txtAge.getText().trim()));
                patient.setGender(genderCombo.getSelectedItem().toString());
                patient.setContact(txtContact.getText().trim());
                patient.setId("PAT" + System.currentTimeMillis());

                // Create work request
                DoctorWorkRequest request = new DoctorWorkRequest();
                request.setPatient(patient);
                request.setStatus("New");
                request.setRequestDate(new java.sql.Date(System.currentTimeMillis())); // Use current time

                // Add to parent's work queue
                parentPanel.getUserAccount().getWorkQueue().getWorkRequestList().add(request);
                parentPanel.refreshData(); // Refresh the parent panel's data

                JOptionPane.showMessageDialog(this,
                        "Patient added successfully!\nPatient ID: " + patient.getId(),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Navigate back to the parent panel
                userProcessContainer.remove(this);
                CardLayout layout = (CardLayout) userProcessContainer.getLayout();
                layout.previous(userProcessContainer);
            }
        });

        btnBack.addActionListener(e -> {
            // Remove the current panel
            userProcessContainer.remove(this);

            // Refresh the parent panel data if needed
            parentPanel.refreshData();

            // Navigate back to the previous panel
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });
    }

    private boolean validateInputs() {
        if (txtName.getText().trim().isEmpty()) {
            showError("Please enter patient name");
            return false;
        }

        if (!txtName.getText().trim().matches("[a-zA-Z\\s]+")) {
            showError("Name should only contain alphabets");
            return false;
        }

        if (txtAge.getText().trim().isEmpty()) {
            showError("Please enter patient age");
            return false;
        }

        try {
            int age = Integer.parseInt(txtAge.getText().trim());
            if (age <= 0 || age > 150) {
                showError("Please enter a valid age");
                return false;
            }
        } catch (NumberFormatException ex) {
            showError("Please enter a valid age");
            return false;
        }

        if (genderCombo.getSelectedIndex() == 0) {
            showError("Please select gender");
            return false;
        }

        if (txtContact.getText().trim().isEmpty()) {
            showError("Please enter contact number");
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
