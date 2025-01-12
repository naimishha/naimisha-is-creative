package Business.Enterprise;

import java.util.ArrayList;

public class EnterpriseDirectory {
    private ArrayList<Enterprise> enterpriseList;

    public EnterpriseDirectory() {
        enterpriseList = new ArrayList<>();
    }

    public ArrayList<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }

    public void setEnterpriseList(ArrayList<Enterprise> enterpriseList) {
        this.enterpriseList = enterpriseList;
    }

    // Create and add enterprise based on type
    public Enterprise createAndAddEnterprise(String name, Enterprise.EnterpriseType type) {
        Enterprise enterprise = null;

        switch (type) {
            case Hospital:
                enterprise = new HospitalEnterprise(name);
                break;

            case Supplier:
                enterprise = new SupplierEnterprise(name); // Ensure SupplierEnterprise exists
                break;

            case DiagnosticLabs:
                enterprise = new LabsEnterprise(name); // Ensure LabsEnterprise exists
                break;

            case Pharmacy:
                enterprise = new PharmacyEnterprise(name); // Ensure PharmacyEnterprise exists
                break;

            case Administrator:
                enterprise = new AdministratorEnterprise(name); // Ensure AdministratorEnterprise exists
                break;

            case ResearchCenter:
                enterprise = new ResearchCenterEnterprise(name); // Ensure ResearchCenterEnterprise exists
                break;
            case MedicalEquipment:
                enterprise = new MedicalEquipmentEnterprise(name); // Ensure MedicalEquipmentEnterprise exists
                break;

      

            default:
                throw new IllegalArgumentException("Unsupported enterprise type: " + type);
        }

        if (enterprise != null) {
            enterpriseList.add(enterprise);
        }

        return enterprise;
    }
}
