package ui.BloodBankRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;

import javax.swing.*;
import java.awt.*;

public class BloodBankJPanel extends JPanel {
    public BloodBankJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise) {
        setLayout(new BorderLayout());
        
        JLabel headerLabel = new JLabel("Blood Bank Work Area");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);
        
        // Add additional components specific to Blood Bank Department
        // Example: Table for blood bank requests
    }
}
