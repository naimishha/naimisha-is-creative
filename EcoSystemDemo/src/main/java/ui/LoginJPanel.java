package ui;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginJPanel extends javax.swing.JPanel {
    private JPanel mainContainer;
    private EcoSystem system;
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> enterpriseTypeCombo;
    private JButton btnLogin;
    private JButton btnReset;

    public LoginJPanel(JPanel mainContainer, EcoSystem system) {
        this.mainContainer = mainContainer;
        this.system = system;
        initializeComponents();
        setupLayout();
        setupListeners();
    }

    private void initializeComponents() {
        // Initialize input components
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        enterpriseTypeCombo = new JComboBox<>();
        
        // Style input components
        styleInputComponent(txtUsername);
        styleInputComponent(txtPassword);
        styleInputComponent(enterpriseTypeCombo);
        
        // Add enterprise types
        enterpriseTypeCombo.addItem("Select Role");
        enterpriseTypeCombo.addItem("System Admin");
        enterpriseTypeCombo.addItem("Doctor");
        enterpriseTypeCombo.addItem("Lab");
        enterpriseTypeCombo.addItem("Pharmacy");
        enterpriseTypeCombo.addItem("Reception");
        
        // Initialize buttons
        btnLogin = createStyledButton("Login", new Color(70, 130, 180));
        btnReset = createStyledButton("Reset", new Color(108, 117, 125));
    }

    private void styleInputComponent(JComponent component) {
        component.setFont(new Font("Arial", Font.PLAIN, 14));
        component.setPreferredSize(new Dimension(250, 35));
        if (component instanceof JTextField || component instanceof JPasswordField) {
            ((JTextField) component).setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(2, 10, 2, 10)
                )
            );
        }
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 250));

        // Create login panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add title
        JLabel titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        // Add subtitle
        JLabel subtitleLabel = new JLabel("Please login to continue");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 7, 20, 7);
        loginPanel.add(subtitleLabel, gbc);

        // Reset insets for form fields
        gbc.insets = new Insets(7, 7, 7, 7);

        // Add role selector
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        loginPanel.add(new JLabel("Select Role:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(enterpriseTypeCombo, gbc);

        // Add username field
        gbc.gridy = 3;
        gbc.gridx = 0;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(txtUsername, gbc);

        // Add password field
        gbc.gridy = 4;
        gbc.gridx = 0;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(txtPassword, gbc);

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnReset);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 7, 7, 7);
        loginPanel.add(buttonPanel, gbc);

        // Add login panel to main panel
        add(loginPanel);
    }

    private void setupListeners() {
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String selectedRole = (String) enterpriseTypeCombo.getSelectedItem();

            if (!validateInputs(username, password, selectedRole)) {
                return;
            }

            UserAccount userAccount = system.getUserAccountDirectory().authenticateUser(username, password);
            
            if (userAccount == null) {
                JOptionPane.showMessageDialog(this,
                    "Invalid credentials. Please try again.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Navigate to appropriate workspace based on role
            CardLayout layout = (CardLayout) mainContainer.getLayout();
            mainContainer.add("WorkArea", createWorkArea(userAccount));
            layout.next(mainContainer);
        });

        btnReset.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
            enterpriseTypeCombo.setSelectedIndex(0);
        });
    }

    private boolean validateInputs(String username, String password, String role) {
        if (role.equals("Select Role")) {
            JOptionPane.showMessageDialog(this,
                "Please select a role",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private JPanel createWorkArea(UserAccount account) {
        // Create appropriate work area based on user role
        // This will be implemented as we create other UI components
        return new JPanel(); // Placeholder
    }
}