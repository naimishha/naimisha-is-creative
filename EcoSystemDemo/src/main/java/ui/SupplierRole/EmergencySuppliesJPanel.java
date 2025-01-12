package ui.SupplierRole;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EmergencySuppliesJPanel extends JPanel {
    private JPanel userProcessContainer;
    private JTable emergencyTable;
    private DefaultTableModel tableModel;
    private JButton btnAddRequest;
    private JButton btnUpdateStatus;
    private JButton btnResolveRequest;
    private JButton btnBack;

    public EmergencySuppliesJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        initComponents();
        setupLayout();
        setupListeners();
        populateTable(); // Add initial data to the table
    }

    private void initComponents() {
        String[] columns = {"Request ID", "Requested Item", "Urgency Level", "Status", "Requested By"};
        tableModel = new DefaultTableModel(columns, 0);
        emergencyTable = new JTable(tableModel);

        btnAddRequest = createStyledButton("Add Request", new Color(70, 130, 180));
        btnUpdateStatus = createStyledButton("Update Status", new Color(46, 139, 87));
        btnResolveRequest = createStyledButton("Resolve Request", new Color(220, 20, 60));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Emergency Supply Requests");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(emergencyTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAddRequest);
        buttonPanel.add(btnUpdateStatus);
        buttonPanel.add(btnResolveRequest);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnAddRequest.addActionListener(e -> {
            JTextField requestIDField = new JTextField();
            JTextField itemField = new JTextField();
            JComboBox<String> urgencyLevelComboBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Resolved"});
            JTextField requestedByField = new JTextField();

            Object[] fields = {
                "Request ID:", requestIDField,
                "Requested Item:", itemField,
                "Urgency Level:", urgencyLevelComboBox,
                "Status:", statusComboBox,
                "Requested By:", requestedByField
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Add Emergency Request", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                tableModel.addRow(new Object[]{
                    requestIDField.getText(),
                    itemField.getText(),
                    urgencyLevelComboBox.getSelectedItem(),
                    statusComboBox.getSelectedItem(),
                    requestedByField.getText()
                });
            }
        });

        btnUpdateStatus.addActionListener(e -> {
            int selectedRow = emergencyTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a request to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Resolved"});
            Object[] fields = {"Update Status:", statusComboBox};

            int option = JOptionPane.showConfirmDialog(this, fields, "Update Request Status", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                tableModel.setValueAt(statusComboBox.getSelectedItem(), selectedRow, 3);
            }
        });

        btnResolveRequest.addActionListener(e -> {
            int selectedRow = emergencyTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a request to resolve.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            tableModel.setValueAt("Resolved", selectedRow, 3);
            JOptionPane.showMessageDialog(this, "Request resolved successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void populateTable() {
        Object[][] sampleData = {
            {"REQ001", "Oxygen Cylinders", "High", "Pending", "Dr. Smith"},
            {"REQ002", "Surgical Masks", "Medium", "In Progress", "Nurse Brown"},
            {"REQ003", "Ventilators", "High", "Pending", "Dr. Adams"}
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
