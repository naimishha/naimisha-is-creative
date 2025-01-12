package Business.Enterprise;

import java.util.ArrayList;

import Business.Role.Role;

public class ResearchCenterEnterprise extends Enterprise {

    public ResearchCenterEnterprise(String name) {
        super(name, EnterpriseType.ResearchCenter);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSupportedRole'");
    }
}
