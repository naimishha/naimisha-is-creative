package UserInterface.SystemAdmin;

import Business.EcoSystem;
import Business.Employee.Employee;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Role.AdminRole;
import Business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManageEnterpriseAdminJPanel extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private EcoSystem system;
    private JTable adminTable;
    private DefaultTableModel tableModel;
    
    // Input Components
    private JComboBox<Network> networkCombo;
    private JComboBox<Enterprise> enterpriseCombo;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtName;
    
    // Buttons
    private JButton btnBack;
    private JButton btnSubmit;
    private JButton btnDelete;

    public ManageEnterpriseAdminJPanel(JPanel userProcessContainer, EcoSystem system) {
        this.userProcessContainer = userProcessContainer;
        this.system = system;
        initializeComponents();
        setupLayout();
        setupListeners();
        populateTable();
        populateNetworkComboBox();
    }

    private void initializeComponents() {
        // Initialize table
        String[] columnNames = {"Network", "Enterprise", "Username", "Employee"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        adminTable = new JTable(tableModel);
        setupTable(adminTable);

        // Initialize input components
        networkCombo = new JComboBox<>();
        enterpriseCombo = new JComboBox<>();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtName = new JTextField();

        // Style input components
        styleInputComponent(networkCombo);
        styleInputComponent(enterpriseCombo);
        styleInputComponent(txtUsername);
        styleInputComponent(txtPassword);
        styleInputComponent(txtName);

        // Initialize buttons
        btnSubmit = createStyledButton("Add Admin", new Color(70, 130, 180));
        btnDelete = createStyledButton("Delete Admin", new Color(220, 53, 69));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
    }

    private void setupTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionBackground(new Color(220, 230, 240));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
    }

    private void styleInputComponent(JComponent component) {
        component.setFont(new Font("Arial", Font.PLAIN, 14));
        component.setPreferredSize(new Dimension(200, 30));
        if (component instanceof JTextField) {
            ((JTextField) component).setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(2, 5, 2, 5)
                )
            );
        }
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    private void setupLayout() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Manage Enterprise Administrators");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JScrollPane tableScrollPane = new JScrollPane(adminTable);
        tableScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Enterprise Administrators",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));
        add(tableScrollPane, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Add Enterprise Administrator",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add form components
        addFormRow(formPanel, "Network:", networkCombo, gbc, 0);
        addFormRow(formPanel, "Enterprise:", enterpriseCombo, gbc, 1);
        addFormRow(formPanel, "Name:", txtName, gbc, 2);
        addFormRow(formPanel, "Username:", txtUsername, gbc, 3);
        addFormRow(formPanel, "Password:", txtPassword, gbc, 4);

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnDelete);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.SOUTH);
    }

    private void addFormRow(JPanel panel, String label, JComponent component, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private void setupListeners() {
        networkCombo.addActionListener(e -> populateEnterpriseComboBox());

        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnSubmit.addActionListener(e -> {
            // Validate inputs
            if (!validateInputs()) {
                return;
            }

            Enterprise enterprise = (Enterprise) enterpriseCombo.getSelectedItem();
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String name = txtName.getText();

            // Create employee and user account
            Employee employee = enterprise.getEmployeeDirectory().createEmployee(name);
            UserAccount account = enterprise.getUserAccountDirectory().createUserAccount(username, password, employee, new AdminRole());

            // Update UI
            populateTable();
            clearFields();
            JOptionPane.showMessageDialog(this,
                "Enterprise Admin added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = adminTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                    "Please select an admin to delete",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this admin?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                // Implement delete logic here
                populateTable();
            }
        });
    }

    private boolean validateInputs() {
        if (networkCombo.getSelectedItem() == null || 
            enterpriseCombo.getSelectedItem() == null ||
            txtUsername.getText().trim().isEmpty() ||
            txtPassword.getPassword().length == 0 ||
            txtName.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this,
                "Please fill all fields",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtName.setText("");
        networkCombo.setSelectedIndex(0);
    }

    private void populateTable() {
        tableModel.setRowCount(0);
        for (Network network : system.getNetworkList()) {
            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                for (UserAccount userAccount : enterprise.getUserAccountDirectory().getUserAccountList()) {
                    if (userAccount.getRole() instanceof AdminRole) {
                        Object[] row = new Object[]{
                            network.getName(),
                            enterprise.getName(),
                            userAccount.getUsername(),
                            userAccount.getEmployee().getName()
                        };
                        tableModel.addRow(row);
                    }
                }
            }
        }
    }

    private void populateNetworkComboBox() {
        networkCombo.removeAllItems();
        for (Network network : system.getNetworkList()) {
            networkCombo.addItem(network);
        }
    }

    private void populateEnterpriseComboBox() {
        enterpriseCombo.removeAllItems();
        Network network = (Network) networkCombo.getSelectedItem();
        if (network != null) {
            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                enterpriseCombo.addItem(enterprise);
            }
        }
    }
}