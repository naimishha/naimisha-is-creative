package ui.Pharmacy;

import javax.swing.*;
import java.awt.*;

public class DrugInformationJPanel extends JPanel {
    private JTextField txtSearch;
    private JTextArea txtDrugDetails;

    public DrugInformationJPanel(JPanel userProcessContainer) {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel titleLabel = new JLabel("Drug Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(30);
        JButton btnSearch = new JButton("Search");
        searchPanel.add(new JLabel("Search Drug:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        add(searchPanel, BorderLayout.NORTH);

        // Drug Details
        txtDrugDetails = new JTextArea();
        txtDrugDetails.setLineWrap(true);
        txtDrugDetails.setWrapStyleWord(true);
        txtDrugDetails.setEditable(false);

        add(new JScrollPane(txtDrugDetails), BorderLayout.CENTER);

        // Search Listener
        btnSearch.addActionListener(e -> searchDrug());
    }

    private void searchDrug() {
        String drugName = txtSearch.getText().trim();
        if (drugName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a drug name to search.");
            return;
        }

        // Simulated search (Replace with actual database query)
        txtDrugDetails.setText("Drug: " + drugName + "\nDescription: Common drug used for XYZ.");
    }
}
