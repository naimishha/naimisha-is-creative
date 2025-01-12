package Business.Enterprise;

import java.util.ArrayList;

import Business.Role.Role;

public class MedicalEquipmentEnterprise extends Enterprise {

    public MedicalEquipmentEnterprise(String name) {
        super(name, EnterpriseType.MedicalEquipment);
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
