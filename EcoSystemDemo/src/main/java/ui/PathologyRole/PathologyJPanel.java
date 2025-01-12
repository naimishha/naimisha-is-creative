package ui.PathologyRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;

import javax.swing.*;
import java.awt.*;

public class PathologyJPanel extends JPanel {
    public PathologyJPanel(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise) {
        setLayout(new BorderLayout());
        
        JLabel headerLabel = new JLabel("Pathology Department Work Area");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);
        
        // Add additional components specific to Pathology Department
        // Example: Table for pathology work requests
    }
}
