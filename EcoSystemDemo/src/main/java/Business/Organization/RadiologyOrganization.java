package Business.Organization;

import Business.Role.RadiologyRole;
import Business.Role.Role;
import java.util.ArrayList;

public class RadiologyOrganization extends Organization {

    public RadiologyOrganization() {
        super(Type.Radiology.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new RadiologyRole()); // Ensure RadiologyRole exists
        return roles;
    }
}
