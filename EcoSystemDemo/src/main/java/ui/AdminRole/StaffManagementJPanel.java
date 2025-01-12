package ui.AdminRole;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class StaffManagementJPanel extends JPanel {
    private JPanel userProcessContainer;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JButton btnAddEmployee;
    private JButton btnUpdateEmployee;
    private JButton btnDeleteEmployee;
    private JButton btnManageTraining;
    private JButton btnViewSchedule;
    private JButton btnBack;

    public StaffManagementJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        initComponents();
        setupLayout();
        setupListeners();
        populateTable(); // Populate table with sample data
    }

    private void initComponents() {
        // Initialize table
        String[] columns = {"Employee ID", "Name", "Department", "Role", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        styleTable(employeeTable);

        // Initialize buttons
        btnAddEmployee = createStyledButton("Add Employee", new Color(70, 130, 180));
        btnUpdateEmployee = createStyledButton("Update Employee", new Color(46, 139, 87));
        btnDeleteEmployee = createStyledButton("Delete Employee", new Color(220, 20, 60));
        btnManageTraining = createStyledButton("Manage Training", new Color(106, 90, 205));
        btnViewSchedule = createStyledButton("View Schedule", new Color(70, 130, 180));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Staff Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(btnAddEmployee);
        buttonPanel.add(btnUpdateEmployee);
        buttonPanel.add(btnDeleteEmployee);
        buttonPanel.add(btnManageTraining);
        buttonPanel.add(btnViewSchedule);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnAddEmployee.addActionListener(e -> addEmployee());

        btnUpdateEmployee.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select an employee to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            updateEmployee(selectedRow);
        });

        btnDeleteEmployee.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            tableModel.removeRow(selectedRow);
        });

        btnManageTraining.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select an employee to manage training.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String employeeID = (String) tableModel.getValueAt(selectedRow, 0);
            String employeeName = (String) tableModel.getValueAt(selectedRow, 1);

            String[] trainingTopics = {"Leadership Skills", "Technical Training", "Communication Skills", "Project Management"};
            JComboBox<String> trainingComboBox = new JComboBox<>(trainingTopics);

            Object[] fields = {
                "Employee ID: " + employeeID,
                "Name: " + employeeName,
                "Select Training:", trainingComboBox
            };

            int option = JOptionPane.showConfirmDialog(this, fields, "Assign Training", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String selectedTraining = (String) trainingComboBox.getSelectedItem();
                JOptionPane.showMessageDialog(this, "Training '" + selectedTraining + "' assigned to " + employeeName + ".", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnViewSchedule.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select an employee to view the schedule.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String employeeID = (String) tableModel.getValueAt(selectedRow, 0);
            String employeeName = (String) tableModel.getValueAt(selectedRow, 1);

            String schedule = "Employee Schedule:\n\n" +
                    "Monday: 9:00 AM - 5:00 PM\n" +
                    "Tuesday: 9:00 AM - 5:00 PM\n" +
                    "Wednesday: 9:00 AM - 5:00 PM\n" +
                    "Thursday: 9:00 AM - 5:00 PM\n" +
                    "Friday: 9:00 AM - 4:00 PM";

            JOptionPane.showMessageDialog(this, "Schedule for " + employeeName + " (" + employeeID + "):\n\n" + schedule, "Employee Schedule", JOptionPane.INFORMATION_MESSAGE);
        });
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
            {"E001", "John Doe", "HR", "Manager", "Active"},
            {"E002", "Jane Smith", "IT", "Developer", "Active"},
            {"E003", "Alice Brown", "Finance", "Analyst", "On Leave"},
            {"E004", "Bob White", "Operations", "Supervisor", "Active"}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    private void addEmployee() {
        JTextField employeeIDField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField roleField = new JTextField();
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Active", "On Leave", "Inactive"});

        Object[] fields = {
            "Employee ID:", employeeIDField,
            "Name:", nameField,
            "Department:", departmentField,
            "Role:", roleField,
            "Status:", statusComboBox
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add New Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String employeeID = employeeIDField.getText();
            String name = nameField.getText();
            String department = departmentField.getText();
            String role = roleField.getText();
            String status = (String) statusComboBox.getSelectedItem();

            if (employeeID.isEmpty() || name.isEmpty() || department.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                tableModel.addRow(new Object[]{employeeID, name, department, role, status});
            }
        }
    }

    private void updateEmployee(int row) {
        JTextField employeeIDField = new JTextField((String) tableModel.getValueAt(row, 0));
        JTextField nameField = new JTextField((String) tableModel.getValueAt(row, 1));
        JTextField departmentField = new JTextField((String) tableModel.getValueAt(row, 2));
        JTextField roleField = new JTextField((String) tableModel.getValueAt(row, 3));
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Active", "On Leave", "Inactive"});
        statusComboBox.setSelectedItem(tableModel.getValueAt(row, 4));

        Object[] fields = {
            "Employee ID:", employeeIDField,
            "Name:", nameField,
            "Department:", departmentField,
            "Role:", roleField,
            "Status:", statusComboBox
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Update Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            tableModel.setValueAt(employeeIDField.getText(), row, 0);
            tableModel.setValueAt(nameField.getText(), row, 1);
            tableModel.setValueAt(departmentField.getText(), row, 2);
            tableModel.setValueAt(roleField.getText(), row, 3);
            tableModel.setValueAt(statusComboBox.getSelectedItem(), row, 4);
        }
    }
}
