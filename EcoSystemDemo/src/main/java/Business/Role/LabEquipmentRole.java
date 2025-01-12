package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import ui.SupplierRole.LabEquipmentJPanel;
import javax.swing.JPanel;

public class LabEquipmentRole extends Role {

    @Override
    public JPanel createWorkArea(
            JPanel userProcessContainer,
            UserAccount account,
            Organization organization,
            Enterprise enterprise,
            EcoSystem system
    ) {
        return new LabEquipmentJPanel(userProcessContainer);
    }

    @Override
    public String toString() {
        return "LabEquipmentRole";
    }
}
