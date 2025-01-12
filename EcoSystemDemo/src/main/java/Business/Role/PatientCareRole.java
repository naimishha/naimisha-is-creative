package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import UserInterface.PatientCareRole.PatientCareWorkAreaJPanel;

public class PatientCareRole extends Role {
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem system) {
        return new PatientCareWorkAreaJPanel(userProcessContainer, account, organization, enterprise, system);
    }
} 