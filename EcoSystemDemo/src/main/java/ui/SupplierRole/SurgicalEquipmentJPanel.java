package ui.SupplierRole;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SurgicalEquipmentJPanel extends JPanel {
    private JPanel userProcessContainer;
    private JTable equipmentTable;
    private DefaultTableModel tableModel;
    private JButton btnAddEquipment;
    private JButton btnUpdateStatus;
    private JButton btnBack;

    public SurgicalEquipmentJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        initComponents();
        setupLayout();
        setupListeners();
        populateTable(); // Sample data
    }

    private void initComponents() {
        String[] columns = {"Equipment ID", "Name", "Status", "Last Maintenance"};
        tableModel = new DefaultTableModel(columns, 0);
        equipmentTable = new JTable(tableModel);

        btnAddEquipment = createStyledButton("Add Equipment", new Color(70, 130, 180));
        btnUpdateStatus = createStyledButton("Update Status", new Color(46, 139, 87));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Surgical Equipment Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(equipmentTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAddEquipment);
        buttonPanel.add(btnUpdateStatus);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnAddEquipment.addActionListener(e -> {
            JTextField equipmentIDField = new JTextField();
            JTextField nameField = new JTextField();
            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Available", "In Use", "Under Maintenance"});
            JTextField maintenanceDateField = new JTextField();

            Object[] fields = {
                "Equipment ID:", equipmentIDField,
                "Name:", nameField,
                "Status:", statusComboBox,
                "Last Maintenance:", maintenanceDateField
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Add New Equipment", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                tableModel.addRow(new Object[]{
                    equipmentIDField.getText(),
                    nameField.getText(),
                    statusComboBox.getSelectedItem(),
                    maintenanceDateField.getText()
                });
            }
        });

        btnUpdateStatus.addActionListener(e -> {
            int selectedRow = equipmentTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select an equipment to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Available", "In Use", "Under Maintenance"});
            Object[] fields = {"Update Status:", statusComboBox};

            int option = JOptionPane.showConfirmDialog(this, fields, "Update Equipment Status", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(statusComboBox.getSelectedItem(), selectedRow, 2);
            }
        });
    }

    private void populateTable() {
        Object[][] sampleData = {
            {"E001", "Scalpel", "Available", "2024-11-01"},
            {"E002", "Forceps", "In Use", "2024-10-20"},
            {"E003", "Surgical Light", "Under Maintenance", "2024-09-15"}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
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
