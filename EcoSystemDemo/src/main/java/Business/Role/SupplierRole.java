package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import ui.SupplierRole.SupplierWorkAreaJPanel;
import javax.swing.JPanel;

public class SupplierRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem system) {
        return new SupplierWorkAreaJPanel(userProcessContainer, account, system, enterprise, system);
    }
}
