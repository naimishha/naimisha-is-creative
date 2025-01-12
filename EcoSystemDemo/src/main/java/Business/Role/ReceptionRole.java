package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import UserInterface.ReceptionRole.ReceptionWorkAreaJPanel;
import javax.swing.JPanel;

public class ReceptionRole extends Role {
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem system) {
        return new ReceptionWorkAreaJPanel(userProcessContainer, account, organization, enterprise, system);
    }
} 