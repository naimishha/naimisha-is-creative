package ui.SupplierRole;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PharmaceuticalSuppliesJPanel extends JPanel {
    private JPanel userProcessContainer;
    private JTable medicationTable;
    private DefaultTableModel tableModel;
    private JButton btnAddMedication;
    private JButton btnUpdateQuantity;
    private JButton btnRemoveExpired;
    private JButton btnBack;

    public PharmaceuticalSuppliesJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        initComponents();
        setupLayout();
        setupListeners();
        populateTable(); // Add initial data to the table
    }

    private void initComponents() {
        String[] columns = {"Medication ID", "Name", "Quantity", "Expiry Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        medicationTable = new JTable(tableModel);

        btnAddMedication = createStyledButton("Add Medication", new Color(70, 130, 180));
        btnUpdateQuantity = createStyledButton("Update Quantity", new Color(46, 139, 87));
        btnRemoveExpired = createStyledButton("Remove Expired", new Color(220, 20, 60));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Pharmaceutical Supplies");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(medicationTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAddMedication);
        buttonPanel.add(btnUpdateQuantity);
        buttonPanel.add(btnRemoveExpired);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnAddMedication.addActionListener(e -> {
            JTextField medicationIDField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField quantityField = new JTextField();
            JTextField expiryDateField = new JTextField();
            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Available", "Out of Stock", "Expired"});

            Object[] fields = {
                "Medication ID:", medicationIDField,
                "Name:", nameField,
                "Quantity:", quantityField,
                "Expiry Date (YYYY-MM-DD):", expiryDateField,
                "Status:", statusComboBox
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Add New Medication", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                tableModel.addRow(new Object[]{
                    medicationIDField.getText(),
                    nameField.getText(),
                    Integer.parseInt(quantityField.getText()),
                    expiryDateField.getText(),
                    statusComboBox.getSelectedItem()
                });
            }
        });

        btnUpdateQuantity.addActionListener(e -> {
            int selectedRow = medicationTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a medication to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JTextField quantityField = new JTextField();
            Object[] fields = {"New Quantity:", quantityField};

            int option = JOptionPane.showConfirmDialog(this, fields, "Update Quantity", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(Integer.parseInt(quantityField.getText()), selectedRow, 2);
            }
        });

        btnRemoveExpired.addActionListener(e -> {
            for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
                String expiryDate = (String) tableModel.getValueAt(i, 3);
                if (isExpired(expiryDate)) {
                    tableModel.removeRow(i);
                }
            }
            JOptionPane.showMessageDialog(this, "Expired medications removed successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void populateTable() {
        Object[][] sampleData = {
            {"M001", "Paracetamol", 100, "2025-12-31", "Available"},
            {"M002", "Ibuprofen", 50, "2023-10-15", "Expired"},
            {"M003", "Cough Syrup", 30, "2024-05-20", "Available"}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    private boolean isExpired(String expiryDate) {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        java.time.LocalDate expDate = java.time.LocalDate.parse(expiryDate);
        return expDate.isBefore(currentDate);
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}
