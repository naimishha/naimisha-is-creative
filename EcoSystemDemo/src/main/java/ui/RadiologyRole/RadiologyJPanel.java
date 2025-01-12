package ui.RadiologyRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;

import javax.swing.*;
import java.awt.*;

public class RadiologyJPanel extends JPanel {
    public RadiologyJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise) {
        setLayout(new BorderLayout());
        
        JLabel headerLabel = new JLabel("Radiology Department Work Area");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);
        
        // Add additional components specific to Radiology Department
        // Example: Table for radiology work requests
    }
}
