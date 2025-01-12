package Business.Organization;

import Business.Role.BloodBankRole;
import Business.Role.Role;
import java.util.ArrayList;

public class BloodBankOrganization extends Organization {

    public BloodBankOrganization() {
        super(Type.BloodBank.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new BloodBankRole());
        return roles;
    }
}
