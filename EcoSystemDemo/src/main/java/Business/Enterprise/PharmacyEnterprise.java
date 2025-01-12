package Business.Enterprise;

import Business.Role.Role;
import java.util.ArrayList;

public class PharmacyEnterprise extends Enterprise {
    
    public PharmacyEnterprise(String name) {
        super(name, Enterprise.EnterpriseType.Pharmacy);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        return new ArrayList<>();
    }
}