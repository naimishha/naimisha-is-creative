package ui.AdminRole;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MedicalRecordsJPanel extends JPanel {
    private JPanel userProcessContainer;
    private JTable patientRecordsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton btnSearch;
    private JButton btnView;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnBack;

    public MedicalRecordsJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        initComponents();
        setupLayout();
        setupListeners();
        populateTable(); // Populate table with sample data
    }

    private void initComponents() {
        // Initialize table
        String[] columns = {"Patient ID", "Name", "Age", "Gender", "Last Updated"};
        tableModel = new DefaultTableModel(columns, 0);
        patientRecordsTable = new JTable(tableModel);
        styleTable(patientRecordsTable);

        // Initialize components
        searchField = new JTextField(20);
        btnSearch = createStyledButton("Search", new Color(70, 130, 180));
        btnView = createStyledButton("View Details", new Color(46, 139, 87));
        btnAdd = createStyledButton("Add Record", new Color(60, 179, 113));
        btnDelete = createStyledButton("Delete Record", new Color(220, 20, 60));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Medical Records Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search Records: "));
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        headerPanel.add(searchPanel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JScrollPane scrollPane = new JScrollPane(patientRecordsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnSearch.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a name or patient ID to search.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            filterTable(query);
        });

        btnView.addActionListener(e -> {
            int selectedRow = patientRecordsTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                    "Please select a patient record to view",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            String patientID = (String) tableModel.getValueAt(selectedRow, 0);
            String patientName = (String) tableModel.getValueAt(selectedRow, 1);
            JOptionPane.showMessageDialog(this,
                "Viewing details for:\nPatient ID: " + patientID + "\nName: " + patientName,
                "Patient Details",
                JOptionPane.INFORMATION_MESSAGE);
        });

        btnAdd.addActionListener(e -> {
            addNewRecord();
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = patientRecordsTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                    "Please select a record to delete.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            tableModel.removeRow(selectedRow);
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

    // Helper method for creating styled buttons
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
            {"P001", "John Doe", 34, "Male", "2024-11-12"},
            {"P002", "Jane Smith", 28, "Female", "2024-11-10"},
            {"P003", "Alice Brown", 45, "Female", "2024-11-05"},
            {"P004", "Bob White", 50, "Male", "2024-11-01"}
        };
        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    private void filterTable(String query) {
        DefaultTableModel filteredModel = new DefaultTableModel(
            new String[]{"Patient ID", "Name", "Age", "Gender", "Last Updated"}, 0);

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String patientID = (String) tableModel.getValueAt(i, 0);
            String name = (String) tableModel.getValueAt(i, 1);

            if (patientID.contains(query) || name.toLowerCase().contains(query.toLowerCase())) {
                Object[] row = new Object[tableModel.getColumnCount()];
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    row[j] = tableModel.getValueAt(i, j);
                }
                filteredModel.addRow(row);
            }
        }

        patientRecordsTable.setModel(filteredModel);

        if (filteredModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "No records found matching your search query.",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
            patientRecordsTable.setModel(tableModel); // Reset to original if no match found
        }
    }

    private void addNewRecord() {
        JTextField patientIDField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField lastUpdatedField = new JTextField();

        Object[] fields = {
            "Patient ID:", patientIDField,
            "Name:", nameField,
            "Age:", ageField,
            "Gender:", genderField,
            "Last Updated (YYYY-MM-DD):", lastUpdatedField
        };

        int option = JOptionPane.showConfirmDialog(
            this, fields, "Add New Patient Record", JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            String patientID = patientIDField.getText();
            String name = nameField.getText();
            String age = ageField.getText();
            String gender = genderField.getText();
            String lastUpdated = lastUpdatedField.getText();

            if (patientID.isEmpty() || name.isEmpty() || age.isEmpty() || gender.isEmpty() || lastUpdated.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "All fields are required.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                tableModel.addRow(new Object[]{patientID, name, Integer.parseInt(age), gender, lastUpdated});
            }
        }
    }
}
