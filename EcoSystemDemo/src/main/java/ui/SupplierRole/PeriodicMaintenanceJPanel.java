package ui.SupplierRole;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PeriodicMaintenanceJPanel extends JPanel {
    private JPanel userProcessContainer;
    private JTable maintenanceTable;
    private DefaultTableModel tableModel;
    private JButton btnAddMaintenance;
    private JButton btnUpdateStatus;
    private JButton btnViewUpcoming;
    private JButton btnBack;

    public PeriodicMaintenanceJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        initComponents();
        setupLayout();
        setupListeners();
        populateTable(); // Add initial data to the table
    }

    private void initComponents() {
        String[] columns = {"Maintenance ID", "Equipment Name", "Status", "Last Maintenance", "Next Due Date"};
        tableModel = new DefaultTableModel(columns, 0);
        maintenanceTable = new JTable(tableModel);

        btnAddMaintenance = createStyledButton("Add Maintenance", new Color(70, 130, 180));
        btnUpdateStatus = createStyledButton("Update Status", new Color(46, 139, 87));
        btnViewUpcoming = createStyledButton("View Upcoming", new Color(255, 165, 0));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Periodic Maintenance Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(maintenanceTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAddMaintenance);
        buttonPanel.add(btnUpdateStatus);
        buttonPanel.add(btnViewUpcoming);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnAddMaintenance.addActionListener(e -> {
            JTextField maintenanceIDField = new JTextField();
            JTextField equipmentNameField = new JTextField();
            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Scheduled", "Completed"});
            JTextField lastMaintenanceField = new JTextField();
            JTextField nextDueDateField = new JTextField();

            Object[] fields = {
                "Maintenance ID:", maintenanceIDField,
                "Equipment Name:", equipmentNameField,
                "Status:", statusComboBox,
                "Last Maintenance (YYYY-MM-DD):", lastMaintenanceField,
                "Next Due Date (YYYY-MM-DD):", nextDueDateField
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Add Maintenance Task", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                tableModel.addRow(new Object[]{
                    maintenanceIDField.getText(),
                    equipmentNameField.getText(),
                    statusComboBox.getSelectedItem(),
                    lastMaintenanceField.getText(),
                    nextDueDateField.getText()
                });
            }
        });

        btnUpdateStatus.addActionListener(e -> {
            int selectedRow = maintenanceTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a maintenance task to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Scheduled", "Completed"});
            Object[] fields = {"Update Status:", statusComboBox};

            int option = JOptionPane.showConfirmDialog(this, fields, "Update Maintenance Status", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(statusComboBox.getSelectedItem(), selectedRow, 2);
            }
        });

        btnViewUpcoming.addActionListener(e -> {
            DefaultTableModel upcomingModel = new DefaultTableModel(
                new String[]{"Maintenance ID", "Equipment Name", "Next Due Date"}, 0);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String nextDueDate = (String) tableModel.getValueAt(i, 4);
                if (isUpcoming(nextDueDate)) {
                    upcomingModel.addRow(new Object[]{
                        tableModel.getValueAt(i, 0),
                        tableModel.getValueAt(i, 1),
                        nextDueDate
                    });
                }
            }

            JTable upcomingTable = new JTable(upcomingModel);
            JScrollPane scrollPane = new JScrollPane(upcomingTable);
            JOptionPane.showMessageDialog(this, scrollPane, "Upcoming Maintenance Tasks", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void populateTable() {
        Object[][] sampleData = {
            {"M001", "Surgical Light", "Scheduled", "2024-11-01", "2025-01-15"},
            {"M002", "Ventilator", "Completed", "2024-10-10", "2024-12-20"},
            {"M003", "Defibrillator", "Scheduled", "2024-09-20", "2024-11-30"}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    private boolean isUpcoming(String nextDueDate) {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        java.time.LocalDate dueDate = java.time.LocalDate.parse(nextDueDate);
        return !dueDate.isBefore(currentDate);
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
