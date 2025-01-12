package ui.LabAssistantRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.LabTestWorkRequest;
import Business.WorkQueue.WorkRequest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LabAssistantWorkAreaJPanel extends JPanel {
    private JPanel userProcessContainer;
    private Organization organization;
    private Enterprise enterprise;
    private UserAccount userAccount;
    private JTable workRequestTable;
    private DefaultTableModel tableModel;

    public LabAssistantWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem system) {
        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        this.enterprise = enterprise;
        this.userAccount = account;

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Lab Assistant Work Area");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        // Tabbed pane for lab functions
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add Radiology Tab
        JPanel radiologyPanel = new RadiologyJPanel(userProcessContainer, userAccount, organization, enterprise);
        tabbedPane.addTab("Radiology", radiologyPanel);

        // Add Pathology Tab
        JPanel pathologyPanel = new PathologyJPanel(userProcessContainer, userAccount, organization, enterprise);
        tabbedPane.addTab("Pathology", pathologyPanel);

        // Add Blood Bank Tab
        JPanel bloodBankPanel = new BloodBankJPanel(userProcessContainer, userAccount, organization, enterprise);
        tabbedPane.addTab("Blood Bank", bloodBankPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Table for overall requests
        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Test Name", "Sender", "Receiver", "Status", "Result"};
        tableModel = new DefaultTableModel(columnNames, 0);
        workRequestTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(workRequestTable);

        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        populateTable();
    }

    public void populateTable() {
        tableModel.setRowCount(0); // Clear existing rows
        for (WorkRequest request : userAccount.getWorkQueue().getWorkRequestList()) {
            if (request instanceof LabTestWorkRequest) {
                LabTestWorkRequest labTestRequest = (LabTestWorkRequest) request;
                Object[] row = new Object[5];
                row[0] = labTestRequest.getTestResult(); // Test Name
                row[1] = labTestRequest.getSender().getEmployee().getName(); // Sender
                row[2] = labTestRequest.getReceiver() == null ? "Unassigned" : labTestRequest.getReceiver().getEmployee().getName(); // Receiver
                row[3] = labTestRequest.getStatus(); // Status
                row[4] = labTestRequest.getTestResult() == null ? "Pending" : labTestRequest.getTestResult(); // Result
                tableModel.addRow(row);
            }
        }
    }
}
