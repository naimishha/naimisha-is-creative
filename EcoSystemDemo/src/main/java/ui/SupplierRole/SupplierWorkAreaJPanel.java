package ui.SupplierRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.*;
import java.awt.*;

public class SupplierWorkAreaJPanel extends JPanel {
    private JPanel userProcessContainer;
    private UserAccount account;

    public SupplierWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem system) {
        this.userProcessContainer = userProcessContainer;
        this.account = account;

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Supplier Management Dashboard", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnSurgical = createButton("Surgical Equipment Management", Color.CYAN);
        JButton btnLab = createButton("Lab Equipment Supplies", Color.GREEN);
        JButton btnPharma = createButton("Pharmaceutical Supplies", Color.MAGENTA);
        JButton btnEmergency = createButton("Emergency Supply Requests", Color.ORANGE);
        JButton btnMaintenance = createButton("Periodic Maintenance Requests", Color.RED);

        buttonPanel.add(btnSurgical);
        buttonPanel.add(btnLab);
        buttonPanel.add(btnPharma);
        buttonPanel.add(btnEmergency);
        buttonPanel.add(btnMaintenance);

        add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });
        add(backButton, BorderLayout.SOUTH);

        // Add ActionListeners for each button to navigate to respective panels
        btnSurgical.addActionListener(e -> navigateToPanel(new SurgicalEquipmentJPanel(userProcessContainer)));
        btnLab.addActionListener(e -> navigateToPanel(new LabEquipmentJPanel(userProcessContainer)));
        btnPharma.addActionListener(e -> navigateToPanel(new PharmaceuticalSuppliesJPanel(userProcessContainer)));
        btnEmergency.addActionListener(e -> navigateToPanel(new EmergencySuppliesJPanel(userProcessContainer)));
        btnMaintenance.addActionListener(e -> navigateToPanel(new PeriodicMaintenanceJPanel(userProcessContainer)));
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void navigateToPanel(JPanel panel) {
        userProcessContainer.add(panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
}
