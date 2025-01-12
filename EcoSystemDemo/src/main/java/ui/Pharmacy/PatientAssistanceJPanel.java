package ui.Pharmacy;

import Business.Pharmacy.Medicine;
import Business.Pharmacy.MedicineDatabase;
import Business.WorkQueue.PrescriptionWorkRequest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientAssistanceJPanel extends JPanel {
    private JPanel userProcessContainer;
    private JTable medicineTable;
    private DefaultTableModel tableModel;
    private MedicineDatabase medicineDB;

    // Form components
    private JTextField txtMedicineId;
    private JTextField txtQuantity;
    private JLabel lblTotalCost;

    public PatientAssistanceJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        this.medicineDB = MedicineDatabase.getInstance();
        initComponents();
        loadMedicineData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Process Patient Prescriptions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton btnBack = createStyledButton("Back", new Color(108, 117, 125));
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        headerPanel.add(btnBack, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        String[] columns = {"Medicine ID", "Name", "Available Stock", "Price (₹)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        medicineTable = new JTable(tableModel);
        styleTable(medicineTable);

        // Selection listener
        medicineTable.getSelectionModel().addListSelectionListener(e -> {
            int row = medicineTable.getSelectedRow();
            if (row >= 0) {
                txtMedicineId.setText((String) medicineTable.getValueAt(row, 0));
                updateTotalCost();
            }
        });

        JScrollPane scrollPane = new JScrollPane(medicineTable);
        add(scrollPane, BorderLayout.CENTER);

        // Process Panel
        JPanel processPanel = new JPanel(new GridBagLayout());
        processPanel.setBorder(BorderFactory.createTitledBorder("Process Prescription"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize form fields
        txtMedicineId = new JTextField(15);
        txtQuantity = new JTextField(15);
        lblTotalCost = new JLabel("Total Cost: ₹0.00");
        lblTotalCost.setFont(new Font("Arial", Font.BOLD, 14));

        // Add form components
        gbc.gridx = 0; gbc.gridy = 0;
        processPanel.add(new JLabel("Medicine ID:"), gbc);
        gbc.gridx = 1;
        processPanel.add(txtMedicineId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        processPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        processPanel.add(txtQuantity, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        processPanel.add(lblTotalCost, gbc);

        // Add quantity change listener
        txtQuantity.addCaretListener(e -> updateTotalCost());

        // Process Button
        JButton btnProcess = createStyledButton("Process Prescription", new Color(46, 139, 87));
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        processPanel.add(btnProcess, gbc);

        // Process button listener
        btnProcess.addActionListener(e -> processPrescription());

        add(processPanel, BorderLayout.SOUTH);
    }

    private void loadMedicineData() {
        tableModel.setRowCount(0);
        for (Medicine medicine : medicineDB.getAllMedicines()) {
            Object[] row = {
                medicine.getId(),
                medicine.getName(),
                medicine.getStock(),
                medicine.getPrice()
            };
            tableModel.addRow(row);
        }
    }

    private void processPrescription() {
        try {
            String medicineId = txtMedicineId.getText().trim();
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
    
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
    
            Medicine medicine = medicineDB.getMedicine(medicineId);
            if (medicine != null) {
                if (medicineDB.updateStock(medicineId, quantity)) { // Updates stock in the database
                    double totalCost = medicine.getPrice() * quantity;
                    JOptionPane.showMessageDialog(this,
                        String.format("Prescription processed successfully!\n" +
                            "Medicine: %s\n" +
                            "Quantity: %d\n" +
                            "Total Cost: ₹%.2f",
                            medicine.getName(),
                            quantity,
                            totalCost),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
    
                    loadMedicineData(); // Refresh UI data
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Insufficient stock for " + medicine.getName(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Medicine not found",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid quantity",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void updateTotalCost() {
        try {
            String medicineId = txtMedicineId.getText().trim();
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            Medicine medicine = medicineDB.getMedicine(medicineId);
            if (medicine != null) {
                double total = medicine.getPrice() * quantity;
                lblTotalCost.setText(String.format("Total Cost: ₹%.2f", total));
            }
        } catch (NumberFormatException e) {
            lblTotalCost.setText("Total Cost: ₹0.00");
        }
    }

    private void clearForm() {
        txtMedicineId.setText("");
        txtQuantity.setText("");
        lblTotalCost.setText("Total Cost: ₹0.00");
        medicineTable.clearSelection();
    }

    private void styleTable(JTable table) {
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
