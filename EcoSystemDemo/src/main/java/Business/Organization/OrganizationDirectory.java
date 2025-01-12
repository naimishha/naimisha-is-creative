package Business.Organization;

import Business.Organization.Organization.Type;
import java.util.ArrayList;

public class OrganizationDirectory {

    private ArrayList<Organization> organizationList;

    public OrganizationDirectory() {
        organizationList = new ArrayList<>();
    }

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }

    public Organization createOrganization(Type type) {
        Organization organization = null;
        switch (type) {
            case Admin:
                organization = new AdminOrganization();
                break;
            case Doctor:
                organization = new DoctorOrganization();
                break;
            case Lab:
                organization = new LabOrganization(); // Consolidated Labs organization
                break;
            case PatientCare:
                organization = new PatientCareOrganization();
                break;
            case Pharmacy:
                organization = new PharmacyOrganization();
                break;
            case Reception:
                organization = new ReceptionOrganization();
                break;
            case Supplier:
                organization = new SupplierOrganization();
                break;
            default:
                throw new IllegalArgumentException("Unsupported organization type: " + type);
        }
        if (organization != null) {
            organizationList.add(organization);
        }
        return organization;
    }
}
