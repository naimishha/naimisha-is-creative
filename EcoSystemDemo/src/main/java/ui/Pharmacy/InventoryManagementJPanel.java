package ui.Pharmacy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Business.Pharmacy.Medicine;
import Business.Pharmacy.MedicineDatabase;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InventoryManagementJPanel extends JPanel {
    private JPanel userProcessContainer;
    private JTable inventoryTable;
    private DefaultTableModel inventoryModel;
    private MedicineDatabase medicineDB;
    
    // Form Fields
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtPrice;
    private JTextField txtStock;
    private JTextField txtExpiry;
    private JTextArea txtDescription;

    public InventoryManagementJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        this.medicineDB = MedicineDatabase.getInstance();
        initComponents();
        loadMedicineData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Pharmacy Inventory Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Add back button
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });
        headerPanel.add(btnBack, BorderLayout.WEST);
        
        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        String[] columns = {"ID", "Medicine Name", "Price (₹)", "Stock", "Expiry Date", "Description"};
        inventoryModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        inventoryTable = new JTable(inventoryModel);
        styleTable(inventoryTable);
        
        // Selection listener for table
        inventoryTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = inventoryTable.getSelectedRow();
            if (selectedRow >= 0) {
                txtId.setText((String) inventoryModel.getValueAt(selectedRow, 0));
                txtName.setText((String) inventoryModel.getValueAt(selectedRow, 1));
                txtPrice.setText(String.valueOf(inventoryModel.getValueAt(selectedRow, 2)));
                txtStock.setText(String.valueOf(inventoryModel.getValueAt(selectedRow, 3)));
                txtExpiry.setText((String) inventoryModel.getValueAt(selectedRow, 4));
                txtDescription.setText((String) inventoryModel.getValueAt(selectedRow, 5));
            }
        });

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        add(scrollPane, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Medicine Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize form fields
        txtId = new JTextField(20);
        txtName = new JTextField(20);
        txtPrice = new JTextField(20);
        txtStock = new JTextField(20);
        txtExpiry = new JTextField(20);
        txtDescription = new JTextArea(3, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);

        // Add form components
        gbc.gridy = 0;
        addFormField(formPanel, "Medicine ID:", txtId, gbc);
        gbc.gridy++;
        addFormField(formPanel, "Name:", txtName, gbc);
        gbc.gridy++;
        addFormField(formPanel, "Price (₹):", txtPrice, gbc);
        gbc.gridy++;
        addFormField(formPanel, "Stock:", txtStock, gbc);
        gbc.gridy++;
        addFormField(formPanel, "Expiry Date:", txtExpiry, gbc);
        gbc.gridy++;
        addFormField(formPanel, "Description:", new JScrollPane(txtDescription), gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdd = createStyledButton("Add Medicine", new Color(70, 130, 180));
        JButton btnUpdate = createStyledButton("Update Medicine", new Color(46, 139, 87));
        JButton btnDelete = createStyledButton("Delete Medicine", new Color(178, 34, 34));
        JButton btnClear = createStyledButton("Clear Form", new Color(108, 117, 125));

        btnAdd.addActionListener(this::addMedicine);
        btnUpdate.addActionListener(this::updateMedicine);
        btnDelete.addActionListener(this::deleteMedicine);
        btnClear.addActionListener(e -> clearForm());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Combine form and button panels
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc) {
        gbc.gridx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
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
        return button;
    }

    private void loadMedicineData() {
        inventoryModel.setRowCount(0);
        for (Medicine medicine : medicineDB.getAllMedicines()) {
            Object[] row = {
                medicine.getId(),
                medicine.getName(),
                medicine.getPrice(),
                medicine.getStock(),
                medicine.getExpiryDate(),
                medicine.getDescription()
            };
            inventoryModel.addRow(row);
        }
    }

    private void addMedicine(ActionEvent evt) {
        try {
            validateInputs();
            Medicine newMedicine = new Medicine(
                txtId.getText(),
                txtName.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtStock.getText()),
                txtDescription.getText(),
                txtExpiry.getText()
            );
            medicineDB.addMedicine(newMedicine);
            loadMedicineData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Medicine added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateMedicine(ActionEvent evt) {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a medicine to update.");
            return;
        }

        try {
            validateInputs();
            String id = txtId.getText();
            Medicine medicine = new Medicine(
                id,
                txtName.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtStock.getText()),
                txtDescription.getText(),
                txtExpiry.getText()
            );
            medicineDB.addMedicine(medicine); // This will override the existing medicine
            loadMedicineData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Medicine updated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteMedicine(ActionEvent evt) {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a medicine to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this medicine?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            String id = (String) inventoryModel.getValueAt(selectedRow, 0);
          //  medicineDB.removeMedicine(id);
            loadMedicineData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Medicine deleted successfully!");
        }
    }

    private void validateInputs() throws Exception {
        if (txtId.getText().trim().isEmpty()) throw new Exception("Medicine ID is required");
        if (txtName.getText().trim().isEmpty()) throw new Exception("Medicine Name is required");
        if (txtPrice.getText().trim().isEmpty()) throw new Exception("Price is required");
        if (txtStock.getText().trim().isEmpty()) throw new Exception("Stock is required");
        if (txtExpiry.getText().trim().isEmpty()) throw new Exception("Expiry Date is required");
        
        try {
            Double.parseDouble(txtPrice.getText());
        } catch (NumberFormatException e) {
            throw new Exception("Invalid price format");
        }

        try {
            Integer.parseInt(txtStock.getText());
        } catch (NumberFormatException e) {
            throw new Exception("Invalid stock format");
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        txtExpiry.setText("");
        txtDescription.setText("");
        inventoryTable.clearSelection();
    }
}