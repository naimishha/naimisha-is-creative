package Business.Organization;

import Business.Role.ReceptionRole;
import Business.Role.Role;
import java.util.ArrayList;

public class ReceptionOrganization extends Organization {
    public ReceptionOrganization() {
        super("Reception Organization");
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new ReceptionRole());
        return roles;
    }
} 