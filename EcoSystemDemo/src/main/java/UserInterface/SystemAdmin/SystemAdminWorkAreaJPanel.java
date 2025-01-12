package UserInterface.SystemAdmin;

import Business.EcoSystem;
import Business.Network.Network;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SystemAdminWorkAreaJPanel extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private EcoSystem system;
    private JTree networkTree;
    private JButton btnManageNetwork;
    private JButton btnManageEnterprise;
    private JButton btnManageAdmin;

    public SystemAdminWorkAreaJPanel(JPanel userProcessContainer, EcoSystem system) {
        this.userProcessContainer = userProcessContainer;
        this.system = system;
        initializeComponents();
        setupLayout();
        setupListeners();
        populateTree();
    }

    private void initializeComponents() {
        // Initialize tree
        networkTree = new JTree();
        networkTree.setBorder(new EmptyBorder(10, 10, 10, 10));
        networkTree.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Create stylized buttons
        btnManageNetwork = createStyledButton("Manage Network", "network.png");
        btnManageEnterprise = createStyledButton("Manage Enterprise", "enterprise.png");
        btnManageAdmin = createStyledButton("Manage Enterprise Admin", "admin.png");
    }

    private JButton createStyledButton(String text, String iconName) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(new Color(51, 51, 51));
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setFocusPainted(false);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(230, 230, 230));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240));
            }
        });

        return button;
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("System Administrator Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Create tree scroll pane
        JScrollPane treeScrollPane = new JScrollPane(networkTree);
        treeScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Network Hierarchy",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 14)
        ));

        // Create button panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        
        buttonPanel.add(btnManageNetwork, gbc);
        buttonPanel.add(btnManageEnterprise, gbc);
        buttonPanel.add(btnManageAdmin, gbc);

        // Add components to main panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints centerGbc = new GridBagConstraints();
        centerGbc.fill = GridBagConstraints.BOTH;
        centerGbc.weightx = 0.7;
        centerGbc.weighty = 1.0;
        centerPanel.add(treeScrollPane, centerGbc);

        centerGbc.weightx = 0.3;
        centerGbc.gridx = 1;
        centerPanel.add(buttonPanel, centerGbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnManageNetwork.addActionListener(e -> {
            ManageNetworkJPanel manageNetworkJPanel = new ManageNetworkJPanel(userProcessContainer, system);
            userProcessContainer.add("ManageNetworkJPanel", manageNetworkJPanel);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        });

        btnManageEnterprise.addActionListener(e -> {
            ManageEnterpriseJPanel manageEnterpriseJPanel = new ManageEnterpriseJPanel(userProcessContainer, system);
            userProcessContainer.add("ManageEnterpriseJPanel", manageEnterpriseJPanel);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        });

        btnManageAdmin.addActionListener(e -> {
            ManageEnterpriseAdminJPanel manageEnterpriseAdminJPanel = new ManageEnterpriseAdminJPanel(userProcessContainer, system);
            userProcessContainer.add("ManageEnterpriseAdminJPanel", manageEnterpriseAdminJPanel);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        });
    }

    private void populateTree() {
        // Your existing tree population code
    }
}