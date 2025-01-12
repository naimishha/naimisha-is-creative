package ui.DoctorRole;

import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.PrescriptionWorkRequest;
import Business.Pharmacy.Medicine;
import Business.Pharmacy.MedicineDatabase;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Date;

public class PrescriptionJPanel extends JPanel {
    private JPanel userProcessContainer;
    private Patient patient;
    private UserAccount doctor;
    private MedicineDatabase medicineDB;
    
    // Form Components
    private JComboBox<Medicine> medicineCombo;
    private JTextField txtDosage;
    private JTextArea txtInstructions;
    private JLabel lblPrice;
    private JLabel lblStock;
    private JButton btnPrescribe;
    private JButton btnBack;

    public PrescriptionJPanel(JPanel userProcessContainer, Patient patient, UserAccount doctor) {
        this.userProcessContainer = userProcessContainer;
        this.patient = patient;
        this.doctor = doctor;
        this.medicineDB = MedicineDatabase.getInstance();
        initComponents();
        setupListeners();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Prescribe Medicine - " + patient.getName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
        headerPanel.add(btnBack, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize form components
        medicineCombo = new JComboBox<>();
        for (Medicine medicine : medicineDB.getAllMedicines()) {
            medicineCombo.addItem(medicine);
        }
        medicineCombo.setRenderer(new MedicineListRenderer());

        txtDosage = new JTextField(20);
        txtInstructions = new JTextArea(5, 20);
        txtInstructions.setLineWrap(true);
        txtInstructions.setWrapStyleWord(true);

        lblPrice = new JLabel("Price: ₹0.00");
        lblStock = new JLabel("Available Stock: 0");

        // Style components
        styleComboBox(medicineCombo);
        styleTextField(txtDosage);
        styleTextArea(txtInstructions);
        styleInfoLabel(lblPrice);
        styleInfoLabel(lblStock);
        
        // Add form components
        addFormRow(formPanel, "Select Medicine:", medicineCombo, gbc, 0);
        addFormRow(formPanel, "Price:", lblPrice, gbc, 1);
        addFormRow(formPanel, "Available Stock:", lblStock, gbc, 2);
        addFormRow(formPanel, "Dosage:", txtDosage, gbc, 3);
        addFormRow(formPanel, "Instructions:", new JScrollPane(txtInstructions), gbc, 4);

        // Add form panel to center
        add(formPanel, BorderLayout.CENTER);

        // Create and add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPrescribe = createStyledButton("Prescribe", new Color(46, 139, 87));
        buttonPanel.add(btnPrescribe);
        add(buttonPanel, BorderLayout.SOUTH);

        // Update initial medicine info
        updateMedicineInfo();
    }

    private void setupListeners() {
        medicineCombo.addActionListener(e -> updateMedicineInfo());

        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnPrescribe.addActionListener(e -> {
            if (validateInputs()) {
                Medicine selectedMedicine = (Medicine) medicineCombo.getSelectedItem();
        
                if (selectedMedicine != null) {
                    // Parse dosage entered by the user
                    int dosage = Integer.parseInt(txtDosage.getText().trim());
        
                    if (selectedMedicine.getStock() >= dosage) {
                        // Reduce stock by the dosage amount
                        if (medicineDB.updateStock(selectedMedicine.getId(), dosage)) { // Pass dosage here
                            PrescriptionWorkRequest prescription = new PrescriptionWorkRequest();
                            prescription.setMedicineName(selectedMedicine.getName());
                            prescription.setMedicineId(selectedMedicine.getId());
                            prescription.setDosage(txtDosage.getText().trim());
                            prescription.setInstructions(txtInstructions.getText().trim());
                            prescription.setSender(doctor);
                            prescription.setPatient(patient);
                            prescription.setRequestDate(new Date());
                            prescription.setStatus("Prescribed");
                            prescription.setPrice(selectedMedicine.getPrice());
        
                            patient.getPrescriptionHistory().add(prescription);
                            doctor.getWorkQueue().getWorkRequestList().add(prescription);
        
                            JOptionPane.showMessageDialog(this,
                                String.format("Prescription created successfully!\n\n" +
                                        "Medicine: %s\n" +
                                        "Price: ₹%.2f\n" +
                                        "Remaining Stock: %d\n" +
                                        "Dosage: %s",
                                    selectedMedicine.getName(),
                                    selectedMedicine.getPrice(),
                                    selectedMedicine.getStock() - dosage,
                                    txtDosage.getText().trim()),
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
        
                            // Refresh UI data
                            updateMedicineInfo();
                        } else {
                            JOptionPane.showMessageDialog(this,
                                "Failed to update stock. Please try again.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Insufficient stock for the selected medicine.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        

    }
        
    private void updateMedicineInfo() {
        Medicine selectedMedicine = (Medicine) medicineCombo.getSelectedItem();
        if (selectedMedicine != null) {
            // Fetch updated medicine details
            Medicine updatedMedicine = medicineDB.getMedicine(selectedMedicine.getId());
            if (updatedMedicine != null) {
                lblPrice.setText(String.format("Price: ₹%.2f", updatedMedicine.getPrice()));
                lblStock.setText(String.format("Available Stock: %d", updatedMedicine.getStock()));
    
                if (updatedMedicine.getStock() < 10) {
                    lblStock.setForeground(Color.RED);
                } else {
                    lblStock.setForeground(Color.BLACK);
                }
            }
        }
    }
    
    private boolean validateInputs() {
        Medicine selectedMedicine = (Medicine) medicineCombo.getSelectedItem();
        if (selectedMedicine == null) {
            showError("Please select a medicine");
            return false;
        }
    
        if (txtDosage.getText().trim().isEmpty()) {
            showError("Please enter dosage");
            return false;
        }
    
        try {
            int dosage = Integer.parseInt(txtDosage.getText().trim());
            if (dosage <= 0) {
                showError("Dosage must be greater than 0");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Invalid dosage. Please enter a numeric value");
            return false;
        }
    
        if (txtInstructions.getText().trim().isEmpty()) {
            showError("Please enter instructions");
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

    // Styling methods...
    private void styleComboBox(JComboBox<Medicine> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(300, 35));
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(300, 35));
    }

    private void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setPreferredSize(new Dimension(300, 100));
    }

    private void styleInfoLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
    }

    // Form layout helper
    private void addFormRow(JPanel panel, String label, JComponent component, 
            GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblLabel, gbc);

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

    // Custom renderer for medicine combo box
    private class MedicineListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Medicine) {
                Medicine medicine = (Medicine) value;
                setText(String.format("%s - ₹%.2f", medicine.getName(), medicine.getPrice()));
            }
            
            return this;
        }
    }
}