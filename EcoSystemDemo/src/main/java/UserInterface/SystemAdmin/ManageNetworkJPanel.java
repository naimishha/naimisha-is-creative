package UserInterface.SystemAdmin;

import Business.EcoSystem;
import Business.Network.Network;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManageNetworkJPanel extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private EcoSystem system;
    private JTable networkTable;
    private DefaultTableModel tableModel;
    private JTextField txtNetworkName;
    private JButton btnBack;
    private JButton btnSubmit;
    private JButton btnDelete;

    public ManageNetworkJPanel(JPanel userProcessContainer, EcoSystem system) {
        this.userProcessContainer = userProcessContainer;
        this.system = system;
        initializeComponents();
        setupLayout();
        setupListeners();
        populateTable();
    }

    private void initializeComponents() {
        // Initialize table
        String[] columnNames = {"Network Name"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        networkTable = new JTable(tableModel);
        networkTable.setFont(new Font("Arial", Font.PLAIN, 14));
        networkTable.setRowHeight(25);
        networkTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        networkTable.setSelectionBackground(new Color(220, 230, 240));

        // Initialize input components
        txtNetworkName = new JTextField();
        txtNetworkName.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNetworkName.setPreferredSize(new Dimension(200, 30));

        // Initialize buttons
        btnSubmit = createStyledButton("Add Network", new Color(70, 130, 180));
        btnDelete = createStyledButton("Delete Network", new Color(220, 53, 69));
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

        // Add hover effect
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
        JLabel titleLabel = new JLabel("Manage Networks");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JScrollPane tableScrollPane = new JScrollPane(networkTable);
        tableScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Networks",
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
                "Add Network",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add network name label and field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Network Name:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(txtNetworkName, gbc);

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnDelete);

        gbc.gridx = 0;
        gbc.gridy = 1;
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
            String name = txtNetworkName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a network name.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Network network = system.createAndAddNetwork();
            network.setName(name);
            populateTable();
            txtNetworkName.setText("");
            JOptionPane.showMessageDialog(this, 
                "Network added successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = networkTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this,
                    "Please select a network to delete.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this network?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                Network network = system.getNetworkList().get(selectedRow);
                system.getNetworkList().remove(network);
                populateTable();
            }
        });
    }

    private void populateTable() {
        tableModel.setRowCount(0);
        for (Network network : system.getNetworkList()) {
            Object[] row = new Object[]{network.getName()};
            tableModel.addRow(row);
        }
    }
}