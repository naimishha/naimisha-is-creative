package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import ui.Pharmacy.PharmacyDashboardJPanel;
import javax.swing.JPanel;

public class PharmacyRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        return new PharmacyDashboardJPanel(userProcessContainer);
    }

    @Override
    public String toString() {
        return "Pharmacy Role";
    }
}
