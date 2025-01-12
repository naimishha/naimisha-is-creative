package ui.AdminRole;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class FinanceManagementJPanel extends JPanel {
    private JPanel userProcessContainer;
    private JTable transactionsTable;
    private DefaultTableModel tableModel;
    private JButton btnNewBilling;
    private JButton btnProcessPayment;
    private JButton btnGenerateReport;
    private JButton btnDeleteTransaction;
    private JButton btnBack;

    // Financial summary labels
    private JLabel lblTotalRevenue;
    private JLabel lblTotalExpenses;
    private JLabel lblNetIncome;

    public FinanceManagementJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        initComponents();
        setupLayout();
        setupListeners();
        populateTable(); // Populate table with sample data
        updateSummary(); // Calculate initial financial summary
    }

    private void initComponents() {
        // Initialize table
        String[] columns = {"Date", "Type", "Description", "Amount", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        transactionsTable = new JTable(tableModel);
        styleTable(transactionsTable);

        // Initialize buttons
        btnNewBilling = createStyledButton("Add Transaction", new Color(70, 130, 180));
        btnProcessPayment = createStyledButton("Process Payment", new Color(46, 139, 87));
        btnGenerateReport = createStyledButton("Generate Report", new Color(106, 90, 205));
        btnDeleteTransaction = createStyledButton("Delete Transaction", new Color(220, 20, 60));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));

        // Initialize summary labels
        lblTotalRevenue = new JLabel("Total Revenue: $0.00");
        lblTotalExpenses = new JLabel("Total Expenses: $0.00");
        lblNetIncome = new JLabel("Net Income: $0.00");
        styleSummaryLabel(lblTotalRevenue);
        styleSummaryLabel(lblTotalExpenses);
        styleSummaryLabel(lblNetIncome);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Finance Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        summaryPanel.add(lblTotalRevenue);
        summaryPanel.add(lblTotalExpenses);
        summaryPanel.add(lblNetIncome);
        headerPanel.add(summaryPanel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JScrollPane scrollPane = new JScrollPane(transactionsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(btnNewBilling);
        buttonPanel.add(btnProcessPayment);
        buttonPanel.add(btnDeleteTransaction);
        buttonPanel.add(btnGenerateReport);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnNewBilling.addActionListener(e -> {
            addTransaction();
        });

        btnProcessPayment.addActionListener(e -> {
            int selectedRow = transactionsTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a transaction to process payment.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            tableModel.setValueAt("Paid", selectedRow, 4);
            updateSummary();
        });

        btnDeleteTransaction.addActionListener(e -> {
            int selectedRow = transactionsTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select a transaction to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            tableModel.removeRow(selectedRow);
            updateSummary();
        });

        btnGenerateReport.addActionListener(e -> {
            generateReport();
        });
    }

    private void styleSummaryLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(70, 70, 70));
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionBackground(new Color(220, 230, 240));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(baseColor.darker(), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
        return button;
    }

    private void populateTable() {
        Object[][] sampleData = {
            {"2024-11-01", "Revenue", "Consultation Fee", 150.00, "Completed"},
            {"2024-11-02", "Expense", "Office Supplies", -50.00, "Paid"},
            {"2024-11-03", "Revenue", "Surgery Fee", 200.00, "Pending"},
            {"2024-11-04", "Expense", "Utility Bills", -100.00, "Paid"}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    private void updateSummary() {
        double totalRevenue = 0;
        double totalExpenses = 0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            double amount = (double) tableModel.getValueAt(i, 3);
            if (amount > 0) {
                totalRevenue += amount;
            } else {
                totalExpenses += amount;
            }
        }

        double netIncome = totalRevenue + totalExpenses;

        lblTotalRevenue.setText(String.format("Total Revenue: $%.2f", totalRevenue));
        lblTotalExpenses.setText(String.format("Total Expenses: $%.2f", Math.abs(totalExpenses)));
        lblNetIncome.setText(String.format("Net Income: $%.2f", netIncome));
    }

    private void addTransaction() {
        JTextField dateField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField amountField = new JTextField();
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Pending", "Paid", "Completed"});

        Object[] fields = {
            "Date (YYYY-MM-DD):", dateField,
            "Type (Revenue/Expense):", typeField,
            "Description:", descriptionField,
            "Amount:", amountField,
            "Status:", statusComboBox
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add New Transaction", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String date = dateField.getText();
                String type = typeField.getText();
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String status = (String) statusComboBox.getSelectedItem();

                tableModel.addRow(new Object[]{date, type, description, amount, status});
                updateSummary();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generateReport() {
        StringBuilder report = new StringBuilder("Finance Report:\n\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            report.append("Date: ").append(tableModel.getValueAt(i, 0))
                  .append(", Type: ").append(tableModel.getValueAt(i, 1))
                  .append(", Description: ").append(tableModel.getValueAt(i, 2))
                  .append(", Amount: $").append(tableModel.getValueAt(i, 3))
                  .append(", Status: ").append(tableModel.getValueAt(i, 4))
                  .append("\n");
        }

        JOptionPane.showMessageDialog(this, report.toString(), "Finance Report", JOptionPane.INFORMATION_MESSAGE);
    }
}
