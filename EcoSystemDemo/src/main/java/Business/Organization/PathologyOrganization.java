package Business.Organization;

import Business.Role.PathologyRole;
import Business.Role.Role;
import java.util.ArrayList;

public class PathologyOrganization extends Organization {

    public PathologyOrganization() {
        super(Type.Pathology.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new PathologyRole());
        return roles;
    }
}
