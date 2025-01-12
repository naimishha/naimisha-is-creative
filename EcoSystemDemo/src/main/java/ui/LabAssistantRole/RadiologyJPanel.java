package ui.LabAssistantRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RadiologyJPanel extends JPanel {

    private JTable radiologyTable;
    private DefaultTableModel tableModel;
    private JButton btnAddRequest;
    private JButton btnRemoveRequest;
    private JButton btnUpdateStatus;
    private JButton btnGenerateReport;

    public RadiologyJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise) {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Radiology Department");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Radiology-specific table
        String[] columns = {"Request ID", "Patient Name", "Test Type", "Status", "Result"};
        tableModel = new DefaultTableModel(columns, 0);
        radiologyTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(radiologyTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for functionality
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAddRequest = createButton("Add Radiology Request");
        btnRemoveRequest = createButton("Remove Selected Request");
        btnUpdateStatus = createButton("Update Status");
        btnGenerateReport = createButton("Generate Report");
        buttonPanel.add(btnAddRequest);
        buttonPanel.add(btnRemoveRequest);
        buttonPanel.add(btnUpdateStatus);
        buttonPanel.add(btnGenerateReport);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add listeners for buttons
        setupListeners();

        // Populate initial data (mock or from backend)
        populateTable();
    }

    private void populateTable() {
        Object[][] sampleData = {
                {"R001", "John Doe", "MRI", "Pending", "N/A"},
                {"R002", "Jane Smith", "CT Scan", "Completed", "Normal"},
                {"R003", "Alice Brown", "X-Ray", "In Progress", "N/A"}
        };
        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void setupListeners() {
        // Add Request Listener
        btnAddRequest.addActionListener((ActionEvent e) -> {
            String requestId = JOptionPane.showInputDialog(this, "Enter Request ID:");
            if (requestId == null || requestId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Request ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String patientName = JOptionPane.showInputDialog(this, "Enter Patient Name:");
            if (patientName == null || patientName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Patient Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String testType = JOptionPane.showInputDialog(this, "Enter Test Type (e.g., MRI, CT Scan, X-Ray):");
            if (testType == null || testType.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Test Type cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.addRow(new Object[]{requestId, patientName, testType, "Pending", "N/A"});
            JOptionPane.showMessageDialog(this, "Request added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Remove Request Listener
        btnRemoveRequest.addActionListener((ActionEvent e) -> {
            int selectedRow = radiologyTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a request to remove!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this request?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Request removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Update Status Listener
        btnUpdateStatus.addActionListener((ActionEvent e) -> {
            int selectedRow = radiologyTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a request to update!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newStatus = JOptionPane.showInputDialog(this, "Enter new status (e.g., Completed, In Progress):");
            if (newStatus == null || newStatus.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Status cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setValueAt(newStatus, selectedRow, 3); // Update status column
            JOptionPane.showMessageDialog(this, "Status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Generate Report Listener
        btnGenerateReport.addActionListener((ActionEvent e) -> {
            int rowCount = tableModel.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "No data available to generate report!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            StringBuilder report = new StringBuilder("Radiology Report\n\n");
            report.append("Request ID\tPatient Name\tTest Type\tStatus\tResult\n");
            report.append("-----------------------------------------------------\n");

            for (int i = 0; i < rowCount; i++) {
                report.append(tableModel.getValueAt(i, 0)).append("\t")
                        .append(tableModel.getValueAt(i, 1)).append("\t")
                        .append(tableModel.getValueAt(i, 2)).append("\t")
                        .append(tableModel.getValueAt(i, 3)).append("\t")
                        .append(tableModel.getValueAt(i, 4)).append("\n");
            }

            JTextArea reportArea = new JTextArea(report.toString());
            reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            reportArea.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(reportArea), "Radiology Report", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
