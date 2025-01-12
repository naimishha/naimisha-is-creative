package ui.Pharmacy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PharmacyDashboardJPanel extends JPanel {
    private JPanel userProcessContainer;

    public PharmacyDashboardJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel titleLabel = new JLabel("Pharmacy Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Buttons
        JButton btnInventory = new JButton("Inventory Management");
        JButton btnDrugInfo = new JButton("Drug Information");

        btnInventory.setFont(new Font("Arial", Font.PLAIN, 16));
        btnDrugInfo.setFont(new Font("Arial", Font.PLAIN, 16));

        btnInventory.addActionListener(this::openInventoryManagement);
        btnDrugInfo.addActionListener(this::openDrugInformation);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(btnInventory);
        buttonPanel.add(btnDrugInfo);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void openInventoryManagement(ActionEvent evt) {
        InventoryManagementJPanel inventoryPanel = new InventoryManagementJPanel(userProcessContainer);
        userProcessContainer.add("InventoryManagementJPanel", inventoryPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }

    private void openDrugInformation(ActionEvent evt) {
        DrugInformationJPanel drugInfoPanel = new DrugInformationJPanel(userProcessContainer);
        userProcessContainer.add("DrugInformationJPanel", drugInfoPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);

    }

    
}
