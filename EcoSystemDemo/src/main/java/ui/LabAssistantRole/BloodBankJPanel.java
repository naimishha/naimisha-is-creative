package ui.LabAssistantRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BloodBankJPanel extends JPanel {

    private JTable bloodBankTable;
    private DefaultTableModel tableModel;
    private JButton btnAddDonation;
    private JButton btnRemoveDonation;
    private JButton btnUpdateStatus;
    private JButton btnGenerateReport;

    public BloodBankJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise) {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Blood Bank Services");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Blood bank-specific table
        String[] columns = {"Donation ID", "Donor Name", "Blood Type", "Status", "Quantity (ml)"};
        tableModel = new DefaultTableModel(columns, 0);
        bloodBankTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(bloodBankTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for functionality
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAddDonation = createButton("Add Donation Record");
        btnRemoveDonation = createButton("Remove Donation Record");
        btnUpdateStatus = createButton("Update Donation Status");
        btnGenerateReport = createButton("Generate Blood Inventory Report");
        buttonPanel.add(btnAddDonation);
        buttonPanel.add(btnRemoveDonation);
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
                {"B001", "John Doe", "O+", "Completed", "500"},
                {"B002", "Jane Smith", "A-", "Pending", "300"},
                {"B003", "Alice Brown", "B+", "In Progress", "400"}
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
        // Add Donation Record Listener
        btnAddDonation.addActionListener((ActionEvent e) -> {
            String donationId = JOptionPane.showInputDialog(this, "Enter Donation ID:");
            if (donationId == null || donationId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Donation ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String donorName = JOptionPane.showInputDialog(this, "Enter Donor Name:");
            if (donorName == null || donorName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Donor Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String bloodType = JOptionPane.showInputDialog(this, "Enter Blood Type (e.g., A+, O-, B+):");
            if (bloodType == null || bloodType.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Blood Type cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String quantity = JOptionPane.showInputDialog(this, "Enter Quantity (in ml):");
            if (quantity == null || quantity.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Quantity cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.addRow(new Object[]{donationId, donorName, bloodType, "Pending", quantity});
            JOptionPane.showMessageDialog(this, "Donation record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Remove Donation Record Listener
        btnRemoveDonation.addActionListener((ActionEvent e) -> {
            int selectedRow = bloodBankTable.getSelectedRow();
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

        // Update Donation Status Listener
        btnUpdateStatus.addActionListener((ActionEvent e) -> {
            int selectedRow = bloodBankTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a record to update!", "Error", JOptionPane.ERROR_MESSAGE);
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

            StringBuilder report = new StringBuilder("Blood Bank Inventory Report\n\n");
            report.append("Donation ID\tDonor Name\tBlood Type\tStatus\tQuantity (ml)\n");
            report.append("------------------------------------------------------------\n");

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
            JOptionPane.showMessageDialog(this, new JScrollPane(reportArea), "Blood Bank Report", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
