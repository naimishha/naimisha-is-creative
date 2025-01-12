package Business.Enterprise;

import Business.Role.Role;
import java.util.ArrayList;

public class SupplierEnterprise extends Enterprise {
    
    public SupplierEnterprise(String name) {
        super(name, Enterprise.EnterpriseType.Supplier);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        return new ArrayList<>();
    }
}