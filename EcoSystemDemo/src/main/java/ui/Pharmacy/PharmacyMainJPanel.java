package ui.Pharmacy;

import javax.swing.*;
import java.awt.*;

public class PharmacyMainJPanel extends JPanel {
    private JPanel userProcessContainer;

    public PharmacyMainJPanel(JPanel userProcessContainer) {
        this.userProcessContainer = userProcessContainer;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        JLabel headerLabel = new JLabel("Pharmacy Dashboard");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton btnInventory = new JButton("Inventory Management");
        JButton btnDrugInfo = new JButton("Drug Information Services");
        JButton btnPatientAssistance = new JButton("Patient Assistance");

        btnInventory.addActionListener(e -> navigateToInventory());
        btnDrugInfo.addActionListener(e -> navigateToDrugInfo());
        btnPatientAssistance.addActionListener(e -> navigateToPatientAssistance());

        buttonPanel.add(btnInventory);
        buttonPanel.add(btnDrugInfo);
        buttonPanel.add(btnPatientAssistance);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void navigateToInventory() {
        InventoryManagementJPanel inventoryPanel = new InventoryManagementJPanel(userProcessContainer);
        userProcessContainer.add("InventoryManagementJPanel", inventoryPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }

    private void navigateToDrugInfo() {
        DrugInformationJPanel drugInfoPanel = new DrugInformationJPanel(userProcessContainer);
        userProcessContainer.add("DrugInformationJPanel", drugInfoPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }

    private void navigateToPatientAssistance() {
        PatientAssistanceJPanel patientAssistancePanel = new PatientAssistanceJPanel(userProcessContainer);
        userProcessContainer.add("PatientAssistanceJPanel", patientAssistancePanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }
}
