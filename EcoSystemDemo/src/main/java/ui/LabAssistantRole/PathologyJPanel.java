package ui.LabAssistantRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PathologyJPanel extends JPanel {

    private JTable pathologyTable;
    private DefaultTableModel tableModel;
    private JButton btnAddTest;
    private JButton btnRemoveTest;
    private JButton btnUpdateStatus;
    private JButton btnGenerateReport;

    public PathologyJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise) {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Pathology Department");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Pathology-specific table
        String[] columns = {"Test ID", "Patient Name", "Test Type", "Sample Type", "Status", "Result"};
        tableModel = new DefaultTableModel(columns, 0);
        pathologyTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(pathologyTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for functionality
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAddTest = createButton("Add Pathology Test");
        btnRemoveTest = createButton("Remove Test Record");
        btnUpdateStatus = createButton("Update Test Status");
        btnGenerateReport = createButton("Generate Test Report");
        buttonPanel.add(btnAddTest);
        buttonPanel.add(btnRemoveTest);
        buttonPanel.add(btnUpdateStatus);
        buttonPanel.add(btnGenerateReport);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add listeners for buttons
        setupListeners();

        // Populate initial data
        populateTable();
    }

    private void populateTable() {
        Object[][] sampleData = {
                {"T001", "John Doe", "Blood Test", "Blood", "Completed", "Normal"},
                {"T002", "Jane Smith", "Biopsy", "Tissue", "In Progress", "N/A"},
                {"T003", "Alice Brown", "Urine Test", "Urine", "Pending", "N/A"}
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
        // Add Test Record Listener
        btnAddTest.addActionListener((ActionEvent e) -> {
            String testId = JOptionPane.showInputDialog(this, "Enter Test ID:");
            if (testId == null || testId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Test ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String patientName = JOptionPane.showInputDialog(this, "Enter Patient Name:");
            if (patientName == null || patientName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Patient Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String testType = JOptionPane.showInputDialog(this, "Enter Test Type:");
            if (testType == null || testType.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Test Type cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sampleType = JOptionPane.showInputDialog(this, "Enter Sample Type:");
            if (sampleType == null || sampleType.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sample Type cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.addRow(new Object[]{testId, patientName, testType, sampleType, "Pending", "N/A"});
            JOptionPane.showMessageDialog(this, "Test record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Remove Test Record Listener
        btnRemoveTest.addActionListener((ActionEvent e) -> {
            int selectedRow = pathologyTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a record to remove!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this record?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Record removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Update Test Status Listener
        btnUpdateStatus.addActionListener((ActionEvent e) -> {
            int selectedRow = pathologyTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a record to update!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newStatus = JOptionPane.showInputDialog(this, "Enter new status (e.g., Completed, In Progress):");
            if (newStatus == null || newStatus.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Status cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setValueAt(newStatus, selectedRow, 4); // Update status column
            JOptionPane.showMessageDialog(this, "Status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Generate Report Listener
        btnGenerateReport.addActionListener((ActionEvent e) -> {
            int rowCount = tableModel.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "No data available to generate report!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            StringBuilder report = new StringBuilder("Pathology Test Report\n\n");
            report.append("Test ID\tPatient Name\tTest Type\tSample Type\tStatus\tResult\n");
            report.append("------------------------------------------------------------------\n");

            for (int i = 0; i < rowCount; i++) {
                report.append(tableModel.getValueAt(i, 0)).append("\t")
                        .append(tableModel.getValueAt(i, 1)).append("\t")
                        .append(tableModel.getValueAt(i, 2)).append("\t")
                        .append(tableModel.getValueAt(i, 3)).append("\t")
                        .append(tableModel.getValueAt(i, 4)).append("\t")
                        .append(tableModel.getValueAt(i, 5)).append("\n");
            }

            JTextArea reportArea = new JTextArea(report.toString());
            reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            reportArea.setEditable(false);
            JOptionPane.showMessageDialog(this, new JScrollPane(reportArea), "Pathology Test Report", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
