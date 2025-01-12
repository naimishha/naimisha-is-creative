package UserInterface.SystemAdmin;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManageEnterpriseJPanel extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private EcoSystem system;
    private JTable enterpriseTable;
    private DefaultTableModel tableModel;
    private JTextField txtName;
    private JComboBox<Network> networkCombo;
    private JComboBox<Enterprise.EnterpriseType> enterpriseTypeCombo;
    private JButton btnBack;
    private JButton btnSubmit;
    private JButton btnDelete;

    public ManageEnterpriseJPanel(JPanel userProcessContainer, EcoSystem system) {
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
        String[] columnNames = {"Network", "Enterprise Name", "Enterprise Type"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        enterpriseTable = new JTable(tableModel);
        enterpriseTable.setFont(new Font("Arial", Font.PLAIN, 14));
        enterpriseTable.setRowHeight(25);
        enterpriseTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        enterpriseTable.setSelectionBackground(new Color(220, 230, 240));

        // Initialize input components
        txtName = new JTextField();
        txtName.setFont(new Font("Arial", Font.PLAIN, 14));
        txtName.setPreferredSize(new Dimension(200, 30));

        networkCombo = new JComboBox<>();
        networkCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        networkCombo.setPreferredSize(new Dimension(200, 30));

        enterpriseTypeCombo = new JComboBox<>(Enterprise.EnterpriseType.values());
        enterpriseTypeCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        enterpriseTypeCombo.setPreferredSize(new Dimension(200, 30));

        // Initialize buttons
        btnSubmit = createStyledButton("Add Enterprise", new Color(70, 130, 180));
        btnDelete = createStyledButton("Delete Enterprise", new Color(220, 53, 69));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
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
        JLabel titleLabel = new JLabel("Manage Enterprises");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JScrollPane tableScrollPane = new JScrollPane(enterpriseTable);
        tableScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Enterprises",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));
        add(tableScrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Add Enterprise",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Add network selector
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Network:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(networkCombo, gbc);

        // Add enterprise type selector
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Enterprise Type:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(enterpriseTypeCombo, gbc);

        // Add enterprise name field
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(txtName, gbc);

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnDelete);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(buttonPanel, gbc);

        add(inputPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnSubmit.addActionListener(e -> {
            Network network = (Network) networkCombo.getSelectedItem();
            Enterprise.EnterpriseType type = (Enterprise.EnterpriseType) enterpriseTypeCombo.getSelectedItem();
            String name = txtName.getText().trim();

            if (name.isEmpty() || network == null || type == null) {
                JOptionPane.showMessageDialog(this,
                    "Please fill all fields",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Enterprise enterprise = network.getEnterpriseDirectory().createAndAddEnterprise(name, type);
            populateTable();
            txtName.setText("");
            JOptionPane.showMessageDialog(this,
                "Enterprise added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = enterpriseTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                    "Please select an enterprise to delete",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this enterprise?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                // Implement delete logic here
                populateTable();
            }
        });
    }

    private void populateTable() {
        tableModel.setRowCount(0);
        for (Network network : system.getNetworkList()) {
            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                Object[] row = new Object[]{
                    network.getName(),
                    enterprise.getName(),
                    enterprise.getEnterpriseType().getValue()
                };
                tableModel.addRow(row);
            }
        }
    }

    private void populateNetworkComboBox() {
        networkCombo.removeAllItems();
        for (Network network : system.getNetworkList()) {
            networkCombo.addItem(network);
        }
    }
}