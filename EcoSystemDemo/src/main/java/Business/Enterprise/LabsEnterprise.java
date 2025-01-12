package Business.Enterprise;

import Business.Role.Role;
import java.util.ArrayList;

public class LabsEnterprise extends Enterprise {
    
    public LabsEnterprise(String name) {
        super(name, Enterprise.EnterpriseType.DiagnosticLabs);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        return new ArrayList<>();
    }
}