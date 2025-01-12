package ui.LabAssistantRole;

import Business.WorkQueue.LabTestWorkRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LabTestResultJPanel extends JPanel {
    private JPanel userProcessContainer;
    private LabTestWorkRequest request;

    private JTextField testResultField;
    private JButton btnSubmit;
    private JButton btnBack;

    public LabTestResultJPanel(JPanel userProcessContainer, LabTestWorkRequest request) {
        this.userProcessContainer = userProcessContainer;
        this.request = request;

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        testResultField = new JTextField(20);
        btnSubmit = createButton("Submit Result");
        btnBack = createButton("Back");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel headerLabel = new JLabel("Update Test Result");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        addFormRow(formPanel, "Test Name:", new JLabel(request.getTestResult()), gbc, 0);
        addFormRow(formPanel, "Result:", testResultField, gbc, 1);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnBack);
        buttonPanel.add(btnSubmit);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnSubmit.addActionListener((ActionEvent e) -> {
            if (testResultField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the test result.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            request.setTestResult(testResultField.getText().trim());
            request.setStatus("Completed");

            JOptionPane.showMessageDialog(this, "Test result updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Navigate back
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });

        btnBack.addActionListener((ActionEvent e) -> {
            userProcessContainer.remove(this);
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);
        });
    }

    private void addFormRow(JPanel panel, String label, JComponent component, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
}
