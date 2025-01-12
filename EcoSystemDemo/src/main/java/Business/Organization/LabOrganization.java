package Business.Organization;

import Business.Role.LabAssistantRole;
import Business.Role.Role;
import java.util.ArrayList;

public class LabOrganization extends Organization {

    public LabOrganization() {
        super(Type.Lab.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new LabAssistantRole()); // Lab Assistant Role for all lab-related functionalities
        return roles;
    }
}
