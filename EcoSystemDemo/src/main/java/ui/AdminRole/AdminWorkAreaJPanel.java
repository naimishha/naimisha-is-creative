package ui.AdminRole;

import Business.UserAccount.UserAccount;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminWorkAreaJPanel extends JPanel {
    private JPanel userProcessContainer;
    private UserAccount userAccount;
    
    // Navigation Buttons
    private JButton btnMedicalRecords;
    private JButton btnFinance;
    private JButton btnStaffManagement;
    private JButton btnBack;

    public AdminWorkAreaJPanel(JPanel userProcessContainer, UserAccount account) {
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        btnMedicalRecords = createStyledButton("Medical Records Management", new Color(70, 130, 180));
        btnFinance = createStyledButton("Finance Management", new Color(46, 139, 87));
        btnStaffManagement = createStyledButton("Staff Management", new Color(106, 90, 205));
        btnBack = createStyledButton("Back", new Color(108, 117, 125));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Administrative Department Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel - Center aligned buttons
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add buttons with spacing
        mainPanel.add(btnMedicalRecords, gbc);
        gbc.gridy++;
        mainPanel.add(btnFinance, gbc);
        gbc.gridy++;
        mainPanel.add(btnStaffManagement, gbc);

        // Wrap mainPanel in another panel for center alignment
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(mainPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void setupListeners() {
        btnMedicalRecords.addActionListener(e -> {
            MedicalRecordsJPanel panel = new MedicalRecordsJPanel(userProcessContainer);
            userProcessContainer.add("MedicalRecords", panel);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        });

        btnFinance.addActionListener(e -> {
            FinanceManagementJPanel panel = new FinanceManagementJPanel(userProcessContainer);
            userProcessContainer.add("Finance", panel);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        });

        btnStaffManagement.addActionListener(e -> {
            StaffManagementJPanel panel = new StaffManagementJPanel(userProcessContainer);
            userProcessContainer.add("StaffManagement", panel);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.next(userProcessContainer);
        });

        btnBack.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setPreferredSize(new Dimension(250, 40));
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
}