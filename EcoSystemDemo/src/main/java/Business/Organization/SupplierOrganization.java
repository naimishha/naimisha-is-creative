package Business.Organization;

import Business.Role.SupplierRole;
import Business.Role.Role;
import java.util.ArrayList;

public class SupplierOrganization extends Organization {

    public enum SupplyType {
        SurgicalEquipment, LabEquipment, Pharmaceutical, EmergencySupplies, PeriodicMaintenance
    }

    private SupplyType supplyType;

    public SupplierOrganization() {
        super(Type.Supplier.getValue());
    }

    public SupplyType getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(SupplyType supplyType) {
        this.supplyType = supplyType;
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new SupplierRole()); // Generic supplier role
        return roles;
    }
}
