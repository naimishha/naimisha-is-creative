package Business.Organization;

import Business.Role.PatientCareRole;
import Business.Role.Role;
import java.util.ArrayList;

public class PatientCareOrganization extends Organization {
    public PatientCareOrganization() {
        super("Patient Care Organization");
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new PatientCareRole());
        return roles;
    }
} 