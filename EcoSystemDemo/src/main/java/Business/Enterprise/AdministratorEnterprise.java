package Business.Enterprise;

import Business.Role.Role;
import java.util.ArrayList;

public class AdministratorEnterprise extends Enterprise {
    
    public AdministratorEnterprise(String name) {
        super(name, Enterprise.EnterpriseType.Administrator);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        return new ArrayList<>();
    }
}