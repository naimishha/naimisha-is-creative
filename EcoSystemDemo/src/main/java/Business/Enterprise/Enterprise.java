package Business.Enterprise;

import Business.Organization.Organization;
import Business.Organization.OrganizationDirectory;

public abstract class Enterprise extends Organization {
    
    private EnterpriseType enterpriseType;
    private OrganizationDirectory organizationDirectory;

    public Enterprise(String name, EnterpriseType type) {
        super(name);
        this.enterpriseType = type;
        organizationDirectory = new OrganizationDirectory();
    }
    
    public enum EnterpriseType {
        Hospital("Hospital"),
        DiagnosticLabs("Diagnostic Labs"),
        Pharmacy("Pharmacy"),
        Administrator("Administrator"),
        Supplier("Supplier"),
        ResearchCenter("Research Center"),
        MedicalEquipment("Medical Equipment Supplier");
        
        private String value;
        
        private EnterpriseType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return value;
        }
    }

    public OrganizationDirectory getOrganizationDirectory() {
        return organizationDirectory;
    }

    public EnterpriseType getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(EnterpriseType enterpriseType) {
        this.enterpriseType = enterpriseType;
    }
}